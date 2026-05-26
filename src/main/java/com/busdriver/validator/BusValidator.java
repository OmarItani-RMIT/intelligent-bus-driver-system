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

        

        // [C1] The bus id must be exactly 8 characters long
        boolean isValidLength = false;
        final int VALID_LENGTH = 8; // valid length size is 8

        // [C2] The bus id must consist of only digits
        boolean isValidFormat = true;


        // [C1] VALIDATION
        int idLength = busID.length(); //length of the busID

        if (idLength == VALID_LENGTH) isValidLength = true; // if the idLength matches VALIDLENGTH, it passed

        // otherwise, throw an exception
        else if (idLength < VALID_LENGTH){ 
            isValidLength = false;
            throw new IllegalArgumentException("[B1 C1 FAILED] Length of ID is LESS than the valid length, got " + idLength);
        }   
        else if (idLength > VALID_LENGTH){
            isValidLength = false;
            throw new IllegalArgumentException("[B1 C1 FAILED] Length of ID is MORE than the valid length, got " + idLength);
        }

        // [C2] VALIDATION
        char[] busIDArray = busID.toCharArray(); // Bus ID converted to an array of characters, for easy looping
        int busArrayLength = busIDArray.length;

        // For each index from 0 to the length of the busIDArray (array of characters), check if the character at that
        // index is not a digit, and if it isnt set the isValidFormat bool to false and throw and exception. 
        for (int index = 0; index < busArrayLength; index++){
            if (!(Character.isDigit(busIDArray[index]))) { 
                isValidFormat = false; 
                throw new IllegalArgumentException("[B1 C2 FAILED] Character at position {" + index + "} is not a digit, got " + busIDArray[index]);
            }
        }


        if ((isValidLength) && (isValidFormat)){
            return true;
        }
        else if (!(isValidLength)){
            throw new IllegalArgumentException("[B1 C1 FAILED]"); // Should be unreachable but still throws descriptive exception 
        }
        else { //stops error must return a boolean (also implicitly only fires if (!(isValidFormat)) )
            throw new IllegalArgumentException("[B1 C2 FAILED]"); // Should be unreachable but still throws descriptive exception 
        }   

    }

    // TODO: Implement validateCapacityUpdate() - B2: Capacity update restriction
    public static boolean validateCapacityUpdate(Bus existingBus, int newCapacity) {
        // B2: Capacity cannot increase during update, but can decrease or stay same

        int existingCapacity = existingBus.getCapacity(); //the existing bus's capacity

        // if the new capacity is more than the existing capacity, throw an exceptiom.
        if (newCapacity > existingCapacity){
            throw new IllegalArgumentException("[B2 FAILED] Bus capacity cannot increase during update, but can decrease or stay same."
                                                 + " Got current bus capacity of " + existingCapacity + " and new capacity of " + newCapacity);

            //TODO: might need to use this instead if i've miss understood this:
            //return false;
        }
        else {
            return true; // returns true if exception not thrown
        }
    }

    // TODO: Implement validateDriverAgeRestriction() - B3: Driver age vs bus capacity
    public static boolean validateDriverAgeRestriction(Driver driver, Bus bus) {
        // B3: Drivers older than 50 cannot drive buses with capacity >= 50
        // Use DriverValidator.calculateAge() to get driver's age

        // Constant Variables
        final int AGE_RESTRICTION = 50; 
        final int CAPACITY_RESTRICTION = 50;

        // Driver Variables
        String birthdate = driver.getBirthdate(); // Birthdate of the driver
        int driverAge = DriverValidator.calculateAge(birthdate); // Current age of the driver

        // Bus Variables
        int currCapacity = bus.getCapacity(); // the bus's current capacity

        // if the drivers age is more than the restriction, check the bus's capacity
        if (driverAge > AGE_RESTRICTION){
            // if the bus's capacity is more than the restriction, the bus driver cannot drive it
            if (currCapacity >= CAPACITY_RESTRICTION){
                throw new IllegalArgumentException("[B3 FAILED] Bus driver is older than " + AGE_RESTRICTION + " and driving a bus "
                                                    + "with a capacity larger than " + CAPACITY_RESTRICTION + ". Got driver age of "  
                                                    + driverAge + " and bus capacity of " + currCapacity);

                //TODO: might need to use this instead if i've miss understood this:
                //return false;
            }
            // otherwise the driver is allowed to drive the bus 
            else {
                return true;
            }
        }
        // otherwise there are no restrictions on this bus driver, and they are allowed to drive the bus
        else {
            return true;
        }
    }

    // TODO: Implement validateElectricBusRestriction() - B4: Experience requirement for electric
    public static boolean validateElectricBusRestriction(Driver driver, Bus bus) {
        // B4: Only drivers with >= 5 years experience can drive electric buses
        // This only applies when fuelType = "Electricity"

        // Constant variables
        final String RESTRICTED_FUEL_TYPE = "Electricity"; 
        final int RESTRICTED_YEARS_EXPERIENCE = 5;

        // Bus variables
        String currFuelType = bus.getFuelType(); // the current bus's fuel type

        // Driver variables
        int currYearsExperience = driver.getExperienceYears(); // the current drivers experience in number of years

        // if the fuel type of the bus is restricted, check the drivers years of experience
        if (currFuelType.equals(RESTRICTED_FUEL_TYPE)){
            // if the drivers years of experience is less than the mandated ammount, throw exception
            if (currYearsExperience < RESTRICTED_YEARS_EXPERIENCE){
                throw new IllegalArgumentException("[B4 FAILED] Bus driver has less years of experience than " + RESTRICTED_YEARS_EXPERIENCE
                                                 + " and is attempting to drive a bus of fuel type " + RESTRICTED_FUEL_TYPE 
                                                 + ". Got " + currYearsExperience + " years of experience.");

                //TODO: might need to use this instead if i've miss understood this:
                //return false;
            }
            // otherwise the driver has experience more than or equal to the mandated ammount, and can drive the bus.
            else {
                return true;
            }
        }   
        // otherwise, the driver can drive the bus.
        else{
            return true;
        }
        
    }

    // TODO: Implement validateDriverLicenceRestriction() - B5: License requirement for electric/hybrid
    public static boolean validateDriverLicenceRestriction(Driver driver, Bus bus) {
        // B5: Only Heavy or PublicTransport license holders can drive
        //     electric (Electricity) or hybrid (Hybrid) buses

        // Constant bus variables
        final String ELECTRIC_CLASSIFIER = "Electricity";
        final String HYBRID_CLASSIFIER = "Hybrid";

        // Constant driver variables
        final String HEAVY_LISCENCE_CLASSIFIER = "Heavy";
        final String PUBLICTRANSPORT_LISCENCE_CLASSIFIER = "PublicTransport";


        // Driver variables
        String currLiscence = driver.getLicenseType();

        // Bus Variables
        String currFuelType = bus.getFuelType();


        // if driver doesnt have a heavy liscence or public transport liscence, check the bus's fuel type
        if (!((currLiscence.equals(HEAVY_LISCENCE_CLASSIFIER)) || (currLiscence.equals(PUBLICTRANSPORT_LISCENCE_CLASSIFIER)))){
            // if the bus's fuel type matches either of the classifiers, they cannot drive the bus
            if ((currFuelType == ELECTRIC_CLASSIFIER) || (currFuelType == HYBRID_CLASSIFIER)){
                throw new IllegalArgumentException("[B5 FAILED] Bus driver of type " + currLiscence + " cannot drive a bus of type " + currFuelType);
                
                //TODO: might need to use this instead if i've miss understood this:
                //return false;
            }
            // otherwise, they can drive the bus
            else{
                return true;
            }    
        }
        // otherwise, the bus driver doesnt have any restrictions
        else {
            return true;
        }


        
    }

    // TODO: Implement validateFuelType() - helper for fuel type validation
    public static boolean validateFuelType(String fuelType) {
        // Must be one of: Diesel, Hybrid, Electricity

        // Array containing all possible fuel types
        String[] fuelTypes = {"Diesel", "Hybrid", "Electricity"};

        // for each possible fuel type, check if the parameter matches it, return true
        for (String type : fuelTypes){
            if (fuelType.equals(type)){
                return true;
            }

        }

        // Getting here means we did not match the parameter to a possible fuel type, so we return false
        return false;
    }

    // TODO: Implement validateCapacity() - helper for capacity validation
    public static boolean validateCapacity(int capacity) {
        // Must be positive (> 0)

        // if the capacity is above 0, return true
        if (capacity > 0){
            return true;
        }
        // otherwise return false.
        else {
            return false;
        }
        
    }

    // TODO: Implement validateFuelLevel() - helper for fuel level validation
    public static boolean validateFuelLevel(double fuelLevel) {
        // Must be non-negative (>= 0)
        
        if (fuelLevel >= 0){
            return true;
        }
        else {
            return false;
        }

    }
}