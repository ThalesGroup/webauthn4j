/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webauthn4j.authenticator;

import com.webauthn4j.data.AuthenticatorTransport;
import com.webauthn4j.data.CoreRegistrationData;
import com.webauthn4j.data.attestation.authenticator.AttestedCredentialData;
import com.webauthn4j.data.attestation.statement.AttestationStatement;
import com.webauthn4j.data.extension.authenticator.AuthenticationExtensionsAuthenticatorOutputs;
import com.webauthn4j.data.extension.authenticator.RegistrationExtensionAuthenticatorOutput;
import com.webauthn4j.util.ConstUtil;

import java.util.Objects;
import java.util.Set;

/**
 * An {@link CoreAuthenticator} implementation
 */
public class CoreAuthenticatorImpl implements CoreAuthenticator {

    //~ Instance fields ================================================================================================
    private AttestedCredentialData attestedCredentialData;
    private AttestationStatement attestationStatement;
    private long counter;
    private Set<AuthenticatorTransport> transports;
    private AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions;

    public CoreAuthenticatorImpl(AttestedCredentialData attestedCredentialData,
                                 AttestationStatement attestationStatement,
                                 long counter,
                                 Set<AuthenticatorTransport> transports,
                                 AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {
        this.attestedCredentialData = attestedCredentialData;
        this.attestationStatement = attestationStatement;
        setCounter(counter);
        this.transports = transports;
        this.authenticatorExtensions = authenticatorExtensions;
    }

    public static CoreAuthenticatorImpl createFromCoreRegistrationData(CoreRegistrationData coreRegistrationData){
        return new CoreAuthenticatorImpl(
                coreRegistrationData.getAttestationObject().getAuthenticatorData().getAttestedCredentialData(),
                coreRegistrationData.getAttestationObject().getAttestationStatement(),
                coreRegistrationData.getAttestationObject().getAuthenticatorData().getSignCount(),
                coreRegistrationData.getTransports(),
                coreRegistrationData.getAttestationObject().getAuthenticatorData().getExtensions());
    }

    @Override
    public AttestedCredentialData getAttestedCredentialData() {
        return attestedCredentialData;
    }

    public void setAttestedCredentialData(AttestedCredentialData attestedCredentialData) {
        this.attestedCredentialData = attestedCredentialData;
    }

    @Override
    public AttestationStatement getAttestationStatement() {
        return attestationStatement;
    }

    public void setAttestationStatement(AttestationStatement attestationStatement) {
        this.attestationStatement = attestationStatement;
    }

    @Override
    public long getCounter() {
        return this.counter;
    }

    @Override
    public void setCounter(long value) {
        if (value > ConstUtil.UINT_MAX_VALUE) {
            throw new IllegalArgumentException("[Assertion failed] - this argument is unsigned int. it must not exceed 4294967295.");
        }
        if (value < 0) {
            throw new IllegalArgumentException("[Assertion failed] - this argument is unsigned int. it must not be negative value.");
        }
        this.counter = value;
    }

    @Override
    public Set<AuthenticatorTransport> getTransports() {
        return transports;
    }

    public void setTransports(Set<AuthenticatorTransport> transports) {
        this.transports = transports;
    }

    @Override
    public AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> getAuthenticatorExtensions() {
        return authenticatorExtensions;
    }

    public void setAuthenticatorExtensions(AuthenticationExtensionsAuthenticatorOutputs<RegistrationExtensionAuthenticatorOutput> authenticatorExtensions) {
        this.authenticatorExtensions = authenticatorExtensions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoreAuthenticatorImpl that = (CoreAuthenticatorImpl) o;
        return counter == that.counter &&
                Objects.equals(attestedCredentialData, that.attestedCredentialData) &&
                Objects.equals(attestationStatement, that.attestationStatement) &&
                Objects.equals(transports, that.transports) &&
                Objects.equals(authenticatorExtensions, that.authenticatorExtensions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attestedCredentialData, attestationStatement, counter, transports, authenticatorExtensions);
    }
}
