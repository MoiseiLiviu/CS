package lab_3.rsa.key;

import java.math.BigInteger;

public record RsaPublicKey(BigInteger e, BigInteger n) {}
