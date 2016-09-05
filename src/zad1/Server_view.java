package zad1;

import java.awt.*;
import java.beans.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;

import javax.swing.*;

public class Server_view implements PropertyChangeListener, Serializable{
	private JList<String> chat;
	private JList<String> list;
	
	Server_view()
	{
		chat = new JList<String>();
		list = new JList<String>();
		
		chat.setPreferredSize(new Dimension(400, 600));
		list.setPreferredSize(new Dimension(300, 500));	
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt)
	{		
		if(evt.getPropertyName().equals("chat"))
		{
			setChat((List<String>)evt.getNewValue());
		}
		else if(evt.getPropertyName().equals("client"))
		{
			setList((List<String>)evt.getNewValue());
		}

	}
	
	void setList(List<String> list)
	{
		System.out.println("setList:"+list);
		this.list.setListData(Arrays.copyOf(list.toArray(), list.size(), String[].class));
	}
	
	void setChat(List<String> chat)
	{
		this.chat.setListData(Arrays.copyOf(chat.toArray(), chat.size(), String[].class));
	}
	
	Component getChat()
	{
		return chat;
	}
	
	Component getList()
	{
		return list;
	}
	
	String getSelectedList()
	{
		return this.list.getSelectedValue();
	}
}
