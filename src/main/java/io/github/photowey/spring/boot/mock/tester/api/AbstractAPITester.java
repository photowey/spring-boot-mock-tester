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
import io.github.photowey.spring.boot.mock.tester.json.Jsoner;
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

    public Filter springSecurityFilterChain() {
        return this.applicationContext.getBean("springSecurityFilterChain", Filter.class);
    }

    // ---------------------------------------------------------------- Custom-area start

    protected String apiOk() {
        return ApiConstants.API_OK;
    }

    /**
     * Response data:
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
     * @return the code pattern of response data, default is: `$.code`
     */
    protected String okPattern() {
        return "$.code";
    }

    protected String healthApi() {
        return HEALTH_API;
    }

    // ---------------------------------------------------------------- Health API

    protected String tryGetHealth() throws Exception {
        return this.mockMvc.perform(MockMvcRequestBuilders.get(this.healthApi()))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    protected void tryHeadHealth() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.head(this.healthApi()))
                .andExpect(status().isOk());
    }

    // ---------------------------------------------------------------- Post

    protected String doPostRequest(String route) throws Exception {
        return this.doPostRequest(null, route);
    }

    protected String doPostRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPostRequest(null, route, fx);
    }

    protected <T> String doPostRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doPostRequestB(null, route, fx);
    }

    protected <T> String doPostRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doPostRequest(null, route, fn, fx);
    }

    protected <T> String doPostRequest(T payload, String route) throws Exception {
        return this.doPostRequest(payload, route, this::defaultPredicate);
    }

    protected <T> String doPostRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPostRequest(payload, route, this::emptyBuilder, fx);
    }

    protected <T> String doPostRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doPostRequest(payload, route, fn, this::defaultPredicate);
    }

    protected <T> String doPostRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = Jsoner.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Put

    protected String doPutRequest(String route) throws Exception {
        return this.doPutRequest(null, route);
    }

    protected String doPutRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPutRequest(null, route, fx);
    }

    protected <T> String doPutRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doPutRequestB(null, route, fx);
    }

    protected <T> String doPutRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doPutRequest(null, route, fn, fx);
    }

    protected <T> String doPutRequest(T payload, String route) throws Exception {
        return this.doPutRequest(payload, route, this::defaultPredicate);
    }

    protected <T> String doPutRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPutRequest(payload, route, this::emptyBuilder, fx);
    }

    protected <T> String doPutRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doPutRequest(payload, route, fn, this::defaultPredicate);
    }

    protected <T> String doPutRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = Jsoner.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Patch

    protected String doPatchRequest(String route) throws Exception {
        return this.doPatchRequest(null, route);
    }

    protected String doPatchRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPatchRequest(null, route, fx);
    }

    protected <T> String doPatchRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doPatchRequestB(null, route, fx);
    }

    protected <T> String doPatchRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doPatchRequest(null, route, fn, fx);
    }

    protected <T> String doPatchRequest(T payload, String route) throws Exception {
        return this.doPatchRequest(payload, route, this::defaultPredicate);
    }

    protected <T> String doPatchRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doPatchRequest(payload, route, this::emptyBuilder, fx);
    }

    protected <T> String doPatchRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doPatchRequest(payload, route, fn, this::defaultPredicate);
    }

    protected <T> String doPatchRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .patch(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = Jsoner.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Delete

    protected String doDeleteRequest(String route) throws Exception {
        return this.doDeleteRequest(null, route);
    }

    protected String doDeleteRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doDeleteRequest(null, route, fx);
    }

    protected <T> String doDeleteRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doDeleteRequestB(null, route, fx);
    }

    protected <T> String doDeleteRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doDeleteRequest(null, route, fn, fx);
    }

    protected <T> String doDeleteRequest(T payload, String route) throws Exception {
        return this.doDeleteRequest(payload, route, this::defaultPredicate);
    }

    protected <T> String doDeleteRequest(T payload, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doDeleteRequest(payload, route, this::emptyBuilder, fx);
    }

    protected <T> String doDeleteRequestB(T payload, String route, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        return this.doDeleteRequest(payload, route, fn, this::defaultPredicate);
    }

    protected <T> String doDeleteRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete(route)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        if (isNotEmpty(payload)) {
            String body = Jsoner.toJSONString(payload);
            requestBuilder.content(body);
        }

        fn.accept(requestBuilder);

        return this.execute(requestBuilder, fx);
    }

    // ---------------------------------------------------------------- Get

    protected String doGetRequest(String route) throws Exception {
        return this.doGetRequest(null, route);
    }

    protected String doGetRequest(String route, Consumer<ResultActions> fx) throws Exception {
        return this.doGetRequest(null, route, fx);
    }

    protected String doGetRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doGetRequestB(null, route, fx);
    }

    protected String doGetRequest(String route, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        return this.doGetRequest(null, route, fn, fx);
    }

    protected <Q> String doGetRequest(Q query, String route) throws Exception {
        return this.doGetRequest(query, route, this::emptyBuilder, this::defaultPredicate);
    }

    protected <Q> String doGetRequest(Q query, String route, Consumer<ResultActions> fx) throws Exception {
        return this.doGetRequest(query, route, this::emptyBuilder, fx);
    }

    protected <Q> String doGetRequestB(Q query, String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        return this.doGetRequest(query, route, fx, this::defaultPredicate);
    }

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

    protected <T> String execute(
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

    public void emptyBuilder(MockHttpServletRequestBuilder builder) {}

    public void defaultPredicate(ResultActions actions) {
        try {
            actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    @Configuration
    public static class Injector {

    }

    protected static <T> boolean isNotEmpty(T target) {
        return !ObjectUtils.isEmpty(target);
    }
}