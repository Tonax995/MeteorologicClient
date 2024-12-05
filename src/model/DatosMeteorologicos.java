/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author tonar
 */
// Model/DatosMeteorologicos.java

public class DatosMeteorologicos {
    private double temperatura;
    private double humedad;
    private double velocidadViento;
    private double direccionViento;
    private double presionAtmosferica;
    private double puntoRocio;
    private double indiceUV;
    private double cantidadPrecipitacion;
    private double visibilidad;
    private double altitudNubosidad;
    private String condicionActual;

    // Getters y Setters
    public double getTemperatura() { return temperatura; }
    public void setTemperatura(double temperatura) { this.temperatura = temperatura; }

    public double getHumedad() { return humedad; }
    public void setHumedad(double humedad) { this.humedad = humedad; }

    public double getVelocidadViento() { return velocidadViento; }
    public void setVelocidadViento(double velocidadViento) { this.velocidadViento = velocidadViento; }

    public double getDireccionViento() { return direccionViento; }
    public void setDireccionViento(double direccionViento) { this.direccionViento = direccionViento; }

    public double getPresionAtmosferica() { return presionAtmosferica; }
    public void setPresionAtmosferica(double presionAtmosferica) { this.presionAtmosferica = presionAtmosferica; }

    public double getPuntoRocio() { return puntoRocio; }
    public void setPuntoRocio(double puntoRocio) { this.puntoRocio = puntoRocio; }

    public double getIndiceUV() { return indiceUV; }
    public void setIndiceUV(double indiceUV) { this.indiceUV = indiceUV; }

    public double getCantidadPrecipitacion() { return cantidadPrecipitacion; }
    public void setCantidadPrecipitacion(double cantidadPrecipitacion) { this.cantidadPrecipitacion = cantidadPrecipitacion; }

    public double getVisibilidad() { return visibilidad; }
    public void setVisibilidad(double visibilidad) { this.visibilidad = visibilidad; }

    public double getAltitudNubosidad() { return altitudNubosidad; }
    public void setAltitudNubosidad(double altitudNubosidad) { this.altitudNubosidad = altitudNubosidad; }

    public String getCondicionActual() { return condicionActual; }
    public void setCondicionActual(String condicionActual) { this.condicionActual = condicionActual; }
}
