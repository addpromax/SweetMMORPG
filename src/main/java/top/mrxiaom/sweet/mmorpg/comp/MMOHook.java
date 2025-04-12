package top.mrxiaom.sweet.mmorpg.comp;

import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.comp.rpg.RPGHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.ResourceData;

public class MMOHook implements RPGHandler, Listener {
    LevelChangeResolver levelChange = new LevelChangeResolver();
    @EventHandler
    public void a(PlayerLevelChangeEvent event) {
        levelChange.resolve(event.getPlayer());
    }

    public RPGPlayer getInfo(PlayerData data) {
        return new MMOPlayer(data);
    }

    @Override
    public void refreshStats(PlayerData data) {
    }

    public static class MMOPlayer extends RPGPlayer {
        private final ResourceData data;

        public MMOPlayer(PlayerData playerData) {
            super(playerData);

            this.data = SweetMMORPG.getInstance().getPlayerDatabase().getOrCached(playerData.getUniqueId());
        }

        public int getLevel() {
            return getPlayer().getLevel();
        }

        public String getClassName() {
            return "";
        }

        public double getMana() {
            return data.getMana();
        }

        public double getStamina() {
            return data.getStamina();
        }

        public void setMana(double value) {
            data.setMana(value);
        }

        public void setStamina(double value) {
            data.setStamina(value);
        }

        public void giveMana(double value) {
            data.giveMana(value);
        }

        public void giveStamina(double value) {
            data.giveStamina(value);
        }
    }
}
