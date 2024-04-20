package dev.ammo.nv.commands;

import dev.ammo.nv.AdvancedNightVision;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Night vision command.
 */
public class NightVisionCommand implements CommandExecutor, TabCompleter {

	/**
	 * Instantiates a new NightVision command.
	 *
	 * @param command the command
	 */
	public NightVisionCommand(PluginCommand command) {
		command.setExecutor(this);
		command.setTabCompleter(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			if (!( sender instanceof Player )) {
				sendMessage(sender, "§cYou must be a player to use this command.");
				return true;
			}

			Player player = (Player) sender;

			if (!player.hasPermission("nv.use.self")) {
				sendNoPermissionMessage(player);
				return true;
			}

			toggleNightVision(player, player);
			return true;
		} else if (args.length == 1) {
			if (!sender.hasPermission("nv.use.others")) {
				sendNoPermissionMessage((Player) sender);
				return true;
			}


			Player target = sender.getServer().getPlayer(args[ 0 ]);

			if (target == null) {
				sendMessage(sender, "§e" + args[ 0 ] + " §cis not online.");
				return true;
			}

			toggleNightVision(target, sender);

			return true;
		}

		return true;
	}

	/**
	 * Toggle night vision.
	 *
	 * @param other the other
	 * @param self  the self
	 */
	private void toggleNightVision(Player other, CommandSender self) {
		if (other.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
			other.removePotionEffect(PotionEffectType.NIGHT_VISION);

			if (other != self) {
				sendMessage(other, "§7Your night vision has been §cdisabled §7by §e" + self.getName() + "§7.");
				sendMessage(self, "§7Night vision §cdisabled §7for §e" + other.getName() + "§7.");
			} else {
				sendMessage(other, "§7Night vision §cdisabled§7.");
			}
		} else {
			other.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0, false, false));

			if (other != self) {
				sendMessage(other, "§7Your night vision has been §aenabled §7by §e" + self.getName() + "§7.");
				sendMessage(self, "§7Night vision §aenabled §7for §e" + other.getName() + "§7.");
			} else {
				sendMessage(other, "§7Night vision §aenabled§7.");
			}
		}
	}

	/**
	 * Send message.
	 *
	 * @param sender  the sender
	 * @param message the message
	 */
	private void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(AdvancedNightVision.getPrefix() + message);
	}

	/**
	 * Send no permission message.
	 *
	 * @param player the player
	 */
	private void sendNoPermissionMessage(Player player) {
		sendMessage(player, "§cYou do not have permission to use this command.");
	}

	/**
	 * Send help.
	 *
	 * @param player the player
	 */
	private void sendHelp(Player player) {
		String headerLine = "§8]" + getLine(5) + "[§bAdvancedNightVision]" + getLine(5) + "§8[";
		sendMessage(player, headerLine);

		sendMessage(player, "§b/nv §8- §7Toggle night vision");
		sendMessage(player, "§b/nv <player> §8- §7Toggle night vision for a player");

		sendMessage(player, getLine(headerLine.length() - 6));
	}

	/**
	 * Gets line.
	 *
	 * @param length the length
	 *
	 * @return the line
	 */
	private String getLine(int length) {
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < length; i++) {
			line.append("=");
		}
		return line.toString();
	}


	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		List<String> completions = new ArrayList<>();

		return Suggestions.suggestions(completions, args);
	}
}
