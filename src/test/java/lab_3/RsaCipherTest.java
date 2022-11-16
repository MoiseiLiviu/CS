package lab_3;

import lab_3.rsa.cipher.RsaCipher;
import lab_3.rsa.key.RsaKeyPair;
import lab_3.rsa.key.RsaKeyPairGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
class RsaCipherTest {

    @Test
    void rsaCipherTest(){
        RsaKeyPair keyPair = RsaKeyPairGenerator.getInstance().generateKeyPair(1024);
        String message = "Rsa cipher test";
        log.info("Message : "+message);
        byte[] encoded = RsaCipher.getInstance().encrypt(message, keyPair.publicKey());
        log.info("Encoded : "+ Arrays.toString(encoded));

        assert message.equals(RsaCipher.getInstance().decrypt(encoded, keyPair.privateKey()));
    }
}
