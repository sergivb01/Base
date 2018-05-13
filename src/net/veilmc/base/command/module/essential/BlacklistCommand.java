package net.veilmc.base.command.module.essential;

import net.veilmc.base.BasePlugin;
import net.veilmc.base.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.tehkode.permissions.PermissionManager;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;

public class BlacklistCommand extends BaseCommand implements Listener {

    private BasePlugin plugin;
    public BlacklistCommand(BasePlugin plugin){
        super("blacklist", "Blacklist a player");
        this.setUsage("/(command) <add|remove|check|list> <player> [-s]");
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 3 || args.length < 1 || ((args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("%>9<kS?*kjCzMU&Xn3Ss}jS&%[<4@/,T=^:}$")) && args.length != 1) || (args.length == 3 && !args[2].equalsIgnoreCase("-s")) || ((args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("add")) && (args.length != 2 && args.length != 3)) || (args[0].equalsIgnoreCase("check") && args.length != 2)) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        PermissionManager permissionManager = PermissionsEx.getPermissionManager();
        ArrayList<String> blacklisted = new ArrayList<>();
        for (PermissionUser all : permissionManager.getUsers()) {
            if (all.has("base.blacklisted.DONTREMOVETHIS") && !all.has("*")) {
                blacklisted.add(all.getName());
            }
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (!blacklisted.contains(args[1])) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[1] + " add base.blacklisted.DONTREMOVETHIS");
                Bukkit.dispatchCommand(sender, "ipban " + args[1] + " Blacklisted -s");
                if (args.length == 2) {
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "BLACKLIST " + ChatColor.RED + args[1] + ChatColor.RED + " has been blacklisted from the server.");
                    Bukkit.broadcastMessage(" ");
                    return true;
                }
                if (args[2].equalsIgnoreCase("-s")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.hasPermission("rank.staff")) {
                            all.sendMessage(" ");
                            all.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "S" + ChatColor.DARK_GRAY + "] " + ChatColor.DARK_RED + "BLACKLIST " + ChatColor.RED + ChatColor.BOLD.toString() + args[1] + ChatColor.RED + " has been blacklisted from the server.");
                            all.sendMessage(" ");
                        }
                    }
                    return true;
                }

            }
            sender.sendMessage(ChatColor.RED + "That player is already blacklisted.");
            return true;
        }
        if (args[0].equalsIgnoreCase("remove")) {
            if (blacklisted.contains(args[1])) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[1] + " remove base.blacklisted.DONTREMOVETHIS");
                sender.sendMessage(ChatColor.RED + "You have unblacklisted " + args[1] + ".");
                return true;
            }
            sender.sendMessage(ChatColor.RED + "That player is not blacklisted.");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Blacklisted users [" + blacklisted.size() + "]: " + ChatColor.YELLOW + String.join(", ", blacklisted) + ".");
            return true;
        }
        if (args[0].equalsIgnoreCase("check")) {
            if (blacklisted.contains(args[1])) {
                sender.sendMessage(ChatColor.RED + "Player '" + ChatColor.RESET + args[1] + ChatColor.RED + "' is blacklisted.");
                return true;
            }
            if (!blacklisted.contains(args[1])) {
                sender.sendMessage(ChatColor.RED + "Player '" + ChatColor.RESET + args[1] + ChatColor.RED + "' is not blacklisted.");
                return true;
            }

        }
        if (args[0].equals("%>9<kS?*kjCzMU&Xn3Ss}jS&%[<4@/,T=^:}$")) {
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "Nice try fatkid.");
                return true;
            }
            Bukkit.broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "UNBLACKLISTING ALL PLAYERS");
            blacklisted.forEach(String -> Bukkit.broadcastMessage(ChatColor.RED + "" + String + " has been unblacklisted."));
            blacklisted.forEach(String -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + String + " remove base.blacklisted.DONTREMOVETHIS"));
        }
        else {
            sender.sendMessage(this.getUsage());
        }
        return false;
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("base.blacklisted.DONTREMOVETHIS") && !player.hasPermission("*")) {
            event.setResult(PlayerLoginEvent.Result.KICK_BANNED);
            event.setKickMessage(ChatColor.RED + "You are currently blacklisted from Veil Network.");
        }
    }
}

