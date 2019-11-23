package dataBoardProj;

import java.util.Iterator;
import dataBoardProjExceptions.*;
import java.util.List;

public interface DataBoard<E extends Data> {
	
	// Crea l’identità una categoria di dati
	public void createCategory(String category, String passw) throws WrongPasswordException;
	
	// Rimuove l’identità una categoria di dati
	public void removeCategory(String category, String passw) throws WrongPasswordException;
	
	// Aggiunge un amico ad una categoria di dati
	public void addFriend(String category, String passw, String friend) throws WrongPasswordException, DuplicateFriendException;
	
	// rimuove un amico ad una categoria di dati
	public void removeFriend(String category, String passw, String friend) throws WrongPasswordException, FriendNotExistsException;
	
	// Inserisce un dato in bacheca
	// se vengono rispettati i controlli di identità
	public boolean put(String passw , String categoria, E dato) throws NullPointerException, WrongPasswordException, CategoryNotPresentException, AlreadyPresentException;
	
	// Ottiene una copia del del dato in bacheca
	// se vengono rispettati i controlli di identità
	public E get(String passw, E dato) throws NullPointerException, WrongPasswordException, DataNotPresentException;
	
	// Rimuove il dato dalla bacheca
	// se vengono rispettati i controlli di identità
	public E remove(String passw, E dato);
	// Crea la lista dei dati in bacheca su una determinata categoria
	// se vengono rispettati i controlli di identità
	public List<E> getDataCategory(String passw, String category);

	

	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca ordinati rispetto al numero di like.
	public Iterator<E> getIterator(String passw);
	
	// Aggiunge un like a un dato
	public void insertLike(String friend, E data) throws AlreadyLikedException;
	
	// Legge un dato condiviso
	// restituisce un iteratore (senza remove) che genera tutti i dati in
	// bacheca condivisi.
	public Iterator<E> getFriendIterator(String friend);
	

	// ... altre operazione da definire a scelta


}
