package com.notryden.votesleep;

import com.notryden.votesleep.commands.CommandVS;
import com.notryden.votesleep.commands.CommandVSAccept;
import com.notryden.votesleep.commands.CommandVSDeny;
import com.notryden.votesleep.commands.CommandVSBroadcast;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        Objects.requireNonNull(this.getCommand("vs")).setExecutor(new CommandVS());
        Objects.requireNonNull(this.getCommand("vsbroadcast")).setExecutor(new CommandVSBroadcast());
        Objects.requireNonNull(this.getCommand("vsaccept")).setExecutor(new CommandVSAccept());
        Objects.requireNonNull(this.getCommand("vsdeny")).setExecutor(new CommandVSDeny());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main getInstance() {
        return instance;
    }
}