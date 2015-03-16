package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCOrgStructureInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractFDCOrgStructureInfo()
    {
        this("id");
    }
    protected AbstractFDCOrgStructureInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:业务组织设置's 是否封存property 
     */
    public boolean isIsPreservation()
    {
        return getBoolean("isPreservation");
    }
    public void setIsPreservation(boolean item)
    {
        setBoolean("isPreservation", item);
    }
    /**
     * Object:业务组织设置's 是否历史property 
     */
    public boolean isIsHis()
    {
        return getBoolean("isHis");
    }
    public void setIsHis(boolean item)
    {
        setBoolean("isHis", item);
    }
    /**
     * Object:业务组织设置's 客户管理单元property 
     */
    public boolean isCustMangageUnit()
    {
        return getBoolean("custMangageUnit");
    }
    public void setCustMangageUnit(boolean item)
    {
        setBoolean("custMangageUnit", item);
    }
    /**
     * Object:业务组织设置's 售楼组织property 
     */
    public boolean isSellHouseStrut()
    {
        return getBoolean("sellHouseStrut");
    }
    public void setSellHouseStrut(boolean item)
    {
        setBoolean("sellHouseStrut", item);
    }
    /**
     * Object:业务组织设置's 是否已执行property 
     */
    public boolean isIsExecuted()
    {
        return getBoolean("isExecuted");
    }
    public void setIsExecuted(boolean item)
    {
        setBoolean("isExecuted", item);
    }
    /**
     * Object:业务组织设置's 组织结构idproperty 
     */
    public com.kingdee.bos.util.BOSUuid getOrgStructure()
    {
        return getBOSUuid("orgStructure");
    }
    public void setOrgStructure(com.kingdee.bos.util.BOSUuid item)
    {
        setBOSUuid("orgStructure", item);
    }
    /**
     * Object:业务组织设置's 长编码property 
     */
    public String getLongNumber()
    {
        return getString("longNumber");
    }
    public void setLongNumber(String item)
    {
        setString("longNumber", item);
    }
    /**
     * Object:业务组织设置's 级次property 
     */
    public int getLevel()
    {
        return getInt("level");
    }
    public void setLevel(int item)
    {
        setInt("level", item);
    }
    /**
     * Object:业务组织设置's 是否孩子property 
     */
    public boolean isIsLeaf()
    {
        return getBoolean("isLeaf");
    }
    public void setIsLeaf(boolean item)
    {
        setBoolean("isLeaf", item);
    }
    /**
     * Object: 业务组织设置 's 范围 property 
     */
    public com.kingdee.eas.basedata.org.OrgTreeInfo getTree()
    {
        return (com.kingdee.eas.basedata.org.OrgTreeInfo)get("tree");
    }
    public void setTree(com.kingdee.eas.basedata.org.OrgTreeInfo item)
    {
        put("tree", item);
    }
    /**
     * Object: 业务组织设置 's 组织单元 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object: 业务组织设置 's 父节点 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:业务组织设置's 是否失效property 
     */
    public boolean isIsValid()
    {
        return getBoolean("isValid");
    }
    public void setIsValid(boolean item)
    {
        setBoolean("isValid", item);
    }
    /**
     * Object:业务组织设置's 显示名称property 
     */
    public String getDisplayName()
    {
        return getDisplayName((Locale)null);
    }
    public void setDisplayName(String item)
    {
		setDisplayName(item,(Locale)null);
    }
    public String getDisplayName(Locale local)
    {
        return TypeConversionUtils.objToString(get("displayName", local));
    }
    public void setDisplayName(String item, Locale local)
    {
        put("displayName", item, local);
    }
    /**
     * Object:业务组织设置's 排序码property 
     */
    public String getSortCode()
    {
        return getString("sortCode");
    }
    public void setSortCode(String item)
    {
        setString("sortCode", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("6A12CC18");
    }
}