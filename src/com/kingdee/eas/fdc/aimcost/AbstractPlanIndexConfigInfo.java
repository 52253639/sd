package com.kingdee.eas.fdc.aimcost;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPlanIndexConfigInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractPlanIndexConfigInfo()
    {
        this("id");
    }
    protected AbstractPlanIndexConfigInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:�滮ָ��'s ���û����״̬property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: �滮ָ�� 's ���ڵ� property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo getParent()
    {
        return (com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:�滮ָ��'s �Ƿ�ɱ༭property 
     */
    public boolean isIsEdit()
    {
        return getBoolean("isEdit");
    }
    public void setIsEdit(boolean item)
    {
        setBoolean("isEdit", item);
    }
    /**
     * Object:�滮ָ��'s ����property 
     */
    public com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum getFieldType()
    {
        return com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum.getEnum(getString("fieldType"));
    }
    public void setFieldType(com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum item)
    {
		if (item != null) {
        setString("fieldType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B6FAE994");
    }
}