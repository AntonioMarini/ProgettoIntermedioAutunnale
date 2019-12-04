package dataBoardProj;

import java.util.ArrayList;
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
		
		String user = "Antonio";
		String password ="1234";
		Board<MyData> games = new Board<MyData>(user, password);
		
		//creo vari dati che andrò ad utilizzare nella board games
		MyData a = new MyData("Max Payne 3");
		MyData b = new MyData("Tomb Raider");
		MyData c = new MyData("The Elder Scrolls V: Skyrim");
		MyData d = new MyData("The Witcher 3: Wild Hunt");
		MyData f = new MyData("DragonBall Z: Budokai Tenkaichi 3");
		MyData g = new MyData("Uncharted 4");
		MyData h = new MyData("Death Stranding");
		MyData i = new MyData("Tekken 3");
		
		System.out.println("*****************OUTPUT DI BOARD *******************");		
		
		//CREAZIONE CATEGORIE
		try {
			games.createCategory("Azione", "1234");
			games.removeCategory("Azione", "1234");
			games.createCategory("Azione", "1234");
			games.createCategory("Avventura", "1234");
			games.createCategory("RPG", "1234");
			games.createCategory("Picchiaduro", "1234");
			
			
		} catch (NullPointerException | WrongPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CategoryNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotRemovableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//AGGIUNGO E RIMUOVO AMICI
	    try {
			games.addFriend("Azione", "1234", "Giuseppe");
			games.addFriend("Azione", "1234", "Martina");
			games.addFriend("Picchiaduro", "1234", "Daniele");
			games.addFriend("Avventura", "1234", "Salvatore");
            games.removeFriend("Avventura", "1234", "Salvatore");
		} catch (WrongPasswordException | NoDuplicatesException | CategoryNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FriendNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    //AGGIUNGO, LEGGO E RIMUOVO I DATI DALLA BOARD
	    try {
			games.put("1234", "Azione", a );
			games.put("1234", "Avventura",  b);
			games.put("1234", "RPG", c );
			games.put("1234", "RPG", d );
			games.put("1234", "Picchiaduro", f );
			games.put("1234", "Avventura", g );
			games.put("1234", "Avventura", h );
			games.put("1234", "Picchiaduro", i );
			games.remove("1234", h);			//posso rimuovere un dato da una categoria e rimetterlo in un'altra
			games.put("1234", "Azione", h );
			//games.get("1234", a).display();
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException
				| NoDuplicatesException | DataNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //PROVO getDataCategory()
	    try {
			ArrayList<MyData> listAdventure = (ArrayList<MyData>) games.getDataCategory("1234", "Avventura");
			//for(MyData dato : listAdventure)
			//	dato.display();
			
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//TEST SECONDA IMPLEMENTAZIONE
	    
	    System.out.println("*****************OUTPUT DI BOARD 2******************");		
		password ="1234";
		Board2<MyData> games2 = new Board2<MyData>(password);
		
		//creo vari dati che andrò ad utilizzare nella board games
				MyData a2 = new MyData("Max Payne 3");
				MyData b2 = new MyData("Tomb Raider");
				MyData c2 = new MyData("The Elder Scrolls V: Skyrim");
				MyData d2 = new MyData("The Witcher 3: Wild Hunt");
				MyData f2 = new MyData("DragonBall Z: Budokai Tenkaichi 3");
				MyData g2 = new MyData("Uncharted 4");
				MyData h2 = new MyData("Death Stranding");
				MyData i2 = new MyData("Tekken 3");
		
		//CREAZIONE CATEGORIE
		try {
			games2.createCategory("Azione", "1234");
			games2.removeCategory("Azione", "1234");
			games2.createCategory("Azione", "1234");
			games2.createCategory("Avventura", "1234");
			games2.createCategory("RPG", "1234");
			games2.createCategory("Picchiaduro", "1234");
			
			
		} catch (NullPointerException | WrongPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoDuplicatesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CategoryNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//AGGIUNGO E RIMUOVO AMICI
	    try {
			games2.addFriend("Azione", "1234", "Giuseppe");
			games2.addFriend("Picchiaduro", "1234", "Martina");
			games2.addFriend("RPG", "1234", "Giovanni");
			games2.addFriend("Picchiaduro", "1234", "Daniele");
			games2.addFriend("Avventura", "1234", "Salvatore");
			games2.removeFriend("RPG", "1234", "Giovanni");
		} catch (WrongPasswordException | NoDuplicatesException | CategoryNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FriendNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotRemovableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    //AGGIUNGO, LEGGO E RIMUOVO I DATI DALLA BOARD
	    try {
			games2.put("1234", "Azione", a2 );
			games2.put("1234", "Avventura",  b2);
			games2.put("1234", "RPG", c2 );
			games2.put("1234", "RPG", d2 );
			games2.put("1234", "Picchiaduro", f2 );
			games2.put("1234", "Avventura", g2 );
			games2.put("1234", "Avventura", h2 );
			
			games2.remove("1234", a2);
			games2.put("1234", "Azione", a2 );
			//games2.get("1234", a2).display();
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException
				| NoDuplicatesException | DataNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    //PROVO getDataCategory()
	    try {
			ArrayList<MyData> listAdventure = (ArrayList<MyData>) games2.getDataCategory("1234", "Avventura");
			//for(MyData dato : listAdventure)
			//	dato.display();
			
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    		
	        try {
	        	games2.insertLike("Gianni", a2);
				Iterator<MyData> it = games2.getIterator("1234");
				while(it.hasNext())
					it.next().display();
			} catch (NullPointerException | WrongPasswordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AlreadyLikedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FriendNotExistsException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CategoryNotPresentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataNotPresentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    
	}
}
