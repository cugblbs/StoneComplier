package com.zd.lexuer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.zd.lexuer.Exception.ParseException;

public class Lexer {

	public static String regexPat = "\\s*((//.*)|([0-9]+)|(\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\")"
			+ "|[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct})?";

	private Pattern pattern = Pattern.compile(regexPat);
	private ArrayList<Token> queue = new ArrayList<Token>();
	private boolean hasMore;
	private LineNumberReader reader;

	public Lexer(Reader r) {
		hasMore = true;
		reader = new LineNumberReader(r);
	}

	public Token read() throws ParseException {
		if (fillQueue(0)) {
			return queue.remove(0);
		} else {
			return Token.EOF;
		}
	}

	public Token peek(int i) throws ParseException {
		if (fillQueue(i)) {
			return queue.get(i);
		} else {
			return Token.EOF;
		}
	}

	private boolean fillQueue(int i) throws ParseException {
		// 只要i的值比queue的size要大，就需要继续读取下一行
		while (i >= queue.size()) {
			if (hasMore)
				readLine();
			else {
				return false;
			}
		}

		return true;
	}

	protected void readLine() throws ParseException {
		String line;
		try {
			line = reader.readLine();// 读取一行代码
		} catch (IOException e) {
			throw new ParseException(e);
		}

		if (line == null) {
			hasMore = false;
			return;
		}

		int lineNo = reader.getLineNumber();
		// 得到line中与pattern相匹配matcher
		Matcher matcher = pattern.matcher(line);
		matcher.useTransparentBounds(true).useAnchoringBounds(false);
		int pos = 0;
		int endPos = line.length();
		while (pos < endPos) {
			matcher.region(pos, endPos);// 设置匹配的范围
			if (matcher.lookingAt()) {
				addToken(lineNo, matcher);
				pos = matcher.end();
			} else {
				throw new ParseException("bad token at line" + lineNo);
			}
		}

		queue.add(new IDToken(lineNo, Token.EOL));
	}

	protected void addToken(int lineNo, Matcher matcher) {
		String m = matcher.group(1);
		if (m != null)// 不为空
		{
			if (matcher.group(2) == null)// 不是注释
			{
				Token token;
				if (matcher.group(3) != null) {
					token = new NumToken(lineNo, Integer.parseInt(m));
				} else if (matcher.group(4) != null) {
					token = new StrToken(lineNo, toStringLiteral(m));
				} else {
					token = new IDToken(lineNo, m);
				}
				queue.add(token);
			}
		}
	}

	protected String toStringLiteral(String s) {
		StringBuffer sb = new StringBuffer();
		int len = s.length() - 1;
		for (int i = 1; i < len; i++) {
			char c = s.charAt(i);
			if (c == '\\' && i + 1 < len) {
				int c2 = s.charAt(i + 1);
				if (c2 == '"' || c2 == '\\')
					c = s.charAt(++i);
				else if (c2 == 'n') {
					++i;
					c = '\n';
				}
			}
			sb.append(c);
		}
		return sb.toString();
	}

	protected static class NumToken extends Token {
		private int value;

		public NumToken(int line, int val) {
			super(line);
			value = val;
		}

		@Override
		public int getNumber() {
			return value;
		}

		@Override
		public String getText() {
			return Integer.toString(value);
		}

		@Override
		public boolean isNumber() {
			return true;
		}
	}

	protected static class IDToken extends Token {
		private String text;

		protected IDToken(int line, String id) {
			super(line);
			text = id;
		}

		@Override
		public boolean isIdentifier() {
			return true;
		}

		@Override
		public String getText() {
			return text;
		}
	}

	protected static class StrToken extends Token {
		private String string;

		StrToken(int line, String s) {
			super(line);
			string = s;
		}

		@Override
		public boolean isString() {
			return true;
		}

		@Override
		public String getText() {
			return string;
		}
	}

}
