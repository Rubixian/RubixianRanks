package org.dcrubik.rubixianranks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.dcrubik.rubixianranks.managers.RankManager;
import org.dcrubik.rubixianranks.storage.Rank;

public class SetRankCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length != 2){
            player.sendMessage(ChatColor.RED + "Usage: /setrank <player> <rank>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null){
            player.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }
        Rank rank;
        try {
            rank = Rank.valueOf(args[0].toUpperCase());
        } catch (NullPointerException ex){
            player.sendMessage(ChatColor.RED + "The rank hasn't been found!");
            return true;
        }

        RankManager.setRank(rank , target);

        player.sendMessage(ChatColor.GREEN + "Successfully set " + target.getName() + "'s rank to " + rank.name() + "!");
        target.sendMessage(ChatColor.GREEN + "You are now " + rank.name() + "!");

        return true;

    }
}

