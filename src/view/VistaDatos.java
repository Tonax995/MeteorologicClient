package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class VistaDatos {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textFieldIP;
    private JTextField textFieldPuerto;
    private JButton botonConectar;
    private JPanel datosPanel;
    private JPanel temperaturaPanel;
    private JPanel vientoPanel;
    private JPanel visibilidadPanel;
    private JPanel condicionPanel;
    private Map<String, JLabel> labelsDatos;

    public VistaDatos() {
        frame = new JFrame("Estación Meteorológica - Cliente UDP");
        frame.setSize(600, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        labelsDatos = new HashMap<>();

        // Panel superior para IP y puerto
        JPanel configPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración de Conexión"));

        textFieldIP = new JTextField("127.0.0.1"); // Valor predeterminado
        textFieldPuerto = new JTextField("9091");  // Valor predeterminado
        botonConectar = new JButton("Conectar");

        configPanel.add(new JLabel("IP del Servidor:"));
        configPanel.add(textFieldIP);
        configPanel.add(new JLabel("Puerto:"));
        configPanel.add(textFieldPuerto);

        frame.add(configPanel, BorderLayout.NORTH);

        // Panel central para mostrar datos
        datosPanel = new JPanel();
        datosPanel.setLayout(new BoxLayout(datosPanel, BoxLayout.Y_AXIS));
        datosPanel.setBorder(BorderFactory.createTitledBorder("Datos Meteorológicos"));

        // Sección para datos de temperatura y humedad
        temperaturaPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        temperaturaPanel.setBorder(BorderFactory.createTitledBorder("Temperatura y Humedad"));
        datosPanel.add(temperaturaPanel);

        // Sección para datos de viento
        vientoPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        vientoPanel.setBorder(BorderFactory.createTitledBorder("Condiciones de Viento"));
        datosPanel.add(vientoPanel);

        // Sección para datos de visibilidad y presión
        visibilidadPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        visibilidadPanel.setBorder(BorderFactory.createTitledBorder("Visibilidad y Presión"));
        datosPanel.add(visibilidadPanel);

        // Sección para condición actual
        condicionPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        condicionPanel.setBorder(BorderFactory.createTitledBorder("Condición Actual"));
        datosPanel.add(condicionPanel);

        frame.add(new JScrollPane(datosPanel), BorderLayout.CENTER);

        // Botón para conectar al servidor
        frame.add(botonConectar, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public String getIP() {
        return textFieldIP.getText();
    }

    public int getPuerto() {
        return Integer.parseInt(textFieldPuerto.getText());
    }

    public void setConectarListener(ActionListener listener) {
        botonConectar.addActionListener(listener);
    }

    public void mostrarDatos(String key, String value) {
        JLabel labelKey;
        JLabel labelValue;
        JPanel targetPanel = datosPanel;

        // Determinar en qué sección colocar los datos
        if (key.equalsIgnoreCase("Temperatura") || key.equalsIgnoreCase("Humedad") || key.equalsIgnoreCase("Punto de Rocío")) {
            targetPanel = temperaturaPanel;
        } else if (key.equalsIgnoreCase("Velocidad del Viento") || key.equalsIgnoreCase("Dirección del Viento") || key.equalsIgnoreCase("Índice UV")) {
            targetPanel = vientoPanel;
        } else if (key.equalsIgnoreCase("Visibilidad") || key.equalsIgnoreCase("Presión Atmosférica") || key.equalsIgnoreCase("Altitud de Nubosidad") || key.equalsIgnoreCase("Cantidad de Precipitación")) {
            targetPanel = visibilidadPanel;
        } else if (key.equalsIgnoreCase("Condición Actual")) {
            targetPanel = condicionPanel;
        }

        if (labelsDatos.containsKey(key)) {
            labelKey = labelsDatos.get(key + "_key");
            labelValue = labelsDatos.get(key);
            labelValue.setText(value);
        } else {
            labelKey = new JLabel(key + ":");
            labelValue = new JLabel(value);
            labelKey.setFont(new Font("Arial", Font.BOLD, 14));
            labelValue.setFont(new Font("Arial", Font.PLAIN, 14));
            labelKey.setHorizontalAlignment(SwingConstants.RIGHT);

            targetPanel.add(labelKey);
            targetPanel.add(labelValue);
            labelsDatos.put(key + "_key", labelKey);
            labelsDatos.put(key, labelValue);
        }

        // Cambiar el color dependiendo de la condición actual
        if (key.equalsIgnoreCase("Condición Actual")) {
            switch (value.toLowerCase()) {
                case "soleado":
                    condicionPanel.setBackground(Color.YELLOW);
                    break;
                case "nublado":
                    condicionPanel.setBackground(Color.LIGHT_GRAY);
                    break;
                case "lluvioso":
                    condicionPanel.setBackground(Color.CYAN);
                    break;
                case "tormenta":
                    condicionPanel.setBackground(Color.DARK_GRAY);
                    break;
                case "niebla":
                    condicionPanel.setBackground(Color.GRAY);
                    break;
                default:
                    condicionPanel.setBackground(Color.WHITE);
                    break;
            }
        }

        condicionPanel.revalidate();
        condicionPanel.repaint();
    }

    public void limpiarDatos() {
        temperaturaPanel.removeAll();
        vientoPanel.removeAll();
        visibilidadPanel.removeAll();
        condicionPanel.removeAll();
        labelsDatos.clear();
        datosPanel.setBackground(Color.WHITE); // Restablecer color de fondo
        datosPanel.revalidate();
        datosPanel.repaint();
    }
}
