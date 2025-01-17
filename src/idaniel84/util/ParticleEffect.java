package idaniel84.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import net.minecraft.server.v1_7_R4.MathHelper;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public enum ParticleEffect{
	HUGE_EXPLODE("hugeexplosion", 0),
	LARGE_EXPLODE("largeexplode", 1),
	FIREWORK_SPARK("fireworksSpark", 2),
	AIR_BUBBLE("bubble", 3),
	SUSPEND("suspend", 4),
	DEPTH_SUSPEND("depthSuspend", 5),
	TOWN_AURA("townaura", 6),
	CRITICAL_HIT("crit", 7),
	MAGIC_CRITICAL_HIT("magicCrit", 8),
	MOB_SPELL("mobSpell", 9),
	MOB_SPELL_AMBIENT("mobSpellAmbient", 10),
	SPELL("spell", 11),
	INSTANT_SPELL("instantSpell", 12),
	BLUE_SPARKLE("witchMagic", 13),
	NOTE_BLOCK("note", 14),
	ENDER("portal", 15),
	ENCHANTMENT_TABLE("enchantmenttable", 16),
	EXPLODE("explode", 17),
	FIRE("flame", 18),
	LAVA_SPARK("lava", 19),
	FOOTSTEP("footstep", 20),
	SPLASH("splash", 21),
	LARGE_SMOKE("largesmoke", 22),
	CLOUD("cloud", 23),
	REDSTONE_DUST("reddust", 24),
	SNOWBALL_HIT("snowballpoof", 25),
	DRIP_WATER("dripWater", 26),
	DRIP_LAVA("dripLava", 27),
	SNOW_DIG("snowshovel", 28),
	SLIME("slime", 29),
	HEART("heart", 30),
	ANGRY_VILLAGER("angryVillager", 31),
	GREEN_SPARKLE("happyVillager", 32),
	ICONCRACK("iconcrack", 33),
	TILECRACK("tilecrack", 34);

	private final String name;
	@Deprecated
	private final int id;

	ParticleEffect(String name, int id){
		this.name = name;
		this.id = id;
	}

	@Deprecated
	String getName(){
		return this.name;
	}

	@Deprecated
	public int getId(){
		return this.id;
	}

	public void display(Player player, float x, float y, float z, float speed, int amount){
		this.display(player, x, y, z, 0.0f, 0.0f, 0.0f, speed, amount);
	}

	public void display(Player player, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount){
		PacketPlayOutWorldParticles packet = this.createPacket(x, y, z, offsetX, offsetY, offsetZ, speed, amount);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public void display(Player player, Location location, float speed, int amount){
		this.display(player, location, 0.0f, 0.0f, 0.0f, speed, amount);
	}

	public void display(Player player, Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount){
		PacketPlayOutWorldParticles packet = this.createPacket(location, offsetX, offsetY, offsetZ, speed, amount);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	public void broadcast(float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount){
		PacketPlayOutWorldParticles packet = this.createPacket(x, y, z, offsetX, offsetY, offsetZ, speed, amount);
		for(Player player : Bukkit.getServer().getOnlinePlayers()){
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void broadcast(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount){
		this.broadcast(location, offsetX, offsetY, offsetZ, speed, amount, null, null);
	}

	public void broadcast(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount, Entity source){
		this.broadcast(location, offsetX, offsetY, offsetZ, speed, amount, source, null);
	}

	public void broadcast(final Location location, final float offsetX, final float offsetY, final float offsetZ, final float speed, final int amount, final Entity source, final Predicate<Player> predicate){
		final Packet packet = this.createPacket(location, offsetX, offsetY, offsetZ, speed, amount);
		for(final Player player : Bukkit.getOnlinePlayers()){
			if((source == null || player.canSee((Player) source)) && (predicate == null || predicate.apply(player))){
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public void sphere(Player player, Location location, float radius){
		this.sphere(player, location, radius, 20.0f, 2);
	}

	public void sphere(Player player, Location location, float radius, float density, int intensity){
		Preconditions.checkNotNull((Object) location, "Location cannot be null");
		Preconditions.checkArgument(radius >= 0.0f, "Radius must be positive");
		Preconditions.checkArgument(density >= 0.0f, "Density must be positive");
		Preconditions.checkArgument(intensity >= 0, "Intensity must be positive");
		float deltaPitch = 180.0f / density;
		float deltaYaw = 360.0f / density;
		World world = location.getWorld();
		int i = 0;
		while((float) i < density){
			int j = 0;
			while((float) j < density){
				float pitch = -90.0f + (float) j * deltaPitch;
				float yaw = -180.0f + (float) i * deltaYaw;
				float x = radius * MathHelper.sin((-yaw) * 0.017453292f - 3.1415927f) * (-MathHelper.cos((-pitch) * 0.017453292f)) + (float) location.getX();
				float y = radius * MathHelper.sin((-pitch) * 0.017453292f) + (float) location.getY();
				float z = radius * MathHelper.cos((-yaw) * 0.017453292f - 3.1415927f) * (-MathHelper.cos((-pitch) * 0.017453292f)) + (float) location.getZ();
				Location target = new Location(world, (double) x, (double) y, (double) z);
				if(player == null){
					this.broadcast(target, 0.0f, 0.0f, 0.0f, 0.0f, intensity);
				}else{
					this.display(player, target, 0.0f, 0.0f, 0.0f, 0.0f, intensity);
				}
				++j;
			}
			++i;
		}
	}

	private PacketPlayOutWorldParticles createPacket(Location location, float offsetX, float offsetY, float offsetZ, float speed, int amount){
		return this.createPacket((float) location.getX(), (float) location.getY(), (float) location.getZ(), offsetX, offsetY, offsetZ, speed, amount);
	}

	private PacketPlayOutWorldParticles createPacket(float x, float y, float z, float offsetX, float offsetY, float offsetZ, float speed, int amount){
		Preconditions.checkArgument(speed >= 0.0f, "Speed must be positive");
		Preconditions.checkArgument(amount > 0, "Cannot use less than one particle.");
		return new PacketPlayOutWorldParticles(this.name, x, y, z, offsetX, offsetY, offsetZ, speed, amount);
	}
}

