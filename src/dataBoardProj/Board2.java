package dataBoardProj;

import java.util.*;

import dataBoardProjExceptions.AlreadyLikedException;
import dataBoardProjExceptions.CategoryNotPresentException;
import dataBoardProjExceptions.DataNotPresentException;
import dataBoardProjExceptions.FriendNotExistsException;
import dataBoardProjExceptions.NoDuplicatesException;
import dataBoardProjExceptions.NotRemovableException;
import dataBoardProjExceptions.WrongPasswordException;

public class Board2<E extends MyData> implements DataBoard<E> {
	
	/*
	 * @overview:			Board2 contiene oggetti generici E che estendono MyData, e ha vari metodi accessibili all utente tramite una password
	 * typical element: <
	 * 						{<category1, {data1, ..., datan}>    , ... , <categoryn, {data1, ..., datan}>    } 
	 * 					  , {<category1, {friend1, ..., friendn}>, ... , <categoryn, {friend1, ..., friendn}>}
	 * 					  , password
	 * 					>
	 * 
	 *  IR: dati != null && friends != null
	 * 	   && password != null
	 * 	   && dati does not contains duplicates && friends does not contains duplicates
	 * 	   && friends è ordinato lessicograficamente
	 */
	
	private HashMap<String,ArrayList<E>> dati;  //coppia <categoria, dati in quella categoria>
	private String password;
	private HashMap<String,ArrayList<String>> friends;  //coppia <categoria, amici di quella categoria>
	
	public Board2(String passw) throws NullPointerException{
		/*
		 * @requires: passw != null
		 * @throws:	  se passw == null lancia NullPointerException (UNCHECKED)
		 * @effects:  
		 */
		if(passw != null)
		{
			this.password = passw;
			this.dati = new HashMap<String, ArrayList<E>>();
			this.friends = new HashMap<String, ArrayList<String>>();
		}
		else throw new NullPointerException("User e/o Password sono null");
		
	}
	
