package g60131.qwirkle.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Game implements Serializable {
    private Grid grid;
    private Player[] players;
    private int curPlayer;

    public Game(List<String> name) {
        this.grid = new Grid();
        players = new Player[name.size()];
        for (int i = 0; i < name.size(); i++) {
            this.players[i] = new Player(name.get(i));
        }
        curPlayer = 0;
    }

    /**
     * Cette méthode permet d'ajouter des tuiles pour la première fois
     * dans le grid
     * @param d la direction par laquel les tuiles vont être poser
     * @param is les indice des tuile de la main du joueur courant.
     */
    public void first(Direction d, int... is) {
        Tile[] firstAddTiles = new Tile[is.length];
        if (grid.isEmpty()) {
            for (int i = 0; i < is.length; i++) {
                firstAddTiles[i] = players[curPlayer].getHand().get(is[i]);
            }
            int score = grid.firstAdd(d, firstAddTiles);
            players[curPlayer].addScore(score);

            for (Tile tile : firstAddTiles) {
                players[curPlayer].remove(tile);
            }
            pass();
        }
        else {
            throw new QwirkleException("Le premier tour à déjà été jouer");
        }
    }

    /**
     * Cette méthode permet d'ajouter qu'une seul tuile dans le tableau
     * @param row La ligne dans le tableau
     * @param col La colonne dans le tableau
     * @param index Indice de la tuile dans la main du joueur courant
     */
    public void play(int row, int col, int index) {
        if (curPlayer == players.length) {
            curPlayer = 0;
        }
        int score = grid.add(row, col, players[curPlayer].getHand().get(index));
        players[curPlayer].addScore(score);
        players[curPlayer].remove(players[curPlayer].getHand().get(index));
        pass();
    }

    /**
     * Cette méthode permet de placer une série de tuile dans le tableaux
     * @param d La direction par laquel les tuiles vont être poser
     * @param row La ligne dans le tableau
     * @param col La colonne dans le tableau
     * @param index Les indices des tuiles dans la main du joueur
     */
    public void play(Direction d, int row, int col, int... index) {
        if (curPlayer == players.length) {
            curPlayer = 0;
        }
        Tile[] tileToAdd = new Tile[index.length];
        for (int i = 0; i < tileToAdd.length; i++) {
            tileToAdd[i] = players[curPlayer].getHand().get(index[i]);
        }
        int score = grid.add(d, row, col, tileToAdd);
        players[curPlayer].addScore(score);

        for (int i = 0; i < index.length; i++) {
            players[curPlayer].remove(tileToAdd[i]);
        }
        pass();
    }

    /**
     * Cette méthode permet de placer une série de tuile dans le tableaux
     * @param is La ligne, La colonne et l'indice de la tuile dans la main du joueur
     */
    public void play(int... is) {
        if (curPlayer == players.length) {
            curPlayer = 0;
        }
        TileAtPosition[] tileAtPositions = new TileAtPosition[is.length / 3];
        int j = 0;
        for (int i = 0; i < tileAtPositions.length; i++) {
            tileAtPositions[i] = new TileAtPosition(is[j], is[j+1], players[curPlayer].getHand().get(is[j+2]));
            j += 3;
        }
        int score = grid.add(tileAtPositions);
        players[curPlayer].addScore(score);

        Tile[] tiles = new Tile[tileAtPositions.length];
        for (int i = 0; i < tileAtPositions.length; i++) {
            tiles[i] = tileAtPositions[i].tile();
        }
        players[curPlayer].remove(tiles);
        pass();
    }

    /**
     * Cette méthode permet de passer son tour
     */
    public void pass() {
        if (curPlayer == players.length) {
            curPlayer = 0;
            players[curPlayer].refill();
        } else {
            players[curPlayer].refill();
            curPlayer++;
        }
    }

    /**
     * Retourne le tableau pour l'affichage
     * @return le tableau qui seras retourner
     */
    public GridView getGrid() { return new GridView(this.grid);}

    /**
     * Retourne le nom du joueur courant
     * @return Le nom du joueur à retourner
     */
    public String getCurrentPlayerName() {
        if (curPlayer == players.length) {
            curPlayer = 0;
        }
        return players[curPlayer].getName();
    }

    /**
     * Retourne la main du joueur courant
     * @return La main du joueur à retourne
     */
    public List<Tile> getCurrentPlayerHand() {
        if (curPlayer == players.length) {
            curPlayer = 0;
        }
        return players[curPlayer].getHand();
    }

    /**
     * Cette méthode retourne le score du joueur
     * @return un entier qui correspond au score du joueur
     */
    public int getCurrentPlayerScore() {
        return players[curPlayer].getScore();
    }

    /**
     * cette méthode vérifie si la partie est terminer
     * en regardant les 2 condition de fin de partie
     * @return Un booléen qui retournera vrais si la partie est terminer, faux sinon.
     */
    public boolean isOver() {
        boolean over = false;
        // curPlayer - 1 car on est passé au joueur suivant à la fin de la partie du joueur qui l'as fini
        if (Bag.getInstance().size() == 0 && players[curPlayer - 1].getHand().size() == 0) {
            players[curPlayer-1].addScore(6); // Car il à fini la partie donc +6 points
            over = true;
        } else if (Bag.getInstance().size() == 0 && !checkPosibilityPutTile()){
            over = true; // Pas de point en plus car personne n'as pu terminer
        }
        return over;
    }

    /**
     * La méthode prend les mains de tout les joueurs et regarde dans le grid s'il est possible de poser une ou plusieurs tuile(s)
     * @return Un booléen qui retourneras vrais s'il est possib
     */
    private boolean checkPosibilityPutTile() {
        boolean posibility = false;
        ArrayList <Tile> allPlayersHands = new ArrayList<>();
        ArrayList<Tile> tileList = new ArrayList<>();
        for (Player player : players) {
            allPlayersHands.addAll(player.getHand());
        }
        for (Tile tile : allPlayersHands) {
            for (int i = 1; i < 90; i++) {
                for (int j = 1; j < 90; j++) {
                    // Si la case (i,j) est null et que au moin un des cases voisine n'est pas null,
                    // on récupère toutes les tuiles voisine pour vérifier la posibilité de poser la tuile actuel du foreach
                    if (grid.get(i, j) == null && (grid.get(i - 1, j) != null || grid.get(i + 1, j) != null || grid.get(i, j - 1) != null || grid.get(i, j + 1) != null)) {
                        Direction[] ligne = {Direction.UP, Direction.RIGHT};
                        for (Direction direction : ligne) {
                            int indiceDir = 1;
                            while (grid.get(i + (indiceDir * direction.getDeltaRow()), j + (indiceDir * direction.getDeltaCol())) != null) {
                                tileList.add(grid.get(i + (indiceDir * direction.getDeltaRow()), j + (indiceDir * direction.getDeltaCol())));
                                indiceDir++;
                            }
                            indiceDir = 1;
                            while (grid.get(i+(indiceDir * direction.opposite().getDeltaRow()), j+(indiceDir * direction.opposite().getDeltaCol())) != null) {
                                tileList.add(grid.get(i+(indiceDir * direction.opposite().getDeltaRow()), j+(indiceDir * direction.opposite().getDeltaCol())));
                                indiceDir++;
                            }
                            if (checkLine(tileList, tile)) {
                                return true;
                            }
                            tileList.clear();
                        }
                    }
                }
            }
        }
        return posibility;
    }

    /**
     * Cette méthode permet de vérifier la tuile que l'on veux placer à une position respect
     * les règles du jeux par rapport à la ligne de tuile qui est déjà sur le plateau.
     * @param tileList la ligne de tuile qui est sur le plateau (horizontale ou vertical)
     * @param tile La tuile à vérifier pour voir si on peux le placer à côté de la ligne
     * @return Un booléen qui retourneras vrais si c'est possible de placer
     * la tuile souhaite, retourneras faux sinon
     */
    private boolean checkLine(List<Tile> tileList, Tile tile) {
        // Vérifier les tuiles du grid entre eux
        for (int i = 0; i < tileList.size()-1; i++) {
            Tile checkTile = tileList.get(i);
            for (int j = i+1; j < tileList.size(); j++) {
                Tile checkTile2 = tileList.get(j);
                if (checkTile2.equals(checkTile)) {
                    return false;
                } else if (!((checkTile2.color() == checkTile.color()) || (checkTile2.shape() == checkTile.shape()))) {
                    return false;
                }
            }
        }
        // Vérifier la tuile que l'on veux poser
        for (Tile actualTile: tileList) {
            if (actualTile.equals(tile)) {
                return false;
            } else if (!((actualTile.shape() == tile.shape()) || (actualTile.color() == tile.color()))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Cette méthode permet décrire l'instance courant dans un fichier pour le sauvegarder
     * l'état actuel du jeux au moment ou le jeux à été arrêté
     * @param fileName le nom du fichier qui vas être sauvegarder
     */
    public void write(String fileName) {
        try {
            Game thisGame = this;
            String curDirectory = System.getProperty("user.dir"); // Le répertoire courant

            FileOutputStream file = new FileOutputStream(curDirectory + "/backupGame/" + fileName);
            ObjectOutputStream objectOS = new ObjectOutputStream(file);
            objectOS.writeObject(thisGame);

            objectOS.close();
            file.close();

            System.out.println("La partie à été sauvegarder");
        } catch (IOException e) {
            throw new QwirkleException("Une erreur est survenue lors de la création du fichier");
        }
    }

    /**
     * Cette méthode permet de récuper une instance qui à été sauvegarder aupart avant
     * @param fileName le nom de fichier à récupérer
     * @return Retourne une instance qui est l'état à laquelle le jeux à été arrêté
     */
    public static Game getFromFile(String fileName) {
        Game getGame;
        try {
            String curDirectory = System.getProperty("user.dir"); // le répertoire courant
            FileInputStream file = new FileInputStream(curDirectory + "/backupGame/" + fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(file);
            getGame = (Game) objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            throw new QwirkleException("une erreur est survenu lors de la récupération du fichier");
        }
        return getGame;
    }
}
