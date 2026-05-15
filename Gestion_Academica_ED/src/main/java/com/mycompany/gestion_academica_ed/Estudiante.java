/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gestion_academica_ed;

/**
 *
 * @author melis
 */
public class Estudiante {
    private String Matricula;
    private String nomCompleto;
    private String telefono;
    private String correo;
    private String direccion;
    private Arreglo_Dinamico calificaciones;
    
    /**
    * constructor de estudiante
    */
   public Estudiante(String Matricula, String nomCompleto, String telefono, String correo, String direccion) {
       this.Matricula = Matricula;
       this.nomCompleto = nomCompleto;
       this.telefono = telefono;
       this.correo = correo;
       this.direccion = direccion;
       // inicializa arreglo dinamico
       calificaciones = new Arreglo_Dinamico();
   }

    /**
    * metodos para agregar calificaciones, calcular promedio y mostrar dichas calificaciones
    */
    public void agregarCalificacion(double cal){
       this.calificaciones.agregar(cal);
    }

    public double calcularPromedio(){
       if(calificaciones.obtenerTamaño() == 0){
            return 0;
        }

        double suma = sumaRecursiva(0);

        return suma / calificaciones.obtenerTamaño();
    }
    
    /**
    * suma recursiva de calificaciones
    */
    private double sumaRecursiva(int indice){
        if(indice == calificaciones.obtenerTamaño()){
            return 0;
        }

        return (double) calificaciones.obtener(indice) + sumaRecursiva(indice + 1);
    }
    
   
    /**
     * getters y setters
     * @return 
     */
    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getNomCompleto() {
        return nomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        this.nomCompleto = nomCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Arreglo_Dinamico getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(Arreglo_Dinamico calificaciones) {
        this.calificaciones = calificaciones;
    }

    @Override
    public String toString() {
        return "Estudiante{" + "Matricula=" + Matricula + ", nomCompleto=" + nomCompleto + ", telefono=" + telefono + ", correo=" + correo + ", direccion=" + direccion + ", calificaciones=" + calificaciones + '}';
    }
    
  
}
