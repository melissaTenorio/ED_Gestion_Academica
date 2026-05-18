/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class Accion {
    private String tipoAccion; // "CALIFICACION", "INSCRIPCION", "BAJA"
    private Estudiante estudiante;
    private double calificacion;
    private Curso curso; // para INSCRIPCION / BAJA

    /** Constructor para acciones de calificacion */
    public Accion(String tipoAccion, Estudiante estudiante, double calificacion) {
        this.tipoAccion   = tipoAccion;
        this.estudiante   = estudiante;
        this.calificacion = calificacion;
    }

    /** Constructor para acciones de inscripcion / baja */
    public Accion(String tipoAccion, Estudiante estudiante, Curso curso) {
        this.tipoAccion = tipoAccion;
        this.estudiante = estudiante;
        this.curso      = curso;
    }

    public String getTipoAccion()   { return tipoAccion; }
    public Estudiante getEstudiante(){ return estudiante; }
    public double getCalificacion() { return calificacion; }
    public Curso getCurso()         { return curso; }
}
