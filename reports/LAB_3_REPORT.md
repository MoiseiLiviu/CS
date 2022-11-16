# Topic: Asymmetric Ciphers.

### Course: Cryptography & Security

### Author: Moisei Liviu

---

## Overview

Asymmetric Cryptography (a.k.a. Public-Key Cryptography)deals with the encryption of plain text when having 2 keys, one being public and the other one private. The keys form a pair and despite being different they are related.

As the name implies, the public key is available to the public but the private one is available only to the authenticated recipients.

A popular use case of the asymmetric encryption is in SSL/TLS certificates alongside symmetric encryption mechanisms. It is necessary to use both types of encryption because asymmetric ciphers are computationally expensive, so these are usually used for the communication initiation and key exchange, or sometimes called handshake. The messages after that are encrypted with symmetric ciphers.

## Objectives

   1. Get familiar with the asymmetric cryptography mechanisms.

   2. Implement an example of an asymmetric cipher.

   3. As in the previous task, please use a client class or test classes to showcase the execution of your programs.

### common.Cipher analysis - RSA:

The idea of RSA is based on the fact that it is difficult to factorize a large integer. The public key consists of two numbers where one number is a multiplication of two large prime numbers. And private key is also derived from the same two prime numbers. RSA keys can be typically 1024 or 2048 bits long, but experts believe that 1024-bit keys could be broken in the near future. But till now it seems to be an infeasible task.

* First we need to generate the public and private keys. For that we choose two big prime numbers `q` and `p`. Then we calculate phi, such that `Φ(n) = (P-1)(Q-1)`. Then we calculate the product of the prime numbers `n` and choose an exponent `e` such that `1 < e < Φ(n)`, and then we calculate private key `d` which is `d = (k*Φ(n) + 1) / e for some integer k
  For k = 2, value of d is 2011.`

We have the following code in the class RsaKeyPairGenerator:
```
    public RsaKeyPair generateKeyPair(int bits) {
        BigInteger p = BigInteger.probablePrime(bits, random);
        log.info("p: " + p);

        BigInteger q = BigInteger.probablePrime(bits, random);
        log.info("q: " + q);

        BigInteger n = generateN(p, q);

        BigInteger phi = getPhi(p, q);
        BigInteger e = generateE(phi, bits);
        BigInteger d = extendedEuclid(e, phi)[1];

        RsaPrivateKey rsaPrivateKey = new RsaPrivateKey(d, n);
        RsaPublicKey rsaPublicKey = new RsaPublicKey(e, n);
        return new RsaKeyPair(rsaPrivateKey, rsaPublicKey);
    }
```

After generating the keys we can obtain the ciphertext with the following method:
```
    public byte[] encrypt(String message, RsaPublicKey rsaPublicKey) {
        BigInteger cipherMessage = new BigInteger(message.getBytes());
        return cipherMessage.modPow(rsaPublicKey.e(), rsaPublicKey.n()).toByteArray();
    }
```

Basically the ciphertext is obtained with the formula `E(x) = (x ^ e) mod n`.

Decoding is the same but we swap `e` with `d`.