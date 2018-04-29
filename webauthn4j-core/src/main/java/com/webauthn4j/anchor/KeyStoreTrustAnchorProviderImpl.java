/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.anchor;

import com.webauthn4j.validator.exception.KeyStoreException;
import com.webauthn4j.util.CertificateUtil;
import com.webauthn4j.util.WIP;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Provides {@link TrustAnchor}'{@link Set} backed by Java KeyStore file.
 */
@WIP
public class KeyStoreTrustAnchorProviderImpl implements TrustAnchorProvider {

    //~ Instance fields ================================================================================================

    private Path keyStore;
    private String password;

    /**
     * Provides {@link TrustAnchor}'{@link Set} backed by Java KeyStore file.
     *
     * @return {@link TrustAnchor}'{@link Set}
     */
    public Set<TrustAnchor> provide() {
        Path keystore = getKeyStore();
        try (InputStream inputStream = Files.newInputStream(keystore)) {
            KeyStore keyStoreObject = loadKeyStoreFromStream(inputStream, getPassword());
            List<String> aliases = Collections.list(keyStoreObject.aliases());
            Set<TrustAnchor> trustAnchors = new HashSet<>();
            for (String alias : aliases) {
                X509Certificate certificate = (X509Certificate) keyStoreObject.getCertificate(alias);
                trustAnchors.add(new TrustAnchor(certificate, null));
            }
            return trustAnchors;
        } catch (java.security.KeyStoreException | IOException e) {
            throw new KeyStoreException(e);
        }
    }

    private KeyStore loadKeyStoreFromStream(InputStream inputStream, String password) {
        KeyStore keyStoreObject = CertificateUtil.createKeyStore();
        try {
            keyStoreObject.load(inputStream, password.toCharArray());
        } catch (IOException | NoSuchAlgorithmException | java.security.cert.CertificateException e) {
            throw new KeyStoreException(e);
        }
        return keyStoreObject;
    }

    /**
     * Provides keyStore file
     *
     * @return keyStore file
     */
    public Path getKeyStore() {
        return keyStore;
    }

    /**
     * Sets keyStore file
     *
     * @param keyStore keyStore file
     */
    public void setKeyStore(Path keyStore) {
        this.keyStore = keyStore;
    }

    /**
     * Provides keyStore file password
     *
     * @return keyStore file password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets keyStore file password
     *
     * @param password keyStore file password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
