package us.blockgame.lib.cache;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.mongo.MongoHandler;
import us.blockgame.lib.redis.RedisHandler;
import us.blockgame.lib.redis.RedisSubscriber;
import us.blockgame.lib.util.MapUtil;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CacheListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();

        if (cacheHandler.getUUID(player.getName()) == null ||
                !cacheHandler.getUUID(player.getName()).equals(player.getUniqueId()) ||
                !cacheHandler.getUsername(player.getUniqueId()).equalsIgnoreCase(player.getName())) {
            Map<String, Object> map = MapUtil.createMap("uuid", player.getUniqueId().toString(), "username", player.getName());

            MongoHandler mongoHandler = LibPlugin.getInstance().getMongoHandler();
            MongoCollection mongoCollection = mongoHandler.getCollection("uuidcache");

            CompletableFuture.runAsync(() -> {
                Document document = (Document) mongoCollection.find(Filters.eq("uuid", player.getUniqueId().toString())).first();

                if (document != null) {
                    mongoCollection.deleteOne(document);
                }
                Map<String, Object> documentMap = MapUtil.createMap("uuid", player.getUniqueId().toString(), "username", player.getName());
                mongoCollection.insertOne(new Document(documentMap));
            });

            RedisHandler redisHandler = LibPlugin.getInstance().getRedisHandler();
            redisHandler.send("add-cache", map);
        }
    }


    @RedisSubscriber("add-cache")
    public void onCacheUpdate(Map<String, Object> messageMap) {
        String uuid = (String) messageMap.get("uuid");
        String username = (String) messageMap.get("username");

        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();
        cacheHandler.addUUID(UUID.fromString(uuid), username);
    }
}
