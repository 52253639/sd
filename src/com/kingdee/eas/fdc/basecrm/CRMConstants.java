package com.kingdee.eas.fdc.basecrm;



/**
 * 防地产CRM系统参数编码定义 
 * @author jeegan_wang
 *
 */

public class CRMConstants {

	//房间上的基础设置   暂时放置在:参数设置的房地产售楼管理下
	public static final String FDCCRM_ROOM_Height = "FDCCRM_ROOM_Height";	//房间高度
	
	public static final String FDCCRM_ROOM_Width = "FDCCRM_ROOM_Width";		//房间宽度
	
	public static final String FDCCRM_ROOM_DispField = "FDCCRM_ROOM_DispField";		//房间显示字段
	
	public static final String FDCCRM_ROOM_AttachDisType = "FDCCRM_ROOM_AttachDisType";		//绑定房间显示
	
	public static final String FDCCRM_ROOM_FontName = "FDCCRM_ROOM_FontName";		//房间显示字体的名称
	
	public static final String FDCCRM_ROOM_FontStyle = "FDCCRM_ROOM_FontStyle";		//房间显示字体的样式
	
	public static final String FDCCRM_ROOM_FontSize = "FDCCRM_ROOM_FontSize";		//房间显示字体的大小
	
	public static final String FDCCRM_ROOM_FontColor = "FDCCRM_ROOM_FontColor";		//房间显示字体的颜色
	
	
	//房间上的设置 售楼部分SHE
	public static final String FDCCRM_SHE_ROOM_InitColor = "FDCCRM_SHE_ROOM_InitColor";		//房间的颜色-未推盘
	public static final String FDCCRM_SHE_ROOM_OnShowColor = "FDCCRM_SHE_ROOM_OnShowColor";		//房间的颜色-待售
	public static final String FDCCRM_SHE_ROOM_KeepDownColor = "FDCCRM_SHE_ROOM_KeepDownColor";		//房间的颜色-保留
	public static final String FDCCRM_SHE_ROOM_PrePurColor = "FDCCRM_SHE_ROOM_PrePurColor";		//房间的颜色-预定
	public static final String FDCCRM_SHE_ROOM_PurchseColor = "FDCCRM_SHE_ROOM_PurchseColor";		//房间的颜色-认购
	public static final String FDCCRM_SHE_ROOM_SignColor = "FDCCRM_SHE_ROOM_SignColor";		//房间的颜色-签约
	public static final String FDCCRM_SHE_ROOM_NoSaleColor = "FDCCRM_SHE_ROOM_NoSaleColor";		//房间的颜色-没有售楼属性

	public static final String FDCCRM_SHE_IsSpcilOnAudit = "FDCCRM_SHE_IsSpcilOnAudit";		//特殊业务以审批时间为准	
	

	//房间上的设置 租赁部分TEN
	public static final String FDCCRM_Ten_ROOM_UnTenColor = "FDCCRM_Ten_ROOM_UnTenColor";		//房间的颜色-未放租
	public static final String FDCCRM_Ten_ROOM_WaitTenColor = "FDCCRM_Ten_ROOM_WaitTenColor";		//房间的颜色-待租
	public static final String FDCCRM_Ten_ROOM_NewTenColor = "FDCCRM_Ten_ROOM_NewTenColor";		//房间的颜色-新租
	public static final String FDCCRM_Ten_ROOM_ContiuTenColor = "FDCCRM_Ten_ROOM_ContiuTenColor";		//房间的颜色-续租
	public static final String FDCCRM_Ten_ROOM_EnlargeTenColor = "FDCCRM_Ten_ROOM_EnlargeTenColor";		//房间的颜色-扩租
	public static final String FDCCRM_Ten_ROOM_KeepTenColor = "FDCCRM_Ten_ROOM_KeepTenColor";		//房间的颜色-保留
	public static final String FDCCRM_Ten_ROOM_SincerTenColor = "FDCCRM_Ten_ROOM_SincerTenColor";		//房间的颜色-预留
	public static final String FDCCRM_Ten_ROOM_NoTenColor = "FDCCRM_Ten_ROOM_NoTenColor";		//房间的颜色-没有租赁属性
	
	
	//售楼管理的其它参数上的
	//功能设置  <'Fun'+'项目id' , 设置的值以分号隔开的字符串> 比如： <"FunoC7k7sSdSu2SSLxMMs3itZAYry8=","true;false;true;false;false;true;true;true;false">
	//	代表的值依次是：//启用强制底价控制 //启用签约收款控制 //启用抵押关联控制 //认购业务实际收款为准 //启用允许诚意认购未开盘房间 //启用允许诚意金必须入账  //定金是否隶属房款	 //认购/签约日期是否允许编辑	 //退房是否必须调价
	//					isBasePrice;isSignGathering;isMortagage;isActGathering;isSincerSellOrder;isSincerPrice;isHouseMoney;isEditPurAndSignDate;isAdjustPrices;
	
