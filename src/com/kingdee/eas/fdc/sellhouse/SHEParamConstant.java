package com.kingdee.eas.fdc.sellhouse;

import java.math.BigDecimal;

public interface SHEParamConstant {
	//---------------------------把存储数据分记录存储，不同记录以不同的编码区分开来------------------------
	public static final String FaithAmount = "FaithAmount";	//诚意金额设置的编码
	public static final String PreMoneyNumber = "PreMoneyNumber";  //预订功能设置的编码
	public static final String FunctionNumber = "FunctionNumber";  //功能设置的编码
	public static final String CasNumber = "CasNumber";  //财务相关设置的编码
	public static final String BaseRoomNumber = "BaseRoomNumber";  //基础房间的编码
	//添加是否自动将预定金转到定金 eric_wang 2010.08.12
	public static final String ISPRETOOTHERMONEY ="isPreToOtherMoney";
	public static final String PROJECT_PRODUCTTYPE_SET_MAP = "projectProductTypeSetMap";
	public static final String PROJECT_SET_MAP = "projectSetMap";
	public static final String CHOOSE_ROOM_SET_MAP = "chooseRoomSetMap";
	//-----------------------------------------------------------------------------------------
	
	public static final String SPIT_STR = ",";

	public static final String T1_PRE_STANDARD = "preStandard";

	public static final String T1_PRE_PURCHASE_STANDARD = "prePurchaseStandard";

	public static final String T1_SIN_PURCHASE_STANDARD = "sinPurchaseStandard";

	public static final String T1_SIGN_LIMIT_TIME = "signLimitTime";

	public static final String T1_TO_SIGN_LIMIT_TIME = "toSignLimitTime";

	public static final String T1_PURCHASE_LIMIT_TIME = "purchaseLimitTime";

	public static final String T1_PRE_PURCHASE_LIMIT_TIME = "prePurchaseLimitTime";

	public static final String T1_KEEP_DOWN_LIMIT_TIME = "keepDownLimitTime";

	public static final String T1_COL_PRODUCT_TYPE = "productType";

	public static final String T1_COL_PROEJCT_NAME = "proejctName";
	
	public static final String T3_EXPIRY_DATE = "expiryDate";

	public static final String T3_IS_ENABLE = "isEnable";

	public static final String T3_END_DATE = "endDate";

	public static final String T3_BEGIN_DATE = "beginDate";

	public static final String T3_BUILDING = "building";

	public static final String T3_PROJECT = "project";

	public static final String T2_PROJECT = "project";

	public static final String T2_CHANCE_DAYS = "chanceDays";

	public static final String T2_CUS_TRADE_DAYS = "cusTradeDays";

	public static final String T2_IS_ENABLE_TRADE = "isEnableTrade";

	public static final String T2_IS_NO_SELL_CAN_SIN = "isNoSellCanSin";

	public static final String T2_IS_BIZ_DATE_EDITABLE = "isBizDateEditable";

	public static final String T2_IS_AREA_CHANGE_NEED_AUDIT = "isAreaChangeNeedAudit";

	public static final String T2_IS_RE_PRICE_OF_CHANGE_ROOM = "isRePriceOfChangeRoom";

	public static final String T2_IS_RE_PRICE_OF_QUIT_ROOM = "isRePriceOfQuitRoom";

	public static final String T2_IS_DEAL_AMT_BY_STAND_AMT = "isDealAmtByStandAmt";

	public static final String T2_IS_MUST_BY_BANK = "isMustByBank";

	public static final String T2_IS_FORCE_LIMIT_PRICE = "isForceLimitPrice";

	public static final String T2_IS_ENABLE_ADJUST_AGIO = "isEnableAdjustAgio";

	public static final String T2_IS_ENABLE_AGIO = "isEnableAgio";

	public static final String T2_IS_DEAL_AMOUNT_EDIT = "isDealAmountEdit";
	/**
	 * @deprecated 该列重复了，隐藏处理了
	 * */
	public static final String T2_DEAL_AMOUNT_CAN_EDIT = "dealAmountCanEdit";

