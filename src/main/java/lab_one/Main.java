package lab_one;

public class Main {

    public static void main(String[] args) {
        String message = "I freaking love the Technical university of Moldova.";

        int caesarShift = 5;
        Caesar caesar = new Caesar(caesarShift);
        String encryptedCaesar = caesar.encrypt(message);
        System.out.println("lab1.Caesar's lab1.Cipher encrypted: "+ encryptedCaesar +"  with shift of "+ caesarShift);
        System.out.println("lab1.Caesar's lab1.Cipher decrypted: "+ caesar.decrypt(encryptedCaesar));

        String vigenereKey = "Coding";
        Vigenere vigenere = new Vigenere(vigenereKey);
        String encryptedVigenere = vigenere.encrypt(message);
        System.out.println("\nlab1.Vigenere lab1.Cipher encrypted: "+ encryptedVigenere + "  with key: "+ vigenereKey);
        System.out.println("lab1.Vigenere lab1.Cipher decrypted: "+ vigenere.decrypt(encryptedVigenere));

        char xorKey = 'X';
        Xor xor = new Xor(xorKey);
        String encryptedXor = xor.encrypt(message);
        System.out.println("\nlab1.Xor lab1.Cipher encrypted: "+ encryptedXor + "  with key: "+ xorKey);
        System.out.println("lab1.Xor lab1.Cipher decrypted: "+ xor.decrypt(encryptedXor));

        int a=3, b=1;
        Affine affine = new Affine(a, b);
        String encryptedAffine = affine.encrypt(message);
        System.out.println("\nlab1.Affine lab1.Cipher encrypted: "+ encryptedAffine + "  with a and b: "+ a +", "+b);
        System.out.println("lab1.Affine lab1.Cipher decrypted: "+ affine.decrypt(encryptedAffine));
    }
}