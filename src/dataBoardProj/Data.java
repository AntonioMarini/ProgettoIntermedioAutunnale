package dataBoardProj;

import dataBoardProjExceptions.AlreadyLikedException;

public interface Data {
	/* @overview: 
	 *  dato generico che ha un metodo display(), la categoria del dato
	 *  
	 * 
	 */
	
	//stampa il dato a schermo
	public void display();
	
	
	//metodo toString di Object per l'adt Data

	//ottiene la categoria del dato
    public String getCategory() throws CloneNotSupportedException;
    
    //incrementa il contatore dei like
    /*
     *
     */
    public void addLike(String friend) throws AlreadyLikedException;
    

}
