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

import io.github.photowey.spring.boot.mock.tester.core.domain.dto.StatusDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * {@code HealthController}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
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
