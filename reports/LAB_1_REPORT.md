# Laboratory Work 1, Ciphers Implementation

### Course: Cryptography & Security
### Author: Moisei Liviu

----

## Objectives:

1. Get familiar with the basics of cryptography and classical ciphers.

2. Implement 4 types of the classical ciphers:
    - Caesar cipher with one key used for substitution,
    - Affine cipher
    - Vigenere cipher,
    - Xor cipher

3. Structure the project in methods/classes/packages as neeeded.

### Caesar's cipher:

Here, we're using the cyclic property of the cipher under modulo to reuse the same method `cipher()` for both encoding and decoding as in `common.Cipher(n) = De-cipher(26-n)`. So we call `cipher(text, this.key)` for encoding and `cipher(text, common.Cipher.ALPHABET_SIZE - this.key)` for decoding.

```
    private String cipher(String text, int s) {
        StringBuilder encryptedText = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char currentChar = text.charAt(i);
            if (Character.isUpperCase(currentChar)) {
                char ch = (char) ((currentChar + s - 65) % common.Cipher.ALPHABET_SIZE + 65);
                encryptedText.append(ch);
            } else if (Character.isLowerCase(currentChar)) {
                char ch = (char) ((currentChar + s - 97) % common.Cipher.ALPHABET_SIZE + 97);
                encryptedText.append(ch);
            } else {
                encryptedText.append(currentChar);
            }
        }

        return encryptedText.toString();
    }
```

In this method we iterate over each character of the message and apply the formula `E(x)=(x+n) % 26` where n is the shift. To do that we get the ASCII code and subtract 65 for uppercase letters and 97 for lowercase letters to map them to their corresponding numbers.

### Affine:

First, we instantiate the keys `a` and `b` inside the constructor:
```
    public Affine(int a, int b) {
        this.a = a;
        this.b = b;
    }
```
These numbers must be coprime.

At encryption, we iterate over each character of the message, and apply the formula `E ( x ) = ( a x + b ) mod m` where x is the number mapped to the character and m is the size of alphabet:
```
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
```
At decryption, we need to apply the formula `D ( x ) = a^-1 ( x - b ) mod m` on each of the characters, for that we need to find the modular inverse of a, which we do with the following code:
```
        for (int i = 0; i < ALPHABET_SIZE; i++) {
            flag = (a * i) % ALPHABET_SIZE;
            if (flag == 1) {
                aInv = i;
            }
        }
```

After that we just apply the formula:
```
        for (int i = 0; i < message.length(); i++) {
            if (Character.isLetter(message.charAt(i))) {
                boolean isLower = Character.isLowerCase(message.charAt(i));
                char x = (char) ((aInv * (Character.toUpperCase(message.charAt(i)) + 'A' - b) % ALPHABET_SIZE) + 'A');
                msg.append(isLower ? Character.toLowerCase(x) : x);
            } else {
                msg.append(message.charAt(i));
            }
        }
```

### Vigenere:

In order to apply the vigenere cipher we need to make sure the key is the same length as the plaintext, for that we circularly repeat the key until that condition is true:
```
    private void circularlyRepeatKey(String text) {
        int len = text.length();
        StringBuilder keyBuilder = new StringBuilder(this.key);
        for (int i = 0; ; i++) {
            if (key.length() >= len)
                i = 0;
            if (keyBuilder.length() == text.length())
                break;
            keyBuilder.append(keyBuilder.charAt(i));
        }
        this.key = keyBuilder.toString();
    }
```
Now we can pair each character of the plaintext to a character in the key, and use the position in the alphabet of that key as a shift just as we did for Caesar's cipher:
```
    public String encrypt(String message) {
        circularlyRepeatKey(message);
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);
            if (Character.isLetter(currentChar)) {
                boolean isLower = Character.isLowerCase(message.charAt(i));
                char x = (char) (((Character.toUpperCase(message.charAt(i)) + key.charAt(i)) % common.Cipher.ALPHABET_SIZE) + 'A');
                encryptedMessage.append(isLower ? Character.toLowerCase(x) : x);
            } else encryptedMessage.append(currentChar);
        }
        return encryptedMessage.toString();
    }
```

### Xor:

For this cypher we just need to perform the XOR operation on each of the characters in the plaintext with the provided key:

```
    private String xorCipher(String message){
        StringBuilder outputString = new StringBuilder();
        int len = message.length();
        for (int i = 0; i < len; i++) {
            outputString.append((char) (message.charAt(i) ^ key));
        }

        return outputString.toString();
    }
```