package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSHEProjectInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractSHEProjectInfo()
    {
        this("id");
    }
    protected AbstractSHEProjectInfo(String pkField)
    {
        super(pkField);
        put("shareOrgUnitList", new com.kingdee.eas.fdc.sellhouse.SHEShareOrgUnitCollection());
    }
    /**
     * Object: 项目建立 's 父节点 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEProjectInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEProjectInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.SHEProjectInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 项目建立 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("project", item);
    }
    /**
     * Object:项目建立's 开工日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:项目建立's 竣工日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: 项目建立 's 组织 property 
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
     * Object: 项目建立 's 共享组织 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEShareOrgUnitCollection getShareOrgUnitList()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEShareOrgUnitCollection)get("shareOrgUnitList");
    }
    /**
     * Object: 项目建立 's 财务组织 property 
     */
    public com.kingdee.eas.basedata.org.CompanyOrgUnitInfo getCompanyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.CompanyOrgUnitInfo)get("companyOrgUnit");
    }
    public void setCompanyOrgUnit(com.kingdee.eas.basedata.org.CompanyOrgUnitInfo item)
    {
        put("companyOrgUnit", item);
    }
    /**
     * Object:项目建立's 启用禁用property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object:项目建立's 引入项目idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getSrcId()
    {
        return getBOSUuid("srcId");
    }
    public void setSrcId(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("srcId", item);
    }
    /**
     * Object:项目建立's 是否被使用property 
     */
    public boolean isIsUse()
    {
        return getBoolean("isUse");
    }
    public void setIsUse(boolean item)
    {
        setBoolean("isUse", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8BD71CA5");
    }
}