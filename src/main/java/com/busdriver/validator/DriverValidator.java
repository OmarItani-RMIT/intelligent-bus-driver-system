package com.busdriver.validator;

import com.busdriver.Driver;

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
     * @param driverID ID of the driver must be exactly 10 chars, first 2 digits 2-9, at least 2 special chars in positions 3-8, last 2 uppercase letters (A-Z)
     * @return returns true if the rule is met
     */
    public static boolean validateDriverID(String driverID) {
        // D1 Rules:
        // - Must be exactly 10 characters long
        // - First two characters must be digits between 2 and 9
        // - Characters 3 to 8 must contain at least two special characters
        // - Last two characters must be uppercase letters (A-Z)
        // Throw IllegalArgumentException with descriptive message if invalid

        //checks the driver id based on the following clauses:
    
        /** [C1] Clause One: Id must be exactly 10 chars */
        boolean isValLength = false;

        /** [C2] Clause Two: first 2 digits must be 2-9 */
        boolean isFirstDigits = false;

        /** [C3] Clause Three: at least 2 special chars in positions 3-8 */
        boolean isSpecialChar = false;

        /** [C4] Clause Four: last 2 characters must be uppercase letters (A-Z) */
        boolean isLastUppercase = false;

        // if any fail, we return false early to save runtime.

        // --Check C1------------------------------------------------------------------------------------------------------
        //ensure the length is exactly 10, if so the clause is passed
        if((driverID.length() == 10)){
            isValLength = true;
        }
        else {
           throw new IllegalArgumentException("[D1, C1 FAILED] Driver ID is not exactly 10 characters, got " 
                                                    + Integer.toString(driverID.length()));
        }

        // --Check C2------------------------------------------------------------------------------------------------------
        
        // variables for the first character
        boolean firstCharVal = false;
        char firstChar = driverID.charAt(0);

        // variables for the second character
        boolean secondCharVal = false;
        char secondChar = driverID.charAt(1);

        // the valid digits 2 through 9
        char[] validDigits = {'2', '3', '4', '5', '6', '7', '8', '9'};

        // for each digit in the valid digits array, check if the first or second character matches them
        for (char digit : validDigits){
            if (firstChar == digit) firstCharVal = true;
            if (secondChar == digit) secondCharVal = true;
        }

        //check to ensure both characters were found to match. If so, the clause is passed
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
        
        //counter variable
        int specialCount = 0;

        //for every character from the third position (index 2) to the end of the id:
        for (int index = 2; index < driverID.length(); index++){

            // check if the character is not an integer, letter, or whitespace (implying it is a special character)
            if( !(Character.isDigit(driverID.charAt(index))) && !(Character.isAlphabetic(driverID.charAt(index)))
                 && !(Character.isWhitespace(driverID.charAt(index)))){

                    //increment counter
                    specialCount++;
            }
        }
        
        //if there are 2 or more special characters found, clause satisfied
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

        // variable for the id's length
        int idLength = driverID.length();

        // variables for the last character
        boolean lastCharVal = false;
        char lastChar = driverID.charAt(idLength - 1);

        // variables for the second last character
        boolean secLastCharVal = false;
        char secondLastChar = driverID.charAt(idLength - 2);

        // check if the last character is alphabetic
        if(Character.isAlphabetic(lastChar)){
            //check if the last character is uppercase
            if(Character.isUpperCase(lastChar)) lastCharVal = true;
        }

        // check if the second to last character is alphabetic
        if(Character.isAlphabetic(secondLastChar)){
            // check if the second to last character is uppercase
            if(Character.isUpperCase(secondLastChar)) secLastCharVal = true;
        }

        // if both pass, the clause is satisfied
        if(lastCharVal && secLastCharVal){ 
            isLastUppercase = true; 
        }
        else if (!(lastCharVal)){
            throw new IllegalArgumentException("[D1, C4 FAILED] LAST character in ID is not an uppercase letter, got " + lastChar);
        }
        else if (!(secLastCharVal)){
            throw new IllegalArgumentException("[D1, C4 FAILED] SECOND LAST character in ID is not an uppercase letter, got" + secondChar);
        }


        // If we get here it is implied that the statement below is always true, but
        // we check it anyways: Return the result of all clauses
        return (isValLength && isFirstDigits && isSpecialChar && isLastUppercase);

    }

     /**
     * helper method for validating the bus drivers address
     * @param address current address where the driver lives: must follow format: Street Number|Street Name|City|State|Country
     * @return returns true if the rule is met
     */
    public static boolean validateAddress(String address) {
        // D2 Rules:
        // - Must follow format: Street Number|Street Name|City|State|Country
        // - Must have exactly 5 parts separated by |
        // - Each part must be non-empty

        // checks the validity of the address by checking that if we use .split using the regex character '|', we get 5
        // segments. Returns true if so.

        // split the string
        String[] addressSplit = address.split("\\|");

        // check number of items in array
        if ((addressSplit.length == 5)){
            // if length is valid, loop through each part to check if it has something
            for (int i = 0; i < addressSplit.length; ++i){
                //if the item is empty, throw an exception
                if (addressSplit[i].isEmpty()){
                    throw new IllegalArgumentException("[D2 FAILED] address part " + Integer.toString(i) + "is empty.");
                }
            }
            // if we did not throw an exception we can return true
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
            //stops "error: must return something"
        }
        
    }


    /**
     * helper method for validating the bus drivers birthdate
     * @param birthdate the birthdate of the driver in the following format: DD-MM-YYYY
     * @return returns true if the rule is met
     */
    public static boolean validateBirthdate(String birthdate) {
        // D3 Rules:
        // - Must follow format: DD-MM-YYYY
        // - Must be a valid calendar date (e.g., 31-02-2000 is invalid)

    
        // checks the validity of the birthdate by checking that if we use .split using the regex character '-', we get 3
        // segments that can be converted to integers and are in a valid format.


        // split the string
        String[] birthdateSplit = birthdate.split("-");
        

        // check number of items in array
        if ((birthdateSplit.length == 3)){
            // loop over string array birthdateSplit to ensure each section is a number
            for (int i = 0; i < birthdateSplit.length; ++i){
                if (stringIsPosNumeric(birthdateSplit[i])){
                    throw new IllegalArgumentException("[D3 FAILED] birthday part " + i + " is not a number.");
                }
            }

            // ensure day is valid:
            int day = Integer.parseInt(birthdateSplit[0]);
            int month = Integer.parseInt(birthdateSplit[1]);

            if (day < 1){
                throw new IllegalArgumentException("[D3 FAILED] day must more than or equal to 1, got " + Integer.toString(day));
            }

            if (month == 2){
                
            }
            // 30 days in September, April, June, and November
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


            //ensure month is valid:

            if (month > 12){
                throw new IllegalArgumentException("[D3 FAILED] month must be 12 or less, got " + Integer.toString(month));
            }
            else if (month < 1){
                throw new IllegalArgumentException("[D3 FAILED] month must more than or equal to 1, got " + Integer.toString(month));
            }


            // we already check that all parts are a positive number so we dont need to validate year.
            return true;
        }
        //set isValid to false if the number of items is invalid
        else if ((birthdateSplit.length > 3)) {
            throw new IllegalArgumentException("[D3 FAILED] birthday field has too many parts."
                                            + Integer.toString(birthdateSplit.length));
        }
        else if (birthdateSplit.length > 3){
            throw new IllegalArgumentException("[D3 FAILED] birthday field has less than 3 parts, got "
                                            + Integer.toString(birthdateSplit.length));
        }
        else {
            throw new IllegalArgumentException("[D3 FAILED] birthday field needs 3 parts, got " 
                                            + Integer.toString(birthdateSplit.length));
        }
    }


    /**
     * Simple helper function for checking that a string is a positive number
     * @param str string to check
     * @return returns true if it finds no non numeric characters, false if it does
     */
    private static boolean stringIsPosNumeric(String str){
        //loop over string to find any characters that arent a digit
        for (char ch : str.toCharArray()){
            if (!(Character.isDigit(ch))) return false; //cut early if the char found isnt a digit
        }
        return true;
    }








    // TODO: Implement validateLicenseType() - helper for license type validation
    public static boolean validateLicenseType(String licenseType) {
        // Must be one of: Light, Medium, Heavy, PublicTransport
        return true;
    }

    // TODO: Implement validateExperienceYears() - helper for experience validation
    public static boolean validateExperienceYears(int experienceYears) {
        // Must be non-negative
        return true;
    }

    // TODO: Implement validateName() - helper for name validation
    public static boolean validateName(String name) {
        // Must not be null or empty
        return true;
    }

    // TODO: Implement validateLicenseUpdateRestriction() - D4
    public static boolean validateLicenseUpdateRestriction(Driver existingDriver, String newLicenseType) {
        // D4: If driver has >10 years experience, licenseType cannot be changed
        return true;
    }

    // TODO: Implement validateImmutableFields() - D5
    public static boolean validateImmutableFields(Driver existingDriver, Driver updatedDriver) {
        // D5: driverID and name cannot be modified during update
        return true;
    }

    // TODO: Implement calculateAge() - helper to calculate driver age from birthdate
    public static int calculateAge(String birthdate) {
        // Parse DD-MM-YYYY and calculate age using LocalDate and Period
        return 0;
    }
}