package com.example.internal;

import com.example.person.PersonNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

class ControllerExceptionHandlerTest {

    @RestController
    @RequestMapping("/example")
    static class ExampleController {

        @GetMapping("/{uuid}")
        public void justThrowException(@PathVariable UUID uuid) {
            throw new PersonNotFoundException(uuid);
        }
    }

    private final WebTestClient webTestClient =
            WebTestClient.bindToController(new ExampleController())
                    .controllerAdvice(new ControllerExceptionHandler())
                    .build();

    @Test
    void handlingPersonNotFoundException() {
        final var personId = UUID.randomUUID();

        webTestClient.get()
                .uri("/example/{uuid}", personId)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND)
                .expectBody()
                .jsonPath("$.type").exists()
                .jsonPath("$.title").exists()
                .jsonPath("$.status").exists()
                .jsonPath("$.detail").exists()
                .jsonPath("$.instance").exists();
    }
}