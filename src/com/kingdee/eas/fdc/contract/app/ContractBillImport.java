package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.ContractSourceFactory;
import com.kingdee.eas.fdc.basedata.ContractSourceInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.LandDeveloperFactory;
import com.kingdee.eas.fdc.basedata.LandDeveloperInfo;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPropertyEnum;
import com.kingdee.eas.fdc.contract.ContractSourceEnum;
import com.kingdee.eas.fdc.contract.ContractWFTypeFactory;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.CostPropertyEnum;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;

/** 					
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		lwj
 * @version		EAS7.0		
 * @createDate	2011-6-10	 
 * @see  合同录入的引入引出工具类
 */
public class ContractBillImport extends AbstractDataTransmission{
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	private Context contx = null;

	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		
		ICoreBase factory = null;
		try {
			factory = ContractBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		ContractBillInfo  info = new ContractBillInfo();
		contx = ctx;
	
		//取值
//		String FOrgUnit_name = FDCTransmissionHelper.getFieldValue(hsData, "FOrgUnit_name_l2");//组织编码
		String FCurProject_longNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCurProject_longNumber");//工程项长编码
		String FState = FDCTransmissionHelper.getFieldValue(hsData, "FState");//状态
		String FContractType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FContractType_name_l2");//合同类型编码
		String FCodingNumber = FDCTransmissionHelper.getFieldValue(hsData, "FCodingNumber");//合同编码
		String FName = FDCTransmissionHelper.getFieldValue(hsData, "FName");//名称
//		String FProgrammingContract_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FProgrammingContract_name_l2");//框架合约名称
		String FLandDeveloper_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FLandDeveloper_name_l2");//甲方编码
		String FPartB_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FPartB_name_l2");//乙方编码
		String FPartC_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FPartC_name_l2");//丙方编码
		String FContractPropert = FDCTransmissionHelper.getFieldValue(hsData, "FContractPropert");//合同性质
//		String FSignDate = FDCTransmissionHelper.getFieldValue(hsData, "FSignDate");//签约日期
		String FCurrency_number = FDCTransmissionHelper.getFieldValue(hsData, "FCurrency_number");//币种编码
		String FExRate = FDCTransmissionHelper.getFieldValue(hsData, "FExRate");//汇率
		String FRespDept_number = FDCTransmissionHelper.getFieldValue(hsData, "FRespDept_number");//责任部门编码
		String FOriginalAmount = FDCTransmissionHelper.getFieldValue(hsData, "FOriginalAmount");//原币金额
		String FAmount = FDCTransmissionHelper.getFieldValue(hsData, "FAmount");//本位币金额
		String FRespPerson_number = FDCTransmissionHelper.getFieldValue(hsData, "FRespPerson_number");//责任人名字
		
		String FGrtRate = FDCTransmissionHelper.getFieldValue(hsData, "FGrtRate");//保修金比率
		FGrtRate = FGrtRate.replace('%', ' ').trim();
		
		String FGrtAmount = FDCTransmissionHelper.getFieldValue(hsData, "FGrtAmount");//保修金金额
		String FBizDate = FDCTransmissionHelper.getFieldValue(hsData, "FBizDate");//业务日期
//		String FCostProperty = FDCTransmissionHelper.getFieldValue(hsData, "FCostProperty");//造价性质
		
//		String FPayPercForWarn = FDCTransmissionHelper.getFieldValue(hsData, "FPayPercForWarn");//付款提示比例
//		FPayPercForWarn = FPayPercForWarn.replace('%', ' ').trim();
//		
//		String FChgPercForWarn = FDCTransmissionHelper.getFieldValue(hsData, "FChgPercForWarn");//变更提示比例
//		FChgPercForWarn = FChgPercForWarn.replace('%', ' ').trim();
//		
//		String FPayScale = FDCTransmissionHelper.getFieldValue(hsData, "FContractType_payScale");//进度款付款比例
//		FPayScale = FPayScale.replace('%', ' ').trim();
//		
//		String FOverRate = FDCTransmissionHelper.getFieldValue(hsData, "FOverRate");//结算提示比率
//		FOverRate = FOverRate.replace('%', ' ').trim();
		
//		String FStampTaxRate = FDCTransmissionHelper.getFieldValue(hsData, "FStampTaxRate");//印花税率
//		FStampTaxRate = FStampTaxRate.replace('%', ' ').trim();
//		
//		String FStampTaxAmt = FDCTransmissionHelper.getFieldValue(hsData, "FStampTaxAmt");//印花税金额
		String FContractSource = FDCTransmissionHelper.getFieldValue(hsData, "FContractSource");//形成方式
		String FIsCoseSplit = FDCTransmissionHelper.getFieldValue(hsData, "FIsCoseSplit");//是否进入动态成本
		String FIsPartAMaterialCon = FDCTransmissionHelper.getFieldValue(hsData, "FIsPartAMaterialCon");//是否甲供材料合同
//		String FIsArchived = FDCTransmissionHelper.getFieldValue(hsData, "FIsArchived");//是否已归档
		String FCreator_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FCreator_name_l2");//制单人
		String FCreateTime = FDCTransmissionHelper.getFieldValue(hsData, "FCreateTime");//制单时间
		String FAuditor_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FAuditor_name_l2");//审核人
		String FAuditTime = FDCTransmissionHelper.getFieldValue(hsData, "FAuditTime");//审核时间
		
		String FNeedPerson_number = FDCTransmissionHelper.getFieldValue(hsData, "FNeedPerson_number");
		String FNeedDept_number = FDCTransmissionHelper.getFieldValue(hsData, "FNeedDept_number");
		String FContractWFType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FContractWFType_name_l2");
		String FOrgType = FDCTransmissionHelper.getFieldValue(hsData, "FOrgType");
		String FInviteType_name_l2 = FDCTransmissionHelper.getFieldValue(hsData, "FInviteType_name_l2");
		String FCreateDept_number =FDCTransmissionHelper.getFieldValue(hsData, "FCreateDept_number");
		
		//校验数据    格式是否合法        必录项是否为空     长度是否超出
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"zzcbm"),FOrgUnit_name,"String",false,40);//组织长编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"gcxmcbm"), FCurProject_longNumber, "String", true, 40);//工程项目长编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhuangtai"), FState, "String", false, 40);//状态
		FDCTransmissionHelper.valueFormat("合同类型", FContractType_name_l2, "String", true, 40);//合同类型编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"htbm"), FCodingNumber, "String", true, 40);//合同编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"htmc"), FName, "String", true, 200);//合同名称
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"kjhymc"), FProgrammingContract_name_l2, "String", false, 40);//框架合约名称
		FDCTransmissionHelper.valueFormat(getResource(ctx,"jfbm"), FLandDeveloper_name_l2, "String", true, 40);//甲方编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"yfbm"), FPartB_name_l2, "String", true, 40);//乙方编码
		if(FContractPropert.trim().equals("THREE_PARTY")){//是3方合同的情况    丙方编码必录
			FDCTransmissionHelper.valueFormat(getResource(ctx,"bfbm"), FPartC_name_l2, "String", true, 40);//丙方编码
		}else{//不是3方合同的情况
			FDCTransmissionHelper.valueFormat(getResource(ctx,"bfbm"), FPartC_name_l2, "String", false, 40);
		}
		FDCTransmissionHelper.valueFormat(getResource(ctx,"hetongxz"), FContractPropert, "String", true, 40);//合同性质
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"qianyueriq"), FSignDate, "Date", true, -1);//签约日期
		FDCTransmissionHelper.valueFormat(getResource(ctx,"bizhongbianma"), FCurrency_number, "String", false, 40);//币种编码
		this.bdValueFormat(getResource(ctx,"huilv"),FExRate,false,18,10);//汇率
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zrbmbm"), FRespDept_number, "String", true, 40);//责任部门编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zrrbm"), FRespPerson_number, "String", true, 40);//责任人编码
//		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhaojiaxingzhi"), FCostProperty, "String", true, 40);//造价性质
		this.bdValueFormat(getResource(ctx,"yuanbijine"), FOriginalAmount, true, 15,4);//原币金额
		this.bdValueFormat(getResource(ctx,"bweibijine"), FAmount, true, 15,4);//本位币金额
		this.bdValueFormat(getResource(ctx,"baoxioujinbilv"), FGrtRate, true, 15, 4);//保修金比例
		this.bdValueFormat(getResource(ctx,"baoxioujinjine"), FGrtAmount, true, 15,4);//保修金金额
		FDCTransmissionHelper.valueFormat(getResource(ctx,"yewuriqi"), FBizDate, "Date", true, 40);//业务日期
