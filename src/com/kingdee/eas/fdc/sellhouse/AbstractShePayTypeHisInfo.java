package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShePayTypeHisInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractShePayTypeHisInfo()
    {
        this("id");
    }
    protected AbstractShePayTypeHisInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 交易付款方案历史分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BusinessOverViewInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BusinessOverViewInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BusinessOverViewInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 交易付款方案历史分录 's 付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getShePayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("shePayType");
    }
    public void setShePayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("shePayType", item);
    }
    /**
     * Object:交易付款方案历史分录's 业务日期property 
     */
    public java.sql.Timestamp getBizDate()
    {
        return getTimestamp("bizDate");
    }
    public void setBizDate(java.sql.Timestamp item)
    {
        setTimestamp("bizDate", item);
    }
    /**
     * Object:交易付款方案历史分录's nullproperty 
     */
    public com.kingdee.eas.fdc.sellhouse.TranLinkEnum getCurrentState()
    {
        return com.kingdee.eas.fdc.sellhouse.TranLinkEnum.getEnum(getString("currentState"));
    }
    public void setCurrentState(com.kingdee.eas.fdc.sellhouse.TranLinkEnum item)
    {
		if (item != null) {
        setString("currentState", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A3043605");
    }
}