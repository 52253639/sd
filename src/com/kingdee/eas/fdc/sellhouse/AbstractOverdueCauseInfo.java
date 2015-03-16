package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOverdueCauseInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractOverdueCauseInfo()
    {
        this("id");
    }
    protected AbstractOverdueCauseInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:逾期原因分类's 类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.OverdueCauseTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.OverdueCauseTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.OverdueCauseTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("08953604");
    }
}