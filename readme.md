	 * 	       The syntax to call this program is presented in next description:
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
