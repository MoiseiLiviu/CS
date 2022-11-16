package lab_1.ciphers;


import common.Cipher;

public class Affine implements Cipher {

    private final int a;
    private final int b;

    public Affine(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String encrypt(String message) {
        StringBuilder cipher = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                boolean isLower = Character.isLowerCase(message.charAt(i));
                char x = (char) ((((a * (Character.toUpperCase(message.charAt(i)) - 'A')) + b) % ALPHABET_SIZE) + 'A');
                cipher.append(isLower ? Character.toLowerCase(x) : x);
            } else {
                cipher.append(message.charAt(i));
            }
        }
        return cipher.toString();
    }

    @Override
    public String decrypt(String message) {
        StringBuilder msg = new StringBuilder();
        int aInv = 0;
        int flag;
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            flag = (a * i) % ALPHABET_SIZE;
            if (flag == 1) {
                aInv = i;
            }
        }
        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                boolean isLower = Character.isLowerCase(message.charAt(i));
                char x = (char) ((aInv * (Character.toUpperCase(message.charAt(i)) + 'A' - b) % ALPHABET_SIZE) + 'A');
                msg.append(isLower ? Character.toLowerCase(x) : x);
            } else {
                msg.append(message.charAt(i));
            }
        }

        return msg.toString();
    }
}
