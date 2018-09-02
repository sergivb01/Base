package idaniel84.base;

import lombok.Getter;
import idaniel84.base.command.CommandManager;
import idaniel84.base.command.SimpleCommandManager;
import idaniel84.base.command.module.ChatModule;
import idaniel84.base.command.module.EssentialModule;
import idaniel84.base.command.module.InventoryModule;
import idaniel84.base.command.module.TeleportModule;
import idaniel84.base.command.module.chat.ChatCommands;
import idaniel84.base.command.module.chat.ToggleMsgCommand;
import idaniel84.base.command.module.essential.*;
import idaniel84.base.command.module.teleport.WorldCommand;
import idaniel84.base.drops.Drop;
import idaniel84.base.drops.DropsManager;
import idaniel84.base.drops.FlatFileDropsManager;
import idaniel84.base.kit.*;
import idaniel84.base.listener.*;
import idaniel84.base.task.AnnouncementHandler;
import idaniel84.base.task.AutoRestartHandler;
import idaniel84.base.task.ClearEntityHandler;
import idaniel84.base.user.*;
import idaniel84.base.warp.FlatFileWarpManager;
import idaniel84.base.warp.Warp;
import idaniel84.base.warp.WarpManager;
import idaniel84.util.PersistableLocation;
import idaniel84.util.RandomUtils;
import idaniel84.util.SignHandler;
import idaniel84.util.bossbar.BossBarManager;
import idaniel84.util.chat.Lang;
import idaniel84.util.cuboid.Cuboid;
import idaniel84.util.cuboid.NamedCuboid;
import idaniel84.util.itemdb.ItemDb;
import idaniel84.util.itemdb.SimpleItemDb;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Random;

public class BasePlugin extends JavaPlugin{
	@Getter
	private static BasePlugin plugin;
	@Getter
	public BukkitRunnable announcementTask;
	@Getter
	private ItemDb itemDb;
	@Getter
	private Random random = new Random();
	@Getter
	private WarpManager warpManager;
	@Getter
	private RandomUtils randomUtils;
	@Getter
	private AutoRestartHandler autoRestartHandler;
	@Getter
	private BukkitRunnable clearEntityHandler;
	@Getter
	private CommandManager commandManager;
	@Getter
	private KitManager kitManager;
	@Getter
	private PlayTimeManager playTimeManager;
	@Getter
	private ServerHandler serverHandler;
	@Getter
	private SignHandler signHandler;
	@Getter
	private UserManager userManager;
	@Getter
	private KitExecutor kitExecutor;
	@Getter
	private DropsManager drop;
	//@Getter private ConfigFile langFile;

