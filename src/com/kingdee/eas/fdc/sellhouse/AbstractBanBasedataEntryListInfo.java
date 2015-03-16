package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractBanBasedataEntryListInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractBanBasedataEntryListInfo()
    {
        this("id");
    }
    protected AbstractBanBasedataEntryListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 主数据楼栋分录 's 主数据楼栋 property 
     */
    public com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo getBanBasedataEntry()
    {
        return (com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo)get("banBasedataEntry");
    }
    public void setBanBasedataEntry(com.kingdee.eas.fdc.basedata.BanBasedataEntryInfo item)
    {
        put("banBasedataEntry", item);
    }
    /**
     * Object: 主数据楼栋分录 's 楼栋 property 
     */
    public com.kingdee.eas.fdc.sellhouse.BuildingInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.BuildingInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.BuildingInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("0B6B1781");
    }
}