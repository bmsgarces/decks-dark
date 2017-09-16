package inc.bizties.fifferz.data.cache;

import java.util.List;

import inc.bizties.fifferz.data.models.Player;

public class DataRepository {

    private static final String TAG = DataRepository.class.getName();

    private static DataRepository mInstance;

    public static DataRepository getInstance() {
        if (mInstance == null) {
            mInstance = new DataRepository();
        }

        return mInstance;
    }

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
