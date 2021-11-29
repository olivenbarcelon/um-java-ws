package io.github.olivenbarcelon.umjavaws.commons.utils.crypto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CryptoUtilityTest {
    
    // Type 1 UUID Generation
    @Test
    public void generateUuid() {
        Assertions.assertNotNull(CryptoUtility.generateUuid());
    }
    
    // Encrypt input
    @Test
    public void encrypt() {
        Assertions.assertNotNull(CryptoUtility.encrypt("password", "SECRET_KEY"));
    }
}
