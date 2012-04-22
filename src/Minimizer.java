/**
 * RegEx Minimizer Class
 * @author Konstantin Möllers
 *
 */
public class Minimizer {

	private String _regEx;
	private Token _items;
	private static Minimizer _instance;
	
	private Minimizer() {}
	
	public String miniMe(String regEx)
	{
		_regEx = regEx;
		_items = new Token(regEx);
		_items.optimize();
		return _items.toString();
	}
	
	public static Minimizer getInstance()
	{
		if (_instance == null)
		{
			_instance = new Minimizer();
		}
		return _instance;
	}
}
