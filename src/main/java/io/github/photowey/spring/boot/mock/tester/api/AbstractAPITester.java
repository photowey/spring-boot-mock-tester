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

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code AbstractAPITester}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
public abstract class AbstractAPITester {

    /**
     * Customized health check api.
     * <p>
     * e.g.:
     * ```http
     * $ curl -X GET "http://localhost:8080/healthz"
     * ```
     */
    public static final String HEALTH_API = "/healthz";

    @Autowired
    protected WebApplicationContext applicationContext;

    @Autowired
    private Filter springSecurityFilterChain;

    protected MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        this.mockMvc();
        this.mockUser();
    }

    @AfterEach
    void tearDown() {

    }

    protected void mockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    protected void mockUser() {

    }

    // -----------------------------------------------------------------------------------------------------------------

    protected void doHealthz() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get(HEALTH_API))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    // -----------------------------------------------------------------------------------------------------------------

    @Configuration
    public static class Injector {

    }
}