//		this.bdValueFormat(getResource(ctx,"fukuantishibili"), FPayPercForWarn, false, 15,4);//付款提示比例
//		this.bdValueFormat(getResource(ctx,"biangentishibili"), FChgPercForWarn, false, 15,4);//变更提示比例
//		this.bdValueFormat(getResource(ctx,"jindukuanbili"), FPayScale, false, 15,4);//进度款付款比例
//		this.bdValueFormat(getResource(ctx,"jieduantishibilv"), FOverRate, false, 15,4);//结算提示比率
//		this.bdValueFormat(getResource(ctx,"yinhuasuilv"), FStampTaxRate, false, 15,4);//印花税率
//		this.bdValueFormat(getResource(ctx,"yinhuasuijine"), FStampTaxAmt, true, 15,4);//印花税金额
		FDCTransmissionHelper.valueFormat(getResource(ctx,"xingchengfangshi"), FContractSource, "String", true, 40);//形成方式
		FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoujinrdongtaicb"), FIsCoseSplit, false,getResource(ctx,"yes"),getResource(ctx,"no"), -1);//是否进入动态成本
		FDCTransmissionHelper.isBoolean(getResource(ctx,"shifoujiagonghetong"), FIsPartAMaterialCon, false,getResource(ctx,"yes"),getResource(ctx,"no"), -1);//是否甲供材料合同
