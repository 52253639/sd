package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCommerceChanceDataTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractCommerceChanceDataTypeInfo()
    {
        this("id");
    }
    protected AbstractCommerceChanceDataTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 商机辅助资料类别 's parent property 
     */
    public com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.sellhouse.CommerceChanceDataTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:商机辅助资料类别's 是否预设property 
     */
    public boolean isIsDefault()
    {
        return getBoolean("isDefault");
    }
    public void setIsDefault(boolean item)
    {
        setBoolean("isDefault", item);
    }
    /**
     * Object: 商机辅助资料类别 's 创建组织 property 
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
        return new BOSObjectType("B7442DAE");
    }
}