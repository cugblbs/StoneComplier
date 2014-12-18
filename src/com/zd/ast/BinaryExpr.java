package com.zd.ast;

import java.util.List;

public class BinaryExpr extends ASTList {
	public BinaryExpr(List<ASTree> c) {
		super(c);
	}
	
	public ASTree left(){
		return child(0);
	}
	
	public ASTree right() {
		return child(2);
	}
	
	public String operation(){
		return ((ASTLeaf)child(1)).token.getText();
	}
	
	
	
	
	
}
