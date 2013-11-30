
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;

public class NFA {

        private static int stateIDsource = 0;
        private int initialState;
        private HashSet<Integer> allStates;
        private HashSet<Integer> acceptStates;
        private HashSet<Edge> transitions;
        private HashSet<Integer> currentStates;

        private NFA(int initialState, HashSet<Integer> allStates, HashSet<Integer> acceptStates, HashSet<Edge> transitions) {
                this.initialState       = initialState;
                this.allStates          = new HashSet<Integer>(allStates);
                this.acceptStates       = new HashSet<Integer>(acceptStates);
                this.transitions        = new HashSet<Edge>(transitions);
        }

        
        
        public String toString() {
                String stringRepresentation = "States : \n";
                stringRepresentation += allStates + "\nTransitions : \n";
                for (Edge edge : transitions) {
                        stringRepresentation += edge + "\n";
                }
                return stringRepresentation;
        }

        public boolean accepts(String text) {
                

                currentStates = new HashSet<Integer>(); //empty set initially.
                HashSet<Integer> states = new HashSet<Integer>();
                char[] textChars = text.toCharArray();
                
                int totalChar = 0;
                char symbol;
                this.currentStates = epsilonClosure(initialState);

                for (int temp=0; totalChar != textChars.length - 1; temp++) {
                        for (int currentState : this.currentStates) {
                                symbol = textChars[totalChar];
                                for (Edge edge : transitions) {
                                        if (edge.sourceState == currentState) 
                                        { 
                                                if (edge.symbol == symbol) {
                                                        states.add(edge.destinationState);
                                                        System.out.println(edge);
                                                        temp--;
                                                }
                                        }
                                }
                                temp++;
                        }
                        System.out.println(totalChar);
                        totalChar++;

                        if (totalChar != textChars.length - 1) {
                                currentStates.clear();
                                for (int i : states) {
                                        currentStates.add(i);
                                        System.out.println(i);
                                }
                        }


                }

                for (int s : currentStates) {
                        if (acceptStates.contains(s)) {
                                return true;
                        }
                }

                return false;
        }

        public HashSet<Integer> epsilonClosure(int state) {
                //finds the set of states that are in epsilon closure of given 'state' within
                //this automaton. (set of states that can be reached from 'state' using
                //epsilon transitions)

               //finds the set of states that are in epsilon closure of given 'state' within
    //this automaton. (set of states that can be reached from 'state' using
    //epsilon transitions)
    
		//TODO implement kleeneClosure!

		throw new UnsupportedOperationException("Not implemented: kleeneClosure");
        }

        private static int newState() {
                //every new state must have distinct id, so increment the id generator
                //each time you need a new one.
                return stateIDsource++;
        }

        public static NFA singleSymbol(char symbol) {
                //implemented for you: singleSymbol
                int initialState = newState();
                int finalState = newState();

                //we have just two states here
                HashSet<Integer> statesSet = new HashSet<Integer>();
                statesSet.add(initialState);
                statesSet.add(finalState);

                //there is a single accept state,
                HashSet<Integer> acceptStates = new HashSet<Integer>();
                acceptStates.add(finalState);

                //there is a single transition in this automaton, which takes initial state
                //to final state if input is given as the same as 'symbol' variable.
                Edge edge = new Edge(initialState, finalState, symbol);
                HashSet<Edge> transitions = new HashSet<Edge>();
                transitions.add(edge);

                //create an NFA with such properties.
                return new NFA(initialState, statesSet, acceptStates, transitions);
        }

        public static NFA union(NFA nfa1, NFA nfa2) {
                
                HashSet<Edge> transitions = new HashSet<Edge>();
                HashSet<Integer> statesSet = new HashSet<Integer>();
                HashSet<Integer> acceptStates = new HashSet<Integer>();

                statesSet.addAll(nfa1.allStates);
                transitions.addAll(nfa1.transitions);
                
                
                statesSet.addAll(nfa2.allStates);
                transitions.addAll(nfa2.transitions);

                int source = newState();
                int lastState = newState();
                acceptStates.add(lastState);

                statesSet.add(source);
                statesSet.add(lastState);

                Edge e1 = Edge.epsilonTransition(source, nfa1.initialState);
                transitions.add(e1);
                
                Edge e2 = Edge.epsilonTransition(source, nfa2.initialState);
                transitions.add(e2);
                
                for (int source1 : nfa1.acceptStates) {
                        transitions.add(Edge.epsilonTransition(source1, lastState));
                }

                for (int source2 : nfa2.acceptStates) {
                        transitions.add(Edge.epsilonTransition(source2, lastState));
                }

                return new NFA(source, statesSet, acceptStates, transitions);

        }

