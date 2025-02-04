package dataBoardProj;
import java.util.ArrayList;
import java.util.Collections;

import dataBoardProjExceptions.FriendNotExistsException;
import dataBoardProjExceptions.NoDuplicatesException;


public class Category implements Cloneable{
	/*
	 *  @overview: classe per mantere le categorie relative a una Databoard 
	 *  
	 *  Typical element: < name, { friend1, friend2, ..., friendn}> for n > 0
	 *  
	 *  IR: c.name != null && c.allowedFriends != null && no duplicates in c.allowedFriends
	 */
	
	private String name;					//nome della categoria
	private ArrayList<String> allowedFriends;	//lista di amici che possono vedere dati di quella categoria 
	
	
	public Category(String nm)
	{
		this.name = nm;
		this.allowedFriends = new ArrayList<String>();
	}
	
	public String getCategoryName()
	{
		return this.name;
	}
	
	public void allowFriend(String newFriend) throws NullPointerException, NoDuplicatesException
	{
		/*
		 * @requires:	newFriend != null && newFriend is not in allowedFriends
		 * @throw:		se newFriend == null 					lancia NullPointerException(Unchecked)
		 * 				se allowedFriend contiene già newFriend lancia NoDuplicatesException(Unchecked)
		 * @modifies:	this.allowedFriends
		 * @effect:		this.allowedFriends_post = this.allowedFriends_pre U {newFriend}
		 */
		if(newFriend != null)
		{
			if(!this.allowedFriends.contains(newFriend))
			{
				this.allowedFriends.add(newFriend);
				Collections.sort(allowedFriends);
			}
			else throw new NoDuplicatesException();
		}
		else throw new NullPointerException("l'amico da aggiungere non deve essere null");
	}
	
	
	public ArrayList<String> getAllowedFriends()
	{
		return new ArrayList<String>(allowedFriends);
	}
	
	public void removeAlllowedFriend(String friend) throws FriendNotExistsException
	{
		if(this.allowedFriends.contains(friend))
		this.allowedFriends.remove(friend);
		else throw new FriendNotExistsException(friend + " non è presente nella categoria " + this.name);;
	}
	
	//deep copy di category
	public Object clone() throws
    CloneNotSupportedException 
	{ 
		Category cloned = (Category) super.clone();
		cloned.allowedFriends = this.getAllowedFriends();
		return super.clone(); 
	}

}
