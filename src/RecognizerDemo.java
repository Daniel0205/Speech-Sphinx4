import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class RecognizerDemo {
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resources/edu/cmu/sphinx/models/voxforge-es-0.1.1/model_parameters/voxforge_es_sphinx.cd_cont_1500");
        configuration.setDictionaryPath("resources/edu/cmu/sphinx/models/voxforge-es-0.1.1/etc/voxforge_es_sphinx.dic");
        configuration.setLanguageModelPath("resources/edu/cmu/sphinx/models/voxforge-es-0.1.1/etc/voxforge_es_sphinx.transcription.test.lm");

        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
        // InputStream stream = new FileInputStream(new File("test.wav"));

        recognizer.startRecognition(true);
        SpeechResult result;
        while (true) {
            result = recognizer.getResult();
            System.out.println(result);
            String res = result.getHypothesis();
            System.out.format("Hypothesis: %s\n", res);

            if(res == "ROJO"){
                break;
            }
        }
        recognizer.stopRecognition();
    }
}
