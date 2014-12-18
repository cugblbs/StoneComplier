package expr;

import com.zd.ast.ASTLeaf;
import com.zd.lexuer.Token;

public class StringLiteral extends ASTLeaf {

	public StringLiteral(Token t) {
		super(t);
	}
	public String value(){ return token().getText();}

}
