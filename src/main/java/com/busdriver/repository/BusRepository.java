package com.busdriver.repository;

import com.busdriver.Bus;
import com.busdriver.validator.BusValidator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BusRepository manages the storage and retrieval of Bus objects using TXT files.
 * Supports Add, Update, Retrieve, and Count operations.
 *
 * File format (human-readable, pipe-delimited):
 * busID|capacity|fuelLevel|fuelType
 *
 * Validation on Add and Update:
 *   B1: Bus ID rules (uniqueness, exactly 8 digits)
 *   B2: Capacity update restriction (cannot increase)
 */
public class BusRepository {

    private final String filePath;

    public BusRepository(String filePath) {
        this.filePath = filePath;
    }



    // TODO: Implement add() - Add a new bus after validation
    public boolean add(Bus bus) {
        // Steps:
        // 1. Call BusValidator.validateBus(bus) to validate B1
        // 2. Check for duplicate bus ID using retrieve()
        // 3. Append bus.toFileString() to the TXT file
        // Throw IllegalArgumentException if validation fails or duplicate ID

        // validate the bus using the bus validator
        if (!(BusValidator.validateBus(bus))){
            throw new IllegalArgumentException("Bus is invalid and cannot be appended");
        }

        // ensure the busID is unique
        if (!(retrieve(bus.getBusID()).equals(null))){
            throw new IllegalArgumentException("Bus has the same busID as an existing bus, and cannot be appended");
        }


        //Append the bus to the file
        File file = new File(filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // 3. Append to file (true parameter means append mode)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(bus.toFileString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("Error writing to bus file: " + e.getMessage(), e);
        }
    
    }



    // TODO: Implement retrieve() - Retrieve a bus by busID
    public Bus retrieve(String busID) {
        // Steps:
        // 1. Read all buses from file using retrieveAll()
        // 2. Find and return the bus with matching busID
        // 3. Return null if not found

        // Grab the list of busses from the file
        List<Bus> busses =  retrieveAll();

        // loop through each bus to find the one with a matching busID, and return the bus if found
        for (Bus bus : busses){
            if (busID.equals(bus.getBusID())){
                return bus;
            }
        }

        // return null if the busID is not found
        return null;
    }

    // TODO: Implement retrieveAll() - Retrieve all buses from TXT file
    public List<Bus> retrieveAll() {
        // Steps:
        // 1. Open the TXT file for reading
        // 2. Read each line, parse using Bus.fromFileString()
        // 3. Return list of all Bus objects
        // Return empty list if file doesn't exist

        // Return variable 
        ArrayList<Bus> busses = new ArrayList<>();

        //file to read
        File file = new File(filePath);

        // attempt to make a reader object and use it:
         try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = null; 
            // while the current read line is not null, create a bus object and append it to the list
            while ((line = reader.readLine()) != null){
                Bus appendBus = Bus.fromFileString(line);
                busses.add(appendBus);
            }

        } catch (IOException e) {
            //throw exception if error occurs
            throw new RuntimeException("Error reading bus file: " + e.getMessage(), e);
        }

        // return the list of busses
        return busses;
    }

    // TODO: Implement update() - Update an existing bus with validation
    public boolean update(Bus updatedBus) {
        // Steps:
        // 1. Retrieve existing bus - throw exception if not found
        // 2. Call BusValidator.validateCapacityUpdate(existing, newCapacity) - B2
        // 3. Validate updated fields
        // 4. Rewrite entire file with updated bus replacing the old one
        return true;
    }

    // TODO: Implement count() - Return number of stored buses
    public int count() {
        // Simply return retrieveAll().size()
        return 0;
    }

    // TODO: Implement clear() - Clear all bus data (used for testing)
    public void clear() {
        // Overwrite file with empty content
    }
}