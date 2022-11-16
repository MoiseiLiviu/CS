package lab_2.stream_ciphers;

public interface StreamCipher {

    byte[] encrypt(String message);

    String decrypt(byte[] encMessage);
}
