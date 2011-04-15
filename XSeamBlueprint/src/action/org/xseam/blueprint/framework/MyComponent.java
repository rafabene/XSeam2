package org.xseam.blueprint.framework;

import org.jboss.seam.annotations.Name;

@Name("myComponent")
public class MyComponent {
	
	private String value;
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public void action(){
		System.out.println("Action invoked whith value " + value);
	}

}
