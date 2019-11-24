package dataBoardProj;
import java.util.Collections;
import java.util.Vector;


public class Category {
	/*
	 *  @overview: classe per mantere le categorie relative a una Databoard 
	 *  
	 *  Typical element: < name, { friend1, friend2, ..., friendn}> for n > 0
	 *  
	 *  IR: c.name != null && c.allowedFriends != null && no duplicates in c.allowedFriends
	 */
	
	private String name;					//nome della categoria
	private Vector<String> allowedFriends;	//lista di amici che possono vedere dati di quella categoria 
	
	
	public Category(String nm)
	{
		this.name = nm;
		this.allowedFriends = new Vector<String>();
	}
	
	public String getCategoryName()
	{
		return this.name;
	}
	
	public void allowFriend(String newFriend) throws NullPointerException
	{
		if(newFriend != null)
		this.allowedFriends.add(newFriend);
		Collections.sort(allowedFriends);
	}
	
	public void remove(String friend)
	{
		this.allowedFriends.remove(friend);
	}

}
