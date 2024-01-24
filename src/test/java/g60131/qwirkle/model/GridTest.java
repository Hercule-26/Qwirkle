package g60131.qwirkle.model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    @Test
    void firstAddSameColor() {
        Grid grid = new Grid();
        grid.firstAdd(Direction.LEFT,
                new Tile(Color.BLUE, Shape.DIAMOND),
                new Tile(Color.BLUE, Shape.STAR),
                new Tile(Color.BLUE, Shape.CROSS));

        Tile t1 = new Tile(Color.BLUE, Shape.DIAMOND);
        Tile t2 = new Tile(Color.BLUE, Shape.STAR);
        Tile t3 = new Tile(Color.BLUE, Shape.CROSS);
        assertEquals(t1, grid.get(45, 45));
        assertEquals(t2, grid.get(45, 44));
        assertEquals(t3, grid.get(45, 43));
    }

    @Test
    void firstAddSameShape() {
        Grid grid = new Grid();
        grid.firstAdd(Direction.UP,
                new Tile(Color.BLUE, Shape.DIAMOND),
                new Tile(Color.PURPLE, Shape.DIAMOND),
                new Tile(Color.YELLOW, Shape.DIAMOND));

        Tile[] expTilesArray = new Tile[3];
        expTilesArray[0] = new Tile(Color.BLUE, Shape.DIAMOND);
        expTilesArray[1] = new Tile(Color.PURPLE, Shape.DIAMOND);
        expTilesArray[2] = new Tile(Color.YELLOW, Shape.DIAMOND);

        Tile[] resTileArray = new Tile[3];
        resTileArray[0] = grid.get(45, 45);
        resTileArray[1] = grid.get(44, 45);
        resTileArray[2] = grid.get(43, 45);
        assertArrayEquals(expTilesArray, resTileArray);
    }

    @Test
    void firstAddCasAllNotSame() {
        Grid grid = new Grid();
        assertThrows(QwirkleException.class, () -> {
            grid.firstAdd(Direction.RIGHT,
                    new Tile(Color.BLUE, Shape.DIAMOND),
                    new Tile(Color.PURPLE, Shape.SQUARE),
                    new Tile(Color.YELLOW, Shape.CROSS));
        });
    }

    @Test
    void addSameColor() {
        Grid grid = new Grid();
        grid.firstAdd(Direction.LEFT,
                new Tile(Color.BLUE, Shape.DIAMOND),
                new Tile(Color.BLUE, Shape.SQUARE),
                new Tile(Color.BLUE, Shape.CROSS));
        grid.add(45, 46, new Tile(Color.BLUE, Shape.STAR));
        assertEquals(grid.get(45, 45).color(), grid.get(45, 46).color());
    }

    @Test
    void addSameShape() {
        Grid grid = new Grid();
        grid.firstAdd(Direction.RIGHT,
                new Tile(Color.BLUE, Shape.DIAMOND),
                new Tile(Color.PURPLE, Shape.DIAMOND),
                new Tile(Color.YELLOW, Shape.DIAMOND));
        grid.add(45, 44, new Tile(Color.RED, Shape.DIAMOND));
        grid.add(45, 48, new Tile(Color.GREEN, Shape.DIAMOND));
        assertEquals(grid.get(45, 47).shape(), grid.get(45, 47).shape());
        assertEquals(grid.get(45, 45).shape(), grid.get(45, 44).shape());
    }

    @Test
    void addNotEmpty() {
        Grid grid = new Grid();
        grid.firstAdd(Direction.DOWN,
                new Tile(Color.BLUE, Shape.DIAMOND),
                new Tile(Color.PURPLE, Shape.DIAMOND),
                new Tile(Color.YELLOW, Shape.DIAMOND));
        assertThrows(QwirkleException.class, () -> {
            grid.add(45, 45, new Tile(Color.BLUE, Shape.PLUS));
        });
    }

    private Grid grid = new Grid();

    @Test
    void rules_sonia_a() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);
        assertEquals(t1, grid.get(45, 45));
        assertEquals(t2, grid.get(44, 45));
        assertEquals(t3, grid.get(43, 45));
    }

    @Test
    void rules_sonia_a_adapted_to_fail() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.DIAMOND);
        assertThrows(QwirkleException.class, () -> {
            grid.firstAdd(Direction.UP, t1, t2, t3);
        });
        assertNull(grid.get(45, 45));
        assertNull(grid.get(44, 45));
        assertNull(grid.get(43, 45));
    }

    @Test
    void rule_cedric_b() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);
        //-----------------------------------------
        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);
        assertEquals(tileCedric1, grid.get(46, 45));
        assertEquals(tileCedric2, grid.get(46, 46));
        assertEquals(tileCedric3, grid.get(46, 47));
    }

    @Test
    void rule_cedric_b_position_not_available() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);
        //-----------------------------------------
        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        assertThrows(QwirkleException.class, () -> {
            grid.add(Direction.RIGHT, 45, 45, tileCedric1, tileCedric2, tileCedric3);
        });
        assertNull(grid.get(46, 45));
        assertNull(grid.get(46, 46));
        assertNull(grid.get(46, 47));
    }

    @Test
    void rule_elvire_c() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);
        //-----------------------------------------
        var tileElvire = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire);
        assertEquals(tileElvire, grid.get(45, 46));
    }

    @Test
    void rule_elvire_c_tile_not_respect_the_game_condition() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);
        //-----------------------------------------
        var tileElvire = new Tile(Color.RED, Shape.DIAMOND);
        assertThrows(QwirkleException.class, () -> {
            grid.add(45, 46, tileElvire);
        });
        assertNull(grid.get(45, 46));
    }

    @Test
    void rule_vincent_d() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire);
        //-----------------------------------------
        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);
        assertEquals(tileVincent1, grid.get(43, 44));
        assertEquals(tileVincent2, grid.get(44, 44));
    }

    @Test
    void rule_vincent_d_tiles_not_the_same() {
        var t1 = new Tile(Color.RED, Shape.ROUND);
        var t2 = new Tile(Color.RED, Shape.DIAMOND);
        var t3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, t1, t2, t3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire);
        //-----------------------------------------
        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.BLUE, Shape.DIAMOND);
        assertThrows(QwirkleException.class, () -> {
            grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);
        });
        assertNull(grid.get(43, 44));
        assertNull(grid.get(44, 44));
    }

    @Test
    void rule_sonia_e() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);
        //-----------------------------------------
        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);
        assertEquals(tileSonia4.tile(), grid.get(42, 44));
        assertEquals(tileSonia5.tile(), grid.get(45, 44));
    }

    @Test
    void rule_sonia_e_not_same_color_and_shape() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);
        //-----------------------------------------
        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.BLUE, Shape.ROUND));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileSonia4, tileSonia5);
        });
        assertNull(grid.get(45, 44));
    }

    @Test
    void rule_cedric_f() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);
        //-----------------------------------------
        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);
        assertEquals(tileCedric4.tile(), grid.get(46, 48));
        assertEquals(tileCedric5.tile(), grid.get(47, 48));
    }

    @Test
    void rule_cedric_f_not_next_to_other() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);
        //-----------------------------------------
        var tileCedric4 = new TileAtPosition(10, 10, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(80, 80, new Tile(Color.RED, Shape.SQUARE));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileCedric4, tileCedric5);
        });
        assertNull(grid.get(46, 48));
        assertNull(grid.get(80, 80));
    }

    @Test
    void rule_Elvire_g() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);
        //-----------------------------------------
        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);
        assertEquals(tileElvire2.tile(), grid.get(42, 43));
        assertEquals(tileElvire3.tile(), grid.get(42, 42));
    }

    @Test
    void rule_Elvire_g_not_next_the_same_color_or_shape() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);
        //-----------------------------------------
        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileElvire2, tileElvire3);
        });
        assertNull(grid.get(42, 43));
        assertNull(grid.get(42, 42));
    }

    @Test
    void rule_vincent_h() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        //-----------------------------------------
        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);
        assertEquals(tileVincent3.tile(), grid.get(43, 42));
        assertEquals(tileVincent4.tile(), grid.get(44, 42));
    }

    @Test
    void rule_vincent_h_position_already_used() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        //-----------------------------------------
        var tileVincent3 = new TileAtPosition(42, 43, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileVincent3, tileVincent4);
        });
    }

    @Test
    void rule_sonia_i() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);
        //-----------------------------------------
        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);
        assertEquals(tileSonia6.tile(), grid.get(44, 43));
        assertEquals(tileSonia7.tile(), grid.get(45, 43));
    }

    @Test
    void rule_sonia_i_not_the_same() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);
        //-----------------------------------------
        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.RED, Shape.SQUARE));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileSonia6, tileSonia7);
        });
        assertNull(grid.get(44, 43));
        assertNull(grid.get(45, 43));
    }

    @Test
    void rule_cedric_j() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);

        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);
        //-----------------------------------------
        var tileCedric6 = new TileAtPosition(42, 45, new Tile(Color.RED, Shape.STAR));
        grid.add(tileCedric6);
        assertEquals(tileCedric6.tile(), grid.get(42, 45));
    }

    @Test
    void rule_cedric_j_not_next_the_same_color_shape() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);

        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);
        //-----------------------------------------
        var tileCedric6 = new TileAtPosition(41, 44, new Tile(Color.RED, Shape.ROUND));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileCedric6);
        });
        assertNull(grid.get(41, 44));
    }

    @Test
    void rule_elvire_k() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);

        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);

        var tileCedric6 = new TileAtPosition(42, 45, new Tile(Color.RED, Shape.STAR));
        grid.add(tileCedric6);
        //-----------------------------------------
        var tileElvire4 = new TileAtPosition(47, 46, new Tile(Color.BLUE, Shape.CROSS));
        var tileElvire5 = new TileAtPosition(47, 45, new Tile(Color.RED, Shape.CROSS));
        var tileElvire6 = new TileAtPosition(47, 44, new Tile(Color.ORANGE, Shape.CROSS));
        grid.add(tileElvire4, tileElvire5, tileElvire6);
        assertEquals(tileElvire4.tile(), grid.get(47, 46));
        assertEquals(tileElvire5.tile(), grid.get(47, 45));
        assertEquals(tileElvire6.tile(), grid.get(47, 44));
    }

    @Test
    void rule_elvire_k_selected_tiles_not_the_same() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);

        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);

        var tileCedric6 = new TileAtPosition(42, 45, new Tile(Color.RED, Shape.STAR));
        grid.add(tileCedric6);
        //-----------------------------------------
        var tileElvire4 = new TileAtPosition(47, 46, new Tile(Color.RED, Shape.CROSS));
        var tileElvire5 = new TileAtPosition(47, 45, new Tile(Color.BLUE, Shape.DIAMOND));
        var tileElvire6 = new TileAtPosition(47, 44, new Tile(Color.ORANGE, Shape.PLUS));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileElvire4, tileElvire5, tileElvire6);
        });
        assertNull(grid.get(47, 46));
        assertNull(grid.get(47, 45));
        assertNull(grid.get(47, 44));
    }

    @Test
    void rule_vincent_l() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);

        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);

        var tileCedric6 = new TileAtPosition(42, 45, new Tile(Color.RED, Shape.STAR));
        grid.add(tileCedric6);

        var tileElvire4 = new TileAtPosition(47, 46, new Tile(Color.BLUE, Shape.CROSS));
        var tileElvire5 = new TileAtPosition(47, 45, new Tile(Color.RED, Shape.CROSS));
        var tileElvire6 = new TileAtPosition(47, 44, new Tile(Color.ORANGE, Shape.CROSS));
        grid.add(tileElvire4, tileElvire5, tileElvire6);
        //-----------------------------------------
        var tileVincent5 = new TileAtPosition(46, 49, new Tile(Color.YELLOW, Shape.SQUARE));
        var tileVincent6 = new TileAtPosition(47, 49, new Tile(Color.BLUE, Shape.SQUARE));
        grid.add(tileVincent5, tileVincent6);
        assertEquals(tileVincent5.tile(), grid.get(46, 49));
        assertEquals(tileVincent6.tile(), grid.get(47, 49));
    }

    @Test
    void rule_vincent_l_position_already_used() {
        var tileSonia1 = new Tile(Color.RED, Shape.ROUND);
        var tileSonia2 = new Tile(Color.RED, Shape.DIAMOND);
        var tileSonia3 = new Tile(Color.RED, Shape.PLUS);
        grid.firstAdd(Direction.UP, tileSonia1, tileSonia2, tileSonia3);

        var tileCedric1 = new Tile(Color.RED, Shape.SQUARE);
        var tileCedric2 = new Tile(Color.BLUE, Shape.SQUARE);
        var tileCedric3 = new Tile(Color.PURPLE, Shape.SQUARE);
        grid.add(Direction.RIGHT, 46, 45, tileCedric1, tileCedric2, tileCedric3);

        var tileElvire1 = new Tile(Color.BLUE, Shape.ROUND);
        grid.add(45, 46, tileElvire1);

        var tileVincent1 = new Tile(Color.GREEN, Shape.PLUS);
        var tileVincent2 = new Tile(Color.GREEN, Shape.DIAMOND);
        grid.add(Direction.DOWN, 43, 44, tileVincent1, tileVincent2);

        var tileSonia4 = new TileAtPosition(42, 44, new Tile(Color.GREEN, Shape.STAR));
        var tileSonia5 = new TileAtPosition(45, 44, new Tile(Color.GREEN, Shape.ROUND));
        grid.add(tileSonia4, tileSonia5);

        var tileCedric4 = new TileAtPosition(46, 48, new Tile(Color.ORANGE, Shape.SQUARE));
        var tileCedric5 = new TileAtPosition(47, 48, new Tile(Color.RED, Shape.SQUARE));
        grid.add(tileCedric4, tileCedric5);

        var tileElvire2 = new TileAtPosition(42, 43, new Tile(Color.YELLOW, Shape.STAR));
        var tileElvire3 = new TileAtPosition(42, 42, new Tile(Color.ORANGE, Shape.STAR));
        grid.add(tileElvire2, tileElvire3);

        var tileVincent3 = new TileAtPosition(43, 42, new Tile(Color.ORANGE, Shape.CROSS));
        var tileVincent4 = new TileAtPosition(44, 42, new Tile(Color.ORANGE, Shape.DIAMOND));
        grid.add(tileVincent3, tileVincent4);

        var tileSonia6 = new TileAtPosition(44, 43, new Tile(Color.YELLOW, Shape.DIAMOND));
        var tileSonia7 = new TileAtPosition(45, 43, new Tile(Color.YELLOW, Shape.ROUND));
        grid.add(tileSonia6, tileSonia7);

        var tileCedric6 = new TileAtPosition(42, 45, new Tile(Color.RED, Shape.STAR));
        grid.add(tileCedric6);

        var tileElvire4 = new TileAtPosition(47, 46, new Tile(Color.BLUE, Shape.CROSS));
        var tileElvire5 = new TileAtPosition(47, 45, new Tile(Color.RED, Shape.CROSS));
        var tileElvire6 = new TileAtPosition(47, 44, new Tile(Color.ORANGE, Shape.CROSS));
        grid.add(tileElvire4, tileElvire5, tileElvire6);
        //-----------------------------------------
        var tileVincent5 = new TileAtPosition(46, 49, new Tile(Color.YELLOW, Shape.SQUARE));
        var tileVincent6 = new TileAtPosition(47, 44, new Tile(Color.BLUE, Shape.SQUARE));
        assertThrows(QwirkleException.class, () -> {
            grid.add(tileVincent5, tileVincent6);
        });
    }
    @Test
    void scoreFirstAdd() {
        int score = 0;
        int expScore = 3;
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        score = grid.firstAdd(Direction.UP, t1, t2, t3);
        assertEquals(expScore, score);
    }

    @Test
    void scoreFirstAddSixTile() {
        int score = 0;
        int expScore = 12;
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        Tile t5 = new Tile(Color.RED, Shape.DIAMOND);
        Tile t6 = new Tile(Color.RED, Shape.PLUS);
        score = grid.firstAdd(Direction.UP, t1, t2, t3, t4, t5, t6);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddOneTile() {
        int score;
        int expScore = 4;
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        grid.firstAdd(Direction.UP, t1, t2, t3);
        Tile t4 = new Tile(Color.RED, Shape.DIAMOND);
        score = grid.add(46, 45, t4);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddOneQwirkleComplete() {
        int score;
        int expScore = 12;
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        Tile t5 = new Tile(Color.RED, Shape.DIAMOND);
        grid.firstAdd(Direction.UP, t1, t2, t3, t4, t5);
        Tile t6 = new Tile(Color.RED, Shape.PLUS);
        score = grid.add(46, 45, t6);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddLine() {
        int score;
        int expScore = 6; // 4 + 2
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        grid.firstAdd(Direction.UP, t1, t2, t3);
        Tile t4 = new Tile(Color.RED, Shape.DIAMOND);
        Tile t5 = new Tile(Color.BLUE, Shape.DIAMOND);
        score = grid.add(Direction.LEFT, 46, 45, t4, t5);
        assertEquals(expScore, score);
    }

    @Test
    void scoreAddLineAndQwirkle() {
        int score;
        int expScore = 16; // 6 + 6 + 4
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        Tile t5 = new Tile(Color.RED, Shape.DIAMOND);
        grid.firstAdd(Direction.UP, t1, t2, t3, t4, t5);
        Tile t6 = new Tile(Color.RED, Shape.PLUS);
        Tile t7 = new Tile(Color.YELLOW, Shape.PLUS);
        Tile t8 = new Tile(Color.GREEN, Shape.PLUS);
        Tile t9 = new Tile(Color.PURPLE, Shape.PLUS);
        score = grid.add(Direction.RIGHT, 46, 45, t6, t7, t8, t9);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddLineTest() {
        int score;
        int expScore = 5; // 5
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        grid.firstAdd(Direction.RIGHT, t1, t2);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        Tile t5 = new Tile(Color.RED, Shape.PLUS);
        score = grid.add(Direction.RIGHT, 45, 47, t3, t4, t5);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddLineDoubleQwirkle() {
        int score;
        int expScore = 24; // (6 + 6) + (6 + 6)
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        Tile t5 = new Tile(Color.RED, Shape.DIAMOND);
        grid.firstAdd(Direction.UP, t1, t2, t3, t4, t5);
        Tile t6 = new Tile(Color.RED, Shape.PLUS);
        Tile t7 = new Tile(Color.YELLOW, Shape.PLUS);
        Tile t8 = new Tile(Color.GREEN, Shape.PLUS);
        Tile t9 = new Tile(Color.PURPLE, Shape.PLUS);
        Tile t10 = new Tile(Color.ORANGE, Shape.PLUS);
        Tile t11 = new Tile(Color.BLUE, Shape.PLUS);
        score = grid.add(Direction.RIGHT, 46, 45, t6, t7, t8, t9, t10, t11);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddTileAtPosition() {
        int score;
        int expScore = 7; // 5 + 2
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        grid.firstAdd(Direction.UP, t1, t2, t3, t4);
        TileAtPosition t5 = new TileAtPosition(46, 45, new Tile(Color.RED, Shape.PLUS));
        TileAtPosition t6 = new TileAtPosition(42, 44, new Tile(Color.BLUE, Shape.CROSS));
        score = grid.add(t5, t6);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddTileAtPositionQwirkle() {
        int score;
        int expScore = 14; // (6 + 6) + 2
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        Tile t4 = new Tile(Color.RED, Shape.CROSS);
        Tile t5 = new Tile(Color.RED, Shape.DIAMOND);
        grid.firstAdd(Direction.UP, t1, t2, t3, t4, t5);
        TileAtPosition t6 = new TileAtPosition(46, 45, new Tile(Color.RED, Shape.PLUS));
        TileAtPosition t7 = new TileAtPosition(42, 44, new Tile(Color.BLUE, Shape.CROSS));
        score = grid.add(t6, t7);
        assertEquals(expScore, score);
    }
    @Test
    void scoreAddTileAtPositionSameLine() {
        int score;
        int expScore = 5; // 5 point car ils sont dans la même ligne
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        grid.firstAdd(Direction.RIGHT, t1, t2, t3);
        TileAtPosition t4 = new TileAtPosition(45, 48, new Tile(Color.RED, Shape.PLUS));
        TileAtPosition t5 = new TileAtPosition(45, 44, new Tile(Color.RED, Shape.DIAMOND));
        score = grid.add(t4, t5);
        assertEquals(expScore, score);
    }
    @Test
    void scoreTileAtPosSameLineQwirkle() {
        int score;
        int expScore = 12; // (6 + 6) (6 car même ligne et + 6 car un Qwirkle)
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        grid.firstAdd(Direction.UP, t1, t2, t3);
        TileAtPosition t4 = new TileAtPosition(46, 45, new Tile(Color.RED, Shape.DIAMOND));
        TileAtPosition t5 = new TileAtPosition(47, 45, new Tile(Color.RED, Shape.PLUS));
        TileAtPosition t6 = new TileAtPosition(42, 45, new Tile(Color.RED, Shape.CROSS));
        score = grid.add(t4, t5, t6);
        assertEquals(expScore, score);
    }
    @Test
    void scoreTileAtPosSameLineOtherDirQwirkle() {
        int score;
        int expScore = 12; // (6 + 6) (6 car même ligne et + 6 car un Qwirkle)
        Tile t1 = new Tile(Color.RED, Shape.STAR);
        Tile t2 = new Tile(Color.RED, Shape.ROUND);
        Tile t3 = new Tile(Color.RED, Shape.SQUARE);
        grid.firstAdd(Direction.LEFT, t1, t2, t3);
        TileAtPosition t4 = new TileAtPosition(45, 46, new Tile(Color.RED, Shape.DIAMOND));
        TileAtPosition t5 = new TileAtPosition(45, 47, new Tile(Color.RED, Shape.PLUS));
        TileAtPosition t6 = new TileAtPosition(45, 42, new Tile(Color.RED, Shape.CROSS));
        score = grid.add(t4, t5, t6);
        assertEquals(expScore, score);
    }
}