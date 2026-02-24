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

import io.github.photowey.spring.boot.mock.tester.domain.query.DefaultQuery;
import io.github.photowey.spring.boot.mock.tester.function.ThrowableConsumer;
import org.assertj.core.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

/**
 * {@code GetTester}
 *
 * @author photowey
 * @version 1.2.0
 * @since 2026/02/24
 */
public interface GetTester extends APITester {

    /**
     * Executes a GET request without query parameters.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    default String doGetRequest(String route) throws Exception {
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
    default String doGetRequest(String route, ThrowableConsumer<ResultActions> fx) throws Exception {
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
    default String doGetRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
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
    default String doGetRequest(
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            ThrowableConsumer<ResultActions> fx) throws Exception {
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
    default <Q> String doGetRequest(Q query, String route) throws Exception {
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
    default <Q> String doGetRequest(Q query, String route, ThrowableConsumer<ResultActions> fx) throws Exception {
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
    default <Q> String doGetRequestB(
            Q query,
            String route,
            Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
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
    default <Q> String doGetRequest(
            Q query,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            ThrowableConsumer<ResultActions> fx) throws Exception {

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

    // ---------------------------------------------------------------- Query

    /**
     * Generates a MultiValueMap of query parameters from the provided query object.
     *
     * @param query The query object from which the query parameters are extracted.
     * @param <Q>   The type of the query object.
     * @return A MultiValueMap containing the extracted query parameters.
     * @throws IllegalAccessException If access to a field is denied by the security manager.
     */
    default <Q> MultiValueMap<String, String> getMultiValueMap(Q query) throws IllegalAccessException {
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
}
