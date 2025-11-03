import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    @Test
    public void testGridCreate4x4() {
        Grid grid = new Grid(4, 4);
        assertEquals(16, grid.getTiles().size(), "Should create 16 tiles");
    }

    @Test
    public void testSeedsWork() {
                long seed = 12345L;
        Grid grid1 = new Grid(4, 4, seed);
        Grid grid2 = new Grid(4, 4, seed);

        for (int i = 0; i < grid1.getTiles().size(); i++) {
            Tile tile1 = grid1.getTiles().get(i);
            Tile tile2 = grid2.getTiles().get(i);
            assertEquals(tile1.getX(), tile2.getX(), "Should be same X value");
            assertEquals(tile1.getY(), tile2.getY(), "Should be same Y value");
        }
    }

    @Test
    public void canMoveMovableTile() {
        long seed = 12345L;
        Grid grid = new Grid(4, 4, seed);

        Tile empty = grid.findTileByValue(0);
        Tile movable = null;

        // letar efter närliggande bricka
        for (Tile t : grid.getTiles()) {
            if (t.getValue() != 0) {
                int distanceX = Math.abs(t.getX() - empty.getX());
                int distanceY = Math.abs(t.getY() - empty.getY());
                if ((distanceX == 1 && distanceY == 0) || (distanceX == 0 && distanceY == 1)) {
                    movable = t;
                    break;
                }
            }
        }

        assertNotNull(movable, "There should be a movable tile touching empty place in grid");
        assertTrue(grid.canMoveTile(movable.getValue()), "Tile near empty place should be movable");
    }

    @Test
    public void testSwitchPosition() {
        long seed = 12345L;
        Grid grid = new Grid(4, 4, seed);

        Tile empty = grid.findTileByValue(0);
        Tile movable = null;

        for (Tile t : grid.getTiles()) {
            if (t.getValue() != 0 && grid.canMoveTile(t.getValue())) {
                movable = t;
                break;
            }
        }

        assertNotNull(movable, "There should be at least one tile that can move.");

        // Store initial positions
        int emptyXBefore = empty.getX();
        int emptyYBefore = empty.getY();
        int movableXBefore = movable.getX();
        int movableYBefore = movable.getY();

        // Move the tile
        grid.moveTile(movable.getValue());

        // Get updated empty and movable tiles (they may be different objects now)
        Tile emptyAfter = grid.findTileByValue(0);
        Tile movableAfter = grid.findTileByValue(movable.getValue());

        // Verify positions swapped
        assertEquals(emptyXBefore, movableAfter.getX(), "Movable tile X should swap with empty tile X");
        assertEquals(emptyYBefore, movableAfter.getY(), "Movable tile Y should swap with empty tile Y");
        assertEquals(movableXBefore, emptyAfter.getX(), "Empty tile X should swap with movable tile X");
        assertEquals(movableYBefore, emptyAfter.getY(), "Empty tile Y should swap with movable tile Y");
    }
}