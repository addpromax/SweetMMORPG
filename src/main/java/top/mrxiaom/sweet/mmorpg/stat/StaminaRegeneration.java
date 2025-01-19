package top.mrxiaom.sweet.mmorpg.stat;

import net.Indyuce.mmoitems.stat.type.DoubleStat;
import org.bukkit.Material;
import top.mrxiaom.pluginbase.utils.Util;

public class StaminaRegeneration extends DoubleStat {
	public StaminaRegeneration() {
		super("STAMINA_REGENERATION",
				Util.valueOr(Material.class, "LIGHT_BLUE_DYE", Material.DIAMOND),
				"Stamina Regeneration", new String[]{"Increases stamina regen."}
		);
	}
}
