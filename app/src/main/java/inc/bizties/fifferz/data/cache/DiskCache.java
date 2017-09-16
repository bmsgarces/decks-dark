package inc.bizties.fifferz.data.cache;

import android.content.Context;

import java.util.List;

import inc.bizties.fifferz.data.models.Player;
import inc.bizties.fifferz.data.provider.tables.PlayerTable;

public class DiskCache implements Cache {

    private PlayerTable mPlayerTable;

    private final Context mContext;

    public DiskCache(Context context) {
        this.mContext = context.getApplicationContext();
        mPlayerTable = new PlayerTable();
    }

    @Override
    public List<Player> getPlayers() {
        return mPlayerTable.getPlayers(mContext);
    }

    @Override
    public Player getPlayer(String name) {
        return mPlayerTable.getPlayer(mContext, name);
    }

    @Override
    public void addPlayer(Player player) {
        mPlayerTable.addPlayer(mContext, player);
    }

    @Override
    public void removePlayer(Player player) {
        mPlayerTable.removePlayer(mContext, player);
    }
}
