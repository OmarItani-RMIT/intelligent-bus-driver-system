package com.busdriver.validator;

import java.time.LocalDate;
import com.busdriver.Driver;
import java.time.Period;

/**
 * Validator class for Driver-related operations.
 * Implements all driver validation conditions (D1 - D5).
 *
 * D1: Driver ID Rules - unique, exactly 10 chars, first 2 digits 2-9,
 *     at least 2 special chars in positions 3-8, last 2 uppercase letters
 * D2: Address Format - Street Number|Street Name|City|State|Country
 * D3: Birthdate Format - DD-MM-YYYY
 * D4: License Update Restriction - >10 years experience = cannot change license
 * D5: Immutable Fields - driverID and name cannot be modified during update
 */
public class DriverValidator {

    /**
     * Helper method for validating the parameters of the constructor
     * @return returns true if all of the rules are met
     */
    public static boolean validateDriver(Driver driver) {
        if(validateDriverID(driver.getDriverID()) && (validateAddress(driver.getAddress())) &&
                           validateBirthdate(driver.getBirthdate())){
            return true;
        }
        return false;
    }

    /**
     * Helper method for validating the driver ID
     */
    public static boolean validateDriverID(String driverID) {
        boolean isValLength = false;
        boolean isFirstDigits = false;
        boolean isSpecialChar = false;
        boolean isLastUppercase = false;

        // --Check C1------------------------------------------------------------------------------------------------------
        if((driverID.length() == 10)){
            isValLength = true;
        }
        else {
           throw new IllegalArgumentException("[D1, C1 FAILED] Driver ID is not exactly 10 characters, got " 
                                                    + Integer.toString(driverID.length()));
        }

        // --Check C2------------------------------------------------------------------------------------------------------
        boolean firstCharVal = false;
        char firstChar = driverID.charAt(0);

        boolean secondCharVal = false;
        char secondChar = driverID.charAt(1);

        char[] validDigits = {'2', '3', '4', '5', '6', '7', '8', '9'};

        for (char digit : validDigits){
            if (firstChar == digit) firstCharVal = true;
            if (secondChar == digit) secondCharVal = true;
        }

        if (firstCharVal && secondCharVal){ 
            isFirstDigits = true; 
        }
        else if (!(firstCharVal)){
            throw new IllegalArgumentException("[D1, C2 FAILED] Driver ID's FIRST character is not a digit 2-9, got " 
                                            + firstChar);
        }
        else if (!(secondCharVal)){
            throw new IllegalArgumentException("[D1, C2 FAILED] Driver ID's SECOND character is not a digit 2-9"
                                            + secondChar);
        }

        // --Check C3------------------------------------------------------------------------------------------------------
        int specialCount = 0;

        for (int index = 2; index < driverID.length(); index++){
            if( !(Character.isDigit(driverID.charAt(index))) && !(Character.isAlphabetic(driverID.charAt(index)))
                 && !(Character.isWhitespace(driverID.charAt(index)))){
                    specialCount++;
            }
        }
        
        if(specialCount >= 2){ 
            isSpecialChar = true;
        }
        else {
            throw new IllegalArgumentException(
                "[D1, C3 FAILED] Driver ID doesn't have 2 or more special characters from the third position to the last position, got " +
                Integer.toString(specialCount) + " character/s."
            );
        }

        // --Check C4------------------------------------------------------------------------------------------------------
        int idLength = driverID.length();

        boolean lastCharVal = false;
        char lastChar = driverID.charAt(idLength - 1);

        boolean secLastCharVal = false;
        char secondLastChar = driverID.charAt(idLength - 2);

        if(Character.isAlphabetic(lastChar)){
            if(Character.isUpperCase(lastChar)) lastCharVal = true;
        }

        if(Character.isAlphabetic(secondLastChar)){
            if(Character.isUpperCase(secondLastChar)) secLastCharVal = true;
        }

        if(lastCharVal && secLastCharVal){ 
            isLastUppercase = true; 
        }
        else if (!(lastCharVal)){
            throw new IllegalArgumentException("[D1, C4 FAILED] LAST character in ID is not an uppercase letter, got " + lastChar);
        }
        else if (!(secLastCharVal)){
            throw new IllegalArgumentException("[D1, C4 FAILED] SECOND LAST character in ID is not an uppercase letter, got" + secondLastChar);
        }

        return (isValLength && isFirstDigits && isSpecialChar && isLastUppercase);
    }

    /**
     * helper method for validating the bus drivers address
     */
    public static boolean validateAddress(String address) {
        String[] addressSplit = address.split("\\|");

        if ((addressSplit.length == 5)){
            for (int i = 0; i < addressSplit.length; ++i){
                if (addressSplit[i].isEmpty()){
                    throw new IllegalArgumentException("[D2 FAILED] address part " + Integer.toString(i) + "is empty.");
                }
            }
            return true; 
        }
        else if ((addressSplit.length > 5)){
            throw new IllegalArgumentException("[D2 FAILED] Address has more than 5 parts, got " + addressSplit.length + " parts.");
        }
        else if ((addressSplit.length < 5)){
            throw new IllegalArgumentException("[D2 FAILED] Address has less than 5 parts, got " + addressSplit.length + " parts.");
        }
        else{
            throw new IllegalArgumentException("[D2 FAILED] Address does not have 5 parts, got " + addressSplit.length + " parts.");
        }
    }

