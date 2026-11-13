package ch.keystocode.business;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// ============================================================================
// POUR COMPRENDRE (FR) : un test automatique
// ============================================================================
// Un test, c'est un petit programme qui vérifie qu'un autre programme fait
// bien ce qu'on attend. Chaque méthode marquée @Test suit le même schéma,
// "les 3 A" :
//
//   Arrange : je prépare la situation   (je crée une leçon et une séance)
//   Act     : j'agis                     (je tape une ligne)
//   Assert  : je vérifie le résultat     (assertEquals(attendu, obtenu))
//
// Pour lancer les tests : clic droit sur le dossier "src/test/java" puis
// "Run 'All Tests'". Vert = tout va bien. Rouge = quelque chose ne va pas,
// et le test te dit quoi.
//
// Pourquoi c'est génial : si un jour tu modifies ExerciseSession et que tu
// casses quelque chose sans le voir, les tests deviendront rouges tout seuls.
// ============================================================================

/**
 * Unit tests of {@link ExerciseSession}.
 */
class ExerciseSessionTest {

    /** Creates a small two-line lesson used by the tests. */
    private Lesson twoLineLesson() {
        return new Lesson("test", "Test lesson", List.of("fff jjj", "ddd kkk"));
    }

    @Test
    void startsOnFirstLine() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        assertEquals("fff jjj", session.currentLine());
        assertEquals(1, session.currentLineNumber());
        assertEquals(2, session.totalLineCount());
        assertFalse(session.isFinished());
    }

    @Test
    void exactLineIsCorrect() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        boolean correct = session.submit("fff jjj");

        assertTrue(correct);
        assertEquals(1, session.correctLineCount());
    }

    @Test
    void lineWithAMistakeIsNotCorrect() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        boolean correct = session.submit("fff jjk");

        assertFalse(correct);
        assertEquals(0, session.correctLineCount());
    }

    @Test
    void extraSpaceIsAMistake() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        assertFalse(session.submit("fff jjj "));
    }

    @Test
    void movesToNextLineEvenAfterAMistake() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        session.submit("oops");

        assertEquals("ddd kkk", session.currentLine());
        assertEquals(2, session.currentLineNumber());
    }

    @Test
    void isFinishedAfterLastLine() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        session.submit("fff jjj");
        session.submit("ddd kkk");

        assertTrue(session.isFinished());
        assertEquals(2, session.correctLineCount());
    }

    @Test
    void cannotAskForALineOnceFinished() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());
        session.submit("fff jjj");
        session.submit("ddd kkk");

        assertThrows(IllegalStateException.class, session::currentLine);
        assertThrows(IllegalStateException.class, () -> session.submit("fff jjj"));
    }

    @Test
    void nullInputIsNeverCorrect() {
        ExerciseSession session = new ExerciseSession(twoLineLesson());

        assertFalse(session.submit(null));
    }
}
