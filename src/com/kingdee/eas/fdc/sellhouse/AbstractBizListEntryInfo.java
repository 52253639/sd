package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBizListEntryInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractBizListEntryInfo()
    {
        this("id");
    }
    protected AbstractBizListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 业务流程分录 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("head", item);
    }
    /**
     * Object:业务流程分录's 业务时间property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizTimeEnum getBizTime()
    {
        return com.kingdee.eas.fdc.sellhouse.BizTimeEnum.getEnum(getString("bizTime"));
    }
    public void setBizTime(com.kingdee.eas.fdc.sellhouse.BizTimeEnum item)
    {
		if (item != null) {
        setString("bizTime", item.getValue());
		}
    }
    /**
     * Object:业务流程分录's 业务流程property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizFlowEnum getBizFlow()
    {
        return com.kingdee.eas.fdc.sellhouse.BizFlowEnum.getEnum(getString("bizFlow"));
    }
    public void setBizFlow(com.kingdee.eas.fdc.sellhouse.BizFlowEnum item)
    {
		if (item != null) {
        setString("bizFlow", item.getValue());
		}
    }
    /**
     * Object:业务流程分录's 月内property 
     */
    public int getMonthLimit()
    {
        return getInt("monthLimit");
    }
    public void setMonthLimit(int item)
    {
        setInt("monthLimit", item);
    }
    /**
     * Object:业务流程分录's 日内property 
     */
    public int getDayLimit()
    {
        return getInt("dayLimit");
    }
    public void setDayLimit(int item)
    {
        setInt("dayLimit", item);
    }
    /**
     * Object:业务流程分录's 指定日期property 
     */
    public java.util.Date getAppDate()
    {
        return getDate("appDate");
    }
    public void setAppDate(java.util.Date item)
    {
        setDate("appDate", item);
    }
    /**
     * Object:业务流程分录's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    /**
     * Object:业务流程分录's 参考时间property 
     */
    public String getPayTypeBizTime()
    {
        return getString("payTypeBizTime");
    }
    public void setPayTypeBizTime(String item)
    {
        setString("payTypeBizTime", item);
    }
    /**
     * Object:业务流程分录's 新业务名称property 
     */
    public com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum getPayTypeBizFlow()
    {
        return com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum.getEnum(getString("payTypeBizFlow"));
    }
    public void setPayTypeBizFlow(com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum item)
    {
		if (item != null) {
        setString("payTypeBizFlow", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D424B8FC");
    }
}