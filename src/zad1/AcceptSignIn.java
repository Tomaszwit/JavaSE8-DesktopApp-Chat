package zad1;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;

public class AcceptSignIn {
	public static boolean online = false;

	public static boolean accept(String email, String password, SocketChannel sc) 
	{
			ByteBuffer bufferIn = ByteBuffer.allocate(4);
			try 
			{
			//Send Message
			String message = "SIGNIN:" + email + ";" + password;
			ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
			sc.write(buffer);
				
			//Receive Message
			sc.read(bufferIn);
			
			bufferIn.flip();
			CharBuffer respond = Charset.forName("UTF-8").decode(bufferIn);
			System.out.println("LogIn:" + respond);
			if (respond.toString().equals("1"))
				online = true;
			} catch (IOException e) {e.printStackTrace();}
			System.out.println("online:"+online);
		return online;
	}

}
