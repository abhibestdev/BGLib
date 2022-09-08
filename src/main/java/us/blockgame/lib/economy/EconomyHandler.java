package us.blockgame.lib.economy;

import com.google.common.collect.Maps;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.command.CommandHandler;
import us.blockgame.lib.economy.command.BalanceCommand;
import us.blockgame.lib.economy.command.EconomyCommand;
import us.blockgame.lib.economy.command.PayCommand;
import us.blockgame.lib.mongo.MongoHandler;
import us.blockgame.lib.util.MapUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class EconomyHandler {

    @Setter @Getter private EconomyType economyType;
    private Map<UUID, EconomyWallet> walletMap;

    public EconomyHandler() {
        //Initialize the map
        walletMap = Maps.newHashMap();
    }

    //Initialize currency and set it to dollars by default
    public void initializeCurrency() {
        initializeCurrency(EconomyType.DOLLARS);
    }

    //Initialize currency with custom type
    public void initializeCurrency(EconomyType economyType) {
        //Set the new currency type
        this.economyType = economyType;

        //Register commands
        CommandHandler commandHandler = LibPlugin.getInstance().getCommandHandler();
        commandHandler.registerCommand(new EconomyCommand());
        commandHandler.registerCommand(new BalanceCommand());

        //Register pay command if the currency is transferable
        if (economyType.isTransferable()) commandHandler.registerCommand(new PayCommand());

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(new EconomyListener(), LibPlugin.getInstance());
    }

    public double getBalance(Player player) {
        //Get player's wallet or make a new one if not found
        EconomyWallet economyWallet = walletMap.getOrDefault(player.getUniqueId(), new EconomyWallet());

        //Get balance for the current economy type
        return economyWallet.getBalance(economyType);
    }

    public void setBalance(Player player, double balance) {
        //Get player's wallet or make a new one if not found
        EconomyWallet economyWallet = walletMap.getOrDefault(player.getUniqueId(), new EconomyWallet());

        //Set balance for the current economy type
        economyWallet.setBalance(economyType, balance);

        //Put wallet back into map
        walletMap.put(player.getUniqueId(), economyWallet);
    }

    //Method to get player's balance regardless of if they are online
    public double getOfflineBalance(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);

        //Return online balance
        if (player != null) return getBalance(player);

        MongoHandler mongoHandler = LibPlugin.getInstance().getMongoHandler();
        MongoCollection mongoCollection = mongoHandler.getCollection("economy");

        Document document = (Document) mongoCollection.find(Filters.eq("_id", uuid)).first();
        if (document != null) {
            return document.getDouble(economyType.toString());
        }
        return 0.0;
    }

    //Method to get player's balance from mongo
    public double getBalanceFromMongo(UUID uuid) {
        MongoHandler mongoHandler = LibPlugin.getInstance().getMongoHandler();
        MongoCollection mongoCollection = mongoHandler.getCollection("economy");

        Document document = (Document) mongoCollection.find(Filters.eq("_id", uuid)).first();
        if (document != null) {
            return document.getDouble(economyType.toString());
        }
        return 0.0;
    }

    //Method to set player's balance regardless of if they are online
    public void setOfflineBalance(UUID uuid, double balance) {
        Player player = Bukkit.getPlayer(uuid);

        //Set online balance
        if (player != null) {
            setBalance(player, balance);
            return;
        }

        MongoHandler mongoHandler = LibPlugin.getInstance().getMongoHandler();
        MongoCollection mongoCollection = mongoHandler.getCollection("economy");

        Document document = (Document) mongoCollection.find(Filters.eq("_id", uuid)).first();

        if (document != null) {
            Map<String, Object> documentMap = MapUtil.cloneDocument(document);

            documentMap.put(economyType.toString(), balance);

            //Delete old document
            mongoCollection.deleteOne(document);

            mongoCollection.insertOne(new Document(documentMap));
        } else {
            Map<String, Object> documentMap = MapUtil.createMap("_id", uuid);

            Arrays.stream(EconomyType.values()).filter(economyType -> economyType != this.economyType).forEach(economyType -> {
                documentMap.put(economyType.toString(), 0.0);
            });
            documentMap.put(economyType.toString(), balance);

            //Insert document
            mongoCollection.insertOne(new Document(documentMap));
            return;
        }
    }

    //Method to set player's balance directly in mongo
    public void setBalanceMongo(UUID uuid, double balance) {
        MongoHandler mongoHandler = LibPlugin.getInstance().getMongoHandler();
        MongoCollection mongoCollection = mongoHandler.getCollection("economy");

        Document document = (Document) mongoCollection.find(Filters.eq("_id", uuid)).first();

        if (document != null) {
            Map<String, Object> documentMap = MapUtil.cloneDocument(document);

            documentMap.put(economyType.toString(), balance);

            //Delete old document
            mongoCollection.deleteOne(document);

            mongoCollection.insertOne(new Document(documentMap));
        } else {
            Map<String, Object> documentMap = MapUtil.createMap("_id", uuid);

            Arrays.stream(EconomyType.values()).filter(economyType -> economyType != this.economyType).forEach(economyType -> {
                documentMap.put(economyType.toString(), 0.0);
            });
            documentMap.put(economyType.toString(), balance);

            //Insert document
            mongoCollection.insertOne(new Document(documentMap));
            return;
        }
    }
}
