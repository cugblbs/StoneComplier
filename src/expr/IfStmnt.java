package expr;

import java.util.List;

import com.zd.ast.ASTList;
import com.zd.ast.ASTree;

public class IfStmnt extends ASTList {
	public IfStmnt(List<ASTree> c) {
		super(c);
	}
	public ASTree condition(){return child(0);}
	public ASTree thenBlock(){return child(1);}
	public ASTree elseBlock(){return numChildren()>2?child(2):null;}
	@Override
	public String toString() {
		return "(if "+condition()+" "+thenBlock()+"else "+elseBlock()+")";
	}
}
