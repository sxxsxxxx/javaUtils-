package com.sunUtils.commos.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/*
 * jsonUtils转换
 */
public class JsonObjUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String ObjToJson(Object s) throws IOException {
        return ObjToJson(s, true);
    }

    public static String ObjToJson(Object s, boolean writeNullValue)
            throws IOException {
        if (!writeNullValue) {
            objectMapper.setSerializationInclusion(Include.NON_NULL);
        }
        return objectMapper.writeValueAsString(s);
    }

    public static void ObjToJson(OutputStream os, Object s) throws IOException {
        ObjToJson(os, s, true, "UTF-8");
    }

    public static void ObjToJson(OutputStream os, Object s,
                                 boolean writeNullValue) throws IOException {
        ObjToJson(os, s, writeNullValue, "UTF-8");
    }

    public static void ObjToJson(OutputStream os, Object s, String encoding)
            throws IOException {
        ObjToJson(os, s, true, encoding);
    }

    public static void ObjToJson(OutputStream os, Object s,
                                 boolean writeNullValue, String encoding)
            throws JsonGenerationException, JsonMappingException, IOException {
        try (OutputStreamWriter osw = new OutputStreamWriter(os, encoding)) {
            objectMapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,
                    writeNullValue);
            if (!writeNullValue) {
                objectMapper.setSerializationInclusion(Include.NON_NULL);
            } else {
                objectMapper.setSerializationInclusion(Include.ALWAYS);
            }
            objectMapper.writeValue(osw, s);
        }
    }

    public static <T> T JsonToObj(String jason, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(jason, clazz);

    }

    public static <T> T JsonToObj(InputStream jason, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper.readValue(jason, clazz);

    }

    public static Map<String, String> Object2Fields(Object obj)
            throws IllegalArgumentException, IllegalAccessException {
        Map<String, String> map = new HashMap<String, String>();
        Object2Fields(obj, map, "");
        return map;
    }

    public static void Object2Fields(Object obj, Map<String, String> map)
            throws IllegalArgumentException, IllegalAccessException {
        Object2Fields(obj, map, "");
    }

    private static void Object2Fields(Object obj, Map<String, String> map,
                                      String parent) throws IllegalArgumentException,
            IllegalAccessException {
        if (obj == null) {
            return;
        }
        if (parent == null) {
            parent = "";
        }
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            for (Annotation an : field.getDeclaredAnnotations()) {
                if (an instanceof JsonProperty) {
                    JsonProperty jp = (JsonProperty) an;
                    if (jp != null && StringUtils.hasLength(jp.value())) {
                        name = jp.value();
                        break;
                    }
                }
            }
            name = parent + name;
            Class<?> fieldType = field.getType();
            if (fieldType.isPrimitive()) {
                String value = primitive2String(obj, field);
                map.put(name, value);
                continue;
            }
            Object val = field.get(obj);
            if (val == null) {
                continue;
            }
            if (fieldType.isArray()) {
                Class<?> comp = fieldType.getComponentType();
                if (comp.isPrimitive()) {
                    String value = primitiveArray2String(val, comp.getName());
                    map.put(name, value);
                } else if (isPrimitiveType(comp.getName())) {
                    String value = primitiveArray2String(val);
                    map.put(name, value);
                } else {
                    Object[] objs = (Object[]) val;
                    int i = 0;
                    for (Object o : objs) {
                        Object2Fields(o, map, name + "[" + (i++) + "].");
                    }
                }
            } else if (List.class.isAssignableFrom(fieldType)) {
                List<?> lst = (List<?>) val;
                int i = 0;
                for (Object o : lst) {
                    if (o != null) {
                        Object2Fields(o, map, name + "[" + (i++) + "].");
                    }
                }
            } else if (isPrimitiveType(fieldType.getName())) {
                String value = val.toString();
                map.put(name, value);
            } else {
                Object2Fields(val, map, name + ".");
            }
        }
    }

    private static String primitive2String(Object obj, Field field)
            throws IllegalArgumentException, IllegalAccessException {
        switch (field.getType().getName()) {
            case "byte":
                return Byte.toString(field.getByte(obj));
            case "short":
                return Short.toString(field.getShort(obj));
            case "int":
                return Integer.toString(field.getInt(obj));
            case "long":
                return Long.toString(field.getLong(obj));
            case "char":
                return Character.toString(field.getChar(obj));
            case "float":
                return Float.toString(field.getFloat(obj));
            case "double":
                return Double.toString(field.getDouble(obj));
            case "boolean":
                return Boolean.toString(field.getBoolean(obj));
            default:
                return "";
        }
    }

    private static String primitiveArray2String(Object val) {
        StringBuilder sb = new StringBuilder();
        Object[] objs = (Object[]) val;
        for (Object obj : objs) {
            sb.append(",");
            sb.append(obj.toString());
        }
        return sb.toString().replaceAll("^\\,", "");
    }

    private static String primitiveArray2String(Object val, String compClass) {
        StringBuilder sb = new StringBuilder();
        switch (compClass) {
            case "byte":
                for (byte b : (byte[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "short":
                for (short b : (short[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "int":
                for (int b : (int[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "long":
                for (long b : (long[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "char":
                for (char b : (char[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "float":
                for (float b : (float[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "double":
                for (double b : (double[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;
            case "boolean":
                for (boolean b : (boolean[]) val) {
                    sb.append(",");
                    sb.append(b);
                }
                break;

            default:
                return "";
        }
        return sb.toString().replaceAll("^\\,", "");
    }

    private static boolean isPrimitiveType(String name) {
        switch (name) {
            case "java.lang.Byte":
            case "java.lang.Short":
            case "java.lang.Integer":
            case "java.lang.Long":
            case "java.lang.Character":
            case "java.lang.Float":
            case "java.lang.Double":
            case "java.lang.Boolean":
            case "java.lang.String": // 这里将string也列入主要类型
                return true;
            default:
                return false;
        }
    }

}

