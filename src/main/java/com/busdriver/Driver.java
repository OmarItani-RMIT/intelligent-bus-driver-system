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
        boolean isValid = false;

        //TODO: Implement isDriverIDValid logic

        return isValid;
    }

    /**
     * helper method for validating the bus drivers address
     * @param address current address where the driver lives: must follow format: Street Number|Street Name|City|State|Country
     * @return returns true if the rule is met
     */
    private boolean isAddressValid(String address){
        boolean isValid = false;

        //TODO: Implement isAddressValid logic

        return isValid;
    }

    /**
     * helper method for validating the bus drivers birthdate
     * @param birthdate the birthdate of the driver in the following format: DD-MM-YYYY
     * @return returns true if the rule is met
     */
    private boolean isBirthDateValid(String birthdate){
        boolean isValid = false;

        //TODO: Implement isBirthDateValid logic

        return isValid;

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
    public void setAddress(String address) { if(isAddressValid(address)) this.address = address; }
    public void setBirthdate(String birthdate) { if(isBirthDateValid(birthdate)) this.birthdate = birthdate; }

    // TODO: Implement toFileString() - converts Driver to pipe-delimited string for TXT storage
    // Format: driverID|name|experienceYears|licenseType|StreetNum|StreetName|City|State|Country|birthdate
    public String toFileString() { return null; }

    // TODO: Implement fromFileString() - creates Driver from pipe-delimited string
    public static Driver fromFileString(String line) { return null; }

    @Override
    public String toString() { return "Driver{}"; }
}