package zad1;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/*
 * CONTROLER
 * 
 */

public class Server_GUI extends JFrame implements ActionListener{
	private static final long serialVersionUID = -141776448537477718L;
	JPanel panel;
	
	private JTextField message = new JTextField();
	private JButton send = new JButton("send");
	
	private JButton addClient = new JButton("Add");
	private JButton removeClient = new JButton("Remove");
	
	private Server_model dane;
	private Server_view view;
	private MultiConnectionServer mcs;
	
	Server_GUI(Server_model dane, Server_view view)
	{
		super("Server");
		panel = new JPanel();
		this.dane = dane;
		this.view = view;
		
		//modyfikacja komponentów wew.
		send.addActionListener(this);
		addClient.addActionListener(this);
		removeClient.addActionListener(this);
		
		panel.add(view.getList());
		panel.add(view.getChat());
		
		panel.add(addClient);
		panel.add(removeClient);
		
		message.setColumns(25);
		panel.add(message);
		panel.add(send);
		
		
		add(panel);
		
		//modyfikacja właściwości okna.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(new Dimension(750,700));
		show();
		setName("Server");
		//KOMUNIKACJA
		
		 new Thread(() -> new MultiConnectionServer(dane)).start();
	}


	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		//DODAWANIE KLIENTA
		if(e.getSource().equals(addClient))
		{
			JFrame frame = new JFrame();
			JPanel panel = new JPanel();
			
			panel.setLayout(new GridLayout(4,2,5,5));
			
			JButton add = new JButton("Add");
			JTextField mail = new JTextField();
			mail.setColumns(15);
			JTextField address = new JTextField();
			address.setColumns(19);
			JTextField haslo = new JTextField();
			haslo.setColumns(20);
			
			panel.add(new JLabel("mail"));
			panel.add(mail);
			panel.add(new JLabel("address ip"));
			panel.add(address);
			panel.add(new JLabel("password"));
			panel.add(haslo);
			panel.add(add);
			
			add.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					dane.addClient(mail.getText(), address.getText(), haslo.getText());
					frame.dispose();
				}
			});

			frame.add(panel);
			frame.pack();
			frame.setVisible(true);
			panel.setBorder(new EmptyBorder(10,10,10,10));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		}
		
		
		//USUWANIE KLIENTA
		if(e.getSource().equals(removeClient))
		{
			dane.removeClient(view.getSelectedList());
		}
		
		
		//WYSŁANIE WIADOMOŚCI
		if(e.getSource().equals(send))
		{
			String message = this.message.getText();
			MultiConnectionServer.send(message);
			dane.addToChat(dane.getClient("Me"), message);
			this.message.setText("");
		}
		
		
	}
	
	
	
}
