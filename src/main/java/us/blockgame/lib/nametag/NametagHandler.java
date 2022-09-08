package us.blockgame.lib.nametag;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NametagHandler {

    //Method to update nametag for player
    public void setPrefix(Player updateFor, Player player, String prefix) {
        Scoreboard scoreboard = updateFor.getScoreboard();

        //Make sure scoreboard exists
        if (scoreboard == null) return;

        //See if player already has a team with that prefix
        Team team = scoreboard.getTeams().stream().filter(t -> t.getPrefix().equalsIgnoreCase(prefix)).findFirst().orElse(null);

        //If player is already on the team, ignore
        if (team != null && team.getEntries().contains(player.getName())) return;

        //Remove from any teams they might be on
        resetPrefix(updateFor, player);

        //If team doesn't exist, create a new one
        if (team == null) {
            team = scoreboard.registerNewTeam(prefix);
            team.setPrefix(prefix);
        }
        //Add player to team
        team.addEntry(player.getName());
        return;
    }

    //Method to reset nametag for player
    public void resetPrefix(Player updateFor, Player player) {
        //Check if player is on a team
        Team entryTeam = getEntryTeam(updateFor, player);
        if (entryTeam != null) {
            //Remove player from previous team
            entryTeam.removeEntry(player.getName());
        }
    }

    public String getPrefix(Player player, Player toCheck) {
        Team entryTeam = getEntryTeam(player, toCheck);

        //Return empty prefix if entry team wasn't found
        if (entryTeam == null) return "";

        //Return team prefix
        return entryTeam.getPrefix();
    }

    //Method to get a team a player might be on
    public Team getEntryTeam(Player player, Player searchingFor) {
        Scoreboard scoreboard = player.getScoreboard();

        //Make sure scoreboard exists
        if (scoreboard == null) return null;

        //Look for team that has the other player's entry
        Team team = scoreboard.getTeams().stream().filter(t -> t.getEntries().contains(searchingFor.getName())).findFirst().orElse(null);
        return team;
    }
}
