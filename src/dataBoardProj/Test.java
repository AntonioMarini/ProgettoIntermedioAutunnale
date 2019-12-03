package dataBoardProj;

import java.util.Iterator;

import dataBoardProjExceptions.AlreadyLikedException;
import dataBoardProjExceptions.CategoryNotPresentException;
import dataBoardProjExceptions.DataNotPresentException;
import dataBoardProjExceptions.FriendNotExistsException;
import dataBoardProjExceptions.NoDuplicatesException;
import dataBoardProjExceptions.NotRemovableException;
import dataBoardProjExceptions.WrongPasswordException;

public class Test {

	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		
		Board<MyData> antonio = new Board<MyData>("Apollyon", "1234");
		MyData frutta1 = new MyData("Mele");
		MyData frutta2 = new MyData("Banane"); 
		MyData carne1 = new MyData("Macinato di Maiale");
		MyData carne2 = new MyData("Petto di pollo");
		
		try {
			antonio.createCategory("Frutta", "1234");
			antonio.createCategory("Verdura", "1234");
			antonio.createCategory("Macelleria", "1234");
			antonio.createCategory("Pesce", "1234");
			antonio.createCategory("Cosmetici", "1234");
			

		} catch (NullPointerException | WrongPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
		
		//INSERIMENTO PRODOTTI NELLA BOARD
		try {
			antonio.put("1234", "Frutta", frutta1);
			antonio.put("1234", "Frutta", frutta2);
			antonio.put("1234", "Macelleria", carne1);
			antonio.put("1234", "Macelleria", carne2);
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException
				| NoDuplicatesException e1) {
			e1.printStackTrace();
		}
		
		
		//AGGIUNTA DI AMICI
		try {
			antonio.addFriend("Frutta", "1234", "peppino");
			antonio.addFriend("Frutta", "1234", "gigi");
			antonio.addFriend("Macelleria", "1234", "gigi");
		} catch (WrongPasswordException | NoDuplicatesException | CategoryNotPresentException e) {

			e.printStackTrace();
		}
		
		
	/*	//RIMOZIONE DI CATEGORIE
		try {
			antonio.removeCategory("Verdura", "1234");
			antonio.removeCategory("Pesce", "1234");
			antonio.removeCategory("Verdura", "1234");
			Iterator<MyData> it = antonio.getIterator("1234");
			while (it.hasNext())
			{
				it.next().display();
			}
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException | NotRemovableException  e1) {
			e1.printStackTrace();
		}
		
		//METTO LIKE E MOSTRO TRAMITE L'ITERATORE I DATI CON IL METODO display()
		try {
			antonio.insertLike("peppino", frutta1);
			antonio.insertLike("gigi", frutta1);
			
		} catch (NullPointerException | AlreadyLikedException | FriendNotExistsException
				| CategoryNotPresentException e) {

			e.printStackTrace();
		}*/
		
		
		
	   //TEST DEL METODO get(passw, data)
		try {
			MyData f = antonio.get("1234", frutta1);
			f.addLike("peppino");
			f.addLike("gaiiai");
			f.display();
			Iterator<MyData> it = antonio.getIterator("1234");
			while (it.hasNext())
			{
				it.next().display();
			}
			
		} catch (NullPointerException | WrongPasswordException | DataNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AlreadyLikedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		
		
	    
		
		
	}

}
