package idaniel84.base.command.module.inventory;

import com.google.common.primitives.Ints;
import idaniel84.base.command.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MoreCommand
		extends BaseCommand{
	public MoreCommand(){
		super("more", "Sets your item to its maximum amount.");
		this.setUsage("/(command)");
	}

	@Override
	public boolean isPlayerOnlyCommand(){
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		Integer amount;
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command is only executable for players.");
			return true;
		}
		Player player = (Player) sender;
		ItemStack stack = player.getItemInHand();
		if(stack == null || stack.getType() == Material.AIR){
			sender.sendMessage(ChatColor.RED + "You are not holding any item.");
			return true;
		}
		if(args.length > 0){
			amount = Ints.tryParse(args[0]);
			if(amount == null){
				sender.sendMessage(ChatColor.RED + "'" + args[0] + "' is not a number.");
				return true;
			}
			if(amount <= 0){
				sender.sendMessage(ChatColor.RED + "Item amounts must be positive.");
				return true;
			}
		}else{
			int curAmount = stack.getAmount();
			if(curAmount >= (amount = Integer.valueOf(stack.getMaxStackSize()))){
				sender.sendMessage(ChatColor.RED + "You already have the maximum amount: " + amount + '.');
				return true;
			}
		}
		stack.setAmount(amount.intValue());
		return true;
	}
}

