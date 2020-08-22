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

package com.webauthn4j.validator;

import com.webauthn4j.data.extension.client.AuthenticationExtensionsClientOutputs;
import com.webauthn4j.data.extension.client.ExtensionClientOutput;
import com.webauthn4j.validator.exception.UnexpectedExtensionException;

import java.util.List;

/**
 * Validates clientExtensionOutputs
 */
class ClientExtensionValidator {

    // ~ Methods
    // ========================================================================================================

    public <C extends ExtensionClientOutput> void validate(AuthenticationExtensionsClientOutputs<C> authenticationExtensionsClientOutputs,
                                                           List<String> expectedExtensionIdentifiers) {
        validateExtensionIds(authenticationExtensionsClientOutputs, expectedExtensionIdentifiers);
    }

    //TODO: extensionId -> key
    private <C extends ExtensionClientOutput> void validateExtensionIds(AuthenticationExtensionsClientOutputs<C> authenticationExtensionsClientOutputs, List<String> expectedExtensionIdentifiers) {
        List<String> expected;
        if (expectedExtensionIdentifiers == null) {
            return;
        } else {
            expected = expectedExtensionIdentifiers;
        }

        if (authenticationExtensionsClientOutputs != null) {
            authenticationExtensionsClientOutputs.getKeys().forEach(identifier -> {
                if (!expected.contains(identifier)) {
                    throw new UnexpectedExtensionException(String.format("Unexpected client extension '%s' is contained", identifier));
                }
            });
        }
    }

}
