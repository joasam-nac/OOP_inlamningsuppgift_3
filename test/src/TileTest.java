import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TileTest {

    @Test
    void test4x4() {
        List<int[]> positions = new ArrayList<>();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++){
                positions.add(new int[]{x,y});
            }
        }

        Collections.shuffle(positions, new Random(12345L));
        List<Tile> tiles = new ArrayList<>();
        for (int i = 0; i < 15; i++){
            int[] p = positions.get(i);
            tiles.add(new Tile(i+1, p));
        }

        assertEquals(2, tiles.getLast().getX(), "Should be 2");
        assertEquals(2, tiles.getLast().getY(), "Should be 2");

    }
}