package com.zd.main;

import com.zd.dialog.CodeDialog;
import com.zd.lexuer.Lexer;
import com.zd.lexuer.Token;
import com.zd.lexuer.Exception.ParseException;

public class LaxerRunner {

	public static void main(String[] args) throws ParseException {
		Lexer l=new Lexer(new CodeDialog());
		for(Token token;(token=l.read())!=Token.EOF;){
			System.out.println("=> "+token.getText());
		}
	}

}
