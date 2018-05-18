package net.veilmc.base.command.module.essential;

import net.veilmc.base.command.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;

public class VanishStaffCommand extends BaseCommand implements Listener{

    public VanishStaffCommand(){
        super("vanishstaff", "Show or hide staff.");
        this.setUsage("/(command)");
    }

    private static ArrayList<Player> toggled = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can use this.");
        }
        Player player = (Player) sender;
        if (!toggled.contains(player)) {
            toggled.add(player);
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("rank.staff")) {
                    player.hidePlayer(all);
                }
            }
            player.sendMessage(ChatColor.BLUE + "Hidding all staff.");
            return true;
        }
        if (toggled.contains(player)) {
            toggled.remove(player);
            for (Player all : Bukkit.getOnlinePlayers()) {
                if (all.hasPermission("rank.staff")) {
                    player.showPlayer(all);
                }
            }
            player.sendMessage(ChatColor.BLUE + "Showing all staff.");
            return true;
        }
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPermission("rank.staff")) {
            toggled.forEach(Player -> Player.hidePlayer(event.getPlayer()));
        }
    }
}
