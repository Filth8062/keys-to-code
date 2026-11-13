package ch.keystocode.presentation;

import ch.keystocode.business.ExerciseSession;

import java.util.Scanner;

// ============================================================================
// POUR COMPRENDRE (FR) : la couche PRESENTATION
// ============================================================================
// Cette classe est la seule qui PARLE avec l'utilisatrice :
//   - System.out.println("...") écrit une ligne de texte dans la console ;
//   - le Scanner lit ce que tu tapes au clavier (jusqu'à la touche Entrée).
//
// Elle ne connaît PAS les règles du jeu : pour savoir si une ligne est juste,
// elle demande à l'ExerciseSession (couche business). Quand on passera à une
// vraie fenêtre (JavaFX) puis au web, c'est CETTE classe qu'on remplacera,
// et seulement elle.
// ============================================================================

/**
 * Text user interface of the typing trainer: shows the lines to type in the
 * console and reads what the user types.
 *
 * <p>Presentation layer: it displays and reads, but the rules (is the line
 * correct? is the lesson over?) belong to {@link ExerciseSession}.</p>
 */
public class ConsoleView {

    /** Visual separator printed between parts of the screen. */
    private static final String SEPARATOR = "------------------------------------------------------------";

    /** Reads the keyboard, one line at a time. */
    private final Scanner keyboard;

    /** Creates a view that reads from the keyboard (standard input). */
    public ConsoleView() {
        this.keyboard = new Scanner(System.in);
    }

    /**
     * Runs a whole typing session, from the welcome message to the final score.
     *
     * @param session the session to run
     */
    public void run(ExerciseSession session) {
        printWelcome(session);

        // POUR COMPRENDRE (FR) : une boucle "while" = "tant que".
        // Tant que la séance n'est PAS terminée (le "!" veut dire "non"),
        // on répète le bloc entre { }.
        while (!session.isFinished()) {
            String expected = session.currentLine();

            System.out.println();
            System.out.println("Ligne " + session.currentLineNumber() + "/" + session.totalLineCount() + " :");
            System.out.println("  " + expected);
            System.out.print("> ");

            if (!keyboard.hasNextLine()) {
                // Plus rien à lire (la console a été fermée) : on s'arrête proprement.
                System.out.println();
                System.out.println("Séance interrompue.");
                return;
            }
            String typed = keyboard.nextLine();

            boolean correct = session.submit(typed);
            if (correct) {
                System.out.println("  Parfait !");
            } else {
                System.out.println("  Pas tout à fait. Attendu : \"" + expected + "\"");
                System.out.println("                  Tapé    : \"" + typed + "\"");
            }
        }

        printSummary(session);
    }

    /**
     * Prints the welcome screen.
     *
     * @param session the session about to start
     */
    private void printWelcome(ExerciseSession session) {
        System.out.println(SEPARATOR);
        System.out.println("  KeysToCode - " + session.lesson().title());
        System.out.println(SEPARATOR);
        System.out.println("Recopie chaque ligne puis appuie sur Entrée.");
        System.out.println("Garde les yeux sur l'écran, pas sur le clavier ;-)");
    }

    /**
     * Prints the final score.
     *
     * @param session the finished session
     */
    private void printSummary(ExerciseSession session) {
        System.out.println();
        System.out.println(SEPARATOR);
        System.out.println("Terminé ! Lignes sans faute : "
                + session.correctLineCount() + "/" + session.totalLineCount());
        if (session.correctLineCount() == session.totalLineCount()) {
            System.out.println("Sans aucune faute. Bravo !");
        } else {
            System.out.println("Recommence quand tu veux : la régularité fait tout.");
        }
        System.out.println(SEPARATOR);
    }
}
