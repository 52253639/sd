package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMarketUnitSellProInfo extends AbstractObjectValue implements Serializable 
{
    public AbstractMarketUnitSellProInfo()
    {
        this("id");
    }
    protected AbstractMarketUnitSellProInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 营销单元项目权限 's 组织 property 
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
     * Object: 营销单元项目权限 's 营销单元 property 
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
     * Object: 营销单元项目权限 's 项目 property 
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
     * Object: 营销单元项目权限 's 父节点项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getParentSpro()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("parentSpro");
    }
    public void setParentSpro(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("parentSpro", item);
    }
    /**
     * Object: 营销单元项目权限 's 营销顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getSaleMan()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("saleMan");
    }
    public void setSaleMan(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("saleMan", item);
    }
    /**
     * Object:营销单元项目权限's nullproperty 
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
        return new BOSObjectType("D6ED721F");
    }
}