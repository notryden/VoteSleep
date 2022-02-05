package com.notryden.votesleep.commands;

import com.notryden.votesleep.VoteSleep;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVS implements CommandExecutor {
    private static boolean active = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("vs")) {
            if (!(sender instanceof Player)) {
                Bukkit.getLogger().warning("The server console cannot vote.");
                return true;
            } else if (VoteSleep.getTime() >= 13000 && !getActive()) {
                Player player = (Player) sender;
                TextComponent vsMessage = new TextComponent("Click to begin sleeping vote!");
                vsMessage.setBold(true);
                vsMessage.setColor(ChatColor.GOLD);
                vsMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vsbroadcast"));
                vsMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("CLICK TO VOTE TO SLEEP").color(ChatColor.RED).create()));
                Bukkit.getServer().spigot().broadcast(vsMessage);
                setActive(true);
                return true;
            } else if (VoteSleep.getTime() < 13000) {
                sender.sendMessage(ChatColor.DARK_RED + "\nTo start a vote, the time must be at least night (13000).");
                return true;
            } else if (getActive()) {
                sender.sendMessage(ChatColor.DARK_RED + "A vote has already started!");
                return true;
            }
            return true;
        }
        return false;
    }
    public static boolean getActive() {
        return active;
    }
    public static void setActive(boolean active) {
        CommandVS.active = active;
    }
}