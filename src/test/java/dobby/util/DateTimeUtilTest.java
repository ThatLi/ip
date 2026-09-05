package dobby.util;

import java.time.LocalDateTime;

import dobby.exception.DobbyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Tests date and time parsing used by deadline and event tasks. */
class DateTimeUtilTest {
    @Test
    void parse_isoDate_returnsStartOfDayWithoutTime() throws DobbyException {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.parse("2019-12-03");

        assertEquals(LocalDateTime.of(2019, 12, 3, 0, 0), parsed.getValue());
        assertFalse(parsed.hasTime());
    }

    @Test
    void parse_slashDate_returnsStartOfDayWithoutTime() throws DobbyException {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.parse("3/12/2019");

        assertEquals(LocalDateTime.of(2019, 12, 3, 0, 0), parsed.getValue());
        assertFalse(parsed.hasTime());
    }

    @Test
    void parse_compactTime_returnsDateAndTime() throws DobbyException {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.parse("2019-12-03 0900");

        assertEquals(LocalDateTime.of(2019, 12, 3, 9, 0), parsed.getValue());
        assertTrue(parsed.hasTime());
    }

    @Test
    void parse_colonSeparatedTime_returnsDateAndTime() throws DobbyException {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.parse("3/12/2019 09:00");

        assertEquals(LocalDateTime.of(2019, 12, 3, 9, 0), parsed.getValue());
        assertTrue(parsed.hasTime());
    }

    @Test
    void parse_surroundingWhitespace_ignoresWhitespace() throws DobbyException {
        DateTimeUtil.ParsedDateTime parsed = DateTimeUtil.parse("  2019-12-03   0900  ");

        assertEquals(LocalDateTime.of(2019, 12, 3, 9, 0), parsed.getValue());
        assertTrue(parsed.hasTime());
    }

    @Test
    void parse_invalidDate_exceptionThrown() {
        assertThrows(DobbyException.class, () -> DateTimeUtil.parse("2019-02-29"));
    }

    @Test
    void parse_invalidTime_exceptionThrown() {
        assertThrows(DobbyException.class, () -> DateTimeUtil.parse("2019-12-03 2400"));
    }

    @Test
    void parse_missingDate_exceptionThrown() {
        assertThrows(DobbyException.class, () -> DateTimeUtil.parse(""));
    }

    @Test
    void parse_extraDateTimePart_exceptionThrown() {
        assertThrows(DobbyException.class, () -> DateTimeUtil.parse("2019-12-03 0900 extra"));
    }
}
