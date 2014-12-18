package com.zd.lexuer.Exception;

import java.io.IOException;

import com.zd.lexuer.Token;

public class ParseException extends Exception {
	public ParseException(Token token) {
		this("", token);
	}

	public ParseException(String msg, Token token) {
		super("syntax error around" + location(token));
	}

	private static String location(Token token) {
		if (token == Token.EOF) {
			return "the last line.";
		} else {
			return "\"" + token.getText() + "\" at line"
					+ token.getLineNumber();
		}
	}

	public ParseException(IOException e) {
		super(e);
	}

	public ParseException(String msg) {
		super(msg);
	}
}
