package org.example.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class AdminEntityTest {

    @Test
    void testAllArgsConstructorAndGetters() {
        AdminEntity admin = new AdminEntity(1L, "Thisu", "thisu@test.com", "pass123", 5000.00);

        assertThat(admin.getId()).isEqualTo(1L);
        assertThat(admin.getUsername()).isEqualTo("Thisu");
        assertThat(admin.getEmail()).isEqualTo("thisu@test.com");
        assertThat(admin.getPassword()).isEqualTo("pass123");
        assertThat(admin.getBalance()).isEqualTo(5000.00);
    }

    @Test
    void testBuilderPattern() {
        AdminEntity admin = AdminEntity.builder()
                .id(2L)
                .username("AdminUser")
                .email("admin@test.com")
                .password("admin123")
                .balance(10000.0)
                .build();

        assertThat(admin.getId()).isEqualTo(2L);
        assertThat(admin.getUsername()).isEqualTo("AdminUser");
        assertThat(admin.getEmail()).isEqualTo("admin@test.com");
        assertThat(admin.getPassword()).isEqualTo("admin123");
        assertThat(admin.getBalance()).isEqualTo(10000.0);
    }

    @Test
    void testNoArgsConstructorAndSetters() {
        AdminEntity admin = new AdminEntity();
        admin.setId(3L);
        admin.setUsername("SetUser");
        admin.setEmail("setuser@test.com");
        admin.setPassword("set123");
        admin.setBalance(2500.0);

        assertThat(admin.getId()).isEqualTo(3L);
        assertThat(admin.getUsername()).isEqualTo("SetUser");
        assertThat(admin.getEmail()).isEqualTo("setuser@test.com");
        assertThat(admin.getPassword()).isEqualTo("set123");
        assertThat(admin.getBalance()).isEqualTo(2500.0);
    }
}
