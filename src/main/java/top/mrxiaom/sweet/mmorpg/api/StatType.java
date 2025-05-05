package top.mrxiaom.sweet.mmorpg.api;

import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.stat.type.ItemStat;
import org.bukkit.configuration.ConfigurationSection;
import top.mrxiaom.sweet.mmorpg.stat.ManaRegeneration;
import top.mrxiaom.sweet.mmorpg.stat.MaxStamina;
import top.mrxiaom.sweet.mmorpg.stat.StaminaRegeneration;

import java.util.HashMap;
import java.util.Map;

public enum StatType {
    MAX_STAMINA(new MaxStamina()),
    MANA_REGENERATION(new ManaRegeneration()),
    STAMINA_REGENERATION(new StaminaRegeneration());

    private static final Map<StatType, Double> baseValues = new HashMap<>();
    private final ItemStat<?, ?> stat;

    private StatType(ItemStat<?, ?> stat) {
        this.stat = stat;
    }

    public double getBase() {
        return baseValues.getOrDefault(this, 0.0);
    }

    public void registerStat() {
        if (stat != null)
            MMOItems.plugin.getStats().register(stat);
    }

    public static void reloadConfig(ConfigurationSection config) {
        baseValues.clear();
        for (StatType type : values()) {
            String key = "default." + type.name().toLowerCase().replace("_", "-");
            baseValues.put(type, config.getDouble(key));
        }
    }
}