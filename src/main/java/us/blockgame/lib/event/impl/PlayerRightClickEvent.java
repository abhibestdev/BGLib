package us.blockgame.lib.event.impl;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import us.blockgame.lib.event.PlayerEvent;

public class PlayerRightClickEvent extends PlayerEvent implements Cancellable {

   @Getter private boolean air;
   @Getter private Block clickedBlock;
   @Setter @Getter private boolean cancelled;

   public PlayerRightClickEvent(Player player, boolean air, Block clickedBlock) {
       super(player);
       this.air = air;
       this.clickedBlock = clickedBlock;
   }

}
