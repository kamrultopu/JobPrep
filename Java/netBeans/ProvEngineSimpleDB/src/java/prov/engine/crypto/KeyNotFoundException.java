/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package prov.engine.crypto;

/**
 *
 * @author zawoad
 */
public class KeyNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String localizedMessage;
	public KeyNotFoundException(String message)
	{
		localizedMessage = message;
	}
	
	public String getLocalizedMessage()
	{
		return localizedMessage;
	}

}
