import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class Grid {
    private final int width;
    private final int height;
    private long seed;
    private final List<Tile> tiles = new ArrayList<>();

    public Grid(int size){
        this.width = size;
        this.height = size;
        this.seed = new Random().nextLong();
    }

    public Grid(int width, int height){
        this.width = width;
        this.height = height;
        this.seed = new Random().nextLong();
    }

    // för tests
    public Grid(int size, long seed){
        this.width = size;
        this.height = size;
        this.seed = seed;
    }

    public Grid(int width, int height, long seed){
        this.width = width;
        this.height = height;
        this.seed = seed;
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
        return tiles;
    }

    public Tile findTileByValue(int value){
        for (Tile t : tiles){
            if (t.getValue() == value){
                return t;
            }
        }
        return null;
    }
}
