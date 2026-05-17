/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ColaGenerica<T> {
    private NodoGenerico<T> frente;
    private NodoGenerico<T> finalCola;

    /**
     * constructor
     */
    public ColaGenerica() {
        frente = null;
        finalCola = null;
    }

    /**
     * enqueue
     * agrega elemento al final
     */
    public void enqueue(T dato) {
        NodoGenerico<T> nuevo = new NodoGenerico<>(dato);

        // cola vacia
        if (frente == null) {
            frente = nuevo;
            finalCola = nuevo;

        } else {
            finalCola.siguiente = nuevo;
            finalCola = nuevo;
        }
    }
    
    /**
     * dequeue
     * elimina el primer elemento
     */
    public T dequeue() {
        if (frente == null) {
            return null;
        }

        T dato = frente.dato;
        frente = frente.siguiente;

        // si queda vacia
        if (frente == null) {
            finalCola = null;
        }
        return dato;
    }

    /**
     * verifica si esta vacia
     */
    public boolean estaVacia() {
        return frente == null;
    }
    
    
}
