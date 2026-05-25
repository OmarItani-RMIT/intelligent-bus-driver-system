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
        
            // Assign all parameters to fields after validating parameters
            this.driverID = driverID;
            this.name = name;
            this.experienceYears = experienceYears;
            this.licenseType = licenseType;
            this.address = address;
            this.birthdate = birthdate;
        

        
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

    public void setAddress(String address) { this.address = address; } //one line if statements are so fancy >u>
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }






    // TODO: Implement toFileString() - converts Driver to pipe-delimited string for TXT storage
    // Format: driverID|name|experienceYears|licenseType|StreetNum|StreetName|City|State|Country|birthdate
    public String toFileString() { 
        String returnString = "";

        returnString = driverID + "|" + name + "|" + experienceYears + "|" + licenseType + "|" + address + "|" + birthdate;

        return returnString;
     }

    
    public static Driver fromFileString(String line) { 
        //return variable
        Driver returnDriver = null;

        //Split based on regex |
        String[] splitLine = line.split("\\|");

        //Store each part in its own variable
        String driverID = splitLine[0], name = splitLine[1],
                liscenceType = splitLine[3], StreetNum = splitLine[4], StreetName = splitLine[5],
                City = splitLine[6], State = splitLine[7], Country = splitLine[9], birthdate = splitLine[10];
        int experienceYears = Integer.parseInt(splitLine[2]);

        // construct the address part
        String address = StreetNum + "|" + StreetName + "|" + City + "|" + State + "|" + Country + "|";

        // create a new driver
        returnDriver = new Driver(driverID, name, experienceYears, liscenceType, address, birthdate);

        // return the driver
        return returnDriver;
     }  

    @Override
    public String toString() { return "Driver{}"; }
}