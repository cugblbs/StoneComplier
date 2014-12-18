package expr;

import java.util.List;

import com.zd.ast.ASTList;
import com.zd.ast.ASTree;

public class NegativeExpr extends ASTList {
	public NegativeExpr(List<ASTree> c) {
		super(c);
	}
	public ASTree operand(){return child(0);}
	@Override
	public String toString() {
		return "-"+operand();
	}
}
