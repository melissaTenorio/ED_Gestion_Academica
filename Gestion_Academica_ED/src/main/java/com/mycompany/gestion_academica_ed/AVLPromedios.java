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
    private NodoAVL<Estudiante> raiz;

    /**
     * metodo para insertar estudiantes
     * usando el promedio como clave
     */
    public void insertar(
            double promedio,
            Estudiante estudiante) {

        raiz = insertarRecursivo(
                raiz,
                promedio,
                estudiante);
    }

    /**
     * metodo recursivo para insertar
     * y balancear el arbol AVL
     */
    private NodoAVL<Estudiante> insertarRecursivo(
            NodoAVL<Estudiante> nodo,
            double promedio,
            Estudiante estudiante) {

        // si el nodo esta vacio
        if (nodo == null) {

            return new NodoAVL<>(
                    promedio,
                    estudiante);
        }

        // insertar izquierda
        if (promedio < nodo.clave) {

            nodo.izquierda = insertarRecursivo(
                    nodo.izquierda,
                    promedio,
                    estudiante);

        } else if (promedio > nodo.clave) {

            // insertar derecha
            nodo.derecha = insertarRecursivo(
                    nodo.derecha,
                    promedio,
                    estudiante);

        } else {

            /**
             * si tienen el mismo promedio
             * ordenar por matricula
             */
            if (estudiante.getMatricula().compareTo(
                    nodo.dato.getMatricula()) < 0) {

                nodo.izquierda = insertarRecursivo(
                        nodo.izquierda,
                        promedio,
                        estudiante);

            } else {

                nodo.derecha = insertarRecursivo(
                        nodo.derecha,
                        promedio,
                        estudiante);
            }
        }

        /**
         * actualizar altura
         */
        nodo.altura = 1 + Math.max(
                altura(nodo.izquierda),
                altura(nodo.derecha));

        /**
         * calcular balance
         */
        int balance = obtenerBalance(nodo);

        /**
         * casos de balanceo AVL
         */

        // izquierda izquierda
        if (balance > 1
                && promedio < nodo.izquierda.clave) {

            return rotarDerecha(nodo);
        }

        // derecha derecha
        if (balance < -1
                && promedio > nodo.derecha.clave) {

            return rotarIzquierda(nodo);
        }

        // izquierda derecha
        if (balance > 1
                && promedio > nodo.izquierda.clave) {

            nodo.izquierda =
                    rotarIzquierda(nodo.izquierda);

            return rotarDerecha(nodo);
        }

        // derecha izquierda
        if (balance < -1
                && promedio < nodo.derecha.clave) {

            nodo.derecha =
                    rotarDerecha(nodo.derecha);

            return rotarIzquierda(nodo);
        }

        return nodo;
    }

    /**
     * obtiene altura del nodo
     */
    private int altura(
            NodoAVL<Estudiante> nodo) {

        if (nodo == null) {
            return 0;
        }

        return nodo.altura;
    }

    /**
     * calcula balance del nodo
     */
    private int obtenerBalance(
            NodoAVL<Estudiante> nodo) {

        if (nodo == null) {
            return 0;
        }

        return altura(nodo.izquierda)
                - altura(nodo.derecha);
    }

    /**
     * rotacion simple derecha
     */
    private NodoAVL<Estudiante> rotarDerecha(
            NodoAVL<Estudiante> y) {

        NodoAVL<Estudiante> x =
                y.izquierda;

        NodoAVL<Estudiante> t2 =
                x.derecha;

        x.derecha = y;

        y.izquierda = t2;

        // actualizar alturas
        y.altura = Math.max(
                altura(y.izquierda),
                altura(y.derecha)) + 1;

        x.altura = Math.max(
                altura(x.izquierda),
                altura(x.derecha)) + 1;

        return x;
    }

    /**
     * rotacion simple izquierda
     */
    private NodoAVL<Estudiante> rotarIzquierda(
            NodoAVL<Estudiante> x) {

        NodoAVL<Estudiante> y =
                x.derecha;

        NodoAVL<Estudiante> t2 =
                y.izquierda;

        y.izquierda = x;

        x.derecha = t2;

        // actualizar alturas
        x.altura = Math.max(
                altura(x.izquierda),
                altura(x.derecha)) + 1;

        y.altura = Math.max(
                altura(y.izquierda),
                altura(y.derecha)) + 1;

        return y;
    }

    /**
     * obtiene estudiantes ordenados
     * de menor a mayor promedio
     */
    public Arreglo_Dinamico obtenerEstudiantesOrdenados() {

        Arreglo_Dinamico listaOrdenada =
                new Arreglo_Dinamico();

        inOrdenRecursivo(
                raiz,
                listaOrdenada);

        return listaOrdenada;
    }

    /**
     * recorrido inorder recursivo
     */
    private void inOrdenRecursivo(
            NodoAVL<Estudiante> nodo,
            Arreglo_Dinamico lista) {

        if (nodo != null) {

            // izquierda
            inOrdenRecursivo(
                    nodo.izquierda,
                    lista);

            // guardar estudiante
            lista.agregar(
                    nodo.dato);

            // derecha
            inOrdenRecursivo(
                    nodo.derecha,
                    lista);
        }
    }

    /**
     * limpia el arbol AVL
     */
    public void limpiar() {
        raiz = null;
    }

    /**
     * getter de raiz
     */
    public NodoAVL<Estudiante> getRaiz() {
        return raiz;
    }

}
