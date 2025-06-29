package org.example.config;

import lombok.RequiredArgsConstructor;
import org.example.entity.AdminEntity;
import org.example.repository.AdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            AdminEntity defaultAdmin = new AdminEntity(
                    "admin", // username
                    "admin@test.com", // email
                    "1234", // password (consider hashing in real app)
                    0.0 // initial balance
            );
            adminRepository.save(defaultAdmin);
            System.out.println("✅ Default admin user created (email: admin@test.com, password: 1234)");
        } else {
            System.out.println("ℹ️ Admin user already exists. Skipping seeding.");
        }
    }
}
