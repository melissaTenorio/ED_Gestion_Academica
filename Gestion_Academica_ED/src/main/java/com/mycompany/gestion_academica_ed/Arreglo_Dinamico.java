package com.mycompany.gestion_academica_ed;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author melis
 */
public class Arreglo_Dinamico {
    private Object [] arregloIn;
    private int capacidad;
    private int contador;

    public Arreglo_Dinamico() {
        capacidad = 10;
        contador = 0;
        arregloIn = new Object[capacidad];
    }
    
    public Arreglo_Dinamico(Object[] arregloIn, int capacidad, int contador) {
        this.arregloIn = arregloIn;
        this.capacidad = capacidad;
        this.contador = contador;
    }
    
    public void agregar(Object dato){
        if(contador == capacidad){
            redimensionar();
        }
        arregloIn[contador] = dato;
        contador++;
    }
    
    public Object obtener(int indice){
        if (indice < 0 || indice >= contador) {
            throw new IndexOutOfBoundsException("El indice esta fuera de rango.");
        }
        return arregloIn[indice];
    }
    
    /**
     * este metodo es para aumentar el tamaño 
     */
    public void redimensionar(){
        int nuevaCapacidad = capacidad * 2;
        Object [] nuevoArreglo = new Object[nuevaCapacidad];
            //duplica/copia los elementos
            for (int i = 0; i < contador; i++) {
                nuevoArreglo[i] = arregloIn[i];
            }
            arregloIn = nuevoArreglo;
            capacidad = nuevaCapacidad;
    }
    
    public int obtenerTamaño(){
        return contador;
    }
    
    /**
     * para eliminar un curso se debe restar del contador
     * tambien
     */
    public void restaContador(){
        if(contador > 0){
            contador--;
        }
    }
    
    
    
}
