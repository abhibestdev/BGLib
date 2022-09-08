package us.blockgame.lib.cache;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.cache.command.CheckCacheCommand;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.mongo.MongoHandler;
import us.blockgame.lib.redis.RedisHandler;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class CacheHandler {

    public CacheHandler() {
        load();

        //Register Commands
        CommandHandler commandHandler = LibPlugin.getInstance().getCommandHandler();
        commandHandler.registerCommand(new CheckCacheCommand());

        //Register Listener
        Bukkit.getPluginManager().registerEvents(new CacheListener(), LibPlugin.getInstance());
    }

    public void load() {
        MongoHandler mongoHandler = LibPlugin.getInstance().getMongoHandler();
        MongoCollection mongoCollection = mongoHandler.getCollection("uuidcache");
        MongoCursor mongoCursor = mongoCollection.find().iterator();

        while (mongoCursor.hasNext()) {
            Document document = (Document) mongoCursor.next();

            addUUID(UUID.fromString(document.getString("uuid")), document.getString("username"));
        }
        if (getUUID("CONSOLE") == null) {
            addUUID(UUID.fromString("f78a4d8d-d51b-4b39-98a3-230f2de0c670"), "CONSOLE");
        }
    }

    public String randomUsername() {
        String randomKey = randomKey();

        //If random key is a uuid, get the username from UUID
        if (randomKey.startsWith("uuid")) {
            return getUsername(UUID.fromString(randomKey.split(":")[1]));
        }
        //Return username
        return randomKey.split(":")[1];
    }

    public String randomKey() {
        RedisHandler redisHandler = LibPlugin.getInstance().getRedisHandler();

        StringBuilder stringBuilder = new StringBuilder();
        redisHandler.call(resource -> {
            stringBuilder.append(resource.randomKey());
        });
        return stringBuilder.toString();
    }

    public String getUsername(UUID uuid) {
        RedisHandler redisHandler = LibPlugin.getInstance().getRedisHandler();

        StringBuilder stringBuilder = new StringBuilder();
        redisHandler.call(resource -> {
            stringBuilder.append(resource.get("uuid-cache:" + uuid.toString()));
        });
        return stringBuilder.toString();
    }

    public UUID getUUID(String username) {
        RedisHandler redisHandler = LibPlugin.getInstance().getRedisHandler();

        AtomicBoolean exists = new AtomicBoolean();
        StringBuilder stringBuilder = new StringBuilder();

        redisHandler.call(resource -> {
            exists.set(resource.exists("name-cache:" + username.toLowerCase()));
            String uuid = resource.get("name-cache:" + username.toLowerCase());
            stringBuilder.append(uuid);
        });
        return exists.get() ? UUID.fromString(stringBuilder.toString()) : null;
    }

    //Get UUID if player is online, or take it from the cache if not
    public UUID getOnlineOfflineUUID(String username) {
        Player target = Bukkit.getPlayer(username);

        if (target != null)
            return target.getUniqueId();
        return getUUID(username);
    }

    public void addUUID(UUID uuid, String username) {
        RedisHandler redisHandler = LibPlugin.getInstance().getRedisHandler();

        redisHandler.call(resource -> {
            resource.set("uuid-cache:" + uuid.toString(), username);
            resource.set("name-cache:" + username.toLowerCase(), uuid.toString());
        });
    }

    public void unload() {
        RedisHandler redisHandler = LibPlugin.getInstance().getRedisHandler();
        redisHandler.call(resource -> {
            resource.del("uuid-cache:*");
            resource.del("name-cache:*");
        });
    }
}
