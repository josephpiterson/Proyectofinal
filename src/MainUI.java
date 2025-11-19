import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

// Interfaz gráfica más completa basada en la imagen proporcionada.
public class MainUI extends JFrame {
    private static final String ORIGEN = "Costa Rica";
    private static final String[] DESTINOS = new String[] {
        // 8 países que requieren visa (según Vuelo.COUNTRIES_REQUIRING_VISA)
        "Estados Unidos", "China", "Rusia", "India", "Reino Unido", "Francia", "Canada", "Australia",
        // 8 países que NO requieren visa (según Vuelo.COUNTRIES_NO_VISA)
        "Mexico", "Panama", "Argentina", "Colombia", "Peru", "Brasil", "Chile", "Uruguay"
    };

    // Campos personales
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField documentoField;
    private JTextField emailField;
    private JTextField nacionalidadField;

    // Campos vuelo
    private JLabel origenLabel;
    private JComboBox<String> destinoCombo;
    private JComboBox<String> claseCombo;
    private JSpinner cantidadSpinner;
    private JCheckBox visaCheck;
    private JSpinner fechaSpinner;
    private JComboBox<String> vuelosDisponiblesCombo;

    // Asientos
    private List<JButton> ejecutivaButtons = new ArrayList<>();
    private List<JButton> economicaButtons = new ArrayList<>();

    public MainUI() {
        super("Reservaciones - Interfaz");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(8,8));

        // Panel principal dividido horizontalmente
        JPanel center = new JPanel(new BorderLayout(8,8));

        // LEFT: Datos de Vuelo
        JPanel vueloPanel = new JPanel();
        vueloPanel.setBorder(new TitledBorder("Datos de Vuelo"));
        vueloPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; vueloPanel.add(new JLabel("Origen:"), c);
        c.gridx = 1; origenLabel = new JLabel(ORIGEN); vueloPanel.add(origenLabel, c);

        c.gridx = 0; c.gridy = 1; vueloPanel.add(new JLabel("Destino:"), c);
        c.gridx = 1; destinoCombo = new JComboBox<>(DESTINOS); vueloPanel.add(destinoCombo, c);

        c.gridx = 0; c.gridy = 2; vueloPanel.add(new JLabel("Clase:"), c);
        c.gridx = 1; claseCombo = new JComboBox<>(new String[]{"ECONOMICA","EJECUTIVA"}); vueloPanel.add(claseCombo, c);

        c.gridx = 0; c.gridy = 3; vueloPanel.add(new JLabel("Cantidad asientos:"), c);
        c.gridx = 1; cantidadSpinner = new JSpinner(new SpinnerNumberModel(1,1,10,1)); vueloPanel.add(cantidadSpinner, c);

        c.gridx = 0; c.gridy = 4; vueloPanel.add(new JLabel("Este destino REQUIERE visa:"), c);
        c.gridx = 1; visaCheck = new JCheckBox("Tengo visa"); vueloPanel.add(visaCheck, c);

        c.gridx = 0; c.gridy = 5; vueloPanel.add(new JLabel("Fecha de vuelo:"), c);
        c.gridx = 1; fechaSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        fechaSpinner.setEditor(new JSpinner.DateEditor(fechaSpinner, "yyyy-MM-dd"));
        vueloPanel.add(fechaSpinner, c);

        c.gridx = 0; c.gridy = 6; vueloPanel.add(new JLabel("Vuelos disponibles:"), c);
        c.gridx = 1; vuelosDisponiblesCombo = new JComboBox<>(); vueloPanel.add(vuelosDisponiblesCombo, c);

        c.gridx = 0; c.gridy = 7; c.gridwidth = 2;
        JButton verificarBtn = new JButton("Verificar Disponibilidad");
        vueloPanel.add(verificarBtn, c);

        // RIGHT: Datos personales (mejor alineados y con validaciones)
        JPanel personalPanel = new JPanel(new GridBagLayout());
        personalPanel.setBorder(new CompoundBorder(new TitledBorder("Datos Personales"), new EmptyBorder(12,12,12,12)));
        personalPanel.setPreferredSize(new Dimension(360, 220));
        GridBagConstraints pc = new GridBagConstraints();
        pc.insets = new Insets(4,4,4,4);
        pc.anchor = GridBagConstraints.WEST;
        pc.fill = GridBagConstraints.HORIZONTAL;
        pc.weightx = 0.0;

