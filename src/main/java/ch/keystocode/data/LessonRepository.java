package ch.keystocode.data;

import ch.keystocode.business.Lesson;

// ============================================================================
// POUR COMPRENDRE (FR) : une interface
// ============================================================================
// Une interface est un CONTRAT. Elle dit CE QU'ON PEUT DEMANDER, sans dire
// COMMENT c'est fait.
//
// Image : une prise électrique. Ton grille-pain se fiche de savoir si le
// courant vient d'une centrale hydraulique ou de panneaux solaires : il a
// juste besoin d'une prise standard.
//
// Ici, le contrat est : "donne-moi la leçon qui a tel identifiant".
// Aujourd'hui, les leçons viennent de fichiers texte (TextFileLessonRepository).
// Plus tard, dans l'application web, elles viendront d'une BASE DE DONNÉES.
// Il suffira d'écrire une nouvelle classe qui respecte le même contrat :
// le reste du programme ne verra AUCUNE différence.
//
// (Pour les curieuses : c'est le "D" de SOLID, le principe d'inversion des
// dépendances, et ce rangement s'appelle le pattern "Repository".)
// ============================================================================

/**
 * Gives access to the typing lessons, wherever they are stored.
 *
 * <p>The rest of the application only knows this interface, never the
 * concrete storage (text files today, a database tomorrow).</p>
 */
public interface LessonRepository {

    /**
     * Finds a lesson by its identifier.
     *
     * @param id the lesson identifier, for example {@code "lesson-01-home-row"}
     * @return the lesson (never {@code null})
     * @throws IllegalArgumentException if no lesson has this identifier
     */
    Lesson findById(String id);
}
