package com.kingdee.eas.fdc.basecrm;

import java.math.BigDecimal;

import com.kingdee.bos.dao.IObjectValue;

/**
 * 收款明细的值对象接口,可收款的收款明细均需要实现该接口
 * 如认购单应收明细,其他应收明细,租赁合同应收明细等
 * */
public interface IRevListInfo extends IObjectValue{
	/**
	 * 描述信息,实现类自由发挥.在收款单编辑界面显示使用
	 * @deprecated 暂时不用了
	 * */
	String getDesc();
	
	/**
	 * 获得最终应收金额.即减免之后总的应收是多少
	 * 主要是因为租赁，物业对减免的实现方式不一致（租赁减免后会反写应收金额，而物业不会）
	 * @return 对于售楼：没有减免，返回 应收金额
	 *            租赁：减免已体现在应收中，返回 应收金额
	 *            物业：减免未体现在应收中，返回 应收金额-减免金额
	 * */
	BigDecimal getFinalAppAmount();
	
	/**
	 * 获得调整后已收
	 * 实现方式为：实收金额-已调
	 * */
	BigDecimal getActRevAmountAfterAdjust();
	
	/**
	 * 获得总剩余金额
	 * 实现方式为：调整后已收-已转-已退-实际划入费用
	 * */
	BigDecimal getAllRemainAmount();
	
	/**
	 * 获得剩余金额
	 * 实现方式为：调整后已收-已转-已退-划入费用
	 * */
	BigDecimal getRemainAmount();
	
	/**
	 * 获得剩余限制金额
	 * 实现方式为：
	 * 	if(剩余金额可退){
			return 剩余金额;
		}else{
			return 限制金额-已转-已退-已调;
		}
	 * */
	BigDecimal getRemainLimitAmount();
	
	/**
	 * 获得明细的类型
	 * */
	RevListTypeEnum getRevListTypeEnum();
	
	/**
     * 收款明细's 应收金额property 
     */
    public java.math.BigDecimal getAppAmount();
    public void setAppAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 应收日期property 
     */
    public java.util.Date getAppDate();
    public void setAppDate(java.util.Date item);
    /**
     * 收款明细 's 款项类型 property 
     */
    public com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo getMoneyDefine();
    public void setMoneyDefine(com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo item);
    /**
     * 收款明细's 实收金额property 
     */
    public java.math.BigDecimal getActRevAmount();
    public void setActRevAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 划入费用金额property 
     */
    public java.math.BigDecimal getToFeeAmount();
    public void setToFeeAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 已转入费用金额property 
     */
    public java.math.BigDecimal getHasToFeeAmount();
    public void setHasToFeeAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 限制金额property 
     */
    public java.math.BigDecimal getLimitAmount();
    public void setLimitAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 已转金额property 
     */
    public java.math.BigDecimal getHasTransferredAmount();
    public void setHasTransferredAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 已退金额property 
     */
    public java.math.BigDecimal getHasRefundmentAmount();
    public void setHasRefundmentAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 已调金额property 
     */
    public java.math.BigDecimal getHasAdjustedAmount();
    public void setHasAdjustedAmount(java.math.BigDecimal item);
    /**
     * 收款明细's 是否剩余金额可退property 
     */
    public boolean isIsRemainCanRefundment();
    public void setIsRemainCanRefundment(boolean item);
    /**
     * 收款明细's 收款款项标识property 
     */
    public com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum getRevMoneyType();
    public void setRevMoneyType(com.kingdee.eas.fdc.basecrm.RevMoneyTypeEnum item);
    /**
     * 收款明细's 退款款项标识property 
     */
    public com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum getRefundmentMoneyType();
    public void setRefundmentMoneyType(com.kingdee.eas.fdc.basecrm.RefundmentMoneyTypeEnum item);
    /**
     * 收款明细's 是否可超收property 
     */
    public boolean isIsCanRevBeyond();
    public void setIsCanRevBeyond(boolean item);
    /**
     * 收款明细's 费用款项标识property 
     */
    public com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum getFeeMoneyType();
    public void setFeeMoneyType(com.kingdee.eas.fdc.basecrm.FeeMoneyTypeEnum item);
    
    /**
     * EAS基类's IDproperty 
     */
    public com.kingdee.bos.util.BOSUuid getId();
    public void setId(com.kingdee.bos.util.BOSUuid item);
          
    /**
     * 收款明细's 实付日期property 
     */
    public java.util.Date getActRevDate();
    public void setActRevDate(java.util.Date item);
    
    /**
     * 收款明细's 序号property 
     */
    public int getSeq();
    public void setSeq(int item);
}
