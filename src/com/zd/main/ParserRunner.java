package com.zd.main;

import com.zd.ast.ASTree;
import com.zd.dialog.CodeDialog;
import com.zd.lexuer.Lexer;
import com.zd.lexuer.Token;
import com.zd.lexuer.Exception.ParseException;
import com.zd.parser.BasicParser;

public class ParserRunner {

	public static void main(String[] args) throws ParseException {
		Lexer l=new Lexer(new CodeDialog());
		BasicParser basicParser=new BasicParser();
		while(l.peek(0)!=Token.EOF){
			ASTree ast=basicParser.parse(l);
			System.out.println("=> "+ast.toString());
		}
	}

}
