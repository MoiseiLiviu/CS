package lab_two.stream_ciphers;

public interface StreamCipher {
    int BYTE_VALUES = 256;

    byte[] encrypt(String plaintext);

    String decrypt(byte[] cipherBytes);
}
