package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractChangeReasonInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractChangeReasonInfo()
    {
        this("id");
    }
    protected AbstractChangeReasonInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:变更原因's 变更类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum getBizType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum.getEnum(getString("bizType"));
    }
    public void setBizType(com.kingdee.eas.fdc.sellhouse.ChangeBizTypeEnum item)
    {
		if (item != null) {
        setString("bizType", item.getValue());
		}
    }
    /**
     * Object: 变更原因 's 组织 property 
     */
    public com.kingdee.eas.basedata.org.FullOrgUnitInfo getOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.FullOrgUnitInfo)get("orgUnit");
    }
    public void setOrgUnit(com.kingdee.eas.basedata.org.FullOrgUnitInfo item)
    {
        put("orgUnit", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("303B046F");
    }
}