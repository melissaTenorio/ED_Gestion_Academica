/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class SistemaCalificaciones {
    
    // cola FIFO de solicitudes
    private ColaGenerica<SolicitudCalificacion> colaSolicitudes;

    // pila de acciones
    private PilaGenerica<Accion> pilaAcciones;

    /**
     * constructor
     */
    public SistemaCalificaciones() {
        colaSolicitudes = new ColaGenerica<>();

        pilaAcciones = new PilaGenerica<>();
    }

    /**
     * agrega solicitud a la cola
     */
    public void enviarSolicitud(SolicitudCalificacion solicitud) {
        colaSolicitudes.enqueue(solicitud);
    }

    /**
     * procesa siguiente solicitud
     */
    public void procesarSiguiente() {
        // sacar de la cola
        SolicitudCalificacion solicitud = colaSolicitudes.dequeue();

        // si no hay solicitudes
        if (solicitud == null) {
            return;
        }
        
        Estudiante estudiante = solicitud.getEstudiante();

        double calificacion = solicitud.getNuevaCalificacion();

        // agregar calificacion
        estudiante.agregarCalificacion(calificacion);

        // guardar accion en pila
        pilaAcciones.push(new Accion("CALIFICACION", estudiante, calificacion));
    }

    /**
     * deshacer ultima accion
     */
    public void deshacerUltimaAccion() {

        Accion accion = pilaAcciones.pop();

        // si no hay acciones
        if (accion == null) {
            return;
        }

        /**
         * aqui se deshace la accion
         */
        if (accion.getTipoAccion().equals("CALIFICACION")) {
            accion.getEstudiante().getCalificaciones().restaContador();
        }
    }
    
}