	//预订功能设置  <'Pre'+'项目id' , 设置的值以分号隔开的字符串> 比如： <"FunoC7k7sSdSu2SSLxMMs3itZAYry8=","1000;false;2000;true">
	//	代表的值依次是：  //预订最低金额 //最低可否修改 //预订标准金额 //标准可否修改
	//					preLevelAmount;isLevelModify;preStandAmount;isStandModify
	
	//财务相关设置 
	public static final String FDCCRM_SHE_Cas_QuitMoneyType = "FDCCRM_SHE_Cas_QuitMoneyType";	//对应的值=款项的id+";" + (true/false)是否允许退房费用款项修改
	public static final String FDCCRM_SHE_Cas_ChangeFeeType = "FDCCRM_SHE_Cas_ChangeFeeType";	//对应的值=款项的id+";" + (true/false)是否允许换房费用款项修改
	public static final String FDCCRM_SHE_Cas_ChangeTrsfType = "FDCCRM_SHE_Cas_ChangeTrsfType";	//对应的值=款项的id+";" + (true/false)是否允许换房转款款项修改
	public static final String FDCCRM_SHE_Cas_ChangeBalcObj = "FDCCRM_SHE_Cas_ChangeBalcObj";	//对应的值=ChangeBalanceObjectEnum的值+";" + (true/false)是否允许换房结算对象修改
	public static final String FDCCRM_SHE_Cas_SincerMoneyType = "FDCCRM_SHE_Cas_SincerMoneyType";	//对应的值=款项的id+";" + (true/false)是否允许诚意金额款项修改
	
	
	//租赁管理的其它参数上的
	
	//CRM系统参数编码
	public static final String FDCCRM_PARAM_PROJECTLEVEL = "FDCCRM_010_PROJECTLEVEL";
	public static final String FDCCRM_PARAM_LEVELLENGTH = "FDCCRM_020_LEVELLENGTH";
	
	public static final String FDCSHE_PARAM_ISSHARECUS = "FDCSHE_010_ISSHARECUS";
	public static final String FDCSHE_PARAM_REPEATCUSSHOWRULE_DIR = "FDCSHE_020_REPEATCUSSHOWRULE_DIR";
	public static final String FDCSHE_PARAM_REPEATCUSSHOWRULE_START = "FDCSHE_030_REPEATCUSSHOWRULE_START";
	public static final String FDCSHE_PARAM_REPEATCUSSHOWRULE_LEN = "FDCSHE_040_REPEATCUSSHOWRULE_LEN";
	public static final String FDCSHE_PARAM_CUSREPEATRULE = "FDCSHE_050_CUSREPEATRULE";
	public static final String FDCSHE_PARAM_ISUSECHEQUE = "FDCSHE_060_ISUSECHEQUE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_INIT = "FDCSHE_070_ROOMCOLOR_INIT";
	public static final String FDCSHE_PARAM_ROOMCOLOR_ONSHOW = "FDCSHE_071_ROOMCOLOR_ONSHOW";
	public static final String FDCSHE_PARAM_ROOMCOLOR_KEEPDOWN = "FDCSHE_072_ROOMCOLOR_KEEPDOWN";
	public static final String FDCSHE_PARAM_ROOMCOLOR_CONTROL = "FDCSHE_072_ROOMCOLOR_CONTROL";
	public static final String FDCSHE_PARAM_ROOMCOLOR_SINPURCHASE = "FDCSHE_073_ROOMCOLOR_SINPURCHASE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_PREPURCHASE = "FDCSHE_074_ROOMCOLOR_PREPURCHASE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_PURCHASE = "FDCSHE_075_ROOMCOLOR_PURCHASE";
	public static final String FDCSHE_PARAM_ROOMCOLOR_SIGN = "FDCSHE_076_ROOMCOLOR_SIGN";
	public static final String FDCSHE_PARAM_TOLAMOUNT_BIT = "FDCSHE_080_TOLAMOUNT_BIT";
	public static final String FDCSHE_PARAM_TOL_TOINTEGER_TYPE = "FDCSHE_090_TOL_TOINTEGER_TYPE";
	public static final String FDCSHE_PARAM_PRICE_BIT = "FDCSHE_100_PRICE_BIT";
	public static final String FDCSHE_PARAM_PRICE_TOINTEGER_TYPE = "FDCSHE_110_PRICE_TOINTEGER_TYPE";
	//回收票据入口参数
	public static final int CHEQUEREBACK_TYEP_CHEQUE = 0;
	public static final int CHEQUEREBACK_TYEP_REVBILLEntry = 1;
	public static final int CHEQUEREBACK_TYEP_REVBILL = 2;
	
