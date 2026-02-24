/*
 * Copyright © 2024 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.photowey.spring.boot.mock.tester.api;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code HealthTester}
 *
 * @author photowey
 * @version 1.2.0
 * @since 2026/02/24
 */
public interface HealthTester extends APITester {

    /**
     * Customized health check api.
     * <p>
     * e.g.:
     * ```http
     * $ curl -X GET "http://localhost:8080/healthz"
     * ```
     */
    String HEALTH_API = "/healthz";

    /**
     * Retrieves the health API endpoint.
     *
     * @return The health API endpoint.
     */
    default String healthApi() {
        return HEALTH_API;
    }

    // ---------------------------------------------------------------- Health API

    /**
     * Tries to fetch health status information using a GET request to the health check API.
     *
     * @return A string representation of the health check API response content.
     * @throws Exception If an error occurs during the request process.
     */
    default String tryGetHealth() throws Exception {
        return this.getMockMvc().perform(MockMvcRequestBuilders.get(this.healthApi()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    /**
     * Attempts to verify the health status of the API by sending a HEAD request.
     *
     * @throws Exception If an error occurs during the request process or if the returned status is not OK (200).
     */
    default void tryHeadHealth() throws Exception {
        this.getMockMvc().perform(MockMvcRequestBuilders.head(this.healthApi()))
                .andExpect(status().isOk());
    }
}
