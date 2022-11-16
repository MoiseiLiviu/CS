package lab_1;

import common.Cipher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CipherTest {

    public static void testCipher(Cipher cipher, String message){
        log.info("Initiating test for cipher : "+cipher.getClass().getSimpleName());
        log.info("Initial message : "+message);
        String encrypted = cipher.encrypt(message);
        log.info("Encrypted message : "+encrypted);

        assert message.equals(cipher.decrypt(encrypted));
    }
}