        pc.gridx = 0; pc.gridy = 0; JLabel ln = new JLabel("Nombre:"); personalPanel.add(ln, pc);
        pc.gridx = 1; pc.weightx = 1.0; nombreField = new JTextField(); nombreField.setColumns(18); nombreField.setToolTipText("Ingrese el nombre"); personalPanel.add(nombreField, pc);

        pc.gridx = 0; pc.gridy = 1; pc.weightx = 0.0; JLabel la = new JLabel("Apellidos:"); personalPanel.add(la, pc);
        pc.gridx = 1; pc.weightx = 1.0; apellidosField = new JTextField(); apellidosField.setColumns(18); apellidosField.setToolTipText("Ingrese los apellidos"); personalPanel.add(apellidosField, pc);

        pc.gridx = 0; pc.gridy = 2; pc.weightx = 0.0; JLabel ld = new JLabel("Documento:"); personalPanel.add(ld, pc);
        pc.gridx = 1; pc.weightx = 1.0; documentoField = new JTextField(); documentoField.setColumns(18); documentoField.setToolTipText("Sólo dígitos"); personalPanel.add(documentoField, pc);

        pc.gridx = 0; pc.gridy = 3; pc.weightx = 0.0; JLabel le = new JLabel("Email:"); personalPanel.add(le, pc);
        pc.gridx = 1; pc.weightx = 1.0; emailField = new JTextField(); emailField.setColumns(18); emailField.setToolTipText("ejemplo@dominio.com"); personalPanel.add(emailField, pc);

        pc.gridx = 0; pc.gridy = 4; pc.weightx = 0.0; JLabel lnac = new JLabel("Nacionalidad:"); personalPanel.add(lnac, pc);
        pc.gridx = 1; pc.weightx = 1.0; nacionalidadField = new JTextField(); nacionalidadField.setColumns(18); nacionalidadField.setToolTipText("Ingrese la nacionalidad"); personalPanel.add(nacionalidadField, pc);

        // ajustar peso para que no colapse el panel izquierdo
        pc.gridx = 0; pc.gridy = 5; pc.weighty = 1.0; pc.gridwidth = 2; personalPanel.add(Box.createVerticalGlue(), pc);

        center.add(vueloPanel, BorderLayout.WEST);
        center.add(personalPanel, BorderLayout.EAST);

        add(center, BorderLayout.NORTH);

        // PANEL ASIENTOS
        JPanel seatsPanel = new JPanel(new BorderLayout(6,6));
        seatsPanel.setBorder(new TitledBorder("Asientos"));

        // Ejecutiva
        JPanel execPanel = new JPanel();
        execPanel.setBorder(new TitledBorder("Ejecutivos"));
        execPanel.setLayout(new GridLayout(0, 5, 6, 6)); // Cambiar a 10 asientos
        for (int i = 1; i <= 10; i++) { // Cambiar de 5 a 10
            String id = "E" + i;
            JButton b = createSeatButton(id);
            b.setPreferredSize(new Dimension(44, 36));
            b.setMargin(new Insets(2, 2, 2, 2));
            ejecutivaButtons.add(b);
            execPanel.add(b);
        }

        // Economica (dentro de un scroll para mostrar todos los asientos si hace falta)
        JPanel econPanel = new JPanel();
        econPanel.setBorder(new TitledBorder("Economica"));
        // Mostrar asientos como pequeños cuadrados agrupados en varias columnas
        int cols = 6; // número de columnas en la cuadrícula
        econPanel.setLayout(new GridLayout(0, cols, 6, 6));
        for (int i = 1; i <= 30; i++) {
            String id = "C" + i;
            JButton b = createSeatButton(id);
            // tamaño pequeño y cuadrado
            b.setPreferredSize(new Dimension(44, 36));
            b.setMargin(new Insets(2, 2, 2, 2));
            economicaButtons.add(b);
            econPanel.add(b);
        }

