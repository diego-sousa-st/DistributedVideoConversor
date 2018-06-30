import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 
 * @author <a href="mailto:diegosousa.st@gmail.com">Diego José de Sousa Gouveia</a>
 * 
 */
public class ClientConversorVideo {

	public static String key;	

	/**
	 * @param args the command line arguments
	 * 
	 * 			   The syntax to call this program is presented in next description:
	 * 
	 *             1º param: command to be executed in the server. The program have 3:
	 * 
	 *             send
	 * 
	 *             receive
	 * 
	 *             convert
	 * 
	 *             2º parametro: server's ip
	 * 
	 *             ex: 127.0.0.1
	 * 
	 * 			   Depending of the command that you give, we will have the 3º, 4ª, ...
	 * 
	 * 
	 * 			   send and receive command:	
	 * 
	 * 			   	 
	 *             3º param: video's name to be send or key to get video converted
	 * 
	 *             ex: test.mp4
	 * 
	 * 			   convert command:
	 * 
	 * 			   3º param: key for convert the video, returned by the send command:
	 * 
	 * 			   ex: 1234567433_teste.mp4
	 * 
	 * 			   4º param: the with of the video frame
	 * 
	 * 			   Ex: 1920
	 * 
	 * 			   5º param: the height of the video frame
	 * 
	 * 			   Ex: 1080
	 * 
	 * 			   6º param: the frame per seconds of the video
	 * 
	 * 			   Ex: 60
	 * 
	 * 			   7º param: boolean option for excluding audio in the video
	 * 
	 * 			   Ex: false
	 * 
	 *             Examples of usage:
	 * 
	 *             java ClientConversorVideo send 127.0.0.1 test.mp4
	 * 
	 *             java ClientConversorVideo convert 127.0.0.1 key width height fps noAudio
	 * 
	 *             		java ClientConversorVideo convert 127.0.0.1 key 1920 1080 60 true
	 * 
	 *             java ClientConversorVideo receive 127.0.0.1 key
	 * 
	 *             Compiling the client:
	 * 
	 * 			   javac *.java && jar cvf ClientConversor.jar ClienteConversorVideo.class RemoteVideoConversor.class VideoPart.class
	 * 
	 *             jar cmf ClientConversor.jar manifestClient.txt *.class
	 * 
	 *             Compiling the server:
	 * 
	 * 			   javac *.java && jar cvf ServerConversor.jar ServidorConversorVideo.class RemoteVideoConversor.class RemoteVideoConversorImpl.class VideoPart.class
	 * 
	 *             jar cmf ServerConversor.jar manifestServer.txt *.class
	 * 
	 */
	public static void main(String[] args) {

		String comando = args[0];

		String ipServer = args[1];

		try {

			String name = "conversor";
			Registry registry = LocateRegistry.getRegistry(ipServer);
			RemoteVideoConversor conversor = (RemoteVideoConversor) registry.lookup(name);
			computeCommand(conversor, comando, args);

		} catch (Exception e) {

			System.err.println("An error have occurred! Please contact Diego Sousa");
			e.printStackTrace();
			
		}

	}

	public static void computeCommand(RemoteVideoConversor conversor, String comando, String[] args) {

		if (comando.equals("send")) {

			String name = args[2];

			try {

				File file = new File(name);
				FileInputStream in = new FileInputStream(file);
				byte[] mydata = new byte[1024 * 1024];
				int mylen = in.read(mydata);
				while (mylen > 0) {
					key = conversor.sendVideoPart(new VideoPart(name, mydata, mylen));
					mylen = in.read(mydata);
				}

				in.close();

				System.out.println("Key to convert and receive the video: " + key);

			} catch (Exception e) {

				e.printStackTrace();

			}

		} else if (comando.equals("receive")) {

			String keyForDownload = args[2];

			try {

				VideoPart videoPart = conversor.getVideoPart(keyForDownload);

				while (videoPart != null) {

					File file = new File("downloaded_" + keyForDownload);
					file.createNewFile();
					FileOutputStream out = new FileOutputStream(file, true);
					out.write(videoPart.data, 0, videoPart.length);
					out.flush();
					out.close();

					System.out.println("Writing data converted...");

					videoPart = conversor.getVideoPart(keyForDownload);
				}

				System.out.println("Video sucessfuly downloaded...");

			} catch (Exception e) {

				e.printStackTrace();

			}

		} else if (comando.equals("convert")) {

			String key = args[2];
			int width = Integer.parseInt(args[3]);
			int height = Integer.parseInt(args[4]);
			int fps = Integer.parseInt(args[5]);
			boolean noAudio = Boolean.parseBoolean(args[6]);

			try {

				System.out.println("The video is being converted...");
				System.out.println("This may take several minutes...");

				String keyForDownload = conversor.convertVideo(key, width, height, fps, noAudio);

				if (keyForDownload != null) {

					System.out.println("Video converted with success! Now just get it!");
					System.out.println("Key for download of the converted video: " + keyForDownload);

				} else {

					System.err.println(
							"Something wrong happened into the process of conversion. Please contact Diego Sousa");

				}

			} catch (Exception e) {

				e.printStackTrace();

			}

		}

	}

}
