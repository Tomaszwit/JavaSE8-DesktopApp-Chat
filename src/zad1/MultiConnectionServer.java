package zad1;
import java.nio.channels.*;
import java.nio.*;
import java.io.*;
import java.util.*;
import java.net.*;
import java.util.concurrent.locks.*;

public class MultiConnectionServer {
	
	Selector selector;
	private String fromClient = "";
	private static String toClient = "";
	public MultiConnectionServer(Server_model model)
	{
		ByteBuffer bufferSendNO = ByteBuffer.wrap("0".getBytes());
		ByteBuffer bufferSendYES = ByteBuffer.wrap("1".getBytes());
		
		try {
			//ODBIERANIE PRZYCHODZĄCYCH POŁĄCZEŃ
			ServerSocketChannel serverSocket = ServerSocketChannel.open();
			InetSocketAddress hostAddress = new InetSocketAddress("localhost", 4500);
			serverSocket.socket().bind(hostAddress);
			serverSocket.configureBlocking(false);
			
			//REJESTROWANIE PRZYCHODZĄCYCH POŁĄCZEŃ DO SELEKTORA
			selector = Selector.open();
			System.out.println("Selector open: " + selector.isOpen());
			SelectionKey selectKy = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
			
			for(;;)
			{
				//System.out.println("Waiting for any operation in channels...");
				int noOfKeys = selector.select();
				//System.out.println("Number of selected keys:" + noOfKeys);
				
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectedKeys.iterator();
				
				ByteBuffer bufferIn = null;
				
				
				while(iter.hasNext())
				{
					if(bufferIn != null)
						bufferIn.flip();
					
					SelectionKey ky = (SelectionKey) iter.next();
					iter.remove();
					
					if(ky.isAcceptable())
					{
						// Accept the new client connection
						SocketChannel client = serverSocket.accept();
						client.configureBlocking(false);
						
						// Add the new connection to the selector
						client.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, null);
					}
					else if(ky.isReadable())
					{
						//Read the data from client
						SocketChannel client = (SocketChannel) ky.channel();
						ByteBuffer buffer = ByteBuffer.allocate(256);
						
						//czy coś przyszło od klienta?
						if(client.read(buffer) <= 0) return;
						String fromClient = new String(buffer.array()).trim();
						
						
						//CZY JEST TO ZAPYTANIE O ZALOGOWANIE?
						if(fromClient.length() > 7 && fromClient.substring(0, 7).equals("SIGNIN:"))
						{
							
							String email = fromClient.split(":")[1].split(";")[0];
							String haslo = fromClient.split(";")[1];
							
							if(model.exists(email,haslo))
							{
								client.write(bufferSendYES);
								Person p = model.getClient(email);
								ky.attach(p);
							}else
							{
								client.write(bufferSendNO);
								ky.cancel(); //odłącz klienta, żeby mógł ustanowić ponownie nowe połączenie z socketu.
							}
						}
						else //WIĘC OTRZYMALISMY NORMALNA WIADOMOSC OD KLIENTA
						{	
							Server_model.addToChat((Person)ky.attachment(), fromClient);
							
							if(fromClient.equals("Bye."))
							{
								ky.cancel(); // = client.close(); zamknięcie gniazda i selektora.
								System.out.println("Client message - to close.");
							}
						}
						//zwolnij z odświeżaniem.
						try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
					}
					else if(ky.isWritable())
					{
						//Send the data to client
						if(toClient.length() > 0)
						{
							SocketChannel client = (SocketChannel) ky.channel();
							
							bufferIn = ByteBuffer.wrap(toClient.getBytes());
							client.write(bufferIn);
							bufferIn.flip();
						
							if(toClient.equals("Bye."))
							{
								ky.cancel(); // = client.close(); zamknięcie gniazda i selektora.
								System.out.println("Server message - to close.");
							}
							
							toClient = "";
						}
					}
					
					if(bufferIn != null && !iter.hasNext())
						bufferIn.clear();
				}
			}
			
		} catch (IOException e) {e.printStackTrace();}
	}
	
	static void send(String text)
	{
		//lock.readLock().lock();
		toClient = text;
		//lock.readLock().unlock();
	}
	
	

}
