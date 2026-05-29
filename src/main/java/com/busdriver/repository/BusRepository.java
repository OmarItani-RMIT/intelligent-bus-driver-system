package com.busdriver.repository;

import com.busdriver.Bus;
import com.busdriver.Driver;
import com.busdriver.validator.BusValidator;
import com.busdriver.validator.DriverValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BusRepository manages the storage and retrieval of Bus objects using TXT files.
 * Supports Add, Update, Retrieve, and Count operations.
 */
public class BusRepository {

    private final String filePath;

    public BusRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Validates the assigned driver against rules B3, B4, and B5
     */
    public void validateDriverAssignment(Bus bus, DriverRepository driverRepo) {
        if (bus.getDriverID() == null || bus.getDriverID().isEmpty() || bus.getDriverID().equals("none")) {
            return; // No driver assigned, skip checks
        }

        Driver driver = driverRepo.retrieve(bus.getDriverID());
        if (driver == null) {
            throw new IllegalArgumentException("Assigned driver with ID " + bus.getDriverID() + " does not exist.");
        }

        // B3: Drivers older than 50 cannot drive buses with capacity >= 50
        int age = DriverValidator.calculateAge(driver.getBirthdate());
        if (age > 50 && bus.getCapacity() >= 50) {
            throw new IllegalArgumentException("[B3 FAILED] Drivers older than 50 years cannot drive buses with a capacity of 50 or more.");
        }

        // B4: Only drivers with >= 5 years experience can drive electric buses
        if (bus.getFuelType().equalsIgnoreCase("Electricity") && driver.getExperienceYears() < 5) {
            throw new IllegalArgumentException("[B4 FAILED] Only drivers with at least 5 years of experience can drive electric buses.");
        }

        // B5: Only Heavy or PublicTransport license holders can drive electric/hybrid buses
        boolean isElectricOrHybrid = bus.getFuelType().equalsIgnoreCase("Electricity") || bus.getFuelType().equalsIgnoreCase("Hybrid");
        boolean hasValidLicense = driver.getLicenseType().equalsIgnoreCase("Heavy") || driver.getLicenseType().equalsIgnoreCase("PublicTransport");
        if (isElectricOrHybrid && !hasValidLicense) {
            throw new IllegalArgumentException("[B5 FAILED] Only drivers holding a Heavy or PublicTransport license are permitted to operate electric/hybrid buses.");
        }
    }

    /**
     * Adds a new bus after validation
     */
    public boolean add(Bus bus) {
        // Validate basic B1 rule
        if (!BusValidator.validateBus(bus)) {
            throw new IllegalArgumentException("Bus is invalid and cannot be appended");
        }

        // Fixed Bug: Safe duplicate check
        if (retrieve(bus.getBusID()) != null) {
            throw new IllegalArgumentException("Bus has the same busID as an existing bus, and cannot be appended");
        }

        // Append to database file
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(bus.toFileString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to bus file: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new bus with driver validation check (B3, B4, B5)
     */
    public boolean add(Bus bus, DriverRepository driverRepo) {
        validateDriverAssignment(bus, driverRepo);
        return add(bus);
    }

    /**
     * Retrieves a bus by busID
     */
    public Bus retrieve(String busID) {
        List<Bus> buses = retrieveAll();
        for (Bus bus : buses) {
            if (busID.equals(bus.getBusID())) {
                return bus;
            }
        }
        return null;
    }

    /**
     * Retrieve all buses from TXT file
     */
    public List<Bus> retrieveAll() {
        ArrayList<Bus> buses = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return buses;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    buses.add(Bus.fromFileString(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading bus file: " + e.getMessage(), e);
        }
        return buses;
    }

    /**
     * Updates an existing bus with formatting validations and B2 checks
     */
    public boolean update(Bus updatedBus) {
        Bus oldBus = retrieve(updatedBus.getBusID());
        if (oldBus == null) {
            throw new IllegalArgumentException("Bus with ID " + updatedBus.getBusID() + " not found.");
        }

        // B2 Capacity Check
        if (!BusValidator.validateCapacityUpdate(oldBus, updatedBus.getCapacity())) {
            throw new IllegalArgumentException("[B2 FAILED] Bus cannot increase in capacity while updating. " 
                                            + "Got old capacity of " + oldBus.getCapacity() 
                                            + " and a new capacity of " + updatedBus.getCapacity());
        }

        if (!BusValidator.validateBus(updatedBus)) {
            throw new IllegalArgumentException("Update fields for the bus are not valid.");
        }

        List<Bus> buses = retrieveAll();
        for (int i = 0; i < buses.size(); ++i) {
            if (buses.get(i).getBusID().equals(updatedBus.getBusID())) {
                buses.set(i, updatedBus);
            }
        }

        // Rewrite entire file
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (Bus bus : buses) {
                writer.write(bus.toFileString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to bus file during update: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing bus with driver validation check (B3, B4, B5)
     */
    public boolean update(Bus updatedBus, DriverRepository driverRepo) {
        validateDriverAssignment(updatedBus, driverRepo);
        return update(updatedBus);
    }

    public int count() {
        return retrieveAll().size();
    }

    public void clear() {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        try {
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Error clearing bus repository: " + e.getMessage(), e);
        }
    }
}