package idaniel84.base.command.module.warp;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommand;
import idaniel84.base.warp.Warp;
import net.veilmc.hcf.faction.FactionManager;
import net.veilmc.hcf.faction.type.Faction;
import net.veilmc.hcf.faction.type.PlayerFaction;
import net.veilmc.hcf.faction.type.SpawnFaction;
import net.veilmc.hcf.HCF;
import net.veilmc.hcf.timer.TimerManager;
import idaniel84.util.BukkitUtils;
import idaniel84.util.command.CommandArgument;
import idaniel84.util.command.CommandWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class WarpExecutor extends BaseCommand implements Listener{
	private final List<CommandArgument> arguments;
	private final Map<UUID, BukkitRunnable> taskMap;
	private final BasePlugin plugin;

	public WarpExecutor(final BasePlugin plugin){
		super("warp", "Teleport to locations on the server.");
		this.arguments = new ArrayList<CommandArgument>(3);
		this.taskMap = new HashMap<UUID, BukkitRunnable>();
		this.setAliases(new String[]{"gw", "globalwarp"});
		this.setUsage("/(command)");
		this.plugin = plugin;
		this.arguments.add(new WarpListArgument(plugin));
		this.arguments.add(new WarpRemoveArgument(plugin));
		this.arguments.add(new WarpSetArgument(plugin));
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler
	public void onMove(final PlayerMoveEvent e){
		if(e.getTo().getBlockX() == e.getFrom().getBlockX() && e.getTo().getBlockZ() == e.getFrom().getBlockZ()){
			return;
		}
		if(this.taskMap.containsKey(e.getPlayer().getUniqueId())){
			this.taskMap.get(e.getPlayer().getUniqueId()).cancel();
			this.taskMap.remove(e.getPlayer().getUniqueId());
			e.getPlayer().sendMessage(ChatColor.RED + "Warp canceled you moved.");
		}
	}

	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args){
		if(args.length < 1){
			CommandWrapper.printUsage(sender, label, this.arguments);
			sender.sendMessage(ChatColor.GREEN + "/" + label + " <warpName> " + ChatColor.GRAY + "- Teleport to a server warp.");
			return true;
		}
		final CommandArgument argument = CommandWrapper.matchArgument(args[0], sender, this.arguments);
		final Warp warp = this.plugin.getWarpManager().getWarp(args[0]);
		if(argument == null){
			Faction factionAt;
			PlayerFaction playerFaction;
			FactionManager factionManager = HCF.getPlugin().getFactionManager();
			if ((factionAt = factionManager.getFactionAt(Bukkit.getPlayer(sender.getName()).getLocation())).isSafezone() && ((playerFaction = factionManager.getPlayerFaction((Player) sender)) == null || !factionAt.equals(playerFaction))) {
				this.warpPlayer(Bukkit.getPlayer(sender.getName()), warp);
				return true;
			}
			this.handleWarp(sender, args);
			return true;
		}
		return argument.onCommand(sender, command, label, args);
	}

	public List<String> onTabComplete(final CommandSender sender, final Command command, final String label, final String[] args){
		if(!(sender instanceof Player)){
			return Collections.emptyList();
		}
		List<String> results;
		if(args.length == 1){
			results = (List<String>) Lists.newArrayList(Iterables.concat(CommandWrapper.getAccessibleArgumentNames(sender, this.arguments), (Iterable) this.plugin.getWarpManager().getWarpNames()));
		}else{
			final CommandArgument argument = CommandWrapper.matchArgument(args[0], sender, this.arguments);
			if(argument == null){
				return Collections.emptyList();
			}
			results = argument.onTabComplete(sender, command, label, args);
		}
		return (results == null) ? null : BukkitUtils.getCompletions(args, results);
	}

	private boolean handleWarp(final CommandSender sender, final String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "Only players can teleport to warps.");
			return true;
		}
		final Warp warp = this.plugin.getWarpManager().getWarp(args[0]);
		if(warp == null){
			sender.sendMessage(ChatColor.RED + "Server warp '" + ChatColor.GRAY + args[0] + ChatColor.RED + "' not found.");
			return true;
		}
		if(!sender.hasPermission(warp.getPermission())){
			sender.sendMessage(ChatColor.RED + "You do not have permissions to teleport to '" + ChatColor.GRAY + args[0] + ChatColor.RED + "' warp.");
			return false;
		}
		TimerManager timerManager = HCF.getPlugin().getTimerManager();
		if (timerManager.spawnTagTimer.getRemaining(Bukkit.getPlayer(sender.getName())) > 0L) {
			sender.sendMessage(ChatColor.RED + "You cannot use warps while Spawn Tag is active.");
			return false;
		}
		final Player player = (Player) sender;
		final long warpDelayTicks = player.hasPermission(warp.getPermission() + ".bypass") ? 0L : this.plugin.getWarpManager().getWarpDelayTicks();
		if(warpDelayTicks <= 0L){
			this.warpPlayer(player, warp);
			return false;
		}
		final BukkitRunnable runnable = new BukkitRunnable(){
			public void run(){
				WarpExecutor.this.warpPlayer(player, warp);
			}
		};
		runnable.runTaskLater(this.plugin, warpDelayTicks);
		this.taskMap.put(player.getUniqueId(), runnable);
		sender.sendMessage(ChatColor.GRAY + "Warping to " + ChatColor.BLUE + warp.getName() + ChatColor.GRAY + " you will teleport in " + ChatColor.BLUE + this.plugin.getWarpManager().getWarpDelayWords() + ChatColor.GRAY + '.');
		return true;
	}

	private void warpPlayer(final Player player, final Warp warp){
		final BukkitRunnable runnable = this.taskMap.remove(player.getUniqueId());
		if(runnable != null){
			runnable.cancel();
		}
		if(player.teleport(warp.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND)){
			player.sendMessage(ChatColor.GRAY + "Warped to " + warp.getName() + '.');
		}
	}
}