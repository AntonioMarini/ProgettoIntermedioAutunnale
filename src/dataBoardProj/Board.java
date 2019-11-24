package dataBoardProj;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Collections;
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
	
	private String username;
	private String password;
	private Vector<E> recordData;
	private Vector<Category> categories;
	private Vector<String> friends;
	
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
			friends = new Vector<String>();
			categories = new Vector<Category>();
		}
		else throw new NullPointerException("User e/o Password sono null");
		
	}

	@Override
	public void createCategory(String category, String passw) throws WrongPasswordException {
		// TODO Auto-generated method stub
		if(passw == this.password)
			categories.add(new Category(category));
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void removeCategory(String category, String passw) throws WrongPasswordException {
		// TODO Auto-generated method stub
		if(passw == this.password)
			categories.remove(categories.indexOf(category));
		else throw new WrongPasswordException("Password sbagliata.");

	}

	@Override
	public void addFriend(String category, String passw, String friend) throws WrongPasswordException, DuplicateFriendException{
		// TODO Auto-generated method stub
		if(passw == this.password)
			throw new WrongPasswordException("Password sbagliata.");
		if(this.friends.contains(friend))
			throw new DuplicateFriendException("L'amico è già presente nella lista.");
		else {
			this.friends.add(friend); //inserisce l'elemento in coda
			for(int i = 0; i < this.categories.size(); i++)
			{
				if(categories.get(i).getCategoryName() == category)
					categories.get(i).allowFriend(friend);          //aggiungo l'amico alla categoria
			}
			Collections.sort(this.friends);  	//riordina il vettore cosi da mantenere l'invariante
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
		if(this.friends.contains(friend))
			throw new FriendNotExistsException("L'amico da rimuovere non è presente nella lista.");
		else {
			this.friends.removeElement(friend);		//rimuove friend mantenendo l'ordine
			for(int i = 0; i < this.categories.size(); i++)
			{
				if(categories.get(i).getCategoryName() == category)
					categories.get(i).remove(friend);          //aggiungo l'amico alla categoria
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
		if(!this.categories.contains(categoria))
			throw new CategoryNotPresentException();
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
			return (E) dato.clone();
		}
		
	}

	@Override
	public E remove(String passw, E dato) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> getDataCategory(String passw, String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> getIterator(String passw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertLike(String friend, E data){
		// TODO Auto-generated method stub

	}

	@Override
	public Iterator<E> getFriendIterator(String friend) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

}
