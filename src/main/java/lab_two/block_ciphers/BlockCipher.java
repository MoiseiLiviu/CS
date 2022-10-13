package lab_two.block_ciphers;

public interface BlockCipher {
    String encrypt(String plaintext);

    String decrypt(String cipherBytes);
}
