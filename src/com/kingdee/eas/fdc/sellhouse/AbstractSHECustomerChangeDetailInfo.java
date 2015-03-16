package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHECustomerChangeDetailInfo extends com.kingdee.eas.framework.CoreBillBaseInfo implements Serializable 
{
    public AbstractSHECustomerChangeDetailInfo()
    {
        this("id");
    }
    protected AbstractSHECustomerChangeDetailInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:客户状态变动记录's 变更原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object: 客户状态变动记录 's 置业顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPropertyConsultant()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("propertyConsultant");
    }
    public void setPropertyConsultant(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("propertyConsultant", item);
    }
    /**
     * Object: 客户状态变动记录 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    /**
     * Object: 客户状态变动记录 's 售楼客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECustomerInfo getSheCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECustomerInfo)get("sheCustomer");
    }
    public void setSheCustomer(com.kingdee.eas.fdc.sellhouse.SHECustomerInfo item)
    {
        put("sheCustomer", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5BC352B4");
    }
}