        public static NFA concatenate(NFA nfa1, NFA nfa2) {
                
                HashSet<Edge> transitions = new HashSet<Edge>();
                HashSet<Integer> statesSet = new HashSet<Integer>();
                HashSet<Integer> acceptStates = new HashSet<Integer>();

                statesSet.addAll(nfa1.allStates);
                transitions.addAll(nfa1.transitions);
                
                statesSet.addAll(nfa2.allStates);
                transitions.addAll(nfa2.transitions);
               
                
                int source = nfa1.initialState;
                
                for (int state : nfa2.acceptStates) {
                        acceptStates.add(state);
                }

                statesSet.add(nfa1.initialState);
                statesSet.addAll(acceptStates);

                for (int source1 : nfa1.acceptStates) {
                        transitions.add(Edge.epsilonTransition(source1, nfa2.initialState));
                }


                return new NFA(source, statesSet, acceptStates, transitions);
        }

        public static NFA star(NFA nfa) {
                HashSet<Integer> statesSet = new HashSet<Integer>();
                HashSet<Integer> acceptStates = new HashSet<Integer>();
                HashSet<Edge> transitions = new HashSet<Edge>();

                int initialState = newState();
                int finalState = newState();
                acceptStates.add(finalState);
                statesSet.addAll(nfa.allStates);

                transitions.addAll(nfa.transitions);

                Edge edge1 = Edge.epsilonTransition(initialState, finalState);
                transitions.add(edge1);

                Edge edge2 = Edge.epsilonTransition(initialState, nfa.initialState);
                transitions.add(edge2);

                for (int source : nfa.acceptStates) {
                        transitions.add(Edge.epsilonTransition(source, finalState));
                }
                for (int source : nfa.acceptStates) {
                        transitions.add(Edge.epsilonTransition(source, nfa.initialState));
                }

                return new NFA(initialState, statesSet, acceptStates, transitions);
        }

        
        public static NFA newNFA(String infix) {
                 InfixToPostfixConverter converter = new InfixToPostfixConverter(infix);
                String postfix = converter.getPosfixExpression();
                char[] postfixCharArray = postfix.toCharArray();
                int totalChar = 0;
                char character;
                
                System.out.println(postfix);
                
                Stack<NFA> stackNFA = new Stack<NFA>();
                NFA result;
                for (int i = 0; totalChar != postfixCharArray.length; i++) {
                        character = postfixCharArray[totalChar];

                        if (stackNFA.size() > 1) {
                                if (character == '&') {
                                        NFA nfa2 = stackNFA.pop();
                                        NFA nfa1 = stackNFA.pop();
                                        stackNFA.push(NFA.concatenate(nfa1, nfa2));
                                }
                        } else if (stackNFA.size() > 1) {
                                if (character == '|') {
                                        NFA nfa2 = stackNFA.pop();
                                        NFA nfa1 = stackNFA.pop();
                                        stackNFA.push(NFA.union(nfa1, nfa2));
                                }
                        } else if (character == '*') {
                                NFA nfa = stackNFA.pop();
                                stackNFA.push(NFA.star(nfa));
                        } else {
                                NFA nfa = NFA.singleSymbol(character);
                                stackNFA.push(nfa);
                        }

                        totalChar++;
                }

                result = stackNFA.pop();
                return result;
        }

        public static void test() {
                System.out.println("Testing...");
                System.out.println();
                System.out.println("Trying to construct automaton that recognizes: ");
                System.out.println("(a|b)*abb");

                NFA result = NFA.newNFA("(a|b)*abb");
                System.out.println("Accepting States: " + result.acceptStates);

                String testString = "aababb";

                if (result.accepts(testString)) {
                        System.out.println("NFA accepts " + testString);
                } else {
                        System.out.println("NFA does not accept");
                }

                System.out.println("All tests passed!");
                System.out.println("Here is the resulting NFA: ");
                //System.out.println(result);

        }
}