//		FDCTransmissionHelper.isBoolean(getResource(ctx,"shifouyiguidang"), FIsArchived, false,getResource(ctx,"yes"),getResource(ctx,"no"), -1);//是否已归档	
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhidanrbianma"), FCreator_name_l2, "String", true, 40);//制单人编码
		FDCTransmissionHelper.valueFormat(getResource(ctx,"zhidanriqi"), FCreateTime, "Date", true, 40);//制单日期
		FState = FState.trim();
		if (!FState.equals(getResource(ctx,"saved")) && !FState.equals(getResource(ctx,"submitted")) && !FState.equals("审批中") && !FState.equals("作废")) {
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenherbianma"), FAuditor_name_l2, "String", true, 40);//审核人编码
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenheshijian"), FAuditTime, "Date", true, 40);//审核时间
		}else{
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenherbianma"), FAuditor_name_l2, "String", false, 40);//特殊判断
			FDCTransmissionHelper.valueFormat(getResource(ctx,"shenheshijian"), FAuditTime, "Date", false, 40);//特殊判断
		}
		
		FDCTransmissionHelper.valueFormat("需求人部门编码", FNeedDept_number, "String", true, 40);
		FDCTransmissionHelper.valueFormat("需求人编码", FNeedPerson_number, "String", true, 40);
		FDCTransmissionHelper.valueFormat("合同流程类型", FContractWFType_name_l2, "String", true, 40);
		FDCTransmissionHelper.valueFormat("审批流程发起组织", FOrgType, "String", true, 40);
		FDCTransmissionHelper.valueFormat("采购类别", FInviteType_name_l2, "String", false, 40);
		FDCTransmissionHelper.valueFormat("制单部门编码", FCreateDept_number, "String", false, 40);
		//数据库中验证   值
		EntityViewInfo view = null;
		FilterInfo filter = null;
		
		FullOrgUnitInfo fouinfo = null;//成本中心 为了取组织编码
		CurProjectInfo cpjinfo = null;//工程项目
		FDCBillStateEnum enumValue= null;//单据状态
		ContractTypeInfo ctinfo = null;//合同类型编码
		ContractBillInfo cbinfo=null , cbnoinfo=null;//合同单据名称  合同单据编码
//		ProgrammingContractInfo pcinfo = null;//框架合约
		LandDeveloperInfo linfo = null;//甲方合约编码
		SupplierInfo slinfo = null;//乙方编码
		SupplierInfo slerinfo = null;//丙方编码
		ContractPropertyEnum cpenum = null;//合同性质
		CurrencyInfo currinfo = null;//币种
		ExchangeRateInfo erinfo = null;//汇率对象
		AdminOrgUnitInfo aouinfo = null;//责任部门编码
		PersonInfo pinfo = null;//责任人名字 FRespPerson
