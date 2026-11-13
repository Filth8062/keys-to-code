package ch.keystocode;

// ============================================================================
// POUR COMPRENDRE (FR) : les "import"
// ============================================================================
// Une classe peut utiliser d'autres classes. Quand elles sont rangées dans un
// autre dossier (un autre "package"), on doit dire à Java où les trouver.
// C'est le rôle des lignes "import" : "je vais utiliser ExerciseSession, qui
// est rangée dans le package ch.keystocode.business".
// ============================================================================
import ch.keystocode.business.ExerciseSession;
import ch.keystocode.business.Lesson;
import ch.keystocode.data.LessonRepository;
import ch.keystocode.data.TextFileLessonRepository;
import ch.keystocode.presentation.ConsoleView;

/**
 * Entry point of the KeysToCode typing trainer.
 *
 * <p>This class only <em>assembles</em> the application: it creates one object
 * per layer (data, business, presentation), connects them, and starts the
 * exercise. It contains no typing logic itself. In software design this place
 * is called the <em>composition root</em>.</p>
 *
 * <p>Architecture (3 layers, "3-tier"):</p>
 * <pre>
 *   presentation  -&gt;  ConsoleView            (talks to the user)
 *   business      -&gt;  ExerciseSession, Lesson (the rules of the exercise)
 *   data          -&gt;  LessonRepository       (where lessons come from)
 * </pre>
 */
public class App {

    /** Identifier of the lesson used by exercise 1 (file {@code lessons/lesson-01-home-row.txt}). */
    private static final String FIRST_LESSON_ID = "lesson-01-home-row";

    // ========================================================================
    // POUR COMPRENDRE (FR) : la méthode main
    // ========================================================================
    // Quand tu cliques sur le bouton vert ▶ "Run", Java cherche une méthode qui
    // s'appelle EXACTEMENT "main" et commence par là. C'est la porte d'entrée.
    //
    //  public      -> tout le monde a le droit de l'appeler (Java doit pouvoir).
    //  static      -> elle appartient à la classe elle-même : pas besoin de
    //                 créer un objet App avant de l'appeler.
    //  void        -> elle ne renvoie aucun résultat ("vide").
    //  String[] args -> une liste de textes qu'on pourrait passer au lancement
    //                 (on ne s'en sert pas pour l'instant).
    // ========================================================================

    /**
     * Starts the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // --------------------------------------------------------------------
        // POUR COMPRENDRE (FR) : une variable
        // --------------------------------------------------------------------
        // Une variable, c'est une BOÎTE avec une étiquette, qui contient une
        // valeur. En Java, chaque boîte a un TYPE : elle ne peut contenir
        // qu'une seule sorte de chose. On l'écrit toujours dans cet ordre :
        //
        //      Type        nom          =  valeur;
        //      LessonRepository repository = new TextFileLessonRepository();
        //
        // "new" fabrique un nouvel OBJET à partir d'une classe (la classe est
        // le plan de construction, l'objet est la maison construite).
        //
        // Et chaque instruction se termine par un point-virgule ";".
        // --------------------------------------------------------------------

        // 1. Couche DATA : l'objet qui sait où trouver les leçons (dans des fichiers texte).
        LessonRepository repository = new TextFileLessonRepository();

        // 2. On lui demande la leçon de l'exercice 1.
        Lesson lesson = repository.findById(FIRST_LESSON_ID);

        // 3. Couche BUSINESS : une "séance" d'exercice, qui connaît les règles
        //    (quelle ligne taper, est-ce juste, est-ce terminé ?).
        ExerciseSession session = new ExerciseSession(lesson);

        // 4. Couche PRESENTATION : l'objet qui parle avec l'utilisatrice dans la console.
        ConsoleView view = new ConsoleView();

        // 5. C'est parti !
        view.run(session);
    }
}
