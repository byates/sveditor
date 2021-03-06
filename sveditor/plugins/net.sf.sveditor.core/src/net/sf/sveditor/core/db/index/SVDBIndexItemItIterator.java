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


package net.sf.sveditor.core.db.index;

import java.util.Iterator;

import net.sf.sveditor.core.db.ISVDBItemBase;
import net.sf.sveditor.core.db.SVDBItemType;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * Iterator that iterates over a list of index item iterators
 * @author ballance
 *
 */
class SVDBIndexItemItIterator implements ISVDBItemIterator {
	
	private Iterator<ISVDBIndexIterator>		fIterator;
	private ISVDBItemIterator					fCurrent;
	private IProgressMonitor					fMonitor;
	
	public SVDBIndexItemItIterator(Iterator<ISVDBIndexIterator> it, IProgressMonitor monitor) {
		fIterator = it;
		fMonitor = monitor;
	}

	public boolean hasNext(SVDBItemType... type_list) {
		while (fCurrent != null || fIterator.hasNext()) {
			
			if (fCurrent == null) {
				fCurrent = fIterator.next().getItemIterator(fMonitor);
			}
			
			if (!fCurrent.hasNext(type_list)) {
				fCurrent = null;
				continue;
			} else {
				break;
			}
		}
		
		return (fCurrent != null && fCurrent.hasNext(type_list));
	}

	public ISVDBItemBase nextItem(SVDBItemType... type_list) {
		ISVDBItemBase ret = null;
		
		while (fCurrent != null || fIterator.hasNext()) {
			
			if (fCurrent == null) {
				fCurrent = fIterator.next().getItemIterator(fMonitor);
			}
			
			if ((ret = fCurrent.nextItem(type_list)) == null) {
				fCurrent = null;
				continue;
			} else {
				break;
			}
		}
		
		return ret;
	}
}