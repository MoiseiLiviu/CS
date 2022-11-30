package com.cs.lab_5.authservice.service;

import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.code.HashingAlgorithm;
import dev.samstevens.totp.exceptions.QrGenerationException;
import dev.samstevens.totp.qr.QrData;
import dev.samstevens.totp.qr.ZxingPngQrGenerator;
import dev.samstevens.totp.secret.DefaultSecretGenerator;
import dev.samstevens.totp.secret.SecretGenerator;
import dev.samstevens.totp.time.SystemTimeProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static dev.samstevens.totp.util.Utils.getDataUriForImage;

@Service
@Slf4j
public class TotpManager {

    private static final SecretGenerator generator = new DefaultSecretGenerator();

    public String generateSecret() {
        return generator.generate();
    }

    public String getUriForImage(final String secret) {
        final var data = new QrData.Builder()
                .label("Two-factor-auth-test")
                .secret(secret)
                .issuer("exampleTwoFactor")
                .algorithm(HashingAlgorithm.SHA1)
                .digits(6)
                .period(30)
                .build();
        final var qrGenerator = new ZxingPngQrGenerator();
        byte[] imageData = new byte[0];
        try {
            imageData = qrGenerator.generate(data);
        } catch (QrGenerationException e) {
           log.error("unable to generate QrCode");
        }
        final String mimeType = qrGenerator.getImageMimeType();

        return getDataUriForImage(imageData, mimeType);
    }

    public boolean verifyCode(final String code, final String secret) {
        final var timeProvider = new SystemTimeProvider();
        final var codeGenerator = new DefaultCodeGenerator();
        final var verifier = new DefaultCodeVerifier(codeGenerator, timeProvider);

        return verifier.isValidCode(secret, code);
    }
}