	@Override
	public void createCategory(String category, String passw) throws WrongPasswordException , NullPointerException, NoDuplicatesException {
		/*
		 * @requires:	category != null && passw != null && passw == this.password
		 * @throw:		se category != null || passw != null lancia NullPointerException(Unchecked)
		 * 				se passw != this.password 			 lancia WrongPasswordException(Checked)
		 * @modifies:	this.dati, this.friends
		 * @effects:	this.dati_post = this.dati_pre U {category}
		 * 				this.friends_post = this.friends_pre U {category}
		 */
		if(category == null || passw == null)
			throw new NullPointerException("I parametri passati non devono essere null");
		if(passw == this.password)
		{
			if(friends.containsKey(category))
				throw new NoDuplicatesException(category + " è già nella lista");
			else {
			friends.put(category, new ArrayList<String>());
			dati.put(category, new ArrayList<E>());
			}
		}
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void removeCategory(String category, String passw) throws WrongPasswordException, NullPointerException,
			CategoryNotPresentException,  CloneNotSupportedException {
		/*
		 * @requires:	category != null && passw != null && passw == this.password && this.dati(category) must be empty
		 * @throw:		se category != null || passw != null lancia NullPointerException(Unchecked)
		 * 				se passw != this.password 			 lancia WrongPasswordException(Checked)
		 * 				se category is not in this.dati lancia CategoryNotPresentException(Checked)
		 * 				
		 * @modifies:	this.dati, this.friends
		 * @effects:	this.dati_post = this.dati_pre \ {category}
		 * 				this.friends_post = this.friends_pre \ {category}
		 */
		if(category == null || passw == null)
			throw new NullPointerException();
		if(passw == this.password)
		{
			if(!friends.containsKey(category))
				throw new CategoryNotPresentException(category + " non è presente nella lista.");
			friends.remove(category);
			dati.remove(category);
		}
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void addFriend(String category, String passw, String friend)
	throws WrongPasswordException, NoDuplicatesException, CategoryNotPresentException {
		/*
		 * @requires: category != null && passw != null && friend != null && passw == this.password 
		 * @throw:	  se category == null || passw == null 				  lancia NullPointerException(Unchecked)
		 * 			  se passw != this.password 		   				  lancia WrongPasswordException(Checked)
		 * 			  se friend appartiene già agli amici in category 	  lancia NoDuplicatesException
		 * 			  se category non appartiene alla map delle categorie lancia CategoryNotPresentException(Checked) 
		 * @modifies: this.friends
		 * 
		 * @effects:  this.friends(category)_post = this.friends(category)_pre U {friend}
		 */
			
		if(category == null || passw == null || friend == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password errata.");
		ArrayList<String> updateFriend = friends.get(category);
		if(updateFriend.contains(friend))
			throw new NoDuplicatesException(friend + " ha già accesso a questa categoria");
		updateFriend.add(friend);
		this.friends.put(category, updateFriend);
	}

	@Override
	public void removeFriend(String category, String passw, String friend)
			throws WrongPasswordException, FriendNotExistsException, NotRemovableException {
		/*
		 * @requires: category != null && passw != null && friend != null && passw == this.password 
		 * @throw:	  se category == null || passw == null 				  lancia NullPointerException(Unchecked)
		 * 			  se passw != this.password 		   				  lancia WrongPasswordException(Checked)
		 * 			  se friend non appartiene già agli amici in category 	  lancia NotRemovableException
		 * 			  se category non appartiene alla map delle categorie lancia NullPointerException(Unchecked) 
		 * @modifies: this.friends
		 * 
		 * @effects:  this.friends(category)_post = this.friends(category)_pre \ {friend}
		 */
		if(category == null || passw == null || friend == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password errata.");
		ArrayList<String> removeFriend = friends.get(category);
		if(!removeFriend.contains(friend))
			throw new NotRemovableException(friend + " non ha già accesso a questa categoria.");
		removeFriend.remove(friend);
		this.friends.put(category, removeFriend);
	}

	@Override
	public boolean put(String passw, String categoria, E dato) throws NullPointerException, WrongPasswordException,
			CategoryNotPresentException, NoDuplicatesException, CloneNotSupportedException {
		/*
		 * @requires: category != null && passw != null && dato != null && passw == this.password 
		 * @throw:	  se category == null || passw == null 				  			  lancia NullPointerException(Unchecked)
		 * 			  se passw != this.password 		   				  			  lancia WrongPasswordException(Checked)
		 * 			  se dato appartiene già a dati oppure è in un altra category 	  lancia NoDuplicatesException
		 * 			  se category non appartiene alla map delle categorie 			  lancia CategoyNotPresentException 
		 * @modifies: this.dati
		 * 
		 * @effects:  this.dati(category)_post = this.dati(category)_pre U {dato}
		 */
		if(categoria == null || passw == null || dato == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password errata.");
		if(!this.dati.containsKey(categoria))
			throw new CategoryNotPresentException(categoria +" non è presente nella lista.");
		ArrayList<E> updateData = this.dati.get(categoria);
		if(updateData.contains(dato))
			throw new NoDuplicatesException("Il dato è già presente.");
		if(dato.getCategory() == null) {
			dato.setCategory(categoria);
			boolean aggiunto = updateData.add(dato);
			this.dati.put(categoria, updateData);
			return aggiunto;
		}
		else
		{
			throw new NoDuplicatesException("Il dato è gia presente nella categoria "+ dato.getCategory());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public E get(String passw, E dato)
			throws NullPointerException, WrongPasswordException, DataNotPresentException, CloneNotSupportedException {
		if( passw == null || dato == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException("Password errata.");
		int i = dati.get(dato.getCategory()).indexOf(dato);
		if(i==-1)
			throw new DataNotPresentException("Il dato non è presente in bacheca");
		return (E) dati.get(dato.getCategory()).get(i).clone();
	}

	@Override
	public E remove(String passw, E dato)
			throws NullPointerException, WrongPasswordException, DataNotPresentException, CloneNotSupportedException {
		/*
		 * @requires: passw != null && dato != null && passw == this.password 
		 * @throw:	  se  passw == null 				  			  				  lancia NullPointerException(Unchecked)
		 * 			  se passw != this.password 		   				  			  lancia WrongPasswordException(Checked)
		 * 			  se dato non a dati      									 	  lancia DataNotPresentException
		 * @modifies: this.dati
		 * 
		 * @effects:  this.dati(category)_post = this.dati(category)_pre \ {dato}
		 */
		if(passw == null || dato == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException();
		int i = dati.get(dato.getCategory()).indexOf(dato);
		if(i==-1)
			throw new DataNotPresentException("Il dato non è presente in bacheca");
		ArrayList<E> removeDataList = dati.get(dato.getCategory());
		removeDataList.remove(dato);
		dati.put(dato.getCategory(), removeDataList);
		dato.setCategory(null);
		return dato;
	}

	@Override
	public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException,
			CategoryNotPresentException, CloneNotSupportedException {
		/*
		 * @requires: category != null && passw != null && passw == this.password 
		 * @throw:	  se category == null || passw == null 				  			  lancia NullPointerException(Unchecked)
		 * 			  se passw != this.password 		   				  			  lancia WrongPasswordException(Checked)
		 * 			  se category non appartiene alla map delle categorie 			  lancia CategoyNotPresentException 
		 *
		 */
		if(passw == null || category == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException();
		ArrayList<E> categoryDataList = dati.get(category);
		return categoryDataList;
	}

	@Override
	public Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException {
		/*
		 * @requires: passw != null && passw == this.password 
		 * @throw:	  se category == null || passw == null 				  			  lancia NullPointerException(Unchecked)
		 * 			  se passw != this.password 		   				  			  lancia WrongPasswordException(Checked)
		 *
		 */
		if(passw == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException();
		ArrayList<E> dataList= new ArrayList<E>();
		for(String cat: dati.keySet())
		{
			dataList.addAll(dati.get(cat));
		}
		Collections.sort(dataList, new SortByLike() );//ordino i dati per numero di like
		return Collections.unmodifiableList(dataList).iterator(); //rstituisco l'iteratore dei dati presenti ordinati per like 
		
	}

	@Override
	public void insertLike(String friend, E data) throws NullPointerException, WrongPasswordException,
			AlreadyLikedException, FriendNotExistsException, CategoryNotPresentException, CloneNotSupportedException, DataNotPresentException {
		if(friend == null || data==null)
			throw new NullPointerException();
		String catData = data.getCategory();
		ArrayList<E> dataLikeList = dati.get(catData);
		for(E dato: dataLikeList)//scorro la lista dei dati in quella categoria
			if(dato.equals(data)) {
				dato.addLike(friend);//aggiungo il like al dato
				break;
			}
		dati.put(catData, dataLikeList);//aggiorno
	}

	@Override
	public Iterator<E> getFriendIterator(String friend) throws NullPointerException, CloneNotSupportedException {
		if(friend == null)
			throw new NullPointerException();
		ArrayList<E> dataFriendsList = new ArrayList<E>();
		for(String cat: friends.keySet())
		{
			if(friends.get(cat).contains(friend))
				dataFriendsList.addAll(dati.get(cat));
		}
		return Collections.unmodifiableList(dataFriendsList).iterator();
	}

}



