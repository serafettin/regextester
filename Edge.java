
public class Edge {
	
	private static final char EPSILON = '\u0395';
	
	public int sourceState;
	public int destinationState;
	public char symbol;
	
	public int getSourceState() {
		return sourceState;
	}
	
	public int getDestinationState() {
		return destinationState;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
	public Edge(int sourceState, int destinationState, char symbol) {
		this.sourceState = sourceState;
		this.destinationState = destinationState;
		this.symbol = symbol;
	}
	
	public static Edge epsilonTransition(int source, int destination) {
		return new Edge(source, destination, EPSILON);
	}
	
	public boolean isEpsilonTransition() {
		return symbol == EPSILON;
	}
	
	public String toString() {
		String strRepresentation =  "(" + sourceState + ", " + destinationState + ", ";
		if (isEpsilonTransition()) {
			strRepresentation += "" +  symbol + ")";
		} else {
			strRepresentation += "'" +  symbol + "')";
		}
		return strRepresentation;
	}

}
