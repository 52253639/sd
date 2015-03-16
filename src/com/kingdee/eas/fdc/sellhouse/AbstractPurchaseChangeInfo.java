package com.kingdee.eas.fdc.sellhouse;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseChangeInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPurchaseChangeInfo()
    {
        this("id");
    }
    protected AbstractPurchaseChangeInfo(String pkField)
    {
        super(pkField);
        put("oldRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryCollection());
        put("newDiscountEntrys", new com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection());
        put("newRoomAttachmentEntry", new com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryCollection());
        put("oldPayListEntrys", new com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryCollection());
        put("newPayListEntrys", new com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryCollection());
        put("oldDiscountEntrys", new com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryCollection());
    }
    /**
     * Object: (已禁用)认购变更 's 认购 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseInfo getPurchase()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseInfo)get("purchase");
    }
    public void setPurchase(com.kingdee.eas.fdc.sellhouse.PurchaseInfo item)
    {
        put("purchase", item);
    }
    /**
     * Object: (已禁用)认购变更 's 老付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getOldPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("oldPayType");
    }
    public void setOldPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("oldPayType", item);
    }
    /**
     * Object: (已禁用)认购变更 's 新的付款方案 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo getNewPayType()
    {
        return (com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo)get("newPayType");
    }
    public void setNewPayType(com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo item)
    {
        put("newPayType", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的成交金额property 
     */
    public java.math.BigDecimal getOldDealAmount()
    {
        return getBigDecimal("oldDealAmount");
    }
    public void setOldDealAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldDealAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的成交金额property 
     */
    public java.math.BigDecimal getNewDealAmount()
    {
        return getBigDecimal("newDealAmount");
    }
    public void setNewDealAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newDealAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的按揭贷款金额property 
     */
    public java.math.BigDecimal getOldLoanAmount()
    {
        return getBigDecimal("oldLoanAmount");
    }
    public void setOldLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldLoanAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的按揭贷款金额property 
     */
    public java.math.BigDecimal getNewLoanAmount()
    {
        return getBigDecimal("newLoanAmount");
    }
    public void setNewLoanAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newLoanAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的公积金金额property 
     */
    public java.math.BigDecimal getOldAccuFundAmount()
    {
        return getBigDecimal("oldAccuFundAmount");
    }
    public void setOldAccuFundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldAccuFundAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的公积金金额property 
     */
    public java.math.BigDecimal getNewAccuFundAmount()
    {
        return getBigDecimal("newAccuFundAmount");
    }
    public void setNewAccuFundAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newAccuFundAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的附属金额property 
     */
    public java.math.BigDecimal getOldAttachmentAmount()
    {
        return getBigDecimal("oldAttachmentAmount");
    }
    public void setOldAttachmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldAttachmentAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的附属金额property 
     */
    public java.math.BigDecimal getNewAttachmentAmount()
    {
        return getBigDecimal("newAttachmentAmount");
    }
    public void setNewAttachmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newAttachmentAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的装修金额property 
     */
    public java.math.BigDecimal getOldFitmentAmount()
    {
        return getBigDecimal("oldFitmentAmount");
    }
    public void setOldFitmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldFitmentAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的装修金额property 
     */
    public java.math.BigDecimal getNewFitmentAmount()
    {
        return getBigDecimal("newFitmentAmount");
    }
    public void setNewFitmentAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newFitmentAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的装修是否并入合同金额property 
     */
    public boolean isOldIsFitmentToContract()
    {
        return getBoolean("oldIsFitmentToContract");
    }
    public void setOldIsFitmentToContract(boolean item)
    {
        setBoolean("oldIsFitmentToContract", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的装修是否并入合同金额property 
     */
    public boolean isNewIsFitmentToContract()
    {
        return getBoolean("newIsFitmentToContract");
    }
    public void setNewIsFitmentToContract(boolean item)
    {
        setBoolean("newIsFitmentToContract", item);
    }
    /**
     * Object:(已禁用)认购变更's 变更原因详细描述property 
     */
    public String getChangeReason()
    {
        return getString("changeReason");
    }
    public void setChangeReason(String item)
    {
        setString("changeReason", item);
    }
    /**
     * Object:(已禁用)认购变更's 申请特殊折扣property 
     */
    public java.math.BigDecimal getReqDiscount()
    {
        return getBigDecimal("reqDiscount");
    }
    public void setReqDiscount(java.math.BigDecimal item)
    {
        setBigDecimal("reqDiscount", item);
    }
    /**
     * Object:(已禁用)认购变更's 核准特殊折扣property 
     */
    public java.math.BigDecimal getNewDiscount()
    {
        return getBigDecimal("newDiscount");
    }
    public void setNewDiscount(java.math.BigDecimal item)
    {
        setBigDecimal("newDiscount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的特殊折扣property 
     */
    public java.math.BigDecimal getOldDiscount()
    {
        return getBigDecimal("oldDiscount");
    }
    public void setOldDiscount(java.math.BigDecimal item)
    {
        setBigDecimal("oldDiscount", item);
    }
    /**
     * Object: (已禁用)认购变更 's 新的折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection getNewDiscountEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioEntryCollection)get("newDiscountEntrys");
    }
    /**
     * Object: (已禁用)认购变更 's 旧的折扣分录 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryCollection getOldDiscountEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeAgioOldEntryCollection)get("oldDiscountEntrys");
    }
    /**
     * Object: (已禁用)认购变更 's 附属房产 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryCollection getNewRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentEntryCollection)get("newRoomAttachmentEntry");
    }
    /**
     * Object: (已禁用)认购变更 's 附属房产 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryCollection getOldRoomAttachmentEntry()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeRoomAttachmentOldEntryCollection)get("oldRoomAttachmentEntry");
    }
    /**
     * Object:(已禁用)认购变更's 新的现售补差款property 
     */
    public java.math.BigDecimal getNewCompensateAmount()
    {
        return getBigDecimal("newCompensateAmount");
    }
    public void setNewCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newCompensateAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 老的现售补差款property 
     */
    public java.math.BigDecimal getOldCompensateAmount()
    {
        return getBigDecimal("oldCompensateAmount");
    }
    public void setOldCompensateAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldCompensateAmount", item);
    }
    /**
     * Object: (已禁用)认购变更 's 新的付款明细 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryCollection getNewPayListEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListEntryCollection)get("newPayListEntrys");
    }
    /**
     * Object: (已禁用)认购变更 's 旧的付款明细 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryCollection getOldPayListEntrys()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangePayListOldEntryCollection)get("oldPayListEntrys");
    }
    /**
     * Object:(已禁用)认购变更's 新的销售方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getNewSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("newSellType"));
    }
    public void setNewSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("newSellType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 旧的销售方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellTypeEnum getOldSellType()
    {
        return com.kingdee.eas.fdc.sellhouse.SellTypeEnum.getEnum(getString("oldSellType"));
    }
    public void setOldSellType(com.kingdee.eas.fdc.sellhouse.SellTypeEnum item)
    {
		if (item != null) {
        setString("oldSellType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 新的合同总价property 
     */
    public java.math.BigDecimal getNewContractAmount()
    {
        return getBigDecimal("newContractAmount");
    }
    public void setNewContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("newContractAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的合同总价property 
     */
    public java.math.BigDecimal getOldContractAmount()
    {
        return getBigDecimal("oldContractAmount");
    }
    public void setOldContractAmount(java.math.BigDecimal item)
    {
        setBigDecimal("oldContractAmount", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的是否自动取整property 
     */
    public boolean isOldIsAutoToInteger()
    {
        return getBoolean("oldIsAutoToInteger");
    }
    public void setOldIsAutoToInteger(boolean item)
    {
        setBoolean("oldIsAutoToInteger", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的是否自动取整property 
     */
    public boolean isNewIsAutoToInteger()
    {
        return getBoolean("newIsAutoToInteger");
    }
    public void setNewIsAutoToInteger(boolean item)
    {
        setBoolean("newIsAutoToInteger", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的取整方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getOldToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("oldToIntegerType"));
    }
    public void setOldToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("oldToIntegerType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 新的取整方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getNewToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("newToIntegerType"));
    }
    public void setNewToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("newToIntegerType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 旧的取整位数property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getOldDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("oldDigit"));
    }
    public void setOldDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("oldDigit", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 新的取整位数property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getNewDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("newDigit"));
    }
    public void setNewDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("newDigit", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 变更日期property 
     */
    public java.util.Date getChangeDate()
    {
        return getDate("changeDate");
    }
    public void setChangeDate(java.util.Date item)
    {
        setDate("changeDate", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的价格计算方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum getOldPriceAccountType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum.getEnum(getString("oldPriceAccountType"));
    }
    public void setOldPriceAccountType(com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum item)
    {
		if (item != null) {
        setString("oldPriceAccountType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 新的价格计算方式property 
     */
    public com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum getNewPriceAccountType()
    {
        return com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum.getEnum(getString("newPriceAccountType"));
    }
    public void setNewPriceAccountType(com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum item)
    {
		if (item != null) {
        setString("newPriceAccountType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 旧的合同建筑单价property 
     */
    public java.math.BigDecimal getOldBuildingPrice()
    {
        return getBigDecimal("oldBuildingPrice");
    }
    public void setOldBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldBuildingPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的合同套内单价property 
     */
    public java.math.BigDecimal getOldRoomPrice()
    {
        return getBigDecimal("oldRoomPrice");
    }
    public void setOldRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldRoomPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 新合同建筑单价property 
     */
    public java.math.BigDecimal getNewBuildingPrice()
    {
        return getBigDecimal("newBuildingPrice");
    }
    public void setNewBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newBuildingPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 新合同套内单价property 
     */
    public java.math.BigDecimal getNewRoomPrice()
    {
        return getBigDecimal("newRoomPrice");
    }
    public void setNewRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newRoomPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的成交建筑单价property 
     */
    public java.math.BigDecimal getOldDealBuildingPrice()
    {
        return getBigDecimal("oldDealBuildingPrice");
    }
    public void setOldDealBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldDealBuildingPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的成交套内单价property 
     */
    public java.math.BigDecimal getOldDealRoomPrice()
    {
        return getBigDecimal("oldDealRoomPrice");
    }
    public void setOldDealRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldDealRoomPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的成交建筑单价property 
     */
    public java.math.BigDecimal getNewDealBuildingPrice()
    {
        return getBigDecimal("newDealBuildingPrice");
    }
    public void setNewDealBuildingPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newDealBuildingPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 新成交套内单价property 
     */
    public java.math.BigDecimal getNewDealRoomPrice()
    {
        return getBigDecimal("newDealRoomPrice");
    }
    public void setNewDealRoomPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newDealRoomPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的成交单价property 
     */
    public java.math.BigDecimal getOldDealPrice()
    {
        return getBigDecimal("oldDealPrice");
    }
    public void setOldDealPrice(java.math.BigDecimal item)
    {
        setBigDecimal("oldDealPrice", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的成交单价property 
     */
    public java.math.BigDecimal getNewDealPrice()
    {
        return getBigDecimal("newDealPrice");
    }
    public void setNewDealPrice(java.math.BigDecimal item)
    {
        setBigDecimal("newDealPrice", item);
    }
    /**
     * Object: (已禁用)认购变更 's 变更原因 property 
     */
    public com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonInfo getPurchaseChangeReason()
    {
        return (com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonInfo)get("purchaseChangeReason");
    }
    public void setPurchaseChangeReason(com.kingdee.eas.fdc.sellhouse.PurchaseChangeReasonInfo item)
    {
        put("purchaseChangeReason", item);
    }
    /**
     * Object:(已禁用)认购变更's 旧的特殊折扣类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum getOldSpecialAgioType()
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum.getEnum(getString("oldSpecialAgioType"));
    }
    public void setOldSpecialAgioType(com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum item)
    {
		if (item != null) {
        setString("oldSpecialAgioType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 新的特殊折扣类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum getNewSpecialAgioType()
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum.getEnum(getString("newSpecialAgioType"));
    }
    public void setNewSpecialAgioType(com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum item)
    {
		if (item != null) {
        setString("newSpecialAgioType", item.getValue());
		}
    }
    /**
     * Object:(已禁用)认购变更's 旧的是否取整property 
     */
    public boolean isOldIsBasePriceSell()
    {
        return getBoolean("oldIsBasePriceSell");
    }
    public void setOldIsBasePriceSell(boolean item)
    {
        setBoolean("oldIsBasePriceSell", item);
    }
    /**
     * Object:(已禁用)认购变更's 新的是否低价销售property 
     */
    public boolean isNewIsBasePriceSell()
    {
        return getBoolean("newIsBasePriceSell");
    }
    public void setNewIsBasePriceSell(boolean item)
    {
        setBoolean("newIsBasePriceSell", item);
    }
    /**
     * Object:(已禁用)认购变更's 原认购购单日期property 
     */
    public java.util.Date getOldPurchaseDate()
    {
        return getDate("oldPurchaseDate");
    }
    public void setOldPurchaseDate(java.util.Date item)
    {
        setDate("oldPurchaseDate", item);
    }
    /**
     * Object:(已禁用)认购变更's 新认购单日期property 
     */
    public java.util.Date getNewPurchaseDate()
    {
        return getDate("newPurchaseDate");
    }
    public void setNewPurchaseDate(java.util.Date item)
    {
        setDate("newPurchaseDate", item);
    }
    /**
     * Object:(已禁用)认购变更's 新计划签约日期property 
     */
    public java.util.Date getNewPlanSignDate()
    {
        return getDate("newPlanSignDate");
    }
    public void setNewPlanSignDate(java.util.Date item)
    {
        setDate("newPlanSignDate", item);
    }
    /**
     * Object:(已禁用)认购变更's 原计划签约日期property 
     */
    public java.util.Date getOldPlanSignDate()
    {
        return getDate("oldPlanSignDate");
    }
    public void setOldPlanSignDate(java.util.Date item)
    {
        setDate("oldPlanSignDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("DE662846");
    }
}