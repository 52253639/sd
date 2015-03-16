package com.kingdee.eas.fdc.market;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractMovementPlanE5Info extends com.kingdee.eas.framework.BillEntryBaseInfo implements Serializable 
{
    public AbstractMovementPlanE5Info()
    {
        this("id");
    }
    protected AbstractMovementPlanE5Info(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 预计效果 's null property 
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
     * Object:预计效果's 名称property 
     */
    public String getPredictName()
    {
        return getString("predictName");
    }
    public void setPredictName(String item)
    {
        setString("predictName", item);
    }
    /**
     * Object:预计效果's 数值property 
     */
    public int getPredictValue()
    {
        return getInt("predictValue");
    }
    public void setPredictValue(int item)
    {
        setInt("predictValue", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("06163F19");
    }
}