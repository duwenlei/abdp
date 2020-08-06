package com.hiooih.core.security;

import com.hiooih.base.cert.csr.CertificateSigningRequest;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.jcajce.JcaPEMWriter;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemWriter;
import sun.security.provider.X509Factory;

import javax.security.auth.x500.X500Principal;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * KeyStore 工具类
 *
 * @author duwenlei
 **/
public class AbdpKeyStoreTool {

    /**
     * 后续需要初始化
     */
    private static final String STORE_PASSWORD = "abdp";

    /**
     * KeyStore 生成
     *
     * @param filePath 文件地址
     * @param keyPair  密钥
     */
    public static void create(String filePath, KeyPair keyPair) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, OperatorCreationException {
        Security.addProvider(new BouncyCastleProvider());
//        X500Principal principal = new X500Principal("CN=hiooih, OU=hiooih");
//        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256WithRSA").build(keyPair.getPrivate());
//        JcaPKCS10CertificationRequestBuilder builder = new JcaPKCS10CertificationRequestBuilder(principal, keyPair.getPublic());
//        ExtensionsGenerator extensionsGenerator = new ExtensionsGenerator();
//        GeneralNames generalNames = new GeneralNames(new GeneralName[]{new GeneralName(GeneralName.rfc822Name, "owner=hiooih")});
//        extensionsGenerator.addExtension(Extension.subjectAlternativeName, false, generalNames);
//
//        builder.addAttribute(PKCSObjectIdentifiers.pkcs_9_at_extensionRequest, extensionsGenerator.generate());
        CertificateSigningRequest certificateSigningRequest = new CertificateSigningRequest.Builder()
                .addCN("www.hiooih.com")
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

        // CSR 写入
        String csrPath = filePath + "/abdp.csr";
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(csrPath));
        JcaPEMWriter writer = new JcaPEMWriter(outputStreamWriter);
        writer.writeObject(csr);
        writer.close();

        // X509 证书创建
        X500Name issue = new X500Name("CN=ABDP");
        X500Name root = new X500Name("CN=CA root");

        // 证书序列号
        BigInteger serial = BigInteger.probablePrime(72, new Random());

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 10);
        SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(keyPair.getPublic().getEncoded());
        X509v3CertificateBuilder certificateBuilder = new X509v3CertificateBuilder(root, serial, new Date(), calendar.getTime(), issue, subjectPublicKeyInfo);
//        X509CertificateHolder holder = certificateBuilder.build(contentSigner);
//        System.out.println(X509Factory.BEGIN_CERT);
//        System.out.println(new String(Base64.encode(holder.getEncoded())));
//        System.out.println(X509Factory.END_CERT);

        // ROOT CA 写入
        String rootCaPath = filePath + "/root.cer";
        OutputStreamWriter streamWriter = new OutputStreamWriter(new FileOutputStream(rootCaPath));
        streamWriter.write(X509Factory.BEGIN_CERT);
//        streamWriter.write(new String(Base64.encode(holder.getEncoded())));
        streamWriter.write(X509Factory.END_CERT);
        streamWriter.close();


//        KeyStore store = KeyStore.getInstance("pkcs12");
//        store.load(null);
//        store.setKeyEntry("ABDP_PRI", keyPair.getPrivate(), STORE_PASSWORD.toCharArray(), null);
//        store.store(new FileOutputStream(filePath), STORE_PASSWORD.toCharArray());
    }


    public static void main(String[] args) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            String property = System.getProperty("user.home");
            String tmpDir = property + "/.abdp";
            File file = new File(tmpDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            AbdpKeyStoreTool.create(property + "/.abdp", keyPair);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (OperatorCreationException e) {
            e.printStackTrace();
        }
    }
}
