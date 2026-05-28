package com.busdriver.integration;

import com.busdriver.Bus;
import com.busdriver.repository.BusRepository;
import org.junit.jupiter.api.*;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Bus-related operations.
 * Uses real TXT files and real implementations of BusRepository.
 *
 * Required: At least 4 integration test cases verifying:
 *   1. Valid buses are stored correctly
 *   2. Invalid buses are rejected
 *   3. Updates are persisted correctly
 *   4. Record counts are updated correctly
 */
@DisplayName("Bus Integration Tests")
public class BusIntegrationTest {

    private static final String TEST_FILE_PATH = "data/test_buses.txt";
    private BusRepository repository;

    @BeforeEach
    void setUp() {
        // TODO: Create data directory if needed
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        // TODO: Initialize repository and clear test data
        repository = new BusRepository(TEST_FILE_PATH);
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

    // TODO: IT-B1 - Valid buses are stored correctly in TXT file
    
    @Test
    @DisplayName("IT-B1: Valid buses are stored correctly in TXT file")
    void testValidBusStoredCorrectly() {
        // 1. Create a valid Bus
        Bus bus = new Bus("12345678", 40, 75.5, "Diesel");
        
        // 2. Call repository.add(bus)
        assertTrue(repository.add(bus));

        // 3. Call repository.retrieve(busID)
        Bus retrieved = repository.retrieve("12345678");
        // 4. Assert all fields match
        assertNotNull(retrieved);
        assertEquals("12345678", retrieved.getBusID());
        assertEquals(40, retrieved.getCapacity());
        assertEquals(75.5, retrieved.getFuelLevel());
        assertEquals("Diesel", retrieved.getFuelType());
    }

    // TODO: IT-B2 - Invalid buses are rejected and not stored
    @Test
    @DisplayName("IT-B2: Invalid buses are rejected and not stored")
    void testInvalidBusesRejected() {
        // 1. Try adding bus with invalid ID (letters) -> assertThrows
        assertThrows(IllegalArgumentException.class, () ->
            new Bus("12AB5678", 40, 60.0, "Diesel")
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Bus("1234567", 40, 60.0, "Diesel")
        );

        assertThrows(IllegalArgumentException.class, () ->
            new Bus("87654321", 0, 60.0, "Diesel")
        );

        assertEquals(0, repository.count());
    
        // 2. Try adding bus with invalid fuel type -> assertThrows
        // 3. 
    }

    // TODO: IT-B3 - Bus updates are persisted correctly
    @Test
    @DisplayName("IT-B3: Bus updates are persisted correctly in TXT file")
    void testUpdatesPersistedCorrectly() {
        // 1. Add a valid bus
        Bus originalBus = new Bus("12345678", 50, 90.0, "Diesel");
        repository.add(originalBus);
        // 2. Create updated bus (same ID, decreased capacity)
        Bus updatedBus = new Bus("12345678", 45, 65.0, "Diesel");
        assertTrue(repository.update(updatedBus));
        // 3. Call repository.update(updatedBus)
        Bus retrieved = repository.retrieve("12345678");
        assertNotNull(retrieved);
        // 4. Retrieve and assert updated fields
        assertEquals("12345678", retrieved.getBusID());
        assertEquals(45, retrieved.getCapacity());
        
        assertEquals(65.0, retrieved.getFuelLevel());
        assertEquals("Diesel", retrieved.getFuelType());
        Bus invalidCapacityIncrease = new Bus("12345678", 60, 65.0, "Diesel");
        assertThrows(IllegalArgumentException.class, () ->
            repository.update(invalidCapacityIncrease)
        );
    
    }

    // TODO: IT-B4 - Bus count is updated correctly
    @Test
    @DisplayName("IT-B4: Bus count is updated correctly after operations")
    void testCountUpdatedCorrectly() {
        // 1. Assert count == 0
        
        // 2. Add bus1, assert count == 1
    
        // 3. Add bus2, assert count == 2
        // 4. Add bus3, assert count == 3
        // 5. Try adding duplicate ID -> assertThrows, count stays 3
    }
}