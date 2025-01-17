package idaniel84.base.command.module.essential;

import com.google.common.collect.ImmutableSet;
import idaniel84.base.BaseConstants;
import idaniel84.base.command.BaseCommand;
import idaniel84.util.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class FeedCommand
		extends BaseCommand{
	private static final int MAX_HUNGER = 20;

	public FeedCommand(){
		super("feed", "Feeds a player.");
		this.setUsage("/(command) <playerName>");
	}

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args){
		Player onlyTarget = null;
		Collection<Player> targets;
		if(args.length > 0 && sender.hasPermission(command.getPermission() + ".others")){
			if(args[0].equalsIgnoreCase("all") && sender.hasPermission(command.getPermission() + ".all")){
				targets = ImmutableSet.copyOf(Bukkit.getOnlinePlayers());
			}else{
				if((onlyTarget = BukkitUtils.playerWithNameOrUUID(args[0])) == null || !BaseCommand.canSee(sender, onlyTarget)){
					sender.sendMessage(String.format(BaseConstants.PLAYER_WITH_NAME_OR_UUID_NOT_FOUND, args[0]));
					return true;
				}
				targets = ImmutableSet.of(onlyTarget);
			}
		}else{
			if(!(sender instanceof Player)){
				sender.sendMessage(this.getUsage(label));
				return true;
			}
			targets = ImmutableSet.of((onlyTarget = (Player) sender));
		}
		if(onlyTarget != null && onlyTarget.getFoodLevel() == 20){
			sender.sendMessage(ChatColor.RED + onlyTarget.getName() + " already has full hunger.");
			return true;
		}
		for(final Player target : targets){
			target.removePotionEffect(PotionEffectType.HUNGER);
			target.setFoodLevel(20);
		}
		sender.sendMessage(ChatColor.YELLOW + "Fed " + (onlyTarget == null ? "all online players" : onlyTarget.getName()) + '.');
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args){
		return args.length == 1 ? null : Collections.emptyList();
	}
}

