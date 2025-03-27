package com.exemple.util;

import com.example.util.DateUtils;
import org.junit.jupiter.api.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateUtilsTest {

    @Test
    void testGetYearsSince() {
        //arrange
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Month.NOVEMBER.getValue(), 21);
        Date date = calendar.getTime();

        //act
        Integer yearsSince = DateUtils.getYearsSince(date);

        //assert
        assertEquals(24, yearsSince);
    }

    @Test
    void testGetYearsSinceWithNull() {
        Integer years = DateUtils.getYearsSince(null);
        assertNull(years);
    }
}
