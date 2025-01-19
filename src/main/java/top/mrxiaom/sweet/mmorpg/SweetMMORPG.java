package top.mrxiaom.sweet.mmorpg;
        
import top.mrxiaom.pluginbase.BukkitPlugin;
import top.mrxiaom.sweet.mmorpg.api.StatType;
import top.mrxiaom.sweet.mmorpg.database.PlayerDatabase;

public class SweetMMORPG extends BukkitPlugin {
    public static SweetMMORPG getInstance() {
        return (SweetMMORPG) BukkitPlugin.getInstance();
    }

    public SweetMMORPG() {
        super(options()
                .bungee(false)
                .adventure(false)
                .database(true)
                .reconnectDatabaseWhenReloadConfig(false)
                .vaultEconomy(false)
                .scanIgnore("top.mrxiaom.sweet.mmorpg.libs")
        );
    }
    private PlayerDatabase playerDatabase;

    public PlayerDatabase getPlayerDatabase() {
        return playerDatabase;
    }

    @Override
    protected void beforeLoad() {
        for (StatType type : StatType.values())
            type.registerStat();
    }

    @Override
    protected void beforeEnable() {
        options.registerDatabase(
                playerDatabase = new PlayerDatabase(this)
        );
    }

    @Override
    protected void afterEnable() {
        getLogger().info("SweetMMORPG 加载完毕");
    }
}
