import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class Kleene {
	
	private Automaton _automaton;
	private String[][][] _calculated;
	
	/**
	 * 
	 * @param automaton
	 */
	public Kleene(Automaton automaton)
	{
		_automaton = automaton;
		
		int states = _automaton.getStates().size();
		_calculated = new String[states + 1][states][states];
	}
	
	/**
	 * 
	 * @return
	 */
	public String buildRegEx()
	{
		Set<String> parts = new HashSet<String>();
		
		int size = _automaton.getStates().size();
		for(int i = 0; i < size; i++)
		{
			for(int j = 0; j < size; j++)
			{
				parts.add("(" + getRegEx(size, i, j) + ")");
			}
		}
		
		return implodeAsString(parts);
	}
	
	/**
	 * 
	 * @param k
	 * @param i
	 * @param j
	 */
	private String getRegEx(int k, int i, int j)
	{
		if(_calculated[k][i][j] != null)
			return _calculated[k][i][j];
		
		String result = "";
		if(k == 0)
		{
			State stateI = _automaton.getStateById(i);
			State stateJ = _automaton.getStateById(j);
			
			Set<Character> connectors = stateI.getSignsTo(stateJ);
			if(connectors.size() == 0)
			{
				// leeres wort
				result = "!";
			}
			else
			{
				result = implodeAsString(connectors);
			}
		}
		else
		{
			String part1 = getRegEx(k-1, i, j);
			String part2 = getRegEx(k-1, i, k - 1); // k-1, weil states bei index 0 anfangen (gilt für i und j)
			String part3 = getRegEx(k-1, k - 1, k - 1);
			String part4 = getRegEx(k-1, k -1, j);
			
			result = "(" + part1 + ") + (" + part2 + ")" + "(" + part3 + ")*" + "(" + part4 + ")";
		}
		
		_calculated[k][i][j] = result;
		System.out.println(k + "," + i + "," + j + "\t\t" + result);
		
		return result;
	}
	
	/**
	 * put all objects in the set into a concatinated string
	 * 
	 * @param connectors
	 * @return
	 */
	private String implodeAsString(Set connectors)
	{
		int size = connectors.size();
		StringBuilder result = new StringBuilder();
		
		for(Object o : connectors)
		{
			result.append(o.toString());
			
			size--;
			if(size > 0)
			{
				result.append(" + ");
			}
		}
		
		return result.toString();
	}
	
}
