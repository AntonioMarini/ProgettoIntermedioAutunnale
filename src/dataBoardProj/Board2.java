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
	
	private TreeMap<String, HashSet<E>> dati;  //coppia <categoria, dati in quella categoria>
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
			this.dati = new TreeMap<String, HashSet<E>>();
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
			dati.put(category, new HashSet<E>());
		}
		else throw new WrongPasswordException("Password sbagliata.");
	}

	@Override
	public void removeCategory(String category, String passw) throws WrongPasswordException, NullPointerException,
			CategoryNotPresentException, NotRemovableException, CloneNotSupportedException {
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
			if(!friends.containsKey(category))
				throw new CategoryNotPresentException();		
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
		return false;
	}

	@Override
	public E get(String passw, E dato)
			throws NullPointerException, WrongPasswordException, DataNotPresentException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E remove(String passw, E dato)
			throws NullPointerException, WrongPasswordException, DataNotPresentException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<E> getDataCategory(String passw, String category) throws NullPointerException, WrongPasswordException,
			CategoryNotPresentException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> getIterator(String passw) throws NullPointerException, WrongPasswordException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertLike(String friend, E data) throws NullPointerException, WrongPasswordException,
			AlreadyLikedException, FriendNotExistsException, CategoryNotPresentException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterator<E> getFriendIterator(String friend) throws NullPointerException, CloneNotSupportedException {
		// TODO Auto-generated method stub
		return null;
	}

}
