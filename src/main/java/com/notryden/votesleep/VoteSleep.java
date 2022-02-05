package com.notryden.votesleep;

import com.notryden.votesleep.commands.CommandVS;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.*;

public class VoteSleep {
    public static Map<String, Boolean> playerListVoted = new HashMap<>();
    static boolean hasVotePassed;
    static int playerAcceptCount = 0;
    static int playerPassedCountMax = getPlayerCount();
    static int playerPassedCountMin = (int) Math.ceil((playerPassedCountMax + 1) * 0.5);
    static int playerDenyCount = 0;


    public static void accept(String uuid) {
        Player player = Bukkit.getPlayer(UUID.fromString(uuid));
        System.out.println(player);
        playerListVoted.put(uuid, false);
        if (!playerListVoted.get(uuid) && CommandVS.getActive() && !hasVotePassed) {
            if (!playerListVoted.get(uuid))
                playerListVoted.put(uuid, Boolean.TRUE);
            playerAcceptCount++;
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
                CommandVS.setActive(false);
                progressDay(Bukkit.getServer());
                playerListVoted.clear();
                Bukkit.broadcastMessage(ChatColor.GREEN + "Vote has passed!");
                playerAcceptCount = 0;
                playerDenyCount = 0;
            } else if (playerDenyCount >= playerPassedCountMin) {
                CommandVS.setActive(false);
                playerListVoted.clear();
                Bukkit.broadcastMessage(ChatColor.RED + "Vote has not passed.");
                playerAcceptCount = 0;
                playerDenyCount = 0;
            }
            finalizeVote(true);
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
        if (!playerListVoted.get(uuid) && CommandVS.getActive() && !hasVotePassed) {
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
            if (playerDenyCount == playerPassedCountMin) {
                CommandVS.setActive(false);
                playerListVoted.clear();
                Bukkit.broadcastMessage(ChatColor.YELLOW + "Vote has not passed.");
                playerDenyCount = 0;
            }
            finalizeVote(false);
        } else if (!CommandVS.getActive()){
            assert player != null;
            player.sendMessage(ChatColor.DARK_RED + "You have already voted!");
            System.out.println(playerListVoted);
        } else {
            assert player != null;
            player.sendMessage(ChatColor.DARK_RED + "Vote has not started!");
        }
    }
    public static void finalizeVote(boolean b) {
        if (b) {
            if (playerAcceptCount >= playerPassedCountMin) {
                hasVotePassed = true;
                progressDay(Bukkit.getServer());
                System.out.println("it worked");
                hasVotePassed = false;
            }
        }
        if (!b) {
            if (playerDenyCount >= playerPassedCountMin) {
                hasVotePassed = false;
                Bukkit.getLogger().info("Vote did not pass!");
            }
        } else if (!CommandVS.getActive()) {
            playerAcceptCount = 0;
            playerDenyCount = 0;
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
}