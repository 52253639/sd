package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseGuideLineInfo extends com.kingdee.eas.framework.DataBaseInfo implements Serializable 
{
    public AbstractAppraiseGuideLineInfo()
    {
        this("id");
    }
    protected AbstractAppraiseGuideLineInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标指标 's 评标指标类型 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo getGuideLineType()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo)get("guideLineType");
    }
    public void setGuideLineType(com.kingdee.eas.fdc.invite.AppraiseGuideLineTypeInfo item)
    {
        put("guideLineType", item);
    }
    /**
     * Object: 评标指标 's 计量单位 property 
     */
    public com.kingdee.eas.basedata.assistant.MeasureUnitInfo getUnit()
    {
        return (com.kingdee.eas.basedata.assistant.MeasureUnitInfo)get("unit");
    }
    public void setUnit(com.kingdee.eas.basedata.assistant.MeasureUnitInfo item)
    {
        put("unit", item);
    }
    /**
     * Object:评标指标's 是否启用状态property 
     */
    public boolean isIsEnable()
    {
        return getBoolean("isEnable");
    }
    public void setIsEnable(boolean item)
    {
        setBoolean("isEnable", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("EEDFF841");
    }
}