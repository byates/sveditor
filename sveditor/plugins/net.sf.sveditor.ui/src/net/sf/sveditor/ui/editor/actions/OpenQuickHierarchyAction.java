/****************************************************************************
 * Copyright (c) 2008-2010 Matthew Ballance and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthew Ballance - initial implementation
 *     Armond Paiva - repurposed from hierarchy view to quick hierarchy 
 ****************************************************************************/


package net.sf.sveditor.ui.editor.actions;

import java.util.ResourceBundle;

import net.sf.sveditor.ui.editor.SVEditor;

import org.eclipse.ui.texteditor.TextEditorAction;

public class OpenQuickHierarchyAction extends TextEditorAction {
	
	private SVEditor fEditor;
	
	public OpenQuickHierarchyAction(
			ResourceBundle			bundle,
			SVEditor editor) {
		
		super(bundle, "OpenQuickHierarchy.", editor) ;
		
		fEditor = editor ;
	}

	@Override
	public void run() {
			
		fEditor.getQuickHierarchyPresenter().showInformation() ;
			
	};
	
}
