package idaniel84.base.command.module.chat;

import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommand;
import idaniel84.base.user.BaseUser;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleStaffChatCommand
		extends BaseCommand{
	private final BasePlugin plugin;

	public ToggleStaffChatCommand(BasePlugin plugin){
		super("togglesc", "Toggles private messages.");
		this.setAliases(new String[]{"toggleac", "togglestaffchat"});
		this.setUsage("/(command)");
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage(ChatColor.RED + "This command is only executable for players.");
			return true;
		}
		Player player = (Player) sender;
		BaseUser baseUser = this.plugin.getUserManager().getUser(player.getUniqueId());
		boolean newToggled = !baseUser.isStaffChatVisible();
		baseUser.setStaffChatVisible(newToggled);
		sender.sendMessage(ChatColor.YELLOW + "You have turned staffchat visibility " + (newToggled ? String.valueOf(ChatColor.GREEN) + "on" : String.valueOf(ChatColor.RED) + "off") + ChatColor.YELLOW + '.');
		return true;
	}
}

