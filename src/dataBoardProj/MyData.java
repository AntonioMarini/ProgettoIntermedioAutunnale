package dataBoardProj;
import java.util.List;
import java.util.ArrayList;



public class MyData implements Data {
	
	
	private String data; //
	private String categoria; //categoria del dato
	private int likes; //numero di likes
	private List<String> addedLikes; //array in cui vedo chi ha già messo like
	
	public MyData(String text, String category) throws IllegalArgumentException {
		/*
		 * @requires: text != null && category != null
		 * @throw   : se text == null or category == null lancia IllegalArgumentException(Unchecked)
		 * @modifies: this
		 * @effects : create a new Object 
		 */
		if( text != null && category != null) 
		{
		this.data = text;
		this.addedLikes = new ArrayList<String>(0);
		this.categoria = category;
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
        System.out.println("\nDato : "+ data +"\nLikes: "+ li +"\nCategory: " + categoria );
	}

	@Override
	public String getCategory() {
		//TODO: METODO CLONE()
       String tmp = this.categoria;
       return tmp;
	}

	@Override
	public void addLike() {
		// TODO Auto-generated method stub
        this.likes++;
	}
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}

}
