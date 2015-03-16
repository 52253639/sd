package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;

import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;

public class AreaCompensateRevListInfo extends AbstractAreaCompensateRevListInfo implements Serializable 
{
    public AreaCompensateRevListInfo()
    {
        super();
    }
    protected AreaCompensateRevListInfo(String pkField)
    {
        super(pkField);
    }
    
    public RevListTypeEnum getRevListTypeEnum() {
    	return RevListTypeEnum.areaCompensate;
    }

    public String getDesc() {
    	return "面积补差应收明细";
    }
}