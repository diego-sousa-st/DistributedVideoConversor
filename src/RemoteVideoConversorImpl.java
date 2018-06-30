import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * 
 * @author <a href="mailto:diegosousa.st@gmail.com">Diego José de Sousa Gouveia</a>
 * 
 */
public class RemoteVideoConversorImpl implements RemoteVideoConversor, Serializable {

	String key;

	String keyForDownload;
	File file;
	FileInputStream in;
	byte[] mydata;
	int mylen;

	private static final long serialVersionUID = 1L;

	@Override
	public String sendVideoPart(VideoPart videoPart) throws RemoteException {

		key = key == null ? Long.toString(System.nanoTime()) + "_" + videoPart.videoName : key;

		try {

			File file = new File(key);
			file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, true);
			out.write(videoPart.data, 0, videoPart.length);
			out.flush();
			out.close();

			System.out.println("Writing data...");	

		} catch (Exception e) {

			e.printStackTrace();

		}

		return key;
	}

	@Override
	public VideoPart getVideoPart(String key) throws RemoteException {

		try {

			if (keyForDownload == null) {

				keyForDownload = key;
				file = new File(keyForDownload);
				in = new FileInputStream(file);
				mydata = new byte[1024 * 1024];

			}

			mylen = in.read(mydata);

			if (mylen < 0) {

				in.close();
				file = null;
				keyForDownload = null;
				in = null;
				return null;

			}

			return new VideoPart(keyForDownload, mydata, mylen);

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		}

	}

	@Override
	public String convertVideo(String key, int width, int height, int fps, boolean noAudio) throws RemoteException {

		String newKey = null;

		try {

			Runtime runTime = Runtime.getRuntime();

			String comando = "ffmpeg -i " + key + " -r " + fps + " -s " + width + "x" + height
					+ " -aspect 16:9 -vcodec libx264 -strict -2 ";

			if (noAudio) {

				comando += "-an ";

			}

			newKey = "converted_" + Long.toString(System.nanoTime()) + "_" + key; 

			comando += newKey;

			System.out.println("Executing: "+comando);

			Process processo = runTime.exec(comando);
			processo.waitFor();

			return newKey;

		} catch (InterruptedException e) {

			e.printStackTrace();
			System.err.println("Interruption error!");

		} catch (IOException e) {

			e.printStackTrace();
			System.err.println("IO error!");

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;

	}

}
