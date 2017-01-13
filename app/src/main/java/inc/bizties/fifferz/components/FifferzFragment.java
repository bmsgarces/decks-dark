package inc.bizties.fifferz.components;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.components.adapters.PlayerAdapter;
import inc.bizties.fifferz.components.callbacks.SimpleItemTouchHelperCallback;
import inc.bizties.fifferz.components.listeners.PlayerInteractionListener;
import inc.bizties.fifferz.core.fragments.BasePresenterFragment;
import inc.bizties.fifferz.data.models.Player;

public class FifferzFragment extends BasePresenterFragment<FifferzFragmentPresenter> implements PlayerInteractionListener {

    private ViewGroup rootView;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private PlayerAdapter adapter;
    private View tableHeader;
    private View noContent;

    @Override
    public FifferzFragmentPresenter initializePresenter() {
        return new FifferzFragmentPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        tableHeader = rootView.findViewById(R.id.table_header);
        noContent = rootView.findViewById(R.id.view_no_content);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        adapter = new PlayerAdapter(this);

        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().fetchTimelineAsync();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(null);
            }
        });

        initSwipe();

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getPresenter().onLoad();
    }

    private void initSwipe(){
        SimpleItemTouchHelperCallback simpleItemTouchHelperCallback = new SimpleItemTouchHelperCallback.Builder(getActivity())
                .setDragDirs(0)
                .setSwipeDirs(ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
                .setListener(this)
                .build();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void manageNoContent() {
        if (adapter.getItemCount() > 0) {
            tableHeader.setVisibility(View.VISIBLE);
            noContent.setVisibility(View.GONE);
        } else {
            tableHeader.setVisibility(View.GONE);
            noContent.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgress() {
        if (swipeContainer != null && swipeContainer.isRefreshing()) {
            swipeContainer.setRefreshing(false);
        }
    }

    public void displayData(List<Player> list) {
        adapter.clear();
        adapter.addAll(list);
    }

    @SuppressLint("InflateParams")
    private void showDialog(@Nullable Player player) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View layout = getActivity().getLayoutInflater().inflate(R.layout.dialog_layout, null);
        builder.setView(layout);

        final EditText name = (EditText) layout.findViewById(R.id.player_name);
        final EditText score = (EditText) layout.findViewById(R.id.player_score);
        final EditText matches = (EditText) layout.findViewById(R.id.number_of_matches);
        final int playerId = player == null ? 0 : player.getId();
        if (player != null) {
            name.setText(player.getName());
            score.setText(String.valueOf(player.getScore()));
            matches.setText(String.valueOf(player.getNumberOfMatches()));
        }

        builder.setPositiveButton(R.string.action_confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Player toUpdate = new Player(playerId, name.getText().toString(),
                        parseValue(score.getText().toString()), parseValue(matches.getText().toString()));
                getPresenter().updatePlayer(toUpdate);
            }
        });

        builder.create().show();
    }

    public int parseValue(String score) {
        try {
            return Integer.parseInt(score);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void showError(@StringRes int stringRes) {
        Snackbar.make(rootView, stringRes, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onAddPoints(int position) {
        getPresenter().updatePlayer(adapter.addPoints(position));
    }

    @Override
    public void onAddMatch(int position) {
        getPresenter().updatePlayer(adapter.addMatch(position));
    }

    @Override
    public void onPlayerClick(int position) {
        showDialog(adapter.getItem(position));
    }
}
