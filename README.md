# `spring-boot-mock-tester`

It is a Spring Boot Web `API` unit testing tool set implemented by `MockMvc`.

## 1.`Usage`

Add this to your `pom.xml`

```xml
<!-- ${spring-boot-mock-tester.version} == ${latest.version} -->
<dependency>
    <groupId>io.github.photowey</groupId>
    <artifactId>spring-boot-mock-tester</artifactId>
    <version>${spring-boot-mock-tester.version}</version>
    <scope>test</scope>
</dependency>

<!-- If necessary -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```



## 2.`APIs`

### 2.1.`Parent`

```java
public abstract class LocalTest extends AbstractAPITester {

    // The mock IOC security enabled?
    @Override
    protected boolean securityEnabled() {
        // The servlet Filter named "springSecurityFilterChain". 
        return true;
    }

    @Override
    protected String apiOk() {
        // Default: 000000
        return "200";
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
     */
    @Override
    protected String okPattern() {
        return "$.code";
    }

    // Health check api.
    @Override
    protected String healthApi() {
        return "/healthz";
    }
}
```



### 2.2.`API`

#### 2.2.1.`Health check`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

    @Test
    void testHealthGet() throws Exception {
        // GET
        super.tryGetHealth();
    }

    @Test
    void testHealthHead() throws Exception {
        // HEAD
        super.tryHeadHealth();
    }
}
```



#### 2.2.2.`Get`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

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
}
```



#### 2.2.3.`Post`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

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
}
```



#### 2.2.4.`Put`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

    // Usage is similar to POST method
}
```



#### 2.2.5.`Patch`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

    // Usage is similar to POST method
}
```



#### 2.2.6.`Delete`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

    // Usage is similar to POST method
}
```



### 2.3.`Methods`

```java
@SpringBootTest(classes = App.class)
class ApiTest extends LocalTest {

    private static final String METHODS_BASE_API = "/api/v1";

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
```



## 3.`Controller`

### 3.1.`Health`

```java
@RestController
public class HealthController {

    /**
     * Health check.
     * http://localhost:7923/healthz
     *
     * @return {@link StatusDTO}
     */
    @GetMapping("/healthz")
    public ResponseEntity<StatusDTO> get() {
        return new ResponseEntity<>(StatusDTO.up(), HttpStatus.OK);
    }

    @RequestMapping(value = "/healthz", method = RequestMethod.HEAD)
    public void head() {

    }
}

```



### 3.2.`Api`

```java
@RestController
@RequestMapping("/api/v1")
public class ApiController {

    /**
     * GET :/get
     * <p>
     * curl -X GET "http://localhost:7923/api/v1/get?name=photowey"
     *
     * @param query {@link HelloQuery}
     * @return {@link GreetingDTO}
     */
    @GetMapping("/get")
    public ApiResult<GreetingDTO> get(HelloQuery query) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello get.%s", query.getName())));
    }

    /**
     * POST :/post
     * <p>
     * curl -X POST -H "Content-Type:application/json" -d '{"name":"photowey"}' http://localhost:7923/api/v1/post
     *
     * @param payload {@link HelloPayload}
     * @return {@link GreetingDTO}
     */
    @PostMapping("/post")
    public ApiResult<GreetingDTO> post(@RequestBody HelloPayload payload) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello post.%s", payload.getName())));
    }

    /**
     * POST :/post/empty/{id}
     * <p>
     * curl -X POST http://localhost:7923/api/v1/post/empty/1711185600000
     *
     * @param userId {@code userId}
     * @return {@link GreetingDTO}
     */
    @PostMapping("/post/empty/{userId}")
    public ApiResult<GreetingDTO> postEmpty(@PathVariable("userId") Long userId) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello post.empty.%s", userId)));
    }

    /**
     * PUT :/put
     * <p>
     * curl -X PUT -H "Content-Type:application/json" -d '{"name":"photowey"}' http://localhost:7923/api/v1/put
     *
     * @param payload {@link HelloPayload}
     * @return {@link GreetingDTO}
     */
    @PutMapping("/put")
    public ApiResult<GreetingDTO> put(@RequestBody HelloPayload payload) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello put.%s", payload.getName())));
    }

    /**
     * PUT :/put/empty/{id}
     * <p>
     * curl -X PUT http://localhost:7923/api/v1/post/empty/1711185600000
     *
     * @param userId {@code userId}
     * @return {@link GreetingDTO}
     */
    @PutMapping("/put/empty/{userId}")
    public ApiResult<GreetingDTO> putEmpty(@PathVariable("userId") Long userId) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello put.empty.%s", userId)));
    }

    /**
     * PATCH :/patch
     * <p>
     * curl -X PATCH -H "Content-Type:application/json" -d '{"name":"photowey"}' http://localhost:7923/api/v1/patch
     *
     * @param payload {@link HelloPayload}
     * @return {@link GreetingDTO}
     */
    @PatchMapping("/patch")
    public ApiResult<GreetingDTO> patch(@RequestBody HelloPayload payload) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello patch.%s", payload.getName())));
    }

    /**
     * PATCH :/patch/empty/{id}
     * <p>
     * curl -X PATCH http://localhost:7923/api/v1/post/empty/1711185600000
     *
     * @param userId {@code userId}
     * @return {@link GreetingDTO}
     */
    @PatchMapping("/patch/empty/{userId}")
    public ApiResult<GreetingDTO> patchEmpty(@PathVariable("userId") Long userId) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello patch.empty.%s", userId)));
    }

    /**
     * DELETE :/delete
     * <p>
     * curl -X DELETE -H "Content-Type:application/json" -d '{"name":"photowey"}' http://localhost:7923/api/v1/delete
     *
     * @param payload {@link  HelloPayload}
     * @return {@link GreetingDTO}
     */
    @DeleteMapping("/delete")
    public ApiResult<GreetingDTO> delete(@RequestBody HelloPayload payload) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello delete.%s", payload.getName())));
    }

    /**
     * DELETE :/delete/empty/{userId}
     * <p>
     * curl -X DELETE http://localhost:7923/api/v1/delete/empty/1711120980000
     *
     * @param userId {@code userId}
     * @return {@link GreetingDTO}
     */
    @DeleteMapping("/delete/empty/{userId}")
    public ApiResult<GreetingDTO> deleteEmpty(@PathVariable("userId") Long userId) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello delete.empty.%d", userId)));
    }
}
```



