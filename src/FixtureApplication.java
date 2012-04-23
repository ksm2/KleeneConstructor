
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
//		String regEx = kleene.buildRegEx();
		String regEx = "a>!a(a(a(b)))";
		System.out.println("Erwartet:       a+a(a(ab))");
		System.out.println("Davor:          " + regEx);
		regEx = Minimizer.getInstance().miniMe(regEx);
		
		System.out.println("Habe optimiert: " + regEx);
	}
	
	/**
	 * 
	 */
	private Automaton createFixtures()
	{
		Automaton automaton = new Automaton();
		
		State q1 = automaton.createState();
		State q2 = automaton.createState();
		State q3 = automaton.createState();
		q3.setAccepting();
		
		q1.connect(q1, 'a');
		q1.connect(q2, 'a');
		q2.connect(q2, 'b');
		q2.connect(q3, 'a');
		q3.connect(q2, 'b');
		q3.connect(q1, 'a');
		
		return automaton;
	}
	
}
