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

import io.github.photowey.spring.boot.mock.tester.App;
import io.github.photowey.spring.boot.mock.tester.LocalTest;
import io.github.photowey.spring.boot.mock.tester.core.domain.payload.HelloPayload;
import io.github.photowey.spring.boot.mock.tester.core.domain.query.HelloQuery;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.function.Consumer;

/**
 * {@code ApiTest}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

    @Test
    void testHealthGet() throws Exception {
        super.tryGetHealth();
    }

    @Test
    void testHealthHead() throws Exception {
        super.tryHeadHealth();
    }

    @Test
    void testGet() throws Exception {
        HelloQuery query = new HelloQuery("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.get(query, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello get.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPost() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.post(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello post.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPut() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.put(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello put.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPatch() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.patch(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello patch.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testDelete() throws Exception {
        Long userId = 1711120980000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.delete(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello delete.1711120980000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void get(
            HelloQuery query,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doGetRequest(query, METHODS_BASE_API + "/get", fn, fx);
    }

    private void post(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doPostRequest(payload, METHODS_BASE_API + "/post", fn, fx);
    }

    private void put(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doPutRequest(payload, METHODS_BASE_API + "/put", fn, fx);
    }

    private void patch(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doPatchRequest(payload, METHODS_BASE_API + "/patch", fn, fx);
    }

    private void delete(
            Long userId,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doDeleteRequest(null, METHODS_BASE_API + "/delete/" + userId, fn, fx);
    }
}