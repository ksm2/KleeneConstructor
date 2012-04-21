import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;


public class Automaton {
	
	public static final char EMPTY_WORD = ' ';
	
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
	public State createState()
	{
		// Create a new state
		State state = new State(this);
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
	
	/**
	 * Returns state count.
	 * 
	 */
	public List<State> getStates()
	{
		return _states;
	}
	
	/**
	 * retun a state
	 * 
	 * @return
	 */
	public State getStateById(int id)
	{
		return _states.get(id);
	}
	
}
