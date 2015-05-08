package com.kingdee.eas.fdc.schedule.framework.ext;

import com.kingdee.eas.fdc.schedule.framework.ScheduleBaseInfo;

import net.sourceforge.ganttproject.PrjInfos;

public class KDPrjInfos extends PrjInfos {
	private ScheduleBaseInfo info=null;
	public KDPrjInfos(ScheduleBaseInfo info){
		setScheduleInfo(info);
	}
	public ScheduleBaseInfo getScheduleInfo(){
		return this.info;
	}
	private void setScheduleInfo(ScheduleBaseInfo info){
		if(info==null){
			throw new NullPointerException("ScheduleBaseInfo can't be null!");
		}
		this.info=info;
	}
	public String getName() {
		return this.info.getName();
	}
	
    public String getNumber() {
        return this.info.getNumber();
    }

    /** @return the description of the project. */
    public String getDescription() {
        return this.info.getDescription();
    }

    /** @return the organization of the project. */
    public String getOrganization() {
        return this.info.getOrgUnit().getName();
    }

    /** @return the web link for the project or for the company. */
    public String getWebLink() {
        return this.info.getWebLink();
    }
}
