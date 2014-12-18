package com.zd.ast;

import com.zd.lexuer.Token;

public class NumberLiteral extends ASTLeaf {
	public NumberLiteral(Token token) {
		super(token);
	}

	public int value() {
		return token().getNumber();
	}

}
