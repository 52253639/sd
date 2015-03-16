package com.kingdee.eas.fdc.tenancy.client;

/**
 * 租赁合同client相关常量
 * @author zhicheng_jin
 * */
public interface TenancyBillConstant {
	
	public static final String DATE_FORMAT_STR = "yyyy-MM-dd";
	
	//------- 租赁操作时候传入的界面参数的key,即UIContext.put(key, value)中的key ----------
	/** 用来新租时传入房间集合RoomCollection */
	public static final String KEY_ROOMS = "rooms";
	
	/** 用来改租或续租时传入旧的租赁合同的ID */
	public static final String KEY_OLD_TENANCY_BILL_ID = "oldTenancyBillID";
	
	/** 用来传入租赁合同类型,TenancyContractTypeEnum */
	public static final String KEY_TENANCY_CONTRACT_TYPE = "tenancyContractType";
	
	/** 用来传入租赁项目,SellProjectInfo */
	public static final String KEY_SELL_PROJECT = "sellProject";
	
	
	
	//------------- 租赁合同界面的房间Table列名 ------------
	/** 房间状态 */
	public static final String C_ROOM_TEN_ROOM_STATE = "tenRoomState";
//	/** 项目 */
//	public static final String C_ROOM_SELL_PROJECT = "sellProject";
//	/** 楼栋 */
//	public static final String C_ROOM_BUILDING = "building";
//	/** 单元 */
//	public static final String C_ROOM_UNIT = "unit";
	/** 房间 */
	public static final String C_ROOM_ROOM = "room";
	/** 楼层 */
	public static final String C_ROOM_FLOOR = "floor";
	/** 标准租金 */
	public static final String C_ROOM_STANDARD_RENT = "standardRent";
	/** 标准租金类型 */
	public static final String C_ROOM_STANDARD_RENT_TYPE = "standardRentType";
	/** 标准单价 */
	public static final String C_ROOM_STANDARD_RENT_PRICE = "standardRentPrice";
	/** 成交租金 */
	public static final String C_ROOM_DEAL_RENT = "dealRent";
	/** 成交租金类型 */
	public static final String C_ROOM_DEAL_RENT_TYPE = "dealRentType";
	/** 成交单价 */
	public static final String C_ROOM_DEAL_RENT_PRICE = "dealRentPrice";
	/** 到期标记 */
	public static final String C_ROOM_FLAG_AT_TERM = "flagAtTerm";
	/** 描述 */
	public static final String C_ROOM_DES = "des";
	/** 实测建筑面积 */
	public static final String C_ROOM_BUILDING_AREA = "buildingArea";
	/** 装修情况 */
	public static final String C_ROOM_FITMENT = "fitment";
	/** 户型 */
	public static final String C_ROOM_ROOM_MODEL = "roomModel";
	/** 朝向 */
	public static final String C_ROOM_DIRECTION = "direction";
	/** 实际交房日期 */
	public static final String C_ROOM_ACT_DELIVER_DATE = "actDeliverDate";
	/** 实际退房日期 */
	public static final String C_ROOM_ACT_QUIT_DATE = "actQuitDate";
	/** 日租金 */
	public static final String C_ROOM_DAYPRICE = "dayPrice";
	
	//add by yangfan
	//------------- 违约金方案设置Table列名 ------------
	/**款项名称*/
	public static final String C_LIQUI_MONEYDEFINE="moneyDefine";
	
	/**违约金计算方案*/
	public static final String C_LIQUI_LIQUIDATED="liquidated";
	
	//------------- 租赁合同界面的配套资源Table列名------------TODO 待整理
	/** 配套资源 */
	public static final String C_ATTACH_ATTACH = "attach";
	/** 配套资源类型 */
	public static final String C_ATTACH_ATTACH_RES_TYPE = "attachResType";
	/** 配套资源名称 */
	public static final String C_ATTACH_ATTACH_RES_NAME = "attachResName";
	/** 配套资源说明 */
	public static final String C_ATTACH_ATTACH_RES_DES = "attachResDes";
	/** 标准租金 */
	public static final String C_ATTACH_STANDARD_RENT = "standardRent";
	/** 标准租金类型 */
	public static final String C_ATTACH_STANDARD_RENT_TYPE = "standardRentType";
//	/** 成交租金 */
//	public static final String C_ATTACH_DEAL_RENT = "dealRent";
//	/** 成交租金类型 */
//	public static final String C_ATTACH_DEAL_RENT_TYPE = "dealRentType";
	/** 到期标记 */
	public static final String C_ATTACH_IS_AT_TERM = "isAtTerm";
	/** 描述 */
	public static final String C_ATTACH_DES = "des";
	/** 实际交出日期 */
	public static final String C_ATTACH_ACT_DELIVER_DATE = "actDeliverDate";
	/** 实际收回日期 */
	public static final String C_ATTACH_ACT_QUIT_DATE = "actQuitDate";
	
	
	//------------- 租赁合同界面的客户Table列名 ------------
	/** 出租比例(%) */
	public static final String C_CUS_PROPERTY_PERCENT = "propertyPercent";
	/** 客户 */
	public static final String C_CUS_CUSTOMER = "customer";
	/** 邮编 */
	public static final String C_CUS_POSTALCODE = "postalcode";
	/** 联系电话 */
	public static final String C_CUS_PHONE = "phone";
	/** 证件名称 */
	public static final String C_CUS_CERTIFICATE_NAME = "certificateName";
	/** 证件号码 */
	public static final String C_CUS_CERTIFICATE_NUMBER = "certificateNumber";
	/** 通信地址 */
	public static final String C_CUS_MAIL_ADDRESS = "mailAddress";
	/** 登记日期 */
	public static final String C_CUS_BOOK_DATE = "bookDate";
	/** 备注 */
	public static final String C_CUS_DES = "des";
	
