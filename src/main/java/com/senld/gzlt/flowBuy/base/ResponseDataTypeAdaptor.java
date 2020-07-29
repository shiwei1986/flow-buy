package com.senld.gzlt.flowBuy.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import lombok.Data;

public class ResponseDataTypeAdaptor extends TypeAdapter<ResponseData>{
	public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (type.getRawType() == ResponseData.class) {
                return (TypeAdapter<T>) new ResponseDataTypeAdaptor(gson);
            }
            return null;
        }
    };

    private final Gson gson;

    ResponseDataTypeAdaptor(Gson gson) {
        this.gson = gson;
    }

    @Override
    public void write(JsonWriter out, ResponseData value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("status");
        gson.getAdapter(Integer.class).write(out, value.getStatus());
        out.name("msg");
        gson.getAdapter(String.class).write(out, value.getMsg());
        out.name("data");
        gson.getAdapter(Object.class).write(out, value.getData());
        out.endObject();
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseData read(JsonReader in) throws IOException {
        ResponseData data = new ResponseData();
        Map<String, Object> dataMap = (Map<String, Object>) readInternal(in);
        data.setStatus((Integer) dataMap.get("status"));
        data.setMsg((String) dataMap.get("msg"));
        data.setData(dataMap.get("data"));
        return data;
    }


    private Object readInternal(JsonReader in) throws IOException {
        JsonToken token = in.peek();
        switch (token) {
            case BEGIN_ARRAY:
                List<Object> list = new ArrayList<Object>();
                in.beginArray();
                while (in.hasNext()) {
                    list.add(readInternal(in));
                }
                in.endArray();
                return list;

            case BEGIN_OBJECT:
                Map<String, Object> map = new LinkedTreeMap<String, Object>();
                in.beginObject();
                while (in.hasNext()) {
                    map.put(in.nextName(), readInternal(in));
                }
                in.endObject();
                return map;

            case STRING:
                return in.nextString();

            case NUMBER:
                String numberStr = in.nextString();
                if (numberStr.contains(".") || numberStr.contains("e")
                        || numberStr.contains("E")) {
                    return Double.parseDouble(numberStr);
                }
                if (Long.parseLong(numberStr) <= Integer.MAX_VALUE) {
                    return Integer.parseInt(numberStr);
                }
                return Long.parseLong(numberStr);

            case BOOLEAN:
                return in.nextBoolean();

            case NULL:
                in.nextNull();
                return null;

            default:
                throw new IllegalStateException();
        }
    }
}

@Data
class ResponseData{
	Integer status;
	
	String msg;
	
	Object data;
}
