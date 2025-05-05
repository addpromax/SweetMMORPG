package top.mrxiaom.sweet.mmorpg.func;

import org.bukkit.Bukkit;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.scheduler.BukkitTask;
import top.mrxiaom.pluginbase.func.AutoRegister;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.ResourceData;
import top.mrxiaom.sweet.mmorpg.api.ResourceRegainReason;
import top.mrxiaom.sweet.mmorpg.api.StatType;

@AutoRegister
public class StatRegenManager extends AbstractModule {
    BukkitTask task = null;
    long regenRate;
    public StatRegenManager(SweetMMORPG plugin) {
        super(plugin);
    }

    @Override
    public void reloadConfig(MemoryConfiguration config) {
        if (task != null) {
            task.cancel();
            task = null;
        }
        regenRate = config.getLong("regen-rate", 10L);
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (ResourceData data : plugin.getPlayerDatabase().getCaches()) {
                if (data.toMythicLib().isOnline()) {
                    data.giveStamina(data.getStat(StatType.STAMINA_REGENERATION) * regenRate / 20, ResourceRegainReason.REGENERATION);
                }
            }
        }, 100L, regenRate);
    }

    @Override
    public void onDisable() {
        if (task != null) {
            task.cancel();
            task = null;
        }
    }
}
