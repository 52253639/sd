package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceChanceAssistantInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCommerceChanceAssistantInfo()
    {
        this("id");
    }
    protected AbstractCommerceChanceAssistantInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 商机辅助资料 's 资料类别 property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo getType()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo)get("type");
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo item)
    {
        put("type", item);
    }
    /**
     * Object: 商机辅助资料 's 项目 property 
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
     * Object: 商机辅助资料 's 创建组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9CBC4874");
    }
}