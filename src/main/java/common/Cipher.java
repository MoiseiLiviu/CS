package common;

public interface Cipher {

    Integer ALPHABET_SIZE = 26;

    String encrypt(String message);

    String decrypt(String message);
}
