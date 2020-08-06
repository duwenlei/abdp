package com.hiooih.base.cert.root;

import org.bouncycastle.operator.OperatorCreationException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import static org.junit.jupiter.api.Assertions.*;

class RootCaGenerateTest {

    @Test
    void generateRootCa() {
        RootCaGenerate rootCaGenerate = new RootCaGenerate();
        try {
            rootCaGenerate.generateRootCa();
        } catch (NoSuchAlgorithmException | InvalidKeyException | OperatorCreationException | IOException | CertificateException | KeyStoreException e) {
            e.printStackTrace();
        }
    }
}