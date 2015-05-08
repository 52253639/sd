package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;

import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;

public class RESchTemplateTaskPredecessorInfo extends AbstractRESchTemplateTaskPredecessorInfo implements Serializable,
		IRESchTaskPredecessor
{
    public RESchTemplateTaskPredecessorInfo()
    {
        super();
    }
    protected RESchTemplateTaskPredecessorInfo(String pkField)
    {
        super(pkField);
    }
	public IRESchTask getCurrentTask() {
		return null;
	}

	public IRESchTask getPredecessor() {
		return null;
	}

	public void setCurrentTask(IRESchTask task) {
		this.setTask((RESchTemplateTaskInfo) task);

	}

	public void setPredecessor(IRESchTask task) {
		this.setPredecessorTask((RESchTemplateTaskInfo) task);

	}
	

	
}