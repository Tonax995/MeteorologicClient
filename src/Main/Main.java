package Main;

import controller.ControladorUDP;
import model.DatosMeteorologicos;
import view.VistaDatos;

public class Main {
    public static void main(String[] args) {
        // Crear las instancias de Vista, Modelo y Controlador
        VistaDatos vista = new VistaDatos();
        DatosMeteorologicos modelo = new DatosMeteorologicos();
        ControladorUDP controlador = new ControladorUDP(vista, modelo);

        // La ventana se abre y espera la interacción del usuario para iniciar la conexión
        // La conexión se iniciará al hacer clic en el botón "Conectar" en la interfaz gráfica
    }
}
