package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.Supplier;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.ChangeReasonFactory;
import com.kingdee.eas.fdc.basedata.ChangeReasonInfo;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.ChangeTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonFactory;
import com.kingdee.eas.fdc.basedata.InvalidCostReasonInfo;
import com.kingdee.eas.fdc.basedata.JobTypeFactory;
import com.kingdee.eas.fdc.basedata.JobTypeInfo;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeFactory;
import com.kingdee.eas.fdc.basedata.SpecialtyTypeInfo;
import com.kingdee.eas.fdc.basedata.VisaTypeFactory;
import com.kingdee.eas.fdc.basedata.VisaTypeInfo;
import com.kingdee.eas.fdc.contract.ChangeAuditBillFactory;
import com.kingdee.eas.fdc.contract.ChangeAuditBillInfo;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryInfo;
import com.kingdee.eas.fdc.contract.ChangeUrgentDegreeEnum;
import com.kingdee.eas.fdc.contract.ContractBailFactory;
import com.kingdee.eas.fdc.contract.ContractBailInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.GraphCountEnum;
import com.kingdee.eas.fdc.contract.OfferEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.DateTimeUtils;

public class ContractChangeFullImport extends AbstractDataTransmission {

	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	private Context ctxt = null;
	
	protected ICoreBase getController(Context ctx)
			throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = ContractChangeBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	public CoreBaseInfo transmit(Hashtable htable, Context ctx)
			throws TaskExternalException {
		this.ctxt = ctx;
		ContractChangeBillInfo info = new ContractChangeBillInfo();
		//从excel中取值
		String FOrgUnit_number = getFieldValue(htable,"FOrgUnit_number");//组织长编码
		String FCurProject_longNumber = getFieldValue(htable, "FCurProject_longNumber");//工程项目长编码
		String FContractBillNumber = getFieldValue(htable,"FContractBillNumber");//合同编码
		String FNumber = getFieldValue(htable,"FNumber");//单据编号
		String FName = getFieldValue(htable,"FName");//单据名称
		String FState = getFieldValue(htable,"FState");//单据状态
		String FBizDate = getFieldValue(htable,"FBizDate");//业务日期
		
		String FChangeType_name_l2 = getFieldValue(htable,"FChangeType_name_l2");//变更类型
		String FChangeReason_name_l2 = getFieldValue(htable,"FChangeReason_name_l2");//签证原因
		String FJobType_name_l2 = getFieldValue(htable,"FJobType_name_l2");//承包类型
		String FChangeSubject = getFieldValue(htable,"FChangeSubject");//变更主题
		String FSpecialtyType_name_l2 = getFieldValue(htable,"FSpecialtyType_name_l2");//专业类型
		String FConductDept_name_l2 = getFieldValue(htable,"FConductDept_name_l2");//提出部门
		String FUrgentDegree = getFieldValue(htable,"FUrgentDegree");//紧急程度
		String FGraphCount = getFieldValue(htable,"FGraphCount");//附图情况
		String FConductUnit_name_l2 = getFieldValue(htable,"FConductUnit_name_l2");//提出单位
		String FOffer = getFieldValue(htable,"FOffer");//提出方
		String FConstrUnit_name_l2 = getFieldValue(htable,"FConstrUnit_name_l2");//施工单位
		String FConstrSite = getFieldValue(htable,"FConstrSite");//施工部位
		String FVisaType_name_l2 = getFieldValue(htable,"FVisaType_name_l2");//签证类型
		String FIsImportChange = getFieldValue(htable,"FIsImportChange");//是否重大变更
		String FMainSupp_name_l2 = getFieldValue(htable,"FMainSupp_name_l2");//主送单位
		
		String FIsDeduct = getFieldValue(htable,"FIsDeduct");//是否责任扣款单位
		String FDeductAmount = getFieldValue(htable,"FDeductAmount");//扣款金额
		String FDeductReason = getFieldValue(htable,"FDeductReason");//扣款原因
		String FOriginalAmount = getFieldValue(htable,"FOriginalAmount");//预算原币金额
		String FOriBalanceAmount = getFieldValue(htable,"FOriBalanceAmount");//结算金额原币
		String FIsSureChangeAmt = getFieldValue(htable,"FIsSureChangeAmt");//是否确认变更金额
		String FChangeAuditNumber = getFieldValue(htable,"FChangeAuditNumber");//审批单据号
		
		String FDisThisTime = getFieldValue(htable,"FDisThisTime");//本次执行说明
		String FImpleCondition = getFieldValue(htable,"FImpleCondition");//未执行说明
		String FInvalidCostReason_name_l2 = getFieldValue(htable,"FInvalidCostReason_name_l2");//无效成本原因
		String FCostNouse = getFieldValue(htable,"FCostNouse");//无效成本金额
		String FOriginalContactNum = getFieldValue(htable,"FOriginalContactNum");//原始联系单号
		String FConstructPrice = getFieldValue(htable,"FConstructPrice");//施工方报审金额
		
		String FCreator_name_l2 = getFieldValue(htable,"FCreator_name_l2");//创建者
		String FCreateTime = getFieldValue(htable,"FCreateTime");//创建时间
		String FAuditor_name_l2 = getFieldValue(htable,"FAuditor_name_l2");//审核人
		String FAuditTime = getFieldValue(htable,"FAuditTime");//审核时间
		
		CurProjectInfo cpinfo = null;
		ContractBillInfo cbinfo = null;
		
		boolean bo = this.isLength(getResource(ctx,"gcxmcbm"), FCurProject_longNumber, "String", true, 40);//工程项目长编码
		
		try {
			cpinfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(getView("longnumber", FCurProject_longNumber.replace('.', '!'))).get(0);
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//成本中心名字   为了取出组织编码  取工程项目长编码对应成本中心名字
		if(this.isLength(getResource(ctx,"zzcbm"), FOrgUnit_number, "String", false, 40)){//组织编码
			FullOrgUnitInfo objInfo = null;
			if (cpinfo == null) {// 工程项目长编码在系统中不存在
				this.isThrow("", getResource(ctx, "gcxmcbmzxtzbcz"));
			}
			String ccouid = cpinfo.getCostCenter().getId().toString();
			CostCenterOrgUnitInfo ccouinfo = null;
			try {// 成本中心对象
				ccouinfo = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("id", ccouid)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			String costCenterLongNumber = ccouinfo.getLongNumber();
			if (FOrgUnit_number.trim().equals("") || FOrgUnit_number == null) {// 为空的时候
				// 强转工程对象-》组织编码
				objInfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();
			} else {// 不为空的时候
				if (!FOrgUnit_number.replace('.', '!').equals(costCenterLongNumber)) {// 填写有误
					this.isThrow(getResource(ctx, "Import_FOrgUnitLongNumberNotE"), "");
				} else {// 没有填写错误 //强转工程对象-》组织编码
					objInfo = cpinfo.getCostCenter().castToFullOrgUnitInfo();
				}
			}
		    info.setOrgUnit(objInfo);
	    }
		
		if (bo) {// 工程项目长编码
			if(cpinfo!=null){
				info.setCurProject(cpinfo);
				// 将工程项目名字方入 不然系统中又看不到
				info.setCurProjectName(cpinfo.getName());
			} else {// 工程项目长编码在系统中不存在
				this.isThrow("", getResource(ctx, "gcxmcbmzxtzbcz"));
			}
		}
		if(this.isLength(getResource(ctx, "gcxmcbmzxtzbcz"), FContractBillNumber, "String", true, 40)){//合同编码
			try {
				cbinfo = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(getView("Number", FContractBillNumber)).get(0);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(cbinfo!=null){
				info.setContractBillNumber(FContractBillNumber);
				info.setContractBill(cbinfo);
				// 放入币别 不然又看不到
				info.setCurrency(cbinfo.getCurrency());
			}else{
				this.isThrow("", getResource(ctx, "htbmzxtzbcz"));// 合同编码不存在
			}
		}
		if(this.isLength(getResource(ctx, "danjubianhao"), FNumber, "String", true, 80)){//单据编号
			ContractChangeBillInfo ccbinfo = null;
			try {
				ccbinfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(getView("Number", FNumber)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			} 
			if(ccbinfo!=null){
				this.isThrow("",getResource(ctx, "CanNotRepeat"));
			}else{
				info.setNumber(FNumber);
			}
		}
		if(this.isLength(getResource(ctx, "danjumingcheng"), FName, "String", true, 80)){//单据名称
			ContractChangeBillInfo ccbinfo = null;
			try {
				ccbinfo = ContractChangeBillFactory.getLocalInstance(ctx).getContractChangeBillCollection(getView("name", FName)).get(0);	
			} catch (BOSException e) {
				e.printStackTrace();
			} 
			if (ccbinfo != null) {// 单据名称重复
				this.isThrow("", getResource(ctx, "djmccf"));
			}else{
				info.setName(FName);
			}
		}
		if(this.isLength(getResource(ctx, "danjuzhaungtai"), FState, "String", false, 40)){//单据状态
			String eunms = FState.trim();
			//状态定义：保存、提交、已审批、已下发、已签证、已结算。默认已审批
			//5CANCEL-----------终止; 4AUDITTED-----------已审批; 2SUBMITTED-----------已提交; 
			//9INVALID-----------作废; 8VISA-----------已签证; 3AUDITTING-----------审批中; 
			//7ANNOUNCE-----------已下发; 1SAVED-----------保存;
			if(eunms.equals(getResource(ctx, "saved"))){
				eunms = "1SAVED";
			}else if(eunms.equals(getResource(ctx, "submitted"))){
				eunms = "2SUBMITTED";
			}else if(eunms.equals(getResource(ctx, "announce"))){
				eunms = "7ANNOUNCE";
			}else if(eunms.equals(getResource(ctx, "visa"))){
				eunms = "8VISA";
			}else{
				eunms = "4AUDITTED";
			}
			
			FDCBillStateEnum enumValue = FDCBillStateEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setState(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightFState"));
			}		
		}
		if(this.isLength(getResource(ctx, "biangengleixing"), FChangeType_name_l2, "String", true, 40)){//变更类型
				ChangeTypeInfo objInfo = null;
				try {
					objInfo = ChangeTypeFactory.getLocalInstance(ctx).getChangeTypeCollection(getView("name", FChangeType_name_l2)).get(0);
			    } catch (BOSException e) {
				e.printStackTrace();
			}
				if (objInfo == null) {
					this.isThrow("", getResource(ctx, "bglxzxtzbcz"));
				}
				info.setChangeType(objInfo);
				// 放入变更类型名字 不然又看见
				info.setChangeTypeName(objInfo.getName());
		}
		if(this.isLength(getResource(ctx, "yewuriqi"), FBizDate, "Date", true, 40)){//业务日期
			info.setBizDate(this.strToDate(FBizDate));
		}
		if(this.isLength(getResource(ctx, "qianzhengyuanyin"), FChangeReason_name_l2, "String", false, 40)){//签证原因
			ChangeReasonInfo objInfo = null;
			try {
				objInfo = ChangeReasonFactory.getLocalInstance(ctx).getChangeReasonCollection(getView("name", FChangeReason_name_l2)).get(0);
			    info.setChangeReason(objInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "chengbaoleixing"), FJobType_name_l2, "String", false, 40)){//承包类型
			JobTypeInfo objInfo = null;
			try {
				objInfo = JobTypeFactory.getLocalInstance(ctx).getJobTypeCollection(getView("name", FJobType_name_l2)).get(0);
                info.setJobType(objInfo);
                if (objInfo != null) {// 放入承包类型 名字 不然 又看见
					info.setJobTypeName(objInfo.getName());
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "biangengzhuti"), FChangeSubject, "String", false, 40)){//变更主题
			info.setChangeSubject(FChangeSubject);
		}
		if(this.isLength(getResource(ctx, "zhuanyeleixing"), FSpecialtyType_name_l2, "String", true, 40)){//专业类型
			SpecialtyTypeInfo objInfo = null;
			try {
				objInfo = SpecialtyTypeFactory.getLocalInstance(ctx).getSpecialtyTypeCollection(getView("name", FSpecialtyType_name_l2)).get(0);
				info.setSpecialtyType(objInfo);
				if (objInfo != null) {// 放入专业类型名称 不然又看不见
					info.setSpecialtyTypeName(objInfo.getName());
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "tcbm"), FConductDept_name_l2, "String", true, 40)){//提出部门
			AdminOrgUnitInfo objInfo = null;
			try {			
				objInfo = AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection(getView("name", FConductDept_name_l2)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(objInfo!=null){
				info.setConductDept(objInfo);
			} else {// 提出部门在系统中不存在
				this.isThrow(getResource(ctx, "tcbm"), getResource(ctx, "Import_NOTNULL"));
			}
		}
		if(this.isLength(getResource(ctx, "jingjichengdu"),FUrgentDegree,"String",false,40)){//紧急程度
			String eunms = FUrgentDegree.trim();
			if (eunms.equals(getResource(ctx, "urgent"))) {
				eunms = "2urgent";
			}else{
				eunms = "1normal";
			}
			
			ChangeUrgentDegreeEnum enumValue = ChangeUrgentDegreeEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setUrgentDegree(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightUrgentDegree"));
			}
		}
		if(this.isLength(getResource(ctx, "futuqingkuang"),FGraphCount,"String",false,40)){//附图情况
			String eunms = FGraphCount.trim();
			if (eunms.equals(getResource(ctx, "electfile"))) {
				eunms = "1ElectFile";
			}else if(eunms.equals(getResource(ctx, "paperfile"))){
				eunms = "2PaperFile";
			}else{
				eunms = "3NoFile";
			}
		
			GraphCountEnum enumValue = GraphCountEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setGraphCount(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightGraphCount"));
			}
		}
		if(this.isLength(getResource(ctx, "tichudanwei"), FConductUnit_name_l2, "String", false, 40)){//提出单位
			SupplierInfo objInfo = null;
			try {			
				objInfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(getView("name", FConductUnit_name_l2)).get(0);
				info.setConductUnit(objInfo);
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "tichufang"), FOffer, "String", false, 40)){//提出方
			String eunms = FOffer.trim();
			if (eunms.equals(getResource(ctx, "constrparty"))) {
				eunms = "CONSTRPARTY";
			}else if(eunms.equals(getResource(ctx, "designcom"))){
				eunms = "DESIGNCOM";
			}else if(eunms.equals(getResource(ctx, "superviser"))){
				eunms = "SUPERVISER";
			}else{ 
				eunms = "SELFCOM";//我司
			}
																																																																																																				
			OfferEnum enumValue = OfferEnum.getEnum(eunms);
			if(enumValue!=null){
				info.setOffer(enumValue);
			}else{
				this.isThrow("",getResource(ctx, "InputRightFOffer"));
			}
		}
		if(this.isLength(getResource(ctx, "shigongdanwei"), FConstrUnit_name_l2, "String", false, 40)){//施工单位
			try {	
				SupplierInfo objInfo = null;
				objInfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(getView("name", FConstrUnit_name_l2)).get(0);
			    info.setConstrUnit(objInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(this.isLength(getResource(ctx, "shigongbuwei"), FConstrSite, "String", false, 300)){//施工部位
			info.setConstrSite(FConstrSite);	
		}
		if(this.isLength(getResource(ctx, ""), FVisaType_name_l2, "String", true, 40)){//签证类型
			VisaTypeInfo objInfo = null;
			try {
				objInfo = VisaTypeFactory.getLocalInstance(ctx).getVisaTypeCollection(getView("name", FVisaType_name_l2)).get(0);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			if(objInfo!=null){
				info.setVisaType(objInfo);
			} else {// 签证类型在系统中不存在
				this.isThrow("", getResource(ctx, "qzlxzxtzbcz"));
			}
		}
		if(this.isBoolean(getResource(ctx, "shifouzhongdabiangeng"),FIsImportChange,false,getResource(ctx, "yes"),getResource(ctx, "no"),-1)){  //是否重大变更
			FIsImportChange = FIsImportChange.trim();
			if(FIsImportChange.equals("true")){
				FIsImportChange = "true";
			}else if(FIsImportChange.equals("false")){
				FIsImportChange = "false";
			}else if(FIsImportChange.equals("") || FIsImportChange==null){
				FIsImportChange = "false";
			}
			
			info.setIsImportChange(this.strToBoolean(FIsImportChange,"true"));
		}
		if(this.isLength(getResource(ctx, "zhusongdanwei"), FMainSupp_name_l2, "String", true, 40)){//true//主送单位
			SupplierInfo objInfo = null;
			try {
				objInfo = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(getView("name", FMainSupp_name_l2)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			} 
			if(objInfo!=null){
				info.setMainSupp(objInfo);
			} else {// 主送单位在系统中不存在
				this.isThrow("", getResource(ctx, "zsdwzxtzbcz"));
			}
		}
		if(this.isBoolean(getResource(ctx, "shifouzerenkoukuandanwei"),FIsDeduct,false,getResource(ctx, "yes"),getResource(ctx, "no"),-1)){//是否责任扣款单位
			FIsDeduct = FIsDeduct.trim();
			if (FIsDeduct.equals(getResource(ctx, "yes"))) {
				FIsDeduct = "true";
			} else {
				FIsDeduct = "false";
			}
			info.setIsDeduct(this.strToBoolean(FIsDeduct,"true"));
		}
		if(this.isLength(getResource(ctx, "koukuanjine"), FDeductAmount, "Double", false, -1)){//扣款金额
			if(FDeductAmount.trim().equals("") || FDeductAmount==null){
				FDeductAmount = "0";
			}
			info.setDeductAmount(this.strToBigDecimal(FDeductAmount));
		}
		if(this.isLength(getResource(ctx, "koukuanyuanyin"), FDeductReason, "String", false, 40)){//扣款原因
			info.setDeductReason(FDeductReason);
		}
		if(this.isLength(getResource(ctx, "yusuanyuanbijine"), FOriginalAmount, "Double", true, -1)){//预算原币金额
			info.setOriginalAmount(this.strToBigDecimal(FOriginalAmount));
		}
		if(this.isLength(getResource(ctx, "jiesuanyuanbijine"), FOriBalanceAmount, "Double", false, -1)){//结算金额原币
			info.setOriBalanceAmount(this.strToBigDecimal(FOriBalanceAmount));
		}
		if(this.isBoolean(getResource(ctx, "shifouquerenbiangengjine"),FIsSureChangeAmt,false,getResource(ctx, "yes"),getResource(ctx, "no"),-1)){//是否确认变更金额
			FIsSureChangeAmt = FIsSureChangeAmt.trim();
			if (FIsSureChangeAmt.equals(getResource(ctx, "yes"))) {
				FIsSureChangeAmt = "true";
			} else {
				FIsSureChangeAmt = "false";
			}
			info.setIsSureChangeAmt(this.strToBoolean(FIsSureChangeAmt,"true"));
		}
		if(this.isLength(getResource(ctx, "shenpidanjuhao"), FChangeAuditNumber, "String", false, 40)){//审批单据号
			info.setChangeAuditNumber(FChangeAuditNumber);
			ChangeAuditBillInfo objinfo = null;
			if (!FChangeAuditNumber.equals("")) {
				try {
					objinfo = ChangeAuditBillFactory.getLocalInstance(ctx).getChangeAuditBillCollection(getView("number", FChangeAuditNumber))
							.get(0);
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
			if (objinfo != null) {
				// 放入变更审批单对象 不然界面上看不到
				info.setChangeAudit(objinfo);
			}	
		}

		if(this.isLength("bencizhixingqingkuang", FDisThisTime, "String", false, 40)){//本次执行说明
			info.setDisThisTime(FDisThisTime);
		}
		if(this.isLength(getResource(ctx, "weizhixingqingkuang"), FImpleCondition, "String", false, 40)){//未执行说明
			info.setImpleCondition(FImpleCondition);
		}
		if(this.isLength(getResource(ctx, "wuxiaochengbenyuanyin"), FInvalidCostReason_name_l2, "String", false, 40)){//无效成本原因
			try {
				InvalidCostReasonInfo objinfo = null;
				objinfo = InvalidCostReasonFactory.getLocalInstance(ctx).getInvalidCostReasonCollection(getView("name", FInvalidCostReason_name_l2)).get(0);
				info.setInvalidCostReason(objinfo);
			} catch (BOSException e) {
				e.printStackTrace();
			} 
		}
		if(this.isLength(getResource(ctx, "wuxiaochengbenjine"), FCostNouse, "Double", false, -1)){	//无效成本金额
			if(FCostNouse.trim().equals("") || FCostNouse==null){
				FCostNouse = "0";
			}
		    info.setCostNouse(this.strToBigDecimal(FCostNouse));
		}
		if(this.isLength(getResource(ctx, "yuanxilianxidanhao"), FOriginalContactNum, "String", false, 50)){	//原始联系单号
			//与变更审批单中的原始联系单号保持一致，否则，取变更审批单中的原始联系单号
			//先通过合同cbinfo的id 在变更审批单分录ChangeSupplierEntryInfo 中拿到分录的信息    然后拿到联系单号
			ChangeSupplierEntryInfo cspinfo = null;   
			try {
				cspinfo = ChangeSupplierEntryFactory.getLocalInstance(ctx).getChangeSupplierEntryCollection(getView("contractBill", cbinfo.getId().toString())).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if (cspinfo != null) {
				info.setOriginalContactNum(cspinfo.getOriginalContactNum());
			}
			
		}
		if(this.isLength(getResource(ctx, "shigongfangbaoshenjine"), FConstructPrice, "Double", false, -1)){//施工方报审金额
			if(FConstructPrice.trim().equals("") || FConstructPrice==null){
				FConstructPrice = "0";
			}
		    info.setConstructPrice(this.strToBigDecimal(FConstructPrice));
		}
		if(this.isLength(getResource(ctx, "zhidanrbianma"), FCreator_name_l2, "String", true, 40)){//制单人编码
			UserInfo objinfo = null;
			try {
				objinfo = UserFactory.getLocalInstance(ctx).getUserCollection(getView("number", FCreator_name_l2)).get(0);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			if(objinfo==null){
				this.isThrow(getResource(ctx, "Import_fCreatorNameL21"), getResource(ctx, "Import_NOTNULL"));
			}
			info.setCreator(objinfo);
		}
		if(this.isLength(getResource(ctx, "zhidanriqi"), FCreateTime, "Date", true, -1)){	//制单日期
			Timestamp tt = Timestamp.valueOf(FCreateTime+" 00:00:00.0");
			info.setCreateTime(tt);
			// 放入提出日期 界面上能显示
			info.setConductTime(this.strToDate(FCreateTime));// 提出日期和制单日期相等
		}
		if (FState.equals(getResource(ctx, "auditted")) || FState.equals(getResource(ctx, "announce")) || FState.equals(getResource(ctx, "visa")) || FState.equals("")) {
			if(this.isLength(getResource(ctx, "shenherbianma"), FAuditor_name_l2, "String", true, 40)){//审核人编码	
				UserInfo objinfo = null;
				try {
					objinfo = UserFactory.getLocalInstance(ctx).getUserCollection(getView("number", FAuditor_name_l2)).get(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(objinfo==null){
					this.isThrow(getResource(ctx, "Import_fAuditorNameL21"), getResource(ctx, "Import_NOTNULL"));
				}
				info.setAuditor(objinfo);
			}
			if(this.isLength(getResource(ctx, "shenheshijian"), FAuditTime, "Date", true, -1)){//审核时间	
				info.setAuditTime(this.strToDate(FAuditTime));
			}
		}else if(FState.equals(getResource(ctx, "submitted"))||FState.equals(getResource(ctx, "saved"))){
			this.isLength(getResource(ctx, "shenherbianma"), FAuditor_name_l2, "String", false, 40);//审核人编码
			this.isLength(getResource(ctx, "shenheshijian"), FAuditTime, "Date", false, -1);//审核时间
		}
		
		
		
		return info;
	}
	

	/**
	 * 从Hashtable中提取字段值。
	 */
	protected String getFieldValue(Hashtable htable, String key) {
		String str = ((String) ((DataToken) htable.get(key)).data).trim();
		if(str==null || str.equals("")){
		    str = "";	
		}
		return str;
	}
	
	/**
	 * 判断取到的数的格式是否合法 、 是否为空、 长度是不是过长   的方法  
	 * 1.数据类型   分为 String字符  Double数字  Data日期
	 * 2.必填但是为空的字段需要 提示用户
	 * 3.长度超出也必须提示用户 
	 * 
	 * chinaName-字段的中文名字         
	 * value-字段的名字     
	 * dataFormat-字段的数据类型
	 * b 不需要判断为必填的字段
	 * length-字段最大的长度    -1的时候不判断         
	 */
    private boolean isLength(String chinaName,String value,String dataFormat,boolean b,int length) throws TaskExternalException{
    	boolean isTrue = false ;
    	
    	if(dataFormat.equals("String")){//字符的情况
    		if(null != value && !"".equals(value.trim())){
    			if(value.trim().length() <= length){
    				isTrue = true ;
    			}else{
					this.isThrow(chinaName,getResource(ctxt, "Import_NumberCanNotsurpass")+length+getResource(ctxt, "Char"));
    			}
    		}else{
    			if(b){//为空的情况  但是是必填的字段
    				this.isThrow(chinaName,getResource(ctxt, "Import_CanNotNull"));	
    			}else{
    				isTrue = true ;
    			}
    		}
    		
    	}else if(dataFormat.equals("Double")){//数据的情况
			if(null != value && !"".equals(value.trim()) ){
				if(value.matches("([1-9]\\d{0,16}(.)\\d{0,2})|(0(.)\\d{0,2})")){
        			isTrue = true ;
        		}else{
        			this.isThrow(chinaName,getResource(ctxt, "Import_MustIntegerOrDecimal"));
        		}
			}else{
				if(b){//为空的情况  但是是必填的字段
    				this.isThrow(chinaName,getResource(ctxt, "Import_CanNotNull"));	
    			}else{
    				isTrue = true ;
    			}
			}
	
    	}else if(dataFormat.equals("Date")){//日期的情况 
    		if(null != value && !"".equals(value.trim()) ){
				if(value.matches("([1-9]\\d{3})-(0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-9]|3[0-1])")){
					String[] strlen = value.split("-");//分离字符串  取得年月日
					int yearInt = Integer.parseInt(strlen[0]);
					int monthInt = Integer.parseInt(strlen[1]);
					int dayInt = Integer.parseInt(strlen[2]);
					if((yearInt%400==0 || (yearInt%4==0 && yearInt%100!=0)) && monthInt==2 && dayInt>=30 ){//如果是润年
						this.isThrow(chinaName+getResource(ctxt, "writteddate")+value+getResource(ctxt, "isnotdate"),getResource(ctxt, "month3")+dayInt+getResource(ctxt, "day"));
					}else if(yearInt%4!=0 && monthInt==2 && dayInt>=29){//不是润年
						this.isThrow(chinaName+getResource(ctxt, "writteddate")+value+getResource(ctxt, "isnotdate"),getResource(ctxt, "month2")+dayInt+getResource(ctxt, "day"));
					}
					if((monthInt==4 || monthInt==6 || monthInt==9 || monthInt==11) && dayInt==31){
						this.isThrow(chinaName+getResource(ctxt, "writteddate")+value+getResource(ctxt, "isnotdate"),monthInt+getResource(ctxt, "month1")+dayInt+getResource(ctxt, "day"));
					}
					
        			isTrue = true ;
        		}else{
        			this.isThrow(chinaName,getResource(ctxt, "Import_RiQiGeShi"));
        		}

			}else{
				if(b){//为空的情况  但是是必填的字段
    				this.isThrow(chinaName,getResource(ctxt, "Import_CanNotNull"));	
    			}else{
    				isTrue = true ;
    			}
			}
    	}
    	
		return isTrue ;
	}
    
    /**
     * 判断boolean 类型的值
     * 中文名字，值，是否必填，ture的值，false的值  判断的长度
     * 判断的长度为-1的时候 不需要再判断是不是长度超出了
     */
    private boolean isBoolean(String chinaName,String value,boolean b,String tValue,String fValue,int length) throws TaskExternalException{

    	boolean isboolean = false;
        
		//填写了模板中  给定的值
		if(value.trim().equals(tValue) || value.trim().equals(fValue)){
			if(length==-1){
				isboolean = true;	
			}else{
			    if(value.length()>length){
			    	this.isThrow(chinaName,getResource(ctxt, "Import_NumberCanNotsurpass")+length+getResource(ctxt, "Char"));	
			    }else{
			    	isboolean = true;	
			    }	
			}
	    }else if(value.trim().equals("") || value==null){
	    	isboolean = true;	
	    }else{//填写了该项目  但不是给定的值
	    	this.isThrow(chinaName,getResource(ctxt, "only")+tValue+getResource(ctxt, "or")+fValue+"！");	
	    }
    
    	return isboolean;
    }
    
    /**
     * 抛出异常的方法
     */
    private void isThrow(String value, String isAlert)throws TaskExternalException{
		throw new TaskExternalException(value.trim() + " " + isAlert);
	}
    
    /**
	 * 描述：String转换为日期，String格式要求为：yyyy-mm-dd，时间清0
	 */
	private Date strToDate(String str) {
		String[] strArray = str.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, Integer.parseInt(strArray[0]));
		cal.set(Calendar.MONTH, Integer.parseInt(strArray[1])-1);
		cal.set(Calendar.DATE, Integer.parseInt(strArray[2]));

		return DateTimeUtils.truncateDate(cal.getTime());
	}
	
	/**
	 * 将String  转换成boolean 类型 
	 * str是将要进行转换的值， trueValue是str为 真的时候  的值
	 */
	private boolean strToBoolean(String str,String trueValue){
		if(str.trim().equals(trueValue)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 将String  转换成BigDecimal 类型 
	 * str是将要进行转换的值， trueValue是str为 真的时候  的值
	 */
	private BigDecimal strToBigDecimal(String str){
		if(str==null || str.trim().equals(""))
			return FDCHelper.toBigDecimal(str, 2);
		else	
		    return FDCHelper.toBigDecimal(str);
	}
	/**
	 * 得到资源文件
	 * @author 朱俊
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	//返回视图
	private EntityViewInfo getView(String sqlcolnum,Object item){
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof ContractChangeBillInfo == false) {
			return;
		}	
		try {
			getController(ctx).save(coreBaseInfo);  
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
}
