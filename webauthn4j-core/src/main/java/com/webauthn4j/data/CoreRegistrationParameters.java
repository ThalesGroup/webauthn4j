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

package com.webauthn4j.data;

import com.webauthn4j.server.CoreServerProperty;
import com.webauthn4j.util.CollectionUtil;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class CoreRegistrationParameters implements Serializable {

    // server property
    private final CoreServerProperty serverProperty;

    // verification condition
    private final boolean userVerificationRequired;
    private final boolean userPresenceRequired;
    private final List<String> expectedExtensionIds;

    public CoreRegistrationParameters(CoreServerProperty serverProperty, boolean userVerificationRequired, boolean userPresenceRequired, List<String> expectedExtensionIds) {
        this.serverProperty = serverProperty;
        this.userVerificationRequired = userVerificationRequired;
        this.userPresenceRequired = userPresenceRequired;
        this.expectedExtensionIds = CollectionUtil.unmodifiableList(expectedExtensionIds);
    }

    public CoreRegistrationParameters(CoreServerProperty serverProperty, boolean userVerificationRequired, boolean userPresenceRequired) {
        this(serverProperty, userVerificationRequired, userPresenceRequired, null);
    }

    public CoreRegistrationParameters(CoreServerProperty serverProperty, boolean userVerificationRequired) {
        this(serverProperty, userVerificationRequired, true);
    }

    public CoreServerProperty getServerProperty() {
        return serverProperty;
    }

    public boolean isUserVerificationRequired() {
        return userVerificationRequired;
    }

    public boolean isUserPresenceRequired() {
        return userPresenceRequired;
    }

    public List<String> getExpectedExtensionIds() {
        return expectedExtensionIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoreRegistrationParameters that = (CoreRegistrationParameters) o;
        return userVerificationRequired == that.userVerificationRequired &&
                userPresenceRequired == that.userPresenceRequired &&
                Objects.equals(serverProperty, that.serverProperty) &&
                Objects.equals(expectedExtensionIds, that.expectedExtensionIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverProperty, userVerificationRequired, userPresenceRequired, expectedExtensionIds);
    }
}
