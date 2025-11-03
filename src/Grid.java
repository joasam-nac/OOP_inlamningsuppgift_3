import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

public class Grid {
    private final int width;
    private final int height;
    private final long seed;
    private final List<Tile> tiles = new ArrayList<>();

    public Grid(int width, int height) {
        this(width, height, new Random().nextLong());
    }

    public Grid(int width, int height, long seed) {
        this.width = width;
        this.height = height;
        this.seed = seed;
        createTiles();
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
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

    public Tile findTileByPosition(int x, int y){
        for (Tile t : tiles){
            if (t.getX() == x && t.getY() == y){
                return t;
            }
        }
        return null;
    }

    /* den tomma rutan (tile med värde 0) är antingen 1
    ifrån i x- eller y-led, men inte samtidigt*/
    public boolean canMoveTile(int value){
        Tile moveTile = findTileByValue(value);
        Tile emptyTile = findTileByValue(0);

        int distanceX = Math.abs(moveTile.getX() - emptyTile.getX());
        int distanceY = Math.abs(moveTile.getY() - emptyTile.getY());

        return (distanceX == 1 && distanceY == 0) || (distanceY == 1 && distanceX == 0);
    }

    public void moveTile(int value){
        if (canMoveTile(value)){
            Tile moveTile = findTileByValue(value);
            Tile emptyTile = findTileByValue(0);

            int tempX = moveTile.getX();
            int tempY = moveTile.getY();

            moveTile.setX(emptyTile.getX());
            moveTile.setY(emptyTile.getY());

            emptyTile.setX(tempX);
            emptyTile.setY(tempY);
        }
    }

    public boolean playerHasWon(){
        int compareVal = 1;
        for (int i = 0; i < width * height; i++){
            int x = i % width;
            int y = i / width;
            Tile tile = findTileByPosition(x, y);
            if (tile.getValue() != compareVal){
                return false;
            }
            if (compareVal == width * height - 1){
                compareVal = 0;
            } else {
                compareVal++;
            }
        }
        return true;
    }

    public static Grid demonstrationGrid(int width, int height){
        Grid grid = new Grid(width, height);

        //placerar ut på fixerad plats
        for (int i = 1; i < width * height; i++){
            Tile tile = grid.findTileByValue(i);
            int x = (i - 1) % width;
            int y = (i - 1) / width;
            tile.setPosition(x, y);
        }
        Tile empty = grid.findTileByValue(0);
        empty.setPosition(width - 1, height - 1);

        // Gör så att man bara behöver flytta 1 ruta
        Tile adjacent = grid.findTileByValue(width * height - 1);
        int tempX = adjacent.getX();
        int tempY = adjacent.getY();
        adjacent.setPosition(empty.getX(), empty.getY());
        empty.setPosition(tempX, tempY);

        return grid;
    }
}
