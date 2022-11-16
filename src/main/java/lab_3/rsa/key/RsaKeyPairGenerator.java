package lab_3.rsa.key;

import lombok.extern.java.Log;

import java.math.BigInteger;
import java.util.Random;

@Log
public class RsaKeyPairGenerator {


    private static RsaKeyPairGenerator rsaKeyPairGenerator;
    private static final Random random = new Random();

    public static synchronized RsaKeyPairGenerator getInstance() {
        if (rsaKeyPairGenerator == null) {
            rsaKeyPairGenerator = new RsaKeyPairGenerator();
        }
        return rsaKeyPairGenerator;
    }

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


    private BigInteger generateE(BigInteger phi, int bits) {
        BigInteger e1;
        do {
            e1 = new BigInteger(bits, random);
            while (e1.min(phi).equals(phi)) {
                e1 = new BigInteger(bits, random);
            }
        } while (!gcd(e1, phi).equals(BigInteger.ONE));
        return e1;
    }

    private BigInteger generateN(BigInteger p, BigInteger q) {
        return p.multiply(q);
    }

    private BigInteger[] extendedEuclid(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) return new BigInteger[]{
                a, BigInteger.ONE, BigInteger.ZERO
        };
        BigInteger[] vals = extendedEuclid(b, a.mod(b));
        BigInteger d1 = vals[0];
        BigInteger p1 = vals[2];
        BigInteger q1 = vals[1].subtract(a.divide(b).multiply(vals[2]));
        return new BigInteger[]{
                d1, p1, q1
        };
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    private BigInteger getPhi(BigInteger p, BigInteger q) {
        return (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
    }
}
