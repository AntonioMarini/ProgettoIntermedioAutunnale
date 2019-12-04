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
		{
			friends.putIfAbsent(category, new ArrayList<String>());
			dati.put(category, new ArrayList<E>());
		}
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void removeCategory(String category, String passw) throws WrongPasswordException, NullPointerException,
			CategoryNotPresentException, NotRemovableException, CloneNotSupportedException {
		/*
		 * @requires:	category != null && passw != null && passw == this.password && this.dati(category) must be empty
		 * @throw:		se category != null || passw != null lancia NullPointerException(Unchecked)
		 * 				se passw != this.password 			 lancia WrongPasswordException(Checked)
		 * 				se category is not in this.dati lancia CategoryNotPresentException(Checked)
		 * 				se esiste data in recordData t.c. data.category == category  lancia NotRemovableException(Checked)
		 * @modifies:	this.categories
		 * @effects:	this.categories_post = this.categories_pre \ {category}
		 */
		if(category == null || passw == null)
			throw new NullPointerException();
		if(passw == this.password)
		{
			if(!friends.containsKey(category))
				throw new CategoryNotPresentException();
			if(dati.get(category).isEmpty())
				throw new NotRemovableException(category + " contiene ancora dei dati in bacheca.");
			friends.remove(category);
			dati.remove(category);
		}
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void addFriend(String category, String passw, String friend)
			throws WrongPasswordException, NoDuplicatesException, CategoryNotPresentException {
		if(category == null || passw == null || friend == null)
			throw new NullPointerException();
		ArrayList<String> updateFriend = friends.get(category);
		updateFriend.add(friend);
		this.friends.put(category, updateFriend);
	}

	@Override
	public void removeFriend(String category, String passw, String friend)
			throws WrongPasswordException, FriendNotExistsException {
		if(category == null || passw == null || friend == null)
			throw new NullPointerException();
		ArrayList<String> removeFriend = friends.get(category);
		removeFriend.remove(friend);
		this.friends.put(category, removeFriend);
	}

	@Override
	public boolean put(String passw, String categoria, E dato) throws NullPointerException, WrongPasswordException,
			CategoryNotPresentException, NoDuplicatesException, CloneNotSupportedException {
		if(categoria == null || passw == null || dato == null)
			throw new NullPointerException();
		ArrayList<E> updateData = this.dati.get(categoria);
		boolean aggiunto = updateData.add(dato);
		this.dati.put(categoria, updateData);
		return aggiunto;
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
		return dato;
	}

	@Override
	public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException,
			CategoryNotPresentException, CloneNotSupportedException {
		if(passw == null || category == null)
			throw new NullPointerException();
		if(passw != this.password)
			throw new WrongPasswordException();
		ArrayList<E> categoryDataList = dati.get(category);
		return categoryDataList;
	}

	@Override
	public Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException {
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
		return Collections.unmodifiableList(dataList).iterator();
		
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



