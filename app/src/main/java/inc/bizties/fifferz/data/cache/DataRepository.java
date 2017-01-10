package inc.bizties.fifferz.data.cache;

import java.util.List;

import inc.bizties.fifferz.data.models.Player;

public enum DataRepository {

    INSTANCE;

    private static final String TAG = DataRepository.class.getName();

    private Cache diskCache;

    public void setDiskCache(Cache diskCache) {
        this.diskCache = diskCache;
    }

    public List<Player> getPlayers() {
        return diskCache.getPlayers();
    }

    public void addPlayer(Player player) {
        diskCache.addPlayer(player);
    }

    public void removePlayer(Player player) {
        diskCache.removePlayer(player);
    }
}
