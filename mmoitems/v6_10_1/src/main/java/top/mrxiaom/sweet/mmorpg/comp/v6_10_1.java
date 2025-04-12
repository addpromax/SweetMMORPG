package top.mrxiaom.sweet.mmorpg.comp;

import net.Indyuce.mmoitems.api.player.PlayerData;
import org.bukkit.entity.Player;

public class v6_10_1 {
    public void resolveLevelChange(Player player) {
        PlayerData.get(player).resolveModifiersLater();
    }
}
