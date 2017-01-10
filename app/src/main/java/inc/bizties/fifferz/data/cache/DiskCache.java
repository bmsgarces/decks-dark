package inc.bizties.fifferz.data.cache;

import android.content.Context;

import java.util.List;

import inc.bizties.fifferz.data.models.Player;
import inc.bizties.fifferz.data.provider.tables.PlayerTable;

public class DiskCache implements Cache {

    private PlayerTable playerTable;

    private final Context context;

    public DiskCache(Context context) {
        this.context = context.getApplicationContext();
        playerTable = new PlayerTable();
    }

    @Override
    public List<Player> getPlayers() {
        return playerTable.getPlayers(context);
    }

    @Override
    public Player getPlayer(String name) {
        return playerTable.getPlayer(context, name);
    }

    @Override
    public void addPlayer(Player player) {
        playerTable.addPlayer(context, player);
    }

    @Override
    public void removePlayer(Player player) {
        playerTable.removePlayer(context, player);
    }
}
