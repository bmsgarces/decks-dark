package inc.bizties.fifferz.data.cache;

import java.util.List;

import inc.bizties.fifferz.data.models.Player;

public interface Cache {

    List<Player> getPlayers();

    Player getPlayer(String name);

    void addPlayer(Player player);

    void removePlayer(Player player);
}
