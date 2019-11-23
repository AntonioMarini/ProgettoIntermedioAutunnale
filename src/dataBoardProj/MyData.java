package dataBoardProj;
import java.util.List;
import java.util.ArrayList;



public class MyData<E> implements Data {
	/*
	 * IR: c.data != null && c.categoria != null && c.likes >= 0  
	 * 
	 */
	
	
	private E data; //contenuto
	private String categoria; //categoria del dato
	private int likes; //numero di likes
	private List<String> addedLikes; //array che contiene gli amici che hanno messo like 
	
	
	//Costruttore per i dati testuali
	public MyData (String text, String category) throws IllegalArgumentException {
		/*
		 * @requires: text != null && category != null
		 * @throw   : se text == null or category == null lancia IllegalArgumentException(Unchecked)
		 * @modifies: this
		 * @effects : create a new Object 
		 */
		if( text != null && category != null) 
		{
		this.data = (E) text;
		this.addedLikes = new ArrayList<String>(0);
		this.categoria = category;
		this.likes = 0;
		}
		else
			throw new IllegalArgumentException();
	}
	
	
	
	public MyData(MyData<E> copy){//////COPIA//////////////////
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
        System.out.println("\nDato : "+ data +"\nLikes: "+ li +"\nCategory: " + categoria );
	}

	@Override
	public String getCategory() {
       String tmp = this.categoria;
       return tmp;
	}

	@Override
	public void addLike() {
		// TODO Auto-generated method stub
        this.likes++;
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




}
