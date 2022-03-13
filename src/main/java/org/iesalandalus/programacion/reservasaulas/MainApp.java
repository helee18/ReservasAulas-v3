package org.iesalandalus.programacion.reservasaulas;

import org.iesalandalus.programacion.reservasaulas.mvc.controlador.Controlador;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IModelo;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.Modelo;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.IVista;
import org.iesalandalus.programacion.reservasaulas.mvc.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;

public class MainApp {

	public static void main(String[] args) {
		System.out.println("Programa para la gestión de reservas de espacios del IES Al-Ándalus");
		
		IModelo modelo = null;
		IVista vista = new Vista();
		
		// Creamos el Modelo pasandole como parametro la fuente de datos
		// En este caso preguntamos si queremos los datos de memoria o de ficheros
		int respuesta = 0;
		do {
			System.out.println("¿Que datos quieres? 0. Memoria, 1. Ficheros ");
			respuesta = Entrada.entero();
		} while (respuesta < 0 || respuesta > 1);
		
		// segun la respuesta llamamo a una opcion u otra de fuente de datos
		if (respuesta == 0)		
			modelo = new Modelo(FactoriaFuenteDatos.MEMORIA.crear());
		else if (respuesta == 1)
			modelo = new Modelo(FactoriaFuenteDatos.FICHEROS.crear());
		
		try {
			new Controlador(modelo, vista);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

}
