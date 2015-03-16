package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDeployTypeInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractDeployTypeInfo()
    {
        this("id");
    }
    protected AbstractDeployTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:目标成本调配方式's property 
     */
    public com.kingdee.eas.fdc.invite.DeployTypeEnum getType()
    {
        return com.kingdee.eas.fdc.invite.DeployTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.invite.DeployTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8ABE8EE5");
    }
}