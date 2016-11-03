package com.app;

import java.util.ArrayList;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Remainder {

	
	@Persistent
	@PrimaryKey
	private String email;
	
	
	@Persistent
	private ArrayList<String> Title=new ArrayList<>();
	@Persistent
	private ArrayList<String> Message=new ArrayList<>(); 
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public ArrayList<String> getTitle() {
		return Title;
	}

	public void setTitle(ArrayList<String> Title) {
	this.Title=Title;
	}

	public ArrayList<String> getMessage() {
		return Message;
	}

	public void setMessage(ArrayList<String> Message) {
		this.Message=Message;
	}
	public void addTitle(String title)
	{
		Title.add(title);
	}
	public void addMessage(String message)
	{
		Message.add(message);
	}
	
	public void removeTitle(int position)
	{
		Title.remove(position);
		
	}
	
	public void removeMessage(int position)
	{
		Message.remove(position);
		
	}
	
}