//		CostPropertyEnum cppenum = null;////造价性质 FCostProperty
//		BigDecimal bd = null;//用于储存印花税金额BigDecimal 的值
		ContractSourceEnum csenum = null;//形成方式 FContractSource
		ContractSourceInfo csinfo = null;//形成方式id
		
		UserInfo uinfo = null,ainfo = null;//制单人 FCreator_name_l2    ,  审核人 FAuditor_name_l2
		Timestamp tt = null;//创建日期  时间戳
		BigDecimal bgm = null;//汇率的值
		
		AdminOrgUnitInfo needDept = null;
		PersonInfo needPerson = null;
		ContractWFTypeInfo wfType= null;
		ContractTypeOrgTypeEnum orgType=null;
		InviteTypeInfo inviteType=null;
		AdminOrgUnitInfo createDept = null;
		try {
			//工程项目对象
			cpjinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(this.getView("longnumber", FCurProject_longNumber.replace('.', '!'))).get(0);
			if(cpjinfo==null){//工程项目编码在系统中不存在
				FDCTransmissionHelper.isThrow(getResource(ctx, "ContractBillImport_FCurProject_longNumber"));
			}
			CostCenterOrgUnitInfo costcouinfo = cpjinfo.getCostCenter();//取成本中心
			if(costcouinfo==null){//工程项目没有对应的成本中心
				FDCTransmissionHelper.isThrow(getResource(ctx, "gcxmmydydcbzx"));
			}
//			String ccouid = costcouinfo.getId().toString();//获得成本中心的id
//			CostCenterOrgUnitInfo ccouinfo = CostCenterOrgUnitFactory.getLocalInstance(ctx)
//					.getCostCenterOrgUnitCollection(this.getView("id", ccouid)).get(0);// 成本中心对象
			
			//成本中心名字   为了取出组织编码  取工程项目长编码对应成本中心名字
			fouinfo = cpjinfo.getCostCenter().castToFullOrgUnitInfo();//强转工程对象-》组织编码
			
			//工程长编码找不到的时候
			if(cpjinfo == null){
				FDCTransmissionHelper.isThrow("", getResource(ctx, "gcxmcbmzxtzbcz"));
			}
			
			//单据状态的值
			String stateEnum = FState.trim();//状态
			//状态定义：保存、提交、已审批、作废。默认已审批
			if(stateEnum.equals(getResource(ctx,"saved"))){
				stateEnum = "1SAVED";
			}else if(stateEnum.equals(getResource(ctx,"submitted"))){
				stateEnum = "2SUBMITTED";
			}else if(stateEnum.equals(getResource(ctx,"auditting"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));//状态只能填写保存、已提交、已审批、作废。默认已审批
			}else if(stateEnum.equals(getResource(ctx,"auditted"))){
				stateEnum = "4AUDITTED";
			}else if(stateEnum.equals(getResource(ctx,"stop"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));
			}else if(stateEnum.equals(getResource(ctx,"announce"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));
			}else if(stateEnum.equals(getResource(ctx,"visa"))){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ztzntxbctjyspzfmrysp"));
			}else if(stateEnum.equals(getResource(ctx,"invalid"))){
				stateEnum = "9INVALID";
			} else {// 默认已经审批
				stateEnum = "4AUDITTED";
			}
			enumValue = FDCBillStateEnum.getEnum(stateEnum);
			
			//合同类型编码
			ctinfo = ContractTypeFactory.getLocalInstance(ctx).getContractTypeCollection(this.getView("name", FContractType_name_l2)).get(0);
			//合同编码类型找不到的时候
			if(ctinfo == null){
				FDCTransmissionHelper.isThrow("合同类型在系统中不存在");
			}
			
			//合同单据编码
			cbnoinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("codingnumber", FCodingNumber)).get(0);
			if(cbnoinfo!=null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_fCodingNumber"));
			}
			
			//合同单名字
			cbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(this.getView("name", FName)).get(0);
			//合同单据名称重复的时候，提示用户
			if(cbinfo!=null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_fName"));
			}
			 
