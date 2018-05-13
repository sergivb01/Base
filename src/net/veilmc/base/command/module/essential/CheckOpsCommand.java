package net.veilmc.base.command.module.essential;

import net.veilmc.base.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class CheckOpsCommand extends BaseCommand {

    public CheckOpsCommand() {
        super("checkops", "Check information about a player.");
        this.setUsage("/(command)");
        this.setAliases(new String[]{"checkoplist, check*, check*list"});
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        PermissionManager permissionManager = PermissionsEx.getPermissionManager();
        ArrayList<String> asterisk = new ArrayList<>();
        for (PermissionUser all : permissionManager.getUsers()) {
            if (all.has("*")) {
                asterisk.add(all.getName());
            }
        }
        ArrayList<String> ops = new ArrayList<>();
        for (OfflinePlayer all : Bukkit.getOperators()) {
            if (all.isOp()) {
                ops.add(all.getName());
            }
        }
        sender.sendMessage(ChatColor.BLUE + "Players with * [" + asterisk.size() + "]: " + ChatColor.RESET + String.join(", ", asterisk) + ".");
        sender.sendMessage(ChatColor.BLUE + "Players opped [" + ops.size() + "]: " + ChatColor.RESET + String.join(", ", ops) + ".");
        return true;
    }
}
