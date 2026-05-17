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
    
    // Listas para inscripción
    private ListaEnlazadaSimple<Estudiante> inscritos;
    private ListaEnlazadaSimple<Estudiante> listaEspera;

    public Curso(String idCurso, String nombreCurso) {
        this.idCurso = idCurso;
        this.nombreCurso = nombreCurso;
        this.inscritos = new ListaEnlazadaSimple<>();
        this.listaEspera = new ListaEnlazadaSimple<>();
    }

    public String inscribirEstudiante(Estudiante est) {
        if (inscritos.getTamaño() < CAPACIDAD_MAXIMA) {
            inscritos.agregarAlFinal(est);
            return "Estudiante " + est.getNomCompleto() + " inscrito exitosamente en " + nombreCurso;
        } else {
            listaEspera.agregarAlFinal(est);
            return "Curso lleno. " + est.getNomCompleto() + " movido a lista de espera.";
        }
    }

    public String getIdCurso() { 
        return idCurso; 
    
    }

    public String getNombreCurso() { 
        return nombreCurso; 

    }

    public ListaEnlazadaSimple<Estudiante> getInscritos() { 
        return inscritos; 
    }
    
    public ListaEnlazadaSimple<Estudiante> getListaEspera() { 
        return listaEspera; 
    }

    @Override
    public String toString() {
        return "Curso: " + nombreCurso + " [" + idCurso + "] - Inscritos: " + inscritos.getTamaño() + "/30";
    }
    
}
