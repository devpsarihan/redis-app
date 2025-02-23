package com.redis_app.integration;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

@ActiveProfiles({"test"})
@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@EnableCaching
@DirtiesContext
public class TestContainersConfiguration {

    static PostgreSQLContainer postgresContainer;
    static GenericContainer redisContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("PRODUCT")
            .withUsername("root")
            .withPassword("password")
            .waitingFor(Wait.defaultWaitStrategy())
            .withReuse(true);
        postgresContainer.start();
        redisContainer = new GenericContainer<>("redis:latest")
            .withExposedPorts(6379)
            .withReuse(true)
            .withCommand("--requirepass", "password", "--timeout", "60")
            .waitingFor(Wait.defaultWaitStrategy());
        redisContainer.start();
    }

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.data.redis.url",
            () -> String.format("redis://%s:%d", redisContainer.getHost(), redisContainer.getMappedPort(6379)));
        registry.add("spring.data.redis.connect-timeout", () -> "6000");
        registry.add("spring.data.redis.database", () -> "0");
        registry.add("spring.data.redis.password", () -> "password");
    }
}
