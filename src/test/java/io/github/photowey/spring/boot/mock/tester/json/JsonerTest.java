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
package io.github.photowey.spring.boot.mock.tester.json;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * {@code JsonerTest}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
class JsonerTest {

    @Test
    void testJson() {
        Hello hello = Hello.builder()
                .id(System.currentTimeMillis())
                .age(18)
                .name("I'm ET")
                .balance(new BigDecimal("1000.00"))
                .birthday(LocalDateTime.now())
                .build();

        String json = Jsoner.toJSONString(hello);
        assertNotNull(json);

        Hello parsedHello = Jsoner.parseObject(json, Hello.class);
        assertNotNull(parsedHello);

        List<Hello> hellos = new ArrayList<>();
        hellos.add(hello);

        String jsonArray = Jsoner.toJSONString(hellos);
        List<Hello> parsedHelloArray = Jsoner.parseArray(jsonArray, Hello.class);
        assertNotNull(parsedHelloArray);
        assertEquals(1, parsedHelloArray.size());

        List<Hello> parsedHelloList = Jsoner.toList(jsonArray);
        assertNotNull(parsedHelloList);
        assertEquals(1, parsedHelloList.size());

        List<Hello> parsedHelloList2 = Jsoner.toList(jsonArray, Hello.class);
        assertNotNull(parsedHelloList2);
        assertEquals(1, parsedHelloList2.size());

        Set<Hello> parsedHelloSet = Jsoner.toSet(jsonArray);
        assertNotNull(parsedHelloSet);
        assertEquals(1, parsedHelloSet.size());

        Collection<Hello> parsedHelloCollection = Jsoner.toCollection(jsonArray);
        assertNotNull(parsedHelloCollection);
        assertEquals(1, parsedHelloCollection.size());
    }

    public static class Hello implements Serializable {

        private static final long serialVersionUID = 6498956106183628239L;

        private Long id;
        private Integer age;
        private String name;
        private BigDecimal balance;
        private LocalDateTime birthday;

        public static HelloBuilder builder() {
            return new HelloBuilder();
        }

        public Long getId() {
            return this.id;
        }

        public Integer getAge() {
            return this.age;
        }

        public String getName() {
            return this.name;
        }

        public BigDecimal getBalance() {
            return this.balance;
        }

        public LocalDateTime getBirthday() {
            return this.birthday;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public void setAge(final Integer age) {
            this.age = age;
        }

        public void setName(final String name) {
            this.name = name;
        }

        public void setBalance(final BigDecimal balance) {
            this.balance = balance;
        }

        public void setBirthday(final LocalDateTime birthday) {
            this.birthday = birthday;
        }

        public Hello() {
        }

        public Hello(final Long id, final Integer age, final String name, final BigDecimal balance, final LocalDateTime birthday) {
            this.id = id;
            this.age = age;
            this.name = name;
            this.balance = balance;
            this.birthday = birthday;
        }

        public static class HelloBuilder {
            private Long id;
            private Integer age;
            private String name;
            private BigDecimal balance;
            private LocalDateTime birthday;

            HelloBuilder() {
            }

            public HelloBuilder id(final Long id) {
                this.id = id;
                return this;
            }

            public HelloBuilder age(final Integer age) {
                this.age = age;
                return this;
            }

            public HelloBuilder name(final String name) {
                this.name = name;
                return this;
            }

            public HelloBuilder balance(final BigDecimal balance) {
                this.balance = balance;
                return this;
            }

            public HelloBuilder birthday(final LocalDateTime birthday) {
                this.birthday = birthday;
                return this;
            }

            public Hello build() {
                return new Hello(this.id, this.age, this.name, this.balance, this.birthday);
            }
        }
    }
}