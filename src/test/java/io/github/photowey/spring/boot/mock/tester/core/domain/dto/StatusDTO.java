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
package io.github.photowey.spring.boot.mock.tester.core.domain.dto;

import java.io.Serializable;

/**
 * {@code Status}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
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
