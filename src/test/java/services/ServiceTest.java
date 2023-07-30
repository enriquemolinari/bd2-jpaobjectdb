package services;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import main.Persona;
import main.Telefono;

public class ServiceTest {

	private EntityManagerFactory emf;

	@BeforeEach
	public void setUp() {
		emf = Persistence
				// drop create for testing pursposes
				.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
	}

	@Test
	public void puedoPersisterUnaPersonaConTelefonos() {
		inTransactionExecute((em) -> {
			Persona enrique = new Persona(1L, "Enrique", "San Martin 123",
					LocalDate.now().minusYears(40));

			Telefono t = new Telefono("234234");
			enrique.addTelefono(t);

			em.persist(enrique);
		});

		inTransactionExecute((em) -> {
			Persona enrique = em.find(Persona.class, 1L);
			assertTrue(enrique.seLlama("Enrique"));
			assertTrue(enrique.viveEn("San Martin 123"));
			assertTrue(enrique.suTelefonoEs("234234"));
		});
	}

	public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			bloqueDeCodigo.accept(em);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@AfterEach
	public void tearDown() {
		emf.close();
	}
}
