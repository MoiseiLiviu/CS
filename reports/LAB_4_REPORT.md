# Topic: Hash functions and Digital Signatures.

### Course: Cryptography & Security

### Author: Moisei Liviu

---

## Overview

Hashing is a technique used to compute a new representation of an existing value, message or any piece of text. The new representation is also commonly called a digest of the initial text, and it is a one way function meaning that it should be impossible to retrieve the initial content from the digest.

Such a technique has the following usages:

* Offering confidentiality when storing passwords,
* Checking for integrity for some downloaded files or content,
* Creation of digital signatures, which provides integrity and non-repudiation.

In order to create digital signatures, the initial message or text needs to be hashed to get the digest. After that, the digest is to be encrypted using a public key encryption cipher. Having this, the obtained digital signature can be decrypted with the public key and the hash can be compared with an additional hash computed from the received message to check the integrity of it.
## Objectives


1. Get familiar with the hashing techniques/algorithms.
2. Use an appropriate hashing algorithms to store passwords in a local DB.
   * You can use already implemented algortihms from libraries provided for your language.
   * The DB choise is up to you, but it can be something simple, like an in memory one.
3. Use an asymmetric cipher to implement a digital signature process for a user message.
   * Take the user input message.
   * Preprocess the message, if needed.
   * Get a digest of it via hashing.
   * Encrypt it with the chosen cipher.
   * Perform a digital signature check by comparing the hash of the message with the decrypted one.

#### Objective one description:

Here we use a third party library that implements the BCrypt hashing algorithm with a random salt and save that password in an H2 in memory database, in the table User.

    public void execute() {
        String password = "Secret_password";
        String hashedPwd = BCrypt.hashpw(password, BCrypt.gensalt());
        log.info("Hashed pwd : " + hashedPwd);
        User user = new User();
        user.setPassword(hashedPwd);
        userRepository.save(user);
    }

#### Objective two description:

First we generate an RSA key pair. After hashing our password we use this key pair to encode our hashed password. Lastly we compare the hash of the decoded text with the hash of the original message.

    public static void main(String[] args) {
        String message = new Scanner(System.in).nextLine();
        String hashedMsg = BCrypt.hashpw(message, BCrypt.gensalt());
        RsaKeyPair rsaKeyPair = RsaKeyPairGenerator.getInstance().generateKeyPair(512);
        byte[] encryptedPwd = RsaCipher.getInstance().encrypt(hashedMsg, rsaKeyPair.rsaPublicKey());

        assert hashedMsg.equals(RsaCipher.getInstance().decrypt(encryptedPwd, rsaKeyPair.rsaPrivateKey()));
    }