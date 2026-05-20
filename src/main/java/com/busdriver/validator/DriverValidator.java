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

    // TODO: Implement validateDriver() - validates entire Driver object against D1-D3
    public static boolean validateDriver(Driver driver) {
        // TODO: Call all individual validation methods
        return true;
    }

    // TODO: Implement validateDriverID() - D1: Driver ID format validation
    public static boolean validateDriverID(String driverID) {
        // D1 Rules:
        // - Must be exactly 10 characters long
        // - First two characters must be digits between 2 and 9
        // - Characters 3 to 8 must contain at least two special characters
        // - Last two characters must be uppercase letters (A-Z)
        // Throw IllegalArgumentException with descriptive message if invalid
        return true;
    }

    // TODO: Implement validateAddress() - D2: Address format validation
    public static boolean validateAddress(String address) {
        // D2 Rules:
        // - Must follow format: Street Number|Street Name|City|State|Country
        // - Must have exactly 5 parts separated by |
        // - Each part must be non-empty
        return true;
    }

    // TODO: Implement validateBirthdate() - D3: Birthdate format validation
    public static boolean validateBirthdate(String birthdate) {
        // D3 Rules:
        // - Must follow format: DD-MM-YYYY
        // - Must be a valid calendar date (e.g., 31-02-2000 is invalid)
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