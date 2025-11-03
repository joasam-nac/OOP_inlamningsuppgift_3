public class GameLogic {
    private Grid grid;
    private final boolean isPresenting;

    public GameLogic(int width, int height, boolean demonstrationMode) {
        this.isPresenting = demonstrationMode;
        this.grid = createNewGrid(width, height);
    }

    private Grid createNewGrid(int width, int height) {
        return isPresenting ?
                Grid.demonstrationGrid(width, height) :
                new Grid(width, height);
    }

    public void newGame() {
        this.grid = createNewGrid(grid.getWidth(), grid.getHeight());
    }

    public boolean moveTile(int value) {
        if (grid.canMoveTile(value)) {
            grid.moveTile(value);
            return true;
        }
        return false;
    }

    public boolean playerHasWon() {
        return grid.playerHasWon();
    }

    public Grid getGrid() {
        return grid;
    }

    public int getWidth() {
        return grid.getWidth();
    }

    public int getHeight() {
        return grid.getHeight();
    }
}