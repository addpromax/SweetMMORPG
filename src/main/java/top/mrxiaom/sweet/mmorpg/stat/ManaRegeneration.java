package top.mrxiaom.sweet.mmorpg.stat;

import net.Indyuce.mmoitems.stat.type.DoubleStat;
import org.bukkit.Material;
import top.mrxiaom.pluginbase.utils.Util;

public class ManaRegeneration extends DoubleStat {
	public ManaRegeneration() {
		super("MANA_REGENERATION",
				Util.valueOr(Material.class, "LAPIS_LAZULI", Material.DIAMOND),
				"Mana Regeneration", new String[]{"Increases mana regen."}
		);
	}
}
