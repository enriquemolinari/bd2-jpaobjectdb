package main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Persona {

	@Id
	private Long id;
	private String nombre;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona")
	private List<Telefono> telefonos = new ArrayList<>();

	private LocalDate fechaNac;

	@Embedded
	private Address direccion;

	public Persona(Long id, String nombre, String direccion,
			LocalDate fechaNacimiento) {
		// validate all !
		this.id = id;
		this.nombre = nombre;
		this.direccion = new Address(direccion);
		this.fechaNac = fechaNacimiento;
	}

	public void printTelefonos() {
		for (Telefono telefono : telefonos) {
			System.out.println(telefono.toString());
		}
	}

	protected Persona() {

	}

	public void addTelefono(Telefono t) {
		this.telefonos.add(t);
	}

	public boolean seLlama(String unNombre) {
		return this.nombre.equals(unNombre);
	}

	public boolean viveEn(String unaDireccion) {
		return this.direccion.suCalleEs(unaDireccion);
	}

	public boolean suTelefonoEs(String string) {
		return this.telefonos.stream().anyMatch(t -> t.suNumberoEs(string));
	}

	private String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private List<Telefono> getTelefonos() {
		return telefonos;
	}

	private void setTelefonos(List<Telefono> telefonos) {
		this.telefonos = telefonos;
	}

	private long getId() {
		return id;
	}

	private void setId(long id) {
		this.id = id;
	}

	private LocalDate getFecha() {
		return fechaNac;
	}

	private void setFecha(LocalDate fecha) {
		this.fechaNac = fecha;
	}

	private Address getDireccion() {
		return direccion;
	}

	private void setDireccion(Address direccion) {
		this.direccion = direccion;
	}

	public String nombre() {
		return nombre;
	}
}
