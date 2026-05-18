package com.mycompany.gestion_academica_ed.ui;

import com.mycompany.gestion_academica_ed.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Contexto compartido entre todas las pantallas.
 * Contiene las estructuras de datos del proyecto.
 */
public class AppContext {

    private final BSTEstudiantes bst;
    private final AVLPromedios avl;
    private final SistemaCalificaciones sistemaCalificaciones;
    private final Diccionario<String, Curso> cursos;       // key = idCurso
    private final ListaEnlazadaSimple<Curso> listaCursos;
    private final List<String> actividad;   // log de actividad reciente

    public AppContext() {
        bst = new BSTEstudiantes();
        avl = new AVLPromedios();
        sistemaCalificaciones = new SistemaCalificaciones();
        cursos = new Diccionario<>(50);
        listaCursos = new ListaEnlazadaSimple<>();
        actividad = new ArrayList<>();
    }

    public BSTEstudiantes getBst()                         { return bst; }
    public AVLPromedios getAvl()                           { return avl; }
    public SistemaCalificaciones getSistemaCalificaciones(){ return sistemaCalificaciones; }
    public Diccionario<String, Curso> getCursosDict()      { return cursos; }
    public ListaEnlazadaSimple<Curso> getCursos()          { return listaCursos; }
    public List<String> getActividad()                     { return actividad; }

    public void registrarActividad(String msg) {
        actividad.add(msg);
        if (actividad.size() > 50) actividad.remove(0);
    }

    /** Agrega estudiante a BST y AVL */
    public void agregarEstudiante(Estudiante e) {
        bst.insertar(e);
        if (e.calcularPromedio() > 0) {
            avl.insertar(e.calcularPromedio(), e);
        }
        registrarActividad("Estudiante registrado: " + e.getNomCompleto());
    }

    /**
     * Inscribe estudiante en curso y registra la accion en la pila
     * para que "Deshacer" pueda revertirla.
     */
    public String inscribirEstudiante(Estudiante est, Curso c) {
        String resultado = c.inscribirEstudiante(est);
        // Solo registrar en pila si fue inscripcion real (no lista espera)
        if (resultado.contains("exitosamente")) {
            sistemaCalificaciones.registrarAccion(
                new Accion("INSCRIPCION", est, c)
            );
        }
        registrarActividad(resultado);
        return resultado;
    }

    /**
     * Elimina un curso del diccionario y de la lista enlazada.
     */
    public void eliminarCurso(String idCurso) {
        Curso c = cursos.obtener(idCurso);
        if (c == null) return;
        cursos.eliminar(idCurso);
        listaCursos.eliminar(c);
        registrarActividad("Curso eliminado: " + idCurso);
    }

    /** Reconstruye el árbol AVL desde el BST (tras cambios de calificaciones) */
    public void reconstruirAVL() {
        avl.limpiar();
        Arreglo_Dinamico todos = bst.obtenerEstudiantes();
        for (int i = 0; i < todos.obtenerTamaño(); i++) {
            Estudiante e = (Estudiante) todos.obtener(i);
            if (e.calcularPromedio() > 0) {
                avl.insertar(e.calcularPromedio(), e);
            }
        }
    }

    /** Cantidad de solicitudes pendientes en la cola */
    public int getCantidadSolicitudes() {
        return sistemaCalificaciones.getCantidadEnCola();
    }
}
