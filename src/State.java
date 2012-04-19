import java.util.Map;
import java.util.HashMap;

/**
 * Class of an automaton state.
 * @author Konstantin Möllers
 */
public class State {

	// Whether this state accepts a word
	private final boolean _accepting;
	
	// State identification number
	private final Automaton _automaton;
	
	// Connected states
	private Map<Character,State> _neighbours;
	
	/**
	 * Constructs a state class.
	 * 
	 */
	public State(Automaton automaton, boolean accepting)
	{
		// Set fields
		_accepting = accepting;
		_automaton = automaton;
		
		// Create state map
		_neighbours = new HashMap<Character, State>();
	}
	
	/**
	 * Returns a state label.
	 * 
	 */
	public String getLabel()
	{
		return "Z" + getId();
	}
	
	/**
	 * Connects this state with another.
	 * 
	 */
	public void connect(State connectWith, char edge)
	{
		_neighbours.put(edge, connectWith);
		_automaton.addSign(edge);
	}
	
	/**
	 * Returns my id.
	 * 
	 */
	public int getId()
	{
		return _automaton.getStateId(this);
	}
}
