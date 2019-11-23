package dataBoardProj;

public interface Data extends Cloneable {
	/* @overview: 
	 *  dato generico che ha un metodo display() e la categoria del dato
	 * 
	 */
	
	//stampa il dato a schermo
	public void display();
	
	
	//metodo toString di Object per l'adt Data

	//ottiene la categoria del dato
    public String getCategory();
    
    //incrementa il contatore dei like
    public void addLike();
    
    //ALTRI METODI DA AGGIUNGERE QUI: 

}
