package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpertAppraiseEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractExpertAppraiseEntryInfo()
    {
        this("id");
    }
    protected AbstractExpertAppraiseEntryInfo(String pkField)
    {
        super(pkField);
        put("scores", new com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreCollection());
    }
    /**
     * Object: 评标专家分录 's 评标指标 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo getGuideLine()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo)get("guideLine");
    }
    public void setGuideLine(com.kingdee.eas.fdc.invite.AppraiseGuideLineInfo item)
    {
        put("guideLine", item);
    }
    /**
     * Object:评标专家分录's 权重property 
     */
    public double getWeight()
    {
        return getDouble("weight");
    }
    public void setWeight(double item)
    {
        setDouble("weight", item);
    }
    /**
     * Object:评标专家分录's 说明property 
     */
    public String getDescription()
    {
        return getString("description");
    }
    public void setDescription(String item)
    {
        setString("description", item);
    }
    /**
     * Object: 评标专家分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.invite.ExpertAppraiseInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.ExpertAppraiseInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.ExpertAppraiseInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 评标专家分录 's 供应商得分 property 
     */
    public com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreCollection getScores()
    {
        return (com.kingdee.eas.fdc.invite.ExpertAppraiseEntryScoreCollection)get("scores");
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7703E119");
    }
}