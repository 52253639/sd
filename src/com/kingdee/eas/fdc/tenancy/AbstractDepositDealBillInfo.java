package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDepositDealBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractDepositDealBillInfo()
    {
        this("id");
    }
    protected AbstractDepositDealBillInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: K押金处理申请单 's 租赁合同 property 
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
     * Object:K押金处理申请单's 处理类型property 
     */
    public com.kingdee.eas.fdc.tenancy.DepositDealTypeEnum getType()
    {
        return com.kingdee.eas.fdc.tenancy.DepositDealTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.tenancy.DepositDealTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("756F2288");
    }
}