	//------------ 租金递增列表和免租列表的Table列名----
	/** 递增日期 */
	public static final String C_INC_INCREASE_DATE = "increaseDate";
	/** 递增方式 */
	public static final String C_INC_INCREASE_TYPE = "increaseType";
	/** 值 */
	public static final String C_INC_VALUE = "value";
	/** 递增类型 */    
	public static final String C_INC_INCREASESTYLE = "increaseStyle";

	/** 免租开始日期 */
	public static final String C_FREE_START_DATE = "freeStartDate";
	/** 免租结束日期 */
	public static final String C_FREE_END_DATE = "freeEndDate";
	/**免租类型 */
	public static final String C_FREE_TENANCY_TYPE="freeTenancyType";
	/** 备注 */
	public static final String C_FREE_DES = "description";
	//---------------------
	
	//------- 租金设置列表的列名 (部分列动态生成)---
	/** 房间 */
	public static final String C_RENT_ROOM = "room";
	/** 押金 */
	public static final String C_RENT_DEPOSIT = "deposit";
	/** 首租 */
	public static final String C_RENT_FIRST_RENT = "firstRent";
	/** 租金定价方式 */
	public static final String C_RENT_TENANCY_MODEL = "tenancyModel";
	/** 租金类型 */
	public static final String C_RENT_RENT_TYPE = "rentType";
	
	/** 租金定价单价前缀 */
	public static final String PREFIX_C_RENT_RENT_PRICE = "rentPrice-";
	/** 租金定价前缀 */
	public static final String PREFIX_C_RENT_RENT = "rent-";
	/** 日租金前缀 */
	public static final String  PREFIX_C_RENT_RENT_PREDAY = "rentPreDay-";
	
	/** 周期型费用单价前缀 */
	public static final String PREFIX_C_RENT_PERIODICITY_PRICE = "periodictityPrice-";
	/** 周期型费用前缀 */
	public static final String PREFIX_C_RENT_PERIODICITY = "periodictity-";
	
	//--------------
	
	//------------- 租赁合同界面的付款明细Table列名(部分列名动态生成) ---------
	/** 款项类型 */
	public static final String C_PAYS_MONEY_DEFINE = "moneyDefine";
	/** 租期序列 */
	public static final String C_PAYS_LEASE_SEQ = "leaseSeq";
	/** 起始日期 */
	public static final String C_PAYS_START_DATE = "startDate";
	/** 结束日期 */
	public static final String C_PAYS_END_DATE = "endDate";
	/** 应付日期 */
	public static final String C_PAYS_APP_PAY_DATE = "actPayDate";
	/** 免租天数 */
	public static final String C_PAYS_FREE_DAYS = "freeDays";
	
	/** 房间列前缀 */
	public static final String PREFIX_C_PAYS_ROOM = "room-";
	/** 配套资源列前缀 */
	public static final String PREFIX_C_PAYS_ATTACH_RES = "attach-";
	/** 应付金额后缀 */
	public static final String POSTFIX_C_PAYS_APP_AMOUNT = "-appPay";
	/** 实付金额后缀 */
	public static final String POSTFIX_C_PAYS_ACT_AMOUNT = "-actPay";
	/** 实付日期后缀 */
	public static final String POSTFIX_C_PAYS_ACT_PAY_DATE = "-actPayDate";
	
	/** 应付合计 */
	public static final String C_PAYS_TOTAL_APP_PAY = "totalAppPay";
	/** 实付合计 */
	public static final String C_PAYS_TOTAL_ACT_PAY = "totalActPay";
	
}
