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

    // TODO: Implement add() - Add a new driver after validation
    public boolean add(Driver driver) {
        // Steps:
        // 1. Call DriverValidator.validateDriver(driver) to validate D1, D2, D3
        // 2. Check for duplicate driver ID using retrieve()
        // 3. Append driver.toFileString() to the TXT file
        // Throw IllegalArgumentException if validation fails or duplicate ID
        return true;
    }

    // TODO: Implement retrieve() - Retrieve a driver by driverID
    public Driver retrieve(String driverID) {
        // Steps:
        // 1. Read all drivers from file using retrieveAll()
        // 2. Find and return the driver with matching driverID
        // 3. Return null if not found
        return null;
    }

    // TODO: Implement retrieveAll() - Retrieve all drivers from TXT file
    public List<Driver> retrieveAll() {
        // Steps:
        // 1. Open the TXT file for reading
        // 2. Read each line, parse using Driver.fromFileString()
        // 3. Return list of all Driver objects
        // Return empty list if file doesn't exist
        return new ArrayList<>();
    }

    // TODO: Implement update() - Update an existing driver with validation
    public boolean update(Driver updatedDriver) {
        // Steps:
        // 1. Retrieve existing driver - throw exception if not found
        // 2. Call DriverValidator.validateImmutableFields(existing, updated) - D5
        // 3. Call DriverValidator.validateLicenseUpdateRestriction(existing, newLicense) - D4
        // 4. Validate updated fields (D2, D3)
        // 5. Rewrite entire file with updated driver replacing the old one
        return true;
    }

    // TODO: Implement count() - Return number of stored drivers
    public int count() {
        // Simply return retrieveAll().size()
        return 0;
    }

    // TODO: Implement clear() - Clear all driver data (used for testing)
    public void clear() {
        // Overwrite file with empty content
    }
}