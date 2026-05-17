/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ListaEnlazadaSimple<T> {
    private NodoLista<T> cabeza;
    private int tamaño;

    public ListaEnlazadaSimple() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    public void agregarAlFinal(T dato) {
        NodoLista<T> nuevo = new NodoLista<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            NodoLista<T> temp = cabeza;
            while (temp.getSiguiente() != null) {
                temp = temp.getSiguiente();
            }
            temp.setSiguiente(nuevo);
        }
        tamaño++;
    }

    public boolean eliminar(T dato) {
        if (cabeza == null) return false;
        if (cabeza.dato.equals(dato)) {
            cabeza = cabeza.getSiguiente();
            tamaño--;
            return true;
        }
        NodoLista<T> temp = cabeza;
        while (temp.getSiguiente() != null && !temp.getSiguiente().dato.equals(dato)) {
            temp = temp.getSiguiente();
        }
        if (temp.getSiguiente() != null) {
            temp.setSiguiente(temp.getSiguiente().getSiguiente());
            tamaño--;
            return true;
        }
        return false;
    }

    public NodoLista<T> getCabeza() { 
        return cabeza; 
    }
    public int getTamaño() { 
        return tamaño; 
    }

    public boolean estaVacia() { 
        return cabeza == null; }
    
}
