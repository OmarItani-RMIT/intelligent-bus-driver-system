package com.busdriver;

import com.busdriver.validator.DriverValidator;

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
public class Driver {

    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;  // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    /**
     * Constructor for the Driver Class
     */
    public Driver(String driverID, String name, int experienceYears,
                  String licenseType, String address, String birthdate) {
        
        this.driverID = driverID;
        this.name = name;
        this.experienceYears = experienceYears;
        this.licenseType = licenseType;
        this.address = address;
        this.birthdate = birthdate;
        
        // Omar's Task: Trigger validation upon creation (D1, D2, D3)
        // This makes sure invalid drivers are rejected immediately
        DriverValidator.validateDriver(this);
    }

    // GETTERS
    public String getDriverID() { return this.driverID; }
    public String getName() { return this.name; }
    public int getExperienceYears() { return this.experienceYears; }
    public String getLicenseType() { return this.licenseType; }
    public String getAddress() { return this.address; }
    public String getBirthdate() { return this.birthdate; }

    // SETTERS (Note: No setters for driverID and name to enforce D5 immutability)
    public void setExperienceYears(int experienceYears) { this.experienceYears = experienceYears; }
    public void setLicenseType(String licenseType) { this.licenseType = licenseType; }
    public void setAddress(String address) { this.address = address; } 
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }

    /**
     * Converts Driver to pipe-delimited string for TXT storage
     * Format: driverID|name|experienceYears|licenseType|StreetNum|StreetName|City|State|Country|birthdate
     */
    public String toFileString() { 
        return driverID + "|" + name + "|" + experienceYears + "|" + licenseType + "|" + address + "|" + birthdate;
    }

    /**
     * Parses a pipe-delimited line from a TXT file and reconstructs a Driver object
     */
    public static Driver fromFileString(String line) { 
        // Split based on pipe delimiter
        String[] splitLine = line.split("\\|");

        // Extract each part using corrected indexing (0 to 9)
        String driverID = splitLine[0];
        String name = splitLine[1];
        int experienceYears = Integer.parseInt(splitLine[2]);
        String licenseType = splitLine[3];
        String streetNum = splitLine[4];
        String streetName = splitLine[5];
        String city = splitLine[6];
        String state = splitLine[7];
        String country = splitLine[8]; // Corrected index
        String birthdate = splitLine[9]; // Corrected index

        // Construct the address string without any trailing pipes
        String address = streetNum + "|" + streetName + "|" + city + "|" + state + "|" + country;

        // Return the validated, reconstructed driver
        return new Driver(driverID, name, experienceYears, licenseType, address, birthdate);
    }  

    @Override
    public String toString() { 
        return "Driver{" +
                "driverID='" + driverID + '\'' +
                ", name='" + name + '\'' +
                ", experienceYears=" + experienceYears +
                ", licenseType='" + licenseType + '\'' +
                ", address='" + address + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}'; 
    }
}