package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanE6Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanE6Info()
    {
        this("id");
    }
    protected AbstractMovementPlanE6Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 媒体管理 's null property 
     */
    public com.kingdee.eas.fdc.market.MovementPlanInfo getParent()
    {
        return (com.kingdee.eas.fdc.market.MovementPlanInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.market.MovementPlanInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 媒体管理 's 媒体类型 property 
     */
    public com.kingdee.eas.fdc.market.MediaInfo getMediaType()
    {
        return (com.kingdee.eas.fdc.market.MediaInfo)get("mediaType");
    }
    public void setMediaType(com.kingdee.eas.fdc.market.MediaInfo item)
    {
        put("mediaType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06163F1A");
    }
}