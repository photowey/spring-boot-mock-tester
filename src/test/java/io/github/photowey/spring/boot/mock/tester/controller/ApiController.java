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
package io.github.photowey.spring.boot.mock.tester.controller;

import io.github.photowey.spring.boot.mock.tester.core.domain.dto.GreetingDTO;
import io.github.photowey.spring.boot.mock.tester.core.domain.payload.HelloPayload;
import io.github.photowey.spring.boot.mock.tester.core.domain.query.HelloQuery;
import io.github.photowey.spring.boot.mock.tester.core.domain.result.ApiResult;
import org.springframework.web.bind.annotation.*;

/**
 * {@code ApiController}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
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
     * DELETE :/delete/{userId}
     * <p>
     * curl -X DELETE http://localhost:7923/api/v1/delete/1711120980000
     *
     * @param userId {@code userId}
     * @return {@link GreetingDTO}
     */
    @DeleteMapping("/delete/{userId}")
    public ApiResult<GreetingDTO> delete(@PathVariable("userId") Long userId) {
        return ApiResult.ok(new GreetingDTO(String.format("Hello delete.%d", userId)));
    }
}