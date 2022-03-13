package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.IFuenteDatos;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IAulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IReservas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Aulas;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Profesores;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.memoria.Reservas;

public class FactoriaFuenteDatosFicheros implements IFuenteDatos {
	

	public FactoriaFuenteDatosFicheros() {

	}

	@Override
	public IAulas crearAulas() {
		return new Aulas();
	}

	@Override
	public IProfesores crearProfesores() {
		return new Profesores();
	}

	@Override
	public IReservas crearReservas() {
		return new Reservas();
	}
	
}
