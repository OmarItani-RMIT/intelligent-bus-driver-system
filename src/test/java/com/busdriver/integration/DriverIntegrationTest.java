package com.busdriver.integration;

import com.busdriver.Driver;
import com.busdriver.repository.DriverRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Driver storage operations using flat files.
 * Validates adding, retrieving, updating, and repository record counters.
 */
@DisplayName("Driver Integration Tests")
public class DriverIntegrationTest {

    private static final String TEST_FILE_PATH = "data/test_drivers.txt";
    private DriverRepository repository;

    @BeforeEach
    void setUp() {
        // Setup local workspace directory
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        // Initialize Driver repository and empty past data
        repository = new DriverRepository(TEST_FILE_PATH);
        repository.clear();
    }

    @AfterEach
    void tearDown() {
        // Clean up temporary local workspace test files
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }
    }

    @Test
    @DisplayName("IT-D1: Valid drivers are stored correctly in TXT file")
    void testValidDriverStoredCorrectly() {
        Driver driver = new Driver(
            "23@@5678AB",
            "John Smith",
            5,
            "Light",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );

        assertTrue(repository.add(driver));

        Driver retrieved = repository.retrieve("23@@5678AB");
        assertNotNull(retrieved);
        assertEquals("23@@5678AB", retrieved.getDriverID());
        assertEquals("John Smith", retrieved.getName());
        assertEquals(5, retrieved.getExperienceYears());
        assertEquals("Light", retrieved.getLicenseType());
        assertEquals("12|King Street|Melbourne|VIC|Australia", retrieved.getAddress());
        assertEquals("01-01-2000", retrieved.getBirthdate());
    }

    @Test
    @DisplayName("IT-D2: Invalid drivers are rejected and not stored")
    void testInvalidDriversRejected() {
        // Rejects invalid ID format
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

        // Rejects invalid address field split length
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

        // Rejects invalid date formatting delimiters
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

        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("IT-D3: Driver updates are persisted correctly in TXT file")
    void testUpdatesPersistedCorrectly() {
        Driver originalDriver = new Driver(
            "23@@5678AB",
            "John Smith",
            5,
            "Light",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );
        repository.add(originalDriver);

        Driver updatedDriver = new Driver(
            "23@@5678AB",
            "John Smith",
            6,
            "Medium",
            "99|Lonsdale Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );

        assertTrue(repository.update(updatedDriver));

        Driver retrieved = repository.retrieve("23@@5678AB");
        assertNotNull(retrieved);
        assertEquals("23@@5678AB", retrieved.getDriverID());
        assertEquals("John Smith", retrieved.getName());
        assertEquals(6, retrieved.getExperienceYears());
        assertEquals("Medium", retrieved.getLicenseType());
        assertEquals("99|Lonsdale Street|Melbourne|VIC|Australia", retrieved.getAddress());

        Driver experiencedDriver = new Driver(
            "24##6789CD",
            "Alice Brown",
            11,
            "Light",
            "25|Queen Street|Melbourne|VIC|Australia",
            "02-02-2000"
        );
        repository.add(experiencedDriver);

        // Block license update modifications for drivers with >10 years exp (D4)
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

    @Test
    @DisplayName("IT-D4: Driver count is updated correctly after operations")
    void testCountUpdatedCorrectly() {
        assertEquals(0, repository.count());

        Driver driver1 = new Driver(
            "23@@5678AB",
            "John Smith",
            5,
            "Light",
            "12|King Street|Melbourne|VIC|Australia",
            "01-01-2000"
        );

        Driver driver2 = new Driver(
            "24##6789CD",
            "Alice Brown",
            3,
            "Medium",
            "25|Queen Street|Melbourne|VIC|Australia",
            "02-02-2001"
        );

        Driver driver3 = new Driver(
            "25$$7890EF",
            "Mark Lee",
            7,
            "Heavy",
            "40|Collins Street|Melbourne|VIC|Australia",
            "03-03-1999"
        );

        repository.add(driver1);
        assertEquals(1, repository.count());

        repository.add(driver2);
        assertEquals(2, repository.count());

        repository.add(driver3);
        assertEquals(3, repository.count());
    }
}