package lab_3.rsa.key;


import java.math.BigInteger;

public record RsaPrivateKey(BigInteger d, BigInteger n) {}
