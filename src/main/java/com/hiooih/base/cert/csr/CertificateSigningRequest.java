package com.hiooih.base.cert.csr;

import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.bouncycastle.util.encoders.Base64;
import sun.security.tools.keytool.CertAndKeyGen;

import javax.security.auth.x500.X500Principal;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Objects;

/**
 * CSR 生成器
 *
 * @author duwenlei
 **/
public class CertificateSigningRequest {

    /**
     * 内容
     */
    private final String principalName;

    /**
     * 签名算法
     */
    private final String signatureAlgorithm;

    /**
     * 公钥
     */
    private final PublicKey publicKey;

    /**
     * 私钥
     */
    private final PrivateKey privateKey;

    public PKCS10CertificationRequest getCSR() throws OperatorCreationException {
        Objects.requireNonNull(principalName, "CN 不能为空");
        Objects.requireNonNull(signatureAlgorithm, "signatureAlgorithm 不能为空");
        Objects.requireNonNull(privateKey, "privateKey 不能为空");
        Objects.requireNonNull(publicKey, "publicKey 不能为空");

        // 生成 PKCS10CertificationRequestBuilder
        PKCS10CertificationRequestBuilder pkcs10CertificationRequestBuilder = new JcaPKCS10CertificationRequestBuilder(new X500Principal(principalName), publicKey);

        // 扩展

        // 内容签名
        JcaContentSignerBuilder jcaContentSignerBuilder = new JcaContentSignerBuilder(signatureAlgorithm);
        ContentSigner contentSigner = jcaContentSignerBuilder.build(privateKey);
        // 返回 P10 格式的 CSR
        return pkcs10CertificationRequestBuilder.build(contentSigner);
    }

    private CertificateSigningRequest(Builder builder) {
        this.principalName = builder.principalName;
        this.signatureAlgorithm = builder.signatureAlgorithm;
        this.privateKey = builder.privateKey;
        this.publicKey = builder.publicKey;
    }

    public static class Builder {
        private String principalName;
        private String signatureAlgorithm;
        private PublicKey publicKey;
        private PrivateKey privateKey;


        public Builder addPrincipalName(String principalName) {
            this.principalName = String.format("CN=%s,C=CN,ST=NMG,L=CF,O=HIOOIH,OU=IT,EMAIL=hiooih@163.com", principalName);
            return this;
        }

        public Builder addSignatureAlgorithm(String signatureAlgorithm) {
            this.signatureAlgorithm = signatureAlgorithm;
            return this;
        }

        public Builder addPrivateKey(PrivateKey privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder addPublicKey(PublicKey publicKey) {
            this.publicKey = publicKey;
            return this;
        }


        public CertificateSigningRequest build() {
            return new CertificateSigningRequest(this);
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, OperatorCreationException, IOException {
        CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", "SHA256WithRSA");
        certAndKeyGen.generate(1024);

        CertificateSigningRequest certificateSigningRequest = new Builder()
                .addPrincipalName("DWL")
                .addSignatureAlgorithm("SHA256WithRSA")
                .addPrivateKey(certAndKeyGen.getPrivateKey())
                .addPublicKey(certAndKeyGen.getPublicKeyAnyway())
                .build();
        PKCS10CertificationRequest csr = certificateSigningRequest.getCSR();
        System.out.println(new String(Base64.encode(csr.getEncoded())));
    }
}
