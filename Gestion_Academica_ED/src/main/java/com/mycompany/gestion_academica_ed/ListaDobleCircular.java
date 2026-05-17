/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ListaDobleCircular<T> {
    private NodoDoble<T> cabeza;
    private int tamaño;

    /**
     * constructor
     */
    public ListaDobleCircular() {
        cabeza = null;
        tamaño = 0;
    }

    /**
     * agregar al final
     */
    public void agregar(T dato) {

        NodoDoble<T> nuevo = new NodoDoble<>(dato);

        // si esta vacia
        if (cabeza == null) {
            cabeza = nuevo;
            cabeza.siguiente = cabeza;
            cabeza.anterior = cabeza;

        } else {
            NodoDoble<T> ultimo = cabeza.anterior;
            ultimo.siguiente = nuevo;
            nuevo.anterior = ultimo;
            nuevo.siguiente = cabeza;
            cabeza.anterior = nuevo;
        }
        tamaño++;
    }

    /**
     * recorrer hacia adelante
     */
    public Arreglo_Dinamico recorrerAdelante() {
        Arreglo_Dinamico lista = new Arreglo_Dinamico();
        if (cabeza == null) {
            return lista;
        }
        NodoDoble<T> temp = cabeza;
        do {
            lista.agregar(temp.dato);
            temp = temp.siguiente;
        } while (temp != cabeza);
        return lista;
    }

    /**
     * recorrer hacia atras
     */
    public Arreglo_Dinamico recorrerAtras() {
        Arreglo_Dinamico lista = new Arreglo_Dinamico();
        if (cabeza == null) {
            return lista;
        }

        NodoDoble<T> temp = cabeza.anterior;

        do {
            lista.agregar(temp.dato);
            temp = temp.anterior;
        } while (temp != cabeza.anterior);
        return lista;
    }

    /**
     * mostrar primeros n elementos
     */
    public Arreglo_Dinamico mostrarPrimeros(int n) {

        Arreglo_Dinamico lista =
                new Arreglo_Dinamico();

        if (cabeza == null) {
            return lista;
        }
        
        NodoDoble<T> temp = cabeza;
        int contador = 0;
        do {
            lista.agregar(temp.dato);
            temp = temp.siguiente;
            contador++;
        } while (temp != cabeza && contador < n);
        return lista;
    }

    public int getTamaño() {
        return tamaño;
    }
        
}


    
