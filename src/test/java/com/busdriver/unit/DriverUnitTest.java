package com.busdriver.unit;

import com.busdriver.Driver;
import com.busdriver.validator.DriverValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests validating Driver-related business constraints (D1 - D5).
 * Verifies boundaries, types, structural limits, formatting, and edge cases.
 */
@DisplayName("Driver Unit Tests")
public class DriverUnitTest {

    @Nested
    @DisplayName("D1: Driver ID Rules")
    class DriverIDTests {

        @Test
        @DisplayName("D1.1 - Valid driver ID should pass validation")
        void testValidDriverID() {
            assertTrue(DriverValidator.validateDriverID("34!@ab$$AB"));
        }

        @Test
        @DisplayName("D1.2 - Driver ID with incorrect length should be rejected")
        void testDriverIDIncorrectLength() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateDriverID("34!@AB"));
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateDriverID("34!@ab$$AB_EXTRA"));
        }

        @Test
        @DisplayName("D1.3 - Driver ID with first two digits outside 2-9 should be rejected")
        void testDriverIDFirstDigitsOutOfRange() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateDriverID("04!@ab$AB"));
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateDriverID("19!@ab$AB"));
        }

        @Test
        @DisplayName("D1.4 - Driver ID with insufficient special chars should be rejected")
        void testDriverIDSpecialCharsAndUppercase() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateDriverID("34abcdeeAB"));
        }
    }

    @Nested
    @DisplayName("D2: Address Format")
    class AddressFormatTests {

        @Test
        @DisplayName("D2.1 - Valid address format should pass validation")
        void testValidAddress() {
            assertTrue(DriverValidator.validateAddress("123|Main Street|Melbourne|Victoria|Australia"));
        }

        @Test
        @DisplayName("D2.2 - Address with incorrect number of fields should be rejected")
        void testInvalidAddressFieldCount() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateAddress("123|Main Street|Melbourne|Victoria"));
        }

        @Test
        @DisplayName("D2.3 - Address with empty fields should be rejected")
        void testAddressWithEmptyFields() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateAddress("123|Main Street||Victoria|Australia"));
        }
    }

    @Nested
    @DisplayName("D3: Birthdate Format")
    class BirthdateFormatTests {

        @Test
        @DisplayName("D3.1 - Valid birthdate format should pass validation")
        void testValidBirthdate() {
            assertTrue(DriverValidator.validateBirthdate("15-06-1990"));
        }

        @Test
        @DisplayName("D3.2 - Birthdate with incorrect format should be rejected")
        void testInvalidBirthdateFormat() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateBirthdate("1990-06-15"));
        }

        @Test
        @DisplayName("D3.3 - Invalid calendar date should be rejected")
        void testInvalidCalendarDate() {
            assertThrows(IllegalArgumentException.class, () -> DriverValidator.validateBirthdate("31-02-2000"));
        }
    }

    @Nested
    @DisplayName("D4: License Update Restriction")
    class LicenseUpdateRestrictionTests {

        @Test
        @DisplayName("D4.1 - Driver with >10 years experience can keep same license type")
        void testExperiencedDriverKeepSameLicense() {
            Driver existing = new Driver("34!@ab$$AB", "John Smith", 15, "Heavy", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            assertTrue(DriverValidator.validateLicenseUpdateRestriction(existing, "Heavy"));
        }

        @Test
        @DisplayName("D4.2 - Driver with >10 years experience cannot change license type")
        void testExperiencedDriverCannotChangeLicense() {
            Driver existing = new Driver("34!@ab$$AB", "John Smith", 15, "Heavy", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            assertFalse(DriverValidator.validateLicenseUpdateRestriction(existing, "Medium"));
        }

        @Test
        @DisplayName("D4.3 - Driver with <=10 years experience can change license type")
        void testInexperiencedDriverCanChangeLicense() {
            Driver existing = new Driver("34!@ab$$AB", "John Smith", 5, "Light", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            assertTrue(DriverValidator.validateLicenseUpdateRestriction(existing, "Medium"));
        }
    }

    @Nested
    @DisplayName("D5: Immutable Fields")
    class ImmutableFieldsTests {

        @Test
        @DisplayName("D5.1 - Update with unchanged driverID and name should be allowed")
        void testUpdateWithUnchangedImmutableFields() {
            Driver existing = new Driver("34!@ab$$AB", "John Smith", 5, "Light", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            Driver updated = new Driver("34!@ab$$AB", "John Smith", 5, "Light", "456|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            assertTrue(DriverValidator.validateImmutableFields(existing, updated));
        }

        @Test
        @DisplayName("D5.2 - Changing driverID during update should be rejected")
        void testChangingDriverIDRejected() {
            Driver existing = new Driver("34!@ab$$AB", "John Smith", 5, "Light", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            Driver updated = new Driver("56!@cd$$CD", "John Smith", 5, "Light", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            assertFalse(DriverValidator.validateImmutableFields(existing, updated));
        }

        @Test
        @DisplayName("D5.3 - Changing name during update should be rejected")
        void testChangingNameRejected() {
            Driver existing = new Driver("34!@ab$$AB", "John Smith", 5, "Light", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            Driver updated = new Driver("34!@ab$$AB", "Jane Smith", 5, "Light", "123|Main St|Melbourne|Victoria|Australia", "15-06-1990");
            assertFalse(DriverValidator.validateImmutableFields(existing, updated));
        }
    }
}