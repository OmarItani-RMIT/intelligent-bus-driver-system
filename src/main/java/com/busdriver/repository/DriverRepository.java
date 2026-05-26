package com.busdriver.repository;

import com.busdriver.Driver;
import com.busdriver.validator.DriverValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DriverRepository manages the storage and retrieval of Driver objects using TXT files.
 * Supports Add, Update, Retrieve, and Count operations.
 */
public class DriverRepository {

    private final String filePath;

    public DriverRepository(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves all stored drivers from the TXT file.
     * Returns an empty list if the file does not exist yet.
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
     * Returns the total count of stored drivers.
     */
    public int count() {
        return retrieveAll().size();
    }

    /**
     * Adds a new driver to the TXT storage after validation and duplicate checking.
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
     * Retrieves a driver by their ID.
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

    // TODO: Implement the following

    public boolean update(Driver updatedDriver) {
        // TODO: Implement update and rewrite operations
        return false;
    }

    public void clear() {
        // TODO: Implement cleanup
    }
}