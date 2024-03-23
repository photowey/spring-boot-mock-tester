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
package io.github.photowey.spring.boot.mock.tester.thrower;

/**
 * {@code AssertionErrorThrower}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
public final class AssertionErrorThrower {

    private AssertionErrorThrower() {
        throwz(AssertionErrorThrower.class);
    }

    /**
     * Throws an {@link AssertionError} with a message that indicates no instances of the specified class can be created.
     * This method is designed to be used when it's necessary to prevent instantiation of a particular type.
     *
     * @param clazz The class whose name will be included in the error message of the AssertionError.
     * @throws AssertionError Always throws an instance of AssertionError with a message indicating that no instances of `clazz` are allowed.
     */
    public static <T> void throwz(Class<T> clazz) {
        throw new AssertionError("No " + clazz.getName() + " instances for you!");
    }
}
