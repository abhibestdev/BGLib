package us.blockgame.lib.disguise;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import us.blockgame.lib.LibPlugin;

public class DisguiseListener implements Listener {

    @EventHandler
    public void onPreLogin(PlayerPreLoginEvent event) {
        DisguiseHandler disguiseHandler = LibPlugin.getInstance().getDisguiseHandler();

        Player disguisedPlayer = Bukkit.getOnlinePlayers().stream().filter(p -> disguiseHandler.isDisguised(p) && disguiseHandler.getDisguisedName(p).equalsIgnoreCase(event.getName())).findFirst().orElse(null);

        if (disguisedPlayer != null) {
            //Undisguise player
            disguiseHandler.undisguise(disguisedPlayer);

            //Kick player
            disguisedPlayer.kickPlayer(ChatColor.RED + "The player you were disguised as has joined the server.");
            return;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        DisguiseHandler disguiseHandler = LibPlugin.getInstance().getDisguiseHandler();

    /*    EntityPlayer entityPlayer = ((CraftPlayer) player).getHandle();
        GameProfile gameProfile = entityPlayer.getProfile();

        Property property = gameProfile.getProperties().get("textures").stream().findFirst().orElse(null);
        if (property != null) {
            //Save property

            disguiseHandler.getSkinMap().put(player.getUniqueId(), new Skin(property.getName(), property.getValue(), property.getSignature()));
        } */
    }
}
