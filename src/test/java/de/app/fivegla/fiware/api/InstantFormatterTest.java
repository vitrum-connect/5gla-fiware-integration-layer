package de.app.fivegla.fiware.api;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class InstantFormatterTest {

    @Test
    void givenNullAsValueWhenFormattingTheValueThenReturnNull() {
        assertNull(InstantFormatter.format(null));
    }

    @Test
    void givenInstantAsValueWhenFormattingTheValueThenReturnFormattedValue() {
        var instant = InstantFormatter.format(Instant.now());
        assertNotNull(instant);
        assertTrue(instant.matches("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z"));
    }

}