package us.blockgame.lib.disguise;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.blockgame.lib.LibPlugin;
import us.blockgame.lib.cache.CacheHandler;
import us.blockgame.lib.util.MojangUtil;

import java.util.Map;
import java.util.UUID;

public class DisguiseHandler {

    @Getter private Map<UUID, String> disguiseMap;
    @Getter private Map<UUID, Skin> skinMap;

    public DisguiseHandler() {
        //Initialize maps
        disguiseMap = Maps.newHashMap();
        skinMap = Maps.newHashMap();

        //Register Listeners
        Bukkit.getPluginManager().registerEvents(new DisguiseListener(), LibPlugin.getInstance());
    }

    //Returns if the player is disguised or not
    public boolean isDisguised(Player player) {
        return disguiseMap.containsKey(player.getUniqueId());
    }

    //Returns player's real name
    public String getRealName(Player player) {
        CacheHandler cacheHandler = LibPlugin.getInstance().getCacheHandler();
        return cacheHandler.getUsername(player.getUniqueId());
    }

    //Get the name a player is disguised as
    public String getDisguisedName(Player player) {
        return disguiseMap.get(player.getUniqueId());
    }

    //Method to change the player's username
    public void changeName(Player player, String name) {
    /*    Bukkit.getOnlinePlayers().stream().forEach(p -> {

            EntityPlayer originalPlayer = ((CraftPlayer) player).getHandle();
            EntityPlayer entityPlayer = ((CraftPlayer) p).getHandle();

            //Delete player from tab
            entityPlayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[]{originalPlayer}));

            GameProfile gameProfile = originalPlayer.getProfile();

            try {
                final Field nameField = GameProfile.class.getDeclaredField("name");
                nameField.setAccessible(true);
                final Field modifiersField = Field.class.getDeclaredField("modifiers");
                modifiersField.setAccessible(true);
                modifiersField.setInt(nameField, nameField.getModifiers() & 0xFFFFFFEF);

                //Change the player's username
                nameField.set(gameProfile, name);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            //Add player on tab
            entityPlayer.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[]{(originalPlayer)}));

            if (p != player) {
                //Destroy the old entity id for everyone
                entityPlayer.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(new int[]{player.getEntityId()}));

                //Spawn the new player
                entityPlayer.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(originalPlayer));
            }
        });
        disguiseMap.put(player.getUniqueId(), name); */
    }

    //Method to change player's skin
    public void changeSkin(Player player, String name, String value, String signature) {
  /*      EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        GameProfile gameProfile = entityPlayer.getProfile();
        gameProfile.getProperties().clear();

        //Change the skin signature and value in the player's game profile
        gameProfile.getProperties().put("textures", new Property(name, value, signature)); */
    }

    //Method to full disguise a player
    public boolean fullDisguise(Player player, String username) {
        //Don't disguise if the player is online
        if (Bukkit.getPlayerExact(username) != null) return false;

        String[] skin = MojangUtil.getSkin(username);

        //If skin not found, return disguise as unsuccessful
        if (skin == null) {
            return false;
        }

        //Change player's name
        changeName(player, username);

        //Change player's skin
        changeSkin(player, skin[0], skin[1], skin[2]);

        disguiseMap.put(player.getUniqueId(), username);

        //Return disguise successful
        return true;
    }

    public void undisguise(Player player) {
        //Change name back to real name
   /*     changeName(player, getRealName(player));

        EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        GameProfile gameProfile = entityPlayer.getProfile();
        gameProfile.getProperties().clear();

        //Change skin back to original skin
        Skin skin = skinMap.get(player.getUniqueId());
        gameProfile.getProperties().put("textures", new Property(skin.getName(), skin.getValue(), skin.getSignature()));

        disguiseMap.remove(player.getUniqueId()); */
    }
}
