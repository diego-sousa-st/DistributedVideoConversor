import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author <a href="mailto:diegosousa.st@gmail.com">Diego José de Sousa Gouveia</a>
 * 
 */
public class ServidorConversorVideo {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {

		try {

			//set your server's ip address
			System.setProperty("java.rmi.server.hostname","127.0.0.1");

			RemoteVideoConversor conversor = new RemoteVideoConversorImpl();			
			// object stay available at randomic port
			RemoteVideoConversor stub = (RemoteVideoConversor) UnicastRemoteObject.exportObject(conversor, 0);

			String name = "conversor";

			Registry registry = LocateRegistry.getRegistry();
			registry.rebind(name, stub);

			System.out.println("Waiting connection...");

		} catch (Exception e) {

			System.err.println("Error performing remote operation!");
			e.printStackTrace();

		}

	}

}
