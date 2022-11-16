package lab_1;

import lab_1.ciphers.Caesar;
import org.junit.jupiter.api.Test;

class CaesarCipherTest {

    @Test
    void testCaesarCipher(){
        CipherTest.testCipher(new Caesar(20), "Caesar Cipher test");
    }
}
