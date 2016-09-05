package zad1;

public class Person {
	String mail, haslo, address;
	
	Person(String mail, String address, String haslo)
	{
		this.mail = mail;
		this.address = address;
		this.haslo = haslo;
	}
	
	
	@Override
	public String toString()
	{
		return mail;
	}
}
