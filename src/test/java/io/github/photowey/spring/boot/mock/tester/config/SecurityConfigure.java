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
package io.github.photowey.spring.boot.mock.tester.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * {@code SecurityConfigure}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
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