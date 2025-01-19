package top.mrxiaom.sweet.mmorpg.stat;

import net.Indyuce.mmoitems.stat.type.DoubleStat;
import org.bukkit.Material;
import top.mrxiaom.pluginbase.utils.Util;

public class MaxStamina extends DoubleStat {
	public MaxStamina() {
		super("MAX_STAMINA",
				Util.valueOr(Material.class, "LIGHT_BLUE_DYE", Material.DIAMOND),
				"Max Stamina", new String[]{"Adds stamina to your max stamina bar."}
		);
	}
}
