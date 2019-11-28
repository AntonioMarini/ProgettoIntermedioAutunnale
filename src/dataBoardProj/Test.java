package dataBoardProj;

import dataBoardProjExceptions.CategoryNotPresentException;
import dataBoardProjExceptions.FriendNotExistsException;
import dataBoardProjExceptions.NoDuplicatesException;
import dataBoardProjExceptions.WrongPasswordException;

public class Test {

	
	public static void main(String[] args) {
		
		
		Board<MyData> antonio = new Board<MyData>("Apollyon", "1234");
		Video a = new Video("piru", "Random");
		
		try {
			antonio.createCategory("Random", "1234");
		} catch (NullPointerException | WrongPasswordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*try {
			antonio.removeCategory("Random", "1234");
		} catch (NullPointerException | WrongPasswordException | CategoryNotPresentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		try {
			antonio.addFriend("Random", "1234", "peppino");
		} catch (WrongPasswordException | NoDuplicatesException | CategoryNotPresentException e) {

			e.printStackTrace();
		}
		
		try {
			antonio.removeFriend("Random", "1234", "peppino");
		} catch (WrongPasswordException | FriendNotExistsException e) {

			e.printStackTrace();
		}
		
		
		
	
		
		
			
		
		
	    
		
		
	}

}
