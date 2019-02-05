import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

public class GUIStroop extends JFrame implements Runnable {

    //Variables de la GUI
    private Container contenedor;
    private JPanel panelUsuario,southPanel;
    private JLabel palabras;
    private JButton iniciar,palabrasB,resultados,coloresB,mixColoresB,menu;
    private Font fuente;
    //Hilo del reconocedor de voz
    private Thread speech,reconocedor;
    //Variables usadas en el reconocimiento
    private Configuration configuration;
    private LiveSpeechRecognizer recognizer = null;
    private SpeechResult result;
    private PruebaStroop stroop;
    private String tipoPrueba;
    private int contador = 0;

    //Contructor
    public GUIStroop(){
        super("Prueba De STROOP");
        speech = new Thread(this);
        reconocedor = new Thread(new Reconociendo());
        stroop = new PruebaStroop();
        fuente = new Font("SansSerif",Font.BOLD,40);

        initMenu();
    }

    //Menu para elegir la modalida de la prueba
    private void initMenu() {
        contenedor = getContentPane();
        contenedor.removeAll();
        contenedor.repaint();
        getContentPane().setLayout(null);

        panelUsuario = new JPanel();
        panelUsuario.setBounds(0, 0, 350, 350);
        contenedor.add(panelUsuario);
        panelUsuario.setLayout(new FlowLayout());

        palabrasB = new JButton("Palabras");
        palabrasB.setSize(100,50);
        palabrasB.addActionListener(new ManejadorDeBotones());
        panelUsuario.add(palabrasB);

        coloresB = new JButton("Colores");
        coloresB.setSize(100,50);
        coloresB.addActionListener(new ManejadorDeBotones());
        panelUsuario.add(coloresB);


        mixColoresB = new JButton("Mix Colores");
        mixColoresB.setSize(100,50);
        mixColoresB.addActionListener(new ManejadorDeBotones());
        panelUsuario.add(mixColoresB);

        setResizable(false);
        setSize(350,350);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    //GUI de la prueba
    private void initGUI() {

        contenedor.removeAll();
        contenedor.repaint();
        getContentPane().setLayout(null);

        panelUsuario = new JPanel();
        panelUsuario.setBounds(0, 0, 500, 475);
        contenedor.add(panelUsuario);
        panelUsuario.setLayout(new BorderLayout());

        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout());
        panelUsuario.add(southPanel,BorderLayout.SOUTH);

        palabras = new JLabel();
        palabras.setFont(fuente);
        palabras.setHorizontalAlignment(JLabel.CENTER);
        //palabras.setEditable(false);
        JScrollPane sp = new JScrollPane(palabras);
        panelUsuario.add(sp,BorderLayout.CENTER);

        iniciar = new JButton("Iniciar");
        iniciar.setSize(100,50);
        iniciar.addActionListener(new ManejadorDeBotones());
        southPanel.add(iniciar,BorderLayout.SOUTH);

        resultados = new JButton("Ver resultados");
        resultados.setEnabled(false);
        resultados.addActionListener(new ManejadorDeBotones());
        southPanel.add(resultados);

        menu = new JButton("Volver al menu");
        menu.setEnabled(false);
        menu.addActionListener(new ManejadorDeBotones());
        southPanel.add(menu);

        setResizable(false);
        setSize(500,500);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    //Actualiza la palabra y el color que aparecera
    private void actualizarPalabras(int i) {
        Color color = new Color(0,0,0);

        if(!(tipoPrueba.compareTo("Palabras")==0)){
            switch (stroop.getColor(i)){
                case "Azul":
                    color = new Color(0,0,255);
                    break;
                case "Rojo":
                    color = new Color(255,0,0);
                    break;
                case "Verde":
                    color = new Color(0,255,0);
                    break;
            }
            palabras.setForeground(color);
        }

        if (tipoPrueba.compareTo("Colores")==0){
            palabras.setText("XXXX");
        }
        else palabras.setText(stroop.getPalabra(i));

    }

    //Inicia la pruba
    public void iniciarPrueba() throws Exception{
        try {
            recognizer = new LiveSpeechRecognizer(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }

        recognizer.startRecognition(true);

        reconocedor.start();

        reconocedor.join(5000);

        reconocedor.interrupt();

        palabras.setText("Prueba terminada");
        palabras.setForeground(Color.BLACK);

        menu.setEnabled(true);
        resultados.setEnabled(true);
    }

    //Inicia el Hilo para controlar el reconcedor y la GUI al tiempo
    @Override
    public void run() {
        iniciar.setEnabled(false);

        //////////Configuracion del reconocedor de voz////////////
        configuration = new Configuration();

        configuration.setAcousticModelPath("resources/cmusphinx-es-5.2/model_parameters/voxforge_es_sphinx.cd_ptm_4000");
        configuration.setDictionaryPath("resources/cmusphinx-es-5.2/etc/voxforge_es_sphinx.dic");

        configuration.setGrammarPath("resources/grammars");
        configuration.setGrammarName("grammar");
        configuration.setUseGrammar(true);
        //////////////////////////////////////////////////////////

        //Inicia la prueba
        try {
            iniciarPrueba();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ManejadorDeBotones implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent)  {
            if(actionEvent.getSource()== iniciar){
                speech.start();
                return;
            }
            if(actionEvent.getSource()== menu){
                initMenu();
                contador=0;
                return;
            }
            if(actionEvent.getSource()== resultados){
                JOptionPane.showMessageDialog(null,"Numero de palabras: "+contador);
                return;
            }

            if (actionEvent.getSource()== palabrasB){
                tipoPrueba = "Palabras";
            }
            if (actionEvent.getSource()== coloresB){
                tipoPrueba = "Colores";
            }
            if (actionEvent.getSource()== mixColoresB){
                tipoPrueba = "Mix Colores";
            }
            initGUI();
        }
    }

    private class Reconociendo implements Runnable{

        @Override
        public void run() {
            Thread t = Thread.currentThread();
            int i = 0;

            while((i<100) && !(t.isInterrupted())){
                actualizarPalabras(i);

                result = recognizer.getResult();
                String res = result.getHypothesis();

                System.out.println("La palabra es: " +res);

                if(stroop.comprobarPalabra(res,i,tipoPrueba)) i++;

            }// fin While
            contador=i;
            recognizer.stopRecognition();
            System.out.println("He terminado :D");

        }// fin run()
    }// fin clase Reconociendo
}
