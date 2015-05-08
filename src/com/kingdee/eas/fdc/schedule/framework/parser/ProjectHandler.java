package com.kingdee.eas.fdc.schedule.framework.parser;

import net.sourceforge.ganttproject.parser.ParsingListener;

import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDPrjInfos;

public class ProjectHandler extends AbstractKDHandler implements IKDHandler, ParsingListener {
	public ProjectHandler() {

	}

	public void handle() {
		KDPrjInfos prjInfos = (KDPrjInfos)this.getKDParser().getPrjInfos();
		ScheduleBaseInfo info=getScheduleBaseInfo();
		prjInfos._sProjectName=info.getName();
		prjInfos._sOrganization=info.getOrgUnit()==null?"":info.getOrgUnit().getName();
		prjInfos._sDescription = info.getDescription();
		prjInfos._sWebLink = info.getWebLink();
	}

	public void parsingFinished() {
		ScheduleBaseInfo info=getScheduleBaseInfo();
		if (info.getViewDate() != null) {
			this.getKDParser().getUiFacade().getScrollingManager().scrollLeft(
					info.getViewDate());
			this.getKDParser().getUiFacade().setViewIndex(info.getViewIndex());
			this.getKDParser().getUiFacade().setGanttDividerLocation(
					info.getGdivlocation());
			this.getKDParser().getUiConfig().setProjectLevelTaskColor(
					ScheduleParserHelper.parseStringToColor(info.getColor()));
		}
	}

	public void parsingStarted() {
		
	}
}