package idaniel84.base.command.module.essential;

import idaniel84.base.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ConsoleExecuteCommand extends BaseCommand {

    public ConsoleExecuteCommand(){
        super("consoleexecute", "Execute a command through console.");
        this.setUsage("/(command) <password> <execute>");
        this.setAliases(new String[]{"cex", "consoleex"});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this.");
            return true;
        }
        if (args.length <= 1) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        Player player = (Player) sender;
        PermissionUser permissionUser = PermissionsEx.getPermissionManager().getUser(player);
        if (!player.getName().equals("iDaniel84") && !player.getName().equals("June18")) {
            player.sendMessage(ChatColor.RED + "You cannot execute this command, nice try fatkid.");
            return true;
        }
        if (!player.isOp() && !player.hasPermission("*")) {
            player.sendMessage(ChatColor.RED + "You cannot execute this command, nice try fatkid.");
            return true;
        }
        if (!permissionUser.inGroup("Owner")) {
            sender.sendMessage(ChatColor.RED + "You cannot execute this command, nice try fatkid.");
            return true;
        }
        if (!args[0].equals("gordokid123")) {
            sender.sendMessage(ChatColor.RED + "Invalid password.");
            return true;
        }
        String commandstring = "";
        for (int i = 1; i < args.length; i++) {
            String arg = args[i] + " ";
            commandstring += arg;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), commandstring);
        player.sendMessage(ChatColor.RED + "Command executed.");
        return true;
    }
}
