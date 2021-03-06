/****************************************************************************
 * Copyright (c) 2008-2011 Matthew Ballance and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthew Ballance - initial implementation
 ****************************************************************************/


package net.sf.sveditor.core.db.expr;

import java.util.ArrayList;
import java.util.List;

import net.sf.sveditor.core.db.SVDBItemType;

public class SVDBParamIdExpr extends SVDBIdentifierExpr {
	public List<SVDBExpr>				fParamExpr;
	
	public SVDBParamIdExpr() {
		this(null);
	}

	public SVDBParamIdExpr(String id) {
		super(SVDBItemType.ParamIdExpr, id);
		fParamExpr = new ArrayList<SVDBExpr>();
	}

	public List<SVDBExpr> getParamExpr() {
		return fParamExpr;
	}
	
	public void addParamExpr(SVDBExpr expr) {
		fParamExpr.add(expr);
	}
	
}
