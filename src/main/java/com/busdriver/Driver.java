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
public class Driver {

    private String driverID;
    private String name;
    private int experienceYears;
    private String licenseType;  // Light, Medium, Heavy, PublicTransport
    private String address;
    private String birthdate;

    // TODO: Implement constructor with all fields
    public Driver(String driverID, String name, int experienceYears,
                  String licenseType, String address, String birthdate) {
        // TODO: Assign all parameters to fields
    }

    // TODO: Implement all getters
    public String getDriverID() { return null; }
    public String getName() { return null; }
    public int getExperienceYears() { return 0; }
    public String getLicenseType() { return null; }
    public String getAddress() { return null; }
    public String getBirthdate() { return null; }

    // TODO: Implement setters (note: NO setter for driverID and name - they are immutable per D5)
    public void setExperienceYears(int experienceYears) { /* TODO */ }
    public void setLicenseType(String licenseType) { /* TODO */ }
    public void setAddress(String address) { /* TODO */ }
    public void setBirthdate(String birthdate) { /* TODO */ }

    // TODO: Implement toFileString() - converts Driver to pipe-delimited string for TXT storage
    // Format: driverID|name|experienceYears|licenseType|StreetNum|StreetName|City|State|Country|birthdate
    public String toFileString() { return null; }

    // TODO: Implement fromFileString() - creates Driver from pipe-delimited string
    public static Driver fromFileString(String line) { return null; }

    @Override
    public String toString() { return "Driver{}"; }
}