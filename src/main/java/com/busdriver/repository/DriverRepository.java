package com.busdriver.repository;

import com.busdriver.Driver;
import com.busdriver.validator.DriverValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DriverRepository manages the storage and retrieval of Driver objects using TXT files.
 * Supports Add, Update, Retrieve, and Count operations.
 *
 * File format (human-readable, pipe-delimited):
 * driverID|name|experienceYears|licenseType|StreetNum|StreetName|City|State|Country|birthdate
 *
 * Validation on Add and Update:
 *   D1: Driver ID rules (uniqueness, format)
 *   D2: Address format
 *   D3: Birthdate format
 *   D4: License update restriction
 *   D5: Immutable fields (driverID, name)
 */
public class DriverRepository {

    private final String filePath;

    public DriverRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Adds a new driver to the TXT storage after validation.
     */
    public boolean add(Driver driver) {
        // 1. Validate D1, D2, D3 format rules
        DriverValidator.validateDriver(driver);

        // 2. Prevent duplicate Driver IDs
        if (retrieve(driver.getDriverID()) != null) {
            throw new IllegalArgumentException("[D1 FAILED] Duplicate driver ID: " + driver.getDriverID());
        }

        // Create directory structure if it doesn't exist
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // 3. Append to file (true parameter means append mode)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(driver.toFileString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to driver file: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves a driver by their ID
     */
    public Driver retrieve(String driverID) {
        if (driverID == null) return null;
        for (Driver d : retrieveAll()) {
            if (driverID.equals(d.getDriverID())) {
                return d;
            }
        }
        return null;
    }

    /**
     * Retrieves all stored drivers from the TXT file
     */
    public List<Driver> retrieveAll() {
        List<Driver> drivers = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return drivers;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    drivers.add(Driver.fromFileString(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading driver file: " + e.getMessage(), e);
        }
        return drivers;
    }

    /**
     * Updates an existing driver while enforcing D4, D5, and formatting validations
     */
    public boolean update(Driver updatedDriver) {
        // 1. Retrieve the existing driver from database
        Driver existing = retrieve(updatedDriver.getDriverID());
        if (existing == null) {
            throw new IllegalArgumentException("Driver with ID " + updatedDriver.getDriverID() + " not found.");
        }

        // 2. D5 Check: driverID and name cannot be modified during updates
        if (!existing.getDriverID().equals(updatedDriver.getDriverID())) {
            throw new IllegalArgumentException("[D5 FAILED] Driver ID is immutable and cannot be modified.");
        }
        if (!existing.getName().equals(updatedDriver.getName())) {
            throw new IllegalArgumentException("[D5 FAILED] Name is immutable and cannot be modified.");
        }

        // 3. D4 Check: Experienced driver (>10 years) cannot change licenseType
        if (existing.getExperienceYears() > 10 && !existing.getLicenseType().equals(updatedDriver.getLicenseType())) {
            throw new IllegalArgumentException("[D4 FAILED] License type cannot be changed for experienced drivers (>10 years experience).");
        }

        // 4. Validate other updated fields (address format, birthdate format, etc.)
        DriverValidator.validateDriver(updatedDriver);

        // 5. Rewrite file with the updated driver replacing the old one
        List<Driver> allDrivers = retrieveAll();
        boolean replaced = false;
        for (int i = 0; i < allDrivers.size(); i++) {
            if (allDrivers.get(i).getDriverID().equals(updatedDriver.getDriverID())) {
                allDrivers.set(i, updatedDriver);
                replaced = true;
                break;
            }
        }

        if (replaced) {
            File file = new File(filePath);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) { // false to overwrite
                for (Driver d : allDrivers) {
                    writer.write(d.toFileString());
                    writer.newLine();
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException("Error rewriting driver file during update: " + e.getMessage(), e);
            }
        }
        return false;
    }

    /**
     * Returns the total count of stored drivers
     */
    public int count() {
        return retrieveAll().size();
    }

    /**
     * Clears all driver data (crucial for resetting data between tests)
     */
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
            throw new RuntimeException("Error clearing driver repository file: " + e.getMessage(), e);
        }
    }
}