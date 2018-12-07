
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class TranscriberDemo {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resources/edu/cmu/sphinx/models/voxforge-es-0.1.1/model_parameters/voxforge_es_sphinx.cd_cont_1500");
        configuration.setDictionaryPath("resources/edu/cmu/sphinx/models/voxforge-es-0.1.1/etc/voxforge_es_sphinx.dic");
        configuration.setLanguageModelPath("resources/edu/cmu/sphinx/models/voxforge-es-0.1.1/etc/voxforge_es_sphinx.transcription.test.lm");

        StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
        InputStream stream = new FileInputStream(new File("test.wav"));


        recognizer.startRecognition(stream);
        SpeechResult result;
        while ((result = recognizer.getResult()) != null) {
            System.out.print(result.getHypothesis() +"\n");
            System.out.format("Hypothesis: %s\n", result.getHypothesis());
        }
        recognizer.stopRecognition();
    }
}
