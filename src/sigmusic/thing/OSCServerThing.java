package sigmusic.thing;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortOut;

public class OSCServerThing {
	private int port;
	private InetAddress address;
	private OSCPortOut server;
	public static String pitchRoute = "/pitch";
	
	/**
	 * 
	 * @param p
	 *            The port to listen on
	 * @param address
	 *            The address, pass "" for localhost
	 */
	public OSCServerThing(int p, String a) {
		port = p;
		String addressStr = a;
		try {
			if (addressStr.compareTo("") == 0) {
				address = InetAddress.getLocalHost();
			} else {
				InetAddress.getByName(addressStr);
			}
			
			server = new OSCPortOut(address, port);
			System.out.println("Listening on " + addressStr + ":" + port);
		} catch (UnknownHostException e) {
			System.out.println("Couldn't resolve address.");
			e.printStackTrace();
		} catch (SocketException e) {
			System.out.println("Couldn't open socket.");
			e.printStackTrace();
		}
	}
	
	public void sendPitch(float pitch) {
		OSCMessage routeMessage = new OSCMessage(pitchRoute);
		routeMessage.addArgument(pitch);
		try {
			server.send(routeMessage);
			System.out.println("Sent pitch " + pitch);
		} catch (IOException e) {
			System.out.println("Failed while sending");
			e.printStackTrace();
		}
	}
}
