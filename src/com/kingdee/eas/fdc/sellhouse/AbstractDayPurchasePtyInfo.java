package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractDayPurchasePtyInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractDayPurchasePtyInfo()
    {
        this("id");
    }
    protected AbstractDayPurchasePtyInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: �½��Ϲ���Ʒ���� 's ���� property 
     */
    public com.kingdee.eas.fdc.sellhouse.DayMainTableInfo getDayMain()
    {
        return (com.kingdee.eas.fdc.sellhouse.DayMainTableInfo)get("dayMain");
    }
    public void setDayMain(com.kingdee.eas.fdc.sellhouse.DayMainTableInfo item)
    {
        put("dayMain", item);
    }
    /**
     * Object: �½��Ϲ���Ʒ���� 's ��Ʒ���� property 
     */
    public com.kingdee.eas.fdc.basedata.ProductTypeInfo getProductType()
    {
        return (com.kingdee.eas.fdc.basedata.ProductTypeInfo)get("productType");
    }
    public void setProductType(com.kingdee.eas.fdc.basedata.ProductTypeInfo item)
    {
        put("productType", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ���ۺ�ͬ��property 
     */
    public int getCount()
    {
        return getInt("count");
    }
    public void setCount(int item)
    {
        setInt("count", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ���ۺ�ͬ���property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s �������property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ����������property 
     */
    public java.math.BigDecimal getCosate()
    {
        return getBigDecimal("cosate");
    }
    public void setCosate(java.math.BigDecimal item)
    {
        setBigDecimal("cosate", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s Ԥ�տ�ؿ�property 
     */
    public java.math.BigDecimal getPreMoney()
    {
        return getBigDecimal("preMoney");
    }
    public void setPreMoney(java.math.BigDecimal item)
    {
        setBigDecimal("preMoney", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s Ԥ����ؿ�property 
     */
    public java.math.BigDecimal getPreconcertMoney()
    {
        return getBigDecimal("preconcertMoney");
    }
    public void setPreconcertMoney(java.math.BigDecimal item)
    {
        setBigDecimal("preconcertMoney", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ����ؿ�property 
     */
    public java.math.BigDecimal getEarnestMoney()
    {
        return getBigDecimal("earnestMoney");
    }
    public void setEarnestMoney(java.math.BigDecimal item)
    {
        setBigDecimal("earnestMoney", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ���ڻؿ�property 
     */
    public java.math.BigDecimal getFisrtAmount()
    {
        return getBigDecimal("fisrtAmount");
    }
    public void setFisrtAmount(java.math.BigDecimal item)
    {
        setBigDecimal("fisrtAmount", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ¥��ؿ�property 
     */
    public java.math.BigDecimal getHouseAmount()
    {
        return getBigDecimal("houseAmount");
    }
    public void setHouseAmount(java.math.BigDecimal item)
    {
        setBigDecimal("houseAmount", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ���ҿ�ؿ�property 
     */
    public java.math.BigDecimal getLoanAmount()
    {
        return getBigDecimal("loanAmount");
    }
    public void setLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("loanAmount", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ������ؿ�property 
     */
    public java.math.BigDecimal getAccFundAmount()
    {
        return getBigDecimal("accFundAmount");
    }
    public void setAccFundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("accFundAmount", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s �����ؿ�property 
     */
    public java.math.BigDecimal getCompensateAmount()
    {
        return getBigDecimal("compensateAmount");
    }
    public void setCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("compensateAmount", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s �����ѻؿ�property 
     */
    public java.math.BigDecimal getCommissionCharge()
    {
        return getBigDecimal("commissionCharge");
    }
    public void setCommissionCharge(java.math.BigDecimal item)
    {
        setBigDecimal("commissionCharge", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s ���ɽ�ؿ�property 
     */
    public java.math.BigDecimal getLateFee()
    {
        return getBigDecimal("lateFee");
    }
    public void setLateFee(java.math.BigDecimal item)
    {
        setBigDecimal("lateFee", item);
    }
    /**
     * Object:�½��Ϲ���Ʒ����'s �˿�ؿ�property 
     */
    public java.math.BigDecimal getRefundment()
    {
        return getBigDecimal("refundment");
    }
    public void setRefundment(java.math.BigDecimal item)
    {
        setBigDecimal("refundment", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("17CE6093");
    }
}