package idaniel84.base.user;

import com.google.common.collect.Sets;
import idaniel84.base.BasePlugin;
import idaniel84.util.GenericUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class ServerParticipator implements ConfigurationSerializable{
	private final UUID uniqueId;
	private final Set<String> ignoring = Sets.newTreeSet((Comparator) String.CASE_INSENSITIVE_ORDER);
	private final Set<String> messageSpying = Sets.newHashSet();
	private UUID lastRepliedTo;
	private boolean inStaffChat;
	private boolean globalChatVisible = true;
	private boolean staffChatVisible = true;
	private boolean messagesVisible = true;
	private long lastSpeakTimeMillis;
	private long lastReceivedMessageMillis;
	private long lastSentMessageMillis;

	public ServerParticipator(UUID uniqueId){
		this.uniqueId = uniqueId;
	}

	public ServerParticipator(Map<String, Object> map){
		this.uniqueId = UUID.fromString((String) map.get("uniqueID"));
		this.ignoring.addAll(GenericUtils.createList(map.get("ignoring"), String.class));
		this.messageSpying.addAll(GenericUtils.createList(map.get("messageSpying"), String.class));
		Object object = map.get("lastRepliedTo");
		if(object instanceof String){
			this.lastRepliedTo = UUID.fromString((String) object);
		}
		if((object = map.get("inStaffChat")) instanceof Boolean){
			this.inStaffChat = (Boolean) object;
		}
		if((object = map.get("globalChatVisible")) instanceof Boolean){
			this.globalChatVisible = (Boolean) object;
		}
		if((object = map.get("staffChatVisible")) instanceof Boolean){
			this.staffChatVisible = (Boolean) object;
		}
		if((object = map.get("messagesVisible")) instanceof Boolean){
			this.messagesVisible = (Boolean) object;
		}
		if((object = map.get("lastSpeakTimeMillis")) instanceof String){
			this.lastSpeakTimeMillis = Long.parseLong((String) object);
		}
		if((object = map.get("lastReceivedMessageMillis")) instanceof String){
			this.lastReceivedMessageMillis = Long.parseLong((String) object);
		}
		if((object = map.get("lastSentMessageMillis")) instanceof String){
			this.lastSentMessageMillis = Long.parseLong((String) object);
		}
	}

	public Map<String, Object> serialize(){
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("uniqueID", this.uniqueId.toString());
		map.put("ignoring", new ArrayList<String>(this.ignoring));
		map.put("messageSpying", new ArrayList<String>(this.messageSpying));
		if(this.lastRepliedTo != null){
			map.put("lastRepliedTo", this.lastRepliedTo.toString());
		}
		map.put("inStaffChat", this.inStaffChat);
		map.put("globalChatVisible", this.globalChatVisible);
		map.put("staffChatVisible", this.staffChatVisible);
		map.put("messagesVisible", this.messagesVisible);
		map.put("lastSpeakTimeMillis", Long.toString(this.lastSpeakTimeMillis));
		map.put("lastReceivedMessageMillis", Long.toString(this.lastReceivedMessageMillis));
		map.put("lastSentMessageMillis", Long.toString(this.lastSentMessageMillis));
		return map;
	}

	public abstract String getName();

	public UUID getUniqueId(){
		return this.uniqueId;
	}

	public Set<String> getIgnoring(){
		return this.ignoring;
	}

	public Set<String> getMessageSpying(){
		return this.messageSpying;
	}

	public UUID getLastRepliedTo(){
		return this.lastRepliedTo;
	}

	public void setLastRepliedTo(UUID lastRepliedTo){
		this.lastRepliedTo = lastRepliedTo;
	}

	public Player getLastRepliedToPlayer(){
		return Bukkit.getPlayer((UUID) this.lastRepliedTo);
	}

	public boolean isInStaffChat(){
		return this.inStaffChat;
	}

	public void setInStaffChat(boolean inStaffChat){
		this.inStaffChat = inStaffChat;
	}

	public boolean isGlobalChatVisible(){
		return this.globalChatVisible;
	}

	public void setGlobalChatVisible(boolean globalChatVisible){
		this.globalChatVisible = globalChatVisible;
	}

	public boolean isStaffChatVisible(){
		return this.staffChatVisible;
	}

	public void setStaffChatVisible(boolean staffChatVisible){
		this.staffChatVisible = staffChatVisible;
	}

	public boolean isMessagesVisible(){
		return this.messagesVisible;
	}

	public void setMessagesVisible(boolean messagesVisible){
		this.messagesVisible = messagesVisible;
	}

	public long getLastSpeakTimeRemaining(){
		if(this.lastSpeakTimeMillis > 0){
			return this.lastSpeakTimeMillis - System.currentTimeMillis();
		}
		return 0;
	}

	public long getLastSpeakTimeMillis(){
		return this.lastSpeakTimeMillis;
	}

	public void setLastSpeakTimeMillis(long lastSpeakTimeMillis){
		this.lastSpeakTimeMillis = lastSpeakTimeMillis;
	}

	public void updateLastSpeakTime(){
		long slowChatDelay = (long) BasePlugin.getPlugin().getServerHandler().getChatSlowedDelay() * 1000;
		this.lastSpeakTimeMillis = System.currentTimeMillis() + slowChatDelay;
	}

	public long getLastReceivedMessageMillis(){
		return this.lastReceivedMessageMillis;
	}

	public void setLastReceivedMessageMillis(long lastReceivedMessageMillis){
		this.lastReceivedMessageMillis = lastReceivedMessageMillis;
	}

	public long getLastSentMessageMillis(){
		return this.lastSentMessageMillis;
	}

	public void setLastSentMessageMillis(long lastSentMessageMillis){
		this.lastSentMessageMillis = lastSentMessageMillis;
	}
}

