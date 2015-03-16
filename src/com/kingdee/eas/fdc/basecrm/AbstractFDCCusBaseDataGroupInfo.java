package com.kingdee.eas.fdc.basecrm;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFDCCusBaseDataGroupInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractFDCCusBaseDataGroupInfo()
    {
        this("id");
    }
    protected AbstractFDCCusBaseDataGroupInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 客户基础资料类别 's 上级 property 
     */
    public com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo getParent()
    {
        return (com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basecrm.FDCCusBaseDataGroupInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:客户基础资料类别's 系统预设property 
     */
    public boolean isSysPreSet()
    {
        return getBoolean("sysPreSet");
    }
    public void setSysPreSet(boolean item)
    {
        setBoolean("sysPreSet", item);
    }
    /**
     * Object:客户基础资料类别's property 
     */
    public com.kingdee.eas.fdc.basecrm.UseTypeEnum getUseType()
    {
        return com.kingdee.eas.fdc.basecrm.UseTypeEnum.getEnum(getString("useType"));
    }
    public void setUseType(com.kingdee.eas.fdc.basecrm.UseTypeEnum item)
    {
		if (item != null) {
        setString("useType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("4851950C");
    }
}