package com.hiooih.base.cert.csr;

import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import sun.security.tools.keytool.CertAndKeyGen;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

class CertificateSigningRequestTest {

    @Test
    void getCSR() throws OperatorCreationException, NoSuchAlgorithmException, InvalidKeyException, IOException {
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
    }
}