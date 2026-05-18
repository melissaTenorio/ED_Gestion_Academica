/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Curso {
    private String idCurso; // Clave unica
    private String nombreCurso;
    private final int CAPACIDAD_MAXIMA = 30;
    
    // Lista enlazada simple de estudiantes inscritos (req. 5)
    private ListaEnlazadaSimple<Estudiante> inscritos;

    // Lista doble circular para lista de espera (req. 6)
    private ListaDobleCircular<Estudiante> listaEspera;

    // Lista circular simple para rotacion de roles (req. 7)
    private ListaCircularSimple<Estudiante> listaRoles;
    private String rolActual; // "Tutor" o "Lider"

    public Curso(String idCurso, String nombreCurso) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.inscritos = new ListaEnlazadaSimple<>();
        this.listaEspera = new ListaDobleCircular<>();
        this.listaRoles  = new ListaCircularSimple<>();
        this.rolActual   = "Tutor";
    }

    /**
     * Inscribe estudiante: si hay cupo va a inscritos,
     * si no, va a la lista doble circular de espera.
     */
    public String inscribirEstudiante(Estudiante est) {
        if (inscritos.getTamaño() < CAPACIDAD_MAXIMA) {
            inscritos.agregarAlFinal(est);
            listaRoles.agregar(est); // también se añade a lista de roles
            return "Estudiante " + est.getNomCompleto() + " inscrito exitosamente en " + nombreCurso;
        } else {
            listaEspera.agregar(est);
            return "Curso lleno. " + est.getNomCompleto() + " añadido a lista de espera.";
        }
    }

    /**
     * Rota el rol (tutor/lider) al siguiente estudiante
     * en la lista circular simple.
     */
    public String rotarRol() {
        Estudiante siguiente = listaRoles.rotar();
        if (siguiente == null) {
            return "No hay estudiantes con rol asignado en " + nombreCurso;
        }
        rolActual = rolActual.equals("Tutor") ? "Líder" : "Tutor";
        return "Nuevo " + rolActual + " en " + nombreCurso + ": " + siguiente.getNomCompleto();
    }

    public String getIdCurso()   { return idCurso; }
    public String getNombreCurso() { return nombreCurso; }
    public ListaEnlazadaSimple<Estudiante> getInscritos()      { return inscritos; }
    public ListaDobleCircular<Estudiante>  getListaEspera()    { return listaEspera; }
    public ListaCircularSimple<Estudiante> getListaRoles()     { return listaRoles; }
    public String getRolActual() { return rolActual; }

    @Override
    public String toString() {
        return "Curso: " + nombreCurso + " [" + idCurso + "] - Inscritos: " + inscritos.getTamaño() + "/30";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Curso)) return false;
        return this.idCurso.equals(((Curso) obj).idCurso);
    }
}
