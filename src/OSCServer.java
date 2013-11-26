



import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.utility.OSCBundle;
import com.illposed.osc.utility.OSCMessage;
import com.illposed.osc.utility.OSCPortOut;


public class OSCServer {
	private int port;
	private InetAddress address;
	private OSCPortOut server;
	private static String pitchRoute = "/pitch";
	private static String volumeRoute = "/volume";
	public int pitch;
	
	/**
	 * 
	 * @param p
	 *            The port to listen on
	 * @param a
	 *            The address, pass "" for localhost
	 */
	public OSCServer(int p, String a) {
		port = p;
		try {
			if (a.equals("")) {
				address = InetAddress.getLocalHost();
			} else {
				address=InetAddress.getByName(a);
			}
			
			server = new OSCPortOut(address, port);
			System.out.println("Listening on " + a + ":" + port);
		} catch (UnknownHostException e) {
			System.out.println("Couldn't resolve address.");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Couldn't open socket.");
			e.printStackTrace();
		}
	}
	
	//bundles pitch and volume message, then sends it to pureData
	public void sendPitch(float pitch, float vol) {
		OSCBundle bundle = new OSCBundle(); 
		OSCMessage routeMessage = new OSCMessage(pitchRoute);
		OSCMessage routeMessage2 = new OSCMessage(volumeRoute);
		routeMessage.addArgument(pitch);
		routeMessage2.addArgument(vol);
		bundle.addPacket(routeMessage);
		bundle.addPacket(routeMessage2);
		
		try {
			server.send(bundle);
			this.pitch = (int)pitch;
		} catch (IOException e) {
			System.out.println("Failed while sending");
			e.printStackTrace();
		}
	}
}
