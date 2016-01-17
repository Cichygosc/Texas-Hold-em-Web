package models.msgs;

public class Winners {

	final String message;
	final String name;

	public Winners(String message, String name) {
		this.message = message;
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public String getName() {
		return name;
	}

}
