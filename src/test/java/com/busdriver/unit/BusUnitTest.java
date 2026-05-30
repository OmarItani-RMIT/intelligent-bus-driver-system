package com.busdriver.unit;

import com.busdriver.Bus;
import com.busdriver.Driver;
import com.busdriver.validator.BusValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Bus-related conditions (B1 - B5).
 *
 * Required: At least 15 unit test cases, at least 3 per condition.
 * Must include normal cases, invalid inputs, and edge cases.
 */
@DisplayName("Bus Unit Tests")
public class BusUnitTest {

    // Helper method to create a valid driver for testing
    private Driver createDriver(String licenseType, int experienceYears, String birthdate) {
        return new Driver("34!@ab$AB", "Test Driver", experienceYears,
                licenseType, "123|Main St|Melbourne|Victoria|Australia", birthdate);
    }

    @Nested
    @DisplayName("B1: Bus ID Rules")
    class BusIDTests {
        // TODO: B1.1 - Valid bus ID passes validation (normal case)
        @Test
        @DisplayName("B1.1 - Valid bus ID with exactly 8 digits should pass")
        void testValidBusID() {
            // Valid: "12345678" (8 digits)
            assertTrue(BsValidator.validateBusID("12345678"));
        }

        // TODO: B1.2 - Bus ID with wrong length is rejected (invalid input)
        @Test
        @DisplayName("B1.2 - Bus ID with incorrect length should be rejected")
        void testBusIDIncorrectLength() {
            // Test too short (6 chars) and too long (10 chars)
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("123456"));
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusId("1234567890"));
        }

        // TODO: B1.3 - Bus ID with non-digit chars is rejected (edge case)
        @Test
        @DisplayName("B1.3 - Bus ID with non-digit characters should be rejected")
        void testBusIDNonDigitCharacters() {
            // Test with letters, special chars, and null
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("12AB5678"));
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("12@45678"));
            assertThrows(NullPointerException.class, () > BusValidator.validateBusID(null));
        }
    }

    @Nested
    @DisplayName("B2: Capacity Update Restriction")
    class CapacityUpdateTests {
        // TODO: B2.1 - Decreasing capacity is allowed (normal case)
        @Test
        @DisplayName("B2.1 - Decreasing bus capacity during update should be allowed")
        void testDecreaseCapacityAllowed() {}

        // TODO: B2.2 - Increasing capacity is rejected (invalid input)
        @Test
        @DisplayName("B2.2 - Increasing bus capacity during update should be rejected")
        void testIncreaseCapacityRejected() {}

        // TODO: B2.3 - Same capacity is allowed (edge case)
        @Test
        @DisplayName("B2.3 - Same capacity during update should be allowed")
        void testSameCapacityAllowed() {}
    }

    @Nested
    @DisplayName("B3: Driver Age Restriction")
    class DriverAgeRestrictionTests {
        // TODO: B3.1 - Young driver can drive large bus (normal case)
        @Test
        @DisplayName("B3.1 - Driver aged 50 or younger can drive large bus")
        void testYoungDriverCanDriveLargeBus() {}

        // TODO: B3.2 - Older driver cannot drive bus with capacity >= 50 (invalid input)
        @Test
        @DisplayName("B3.2 - Driver older than 50 cannot drive bus with capacity >= 50")
        void testOlderDriverCannotDriveLargeBus() {}

        // TODO: B3.3 - Older driver CAN drive small bus (edge case)
        @Test
        @DisplayName("B3.3 - Driver older than 50 can drive small bus (capacity < 50)")
        void testOlderDriverCanDriveSmallBus() {}
    }

    @Nested
    @DisplayName("B4: Electric Bus Restriction")
    class ElectricBusRestrictionTests {
        // TODO: B4.1 - Experienced driver can drive electric bus (normal case)
        @Test
        @DisplayName("B4.1 - Driver with 5+ years experience can drive electric bus")
        void testExperiencedDriverCanDriveElectricBus() {}

        // TODO: B4.2 - Inexperienced driver cannot drive electric bus (invalid input)
        @Test
        @DisplayName("B4.2 - Driver with <5 years experience cannot drive electric bus")
        void testInexperiencedDriverCannotDriveElectricBus() {}

        // TODO: B4.3 - Inexperienced driver CAN drive non-electric buses (edge case)
        @Test
        @DisplayName("B4.3 - Less experienced driver can drive non-electric buses")
        void testInexperiencedDriverCanDriveNonElectricBus() {}
    }

    @Nested
    @DisplayName("B5: Driver Licence Restriction")
    class DriverLicenceRestrictionTests {
        // TODO: B5.1 - Heavy/PublicTransport license can drive electric/hybrid (normal case)
        @Test
        @DisplayName("B5.1 - Heavy/PublicTransport license holder can drive electric/hybrid buses")
        void testValidLicenseForElectricHybridBus() {}

        // TODO: B5.2 - Light/Medium license cannot drive electric/hybrid (invalid input)
        @Test
        @DisplayName("B5.2 - Light/Medium license holder cannot drive electric/hybrid buses")
        void testInvalidLicenseForElectricHybridBus() {}

        // TODO: B5.3 - Any license can drive Diesel buses (edge case)
        @Test
        @DisplayName("B5.3 - Any license type can drive Diesel buses")
        void testAnyLicenseForDieselBus() {}
    }
}