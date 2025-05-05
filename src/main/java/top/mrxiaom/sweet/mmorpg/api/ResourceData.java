package top.mrxiaom.sweet.mmorpg.api;

import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.StatInstance;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import org.bukkit.Bukkit;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.event.ResourceRegainEvent;
import top.mrxiaom.sweet.mmorpg.database.PlayerDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourceData {
    private final MMOPlayerData data;

    private double stamina;

    private final List<StatModifier> registeredModifiers = new ArrayList<>();

    public ResourceData(MMOPlayerData data, Double mana) {
        this.data = data;
        registerModifiers();
        this.setStamina(stamina);
    }

    public void unregisterModifiers() {
        for (StatModifier modifier : registeredModifiers) {
            modifier.unregister(data);
        }
        registeredModifiers.clear();
    }

    public void registerModifiers() {
        StatMap statMap = data.getStatMap();
        for (StatType stat : StatType.values()) {
            StatInstance inst = statMap.getInstance(stat.name());
            List<StatModifier> modifiers = new ArrayList<>(inst.getModifiers());
            for (StatModifier modifier : modifiers) {
                if (modifier.getKey().equals("manaAndStamina")) {
                    modifier.unregister(data);
                }
            }
            modifiers.clear();
            StatModifier modifier = new StatModifier("manaAndStamina", stat.name(), stat.getBase());
            modifier.register(data);
            registeredModifiers.add(modifier);
        }
    }

    public MMOPlayerData toMythicLib() {
        return data;
    }

    public UUID getUniqueId() {
        return data.getUniqueId();
    }

    public double getStamina() {
        return stamina;
    }

    public double getStat(StatType type) {
        return getStat(type.name());
    }

    public double getStat(String type) {
        return data.getStatMap().getStat(type);
    }

    public void giveStamina(double value) {
        giveStamina(value, ResourceRegainReason.OTHER);
    }

    public void giveStamina(double value, ResourceRegainReason reason) {
        ResourceRegainEvent called = new ResourceRegainEvent(this, value, reason, ResourceType.STAMINA);
        Bukkit.getPluginManager().callEvent(called);
        if (!called.isCancelled())
            setStamina(stamina + called.getAmount());
    }

    public void setStamina(Double value) {
        double max = getStat(StatType.MAX_STAMINA);
        if (value == null) {
            PlayerDatabase db = SweetMMORPG.getInstance().getPlayerDatabase();
            value = db.getStaminaRatio() * max;
        }
        stamina = Math.max(0, Math.min(max, value));
    }
}