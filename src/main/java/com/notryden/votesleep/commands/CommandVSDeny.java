package com.notryden.votesleep.commands;

import com.notryden.votesleep.VoteSleep;
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
            if (!(sender instanceof Player)) Bukkit.getLogger().warning("The server console cannot vote.");
            else if (CommandVS.getActive()){
                Player player = (Player) sender;
                VoteSleep.deny(player.getUniqueId().toString());
                System.out.println(player.getUniqueId());
            } else {
                sender.sendMessage(ChatColor.RED + "Vote is not active");
            }
            return true;
        }
        return false;
    }
}
