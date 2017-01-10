package inc.bizties.fifferz.data.models;

public class Player {

    private int id;
    private String name;
    private int score;
    private int numberOfMatches;

    public Player(int id, String name, int score, int numberOfMatches) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.numberOfMatches = numberOfMatches;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void addPoints() {
        this.score += 3;
        this.numberOfMatches++;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public void addMatch() {
        this.numberOfMatches++;
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (id != player.id) return false;
        if (score != player.score) return false;
        if (numberOfMatches != player.numberOfMatches) return false;
        return name != null ? name.equals(player.name) : player.name == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + score;
        result = 31 * result + numberOfMatches;
        return result;
    }
}
