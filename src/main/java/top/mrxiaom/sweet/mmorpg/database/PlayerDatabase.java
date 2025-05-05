package top.mrxiaom.sweet.mmorpg.database;

import io.lumine.mythic.lib.api.player.MMOPlayerData;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.Nullable;
import top.mrxiaom.pluginbase.DatabaseHolder;
import top.mrxiaom.pluginbase.database.IDatabase;
import top.mrxiaom.pluginbase.utils.Util;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.ResourceData;
import top.mrxiaom.sweet.mmorpg.func.AbstractPluginHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class PlayerDatabase extends AbstractPluginHolder implements IDatabase, Listener {
    private String table;
    Map<UUID, ResourceData> cache = new HashMap<>();
    double staminaRatio; // 移除 manaRatio

    public PlayerDatabase(SweetMMORPG plugin) {
        super(plugin);
        registerEvents();
        register();
    }

    // 移除 getManaRatio 方法

    public double getStaminaRatio() {
        return staminaRatio;
    }

    @Override
    public int priority() {
        return 1001;
    }

    @Override
    public void reloadConfig(MemoryConfiguration config) {
        // 移除 manaRatio 配置项
        staminaRatio = config.getDouble("default-ratio.stamina", 75) / 100.0;
        for (ResourceData data : cache.values()) {
            data.unregisterModifiers();
            data.registerModifiers();
        }
    }

    // ... 事件处理保持不变 ...

    @Override
    public void reload(Connection conn, String prefix) throws SQLException {
        table = (prefix + "playerdata").toLowerCase();
        try (PreparedStatement ps = conn.prepareStatement(
                "CREATE TABLE if NOT EXISTS `" + table + "`(" +
                        "`uuid` VARCHAR(48) PRIMARY KEY," +
                        "`name` VARCHAR(48)," +
                        // 移除 mana 字段
                        "`stamina` DECIMAL(10, 2)" +
                        ");"
        )) {
            ps.execute();
        }
    }

    public ResourceData load(UUID uuid) {
        boolean error = false;
        try (Connection conn = plugin.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM `" + table + "` WHERE `uuid`=?;"
             )) {
            ps.setString(1, uuid.toString());
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    // 移除 mana 读取
                    double stamina = resultSet.getDouble("stamina");
                    ResourceData data = getOrCreateData(uuid, stamina, true);
                    data.setStamina(stamina);
                    return data;
                }
            }
        } catch (SQLException e) {
            error = true;
            warn(e);
        }
        ResourceData data = getOrCreateData(uuid, null, false);
        if (!error) save(data);
        return data;
    }

    public Collection<ResourceData> getCaches() {
        return Collections.unmodifiableCollection(cache.values());
    }

    @Nullable
    public ResourceData getOrCached(OfflinePlayer player) {
        if (player == null) return null;
        return getOrCached(player.getUniqueId());
    }

    private ResourceData getOrCreateData(UUID uuid, Double defaultStamina, boolean setIfCached) {
        ResourceData data = cache.get(uuid);
        if (data == null) {
            // 移除 mana 参数
            data = new ResourceData(MMOPlayerData.get(uuid), defaultStamina);
            cache.put(uuid, data);
        } else if (setIfCached) {
            data.setStamina(defaultStamina);
        }
        return data;
    }

    public ResourceData getOrCached(UUID uuid) {
        ResourceData cached = cache.get(uuid);
        if (cached != null) {
            return cached;
        }
        return load(uuid);
    }

    public void save(ResourceData data) {
        UUID uuid = data.getUniqueId();
        if (!cache.containsKey(uuid)) {
            data.unregisterModifiers();
        }
        String name = Util.getOfflinePlayer(uuid).map(OfflinePlayer::getName).orElse(null);
        double stamina = data.getStamina();
        try (Connection conn = plugin.getConnection()) {
            // 移除 mana 参数
            save(conn, uuid, name == null ? "" : name, stamina);
        } catch (SQLException e) {
            warn(e);
        }
    }

    private void save(Connection conn, UUID uuid, String name, double stamina) throws SQLException {
        DatabaseHolder holder = plugin.options.database();
        if (holder.isSQLite()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    // 调整 SQLite 语句
                    "INSERT OR REPLACE INTO `" + table + "`(`uuid`,`name`,`stamina`) VALUES(?, ?, ?);"
            )) {
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setDouble(3, stamina); // 参数索引调整
                ps.execute();
            }
        } else if (holder.isMySQL()) {
            try (PreparedStatement ps = conn.prepareStatement(
                    // 调整 MySQL 语句
                    "INSERT INTO `" + table + "`(`uuid`,`name`,`stamina`) VALUES(?, ?, ?) " +
                            "on duplicate key update `name`=?, `stamina`=?;"
            )) {
                ps.setString(1, uuid.toString());
                ps.setString(2, name);
                ps.setDouble(3, stamina);
                // 更新部分参数
                ps.setString(4, name);
                ps.setDouble(5, stamina);
                ps.execute();
            }
        }
    }
}
