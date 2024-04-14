package org.dcrubik.rubixianranks.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.dcrubik.rubixianranks.storage.Rank;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;


public class NametagManager {

    public static void setNametag(Player player){
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());

        Arrays.stream(Rank.values())
                .forEach(rank -> {
                    Team team = player.getScoreboard().registerNewTeam(rank.name());
                    team.setPrefix(rank.getPrefix());
                });

        Bukkit.getOnlinePlayers().stream()
                .filter(target -> !player.getUniqueId().equals(target.getUniqueId()))
                .forEach(target -> player.getScoreboard().getTeam(RankManager.getRank(target).name()).addEntry(target.getName()));
    }

    public static void newTag(Player player){
        Rank rank = RankManager.getRank(player);

        Bukkit.getOnlinePlayers().stream()
                .forEach(target -> target.getScoreboard().getTeam(rank.name()).addEntry(player.getName()));
    }

    public static void removeTag(Player player){
        Bukkit.getOnlinePlayers().forEach(target -> {
            Team entryTeam = target.getScoreboard().getEntryTeam(player.getName());
            if (entryTeam != null) entryTeam.removeEntry(player.getName());
        });
    }
}
