package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCluesManageInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractCluesManageInfo()
    {
        this("id");
    }
    protected AbstractCluesManageInfo(String pkField)
    {
        super(pkField);
        put("shareProperty", new com.kingdee.eas.fdc.sellhouse.SharePropertyCollection());
        put("shareProject", new com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection());
    }
    /**
     * Object:线索管理's 联系电话property 
     */
    public String getPhone()
    {
        return getString("phone");
    }
    public void setPhone(String item)
    {
        setString("phone", item);
    }
    /**
     * Object:线索管理's 来源property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesSourceEnum getSource()
    {
        return com.kingdee.eas.fdc.sellhouse.CluesSourceEnum.getEnum(getString("source"));
    }
    public void setSource(com.kingdee.eas.fdc.sellhouse.CluesSourceEnum item)
    {
		if (item != null) {
        setString("source", item.getValue());
		}
    }
    /**
     * Object:线索管理's 线索状态property 
     */
    public com.kingdee.eas.fdc.sellhouse.CluesStatusEnum getCluesStatus()
    {
        return com.kingdee.eas.fdc.sellhouse.CluesStatusEnum.getEnum(getString("cluesStatus"));
    }
    public void setCluesStatus(com.kingdee.eas.fdc.sellhouse.CluesStatusEnum item)
    {
		if (item != null) {
        setString("cluesStatus", item.getValue());
		}
    }
    /**
     * Object: 线索管理 's 项目 property 
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
     * Object: 线索管理 's 置业顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getPropertyConsultant()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("propertyConsultant");
    }
    public void setPropertyConsultant(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("propertyConsultant", item);
    }
    /**
     * Object: 线索管理 's 共享置业顾问 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SharePropertyCollection getShareProperty()
    {
        return (com.kingdee.eas.fdc.sellhouse.SharePropertyCollection)get("shareProperty");
    }
    /**
     * Object: 线索管理 's 认知途径 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo getCognizePath()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo)get("cognizePath");
    }
    public void setCognizePath(com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo item)
    {
        put("cognizePath", item);
    }
    /**
     * Object: 线索管理 's 共享项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection getShareProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.ShareSellProjectCollection)get("shareProject");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EA85C324");
    }
}