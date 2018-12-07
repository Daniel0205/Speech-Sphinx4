import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class RecognizerDemo {
    public static void main(String[] args) throws Exception {

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resources/cmusphinx-es-5.2/model_parameters/voxforge_es_sphinx.cd_ptm_4000");
        configuration.setDictionaryPath("resources/cmusphinx-es-5.2/etc/voxforge_es_sphinx.dic");

        configuration.setGrammarPath("resources/grammars");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);


        LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);

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