        // Scroll para mostrar la cuadrícula completa (ambos ejes si es necesario)
        JScrollPane econScroll = new JScrollPane(econPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        econScroll.setPreferredSize(new Dimension(520, 160));

        JPanel both = new JPanel(new GridLayout(2,1));
        both.add(execPanel);
        both.add(econScroll);
        seatsPanel.add(both, BorderLayout.CENTER);

        JPanel bottomButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton autoBtn = new JButton("Auto-asignar");
        JButton confirmarBtn = new JButton("Confirmar selección");
        bottomButtons.add(autoBtn); bottomButtons.add(confirmarBtn);
        seatsPanel.add(bottomButtons, BorderLayout.SOUTH);

        add(seatsPanel, BorderLayout.CENTER);

        // Eventos
        verificarBtn.addActionListener(e -> verificarDisponibilidad());
        // Actualizar lista de vuelos disponibles al cambiar el destino
        destinoCombo.addActionListener(e -> updateVuelosDisponibles());
        autoBtn.addActionListener(e -> autoAsignar());
        confirmarBtn.addActionListener(e -> confirmarSeleccion());

    }

    private void updateVuelosDisponibles() {
        String destino = (String) destinoCombo.getSelectedItem();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        if (destino == null) {
            vuelosDisponiblesCombo.setModel(model);
            return;
        }

        // Mostrar sólo el destino seleccionado. Si no requiere visa, indicar "No ocupa visa".
        if (Vuelo.noVisaFor(destino)) {
            model.addElement(destino + " (No ocupa visa)");
        } else if (Vuelo.requiresVisaFor(destino)) {
            String tipo = Vuelo.getTipoVisaFor(destino);
            if (tipo != null) model.addElement(destino + " (Requiere visa: " + tipo + ")");
            else model.addElement(destino + " (Requiere visa)");
        } else {
            // Si no está en ninguna lista, mostrar destino sin nota
            model.addElement(destino);
        }

        vuelosDisponiblesCombo.setModel(model);
    }

