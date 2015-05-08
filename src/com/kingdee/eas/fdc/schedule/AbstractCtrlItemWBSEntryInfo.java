package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractCtrlItemWBSEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractCtrlItemWBSEntryInfo()
    {
        this("id");
    }
    protected AbstractCtrlItemWBSEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: WBS控制事项分录 's WBS表头 property 
     */
    public com.kingdee.eas.fdc.schedule.FDCWBSInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.FDCWBSInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.FDCWBSInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: WBS控制事项分录 's 控制事项 property 
     */
    public com.kingdee.eas.fdc.schedule.CtrlItemInfo getCtrlItem()
    {
        return (com.kingdee.eas.fdc.schedule.CtrlItemInfo)get("ctrlItem");
    }
    public void setCtrlItem(com.kingdee.eas.fdc.schedule.CtrlItemInfo item)
    {
        put("ctrlItem", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EF2B205E");
    }
}