	//------------------ 客户辅助资料类别预设数据 ---------------------
//	/** 客户来源 */ 用客户基础资料中的
//	public static final String CUSTOMER_ORIGIN_GROUP_ID = "r7uyGaH8QjmY6mtFiwBbIoCJEuM=";
	/** 接待方式 */
	public static final String RECEPTION_TYPE_GROUP_ID = "AWrIRp54RU2mKNK/dZGfPICJEuM=";
	/** 居住区域 */
	public static final String HABITATION_AREA_GROUP_ID = "t2QAma06THCMM1fhcgFQJ4CJEuM=";
	/** 工作区域 */
	public static final String WORK_AREA_GROUP_ID = "4+yJb7XlRjyWtiT9wAcDMYCJEuM=";
	/** 业务范围 */
	public static final String BIZ_SCOPE_GROUP_ID = "tGKMX23PQjW3uiwz/rXiOICJEuM=";
	/** 认知途径 */
	public static final String KNOW_TYPE_GROUP_ID = "aKnRDPwgRLKedBSNSdp5VoCJEuM=";
	//--------------------------------------------------------------------
	
	//------------------------ 客户基础资料类别预设数据 -------------------------
	/** 个人 */
	public static final String PERSON_GROUP_ID = "Y+Wj8rPtRrix14gOw9isKEhRlQw=";
	/** 企业 */
	public static final String ENTERPRICE_GROUP_ID = "5MhohX5hT4Sx/Iw/1Uhow0hRlQw=";
	/** 企业证件类型 */
	public static final String EN_CERTIFICATE_TYPE_GROUP_ID = "QjTHOJE/SdeQXpOArsMIDUhRlQw=";
	/** 企业性质 */
	public static final String ENTERPRICE_PROPERTY_GROUP_ID = "VEcIKw21SKCbY32n5imloEhRlQw=";
	/** 企业规模 */
	public static final String ENTERPRICE_SIZE_GROUP_ID = "kjFpMWS3SZiB5HCbX8OouUhRlQw=";
	/** 合作模式 */
	public static final String COOPERATE_MODEL_GROUP_ID = "+gURTIYCScOj2MJw0w6W4UhRlQw=";
	/** 个人证件类型 */
	public static final String PE_CERTIFICATE_TYPE_GROUP_ID = "dKH98sDNTvepPa6zBbdTv0hRlQw=";
	/** 客户身份 */
	public static final String CUSTOMER_STATUS_GROUP_ID = "n++o6YvfRXKE8SBbbYD88khRlQw=";
	/** 年龄阶段 */
	public static final String AGE_STEP_GROUP_ID = "52JgBl0gQRu2b2dTejXk2khRlQw=";
	/** 教育程度 */
	public static final String EDUCATION_GROUP_ID = "qClQZBozSPiy+GdHQH5tkEhRlQw=";
	/** 收入层次 */
	public static final String INCOME_GROUP_ID = "dLJDogdGSj6d9X/kM3ma30hRlQw=";
	/** 兴趣爱好 */
	public static final String INTEREST_GROUP_ID = "DN6MxqEvSkuXySzMOJMwp0hRlQw=";
	/** 家庭结构 */
	public static final String FAMILY_GROUP_ID = "rCf2+xliTFSC6n2uvpBV5UhRlQw=";
	/** 职业 */
	public static final String WORK_GROUP_ID = "0oWLxLdFTeqHjD+F1Eh/80hRlQw=";
	/** 客户来源 */
	public static final String CUSTOMER_ORIGIN_GROUP_ID = "aP1LxRYPSGabLszhEs2T8UhRlQw=";
	/** 居住情况 */
	public static final String HABITATION_STATUS_GROUP_ID = "xTdtP3JzTBSwYf8/Gib280hRlQw=";
	//--------------------------------------------------------------------
	
