package g60131.qwirkle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grid implements Serializable {
    private Tile[][] tiles;
    private boolean isEmpty;
    public Grid() {
        this.tiles = new Tile[91][91];
        this.isEmpty = true;
    }

    /**
     * Cette méthode permet de voir le contenue du tableau à la position donné
     * @param row La ligne du tableau
     * @param col La colonne du tableau
     * @return Retourne le contenue du tableau à la position donné
     */
    public Tile get(int row, int col) {
        return tiles[row][col];
    }

    /**
     * La méthode permet de ajouter pour la première fois (au tout début du jeux),
     * un certain nombre de tuiles
     * @param d La direction dont les tuile vont être placer
     * @param line Les tuiles qui sont à placer
     */
    public int firstAdd(Direction d, Tile... line) { // Les 3 points permet de mettre plusieur chose en paramètre à la fois
        int row = 45;
        int col = 45;
        int score = 0;
        if (this.isEmpty) {
            for (int i = 0; i < line.length - 1; i++) {
                Tile checkTile = line[i];
                for (int j = i + 1; j < line.length; j++) {
                    if (!(i == j)) {
                        if (line[j].equals(checkTile)) {
                            throw new QwirkleException("Les tuiles selectionné ne respecte pas les règle du jeux");
                        } else if (!((line[j].color() == checkTile.color()) || (line[j].shape() == checkTile.shape()))) {
                            throw new QwirkleException("Les tuiles n'ont pas les mêmes couleurs ou les mêmes formes");
                        }
                    }
                }
            }
            for (Tile tile : line) {
                this.tiles[row][col] = tile;
                if ((d.getDeltaRow() != 0) && (d.getDeltaCol() == 0)) {
                    row += d.getDeltaRow();
                } else if ((d.getDeltaRow() == 0) && (d.getDeltaCol() != 0)) {
                    col += d.getDeltaCol();
                }
            }
            score = line.length;
            if (score == 6) {
                score += 6; // Car une ligne de 6 à été compléter
            }
            isEmpty = false;
        } else {
            throw new QwirkleException("La première partie à déjà été jouer");
        }
        return score;
    }

    /**
     * Cette méthode permet d'ajouter une seul tuile à une position donné tout en respectant les règles du jeux
     * @param row La ligne à la quel la tuile vas être ajouter
     * @param col La colonne à la quel la tuile vas être ajouter
     * @param tile La tuile en question qui doit être mise dans le tableau
     */
    public int add(int row, int col, Tile tile) {
        int score = 0;
        // Vérifie si la position demander est vide
        if (get(row, col) != null) {
            throw new QwirkleException("La case n'est pas disponible, une tuile est déjà placer à cette position");
        }
        List<Tile> tileList = new ArrayList<>();
        if (!this.isEmpty) {
            tileNextTo(row, col);
            Direction[] ligne = {Direction.UP, Direction.RIGHT};
            for (Direction direction : ligne) {
                int j = 1;
                while (tiles[row + (j * direction.getDeltaRow())][col + (j * direction.getDeltaCol())] != null) {
                    tileList.add(tiles[row + (j * direction.getDeltaRow())][col + (j * direction.getDeltaCol())]);
                    j++;
                    score++;
                }
                j = 1;
                while (tiles[row + (j * direction.opposite().getDeltaRow())][col + (j * direction.opposite().getDeltaCol())] != null) {
                    tileList.add(tiles[row + (j * direction.opposite().getDeltaRow())][col + (j * direction.opposite().getDeltaCol())]);
                    j++;
                    score++;
                }
                checkLine(tileList, tile);
                tileList.clear();
            }
            tiles[row][col] = tile;
            if (tiles[row - 1][col] != null || tiles[row + 1][col] != null) { // Pour UP ou DOWN
                score += 1; // 1 point pour lui-même si la direction tester n'est pas null
            } else if (tiles[row][col - 1] != null || tiles[row][col + 1] != null) { // Pour LEFT ou RIGHT
                score += 1; // 1 point pour lui-même si la direction tester n'est pas null
            }
            if (score == 6) {
                score += 6; // 6 points en plus car une ligne complète à été créé
            }
        } else {
            throw new QwirkleException("La première partie n'as pas encore été jouer");
        }
        return score;
    }
    private void checkLine(List<Tile> tileList, Tile tile) {
        // Vérifier les tuiles du grid entre eux
        for (int i = 0; i < tileList.size()-1; i++) {
            Tile checkTile = tileList.get(i);
            for (int j = i+1; j < tileList.size(); j++) {
                Tile checkTile2 = tileList.get(j);
                if (checkTile2.equals(checkTile)) {
                    throw new QwirkleException("Les tuiles selectionné ne respecte pas les règle du jeux");
                } else if (!((checkTile2.color() == checkTile.color()) || (checkTile2.shape() == checkTile.shape()))) {
                    throw new QwirkleException("Les tuiles n'ont pas les mêmes couleurs ou les mêmes formes");
                }
            }
        }
        // Vérifier la tuile que l'on veux poser
        for (Tile actualTile: tileList) {
            if (actualTile.equals(tile)) {
                throw new QwirkleException("La tuile ne respecte pas les règle du jeux");
            } else if (!((actualTile.shape() == tile.shape()) || (actualTile.color() == tile.color()))) {
                throw new QwirkleException("La tuile ne respecte pas les règle du jeux");
            }
        }
    }

    /**
     * Cette méthode permet de vérifier si la tuile est rattacher à d'autre tuiles
     * @param row la position de la ligne dans le tableau
     * @param col la position de la colonne dans le tableau
     */
    private void tileNextTo(int row, int col) {
        // Vérifie si la tuile est rattaché à d'autres tuiles
        if (row == 0 && col == 90) {
            if (this.tiles[row+1][col] == null && this.tiles[row][col-1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (row == 90 && col == 0) {
            if (this.tiles[row-1][col] == null && this.tiles[row][col+1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (row == 90 && col == 90) {
            if (this.tiles[row-1][col] == null && this.tiles[row][col-1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (row == 0 && col == 0) {
            if (this.tiles[row+1][col] == null && this.tiles[row][col+1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (row == 0 && (col > 0 && col < 90)) {
            if (this.tiles[row+1][col] == null && this.tiles[row][col-1] == null && this.tiles[row][col+1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (row == 90 && (col > 0 && col < 90)) {
            if (this.tiles[row-1][col] == null && this.tiles[row][col-1] == null && this.tiles[row][col+1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (col == 0 && (row > 0 && row < 90)) {
            if (this.tiles[row-1][col] == null && this.tiles[row+1][col] == null && this.tiles[row][col+1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if (col ==  90 && (row > 0 && row < 90)) {
            if (this.tiles[row-1][col] == null && this.tiles[row+1][col] == null && this.tiles[row][col-1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        } else if ((row > 0 && row < 90) && (col > 0 && col < 90)) {
            if (this.tiles[row-1][col] == null && this.tiles[row+1][col] == null && this.tiles[row][col-1] == null && this.tiles[row][col+1] == null) {
                throw new QwirkleException("la tuile selectionner est rattacher à aucune tuiles");
            }
        }
    }

    /**
     * Cete méthode permet d'ajouter une ligne de tuile directement sur le plateau.
     * @param d la direction auxquel les tuiles vont être poser.
     * @param row la ligne de la position du tableau.
     * @param col la colonne de la position du tableau.
     * @param line les tuiles en question qui vont être poser.
     */
    public int add(Direction d, int row, int col, Tile... line) {
        int score = 0;
        int rowTest = row;
        int colTest = col;
        // Vérifier si la position des tuiles ne sont pas déjà remplis
        int i = 0;
        boolean lineIsEmpty = true;
        if (!this.isEmpty) {
            while (lineIsEmpty && i < line.length) {
                if (get(rowTest, colTest) != null) {
                    lineIsEmpty = false;
                }
                rowTest += d.getDeltaRow();
                colTest += d.getDeltaCol();
                i++;
            }
            // Vérifier si les tuiles reçu en paramètre respecte les règles du jeux
            for (i = 1; i < line.length; i++) {
                Tile firstTile = line[0];
                if (!((firstTile.color() == line[i].color()) || (firstTile.shape() == line[i].shape()))) {
                    throw new QwirkleException("Les tuiles n'ont pas les mêmes couleurs ou les mêmes formes");
                }
            }
            // Si toutes les cases sont vide, les tuiles seront ajouté.
            if (lineIsEmpty) {
                int cpt = 0;
                try {
                    rowTest = row;
                    colTest = col;
                    for (i = 0; i < line.length; i++) {
                        score += add(rowTest, colTest, line[i]);
                        rowTest += d.getDeltaRow();
                        colTest += d.getDeltaCol();
                        cpt++;
                    }
                } catch (QwirkleException e) {
                    rowTest = row;
                    colTest = col;
                    for (int j = 0; j < cpt; j++) {
                        tiles[rowTest][colTest] = null;
                            rowTest += d.getDeltaRow();
                            colTest += d.getDeltaCol();
                    }
                    throw new QwirkleException(e.getMessage());
                }
            } else {
                throw new QwirkleException("Toutes les cases ne sont pas vide, il y a déjà des tuiles");
            }
        } else {
            throw new QwirkleException("La première partie n'as pas encore été jouer");
        }
        // Les codes suivant sont là pour pouvoir corriger le nombre de point
        // car les tuile précédent rajoutent des points de manière croissant donc
        // on les retire par rapport à s'il y a déjà des tuile dans la direction opposé ou non.
        int removeScore = pointToRemove(row, col, d);
        if (removeScore != 0) {
            for (int j = 1; j <= line.length - 1; j++) {
                score -= (removeScore+j);
            }
        } else if (line.length > 2) {
            for (int k = 2; k <= line.length-1; k++) {
                score -= k;
            }
        }
        return score;
    }

    /**
     * Cette méthode permet dajouter un nombre de tuile à des position différentes
     * @param line la ligne de tuile dont chacune ont leur position dans le tableau
     */
    public int add(TileAtPosition... line) {
        int score = 0;
        // Vérifier si la position des tuile ne sont pas remplis
        int i = 0;
        if (!this.isEmpty) {
            boolean lineIsEmpty = true;
            while (lineIsEmpty && i < line.length) {
                if (get(line[i].row(), line[i].col()) != null) {
                    lineIsEmpty = false;
                }
                i++;
            }
            int cpt = 0;

            if (lineIsEmpty) {
                try {
                    for (TileAtPosition tileToAdd : line) {
                        score += add(tileToAdd.row(), tileToAdd.col(), tileToAdd.tile());
                        cpt++;
                    }
                } catch (QwirkleException e) {
                    for (int j = 0; j < cpt; j++) {
                        tiles[line[j].row()][line[j].col()] = null;
                    }
                    throw new QwirkleException(e.getMessage());
                }
            } else {
                throw new QwirkleException("Les positions séléctionner ne sont pas disponible, il y a déjà des tuiles");
            }
        } else {
            throw new QwirkleException("La première partie n'as pas encore été jouer");
        }
        score -= removeScoreTAPSameLine(line);
        return score;
    }

    /**
     * Cette méthode calcule la différence de point à retirer point
     * si les tuile sont dans la même ligne ou dans la même colonne
     * rétablir les points que le joueur est réelement sensée gagner.
     * @param tilesTab le tableau de TileAtPosition.
     * @return un entier qui seras le nombre de point(s) à retirer.
     */
    private int removeScoreTAPSameLine(TileAtPosition[] tilesTab) {
        int scoreToRemove = 0;
        TileAtPosition lastTileInSameLine = null;
        for (int i = 0; i < tilesTab.length-1; i++) {
            TileAtPosition tile1 = tilesTab[i];
            for (int j = 1; j < tilesTab.length; j++) {
                TileAtPosition tile2 = tilesTab[j];
                if (tile1.row() == tile2.row()) {
                    lastTileInSameLine = tile2;
                    // Equivalent à un if / else classique => (condition) ? if : else
                    //maxTileInSameLine = tile1.col() >= tile2.col() ? tile1 : tile2;
                } else if (tile1.col() == tile2.col()) {
                    lastTileInSameLine = tile2;
                }
            }
        }
        // si lastTileAtPosition n'est pas null ça veux dire qu'il y a des tuiles dans la même ligne ou dans la même colonne
        if (lastTileInSameLine != null) {
            for (TileAtPosition tile : tilesTab) {
                if (!lastTileInSameLine.equals(tile)) {
                    if (lastTileInSameLine.row() == tile.row()) {
                        // Renvoie un entier positif qui est le résultat du calcule en paramètre, même si c'est négatif
                        scoreToRemove += Math.abs(lastTileInSameLine.col() - tile.col());
                    } else if (lastTileInSameLine.col() == tile.col()) {
                        scoreToRemove += Math.abs(lastTileInSameLine.row() - tile.row());
                    }
                }
            }
        }
        return scoreToRemove;
    }

    /**
     * Cette méthode calcule le nombre de point à retirer pour la méthode qui place une ligne de tuiles
     * @param row L'indice de la ligne de la première tuile à placer
     * @param col L'indice de la colonne de la première tuile à placer
     * @param direction La direction par laquel la ligne de tuile vas être placé
     * @return Retourne un entier qui est le nombre de point à retirer (pas totalement : + (Somme de 1 à n tuiles))
     */
    private int pointToRemove(int row, int col, Direction direction) {
        int score = 0;
        int j = 1;
        while (tiles[row + (j * direction.opposite().getDeltaRow())][col + (j * direction.opposite().getDeltaCol())] != null) {
            score++;
            row += direction.opposite().getDeltaRow();
            col += direction.opposite().getDeltaCol();
        }
        return score;
    }

    /**
     * Un accesseur qui permet de savoir si le tableau de tuile est vide
     * @return un bouléen qui returne vrai sile tableau est totalement videet faux sinon
     */
    public boolean isEmpty() {
        return isEmpty;
        }
}