package com.busdriver;

import com.busdriver.validator.BusValidator;

/**
 * Bus class represents a bus in the Intelligent Bus Driver Guidance System.
 * Each bus has a unique ID, capacity, fuel level, and fuel type.
 *
 * Validation rules:
 *   B1: busID must be exactly 8 digits, all numeric, unique
 *   B2: Capacity cannot increase during update (but can decrease)
 *   B3: Drivers older than 50 cannot drive buses with capacity >= 50
 *   B4: Only drivers with >= 5 years experience can drive electric buses
 *   B5: Only Heavy or PublicTransport license holders can drive electric/hybrid buses
 */
public class Bus {

    private String busID;
    private int capacity;
    private double fuelLevel;
    private String fuelType;  // Diesel, Hybrid, Electricity
    private String driverID;  // Omar's Extension: Stores assigned driver ID (empty string if unassigned)

    /**
     * Constructor for creating an unassigned Bus
     */
    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {
        this(busID, capacity, fuelLevel, fuelType, "");
    }

    /**
     * Full Constructor with driver assignment
     */
    public Bus(String busID, int capacity, double fuelLevel, String fuelType, String driverID) {
        this.busID = busID;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
        this.fuelType = fuelType;
        this.driverID = (driverID == null) ? "" : driverID;

        // Trigger basic bus field validations (like B1 format)
        BusValidator.validateBus(this);
    }

    // GETTERS
    public String getBusID() { return this.busID; }
    public int getCapacity() { return this.capacity; }
    public double getFuelLevel() { return this.fuelLevel; }
    public String getFuelType() { return this.fuelType; }
    public String getDriverID() { return this.driverID; }

    // SETTERS (Note: busID is unique and immutable, so we don't provide a setter)
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setFuelLevel(double fuelLevel) { this.fuelLevel = fuelLevel; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public void setDriverID(String driverID) { this.driverID = (driverID == null) ? "" : driverID; }

    /**
     * Converts Bus to pipe-delimited string for TXT storage
     * Format: busID|capacity|fuelLevel|fuelType|driverID
     */
    public String toFileString() { 
        String assignedDriver = (driverID.isEmpty()) ? "none" : driverID;
        return busID + "|" + capacity + "|" + fuelLevel + "|" + fuelType + "|" + assignedDriver;
    }

    /**
     * Creates Bus from pipe-delimited string
     */
    public static Bus fromFileString(String line) { 
        String[] splitLine = line.split("\\|");
        
        String busID = splitLine[0];
        int capacity = Integer.parseInt(splitLine[1]);
        double fuelLevel = Double.parseDouble(splitLine[2]);
        String fuelType = splitLine[3];
        
        // Handle optional driverID field if present in storage, defaults to empty
        String driverID = (splitLine.length > 4 && !splitLine[4].equals("none")) ? splitLine[4] : "";

        return new Bus(busID, capacity, fuelLevel, fuelType, driverID); 
    }

    @Override
    public String toString() { 
        return "Bus{" +
                "busID='" + busID + '\'' +
                ", capacity=" + capacity +
                ", fuelLevel=" + fuelLevel +
                ", fuelType='" + fuelType + '\'' +
                ", driverID='" + driverID + '\'' +
                '}'; 
    }
}