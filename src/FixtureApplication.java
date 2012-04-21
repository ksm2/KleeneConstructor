
public class FixtureApplication {
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		new FixtureApplication();
	}
	
	/*
	 * 
	 */
	public FixtureApplication()
	{
		Automaton automaton = createFixtures();
		Kleene kleene = new Kleene(automaton);
		System.out.println(kleene.buildRegEx());
	}
	
	/**
	 * 
	 */
	private Automaton createFixtures()
	{
		Automaton automaton = new Automaton();
		
		State q1 = automaton.createState();
		State q2 = automaton.createState();
		q2.setAccepting();
		
		q1.connect(q1, 'c');
		q1.connect(q2, 'a');
		q2.connect(q1, 'b');
		
		return automaton;
	}
	
}
