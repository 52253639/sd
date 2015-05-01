package com.kingdee.eas.fdc.contract.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonFactory;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.cp.bc.ExpenseTypeFactory;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.PayContentTypeFactory;
import com.kingdee.eas.fdc.contract.PayContentTypeInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.UrgentDegreeEnum;
import com.kingdee.eas.fi.cas.DifPlaceEnum;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.ma.budget.BgItemFactory;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.util.ResourceBase;

public class PayRequestImport extends AbstractDataTransmission{
	private Context contx = null;
	private static String resource = "com.kingdee.eas.fdc.contract.ContractResource";
	protected ICoreBase getController(Context ctx)throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = PayRequestBillFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}
	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)throws TaskExternalException {
		PayRequestBillInfo  info = new PayRequestBillInfo();
		try {
			contx = ctx;
			
			info.setSourceType(SourceTypeEnum.ADDNEW);
			info.setHasClosed(false);
			info.setCostAmount(FDCConstants.ZERO);
			info.setIsPay(true);
			info.setPaymentProportion(FDCConstants.ONE_HUNDRED);
			info.setState(FDCBillStateEnum.SAVED);
			info.setIsBgControl(true);
			info.setUrgentDegree(UrgentDegreeEnum.NORMAL);
			info.setIsDifferPlace(DifPlaceEnum.samePlace);
			
			String FContractBase_number=FDCTransmissionHelper.getFieldValue(hsData, "FContractBase_number");
			String FNumber=FDCTransmissionHelper.getFieldValue(hsData, "FNumber");
			String FBizDate=FDCTransmissionHelper.getFieldValue(hsData, "FBizDate");
			String FPayContentType_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FPayContentType_name_l2");
			String FPayBillType_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FPayBillType_name_l2");
			String FRealSupplier_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FRealSupplier_name_l2");
			String FSettlementType_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FSettlementType_name_l2");
			String FRecBank=FDCTransmissionHelper.getFieldValue(hsData, "FRecBank");
			String FRecAccount=FDCTransmissionHelper.getFieldValue(hsData, "FRecAccount");
			String FIsInvoice=FDCTransmissionHelper.getFieldValue(hsData, "FIsInvoice");
			String FInvoiceNumber=FDCTransmissionHelper.getFieldValue(hsData, "FInvoiceNumber");
			String FInvoiceAmt=FDCTransmissionHelper.getFieldValue(hsData, "FInvoiceAmt");
			String FInvoiceDate=FDCTransmissionHelper.getFieldValue(hsData, "FInvoiceDate");
			String FUseDepartment_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FUseDepartment_name_l2");
			String FApplier_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FApplier_name_l2");
			String FCostedDept_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FCostedDept_name_l2");
			String FCostedCompany_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FCostedCompany_name_l2");
			String FDescription=FDCTransmissionHelper.getFieldValue(hsData, "FDescription");
			String FBgEntry$expenseType_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FBgEntry$expenseType_name_l2");
			String FBgEntry$bgItem_name_l2=FDCTransmissionHelper.getFieldValue(hsData, "FBgEntry$bgItem_name_l2");
			String FBgEntry_requestAmount=FDCTransmissionHelper.getFieldValue(hsData, "FBgEntry_requestAmount");
			
			FDCTransmissionHelper.valueFormat("合同编码", FContractBase_number, "String", true, 40);
			FDCTransmissionHelper.valueFormat("单据编号", FNumber, "String", true, 40);
			FDCTransmissionHelper.valueFormat("业务日期", FBizDate, "Date", true, 40);
			FDCTransmissionHelper.valueFormat("付款事项", FPayContentType_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("付款类型", FPayBillType_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("实付收款单位", FRealSupplier_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("结算方式", FSettlementType_name_l2, "String", false, 40);
			FDCTransmissionHelper.valueFormat("收款银行", FRecBank, "String", false, 40);
			FDCTransmissionHelper.valueFormat("收款账号", FRecAccount, "String", false, 40);
			FDCTransmissionHelper.valueFormat("有无发票", FIsInvoice, "String", true, 40);
			FDCTransmissionHelper.valueFormat("发票号 ", FInvoiceNumber, "String", false, 40);
			bdValueFormat("发票金额", FInvoiceAmt, false,15,4);
			FDCTransmissionHelper.valueFormat("开票日期", FInvoiceDate, "Date", false, 40);
			FDCTransmissionHelper.valueFormat("用款部门", FUseDepartment_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("申请人", FApplier_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("预算承担部门", FCostedDept_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("预算承担公司", FCostedCompany_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("备注", FDescription, "String", false, 200);
			FDCTransmissionHelper.valueFormat("费用清单_费用类别", FBgEntry$expenseType_name_l2, "String", true, 40);
			FDCTransmissionHelper.valueFormat("费用清单_预算项目", FBgEntry$bgItem_name_l2, "String", true, 40);
			bdValueFormat("费用清单_申请金额", FBgEntry_requestAmount,true,15,4);
			
			
			ContractBillInfo contract=ContractBillFactory.getLocalInstance(ctx).getContractBillCollection("select *,currency.*,curProject.*,orgUnit.* from where number='"+FContractBase_number+"'").get(0);
			if(contract==null){
				FDCTransmissionHelper.isThrow("合同编码在系统中不存在！");
			}else{
				info.setContractId(contract.getId().toString());
				info.setSource(contract.getBOSType().toString());
				info.setContractNo(contract.getNumber());
				info.setContractName(contract.getName());
				info.setContractPrice(contract.getAmount());
				info.setCurrency(contract.getCurrency());
				info.setExchangeRate(contract.getExRate());
				info.setCurProject(contract.getCurProject());
				info.setOrgUnit(contract.getOrgUnit());
			}
			info.setNumber(FNumber);
			info.setBookedDate(FDCTransmissionHelper.strToDate(FBizDate));
			
			PayContentTypeInfo pct=PayContentTypeFactory.getLocalInstance(ctx).getPayContentTypeCollection("select * from where name='"+FPayContentType_name_l2+"'").get(0);
			if(pct==null){
				FDCTransmissionHelper.isThrow("付款事项在系统中不存在！");
			}else{
				info.setPayContentType(pct);
			}
			
			PaymentTypeInfo pbt=PaymentTypeFactory.getLocalInstance(ctx).getPaymentTypeCollection("select * from where name='"+FPayBillType_name_l2+"'").get(0);
			if(pct==null){
				FDCTransmissionHelper.isThrow("付款类型在系统中不存在！");
			}else{
				info.setPaymentType(pbt);
			}
			
			SupplierInfo supplier=SupplierFactory.getLocalInstance(ctx).getSupplierCollection("select * from where name='"+FRealSupplier_name_l2+"'").get(0);
			if(pct==null){
				FDCTransmissionHelper.isThrow("实付收款单位在系统中不存在！");
			}else{
				info.setRealSupplier(supplier);
				info.setSupplier(supplier);
			}
			
			SettlementTypeInfo st=SettlementTypeFactory.getLocalInstance(ctx).getSettlementTypeCollection(this.getView("name", FSettlementType_name_l2)).get(0);
			info.setSettlementType(st);
			
			info.setRecBank(FRecBank);
			info.setRecAccount(FRecAccount);
			
			FIsInvoice= FIsInvoice.trim();
			if(FIsInvoice.equals("true")&&FIsInvoice.equals("false")){
				FDCTransmissionHelper.isThrow("有无发票必须为true或者false！");
			}
			info.setIsInvoice(FDCTransmissionHelper.strToBoolean(FIsInvoice, "true"));
			info.setInvoiceNumber(FInvoiceNumber);
			info.setInvoiceAmt(FDCTransmissionHelper.strToBigDecimal(FInvoiceAmt));
			info.setInvoiceDate(FDCTransmissionHelper.strToDate(FInvoiceDate));
			
			AdminOrgUnitInfo useDepartment=AdminOrgUnitFactory.getLocalInstance(ctx).getAdminOrgUnitCollection("select * from where name='"+FUseDepartment_name_l2+"'").get(0);
			if(useDepartment==null){
				FDCTransmissionHelper.isThrow("用款部门在系统中不存在！");
			}else{
				info.setUseDepartment(useDepartment);
			}
			
			PersonInfo applier=PersonFactory.getLocalInstance(ctx).getPersonCollection("select * from where name='"+FApplier_name_l2+"'").get(0);
			if(applier==null){
				FDCTransmissionHelper.isThrow("申请人在系统中不存在！");
			}else{
				info.setApplier(applier);
			}
			
			CostCenterOrgUnitInfo costedDept=CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitCollection(this.getView("name", FCostedDept_name_l2)).get(0);
			if(costedDept==null){
				FDCTransmissionHelper.isThrow("预算承担部门在系统中不存在！");
			}else{
				info.setCostedDept(costedDept);
			}
			
			CompanyOrgUnitInfo company=CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitCollection("select * from where name='"+FCostedCompany_name_l2+"'").get(0);
			if(company==null){
				FDCTransmissionHelper.isThrow("预算承担公司在系统中不存在！");
			}else{
				info.setCostedCompany(company);
			}
			
			info.setDescription(FDescription);

			PayRequestBillBgEntryInfo entry=new PayRequestBillBgEntryInfo();
			ExpenseTypeInfo expenseType=ExpenseTypeFactory.getLocalInstance(ctx).getExpenseTypeCollection("select * from where name='"+FBgEntry$expenseType_name_l2+"'").get(0);
			if(expenseType==null){
				FDCTransmissionHelper.isThrow("费用清单_费用类别在系统中不存在！");
			}else{
				entry.setExpenseType(expenseType);
			}
			
			
			BgItemInfo bg=BgItemFactory.getLocalInstance(ctx).getBgItemCollection("select * from where name='"+FBgEntry$bgItem_name_l2+"'").get(0);
			if(bg==null){
				FDCTransmissionHelper.isThrow("费用清单_预算项目在系统中不存在！");
			}else{
				entry.setBgItem(bg);
			}
			entry.setRequestAmount(FDCTransmissionHelper.strToBigDecimal(FBgEntry_requestAmount));
			info.getBgEntry().add(entry);
			info.setAmount(entry.getRequestAmount());
			info.setOriginalAmount(entry.getRequestAmount());
			info.setPrjAllReqAmt(entry.getRequestAmount());
			info.setProjectPriceInContract(entry.getRequestAmount());
			info.setProjectPriceInContractOri(entry.getRequestAmount());
			info.setCurPaid(entry.getRequestAmount());
			info.setCurPaidOriginal(entry.getRequestAmount());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return info;
	}
	
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
		if (coreBaseInfo == null || coreBaseInfo instanceof PayRequestBillInfo == false) {
			return;
		}
		try {
			getController(ctx).save(coreBaseInfo);  
		} catch (Exception ex) {
			throw new TaskExternalException(ex.getMessage(), ex.getCause());
		}
	}
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
	private EntityViewInfo getView(String sqlcolnum,Object item){
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(sqlcolnum,item,CompareType.EQUALS));
        view.setFilter(filter);
		return view;	
	}
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
