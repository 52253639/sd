package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInviteTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractInviteTypeInfo()
    {
        this("id");
    }
    protected AbstractInviteTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:采购类别's 是否启用property 
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
     * Object: 采购类别 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.InviteTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.InviteTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.InviteTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 采购类别 's 所属组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrg()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("org");
    }
    public void setOrg(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("org", item);
    }
    /**
     * Object: 采购类别 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:采购类别's 是否进入造价指标库property 
     */
    public boolean isIsCostIndex()
    {
        return getBoolean("isCostIndex");
    }
    public void setIsCostIndex(boolean item)
    {
        setBoolean("isCostIndex", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("5E986C07");
    }
}