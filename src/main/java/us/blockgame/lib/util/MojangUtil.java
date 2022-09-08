package us.blockgame.lib.util;

import lombok.SneakyThrows;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MojangUtil {

    @SneakyThrows
    public static String[] getSkin(String name) {
        final URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUIDFetcher.getUUID(name).toString() + "?unsigned=false");
        final URLConnection uc = url.openConnection();
        uc.setUseCaches(false);
        uc.setDefaultUseCaches(false);
        uc.addRequestProperty("User-Agent", "Mozilla/5.0");
        uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
        uc.addRequestProperty("Pragma", "no-cache");
        final String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
        final JSONParser parser = new JSONParser();
        final Object obj = parser.parse(json);
        final JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
        for (int i = 0; i < properties.size(); ++i) {
            final JSONObject property = (JSONObject) properties.get(i);
            final String skinName = (String) property.get((Object) "name");
            final String value = (String) property.get((Object) "value");
            final String signature = property.containsKey((Object) "signature") ? ((String) property.get((Object) "signature")) : null;

            return new String[]{skinName, value, signature};
        }
        return null;
    }
}
