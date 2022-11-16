package lab_1;

import lab_1.ciphers.Vigenere;
import org.junit.jupiter.api.Test;

class VigenereCipherTest {

    @Test
    void vigenereCipherTest(){
        CipherTest.testCipher(new Vigenere("key"), "Vigenere Cipher test");
    }
}
