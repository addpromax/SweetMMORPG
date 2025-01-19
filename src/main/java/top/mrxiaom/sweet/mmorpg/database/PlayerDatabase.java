package top.mrxiaom.sweet.mmorpg.database;

import org.bukkit.OfflinePlayer;
import top.mrxiaom.pluginbase.database.IDatabase;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.ResourceData;
import top.mrxiaom.sweet.mmorpg.func.AbstractPluginHolder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class PlayerDatabase extends AbstractPluginHolder implements IDatabase {
    private String table;
    public PlayerDatabase(SweetMMORPG plugin) {
        super(plugin);
    }

    public ResourceData get(OfflinePlayer player) {
        return get(player.getUniqueId());
    }

    public ResourceData get(UUID uuid) {
        throw new IllegalStateException("Not yet implemented");
    }

    @Override
    public void reload(Connection conn, String prefix) throws SQLException {
        table = (prefix + "playerdata").toLowerCase();
        // TODO: 设计数据表
    }
}
