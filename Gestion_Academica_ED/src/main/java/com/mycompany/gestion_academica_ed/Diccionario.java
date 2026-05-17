/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Diccionario<K, V> {
    private ListaEnlazadaSimple<EntradaHash<K, V>>[] tabla;
    private int capacidad;

    @SuppressWarnings("unchecked")
    public Diccionario(int capacidad) {
        this.capacidad = capacidad;
        this.tabla = new ListaEnlazadaSimple[capacidad];
        for (int i = 0; i < capacidad; i++) {
            tabla[i] = new ListaEnlazadaSimple<>();
        }
    }

    private int generarIndice(K llave) {
        return Math.abs(llave.hashCode() % capacidad);
    }

    public void insertar(K llave, V valor) {
        int indice = generarIndice(llave);
        // Verificar si la llave ya existe para actualizar
        NodoLista<EntradaHash<K, V>> temp = tabla[indice].getCabeza();
        while (temp != null) {
            if (temp.dato.llave.equals(llave)) {
                temp.dato.valor = valor;
                return;
            }
            temp = temp.getSiguiente();
        }
        tabla[indice].agregarAlFinal(new EntradaHash<>(llave, valor));
    }

    public V obtener(K llave) {
        int indice = generarIndice(llave);
        NodoLista<EntradaHash<K, V>> temp = tabla[indice].getCabeza();
        while (temp != null) {
            if (temp.dato.llave.equals(llave)) {
                return temp.dato.valor;
            }
            temp = temp.getSiguiente();
        }
        return null;
    }

    public boolean eliminar(K llave) {
        int indice = generarIndice(llave);
        NodoLista<EntradaHash<K, V>> temp = tabla[indice].getCabeza();
        while (temp != null) {
            if (temp.dato.llave.equals(llave)) {
                return tabla[indice].eliminar(temp.dato);
            }
            temp = temp.getSiguiente();
        }
        return false;
    }

    public Arreglo_Dinamico obtenerTodosLosValores() {
        Arreglo_Dinamico lista = new Arreglo_Dinamico();
        for (int i = 0; i < capacidad; i++) {
            NodoLista<EntradaHash<K, V>> temp = tabla[i].getCabeza();
            while (temp != null) {
                lista.agregar(temp.dato.valor);
                temp = temp.getSiguiente();
            }
        }
        return lista;
    }
    
}
