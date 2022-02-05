package com.notryden.votesleep.commands;

import com.notryden.votesleep.Voting;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVSDeny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("vsdeny")) {
            Voting voting = new Voting();
            if (!(sender instanceof Player)) Bukkit.getLogger().warning("The server console cannot vote.");
            else if (CommandVS.getActive()){
                Player player = (Player) sender;
//                LEGACY CODE!!!!
//                VoteSleep.deny(player.getName());
//                System.out.println(player.getName());
                voting.deny(player.getName());
            } else {
                sender.sendMessage(ChatColor.RED + "Vote is not active");
            }
            return true;
        }
        return false;
    }
}
