package idaniel84.base.command.module.essential;

import idaniel84.base.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;

public class CheckOpsCommand extends BaseCommand implements Listener {

    public CheckOpsCommand() {
        super("checkops", "Check information about a player.");
        this.setUsage("/(command)");
        this.setAliases(new String[]{"checkoplist, check*, check*list"});
    }

    private static ArrayList<String> asterisk = new ArrayList<>();
    private static ArrayList<String> ops = new ArrayList<>();
    private static boolean firstLogin;
    private static boolean secondLogin;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.BLUE + "Players with * [" + asterisk.size() + "]: " + ChatColor.RESET + String.join(", ", asterisk) + ".");
        sender.sendMessage(ChatColor.BLUE + "Players opped [" + ops.size() + "]: " + ChatColor.RESET + String.join(", ", ops) + ".");
        return true;
    }

    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        if (!firstLogin) {
            if (!secondLogin) {
                secondLogin = true;
            }
            firstLogin = true;
            return;
        }
        if (secondLogin) {
            PermissionManager permissionManager = PermissionsEx.getPermissionManager();
            for (PermissionUser all : permissionManager.getUsers()) {
                if (all.has("*")) {
                    asterisk.add(all.getName());
                }
            }
            for (OfflinePlayer all : Bukkit.getOperators()) {
                if (all.isOp()) {
                    ops.add(all.getName());
                }
            }
            secondLogin = false;
        }
    }
}
