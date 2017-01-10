package inc.bizties.fifferz.components.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.components.adapters.viewholders.PlayerViewHolder;
import inc.bizties.fifferz.components.listeners.PlayerInteractionListener;
import inc.bizties.fifferz.data.models.Player;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private List<Player> collection;
    private PlayerInteractionListener listener;

    public PlayerAdapter(PlayerInteractionListener listener) {
        this.collection = new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_view, parent, false);

        return new PlayerViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        holder.onBindViewHolder(collection.get(position), position);
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public void addItem(Player player) {
        collection.add(player);
        Collections.sort(collection, new TableComparator());
        notifyDataSetChanged();
    }

    public void addAll(List<Player> list) {
        collection.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        collection.remove(position);
        notifyItemRemoved(position);
    }

    public Player getItem(int position) {
        return collection.get(position);
    }

    public void clear() {
        collection.clear();
    }

    public Player addPoints(int position) {
        Player player = collection.get(position);
        player.addPoints();
        Collections.sort(collection, new TableComparator());
        notifyDataSetChanged();
        return player;
    }

    @Nullable
    public Player addMatch(int position) {
        Player player = collection.get(position);
        player.addMatch();
        Collections.sort(collection, new TableComparator());
        notifyDataSetChanged();
        return player;
    }

    public class TableComparator implements Comparator<Player> {
        @Override
        public int compare(Player o1, Player o2) {
            return o2.getScore() - o1.getScore();
        }
    }
}
