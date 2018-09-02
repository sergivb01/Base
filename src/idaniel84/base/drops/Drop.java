package idaniel84.base.drops;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public class Drop implements ConfigurationSerializable {
    private String uuid;
    private Boolean beef;
    private Boolean pork;
    private Boolean chicken;
    private Boolean feather;
    private Boolean wool;
    private Boolean leather;
    private Boolean magmacream;
    private Boolean ghasttear;
    private Boolean nugget;
    private Boolean blazerod;
    private Boolean gunpowder;
    private Boolean enderpearl;
    private Boolean flesh;
    private Boolean arrow;
    private Boolean bone;
    private Boolean string;
    private Boolean spidereye;
    private Boolean slimeball;

    public Drop(String uuid, Boolean beef, Boolean pork, Boolean chicken, Boolean feather, Boolean wool, Boolean leather, Boolean magmacream, Boolean ghasttear, Boolean nugget, Boolean blazerod, Boolean gunpowder, Boolean enderpearl, Boolean flesh, Boolean arrow, Boolean bone, Boolean string, Boolean spidereye, Boolean slimeball) {
        this.uuid = uuid;
        this.beef = beef;
        this.pork = pork;
        this.chicken = chicken;
        this.feather = feather;
        this.wool = wool;
        this.leather = leather;
        this.magmacream = magmacream;
        this.ghasttear = ghasttear;
        this.nugget = nugget;
        this.blazerod = blazerod;
        this.gunpowder = gunpowder;
        this.enderpearl = enderpearl;
        this.flesh = flesh;
        this.arrow = arrow;
        this.bone = bone;
        this.string = string;
        this.spidereye = spidereye;
        this.slimeball = slimeball;
    }

    public Drop(Map map) {
        this.uuid = ((String) map.get("uuid"));
        this.beef = ((Boolean) map.get("beef"));
        this.pork = ((Boolean) map.get("pork"));
        this.chicken = ((Boolean) map.get("chicken"));
        this.feather = ((Boolean) map.get("feather"));
        this.wool = ((Boolean) map.get("wool"));
        this.leather = ((Boolean) map.get("leather"));
        this.magmacream = ((Boolean) map.get("magmacream"));
        this.ghasttear = ((Boolean) map.get("ghasttear"));
        this.nugget = ((Boolean) map.get("nugget"));
        this.blazerod = ((Boolean) map.get("blazerod"));
        this.gunpowder = ((Boolean) map.get("gunpowder"));
        this.enderpearl = ((Boolean) map.get("enderpearl"));
        this.flesh = ((Boolean) map.get("flesh"));
        this.arrow = ((Boolean) map.get("arrow"));
        this.bone = ((Boolean) map.get("bone"));
        this.string = ((Boolean) map.get("string"));
        this.spidereye = ((Boolean) map.get("spidereye"));
        this.slimeball = ((Boolean) map.get("slimeball"));
    }

    public Map<String, Object> serialize() {
        Map<String, Object> map = Maps.newLinkedHashMap();
        map.put("uuid", this.uuid);
        map.put("beef", this.beef);
        map.put("pork", this.pork);
        map.put("chicken", this.chicken);
        map.put("feather", this.feather);
        map.put("wool", this.wool);
        map.put("leather", this.leather);
        map.put("magmacream", this.magmacream);
        map.put("ghasttear", this.ghasttear);
        map.put("nugget", this.nugget);
        map.put("blazerod", this.blazerod);
        map.put("gunpowder", this.gunpowder);
        map.put("enderpearl", this.enderpearl);
        map.put("flesh", this.flesh);
        map.put("arrow", this.arrow);
        map.put("bone", this.bone);
        map.put("string", this.string);
        map.put("spidereye", this.spidereye);
        map.put("slimeball", this.slimeball);
        return map;
    }

    public String getName() {
        return this.uuid;
    }

    public void setName(String uuid) {
        Preconditions.checkNotNull(uuid, "User name cannot be null");
        this.uuid = uuid;
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
        Drop drop = (Drop) o;
        if (this.uuid != null) {
            if (!this.uuid.equals(drop.uuid)) {
                return false;
            }
        } else if (drop.uuid != null) {
            return false;
        }
        return false;
    }

    public Boolean getBeef() {
        return this.beef;
    }
    public void setBeef(Boolean var1) {
        this.beef = var1;
    }

    public Boolean getPork() {
        return this.pork;
    }
    public void setPork(Boolean var1) {
        this.pork = var1;
    }

    public Boolean getChicken() {
        return this.chicken;
    }
    public void setChicken(Boolean var1) {
        this.chicken = var1;
    }

    public Boolean getFeather() {
        return this.feather;
    }
    public void setFeather(Boolean var1) {
        this.feather = var1;
    }

    public Boolean getWool() {
        return this.wool;
    }
    public void setWool(Boolean var1) {
        this.wool = var1;
    }

    public Boolean getLeather() {
        return this.leather;
    }
    public void setLeather(Boolean var1) {
        this.leather = var1;
    }

    public Boolean getMagmacream() {
        return this.magmacream;
    }
    public void setMagmacream(Boolean var1) {
        this.magmacream = var1;
    }

    public Boolean getGhasttear() {
        return this.ghasttear;
    }
    public void setGhasttear(Boolean var1) {
        this.ghasttear = var1;
    }

    public Boolean getNugget() {
        return this.nugget;
    }
    public void setNugget(Boolean var1) {
        this.nugget = var1;
    }

    public Boolean getBlazerod() {
        return this.blazerod;
    }
    public void setBlazerod(Boolean var1) {
        this.blazerod = var1;
    }

    public Boolean getGunpowder() {
        return this.gunpowder;
    }
    public void setGunpowder(Boolean var1) {
        this.gunpowder = var1;
    }

    public Boolean getEnderpearl() {
        return this.enderpearl;
    }
    public void setEnderpearl(Boolean var1) {
        this.enderpearl = var1;
    }

    public Boolean getFlesh() {
        return this.flesh;
    }
    public void setFlesh(Boolean var1) {
        this.flesh = var1;
    }

    public Boolean getArrow() {
        return this.arrow;
    }
    public void setArrow(Boolean var1) {
        this.arrow = var1;
    }

    public Boolean getBone() {
        return this.bone;
    }
    public void setBone(Boolean var1) {
        this.bone = var1;
    }

    public Boolean getStringItem() {
        return this.string;
    }
    public void setStringItem(Boolean var1) {
        this.string = var1;
    }

    public Boolean getSpidereye() {
        return this.spidereye;
    }
    public void setSpidereye(Boolean var1) {
        this.spidereye = var1;
    }

    public Boolean getSlimeball() {
        return this.slimeball;
    }
    public void setSlimeball(Boolean var1) {
        this.slimeball = var1;
    }
}
