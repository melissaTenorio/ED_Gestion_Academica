/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoCircular<T> {
    T dato;
    NodoCircular<T> siguiente;

    public NodoCircular(T dato) {
        this.dato = dato;
        siguiente = null;
    }
    
}
