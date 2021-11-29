package io.github.olivenbarcelon.umjavaws.commons.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringUtilityTest {
    
    // Check input if null or empty
    @Test
    public void isValid() {
        Assertions.assertTrue(StringUtility.isValid("input", "input"));
        Assertions.assertFalse(StringUtility.isValid(null, ""));
    }
}
