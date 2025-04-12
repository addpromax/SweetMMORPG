package top.mrxiaom.sweet.mmorpg.comp;

import org.bukkit.entity.Player;
import top.mrxiaom.pluginbase.utils.Util;

import java.util.function.Consumer;

public class LevelChangeResolver {
    private final Consumer<Player> consumer;
    public LevelChangeResolver() {
        if (Util.isPresent("net.Indyuce.mmoitems.api.player.inventory.InventoryUpdateHandler")) {
            v6_10 mmoitems = new v6_10();
            consumer = mmoitems::resolveLevelChange;
        } else {
            v6_10_1 mmoitems = new v6_10_1();
            consumer = mmoitems::resolveLevelChange;
        }
    }

    public void resolve(Player player) {
        consumer.accept(player);
    }
}