//			//框架合约
//			pcinfo = ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractCollection(this.getView("name", FProgrammingContract_name_l2)).get(0);
//			//框架合约在系统中找不到的时候
//			if(pcinfo==null && !FProgrammingContract_name_l2.trim().equals("") && FProgrammingContract_name_l2!=null){
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FProgrammingContract_name_l2"));
//			}
			
			//甲方编码
			linfo = LandDeveloperFactory.getLocalInstance(ctx).getLandDeveloperCollection(this.getView("name", FLandDeveloper_name_l2)).get(0);
			//甲方编码中找不到的时候
			if(linfo==null){
				FDCTransmissionHelper.isThrow("甲方在系统中不存在");
			}
			
			//乙方编码 
			slinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FPartB_name_l2)).get(0);
			//乙方编码找不到的时候
			if(slinfo==null){
				FDCTransmissionHelper.isThrow("乙方在系统中不存在");
			}
			
			//丙方编码
			slerinfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(this.getView("name", FPartC_name_l2)).get(0);
			
			//合同性质
			String cproenum = FContractPropert.trim();//状态
			if(cproenum.equals(getResource(ctx,"directContract"))){
				cproenum = "DIRECT";
			}else if(cproenum.equals(getResource(ctx,"threePartContract"))){
				cproenum = "THREE_PARTY";
			}else if(cproenum.equals(getResource(ctx,"supplyContract"))){
				cproenum = "SUPPLY";
			}else{
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_Fcpenum"));
			}
			cpenum = ContractPropertyEnum.getEnum(cproenum);
			
			//币种编码 
			currinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(this.getView("number", FCurrency_number)).get(0);
			//币种为空的时候去本位币 
			if(currinfo==null){
				currinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where number='RMB'").get(0);
				if(currinfo==null){//在系统中找不到本位币
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCanotRMB"));
				}	
			}
			
			CurrencyInfo ci = null;//用于找本位币
			view = new EntityViewInfo();
			filter = new FilterInfo();
			//汇率  如果汇率为空   取当前汇率
			if(FExRate.trim().equals("") || FExRate==null){
				//本位币对象ci        //用户填写的币种-原币对象 currinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where number='RMB'").get(0);
				if(ci==null){//ci是本位币哈   找不到本位币
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCanotRMB"));
				}else{//找到本位币的情况
					if(ci.getName().trim().equals(currinfo.getName().trim())){//都是人民币的情况
						bgm = new BigDecimal(1.0000);
					}else{
						filter.getFilterItems().add(new FilterItemInfo("targetCurrency",ci.getId(),CompareType.EQUALS));//目标币本位币
						filter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currinfo.getId(), CompareType.EQUALS));//用户填写的原币
						filter.setMaskString("#0 and #1");
						view.setFilter(filter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(view).get(0);
						if(xinfo==null){
							FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FyuanAndRmb"));
						}else{
							erinfo = ExchangeRateFactory.getLocalInstance(ctx).
							getExchangeRate(new ObjectUuidPK(xinfo.getExchangeTable().getId()),
									new ObjectUuidPK(xinfo.getSourceCurrency().getId()), 
									new ObjectUuidPK(xinfo.getTargetCurrency().getId()), 
									Calendar.getInstance().getTime());
							bgm = erinfo.getConvertRate();
							if(erinfo==null){
								FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FyuanAndRmb"));
							}
						}
					}
				}	
			}else{
				bgm = FDCTransmissionHelper.strToBigDecimal(FExRate);
			}
			
			//责任部门编码
			aouinfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("number", FRespDept_number)).get(0);
			if(aouinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FRespDept_number"));
			}
			
			//责任人编码 
			pinfo = PersonFactory.getLocalInstance(ctx).getPersonCollection(this.getView("number", FRespPerson_number)).get(0);
			if(pinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FRespPerson_number"));
			}
//			////造价性质 FCostProperty
//			String cptyenum = FCostProperty.trim();
//			if(cptyenum.equals(getResource(ctx,"tempeval"))){
//				cptyenum = "TEMP_EVAL";
//			}else if(cptyenum.equals(getResource(ctx,"baseconfirm"))){
//				cptyenum = "BASE_CONFIRM";
//			}else if(cptyenum.equals(getResource(ctx,"compconfirm"))){
//				cptyenum = "COMP_COMFIRM";
//			}else{
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_Fcppenum"));
//			}
//			cppenum = CostPropertyEnum.getEnum(cptyenum);
			
