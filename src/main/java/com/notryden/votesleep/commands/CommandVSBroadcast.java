package com.notryden.votesleep.commands;

import com.notryden.votesleep.Voting;
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
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class CommandVSBroadcast implements CommandExecutor  {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("vsbroadcast") && Voting.getTime() >= 13000) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                CommandVS.setActive(true);
                TextComponent acceptMessage = new TextComponent(
                        ChatColor.GREEN + "\nACCEPT\n" + ChatColor.RESET + " (click or use /vsaccept)\n"
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

                player.spigot().sendMessage(acceptMessage);
                player.spigot().sendMessage(denyMessage);

                return true;
            }
            if (!(sender instanceof Player)) {
                Bukkit.getLogger().warning("The server console cannot vote.");
                return true;
            }
            if (!CommandVS.getActive()) {
                TextComponent activeError = new TextComponent("You cannot use this command without first starting a vote.");
                activeError.setColor(ChatColor.DARK_RED);
                activeError.setItalic(true);
                sender.spigot().sendMessage(activeError);
                return true;
            }
        }
        return true;
    }
}