import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class GUIStroop extends JFrame implements Runnable {


    private Container contenedor;
    private JPanel panelUsuario;
    private JTextArea palabras;
    private JButton iniciar;
    private Thread speech;

    public GUIStroop(){
        super("Prueba De STROOP");
        speech = new Thread(this);

        initGUI();
    }

    private void initGUI() {

        contenedor = getContentPane();
        contenedor.removeAll();
        getContentPane().setLayout(null);

        panelUsuario = new JPanel();
        panelUsuario.setBounds(0, 0, 500, 475);
        contenedor.add(panelUsuario);
        panelUsuario.setLayout(new BorderLayout());

        palabras = new JTextArea();
        palabras.setEditable(false);
        JScrollPane sp = new JScrollPane(palabras);
        panelUsuario.add(sp,BorderLayout.CENTER);

        iniciar = new JButton("Iniciar");
        iniciar.setSize(100,50);
        iniciar.addActionListener(new ManejadorDeBotones());
        panelUsuario.add(iniciar,BorderLayout.SOUTH);


        setResizable(false);
        setSize(500,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }


    @Override
    public void run() {
        iniciar.setEnabled(false);

        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resources/cmusphinx-es-5.2/model_parameters/voxforge_es_sphinx.cd_ptm_4000");
        configuration.setDictionaryPath("resources/cmusphinx-es-5.2/etc/voxforge_es_sphinx.dic");

        configuration.setGrammarPath("resources/grammars");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);


        LiveSpeechRecognizer recognizer = null;
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recognizer.startRecognition(true);
        SpeechResult result;
       while (true) {
        result = recognizer.getResult();
        String res = result.getHypothesis();

        palabras.setText(palabras.getText()+ res+ "\n");

            if(res == "ROJO"){
                break;
            }
        }
        recognizer.stopRecognition();
    }

    private class ManejadorDeBotones implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent)  {
            if(actionEvent.getSource()== iniciar){
                speech.start();
            }
        }
    }

}