//			//付款提示比例
//			double b = FDCTransmissionHelper.strToDouble(FPayPercForWarn);
//			if(FPayPercForWarn.trim().equals("") || FPayPercForWarn==null){
//				FPayPercForWarn = "85";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FPayPercForWarn"));
//			}
//			
//			//变更提示比例 FChgPercForWarn
//			b = FDCTransmissionHelper.strToDouble(FChgPercForWarn);
//			if(FChgPercForWarn==null || FChgPercForWarn.trim().equals("")){
//				FChgPercForWarn = "5";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FChgPercForWarn"));
//			}
//			
//			//进度款付款比例 FPayScale
//			b = FDCTransmissionHelper.strToDouble(FPayScale);
//			if(FPayScale==null || FPayScale.trim().equals("")){
//				FPayScale = "0";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FPayScale"));
//			}
//			
//			//结算提示比率 FOverRate
//			b = FDCTransmissionHelper.strToDouble(FOverRate);
//			if(FOverRate==null || FOverRate.trim().equals("")){
//				FOverRate = "0";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FOverRate"));
//			}
			
//			//印花税率 FStampTaxRate
//			b = FDCTransmissionHelper.strToDouble(FStampTaxRate);
//			if(FStampTaxRate==null || FStampTaxRate.trim().equals("")){
//				FStampTaxRate = "0";
//			} else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FStampTaxRate"));
//			}
//			
//			//印花税金额 FStampTaxAmt
//			b = FDCTransmissionHelper.strToDouble(FStampTaxAmt);
//			if(b==0){
//				BigDecimal baAmount = FDCTransmissionHelper.strToBigDecimal(FOriginalAmount);
//				BigDecimal baTaxRate = FDCTransmissionHelper.strToBigDecimal(FStampTaxRate);
//				bd = baAmount.multiply(baTaxRate);
//			} 
//			else if (b < 0 || b > 100) {
//				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FStampTaxAmt"));
//			}
//			else{
//				bd = FDCTransmissionHelper.strToBigDecimal(FStampTaxAmt);
//			}
			
			//形成方式 FContractSource
			String csouenum = FContractSource.trim();
			//界面上用的形成方式  是  ContractSourceID
			csinfo = ContractSourceFactory.getLocalInstance(ctx).getContractSourceCollection(this.getView("name", csouenum)).get(0);
			if(csinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"xcfszxtzbcz"));//形成方式在系统中不存在
			}
			
			if(csouenum.equals(getResource(ctx,"trust"))){
				csouenum = "TRUST";
			}else if(csouenum.equals(getResource(ctx,"invite"))){
				csouenum = "INVITE";
			}else if(csouenum.equals(getResource(ctx,"discuss"))){
				csouenum = "DISCUSS";
			}else if(csouenum.equals(getResource(ctx,"coop"))){
				csouenum = "COOP";
			}else{
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_Fcppenum"));
			}
			csenum = ContractSourceEnum.getEnum(csouenum);
			
			
			//是否进入动态成本 FIsCoseSplit
			FIsCoseSplit = FIsCoseSplit.trim();
			if(FIsCoseSplit==null || FIsCoseSplit.equals("")){
				FIsCoseSplit = "true";
			}else if(FIsCoseSplit.equals(getResource(ctx,"yes"))){
				FIsCoseSplit = "true";
			}else if(FIsCoseSplit.equals(getResource(ctx,"no"))){
				FIsCoseSplit = "false";
			}
			
			////是否甲供材料合同 FIsPartAMaterialCon
			FIsPartAMaterialCon = FIsPartAMaterialCon.trim();
			if(FIsPartAMaterialCon==null || FIsPartAMaterialCon.equals("")){
				FIsPartAMaterialCon = "false";
			}else if(FIsPartAMaterialCon.equals(getResource(ctx,"yes"))){
				FIsPartAMaterialCon = "true";
			}else if(FIsPartAMaterialCon.equals(getResource(ctx,"no"))){
				FIsPartAMaterialCon = "false";
			}
			
