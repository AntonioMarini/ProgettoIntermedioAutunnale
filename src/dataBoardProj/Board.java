package dataBoardProj;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dataBoardProjExceptions.*;

public class Board<E extends MyData> implements DataBoard<E> {
	/*
	 * Overview:
	 * 
	 * IR: username != null && recordData != null && categories != null && friends != null 
	 * 	   && password != null
	 * 	   && categories does not contains duplicates && friends does not contains duplicates
	 * 	   && friends is ordered 
	 *     
	 */
	
	private String username;				//user id per ogni board
	private String password;
	private Vector<E> recordData;			//collezione di dati
	private Vector<Category> categories;
	
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
			recordData= new Vector<E>();
			categories = new Vector<Category>();
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
			throw new NullPointerException("category e/o passw devono essere non null");
		if(passw == this.password)
			categories.add(new Category(category));
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void removeCategory(String category, String passw) throws WrongPasswordException , NullPointerException, CategoryNotPresentException {
		/*
		 * @requires:	category != null && passw != null && passw == this.password
		 * @throw:		se category != null || passw != null lancia NullPointerException(Unchecked)
		 * 				se passw != this.password 			 lancia WrongPasswordException(Checked)
		 * @modifies:	this.categories
		 * @effects:	this.categories_post = this.categories_pre \ {category}
		 */
		if(passw == this.password)
		{
			boolean rimosso = false;
			for(Category cat : categories)
				if(cat.getCategoryName() == category)
				{
					rimosso = categories.remove(cat);	//rimuovo la categoria dalla lista
					cat = null;							//andrà nel garbage collector
				}
			if(rimosso == false)
				throw new CategoryNotPresentException();//se non è stato rimosso allora la categoria non è presente
				
		}
		else throw new WrongPasswordException("Password sbagliata.");

	}

	@Override
	public void addFriend(String category, String passw, String friend) throws WrongPasswordException, DuplicateFriendException{
		// TODO Auto-generated method stub
		if(passw == this.password)
			throw new WrongPasswordException("Password sbagliata.");
		else {
			for(Category c : categories)
			{
				if(c.getCategoryName() == category)
					c.allowFriend(friend);          //aggiungo l'amico alla categoria
			}
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
		if(passw == this.password)
			throw new WrongPasswordException("Password sbagliata.");
		else {
			for(Category c : categories)
			{
				if(c.getCategoryName() == category)
					c.remove(friend);          //rimuovo l'amico alla categoria
			}
		}
	}

	@Override
	public boolean put(String passw, String categoria, E dato) throws NullPointerException, WrongPasswordException, CategoryNotPresentException, AlreadyPresentException {
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
		if(passw == null || categoria == null || dato == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password sbagliata.");
		boolean trovato = false;
		for(Category c: categories)
		{
			if(c.getCategoryName() == categoria)
				trovato = true;
		}
		if(!trovato)
			throw new CategoryNotPresentException(categoria + "non è presente nelle categorie");
		if(this.recordData.contains(dato))
			throw new AlreadyPresentException();
		
		
	    boolean res = this.recordData.add(dato);    //passati tutti i controlli aggiungo il dato
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
		if(!recordData.contains(dato))
			throw new DataNotPresentException("Dato non presente in bacheca.");
		else
		{
			E cpy = (E) recordData.elementAt(recordData.indexOf(dato)).clone();
			return cpy;
		}
		
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
			if(dato.getCategory().getCategoryName() == category)
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
		Collections.sort(recordData, new SortByLike() );
		Iterator<E> it = recordData.iterator();
		return it;
	}

	@Override
	public void insertLike(String friend, E data) throws AlreadyLikedException{
		data.addLike(friend);
	}

	@Override
	public Iterator<E> getFriendIterator(String friend) {
		ArrayList<E> a = new ArrayList<E>();
		for(E dato: recordData) {
			if(dato.getCategory().getAllowedFriends().contains(friend))
				a.add(dato);
		}
		return a.iterator();
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

}

class SortByLike implements Comparator<MyData> 
{ 
    // Used for sorting in ascending order of 
    // roll number 
    public int compare(MyData a, MyData b) 
    { 
        return a.getLikes() - b.getLikes(); 
    } 
} 
