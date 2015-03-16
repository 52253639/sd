package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDrawingDepthInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractDrawingDepthInfo()
    {
        this("id");
    }
    protected AbstractDrawingDepthInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:Õº÷Ω…Ó∂»'s property 
     */
    public com.kingdee.eas.fdc.invite.DrawingDepthEnum getType()
    {
        return com.kingdee.eas.fdc.invite.DrawingDepthEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.DrawingDepthEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DBE77669");
    }
}