//			//是否已归档 FIsArchived
//			FIsArchived = FIsArchived.trim();
//			if(FIsArchived==null || FIsArchived.equals("")){
//				FIsArchived = "false";
//			}else if(FIsArchived.equals(getResource(ctx,"yes"))){
//				FIsArchived = "true";
//			}else if(FIsArchived.equals(getResource(ctx,"no"))){
//				FIsArchived = "false";
//			}
			
			//制单人 FCreator_name_l2
			uinfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FCreator_name_l2)).get(0);
			if(uinfo==null){
				FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCreator_name_l2"));
			}
			
			//制单日期 FCreateTime
			tt = Timestamp.valueOf(FCreateTime+" 00:00:00.0");
			
			//审核人 FAuditor_name_l2
			uinfo = UserFactory.getLocalInstance(ctx).getUserCollection(this.getView("number", FAuditor_name_l2)).get(0);
			if(!FAuditor_name_l2.trim().equals("")){
				if(uinfo==null){
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FAuditor_name_l2"));
				}
			}
			
			needDept = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("number", FNeedDept_number)).get(0);
			if(needDept==null){
				FDCTransmissionHelper.isThrow("需求部门编码在系统中不存在");
			}
			
			needPerson = PersonFactory.getLocalInstance(ctx).getPersonCollection(this.getView("number", FNeedPerson_number)).get(0);
			if(needPerson==null){
				FDCTransmissionHelper.isThrow("需求人编码在系统中不存在");
			}
			
			wfType = ContractWFTypeFactory.getLocalInstance(ctx).getContractWFTypeCollection(this.getView("name", FContractWFType_name_l2)).get(0);
			if(wfType == null){
				FDCTransmissionHelper.isThrow("合同流程类型在系统中不存在");
			}
			
			String orgTypeEnum = FOrgType.trim();
			if(orgTypeEnum.equals("集团/事业部/城市公司-项目部")){
				orgTypeEnum = "ALLRANGE";
			}else if(orgTypeEnum.equals("集团/事业部/城市公司")){
				orgTypeEnum = "BIGRANGE";
			}else if(orgTypeEnum.equals("项目部")){
				orgTypeEnum = "SMALLRANGE";
			}else{
				FDCTransmissionHelper.isThrow("审批流程发起组织不存在");
			}
			orgType = ContractTypeOrgTypeEnum.getEnum(orgTypeEnum);
			
			inviteType = InviteTypeFactory.getLocalInstance(ctx).getInviteTypeCollection(this.getView("name", FInviteType_name_l2)).get(0);
			createDept = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(this.getView("number", FCreateDept_number)).get(0);
			//设置  值
			info.setOrgUnit(fouinfo);//组织编码1
			info.setCurProject(cpjinfo);//工程项目长编码2
			//状态3
			info.setState(enumValue);
				
			info.setContractType(ctinfo);//合同类型编码4
			info.setNumber(FCodingNumber);//合同单据编码5
			info.setCodingNumber(FCodingNumber);
			info.setName(FName);//合同名字6
//			info.setProgrammingContract(pcinfo);//框架合约7
			info.setLandDeveloper(linfo);//甲方编码8
			info.setPartB(slinfo);//乙方编码9
			info.setPartC(slerinfo);//丙方编码10
			//合同性质11 
			info.setContractPropert(cpenum);
			
			info.setSignDate(FDCTransmissionHelper.strToDate(FBizDate));//FSignDate签约日期12
			info.setCurrency(currinfo);// 币种编码 13
		    info.setExRate(bgm);//汇率14,FExRate
			info.setRespDept(aouinfo);//责任部门编码15 FRespDept_number
			info.setRespPerson(pinfo);//责任人名字16 FRespPerson_name_l2
			//造价性质 17FCostProperty
			info.setCostProperty(CostPropertyEnum.BASE_CONFIRM);
			
			info.setOriginalAmount(FDCTransmissionHelper.strToBigDecimal(FOriginalAmount));//原币金额18 FOriginalAmount
			info.setAmount(FDCTransmissionHelper.strToBigDecimal(FAmount));//本位币金额19 
			info.setGrtRate(FDCTransmissionHelper.strToBigDecimal(FGrtRate));//保修金比率20 FGrtRate
			info.setGrtAmount(FDCTransmissionHelper.strToBigDecimal(FGrtAmount));//保修金金额21 FGrtAmount
			info.setBizDate(FDCTransmissionHelper.strToDate(FBizDate));//FBizDate22 业务日期
			
			info.setPayPercForWarn(new BigDecimal("85"));
			info.setChgPercForWarn(new BigDecimal("5"));
			
