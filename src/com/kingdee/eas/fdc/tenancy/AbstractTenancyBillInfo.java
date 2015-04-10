package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractTenancyBillInfo()
    {
        this("id");
    }
    protected AbstractTenancyBillInfo(String pkField)
    {
        super(pkField);
        put("tenAttachResourceList", new com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection());
        put("tenLiquidated", new com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection());
        put("otherPayList", new com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection());
        put("tenLongContract", new com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection());
        put("tenancyRoomList", new com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection());
        put("increasedRents", new com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection());
        put("rentFrees", new com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection());
        put("tenCustomerList", new com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection());
    }
    /**
     * Object:租赁合同's 合同名称property 
     */
    public String getTenancyName()
    {
        return getString("tenancyName");
    }
    public void setTenancyName(String item)
    {
        setString("tenancyName", item);
    }
    /**
     * Object:租赁合同's 合同类型property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum getTenancyType()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum.getEnum(getString("tenancyType"));
    }
    public void setTenancyType(com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum item)
    {
		if (item != null) {
        setString("tenancyType", item.getValue());
		}
    }
    /**
     * Object: 租赁合同 's 租赁顾问 property 
     */
    public com.kingdee.eas.base.permission.UserInfo getTenancyAdviser()
    {
        return (com.kingdee.eas.base.permission.UserInfo)get("tenancyAdviser");
    }
    public void setTenancyAdviser(com.kingdee.eas.base.permission.UserInfo item)
    {
        put("tenancyAdviser", item);
    }
    /**
     * Object:租赁合同's 起始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:租赁合同's 结束日期property 
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
     * Object:租赁合同's 租期property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object: 租赁合同 's 租赁房间分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection getTenancyRoomList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection)get("tenancyRoomList");
    }
    /**
     * Object: 租赁合同 's 租赁客户分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection getTenCustomerList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection)get("tenCustomerList");
    }
    /**
     * Object: 租赁合同 's 租赁附属资源分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection getTenAttachResourceList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection)get("tenAttachResourceList");
    }
    /**
     * Object:租赁合同's 免租天数property 
     */
    public int getFreeDays()
    {
        return getInt("freeDays");
    }
    public void setFreeDays(int item)
    {
        setInt("freeDays", item);
    }
    /**
     * Object:租赁合同's 约定交房日期property 
     */
    public java.util.Date getDeliveryRoomDate()
    {
        return getDate("deliveryRoomDate");
    }
    public void setDeliveryRoomDate(java.util.Date item)
    {
        setDate("deliveryRoomDate", item);
    }
    /**
     * Object:租赁合同's 特殊条款property 
     */
    public String getSpecialClause()
    {
        return getString("specialClause");
    }
    public void setSpecialClause(String item)
    {
        setString("specialClause", item);
    }
    /**
     * Object:租赁合同's 变动说明property 
     */
    public String getChangeDes()
    {
        return getString("changeDes");
    }
    public void setChangeDes(String item)
    {
        setString("changeDes", item);
    }
    /**
     * Object:租赁合同's 期间长度类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentTypeEnum getLeaseTimeType()
    {
        return com.kingdee.eas.fdc.tenancy.RentTypeEnum.getEnum(getString("leaseTimeType"));
    }
    public void setLeaseTimeType(com.kingdee.eas.fdc.tenancy.RentTypeEnum item)
    {
		if (item != null) {
        setString("leaseTimeType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 计租期间长度property 
     */
    public int getLeaseTime()
    {
        return getInt("leaseTime");
    }
    public void setLeaseTime(int item)
    {
        setInt("leaseTime", item);
    }
    /**
     * Object:租赁合同's 标准租金property 
     */
    public java.math.BigDecimal getStandardTotalRent()
    {
        return getBigDecimal("standardTotalRent");
    }
    public void setStandardTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("standardTotalRent", item);
    }
    /**
     * Object:租赁合同's 成交租金property 
     */
    public java.math.BigDecimal getDealTotalRent()
    {
        return getBigDecimal("dealTotalRent");
    }
    public void setDealTotalRent(java.math.BigDecimal item)
    {
        setBigDecimal("dealTotalRent", item);
    }
    /**
     * Object:租赁合同's 合同状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 押金金额property 
     */
    public java.math.BigDecimal getDepositAmount()
    {
        return getBigDecimal("depositAmount");
    }
    public void setDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("depositAmount", item);
    }
    /**
     * Object:租赁合同's 剩余押金property 
     */
    public java.math.BigDecimal getRemainDepositAmount()
    {
        return getBigDecimal("remainDepositAmount");
    }
    public void setRemainDepositAmount(java.math.BigDecimal item)
    {
        setBigDecimal("remainDepositAmount", item);
    }
    /**
     * Object: 租赁合同 's 原租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getOldTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("oldTenancyBill");
    }
    public void setOldTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("oldTenancyBill", item);
    }
    /**
     * Object:租赁合同's 滞纳金property 
     */
    public java.math.BigDecimal getLateFeeAmount()
    {
        return getBigDecimal("lateFeeAmount");
    }
    public void setLateFeeAmount(java.math.BigDecimal item)
    {
        setBigDecimal("lateFeeAmount", item);
    }
    /**
     * Object:租赁合同's 收租日类型property 
     */
    public com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum getChargeDateType()
    {
        return com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum.getEnum(getString("chargeDateType"));
    }
    public void setChargeDateType(com.kingdee.eas.fdc.tenancy.ChargeDateTypeEnum item)
    {
		if (item != null) {
        setString("chargeDateType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 收租日偏移天数property 
     */
    public int getChargeOffsetDays()
    {
        return getInt("chargeOffsetDays");
    }
    public void setChargeOffsetDays(int item)
    {
        setInt("chargeOffsetDays", item);
    }
    /**
     * Object:租赁合同's 免租日是否计入租期property 
     */
    public boolean isIsFreeDaysInLease()
    {
        return getBoolean("isFreeDaysInLease");
    }
    public void setIsFreeDaysInLease(boolean item)
    {
        setBoolean("isFreeDaysInLease", item);
    }
    /**
     * Object:租赁合同's 租赁日期property 
     */
    public java.util.Date getTenancyDate()
    {
        return getDate("tenancyDate");
    }
    public void setTenancyDate(java.util.Date item)
    {
        setDate("tenancyDate", item);
    }
    /**
     * Object: 租赁合同 's 收款银行 property 
     */
    public com.kingdee.eas.basedata.assistant.BankInfo getPayeeBank()
    {
        return (com.kingdee.eas.basedata.assistant.BankInfo)get("payeeBank");
    }
    public void setPayeeBank(com.kingdee.eas.basedata.assistant.BankInfo item)
    {
        put("payeeBank", item);
    }
    /**
     * Object: 租赁合同 's 中介机构 property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyInfo getAgency()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyInfo)get("agency");
    }
    public void setAgency(com.kingdee.eas.fdc.tenancy.AgencyInfo item)
    {
        put("agency", item);
    }
    /**
     * Object:租赁合同's 到期标记property 
     */
    public com.kingdee.eas.fdc.tenancy.FlagAtTermEnum getFlagAtTerm()
    {
        return com.kingdee.eas.fdc.tenancy.FlagAtTermEnum.getEnum(getString("flagAtTerm"));
    }
    public void setFlagAtTerm(com.kingdee.eas.fdc.tenancy.FlagAtTermEnum item)
    {
		if (item != null) {
        setString("flagAtTerm", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 首付租金property 
     */
    public java.math.BigDecimal getFirstPayRent()
    {
        return getBigDecimal("firstPayRent");
    }
    public void setFirstPayRent(java.math.BigDecimal item)
    {
        setBigDecimal("firstPayRent", item);
    }
    /**
     * Object:租赁合同's 租赁房间property 
     */
    public String getTenRoomsDes()
    {
        return getString("tenRoomsDes");
    }
    public void setTenRoomsDes(String item)
    {
        setString("tenRoomsDes", item);
    }
    /**
     * Object:租赁合同's 租赁配套资源property 
     */
    public String getTenAttachesDes()
    {
        return getString("tenAttachesDes");
    }
    public void setTenAttachesDes(String item)
    {
        setString("tenAttachesDes", item);
    }
    /**
     * Object:租赁合同's 租赁客户property 
     */
    public String getTenCustomerDes()
    {
        return getString("tenCustomerDes");
    }
    public void setTenCustomerDes(String item)
    {
        setString("tenCustomerDes", item);
    }
    /**
     * Object:租赁合同's 原合同扣除金额property 
     */
    public java.math.BigDecimal getDeductAmount()
    {
        return getBigDecimal("deductAmount");
    }
    public void setDeductAmount(java.math.BigDecimal item)
    {
        setBigDecimal("deductAmount", item);
    }
    /**
     * Object: 租赁合同 's 租售项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:租赁合同's 是否中介代理property 
     */
    public boolean isIsByAgency()
    {
        return getBoolean("isByAgency");
    }
    public void setIsByAgency(boolean item)
    {
        setBoolean("isByAgency", item);
    }
    /**
     * Object: 租赁合同 's 中介代理合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.AgencyContractInfo getAgencyContract()
    {
        return (com.kingdee.eas.fdc.tenancy.AgencyContractInfo)get("agencyContract");
    }
    public void setAgencyContract(com.kingdee.eas.fdc.tenancy.AgencyContractInfo item)
    {
        put("agencyContract", item);
    }
    /**
     * Object:租赁合同's 约定代理费property 
     */
    public java.math.BigDecimal getPromissoryAgentFee()
    {
        return getBigDecimal("promissoryAgentFee");
    }
    public void setPromissoryAgentFee(java.math.BigDecimal item)
    {
        setBigDecimal("promissoryAgentFee", item);
    }
    /**
     * Object:租赁合同's 约定应付日期property 
     */
    public java.util.Date getPromissoryAppPayDate()
    {
        return getDate("promissoryAppPayDate");
    }
    public void setPromissoryAppPayDate(java.util.Date item)
    {
        setDate("promissoryAppPayDate", item);
    }
    /**
     * Object:租赁合同's 代理费property 
     */
    public java.math.BigDecimal getAgentFee()
    {
        return getBigDecimal("agentFee");
    }
    public void setAgentFee(java.math.BigDecimal item)
    {
        setBigDecimal("agentFee", item);
    }
    /**
     * Object: 租赁合同 's 代理费付款方式 property 
     */
    public com.kingdee.eas.basedata.assistant.SettlementTypeInfo getSettlementType()
    {
        return (com.kingdee.eas.basedata.assistant.SettlementTypeInfo)get("settlementType");
    }
    public void setSettlementType(com.kingdee.eas.basedata.assistant.SettlementTypeInfo item)
    {
        put("settlementType", item);
    }
    /**
     * Object:租赁合同's 代理费应付日期property 
     */
    public java.util.Date getAppPayDate()
    {
        return getDate("appPayDate");
    }
    public void setAppPayDate(java.util.Date item)
    {
        setDate("appPayDate", item);
    }
    /**
     * Object:租赁合同's 代理说明property 
     */
    public String getAgentDes()
    {
        return getString("agentDes");
    }
    public void setAgentDes(String item)
    {
        setString("agentDes", item);
    }
    /**
     * Object: 租赁合同 's 租金递增分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection getIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection)get("increasedRents");
    }
    /**
     * Object: 租赁合同 's 免租分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection getRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection)get("rentFrees");
    }
    /**
     * Object:租赁合同's 首期类型property 
     */
    public com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum getFirstLeaseType()
    {
        return com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum.getEnum(getString("firstLeaseType"));
    }
    public void setFirstLeaseType(com.kingdee.eas.fdc.tenancy.FirstLeaseTypeEnum item)
    {
		if (item != null) {
        setString("firstLeaseType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 首期结束日期property 
     */
    public java.util.Date getFirstLeaseEndDate()
    {
        return getDate("firstLeaseEndDate");
    }
    public void setFirstLeaseEndDate(java.util.Date item)
    {
        setDate("firstLeaseEndDate", item);
    }
    /**
     * Object: 租赁合同 's 诚意预留 property 
     */
    public com.kingdee.eas.fdc.tenancy.SincerObligateInfo getSincerObligate()
    {
        return (com.kingdee.eas.fdc.tenancy.SincerObligateInfo)get("sincerObligate");
    }
    public void setSincerObligate(com.kingdee.eas.fdc.tenancy.SincerObligateInfo item)
    {
        put("sincerObligate", item);
    }
    /**
     * Object:租赁合同's 租赁计算方式property 
     */
    public com.kingdee.eas.fdc.tenancy.RentCountTypeEnum getRentCountType()
    {
        return com.kingdee.eas.fdc.tenancy.RentCountTypeEnum.getEnum(getString("rentCountType"));
    }
    public void setRentCountType(com.kingdee.eas.fdc.tenancy.RentCountTypeEnum item)
    {
		if (item != null) {
        setString("rentCountType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 是否自动取整property 
     */
    public boolean isIsAutoToInteger()
    {
        return getBoolean("isAutoToInteger");
    }
    public void setIsAutoToInteger(boolean item)
    {
        setBoolean("isAutoToInteger", item);
    }
    /**
     * Object:租赁合同's 取整类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegerType()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegerType"));
    }
    public void setToIntegerType(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegerType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 位数property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigit()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digit"));
    }
    public void setDigit(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digit", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 收租起始类型property 
     */
    public com.kingdee.eas.fdc.tenancy.RentStartTypeEnum getRentStartType()
    {
        return com.kingdee.eas.fdc.tenancy.RentStartTypeEnum.getEnum(getString("rentStartType"));
    }
    public void setRentStartType(com.kingdee.eas.fdc.tenancy.RentStartTypeEnum item)
    {
		if (item != null) {
        setString("rentStartType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 起始日期期限property 
     */
    public java.util.Date getStartDateLimit()
    {
        return getDate("startDateLimit");
    }
    public void setStartDateLimit(java.util.Date item)
    {
        setDate("startDateLimit", item);
    }
    /**
     * Object:租赁合同's 年天数property 
     */
    public int getDaysPerYear()
    {
        return getInt("daysPerYear");
    }
    public void setDaysPerYear(int item)
    {
        setInt("daysPerYear", item);
    }
    /**
     * Object:租赁合同's 多房产计算方式property 
     */
    public com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum getMoreRoomsType()
    {
        return com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum.getEnum(getString("moreRoomsType"));
    }
    public void setMoreRoomsType(com.kingdee.eas.fdc.tenancy.MoreRoomsTypeEnum item)
    {
		if (item != null) {
        setString("moreRoomsType", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 是否自由式合同property 
     */
    public boolean isIsFreeContract()
    {
        return getBoolean("isFreeContract");
    }
    public void setIsFreeContract(boolean item)
    {
        setBoolean("isFreeContract", item);
    }
    /**
     * Object: 租赁合同 's 其他应付明细分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection getOtherPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection)get("otherPayList");
    }
    /**
     * Object:租赁合同's 周期性费用是否自动取整property 
     */
    public boolean isIsAutoToIntegerFee()
    {
        return getBoolean("isAutoToIntegerFee");
    }
    public void setIsAutoToIntegerFee(boolean item)
    {
        setBoolean("isAutoToIntegerFee", item);
    }
    /**
     * Object:租赁合同's 周期性费用取整位数property 
     */
    public com.kingdee.eas.fdc.sellhouse.DigitEnum getDigitFee()
    {
        return com.kingdee.eas.fdc.sellhouse.DigitEnum.getEnum(getString("digitFee"));
    }
    public void setDigitFee(com.kingdee.eas.fdc.sellhouse.DigitEnum item)
    {
		if (item != null) {
        setString("digitFee", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 周期性费用取整property 
     */
    public com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum getToIntegetTypeFee()
    {
        return com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum.getEnum(getString("toIntegetTypeFee"));
    }
    public void setToIntegetTypeFee(com.kingdee.eas.fdc.sellhouse.ToIntegerTypeEnum item)
    {
		if (item != null) {
        setString("toIntegetTypeFee", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 固定日的第几个月property 
     */
    public java.util.Date getFixedDateFromMonth()
    {
        return getDate("fixedDateFromMonth");
    }
    public void setFixedDateFromMonth(java.util.Date item)
    {
        setDate("fixedDateFromMonth", item);
    }
    /**
     * Object: 租赁合同 's 租凭合同违约金计算方案设置 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection getTenLiquidated()
    {
        return (com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection)get("tenLiquidated");
    }
    /**
     * Object:租赁合同's 是否计算违约金property 
     */
    public boolean isIsAccountLiquidated()
    {
        return getBoolean("isAccountLiquidated");
    }
    public void setIsAccountLiquidated(boolean item)
    {
        setBoolean("isAccountLiquidated", item);
    }
    /**
     * Object:租赁合同's 违约金比例property 
     */
    public java.math.BigDecimal getRate()
    {
        return getBigDecimal("rate");
    }
    public void setRate(java.math.BigDecimal item)
    {
        setBigDecimal("rate", item);
    }
    /**
     * Object:租赁合同's 违约金比例日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getRateDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("rateDate"));
    }
    public void setRateDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("rateDate", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 违约金减免property 
     */
    public int getRelief()
    {
        return getInt("relief");
    }
    public void setRelief(int item)
    {
        setInt("relief", item);
    }
    /**
     * Object:租赁合同's 违约金减免日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getReliefDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("reliefDate"));
    }
    public void setReliefDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("reliefDate", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 违约金计算标准property 
     */
    public int getStandard()
    {
        return getInt("standard");
    }
    public void setStandard(int item)
    {
        setInt("standard", item);
    }
    /**
     * Object:租赁合同's 违约金计算标准日期property 
     */
    public com.kingdee.eas.fdc.tenancy.DateEnum getStandardDate()
    {
        return com.kingdee.eas.fdc.tenancy.DateEnum.getEnum(getString("standardDate"));
    }
    public void setStandardDate(com.kingdee.eas.fdc.tenancy.DateEnum item)
    {
		if (item != null) {
        setString("standardDate", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 违约金保留位数property 
     */
    public com.kingdee.eas.fdc.tenancy.MoneyEnum getBit()
    {
        return com.kingdee.eas.fdc.tenancy.MoneyEnum.getEnum(getString("bit"));
    }
    public void setBit(com.kingdee.eas.fdc.tenancy.MoneyEnum item)
    {
		if (item != null) {
        setString("bit", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 是否根据款项设置违约金property 
     */
    public boolean isIsMDLiquidated()
    {
        return getBoolean("isMDLiquidated");
    }
    public void setIsMDLiquidated(boolean item)
    {
        setBoolean("isMDLiquidated", item);
    }
    /**
     * Object:租赁合同's 发生状态property 
     */
    public com.kingdee.eas.fdc.tenancy.OccurreStateEnum getOccurred()
    {
        return com.kingdee.eas.fdc.tenancy.OccurreStateEnum.getEnum(getString("occurred"));
    }
    public void setOccurred(com.kingdee.eas.fdc.tenancy.OccurreStateEnum item)
    {
		if (item != null) {
        setString("occurred", item.getValue());
		}
    }
    /**
     * Object:租赁合同's 首期应收日期property 
     */
    public java.util.Date getFristRevDate()
    {
        return getDate("fristRevDate");
    }
    public void setFristRevDate(java.util.Date item)
    {
        setDate("fristRevDate", item);
    }
    /**
     * Object:租赁合同's 第二期应收日期property 
     */
    public java.util.Date getSecondRevDate()
    {
        return getDate("secondRevDate");
    }
    public void setSecondRevDate(java.util.Date item)
    {
        setDate("secondRevDate", item);
    }
    /**
     * Object:租赁合同's 合同状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyStateDisplayEnum getTenancyStateDisplay()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyStateDisplayEnum.getEnum(getString("tenancyStateDisplay"));
    }
    public void setTenancyStateDisplay(com.kingdee.eas.fdc.tenancy.TenancyStateDisplayEnum item)
    {
		if (item != null) {
        setString("tenancyStateDisplay", item.getValue());
		}
    }
    /**
     * Object: 租赁合同 's 长租合同设置 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection getTenLongContract()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection)get("tenLongContract");
    }
    /**
     * Object:租赁合同's 房间记租周期property 
     */
    public String getTenRoomsRentType()
    {
        return getString("tenRoomsRentType");
    }
    public void setTenRoomsRentType(String item)
    {
        setString("tenRoomsRentType", item);
    }
    /**
     * Object: 租赁合同 's 变更原因 property 
     */
    public com.kingdee.eas.fdc.tenancy.ChangeReasonInfo getChangeReason()
    {
        return (com.kingdee.eas.fdc.tenancy.ChangeReasonInfo)get("changeReason");
    }
    public void setChangeReason(com.kingdee.eas.fdc.tenancy.ChangeReasonInfo item)
    {
        put("changeReason", item);
    }
    /**
     * Object: 租赁合同 's 经营业态 property 
     */
    public com.kingdee.eas.fdc.tenancy.OperateStateInfo getOperateState()
    {
        return (com.kingdee.eas.fdc.tenancy.OperateStateInfo)get("operateState");
    }
    public void setOperateState(com.kingdee.eas.fdc.tenancy.OperateStateInfo item)
    {
        put("operateState", item);
    }
    /**
     * Object: 租赁合同 's 代理商 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyAgencyInfo getTenancyAgency()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyAgencyInfo)get("tenancyAgency");
    }
    public void setTenancyAgency(com.kingdee.eas.fdc.tenancy.TenancyAgencyInfo item)
    {
        put("tenancyAgency", item);
    }
    /**
     * Object:租赁合同's 固定金额property 
     */
    public java.math.BigDecimal getFinalAmount()
    {
        return getBigDecimal("finalAmount");
    }
    public void setFinalAmount(java.math.BigDecimal item)
    {
        setBigDecimal("finalAmount", item);
    }
    /**
     * Object:租赁合同's 退租日期property 
     */
    public java.util.Date getQuitRoomDate()
    {
        return getDate("quitRoomDate");
    }
    public void setQuitRoomDate(java.util.Date item)
    {
        setDate("quitRoomDate", item);
    }
    /**
     * Object: 租赁合同 's 免租期审批单 property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeBillInfo getRentFreeBill()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeBillInfo)get("rentFreeBill");
    }
    public void setRentFreeBill(com.kingdee.eas.fdc.tenancy.RentFreeBillInfo item)
    {
        put("rentFreeBill", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("7BA91DDE");
    }
}