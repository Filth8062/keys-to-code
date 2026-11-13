package ch.keystocode.data;

import ch.keystocode.business.Lesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

// ============================================================================
// POUR COMPRENDRE (FR)
// ============================================================================
// Cette classe RESPECTE LE CONTRAT LessonRepository ("implements") : elle
// fournit vraiment la méthode findById, en allant lire un fichier texte rangé
// dans src/main/resources/lessons/.
//
// Tu n'as pas besoin de comprendre chaque ligne aujourd'hui : la lecture de
// fichiers et les "exceptions" sont au programme de l'exercice 5. Pour
// l'instant, retiens juste le format d'un fichier de leçon :
//
//      # Les lignes qui commencent par # sont des commentaires (ignorés).
//      @title La rangée de base
//      fff jjj fff jjj
//      ddd kkk ddd kkk
//
//   - la ligne "@title ..." donne le titre de la leçon ;
//   - les lignes vides sont ignorées ;
//   - toutes les autres lignes sont les lignes à taper.
// ============================================================================

/**
 * A {@link LessonRepository} that reads lessons from UTF-8 text files packaged
 * with the application, in the {@code lessons/} resource folder.
 *
 * <p>File format, one file per lesson, named {@code <id>.txt}:</p>
 * <ul>
 *   <li>lines starting with {@code #} are comments and are ignored;</li>
 *   <li>blank lines are ignored;</li>
 *   <li>a line starting with {@code @title } gives the lesson title;</li>
 *   <li>every other line is a line to type, kept exactly as written.</li>
 * </ul>
 */
public class TextFileLessonRepository implements LessonRepository {

    /** Folder (inside the resources) where the lesson files are stored. */
    private static final String LESSON_FOLDER = "/lessons/";

    /** Prefix of the line that gives the lesson title. */
    private static final String TITLE_PREFIX = "@title ";

    /** Prefix of a comment line. */
    private static final String COMMENT_PREFIX = "#";

    @Override
    public Lesson findById(String id) {
        String path = LESSON_FOLDER + id + ".txt";

        // try-with-resources : le fichier sera refermé automatiquement à la fin,
        // même en cas d'erreur (exercice 5).
        try (InputStream stream = TextFileLessonRepository.class.getResourceAsStream(path)) {
            if (stream == null) {
                throw new IllegalArgumentException("No lesson found with id '" + id + "' (expected file " + path + ")");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            return parse(id, reader.readAllLines());
        } catch (IOException e) {
            throw new UncheckedIOException("Could not read lesson file " + path, e);
        }
    }

    /**
     * Turns the raw lines of a lesson file into a {@link Lesson}.
     *
     * @param id       the lesson identifier
     * @param rawLines all the lines of the file
     * @return the lesson
     */
    private Lesson parse(String id, List<String> rawLines) {
        String title = id; // titre par défaut si le fichier n'a pas de ligne @title
        List<String> lines = new ArrayList<>();

        // POUR COMPRENDRE (FR) : une boucle "for-each". Se lit : "pour chaque
        // ligne rawLine de la liste rawLines, fais ce qui est entre { }".
        for (String rawLine : rawLines) {
            if (rawLine.isBlank() || rawLine.startsWith(COMMENT_PREFIX)) {
                continue; // "continue" = passe directement à la ligne suivante
            }
            if (rawLine.startsWith(TITLE_PREFIX)) {
                title = rawLine.substring(TITLE_PREFIX.length()).strip();
            } else {
                lines.add(rawLine);
            }
        }
        return new Lesson(id, title, lines);
    }
}
