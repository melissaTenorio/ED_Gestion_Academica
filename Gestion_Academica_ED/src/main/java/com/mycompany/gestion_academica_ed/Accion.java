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
    private String tipoAccion;
    private Estudiante estudiante;
    private double calificacion;

    /**
     * constructor
     */
    public Accion(String tipoAccion, Estudiante estudiante, double calificacion) {
        this.tipoAccion = tipoAccion;
        this.estudiante = estudiante;
        this.calificacion = calificacion;
    }

    public String getTipoAccion() {
        return tipoAccion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public double getCalificacion() {
        return calificacion;
    }
    
}
