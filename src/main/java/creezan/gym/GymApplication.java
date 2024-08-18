package creezan.gym;

import creezan.gym.modelo.Cliente;
import creezan.gym.servicio.ClienteServicio;
import creezan.gym.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class GymApplication implements CommandLineRunner {
	@Autowired
	private IClienteServicio clienteServicio;
	private String nl = System.lineSeparator();
	private static final Logger logger =
			LoggerFactory.getLogger(GymApplication.class);

	public static void main(String[] args) {
		logger.info("Iniciar aplicación");
		SpringApplication.run(GymApplication.class, args);
		logger.info("Finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		creezanGymApp();
	}

	private void creezanGymApp() {
		var salir = false;
		var consola = new Scanner(System.in);
		while (!salir) {
			var opcion = mostrarMenu(consola);
			salir = ejecutarOpciones(consola, opcion);
			logger.info(nl);
		}
	}

	private int mostrarMenu(Scanner consola) {
		logger.info(nl + "*** Iniciando la aplicación ***" + nl +
				"1. Listar Clientes." + nl +
				"2. Buscar Cliente." + nl +
				"3. Agregar Cliente." + nl +
				"4. Modificar Cliente." + nl +
				"5. Eliminar Cliente." + nl +
				"6. Salir." + nl +
				"Elige una opción:\n");
		return Integer.parseInt(consola.nextLine());
	}

	private boolean ejecutarOpciones(Scanner consola, int opcion) {
		var salir = false;
			switch (opcion) {
				case 1 -> {
					logger.info(nl + "*** Listado de clientes ***" + nl);
					List<Cliente> clientes = clienteServicio.listarClientes();
					clientes.forEach(cliente -> logger.info(nl + cliente.toString() + nl));

				}
				case 2 -> {
					logger.info(nl + "*** Buscar clientes por ID ***" + nl);
					logger.info("ID del cliente buscado: ");
					var idCliente = Integer.parseInt(consola.nextLine());
					Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
					if (cliente != null) {
						logger.info(nl + "Cliente encontrado: " + cliente + nl);
					} else {
						logger.info(nl + "Cliente no encontrado." + nl);
					}
				}
				case 3 -> {
					logger.info(nl + "*** Agregar miembro ***"+nl);
					logger.info(nl + "Nombre: ");
					var nombre = consola.nextLine();
					logger.info(nl + "Apellido: ");
					var apellido = consola.nextLine();
					logger.info(nl + "Membresía: ");
					var membresia = Integer.parseInt(consola.nextLine());
					var cliente = new Cliente();
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					clienteServicio.guardarCliente(cliente);
					logger.info(nl + "Cliente agregado: " + cliente + nl);
				}
				case 4 -> {
					logger.info(nl + "*** Modificar cliente ***");
					logger.info(nl + "Ingresa el ID del cliente a modificar: ");
					var idCliente = Integer.parseInt(consola.nextLine());
					Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
					if (cliente != null) {
						logger.info(nl + "Nombre: "+nl);
						var nombre = consola.nextLine();
						logger.info(nl + "Apellido: "+nl);
						var apellido = consola.nextLine();
						logger.info(nl + "Membresía: "+nl);
						var membresia = Integer.parseInt(consola.nextLine());
						cliente.setNombre(nombre);
						cliente.setNombre(apellido);
						cliente.setMembresia(membresia);
						clienteServicio.guardarCliente(cliente);
						logger.info("Cliente modificado: " + cliente + nl);
					} else {
						logger.info("Cliente no encontrado. ");
					}
				}
				case 5 -> {
					logger.info("*** Eliminar cliente ***");
					logger.info("Ingrese el ID del cliente que deseas eliminar: ");
					var idCliente = Integer.parseInt(consola.nextLine());
					var cliente = clienteServicio.buscarClientePorId(idCliente);

					if (cliente != null) {
						clienteServicio.eliminarCliente(cliente);
						logger.info("El cliente ha sido eliminado." + nl);
					} else {
						logger.info("No se ha encontrado un cliente. " + nl);
					}
				}
				case 6 -> {
					logger.info("Hasta pronto!!" + nl + nl);
					salir = true;

				}
				default -> logger.info("Opción no reconocida.");
			}
		return salir;
	}
}

