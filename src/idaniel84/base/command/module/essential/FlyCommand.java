package idaniel84.base.command.module.essential;

import idaniel84.base.BaseConstants;
import idaniel84.base.command.BaseCommand;
import idaniel84.util.BukkitUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class FlyCommand
		extends BaseCommand{
	public FlyCommand(){
		super("fly", "Toggles flight mode for a player.");
		this.setUsage("/(command) <playerName>");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		Player target;
		if(args.length > 0 && sender.hasPermission(command.getPermission() + ".others")){
			target = BukkitUtils.playerWithNameOrUUID(args[0]);
		}else{
			if(!(sender instanceof Player)){
				sender.sendMessage(this.getUsage(label));
				return true;
			}
			target = (Player) sender;
		}
		if(target == null || !BaseCommand.canSee(sender, target)){
			sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, args[0]));
			return true;
		}
		boolean newFlight = !target.getAllowFlight();
		target.setAllowFlight(newFlight);
		if(newFlight){
			target.setFlying(true);
		}
		Command.broadcastCommandMessage(sender, ChatColor.YELLOW + "Flight mode of " + target.getName() + " set to " + newFlight + '.');
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		return args.length == 1 ? null : Collections.emptyList();
	}
}