//			info.setPayPercForWarn(FDCTransmissionHelper.strToBigDecimal(FPayPercForWarn));//付款提示比例23 FPayPercForWarn
//		    info.setChgPercForWarn(FDCTransmissionHelper.strToBigDecimal(FPayPercForWarn));//变更提示比例24 FChgPercForWarn
//			info.setPayScale(FDCTransmissionHelper.strToBigDecimal(FPayScale));//进度款付款比例25 payScale
//			info.setOverRate(FDCTransmissionHelper.strToDouble(FOverRate));//结算提示比率26 FOverRate
//			info.setStampTaxRate(FDCTransmissionHelper.strToBigDecimal(FStampTaxRate));//印花税率27 FStampTaxRate
//			info.setStampTaxAmt(bd);//印花税金额28 FStampTaxAmt
			
			//形成方式29  FContractSource
			info.setContractSource(csenum);
			info.setContractSourceId(csinfo);
			
			info.setIsCoseSplit(FDCTransmissionHelper.strToBoolean(FIsCoseSplit, "true"));//是否进入动态成本30 FIsCoseSplit
			info.setIsPartAMaterialCon(FDCTransmissionHelper.strToBoolean(FIsPartAMaterialCon, "true"));//是否甲供材料合同31 FIsPartAMaterialCon
//			info.setIsArchived(FDCTransmissionHelper.strToBoolean(FIsArchived, "true"));//是否已归档32 FIsArchived
			
			info.setCreator(uinfo);//制单人33 FCreator_name_l2
			info.setCreateTime(tt);//制单日期34 FCreateTime
			info.setAuditor(ainfo);//审核人35 FAuditor_name_l2
			info.setAuditTime(FDCTransmissionHelper.strToDate(FAuditTime));//审核日期36 FAuditTime
			
			ContractBailInfo bailInfo = new ContractBailInfo();//合同经济条款id 在界面上录入合同的时候就已经自动生成
			BOSUuid id = BOSUuid.create(bailInfo.getBOSType());//所以这里也需要自动生成经济条款的id
			bailInfo.setId(id);
			info.setBail(bailInfo);

			info.setNeedDept(needDept);
			info.setNeedPerson(needPerson);
			info.setContractWFType(wfType);
			info.setOrgType(orgType);
			info.setInviteType(inviteType);
			info.setCreateDept(createDept);
		}catch (BOSException e) {
			FDCTransmissionHelper.isThrow("",e.toString());
		}catch (EASBizException e) {
			e.printStackTrace();
		}

		return info;
	}
	
	
	//提交
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ContractBillInfo == false) {
			return;
		}	
		try {
			ContractBillInfo billBase = (ContractBillInfo) coreBaseInfo;
			ContractBailInfo bailInfo = billBase.getBail();
			ContractBailFactory.getLocalInstance(ctx).save(bailInfo);//插入bail的id
			getController(ctx).save(coreBaseInfo);  
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
	
	
	/**
	 * 判断 数字型的数据是否合法  必填项是否为空、 数据的长度是不是过长   的方法  
	 * name 中文名字 value 值  b是否必填 iv整数位 fv小数位
	 */
	private void bdValueFormat(String name,String value,boolean b,int iv,int fv) throws TaskExternalException{
		if(null != value && !"".equals(value.trim()) ){
			if(!value.matches("([1-9]\\d{0,"+iv+"}(.)\\d{0,"+fv+"})|(0(.)\\d{0,"+fv+"})|([0-9]\\d{0,"+iv+"})")){
				FDCTransmissionHelper.isThrow(name,getResource(contx,"mustB")+iv+getResource(contx,"iNumber")+fv+getResource(contx,"fNumber") );
    		}
		}else{
			if(b){//为空的情况  但是是必填的字段
				FDCTransmissionHelper.isThrow(name,getResource(contx,"Import_CanNotNull"));//不能为空
			}
		}
	}
	
	

	//返回视图
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	/**
	 * 得到资源文件
	 * @author 郑杰元
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
