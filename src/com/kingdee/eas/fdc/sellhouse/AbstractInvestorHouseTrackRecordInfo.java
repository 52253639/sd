package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvestorHouseTrackRecordInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractInvestorHouseTrackRecordInfo()
    {
        this("id");
    }
    protected AbstractInvestorHouseTrackRecordInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 投资房源跟进记录 's 投资房源头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.InvestorHouseInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 投资房源跟进记录 's 跟进人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTrackPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("trackPerson");
    }
    public void setTrackPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("trackPerson", item);
    }
    /**
     * Object:投资房源跟进记录's 跟进日期property 
     */
    public java.util.Date getTrackDate()
    {
        return getDate("trackDate");
    }
    public void setTrackDate(java.util.Date item)
    {
        setDate("trackDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E2FB70CF");
    }
}