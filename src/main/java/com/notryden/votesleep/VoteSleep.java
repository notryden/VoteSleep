package com.notryden.votesleep;

import com.notryden.votesleep.commands.CommandVS;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.*;

public class VoteSleep {
    public static Map<String, Boolean> playerListVoted = new HashMap<>();
    static boolean votePassed;
    static int playerAcceptCount = 0;
    static int playerPassedCountMax = getPlayerCount();
    static int playerPassedCountMin = (int) Math.ceil((playerPassedCountMax + 1) * 0.5);
    static int possibleSubtraction = -(playerPassedCountMin % 2); // because even numbers of players exist
    static int playerDenyCount = 0;


    public static void accept(String uuid) {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        playerListVoted.put(uuid, false);
        if (!playerListVoted.get(uuid) && CommandVS.getActive()) {
            if (!playerListVoted.get(uuid))
                playerListVoted.put(uuid, Boolean.TRUE);
            playerAcceptCount++;
            System.out.println(playerPassedCountMax);
            System.out.println(playerPassedCountMin);
            assert player != null;
            player.sendMessage(ChatColor.AQUA + "Vote submitted.");
            Bukkit.broadcastMessage(
                ChatColor.GREEN + Integer.toString(playerAcceptCount) + ChatColor.RESET + "/"
                + ChatColor.RED + playerDenyCount + ChatColor.RESET + "/" +
                ChatColor.LIGHT_PURPLE + playerPassedCountMax);
            System.out.println(playerListVoted.get(uuid));
            System.out.println(!(playerListVoted.get(uuid)));
            System.out.println(playerListVoted);
            if (playerAcceptCount == playerPassedCountMin) {
                playerListVoted.clear();
                Bukkit.broadcastMessage(ChatColor.GREEN + "Vote has passed!");
                playerAcceptCount = 0;
                CommandVS.setActive(false);
            }
            finalizeVote();
        } else if (!CommandVS.getActive()) {
            assert player != null;
            player.sendMessage(ChatColor.DARK_RED + "You have already voted!");
            System.out.println(playerListVoted);
        } else {
            assert player != null;
            player.sendMessage(ChatColor.DARK_RED + "Vote has not started!");
        }
    }
    public static void deny(String uuid) {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        playerListVoted.put(uuid, false);
        if (!playerListVoted.get(uuid) && CommandVS.getActive()) {
            if (!playerListVoted.get(uuid))
                playerListVoted.put(uuid, Boolean.TRUE);
            playerDenyCount++;
            assert player != null;
            player.sendMessage(ChatColor.AQUA + "Vote submitted.");
            Bukkit.broadcastMessage(
                ChatColor.GREEN + Integer.toString(playerAcceptCount) + ChatColor.RESET + "/"
                +  ChatColor.RED + playerDenyCount + ChatColor.RESET + "/" +
                ChatColor.LIGHT_PURPLE + playerPassedCountMax);
            System.out.println(playerListVoted);
            if (playerDenyCount == playerPassedCountMin + possibleSubtraction) {
                playerListVoted.clear();
                Bukkit.broadcastMessage(ChatColor.GREEN + "Vote has passed!");
                playerDenyCount = 0;
                CommandVS.setActive(false);
            }
            finalizeVote();
        } else if (!CommandVS.getActive()){
            assert player != null;
            player.sendMessage(ChatColor.DARK_RED + "You have already voted!");
            System.out.println(playerListVoted);
        } else {
            assert player != null;
            player.sendMessage(ChatColor.DARK_RED + "Vote has not started!");
        }
    }
    public static void finalizeVote() {
        if (playerAcceptCount >= playerPassedCountMin) {
            votePassed = true;
            progressDay(Bukkit.getServer());
            votePassed = false;
        }
        else if (playerDenyCount >= playerPassedCountMin + possibleSubtraction) {
            votePassed = false;
            Bukkit.getLogger().info("Vote did not pass!");
        }
        if (!CommandVS.getActive()) {
            playerAcceptCount = 0;
            playerDenyCount = 0;
            progressDay(Bukkit.getServer());
        }
    }
    public static void progressDay(Server s) {
        Objects.requireNonNull(s.getWorld("world")).setTime(1000);
    }
    public static int getPlayerCount() {
        int playerCount = 0;
        for (Player p : Bukkit.getOnlinePlayers())
            playerCount++;
        return playerCount;
    }
    public static long getTime() {
        return Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getTime();
    }
    public static boolean isVotesWhole() {
        return playerAcceptCount + playerDenyCount == playerPassedCountMax;
    }
}