    private JButton createSeatButton(String id) {
        JButton b = new JButton(id);
        b.setPreferredSize(new Dimension(50,32));
        b.setBackground(Color.LIGHT_GRAY);
        b.addActionListener(ev -> {
            // Alterna selección
            if (b.getBackground() == Color.GREEN) {
                b.setBackground(Color.LIGHT_GRAY);
            } else {
                // sólo permitir selección de acuerdo a clase seleccionada
                String clase = (String) claseCombo.getSelectedItem();
                if (clase.equals("EJECUTIVA") && id.startsWith("C")) {
                    JOptionPane.showMessageDialog(this, "Cambia la clase a ECONOMICA para seleccionar asientos C.", "Clase/Asiento", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                if (clase.equals("ECONOMICA") && id.startsWith("E")) {
                    JOptionPane.showMessageDialog(this, "Cambia la clase a EJECUTIVA para seleccionar asientos E.", "Clase/Asiento", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                // Evitar seleccionar más asientos de los pedidos
                int max = (Integer) cantidadSpinner.getValue();
                int selectedCount = 0;
                for (JButton bb : ejecutivaButtons) if (bb.getBackground() == Color.GREEN) selectedCount++;
                for (JButton bb : economicaButtons) if (bb.getBackground() == Color.GREEN) selectedCount++;
                if (selectedCount >= max) {
                    JOptionPane.showMessageDialog(this, "Ya seleccionaste la cantidad máxima de asientos solicitada.", "Máximo alcanzado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                b.setBackground(Color.GREEN);
            }
        });
        return b;
    }

    private void verificarDisponibilidad() {
        // Simplemente muestra una dialog con información simulada
        String destino = (String) destinoCombo.getSelectedItem();
        String vuelo = (String) vuelosDisponiblesCombo.getSelectedItem();
        JOptionPane.showMessageDialog(this, "Vuelos disponibles para " + destino + ":\n" + vuelo, "Disponibilidad", JOptionPane.INFORMATION_MESSAGE);
    }

    private void autoAsignar() {
        int cantidad = (Integer) cantidadSpinner.getValue();
        String clase = (String) claseCombo.getSelectedItem();
        List<JButton> pool = clase.equals("EJECUTIVA") ? ejecutivaButtons : economicaButtons;
        int assigned = 0;
        for (JButton b : pool) {
            if (assigned >= cantidad) break;
            if (b.getBackground() != Color.GREEN) {
                b.setBackground(Color.GREEN);
                assigned++;
            }
        }
        if (assigned < cantidad) {
            JOptionPane.showMessageDialog(this, "No hay suficientes asientos libres para auto-asignar.", "Atención", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void confirmarSeleccion() {
        // Validar datos personales
        String nombre = nombreField.getText().trim();
        String apellidos = apellidosField.getText().trim();
        String documento = documentoField.getText().trim();
        String email = emailField.getText().trim();
        String nacionalidad = nacionalidadField.getText().trim();

        if (nombre.isEmpty() || apellidos.isEmpty() || documento.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los datos personales obligatorios.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validaciones sencillas
        if (!documento.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "El campo Documento debe contener sólo dígitos.", "Documento inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String emailRegex = "^[\\w.%+\\-]+@[\\w.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            JOptionPane.showMessageDialog(this, "Ingrese un email con formato válido.", "Email inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Contar asientos seleccionados
        List<String> seleccion = new ArrayList<>();
        for (JButton b : ejecutivaButtons) if (b.getBackground() == Color.GREEN) seleccion.add(b.getText());
        for (JButton b : economicaButtons) if (b.getBackground() == Color.GREEN) seleccion.add(b.getText());

        int cantidad = (Integer) cantidadSpinner.getValue();
        if (seleccion.size() > cantidad) {
            JOptionPane.showMessageDialog(this, "Ha seleccionado más asientos de los pedidos. Ajuste la selección.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (seleccion.size() < cantidad) {
            int res = JOptionPane.showConfirmDialog(this, "La cantidad de asientos seleccionados ("+seleccion.size()+") es menor que la cantidad requerida ("+cantidad+"). ¿Desea continuar?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (res != JOptionPane.YES_OPTION) return;
        }

        // Crear objetos del modelo y llamar a Reservacion
        Avion avion = new Avion("Boeing-737");
        String vueloSel = (String) vuelosDisponiblesCombo.getSelectedItem();
        double precio = 300.0;
        if (vueloSel != null) {
            try {
                // intenta extraer precio del texto del combo
                int p1 = vueloSel.indexOf('(');
                int p2 = vueloSel.indexOf(')');
                if (p1>=0 && p2>p1) {
                    String inside = vueloSel.substring(p1+1,p2);
                    inside = inside.replace("$","" ).replace(" ","");
                    precio = Double.parseDouble(inside);
                }
            } catch (NumberFormatException ex) { /* ignore, usar precio por defecto */ }
        }

        String destino = (String) destinoCombo.getSelectedItem();
        String clase = (String) claseCombo.getSelectedItem();
        Pasajero pasajero = new Pasajero(nombre, apellidos, documento, nacionalidad, email);
        Vuelo vuelo = new Vuelo("V-XXX", ORIGEN, destino, precio, precio*0.2, avion);

        Reservacion.Resultado r = Reservacion.reservar(vuelo, pasajero, clase, cantidad);
        if (!r.exito) {
            JOptionPane.showMessageDialog(this, "No fue posible crear la reservación: " + r.mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Añade selección de asientos al tiquete (si existe campo toString)
        StringBuilder info = new StringBuilder();
        info.append("Reservación exitosa!\n");
        info.append(r.tiquete).append("\n");
        info.append("Asientos: ").append(seleccion).append("\n\n");
        info.append(r.factura.generarTexto());

        JOptionPane.showMessageDialog(this, info.toString(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Guardar factura
        Path f = Path.of("facturas.txt");
        String sep = "------------------------------\n";
        try {
            Files.writeString(f, sep + r.factura.generarTexto() + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la factura: " + ex.getMessage(), "I/O", JOptionPane.WARNING_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainUI ui = new MainUI();
            ui.setVisible(true);
        });
    }
}
