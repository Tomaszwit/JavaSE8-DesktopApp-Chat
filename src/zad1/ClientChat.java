package zad1;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * KONTROLER - uruchomiony przez klasę Client
 * 
 * 
 */
public class ClientChat extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = -5930960975767916628L;

	JPanel panel;
	
	private JTextField message = new JTextField();
	private JButton send = new JButton("send");
	
	private Client_model dane;
	private Client_view view;
	private ConnectionClient connection;
	
	ClientChat(String imie, String password, ConnectionClient con)
	{
		super("Klinet");
		panel = new JPanel();
		this.dane = new Client_model(new Person(imie,null,password));
		this.view = new Client_view();
		dane.addPropertyChangeListener(view);
		this.connection = con;
		
		//modyfikacja komponentów wew.
		send.addActionListener(this);
		
		panel.add(view.getChat());
		
		message.setColumns(25);
		panel.add(message);
		panel.add(send);
		
		
		add(panel);
		
		//modyfikacja właściwości okna.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(new Dimension(450,700));
		show();
		setName("Klient");
		//KOMUNIKACJA
		new Thread(() -> connection.startTalking(dane)).start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		//WYSŁANIE WIADOMOŚCI
		if(e.getSource().equals(send))
		{
			String message = this.message.getText();
			connection.send(message);
			Client_model.addToChat(dane.me, message);
			this.message.setText("");
		}
	}
}
