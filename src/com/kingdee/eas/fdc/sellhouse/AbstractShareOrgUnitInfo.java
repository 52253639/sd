package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractShareOrgUnitInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractShareOrgUnitInfo()
    {
        this("id");
    }
    protected AbstractShareOrgUnitInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 共享组织单元 's 头 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("head", item);
    }
    /**
     * Object: 共享组织单元 's 共享组织 property 
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
     * Object: 共享组织单元 's 共享操作人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getOperationPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("operationPerson");
    }
    public void setOperationPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("operationPerson", item);
    }
    /**
     * Object:共享组织单元's 共享日期property 
     */
    public java.util.Date getShareDate()
    {
        return getDate("shareDate");
    }
    public void setShareDate(java.util.Date item)
    {
        setDate("shareDate", item);
    }
    /**
     * Object: 共享组织单元 's 项目创建组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getCreateOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("createOrgUnit");
    }
    public void setCreateOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("createOrgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D78D6004");
    }
}