package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPartABOMEntryInfo extends com.kingdee.eas.fdc.invite.BOMEntryInfo implements Serializable 
{
    public AbstractPartABOMEntryInfo()
    {
        this("id");
    }
    protected AbstractPartABOMEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 甲供物资清单 's 父节点 property 
     */
    public com.kingdee.eas.fdc.invite.PartABOMInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.PartABOMInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.PartABOMInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:甲供物资清单's 标段property 
     */
    public com.kingdee.eas.fdc.invite.SectionEnum getSection()
    {
        return com.kingdee.eas.fdc.invite.SectionEnum.getEnum(getInt("section"));
    }
    public void setSection(com.kingdee.eas.fdc.invite.SectionEnum item)
    {
		if (item != null) {
        setInt("section", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("65505EBC");
    }
}