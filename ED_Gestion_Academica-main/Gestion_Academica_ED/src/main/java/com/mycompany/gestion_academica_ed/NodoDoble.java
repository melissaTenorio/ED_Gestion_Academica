/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoDoble<T> {
    T dato;

    NodoDoble<T> siguiente;
    NodoDoble<T> anterior;

    /**
     * constructor del nodo doble
     */
    public NodoDoble(T dato) {

        this.dato = dato;

        siguiente = null;
        anterior = null;
    }
    
}
