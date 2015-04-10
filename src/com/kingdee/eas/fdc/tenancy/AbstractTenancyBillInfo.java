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
     * Object:���޺�ͬ's ��ͬ����property 
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
     * Object:���޺�ͬ's ��ͬ����property 
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
     * Object: ���޺�ͬ 's ���޹��� property 
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
     * Object:���޺�ͬ's ��ʼ����property 
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
     * Object:���޺�ͬ's ��������property 
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
     * Object:���޺�ͬ's ����property 
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
     * Object: ���޺�ͬ 's ���޷����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection getTenancyRoomList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection)get("tenancyRoomList");
    }
    /**
     * Object: ���޺�ͬ 's ���޿ͻ���¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection getTenCustomerList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryCollection)get("tenCustomerList");
    }
    /**
     * Object: ���޺�ͬ 's ���޸�����Դ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection getTenAttachResourceList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenAttachResourceEntryCollection)get("tenAttachResourceList");
    }
    /**
     * Object:���޺�ͬ's ��������property 
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
     * Object:���޺�ͬ's Լ����������property 
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
     * Object:���޺�ͬ's ��������property 
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
     * Object:���޺�ͬ's �䶯˵��property 
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
     * Object:���޺�ͬ's �ڼ䳤������property 
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
     * Object:���޺�ͬ's �����ڼ䳤��property 
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
     * Object:���޺�ͬ's ��׼���property 
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
     * Object:���޺�ͬ's �ɽ����property 
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
     * Object:���޺�ͬ's ��ͬ״̬property 
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
     * Object:���޺�ͬ's Ѻ����property 
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
     * Object:���޺�ͬ's ʣ��Ѻ��property 
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
     * Object: ���޺�ͬ 's ԭ���޺�ͬ property 
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
     * Object:���޺�ͬ's ���ɽ�property 
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
     * Object:���޺�ͬ's ����������property 
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
     * Object:���޺�ͬ's ������ƫ������property 
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
     * Object:���޺�ͬ's �������Ƿ��������property 
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
     * Object:���޺�ͬ's ��������property 
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
     * Object: ���޺�ͬ 's �տ����� property 
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
     * Object: ���޺�ͬ 's �н���� property 
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
     * Object:���޺�ͬ's ���ڱ��property 
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
     * Object:���޺�ͬ's �׸����property 
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
     * Object:���޺�ͬ's ���޷���property 
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
     * Object:���޺�ͬ's ����������Դproperty 
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
     * Object:���޺�ͬ's ���޿ͻ�property 
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
     * Object:���޺�ͬ's ԭ��ͬ�۳����property 
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
     * Object: ���޺�ͬ 's ������Ŀ property 
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
     * Object:���޺�ͬ's �Ƿ��н����property 
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
     * Object: ���޺�ͬ 's �н�����ͬ property 
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
     * Object:���޺�ͬ's Լ�������property 
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
     * Object:���޺�ͬ's Լ��Ӧ������property 
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
     * Object:���޺�ͬ's �����property 
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
     * Object: ���޺�ͬ 's ����Ѹ��ʽ property 
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
     * Object:���޺�ͬ's �����Ӧ������property 
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
     * Object:���޺�ͬ's ����˵��property 
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
     * Object: ���޺�ͬ 's ��������¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection getIncreasedRents()
    {
        return (com.kingdee.eas.fdc.tenancy.IncreasedRentEntryCollection)get("increasedRents");
    }
    /**
     * Object: ���޺�ͬ 's �����¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection getRentFrees()
    {
        return (com.kingdee.eas.fdc.tenancy.RentFreeEntryCollection)get("rentFrees");
    }
    /**
     * Object:���޺�ͬ's ��������property 
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
     * Object:���޺�ͬ's ���ڽ�������property 
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
     * Object: ���޺�ͬ 's ����Ԥ�� property 
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
     * Object:���޺�ͬ's ���޼��㷽ʽproperty 
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
     * Object:���޺�ͬ's �Ƿ��Զ�ȡ��property 
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
     * Object:���޺�ͬ's ȡ������property 
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
     * Object:���޺�ͬ's λ��property 
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
     * Object:���޺�ͬ's ������ʼ����property 
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
     * Object:���޺�ͬ's ��ʼ��������property 
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
     * Object:���޺�ͬ's ������property 
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
     * Object:���޺�ͬ's �෿�����㷽ʽproperty 
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
     * Object:���޺�ͬ's �Ƿ�����ʽ��ͬproperty 
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
     * Object: ���޺�ͬ 's ����Ӧ����ϸ��¼ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection getOtherPayList()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection)get("otherPayList");
    }
    /**
     * Object:���޺�ͬ's �����Է����Ƿ��Զ�ȡ��property 
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
     * Object:���޺�ͬ's �����Է���ȡ��λ��property 
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
     * Object:���޺�ͬ's �����Է���ȡ��property 
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
     * Object:���޺�ͬ's �̶��յĵڼ�����property 
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
     * Object: ���޺�ͬ 's ��ƾ��ͬΥԼ����㷽������ property 
     */
    public com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection getTenLiquidated()
    {
        return (com.kingdee.eas.fdc.tenancy.TenLiquidatedCollection)get("tenLiquidated");
    }
    /**
     * Object:���޺�ͬ's �Ƿ����ΥԼ��property 
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
     * Object:���޺�ͬ's ΥԼ�����property 
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
     * Object:���޺�ͬ's ΥԼ���������property 
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
     * Object:���޺�ͬ's ΥԼ�����property 
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
     * Object:���޺�ͬ's ΥԼ���������property 
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
     * Object:���޺�ͬ's ΥԼ������׼property 
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
     * Object:���޺�ͬ's ΥԼ������׼����property 
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
     * Object:���޺�ͬ's ΥԼ����λ��property 
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
     * Object:���޺�ͬ's �Ƿ���ݿ�������ΥԼ��property 
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
     * Object:���޺�ͬ's ����״̬property 
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
     * Object:���޺�ͬ's ����Ӧ������property 
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
     * Object:���޺�ͬ's �ڶ���Ӧ������property 
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
     * Object:���޺�ͬ's ��ͬ״̬property 
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
     * Object: ���޺�ͬ 's �����ͬ���� property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection getTenLongContract()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyLongContractCollection)get("tenLongContract");
    }
    /**
     * Object:���޺�ͬ's �����������property 
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
     * Object: ���޺�ͬ 's ���ԭ�� property 
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
     * Object: ���޺�ͬ 's ��Ӫҵ̬ property 
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
     * Object: ���޺�ͬ 's ������ property 
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
     * Object:���޺�ͬ's �̶����property 
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
     * Object:���޺�ͬ's ��������property 
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
     * Object: ���޺�ͬ 's ������������ property 
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