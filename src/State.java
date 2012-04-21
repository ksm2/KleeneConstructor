import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class of an automaton state.
 * @author Konstantin M�llers
 */
public class State {

	// Whether this state accepts a word
	private boolean _accepting;
	
	// State identification number
	private final Automaton _automaton;
	
	// Connected states
	private Map<Character,State> _neighbours;
	
	/**
	 * Constructs a state class.
	 * 
	 */
	public State(Automaton automaton)
	{
		// Set fields
		_accepting = false;
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
	
	/**
	 * Returns whether this state is accepting words.
	 * 
	 */
	public boolean getAccepting()
	{
		return _accepting;
	}
	
	/**
	 * Sets whether this automaton accepts words.
	 * 
	 */
	public void setAccepting()
	{
		setAccepting(true);
	}
	
	/**
	 * Sets whether this automaton accepts words.
	 * 
	 */
	public void setAccepting(boolean value)
	{
		_accepting = value;
	}
	
	/**
	 * 
	 * @param state
	 * @return
	 */
	public Set<Character> getSignsTo(State target)
	{
		Set<Character> characters = new HashSet<Character>();
		
		if(target == this)
		{
			characters.add('!');
		}
		
		for(Entry<Character, State> entry : _neighbours.entrySet())
		{
			if(entry.getValue() == target)
			{
				characters.add(entry.getKey());
			}
		}
		
		return characters;
	}
}
