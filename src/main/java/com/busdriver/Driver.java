package com.busdriver;

/**
 * Driver class represents a bus driver in the Intelligent Bus Driver Guidance System.
 * Each driver has a unique ID, name, experience level, license type, address, and birthdate.
 *
 * Validation rules:
 *   D1: driverID must be exactly 10 chars, first 2 digits 2-9, at least 2 special chars
 *       in positions 3-8, last 2 uppercase letters (A-Z)
 *   D2: Address must follow format: Street Number|Street Name|City|State|Country
 *   D3: Birthdate must follow format: DD-MM-YYYY
 *   D5: driverID and name are immutable after creation 
 */
//TODO: Figure out what constitutes IMMUTABLE

public class Driver {

    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;  // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;


    /**
     * Constructor for the Driver Class
     * @param driverID ID of the driver must be exactly 10 chars, first 2 digits 2-9, at least 2 special chars in positions 3-8, last 2 uppercase letters (A-Z)
     * @param name name of the driver
     * @param experienceYears the ammount of experience the driver has in years
     * @param licenseType the type of liscence they currently hold: Light, Medium, Heavy, PublicTransport
     * @param address current address where the driver lives: must follow format: Street Number|Street Name|City|State|Country
     * @param birthdate the birthdate of the driver in the following format: DD-MM-YYYY
     */
    public Driver(String driverID, String name, int experienceYears,
                  String licenseType, String address, String birthdate) {
        
        if (isValid(driverID, experienceYears, licenseType, address, birthdate)){
            // Assign all parameters to fields after validating parameters
            this.driverID = driverID;
            this.name = name;
            this.experienceYears = experienceYears;
            this.licenseType = licenseType;
            this.address = address;
            this.birthdate = birthdate;
        }
        
    }


    //TODO: Validate Parameters
    /**
     * Helper method for validating the parameters of the constructor
     * @return returns true if all of the rules are met
     */
    private boolean isValid(String driverID, int experienceYears,
                  String licenseType, String address, String birthdate) {
                    
        boolean isValid = false;
        
        isValid = (isDriverIDValid(driverID) && isDriverIDValid(driverID) && isBirthDateValid(birthdate));

        return isValid;
    }

    /**
     * Helper method for validating the driver ID
     * @param driverID ID of the driver must be exactly 10 chars, first 2 digits 2-9, at least 2 special chars in positions 3-8, last 2 uppercase letters (A-Z)
     * @return returns true if the rule is met
     */
    private boolean isDriverIDValid(String driverID){
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
            return false; //early exit clause
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
        else {
            return false; //early exit clause
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
            return false; // early exit clause
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
        else {
            return false; // early exit clause
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
    private boolean isAddressValid(String address){
        // checks the validity of the address by checking that if we use .split using the regex character '|', we get 5
        // segments. Returns true if so.

        // split the string
        String[] addressSplit = address.split("\\|");

        // check number of items in array
        if ((addressSplit.length == 5)){
            return true;
        }
        return false;
    }

    /**
     * helper method for validating the bus drivers birthdate
     * @param birthdate the birthdate of the driver in the following format: DD-MM-YYYY
     * @return returns true if the rule is met
     */
    private boolean isBirthDateValid(String birthdate){
        // checks the validity of the birthdate by checking that if we use .split using the regex character '-', we get 3
        // segments that can be converted to integers.
        boolean isValid = true;


        // split the string
        String[] birthdateSplit = birthdate.split("-");
        

        // check number of items in array
        if ((birthdateSplit.length == 3)){
            //loop over string array birthdateSplit to ensure each section is a number
            for (String num : birthdateSplit){
                if (stringIsPosNumeric(num)) isValid = false;
            }
        }
        //set isValid to false if the number of items is invalid
        else isValid = false;

        return isValid;

    }

    /**
     * Simple helper function for checking that a string is a positive number
     * @param str string to check
     * @return returns true if it finds no non numeric characters, false if it does
     */
    private boolean stringIsPosNumeric(String str){
        //loop over string to find any characters that arent a digit
        for (char ch : str.toCharArray()){
            if (!(Character.isDigit(ch))) return false; //cut early if the char found isnt a digit
        }
        return true;
    }



    // GETTERS:
    public String getDriverID() { return this.driverID; }
    public String getName() { return this.name; }
    public int getExperienceYears() { return this.experienceYears; }
    public String getLicenseType() { return this.licenseType; }
    public String getAddress() { return this.address; }
    public String getBirthdate() { return this.birthdate; }



    //SETTERS: (note: NO setter for driverID and name - they are immutable per D5)
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public void setLicenseType(String licenseType) { this.licenseType = licenseType; }

    public void setAddress(String address) { if(isAddressValid(address)) this.address = address; } //one line if statements are so fancy >u>
    public void setBirthdate(String birthdate) { if(isBirthDateValid(birthdate)) this.birthdate = birthdate; }






    // TODO: Implement toFileString() - converts Driver to pipe-delimited string for TXT storage
    // Format: driverID|name|experienceYears|licenseType|StreetNum|StreetName|City|State|Country|birthdate
    public String toFileString() { return null; }

    // TODO: Implement fromFileString() - creates Driver from pipe-delimited string
    public static Driver fromFileString(String line) { return null; }

    @Override
    public String toString() { return "Driver{}"; }
}