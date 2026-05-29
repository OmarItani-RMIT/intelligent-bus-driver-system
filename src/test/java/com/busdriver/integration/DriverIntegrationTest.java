package com.busdriver.integration;

import com.busdriver.Driver;
import com.busdriver.repository.DriverRepository;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Driver-related operations.
 * Uses real TXT files and real implementations of DriverRepository.
 *
 * Required: At least 4 integration test cases verifying:
 *   1. Valid drivers are stored correctly
 *   2. Invalid drivers are rejected
 *   3. Updates are persisted correctly
 *   4. Record counts are updated correctly
 */
@DisplayName("Driver Integration Tests")
public class DriverIntegrationTest {

    private static final String TEST_FILE_PATH = "data/test_drivers.txt";
    private DriverRepository repository;

    @BeforeEach
    void setUp() {
        // TODO: Create data directory if needed
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        // TODO: Initialize repository and clear test data
        repository = new DriverRepository(TEST_FILE_PATH);
        repository.clear();
    }

    @AfterEach
    void tearDown() {
        // TODO: Clean up test file
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    // TODO: IT-D1 - Valid drivers are stored correctly in TXT file
    @Test
    @DisplayName("IT-D1: Valid drivers are stored correctly in TXT file")
    void testValidDriverStoredCorrectly() {
        // 1. Create a valid Driver
        Driver driver = new Driver(
            "23@@5678AB",
            "John Smith",
            5,
            "Light",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );
        // 2. Call repository.add(driver)
        assertTrue(repository.add(driver));
        // 3. Call repository.retrieve(driverID)
        Driver retrieved = repository.retrieve("23@@5678AB");
        // 4. Assert all fields match
        assertNotNull(retrieved);
        assertEquals("23@@5678AB", retrieved.getDriverID());
        assertEquals("John Smith", retrieved.getName());
        assertEquals(5, retrieved.getExperienceYears());
        assertEquals("Light", retrieved.getLicenseType());
        assertEquals("12|King Street|Melbourne|VIC|Australia", retrieved.getAddress());
        assertEquals("01-01-2000", retrieved.getBirthdate());
    }

    // TODO: IT-D2 - Invalid drivers are rejected and not stored
    @Test
    @DisplayName("IT-D2: Invalid drivers are rejected and not stored")
    void testInvalidDriversRejected() {
        // 1. Try adding driver with invalid ID -> assertThrows
        assertThrows(IllegalArgumentException.class, () ->
            new Driver(
                    "1A@5678AB",
                    "John Smith",
                    5,
                    "Light",
                    "12|King Street|Melbourne|VIC|Australia",
                    "01-01-2000"
            )
        );
        // 2. Try adding driver with invalid address -> assertThrows
        assertThrows(IllegalArgumentException.class, () ->
            new Driver(
                    "23@@5678AB",
                    "John Smith",
                    5,
                    "Light",
                    "12|King Street|Melbourne|VIC",
                    "01-01-2000"
            )
        );
        // 3. Try adding driver with invalid birthdate -> assertThrows
        assertThrows(IllegalArgumentException.class, () ->
            new Driver(
                    "24##6789CD",
                    "Alice Brown",
                    3,
                    "Medium",
                    "25|Queen Street|Melbourne|VIC|Australia",
                    "2000/01/01"
            )
        );
        // 4. Assert count == 0
        assertEquals(0, repository.count());
    }

    // TODO: IT-D3 - Driver updates are persisted correctly
    @Test
    @DisplayName("IT-D3: Driver updates are persisted correctly in TXT file")
    void testUpdatesPersistedCorrectly() {
        // 1. Add a valid driver
        Driver originalDriver = new Driver(
            "23@@5678AB",
            "John Smith",
            5,
            "Light",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );
        repository.add(originalDriver);
        // 2. Create updated driver (same ID, different fields)
        Driver updatedDriver = new Driver(
            "23@@5678AB",
            "John Smith",
            6,
            "Medium",
            "99|Lonsdale Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );

        assertTrue(repository.update(updatedDriver));
        // 3. Call repository.update(updatedDriver)
        Driver retrieved = repository.retrieve("23@@5678AB");
        assertNotNull(retrieved);
        assertEquals(6, retrieved.getExperienceYears());
        assertEquals("Medium", retrieved.getLicenseType());
        assertEquals("99|Lonsdale Street|Melbourne|VIC|Australia", retrieved.getAddress());

        // 4. Retrieve and assert updated fields
        Driver experiencedDriver = new Driver(
            "24##6789CD",
            "Alice Brown",
            11,
            "Light",
            "25|Queen Street|Melbourne|VIC|Australia",
            "02-02-2000"
        );
        repository.add(experiencedDriver);

        Driver invalidLicenseChange = new Driver(
            "24##6789CD",
            "Alice Brown",
            11,
            "Heavy",
            "25|Queen Street|Melbourne|VIC|Australia",
            "02-02-2000"
        );

        assertThrows(IllegalArgumentException.class, () ->
            repository.update(invalidLicenseChange)
        );
    }

    // TODO: IT-D4 - Driver count is updated correctly
    @Test
    @DisplayName("IT-D4: Driver count is updated correctly after operations")
    void testCountUpdatedCorrectly() {
        // 1. Assert count == 0
        // 2. Add driver1, assert count == 1
        // 3. Add driver2, assert count == 2
        // 4. Add driver3, assert count == 3
    }
}