package idaniel84.base.command.module.essential;

import net.md_5.bungee.api.ChatColor;
import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommand;
import idaniel84.base.drops.Drop;
import net.veilmc.hcf.utils.ConfigurationService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class DropsCommand extends BaseCommand implements Listener {
    private Inventory inv;
    private HashMap<UUID, String> toggled = new HashMap<UUID, String>();

    private final BasePlugin plugin;

    public DropsCommand(BasePlugin plugin) {
        super("drops", "Toggle mob drops.");
        this.plugin = plugin;
        this.setUsage("/(command)");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }
        if (ConfigurationService.VEILZ) {
            sender.sendMessage(ChatColor.RED + "This command is disabled on VeilZ.");
            return true;
        }
        if (args.length != 0) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        Player player = (Player) sender;
        if (this.plugin.getDropsManager().getDrop(player.getUniqueId().toString()) == null) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "New user registered into Drops database.");
            Drop drop = new Drop(player.getUniqueId().toString(), true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true);
            this.plugin.getDropsManager().createDrop(drop);
        }
        inv = Bukkit.createInventory(player, 54, "Mob Drops Manager");
        DropsGui(player);
        return true;
    }

    private void DropsGui(Player player) {
        Drop drop = this.plugin.getDropsManager().getDrop(player.getUniqueId().toString());

        ItemStack beef = new ItemStack(Material.RAW_BEEF, 1);
        inv.setItem(0, beef);

        ItemStack status_beef = new ItemStack(Material.WOOL, 1, (drop.getBeef().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_beef = status_beef.getItemMeta();
        meta_status_beef.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Raw Beef: " + (drop.getBeef().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_beef.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_beef.setItemMeta(meta_status_beef);
        inv.setItem(1, status_beef);

        ItemStack pork = new ItemStack(Material.PORK, 1);
        inv.setItem(9, pork);

        ItemStack status_pork = new ItemStack(Material.WOOL, 1, (drop.getPork().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_pork = status_pork.getItemMeta();
        meta_status_pork.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Raw Porkchop: " + (drop.getPork().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_pork.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_pork.setItemMeta(meta_status_pork);
        inv.setItem(10, status_pork);

        ItemStack chicken = new ItemStack(Material.RAW_CHICKEN, 1);
        inv.setItem(18, chicken);

        ItemStack status_chicken = new ItemStack(Material.WOOL, 1, (drop.getChicken().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_chicken = status_chicken.getItemMeta();
        meta_status_chicken.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Raw Chicken: " + (drop.getChicken().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_chicken.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_chicken.setItemMeta(meta_status_chicken);
        inv.setItem(19, status_chicken);

        ItemStack feather = new ItemStack(Material.FEATHER, 1);
        inv.setItem(27, feather);

        ItemStack status_feather = new ItemStack(Material.WOOL, 1, (drop.getFeather().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_feather = status_feather.getItemMeta();
        meta_status_feather.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Feather: " + (drop.getFeather().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_feather.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_feather.setItemMeta(meta_status_feather);
        inv.setItem(28, status_feather);

        ItemStack wool = new ItemStack(Material.WOOL, 1);
        inv.setItem(36, wool);

        ItemStack status_wool = new ItemStack(Material.WOOL, 1, (drop.getWool().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_wool = status_wool.getItemMeta();
        meta_status_wool.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Wool: " + (drop.getWool().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_wool.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_wool.setItemMeta(meta_status_wool);
        inv.setItem(37, status_wool);

        ItemStack leather = new ItemStack(Material.LEATHER, 1);
        inv.setItem(45, leather);

        ItemStack status_leather = new ItemStack(Material.WOOL, 1, (drop.getLeather().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_leather = status_leather.getItemMeta();
        meta_status_leather.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Leather: " + (drop.getLeather().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_leather.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_leather.setItemMeta(meta_status_leather);
        inv.setItem(46, status_leather);

        ItemStack magmacream = new ItemStack(Material.MAGMA_CREAM, 1);
        inv.setItem(21, magmacream);

        ItemStack status_magmacream = new ItemStack(Material.WOOL, 1, (drop.getMagmacream().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_magmacream = status_magmacream.getItemMeta();
        meta_status_magmacream.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Magma Cream: " + (drop.getMagmacream().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_magmacream.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_magmacream.setItemMeta(meta_status_magmacream);
        inv.setItem(30, status_magmacream);

        ItemStack ghasttear = new ItemStack(Material.GHAST_TEAR, 1);
        inv.setItem(22, ghasttear);

        ItemStack status_ghasttear = new ItemStack(Material.WOOL, 1, (drop.getGhasttear().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_ghasttear = status_ghasttear.getItemMeta();
        meta_status_ghasttear.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Ghast Tear: " + (drop.getGhasttear().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_ghasttear.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_ghasttear.setItemMeta(meta_status_ghasttear);
        inv.setItem(31, status_ghasttear);

        ItemStack nugget = new ItemStack(Material.GOLD_NUGGET, 1);
        inv.setItem(23, nugget);

        ItemStack status_nugget = new ItemStack(Material.WOOL, 1, (drop.getNugget().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_nugget = status_nugget.getItemMeta();
        meta_status_nugget.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Gold Nugget: " + (drop.getNugget().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_nugget.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_nugget.setItemMeta(meta_status_nugget);
        inv.setItem(32, status_nugget);

        ItemStack blazerod = new ItemStack(Material.BLAZE_ROD, 1);
        inv.setItem(48, blazerod);

        ItemStack status_blazerod = new ItemStack(Material.WOOL, 1, (drop.getBlazerod().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_blazerod = status_blazerod.getItemMeta();
        meta_status_blazerod.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Blaze Rod: " + (drop.getBlazerod().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_blazerod.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_blazerod.setItemMeta(meta_status_blazerod);
        inv.setItem(39, status_blazerod);

        ItemStack gunpowder = new ItemStack(Material.SULPHUR, 1);
        inv.setItem(49, gunpowder);

        ItemStack status_gunpowder = new ItemStack(Material.WOOL, 1, (drop.getGunpowder().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_gunpowder = status_gunpowder.getItemMeta();
        meta_status_gunpowder.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Gunpowder: " + (drop.getGunpowder().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_gunpowder.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_gunpowder.setItemMeta(meta_status_gunpowder);
        inv.setItem(40, status_gunpowder);

        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 1);
        inv.setItem(50, enderpearl);

        ItemStack status_enderpearl = new ItemStack(Material.WOOL, 1, (drop.getEnderpearl().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_enderpearl = status_enderpearl.getItemMeta();
        meta_status_enderpearl.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Ender Pearl: " + (drop.getEnderpearl().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_enderpearl.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_enderpearl.setItemMeta(meta_status_enderpearl);
        inv.setItem(41, status_enderpearl);

        ItemStack flesh = new ItemStack(Material.ROTTEN_FLESH, 1);
        inv.setItem(8, flesh);

        ItemStack status_flesh = new ItemStack(Material.WOOL, 1, (drop.getFlesh().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_flesh = status_flesh.getItemMeta();
        meta_status_flesh.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Rotten Flesh: " + (drop.getFlesh().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_flesh.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_flesh.setItemMeta(meta_status_flesh);
        inv.setItem(7, status_flesh);

        ItemStack arrow = new ItemStack(Material.ARROW, 1);
        inv.setItem(17, arrow);

        ItemStack status_arrow = new ItemStack(Material.WOOL, 1, (drop.getArrow().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_arrow = status_arrow.getItemMeta();
        meta_status_arrow.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Arrow: " + (drop.getArrow().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_arrow.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_arrow.setItemMeta(meta_status_arrow);
        inv.setItem(16, status_arrow);

        ItemStack bone = new ItemStack(Material.BONE, 1);
        inv.setItem(26, bone);

        ItemStack status_bone = new ItemStack(Material.WOOL, 1, (drop.getBone().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_bone = status_bone.getItemMeta();
        meta_status_bone.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Bone: " + (drop.getBone().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_bone.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_bone.setItemMeta(meta_status_bone);
        inv.setItem(25, status_bone);

        ItemStack string = new ItemStack(Material.STRING, 1);
        inv.setItem(35, string);

        ItemStack status_string = new ItemStack(Material.WOOL, 1, (drop.getStringItem().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_string = status_string.getItemMeta();
        meta_status_string.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "String: " + (drop.getStringItem().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_string.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_string.setItemMeta(meta_status_string);
        inv.setItem(34, status_string);

        ItemStack spidereye = new ItemStack(Material.SPIDER_EYE, 1);
        inv.setItem(44, spidereye);

        ItemStack status_spidereye = new ItemStack(Material.WOOL, 1, (drop.getSpidereye().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_spidereye = status_spidereye.getItemMeta();
        meta_status_spidereye.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Spider Eye: " + (drop.getSpidereye().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_spidereye.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_spidereye.setItemMeta(meta_status_spidereye);
        inv.setItem(43, status_spidereye);

        ItemStack slimeball = new ItemStack(Material.SLIME_BALL, 1);
        inv.setItem(53, slimeball);

        ItemStack status_slimeball = new ItemStack(Material.WOOL, 1, (drop.getSlimeball().equals(false) ? (short) 14 : (short) 5));
        ItemMeta meta_status_slimeball = status_slimeball.getItemMeta();
        meta_status_slimeball.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Slimeball: " + (drop.getSlimeball().equals(false) ? ChatColor.RED + "Disabled." : ChatColor.GREEN + "Enabled."));
        meta_status_slimeball.setLore(Arrays.asList(ChatColor.YELLOW + "Click to change the status of this drop."));
        status_slimeball.setItemMeta(meta_status_slimeball);
        inv.setItem(52, status_slimeball);

        ItemStack enable_all = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta meta_enable_all = enable_all.getItemMeta();
        meta_enable_all.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Enable all.");
        enable_all.setItemMeta(meta_enable_all);
        inv.setItem(3, enable_all);

        ItemStack disable_all = new ItemStack(Material.INK_SACK, 1, (short) 1);
        ItemMeta meta_disable_all = disable_all.getItemMeta();
        meta_disable_all.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Disable all.");
        disable_all.setItemMeta(meta_disable_all);
        inv.setItem(5, disable_all);

        ItemStack book = new ItemStack(Material.BOOK, 1);
        ItemMeta meta_book = book.getItemMeta();
        meta_book.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "Read before using.");
        meta_book.setLore(Arrays.asList(ChatColor.YELLOW + "Use this GUI to disable specific mob drops by clicking on the wools.", ChatColor.YELLOW + "While you have one disabled, you wont be able to pickup it."));
        meta_book.addEnchant(Enchantment.DURABILITY, 1, true);
        book.setItemMeta(meta_book);
        inv.setItem(4, book);

        player.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!event.getInventory().getName().equals("Mob Drops Manager")) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        Drop drop = this.plugin.getDropsManager().getDrop(player.getUniqueId().toString());
        event.setCancelled(true);
        switch (event.getRawSlot()) {
            case 1:
                if (drop.getBeef().equals(false)) drop.setBeef(true);
                else drop.setBeef(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 10:
                if (drop.getPork().equals(false)) drop.setPork(true);
                else drop.setPork(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 19:
                if (drop.getChicken().equals(false)) drop.setChicken(true);
                else drop.setChicken(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 28:
                if (drop.getFeather().equals(false)) drop.setFeather(true);
                else drop.setFeather(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 37:
                if (drop.getWool().equals(false)) drop.setWool(true);
                else drop.setWool(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 46:
                if (drop.getLeather().equals(false)) drop.setLeather(true);
                else drop.setLeather(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 30:
                if (drop.getMagmacream().equals(false)) drop.setMagmacream(true);
                else drop.setMagmacream(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 31:
                if (drop.getGhasttear().equals(false)) drop.setGhasttear(true);
                else drop.setGhasttear(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 32:
                if (drop.getNugget().equals(false)) drop.setNugget(true);
                else drop.setNugget(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 39:
                if (drop.getBlazerod().equals(false)) drop.setBlazerod(true);
                else drop.setBlazerod(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 40:
                if (drop.getGunpowder().equals(false)) drop.setGunpowder(true);
                else drop.setGunpowder(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 41:
                if (drop.getEnderpearl().equals(false)) drop.setEnderpearl(true);
                else drop.setEnderpearl(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 7:
                if (drop.getFlesh().equals(false)) drop.setFlesh(true);
                else drop.setFlesh(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 16:
                if (drop.getArrow().equals(false)) drop.setArrow(true);
                else drop.setArrow(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 25:
                if (drop.getBone().equals(false)) drop.setBone(true);
                else drop.setBone(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 34:
                if (drop.getStringItem().equals(false)) drop.setStringItem(true);
                else drop.setStringItem(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 43:
                if (drop.getSpidereye().equals(false)) drop.setSpidereye(true);
                else drop.setSpidereye(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 52:
                if (drop.getSlimeball().equals(false)) drop.setSlimeball(true);
                else drop.setSlimeball(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have changed the status of one drop.");
                break;
            case 3:
                drop.setBeef(true);
                drop.setPork(true);
                drop.setChicken(true);
                drop.setFeather(true);
                drop.setWool(true);
                drop.setLeather(true);
                drop.setMagmacream(true);
                drop.setGhasttear(true);
                drop.setNugget(true);
                drop.setBlazerod(true);
                drop.setGunpowder(true);
                drop.setEnderpearl(true);
                drop.setFlesh(true);
                drop.setArrow(true);
                drop.setBone(true);
                drop.setStringItem(true);
                drop.setSpidereye(true);
                drop.setSlimeball(true);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have enabled all drops.");
                break;
            case 5:
                drop.setBeef(false);
                drop.setPork(false);
                drop.setChicken(false);
                drop.setFeather(false);
                drop.setWool(false);
                drop.setLeather(false);
                drop.setMagmacream(false);
                drop.setGhasttear(false);
                drop.setNugget(false);
                drop.setBlazerod(false);
                drop.setGunpowder(false);
                drop.setEnderpearl(false);
                drop.setFlesh(false);
                drop.setArrow(false);
                drop.setBone(false);
                drop.setStringItem(false);
                drop.setSpidereye(false);
                drop.setSlimeball(false);
                player.closeInventory();
                player.sendMessage(ChatColor.BLUE + "You have disabled all drops.");
                break;
        }
    }
    @EventHandler
    public void onItemPickupSetCancelled(PlayerPickupItemEvent event) {
        Player player = event.getPlayer();
        Drop drop = this.plugin.getDropsManager().getDrop(player.getUniqueId().toString());
        if (drop != null) {
            if (event.getItem().getItemStack().getType() == Material.RAW_BEEF && drop.getBeef().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.PORK && drop.getPork().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.RAW_CHICKEN && drop.getChicken().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.FEATHER && drop.getFeather().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.WOOL && drop.getWool().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.LEATHER && drop.getLeather().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.MAGMA_CREAM && drop.getMagmacream().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.GHAST_TEAR && drop.getGhasttear().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.GOLD_NUGGET && drop.getNugget().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.BLAZE_ROD && drop.getBlazerod().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.SULPHUR && drop.getGunpowder().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.ENDER_PEARL && drop.getEnderpearl().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.ROTTEN_FLESH && drop.getFlesh().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.ARROW && drop.getArrow().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.BONE && drop.getBone().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.STRING && drop.getStringItem().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.SPIDER_EYE && drop.getSpidereye().equals(false)) event.setCancelled(true);
            if (event.getItem().getItemStack().getType() == Material.SLIME_BALL && drop.getSlimeball().equals(false)) event.setCancelled(true);
        }
    }

    /*@EventHandler
    public void onEntitieKillNotDropItems(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player && !(event.getEntity() instanceof Player)) {
            Drop drop = this.plugin.getDropsManager().getDrop(event.getEntity().getKiller().getUniqueId().toString());
            if (event.getEntity() instanceof Cow && drop.getBeef().equals(false)) event.getDrops().remove(new ItemStack(Material.RAW_BEEF));
            if (event.getEntity() instanceof Pig && drop.getPork().equals(false)) event.getDrops().remove(new ItemStack(Material.PORK));
            if (event.getEntity() instanceof Chicken && drop.getChicken().equals(false)) event.getDrops().remove(new ItemStack(Material.RAW_CHICKEN));
            if (event.getEntity() instanceof Chicken && drop.getFeather().equals(false)) event.getDrops().remove(new ItemStack(Material.FEATHER));
            if (event.getEntity() instanceof Sheep && drop.getWool().equals(false)) event.getDrops().remove(new ItemStack(Material.WOOL));
            if (event.getEntity() instanceof Cow && drop.getLeather().equals(false)) event.getDrops().remove(new ItemStack(Material.LEATHER));
            if (event.getEntity() instanceof MagmaCube && drop.getMagmacream().equals(false)) event.getDrops().remove(new ItemStack(Material.MAGMA_CREAM));
            if (event.getEntity() instanceof Ghast && drop.getGhasttear().equals(false)) event.getDrops().remove(new ItemStack(Material.GHAST_TEAR));
            if (event.getEntity() instanceof PigZombie && drop.getNugget().equals(false)) event.getDrops().remove(new ItemStack(Material.GOLD_NUGGET));
            if (event.getEntity() instanceof Blaze && drop.getBlazerod().equals(false)) event.getDrops().remove(new ItemStack(Material.BLAZE_ROD));
            if (event.getEntity() instanceof Creeper && drop.getGunpowder().equals(false)) event.getDrops().remove(new ItemStack(Material.SULPHUR));
            if (event.getEntity() instanceof Enderman && drop.getEnderpearl().equals(false)) event.getDrops().remove(new ItemStack(Material.ENDER_PEARL));
            if (event.getEntity() instanceof Zombie && drop.getFlesh().equals(false)) event.getDrops().remove(new ItemStack(Material.ROTTEN_FLESH));
            if (event.getEntity() instanceof Skeleton && drop.getArrow().equals(false)) event.getDrops().remove(new ItemStack(Material.ARROW));
            if (event.getEntity() instanceof Skeleton && drop.getBone().equals(false)) event.getDrops().remove(new ItemStack(Material.BONE));
            if (event.getEntity() instanceof Spider && drop.getStringItem().equals(false)) event.getDrops().remove(new ItemStack(Material.STRING));
            if (event.getEntity() instanceof Spider && drop.getSpidereye().equals(false)) event.getDrops().remove(new ItemStack(Material.SPIDER_EYE));
            if (event.getEntity() instanceof Slime && drop.getSlimeball().equals(false)) event.getDrops().remove(new ItemStack(Material.SLIME_BALL));
        }
    }*/
}
