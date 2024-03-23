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

    // ----------------------------------------------------------------

    @Test
    void testGet_1() throws Exception {
        this.get_1("photowey");
    }

    @Test
    void testGet_2() throws Exception {
        this.get_2("photowey", (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello get.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testGet_3() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.get_3("photowey", (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testGet_4() throws Exception {
        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.get_4("photowey", (builder) -> {
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
    void testGet_5() throws Exception {
        HelloQuery query = new HelloQuery("photowey");

        this.get_5(query);
    }

    @Test
    void testGet_6() throws Exception {
        HelloQuery query = new HelloQuery("photowey");

        this.get_6(query, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello get.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testGet_7() throws Exception {
        HelloQuery query = new HelloQuery("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.get_7(query, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testGet_8() throws Exception {
        HelloQuery query = new HelloQuery("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.get_8(query, (builder) -> {
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

    // ---------------------------------------------------------------- Post

    @Test
    void testPost_1() throws Exception {
        Long userId = 1711185600000L;
        this.post_1(userId);
    }

    @Test
    void testPost_2() throws Exception {
        Long userId = 1711185600000L;
        this.post_2(userId, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello post.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPost_3() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.post_3(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testPost_4() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.post_4(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello post.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPost_5() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.post_5(payload);
    }

    @Test
    void testPost_6() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.post_6(payload, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello post.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPost_7() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.post_7(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testPost_8() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.post_8(payload, (builder) -> {
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

    // ---------------------------------------------------------------- Put

    @Test
    void testPut_1() throws Exception {
        Long userId = 1711185600000L;
        this.put_1(userId);
    }

    @Test
    void testPut_2() throws Exception {
        Long userId = 1711185600000L;
        this.put_2(userId, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello put.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPut_3() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.put_3(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testPut_4() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.put_4(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello put.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPut_5() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.put_5(payload);
    }

    @Test
    void testPut_6() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.put_6(payload, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello put.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPut_7() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.put_7(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testPut_8() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.put_8(payload, (builder) -> {
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

    // ---------------------------------------------------------------- Patch

    @Test
    void testPatch_1() throws Exception {
        Long userId = 1711185600000L;
        this.patch_1(userId);
    }

    @Test
    void testPatch_2() throws Exception {
        Long userId = 1711185600000L;
        this.patch_2(userId, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello patch.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPatch_3() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.patch_3(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testPatch_4() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.patch_4(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello patch.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPatch_5() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.put_5(payload);
    }

    @Test
    void testPatch_6() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.patch_6(payload, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello patch.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testPatch_7() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.patch_7(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testPatch_8() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.patch_8(payload, (builder) -> {
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

    // ----------------------------------------------------------------

    @Test
    void testDelete_1() throws Exception {
        Long userId = 1711185600000L;
        this.delete_1(userId);
    }

    @Test
    void testDelete_2() throws Exception {
        Long userId = 1711185600000L;
        this.delete_2(userId, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello delete.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testDelete_3() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.delete_3(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testDelete_4() throws Exception {
        Long userId = 1711185600000L;

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.delete_4(userId, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello delete.empty.1711185600000"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testDelete_5() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.delete_5(payload);
    }

    @Test
    void testDelete_6() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        this.delete_6(payload, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello delete.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testDelete_7() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.delete_7(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        });
    }

    @Test
    void testDelete_8() throws Exception {
        HelloPayload payload = new HelloPayload("photowey");

        String token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

        this.delete_8(payload, (builder) -> {
            builder.header("Tenant", "web");
            builder.header("Authorization", token);
        }, (actions) -> {
            try {
                actions.andExpect(MockMvcResultMatchers.jsonPath(this.okPattern()).value(this.apiOk()))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.data.greeting").value("Hello delete.photowey"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    // ----------------------------------------------------------------

    private void get_1(String name) throws Exception {
        this.doGetRequest(METHODS_BASE_API + "/get?name=" + name);
    }

    private void get_2(String name, Consumer<ResultActions> fx) throws Exception {
        this.doGetRequest(METHODS_BASE_API + "/get?name=" + name, fx);
    }

    private void get_3(String name, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        this.doGetRequestB(METHODS_BASE_API + "/get?name=" + name, fx);
    }

    private void get_4(String name, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        this.doGetRequest(METHODS_BASE_API + "/get?name=" + name, fn, fx);
    }


    private void get_5(HelloQuery query) throws Exception {
        this.doGetRequest(query, METHODS_BASE_API + "/get");
    }

    private void get_6(HelloQuery query, Consumer<ResultActions> fx) throws Exception {
        this.doGetRequest(query, METHODS_BASE_API + "/get", fx);
    }

    private void get_7(HelloQuery query, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        this.doGetRequestB(query, METHODS_BASE_API + "/get", fx);
    }

    private void get_8(HelloQuery query, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        this.doGetRequest(query, METHODS_BASE_API + "/get", fn, fx);
    }

    // ----------------------------------------------------------------

    private void post_1(Long userId) throws Exception {
        this.doPostRequest(METHODS_BASE_API + "/post/empty/" + userId);
    }

    private void post_2(Long userId, Consumer<ResultActions> fx) throws Exception {
        this.doPostRequest(METHODS_BASE_API + "/post/empty/" + userId, fx);
    }

    private void post_3(Long userId, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        this.doPostRequestB(METHODS_BASE_API + "/post/empty/" + userId, fx);
    }

    private void post_4(Long userId, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        this.doPostRequest(METHODS_BASE_API + "/post/empty/" + userId, fn, fx);
    }

    private void post_5(HelloPayload payload) throws Exception {
        this.doPostRequest(payload, METHODS_BASE_API + "/post");
    }

    private void post_6(HelloPayload payload, Consumer<ResultActions> fx) throws Exception {
        this.doPostRequest(payload, METHODS_BASE_API + "/post", fx);
    }

    private void post_7(HelloPayload payload, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        this.doPostRequestB(payload, METHODS_BASE_API + "/post", fn);
    }

    private void post_8(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doPostRequest(payload, METHODS_BASE_API + "/post", fn, fx);
    }

    // ---------------------------------------------------------------- Put

    private void put_1(Long userId) throws Exception {
        this.doPutRequest(METHODS_BASE_API + "/put/empty/" + userId);
    }

    private void put_2(Long userId, Consumer<ResultActions> fx) throws Exception {
        this.doPutRequest(METHODS_BASE_API + "/put/empty/" + userId, fx);
    }

    private void put_3(Long userId, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        this.doPutRequestB(METHODS_BASE_API + "/put/empty/" + userId, fx);
    }

    private void put_4(Long userId, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        this.doPutRequest(METHODS_BASE_API + "/put/empty/" + userId, fn, fx);
    }

    private void put_5(HelloPayload payload) throws Exception {
        this.doPutRequest(payload, METHODS_BASE_API + "/put");
    }

    private void put_6(HelloPayload payload, Consumer<ResultActions> fx) throws Exception {
        this.doPutRequest(payload, METHODS_BASE_API + "/put", fx);
    }

    private void put_7(HelloPayload payload, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        this.doPutRequestB(payload, METHODS_BASE_API + "/put", fn);
    }

    private void put_8(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doPutRequest(payload, METHODS_BASE_API + "/put", fn, fx);
    }

    // ---------------------------------------------------------------- Patch

    private void patch_1(Long userId) throws Exception {
        this.doPatchRequest(METHODS_BASE_API + "/patch/empty/" + userId);
    }

    private void patch_2(Long userId, Consumer<ResultActions> fx) throws Exception {
        this.doPatchRequest(METHODS_BASE_API + "/patch/empty/" + userId, fx);
    }

    private void patch_3(Long userId, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        this.doPatchRequestB(METHODS_BASE_API + "/patch/empty/" + userId, fx);
    }

    private void patch_4(Long userId, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        this.doPatchRequest(METHODS_BASE_API + "/patch/empty/" + userId, fn, fx);
    }

    private void patch_5(HelloPayload payload) throws Exception {
        this.doPatchRequest(payload, METHODS_BASE_API + "/patch");
    }

    private void patch_6(HelloPayload payload, Consumer<ResultActions> fx) throws Exception {
        this.doPatchRequest(payload, METHODS_BASE_API + "/patch", fx);
    }

    private void patch_7(HelloPayload payload, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        this.doPatchRequestB(payload, METHODS_BASE_API + "/patch", fn);
    }

    private void patch_8(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doPatchRequest(payload, METHODS_BASE_API + "/patch", fn, fx);
    }

    // ---------------------------------------------------------------- Delete

    private void delete_1(Long userId) throws Exception {
        this.doDeleteRequest(METHODS_BASE_API + "/delete/empty/" + userId);
    }

    private void delete_2(Long userId, Consumer<ResultActions> fx) throws Exception {
        this.doDeleteRequest(METHODS_BASE_API + "/delete/empty/" + userId, fx);
    }

    private void delete_3(Long userId, Consumer<MockHttpServletRequestBuilder> fx) throws Exception {
        this.doDeleteRequestB(METHODS_BASE_API + "/delete/empty/" + userId, fx);
    }

    private void delete_4(Long userId, Consumer<MockHttpServletRequestBuilder> fn, Consumer<ResultActions> fx) throws Exception {
        this.doDeleteRequest(METHODS_BASE_API + "/delete/empty/" + userId, fn, fx);
    }

    private void delete_5(HelloPayload payload) throws Exception {
        this.doDeleteRequest(payload, METHODS_BASE_API + "/delete");
    }

    private void delete_6(HelloPayload payload, Consumer<ResultActions> fx) throws Exception {
        this.doDeleteRequest(payload, METHODS_BASE_API + "/delete", fx);
    }

    private void delete_7(HelloPayload payload, Consumer<MockHttpServletRequestBuilder> fn) throws Exception {
        this.doDeleteRequestB(payload, METHODS_BASE_API + "/delete", fn);
    }

    private void delete_8(
            HelloPayload payload,
            Consumer<MockHttpServletRequestBuilder> fn,
            Consumer<ResultActions> fx) throws Exception {
        this.doDeleteRequest(payload, METHODS_BASE_API + "/delete", fn, fx);
    }
}