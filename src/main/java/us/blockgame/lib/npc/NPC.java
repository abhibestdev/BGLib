package us.blockgame.lib.npc;

import com.comphenix.packetwrapper.WrapperPlayServerEntityMetadata;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.Hash;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.EquipmentSlot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import us.blockgame.lib.LibPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/*
 * NMS code was given to me by svdragster
 */
public class NPC {
    private final MinecraftServer server;

    public ServerPlayer serverPlayer;
    private boolean showInTab;
    private Location location;
    private boolean invis;
    private ArrayList<Player> players;

    private Player toFollow;
    private Vector followRelatively;

    public NPC(UUID uuid, String name, String skinValue, String skinSignature, Location location) {
        server = ((CraftServer) Bukkit.getServer()).getServer();

        final ServerLevel world = ((CraftWorld) location.getWorld()).getHandle();
        GameProfile gameProfile = new GameProfile(uuid, name);
        gameProfile.getProperties().clear();
        gameProfile.getProperties().put("textures", new Property("textures", skinValue, skinSignature));
        this.serverPlayer = new ServerPlayer(server, world, gameProfile);
        this.serverPlayer.setPos(location.getX(), location.getY(), location.getZ());
        this.serverPlayer.turn(location.getYaw(), location.getPitch());
        this.location = location;
        this.invis = false;
        this.players = new ArrayList<Player>();
        this.toFollow = null;
        this.followRelatively = new Vector(0, 0, 0);
    }

    public String getName() {
        return this.serverPlayer.getName().getString();
    }

    public UUID getUUID() {
        return this.serverPlayer.getUUID();
    }

    public int getEntityID() {
        return this.serverPlayer.getId();
    }

    public boolean isInTab() {
        return this.showInTab;
    }

    public Location getLocation() {
        return this.location;
    }

    public boolean isInvisible() {
        return this.invis;
    }

    public Player getPlayerToFollow() {
        return this.toFollow;
    }

    public boolean followsPlayer() {
        return this.toFollow != null;
    }

    public Vector getFollowRelative() {
        return this.followRelatively;
    }

    public NPC updateRelativeLocation() {
        if (this.toFollow == null || this.followRelatively == null)
            return this;
        this.setLocation(this.toFollow.getLocation().clone().add(this.followRelatively));
        return this;
    }

    public NPC setToFollow(Player player, Vector relatively) {
        this.toFollow = player;
        this.followRelatively = relatively;
        return this;
    }

    public NPC setFollowRelative(Vector relatively) {
        this.followRelatively = relatively;
        return this;
    }

    public NPC setShowInTab(boolean tab) {
        this.showInTab = tab;
        return this;
    }

    public NPC setLocation(Location location) {
        this.serverPlayer.setPos(location.getX(), location.getY(), location.getZ());
        this.serverPlayer.turn(location.getY(), location.getPitch());
        this.location = location;
        for (Player player : this.players) {
            ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
            connection.send(new ClientboundTeleportEntityPacket(serverPlayer));
          //  connection.send(new ClientboundTeleportEntityPacket(serverPlayer));
            connection.send(new ClientboundRotateHeadPacket(serverPlayer, (byte) (int) (location.getYaw() * 256.0F / 360.0F)));
        }
        return this;
    }

    public NPC setInvisible(boolean invis) {
        this.invis = invis;
        return this;
    }


    public NPC setArmorSet(ItemStack helm, ItemStack chest, ItemStack legs, ItemStack boots) {

        List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> armor = new ArrayList<>();
        armor.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(helm)));
        armor.add(new Pair<>(EquipmentSlot.CHEST, CraftItemStack.asNMSCopy(chest)));
        armor.add(new Pair<>(EquipmentSlot.LEGS, CraftItemStack.asNMSCopy(legs)));
        armor.add(new Pair<>(EquipmentSlot.FEET, CraftItemStack.asNMSCopy(boots)));

        for (Player player : this.players) {
            ((CraftPlayer) player).getHandle().connection.send(new ClientboundSetEquipmentPacket(serverPlayer.getId(), armor));
        }

        return this;
    }

    public boolean canSee(Player player) {
        return this.players.contains(player);
    }

    public void addPlayer(Player player) {
        if (this.players.contains(player))
            return;
        this.players.add(player);
        this.spawn(player);
    }

    public void removePlayer(Player player) {
        if (this.players.contains(player))
            return;
        this.players.remove(player);
        this.despawn(player);
    }

    public void remove() {
        for (Player lp : this.players)
            if (lp != null && lp.isOnline())
                this.despawn(lp);
        this.players = new ArrayList<Player>();
    }

    private void spawn(Player player) {
        if (player == null) return;

        final ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        if (this.showInTab)
            connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.ADD_PLAYER, serverPlayer));
        connection.send(new ClientboundAddEntityPacket(serverPlayer));
        connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_GAME_MODE, serverPlayer));

        if (this.invis) {
            connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.UPDATE_LATENCY, serverPlayer));
            try {
                WrappedDataWatcher watcher = new WrappedDataWatcher();
                watcher.setObject(0, false ? (byte) 0 : 0x20);
                WrapperPlayServerEntityMetadata update = new WrapperPlayServerEntityMetadata();
                update.setEntityId(this.serverPlayer.getId());
                update.setEntityMetadata(watcher.getWatchableObjects());
                ProtocolLibrary.getProtocolManager().sendServerPacket(player, update.getHandle());
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void despawn(Player player) {
        if (player == null) return;

        final ServerGamePacketListenerImpl connection = ((CraftPlayer) player).getHandle().connection;
        connection.send(new ClientboundRemoveEntitiesPacket(this.serverPlayer.getId()));
        if (this.showInTab)
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(LibPlugin.getInstance(), () -> connection.send(new ClientboundPlayerInfoPacket(ClientboundPlayerInfoPacket.Action.REMOVE_PLAYER, this.serverPlayer)), 5);
    }

    private ServerPlayer getServerPlayer() {
        return this.serverPlayer;
    }
}