package com.exemple.util;

import com.example.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void testGetYearsSince() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Month.NOVEMBER.getValue(), 21);
        Date date = calendar.getTime();

        Integer yearsSince = DateUtils.getYearsSince(date);

        assertEquals(24, yearsSince);
    }

    @Test
    void testGetYearsSinceWithNull() {
        Integer years = DateUtils.getYearsSince(null);
        assertNull(years);
    }

    @Test
    void testLocalDateToDate() {
        LocalDate localDate = LocalDate.of(2023, 3, 27);
        Date date = DateUtils.localDateToDate(localDate);

        assertNotNull(date, "A data convertida não deve ser nula");
        assertEquals(2023, date.getYear() + 1900, "O ano deve ser 2023"); // getYear retorna o ano desde 1900
        assertEquals(2, date.getMonth(), "O mês deve ser março");
        assertEquals(27, date.getDate(), "O dia deve ser 27");

        date = DateUtils.localDateToDate(null);

        assertNull(date, "A data convertida para null deve ser nula");
    }
}
