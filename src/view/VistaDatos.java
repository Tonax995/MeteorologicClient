package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaDatos {
    private JFrame frame;
    private JTextArea textArea;
    private JTextField textFieldIP;
    private JTextField textFieldPuerto;
    private JButton botonConectar;
    private JPanel datosPanel;

    public VistaDatos() {
        frame = new JFrame("Estación Meteorológica - Cliente UDP");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Panel superior para IP y puerto
        JPanel configPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        configPanel.setBorder(BorderFactory.createTitledBorder("Configuración de Conexión"));

        textFieldIP = new JTextField("127.0.0.1"); // Valor predeterminado
        textFieldPuerto = new JTextField("9090");  // Valor predeterminado
        botonConectar = new JButton("Conectar");

        configPanel.add(new JLabel("IP del Servidor:"));
        configPanel.add(textFieldIP);
        configPanel.add(new JLabel("Puerto:"));
        configPanel.add(textFieldPuerto);

        frame.add(configPanel, BorderLayout.NORTH);

        // Panel central para mostrar datos
        datosPanel = new JPanel();
        datosPanel.setLayout(new GridLayout(0, 2, 10, 10));
        datosPanel.setBorder(BorderFactory.createTitledBorder("Datos Meteorológicos"));
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
        JLabel labelKey = new JLabel(key + ":");
        JLabel labelValue = new JLabel(value);
        labelKey.setFont(new Font("Arial", Font.BOLD, 14));
        labelValue.setFont(new Font("Arial", Font.PLAIN, 14));
        labelKey.setHorizontalAlignment(SwingConstants.RIGHT);

        datosPanel.add(labelKey);
        datosPanel.add(labelValue);
        datosPanel.revalidate();
        datosPanel.repaint();
    }

    public void limpiarDatos() {
        datosPanel.removeAll();
    }
}
