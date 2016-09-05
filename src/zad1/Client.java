/**
 *
 *  @author Witkowski Tomasz S12678
 *
 */

package zad1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Client {
	static ConnectionClient connection;
	static ClientChat clientChat;
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(5, 1, 5, 5));

		JTextField mail = new JTextField();
		mail.setColumns(15);
		JButton SignIn = new JButton("Sign in");

		JTextField address = new JTextField();
		address.setColumns(19);
		JTextField haslo = new JTextField();
		haslo.setColumns(20);

		panel.add(new JLabel("mail"));
		panel.add(mail);
		panel.add(new JLabel("password"));
		panel.add(haslo);
		panel.add(SignIn);

		SignIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				if (e.getSource().equals(SignIn))
				{
					String _mail = mail.getText();
					String _password = haslo.getText();
					
					connection = new ConnectionClient(_mail,_password);
					if(!AcceptSignIn.online)
					{
						JOptionPane.showMessageDialog(new Frame(), "Nieprawidłowe dane logowania.");
					}else//TYLKO JEŚLI UŻYTKOWNIK SIĘ ZALOGOWAŁ WYŁĄCZ OKNO
					{
						frame.dispose();
						clientChat = new ClientChat(_mail,_password, connection);
					}
					
					
				}
				
			}
		});
		
		frame.add(panel);
		frame.pack();
		frame.setVisible(true);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