	public void onEnable(){
		plugin = this;

		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		ConfigurationSerialization.registerClass(Warp.class);
		ConfigurationSerialization.registerClass(Drop.class);
		ConfigurationSerialization.registerClass(ServerParticipator.class);
		ConfigurationSerialization.registerClass(BaseUser.class);
		ConfigurationSerialization.registerClass(ConsoleUser.class);
		ConfigurationSerialization.registerClass(NameHistory.class);
		ConfigurationSerialization.registerClass(PersistableLocation.class);
		ConfigurationSerialization.registerClass(Cuboid.class);
		ConfigurationSerialization.registerClass(NamedCuboid.class);
		ConfigurationSerialization.registerClass(Kit.class);

		this.registerManagers();
		this.registerCommands();
		this.registerListeners();
		this.reloadSchedulers();

		Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "clearlag 100000");
	}


	public void onDisable(){
		super.onDisable();

		BossBarManager.unhook();

		this.kitManager.saveKitData();
		this.playTimeManager.savePlaytimeData();
		this.serverHandler.saveServerData();
		this.signHandler.cancelTasks(null);
		this.userManager.saveParticipatorData();
		this.warpManager.saveWarpData();
		this.drop.saveDropData();

		plugin = null;
	}

	public static BasePlugin getPlugin() { return plugin; }


	private void registerManagers(){
		BossBarManager.hook();

		this.randomUtils = new RandomUtils();
		this.autoRestartHandler = new AutoRestartHandler(this);
		this.kitManager = new FlatFileKitManager(this);
		this.serverHandler = new ServerHandler(this);
		this.signHandler = new SignHandler(this);
		this.userManager = new UserManager(this);
		this.itemDb = new SimpleItemDb(this);
		this.warpManager = new FlatFileWarpManager(this);
		this.drop = new FlatFileDropsManager(this);

		try{
			Lang.initialize("en_US");
		}catch(IOException ex){
			ex.printStackTrace();
		}

	}

	private void registerCommands(){
		this.commandManager = new SimpleCommandManager(this);
		this.commandManager.registerAll(new ChatModule(this));
		this.commandManager.registerAll(new EssentialModule(this));
		this.commandManager.registerAll(new InventoryModule(this));
		this.commandManager.registerAll(new TeleportModule(this));
		this.kitExecutor = new KitExecutor(this);
		this.getCommand("kit").setExecutor(this.kitExecutor);
	}

	public KitExecutor getKitExecutor(){
		return this.kitExecutor;
	}

	public DropsManager getDropsManager() { return this.drop; }

	public UserManager getUserManager() { return this.userManager; }

	public AutoRestartHandler getAutoRestartHandler() { return this.autoRestartHandler;}

	public RandomUtils getRandomUtils() { return randomUtils; }

	public ItemDb getItemDb() { return itemDb; }

	public SignHandler getSignHandler() { return signHandler; }

	public WarpManager getWarpManager() { return warpManager; }

	public ServerHandler getServerHandler() { return serverHandler; }

	public KitManager getKitManager() { return kitManager; }

	public PlayTimeManager getPlayTimeManager() { return playTimeManager; }

	public BukkitRunnable getClearEntityHandler() { return this.clearEntityHandler; }

	public CommandManager getCommandManager() { return commandManager; }

	private void registerListeners(){
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(new WorldCommand(), this);
		manager.registerEvents(new ChatListener(this), this);
		manager.registerEvents(new PunishCommand(), this);
		manager.registerEvents(new ColouredSignListener(), this);
		manager.registerEvents(new DecreasedLagListener(this), this);
		manager.registerEvents(new JoinListener(this), this);
		manager.registerEvents(new ReportCommand(), this);
		manager.registerEvents(new KitListener(this), this);
		manager.registerEvents(new MoveByBlockEvent(), this);
		manager.registerEvents(new MobstackListener(), this);
		manager.registerEvents(new StaffListener(), this);
		manager.registerEvents(new NameVerifyListener(this), this);
		this.playTimeManager = new PlayTimeManager(this);
		manager.registerEvents(this.playTimeManager, this);
		manager.registerEvents(new PlayerLimitListener(), this);
		manager.registerEvents(new VanishListener(this), this);
		manager.registerEvents(new ChatCommands(), this);
		//manager.registerEvents(new AutoMuteListener(this), this);
		manager.registerEvents(new StaffUtilsRemoveListener(), this);
		manager.registerEvents(new DropsCommand(this), this);
		manager.registerEvents(new ToggleMsgCommand(this), this);
		//manager.registerEvents(new BlacklistCommand(), this);
		manager.registerEvents(new CheckOpsCommand(), this);
		manager.registerEvents(new VanishStaffCommand(), this);
	}


	private void reloadSchedulers(){
		ClearEntityHandler clearEntityHandler;
		AnnouncementHandler announcementTask;

		if(this.clearEntityHandler != null) this.clearEntityHandler.cancel();
		if(this.announcementTask != null) this.announcementTask.cancel();

		long announcementDelay = (long) this.serverHandler.getAnnouncementDelay() * 20;
		long claggdelay = (long) this.serverHandler.getClearlagdelay() * 20;

		this.announcementTask = announcementTask = new AnnouncementHandler(this);
		MobstackListener mobstackListener = new MobstackListener();
		this.clearEntityHandler = clearEntityHandler = new ClearEntityHandler();

		mobstackListener.runTaskTimerAsynchronously(this, 20, 20);
		clearEntityHandler.runTaskTimer(this, claggdelay, claggdelay);
		announcementTask.runTaskTimer(this, announcementDelay, announcementDelay);
	}


}

