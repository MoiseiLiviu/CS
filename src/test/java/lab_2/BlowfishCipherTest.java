package lab_2;

import lab_1.CipherTest;
import lab_2.block_ciphers.Blowfish;
import org.junit.jupiter.api.Test;

class BlowfishCipherTest {

    @Test
    void blowfishCipherTest(){
        CipherTest.testCipher(new Blowfish("aabb09182736ccdd"), "123456abcd132536");
    }
}
