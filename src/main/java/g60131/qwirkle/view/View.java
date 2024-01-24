package g60131.qwirkle.view;

import g60131.qwirkle.model.* ;

import java.util.List;

public class View {
    private static final String ANSI_RESET = "\u001B[0m"; // Reset to the default color.
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_ORANGE = "\u001B[38;5;208m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_GOLD = "\u001B[38;5;220m";

    /**
     * Cette méthode permet d'afficher le tableau de tuile
     * @param grid le tableau de tuile à afficher
     */
    public static void display(GridView grid) {
        int rowMin = 45;
        int rowMax = 45;
        int colMin = 45;
        int colMax = 45;
        for (int i = 0; i < 91; i++) {
            for (int j = 0; j < 91; j++) {
                if (grid.get(i, j) != null) {
                    if (i < rowMin) {
                        rowMin = i;
                    }
                    if (i > rowMax) {
                        rowMax = i;
                    }
                    if (j < colMin) {
                        colMin = j;
                    }
                    if (j > colMax) {
                        colMax = j;
                    }
                }
            }
        }
        for (int i = rowMin; i <= rowMax; i++) {
            if (i < 10){
                System.out.print(" " + i + "|");
            } else {
                System.out.print(i + "|");
            }
            for (int j = colMin; j <= colMax ; j++) {
                if (grid.get(i, j) != null) {
                    View.tileView(grid.get(i, j));
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.print("    ");
        for (int i = colMin; i <= colMax; i++) {
            System.out.print("__ ");
        }
        System.out.print("\n   ");
        for (int i = colMin; i <= colMax ; i++) {
            if (i < 10){
                System.out.print("  " + i);
            } else {
                System.out.print(" " + i);
            }
        }
        System.out.println();
    }

    /**
     * Cette méthode permet d'afficher proprement le joueur
     * @param name Le nom du joueur
     * @param hand La main du joueur
     */
    public static void display(String name, List<Tile> hand, int score) {
        for (int i = 0; i < (21 + name.length()); i++) {
            System.out.print("-");
        }
        System.out.print("\n|" + ANSI_BLUE + " C'est le tour de " + name + ANSI_RESET + " |"+ " Score : " + score + " point(s)"+ "\n");
        for (int i = 0; i < (21 + name.length()); i++) {
            System.out.print("-");
        }
        System.out.print("\nVotre main est :\n");
        for (int i = 0; i < hand.size(); i++) {
            Tile tile = hand.get(i);
            View.tileView(tile);
        }
        System.out.println();
    }

    public static void displayHelp() {
        System.out.println("Q W I R K L E");
        System.out.println("Qwirkle command: ");
        System.out.println("- play 1 tile : o <row> <col> <i>");
        System.out.println("- play line : l <row> <col> <Direction> <i1> [<i2>]");
        System.out.println("- play plic-ploc : m <row1> <col1> <i1> [<row2> <col2> <i2>]");
        System.out.println("- play first : f <Direction> <i1> [<i2>]");
        System.out.println("- pass : p");
        System.out.println("- quit : q");
        System.out.println("\t i : index in list of tiles (player deck)");
        System.out.println("\t d : direction in: u (up), d (down), l (left), r (right)");
    }
    public static void displayEnd(Player[] players, int indexWinner) {
        System.out.println(ANSI_YELLOW + "Le gagnant avec  " + players[indexWinner].getScore() + " est : " + ANSI_RESET);
        System.out.println(ANSI_GOLD + "   !!! " + players[indexWinner].getName() + " !!!" +  ANSI_RESET);
        System.out.println(ANSI_GOLD + "!!! Félicitation !!!" + ANSI_RESET);
    }

    public static void displayError(String message) {
        System.out.println(ANSI_RED + message + ANSI_RESET);
    }

    private static void tileView(Tile tile) {
        String color = "";
        switch (tile.color()) {
            case PURPLE -> color = ANSI_PURPLE;
            case YELLOW -> color = ANSI_YELLOW;
            case ORANGE -> color = ANSI_ORANGE;
            case RED ->  color = ANSI_RED;
            case BLUE -> color = ANSI_BLUE;
            case GREEN -> color = ANSI_GREEN;
        }
        System.out.print(color + getTileShapeView(tile) + ANSI_RESET);
    }
    private static String getTileShapeView(Tile tile) {
        return switch (tile.shape()) {
            case SQUARE -> " []";
            case DIAMOND -> " <>";
            case STAR -> "  *";
            case ROUND -> "  O";
            case PLUS -> "  +";
            case CROSS -> "  X";
        };
    }
}
