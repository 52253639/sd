package com.kingdee.eas.fdc.schedule.framework.ext;

import net.sourceforge.ganttproject.document.DocumentManager;
import net.sourceforge.ganttproject.gui.ProjectUIFacadeImpl;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.undo.GPUndoManager;

public class ScheduleProjectUIFacadeImpl extends ProjectUIFacadeImpl {

	public ScheduleProjectUIFacadeImpl(UIFacade workbenchFacade, DocumentManager documentManager, GPUndoManager undoManager) {
		super(workbenchFacade, documentManager, undoManager);
	}

}
