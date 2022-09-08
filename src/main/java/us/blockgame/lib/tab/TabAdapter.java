package us.blockgame.lib.tab;

import io.github.thatkawaiisam.ziggurat.ZigguratAdapter;
import io.github.thatkawaiisam.ziggurat.ZigguratCommons;
import io.github.thatkawaiisam.ziggurat.utils.BufferedTabObject;
import io.github.thatkawaiisam.ziggurat.utils.TabColumn;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class TabAdapter implements ZigguratAdapter {

    public BGTab bgTab;

    @Override
    public String getHeader() {
        return bgTab.getHeader();
    }

    @Override
    public String getFooter() {
        return bgTab.getFooter();
    }

    @Override
    public Set<BufferedTabObject> getSlots(Player player) {
        HashSet<BufferedTabObject> bufferedTabObjects = new HashSet<>();

        //Organize custom tab slot system into a way ziggurat can read
        for (int slot : bgTab.getSlots(player).keySet()) {

            TabColumn tabColumn = null;
            String text = bgTab.getSlots(player).get(slot);

            //Find tab column from slot
            if (slot >= 1 && slot <= 20) {
                tabColumn = TabColumn.LEFT;
            } else if (slot > 20 && slot <= 40) {
                tabColumn = TabColumn.MIDDLE;
            } else if (slot >= 41 && slot <= 60) {
                tabColumn = TabColumn.RIGHT;
            } else if (slot >= 61) {
                tabColumn = TabColumn.FAR_RIGHT;
            }

            int newSlot = slot;

            //Calculate slot in column
            switch (tabColumn) {
                case MIDDLE: {
                    newSlot -= 20;
                    break;
                }
                case RIGHT: {
                    newSlot -= 40;
                    break;
                }
                case FAR_RIGHT: {
                    newSlot -= 60;
                    break;
                }
            }

            //Create BufferedTabObject
            bufferedTabObjects.add(new BufferedTabObject().column(tabColumn).slot(newSlot).ping(1).text(text).skin(ZigguratCommons.defaultTexture));
        }
        return bufferedTabObjects;
    }

}
