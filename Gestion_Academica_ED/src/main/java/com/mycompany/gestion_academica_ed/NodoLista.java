/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class NodoLista<T> {
    T dato;
    NodoLista<T> siguiente;

    public NodoLista(T dato) {
        this.dato = dato;
        this.siguiente = null;
    }

    public T getDato() { 
        return dato; 
    }
    public NodoLista<T> getSiguiente() { 
        return siguiente; 
    }
    public void setSiguiente(NodoLista<T> siguiente) { 
        this.siguiente = siguiente; 
    }
    
}
