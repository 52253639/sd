package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHECusAssistantDataInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractSHECusAssistantDataInfo()
    {
        this("id");
    }
    protected AbstractSHECusAssistantDataInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户辅助资料 's 分组 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo getGroup()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo)get("group");
    }
    public void setGroup(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataGroupInfo item)
    {
        put("group", item);
    }
    /**
     * Object: 客户辅助资料 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 客户辅助资料 's 创建组织 property 
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
        return new BOSObjectType("9D3830FC");
    }
}