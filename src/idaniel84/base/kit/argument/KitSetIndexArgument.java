package idaniel84.base.kit.argument;

import com.google.common.primitives.Ints;
import idaniel84.base.BasePlugin;
import idaniel84.base.kit.Kit;
import idaniel84.util.command.CommandArgument;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KitSetIndexArgument
		extends CommandArgument{
	private final BasePlugin plugin;

	public KitSetIndexArgument(BasePlugin plugin){
		super("setindex", "Sets the position of a kit for the GUI");
		this.plugin = plugin;
		this.aliases = new String[]{"setorder", "setindex", "setpos", "setposition"};
		this.permission = "base.command.kit.argument." + this.getName();
	}

	@Override
	public String getUsage(String label){
		return "" + '/' + label + ' ' + this.getName() + " <kitName> <index[0 = minimum]>";
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(args.length < 3){
			sender.sendMessage(ChatColor.RED + "Usage: " + this.getUsage(label));
			return true;
		}
		Kit kit = this.plugin.getKitManager().getKit(args[1]);
		if(kit == null){
			sender.sendMessage(ChatColor.RED + "Kit '" + args[1] + "' not found.");
			return true;
		}
		Integer newIndex = Ints.tryParse(args[2]);
		if(newIndex == null){
			sender.sendMessage(ChatColor.RED + "'" + args[2] + "' is not a number.");
			return true;
		}
		if(newIndex < 1){
			sender.sendMessage(ChatColor.RED + "The kit index cannot be less than " + 1 + '.');
			return true;
		}
		List<Kit> kits = this.plugin.getKitManager().getKits();
		int totalKitAmount = kits.size() + 1;
		if(newIndex > totalKitAmount){
			sender.sendMessage(ChatColor.RED + "The kit index must be a maximum of " + totalKitAmount + '.');
			return true;
		}
		int previousIndex = kits.indexOf(kit) + 1;
		if(newIndex == previousIndex){
			sender.sendMessage(ChatColor.RED + "Index of kit " + kit.getDisplayName() + " is already " + newIndex + '.');
			return true;
		}
		kits.remove(kit);
		newIndex = newIndex - 1;
		kits.add(newIndex, kit);
		sender.sendMessage(ChatColor.AQUA + "Set the index of kit " + kit.getDisplayName() + " from " + previousIndex + " to " + newIndex + '.');
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		if(args.length != 2){
			return Collections.emptyList();
		}
		List<Kit> kits = this.plugin.getKitManager().getKits();
		ArrayList<String> results = new ArrayList<String>(kits.size());
		for(Kit kit : kits){
			results.add(kit.getName());
		}
		return results;
	}
}

