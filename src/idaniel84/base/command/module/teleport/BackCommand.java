package idaniel84.base.command.module.teleport;

import idaniel84.base.BaseConstants;
import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommand;
import idaniel84.base.user.BaseUser;
import idaniel84.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Collections;
import java.util.List;

public class BackCommand
		extends BaseCommand
		implements Listener{
	private final BasePlugin plugin;

	public BackCommand(BasePlugin plugin){
		super("back", "Go to a players last known location.");
		this.setUsage("/(command) [playerName]");
		this.plugin = plugin;
		Bukkit.getPluginManager().registerEvents(this, this.plugin);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		Location previous;
		Player target;
		BaseUser targetUser;
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command is only executable by players.");
			return true;
		}
		if(args.length > 0 && sender.hasPermission(command.getPermission() + ".others")){
			target = BukkitUtils.playerWithNameOrUUID(args[0]);
			if(target == null){
				sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, args[0]));
				return true;
			}
		}else{
			target = (Player) sender;
		}
		if((previous = (targetUser = this.plugin.getUserManager().getUser(target.getUniqueId())).getBackLocation()) == null){
			sender.sendMessage(ChatColor.RED + target.getName() + " doesn't have a back location.");
			return true;
		}
		((Player) sender).teleport(previous);
		sender.sendMessage(ChatColor.YELLOW + "Teleported to back location of " + target.getName() + '.');
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		return args.length == 1 ? null : Collections.emptyList();
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event){
		Player player = event.getEntity();
		this.plugin.getUserManager().getUser(player.getUniqueId()).setBackLocation(player.getLocation().clone());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onPlayerTeleport(PlayerTeleportEvent event){
		if(event.getCause() != PlayerTeleportEvent.TeleportCause.ENDER_PEARL){
			this.plugin.getUserManager().getUser(event.getPlayer().getUniqueId()).setBackLocation(event.getFrom().clone());
		}
	}
}

