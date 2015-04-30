package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeAuxFactory;
import com.kingdee.eas.basedata.assistant.ExchangeAuxInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateFactory;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

public class ContractSettlementBillTransmission extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.contract.ContractTransResource";
	
	private static UserInfo userInfo1 = null;
	
	private static UserInfo userInfo2 = null;
	
	private static CurProjectInfo curprojectInfo = null;
	
	protected ICoreBase getController(Context ctx)throws TaskExternalException {
		try {
			return ContractSettlementBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx) throws TaskExternalException {
		
		ContractSettlementBillInfo info = null;
		
		//组织编码
		String FOrgUnitLongNumber=(String) ((DataToken) hsData.get("FOrgUnit_longNumber")).data;
		//工程项目编码
		String fCurProjectLongNumber=(String) ((DataToken) hsData.get("FCurProject_longNumber")).data;
		//合同编码
		String fContractBillCodingNumber=(String) ((DataToken) hsData.get("FContractBill_codingNumber")).data;
        //编码
		String fNumber=(String) ((DataToken) hsData.get("FNumber")).data;
		//名称
		String fName=(String) ((DataToken) hsData.get("FName")).data;
		//状态
		String fState=(String) ((DataToken) hsData.get("FState")).data;
		//币别编码
		String fCurrencyNumber=(String) ((DataToken) hsData.get("FCurrency_number")).data;
		//业务日期
		String fBizDate=(String) ((DataToken) hsData.get("FBizDate")).data;
		//原币金额
		String fOriginalAmount=(String) ((DataToken) hsData.get("FOriginalAmount")).data;
		//汇率
		String fExchangeRate=(String) ((DataToken) hsData.get("FExchangeRate")).data;
		//本位币金额
		String fAmount=(String) ((DataToken) hsData.get("FAmount")).data;
		//建筑面积
		String fBuildArea=(String) ((DataToken) hsData.get("FBuildArea")).data;
		//取费标准
		String fGetFeeCriteria=(String) ((DataToken) hsData.get("FGetFeeCriteria")).data;
		//单位造价
		String fUnitPrice=(String) ((DataToken) hsData.get("FUnitPrice")).data;
		//信息价
		String fInfoPrice=(String) ((DataToken) hsData.get("FInfoPrice")).data;
		//保修金比例
		String fQualityGuaranteRate=(String) ((DataToken) hsData.get("FQualityGuaranteRate")).data;
		//保修金
		String fGuaranteAmt=(String) ((DataToken) hsData.get("FGuaranteAmt")).data;
		//保修期限
		String fQualityTime=(String) ((DataToken) hsData.get("FQualityTime")).data;
		//是否最终结算
		String fIsFinalSettle=(String) ((DataToken) hsData.get("FIsFinalSettle")).data;
		//制单人编码
		String fCreatorNumber=(String) ((DataToken) hsData.get("FCreator_number")).data;
		//制单时间
		String fCreateTime=(String) ((DataToken) hsData.get("FCreateTime")).data;
		//审批人编码
		String fAuditorNumber=(String) ((DataToken) hsData.get("FAuditor_number")).data;
		//审核时间
		String fAuditTime=(String) ((DataToken) hsData.get("FAuditTime")).data;

		/*
		 * 判断是否为空
		 */
		if (StringUtils.isEmpty(fCurProjectLongNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fContractBillCodingNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractCodingNumberNotNull"));
		}
		if (StringUtils.isEmpty(fNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumberNotNull"));
		}
		if (StringUtils.isEmpty(fName)) {
			throw new TaskExternalException(getResource(ctx,"Import_fNameNotNull"));
		}
		if (StringUtils.isEmpty(fOriginalAmount)) {
			throw new TaskExternalException(getResource(ctx,"Import_fOriginalAmountNotNull"));
		}
		if (StringUtils.isEmpty(fBizDate)) {
			throw new TaskExternalException(getResource(ctx,"Import_fBizDateNotNull"));
		}		
		if (StringUtils.isEmpty(fCreatorNumber)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreatorNameL2NotNull"));
		}
		if (StringUtils.isEmpty(fCreateTime)) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreateTime"));
		}
		
		/*
		 * 判断字符长度
		 */
		if (FOrgUnitLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCostCenterNumber"));
		}
		if (fCurProjectLongNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber"));
		}
		if (fContractBillCodingNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fContractCodingNumber"));
		}
		if (fNumber.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fNumber"));
		}
		if (fName.length() > 80) {
			throw new TaskExternalException(getResource(ctx,"Import_fName"));
		}
		if (fState.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fStateLen40"));
		}
		if (fCurrencyNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCurrencyNumber"));
		}
		if (fGetFeeCriteria.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fGetFeeCriteriaLen40"));
		}
		if (fInfoPrice.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fInfoPriceLen40"));
		}
		if (fQualityTime.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fQualityTimeLen40"));
		}
		if (fCreatorNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fCreatorNameL2"));
		}
		if (fAuditorNumber.length() > 40) {
			throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL2"));
		}
		//单据编码重复判断
		try {
			FilterInfo CSBfilter = new FilterInfo();
			CSBfilter.getFilterItems().add(new FilterItemInfo("number", fNumber));
			EntityViewInfo CSBview = new EntityViewInfo();
			CSBview.setFilter(CSBfilter);
			ContractSettlementBillCollection csbcollection;
			csbcollection = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(CSBview);
			if(csbcollection.size() == 0){
				info = new ContractSettlementBillInfo();
			}else{
				throw new TaskExternalException(getResource(ctx,"ContractBillImport_fCodingNumber"));
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		//单据名称重复判断
		try {
			FilterInfo CSBfilter = new FilterInfo();
			CSBfilter.getFilterItems().add(new FilterItemInfo("name", fName));
			EntityViewInfo CSBview = new EntityViewInfo();
			CSBview.setFilter(CSBfilter);
			ContractSettlementBillCollection csbcollection;
			csbcollection = ContractSettlementBillFactory.getLocalInstance(ctx).getContractSettlementBillCollection(CSBview);
			if(csbcollection.size() == 0){
				info = new ContractSettlementBillInfo();
			}else{
				throw new TaskExternalException(getResource(ctx,"ContractBillImport_fName"));
			}
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		/*
		 * 枚举判断
		 */
		//状态
		if(fState.trim().equals(getResource(ctx,"baocun"))){
			info.setState(FDCBillStateEnum.SAVED);
		}else if(fState.trim().equals(getResource(ctx,"yitijiao"))){
			info.setState(FDCBillStateEnum.SUBMITTED);
		}else{
			//默认为已审批状态
			info.setState(FDCBillStateEnum.AUDITTED);
		}
		
		//数值判断
		// 正则表达式 验证输入的是否为数字型参数：    "^\\d+(\\.\\d+)?$"
		//原币金额  	 	
		if(!fOriginalAmount.matches("^\\d+(\\.\\d+)?$")){
			// "必须以 1－19 位整数或加 1－4位小数构成！"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfOriginalAmount"));
		}
		//汇率
		if(!(StringUtils.isEmpty(fExchangeRate))&&(FDCTransmissionHelper.isRangedInHundred(fExchangeRate) == null)){
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfExchangeRate"));
	 	}
		//本位币金额
		if((!StringUtils.isEmpty(fAmount))&&(!fAmount.matches("^\\d+(\\.\\d+)?$")))
				throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfAmount"));
		//建筑面积
		if((!StringUtils.isEmpty(fBuildArea))&&(!fBuildArea.matches("^\\d+(\\.\\d+)?$"))){
			// "必须以 1－19 位整数或加 1－4位小数构成！"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfBuildArea"));
		}
		//单位造价
		if((!StringUtils.isEmpty(fUnitPrice))&&(!fUnitPrice.matches("^\\d+(\\.\\d+)?$"))){
			// "必须以 1－19 位整数或加 1－4位小数构成！"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfUnitPrice"));
		}
		//保修金比例
		if((!StringUtils.isEmpty(fQualityGuaranteRate))&&(!fQualityGuaranteRate.matches("^\\d+(\\.\\d+)?$"))){
			// "必须以 1－19 位整数或加 1－4位小数构成！"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfQualityGuaranteRate"));
		}
		//保修金
		if((!StringUtils.isEmpty(fGuaranteAmt))&&(!fGuaranteAmt.matches("^\\d+(\\.\\d+)?$"))){
			// "必须以 1－19 位整数或加 1－4位小数构成！"
			throw new TaskExternalException(getResource(ctx, "Import_strTOnumberfGuaranteAmt"));
		}
		
		/*
		 * 布尔判断
		 */
		//是否最终结算
		if(fIsFinalSettle.trim().equals(getResource(ctx,"No"))){
			info.setIsFinalSettle(BooleanEnum.FALSE);
		}else{
			info.setIsFinalSettle(BooleanEnum.TRUE);
		}
		try {
			//币种编码 
			CurrencyInfo currinfo = null;
			currinfo= CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection(this.getView("number", fCurrencyNumber)).get(0);
			//币种为空的时候去本位币 
			if (currinfo == null) {
				currinfo = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='人民币'").get(0);
				if(currinfo==null){
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCurrency_number")+ fCurrencyNumber + getResource(ctx,"ContractBillImport_FY"));
				}	
			}
			info.setCurrency(currinfo);
			
			//汇率	
			CurrencyInfo ci = null;//用于找本位币
			EntityViewInfo rateView = new EntityViewInfo();
			FilterInfo rateFilter = new FilterInfo();
			ExchangeRateInfo erinfo = null;//汇率对象
			BigDecimal bgm = null;//汇率的值
			//如果汇率为空，则根据币别获取当前汇率；
			if (fExchangeRate==null || fExchangeRate.trim().equals("")) {
				//本位币对象ci        //用户填写的币种-原币对象 currinfo
				ci = CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("where name='人民币'").get(0);
				if (ci == null) {//ci是本位币哈   找不到本位币
					FDCTransmissionHelper.isThrow(getResource(ctx,"ContractBillImport_FCanotRMB"));
				}else{//找到本位币的情况
					if(ci.getName().trim().equals(currinfo.getName().trim())){//都是人民币的情况
						bgm = new BigDecimal(1.0000);
					} else {
						rateFilter.getFilterItems().add(new FilterItemInfo("targetCurrency",ci.getId(),CompareType.EQUALS));//目标币本位币
						rateFilter.getFilterItems().add(new FilterItemInfo("SourceCurrency", currinfo.getId(), CompareType.EQUALS));//用户填写的原币
						rateFilter.setMaskString("#0 and #1");
						rateView.setFilter(rateFilter);
						ExchangeAuxInfo xinfo = ExchangeAuxFactory.getLocalInstance(ctx).getExchangeAuxCollection(rateView).get(0);
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
			} else {
				bgm = FDCTransmissionHelper.strToBigDecimal(fExchangeRate);
			}
			info.setExchangeRate(bgm);
			
			//本位币金额  如果为0，那么根据金额*汇率计算
			if (fAmount == null || FDCTransmissionHelper.strToDouble(fAmount) == 0) {
				// 本位币金额 = 原币金额*汇率
				info.setAmount(FDCHelper.multiply(new BigDecimal(fOriginalAmount), bgm));
				// 当前本位币结算价
				info.setCurSettlePrice(FDCHelper.multiply(new BigDecimal(fOriginalAmount), bgm));
			}else{
				// 本位币金额
				info.setAmount(new BigDecimal(fAmount));
				// 当前本位币结算价
				info.setCurSettlePrice(new BigDecimal(fAmount));
			}		
			
			/*
			 * 找不到提示
			 */
			//工程项目编码
			FilterInfo curFilter = new FilterInfo();
			curFilter.getFilterItems().add(new FilterItemInfo("LongNumber", fCurProjectLongNumber.replace('.', '!')));
			EntityViewInfo curView = new EntityViewInfo();
			curView.setFilter(curFilter);
			CurProjectCollection curcoll = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(curView);
			if(curcoll.size()>0){
				curprojectInfo = curcoll.get(0);
				info.setCurProject(curprojectInfo);
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCurProjectCodingNumber1")+getResource(ctx, "Import_NOTNULL"));
			}
			//组织编码
			CostCenterOrgUnitInfo costCenterOrgUnit = curprojectInfo.getCostCenter(); // 工程项目对应成本中心组织
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", costCenterOrgUnit.getId().toString()));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			CostCenterOrgUnitCollection collection = CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(view);
			if(collection.size() > 0) {
				costCenterOrgUnit = collection.get(0);
			} 
			if(StringUtils.isEmpty(FOrgUnitLongNumber)){
				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//				info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
			}else{
				if(FOrgUnitLongNumber.trim().replace('.', '!').equals(costCenterOrgUnit.getLongNumber().toString())){
					info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
//					info.setOrgUnit(costCenterOrgUnit.castToFullOrgUnitInfo());
				}else{
					throw new TaskExternalException(getResource(ctx,"Import_FOrgUnitLongNumberNotE"));
				}
			}
			//合同编码
			ContractBillInfo contractbillInfo = null;
			FilterInfo conbfilter = new FilterInfo ();
			conbfilter.getFilterItems().add(new FilterItemInfo("number",fContractBillCodingNumber));
			EntityViewInfo conbview = new EntityViewInfo();
			conbview.setFilter(conbfilter);
			ContractBillCollection conbcoll = ContractBillFactory.getLocalInstance(ctx).getContractBillCollection(conbview);
			if(conbcoll.size()>0){
				contractbillInfo = conbcoll.get(0);
			}
			if(contractbillInfo==null){
				throw new TaskExternalException(getResource(ctx,"Import_fContractCodingNumber1")+getResource(ctx,"Import_NOTNULL"));
			}else{
				info.setContractBill(contractbillInfo);
			}
			//制单人编码
			FilterInfo user1filter = new FilterInfo();
			user1filter.getFilterItems().add(new FilterItemInfo("number",fCreatorNumber));
			EntityViewInfo user1view = new EntityViewInfo();
			user1view.setFilter(user1filter);
			UserCollection user1coll = UserFactory.getLocalInstance(ctx).getUserCollection(user1view);
			if(user1coll.size()>0){
				userInfo1 = user1coll.get(0);	
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_fCreatorNumber")+getResource(ctx,"Import_NOTNULL"));
			}
			//审批人编码，审核时间		
			if(!(fState.trim().equals(getResource(ctx,"baocun"))||fState.trim().equals(getResource(ctx,"yitijiao")))){
				if (!StringUtils.isEmpty(fAuditorNumber)){ 
					FilterInfo uesr2filter = new FilterInfo();
					uesr2filter.getFilterItems().add(new FilterItemInfo("number",fAuditorNumber));
					EntityViewInfo user2view = new EntityViewInfo();
					user2view.setFilter(uesr2filter);
					UserCollection user2coll = UserFactory.getLocalInstance(ctx).getUserCollection(user2view);
					if(user2coll.size()>0){
						userInfo2 = user2coll.get(0);	
						info.setAuditor(userInfo2);
					} else {
						throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL21")+getResource(ctx,"Import_NOTNULL"));
					}
				} else {
					throw new TaskExternalException(getResource(ctx,"Import_fAuditorNameL2NotNull"));
				}
			}	
			if(!(fState.trim().equals(getResource(ctx,"baocun"))||fState.trim().equals(getResource(ctx,"yitijiao")))){
				if (StringUtils.isEmpty(fAuditTime)) 
					throw new TaskExternalException(getResource(ctx,"Import_fAuditTime"));
				info.setAuditTime(checkDate(fAuditTime,ctx));
			}		
			checkDate(fBizDate,ctx);
			checkDate(fCreateTime,ctx);
			checkDate(fAuditTime,ctx);
			
			info.setNumber(fNumber);
			info.setName(fName);
			info.setBizDate(checkDate(fBizDate,ctx));	
			// 原币金额
			info.setOriginalAmount(new BigDecimal(fOriginalAmount));
			// 当前原币结算价
			info.setCurOriginalAmount(new BigDecimal(fOriginalAmount));
			if (!StringUtils.isEmpty(fBuildArea)) {
				info.setBuildArea(new BigDecimal(fBuildArea));
			} else {
				info.setBuildArea(new BigDecimal(0.0000));
			}
			info.setGetFeeCriteria(fGetFeeCriteria);
			if (!StringUtils.isEmpty(fUnitPrice)) {
				info.setUnitPrice(new BigDecimal(fUnitPrice));
			} else{
				info.setUnitPrice(new BigDecimal(0.0000));
			}
			info.setInfoPrice(fInfoPrice);
			if (!StringUtils.isEmpty(fQualityGuaranteRate)) {
				info.setQualityGuaranteRate(new BigDecimal(fQualityGuaranteRate));
			} else {
				info.setQualityGuaranteRate(new BigDecimal(0.0000));
			}
			if (!StringUtils.isEmpty(fGuaranteAmt)) {
				info.setGuaranteAmt(new BigDecimal(fGuaranteAmt));
			} else {
				info.setGuaranteAmt(new BigDecimal(0.0000));
			}
			info.setQualityTime(fQualityTime);	
			info.setCreator(userInfo1);
			info.setCreateTime(new Timestamp(checkDate(fCreateTime, ctx).getTime()));
	        
	        
	        
		} catch (BOSException e) {
			FDCTransmissionHelper.isThrow("",e.toString());
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	
	//日期判断
	private Date checkDate(String dateStr,Context ctx) throws TaskExternalException{
		try {
			if(StringUtils.isEmpty(dateStr)) return null;
			DateFormat df = null;
			if(dateStr.trim().length() <= "yyyy-MM-dd".length()){ // 处理 "yyyy-MM-d"
				df = new SimpleDateFormat("yyyy-MM-dd");
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm".length()){ //处理 yyyy-MM-d HH:mm情况
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm");	
			}else if(dateStr.trim().length() <= "yyyy-MM-dd  HH:mm:ss".length()){
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			}else{
				throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
			}
			df.setLenient(false);
			return df.parse(dateStr);
		} catch (ParseException e) {
//			e.printStackTrace();
			throw new TaskExternalException(getResource(ctx,"Import_DateForm"));
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
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
}
