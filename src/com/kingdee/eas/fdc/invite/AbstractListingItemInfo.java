package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractListingItemInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractListingItemInfo()
    {
        this("id");
    }
    protected AbstractListingItemInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 清单子目 's 表头类型 property 
     */
    public com.kingdee.eas.fdc.invite.HeadTypeInfo getHeadType()
    {
        return (com.kingdee.eas.fdc.invite.HeadTypeInfo)get("headType");
    }
    public void setHeadType(com.kingdee.eas.fdc.invite.HeadTypeInfo item)
    {
        put("headType", item);
    }
    /**
     * Object: 清单子目 's 子目所属组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOriOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("oriOrg");
    }
    public void setOriOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("oriOrg", item);
    }
    /**
     * Object:清单子目's 单位property 
     */
    public String getUnit()
    {
        return getString("unit");
    }
    public void setUnit(String item)
    {
        setString("unit", item);
    }
    /**
     * Object:清单子目's 是否关键子目property 
     */
    public boolean isIsImportant()
    {
        return getBoolean("isImportant");
    }
    public void setIsImportant(boolean item)
    {
        setBoolean("isImportant", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F200A613");
    }
}