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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * {@code APITester}
 *
 * @author photowey
 * @version 1.2.0
 * @since 2026/02/24
 */
public interface APITester {

    MockMvc getMockMvc();

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
    String okPattern();

    String apiOk();

    // ----------------------------------------------------------------

    /**
     * Executes an HTTP request using MockMvc framework and performs additional operations or assertions on the result.
     *
     * @param requestBuilder The request builder object representing the HTTP request to be executed.
     * @param fx             A consumer interface for further operating or asserting on the request result.
     * @return A string representing the content of the response.
     * @throws Exception If an error occurs during the request process.
     */
    default String execute(
            MockHttpServletRequestBuilder requestBuilder,
            ThrowableConsumer<ResultActions> fx) throws Exception {

        ResultActions actions = this.getMockMvc().perform(requestBuilder).andExpect(status().isOk());
        fx.accept(actions);

        String content = actions.andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        return content;
    }

    // ----------------------------------------------------------------

    /**
     * Empty builder method.
     *
     * @param builder The builder object.
     */
    default void emptyBuilder(MockHttpServletRequestBuilder builder) {
    }

    /**
     * Default predicate for result actions.
     *
     * @param actions The result actions object.
     */
    default void defaultPredicate(ResultActions actions) {
        try {
            actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ----------------------------------------------------------------

    /**
     * Checks if the given target is not empty.
     *
     * @param target The target object to check.
     * @param <T>    The type of the target object.
     * @return true if the target is not empty, false otherwise.
     */
    default <T> boolean isNotEmpty(T target) {
        return !ObjectUtils.isEmpty(target);
    }
}
