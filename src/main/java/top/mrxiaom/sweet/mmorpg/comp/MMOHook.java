package top.mrxiaom.sweet.mmorpg.comp;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.event.user.UserLoadEvent;
import dev.aurelium.auraskills.api.user.SkillsUser;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.comp.rpg.RPGHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;
import top.mrxiaom.sweet.mmorpg.api.ResourceData;

public class MMOHook implements RPGHandler, Listener {

    private final AuraSkillsApi auraSkills = AuraSkillsApi.get();
    private final SweetMMORPG plugin;

    public MMOHook(SweetMMORPG plugin) { // 添加参数
        this.plugin = plugin; // 正确初始化
        plugin.getServer().getPluginManager().registerEvents(this, plugin); // 推荐添加事件注册
    }
    @Override
    public RPGPlayer getInfo(PlayerData data) {
        return new AuraSkillsPlayer(data);
    }

    @Override
    public void refreshStats(PlayerData playerData) {

    }

    public class AuraSkillsPlayer extends RPGPlayer {
        private final SkillsUser user;
        private final ResourceData resourceData;
        public AuraSkillsPlayer(PlayerData playerData) {
            super(playerData);
            Player player = playerData.getPlayer();
            this.user = auraSkills.getUser(player.getUniqueId());
            this.resourceData = plugin.getPlayerDatabase().getOrCached(player);
        }
        @Override
        public int getLevel() {
            return user.getPowerLevel();
        }

        @Override
        public String getClassName() {
            return "";
        }

        @Override
        public double getMana() {
            return user.getMana();
        }
        @Override
        public double getStamina() {
            return resourceData.getStamina();
        }
        @Override
        public void setMana(double value) {
            double max = user.getMaxMana();
            user.setMana(Math.min(value, max));
        }
        @Override
        public void setStamina(double value) {
            resourceData.setStamina(value);
        }
        @Override
        public void giveMana(double value) {
            user.setMana(Math.min(user.getMana() + value, user.getMaxMana()));
        }
        @Override
        public void giveStamina(double value) {
            resourceData.giveStamina(value);
        }
    }
    // 处理玩家数据加载
    @EventHandler
    public void onUserLoad(UserLoadEvent event) {
        PlayerData.get(event.getPlayer()).setRPGPlayer(new AuraSkillsPlayer(PlayerData.get(event.getPlayer())));
    }
}