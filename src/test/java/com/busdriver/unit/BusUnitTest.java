package com.busdriver.unit;

import com.busdriver.Bus;
import com.busdriver.Driver;
import com.busdriver.validator.BusValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests validating Bus validation constraints (B1 - B5).
 * Tests valid formatting, limits, edge values, and business rules.
 */
@DisplayName("Bus Unit Tests")
public class BusUnitTest {

    /**
     * Helper to create a valid validated Driver instance for assignments
     */
    private Driver createDriver(String licenseType, int experienceYears, String birthdate) {
        return new Driver("34!@ab$$AB", "Test Driver", experienceYears,
                licenseType, "123|Main St|Melbourne|Victoria|Australia", birthdate);
    }

    @Nested
    @DisplayName("B1: Bus ID Rules")
    class BusIDTests {

        @Test
        @DisplayName("B1.1 - Valid bus ID with exactly 8 digits should pass")
        void testValidBusID() {
            assertTrue(BusValidator.validateBusID("12345678"));
        }

        @Test
        @DisplayName("B1.2 - Bus ID with incorrect length should be rejected")
        void testBusIDIncorrectLength() {
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("123456"));
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("1234567890"));
        }

        @Test
        @DisplayName("B1.3 - Bus ID with non-digit characters should be rejected")
        void testBusIDNonDigitCharacters() {
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("12AB5678"));
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateBusID("12@45678"));
            assertThrows(NullPointerException.class, () -> BusValidator.validateBusID(null));
        }
    }

    @Nested
    @DisplayName("B2: Capacity Update Restriction")
    class CapacityUpdateTests {

        @Test
        @DisplayName("B2.1 - Decreasing bus capacity during update should be allowed")
        void testDecreaseCapacityAllowed() {
            Bus existingBus = new Bus("12345678", 50, 80.0, "Diesel");
            assertTrue(BusValidator.validateCapacityUpdate(existingBus, 45));
        }

        @Test
        @DisplayName("B2.2 - Increasing bus capacity during update should be rejected")
        void testIncreaseCapacityRejected() {
            Bus existingBus = new Bus("12345678", 40, 80.0, "Diesel");
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateCapacityUpdate(existingBus, 45));
        }

        @Test
        @DisplayName("B2.3 - Same capacity during update should be allowed")
        void testSameCapacityAllowed() {
            Bus existingBus = new Bus("12345678", 50, 80.0, "Diesel");
            assertTrue(BusValidator.validateCapacityUpdate(existingBus, 50));
        }
    }

    @Nested
    @DisplayName("B3: Driver Age Restriction")
    class DriverAgeRestrictionTests {

        @Test
        @DisplayName("B3.1 - Driver aged 50 or younger can drive large bus")
        void testYoungDriverCanDriveLargeBus() {
            Driver driver = createDriver("Heavy", 10, "15-06-1990"); // ~36 years old
            Bus bus = new Bus("12345678", 60, 100.0, "Diesel");
            assertTrue(BusValidator.validateDriverAgeRestriction(driver, bus));
        }

        @Test
        @DisplayName("B3.2 - Driver older than 50 cannot drive bus with capacity >= 50")
        void testOlderDriverCannotDriveLargeBus() {
            Driver driver = createDriver("Heavy", 10, "15-06-1970"); // ~56 years old
            Bus bus = new Bus("12345678", 50, 100.0, "Diesel");
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateDriverAgeRestriction(driver, bus));
        }

        @Test
        @DisplayName("B3.3 - Driver older than 50 can drive small bus (capacity < 50)")
        void testOlderDriverCanDriveSmallBus() {
            Driver driver = createDriver("Heavy", 10, "15-06-1970"); // ~56 years old
            Bus bus = new Bus("12345678", 30, 100.0, "Diesel");
            assertTrue(BusValidator.validateDriverAgeRestriction(driver, bus));
        }
    }

    @Nested
    @DisplayName("B4: Electric Bus Restriction")
    class ElectricBusRestrictionTests {

        @Test
        @DisplayName("B4.1 - Driver with 5+ years experience can drive electric bus")
        void testExperiencedDriverCanDriveElectricBus() {
            Driver driver = createDriver("Heavy", 7, "15-06-1990");
            Bus bus = new Bus("12345678", 40, 100.0, "Electricity");
            assertTrue(BusValidator.validateElectricBusRestriction(driver, bus));
        }

        @Test
        @DisplayName("B4.2 - Driver with <5 years experience cannot drive electric bus")
        void testInexperiencedDriverCannotDriveElectricBus() {
            Driver driver = createDriver("Heavy", 3, "15-06-1990");
            Bus bus = new Bus("12345678", 40, 100.0, "Electricity");
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateElectricBusRestriction(driver, bus));
        }

        @Test
        @DisplayName("B4.3 - Less experienced driver can drive non-electric buses")
        void testInexperiencedDriverCanDriveNonElectricBus() {
            Driver driver = createDriver("Heavy", 2, "15-06-1990");
            Bus bus = new Bus("12345678", 40, 100.0, "Diesel");
            assertTrue(BusValidator.validateElectricBusRestriction(driver, bus));
        }
    }

    @Nested
    @DisplayName("B5: Driver Licence Restriction")
    class DriverLicenceRestrictionTests {

        @Test
        @DisplayName("B5.1 - Heavy/PublicTransport license holder can drive electric/hybrid buses")
        void testValidLicenseForElectricHybridBus() {
            Driver driver = createDriver("Heavy", 10, "15-06-1990");
            Bus bus = new Bus("12345678", 40, 100.0, "Electricity");
            assertTrue(BusValidator.validateDriverLicenceRestriction(driver, bus));
        }

        @Test
        @DisplayName("B5.2 - Light/Medium license holder cannot drive electric/hybrid buses")
        void testInvalidLicenseForElectricHybridBus() {
            Driver driver = createDriver("Light", 10, "15-06-1990");
            Bus bus = new Bus("12345678", 40, 100.0, "Electricity");
            assertThrows(IllegalArgumentException.class, () -> BusValidator.validateDriverLicenceRestriction(driver, bus));
        }

        @Test
        @DisplayName("B5.3 - Any license type can drive Diesel buses")
        void testAnyLicenseForDieselBus() {
            Driver driver = createDriver("Light", 2, "15-06-1990");
            Bus bus = new Bus("12345678", 40, 100.0, "Diesel");
            assertTrue(BusValidator.validateDriverLicenceRestriction(driver, bus));
        }
    }
}