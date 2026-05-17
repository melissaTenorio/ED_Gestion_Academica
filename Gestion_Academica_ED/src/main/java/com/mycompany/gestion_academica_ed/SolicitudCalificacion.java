/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class SolicitudCalificacion {
    private Estudiante estudiante;
    private double nuevaCalificacion;

    /**
     * constructor
     */
    public SolicitudCalificacion(Estudiante estudiante, double nuevaCalificacion) {
        this.estudiante = estudiante;
        this.nuevaCalificacion = nuevaCalificacion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public double getNuevaCalificacion() {
        return nuevaCalificacion;
    }
    
}
