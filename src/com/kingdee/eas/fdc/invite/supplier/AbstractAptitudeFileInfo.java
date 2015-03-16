package com.kingdee.eas.fdc.invite.supplier;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractAptitudeFileInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractAptitudeFileInfo()
    {
        this("id");
    }
    protected AbstractAptitudeFileInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:资质文件's 文件标号property 
     */
    public String getAptNumber()
    {
        return getString("aptNumber");
    }
    public void setAptNumber(String item)
    {
        setString("aptNumber", item);
    }
    /**
     * Object:资质文件's 文件名称property 
     */
    public String getAptName()
    {
        return getString("aptName");
    }
    public void setAptName(String item)
    {
        setString("aptName", item);
    }
    /**
     * Object:资质文件's 资质等级property 
     */
    public String getAptLevel()
    {
        return getString("aptLevel");
    }
    public void setAptLevel(String item)
    {
        setString("aptLevel", item);
    }
    /**
     * Object:资质文件's 到期时间property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object:资质文件's 颁发单位property 
     */
    public String getAwardUnit()
    {
        return getString("awardUnit");
    }
    public void setAwardUnit(String item)
    {
        setString("awardUnit", item);
    }
    /**
     * Object:资质文件's 备注property 
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
     * Object:资质文件's 是否有附件property 
     */
    public boolean isIsHaveAttach()
    {
        return getBoolean("isHaveAttach");
    }
    public void setIsHaveAttach(boolean item)
    {
        setBoolean("isHaveAttach", item);
    }
    /**
     * Object: 资质文件 's 所属单据头 property 
     */
    public com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo getParent()
    {
        return (com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("11DD1A40");
    }
}