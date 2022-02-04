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

public class CommandVSBroadcast implements CommandExecutor  {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("vsbroadcast") && VoteSleep.getTime() >= 13000) {
            if (sender instanceof Player) {
                CommandVS.setActive(true);
                TextComponent acceptMessage = new TextComponent(
                        ChatColor.GREEN + "ACCEPT\n" + ChatColor.RESET + " (click or use /vsaccept)\n"
                );
                TextComponent denyMessage = new TextComponent(
                        ChatColor.RED + "DENY\n" + ChatColor.RESET + " (click or use /vsdeny)"
                );
                acceptMessage.setBold(true);
                denyMessage.setBold(true);
                acceptMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vsaccept"));
                acceptMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("Click to accept vote").bold(true).create()));
                denyMessage.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vsdeny"));
                denyMessage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new ComponentBuilder("Click to deny vote").bold(true).create()));
                Bukkit.getServer().spigot().broadcast(acceptMessage);
                Bukkit.getServer().spigot().broadcast(denyMessage);
                return true;
            } else {
                Bukkit.getLogger().warning("The server console cannot vote.");
            }
            if (!CommandVS.getActive()) {
                TextComponent activeError = new TextComponent("You cannot use this command without first starting a vote.");
                activeError.setColor(ChatColor.DARK_RED);
                activeError.setItalic(true);
                sender.spigot().sendMessage(activeError);
                return true;
            }
        }
        return false;
    }
}