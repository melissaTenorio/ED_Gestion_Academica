/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class PilaGenerica<T> {
    private NodoGenerico<T> cima;

    /**
     * constructor
     */
    public PilaGenerica() {
        cima = null;
    }

    /**
     * push
     * agrega elemento a la pila
     */
    public void push(T dato) {
        NodoGenerico<T> nuevo = new NodoGenerico<>(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
    }

    /**
     * pop
     * elimina elemento superior
     */
    public T pop() {
        if (cima == null) {
            return null;
        }

        T dato = cima.dato;
        cima = cima.siguiente;
        return dato;
    }

    /**
     * verifica si esta vacia
     */
    public boolean estaVacia() {
        return cima == null;
    }
    
}
