
package com.iware.lottery.exception;

/**
 *
 * @author Hantsy Bai<hantsy@gmail.com>
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final String id;

    public ResourceNotFoundException(String id){this.id = id;}

    public String getName() {return id;}
}
