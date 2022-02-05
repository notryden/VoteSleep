package com.notryden.votesleep;

import com.notryden.votesleep.commands.CommandVS;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class Voting {
    private static List<String> listOfVotingPlayers = new ArrayList<>();
    private static int playerAcceptCount = 0;
    private static int playerDenyCount = 0;
    private static int playerPassCountMax = getPlayerCount();
    private static int playerPassCountMin = (int) Math.ceil((playerPassCountMax + 1) * 0.5);

    public void accept(String name) {
        if (!Voting.hasPlayerVoted(name) && CommandVS.getActive()) {
            playerAcceptCount++;
            vote(name);
        } else if (Voting.hasPlayerVoted(name)) {
            broadcastAlreadyVoted(name);
        } else {
            broadcastVoteNotStarted(name);
        }
    }
    public void deny(String name) {
        if (!Voting.hasPlayerVoted(name) && CommandVS.getActive()) {
            playerDenyCount++;
            vote(name);
        } else if (Voting.hasPlayerVoted(name)) {
            broadcastAlreadyVoted(name);
        } else if (!CommandVS.getActive()) {
            broadcastVoteNotStarted(name);
        }
    }
    private void vote(String name) {
        Voting voteTimer = new Voting();
        listOfVotingPlayers.add(name);
        broadcastVoteCount();
        if (playerAcceptCount == playerPassCountMin) {
            broadcastVotePassed();
            progressDay(Bukkit.getServer());
            CommandVS.setActive(false);
            listOfVotingPlayers.clear();
            playerAcceptCount = 0;
            playerDenyCount = 0;

        }
        if (playerDenyCount == playerPassCountMin) {
            broadcastVoteNotPassed(name);
            CommandVS.setActive(false);
            listOfVotingPlayers.clear();
            playerAcceptCount = 0;
            playerDenyCount = 0;
        }
        voteTimer();
    }
    public void voteTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (CommandVS.getActive())
                    cancelVote();
                else {
                    this.cancel();
                }
            }
        }.runTaskLater(Main.getInstance(), 300L);
    }
    private void cancelVote() {
        listOfVotingPlayers.clear();
        CommandVS.setActive(false);
        broadcastVoteFailed();
    }

    private static boolean hasPlayerVoted(String name) {
        if (listOfVotingPlayers.contains(name))
            return true;
        else
            return false;
    }
    private static int getPlayerCount() {
        int playerCount = 0;
        for (Player p : Bukkit.getOnlinePlayers())
            playerCount++;
        return playerCount;
    }

    private static void broadcastVoteCount() {
        Bukkit.broadcastMessage(
                ChatColor.GREEN + Integer.toString(playerAcceptCount) + ChatColor.RESET + "/"
                +  ChatColor.RED + playerDenyCount + ChatColor.RESET + "/" +
                ChatColor.LIGHT_PURPLE + playerPassCountMax
        );
    }
    private static void broadcastAlreadyVoted(String name) {
        Player player = Bukkit.getPlayer(name);
        assert player != null;
        player.sendMessage(ChatColor.DARK_RED + "You have already voted!");
    }
    private static void broadcastVoteNotStarted(String name) {
        Player player = Bukkit.getPlayer(name);
        assert player != null;
        player.sendMessage(ChatColor.DARK_RED + "Vote has not started!");
    }
    private static void broadcastVoteNotPassed(String name) {
        Bukkit.broadcastMessage(ChatColor.YELLOW + "Vote has not passed.");
    }
    private static void broadcastVotePassed() {
        Bukkit.broadcastMessage(ChatColor.GREEN + "Vote has passed!");
    }
    private static void broadcastVoteFailed() {
        Bukkit.broadcastMessage(ChatColor.RED + "The vote has failed because it took too long.");
    }
    private static void progressDay(Server s) {
        Objects.requireNonNull(s.getWorld("world")).setTime(1000);
    }
    public static long getTime() {
        return Objects.requireNonNull(Bukkit.getServer().getWorld("world")).getTime();
    }
}
