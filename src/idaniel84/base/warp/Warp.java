package idaniel84.base.warp;


import com.google.common.base.Preconditions;

import java.util.Map;

import idaniel84.util.PersistableLocation;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public class Warp
        extends PersistableLocation
        implements ConfigurationSerializable {
    private String name;
    private String permission;

    public Warp(String name, Location location) {
        super(location);
        Preconditions.checkNotNull(name, "Warp name cannot be null");
        Preconditions.checkNotNull(location, "Warp location cannot be null");
        this.name = name;
        this.permission = ("warp." + name);
    }

    public Warp(Map<String, Object> map) {
        super(map);
        this.name = ((String) map.get("name"));
        this.permission = ((String) map.get("permission"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = super.serialize();
        map.put("name", this.name);
        map.put("permission", this.permission);
        return map;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        Preconditions.checkNotNull(name, "Warp name cannot be null");
        this.name = name;
    }

    public String getPermission() {
        return this.permission;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (getClass() != o.getClass())) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Warp warp = (Warp) o;
        if (this.name != null) {
            if (!this.name.equals(warp.name)) {
                return false;
            }
        } else if (warp.name != null) {
            return false;
        }
        return false;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (this.name != null ? this.name.hashCode() : 0);
        return result;
    }
}
