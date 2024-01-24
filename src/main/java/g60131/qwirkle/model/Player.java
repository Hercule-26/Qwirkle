package g60131.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Player implements Serializable {
    private String name;
    private int score;
    private List<Tile> tiles;

    public Player(String name) {
        this.name = name;
        tiles = new ArrayList<>(6);
        this.score = 0;
    }

    /**
     * Cette méthode permet de voir la main du joueur
     * sans pour autant avoir la posibiliter de modifier la main
     * @return retourne toute les tuile de la main du joueur
     */
    public List<Tile> getHand() {
        return Collections.unmodifiableList(tiles);
    }

    /**
     * Cette méthode permet de remplir la main du joueur
     */
    public void refill() {
        if (this.tiles.size() < 6) {
            int neededTile = 6 - this.tiles.size();
            Tile[] tileList = Bag.getInstance().getRandomTile(neededTile);
            this.tiles.addAll(Arrays.asList(tileList));
        }
    }

    /**
     * Cette méthode permet de supprimer un nombre de tuiles de la main du joueur
     * @param ts les tuile à supprimmer de la main du joueur
     */
    public void remove(Tile... ts) {
        for (Tile t : ts) {
            tiles.remove(t);
        }
    }

    /**
     * Cette méthode permet de retourner le score du joueur
     * @return un entier qui est le score du joueur
     */
    public int getScore() {
        return score;
    }
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Un accesseur qui permet d'avoir le nom du joueur
     * @return Retourne le nom du joueur
     */
    public String getName() {
        return name;
    }

}
