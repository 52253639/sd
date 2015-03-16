package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAreaCompensateRevListInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractAreaCompensateRevListInfo()
    {
        this("id");
    }
    protected AbstractAreaCompensateRevListInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 面积补差明细 's 补差单 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo getHead()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.sellhouse.RoomAreaCompensateInfo item)
    {
        put("head", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("179EB1A2");
    }
}