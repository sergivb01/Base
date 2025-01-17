package idaniel84.base.kit.argument;

import idaniel84.base.BasePlugin;
import idaniel84.base.kit.Kit;
import idaniel84.base.user.BaseUser;
import idaniel84.util.BukkitUtils;
import idaniel84.util.command.CommandArgument;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KitListArgument
		extends CommandArgument{
	private final BasePlugin plugin;

	public KitListArgument(BasePlugin plugin){
		super("list", "Lists all current kits");
		this.plugin = plugin;
		this.permission = "command.kit.argument." + this.getName();
	}

	@Override
	public String getUsage(String label){
		return "" + '/' + label + ' ' + this.getName();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		List<Kit> kits = this.plugin.getKitManager().getKits();
		if(kits.isEmpty()){
			sender.sendMessage(ChatColor.RED + "No kits have been defined.");
			return true;
		}
		ArrayList<String> kitNames = new ArrayList<String>();
		for(Kit kit : kits){
			String permission = kit.getPermissionNode();
			if(permission != null && !sender.hasPermission(permission)) continue;
			BaseUser user = this.plugin.getUserManager().getUser(((Player) sender).getUniqueId());
			ChatColor color = user.getKitUses(kit) >= kit.getMaximumUses() || user.getRemainingKitCooldown(kit) >= (long) kit.getMaximumUses() || this.plugin.getPlayTimeManager().getTotalPlayTime(((Player) sender).getUniqueId()) <= kit.getMinPlaytimeMillis() ? ChatColor.RED : ChatColor.GREEN;
			kitNames.add(color + kit.getDisplayName());
		}
		String kitList = StringUtils.join(kitNames, ChatColor.GRAY + ", ");
		sender.sendMessage(ChatColor.DARK_GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
		sender.sendMessage(ChatColor.DARK_AQUA + ChatColor.BOLD.toString() + "Kit List" + ChatColor.GREEN + "[" + kitNames.size() + '/' + kits.size() + "]");
		sender.sendMessage(ChatColor.GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
		sender.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + kitList + ChatColor.GRAY + ']');
		sender.sendMessage(ChatColor.DARK_GRAY + BukkitUtils.STRAIGHT_LINE_DEFAULT);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		return Collections.emptyList();
	}
}

