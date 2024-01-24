package g60131.qwirkle.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BagTest {

    @Test
    void getRandomTileCasBon() {
        Bag bag = Bag.getInstance();
        Tile[] deck = bag.getRandomTile(10);
        int expResult = 98;
        int result = bag.size();
        assertEquals(expResult, result);
    }
    @Test
    void getRandomTileCasVide() {
        Bag bag = Bag.getInstance();
        Tile[] deck1 = bag.getRandomTile(108);
        int expResult = 0;
        int result = bag.size();
        assertEquals(expResult, result);
    }
    @Test
    void getRandomTileCasInsuffisant() {
        Bag bag = Bag.getInstance();
        Tile[] deck1 = bag.getRandomTile(100);
        Tile[] deck2 = bag.getRandomTile(15);
        int expResult = 8;
        int result = deck2.length;
        assertEquals(expResult, result);
    }
}