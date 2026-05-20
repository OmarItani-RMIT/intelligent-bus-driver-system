package com.busdriver.validator;

import com.busdriver.Bus;
import com.busdriver.Driver;

/**
 * Validator class for Bus-related operations.
 * Implements all bus validation conditions (B1 - B5).
 *
 * B1: Bus ID Rules - exactly 8 digits, all numeric, unique
 * B2: Capacity Update Restriction - capacity cannot increase during update
 * B3: Driver Age Restriction - drivers older than 50 cannot drive buses with capacity >= 50
 * B4: Electric Bus Restriction - only drivers with >= 5 years experience can drive electric buses
 * B5: Driver Licence Restriction - only Heavy/PublicTransport license for electric/hybrid buses
 */
public class BusValidator {

    // TODO: Implement validateBus() - validates entire Bus object against B1
    public static boolean validateBus(Bus bus) {
        // TODO: Call all individual validation methods
        return true;
    }

    // TODO: Implement validateBusID() - B1: Bus ID format validation
    public static boolean validateBusID(String busID) {
        // B1 Rules:
        // - Must be exactly 8 characters long
        // - All characters must be digits (0-9)
        // Throw IllegalArgumentException with descriptive message if invalid
        return true;
    }

    // TODO: Implement validateCapacityUpdate() - B2: Capacity update restriction
    public static boolean validateCapacityUpdate(Bus existingBus, int newCapacity) {
        // B2: Capacity cannot increase during update, but can decrease or stay same
        return true;
    }

    // TODO: Implement validateDriverAgeRestriction() - B3: Driver age vs bus capacity
    public static boolean validateDriverAgeRestriction(Driver driver, Bus bus) {
        // B3: Drivers older than 50 cannot drive buses with capacity >= 50
        // Use DriverValidator.calculateAge() to get driver's age
        return true;
    }

    // TODO: Implement validateElectricBusRestriction() - B4: Experience requirement for electric
    public static boolean validateElectricBusRestriction(Driver driver, Bus bus) {
        // B4: Only drivers with >= 5 years experience can drive electric buses
        // This only applies when fuelType = "Electricity"
        return true;
    }

    // TODO: Implement validateDriverLicenceRestriction() - B5: License requirement for electric/hybrid
    public static boolean validateDriverLicenceRestriction(Driver driver, Bus bus) {
        // B5: Only Heavy or PublicTransport license holders can drive
        //     electric (Electricity) or hybrid (Hybrid) buses
        return true;
    }

    // TODO: Implement validateFuelType() - helper for fuel type validation
    public static boolean validateFuelType(String fuelType) {
        // Must be one of: Diesel, Hybrid, Electricity
        return true;
    }

    // TODO: Implement validateCapacity() - helper for capacity validation
    public static boolean validateCapacity(int capacity) {
        // Must be positive (> 0)
        return true;
    }

    // TODO: Implement validateFuelLevel() - helper for fuel level validation
    public static boolean validateFuelLevel(double fuelLevel) {
        // Must be non-negative (>= 0)
        return true;
    }
}