/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ListaCircularSimple<T> {
    private NodoCircular<T> cabeza;
    private NodoCircular<T> actual;

    /**
     * agregar elementos
     */
    public void agregar(T dato) {

        NodoCircular<T> nuevo = new NodoCircular<>(dato);

        // lista vacia
        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.siguiente = cabeza;
            actual = cabeza;
        } else {
            NodoCircular<T> temp = cabeza;
            while (temp.siguiente != cabeza) {
                temp = temp.siguiente;
            }

            temp.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }
    }

    /**
     * rota al siguiente elemento
     */
    public T rotar() {
        if (actual == null) {
            return null;
        }

        actual = actual.siguiente;
        return actual.dato;
    }
    
    
}