    /**
     * helper method for validating the bus drivers birthdate
     */
    public static boolean validateBirthdate(String birthdate) {
        String[] birthdateSplit = birthdate.split("-");
        
        if ((birthdateSplit.length == 3)){
            // Loop and ensure each section is a number
            for (int i = 0; i < birthdateSplit.length; ++i){
                // Fix: Added '!' to check if the birthday part is NOT numeric
                if (!stringIsPosNumeric(birthdateSplit[i])){
                    throw new IllegalArgumentException("[D3 FAILED] birthday part " + i + " is not a number.");
                }
            }

            int day = Integer.parseInt(birthdateSplit[0]);
            int month = Integer.parseInt(birthdateSplit[1]);

            if (day < 1){
                throw new IllegalArgumentException("[D3 FAILED] day must more than or equal to 1, got " + Integer.toString(day));
            }

            if (month == 2){
                // Validate February
                int year = Integer.parseInt(birthdateSplit[2]);
                boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
                int maxDays = isLeapYear ? 29 : 28;
                if (day > maxDays) {
                    throw new IllegalArgumentException("[D3 FAILED] February only has " + maxDays + " days in " + year + ", got " + day);
                }
            }
            else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)){
                if (day > 30){
                    throw new IllegalArgumentException("[D3 FAILED] month " + Integer.toString(month) + 
                                                            " only has 30 days, got " + Integer.toString(day));
                }
            }
            else {
                if (day > 31){
                    throw new IllegalArgumentException("[D3 FAILED] month " + Integer.toString(month) + 
                                                            " only has 31 days, got " + Integer.toString(day));
                }
            }

            if (month > 12){
                throw new IllegalArgumentException("[D3 FAILED] month must be 12 or less, got " + Integer.toString(month));
            }
            else if (month < 1){
                throw new IllegalArgumentException("[D3 FAILED] month must more than or equal to 1, got " + Integer.toString(month));
            }

            return true;
        }
        else if ((birthdateSplit.length > 3)) {
            throw new IllegalArgumentException("[D3 FAILED] birthday field has too many parts."
                                            + Integer.toString(birthdateSplit.length));
        }
        else {
            throw new IllegalArgumentException("[D3 FAILED] birthday field needs 3 parts, got " 
                                            + Integer.toString(birthdateSplit.length));
        }
    }

    /**
     * Simple helper function for checking that a string is a positive number
     */
    private static boolean stringIsPosNumeric(String str){
        for (char ch : str.toCharArray()){
            if (!(Character.isDigit(ch))) return false; 
        }
        return true;
    }

    /**
     * Helper for license type validation
     */
    public static boolean validateLicenseType(String licenseType) {
        final String[] VALID_TYPES = {"Light", "Medium", "Heavy", "PublicTransport"};

        for (String type : VALID_TYPES) {
            // Fix: Changed string comparison '==' operator to .equals()
            if (licenseType.equals(type)) return true;
        }
        return false;
    }

    public static boolean validateExperienceYears(int experienceYears) {
        return (experienceYears >= 0);
    }

    public static boolean validateName(String name) {
        return ((name != null) && !(name.isEmpty()) );
    }

    /**
     * License Update Restriction (D4)
     */
    public static boolean validateLicenseUpdateRestriction(Driver existingDriver, String newLicenseType) {
        final int RESTRICTED_YEARS_EXPERIENCE = 10;
        int experience = existingDriver.getExperienceYears();

        if (experience >= RESTRICTED_YEARS_EXPERIENCE){
            if (!(existingDriver.getLicenseType().equals(newLicenseType))){
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return true;
        }
    }

    /**
     * Immutable Fields Validation (D5)
     */
    public static boolean validateImmutableFields(Driver existingDriver, Driver updatedDriver) {
        String oldName = existingDriver.getName();
        String newName = updatedDriver.getName();

        if (!(oldName.equals(newName))){
            return false;
        }
        
        String oldID = existingDriver.getDriverID();
        String newID = updatedDriver.getDriverID();

        if (!(oldID.equals(newID))){
            return false;
        }

        return true;
    }

    /**
     * Calculates driver age from birthdate
     */
    public static int calculateAge(String birthdate) {
        if(!(validateBirthdate(birthdate))){
            throw new IllegalArgumentException("Date format is not correct.");
        }

        String[] dateSplit = birthdate.split("-");
        
        int day = Integer.parseInt(dateSplit[0]); 
        int month = Integer.parseInt(dateSplit[1]); 
        int year = Integer.parseInt(dateSplit[2]); 

        LocalDate birthDate =  LocalDate.of(year, month, day);
        LocalDate currDate = LocalDate.now();

        return Period.between(birthDate, currDate).getYears();
    }
}