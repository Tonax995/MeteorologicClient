package server;

import org.json.JSONObject;
import java.util.Random;

public class DatosMeteorologicos {
    private Random random;

    public DatosMeteorologicos() {
        this.random = new Random();
    }

    public JSONObject generarDatosJSON() {
        JSONObject datosClima = new JSONObject();
        datosClima.put("temperatura", generarTemperatura());
        datosClima.put("humedad", generarHumedad());
        datosClima.put("velocidadViento", generarVelocidadViento());
        datosClima.put("direccionViento", generarDireccionViento());
        datosClima.put("presionAtmosferica", generarPresionAtmosferica());
        datosClima.put("puntoRocio", generarPuntoRocio(datosClima.getDouble("temperatura"), datosClima.getDouble("humedad")));
        datosClima.put("indiceUV", generarIndiceUV());
        datosClima.put("cantidadPrecipitacion", generarCantidadPrecipitacion());
        datosClima.put("visibilidad", generarVisibilidad());
        datosClima.put("altitudNubosidad", generarAltitudNubosidad());
        datosClima.put("condicionActual", generarCondicionActual());
        return datosClima;
    }

    private double generarTemperatura() {
        return 15 + (30 - 15) * random.nextDouble();
    }

    private double generarHumedad() {
        return 30 + (90 - 30) * random.nextDouble();
    }

    private double generarVelocidadViento() {
        return 0 + (15 - 0) * random.nextDouble();
    }

    private double generarDireccionViento() {
        return random.nextDouble() * 360;
    }

    private double generarPresionAtmosferica() {
        return 950 + (1050 - 950) * random.nextDouble();
    }

    private double generarPuntoRocio(double temperatura, double humedad) {
        return temperatura - ((100 - humedad) / 5);
    }

    private double generarIndiceUV() {
        return random.nextDouble() * 10;
    }

    private double generarCantidadPrecipitacion() {
        return random.nextDouble() * 50;
    }

    private double generarVisibilidad() {
        return random.nextDouble() * 10;
    }

    private double generarAltitudNubosidad() {
        return random.nextDouble() * 2000;
    }

    private String generarCondicionActual() {
        String[] condiciones = {"Soleado", "Nublado", "Lluvioso", "Tormenta", "Niebla"};
        return condiciones[random.nextInt(condiciones.length)];
    }
}
