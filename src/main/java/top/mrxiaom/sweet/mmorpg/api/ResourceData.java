package top.mrxiaom.sweet.mmorpg.api;

import io.lumine.mythic.lib.api.player.MMOPlayerData;
import io.lumine.mythic.lib.api.stat.modifier.StatModifier;
import org.bukkit.Bukkit;
import top.mrxiaom.sweet.mmorpg.api.event.ResourceRegainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ResourceData {
    private final MMOPlayerData data;

    private double mana;
    private double stamina;

    private final List<StatModifier> registeredModifiers = new ArrayList<>();

    public ResourceData(MMOPlayerData data, double mana, double stamina) {
        this.data = data;
        this.mana = mana;
        this.stamina = stamina;
        registerModifiers();
    }

    public void unregisterModifiers() {
        for (StatModifier modifier : registeredModifiers) {
            modifier.unregister(data);
        }
        registeredModifiers.clear();
    }

    public void registerModifiers() {
        for (StatType stat : StatType.values()) {
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

    public double getMana() {
        return mana;
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

    public void giveMana(double value) {
        giveMana(value, ResourceRegainReason.PLUGIN);
    }

    public void giveMana(double value, ResourceRegainReason reason) {
        ResourceRegainEvent called = new ResourceRegainEvent(this, value, reason, ResourceType.MANA);
        Bukkit.getPluginManager().callEvent(called);
        if (!called.isCancelled())
            setMana(mana + called.getAmount());
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

    public void setMana(double value) {
        mana = Math.max(0, Math.min(getStat("MAX_MANA"), value));
    }

    public void setStamina(double value) {
        stamina = Math.max(0, Math.min(getStat(StatType.MAX_STAMINA), value));
    }
}