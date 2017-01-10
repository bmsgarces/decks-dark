package inc.bizties.fifferz.components.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.components.listeners.PlayerInteractionListener;
import inc.bizties.fifferz.data.models.Player;

public class PlayerViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{

    private PlayerInteractionListener listener;

    private TextView position;
    public TextView name;
    private TextView score;
    private TextView numberOfMatches;

    public PlayerViewHolder(View v, PlayerInteractionListener listener) {
        super(v);
        this.listener = listener;

        position = (TextView) v.findViewById(R.id.position);
        name = (TextView) v.findViewById(R.id.name);
        score = (TextView) v.findViewById(R.id.score);
        numberOfMatches = (TextView) v.findViewById(R.id.number_of_matches);

        v.setOnLongClickListener(this);
    }

    public void onBindViewHolder(final Player player, final int index) {
        position.setText(String.valueOf(index + 1));
        name.setText(player.getName());
        score.setText(String.valueOf(player.getScore()));
        numberOfMatches.setText(String.valueOf(player.getNumberOfMatches()));
    }

    @Override
    public boolean onLongClick(View v) {
        listener.onPlayerClick(getAdapterPosition());
        return false;
    }
}
