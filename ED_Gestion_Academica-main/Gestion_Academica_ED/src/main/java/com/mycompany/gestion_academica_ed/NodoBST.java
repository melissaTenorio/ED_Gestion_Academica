/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoBST<T> {
    // dato almacenado
    T dato;
    // referencias a hijos
    NodoBST<T> izquierda;
    NodoBST<T> derecha;

    /**
     * constructor del nodo
     */
    public NodoBST(T dato) {
        this.dato = dato;
        izquierda = null;
        derecha = null;
    }
    
}
