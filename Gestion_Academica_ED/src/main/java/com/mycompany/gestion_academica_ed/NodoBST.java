/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoBST {
    // aqui se guarda el estudiante
    Estudiante estudiante;

    // referencias a los hijos
    NodoBST izquierda;
    NodoBST derecha;

    /**
     * constructor del nodo
     * recibe un estudiante y lo guarda en el nodo
     */
    public NodoBST(Estudiante estudiante) {
        this.estudiante = estudiante;
        // al inicio los hijos apuntan a null
        izquierda = null;
        derecha = null;
    }
    
}
