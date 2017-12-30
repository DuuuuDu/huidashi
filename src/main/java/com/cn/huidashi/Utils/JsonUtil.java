package com.cn.huidashi.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by alvin on 2017/12/26.
 */
public class JsonUtil {

    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    /**
     * 在指定的JsonNode中获取指定key值对应的值
     * @param jsonNode
     * @param key
     * @return
     * @throws IOException
     */
    public static String getStringValue(JsonNode jsonNode,String key) throws IOException {
        return ObjectUtils.isEmpty(jsonNode)?"":jsonNode.has(key)?jsonNode.get(key).textValue():"";
    }

    /**
     * 在指定的Json字符串中获取指定key值对应的值
     * @param srcJsonString Json字符串
     * @param key
     * @return
     * @throws IOException
     */
    public static String getStringValue(String srcJsonString,String key) throws IOException {
        return JsonUtil.getStringValue(JsonUtil.toJson(srcJsonString),key);
    }

    /**
     * 将传入对象转换为JsonNode
     * @param o 待转化POJO
     * @return JsonNode
     * @throws IOException
     */
    public static JsonNode toJson(Object o) throws IOException {
        return mapper.readTree(mapper.writeValueAsString(o));
    }

    /**
     * 将传入对象转换为JsonNode
     * @param s 待转化JSON字符串
     * @return JsonNode
     * @throws IOException
     */
    public static JsonNode toJson(String s) throws IOException {
        return mapper.readTree(s);
    }

    /**
     * 根据指定的JsonNode，指定的键值，指定的类型，返回具体的实例，
     * @param jsonNode
     * @param key JsonNode中具体的key值
     * @param clz 需要的类型
     * @param <T> 指定泛型
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T toPOJO(JsonNode jsonNode,String key,Class<T> clz) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        T t = null;
        if(!ObjectUtils.isEmpty(jsonNode)){
            if(jsonNode.has(key)){
                t = mapper.readValue(jsonNode.get(key).toString(), new TypeReference<T>() {});
                return t;
            }
        }
        return (T) Class.forName(clz.getName()).newInstance();
    }

    /**
     * 根据指定的JsonNode，指定的类型，返回具体的实例，
     * @param jsonNode
     * @param clz 需要的类型
     * @param <T> 指定泛型
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T toPOJO(JsonNode jsonNode,TypeReference clz) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        T t = null;
        if(!ObjectUtils.isEmpty(jsonNode)){
            t = mapper.readValue(jsonNode.toString(), clz);
            return t;
        }
        return (T) clz.getClass().newInstance();
    }

    /**
     * 根据指定的源JSON字符串，指定的类型，返回具体的实例，
     * @param srcString 源JSON字符串
     * @param <T> 指定泛型
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T toPOJO(String srcString,String key,TypeReference clz) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {

        T t = null;
        if(!StringUtils.isEmpty(srcString)){
            JsonNode node = mapper.readTree(srcString);
            if(!ObjectUtils.isEmpty(node)){
                if(node.has(key)){
                    t = mapper.readValue(node.get(key).toString(), clz);
                    return t;
                }
            }
        }

        return (T) clz.getClass().newInstance();
    }

    /**clz
     * 根据指定的源JSON字符串，指定的类型，返回具体的实例，
     * @param srcString 源JSON字符串
     * @param <T> 指定泛型
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> T toPOJO(String srcString,Class<T> clz) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        T t = null;
        if(!StringUtils.isEmpty(srcString)){
            t = mapper.readValue(srcString, new TypeReference<T>() {});
            return t;
        }
        return (T) Class.forName(clz.getName()).newInstance();
    }
}
