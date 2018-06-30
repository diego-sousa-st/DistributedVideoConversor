import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author <a href="mailto:diegosousa.st@gmail.com">Diego José de Sousa Gouveia</a>
 * 
 */
public interface RemoteVideoConversor extends Remote {

	/**
	 * Retorna a key do video transferido
	 * 
	 * @param fileName  Nome do video
	 * @param videoPart Objeto que representa um video
	 * @return key do video
	 */
	String sendVideoPart(VideoPart videoPart) throws RemoteException;

	/**
	 * Método que busca o video convertido
	 * 
	 * @param key key para identificar o video a ser transferido
	 * @return
	 */
	VideoPart getVideoPart(String key) throws RemoteException;

	/**
	 * Método que converte o video upado para o formato especificado segundo os
	 * parametros abaixo
	 * 
	 * @param key     key para identificar o video
	 * @param width   largura do frame de video
	 * @param height  altura do frame de video
	 * @param fps     taxa de quadros do video
	 * @param noAudio flag indicando a remocao ou nao do audio
	 * @return Key caso o video foi convertido com sucesso ou null caso contrário
	 */
	String convertVideo(String key, int width, int height, int fps, boolean noAudio) throws RemoteException;

}
