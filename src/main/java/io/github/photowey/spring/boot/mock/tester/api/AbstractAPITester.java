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
import io.github.photowey.spring.boot.mock.tester.domain.query.DefaultQuery;
import io.github.photowey.spring.infras.common.json.JSON;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

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

    protected MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        this.mockMvc();
        this.mockUser();
    }

    @AfterEach
    void tearDown() {

    }

    /**
     * Determines if security is enabled.
     *
     * @return true if security is enabled, false otherwise.
     */
    protected boolean securityEnabled() {
        return true;
    }

    protected void mockMvc() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(applicationContext);
        if (this.securityEnabled()) {
            builder.addFilter(this.springSecurityFilterChain());
        }

        this.mockMvc = builder.build();
    }

    protected void mockUser() {

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

    protected String apiOk() {
        return ApiConstants.API_OK;
    }

    /**
     * Returns the pattern for the OK response code.
     * Response:
     * Example 1:
     * <pre>
     * {
     *   "code": "000000;",
     *   "message": "ok",
     *   "data": {
     *     "hello": "world"
     *   }
     * }
     * </pre>
     * <p>
     * Example 2:
     * <pre>
     * {
     *   "code": "000000;",
     *   "message": "ok",
     *   "data": [
     *     {
     *       "hello": "world"
     *     }
     *   ]
     * }
     * </pre>
     *
     * @return The pattern for the OK response code, default is: `$.code`
     */
    protected String okPattern() {
        return "$.code";
    }

    /**
     * Retrieves the health API endpoint.
     *
     * @return The health API endpoint.
     */
    protected String healthApi() {
        return HEALTH_API;
    }

    // ---------------------------------------------------------------- Health API

    /**
     * Tries to fetch health status information using a GET request to the health check API.
     *
     * @return A string representation of the health check API response content.
     * @throws Exception If an error occurs during the request process.
     */
    protected String tryGetHealth() throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(this.healthApi()))
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
    protected void tryHeadHealth() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(this.healthApi()))
                .andExpect(status().isOk());
    }

    // ---------------------------------------------------------------- Post

    /**
     * Executes a POST request without a request body.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doPostRequest(String route) throws Exception {
        return this.doPostRequest(null, route);
    }

    /**
     * Executes a POST request with additional operation or assertion on the result.
     *
     * @param route The target route or URL for the request.
     * @param fx    A functional interface for further operating or asserting on the request result.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doPostRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPostRequest(null, route, fx);
    }

    /**
     * Executes a POST request with customized request setup.
     *
     * @param route The path of the request.
     * @param fx    A consumer interface for modifying the request builder.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doPostRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doPostRequestB(null, route, fx);
    }

    /**
     * Executes a POST request with both customized setup and result processing.
     *
     * @param route The path of the request.
     * @param fn    A consumer interface for modifying the request builder.
     * @param fx    A consumer interface for further operating or asserting on the request result.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doPostRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doPostRequest(null, route, fn, fx);
    }

    /**
     * Executes a POST request with a payload.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The route or URL of the request.
     * @param <T>     The type of the payload.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doPostRequest(T payload, String route) throws Exception {
        return this.doPostRequest(payload, route, this::defaultPredicate);
    }

    /**
     * Executes a POST request with a payload and additional operation or assertion on the result.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The target route or URL for the request.
     * @param fx      A functional interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doPostRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPostRequest(payload, route, this::emptyBuilder, fx);
    }

    /**
     * Executes a POST request with a payload and customized request setup.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param <T>     The type of the payload.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doPostRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doPostRequest(payload, route, fn, this::defaultPredicate);
    }

    /**
     * Executes a POST request with a payload, customized setup, and result processing.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param fx      A consumer interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doPostRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = JSON.Jackson.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Put

    /**
     * Executes a PUT request without a request body.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doPutRequest(String route) throws Exception {
        return this.doPutRequest(null, route);
    }

    /**
     * Executes a PUT request with additional operation or assertion on the result.
     *
     * @param route The target route or URL for the request.
     * @param fx    A functional interface for further operating or asserting on the request result.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doPutRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPutRequest(null, route, fx);
    }

    /**
     * Executes a PUT request with customized request setup.
     *
     * @param route The path of the request.
     * @param fx    A consumer interface for modifying the request builder.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doPutRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doPutRequestB(null, route, fx);
    }

    /**
     * Executes a PUT request with both customized setup and result processing.
     *
     * @param route The path of the request.
     * @param fn    A consumer interface for modifying the request builder.
     * @param fx    A consumer interface for further operating or asserting on the request result.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doPutRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doPutRequest(null, route, fn, fx);
    }

    /**
     * Executes a PUT request with a payload.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The route or URL of the request.
     * @param <T>     The type of the payload.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doPutRequest(T payload, String route) throws Exception {
        return this.doPutRequest(payload, route, this::defaultPredicate);
    }

    /**
     * Executes a PUT request with a payload and additional operation or assertion on the result.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The target route or URL for the request.
     * @param fx      A functional interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doPutRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPutRequest(payload, route, this::emptyBuilder, fx);
    }

    /**
     * Executes a PUT request with a payload and customized request setup.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param <T>     The type of the payload.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doPutRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doPutRequest(payload, route, fn, this::defaultPredicate);
    }

    /**
     * Executes a PUT request with a payload, customized setup, and result processing.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param fx      A consumer interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doPutRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = JSON.Jackson.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Patch

    /**
     * Executes a PATCH request without a request body.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doPatchRequest(String route) throws Exception {
        return this.doPatchRequest(null, route);
    }

    /**
     * Executes a PATCH request with additional operation or assertion on the result.
     *
     * @param route The target route or URL for the request.
     * @param fx    A functional interface for further operating or asserting on the request result.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doPatchRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPatchRequest(null, route, fx);
    }

    /**
     * Executes a PATCH request with customized request setup.
     *
     * @param route The path of the request.
     * @param fx    A consumer interface for modifying the request builder.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doPatchRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doPatchRequestB(null, route, fx);
    }

    /**
     * Executes a PATCH request with both customized setup and result processing.
     *
     * @param route The path of the request.
     * @param fn    A consumer interface for modifying the request builder.
     * @param fx    A consumer interface for further operating or asserting on the request result.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doPatchRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doPatchRequest(null, route, fn, fx);
    }

    /**
     * Executes a PATCH request with a payload.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The route or URL of the request.
     * @param <T>     The type of the payload.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doPatchRequest(T payload, String route) throws Exception {
        return this.doPatchRequest(payload, route, this::defaultPredicate);
    }

    /**
     * Executes a PATCH request with a payload and additional operation or assertion on the result.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The target route or URL for the request.
     * @param fx      A functional interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doPatchRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPatchRequest(payload, route, this::emptyBuilder, fx);
    }

    /**
     * Executes a PATCH request with a payload and customized request setup.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param <T>     The type of the payload.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doPatchRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doPatchRequest(payload, route, fn, this::defaultPredicate);
    }

    /**
     * Executes a PATCH request with a payload, customized setup, and result processing.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param fx      A consumer interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doPatchRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = JSON.Jackson.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Delete

    /**
     * Executes a DELETE request without a request body.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doDeleteRequest(String route) throws Exception {
        return this.doDeleteRequest(null, route);
    }

    /**
     * Executes a DELETE request with additional operation or assertion on the result.
     *
     * @param route The target route or URL for the request.
     * @param fx    A functional interface for further operating or asserting on the request result.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doDeleteRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doDeleteRequest(null, route, fx);
    }

    /**
     * Executes a DELETE request with customized request setup.
     *
     * @param route The path of the request.
     * @param fx    A consumer interface for modifying the request builder.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doDeleteRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doDeleteRequestB(null, route, fx);
    }

    /**
     * Executes a DELETE request with both customized setup and result processing.
     *
     * @param route The path of the request.
     * @param fn    A consumer interface for modifying the request builder.
     * @param fx    A consumer interface for further operating or asserting on the request result.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doDeleteRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doDeleteRequest(null, route, fn, fx);
    }

    /**
     * Executes a DELETE request with a payload.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The route or URL of the request.
     * @param <T>     The type of the payload.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doDeleteRequest(T payload, String route) throws Exception {
        return this.doDeleteRequest(payload, route, this::defaultPredicate);
    }

    /**
     * Executes a DELETE request with a payload and additional operation or assertion on the result.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The target route or URL for the request.
     * @param fx      A functional interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doDeleteRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doDeleteRequest(payload, route, this::emptyBuilder, fx);
    }

    /**
     * Executes a DELETE request with a payload and customized request setup.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param <T>     The type of the payload.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <T> String doDeleteRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doDeleteRequest(payload, route, fn, this::defaultPredicate);
    }

    /**
     * Executes a DELETE request with a payload, customized setup, and result processing.
     *
     * @param payload The payload to be sent with the request.
     * @param route   The path of the request.
     * @param fn      A consumer interface for modifying the request builder.
     * @param fx      A consumer interface for further operating or asserting on the request result.
     * @param <T>     The type of the payload.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <T> String doDeleteRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = JSON.Jackson.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Get

    /**
     * Executes a GET request without query parameters.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doGetRequest(String route) throws Exception {
        return this.doGetRequest(null, route);
    }

    /**
     * Executes a GET request with additional operation or assertion on the result.
     *
     * @param route The target route or URL for the request.
     * @param fx    A functional interface for further operating or asserting on the request result.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected String doGetRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doGetRequest(null, route, fx);
    }

    /**
     * Executes a GET request with customized request setup.
     *
     * @param route The path of the request.
     * @param fx    A consumer interface for modifying the request builder.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String doGetRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doGetRequestB(null, route, fx);
    }

    /**
     * Executes a GET request with both customized setup and result processing.
     *
     * @param route The path of the request.
     * @param fn    A {@link Consumer<MockHttpServletRequestBuilder>} that consumes and configures the request builder object.
     * @param fx    A consumer interface for further operating or asserting on the request result.
     * @return A string representation of the response received from the request.
     * @throws Exception If an error occurs during the course of the request, an exception is thrown.
     */
    protected String doGetRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doGetRequest(null, route, fn, fx);
    }

    /**
     * Executes a GET request with query parameters.
     *
     * @param query The query parameters to be included in the request.
     * @param route The target route or URL for the request.
     * @param <Q>   The type of the query parameters.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <Q> String doGetRequest(Q query, String route) throws Exception {
        return this.doGetRequest(query, route, this::emptyBuilder, this::defaultPredicate);
    }

    /**
     * Executes a GET request with query parameters and additional operation or assertion on the result.
     *
     * @param query The query parameters to be included in the request.
     * @param route The target route or URL for the request.
     * @param fx    A functional interface for further operating or asserting on the request result.
     * @param <Q>   The type of the query parameters.
     * @return The result after calling the overloaded method internally.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <Q> String doGetRequest(Q query, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doGetRequest(query, route, this::emptyBuilder, fx);
    }

    /**
     * Executes a GET request with query parameters and customized request setup.
     *
     * @param query The query parameters to be included in the request.
     * @param route The path of the request.
     * @param fx    A consumer interface for modifying the request builder.
     * @param <Q>   The type of the query parameters.
     * @return A string representation of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected <Q> String doGetRequestB(Q query, String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doGetRequest(query, route, fx, this::defaultPredicate);
    }

    /**
     * Executes a GET request with query parameters, customized setup, and result processing.
     *
     * @param query The query parameters to be included in the request.
     * @param route The target route or URL for the request.
     * @param fn    A consumer interface for modifying the request builder.
     * @param fx    A consumer interface for further operating or asserting on the request result.
     * @param <Q>   The type of the query parameters.
     * @return A string representing the response result of the request.
     * @throws Exception If an error occurs during the request process or while processing the result.
     */
    protected <Q> String doGetRequest(
            Q query,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(route)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        if (isNotEmpty(query)) {
            MultiValueMap<String, String> params = this.getMultiValueMap(query);
            builder.queryParams(params);
        }

        fn.accept(builder);

        return this.execute(builder, fx);
    }

    // ---------------------------------------------------------------- Exec

    /**
     * Executes an HTTP request using MockMvc framework and performs additional operations or assertions on the result.
     *
     * @param requestBuilder The request builder object representing the HTTP request to be executed.
     * @param fx             A consumer interface for further operating or asserting on the request result.
     * @return A string representing the content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    protected String execute(
            MockHttpServletRequestBuilder requestBuilder,
            Consumer<ResultActions> fx) throws Exception {

        ResultActions actions = this.mockMvc.perform(requestBuilder).andExpect(status().isOk());
        fx.accept(actions);

        String content = actions.andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return content;
    }

    // ---------------------------------------------------------------- Query

    /**
     * Generates a MultiValueMap of query parameters from the provided query object.
     *
     * @param query The query object from which the query parameters are extracted.
     * @param <Q>   The type of the query object.
     * @return A MultiValueMap containing the extracted query parameters.
     * @throws IllegalAccessException If access to a field is denied by the security manager.
     */
    private <Q> MultiValueMap<String, String> getMultiValueMap(Q query) throws IllegalAccessException {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (null == query || query instanceof DefaultQuery) {
            return params;
        }

        Class<?> clazz = query.getClass();
        while (Object.class != clazz) {
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (Modifier.isStatic(declaredField.getModifiers())) {
                    continue;
                }

                ReflectionUtils.makeAccessible(declaredField);
                Object v = declaredField.get(query);
                if (isNotEmpty(v)) {
                    params.put(declaredField.getName(), Lists.newArrayList(String.valueOf(v)));
                }
            }

            clazz = clazz.getSuperclass();
        }

        return params;
    }

    // ----------------------------------------------------------------

    /**
     * Empty builder method.
     *
     * @param builder The builder object.
     */
    public void emptyBuilder(MockHttpServletRequestBuilder builder) {}

    /**
     * Default predicate for result actions.
     *
     * @param actions The result actions object.
     */
    public void defaultPredicate(ResultActions actions) {
        try {
            actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    /**
     * Default Injector configuration class.
     */
    @Configuration
    public static class Injector {

    }

    /**
     * Checks if the given target is not empty.
     *
     * @param target The target object to check.
     * @param <T>    The type of the target object.
     * @return true if the target is not empty, false otherwise.
     */
    protected static <T> boolean isNotEmpty(T target) {
        return !ObjectUtils.isEmpty(target);
    }
}