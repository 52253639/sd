package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractRestReceivableInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractRestReceivableInfo()
    {
        this("id");
    }
    protected AbstractRestReceivableInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection());
        put("otherPayList", new com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection());
    }
    /**
     * Object: 其他应收 's 分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.tenancy.RestReceivableEntryCollection)get("entrys");
    }
    /**
     * Object:其他应收's 是否生成凭证property 
     */
    public boolean isFivouchered()
    {
        return getBoolean("Fivouchered");
    }
    public void setFivouchered(boolean item)
    {
        setBoolean("Fivouchered", item);
    }
    /**
     * Object: 其他应收 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object:其他应收's 单据状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum getBillState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum.getEnum(getString("billState"));
    }
    public void setBillState(com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum item)
    {
		if (item != null) {
        setString("billState", item.getValue());
		}
    }
    /**
     * Object:其他应收's 审核日期property 
     */
    public java.util.Date getAuditDate()
    {
        return getDate("auditDate");
    }
    public void setAuditDate(java.util.Date item)
    {
        setDate("auditDate", item);
    }
    /**
     * Object: 其他应收 's 其它应收条目 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection getOtherPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection)get("otherPayList");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B42ABEF9");
    }
}