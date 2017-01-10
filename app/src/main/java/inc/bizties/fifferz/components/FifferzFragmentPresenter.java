package inc.bizties.fifferz.components;

import inc.bizties.fifferz.R;
import inc.bizties.fifferz.core.presenters.BasePresenter;
import inc.bizties.fifferz.data.cache.DataRepository;
import inc.bizties.fifferz.data.models.Player;

class FifferzFragmentPresenter extends BasePresenter<FifferzFragment> {

    private DataRepository repository;

    void onLoad() {
        repository = DataRepository.INSTANCE;

        fetchTimelineAsync();
    }

    void fetchTimelineAsync() {
        getView().displayData(repository.getPlayers());

        getView().manageNoContent();
        getView().hideProgress();
    }

    void updatePlayer(Player player) {

        if (player == null) {
            return;
        }

        if (player.getName().isEmpty()) {
            getView().showNameError();
            return;
        }

        if (player.getScore() % 3 != 0) {
            getView().showError(R.string.invalid_score);
            return;
        }

        if (!isValidPair(player.getScore(), player.getNumberOfMatches())) {
            getView().showError(R.string.invalid_score_match_combination);
            return;
        }

        repository.addPlayer(player);
        fetchTimelineAsync();
    }

    private boolean isValidPair(int score, int matches) {
        return (score == 0 && matches == 0) || (score > 0 && matches >= score / 3);
    }
}
