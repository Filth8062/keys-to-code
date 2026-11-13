package ch.keystocode.business;

import java.util.List;
import java.util.Objects;

// ============================================================================
// POUR COMPRENDRE (FR) : un "record"
// ============================================================================
// Un record est une classe spéciale qui sert juste à TRANSPORTER des données,
// comme une fiche bristol remplie une fois pour toutes :
//
//      Leçon n° lesson-01-home-row
//      Titre : La rangée de base
//      Lignes : "fff jjj", "ddd kkk", ...
//
// En une seule ligne, Java fabrique pour nous :
//   - un constructeur  : new Lesson("id", "titre", lignes)
//   - des "accesseurs" : lesson.id(), lesson.title(), lesson.lines()
//   - et quelques méthodes utiles (equals, toString...).
//
// Un record est IMMUABLE : une fois créé, on ne peut plus changer son contenu.
// C'est une sécurité : personne ne pourra modifier la leçon "par erreur".
//
// "List<String>" se lit "une liste de String" (String = du texte).
// Les chevrons < > précisent CE QUE contient la liste : Java refuse qu'on y
// mette autre chose que du texte. C'est ça, un langage fortement typé.
// ============================================================================

/**
 * A typing lesson: an identifier, a human-readable title and the lines to type.
 *
 * <p>A {@code Lesson} is immutable. The list of lines is copied on creation,
 * so changing the original list afterwards has no effect on the lesson.</p>
 *
 * @param id    unique identifier, for example {@code "lesson-01-home-row"}
 * @param title title shown to the user
 * @param lines the lines the user has to type, in order (never empty)
 */
public record Lesson(String id, String title, List<String> lines) {

    /**
     * Validates the data and makes a defensive copy of the lines.
     *
     * @throws NullPointerException     if an argument is {@code null}
     * @throws IllegalArgumentException if {@code lines} is empty
     */
    public Lesson {
        // POUR COMPRENDRE (FR) : on vérifie que les données sont valables AVANT
        // de créer la leçon. Si ce n'est pas le cas, on "lance une exception" :
        // le programme s'arrête avec un message clair, plutôt que de continuer
        // avec des données fausses et planter plus tard, de façon mystérieuse.
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(lines, "lines must not be null");
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("A lesson needs at least one line: " + id);
        }
        // Copie "défensive" : on garde notre propre exemplaire, non modifiable.
        lines = List.copyOf(lines);
    }
}
