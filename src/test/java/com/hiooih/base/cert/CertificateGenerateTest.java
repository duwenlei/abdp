package com.hiooih.base.cert;

import com.hiooih.base.cert.csr.CertificateSigningRequest;
import com.hiooih.base.cert.root.KeyStoreConst;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import sun.security.provider.X509Factory;
import sun.security.tools.keytool.CertAndKeyGen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

class CertificateGenerateTest {

    @Test
    void generateRootCa() throws NoSuchAlgorithmException, InvalidKeyException, OperatorCreationException, IOException, CertificateException {
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
        Certificate certificate = certificateGenerate.generateRootCa(csr.getEncoded(),
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

    @Test
    void generate() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, OperatorCreationException, InvalidKeyException {
        KeyStore store = KeyStore.getInstance("jks");
        File file = ResourceUtils.getFile(String.format("%s%s", ResourceUtils.CLASSPATH_URL_PREFIX, "config/root.jks"));
        store.load(new FileInputStream(file), KeyStoreConst.PASSWORD.toCharArray());
        X509Certificate rootCa = (X509Certificate) store.getCertificate("root");
        PrivateKey rootPrivateKey = (PrivateKey) store.getKey(KeyStoreConst.ROOT_PRIVATE_KEY_ALIAS, KeyStoreConst.PASSWORD.toCharArray());
        System.out.println(rootCa.getSubjectDN().toString());

        // CSR
        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA256WithRSA");
        certAndKeyGen.generate(1024);
        CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest.Builder()
                .addCN("www.dwl.com")
                .addC("CN")
                .addST("NMG")
                .addL("CF")
                .addO("dwl")
                .addOU("IT")
                .addSignatureAlgorithm("SHA256WithRSA")
                .addPrivateKey(certAndKeyGen.getPrivateKey())
                .addPublicKey(certAndKeyGen.getPublicKeyAnyway())
                .build();
        PKCS10CertificationRequest csr = certificateSigningRequest.getCSR();
        System.out.println("-----BEGIN CERTIFICATE REQUEST-----");
        System.out.println(new String(Base64.encode(csr.getEncoded())));
        System.out.println("-----END CERTIFICATE REQUEST-----");

        CertificateGenerate certificateGenerate = new CertificateGenerate();
        Date notBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        Date notAfter = calendar.getTime();

        Certificate certificate = certificateGenerate.generate(rootCa, csr.getEncoded(), "SHA256WithRSA", rootPrivateKey, System.currentTimeMillis(), notBefore, notAfter);
        System.out.println(X509Factory.BEGIN_CERT);
        System.out.println(new String(Base64.encode(certificate.getEncoded())));
        System.out.println(X509Factory.END_CERT);
        saveToFile(rootCa, "C:\\Users\\duwenlei\\Downloads\\root.cer");
        saveToFile((X509Certificate) certificate, "C:\\Users\\duwenlei\\Downloads\\user.cer");
    }

    private static void saveToFile(X509Certificate certificate, String filePath) throws IOException, CertificateEncodingException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        fileOutputStream.write(certificate.getEncoded());
        fileOutputStream.close();
    }
}