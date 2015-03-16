package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierGuideEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierGuideEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierGuideEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 评审指标分录 's 评审模板-评审指标分录 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseTemplateInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 评审指标分录 's 评审指标 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo getSplAuditIndex()
    {
        return (com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo)get("splAuditIndex");
    }
    public void setSplAuditIndex(com.kingdee.eas.fdc.invite.supplier.FDCSplAuditIndexInfo item)
    {
        put("splAuditIndex", item);
    }
    /**
     * Object:评审指标分录's 评分类别property 
     */
    public com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum getAppraiseType()
    {
        return com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum.getEnum(getString("appraiseType"));
    }
    public void setAppraiseType(com.kingdee.eas.fdc.invite.supplier.AppraiseTypeEnum item)
    {
		if (item != null) {
        setString("appraiseType", item.getValue());
		}
    }
    /**
     * Object:评审指标分录's 权重property 
     */
    public java.math.BigDecimal getWeight()
    {
        return getBigDecimal("weight");
    }
    public void setWeight(java.math.BigDecimal item)
    {
        setBigDecimal("weight", item);
    }
    /**
     * Object:评审指标分录's 备注property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("ED86A740");
    }
}