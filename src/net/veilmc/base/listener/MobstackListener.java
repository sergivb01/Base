package net.veilmc.base.listener;
import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.primitives.Ints;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.server.v1_7_R4.MobSpawnerAbstract;
import net.minecraft.server.v1_7_R4.TileEntityMobSpawner;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.veilmc.base.BaseConstants;
import net.veilmc.base.BasePlugin;
import net.veilmc.hcf.utils.ConfigurationService;
import net.veilmc.util.cuboid.CoordinatePair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class MobstackListener
		extends BukkitRunnable
		implements Listener
{
	private static final int NATURAL_STACK_RADIUS = 20;
	private static final int MAX_STACKED_QUANTITY = 150;
	private static final int OTHER_STACK_RADIUS = 8;
	public static final String STACKED_PREFIX = ChatColor.GREEN.toString() + "x";
	private Table<CoordinatePair, EntityType, Integer> naturalSpawnStacks;
	private Map<MobSpawnerAbstract, Integer> mobSpawnerAbstractIntegerMap = Maps.newHashMap();
	private BasePlugin plugin;

	public MobstackListener(BasePlugin plugin)
	{
		this.naturalSpawnStacks = HashBasedTable.create();
		this.plugin = plugin;
		runTaskTimer(plugin, 40L, 1200L);
	}

	private CoordinatePair fromLocation(Location location)
	{
		return new CoordinatePair(location.getWorld(), Math.round(location.getBlockX() / 20), Math.round(location.getBlockZ() / 20));
	}

	public void run()
	{
		long now = System.currentTimeMillis();
		Iterator localIterator2;
		LivingEntity entity;
		for (World world : Bukkit.getServer().getWorlds()) {
			if (world.getEnvironment() != World.Environment.THE_END) {
				for (localIterator2 = world.getLivingEntities().iterator(); localIterator2.hasNext();)
				{
					entity = (LivingEntity)localIterator2.next();
					if ((entity.isValid()) && (!entity.isDead())) {
						if (((entity instanceof Animals)) || ((entity instanceof Monster))) {
							for (org.bukkit.entity.Entity nearby : entity.getNearbyEntities(8.0D, 8.0D, 8.0D)) {
								if ((nearby != null) && ((nearby instanceof LivingEntity)) && (!nearby.isDead()) && (nearby.isValid())) {
									if (((nearby instanceof Animals)) || ((nearby instanceof Monster))) {
										if (stack((LivingEntity)nearby, entity))
										{
											if (this.naturalSpawnStacks.containsValue(Integer.valueOf(entity.getEntityId())))
											{
												for (Map.Entry<CoordinatePair, Integer> entry : this.naturalSpawnStacks.column(entity.getType()).entrySet()) {
													if (((Integer)entry.getValue()).intValue() == entity.getEntityId())
													{
														this.naturalSpawnStacks.put(entry.getKey(), entity.getType(), Integer.valueOf(nearby.getEntityId()));
														break;
													}
												}
												break;
											}
											if (!this.mobSpawnerAbstractIntegerMap.containsValue(Integer.valueOf(entity.getEntityId()))) {
												break;
											}
											for (Map.Entry<MobSpawnerAbstract, Integer> entry : this.mobSpawnerAbstractIntegerMap.entrySet()) {
												if (((Integer)entry.getValue()).intValue() == entity.getEntityId())
												{
													this.mobSpawnerAbstractIntegerMap.put(entry.getKey(), Integer.valueOf(nearby.getEntityId()));
													break;
												}
											}
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	@EventHandler(ignoreCancelled=true, priority=EventPriority.HIGH)
	public void onSpawnerSpawn(SpawnerSpawnEvent event)
	{
		if (!canRemove(event.getEntity())) {
			return;
		}
		if (event.getSpawner().getWorld().getEnvironment() != World.Environment.THE_END)
		{
			CreatureSpawner creatureSpawner = event.getSpawner();
			TileEntityMobSpawner tile = (TileEntityMobSpawner)((CraftWorld)creatureSpawner.getWorld()).getTileEntityAt(creatureSpawner.getX(), creatureSpawner.getY(), creatureSpawner.getZ());
			Integer integer = (Integer)this.mobSpawnerAbstractIntegerMap.get(tile.getSpawner());
			if (integer != null)
			{
				net.minecraft.server.v1_7_R4.Entity nmsTarget = ((CraftWorld)creatureSpawner.getWorld()).getHandle().getEntity(integer.intValue());
				if (nmsTarget != null)
				{
					org.bukkit.entity.Entity target = nmsTarget.getBukkitEntity();
					if ((target != null) && ((target instanceof LivingEntity)) && (target.isValid()) && (!target.isDead()) && (target.getLocation().distance(creatureSpawner.getBlock().getLocation()) < 10.0D))
					{
						event.setCancelled(true);
						LivingEntity targetLiving = (LivingEntity)target;
						int stackedQuantity = getStackedQuantity(targetLiving);
						if (stackedQuantity == -1) {
							stackedQuantity = 1;
						}
						setStackedQuantity(targetLiving, Math.min(150, stackedQuantity + 1));
						return;
					}
				}
			}
			this.mobSpawnerAbstractIntegerMap.put(tile.getSpawner(), Integer.valueOf(event.getEntity().getEntityId()));
		}
	}

	@EventHandler(ignoreCancelled=true, priority=EventPriority.HIGHEST)
	public void onCreatureSpawn(CreatureSpawnEvent event)
	{
		EntityType entityType = event.getEntityType();
		switch (event.getSpawnReason())
		{
			case CHUNK_GEN:
			case NATURAL:
			case DEFAULT:
				Location location = event.getLocation();
				CoordinatePair coordinatePair = fromLocation(location);
				Optional<Integer> entityIdOptional = Optional.fromNullable(this.naturalSpawnStacks.get(coordinatePair, entityType));
				if (entityIdOptional.isPresent())
				{
					int entityId = ((Integer)entityIdOptional.get()).intValue();
					net.minecraft.server.v1_7_R4.Entity nmsTarget = ((CraftWorld) location.getWorld()).getHandle().getEntity(entityId);
					Entity target = nmsTarget == null ? null : nmsTarget.getBukkitEntity();
					//Entity target = BasePlugin.getPlugin().getNmsProvider().getEntityFromID(location.getWorld(), entityId);
					if ((target != null) && ((target instanceof LivingEntity)))
					{
						LivingEntity targetLiving = (LivingEntity)target;
						boolean canSpawn;
						if ((targetLiving instanceof Ageable)) {
							canSpawn = ((Ageable)targetLiving).isAdult();
						} else {
							canSpawn = (!(targetLiving instanceof Zombie)) || (!((Zombie)targetLiving).isBaby());
						}
						if (canSpawn)
						{
							int stackedQuantity = getStackedQuantity(targetLiving);
							if (stackedQuantity == -1) {
								stackedQuantity = 1;
							}
							int stacked = Math.min(150, stackedQuantity + 1);
							setStackedQuantity(targetLiving, stacked);
							event.setCancelled(true);
						}
					}
				}
				this.naturalSpawnStacks.put(coordinatePair, entityType, Integer.valueOf(event.getEntity().getEntityId()));
				break;
		}
	}

	@EventHandler(ignoreCancelled=true, priority=EventPriority.HIGH)
	public void onEntityDeath(EntityDeathEvent event)
	{
		LivingEntity livingEntity = event.getEntity();
		if (livingEntity == null) {
			return;
		}
		int stackedQuantity = getStackedQuantity(livingEntity);
		LivingEntity respawned;
		if (stackedQuantity > 1)
		{
			respawned = (LivingEntity)livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), event.getEntityType());
			((CraftLivingEntity)respawned).getHandle().fromMobSpawner = true;
			setStackedQuantity(respawned, Math.min(150, stackedQuantity - 1));
			if ((respawned instanceof Ageable)) {
				((Ageable)respawned).setAdult();
			}
			if ((respawned instanceof Slime)) {
				((Slime)respawned).setSize(3);
			}
			if ((respawned instanceof Zombie)) {
				((Zombie)respawned).setBaby(false);
			}
			if (this.mobSpawnerAbstractIntegerMap.containsValue(Integer.valueOf(livingEntity.getEntityId()))) {
				for (Map.Entry<MobSpawnerAbstract, Integer> entry : this.mobSpawnerAbstractIntegerMap.entrySet()) {
					if (((Integer)entry.getValue()).intValue() == livingEntity.getEntityId())
					{
						this.mobSpawnerAbstractIntegerMap.put(entry.getKey(), Integer.valueOf(respawned.getEntityId()));
						return;
					}
				}
			} else if (this.naturalSpawnStacks.containsValue(Integer.valueOf(livingEntity.getEntityId()))) {
				for (Map.Entry<CoordinatePair, Integer> entry : this.naturalSpawnStacks.column(livingEntity.getType()).entrySet()) {
					if (((Integer)entry.getValue()).intValue() == livingEntity.getEntityId())
					{
						this.naturalSpawnStacks.put(entry.getKey(), respawned.getType(), Integer.valueOf(respawned.getEntityId()));
						return;
					}
				}
			}
		}
		else
		{
			this.naturalSpawnStacks.values().remove(Integer.valueOf(livingEntity.getEntityId()));
			this.mobSpawnerAbstractIntegerMap.values().remove(Integer.valueOf(livingEntity.getEntityId()));
		}
	}

	private int getStackedQuantity(LivingEntity livingEntity)
	{
		if (livingEntity == null) {
			return -1;
		}
		String customName = livingEntity.getCustomName();
		if ((customName == null) || (!customName.contains(STACKED_PREFIX))) {
			return -1;
		}
		customName = customName.replace(STACKED_PREFIX, "");
		if (customName == null) {
			return -1;
		}
		customName = ChatColor.stripColor(customName);
		return Ints.tryParse(customName).intValue();
	}

	private boolean stack(LivingEntity tostack, LivingEntity toremove)
	{
		if ((tostack == null) || (toremove == null) || (!tostack.isValid()) || (!toremove.isValid()) || (toremove.getType() != tostack.getType()) || ((tostack instanceof MagmaCube)) || ((tostack instanceof Slime)) || ((tostack instanceof Villager)) || (ConfigurationService.VEILZ && ((tostack instanceof Zombie)))) {
			return false;
		}
		Integer newStack = Integer.valueOf(1);
		Integer removeStack = Integer.valueOf(1);
		if (hasStack(tostack)) {
			newStack = Integer.valueOf(getStackedQuantity(tostack));
		}
		if (hasStack(toremove)) {
			removeStack = Integer.valueOf(getStackedQuantity(toremove));
		} else if ((getStackedQuantity(toremove) == -1) && (toremove.getCustomName() != null) && (toremove.getCustomName().contains(ChatColor.WHITE.toString()))) {
			return false;
		}
		toremove.remove();
		tostack.eject();
		toremove.eject();
		setStackedQuantity(tostack, Math.min(150, newStack.intValue() + removeStack.intValue()));
		return true;
	}

	public boolean canRemove(org.bukkit.entity.Entity toremove)
	{
		return (!(toremove instanceof MagmaCube)) && (!(toremove instanceof Slime)) && (!(toremove instanceof Villager) && (ConfigurationService.VEILZ && (!(toremove instanceof Zombie))));
	}

	private boolean hasStack(LivingEntity livingEntity)
	{
		return getStackedQuantity(livingEntity) != -1;
	}

	private void setStackedQuantity(LivingEntity livingEntity, int quantity)
	{
		livingEntity.eject();
		livingEntity.setPassenger(null);
		if (quantity <= 1)
		{
			livingEntity.setCustomName(null);
		}
		else
		{
			livingEntity.setCustomName(STACKED_PREFIX + quantity);
			livingEntity.setCustomNameVisible(false);
		}
	}
}
