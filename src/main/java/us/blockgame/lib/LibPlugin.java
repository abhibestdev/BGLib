package us.blockgame.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.blockgame.lib.cache.CacheHandler;
import us.blockgame.lib.cache.CacheListener;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.disguise.DisguiseHandler;
import us.blockgame.lib.economy.EconomyHandler;
import us.blockgame.lib.event.EventHandler;
import us.blockgame.lib.menu.MenuHandler;
import us.blockgame.lib.mongo.MongoHandler;
import us.blockgame.lib.nametag.NametagHandler;
import us.blockgame.lib.packet.PacketHandler;
import us.blockgame.lib.redis.RedisHandler;
import us.blockgame.lib.redis.RedisListener;
import us.blockgame.lib.scoreboard.ScoreboardHandler;
import us.blockgame.lib.tab.TabHandler;

import java.util.Random;

public class LibPlugin extends JavaPlugin {

    @Getter private static LibPlugin instance;
    @Getter private static Gson gson;
    @Getter private static Random random;
    @Getter private static boolean exampleMode = false;

    @Getter private MongoHandler mongoHandler;
    @Getter private RedisHandler redisHandler;
    @Getter private CommandHandler commandHandler;
    @Getter private CacheHandler cacheHandler;
    @Getter private EventHandler eventHandler;
    @Getter private EconomyHandler economyHandler;
    @Getter private ScoreboardHandler scoreboardHandler;
    @Getter private TabHandler tabHandler;
    @Getter private MenuHandler menuHandler;
    @Getter private DisguiseHandler disguiseHandler;
    @Getter private NametagHandler nametagHandler;
    @Getter private PacketHandler packetHandler;

    @Override
    public void onEnable() {
        instance = this;
        gson = new GsonBuilder().setPrettyPrinting().create();
        random = new Random();

        getConfig().options().copyDefaults(true);
        saveConfig();

        //Register handlers
        registerHandlers();
    }

    @Override
    public void onDisable() {
        //Close Mongo connection
        mongoHandler.getMongoClient().close();

        //Empty redis cache
        cacheHandler.unload();

        //Close Redis connection
        redisHandler.getJedisPool().close();
    }

    private void registerHandlers() {
        mongoHandler = new MongoHandler();
        redisHandler = new RedisHandler(
                getConfig().getString("redis.ip"),
                getConfig().getInt("redis.port"),
                30_000,
                getConfig().getString("redis.password"));

        redisHandler.registerListeners(
                new RedisListener(),
                new CacheListener()
        );
        redisHandler.initialize();
        commandHandler = new CommandHandler();
        cacheHandler = new CacheHandler();
        eventHandler = new EventHandler();
        economyHandler = new EconomyHandler();
        scoreboardHandler = new ScoreboardHandler();
        tabHandler = new TabHandler();
        menuHandler = new MenuHandler();
        disguiseHandler = new DisguiseHandler();
        nametagHandler = new NametagHandler();
        packetHandler = new PacketHandler();
    }

    public static Player getPlayer(String name) {
        Player player = Bukkit.getPlayer(name);

        if (player == null) return null;

        DisguiseHandler disguiseHandler = instance.getDisguiseHandler();

        if (disguiseHandler == null) {
            return player;
        }

        //If the player isn't disguised or they are referred to by their disguised name, return the player
        if (!disguiseHandler.isDisguised(player) || disguiseHandler.getDisguisedName(player).equalsIgnoreCase(name)) return player;

        return null;
    }
}
