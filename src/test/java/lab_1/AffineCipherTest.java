package lab_1;

import lab_1.ciphers.Affine;
import org.junit.jupiter.api.Test;

class AffineCipherTest {

    @Test
    public void testAffineCipher(){
        CipherTest.testCipher(new Affine(17, 13), "Affine Cipher test");
    }
}
