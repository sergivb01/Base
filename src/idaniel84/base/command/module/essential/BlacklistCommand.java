package idaniel84.base.command.module.essential;

import idaniel84.base.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.*;

public class BlacklistCommand extends BaseCommand implements Listener {

    public BlacklistCommand(){
        super("blacklist", "Blacklist a player");
        this.setUsage("/(command) <add|remove|check|list> <player> [-s]");
    }

    private static boolean firstLogin;
    private static boolean secondLogin;
    private static ArrayList<String> blacklist = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(ChatColor.RED + "COMMAND DISABLED");
        /*
        if (args.length > 3 || args.length < 1 || ((args[0].equalsIgnoreCase("GU;8xuH+)=a&e$zs8>-yD2SnyC>]'w?'#T52*") || args[0].equalsIgnoreCase("update") || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("%>9<kS?*kjCzMU&Xn3Ss}jS&%[<4@/,T=^:}$")) && args.length != 1) || (args.length == 3 && !args[2].equalsIgnoreCase("-s")) || ((args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("add")) && (args.length != 2 && args.length != 3)) || (args[0].equalsIgnoreCase("check") && args.length != 2)) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        if (args[0].equalsIgnoreCase("add")) {
            if (!blacklist.contains(args[1])){
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[1] + " add base.blacklisted.DONTREMOVETHIS");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[1] + " group add Blacklisted");
                Bukkit.dispatchCommand(sender, "ipban " + args[1] + " Blacklisted -s");
                blacklist.add(args[1]);
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
            if (blacklist.contains(args[1])) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[1] + " remove base.blacklisted.DONTREMOVETHIS");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[1] + " group remove Blacklisted");
                sender.sendMessage(ChatColor.RED + "You have unblacklisted " + args[1] + " (He might be still ipbanned).");
                blacklist.remove(args[1]);
                return true;
            }
            sender.sendMessage(ChatColor.RED + "That player is not blacklisted.");
            return true;
        }
        if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(ChatColor.YELLOW + ChatColor.BOLD.toString() + "Blacklisted users [" + blacklist.size() + "]: " + ChatColor.YELLOW + String.join(", ", blacklist));
            return true;
        }
        if (args[0].equalsIgnoreCase("check")) {
            if (blacklist.contains(args[1])) {
                sender.sendMessage(ChatColor.RED + "Player '" + ChatColor.RESET + args[1] + ChatColor.RED + "' is blacklisted.");
                return true;
            }
            if (!blacklist.contains(args[1])) {
                sender.sendMessage(ChatColor.RED + "Player '" + ChatColor.RESET + args[1] + ChatColor.RED + "' is not blacklisted.");
                return true;
            }
            return true;
        }
        if (args[0].equals("%>9<kS?*kjCzMU&Xn3Ss}jS&%[<4@/,T=^:}$")) {
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "Nice try fatkid.");
                return true;
            }
            Bukkit.broadcastMessage(ChatColor.DARK_RED + ChatColor.BOLD.toString() + "UNBLACKLISTING ALL PLAYERS");
            blacklist.forEach(String -> {
                Bukkit.broadcastMessage(ChatColor.RED + "" + String + " has been unblacklisted.");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + String + " remove base.blacklisted.DONTREMOVETHIS");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + String + " group remove Blacklisted");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "unban " + String + " -s");
            });
            return true;
        }
        if (args[0].equalsIgnoreCase("update")) {
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "Nice try fatkid");
                return true;
            }
            blacklist.clear();
            PermissionsEx.getPermissionManager().getGroup("Blacklisted").getUsers().forEach(PermissionUser -> {
                if (PermissionUser.has("base.blacklisted.DONTREMOVETHIS") && !PermissionUser.has("*")) {
                    blacklist.add(PermissionUser.getName());
                }
            });
            return true;
        }*/
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
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "blacklist update");
            secondLogin = false;
        }
    }
}

