package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketUnitControlInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractMarketUnitControlInfo()
    {
        this("id");
    }
    protected AbstractMarketUnitControlInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销管控 's 所在组织 property 
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
     * Object: 营销管控 's 管理人员 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getControler()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("controler");
    }
    public void setControler(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("controler", item);
    }
    /**
     * Object:营销管控's 上岗日期property 
     */
    public java.util.Date getAccessionDate()
    {
        return getDate("accessionDate");
    }
    public void setAccessionDate(java.util.Date item)
    {
        setDate("accessionDate", item);
    }
    /**
     * Object:营销管控's 离岗日期property 
     */
    public java.util.Date getDimissionDate()
    {
        return getDate("dimissionDate");
    }
    public void setDimissionDate(java.util.Date item)
    {
        setDate("dimissionDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("812147E2");
    }
}