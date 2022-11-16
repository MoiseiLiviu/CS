package lab_2;

import lab_2.stream_ciphers.RC4;
import lab_2.stream_ciphers.StreamCipher;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class Rc4CipherTest {

    @Test
    void rc4CipherTest() {
        StreamCipher streamCipher = new RC4("Secret");
        String message = "Rc4 cipher test";
        log.info("Initial message : " + message);
        byte[] encrypted = streamCipher.encrypt(message);
        log.info("Encrypted message : " + encrypted);

        assert message.equals(streamCipher.decrypt(encrypted));
    }
}
