package dataBoardProj;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dataBoardProjExceptions.*;

public class Board<E extends MyData> implements DataBoard<E> {
	/*
	 * Overview:
	 * 
	 * IR: username != null && recordData != null && categories != null
	 * 	   && password != null
	 * 	   && categories does not contains duplicates 
	 * 	   && friends is ordered 
	 * 
	 * Typica Element: <username, password, {data1, data2, ..., datan}, {category1, category2, ..., categoryn}
	 *     
	 */
	
	private String username;				//user id per ogni board
	private String password;				//password univoca per l'user
	private ArrayList<E> recordData;	    //collezione di dati	
	private ArrayList<Category> categories; //collezione di categorie
	
	//Costruttore, crea un'istanza della classe Databoard assegnando username e password
	public Board(String user, String passw) throws NullPointerException{
		/*
		 * @requires: user != null && passw != null
		 * @throws:	  se user == null || passw == null lancia NullPointerException (UNCHECKED)
		 * @effects:  
		 */
		if(user != null && passw != null)
		{
			this.username = user;
			this.password = passw;
			recordData= new ArrayList<E>();
			categories = new ArrayList<Category>();
		}
		else throw new NullPointerException("User e/o Password sono null");
		
	}

	@Override
	public void createCategory(String category, String passw) throws WrongPasswordException , NullPointerException {
		/*
		 * @requires:	category != null && passw != null && passw == this.password
		 * @throw:		se category != null || passw != null lancia NullPointerException(Unchecked)
		 * 				se passw != this.password 			 lancia WrongPasswordException(Checked)
		 * @modifies:	this.categories
		 * @effects:	this.categories_post = this.categories_pre U {category}
		 */
		if(category == null || passw == null)
			throw new NullPointerException("I parametri passati non devono essere null");
		if(passw == this.password)
			categories.add(new Category(category));
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void removeCategory(String category, String passw) throws WrongPasswordException , NullPointerException, CategoryNotPresentException, NotRemovableException, CloneNotSupportedException {
		/*
		 * @requires:	category != null && passw != null && passw == this.password && for each data in this.recordData => data.category != category
		 * @throw:		se category != null || passw != null lancia NullPointerException(Unchecked)
		 * 				se passw != this.password 			 lancia WrongPasswordException(Checked)
		 * 				se category is not in this.categories lancia CategoryNotPresentException(Checked)
		 * 				se esiste data in recordData t.c. data.category == category  lancia NotRemovableException(Checked)
		 * @modifies:	this.categories
		 * @effects:	this.categories_post = this.categories_pre \ {category}
		 */
		if(category == null || passw == null)
			throw new NullPointerException();
		if(passw == this.password)
		{
			Category cat = this.findCategory(category);
			if(cat == null)
				throw new CategoryNotPresentException(category + " non è presente nelle categorie della board.");
			for(E data:recordData )
				if(data.getCategory() == category)
					throw new NotRemovableException("Ci sono ancora dati nella board con questa categoria: non è possibile rimuoverla.");
			//se non è stato rimosso allora la categoria non è presente
				categories.remove(cat);	//rimuovo la categoria dalla lista
				cat = null;							//andrà nel garbage collector
		}
		else throw new WrongPasswordException("Password sbagliata.");

	}

	@Override
	public void addFriend(String category, String passw, String friend) throws WrongPasswordException, NoDuplicatesException, CategoryNotPresentException{
		/*
		 * @requires: passw != null && friend != null && category != null && this.password == passw
		 * 			  && categoria is in this.categories && friend is not in this.friends
		 * @throw:
		 * 			  se passw == null || categoria == null || friend == null lancia NullPointerException(UNCHECKED)
		 * 			  se passw != this.password 							lancia WrongPasswordException(CHECKED)
		 * 			  se categoria is not in this.categories				lancia CategoryNotPresentException(CHECKED)
		 * 			  se friend is not in this.friends						lancia NoDuplicatesException(CHECKED)
		 * @modifies:
		 * 			  this.categories[category].allowedFriends_post = this.categories[category].allowedFriends_pre U {friend}
		 * @effects:
		 * 			  aggiunge dato in this.recordData e restituisce 
		 * 			  true se l'inserimento è andato a buon fine, false altrimenti
		 */
		if(passw== null || category == null || friend == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata.");
		else {
				Category c = this.findCategory(category);
				if(c != null)
				{
					c.allowFriend(friend); //aggiungo l'amico alla categoria
					return;
				}
			throw new CategoryNotPresentException(category + " non è presente nella lista delle categorie");
		}

	}

	@Override
	public void removeFriend(String category, String passw, String friend) throws WrongPasswordException, FriendNotExistsException{
		/*
		 * @requires: passw != null && friend != null && category != null && this.password == passw
		 * 			  && categoria is in this.categories && friend is in this.friends
		 * @throw:
		 * 			  se passw == null || categoria == null || friend == null lancia NullPointerException(UNCHECKED)
		 * 			  se passw != this.password 							lancia WrongPasswordException(CHECKED)
		 * 			  se categoria is not in this.categories				lancia CategoryNotPresentException(CHECKED)
		 * 			  se friend is not in this.friends						lancia FriendNotExistsException(CHECKED)
		 * @modifies:
		 * 			  this_post.recordData = this_pre.recordData U {dato}
		 * @effects:
		 * 			  aggiunge dato in this.recordData e restituisce 
		 * 			  true se l'inserimento è andato a buon fine, false altrimenti
		 */
		if(passw== null || category == null || friend == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata.");
		else {
			Category c = this.findCategory(category);
				if(c != null)
				{	
					c.removeAlllowedFriend(friend);          //rimuovo l'amico alla categoria
					return;
				}	
		}
	}

	@Override
	public boolean put(String passw, String categoria, E dato) throws NullPointerException, WrongPasswordException, CategoryNotPresentException, NoDuplicatesException, CloneNotSupportedException {
		/*
		 * @requires: passw != null && categoria != null && dato != null && this.password == passw
		 * 			  && categoria is in this.categories && dato is not in this.recordData
		 * @throw:
		 * 			  se passw == null || categoria == null || dato == null lancia NullPointerException(UNCHECKED)
		 * 			  se passw != this.password 							lancia WrongPasswordException(CHECKED)
		 * 			  se categoria is not in this.categories				lancia CategoryNotPresentException(CHECKED)
		 * 			  se dato is in this.recordData							lancia AlreadyPresentException(CHECKED)
		 * @modifies:
		 * 			  this_post.recordData = this_pre.recordData U {dato}
		 * @effects:
		 * 			  aggiunge dato in this.recordData e restituisce 
		 * 			  true se l'inserimento è andato a buon fine, false altrimenti
		 */
		if(passw == null || categoria == null || dato == null)  //controllo non null
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata."); //controllo password
		Category c = this.findCategory(categoria);
		if(c == null)												 //controllo presenza della categoria
			throw new CategoryNotPresentException(categoria + "non è presente nelle categorie");
		boolean res = false;
			if(!this.recordData.contains(dato))  //se il dato non è nella board
			{
				dato.setCategory(categoria);	 //imposto la categoria del dato
				res = this.recordData.add(dato); //aggiungo il dato
			}	
			else
				throw new NoDuplicatesException(); 
			return res;									//restituisco l'esito dell'inserimento
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public E get(String passw, E dato) throws NullPointerException, WrongPasswordException, DataNotPresentException, CloneNotSupportedException {
		/*
		 * @requires: passw != null && dato != null && this.password == passw && dato is present in this.recordData
		 * 			 
		 * @throw:
		 * 			   se passw == null  || dato == null 			lancia NullPointerException(UNCHECKED)
		 * 			  se passw != this.password 					lancia WrongPasswordException(CHECKED)
		 * 			  se dato is not in recordData					lancia DataNotPresentException(CHECKED)	 
		 */
		if (passw == null || dato == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata.");
		for(E d: recordData)
		{
			if(d.equals(dato))
			{
				return (E) dato.clone();
			}
		}
		throw new DataNotPresentException("Dato non presente in bacheca");
	}


	@Override
	public E remove(String passw, E dato) throws NullPointerException, WrongPasswordException, DataNotPresentException, CloneNotSupportedException{
		/*
		 * @requires: passw != null && dato != null && this.password == passw && dato is present in this.recordData
		 * 			 
		 * @throw:
		 * 			   se passw == null  || dato == null 			lancia NullPointerException(UNCHECKED)
		 * 			  se passw != this.password 					lancia WrongPasswordException(CHECKED)
		 * 			  se dato is not in recordData					lancia DataNotPresentException(CHECKED)	 
		 */
		if (passw == null || dato == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata.");
		if(!recordData.contains(dato))
			throw new DataNotPresentException("Dato non presente in bacheca.");
		E cpy = this.get(passw, dato);   						//creo una copia dell'elemento
		recordData.remove(recordData.indexOf(dato));			//lo rimuovo
		return cpy;												//restituisco l'elemento rimosso
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException, CategoryNotPresentException, CloneNotSupportedException {
		if (passw == null || category == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata.");
		boolean trovato = false;
		for(Category c : categories)
		{
			if(c.getCategoryName() == category)
				trovato = true;
		}
		if(!trovato)
			throw new CategoryNotPresentException(category + "non è presente");
		List<E> res = new ArrayList<E>();
		
		for(E dato : recordData)
		{
			if(dato.getCategory() == category)
			{
				res.add((E) dato.clone());
				trovato = true;
				break;
			}
		}
		return res;
	}

	@Override
	public Iterator<E> getIterator(String passw) {
		Collections.sort(recordData, new SortByLike() );//ordino i dati per numero di like
		Iterator<E> it = Collections.unmodifiableList(recordData).iterator();//restituisco un iteratore di una lista senza remove
		return it;
	}

	@Override
	public void insertLike(String friend, E data) throws AlreadyLikedException, NullPointerException, FriendNotExistsException, CategoryNotPresentException, CloneNotSupportedException{
		/*
		 * @requires: friend != null && data != null && data.category is in this.categories && friend is in data.category.allowedFriends
		 * 
		 * @throw:	  se friend == null || data == null 				lancia NullPointerException(Unchecked)
		 * 			  se data.category is not in this.categories 		lancia CategoryNotPresentException
		 * 			  se fiend is not in data.category.allowedFriends 	lancia FriendNotExistsException
		 * 
		 * @modifies:this.data.likes
		 * @effects: this.data.likes_post = this.data.likes_pre + 1
		 */
		if(friend == null || data == null)
			throw new NullPointerException();
		String cat = data.getCategory();
		if(cat == null)
			throw new CategoryNotPresentException("La categoria del dato non è presente nelle categorie della board, prima devi aggiungere la categoria.");
		if(this.findCategory(cat).getAllowedFriends().contains(friend))
			data.addLike(friend);
		else throw new FriendNotExistsException(friend + " non ha accesso alla categoria del dato");
		
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterator<E> getFriendIterator(String friend) throws CloneNotSupportedException, NullPointerException{
		if(friend == null)
			throw new NullPointerException();
		ArrayList<E> a = new ArrayList<E>();
		for(E dato: recordData) {
			String catName = dato.getCategory();		//ottengo il nome della categoria
			Category cat = this.findCategory(catName);	//cerco la categoria di quel dato
			if(cat.getAllowedFriends().contains(friend))
				a.add((E) dato.clone()); //se l'amico ha accesso alla categoria aggiungo alla lista il dato
		}
		return Collections.unmodifiableList(a).iterator(); //restituisco l'iteratore senza remove
	}
	
	
	/* ALTRI METODI ///////////////*/

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	
	
	public Category findCategory(String name)
	{
		for(Category cat : this.categories)
		{
			if(cat.getCategoryName() == name)
				return cat;
		}
		return null;
	}

}


//Comparator che ordina in modo crescente i dati
class SortByLike implements Comparator<MyData> 
{ 
    public int compare(MyData a, MyData b) 
    { 
        return b.getLikes() - a.getLikes(); 
    } 
} 


