package com.zd.parser;


//这种引入静态方法的方法值得注意一下
import static com.zd.parser.Parser.rule;

import java.util.HashSet;

import com.zd.ast.ASTree;
import com.zd.ast.BinaryExpr;
import com.zd.ast.Name;
import com.zd.ast.NumberLiteral;
import com.zd.lexuer.Lexer;
import com.zd.lexuer.Token;
import com.zd.lexuer.Exception.ParseException;
import com.zd.parser.Parser.Operators;

import expr.BlockStmnt;
import expr.IfStmnt;
import expr.NegativeExpr;
import expr.NullStmnt;
import expr.PrimaryExpr;
import expr.StringLiteral;
import expr.WhileStmnt;



//由BNF定义语法转化而来的Java定义的语法规则
public class BasicParser {
	//hashset 可以为空，但是不是同步的
	HashSet<String> reserved = new HashSet<String>();
	Operators operators = new Operators();
	//由于前面已经导入过该静态方法，故可以直接引用
	Parser expr0 = rule();
	Parser primary = rule(PrimaryExpr.class)
			.or(rule().sep("(").ast(expr0).sep(")"),
					rule().number(NumberLiteral.class),
					rule().identifier(Name.class, reserved),
					rule().string(StringLiteral.class));
	Parser factor = rule().or(rule(NegativeExpr.class).sep("-").ast(primary), primary);
	
	Parser expr = expr0.expression(BinaryExpr.class, factor, operators);
	
	Parser statement0 = rule();
	Parser block = rule(BlockStmnt.class)
			.sep("{").option(statement0)
			.repeat(rule().sep(";", Token.EOL).option(statement0))
			.sep("}");
	Parser simple = rule(PrimaryExpr.class).ast(expr);
	Parser statement = statement0.or(
			rule(IfStmnt.class).sep("if").ast(expr).ast(block)
			.option(rule().sep("else").ast(block)),
			rule(WhileStmnt.class).sep("while").ast(expr).ast(block),
			simple);
	Parser program = rule().or(statement, rule(NullStmnt.class))
			.sep(";",Token.EOL);
	
	public BasicParser(){
		reserved.add(";");
		reserved.add("}");
		reserved.add(Token.EOL);
		
		operators.add("=", 1, Operators.RIGHT);
		operators.add("==", 2, Operators.LEFT);
		operators.add(">", 2, Operators.LEFT);
		operators.add("<", 2, Operators.LEFT);
		operators.add("+", 3, Operators.LEFT);
		operators.add("-", 3, Operators.LEFT);
		operators.add("*", 4, Operators.LEFT);
		operators.add("/", 4, Operators.LEFT);
		operators.add("%", 4, Operators.LEFT);
	}
	public ASTree parse(Lexer lexer) throws ParseException{
		return program.parse(lexer);
	}
	
			
}
