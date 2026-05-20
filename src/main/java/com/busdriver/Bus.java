package com.busdriver;

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

    // TODO: Implement constructor with all fields
    public Bus(String busID, int capacity, double fuelLevel, String fuelType) {
        // TODO: Assign all parameters to fields
    }

    // TODO: Implement all getters
    public String getBusID() { return null; }
    public int getCapacity() { return 0; }
    public double getFuelLevel() { return 0.0; }
    public String getFuelType() { return null; }

    // TODO: Implement setters
    public void setCapacity(int capacity) { /* TODO */ }
    public void setFuelLevel(double fuelLevel) { /* TODO */ }
    public void setFuelType(String fuelType) { /* TODO */ }

    // TODO: Implement toFileString() - converts Bus to pipe-delimited string for TXT storage
    // Format: busID|capacity|fuelLevel|fuelType
    public String toFileString() { return null; }

    // TODO: Implement fromFileString() - creates Bus from pipe-delimited string
    public static Bus fromFileString(String line) { return null; }

    @Override
    public String toString() { return "Bus{}"; }
}