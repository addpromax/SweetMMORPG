package top.mrxiaom.sweet.mmorpg.comp;

import io.lumine.mythic.lib.MythicLib;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.ResourceData;
import top.mrxiaom.sweet.mmorpg.api.StatType;

public class Placeholders extends PlaceholderExpansion {
    SweetMMORPG plugin;
    public Placeholders(SweetMMORPG plugin) {
        this.plugin = plugin;
    }
    @Override
    public @NotNull String getIdentifier() {
        return plugin.getDescription().getName().toLowerCase();
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        ResourceData data = plugin.getPlayerDatabase().getOrCached(player);
        if (data == null) return "null";
        switch (params.toLowerCase()) {
            case "stamina":
                return MythicLib.plugin.getMMOConfig().decimal.format(data.getStamina());
            case "max_stamina":
                return MythicLib.plugin.getMMOConfig().decimal.format(data.getStat(StatType.MAX_STAMINA));
            case "mana_regen":
                return MythicLib.plugin.getMMOConfig().decimal.format(data.getStat(StatType.MANA_REGENERATION));
            case "stamina_regen":
                return MythicLib.plugin.getMMOConfig().decimal.format(data.getStat(StatType.STAMINA_REGENERATION));
            default:
                return null;
        }
    }
}
