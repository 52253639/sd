package com.kingdee.eas.fdc.schedule.framework.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.ganttproject.IGanttProject;
import net.sourceforge.ganttproject.PrjInfos;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.io.GanttXMLOpen;
import net.sourceforge.ganttproject.parser.GPParser;
import net.sourceforge.ganttproject.parser.ParsingContext;
import net.sourceforge.ganttproject.parser.ParsingListener;
import net.sourceforge.ganttproject.parser.TagHandler;
import net.sourceforge.ganttproject.task.TaskManager;

public abstract class AbstractKDParser extends GanttXMLOpen implements IKDParser,GPParser {
	private PrjInfos prjInfos=null;
	private UIConfiguration uiConfig=null;
	private TaskManager taskManager=null;
	private UIFacade uiFacade=null;
	private IGanttProject myProject=null;
	public AbstractKDParser(PrjInfos info, UIConfiguration uiConfig,
			TaskManager taskManager, UIFacade uiFacade) {
		super(info, uiConfig, taskManager, uiFacade);
		this.prjInfos=info;
		this.taskManager=taskManager;
		this.uiConfig=uiConfig;
		this.uiFacade=uiFacade;
//		this.myProject=myProject;
	}
	public AbstractKDParser(TaskManager taskManager) {
		super(taskManager);
	}
	public IGanttProject getProject(){
		return this.myProject;
	}
	public void setProject(IGanttProject myProject){
		this.myProject=myProject;
	}
	public PrjInfos getPrjInfos() {
		return prjInfos;
	}
	public void setPrjInfos(PrjInfos prjInfos) {
		this.prjInfos = prjInfos;
	}
	public UIConfiguration getUiConfig() {
		return uiConfig;
	}
	public void setUiConfig(UIConfiguration uiConfig) {
		this.uiConfig = uiConfig;
	}
	public TaskManager getTaskManager() {
		return taskManager;
	}
	public void setTaskManager(TaskManager taskManager) {
		this.taskManager = taskManager;
	}
	public UIFacade getUiFacade() {
		return uiFacade;
	}
	public void setUiFacade(UIFacade uiFacade) {
		this.uiFacade = uiFacade;
	}
	public void setHandlers(List handlers) {
		this.handlers = handlers;
	}
	
	private List handlers=new ArrayList();
	public void addHandler(IKDHandler handler) {
		this.handlers.add(handler);
	}

	private List parsingListeners=new ArrayList();
	public void addParsingListener(ParsingListener listener) {
		parsingListeners.add(listener);
		super.addParsingListener(listener);
	}

	public List getParsingListeners(){
		return parsingListeners;
	}
	public ParsingContext getContext() {
		return super.getContext();
	}

	public List getHandlers() {
		return this.handlers;
	}
}
