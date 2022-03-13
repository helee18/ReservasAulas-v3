package org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.ficheros;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.reservasaulas.mvc.modelo.dominio.Profesor;
import org.iesalandalus.programacion.reservasaulas.mvc.modelo.negocio.IProfesores;

public class Profesores implements IProfesores {
	
	private List<Profesor> coleccionProfesores;
	private static final String NOMBRE_FICHERO_PROFESORES = ".\\src\\main\\java\\org\\iesalandalus\\programacion\\reservasaulas\\mvc\\modelo\\negocio\\ficheros\\Ficheros\\Profesores.dat";
	
	public Profesores () {
		coleccionProfesores = new ArrayList<Profesor>();
	}
	
	public Profesores(IProfesores profesoresOriginal) {
		if (profesoresOriginal == null)
			throw new NullPointerException("ERROR: No se pueden copiar profesores nulos.");
		
		if (profesoresOriginal.getNumProfesores() == 0)
			coleccionProfesores = new ArrayList<Profesor>();
		else 
			setProfesores(profesoresOriginal);
	}
	
	public void comenzar(){
		leer();
	}
	
	public void terminar(){
		escribir();
	}
	
	private void leer(){
		File fileProfesores = new File(NOMBRE_FICHERO_PROFESORES); // creamos el fichero
		
		// creamos el flujo y recorremos los profesores para ir copiandolas del fichero
		try { 
			FileInputStream fileIS = new FileInputStream(fileProfesores);// creamos el flujo
			ObjectInputStream objectIS = new ObjectInputStream(fileIS);// definimos que el tipo de dato va a ser objeto
			Profesor profesor = null;
			do {
				profesor = (Profesor) objectIS.readObject();
				insertar(profesor);

			} while (profesor != null);
			
			objectIS.close(); //cerramos flujo
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: No puedo encontrar la clase que tengo que leer.");
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No puedo abrir el fichero de profesores.");
		} catch (EOFException e) {
			System.out.println("Fichero profesores leído.");
		} catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida.");
		} catch (OperationNotSupportedException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void escribir(){
		File fileProfesores = new File(NOMBRE_FICHERO_PROFESORES); // creamos el fichero
		
		// creamos el flujo y recorremos los profesores para ir copiandolas en el fichero
		try {
			FileOutputStream fileOS = new FileOutputStream(fileProfesores); // creamos el flujo
			ObjectOutputStream objectOS = new ObjectOutputStream(fileOS); // definimos que el tipo de dato va a ser objeto
			
			for (Profesor profesor : coleccionProfesores)
				objectOS.writeObject(profesor);
			System.out.println("Fichero profesores escrito.");
			
			objectOS.close();//cerramos flujo
		} catch (FileNotFoundException e) {
			System.out.println("ERROR: No puedo abrir el fichero de profesores.");
		} catch (IOException e) {
			System.out.println("ERROR inesperado de Entrada/Salida.");
		}
	}
	
	private void setProfesores(IProfesores profesores) {

		coleccionProfesores = copiaProfundaProfesores(profesores.getProfesores());
	}
	
	@Override
	public List<Profesor> getProfesores() {
		if (getNumProfesores() == 0)
			throw new NullPointerException("ERROR: No se ha creado ningun profesor aun.");
		
		Comparator<Profesor> comparator = Comparator.comparing(Profesor::getCorreo);
		
		List<Profesor> copiaProfesores = copiaProfundaProfesores(coleccionProfesores);
		
		Collections.sort(copiaProfesores, comparator);
		
		return copiaProfesores;
	}
	
	private List<Profesor> copiaProfundaProfesores(List<Profesor> coleccionProfesoresOriginal) {
		
		List<Profesor> coleccionCopiaProfesores;
		
		coleccionCopiaProfesores = new ArrayList<Profesor>(getNumProfesores());
		
		// recorremos todas las profesores comparando
		Iterator<Profesor> it = coleccionProfesoresOriginal.iterator();
		while(it.hasNext()) {
			Profesor profesorCopia = new Profesor(it.next());
			coleccionCopiaProfesores.add(profesorCopia);
		}
		return coleccionCopiaProfesores;
		
	}
	
	@Override
	public void insertar (Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) 
			throw new NullPointerException("ERROR: No se puede insertar un profesor nulo.");
		
		if (buscar(profesor) != null)
			throw new OperationNotSupportedException("ERROR: Ya existe un profesor con ese correo.");
		
		// insertamos la nueva cita y actualizamos el tamaño
		coleccionProfesores.add(new Profesor(profesor));
	}
	
	@Override
	public Profesor buscar (Profesor profesor) {
		if (profesor == null)
			throw new NullPointerException("ERROR: No se puede buscar un profesor nulo.");
		
		// si el indice supera al tamaño, es que no lo ha encontrado y es un objeto nuevo
		if (coleccionProfesores.contains(profesor))
			return new Profesor(profesor); 
		else
			return null;
	}
	
	@Override
	public void borrar (Profesor profesor) throws OperationNotSupportedException {
		if (profesor == null) 
			throw new NullPointerException("ERROR: No se puede borrar un profesor nulo.");
		
		if (buscar(profesor) == null) 		
			throw new OperationNotSupportedException("ERROR: No existe ningún profesor con ese correo.");
		
		coleccionProfesores.remove(profesor);
	}
	
	@Override
	public List<String> representar() throws OperationNotSupportedException {
		if (getNumProfesores() == 0)
			throw new OperationNotSupportedException("ERROR: La lista de profesores está vacia.");
		
		List<String> representacion = new ArrayList<String>();
		
		for (Profesor p : coleccionProfesores)
			representacion.add(p.toString());	
	
		return representacion;
	}

	@Override
	public int getNumProfesores() {
		return coleccionProfesores.size();
	}
	

}
