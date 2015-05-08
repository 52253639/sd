package com.kingdee.eas.fdc.schedule.framework.parser;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sourceforge.ganttproject.PrjInfos;
import net.sourceforge.ganttproject.gui.UIConfiguration;
import net.sourceforge.ganttproject.gui.UIFacade;
import net.sourceforge.ganttproject.parser.GPParser;
import net.sourceforge.ganttproject.parser.ParsingListener;
import net.sourceforge.ganttproject.task.TaskManager;

import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;

public class DefaultKDParser extends AbstractKDParser implements IKDParser,GPParser {
	public DefaultKDParser(PrjInfos info, UIConfiguration uiConfig,
			TaskManager taskManager, UIFacade uiFacade) {
		super(info, uiConfig, taskManager, uiFacade);
	}
	public DefaultKDParser(TaskManager taskManager) {
		super(taskManager);
	}
	
	public void parse() {
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		Map parseMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "parse");
		// ////////////////////////////////////////////////////////////////////////
		
		List handlers = getHandlers();
		for(Iterator iter=handlers.iterator();iter.hasNext();){
			IKDHandler handler=(IKDHandler)iter.next();
			if(handler instanceof AbstractKDHandler){
				((AbstractKDHandler)handler).setParser(this);
			}
			handler.handle();
		}
		List parsingListeners=getParsingListeners(); 
		for (int i = 0; i < parsingListeners.size(); i++) {
			ParsingListener l = (ParsingListener)parsingListeners.get(i);
			l.parsingFinished();
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// 进度性能优化 by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "parse", parseMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	public void reParse() {
		List handlers = getHandlers();
		for(Iterator iter=handlers.iterator();iter.hasNext();){
			IKDHandler handler=(IKDHandler)iter.next();
			handler.reHandle();
		}
	}
}
