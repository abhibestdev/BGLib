package us.blockgame.lib.economy;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public class EconomyWallet {

    private Map<EconomyType, Double> economyTypeDoubleMap;

    public EconomyWallet() {
        //Initialize map
        economyTypeDoubleMap = Maps.newHashMap();
    }

    //Get balance of current economy type, return 0 by default
    public double getBalance(EconomyType economyType) {
        return economyTypeDoubleMap.getOrDefault(economyType, 0D);
    }

    //Set the balance of economy type
    public void setBalance(EconomyType economyType, double balance) {
        economyTypeDoubleMap.put(economyType, balance);
    }
}
