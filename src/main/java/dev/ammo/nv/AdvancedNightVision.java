package dev.ammo.nv;

import dev.ammo.nv.commands.NightVisionCommand;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The type Advanced nightvision.
 */
public final class AdvancedNightVision extends JavaPlugin {

	private static AdvancedNightVision instance;
	private Metrics metrics;

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static AdvancedNightVision getInstance() {
		return instance;
	}

	public static String getPrefix() {
		return "§8>> §6AdvancedNightVision §8| §7";
	}

	@Override
	public void onDisable() {
		metrics.shutdown();
	}

	@Override
	public void onEnable() {
		instance = this;
		metrics = new Metrics(this, 21667);

		new NightVisionCommand(getCommand("nv"));
	}
}
