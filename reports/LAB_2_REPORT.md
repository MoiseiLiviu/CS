# Topic: Symmetric Ciphers. Stream Ciphers. Block Ciphers.

### Course: Cryptography & Security

### Author: Moisei Liviu

---

## Overview

&ensp;&ensp;&ensp; Symmetric Cryptography deals with the encryption of plain text when having only one encryption key which needs to remain private. Based on the way the plain text is processed/encrypted there are 2 types of ciphers:

- Stream ciphers:
    - The encryption is done one byte at a time.
    - Stream ciphers use confusion to hide the plain text.
    - Make use of substitution techniques to modify the plain text.
    - The implementation is fairly complex.
    - The execution is fast.
- Block ciphers:
    - The encryption is done one block of plain text at a time.
    - Block ciphers use confusion and diffusion to hide the plain text.
    - Make use of transposition techniques to modify the plain text.
    - The implementation is simpler relative to the stream ciphers.
    - The execution is slow compared to the stream ciphers.

## Objectives

1. Get familiar with the symmetric cryptography, stream and block ciphers.

2. Implement an example of a stream cipher RC4.

3. Implement an example of a block cipher DES.

### RC4:

For this cipher we are going to encrypt one byte at a time by performing the XOR operation with a pseudo random stream key which is unpredictable without prior knowledge of the input key. In order to obtain this key stream, we first use the KSA algorithm:
```
    private void ksa() {
        int keyLength = key.length;

        for (int i = 0; i < BYTE_VALUES; i++) {
            S[i] = (byte) i;
        }

        int j = 0;
        for (int i = 0; i < BYTE_VALUES; i++) {
            j = (j + unsignedToBytes(S[i]) + unsignedToBytes(key[i % keyLength])) % BYTE_VALUES;
            byte tmp = S[i];
            S[i] = S[j];
            S[j] = tmp;

        }
    }
```

Here we instantiate the array S as an increasing array with values corresponding to the cell index. After that we scramble this stream by swapping each position in the array to a new position obtained with the formula `j = (j + unsignedToBytes(S[i]) + unsignedToBytes(key[i % keyLength])) % BYTE_VALUES;`. Now that we scrambled KSA, we can use this stream to obtain the actual stream key using the pseudo random generation algorithm:
```
    private byte prga() {
        x = (x + 1) % BYTE_VALUES;
        y = (y + unsignedToBytes(S[x])) % BYTE_VALUES;

        byte temp = S[x];
        S[x] = S[y];
        S[y] = temp;

        return S[(unsignedToBytes(S[x]) + unsignedToBytes(S[y])) % BYTE_VALUES];
    }
```

Now generating the key stream is only a matter of calling the prga enough times to have a corresponding byte for each byte in the plaintext.
```
    public byte[] encrypt(String plaintext) {
        byte[] plainTextToByteArray = plaintext.getBytes(StandardCharsets.US_ASCII);
        K = generateKey(plainTextToByteArray.length);
        return cipher(plainTextToByteArray);
    }
```

The actual encryption/decryption is performed using the `cipher()` method which XORs each byte with the stream key.
```
    private byte[] cipher(byte[] text) {
        byte[] cipher = new byte[text.length];
        for (int m = 0; m < text.length; m++) {
            cipher[m] = (byte) (text[m] ^ K[m]);
        }
        return cipher;
    }
```

### Blowfish:

This is a symmetric block cipher algorithm, and it encrypts the block information of 64-bits at a time. It follows the Feistel network and the working procedure of this algorithm is divided into two parts:
* Subkey Generation − This process transform the key upto 448 bits long to subkeys adding 4168 bits.
* In the data encryption process, it will iterate 16 times. Each round includes the key-dependent permutation, and the key and data-dependent substitution. The operations in the algorithms are XORs or additions on 32-bit words. The only additional operations are four indexed array information lookups per round.

#### Subkey generation:

* First we initialize the P-array and therefore the four S-boxes, in order, with a fixed string and also this string also includes the hexadecimal digits of π.

P1=0x243f6a88, P2=0x85a308d3, P3=0x13198a2e, P4=0x3707344, etc.

* XOR P1 with the first 32 bits of the key, XOR P2 with second which is 32-bits of the key, etc. for all bits of the key (conceivably up to P14). Repeatedly cycles the procedure through the key bits until the complete P-array has been XORed with key bits. (For each short key, there is partly one equivalent longer key. For example, if A is a 64-bit key, then AA, AAA, etc., are same keys.)
```
    private void keyGenerate(String key) {
        int j = 0;
        for (int i = 0; i < P.length; i++) {
            P[i] = xor(P[i], key.substring(j, j + 8));
            j = (j + 8) % key.length();
        }
    }
```

#### Data encryption:

The process consists of 16 rounds, the process goes as follows:

![alt](https://media.geeksforgeeks.org/wp-content/uploads/20190929211024/round-blowfish1.jpg)

* Take input that is a 64-bit data element, x. Divide x into two 32-bit halves : xL, xR.
* Then, for i = 1 to 16;

- xL = xLXOR Pi
- xR = F(xL) XOR xR. Description of function F:
![alt](https://media.geeksforgeeks.org/wp-content/uploads/20190929212325/F-blowfish.jpg)
- Swap xL and xR

* After the 16th round, Swap xL and xR again to undo the last swap.

* Then, ciphertext = concatenation of xL and xR, xR = xR XOR P17 and xL = xL XOR P18.

```
    private String round(int time, String plainText) {
        String left;
        String right;
        left = plainText.substring(0, 8);
        right = plainText.substring(8, 16);
        left = xor(left, P[time]);
        String fOut = f(left);
        right = xor(fOut, right);

        return right + left;
    }
```

* Finally, recombine xL and xR to get the ciphertext. Decryption is the equivalent as encryption, other than P1, P2,……P18 are utilized in the reverse order.