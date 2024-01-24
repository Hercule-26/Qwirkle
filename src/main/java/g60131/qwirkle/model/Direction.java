package g60131.qwirkle.model;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    private int deltaRow;
    private int deltaCol;
    Direction(int row, int col) {
        this.deltaRow = row;
        this.deltaCol = col;
    }

    /**
     * Cette méthode permet de récupérer la position de la ligne
     * @return un entier qui est la ligne
     */
    public int getDeltaRow() {
        return deltaRow;
    }

    /**
     * Cette méthode permet de récupérer la position de la colonne
     * @return un entier qui est la colonne
     */
    public int getDeltaCol() {
        return deltaCol;
    }

    /**
     * Cette méthode est appéler avec une direction renvoie la direction opposé à celle-ci
     * @return retourne la direction opposé
     */
    public Direction opposite(){
        // instance courant (avec this)
        if(this == UP) {
            return Direction.DOWN;
        }
        else if (this == DOWN) {
           return Direction.UP;
        }
        else if (this ==  LEFT) {
            return Direction.RIGHT;
        }
        else {
            return Direction.LEFT;
        }
    }
}