	public static final String T2_CHANGE_CUS_DEF_BIZ_DATE = "changeCusDefBizDate";

	public static final String T2_PRICE_ADJUST_DEF_BIZ_DATE = "priceAdjustDefBizDate";

	public static final String T2_CHANGE_ROOM_DEF_BIZ_DATE = "changeRoomDefBizDate";
	
	//add by qinyouzhen,20110809,商机有效日期
	public static final String T2_COMMERCECHANCE_DAYS = "commerceChanceDays";
	
	public static final String T2_NAME_REPEAT_RULE= "nameRepeatRule";
	public static final String T2_PHONE_REPEAT_RULE= "phoneRepeatRule";
	public static final String T2_CER_REPEAT_RULE= "cerRepeatRule";
	
	
	public static final Object[][] t1 = {
			{T1_KEEP_DOWN_LIMIT_TIME, new Integer(10)},
			{T1_PRE_PURCHASE_LIMIT_TIME, new Integer(10)},
			{T1_PURCHASE_LIMIT_TIME, new Integer(10)},
			{T1_TO_SIGN_LIMIT_TIME, new Integer(10)},
			{T1_SIGN_LIMIT_TIME, new Integer(10)},
			
			{T1_SIN_PURCHASE_STANDARD, new BigDecimal("5000")},
			{T1_PRE_PURCHASE_STANDARD, new BigDecimal("10000")},
			{T1_PRE_STANDARD, new BigDecimal("20000")}
					};
	
	public static final Object[][] t2 = {
			{T2_NAME_REPEAT_RULE, CusRepeatRuleEnum.INFO},
			{T2_PHONE_REPEAT_RULE, CusRepeatRuleEnum.FORBITTEN},
			{T2_CER_REPEAT_RULE, CusRepeatRuleEnum.FORBITTEN},
			
			{T2_IS_DEAL_AMOUNT_EDIT, Boolean.FALSE},
			{T2_IS_ENABLE_AGIO, Boolean.FALSE},
			{T2_IS_ENABLE_ADJUST_AGIO, Boolean.TRUE},
			{T2_IS_FORCE_LIMIT_PRICE, Boolean.TRUE},
			{T2_IS_MUST_BY_BANK , Boolean.FALSE},
			
			{T2_IS_DEAL_AMT_BY_STAND_AMT, Boolean.TRUE},
			{T2_DEAL_AMOUNT_CAN_EDIT, Boolean.FALSE},
			{T2_IS_RE_PRICE_OF_QUIT_ROOM , Boolean.FALSE},
			{T2_IS_RE_PRICE_OF_CHANGE_ROOM, Boolean.FALSE},
			{T2_IS_AREA_CHANGE_NEED_AUDIT, Boolean.FALSE},
			
			{T2_IS_BIZ_DATE_EDITABLE , Boolean.FALSE},
			{T2_CHANGE_ROOM_DEF_BIZ_DATE , BizDateToEnum.OldBillBizDate},
			{T2_PRICE_ADJUST_DEF_BIZ_DATE, BizDateToEnum.OldBillBizDate},
			{T2_CHANGE_CUS_DEF_BIZ_DATE, BizDateToEnum.OldBillBizDate},
			{T2_IS_NO_SELL_CAN_SIN , Boolean.FALSE},
			
			{T2_IS_ENABLE_TRADE, Boolean.FALSE},
			{T2_CUS_TRADE_DAYS, new Integer(30)},
			{T2_CHANCE_DAYS, new Integer(30)},
			{T2_COMMERCECHANCE_DAYS, new Integer(30)}
					};
	
	public static final Object[][] t3 = {
			{T3_BEGIN_DATE, null},
			{T3_END_DATE, null},
			{T3_IS_ENABLE, Boolean.FALSE},
			{T3_EXPIRY_DATE, new Integer(30)}
	};
}
