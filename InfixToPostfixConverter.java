
public class InfixToPostfixConverter {

	private String regex;
	private char c;
	private int cc;

	private StringBuffer postfix = new StringBuffer();

	public String getPosfixExpression() {
		return postfix.toString();
	}

	public InfixToPostfixConverter(String infixExpression) {
		regex = infixExpression + '\0';
		c = regex.charAt(cc++);
		E();
	}

	private void E() {
		T();
		while (c == '|') {
			c = regex.charAt(cc++);
			T();
			postfix.append("|");
		}
	}

	private void T() {
		F();
		while (c != '\0' && !(")|*+?").contains(""+c)) { /* '(' or LITERAL */
			F();
			postfix.append("&");
		}
	}

	private void F() {
		G();
		while (c != '\0' && ("*+?").contains(""+c)) {
			postfix.append(c);
			c = regex.charAt(cc++);
		}
	}

	private void G() {
		if (c == '\0')
			fatal("unexpected end of expression!");
		if (c == '\\') { /* escape */
			c = regex.charAt(cc++);
			if (c == '\0')
				fatal("escape at end of string");
			postfix.append(c);
			c = regex.charAt(cc++);
		} else if (!("()|*+?").contains(""+c)) { /* non-meta chacter */
			if (c == '&')
				postfix.append("&");
			else
				postfix.append(c);
			c = regex.charAt(cc++);
		} else if (c == '(') {
			c = regex.charAt(cc++);
			E();
			if (c != ')')
				fatal("mismatched parantheses!");
			c = regex.charAt(cc++);
		} else
			fatal("bogus expression!");
	}

	private void fatal(String message) {
		throw new RuntimeException(message);
	}
	
	public static void testInfixToPostfix() {
		//example
		InfixToPostfixConverter converter = new InfixToPostfixConverter("((a|b)*aba*)");
		System.out.println(converter.getPosfixExpression());
	}

}
