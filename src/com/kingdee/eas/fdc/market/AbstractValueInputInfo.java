package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractValueInputInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractValueInputInfo()
    {
        this("id");
    }
    protected AbstractValueInputInfo(String pkField)
    {
        super(pkField);
        put("dyEntry", new com.kingdee.eas.fdc.market.ValueInputDYEntryCollection());
        put("priceEntry", new com.kingdee.eas.fdc.market.ValueInputPriceEntryCollection());
        put("entry", new com.kingdee.eas.fdc.market.ValueInputEntryCollection());
    }
    /**
     * Object: 货值填报 's 项目阶段 property 
     */
    public com.kingdee.eas.fdc.basedata.IndexVersionInfo getIndexVersion()
    {
        return (com.kingdee.eas.fdc.basedata.IndexVersionInfo)get("indexVersion");
    }
    public void setIndexVersion(com.kingdee.eas.fdc.basedata.IndexVersionInfo item)
    {
        put("indexVersion", item);
    }
    /**
     * Object: 货值填报 's 项目 property 
     */
    public com.kingdee.eas.fdc.basedata.ProjectBaseInfo getProject()
    {
        return (com.kingdee.eas.fdc.basedata.ProjectBaseInfo)get("project");
    }
    public void setProject(com.kingdee.eas.fdc.basedata.ProjectBaseInfo item)
    {
        put("project", item);
    }
    /**
     * Object: 货值填报 's 分录 property 
     */
    public com.kingdee.eas.fdc.market.ValueInputEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.market.ValueInputEntryCollection)get("entry");
    }
    /**
     * Object: 货值填报 's 汇总分录 property 
     */
    public com.kingdee.eas.fdc.market.ValueInputDYEntryCollection getDyEntry()
    {
        return (com.kingdee.eas.fdc.market.ValueInputDYEntryCollection)get("dyEntry");
    }
    /**
     * Object: 货值填报 's 定价分录 property 
     */
    public com.kingdee.eas.fdc.market.ValueInputPriceEntryCollection getPriceEntry()
    {
        return (com.kingdee.eas.fdc.market.ValueInputPriceEntryCollection)get("priceEntry");
    }
    /**
     * Object:货值填报's 是否最新property 
     */
    public boolean isIsLatest()
    {
        return getBoolean("isLatest");
    }
    public void setIsLatest(boolean item)
    {
        setBoolean("isLatest", item);
    }
    /**
     * Object:货值填报's 版本号property 
     */
    public int getVersion()
    {
        return getInt("version");
    }
    public void setVersion(int item)
    {
        setInt("version", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CEFF296A");
    }
}