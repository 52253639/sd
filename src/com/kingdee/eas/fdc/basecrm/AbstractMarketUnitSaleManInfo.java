package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketUnitSaleManInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractMarketUnitSaleManInfo()
    {
        this("id");
    }
    protected AbstractMarketUnitSaleManInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销顾问权限 's 组织 property 
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
     * Object: 营销顾问权限 's 营销单元 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo getMarketUnit()
    {
        return (com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo)get("marketUnit");
    }
    public void setMarketUnit(com.kingdee.eas.fdc.sellhouse.MarketingUnitInfo item)
    {
        put("marketUnit", item);
    }
    /**
     * Object: 营销顾问权限 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object: 营销顾问权限 's 负责人 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getDutyPerson()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("dutyPerson");
    }
    public void setDutyPerson(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("dutyPerson", item);
    }
    /**
     * Object: 营销顾问权限 's 成员 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getMember()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("member");
    }
    public void setMember(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("member", item);
    }
    /**
     * Object:营销顾问权限's 上岗日期property 
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
     * Object:营销顾问权限's 离岗日期property 
     */
    public java.util.Date getDimissionDate()
    {
        return getDate("dimissionDate");
    }
    public void setDimissionDate(java.util.Date item)
    {
        setDate("dimissionDate", item);
    }
    /**
     * Object:营销顾问权限's 是否负责人property 
     */
    public boolean isIsDuty()
    {
        return getBoolean("isDuty");
    }
    public void setIsDuty(boolean item)
    {
        setBoolean("isDuty", item);
    }
    /**
     * Object:营销顾问权限's nullproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId()
    {
        return getBOSUuid("id");
    }
    public void setId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("id", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("D016D3B7");
    }
}