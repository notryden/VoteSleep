package com.notryden.votesleep.commands;

import com.notryden.votesleep.Voting;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandVSAccept implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("vsaccept")) {
            Voting voting = new Voting();
            if (!(sender instanceof Player)) Bukkit.getLogger().warning("The server console cannot vote.");
            else if (CommandVS.getActive()){
                Player player = (Player) sender;
                voting.accept(player.getName());
            } else {
                sender.sendMessage(ChatColor.RED + "Vote is not active");
            }
            return true;
        }
        return false;
    }
}
