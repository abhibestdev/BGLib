package us.blockgame.lib.menu.pagination;

import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import us.blockgame.lib.menu.Button;
import us.blockgame.lib.util.CC;
import us.blockgame.lib.util.ItemBuilder;

import java.util.Arrays;

@AllArgsConstructor
public class PageButton extends Button {

    private int mod;
    private PaginatedMenu menu;

    @Override
    public ItemStack getButtonItem(Player player) {
        if (this.mod > 0) {
            if (hasNext(player)) {
                return new ItemBuilder(Material.REDSTONE_TORCH)
                        .setName(CC.GREEN + "Next Page")
                        .setLore(Arrays.asList(
                                CC.YELLOW + "Click here to jump",
                                CC.YELLOW + "to the next page."
                        ))
                        .toItemStack();
            } else {
                return new ItemBuilder(Material.LEVER)
                        .setName(CC.GRAY + "Next Page")
                        .setLore(Arrays.asList(
                                CC.YELLOW + "There is no available",
                                CC.YELLOW + "next page."
                        ))
                        .toItemStack();
            }
        } else {
            if (hasPrevious(player)) {
                return new ItemBuilder(Material.REDSTONE_TORCH)
                        .setName(CC.GREEN + "Previous Page")
                        .setLore(Arrays.asList(
                                CC.YELLOW + "Click here to jump",
                                CC.YELLOW + "to the previous page."
                        ))
                        .toItemStack();
            } else {
                return new ItemBuilder(Material.LEVER)
                        .setName(CC.GRAY + "Previous Page")
                        .setLore(Arrays.asList(
                                CC.YELLOW + "There is no available",
                                CC.YELLOW + "previous page."
                        ))
                        .toItemStack();
            }
        }
    }

    @Override
    public void clicked(Player player, ClickType clickType) {
        if (this.mod > 0) {
            if (hasNext(player)) {
                this.menu.modPage(player, this.mod);
            }
        } else {
            if (hasPrevious(player)) {
                this.menu.modPage(player, this.mod);
            }
        }
    }

    private boolean hasNext(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return this.menu.getPages(player) >= pg;
    }

    private boolean hasPrevious(Player player) {
        int pg = this.menu.getPage() + this.mod;
        return pg > 0;
    }

}
