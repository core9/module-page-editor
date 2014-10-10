package io.core9.editor;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.template.soy.data.SoyData;
import com.google.template.soy.data.SoyListData;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.data.restricted.BooleanData;
import com.google.template.soy.data.restricted.FloatData;
import com.google.template.soy.data.restricted.IntegerData;
import com.google.template.soy.data.restricted.NullData;
import com.google.template.soy.data.restricted.StringData;

public class JsonSoyUtils {

  public static JsonElement SoyToJson(SoyData data) {

    JsonElement root = null;
    if (data instanceof SoyMapData) {
      Map<String, SoyData> map = ((SoyMapData) data).asMap();
      JsonObject hash = new JsonObject();

      for (Map.Entry<String, ?> entry : map.entrySet()) {
        String key = entry.getKey();
        SoyData soyValue = (SoyData) entry.getValue();
        JsonElement value = SoyToJson(soyValue);

        hash.add(key, value);
      }
      root = hash;
    } else if (data instanceof SoyListData) {
      SoyListData list = ((SoyListData) data);
      JsonArray array = new JsonArray();

      for (SoyData soyValue : list) {
        JsonElement value = SoyToJson(soyValue);
        array.add(value);
      }
      root = array;
    } else { // Primitive
      if (data == NullData.INSTANCE) {
        root = new JsonNull();
      } else if (data instanceof StringData) {
        root = new JsonPrimitive(((StringData) data).getValue());
      } else if (data instanceof BooleanData) {
        root = new JsonPrimitive(((BooleanData) data).getValue());
      } else if (data instanceof IntegerData) {
        root = new JsonPrimitive(((IntegerData) data).getValue());
      } else if (data instanceof FloatData) {
        root = new JsonPrimitive(((FloatData) data).getValue());
      } else {
        throw new JsonParseException(
            "Attempting to convert unrecognized SoyData to JSON Primitive(object type"
                + data.getClass().getSimpleName() + ").");
      }
    }

    return root;
  }

  public static SoyData JsonToSoy(InputStream stream, String encoding) {
    InputStreamReader reader = new InputStreamReader(stream,
        Charset.forName(encoding));
    return JsonToSoy(reader);
  }

  public static SoyData JsonToSoy(Reader reader) {
    JsonParser parser = new JsonParser();

    JsonElement rootElement = parser.parse(reader);

    SoyData data = parseJsonElement(rootElement);

    return data;
  }

  public static SoyData JsonToSoy(String json) {
    JsonParser parser = new JsonParser();

    JsonElement rootElement = parser.parse(json);

    SoyData data = parseJsonElement(rootElement);

    return data;
  }

  public static SoyMapData parseJsonObject(JsonObject object) {
    SoyMapData map = new SoyMapData();

    Set<Entry<String, JsonElement>> items = object.entrySet();
    for (Entry<String, JsonElement> entry : items) {
      String key = entry.getKey();
      JsonElement value = entry.getValue();
      SoyData data = parseJsonElement(value);

      if (data != null) {
        map.put(key, data);
      } else { // Prolly a primitive
        if (value.isJsonPrimitive()) {
          JsonPrimitive node = value.getAsJsonPrimitive();
          if (node.isBoolean()) {
            map.put(key, node.getAsBoolean());
          } else if (node.isString()) {
            map.put(key, node.getAsString());
          } else if (node.isNumber()) {
            Number number = node.getAsNumber();
            if (number instanceof Integer) {
              map.put(key, number.intValue());
            } else if (number instanceof Double) {
              map.put(key, number.doubleValue());
            } else {
              map.put(key, number);
            }
          }
        } else {
          map.put(key, (Object) null);
        }
      }
    }

    return map;
  }

  public static SoyListData MapToList(SoyMapData soyMap) {
    SoyListData data = new SoyListData();
    Map<String, SoyData> map = soyMap.asMap();

    for (Entry<String, SoyData> entry : map.entrySet()) {
      SoyMapData item = new SoyMapData();
      item.put("key", entry.getKey());
      item.put("value", entry.getValue());

      data.add(item);
    }

    return data;
  }

  public static SoyListData parseJsonArray(JsonArray items) {
    SoyListData list = new SoyListData();

    for (JsonElement item : items) {
      SoyData data = parseJsonElement(item);
      if (data != null) {
        list.add(data);
      } else { // Prolly a primitive
        if (item.isJsonPrimitive()) {
          JsonPrimitive node = item.getAsJsonPrimitive();
          if (node.isBoolean()) {
            list.add(node.getAsBoolean());
          } else if (node.isString()) {
            list.add(node.getAsString());
          } else if (node.isNumber()) {
            Number number = node.getAsNumber();
            if (number instanceof Integer) {
              list.add(number.intValue());
            } else if (number instanceof Double) {
              list.add(number.doubleValue());
            } else {
              list.add(number);
            }
          }
        } else {
          list.add((Object) null);
        }
      }

    }
    return list;
  }

  public static SoyData parseJsonElement(JsonElement element) {
    if (element.isJsonArray()) {
      return parseJsonArray(element.getAsJsonArray());
    } else if (element.isJsonNull()) {
      return null;
    } else if (element.isJsonObject()) {
      return parseJsonObject(element.getAsJsonObject());
    }
    return null;
  }
}
