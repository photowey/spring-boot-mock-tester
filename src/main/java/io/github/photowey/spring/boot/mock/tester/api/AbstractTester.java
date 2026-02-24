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

import io.github.photowey.spring.boot.mock.tester.constant.ApiConstants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

/**
 * {@code AbstractTester}
 *
 * @author photowey
 * @version 1.2.0
 * @since 2026/02/24
 */
public abstract class AbstractTester
        implements HealthTester, PostTester, PutTester, PatchTester, DeleteTester, GetTester {

    @Autowired
    protected WebApplicationContext applicationContext;

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc();
        this.mockUser();
    }

    @AfterEach
    void tearDown() {

    }

    // ----------------------------------------------------------------

    public MockMvc getMockMvc() {
        return this.mockMvc;
    }

    // ----------------------------------------------------------------

    /**
     * Determines if security is enabled.
     *
     * @return true if security is enabled, false otherwise.
     */
    protected boolean securityEnabled() {
        return true;
    }

    @Deprecated
    protected void mockMvc() {
        this.initMockMvc();
    }

    @Deprecated
    protected void mockUser() {
        this.initMockUser();
    }

    // ----------------------------------------------------------------

    protected void initMockMvc() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(applicationContext);
        if (this.securityEnabled()) {
            builder.addFilter(this.springSecurityFilterChain());
        }

        this.mockMvc = builder.build();
    }

    protected void initMockUser() {

    }

    // ----------------------------------------------------------------

    /**
     * Retrieves the Spring Security filter chain.
     *
     * @return The Spring Security filter chain.
     */
    public Filter springSecurityFilterChain() {
        return this.applicationContext.getBean("springSecurityFilterChain", Filter.class);
    }

    // ---------------------------------------------------------------- Custom-area start

    @Override
    public String apiOk() {
        return ApiConstants.API_OK;
    }

    @Override
    public String okPattern() {
        return "$.code";
    }

    // ----------------------------------------------------------------

    /**
     * Default Injector configuration class.
     */
    @Configuration
    public static class Injector {

    }
}