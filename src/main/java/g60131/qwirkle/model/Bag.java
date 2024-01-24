package g60131.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag implements Serializable {
    private List<Tile> tiles;
    private static Bag bag;

    /*
      Constructeur privé qui initialise 36 tuiles différent
      et chacune 3 fois donc 108 au total.
     */
    private Bag(){
        this.tiles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (Color color : Color.values()) {
                for (Shape shape : Shape.values()) {
                    tiles.add(new Tile(color, shape));
                }
            }
        }
        Collections.shuffle(this.tiles);
    }

    /**
     * Cette méthode instancie le sac de tuile.
     * @return retourne les 108 tuiles initialiser.
     */
    public static Bag getInstance() {
        if (bag == null) {
            bag = new Bag();
        }
        return bag;
    }

    /**
     * Cette méthode prend un nombre en paramètre et renvoie une tuile aléatoire
     * @param n Le nombre de tuile(s) à retourner.
     * @return Retourne n nombre de tuile.
     */
    public Tile[] getRandomTile(int n) {
        if (tiles.isEmpty()) {
            return null;
        }
        if (size() <= n) {
            Tile[] tabTile = new Tile[size()];
            for (int i = 0; i < size(); i++) {
                tabTile[i] = tiles.get(i);
            }
            for (Tile tile : tabTile) {
                tiles.remove(tile);
            }
            return tabTile;
        }
        else {
            Tile[] tabTile = new Tile[n];
            for (int i = 0; i < n; i++) {
                tabTile[i] = tiles.get(i);
            }
            for (Tile tile : tabTile) {
                tiles.remove(tile);
            }
            return tabTile;
        }
    }

    /**
     * Cette méthode compte le nombre de tuile(s) restant(s).
     * @return Retourne le nombre de tuile restant
     */
    public int size(){
        return tiles.size();
    }


}
