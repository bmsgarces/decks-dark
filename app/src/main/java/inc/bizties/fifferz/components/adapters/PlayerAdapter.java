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

    private List<Player> mCollection;
    private PlayerInteractionListener mListener;

    public PlayerAdapter(PlayerInteractionListener listener) {
        this.mCollection = new ArrayList<>();
        this.mListener = listener;
    }

    @Override
    public PlayerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_item_view, parent, false);

        return new PlayerViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(PlayerViewHolder holder, int position) {
        holder.onBindViewHolder(mCollection.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mCollection.size();
    }

    public void addItem(Player player) {
        mCollection.add(player);
        Collections.sort(mCollection, new TableComparator());
        notifyDataSetChanged();
    }

    public void addAll(List<Player> list) {
        Collections.sort(list, new TableComparator());
        mCollection.clear();
        mCollection.addAll(list);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mCollection.remove(position);
        notifyItemRemoved(position);
    }

    public Player getItem(int position) {
        return mCollection.get(position);
    }

    public void clear() {
        mCollection.clear();
    }

    public Player addPoints(int position) {
        Player player = mCollection.get(position);
        player.addPoints();
        Collections.sort(mCollection, new TableComparator());
        notifyDataSetChanged();
        return player;
    }

    @Nullable
    public Player addMatch(int position) {
        Player player = mCollection.get(position);
        player.addMatch();
        Collections.sort(mCollection, new TableComparator());
        notifyDataSetChanged();
        return player;
    }

    public class TableComparator implements Comparator<Player> {
        @Override
        public int compare(Player p1, Player p2) {
            return p1.getAvg() > p2.getAvg() ? -1 : p1.getAvg() < p2.getAvg() ? 1
                    : p1.getScore() > p2.getScore() ? -1 : p1.getScore() < p2.getScore() ? 1
                            : p1.getName().compareToIgnoreCase(p2.getName());
        }
    }
}
