package org.wangpai.mathlab.tool;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DigitalStringUtilTest {

    @Test
    void addComma() {
        assertEquals("3", DigitalStringUtil.addComma("3", 4));
        assertEquals("321.123456789",
                DigitalStringUtil.addComma("321.123456789", 4));
        assertEquals("4321.123456789",
                DigitalStringUtil.addComma("4321.123456789", 4));
        assertEquals("8765,4321,8765,4321,8765,4321.123456789",
                DigitalStringUtil.addComma("876543218765432187654321.123456789", 4));
        assertEquals("9,8765,4321,8765,4321,8765,4321.123456789",
                DigitalStringUtil.addComma("9876543218765432187654321.123456789", 4));
    }

    @Test
    void removeComma() {
        String digitStr = "876543218765432187654321.123456789";
        int interval = 5;
        assertEquals(digitStr,
                DigitalStringUtil.removeComma(
                        DigitalStringUtil.addComma(digitStr, interval)));
    }
}