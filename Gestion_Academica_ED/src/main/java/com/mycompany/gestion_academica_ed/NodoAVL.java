/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoAVL<T> {
    double clave;
    T dato;
    NodoAVL<T> izquierda;
    NodoAVL<T> derecha;
    int altura;

    /**
     * constructor
     */
    public NodoAVL(double clave, T dato) {
        this.clave = clave;
        this.dato = dato;
        altura = 1;
    }
}
