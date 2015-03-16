package com.kingdee.eas.fdc.finance;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFIProductSettleBillInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFIProductSettleBillInfo()
    {
        this("id");
    }
    protected AbstractFIProductSettleBillInfo(String pkField)
    {
        super(pkField);
        put("entrys", new com.kingdee.eas.fdc.finance.FIProSttBillEntryCollection());
    }
    /**
     * Object: 竣工结算账务处理 's 工程项目产品分录 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo getCurProjProductEntries()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo)get("curProjProductEntries");
    }
    public void setCurProjProductEntries(com.kingdee.eas.fdc.basedata.CurProjProductEntriesInfo item)
    {
        put("curProjProductEntries", item);
    }
    /**
     * Object:竣工结算账务处理's 竣工面积property 
     */
    public java.math.BigDecimal getCompArea()
    {
        return getBigDecimal("compArea");
    }
    public void setCompArea(java.math.BigDecimal item)
    {
        setBigDecimal("compArea", item);
    }
    /**
     * Object:竣工结算账务处理's 竣工日期property 
     */
    public java.util.Date getCompDate()
    {
        return getDate("compDate");
    }
    public void setCompDate(java.util.Date item)
    {
        setDate("compDate", item);
    }
    /**
     * Object:竣工结算账务处理's 总成本property 
     */
    public java.math.BigDecimal getTotalCost()
    {
        return getBigDecimal("totalCost");
    }
    public void setTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("totalCost", item);
    }
    /**
     * Object:竣工结算账务处理's 是否竣工结算property 
     */
    public boolean isIsCompSettle()
    {
        return getBoolean("isCompSettle");
    }
    public void setIsCompSettle(boolean item)
    {
        setBoolean("isCompSettle", item);
    }
    /**
     * Object:竣工结算账务处理's 是否已生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object: 竣工结算账务处理 's 一体化科目 property 
     */
    public com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo getBeforeAccount()
    {
        return (com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo)get("beforeAccount");
    }
    public void setBeforeAccount(com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo item)
    {
        put("beforeAccount", item);
    }
    /**
     * Object: 竣工结算账务处理 's 凭证 property 
     */
    public com.kingdee.eas.fi.gl.VoucherInfo getVoucher()
    {
        return (com.kingdee.eas.fi.gl.VoucherInfo)get("voucher");
    }
    public void setVoucher(com.kingdee.eas.fi.gl.VoucherInfo item)
    {
        put("voucher", item);
    }
    /**
     * Object:竣工结算账务处理's 是否已经红冲property 
     */
    public boolean isIsRedVouchered()
    {
        return getBoolean("isRedVouchered");
    }
    public void setIsRedVouchered(boolean item)
    {
        setBoolean("isRedVouchered", item);
    }
    /**
     * Object:竣工结算账务处理's 本次已实现金额property 
     */
    public java.math.BigDecimal getHappenCost()
    {
        return getBigDecimal("happenCost");
    }
    public void setHappenCost(java.math.BigDecimal item)
    {
        setBigDecimal("happenCost", item);
    }
    /**
     * Object:竣工结算账务处理's 上次竣工面积property 
     */
    public java.math.BigDecimal getLastCompArea()
    {
        return getBigDecimal("lastCompArea");
    }
    public void setLastCompArea(java.math.BigDecimal item)
    {
        setBigDecimal("lastCompArea", item);
    }
    /**
     * Object:竣工结算账务处理's 上次成本金额property 
     */
    public java.math.BigDecimal getLastTotalCost()
    {
        return getBigDecimal("lastTotalCost");
    }
    public void setLastTotalCost(java.math.BigDecimal item)
    {
        setBigDecimal("lastTotalCost", item);
    }
    /**
     * Object:竣工结算账务处理's 上次已实现金额property 
     */
    public java.math.BigDecimal getLastHappenCost()
    {
        return getBigDecimal("lastHappenCost");
    }
    public void setLastHappenCost(java.math.BigDecimal item)
    {
        setBigDecimal("lastHappenCost", item);
    }
    /**
     * Object:竣工结算账务处理's 可售面积property 
     */
    public java.math.BigDecimal getSaleArea()
    {
        return getBigDecimal("saleArea");
    }
    public void setSaleArea(java.math.BigDecimal item)
    {
        setBigDecimal("saleArea", item);
    }
    /**
     * Object: 竣工结算账务处理 's 成本产品结算单 property 
     */
    public com.kingdee.eas.fdc.finance.FIProSttBillEntryCollection getEntrys()
    {
        return (com.kingdee.eas.fdc.finance.FIProSttBillEntryCollection)get("entrys");
    }
    /**
     * Object:竣工结算账务处理's 不使用竣工面积/可售面积做预提比率property 
     */
    public boolean isIsSelfDefine()
    {
        return getBoolean("isSelfDefine");
    }
    public void setIsSelfDefine(boolean item)
    {
        setBoolean("isSelfDefine", item);
    }
    /**
     * Object:竣工结算账务处理's 成本预提比率property 
     */
    public java.math.BigDecimal getDrawingCostRate()
    {
        return getBigDecimal("drawingCostRate");
    }
    public void setDrawingCostRate(java.math.BigDecimal item)
    {
        setBigDecimal("drawingCostRate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("2CF92E6F");
    }
}