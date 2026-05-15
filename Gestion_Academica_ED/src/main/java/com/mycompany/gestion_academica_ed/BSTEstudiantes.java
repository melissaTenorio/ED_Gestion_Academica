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
    private NodoBST raiz;

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
        raiz = insertarRecursivo(raiz,estudiante);
    }

    /**
     * metodo recursivo para insertar
     * estudiantes comparando matriculas
     */
    private NodoBST insertarRecursivo(NodoBST actual, Estudiante estudiante) {
        /**
         * si el nodo esta vacio
         * se crea uno nuevo
         */
        if (actual == null) {
            return new NodoBST(estudiante);
        }

        /**
         * compareTo:
         * menor que 0 -> izquierda
         * mayor que 0 -> derecha
         */

        // insertar izquierda
        if (estudiante.getMatricula().compareTo(actual.estudiante.getMatricula()) < 0) {
            actual.izquierda = insertarRecursivo(actual.izquierda, estudiante);
        } else {
            // insertar derecha
            actual.derecha = insertarRecursivo(actual.derecha, estudiante);
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
    private Estudiante buscarRecursivo(NodoBST actual, String matricula) {
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
        if (actual.estudiante.getMatricula().equals(matricula)) {
            return actual.estudiante;
        }

        /**
         * si la matricula es menor
         * buscar izquierda
         */
        if (matricula.compareTo(actual.estudiante.getMatricula()) < 0) {
            return buscarRecursivo(actual.izquierda, matricula);
        }

        /**
         * si es mayor
         * buscar derecha
         */
        return buscarRecursivo(actual.derecha, matricula);
    }

    /**
     * getter de la raiz
     */
    public NodoBST getRaiz() {
        return raiz;
    }
    
}
