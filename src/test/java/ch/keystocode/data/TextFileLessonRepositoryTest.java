package ch.keystocode.data;

import ch.keystocode.business.Lesson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests of {@link TextFileLessonRepository}, using the real lesson files.
 */
class TextFileLessonRepositoryTest {

    private final LessonRepository repository = new TextFileLessonRepository();

    @Test
    void loadsFirstLesson() {
        Lesson lesson = repository.findById("lesson-01-home-row");

        assertEquals("lesson-01-home-row", lesson.id());
        assertTrue(lesson.title().startsWith("Exercice 1"));
        assertFalse(lesson.lines().isEmpty());
    }

    @Test
    void ignoresCommentsBlankLinesAndTitle() {
        Lesson lesson = repository.findById("lesson-01-home-row");

        for (String line : lesson.lines()) {
            assertFalse(line.isBlank(), "blank line kept");
            assertFalse(line.startsWith("#"), "comment kept: " + line);
            assertFalse(line.startsWith("@title"), "title kept: " + line);
        }
    }

    @Test
    void firstLessonOnlyUsesHomeRowKeys() {
        Lesson lesson = repository.findById("lesson-01-home-row");

        for (String line : lesson.lines()) {
            assertTrue(line.matches("[asdfjkl ]+"), "unexpected key in: " + line);
        }
    }

    @Test
    void unknownLessonIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> repository.findById("does-not-exist"));
    }
}
