package com.busdriver;

import com.busdriver.repository.DriverRepository;
import com.busdriver.repository.BusRepository;
import java.util.List;

/**
 * Main entry point of the Intelligent Bus Driver Guidance System.
 * Loads and prints both the local Driver and Bus databases on execution.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("      INTELLIGENT BUS DRIVER GUIDANCE SYSTEM      ");
        System.out.println("==================================================");

        // 1. Load and display Drivers
        DriverRepository driverRepo = new DriverRepository("data/test_drivers.txt");
        System.out.println("\n--- DRIVER REGISTRY (data/test_drivers.txt) ---");
        List<Driver> drivers = driverRepo.retrieveAll();
        if (drivers.isEmpty()) {
            System.out.println("No drivers found in database.");
        } else {
            for (Driver d : drivers) {
                System.out.println("ID        : " + d.getDriverID());
                System.out.println("Name      : " + d.getName());
                System.out.println("Experience: " + d.getExperienceYears() + " years");
                System.out.println("License   : " + d.getLicenseType());
                System.out.println("Address   : " + d.getAddress());
                System.out.println("Birthdate : " + d.getBirthdate());
                System.out.println("--------------------------------------------------");
            }
        }

        // 2. Load and display Buses
        BusRepository busRepo = new BusRepository("data/test_buses.txt");
        System.out.println("\n--- BUS REGISTRY (data/test_buses.txt) ---");
        List<Bus> buses = busRepo.retrieveAll();
        if (buses.isEmpty()) {
            System.out.println("No buses found in database.");
        } else {
            for (Bus b : buses) {
                System.out.println("Bus ID    : " + b.getBusID());
                System.out.println("Capacity  : " + b.getCapacity() + " passengers");
                System.out.println("Fuel Level: " + b.getFuelLevel() + "%");
                System.out.println("Fuel Type : " + b.getFuelType());
                System.out.println("Driver ID : " + (b.getDriverID().isEmpty() ? "Unassigned" : b.getDriverID()));
                System.out.println("--------------------------------------------------");
            }
        }
    }
}

