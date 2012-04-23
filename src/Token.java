import java.util.ArrayList;
import java.util.List;

class Token
{
	public static final byte TYPE_NOTHING = 0;
	public static final byte TYPE_STAR = 1;
	public static final byte TYPE_PLUS = 2;
	
	public char value;
	public byte type;
	private List<Token> _items;
	private Token _lastToken;
	
	public Token(char expression)
	{
		value = expression;
		type = Token.TYPE_NOTHING;
	}
	
	public Token(String expression)
	{
		value = 0;
		if (expression.length() > 1)
		{
			_items = new ArrayList<Token>();
			build(expression);
		}
		else
			value = expression.charAt(0);
	}
	
	/**
	 * Returns whether the value exists.
	 *  
	 */
	public boolean hasValue()
	{
		return value != 0;
	}
	
	public String toString()
	{
		return _toString(true);
	}
	
	private String _toString(boolean first)
	{
		StringBuffer str = new StringBuffer();
		if (!hasValue())
		{
			if (!first)
				str.append("(");
			for(Token token: _items)
				str.append(token._toString(false));				
			if (!first)
				str.append(")");
		}
		else
		{
			if (value == '!')
				str.append("!");
			else if (value == '>')
				str.append("+");
			else
				str.append(value);
		}
		if (type == Token.TYPE_PLUS)
			str.append("^+");
		else if (type == Token.TYPE_STAR)
			str.append("^*");
		return str.toString();
	}
	
	/**
	 * Optimizes itself.
	 * 
	 */
	public void optimize()
	{
		if (!hasValue())
		{
			for(Token token: _items)
				token.optimize();
		
			int index = 0;
			while (index < _items.size() - 1)
			{
				Token item = _items.get(index);
				Token next = _items.get(index + 1);
				if (itemIsUnitedWithNext(item))
				{
					Token unitedWith = _items.get(index + 2);
					// Epsilon-Optimize
					if (unitedWith.isEpsilon() && (item.type == Token.TYPE_PLUS))
					{
						_items.remove(index + 1);
						_items.remove(index + 1);
						item.type = Token.TYPE_STAR;
						--index;		
					}
					else if (item.isEpsilon() && (unitedWith.type == Token.TYPE_PLUS))
					{
						_items.remove(index);
						_items.remove(index);
						unitedWith.type = Token.TYPE_STAR;
						--index;		
					}
					else if (item.itemsEqual(unitedWith))
					{
						_items.remove(index);
						_items.remove(index);
						--index;
					}
				}
				else // Concatenated with next
				{
					if (item.isEpsilon())
					{
						_items.remove(index);
						--index;
					}
					else if (item.type == Token.TYPE_NOTHING)
					{
						if (item.itemsEqual(next) && (next.type == Token.TYPE_STAR))
						{
							_items.remove(index);
							next.type = Token.TYPE_PLUS;
							--index;
						}
					}
					else if (item.type == Token.TYPE_STAR)
					{
						if (item.itemsEqual(next))
						{
							_items.remove(index + 1);
							item.type = Token.TYPE_PLUS;
							--index;
						}
					}
				}
				++index;
			} /* of while */
			
			// Last element?
			// Finally remove last epsilon
			if ((_items.size() > 1) && (_items.get(index).isEpsilon()))
			{
				if (_items.get(index - 1).value != '>')
					_items.remove(index);
			}
			switch (_items.size())
			{
			case 1:
				value = _items.get(0).value;
				type = _items.get(0).type;
				_items.remove(0);
				break;
			case 3:
				if (_items.get(1).value == '>')
				{
					if (_items.get(0).isEpsilon())
					{
						value = _items.get(2).value;
						_items.clear();
					}
					else if (_items.get(2).isEpsilon())
					{
						value = _items.get(0).value;
						_items.clear();
					}
				}
				break;
			}
		} /* of if not hasValue */
		
		// Single optimization
		if (hasValue())
		{
			if (isEpsilon())
				type = Token.TYPE_NOTHING;
		}
	}

	/**
	 * Builds its own token list.
	 * 
	 */
	private void build(String expression)
	{
		int i = 0;
		while(i < expression.length())
		{
			char c = expression.charAt(i);
			switch (c)
			{
				// Detected subexpression
				case '(':
				    i = findSubExpression(expression, i);
				    break;
				// Plus-Operator
				case '+':
					_lastToken.type = Token.TYPE_PLUS;
					++i;
					break;
				// Star-Operator
				case '*':
					_lastToken.type = Token.TYPE_STAR;
					++i;
					break;
				default:
					_lastToken = new Token(c);
					_items.add(_lastToken);
					++i;
			}
		}
	}
	
	/**
	 * Builds subexpression.
	 * 
	 */
	private int findSubExpression(String expression, int offset)
	{
		int openers = 1, closers = 0, start = offset + 1;
		while (openers > closers)
		{
			++offset;
			int nextOpener = expression.indexOf('(', offset);
			int nextCloser = expression.indexOf(')', offset);
			if ((nextOpener != -1) && (nextOpener < nextCloser))
			{
				offset = nextOpener;
				++openers;
			}
			else
			{
				offset = nextCloser;
				++closers;
			}
		}
		// Read subexpression
		_lastToken = new Token(expression.substring(start, offset));
		_items.add(_lastToken);
		// Return end of subexpression
		return offset + 1;
	}
	
	/**
	 * Proofes, if a item is united with another
	 * 
	 */
	private boolean itemIsUnitedWithNext(Token item)
	{
		if (_items != null)
		{
			int i = _items.indexOf(item) + 1;
			return (i < _items.size() - 1) && (_items.get(i).value == '>');
		}
		else
			return false;
	}
	
	/**
	 * Proofes, if this is epsilon
	 * 
	 */
	private boolean isEpsilon()
	{
		return hasValue() && (value == '!');
	}
	
	/**
	 * Proofes, whether items are equal
	 * 
	 */
	private boolean itemsEqual(Token other)
	{
		if (hasValue())
			return other.value == value;
		else
		{
			if (other.hasValue() || other._items.size() != _items.size())
				return false;
			for (int i = 0; i < _items.size(); ++i)
				if (!_items.get(i).itemsEqual(other._items.get(i)))
					return false;
			return true;
		}
	}
}