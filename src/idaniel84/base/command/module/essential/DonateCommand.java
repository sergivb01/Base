package idaniel84.base.command.module.essential;

import net.md_5.bungee.api.ChatColor;
import idaniel84.base.command.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class DonateCommand
		extends BaseCommand{
	public DonateCommand(){
		super("donate", "Donates");
		this.setAliases(new String[]{"buy"});
		this.setUsage("/(command)]");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(ChatColor.YELLOW + "You can purchase ranks at " + ChatColor.GREEN + "store.veilhcf.us");
		return true;
	}
}

