package idaniel84.base.command.module.essential;

import idaniel84.base.BaseConstants;
import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommand;
import idaniel84.util.BukkitUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class PlayTimeCommand
		extends BaseCommand{
	private final BasePlugin plugin;

	public PlayTimeCommand(BasePlugin plugin){
		super("playtime", "Check the playtime of another player.");
		this.setAliases(new String[]{"pt"});
		this.setUsage("/(command) [playerName]");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		OfflinePlayer target;
		if(args.length >= 1){
			target = BukkitUtils.offlinePlayerWithNameOrUUID(args[0]);
		}else{
			if(!(sender instanceof Player)){
				sender.sendMessage(this.getUsage(label));
				return true;
			}
			target = (OfflinePlayer) sender;
		}
		if(!target.hasPlayedBefore() && !target.isOnline()){
			sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, args[0]));
			return true;
		}
		sender.sendMessage(ChatColor.YELLOW + target.getName() + " has been playing for " + ChatColor.LIGHT_PURPLE + DurationFormatUtils.formatDurationWords(this.plugin.getPlayTimeManager().getTotalPlayTime(target.getUniqueId()), true, true) + ChatColor.YELLOW + " this map.");
		if(sender.hasPermission("rank.staff") && target.isOnline()){
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAFK Timer: &e" + DurationFormatUtils.formatDurationWords(BukkitUtils.getIdleTime(target.getPlayer()), true, true)));
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		return args.length == 1 ? null : Collections.emptyList();
	}
}

