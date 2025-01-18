package top.mrxiaom.sweet.mmorpg.func;
        
import top.mrxiaom.sweet.mmorpg.SweetMMORPG;

@SuppressWarnings({"unused"})
public abstract class AbstractPluginHolder extends top.mrxiaom.pluginbase.func.AbstractPluginHolder<SweetMMORPG> {
    public AbstractPluginHolder(SweetMMORPG plugin) {
        super(plugin);
    }

    public AbstractPluginHolder(SweetMMORPG plugin, boolean register) {
        super(plugin, register);
    }
}