	/**
	 * 以下为商机辅助资料的预设数据
	 */
	/**
	 * 01	商机－商机产生原因
	 */
	public static final String COMMERCECHANCE_REASON_ID="WTkBZnw4R6ykPnTi1iqB37dELa4=";
	
	/**
	 * 02	意向强度
	 */
	//public static final String HOPE_INTENSITY_ID="/egtTNg2TzqA834F8giiSrdELa4=";
	/**
	 * 03	意向－总价范围
	 */
	public static final String HOPE_TOTALAMOUNT_ID="Ksya73s9TxyhwmDJmErrhrdELa4=";
	/**
	 * 04	意向－单价区间
	 */
	public static final String HOPE_PRICE_ID="bb9+9OvfQD6xkYBMuJOUrLdELa4=";
	/**
	 * 05	意向－楼层区间
	 */
	public static final String HOPE_FLOOR_ID="ZHxBhnrrRYK5YoLNrPVpg7dELa4=";
	/**
	 * 06	意向首付比例
	 */
	//public static final String HOPE_FIRSTPAY_RATE_ID="gUWIwYh+QT2fedXTMVuF5rdELa4=";
	/**
	 * 07	意向－面积范围
	 */
	public static final String HOPE_AREA_SCOPE_ID="wFKzZaRpRQCl3VvhuKPfwrdELa4=";
	/**
	 * 08	意向购房区域
	 */
	public static final String HOPE_BUY_AREA_ID="x9MDCs3JQ0uce+MqH7sIs7dELa4=";
	/**
	 * 09	商机－置业目的
	 */
	public static final String HOPE_PURPOSE_ID="nlP5mkPWSmuIfCf6i9yEBLdELa4=";
	/**
	 * 10	置业次数
	 */
	//public static final String HOPE_BUY_NUMBER="h/FLyJa0Sc6t+dpFAveVs7dELa4=";
	/**
	 * 11	商机－商机阶段（年龄阶段）
	 */
	public static final String HOPE_STAGE_ID="iFsdzMD5R76pTkICDmcBq7dELa4=";
	/**
	 * 12	商机－商机级别
	 */
	public static final String COMMERCECHANCE_LEVEL_ID="91bqhU4lTgKoJZDW9lCj87dELa4=";
	
	/**
	 * 13  意向－景观
	 */
	public static final String  HOPE_LANDSCAPE_ID="aDya2NbITr+ymXXU7/kCW7yNrUg=";
	
	/**
	 * 商机阶段-排号
	 */
	public static final String COMMERCECHANCE_STAGE_PAIHAO="O1kNpTflRGmd+TnT9sq7LJy8SHQ=";
	/**
	 * 商机阶段-预定
	 */
	public static final String COMMERCECHANCE_STAGE_BOOKING="UCR/jByXQkKgtBkyxqFGaZy8SHQ=";
	/**
	 * 商机阶段-认购
	 */
	public static final String COMMERCECHANCE_STAGE_PURCHASE="iizo9mVGR32CH8Qg04Qfcpy8SHQ=";
	/**
	 * 商机阶段-签约
	 */
	public static final String COMMERCECHANCE_STAGE_SIGN="QkGAAtjBSeCbUrDmMFvvOZy8SHQ=";
	/**
	 * 商机阶段-退房
	 */
	public static final String COMMERCECHANCE_STAGE_QUITROOM="qMXIwmEFSJayy6BGFG5hx5y8SHQ=";
	/**
	 * 商机阶段-更名
	 */
	public static final String COMMERCECHANCE_STAGE_CHANGENAME="ty07iCgaR7S4/OAl7VDqx5y8SHQ=";
	
	/**
	 * 商机终止的原因
	 */
	public static final String COMMERCECHANCE_STOP_REASON="Bb5bDAIuRUixAVLwgwkyDrdELa4=";
	
		
	
}
