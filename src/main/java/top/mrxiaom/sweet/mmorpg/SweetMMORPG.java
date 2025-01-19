package top.mrxiaom.sweet.mmorpg;
        
import net.Indyuce.mmoitems.MMOItems;
import org.bukkit.Bukkit;
import top.mrxiaom.pluginbase.BukkitPlugin;
import top.mrxiaom.sweet.mmorpg.api.StatType;
import top.mrxiaom.sweet.mmorpg.comp.MMOHook;
import top.mrxiaom.sweet.mmorpg.comp.Placeholders;
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
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders(this).register();
        }
        MMOItems.plugin.setRPG(new MMOHook());
        Bukkit.getOnlinePlayers().forEach(playerDatabase::load);
        getLogger().info("SweetMMORPG 加载完毕");
    }
}
