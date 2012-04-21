import java.util.List;
import java.util.Scanner;


public class Application {
	
	public static final int DEFAULT_INITIAL_STATE = 0;
	
	private static Automaton _automaton;
	
	private static String[] _arguments;
	private static Scanner _inputScanner;
	private static int _argumentCounter;
	
	public static void main(String[] arguments)
	{
		_arguments = arguments;
		_inputScanner = new Scanner(System.in);
		_argumentCounter = 0;
		_automaton = new Automaton();
		
		int stateCount; 
		
		System.out.println("KLEENE CONSTRUCTOR");
		System.out.println("Written by Konstantin M�llers, Markus Fasselt\n");
		
		stateCount = getStateCount();
		
		if (stateCount == 0)
			goodBye("You didn't give me any states to play with!");
			
		configureAllStates(stateCount);
		
		goodBye();
	}
	
	/**
	 * Says good bye to user.
	 * 
	 */
	private static void goodBye()
	{
		System.out.println("Well then, kthxbye! :)");
		System.exit(1);
	}
	
	/**
	 * Is there a parameter available?
	 * 
	 */
	private static boolean hasParam()
	{
		return _arguments.length > _argumentCounter;
	}
	
	/**
	 * Gets a new parameter
	 * 
	 */
	private static String getParam()
	{
		return _arguments[_argumentCounter++];
	}
	
	/**
	 * Says good bye to user and shows an error Message.
	 * 
	 */
	private static void goodBye(String errorMsg)
	{
		System.out.println("Error: " + errorMsg);
		goodBye();
	}
	
	/** 
	 * Returns well formed state num. 
	 * @param count
	 * @return
	 */
	private static String stateNum(int count)
	{
		switch(count)
		{
			case 0: return "no states";
			case 1: return "one state";
			default: return count + " states";
		}
	}
	
	/**
	 * Gets state count from user.
	 * 
	 */
	private static int getStateCount()
	{
		int stateCount;
		// Ask for state count
		stateCount = Integer.parseInt(askFor("How many states do you need?"));
		// Ensurance out
		System.out.println("So you wish " + stateNum(stateCount) + "!\n");
		return stateCount;
	}
	
	private static void configureAllStates(int howMany)
	{
		for (int i = 0; i < howMany; ++i)
			_automaton.createState();
		
		List<State> states = _automaton.getStates();
		for (State s: states)
			configureState(s);
	}
	
	/**
	 * Configures a state.
	 * 
	 */
	private static void configureState(State state)
	{
		// Present this state
		System.out.println("=== ABOUT " + state.getLabel() + " ===");
		// Is this the inital state?
		if (state.getId() == DEFAULT_INITIAL_STATE)
			System.out.println("  This is the initial state.");
		// Ask whether it should accept
		state.setAccepting(askFor("  Does this state accept words?").equals("yes"));
		// Ask for connections
		
		System.out.println();
	}
	
	/**
	 * Asks the user for something.
	 * 
	 */
	private static String askFor(String question)
	{
		if (hasParam())
			return getParam();
		else
		{
			System.out.print(question + "  ");
			return _inputScanner.next();
		}
	}
	
}
