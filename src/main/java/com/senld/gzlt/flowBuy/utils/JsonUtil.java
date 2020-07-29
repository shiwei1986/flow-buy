package com.senld.gzlt.flowBuy.utils;

import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonUtil {

    private static Gson gson;

    static {
        GsonBuilder gb = new GsonBuilder();
        gb.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gson = gb.create();
    }

    public static final String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static final <T> T fromJson(final String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public static final <T> T fromJson(final String json, Type t) {
        return gson.fromJson(json, t);
    }

    @SuppressWarnings("unchecked")
    public static final Map<String, Object> fromJson(final String json) {
        return fromJson(json, Map.class);
    }

    public static final void prettyPrint(Object obj) {
        Gson create = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().disableHtmlEscaping().create();
        System.out.println(create.toJson(obj));
    }

    public static final void prettyPrint(Object obj, PrintStream printStream) {
        Gson create = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().disableHtmlEscaping().create();
        printStream.println(create.toJson(obj));
    }

    public static final String toPretty(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

}