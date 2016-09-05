package zad1;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.swing.JList;

public class Client_view implements PropertyChangeListener, Serializable{
	private static final long serialVersionUID = 6748751167564487298L;
	private JList<String> chat;
	
	Client_view()
	{
		chat = new JList<String>();
		chat.setPreferredSize(new Dimension(400, 600));
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{		
		if(evt.getPropertyName().equals("chat"))
		{
			setChat((List<String>)evt.getNewValue());
		}

	}
	
	void setChat(List<String> chat)
	{
		this.chat.setListData(Arrays.copyOf(chat.toArray(), chat.size(), String[].class));
	}
	
	Component getChat()
	{
		return chat;
	}
	
}
