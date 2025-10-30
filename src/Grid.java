import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class Grid {
    private final int width;
    private final int height;
    private final long seed;
    private final List<Tile> tiles = new ArrayList<>();

    public Grid(int size) {
        this(size, size, new Random().nextLong());
    }

    public Grid(int width, int height) {
        this(width, height, new Random().nextLong());
    }

    public Grid(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        createTiles();
    }

    private void createTiles(){
        List<int[]> positions = new ArrayList<>();
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                positions.add(new int[] {x, y});
            }
        }

        Collections.shuffle(positions, new Random(seed));

        int tilesAmount = width * height;
        for (int i = 0; i < tilesAmount; i++){
            int[] position = positions.get(i);
            if (i < tilesAmount - 1){
                tiles.add(new Tile(i+1, position));
            } else {
                tiles.add(new Tile(0, position));
            }
        }
    }

    public List<Tile> getTiles(){
        return new ArrayList<>(tiles); //kopia för avläsning
    }

    public Tile findTileByValue(int value){
        for (Tile t : tiles){
            if (t.getValue() == value){
                return t;
            }
        }
        return null;
    }

    // den tomma rutan (tile med värde 0) är antingen 1 ifrån i x- eller y-led
    public boolean canMoveTile(int value){
        Tile moveTile = findTileByValue(value);
        Tile emptyTile = findTileByValue(0);

        int distanceX = Math.abs(moveTile.getX() - emptyTile.getX());
        int distanceY = Math.abs(moveTile.getY() - emptyTile.getY());

        return distanceX == 1 || distanceY == 1;
    }
}
