package g60131.qwirkle;

import g60131.qwirkle.model.*;
import g60131.qwirkle.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class App {
    private static Game game;
    private static boolean quit = false;
    public static void main(String[] args) {
        Scanner clavier = new Scanner(System.in);
        ArrayList<String> players = new ArrayList<>();
        boolean restaurer = yesOrNo("Voulez-vous restaurer une partie ? y : Yes | n : Non");
        if (restaurer) {
            boolean done = false;
            // Restaurer un jeux
            while(!done && restaurer) {
                try {
                    System.out.println("Entrer le nom du ficher à restaurer : ");
                    String fileName = clavier.nextLine();
                    game = Game.getFromFile(fileName);
                    done = true;
                } catch (QwirkleException e) {
                    System.out.println("Une erreur est survenue");
                    restaurer = yesOrNo("Voulez-vous restaurer une partie ? y : Yes | n : Non");

                }
            }
        }
        // Nouveau jeux
        if (!restaurer) {
            int cptPlayer = askInt("Combien de joueurs ?", 2, 4);
            for (int i = 1; i <= cptPlayer; i++) {
                System.out.println("Entrer le nom du joueur " + i);
                String playerName = clavier.next();
                if (players.size() >= 2) {
                    boolean sameName = checkNotSameName(players, playerName);
                    while (sameName) {
                        System.out.println("Choisisez un autres nom (Ce nom est déjà pris)");
                        playerName = clavier.next();
                        sameName = checkNotSameName(players, playerName);
                    }
                    players.add(playerName);
                } else if (players.size() == 1) {
                    String comparedName = players.get(0).toLowerCase();
                    String nameToAdd = playerName.toLowerCase();
                    boolean sameName = nameToAdd.equals(comparedName);
                    while (sameName) {
                        System.out.println("Choisisez un autres nom (Ce nom est déjà pris)");
                        playerName = clavier.next();
                        nameToAdd = playerName.toLowerCase();
                        sameName = nameToAdd.equals(comparedName);;
                    }
                    players.add(playerName);
                } else {
                    players.add(playerName);
                }
            }
            Collections.shuffle(players); // Mélanger le tableau de joueur
            // Création du jeux
            game = new Game(players);
            // Remplissage la main des joueurs
            for (int i = 0; i < players.size(); i++) {
                game.pass();
            }
        }
        // ------------ The Game ------------ \\
        while (game.isOver() || !quit) {
            View.display(game.getGrid());
            View.display(game.getCurrentPlayerName(), game.getCurrentPlayerHand(), game.getCurrentPlayerScore());
            System.out.println();
            View.displayHelp();
            String[] commandPartition = letterValidity();
            boolean commandComplet = witchCommand(commandPartition);
            while (!commandComplet) {
                View.displayError("Entrer une commande valide");
                commandPartition = letterValidity();
                commandComplet = witchCommand(commandPartition);
            }
        }
        // ------------ End Game ------------ \\
        // Savoir le nombre de joueur pour pouvoir récuperer leurs nom et leur score
        // On peut le savoir en comptant le nombre de nom différent
        // jusqu'à que l'on reviens sur le nom du joueur courant.
        String indexPlayerName = game.getCurrentPlayerName();
        game.pass();
        int cptPlayer = 1; // car déjà 1 joueur
        while (cptPlayer <= 4 && !(indexPlayerName.equals(game.getCurrentPlayerName()))) {
            cptPlayer++;
            game.pass();
        }
        // Récuperer le nom et le score des joueurs pour trouver le gagnant
        Player[] arrayPlayer = new Player[cptPlayer];
        for (int i = 0; i < players.size(); i++) {
            arrayPlayer[i] = new Player(game.getCurrentPlayerName());
            arrayPlayer[i].addScore(game.getCurrentPlayerScore());
            game.pass();
        }
        // Afficher le gagnant
        View.displayEnd(arrayPlayer, getWinnerIndex(arrayPlayer));

        // Sauvegarder la partie
        boolean saveGame = yesOrNo("Voulez vous sauver la partie ? y : Yes | n : Non");
        if (saveGame) {
            System.out.println("Entrer le nom du fichier : ");
            String fileName = clavier.next();
            game.write(fileName);
        }
    }

    /**
     * Cette méthode permet de vérifier la validité de la lettre de commande
     * @return un tableau qui sépare les éléments de la commmande.
     */
    private static String[] letterValidity() {
        Scanner clavier = new Scanner(System.in);
        boolean validity;
        char letter;
        String patern = "olmfpq";
        System.out.println("Que voulez-vous faire ? ");
        String command = clavier.nextLine().toLowerCase();
        while (command.length() == 0) {
            View.displayError("Aucune commande n'as été entrer");
            System.out.println("Quel voulez-vous faire ? ");
            command = clavier.nextLine().toLowerCase();
        }
        String[] commandPart = command.split("\\s+");
        if (commandPart[0].length() == 0) {
            letter = commandPart[1].charAt(0);
        } else {
            letter = commandPart[0].charAt(0);
        }
        validity = letter == patern.charAt(0) || letter == patern.charAt(1) || letter == patern.charAt(2) ||
                letter == patern.charAt(3) || letter == patern.charAt(4) || letter == patern.charAt(5);
        while (!validity) {
            View.displayError("La lettre de la commande n'est pas correct !! " +
                    "\nEntrer une des commande du tableau suivant :");
            View.displayHelp();
            command = clavier.next().toLowerCase();
            commandPart = command.split("\\s+");
            if (commandPart[0].length() == 0) {
                letter = commandPart[1].charAt(0);
            } else {
                letter = commandPart[0].charAt(0);
            }
            validity = letter == patern.charAt(0) || letter == patern.charAt(1) || letter == patern.charAt(2) ||
                    letter == patern.charAt(3) || letter == patern.charAt(4) || letter == patern.charAt(5);
        }
        return commandPart;
    }

    /**
     * Cette méthode permet de jouer à Qwirkle avec une commande entrer
     * @param commandPart Un tableau avec les commandes partitionner
     * @return Un booléen si la commande à été correctement exécuter et faux sinon
     */
    private static boolean witchCommand(String[] commandPart) {
        boolean commandComplete = true;
        String[] newCommandPart;
        if (commandPart[0].length() == 0) {
            newCommandPart = new String[commandPart.length -1];
            for (int i = 1; i < commandPart.length; i++) {
                newCommandPart[i-1] = commandPart[i];
            }
        } else {
            newCommandPart = new String[commandPart.length];
            for (int i = 0; i < commandPart.length; i++) {
                newCommandPart[i] = commandPart[i];
            }
        }
        char letter = newCommandPart[0].charAt(0);
        switch (letter) {
            case 'o' :
                if (newCommandPart.length != 4) {
                    commandComplete = false;
                } else {
                    try {
                        int row = convertToInteger(newCommandPart[1], 0, 90);
                        int col = convertToInteger(newCommandPart[2], 0, 90);
                        int index = convertToInteger(newCommandPart[3], 0, 5);
                        game.play(row, col, index);
                    } catch (QwirkleException e) {
                        View.displayError(e.getMessage());
                        commandComplete = false;
                    }
                }
            break;
            case 'l' :
                if (newCommandPart.length < 4) {
                    commandComplete = false;
                } else {
                    try {
                        Direction d = witchDirection(newCommandPart[3]);
                        int row = convertToInteger(newCommandPart[1],0, 90);
                        int col = convertToInteger(newCommandPart[2], 0, 90);
                        int[] index = new int[newCommandPart.length - 4];
                        for (int i = 0; i < index.length; i++) {
                            index[i] = convertToInteger(newCommandPart[i+4], 0, 5);
                        }
                        game.play(d, row, col, index);
                    } catch (QwirkleException e) {
                        View.displayError(e.getMessage());
                        commandComplete = false;
                    }
                }
                break;
            case 'm' :
                if (newCommandPart.length < 4 && ((newCommandPart.length-1) % 3 == 0)) {
                    commandComplete = false;
                } else {
                    try {
                        int[] index = new int[newCommandPart.length - 1];
                        for (int i = 0; i < index.length; i+= 3) {
                            index[i] = convertToInteger(newCommandPart[i+1], 0, 90);
                            index[i+1] = convertToInteger(newCommandPart[i+2], 0, 90);
                            index[i+2] = convertToInteger(newCommandPart[i+3], 0, 5);
                        }
                        game.play(index);
                    } catch (QwirkleException e) {
                        View.displayError(e.getMessage());
                        commandComplete = false;
                    }
                }
                break;
            case 'f' :
                if (newCommandPart.length < 3) {
                    commandComplete = false;
                } else {
                    try {
                        Direction d = witchDirection(newCommandPart[1]);
                        int[] index = new int[newCommandPart.length - 2];
                        for (int i = 0; i < newCommandPart.length - 2; i++) {
                            index[i] = convertToInteger(newCommandPart[i+2], 0, 5);
                        }
                        game.first(d, index);
                    } catch (QwirkleException e) {
                        View.displayError(e.getMessage());
                        commandComplete = false;
                    }
                }
                break;
            case 'p' :
                if (newCommandPart.length != 1) {
                    commandComplete = false;
                } else {
                    game.pass();
                }
                break;
            case 'q' :
                System.out.println("La partie est terminer (vous avez quitter la partie)");
                quit = true;
                break;
            default:
                commandComplete = false;
                break;
        }
        return commandComplete;
    }

    /**
     * La méthode permet de savoir quel est la direction par rapport à la commande demandé pour jouer un/des tuile(s)
     * @param direction Est un caractère qui devrait être une direction
     * @return Une direction par rapport à la Class Direction
     */
    private static Direction witchDirection(String direction) {
        return switch (direction.charAt(0)) {
            case 'u' -> Direction.UP;
            case 'd' -> Direction.DOWN;
            case 'l' -> Direction.LEFT;
            case 'r' -> Direction.RIGHT;
            default -> throw new QwirkleException("Commende pas correct");
        };
    }

    /**
     * Cette méthode permet de convertir une chaîne de charactère en un entier
     * @param str la chaîne de caractère à convertir
     * @param min Un entier dont la convertion ne doit pas être plus petit que ce nombre
     * @param max Un entier dont la convertion ne doit pas dépasser ce nombre
     * @return Un entier qui à été converti depuis une chaîne de charactère.
     */
    private static int convertToInteger(String str, int min, int max) {
        int val;
        try {
            val = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {
            throw new QwirkleException("Commande pas correct");
        }
        if ((val < min) || (val > max)) {
            throw new QwirkleException("Commande pas correct");
        }
        return val;
    }

    /**
     * C'est une méthode robuste qui permet de faire entrer obligadoirement un entier à l'utilisateur
     * @param message Le méssage à afficher pour savoir se que l'utilisateur doit entrer
     * @return Un entier qui à été validé par la méthode robuste
     */
    private static int askInt(String message) {
        Scanner clavier = new Scanner(System.in);
        System.out.print(message);

        while (!clavier.hasNextInt()) {
            clavier.next();
            System.out.println("La valeur entrée n'est pas un entier !");
            System.out.print(message);
        }
        return clavier.nextInt();
    }
    private static int getWinnerIndex(Player[] players) {
        int winnerIndex = 0;
        for (int i = 0; i < players.length-1; i++) {
            int playerScoreCheck = players[i].getScore();
            if (playerScoreCheck < players[i+1].getScore()) {
                winnerIndex = i+1;
            }
        }
        return winnerIndex;
    }

    /**
     * Cette méthode permet de demander à l'utilisateur un nombre compris entre un minimum et un maximum
     * @param message Le message à afficher pour savoir se que l'utilisateur doit entrer
     * @param min Un entier dont la valeur entrer par l'utilisateur ne doit pas être plus petit que min
     * @param max Un entier dont la valeur entrer par l'utilisateur ne doit pas dépasser le max
     * @return L'entier qui auras été validé par la méthode robuste
     */
    private static int askInt(String message, int min, int max) {
        int value = askInt(message + " (comprise entre "+ min +" et "+ max +")");
        while (! (min <= value && value <= max)) {
            System.out.println("la valeur entrée n'est pas comprise entre "+ min +" et "+ max);
            value = askInt(message + "(comprise entre "+ min +" et "+ max +")");
        }
        return value;
    }

    /**
     * Cette méthode est là pour savoir si la personne veux ou non quelque chose (y / n)
     * @param message le message à affichier pour savoir si la personne veux
     * @return un booléen qui sera vrais si 'y' ou faux si 'n'
     */
    private static boolean yesOrNo(String message) {
        Scanner clavier = new Scanner(System.in);
        boolean restaurer = false;
        System.out.println(message);
        char restaurerPartie = clavier.next().toLowerCase().charAt(0);
        while (restaurerPartie != 'y' && restaurerPartie != 'n') {
            System.out.println("Erreur d'encodage");
            System.out.println(message);
            restaurerPartie = clavier.next().toLowerCase().charAt(0);
        }
        switch (restaurerPartie) {
            case 'y' -> restaurer = true;
            case 'n' -> restaurer = false;
        }
        return restaurer;
    }

    /**
     * Cette methode permet de vérifier pour ne pas avoir 2 fois le même nom de joueur
     * @param playerNames une chaîne qui est le nom que l'on veux vérifier
     * @param actualNameToAdd un tableau de chaîne qui contient des nom de joueur
     * @return un booléen qui sera vrais si le nom est déjà présent, faux sinon.
     */
    private static boolean checkNotSameName(List<String> playerNames, String actualNameToAdd) {
        int index = 0;
        boolean sameName = false;
        actualNameToAdd = actualNameToAdd.toLowerCase(); // Mettre en minuscule
        while (!sameName && index < playerNames.size()) {
            String name2 = playerNames.get(index).toLowerCase(); // Mettre en minuscule
            sameName = actualNameToAdd.equals(name2);
            index++;
        }
        return sameName;
    }
}