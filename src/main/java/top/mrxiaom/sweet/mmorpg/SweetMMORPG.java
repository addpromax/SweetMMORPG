package top.mrxiaom.sweet.mmorpg;
        
import org.jetbrains.annotations.NotNull;
import top.mrxiaom.pluginbase.BukkitPlugin;
import top.mrxiaom.pluginbase.EconomyHolder;

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


    @Override
    protected void beforeEnable() {
        options.registerDatabase(
                // 在这里添加数据库 (如果需要的话)
        );
    }

    @Override
    protected void afterEnable() {
        getLogger().info("SweetMMORPG 加载完毕");
    }
}
