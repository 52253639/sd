package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTranBusinessOverViewInfo extends com.kingdee.eas.fdc.sellhouse.TranPayListEntryInfo implements Serializable 
{
    public AbstractTranBusinessOverViewInfo()
    {
        this("id");
    }
    protected AbstractTranBusinessOverViewInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:交易主线业务总览's 业务名称property 
     */
    public String getBusinessName()
    {
        return getString("businessName");
    }
    public void setBusinessName(String item)
    {
        setString("businessName", item);
    }
    /**
     * Object:交易主线业务总览's 应完成时间property 
     */
    public java.util.Date getFinishDate()
    {
        return getDate("finishDate");
    }
    public void setFinishDate(java.util.Date item)
    {
        setDate("finishDate", item);
    }
    /**
     * Object:交易主线业务总览's 实际完成时间property 
     */
    public java.util.Date getActualFinishDate()
    {
        return getDate("actualFinishDate");
    }
    public void setActualFinishDate(java.util.Date item)
    {
        setDate("actualFinishDate", item);
    }
    /**
     * Object:交易主线业务总览's 是否完成property 
     */
    public boolean isIsFinish()
    {
        return getBoolean("isFinish");
    }
    public void setIsFinish(boolean item)
    {
        setBoolean("isFinish", item);
    }
    /**
     * Object:交易主线业务总览's 描述property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:交易主线业务总览's 单据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.BusTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.BusTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.BusTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object: 交易主线业务总览 's 交易单id property 
     */
    public com.kingdee.eas.fdc.sellhouse.TransactionInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.TransactionInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.TransactionInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 交易主线业务总览 's 交易历史 property 
     */
    public com.kingdee.eas.fdc.sellhouse.TranStateHisInfo getHisHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.TranStateHisInfo)get("hisHead");
    }
    public void setHisHead(com.kingdee.eas.fdc.sellhouse.TranStateHisInfo item)
    {
        put("hisHead", item);
    }
    /**
     * Object:交易主线业务总览's 是否已代收property 
     */
    public boolean isIsSubstitute()
    {
        return getBoolean("isSubstitute");
    }
    public void setIsSubstitute(boolean item)
    {
        setBoolean("isSubstitute", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7FB3321F");
    }
}