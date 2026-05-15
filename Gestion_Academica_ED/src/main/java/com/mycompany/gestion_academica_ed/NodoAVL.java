/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoAVL {
    // promedio del estudiante
    double promedio;

    // referencia al estudiante
    Estudiante estudiante;

    // hijos
    NodoAVL izquierda;
    NodoAVL derecha;

    // altura del nodo
    int altura;

    /**
     * constructor del nodo AVL
     */
    public NodoAVL(double promedio, Estudiante estudiante) {
        this.promedio = promedio;
        this.estudiante = estudiante;
        altura = 1;
    }
    
}
