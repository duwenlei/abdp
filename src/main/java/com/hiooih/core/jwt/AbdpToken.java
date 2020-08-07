package com.hiooih.core.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.hiooih.base.cert.root.KeyStoreConst;
import com.hiooih.base.properties.ConfigService;
import com.hiooih.core.jwt.entity.AuthenticationUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/**
 * @author duwenlei
 **/
@Component
@Slf4j
public class AbdpToken {

    @Autowired
    private ConfigService configService;


    /**
     * 公钥
     */
    private static RSAPublicKey RSA_PUBLIC_KEY;

    /**
     * 私钥
     */
    private static RSAPrivateKey RSA_PRIVATE_KEY;


    public String create(AuthenticationUserEntity userEntity) throws UnrecoverableKeyException, CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException {
        // 初始化
        initKey();

        // 生成 Token
        Algorithm algorithm = Algorithm.RSA256(RSA_PUBLIC_KEY, RSA_PRIVATE_KEY);
        return JWT.create()
                .withClaim("userInfo", userEntity.getMap())
                .sign(algorithm);
    }

    public boolean verify(String token) {
        Algorithm algorithm = Algorithm.RSA256(RSA_PUBLIC_KEY, RSA_PRIVATE_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        Map<String, Object> userInfo = decodedJWT.getClaim("userInfo").asMap();

        // 验证

        return false;
    }

    private void initKey() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        if (RSA_PUBLIC_KEY != null) {
            return;
        }

        String abdpTmpFilePath = configService.getAbdpValue("abdp.tmp.file.path", System.getProperty("user.home"));
        String abdpJksFileName = configService.getAbdpValue("abdp.jks.file.name", "abdp.jks");
        File userJksFile = new File(String.format("%s/%s", abdpTmpFilePath, abdpJksFileName));
        if (!userJksFile.exists() || !userJksFile.isFile()) {
            log.error("{} file is not exists.", abdpJksFileName);
            throw new FileNotFoundException("jks file is not exists.");
        }

        KeyStore store = KeyStore.getInstance("jks");
        FileInputStream inputStream = new FileInputStream(userJksFile);
        store.load(inputStream, KeyStoreConst.PASSWORD.toCharArray());

        X509Certificate userCert = (X509Certificate) store.getCertificate(KeyStoreConst.ROOT_CA_ALIAS);
        RSA_PUBLIC_KEY = (RSAPublicKey) userCert.getPublicKey();
        RSA_PRIVATE_KEY = (RSAPrivateKey) store.getKey(KeyStoreConst.ROOT_PRIVATE_KEY_ALIAS, KeyStoreConst.PASSWORD.toCharArray());
        // close stream
        inputStream.close();
    }
}
