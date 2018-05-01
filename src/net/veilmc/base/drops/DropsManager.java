package net.veilmc.base.drops;

import java.util.Collection;

public interface DropsManager {

    Collection<Drop> getDrops();

    Drop getDrop(String var1);

    boolean containsDrop(Drop var1);

    void createDrop(Drop var1);

    void removeDrop(Drop var1);

    void reloadDropData();

    void saveDropData();
}
