package dataBoardProj;
import java.util.List;

import dataBoardProjExceptions.AlreadyLikedException;

import java.util.ArrayList;



public class MyData implements Data, Cloneable, Comparable<Object>{
	/*
	 * IR: c.data != null && c.categoria != null && c.likes >= 0  && addedLikes has no duplicates
	 * Typical Element:
	 * 		<data, categoria, likes, {friend1, ..., friendn}>
	 */
	
	private String data; //contenuto
	private String categoria; //categoria del dato
	private int likes; //numero di likes
	private List<String> addedLikes; //array che contiene gli amici che hanno messo like 
	
	
	//Costruttore 
	public MyData (String content) throws IllegalArgumentException {
		/*
		 * @requires: text != null && category != null
		 * @throw   : se text == null or category == null lancia IllegalArgumentException(Unchecked)
		 * @modifies: this
		 * @effects : create a new Object 
		 */
		if( content != null) 
		{
			this.data =  content;
			this.addedLikes = new ArrayList<String>(0);
			this.likes = 0;
			
		}
		else
			throw new IllegalArgumentException();
	}
	
	


	@Override
	/*stampa il dato a schermo*/
	public void display() {
		
		String li = Integer.toString(likes);
        System.out.println("\nDato : "+ data.toString() +"\nLikes: "+ li +"\nCategory: " + categoria );
	}

	/*restituisce la categoria del dato*/
	@Override
	public String getCategory() throws CloneNotSupportedException {
       return this.categoria;
	}
	
	/*restituisce i like del dato*/
	public int getLikes() {
		return this.likes;
	}

	@Override
	/*friend aggiunge like al dato solo se non lo ha gia messo in precedenza*/
	public void addLike(String friend) throws AlreadyLikedException{
		/*
		 * REQUIRES: addedLikes non contiene friend
		 * THROWS:   se friend è presente in addedLikes lancia AlreadyLikedException
		 * MODIFIES: this.likes, this.addedlikes
		 * EFFECTS:  this.likes_post = this.likes_pre + 1
		 * 			 this.addedLikes_post = this.addedLikes_pre U friend
		 * */
		if(!this.addedLikes.contains(friend))
		{
			 this.likes++;			//aggiungo il like..
			 addedLikes.add(friend);//..e aggiungo friend alla lista di amici che hanno gia messo like
		}
		else
		{
			 throw new AlreadyLikedException(friend + "ha già messo like a questo dato.");
		}
	}
	


	public Object clone() throws
    CloneNotSupportedException 
	{ 
		MyData cloned = (MyData) super.clone();
		return cloned; 
	}

	
	public void setCategory (String c) {
		if(c == null)
			this.categoria = null;
		else
		this.categoria = c;
	}




	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


}
