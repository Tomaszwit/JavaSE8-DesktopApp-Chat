package zad1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Client_model implements Serializable{
	
	private static final long serialVersionUID = -8007202941769460414L;
	Person me;
	Person server;
	static List<String> chat;
	
	 private static PropertyChangeSupport propertyChange;
	
	Client_model(Person person)
	{
		propertyChange = new PropertyChangeSupport(this);
		chat = new ArrayList<String>();
		me = person;
		server = new Person("Server",null,null);
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
