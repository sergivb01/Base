package idaniel84.base.command.module;

import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommandModule;
import idaniel84.base.command.module.essential.*;

public class EssentialModule
		extends BaseCommandModule{
	public EssentialModule(BasePlugin plugin){
		this.commands.add(new CrashCommand(plugin));
		this.commands.add(new SettingsCommand(plugin));
		this.commands.add(new SetViewDistanceCommand());
		this.commands.add(new LolCommand(plugin));
		this.commands.add(new ToggleDonorOnly(plugin));
		this.commands.add(new ClearLagg());
		this.commands.add(new SNoteCommand());
		this.commands.add(new StaffServerCommand());
		this.commands.add(new RequestCommand());
		this.commands.add(new TrollCommand(plugin));
		this.commands.add(new BanWaveCommand(plugin));
		this.commands.add(new AmivisCommand(plugin));
		this.commands.add(new DonateCommand());
		this.commands.add(new AutoRestartCommand(plugin));
		this.commands.add(new BoomCommand(plugin));
		this.commands.add(new ListCommand());
		this.commands.add(new EnchantCommand());
		this.commands.add(new NoteCommand());
		this.commands.add(new PunishCommand());
		this.commands.add(new FeedCommand());
		this.commands.add(new FlyCommand());
		this.commands.add(new KillMobsCommand(plugin));
		this.commands.add(new NearCommand());
		this.commands.add(new FreezeCommand(plugin));
		this.commands.add(new GamemodeCommand());
		this.commands.add(new HatCommand());
		this.commands.add(new StaffUtilitiesCommand(plugin));
		this.commands.add(new HealCommand());
		this.commands.add(new KillCommand());
		this.commands.add(new PingCommand());
		this.commands.add(new PlayTimeCommand(plugin));
		this.commands.add(new RemoveEntityCommand());
		this.commands.add(new RenameCommand());
		this.commands.add(new ReportCommand());
		this.commands.add(new RepairCommand());
		this.commands.add(new LagCommand());
		this.commands.add(new RulesCommand(plugin));
		this.commands.add(new SetMotdCommand(plugin));
		this.commands.add(new InsiderCommand(plugin));
		this.commands.add(new SpeedCommand());
		this.commands.add(new StopLagCommand(plugin));
		this.commands.add(new SudoCommand());
		this.commands.add(new VanishCommand(plugin));
		this.commands.add(new WhoisCommand(plugin));
		this.commands.add(new DropsCommand(plugin));
		this.commands.add(new BlacklistCommand());
		this.commands.add(new CheckOpsCommand());
		this.commands.add(new VanishStaffCommand());
		this.commands.add(new ConsoleExecuteCommand());
	}
}

