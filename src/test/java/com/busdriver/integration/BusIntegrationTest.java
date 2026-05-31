package com.busdriver.integration;

import com.busdriver.Bus;
import com.busdriver.repository.BusRepository;
import org.junit.jupiter.api.*;

import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Bus-related storage and database operations.
 * Validates adding, retrieving, updating, and counting buses using real text files.
 */
@DisplayName("Bus Integration Tests")
public class BusIntegrationTest {

    private static final String TEST_FILE_PATH = "data/test_buses.txt";
    private BusRepository repository;

    @BeforeEach
    void setUp() {
        // Prepare the local data directory structure if it does not exist
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        // Instantiate repository and reset old data before each test execution
        repository = new BusRepository(TEST_FILE_PATH);
        repository.clear();
    }

    @AfterEach
    void tearDown() {
        // Clean up the temporary database file after each test
        File testFile = new File(TEST_FILE_PATH);
        if (testFile.exists()) {
            testFile.delete();
        }   
    }

    @Test
    @DisplayName("IT-B1: Valid buses are stored correctly in TXT file")
    void testValidBusStoredCorrectly() {
        Bus bus = new Bus("12345678", 40, 75.5, "Diesel");
        
        assertTrue(repository.add(bus));

        Bus retrieved = repository.retrieve("12345678");
        assertNotNull(retrieved);
        assertEquals("12345678", retrieved.getBusID());
        assertEquals(40, retrieved.getCapacity());
        assertEquals(75.5, retrieved.getFuelLevel());
        assertEquals("Diesel", retrieved.getFuelType());
    }

    @Test
    @DisplayName("IT-B2: Invalid buses are rejected and not stored")
    void testInvalidBusesRejected() {
        // Rejects invalid formatted ID with alphabets
        assertThrows(IllegalArgumentException.class, () ->
            new Bus("12AB5678", 40, 60.0, "Diesel")
        );

        // Rejects ID with insufficient length
        assertThrows(IllegalArgumentException.class, () ->
            new Bus("1234567", 40, 60.0, "Diesel")
        );

        // Rejects invalid capacity (0 or negative)
        assertThrows(IllegalArgumentException.class, () ->
            new Bus("87654321", 0, 60.0, "Diesel")
        );

        // Ensure no corrupt objects were recorded in storage
        assertEquals(0, repository.count());
    }

    @Test
    @DisplayName("IT-B3: Bus updates are persisted correctly in TXT file")
    void testUpdatesPersistedCorrectly() {
        Bus originalBus = new Bus("12345678", 50, 90.0, "Diesel");
        repository.add(originalBus);

        // Update the bus with lower capacity (Allowed)
        Bus updatedBus = new Bus("12345678", 45, 65.0, "Diesel");
        assertTrue(repository.update(updatedBus));

        Bus retrieved = repository.retrieve("12345678");
        assertNotNull(retrieved);
        assertEquals("12345678", retrieved.getBusID());
        assertEquals(45, retrieved.getCapacity());
        assertEquals(65.0, retrieved.getFuelLevel());
        assertEquals("Diesel", retrieved.getFuelType());

        // Attempting to increase capacity (Blocked by Rule B2)
        Bus invalidCapacityIncrease = new Bus("12345678", 60, 65.0, "Diesel");
        assertThrows(IllegalArgumentException.class, () ->
            repository.update(invalidCapacityIncrease)
        );
    }

    @Test
    @DisplayName("IT-B4: Bus count is updated correctly after operations")
    void testCountUpdatedCorrectly() {
        assertEquals(0, repository.count());

        Bus bus1 = new Bus("11111111", 30, 50.0, "Diesel");
        Bus bus2 = new Bus("22222222", 40, 60.0, "Hybrid");
        Bus bus3 = new Bus("33333333", 45, 70.0, "Electricity");

        repository.add(bus1);
        assertEquals(1, repository.count());

        repository.add(bus2);
        assertEquals(2, repository.count());

        repository.add(bus3);
        assertEquals(3, repository.count());
        
        // Prevent duplicate bus additions and preserve original count
        Bus duplicateBus = new Bus("11111111", 30, 50.0, "Diesel");
        assertThrows(IllegalArgumentException.class, () -> repository.add(duplicateBus));
        assertEquals(3, repository.count());
    }
}