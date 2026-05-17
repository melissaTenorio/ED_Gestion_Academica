/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class BSTEstudiantes {
    // raiz principal del arbol
    private NodoBST<Estudiante> raiz;

    /**
     * constructor del BST
     */
    public BSTEstudiantes() {
        raiz = null;
    }

    /**
     * metodo para insertar estudiantes
     * en el arbol binario de busqueda
     */
    public void insertar(Estudiante estudiante) {
        raiz = insertarRecursivo(raiz, estudiante);
    }

    /**
     * metodo recursivo para insertar
     * estudiantes comparando matriculas
     */
    private NodoBST<Estudiante> insertarRecursivo(NodoBST<Estudiante> actual, Estudiante estudiante) {
        /**
         * si el nodo esta vacio
         * se crea uno nuevo
         */
        if (actual == null) {
            return new NodoBST<>(estudiante);
        }

        /**
         * comparar matriculas
         */

        // insertar izquierda
        if (estudiante.getMatricula().compareTo(actual.dato.getMatricula()) < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, estudiante);
        } else if (estudiante.getMatricula().compareTo(actual.dato.getMatricula()) > 0) {
            // insertar derecha
            actual.derecha = insertarRecursivo(actual.derecha,estudiante);
        } else {
            /**
             * matricula repetida
             * no insertar
             */
            return actual;
        }
        return actual;
    }

    /**
     * metodo para buscar estudiantes
     * usando matricula
     */
    public Estudiante buscar(String matricula) {
        return buscarRecursivo(raiz, matricula);
    }

    /**
     * metodo recursivo de busqueda
     */
    private Estudiante buscarRecursivo(NodoBST<Estudiante> actual, String matricula) {
        /**
         * si llega a null
         * el estudiante no existe
         */
        if (actual == null) {
            return null;
        }

        /**
         * si encuentra la matricula
         * retorna el estudiante
         */
        if (actual.dato.getMatricula().equals(matricula)) {
            return actual.dato;
        }

        /**
         * si la matricula es menor
         * buscar izquierda
         */
        if (matricula.compareTo(actual.dato.getMatricula()) < 0) {
            return buscarRecursivo(actual.izquierda, matricula);
        }

        /**
         * si es mayor
         * buscar derecha
         */
        return buscarRecursivo(actual.derecha, matricula);
    }

    /**
     * obtiene todos los estudiantes
     * en orden por matricula
     */
    public Arreglo_Dinamico obtenerEstudiantes() {
        Arreglo_Dinamico lista = new Arreglo_Dinamico();
        
        inOrdenRecursivo(raiz, lista);
        return lista;
    }

    /**
     * recorrido inorder recursivo
     */
    private void inOrdenRecursivo(NodoBST<Estudiante> nodo, Arreglo_Dinamico lista) {
        if (nodo != null) {
            // izquierda
            inOrdenRecursivo(nodo.izquierda, lista);

            // guardar estudiante
            lista.agregar(nodo.dato);

            // derecha
            inOrdenRecursivo(nodo.derecha, lista);
        }
    }

    /**
     * getter de la raiz
     */
    public NodoBST<Estudiante> getRaiz() {
        return raiz;
    }
    
}
