package com.kingdee.eas.fdc.schedule.framework.parser;

import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ext.KDPrjInfos;

public abstract class AbstractKDHandler implements IKDHandler {
	private AbstractKDParser parser=null;
	public AbstractKDParser getKDParser() {
		return this.parser;
	}

	public void setParser(AbstractKDParser parser) {
		this.parser=parser;
	}
	protected ScheduleBaseInfo getScheduleBaseInfo(){
		KDPrjInfos prjInfos = (KDPrjInfos)this.getKDParser().getPrjInfos();
		ScheduleBaseInfo info=prjInfos.getScheduleInfo();
		return info;
	}
	
	public void reHandle() {
	}
}
