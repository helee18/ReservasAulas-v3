package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;

public class MainApp {

	public static void main(String[] args) {
		System.out.println("Programa para la gestión de reservas de espacios del IES Al-Ándalus");
		
		IModelo modelo;
		IVista vista;
		
		vista = new Vista();
		
		// Creamos el Modelo pasandole como parametro la fuente de datos
		// En este caso solo tenemos una opcion por lo que la pasamos directamente sin preguntar
		modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
		
		try {
			new Controlador(modelo, vista);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

}
