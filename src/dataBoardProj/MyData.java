package dataBoardProj;
import java.util.List;

import dataBoardProjExceptions.AlreadyLikedException;

import java.util.ArrayList;



public class MyData implements Data, Cloneable, Comparable<Object>{
	/*
	 * IR: c.data != null && c.categoria != null && c.likes >= 0  
	 * 
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
	public void display() {
		/*
		 * @requires: ogg != null 
		 */
		String li = Integer.toString(likes);
        System.out.println("\nDato : "+ data.toString() +"\nLikes: "+ li +"\nCategory: " + categoria );
	}

	@Override
	public String getCategory() throws CloneNotSupportedException {
       return this.categoria;
	}
	
	public int getLikes() {
		return this.likes;
	}

	@Override
	public void addLike(String friend) throws AlreadyLikedException{
		if(!this.addedLikes.contains(friend))
		{
			 this.likes++;
			 addedLikes.add(friend);
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
