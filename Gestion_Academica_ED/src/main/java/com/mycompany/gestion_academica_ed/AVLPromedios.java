/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class AVLPromedios {
    // raiz del arbol AVL
    private NodoAVL raiz;

    /**
     * metodo para insertar estudiantes
     * usando el promedio como clave
     */
    public void insertar(double promedio, Estudiante estudiante) {
        raiz = insertarRecursivo(raiz, promedio, estudiante);
    }

    /**
     * metodo recursivo para insertar
     * y balancear el arbol AVL
     */
    private NodoAVL insertarRecursivo(NodoAVL nodo, double promedio, Estudiante estudiante) {
        // si el nodo esta vacio
        if (nodo == null) {
            return new NodoAVL(promedio, estudiante);
        }

        // insertar izquierda
        if (promedio < nodo.promedio) {
            nodo.izquierda = insertarRecursivo(nodo.izquierda, promedio, estudiante);
        } else {
            // insertar derecha
            nodo.derecha = insertarRecursivo(nodo.derecha, promedio, estudiante);
        }

        /**
         * actualizar altura
         */
        nodo.altura = 1 + Math.max(altura(nodo.izquierda), altura(nodo.derecha));

        /**
         * calcular balance
         */
        int balance = obtenerBalance(nodo);

        /**
         * ROTACIONES AVL
         */

        // izquierda izquierda
        if (balance > 1 && promedio < nodo.izquierda.promedio) {
            return rotarDerecha(nodo);
        }

        // derecha derecha
        if (balance < -1 && promedio > nodo.derecha.promedio) {
            return rotarIzquierda(nodo);
        }

        // izquierda derecha
        if (balance > 1 && promedio > nodo.izquierda.promedio) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // derecha izquierda
        if (balance < -1 && promedio < nodo.derecha.promedio) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }
        return nodo;
    }

    /**
     * obtiene altura del nodo
     */
    private int altura(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    /**
     * calcula balance del nodo
     */
    private int obtenerBalance(NodoAVL nodo) {
        if (nodo == null) {
            return 0;
        }
        return altura(nodo.izquierda) - altura(nodo.derecha);
    }

    /**
     * rotacion simple derecha
     */
    private NodoAVL rotarDerecha(NodoAVL y) {
        NodoAVL x = y.izquierda;
        NodoAVL t2 = x.derecha;

        x.derecha = y;
        y.izquierda = t2;

        // actualizar alturas
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        
        return x;
    }

    /**
     * rotacion simple izquierda
     */
    private NodoAVL rotarIzquierda(NodoAVL x) {
        NodoAVL y = x.derecha;
        NodoAVL t2 = y.izquierda;

        y.izquierda = x;
        x.derecha = t2;

        // actualizar alturas
        x.altura = Math.max(altura(x.izquierda), altura(x.derecha)) + 1;
        y.altura = Math.max(altura(y.izquierda), altura(y.derecha)) + 1;
        return y;
    }

    /**
     * getter de raiz
     */
    public NodoAVL getRaiz() {
        return raiz;
    }
    
    /**
    * recorrido inorder
    * guarda estudiantes ordenados
    * en un arreglo dinamico
    */
   public Arreglo_Dinamico inOrden(){
       Arreglo_Dinamico listaOrdenada = new Arreglo_Dinamico();
       inOrdenRecursivo(raiz, listaOrdenada);
       return listaOrdenada;
   }

   /**
    * recorrido recursivo inorder
    */
   private void inOrdenRecursivo(NodoAVL nodo, Arreglo_Dinamico lista){
       if(nodo != null){
           // izquierda
           inOrdenRecursivo(nodo.izquierda, lista);

           // guardar estudiante
           lista.agregar(nodo.estudiante);

           // derecha
           inOrdenRecursivo(nodo.derecha, lista);
       }
   }

}
