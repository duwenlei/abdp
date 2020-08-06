package com.hiooih.base.cert;

import com.hiooih.base.cert.csr.CertificateSigningRequest;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import sun.security.provider.X509Factory;
import sun.security.tools.keytool.CertAndKeyGen;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CertificateGenerateTest {

    @Test
    void generate() throws NoSuchAlgorithmException, InvalidKeyException, OperatorCreationException, IOException, CertificateException {
        // CSR
        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA256WithRSA");
        certAndKeyGen.generate(1024);
        CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest.Builder()
                .addCN("www.hiooih.com")
                .addC("CN")
                .addST("NMG")
                .addL("CF")
                .addO("hiooih")
                .addOU("IT")
                .addSignatureAlgorithm("SHA256WithRSA")
                .addPrivateKey(certAndKeyGen.getPrivateKey())
                .addPublicKey(certAndKeyGen.getPublicKeyAnyway())
                .build();
        PKCS10CertificationRequest csr = certificateSigningRequest.getCSR();
        System.out.println("-----BEGIN CERTIFICATE REQUEST-----");
        System.out.println(new String(Base64.encode(csr.getEncoded())));
        System.out.println("-----END CERTIFICATE REQUEST-----");

        // Certificate
        Date notBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        Date notAfter = calendar.getTime();

        CertificateGenerate certificateGenerate = new CertificateGenerate();
        Certificate certificate = certificateGenerate.generate(csr.getEncoded(),
                "SHA256WithRSA",
                certAndKeyGen.getPrivateKey(),
                System.currentTimeMillis(),
                notBefore,
                notAfter
        );
        System.out.println(X509Factory.BEGIN_CERT);
        System.out.println(new String(Base64.encode(certificate.getEncoded())));
        System.out.println(X509Factory.END_CERT);
    }
}