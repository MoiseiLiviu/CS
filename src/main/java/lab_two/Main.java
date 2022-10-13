package lab_two;


import lab_two.block_ciphers.DES;
import lab_two.stream_ciphers.RC4;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        testStreamCipher();
        testBlockCipher();
    }

    private static void testStreamCipher() {
        RC4 r = new RC4("UTM");

        byte[] encrypted = r.encrypt("Cryptography");
        String byteArrayToString = new String(encrypted);
        System.out.println("Encrypted: " + Arrays.toString(encrypted) + " | " + byteArrayToString);

        String decrypted = r.decrypt(encrypted);
        System.out.println("Decrypted: " + decrypted + "\n");


        RC4 r2 = new RC4("Panda");

        byte[] encrypted2 = r2.encrypt("Or you will go down in history as cowards");
        String byteArrayToString2 = new String(encrypted2);
        System.out.println("Encrypted: " + Arrays.toString(encrypted2) + " | " + byteArrayToString2);

        String decrypted2 = r2.decrypt(encrypted2);
        System.out.println("Decrypted: " + decrypted2 + "\n");


        RC4 r3 = new RC4("Locke");

        byte[] encrypted3 = r3.encrypt("Lamora");
        String byteArrayToString3 = new String(encrypted3);
        System.out.println("Encrypted: " + Arrays.toString(encrypted3) + " | " + byteArrayToString3);

        String decrypted3 = r3.decrypt(encrypted3);
        System.out.println("Decrypted: " + decrypted3 + "\n");
    }

    private static void testBlockCipher() {
        String text = "123456ABCD132536";
        String key = "AABB09182736CCDD";

        DES cipher = new DES(key);
        System.out.println("Encryption:\n");

        text = cipher.encrypt(text);

        System.out.println("\nCipher Text: " + text.toUpperCase() + "\n");
        System.out.println("Decryption\n");

        text = cipher.decrypt(text);
        System.out.println("\nPlain Text: " + text.toUpperCase());
    }
}
