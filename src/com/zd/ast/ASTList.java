package com.zd.ast;

import java.util.Iterator;
import java.util.List;

import com.zd.lexuer.Token;

public class ASTList extends ASTree {

	protected List<ASTree> children;

	public ASTList(List<ASTree> list) {
		children = list;
	}

	@Override
	public ASTree child(int i) {
		return children.get(i);
	}

	@Override
	public int numChildren() {
		return children.size();
	}

	@Override
	public Iterator<ASTree> children() {
		return children.iterator();
	}

	@Override
	public String location() {
		for (ASTree asTree : children) {
			String string = asTree.location();
			if (string != null) {
				return string;
			}

		}

		return null;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append('(');
		String sep = "";
		for (ASTree asTree : children) {
			sb.append(sep);
			sep = " ";
			sb.append(asTree.toString());
		}
		return sb.append(')').toString();
	}
}
