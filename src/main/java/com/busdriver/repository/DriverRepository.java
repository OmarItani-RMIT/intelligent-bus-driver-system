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

    // TODO: Implement the following

    public boolean add(Driver driver) {
        // TODO: Implement append and duplicate check
        return false;
    }

    public Driver retrieve(String driverID) {
        // TODO: Implement retrieve by ID
        return null;
    }

    public boolean update(Driver updatedDriver) {
        // TODO: Implement update and rewrite operations
        return false;
    }

    public void clear() {
        // TODO: Implement cleanup
    }
}