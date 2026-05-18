package com.mycompany.gestion_academica_ed;

import com.mycompany.gestion_academica_ed.ui.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Gestion_Academica_ED {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaPrincipal::new);
    }
}
