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

    /**
     * Validates the entire Bus object against B1 rules.
     */
    public static boolean validateBus(Bus bus) {
        return validateBusID(bus.getBusID()) && validateCapacity(bus.getCapacity());
    }

    /**
     * B1: Bus ID format validation.
     * Must be exactly 8 characters long and consist of only digits (0-9).
     */
    public static boolean validateBusID(String busID) {
        boolean isValidLength = false;
        final int VALID_LENGTH = 8;
        boolean isValidFormat = true;

        int idLength = busID.length();

        if (idLength == VALID_LENGTH) {
            isValidLength = true;
        } else if (idLength < VALID_LENGTH) { 
            throw new IllegalArgumentException("[B1 C1 FAILED] Length of ID is LESS than the valid length, got " + idLength);
        } else {
            throw new IllegalArgumentException("[B1 C1 FAILED] Length of ID is MORE than the valid length, got " + idLength);
        }

        char[] busIDArray = busID.toCharArray();
        int busArrayLength = busIDArray.length;

        for (int index = 0; index < busArrayLength; index++) {
            if (!(Character.isDigit(busIDArray[index]))) { 
                isValidFormat = false; 
                throw new IllegalArgumentException("[B1 C2 FAILED] Character at position {" + index + "} is not a digit, got " + busIDArray[index]);
            }
        }

        if (isValidLength && isValidFormat) {
            return true;
        } else if (!isValidLength) {
            throw new IllegalArgumentException("[B1 C1 FAILED]"); 
        } else { 
            throw new IllegalArgumentException("[B1 C2 FAILED]"); 
        }   
    }

    /**
     * B2: Capacity update restriction.
     * Capacity cannot increase during update, but can decrease or stay same.
     */
    public static boolean validateCapacityUpdate(Bus existingBus, int newCapacity) {
        int existingCapacity = existingBus.getCapacity();

        if (!(validateCapacity(existingCapacity))) {
             throw new IllegalArgumentException("[B2 FAILED] Bus capacity is not positive, got current bus capacity of " + existingCapacity);
        }

        if (newCapacity > existingCapacity) {
            throw new IllegalArgumentException("[B2 FAILED] Bus capacity cannot increase during update, but can decrease or stay same."
                                                 + " Got current bus capacity of " + existingCapacity + " and new capacity of " + newCapacity);
        } else {
            return true; 
        }
    }

    /**
     * B3: Driver age vs bus capacity.
     * Drivers older than 50 cannot drive buses with capacity >= 50.
     */
    public static boolean validateDriverAgeRestriction(Driver driver, Bus bus) {
        final int AGE_RESTRICTION = 50; 
        final int CAPACITY_RESTRICTION = 50;

        String birthdate = driver.getBirthdate();
        int driverAge = DriverValidator.calculateAge(birthdate);

        int currCapacity = bus.getCapacity();
        if (!(validateCapacity(currCapacity))) {
             throw new IllegalArgumentException("[B2 FAILED] Bus capacity is not positive, got current bus capacity of " + currCapacity);
        }

        if (driverAge > AGE_RESTRICTION) {
            if (currCapacity >= CAPACITY_RESTRICTION) {
                throw new IllegalArgumentException("[B3 FAILED] Bus driver is older than " + AGE_RESTRICTION + " and driving a bus "
                                                    + "with a capacity larger than " + CAPACITY_RESTRICTION + ". Got driver age of "  
                                                    + driverAge + " and bus capacity of " + currCapacity);
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * B4: Experience requirement for electric buses.
     * Only drivers with >= 5 years experience can drive electric buses.
     */
    public static boolean validateElectricBusRestriction(Driver driver, Bus bus) {
        final String RESTRICTED_FUEL_TYPE = "Electricity"; 
        final int RESTRICTED_YEARS_EXPERIENCE = 5;

        String currFuelType = bus.getFuelType();

        if (!(validateFuelType(currFuelType))) {
             throw new IllegalArgumentException("[B2 FAILED] Bus fueltype is not a valid fueltype, must be one of: " + 
                                                    "Diesel, Hybrid, Electricity, got current bus capacity of " + currFuelType);
        }

        int currYearsExperience = driver.getExperienceYears();

        if (currFuelType.equals(RESTRICTED_FUEL_TYPE)) {
            if (currYearsExperience < RESTRICTED_YEARS_EXPERIENCE) {
                throw new IllegalArgumentException("[B4 FAILED] Bus driver has less years of experience than " + RESTRICTED_YEARS_EXPERIENCE
                                                 + " and is attempting to drive a bus of fuel type " + RESTRICTED_FUEL_TYPE 
                                                 + ". Got " + currYearsExperience + " years of experience.");
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    /**
     * B5: License requirement for electric/hybrid.
     * Only Heavy or PublicTransport license holders can drive electric (Electricity) or hybrid (Hybrid) buses.
     */
    public static boolean validateDriverLicenceRestriction(Driver driver, Bus bus) {
        final String ELECTRIC_CLASSIFIER = "Electricity";
        final String HYBRID_CLASSIFIER = "Hybrid";
        final String HEAVY_LISCENCE_CLASSIFIER = "Heavy";
        final String PUBLICTRANSPORT_LISCENCE_CLASSIFIER = "PublicTransport";

        String currLiscence = driver.getLicenseType();
        String currFuelType = bus.getFuelType();
        
        if (!(validateFuelType(currFuelType))) {
             throw new IllegalArgumentException("[B2 FAILED] Bus fueltype is not a valid fueltype, must be one of: " + 
                                                    "Diesel, Hybrid, Electricity, got current bus capacity of " + currFuelType);
        }

        if (!((currLiscence.equals(HEAVY_LISCENCE_CLASSIFIER)) || (currLiscence.equals(PUBLICTRANSPORT_LISCENCE_CLASSIFIER)))) {
            // Fix: Replaced string comparison '==' operator with .equals()
            if (currFuelType.equals(ELECTRIC_CLASSIFIER) || currFuelType.equals(HYBRID_CLASSIFIER)) {
                throw new IllegalArgumentException("[B5 FAILED] Bus driver of type " + currLiscence + " cannot drive a bus of type " + currFuelType);
            } else {
                return true;
            }    
        } else {
            return true;
        }
    }

    /**
     * Helper for fuel type validation.
     * Must be one of: Diesel, Hybrid, Electricity.
     */
    public static boolean validateFuelType(String fuelType) {
        String[] fuelTypes = {"Diesel", "Hybrid", "Electricity"};

        for (String type : fuelTypes) {
            if (fuelType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper for capacity validation.
     * Must be positive (> 0).
     */
    public static boolean validateCapacity(int capacity) {
        return capacity > 0;
    }

    /**
     * Helper for fuel level validation.
     * Must be non-negative (>= 0).
     */
    public static boolean validateFuelLevel(double fuelLevel) {
        return fuelLevel >= 0;
    }
}