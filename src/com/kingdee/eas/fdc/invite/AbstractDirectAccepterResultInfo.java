package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDirectAccepterResultInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractDirectAccepterResultInfo()
    {
        this("id");
    }
    protected AbstractDirectAccepterResultInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.DirectAccepterResultEntryCollection());
    }
    /**
     * Object: 直接委托审批 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.DirectAccepterResultEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.DirectAccepterResultEntryCollection)get("entrys");
    }
    /**
     * Object:直接委托审批's 市场行情property 
     */
    public java.math.BigDecimal getMarket()
    {
        return getBigDecimal("market");
    }
    public void setMarket(java.math.BigDecimal item)
    {
        setBigDecimal("market", item);
    }
    /**
     * Object:直接委托审批's 标底property 
     */
    public java.math.BigDecimal getBasePrice()
    {
        return getBigDecimal("basePrice");
    }
    public void setBasePrice(java.math.BigDecimal item)
    {
        setBigDecimal("basePrice", item);
    }
    /**
     * Object:直接委托审批's 成本数据库property 
     */
    public java.math.BigDecimal getCostData()
    {
        return getBigDecimal("costData");
    }
    public void setCostData(java.math.BigDecimal item)
    {
        setBigDecimal("costData", item);
    }
    /**
     * Object:直接委托审批's 预估合同金额(无用)property 
     */
    public java.math.BigDecimal getContractPrice()
    {
        return getBigDecimal("contractPrice");
    }
    public void setContractPrice(java.math.BigDecimal item)
    {
        setBigDecimal("contractPrice", item);
    }
    /**
     * Object:直接委托审批's 差异property 
     */
    public String getDifference()
    {
        return getString("difference");
    }
    public void setDifference(String item)
    {
        setString("difference", item);
    }
    /**
     * Object:直接委托审批's 特别说明property 
     */
    public String getSpecialNote()
    {
        return getString("specialNote");
    }
    public void setSpecialNote(String item)
    {
        setString("specialNote", item);
    }
    /**
     * Object: 直接委托审批 's 图纸深度 property 
     */
    public com.kingdee.eas.fdc.invite.DrawingDepthInfo getDrawingDepth()
    {
        return (com.kingdee.eas.fdc.invite.DrawingDepthInfo)get("drawingDepth");
    }
    public void setDrawingDepth(com.kingdee.eas.fdc.invite.DrawingDepthInfo item)
    {
        put("drawingDepth", item);
    }
    /**
     * Object: 直接委托审批 's 目标成本调配方式 property 
     */
    public com.kingdee.eas.fdc.invite.DeployTypeInfo getDeployType()
    {
        return (com.kingdee.eas.fdc.invite.DeployTypeInfo)get("deployType");
    }
    public void setDeployType(com.kingdee.eas.fdc.invite.DeployTypeInfo item)
    {
        put("deployType", item);
    }
    /**
     * Object:直接委托审批's 目标成本property 
     */
    public String getAimCost()
    {
        return getString("aimCost");
    }
    public void setAimCost(String item)
    {
        setString("aimCost", item);
    }
    /**
     * Object:直接委托审批's 参考价property 
     */
    public String getRefPrice()
    {
        return getString("refPrice");
    }
    public void setRefPrice(String item)
    {
        setString("refPrice", item);
    }
    /**
     * Object:直接委托审批's 预估合同金额property 
     */
    public String getConPrice()
    {
        return getString("conPrice");
    }
    public void setConPrice(String item)
    {
        setString("conPrice", item);
    }
    /**
     * Object:直接委托审批's 承包范围property 
     */
    public String getScope()
    {
        return getString("scope");
    }
    public void setScope(String item)
    {
        setString("scope", item);
    }
    /**
     * Object:直接委托审批's 与目标成本比较property 
     */
    public com.kingdee.eas.fdc.invite.RangeEnum getCompareAim()
    {
        return com.kingdee.eas.fdc.invite.RangeEnum.getEnum(getString("compareAim"));
    }
    public void setCompareAim(com.kingdee.eas.fdc.invite.RangeEnum item)
    {
		if (item != null) {
        setString("compareAim", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7C775D9F");
    }
}