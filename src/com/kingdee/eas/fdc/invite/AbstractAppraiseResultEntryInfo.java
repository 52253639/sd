package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAppraiseResultEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAppraiseResultEntryInfo()
    {
        this("id");
    }
    protected AbstractAppraiseResultEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评标结果分录 's 评标结果 property 
     */
    public com.kingdee.eas.fdc.invite.AppraiseResultInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.AppraiseResultInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.AppraiseResultInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:评标结果分录's 专家评标得分property 
     */
    public double getScore()
    {
        return getDouble("score");
    }
    public void setScore(double item)
    {
        setDouble("score", item);
    }
    /**
     * Object:评标结果分录's 是否参与评标property 
     */
    public boolean isIsPartIn()
    {
        return getBoolean("isPartIn");
    }
    public void setIsPartIn(boolean item)
    {
        setBoolean("isPartIn", item);
    }
    /**
     * Object:评标结果分录's 专家评标排名property 
     */
    public int getExpertAppraiseSeq()
    {
        return getInt("expertAppraiseSeq");
    }
    public void setExpertAppraiseSeq(int item)
    {
        setInt("expertAppraiseSeq", item);
    }
    /**
     * Object:评标结果分录's 投标报价property 
     */
    public java.math.BigDecimal getBidAmount()
    {
        return getBigDecimal("bidAmount");
    }
    public void setBidAmount(java.math.BigDecimal item)
    {
        setBigDecimal("bidAmount", item);
    }
    /**
     * Object:评标结果分录's 谈判价property 
     */
    public java.math.BigDecimal getTreatAmount()
    {
        return getBigDecimal("treatAmount");
    }
    public void setTreatAmount(java.math.BigDecimal item)
    {
        setBigDecimal("treatAmount", item);
    }
    /**
     * Object:评标结果分录's 总价排名property 
     */
    public int getTotalSeq()
    {
        return getInt("totalSeq");
    }
    public void setTotalSeq(int item)
    {
        setInt("totalSeq", item);
    }
    /**
     * Object: 评标结果分录 's 投标人 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:评标结果分录's 投标报价是否导入property 
     */
    public boolean isIsImport()
    {
        return getBoolean("isImport");
    }
    public void setIsImport(boolean item)
    {
        setBoolean("isImport", item);
    }
    /**
     * Object:评标结果分录's 中标property 
     */
    public boolean isHit()
    {
        return getBoolean("hit");
    }
    public void setHit(boolean item)
    {
        setBoolean("hit", item);
    }
    /**
     * Object:评标结果分录's 预中标property 
     */
    public boolean isPreHit()
    {
        return getBoolean("preHit");
    }
    public void setPreHit(boolean item)
    {
        setBoolean("preHit", item);
    }
    /**
     * Object:评标结果分录's nullproperty 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    /**
     * Object:评标结果分录's nullproperty 
     */
    public com.kingdee.eas.fdc.invite.EffectivenessEnum getEffective()
    {
        return com.kingdee.eas.fdc.invite.EffectivenessEnum.getEnum(getInt("effective"));
    }
    public void setEffective(com.kingdee.eas.fdc.invite.EffectivenessEnum item)
    {
		if (item != null) {
        setInt("effective", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("3D324946");
    }
}