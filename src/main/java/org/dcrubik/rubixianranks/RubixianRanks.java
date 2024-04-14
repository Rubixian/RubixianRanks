package org.dcrubik.rubixianranks;

import lombok.Getter;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.dcrubik.rubixianranks.commands.SetRankCommand;
import org.dcrubik.rubixianranks.commands.TestCommand;
import org.dcrubik.rubixianranks.listeners.PlayerListener;
import java.util.HashMap;
import java.util.UUID;


public final class RubixianRanks extends JavaPlugin {
    @Getter
    private static final HashMap<UUID, PermissionAttachment> permissions = new HashMap<>();
    @Getter
    private static RubixianRanks instance;

    @Override
    public void onEnable() {
        instance = this;

        this.getConfig().options().copyDefaults(true);
        this.saveConfig();

       this.registerListener();
       this.registerCommand();
    }

    private void registerListener(){
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    private void registerCommand(){
        this.getCommand("setrank").setExecutor(new SetRankCommand());
        this.getCommand("test").setExecutor(new TestCommand());
    }

    @Override
    public void onDisable() {
        permissions.clear();
    }

}
