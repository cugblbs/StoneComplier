package com.zd.lexuer.Exception;

import com.zd.ast.ASTree;

public class StoneException extends RuntimeException {
	public StoneException(String m){super(m);}
	public StoneException(String m,ASTree t){
		super(m+" "+t.location());
	}
}
