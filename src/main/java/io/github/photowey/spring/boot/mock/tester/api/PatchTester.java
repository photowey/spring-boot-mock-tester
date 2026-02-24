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

import io.github.photowey.spring.boot.mock.tester.function.ThrowableConsumer;
import io.github.photowey.spring.infras.common.json.JSON;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.function.Consumer;

/**
 * {@code PatchTester}
 *
 * @author photowey
 * @version 1.2.0
 * @since 2026/02/24
 */
public interface PatchTester extends APITester {

    /**
     * Executes a PATCH request without a request body.
     *
     * @param route The route or URL of the request.
     * @return The content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    default String doPatchRequest(String route) throws Exception {
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
    default String doPatchRequest(String route, ThrowableConsumer<ResultActions> fx) throws Exception {
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
    default String doPatchRequestB(String route, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
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
    default String doPatchRequest(
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            ThrowableConsumer<ResultActions> fx) throws Exception {
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
    default <T> String doPatchRequest(T payload, String route) throws Exception {
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
    default <T> String doPatchRequest(
            T payload,
            String route,
            ThrowableConsumer<ResultActions> fx) throws Exception {
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
    default <T> String doPatchRequestB(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
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
    default <T> String doPatchRequest(
            T payload,
            String route,
            Consumer<MockHttpServletRequestBuilder> fn,
            ThrowableConsumer<ResultActions> fx) throws Exception {

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
}
