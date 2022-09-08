package us.blockgame.lib.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum EconomyType {

    DOLLARS("$", true),
    EUROS("€", true),
    POUNDS("£", true),
    POINTS("", false);

    @Getter private String symbol;
    @Getter private boolean transferable;
}
