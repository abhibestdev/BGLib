package us.blockgame.lib.redis;

import com.google.gson.reflect.TypeToken;
import us.blockgame.lib.LibPlugin;

import java.lang.reflect.Type;
import java.util.Map;

public class RedisMessage {

    public static Map<String, Object> deserialize(String string) {
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        return LibPlugin.getGson().fromJson(string, type);
    }

    public static String serialize(Map<String, Object> map) {
        return LibPlugin.getGson().toJson(map);
    }

}