package com.kingdee.eas.fdc.schedule.client;

import com.kingdee.bos.BOSException;
import com.kingdee.eas.common.EASBizException;

public interface ITaskPanelHelper {
	public void load() throws EASBizException, BOSException;
	public void commit();
    /**
     * 设置执行状态的各UI状态
     */
	public void setExecutingUIStatus();
    
    /**
     * 设置查看状态的各UI状态
     */
	public void setViewUIStatus();
    
    /**
     * 设置编辑状态的UI状态--只有计划编制的时候才会有编辑状态的任务属性
     */
    public void setEditUIStatus();
}
