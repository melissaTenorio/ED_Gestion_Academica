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
    }
    
    public Arreglo_Dinamico(Object[] arregloIn, int capacidad, int contador) {
        this.arregloIn = new Object[capacidad];
        this.capacidad = 10;
        this.contador = 0;
    }
    
    public void agregar(Object dato){
    if(contador == capacidad){redimensionar();}
    arregloIn[contador]=dato;
    contador++;
    }
    
    public Object obtener(int indice){
        if (indice<0||indice>=contador) {
            throw new IndexOutOfBoundsException("El indice esta fuewra de rango.");
        } return arregloIn[indice];
    }
    
    public void redimensionar(){
    /**
     * este metodo es para aumentar el tamaño 
     */
    int nuevaCapacidad=capacidad*2;
    Object [] nuevoArreglo = new Object[nuevaCapacidad];
    //duplica/copia los elementos
        for (int i = 0; i < contador; i++) {
            nuevoArreglo[i]=arregloIn[i];
        }
    }
    
    public int obtenerTamaño(){return contador;}
    
    /**
     * para eliminar un curso se debe restar del contador
     * tambien
     */
    public void restaContador(){
    
    }
}
