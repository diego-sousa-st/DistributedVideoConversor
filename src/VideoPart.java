import java.io.Serializable;

/**
 * 
 * @author <a href="mailto:diegosousa.st@gmail.com">Diego José de Sousa Gouveia</a>
 * 
 */
public class VideoPart implements Serializable {

	private static final long serialVersionUID = 1L;	

	String videoName;
	byte data[];
	int length;

	public VideoPart(String name, byte data[], int length) {
		this.videoName = name;
		this.data = data;
		this.length = length;
	}

}