## 4.`Security`

### 4.1.`Configuration`

```java
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfigure {

    @Bean
    public SecurityFilterChain authorizationFilterChain(HttpSecurity http) throws Exception {

        this.preBuild(http);
        SecurityFilterChain chain = http.build();
        this.postBuild(chain);

        return chain;
    }

    private void preBuild(HttpSecurity http) throws Exception {
        this.configure(http);
    }

    private void configure(HttpSecurity http) throws Exception {
        String[] ignorePaths = this.determineIgnorePaths();

        // @formatter:off
        http
            .csrf()
                .disable()
                .exceptionHandling()
            .and()
                .headers()
                .frameOptions()
                .disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.HEAD, "/**").permitAll()
                .antMatchers(ignorePaths).permitAll()
                .anyRequest().authenticated();
        // @formatter:on
    }

    private String[] determineIgnorePaths() {
        return new String[]{"/healthz", "/api/v1/**"};
    }

    private void postBuild(SecurityFilterChain chain) {

    }
}
```



## 5.`Model`

### 5.1.`Result`

```java
public class ApiResult<T> implements Serializable {

    private String code;
    private String message;

    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> ApiResult<T> ok(T data) {
        return new ApiResult<T>() {{
            setCode("200");
            setMessage("OK");
            setData(data);
        }};
    }
}
```



### 5.2.`DTO`

#### 5.2.1.`Status`

```java
public class StatusDTO implements Serializable {

    private static final long serialVersionUID = 8701760868946842501L;

    private String status;

    public StatusDTO() {

    }

    public StatusDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static StatusDTO up() {
        return new StatusDTO("UP");
    }
}

```



#### 5.2.2.`Greeting`

```java
public class GreetingDTO implements Serializable {

    private static final long serialVersionUID = -1833768222412496576L;

    private String greeting;

    public GreetingDTO() {
    }

    public GreetingDTO(String greeting) {
        this.greeting = greeting;
    }

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
```



### 5.3.`Payload`

```java
public class HelloPayload implements Serializable {

    private static final long serialVersionUID = -1833768222412496576L;

    private String name;

    public HelloPayload() {
    }

    public HelloPayload(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```



### 5.4.`Query`

```java
public class HelloQuery implements Serializable {

    private String name;

    public HelloQuery() {

    }

    public HelloQuery(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```



## 6.`JSON`

### 6.1.`Jsoner`

