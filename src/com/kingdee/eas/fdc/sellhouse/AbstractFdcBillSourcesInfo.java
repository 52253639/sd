package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFdcBillSourcesInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractFdcBillSourcesInfo()
    {
        this("id");
    }
    protected AbstractFdcBillSourcesInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:收款单分录来源's nullproperty 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: 收款单分录来源 's null property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 收款单分录来源 's 收款单分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo getEntrySource()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo)get("entrySource");
    }
    public void setEntrySource(com.kingdee.eas.fdc.sellhouse.FDCReceiveBillEntryInfo item)
    {
        put("entrySource", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("544F10A7");
    }
}