package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenderAccepterResultInfo extends com.kingdee.eas.fdc.invite.BaseInviteInfo implements Serializable 
{
    public AbstractTenderAccepterResultInfo()
    {
        this("id");
    }
    protected AbstractTenderAccepterResultInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection());
    }
    /**
     * Object: 中标审批 's 分录 property 
     */
    public com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.invite.TenderAccepterResultEntryCollection)get("entrys");
    }
    /**
     * Object:中标审批's 市场行情property 
     */
    public String getMarket()
    {
        return getString("market");
    }
    public void setMarket(String item)
    {
        setString("market", item);
    }
    /**
     * Object:中标审批's 标底property 
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
     * Object:中标审批's 成本数据库property 
     */
    public String getCostData()
    {
        return getString("costData");
    }
    public void setCostData(String item)
    {
        setString("costData", item);
    }
    /**
     * Object:中标审批's 预估合同金额property 
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
     * Object:中标审批's 目标成本property 
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
     * Object:中标审批's 特别说明property 
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
     * Object:中标审批's 超目标成本原因property 
     */
    public String getReason()
    {
        return getString("reason");
    }
    public void setReason(String item)
    {
        setString("reason", item);
    }
    /**
     * Object: 中标审批 's 目标成本调配方式 property 
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
     * Object: 中标审批 's 图纸深度 property 
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
     * Object:中标审批's 参考价property 
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
     * Object:中标审批's 与目标成本比较property 
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
        return new BOSObjectType("6C66BD4A");
    }
}