```java
public final class Jsoner {

    private Jsoner() {
        AssertionErrorThrower.throwz(Jsoner.class);
    }

    private static ObjectMapper sharedObjectMapper;

    public static class PrivateView {}

    public static class PublicView {}

    private static final InheritableThreadLocal<ObjectMapper> objectMapperHolder = new InheritableThreadLocal<ObjectMapper>() {
        @Override
        protected ObjectMapper initialValue() {
            return initObjectMapper();
        }
    };

    private static ObjectMapper initDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.registerModule(new JavaTimeModule());

        // changed
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }

    private static ObjectMapper initObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : initDefaultObjectMapper();
    }

    public static void injectSharedObjectMapper(ObjectMapper objectMapper) {
        sharedObjectMapper = objectMapper;
    }

    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(byte[] json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(InputStream json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(byte[] json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    public static <T> T parseObject(InputStream json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    // ---------------------------------------------------------------- parse.array

    public static <T> List<T> parseArray(String json) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    // ---------------------------------------------------------------- additional.methods

    public static <T> List<T> toList(String json) {
        return parseArray(json);
    }

    public static <T> List<T> toList(String json, Class<T> clazz) {
        return parseArray(json, clazz);
    }

    public static <T> Set<T> toSet(String json) {
        return parseObject(json, new TypeReference<Set<T>>() {});
    }

    public static <T> Collection<T> toCollection(String json) {
        return parseObject(json, new TypeReference<Set<T>>() {});
    }

    public static <T> Map<String, T> toMap(String json) {
        return parseObject(json, new TypeReference<Map<String, T>>() {});
    }

    // ----------------------------------------------------------------

    public static <T> String toJSONString(T object) {
        return toJSONString(object, null);
    }

    public static <T> String toJSONString(T object, Class<?> view) {
        try {
            ObjectMapper mapper = getObjectMapper();
            ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
            if (view != null) {
                objectWriter = objectWriter.withView(view);
            }
            return objectWriter.writeValueAsString(object);
        } catch (IOException ioe) {
            return throwUnchecked(ioe, String.class);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : objectMapperHolder.get();
    }

    public static <T> byte[] toByteArray(T object) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.writeValueAsBytes(object);
        } catch (IOException ioe) {
            return throwUnchecked(ioe, byte[].class);
        }
    }

    public static JsonNode node(String json) {
        return parseObject(json, JsonNode.class);
    }

    public static int maxDeepSize(JsonNode one, JsonNode two) {
        return Math.max(deepSize(one), deepSize(two));
    }

    public static int deepSize(JsonNode node) {
        if (node == null) {
            return 0;
        }

        int acc = 1;
        if (node.isContainerNode()) {
            for (JsonNode child : node) {
                acc++;
                if (child.isContainerNode()) {
                    acc += deepSize(child);
                }
            }
        }

        return acc;
    }

    public static String prettyPrint(String json) {
        ObjectMapper mapper = getObjectMapper();
        try {
            // @formatter:off
            return mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(mapper.readValue(json, JsonNode.class));
            // @formatter:on
        } catch (IOException e) {
            return throwUnchecked(e, String.class);
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> targetClass) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(map, targetClass);
    }

    public static <T> Map<String, Object> objectToMap(T theObject) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(theObject, new TypeReference<Map<String, Object>>() {});
    }

    public static int schemaPropertyCount(JsonNode schema) {
        int count = 0;
        final JsonNode propertiesNode = schema.get("properties");
        if (propertiesNode != null && !propertiesNode.isEmpty()) {
            for (JsonNode property : propertiesNode) {
                count++;
                if (property.has("properties")) {
                    count += schemaPropertyCount(property);
                }
            }
        }

        return count;
    }

    public static <T> T throwUnchecked(final Throwable ex, final Class<T> returnType) {
        throwsUnchecked(ex);
        throw new AssertionError("json: this.code.should.be.unreachable.here!");
    }

    public static <T> T throwUnchecked(final Throwable ex) {
        return throwUnchecked(ex, null);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwsUnchecked(Throwable toThrow) throws T {
        throw (T) toThrow;
    }

    public static <T> T uncheck(Callable<T> work, Class<T> returnType) {
        try {
            return work.call();
        } catch (Exception e) {
            return throwUnchecked(e, returnType);
        }
    }
}

```



#### 6.2.`bjectMapper`

##### 6.2.1.`Register`

```java
public final class Jsoner {
    
    // ...

    public static void injectSharedObjectMapper(ObjectMapper objectMapper) {
        sharedObjectMapper = objectMapper;
    }
   
    // ...
}
```



##### 6.2.2.`Init`

```java
public final class Jsoner {
    
    // ...

    private static ObjectMapper initObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : initDefaultObjectMapper();
    }
   
    // ...
}
```



##### 6.2.3.`Default`

```java
public final class Jsoner {
    
    // ...

    private static ObjectMapper initDefaultObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.IGNORE_UNDEFINED, true);
        objectMapper.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
        objectMapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        objectMapper.registerModule(new JavaTimeModule());

        // changed
        //objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return objectMapper;
    }
   
    // ...
}
```

