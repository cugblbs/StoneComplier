package com.zd.eval;

import java.rmi.server.Operation;
import java.util.List;

import javassist.gluonj.Reviser;

import com.zd.ast.ASTLeaf;
import com.zd.ast.ASTList;
import com.zd.ast.ASTree;
import com.zd.ast.BinaryExpr;
import com.zd.ast.Name;
import com.zd.ast.NumberLiteral;
import com.zd.lexuer.Token;
import com.zd.lexuer.Exception.StoneException;
import com.zd.main.Environment;
import com.zd.parser.Parser.Operators;

import expr.NegativeExpr;
import expr.StringLiteral;

public class BasicEvaluator {
	public static final int True = 1;
	public static final int FALSE = 0;

	@Reviser
	public static abstract class ASTreeEx extends ASTree {
		public abstract Object eval(Environment env);
	}

	@Reviser
	public static class ASTListEx extends ASTList {

		public ASTListEx(List<ASTree> ast) {
			super(ast);
		}

		public Object eval(Environment env) {
			throw new StoneException("cannot eval: " + toString(), this);
		}
	}

	@Reviser
	public static class ASTLeafEx extends ASTLeaf {
		public ASTLeafEx(Token t) {
			super(t);
		}

		public Object eval(Environment env) {
			throw new StoneException("cannot eval: " + toString(), this);
		}
	}

	@Reviser
	public static class NumberEx extends NumberLiteral {
		public NumberEx(Token t) {
			super(t);
		}

		public Object eval(Environment env) {
			return value();
		}
	}

	@Reviser
	public static class NameEx extends Name {
		public NameEx(Token token) {
			super(token);
		}

		public Object eval(Environment env) {
			String value = env.get(name()).toString();
			if (value == null) {
				throw new StoneException("NullPointer--->value");
			} else {
				return value;
			}
		}
	}

	@Reviser
	public static class StringEx extends StringLiteral {
		public StringEx(Token token) {
			super(token);
		}

		public Object eval(Environment env) {
			return value();
		}
	}

	@Reviser
	public static class NegativeEx extends NegativeExpr {
		public NegativeEx(List<ASTree> c) {
			super(c);
		}

		public Object eval(Environment env) {
			Object value = ((ASTreeEx) operand()).eval(env);
			if (value instanceof Integer) {
				return new Integer(-((Integer) value).intValue());
			} else {
				throw new StoneException("bad type for -", this);
			}
		}
	}

	@Reviser
	public static class BinaryEx extends BinaryExpr {

		public BinaryEx(List<ASTree> c) {
			super(c);
		}

		public Object eval(Environment env) {
			String op = operation();
			if (op.equals("=")) {
				Object right = ((ASTreeEx) right()).eval(env);
				return null;// 返回计算结果
			} else {
				Object left = ((ASTreeEx) right()).eval(env);
				Object right = ((ASTreeEx) right()).eval(env);
				return null;//返回结果；
			}
		}

	}
	
	
	 

}
