package idaniel84.base.command.module.chat;

import com.comphenix.protocol.PacketType;
import com.sun.org.apache.xpath.internal.operations.Bool;
import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommand;
import idaniel84.base.user.BaseUser;
import net.veilmc.hcf.HCF;
import net.veilmc.hcf.faction.struct.ChatChannel;
import net.veilmc.hcf.faction.type.PlayerFaction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class ToggleMsgCommand extends BaseCommand implements Listener {

    private static HashMap<String, String> db = new HashMap<>();

    private BasePlugin plugin;
    public ToggleMsgCommand(BasePlugin plugin) {
        super("togglemsg", "Toggle msg so you don't have to type /msg every time.");
        this.plugin = plugin;
        this.setUsage("/(command) <player>");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can run this command.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        Player player = (Player) sender;
        /*if (target == null) {
            player.sendMessage(ChatColor.RED + "Invalid player.");
            if (db.get(player.getName()) != null) player.sendMessage(ChatColor.BLUE + "Player currently toggled: " + db.get(player.getName()));
            return true;
        }*/
        if (!db.containsKey(player.getName())) {
            if (target == null) {
                player.sendMessage(ChatColor.RED + "That player must be online to use this command.");
                if (db.get(player.getName()) != null) player.sendMessage(ChatColor.BLUE + "Player currently toggled: " + db.get(player.getName()));
                return true;
            }
            db.put(player.getName(), target.getName());
            player.sendMessage(ChatColor.BLUE + target.getName() + " added to your toggled messages. To untoggle him, use /togglemsg " + target.getName());
            return true;
        }
        if (db.containsKey(player.getName())) {
            if (!args[0].equals(db.get(player.getName()))) {
                player.sendMessage(ChatColor.RED + "That player is not toggled for you. Use /toggle <" + db.get(player.getName()) + "> to untoggle that player.");
                return true;
            }
            player.sendMessage(ChatColor.BLUE + db.get(player.getName()) + " removed from your toggled messages.");
            db.remove(player.getName());
            return true;
        }
        return false;
    }

    @EventHandler
    public void onPlayerToggledMsgChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (db.containsKey(player.getName())) {
            String target = db.get(player.getName());
            Player targetpy = Bukkit.getPlayer(target);
            if (!targetpy.isOnline()) {
                player.sendMessage(ChatColor.RED + "Invalid player or is not online. Use /togglemsg <" + target + "> to untoggle him.");
                return;
            }
            Boolean statusstaffchat = false;
            BaseUser baseUser = this.plugin.getUserManager().getUser(player.getUniqueId());
            PlayerFaction playerFaction = HCF.getPlugin().getFactionManager().getPlayerFaction(player);
            ChatChannel chatChannel = playerFaction.getMember(player).getChatChannel();
            if (chatChannel != ChatChannel.PUBLIC) {
                event.getMessage().replace(event.getMessage(), "!" + event.getMessage());
            }
            if (baseUser.isInStaffChat()) {
                baseUser.setInStaffChat(false);
                event.getMessage().replace(event.getMessage(), "!" + event.getMessage());
                statusstaffchat = true;
            }
            Bukkit.dispatchCommand(player, "msg " + target + " " + event.getMessage());
            event.setCancelled(true);
            if (statusstaffchat) baseUser.setInStaffChat(true);
        }
    }
}
