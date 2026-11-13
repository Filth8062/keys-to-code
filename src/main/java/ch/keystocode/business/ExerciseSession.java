package ch.keystocode.business;

import java.util.Objects;

// ============================================================================
// POUR COMPRENDRE (FR) : une classe
// ============================================================================
// Une classe, c'est un PLAN DE CONSTRUCTION. Elle décrit :
//   - ce qu'un objet SAIT (ses "attributs", ses variables à lui) ;
//   - ce qu'un objet SAIT FAIRE (ses "méthodes").
//
// Une ExerciseSession, c'est une séance d'entraînement. Elle sait :
//   - quelle leçon on fait ;
//   - à quelle ligne on en est ;
//   - combien de lignes ont été tapées sans faute.
// Et elle sait faire :
//   - donner la ligne à taper (currentLine) ;
//   - vérifier ce que tu as tapé (submit) ;
//   - dire si c'est terminé (isFinished).
//
// Cette classe est dans la couche BUSINESS ("métier") : elle contient les
// RÈGLES du jeu. Elle n'affiche rien et ne lit rien au clavier : ça, c'est le
// travail de la couche presentation. Grâce à cette séparation, on pourra
// réutiliser cette classe TELLE QUELLE dans l'application de bureau et dans
// l'application web. Retiens bien cette idée, elle reviendra souvent !
// ============================================================================

/**
 * One typing session on a {@link Lesson}: the user types the lines one after
 * the other, and the session keeps track of the progress.
 *
 * <p>Rule of exercise 1: a typed line is correct only if it is
 * <strong>exactly</strong> identical to the expected line (same characters,
 * same spaces, same case). Whether the line is correct or not, the session
 * then moves on to the next line.</p>
 *
 * <p>This class belongs to the business layer: it does not print anything
 * and does not read the keyboard.</p>
 */
public class ExerciseSession {

    // ------------------------------------------------------------------------
    // POUR COMPRENDRE (FR) : les attributs
    // ------------------------------------------------------------------------
    // private -> seule cette classe peut les lire ou les modifier. Les autres
    //            classes doivent passer par les méthodes publiques. C'est
    //            "l'encapsulation" : on protège l'intérieur de l'objet.
    // final   -> une fois donnée, la valeur ne changera plus jamais.
    // int     -> un nombre entier (…, -1, 0, 1, 2, …).
    // ------------------------------------------------------------------------

    /** The lesson being practised. */
    private final Lesson lesson;

    /** Index (starting at 0) of the line the user has to type now. */
    private int currentIndex;

    /** Number of lines typed without any mistake so far. */
    private int correctLineCount;

    // ------------------------------------------------------------------------
    // POUR COMPRENDRE (FR) : le constructeur
    // ------------------------------------------------------------------------
    // C'est la méthode spéciale appelée par "new ExerciseSession(lesson)".
    // Elle porte le même nom que la classe et prépare l'objet tout neuf.
    // "this.lesson" = l'attribut de l'objet ; "lesson" tout court = le
    // paramètre reçu. On range le paramètre dans l'attribut.
    // ------------------------------------------------------------------------

    /**
     * Creates a new session, positioned on the first line of the lesson.
     *
     * @param lesson the lesson to practise
     * @throws NullPointerException if {@code lesson} is {@code null}
     */
    public ExerciseSession(Lesson lesson) {
        this.lesson = Objects.requireNonNull(lesson, "lesson must not be null");
        this.currentIndex = 0;
        this.correctLineCount = 0;
    }

    /**
     * Returns the lesson being practised.
     *
     * @return the lesson
     */
    public Lesson lesson() {
        return lesson;
    }

    /**
     * Returns the line the user has to type now.
     *
     * @return the expected line
     * @throws IllegalStateException if the session is already finished
     */
    public String currentLine() {
        ensureNotFinished();
        // POUR COMPRENDRE (FR) : get(i) prend l'élément numéro i de la liste.
        // Attention, en informatique on compte à partir de 0 : la première
        // ligne est get(0), la deuxième get(1), etc.
        return lesson.lines().get(currentIndex);
    }

    /**
     * Checks the line typed by the user against the expected line, then moves
     * on to the next line.
     *
     * @param typedLine what the user typed (may be {@code null}, which is never correct)
     * @return {@code true} if the line was typed exactly right, {@code false} otherwise
     * @throws IllegalStateException if the session is already finished
     */
    public boolean submit(String typedLine) {
        String expectedLine = currentLine();

        // POUR COMPRENDRE (FR) : boolean = une valeur qui vaut true (vrai) ou
        // false (faux). equals() compare deux textes caractère par caractère.
        // ATTENTION : pour comparer des textes en Java, on utilise equals(),
        // jamais == (on verra pourquoi plus tard, c'est un piège classique).
        boolean correct = expectedLine.equals(typedLine);

        // POUR COMPRENDRE (FR) : "if" = "si". Le code entre { } n'est exécuté
        // que si la condition entre ( ) est vraie.
        if (correct) {
            correctLineCount++; // "++" ajoute 1 : correctLineCount = correctLineCount + 1
        }
        currentIndex++;
        return correct;
    }

    /**
     * Tells whether every line of the lesson has been submitted.
     *
     * @return {@code true} when there is no line left to type
     */
    public boolean isFinished() {
        return currentIndex >= lesson.lines().size();
    }

    /**
     * Returns the number of the line to type now, counting from 1 (handy for display).
     *
     * @return the current line number, between 1 and {@link #totalLineCount()}
     *         (or {@code totalLineCount() + 1} once finished)
     */
    public int currentLineNumber() {
        return currentIndex + 1;
    }

    /**
     * Returns the number of lines typed without any mistake so far.
     *
     * @return the number of correct lines
     */
    public int correctLineCount() {
        return correctLineCount;
    }

    /**
     * Returns the total number of lines in the lesson.
     *
     * @return the number of lines
     */
    public int totalLineCount() {
        return lesson.lines().size();
    }

    /**
     * Throws an exception if the session is finished. Used to protect the
     * methods that need a current line.
     */
    private void ensureNotFinished() {
        if (isFinished()) {
            throw new IllegalStateException("The session is finished: there is no line left to type.");
        }
    }
}
