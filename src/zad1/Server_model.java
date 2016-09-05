package zad1;

import java.beans.*;
import java.io.Serializable;
import java.util.*;

public class Server_model implements Serializable{
	
	private static final long serialVersionUID = -2887749867647447257L;
	
	List<Person> persons;
	List<String> clients;
	static List<String> chat;
	
	 private static PropertyChangeSupport propertyChange;
	
	Server_model()
	{
		propertyChange = new PropertyChangeSupport(this);
		persons = new ArrayList<Person>();
		persons.add(new Person("Me",null, null));
		clients = new ArrayList<String>();
		chat = new ArrayList<String>();
	}
	
	
	synchronized void addClient(String mail, String address, String haslo)
	{
		Person p = new Person(mail, address, haslo);
		persons.add(p);
		clients.add(p.mail);
		propertyChange.firePropertyChange("client", null, clients);
	}
	
	void removeClient(String mail)
	{
		persons.remove(getClient(mail));
		clients.remove(mail);
		propertyChange.firePropertyChange("client", null, clients);
	}
	
	List<Person> getPersons()
	{
		return persons;
	}
	
	Person getClient(String mail)
	{	
		return persons.stream()
				.filter(e -> e.mail.equals(mail))
				.findFirst()
				.orElse(null);
	}
	
	boolean exists(String mail, String pass)
	{
		Person tmp = getClient(mail);
		if(tmp != null)
		{
			if(tmp.haslo.equals(pass))
				return true;
		}else
		{
			return false;
		}
		return false;
	}
	
	static void addToChat(Person p, String message)
	{
		int height = message.length() / 70 * 19;
		String text = "<html><body>";
			  text += "<div style='margin-bottom:6px;height:"+height+"'>";
			    text += "<div style='width:300px;color:7908AA;font:bold 14px'>"+p.mail + ":</div>";
			    text += message+"</div></body></html>";
		chat.add(text);
		propertyChange.firePropertyChange("chat", null, chat);
	}

	public synchronized void addPropertyChangeListener(PropertyChangeListener listener) {propertyChange.addPropertyChangeListener(listener);}
	public synchronized void removePropertyChangeListener(PropertyChangeListener listener) {propertyChange.removePropertyChangeListener(listener);}

	
	
	
	
	
	

}
