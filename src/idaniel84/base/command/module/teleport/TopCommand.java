package idaniel84.base.command.module.teleport;

import idaniel84.base.command.BaseCommand;
import idaniel84.util.BukkitUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class TopCommand
		extends BaseCommand{
	public TopCommand(){
		super("top", "Teleports to the highest safe spot.");
		this.setUsage("/(command)");
	}

	@Override
	public boolean isPlayerOnlyCommand(){
		return true;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command is only executable for players.");
			return true;
		}
		Player player = (Player) sender;
		Location origin = player.getLocation().clone();
		Location highestLocation = BukkitUtils.getHighestLocation(origin.clone());
		if(highestLocation != null && !Objects.equals(highestLocation, origin)){
			Block originBlock = origin.getBlock();
			if((highestLocation.getBlockY() - originBlock.getY() != 1 || originBlock.getType() != Material.WATER) && originBlock.getType() != Material.STATIONARY_WATER){
				player.teleport(highestLocation.add(0.0, 1.0, 0.0), PlayerTeleportEvent.TeleportCause.COMMAND);
				Command.broadcastCommandMessage(sender, ChatColor.GOLD + "Teleported to highest location.");
				return true;
			}
		}
		sender.sendMessage(ChatColor.RED + "No highest location found.");
		return true;
	}
}

