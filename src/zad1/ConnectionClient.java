package zad1;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ConnectionClient {
	InetSocketAddress hostAddress;
	BufferedReader br;
	String toServer,fromServer;
	SocketChannel clientChannel;
	Client_model dane;
	ByteBuffer bufferIn;
	
	public ConnectionClient(String mail, String pass) {
		toServer = "";
		
		hostAddress = new InetSocketAddress("localhost", 4500);
		try
		{
			clientChannel = SocketChannel.open(hostAddress);
			AcceptSignIn.accept(mail,pass, clientChannel);
		} catch (IOException e) {e.printStackTrace();}
			
	}
	
	SocketChannel getChannel()
	{
		return clientChannel;
	}

	public void startTalking(Client_model model) 
	{
		dane = model;//Start talking to model!
		bufferIn = ByteBuffer.allocate(256);
		br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("ConnectionClient: i'm start talking");
		
		try{
		if(AcceptSignIn.online)//TYLKO ZALOGOWANY UŻYTKOWNIK MOŻE KOMUNIKOWAĆ SIĘ Z SERWEREM
			for (;;) {
				//Send Message
				if(toServer.length() > 0)
				{
					byte[] message = toServer.getBytes();
					ByteBuffer buffer = ByteBuffer.wrap(message);
					clientChannel.write(buffer);
					buffer.clear();
					if (toServer.equals("Bye."))
						break;
					toServer = "";
				}

				clientChannel.configureBlocking(false);
				
				//Receive message
				if (clientChannel.read(bufferIn) > 0) 
				{
					bufferIn.flip();
					CharBuffer respond = Charset.forName("UTF-8").decode(bufferIn);
					fromServer = respond.toString();
					Client_model.addToChat(dane.server, fromServer.toString());
						if (fromServer.equals("Bye.")) 
							break;
						bufferIn.clear();
				}
		
				try {Thread.sleep(500);} catch (InterruptedException e) {System.err.println(e.getStackTrace());}
			}
		}catch(IOException ex){System.err.println(ex.getMessage());}
	}

	public void send(String message) 
	{
		toServer = message;
	}

}
