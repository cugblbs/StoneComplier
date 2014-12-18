package expr;

import java.util.List;

import com.zd.ast.ASTList;
import com.zd.ast.ASTree;

public class WhileStmnt extends ASTList {

	public WhileStmnt(List<ASTree> list) {
		super(list);
	}
	public ASTree condition(){return child(0);}
	public ASTree body(){return child(1);}
	@Override
	public String toString() {
		return "(while "+condition()+" "+body()+")";
	}
	
}
