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

package com.webauthn4j.converter.jackson.deserializer;

import com.webauthn4j.converter.util.JsonConverter;
import com.webauthn4j.response.attestation.statement.AttestationStatement;
import com.webauthn4j.response.attestation.statement.FIDOU2FAttestationStatement;
import com.webauthn4j.test.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttestationStatementDeserializerTest {

    private JsonConverter jsonConverter = new JsonConverter();

    @Test
    void test() {
        AttestationStatement source = TestUtil.createFIDOU2FAttestationStatement();
        String str = jsonConverter.writeValueAsString(source);
        AttestationStatement obj = jsonConverter.readValue(str, AttestationStatement.class);

        assertAll(
                () -> assertThat(obj).isInstanceOf(FIDOU2FAttestationStatement.class),
                () -> assertThat(obj).isEqualTo(source)
        );
    }
}
