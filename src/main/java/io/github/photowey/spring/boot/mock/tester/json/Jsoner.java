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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.github.photowey.spring.boot.mock.tester.thrower.AssertionErrorThrower;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * {@code Jsoner}
 *
 * @author photowey
 * @date 2024/03/22
 * @since 1.0.0
 */
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

    /**
     * Initializes and returns the {@link ObjectMapper} instance, either using the shared instance or creating a new one.
     *
     * @return The initialized ObjectMapper instance for JSON serialization and deserialization.
     */
    private static ObjectMapper initObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : initDefaultObjectMapper();
    }

    /**
     * Injects a shared {@link ObjectMapper} instance for JSON serialization and deserialization.
     *
     * @param objectMapper The ObjectMapper instance to be shared.
     */
    public static void injectSharedObjectMapper(ObjectMapper objectMapper) {
        sharedObjectMapper = objectMapper;
    }

    /**
     * Parses the given JSON string into an object of the specified class type.
     *
     * @param json  The JSON string to be parsed.
     * @param clazz The target class type to which the JSON should be deserialized.
     * @param <T>   The generic type parameter representing the type of the parsed object.
     * @return An instance of the desired class `T` as represented by the JSON data.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    /**
     * Parses the given JSON string into an object of the specified generic type.
     *
     * @param json    The JSON string to be parsed.
     * @param typeRef A `TypeReference` that accurately specifies the target generic type for the object.
     * @param <T>     The generic type parameter representing the type of the parsed object.
     * @return An instance of the parsed object with the type defined by `typeRef`.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T parseObject(String json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    /**
     * Parses the given JSON byte array into an object of the specified class type.
     *
     * @param json  The JSON byte array to be parsed.
     * @param clazz The target class type to which the JSON should be deserialized.
     * @param <T>   The generic type parameter representing the type of the parsed object.
     * @return An instance of the desired class `T` as represented by the JSON data.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T parseObject(byte[] json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    /**
     * Parses the given JSON input stream into an object of the specified class type.
     *
     * @param json  The JSON input stream to be parsed.
     * @param clazz The target class type to which the JSON should be deserialized.
     * @param <T>   The generic type parameter representing the type of the parsed object.
     * @return An instance of the desired class `T` as represented by the JSON data.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T parseObject(InputStream json, Class<T> clazz) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, clazz);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    /**
     * Parses the given JSON byte array into an object of the specified generic type.
     *
     * @param json    The JSON byte array to be parsed.
     * @param typeRef A `TypeReference` that accurately specifies the target generic type for the object.
     * @param <T>     The generic type parameter representing the type of the parsed object.
     * @return An instance of the parsed object with the type defined by `typeRef`.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T parseObject(byte[] json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    /**
     * Parses the given JSON input stream into an object of the specified generic type.
     *
     * @param json    The JSON input stream to be parsed.
     * @param typeRef A `TypeReference` that accurately specifies the target generic type for the object.
     * @param <T>     The generic type parameter representing the type of the parsed object.
     * @return An instance of the parsed object with the type defined by `typeRef`.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T parseObject(InputStream json, TypeReference<T> typeRef) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.readValue(json, typeRef);
        } catch (Exception processingException) {
            return throwUnchecked(processingException);
        }
    }

    // ---------------------------------------------------------------- parse.array

    /**
     * Parses the given JSON string into a List of objects of the specified type.
     *
     * @param json The JSON string to be parsed into a List.
     * @param <T>  The generic type parameter representing the type of objects in the List.
     * @return A List of objects parsed from the JSON string.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> List<T> parseArray(String json) {
        return parseObject(json, new TypeReference<List<T>>() {});
    }

    /**
     * Parses the given JSON string into a List of objects of the specified class type.
     *
     * @param json  The JSON string to be parsed into a List.
     * @param clazz The target class type to which the JSON objects should be deserialized.
     * @param <T>   The generic type parameter representing the type of objects in the List.
     * @return A List of objects of type `T` parsed from the JSON string.
     * If an exception occurs during parsing, it is wrapped and rethrown as a runtime exception.
     */
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

    /**
     * Converts the given object into a JSON string representation using default serialization options.
     *
     * @param object The object to be converted into a JSON string.
     * @param <T>    The type of the input object to be serialized.
     * @return A JSON formatted string representing the provided object.
     * If an exception occurs during serialization, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> String toJSONString(T object) {
        return toJSONString(object, null);
    }

    /**
     * Converts the given object into a JSON string representation using specified serialization options.
     *
     * @param object The object to be converted into a JSON string.
     * @param view   The view class used for serialization control, or null for default serialization.
     * @param <T>    The type of the input object to be serialized.
     * @return A JSON formatted string representing the provided object.
     * If an exception occurs during serialization, it is wrapped and rethrown as a runtime exception.
     */
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

    /**
     * Retrieves the shared ObjectMapper instance, or creates a new one if not already initialized.
     *
     * @return The shared ObjectMapper instance used for JSON serialization and deserialization.
     */
    public static ObjectMapper getObjectMapper() {
        return sharedObjectMapper != null ? sharedObjectMapper : objectMapperHolder.get();
    }

    /**
     * Converts the given object into a byte array representation using default serialization options.
     *
     * @param object The object to be converted into a byte array.
     * @param <T>    The type of the input object to be serialized.
     * @return A byte array representing the provided object in serialized form.
     * If an exception occurs during serialization, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> byte[] toByteArray(T object) {
        try {
            ObjectMapper mapper = getObjectMapper();
            return mapper.writeValueAsBytes(object);
        } catch (IOException ioe) {
            return throwUnchecked(ioe, byte[].class);
        }
    }

    /**
     * Formats the given JSON string with pretty printing for improved readability.
     *
     * @param json The JSON string to be pretty printed.
     * @return A formatted JSON string with improved readability.
     * If an exception occurs during formatting, it is wrapped and rethrown as a runtime exception.
     */
    public static String toPrettyJSONString(String json) {
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

    /**
     * Converts a Map object to an instance of the specified target class using ObjectMapper's convertValue method.
     *
     * @param map         The Map object to be converted.
     * @param targetClass The target class type to which the Map should be converted.
     * @param <T>         The generic type parameter representing the type of the converted object.
     * @return An instance of the target class `T` converted from the Map object.
     * If an exception occurs during conversion, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> targetClass) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(map, targetClass);
    }

    /**
     * Converts the given object to a Map object using ObjectMapper's convertValue method.
     *
     * @param object The object to be converted to a Map.
     * @param <T>    The type of the input object to be converted.
     * @return A Map object representing the provided object's fields and values.
     * If an exception occurs during conversion, it is wrapped and rethrown as a runtime exception.
     */
    public static <T> Map<String, Object> objectToMap(T object) {
        ObjectMapper mapper = getObjectMapper();
        return mapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
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
}
