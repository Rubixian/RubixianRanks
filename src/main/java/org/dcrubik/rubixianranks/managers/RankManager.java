package org.dcrubik.rubixianranks.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.permissions.PermissionAttachment;
import org.dcrubik.rubixianranks.RubixianRanks;
import org.dcrubik.rubixianranks.storage.Rank;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.UUID;

public class RankManager {

    public static void setRank(Rank rank, Player player) {
        RubixianRanks main = RubixianRanks.getInstance();
        FileConfiguration config = main.getConfig();
        String uuid = player.getUniqueId().toString();

        if (config.contains(uuid)) {
            removePermissions(player);
        }
        config.set(uuid, rank.name());
        main.saveConfig();
        setPermissions(player, rank);
    }

    public static Rank getRank(Player player) {
        RubixianRanks main = RubixianRanks.getInstance();
        FileConfiguration config = main.getConfig();

        return Rank.valueOf(config.getString(player.getUniqueId().toString()));
    }

    public static void setPermissions(Player player, Rank rank) {
        RubixianRanks main = RubixianRanks.getInstance();
        UUID uuid = player.getUniqueId();

        PermissionAttachment attachment = player.addAttachment(main);
        RubixianRanks.getPermissions().put(uuid, attachment);

        Arrays.stream(rank.getPermissions())
                .map(perm -> perm.substring(0, Math.min(perm.length(), 16)))
                .forEach(truncatedPerm -> attachment.setPermission(truncatedPerm, true));

    }

    public static void removePermissions(Player player) {
        UUID uuid = player.getUniqueId();
        Rank rank = getRank(player);

        if (!RubixianRanks.getPermissions().containsKey(uuid)) {
            return;
        }

        PermissionAttachment attachment = RubixianRanks.getPermissions().get(uuid);

        if (attachment == null) {
            return;
        }

        Arrays.stream(rank.getPermissions()).filter(perm -> attachment.getPermissions().containsKey(perm)).forEach(attachment::unsetPermission);

        RubixianRanks.getPermissions().remove(uuid);
    }

}
