package lab_4;

import lab_3.rsa.cipher.RsaCipher;
import lab_3.rsa.key.RsaKeyPair;
import lab_3.rsa.key.RsaKeyPairGenerator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class ObjectiveTwo {

    public static void main(String[] args) {
        String message = new Scanner(System.in).nextLine();
        String hashedMsg = BCrypt.hashpw(message, BCrypt.gensalt());
        RsaKeyPair rsaKeyPair = RsaKeyPairGenerator.getInstance().generateKeyPair(512);
        byte[] encryptedPwd = RsaCipher.getInstance().encrypt(hashedMsg, rsaKeyPair.publicKey());

        assert hashedMsg.equals(RsaCipher.getInstance().decrypt(encryptedPwd, rsaKeyPair.privateKey()));
    }
}
