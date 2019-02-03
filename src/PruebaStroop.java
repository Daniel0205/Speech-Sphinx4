import javax.swing.*;

public class PruebaStroop extends JFrame {

    private String[] palabras;
    private String[] colores ;


    //Contructor
    public PruebaStroop(){

        //Definicion de orden de palabras y colores definidos en la prueba
        palabras = new String[]{"Rojo", "Verde", "Azul", "Verde", "Rojo", "Azul", "Rojo", "Azul", "Verde", "Azul",
                "Verde", "Rojo", "Verde", "Azul", "Rojo", "Azul", "Rojo", "Verde", "Rojo", "Verde",
                "Azul", "Verde", "Rojo", "Azul", "Rojo", "Verde", "Azul", "Verde", "Rojo", "Verde",
                "Rojo", "Azul", "Rojo", "Azul", "Verde", "Azul", "Verde", "Rojo", "Azul", "Rojo",
                "Verde", "Rojo", "Azul", "Rojo", "Verde", "Azul", "Verde", "Rojo", "Azul", "Verde",
                "Azul", "Rojo", "Azul", "Rojo", "Verde", "Rojo", "Azul", "Verde", "Rojo", "Verde",
                "Rojo", "Azul", "Verde", "Rojo", "Azul", "Verde", "Azul", "Verde", "Rojo", "Azul",
                "Rojo", "Verde", "Rojo", "Verde", "Azul", "Verde", "Rojo", "Azul", "Verde", "Azul",
                "Azul", "Verde", "Rojo", "Azul", "Verde", "Rojo", "Verde", "Rojo", "Azul", "Verde",
                "Rojo", "Azul", "Verde", "Rojo", "Azul", "Rojo", "Verde", "Azul", "Rojo", "Verde" };


    }

    //Obtiene las palabras
    String getPalabra(int index){
        return palabras[index];
    }

    //Obtiene el color de las palabras
    String getColor(int index){
        return palabras[index];
    }

    //Evalua la palabra obtenida con la que esta en la prueba segun la modalida de la prueba
    boolean comprobarPalabra(String palabra,int index,String tipoPrueba){
        String[] aux;
        if (tipoPrueba.compareTo("Colores")==0 || tipoPrueba.compareTo("Mix Colores")==0 )aux = colores;
        else aux=palabras;

        if (palabra.compareTo(aux[index].toLowerCase())==0)return true;
        else return false;
    }
}
