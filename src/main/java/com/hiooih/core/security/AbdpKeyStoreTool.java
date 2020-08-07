package com.hiooih.core.security;

import com.hiooih.base.cert.CertificateGenerate;
import com.hiooih.base.cert.csr.CertificateSigningRequest;
import com.hiooih.base.cert.root.KeyStoreConst;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

/**
 * KeyStore 工具类
 *
 * @author duwenlei
 **/
public class AbdpKeyStoreTool {

    /**
     * KeyStore 生成
     * 已废弃 移植到 Jwt command line Runner 中
     *
     * @param filePath 文件地址
     * @param keyPair  密钥
     * @see com.hiooih.base.command.JwtCommandLineRunner
     */
    public static void create(String filePath, KeyPair keyPair) throws IOException, OperatorCreationException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException {
        // CSR
        CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest.Builder()
                .addCN("www.abdp.com")
                .addC("CN")
                .addST("NMG")
                .addL("CF")
                .addO("hiooih")
                .addOU("IT")
                .addSignatureAlgorithm("SHA256WithRSA")
                .addPrivateKey(keyPair.getPrivate())
                .addPublicKey(keyPair.getPublic())
                .build();
        PKCS10CertificationRequest csr = certificateSigningRequest.getCSR();

        // keystore
        KeyStore store = KeyStore.getInstance("jks");
        File file = ResourceUtils.getFile(String.format("%s%s", ResourceUtils.CLASSPATH_URL_PREFIX, "config/root.jks"));
        store.load(new FileInputStream(file), KeyStoreConst.PASSWORD.toCharArray());
        X509Certificate rootCa = (X509Certificate) store.getCertificate("root");
        PrivateKey rootPrivateKey = (PrivateKey) store.getKey("priKey", KeyStoreConst.PASSWORD.toCharArray());

        // Cert
        CertificateGenerate certificateGenerate = new CertificateGenerate();
        Date notBefore = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        Date notAfter = calendar.getTime();

        Certificate certificate = certificateGenerate.generate(rootCa, csr.getEncoded(), "SHA256WithRSA", rootPrivateKey, System.currentTimeMillis(), notBefore, notAfter);

        KeyStore keyStore = KeyStore.getInstance("jks");
        keyStore.load(null);
        keyStore.setCertificateEntry(KeyStoreConst.SUB_CA_ALIAS, certificate);
        keyStore.setKeyEntry(KeyStoreConst.SUB_PRIVATE_KEY_ALIAS, keyPair.getPrivate(), KeyStoreConst.PASSWORD.toCharArray(), new Certificate[]{certificate});
        keyStore.store(new FileOutputStream(filePath), KeyStoreConst.SUB_PASSWORD.toCharArray());
    }

}
