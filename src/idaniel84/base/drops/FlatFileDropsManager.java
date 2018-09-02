package idaniel84.base.drops;

import idaniel84.util.Config;
import idaniel84.util.GenericUtils;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class FlatFileDropsManager implements DropsManager{

    private final JavaPlugin plugin;
    private Map<String, Drop> dropNameMap;
    private Config config;
    private List<Drop> drop;

    public FlatFileDropsManager(JavaPlugin plugin)
    {
        this.drop = new ArrayList();
        this.plugin = plugin;
        reloadDropData();
    }

    public Collection<Drop> getDrops()
    {
        return this.dropNameMap.values();
    }

    public Drop getDrop(String uuid)
    {
        return this.dropNameMap.get(uuid);
    }

    public boolean containsDrop(Drop drop)
    {
        return this.drop.contains(drop);
    }

    public void createDrop(Drop drop)
    {
        if (this.drop.add(drop)) {
            this.dropNameMap.put(drop.getName(), drop);
        }
    }

    public void removeDrop(Drop drop)
    {
        if (this.drop.remove(drop)) {
            this.dropNameMap.remove(drop.getName());
        }
    }

    public void reloadDropData()
    {
        this.config = new Config(this.plugin, "drops");
        Object object = this.config.get("drop");
        if ((object instanceof List)) {
            this.drop = GenericUtils.createList(object, Drop.class);
            this.dropNameMap = new CaseInsensitiveMap();
            for (Drop drop : this.drop) {
                this.dropNameMap.put(drop.getName(), drop);
            }
        }
    }

    public void saveDropData()
    {
        this.config.set("drop", this.drop);
        this.config.save();
    }
}
