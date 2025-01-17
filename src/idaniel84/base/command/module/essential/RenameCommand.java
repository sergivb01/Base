package idaniel84.base.command.module.essential;

import idaniel84.base.command.BaseCommand;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;

public class RenameCommand
		extends BaseCommand{
	public RenameCommand(){
		super("rename", "Rename your held item.");
		this.setUsage("/(command) <newItemName>");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
			return true;
		}
		if(args.length < 1){
			sender.sendMessage(this.getUsage(label));
			return true;
		}
		Player player = (Player) sender;
		ItemStack stack = player.getItemInHand();
		if(stack == null || stack.getType() == Material.AIR){
			sender.sendMessage(ChatColor.RED + "You are not holding anything.");
			return true;
		}
		ItemMeta meta = stack.getItemMeta();
		String oldName = meta.getDisplayName();
		if(oldName != null){
			oldName = oldName.trim();
		}
		String newName = args[0].equalsIgnoreCase("none") || args[0].equalsIgnoreCase("null") ? null : ChatColor.translateAlternateColorCodes('&', StringUtils.join(args, ' ', 0, args.length));
		if(oldName == null && newName == null){
			sender.sendMessage(ChatColor.RED + "Your held item already has no name.");
			return true;
		}
		if(oldName != null && oldName.equals(newName)){
			sender.sendMessage(ChatColor.RED + "Your held item is already named this.");
			return true;
		}
		meta.setDisplayName(newName);
		stack.setItemMeta(meta);
		if(newName == null){
			sender.sendMessage(ChatColor.YELLOW + "Removed name of held item from " + oldName + '.');
			return true;
		}
		sender.sendMessage(ChatColor.YELLOW + "Renamed item in hand from " + (oldName == null ? "no name" : oldName) + ChatColor.YELLOW + " to " + newName + ChatColor.YELLOW + '.');
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		return Collections.emptyList();
	}
}

