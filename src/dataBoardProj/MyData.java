package dataBoardProj;
import java.util.List;

import dataBoardProjExceptions.AlreadyLikedException;

import java.util.ArrayList;



public class MyData implements Data, Cloneable{
	/*
	 * IR: c.data != null && c.categoria != null && c.likes >= 0  
	 * 
	 */
	
	private Board<?> author;
	private String data; //contenuto
	private Category categoria; //categoria del dato
	private int likes; //numero di likes
	private List<String> addedLikes; //array che contiene gli amici che hanno messo like 
	
	
	//Costruttore 
	public MyData (String content, String category, Board author) throws IllegalArgumentException {
		/*
		 * @requires: text != null && category != null
		 * @throw   : se text == null or category == null lancia IllegalArgumentException(Unchecked)
		 * @modifies: this
		 * @effects : create a new Object 
		 */
		if( content != null && category != null) 
		{
			this.setAuthor(author);
			this.data =  content;
			this.addedLikes = new ArrayList<String>(0);
			this.likes = 0;
		}
		else
			throw new IllegalArgumentException();
	}
	
	
	
	private void setAuthor(Board author2) {
		this.author = author2;
		
	}



	public MyData(MyData copy){//////COPIA//////////////////
		/* Metodo costrutttore per creare una shallow copy dell'oggetto
		 * @effects : crea una shallow copy dell'oggetto 
		 */
		
		copy.data = this.data;
		copy.addedLikes = cloneList(this.addedLikes);
		copy.categoria = this.getCategory();
		copy.likes = this.likes;
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
	public Category getCategory() {
       Category tmp = this.categoria;
       return tmp;
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
	


	//metodo per clonare la lista di stringhe
	public List<String> cloneList(List<String> toCopyList) throws NullPointerException{
		/*
		 * @requires: toCopyList != null
		 * @throw:	se toCopyList == null lancio NullPointerException
		 * @effects:	restituisco una copia della lista
		 */
		if(toCopyList != null) {
			List<String> copy = new ArrayList<String>(toCopyList.size()); 
			for(String friend : toCopyList) copy.add(friend); //copio il contenuto in copy
			return copy; 	//restituisco la copia dell'oggetto.
		}
		
		else throw new NullPointerException("La lista da copiare è null");
	}


	public Object clone() throws
    CloneNotSupportedException 
	{ 
		return super.clone(); 
	}



	/**
	 * @return the author
	 */
	public Board getAuthor() {
		return author;
	}
	
	public void setCategory (Category c) {
		this.categoria= c;
	}


}
