package com.hiooih.base.cert;

import org.bouncycastle.asn1.pkcs.Attribute;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;

import java.io.IOException;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * 证书生成工具类
 *
 * @author duwenlei
 **/
public class CertificateGenerate {
    /**
     * 颁发者
     */
    private X500Name ISSUER = new X500Name("CN=www.hiooih.com,C=CN,ST=NMG,L=CF,O=HIOOIH,OU=IT,E=hiooih@163.com");

    /**
     * 证书生成
     *
     * @return
     */
    public Certificate generate(byte[] csr, String signatureAlgorithm, PrivateKey privateKey, long serialNumber, Date notBefore, Date notAfter) throws IOException, OperatorCreationException, CertificateException {
        PKCS10CertificationRequest pkcs10CertificationRequest = new PKCS10CertificationRequest(csr);
        X509v3CertificateBuilder x509v3CertificateBuilder = new X509v3CertificateBuilder(ISSUER,
                BigInteger.valueOf(serialNumber),
                notBefore,
                notAfter,
                pkcs10CertificationRequest.getSubject(),
                pkcs10CertificationRequest.getSubjectPublicKeyInfo()
        );

        // TODO 扩展
        ContentSigner signer = new JcaContentSignerBuilder(signatureAlgorithm).setProvider(new BouncyCastleProvider()).build(privateKey);
        X509CertificateHolder x509CertificateHolder = x509v3CertificateBuilder.build(signer);
        X509Certificate certificate = new JcaX509CertificateConverter().getCertificate(x509CertificateHolder);
        return certificate;
    }
}
