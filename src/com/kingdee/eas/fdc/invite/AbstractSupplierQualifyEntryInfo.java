package com.kingdee.eas.fdc.invite;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractSupplierQualifyEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractSupplierQualifyEntryInfo()
    {
        this("id");
    }
    protected AbstractSupplierQualifyEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 入围供应商分录 's 入围供应商 property 
     */
    public com.kingdee.eas.fdc.invite.SupplierQualifyInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.SupplierQualifyInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.SupplierQualifyInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 入围供应商分录 's 供应商 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getSupplier()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object:入围供应商分录's 联系人property 
     */
    public String getLinkPerson()
    {
        return getString("linkPerson");
    }
    public void setLinkPerson(String item)
    {
        setString("linkPerson", item);
    }
    /**
     * Object:入围供应商分录's 联系电话property 
     */
    public String getLinkPhone()
    {
        return getString("linkPhone");
    }
    public void setLinkPhone(String item)
    {
        setString("linkPhone", item);
    }
    /**
     * Object:入围供应商分录's 供应商状态property 
     */
    public com.kingdee.eas.fdc.invite.supplier.IsGradeEnum getSupplierState()
    {
        return com.kingdee.eas.fdc.invite.supplier.IsGradeEnum.getEnum(getInt("supplierState"));
    }
    public void setSupplierState(com.kingdee.eas.fdc.invite.supplier.IsGradeEnum item)
    {
		if (item != null) {
        setInt("supplierState", item.getValue());
		}
    }
    /**
     * Object:入围供应商分录's 投标意向征询结果property 
     */
    public com.kingdee.eas.fdc.invite.ResultEnum getResult()
    {
        return com.kingdee.eas.fdc.invite.ResultEnum.getEnum(getString("result"));
    }
    public void setResult(com.kingdee.eas.fdc.invite.ResultEnum item)
    {
		if (item != null) {
        setString("result", item.getValue());
		}
    }
    /**
     * Object:入围供应商分录's 不参投原因property 
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
     * Object:入围供应商分录's 是否入围property 
     */
    public com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum getIsAccept()
    {
        return com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum.getEnum(getInt("isAccept"));
    }
    public void setIsAccept(com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum item)
    {
		if (item != null) {
        setInt("isAccept", item.getValue());
		}
    }
    /**
     * Object:入围供应商分录's 备注property 
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
     * Object:入围供应商分录's 推荐人property 
     */
    public String getRecommended()
    {
        return getString("recommended");
    }
    public void setRecommended(String item)
    {
        setString("recommended", item);
    }
    /**
     * Object:入围供应商分录's 项目经理人property 
     */
    public String getManager()
    {
        return getString("manager");
    }
    public void setManager(String item)
    {
        setString("manager", item);
    }
    /**
     * Object:入围供应商分录's 合作状态property 
     */
    public String getCoState()
    {
        return getString("coState");
    }
    public void setCoState(String item)
    {
        setString("coState", item);
    }
    /**
     * Object:入围供应商分录's 履约得分property 
     */
    public java.math.BigDecimal getScore1()
    {
        return getBigDecimal("score1");
    }
    public void setScore1(java.math.BigDecimal item)
    {
        setBigDecimal("score1", item);
    }
    /**
     * Object:入围供应商分录's 考察得分property 
     */
    public java.math.BigDecimal getScore2()
    {
        return getBigDecimal("score2");
    }
    public void setScore2(java.math.BigDecimal item)
    {
        setBigDecimal("score2", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A6876635");
    }
}