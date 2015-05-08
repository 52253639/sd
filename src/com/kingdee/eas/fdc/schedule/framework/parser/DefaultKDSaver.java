package com.kingdee.eas.fdc.schedule.framework.parser;

import java.io.IOException;
import java.io.OutputStream;

import net.sourceforge.ganttproject.GanttGraphicArea;
import net.sourceforge.ganttproject.GanttResourcePanel;
import net.sourceforge.ganttproject.GanttTree2;
import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.io.GPSaver;
import net.sourceforge.ganttproject.io.GanttXMLSaver;

public class DefaultKDSaver extends GanttXMLSaver implements GPSaver, IKDSaver {

	public DefaultKDSaver(IGanttProject project, GanttTree2 tree,
			GanttResourcePanel peop, GanttGraphicArea area, UIFacade uiFacade) {
		super(project, tree, peop, area, uiFacade);
	}

	public void save(OutputStream output) throws IOException {
//		super.save(output);

	}

	public void storeFile() {

	}

}
