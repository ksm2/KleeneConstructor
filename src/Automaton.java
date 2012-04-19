import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


public class Automaton {
	
	// State list
	private List<State> _states;
	
	// Edge alphabet
	private Set<Character> _alphabet;
	
	/**
	 * Constructor
	 * 
	 */
	public Automaton()
	{
		_states = new ArrayList<State>();
		_alphabet = new HashSet<Character>();
	}
	
	/**
	 * Creates a new state.
	 * 
	 * @param accepting
	 * 			This state accepts words. 
	 */
	public State createState(boolean accepting)
	{
		// Create a new state
		State state = new State(this, accepting);
		// Add it to the list
		_states.add(state);
		// Return the state
		return state;
	}
	
	/**
	 * Adds a sign.
	 * 
	 */
	public void addSign(char sign)
	{
		_alphabet.add(sign);
	}
	
	/**
	 * Returns the state index.
	 * 
	 */
	public int getStateId(State subject)
	{
		return _states.indexOf(subject);
	}
}
