package expr;

import java.util.List;

import com.zd.ast.ASTList;
import com.zd.ast.ASTree;

public class PrimaryExpr extends ASTList {
	public PrimaryExpr(List<ASTree> ast) {
		super(ast);
	}
	public static ASTree create(List<ASTree> c){
		return c.size()==1?c.get(0):new PrimaryExpr(c);
	}
}
