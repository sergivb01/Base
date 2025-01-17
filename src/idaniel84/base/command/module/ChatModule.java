package idaniel84.base.command.module;

import idaniel84.base.BasePlugin;
import idaniel84.base.command.BaseCommandModule;
import idaniel84.base.command.module.chat.*;

public class ChatModule
		extends BaseCommandModule{
	public ChatModule(BasePlugin plugin){
		this.commands.add(new ToggleSoundsCommand(plugin));
		this.commands.add(new ToggleStaffChatCommand(plugin));
		this.commands.add(new AnnouncementCommand(plugin));
		this.commands.add(new BroadcastCommand(plugin));
		this.commands.add(new ClearChatCommand());
		this.commands.add(new DisableChatCommand(plugin));
		this.commands.add(new SlowChatCommand(plugin));
		this.commands.add(new StaffChatCommand(plugin));
		this.commands.add(new FamousCommand(plugin));
		this.commands.add(new YoutubeCommand(plugin));
		this.commands.add(new IgnoreCommand(plugin));
		this.commands.add(new MessageCommand(plugin));
		this.commands.add(new MessageSpyCommand(plugin));
		this.commands.add(new ReplyCommand(plugin));
		this.commands.add(new ToggleMessagesCommand(plugin));
		this.commands.add(new ToggleMsgCommand(plugin));
	}
}

