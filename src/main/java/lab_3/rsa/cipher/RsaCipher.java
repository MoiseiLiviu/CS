package lab_3.rsa.cipher;

import lab_3.rsa.key.RsaPrivateKey;
import lab_3.rsa.key.RsaPublicKey;
import lombok.extern.java.Log;

import java.math.BigInteger;


@Log
public class RsaCipher {

    private static RsaCipher instance;

    public static synchronized RsaCipher getInstance(){
        if(instance == null){
            instance = new RsaCipher();
        }
        return instance;
    }

    private RsaCipher() {}

    public byte[] encrypt(String message, RsaPublicKey rsaPublicKey) {
        BigInteger cipherMessage = new BigInteger(message.getBytes());
        return cipherMessage.modPow(rsaPublicKey.e(), rsaPublicKey.n()).toByteArray();
    }

    public String decrypt(byte[] message, RsaPrivateKey rsaPrivateKey) {
        BigInteger decryptedMessage = new BigInteger(message).modPow(rsaPrivateKey.d(), rsaPrivateKey.n());
        return new String(decryptedMessage.toByteArray());
    }
}