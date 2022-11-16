package lab_1;

import lab_1.ciphers.Xor;
import org.junit.jupiter.api.Test;

class XorCipherTest {

    @Test
    void xorCipherTest(){
        CipherTest.testCipher(new Xor('d'), "Xor Cipher test");
    }
}
