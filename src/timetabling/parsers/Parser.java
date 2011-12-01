/**
 * 
 */
package timetabling.parsers;

import java.io.IOException;

import timetabling.core.Constraints;



/**
 * @author mbr
 *
 */
public interface Parser {
	public Constraints parse() throws IOException;	
}
