package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHERevMapInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractSHERevMapInfo()
    {
        this("id");
    }
    protected AbstractSHERevMapInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:收款对冲关系's 业务单据类型property 
     */
    public com.kingdee.eas.fdc.basecrm.RelatBizType getRelatBizType()
    {
        return com.kingdee.eas.fdc.basecrm.RelatBizType.getEnum(getString("relatBizType"));
    }
    public void setRelatBizType(com.kingdee.eas.fdc.basecrm.RelatBizType item)
    {
		if (item != null) {
        setString("relatBizType", item.getValue());
		}
    }
    /**
     * Object:收款对冲关系's 交易主线应收明细Idproperty 
     */
    public String getPayListEntryId()
    {
        return getString("payListEntryId");
    }
    public void setPayListEntryId(String item)
    {
        setString("payListEntryId", item);
    }
    /**
     * Object: 收款对冲关系 's 收款明细id property 
     */
    public com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo getRevBillEntryId()
    {
        return (com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo)get("revBillEntryId");
    }
    public void setRevBillEntryId(com.kingdee.eas.fdc.basecrm.SHERevBillEntryInfo item)
    {
        put("revBillEntryId", item);
    }
    /**
     * Object:收款对冲关系's 对冲金额property 
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
     * Object:收款对冲关系's 序号property 
     */
    public int getSeq()
    {
        return getInt("seq");
    }
    public void setSeq(int item)
    {
        setInt("seq", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AC615AAD");
    }
}