package idaniel84.base.warp;

import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;
import idaniel84.util.Config;
import idaniel84.util.GenericUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class FlatFileWarpManager
		implements WarpManager
{
	private final JavaPlugin plugin;
	private String warpDelayWords;
	private long warpDelayMillis;
	private long warpDelayTicks;
	private Map<String, Warp> warpNameMap;
	private Config config;
	private List<Warp> warp;

	public FlatFileWarpManager(JavaPlugin plugin)
	{
		this.warp = new ArrayList();
		this.plugin = plugin;
		reloadWarpData();
	}

	public Collection<String> getWarpNames()
	{
		return this.warpNameMap.keySet();
	}

	public Collection<Warp> getWarps()
	{
		return this.warpNameMap.values();
	}

	public Warp getWarp(String warpName)
	{
		return (Warp)this.warpNameMap.get(warpName);
	}

	public boolean containsWarp(Warp warp)
	{
		return this.warp.contains(warp);
	}

	public void createWarp(Warp warp)
	{
		if (this.warp.add(warp)) {
			this.warpNameMap.put(warp.getName(), warp);
		}
	}

	public void removeWarp(Warp warp)
	{
		if (this.warp.remove(warp)) {
			this.warpNameMap.remove(warp.getName());
		}
	}

	public String getWarpDelayWords()
	{
		return this.warpDelayWords;
	}

	public long getWarpDelayMillis()
	{
		return this.warpDelayMillis;
	}

	public long getWarpDelayTicks()
	{
		return this.warpDelayTicks;
	}

	public void reloadWarpData()
	{
		this.config = new Config(this.plugin, "warps");
		Object object = this.config.get("warp");
		if ((object instanceof List))
		{
			this.warp = GenericUtils.createList(object, Warp.class);
			this.warpNameMap = new CaseInsensitiveMap();
			for (Warp warp : this.warp) {
				this.warpNameMap.put(warp.getName(), warp);
			}
		}
		this.warpDelayMillis = TimeUnit.SECONDS.toMillis((this.plugin.getConfig().get("warp-delay-seconds") != null) ? this.plugin.getConfig().getInt("warp-delay-seconds") : 10);
		this.warpDelayTicks = (this.warpDelayMillis / 50L);
		this.warpDelayWords = DurationFormatUtils.formatDurationWords(this.warpDelayMillis, true, true);
	}

	public void saveWarpData()
	{
		this.plugin.getConfig().set("warp-delay-millis", Long.valueOf(this.warpDelayMillis));
		this.plugin.saveConfig();
		this.config.set("warp", this.warp);
		this.config.save();
	}
}
