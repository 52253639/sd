/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.sql.SQLException;

import org.w3c.dom.Entity;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.contract.ConNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.ConNoCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractBailEntryInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.contract.ContractPayItemCollection;
import com.kingdee.eas.fdc.contract.ContractPayItemFactory;
import com.kingdee.eas.fdc.contract.ContractPayItemInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class ContractDetailFullInfoUI extends AbstractContractDetailFullInfoUI {
	public static final String resourcePath = "com.kingdee.eas.fdc.contract.client.ContractFullResource";
	
	private void loadContractBailAndPayItemData() throws BOSException, EASBizException{
		//很无语的东西，已经都绑定好了的东西，在保存的时候已经存进了数据库，加载数据的时候也取到了数据，可就是不会填充
		//到表格中来，郁闷死了！ 只能自己维护将值填到界面中去了  by Cassiel_peng  2008-9-8
		if(this.editData!=null&&this.editData.getId()!=null){
			SelectorItemCollection selector=new SelectorItemCollection();
			selector.add("bail.amount");
			selector.add("bail.prop");
			selector.add("bail.entry.bailDate");
			selector.add("bail.entry.bailConditon");
			selector.add("bail.entry.desc");
			selector.add("bail.entry.amount");
			selector.add("bail.entry.prop");
			ContractBillInfo contractBill=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(this.editData.getId()),selector);
			if(contractBill.getBail()!=null){
				if(contractBill.getBail().getAmount()!=null){
					this.txtBailOriAmount.setValue(FDCHelper.toBigDecimal(contractBill.getBail().getAmount(),2));
				}
				if(contractBill.getBail().getProp()!=null){
					this.txtBailRate.setValue(FDCHelper.toBigDecimal(contractBill.getBail().getProp(),2));
				}
				
//				this.tblBail.removeRows();
				if(contractBill.getBail().getEntry()!=null){
					for (int i = 0; i < contractBill.getBail().getEntry().size(); i++) {
						ContractBailEntryInfo entry = contractBill.getBail().getEntry().get(i);
						IRow row=tblBail.addRow();
						row.getStyleAttributes().setLocked(true);
						row.setUserObject(entry);
						row.getCell("bailAmount").setValue(FDCHelper.toBigDecimal(entry.getAmount(),2));
						row.getCell("bailRate").setValue(FDCHelper.toBigDecimal(entry.getProp(),2));
						row.getCell("bailDate").setValue(entry.getBailDate());
						row.getCell("bailCondition").setValue(entry.getBailConditon());
						row.getCell("desc").setValue(entry.getDesc());
					}
				}
			}
		}
		//这个分录如果绑定好了的话是会自动显示出来的，不需要自己维护了 
		/*if(this.editData!=null&&this.editData.getId()!=null){
			EntityViewInfo view=new EntityViewInfo();
			view.getSelector().add("*");
//			view.getSelector().add("paymentType.payType");
			view.getSelector().add("paymentType.name");
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractbill.id",this.editData.getId().toString()));
			view.setFilter(filter);
			ContractPayItemCollection payItemCollection=ContractPayItemFactory.getRemoteInstance().getContractPayItemCollection(view);
			if(payItemCollection!=null){
				//加载数据的时候实际上已经按照该合同所有的ContractPayItemInfo分录对象条数给表格添加了行,但是却没有取得实际数据并填充到表格相应的列中去
				//故在手动填充数据之前需要清除之前的行，避免显示出多行(并且还是空行)数据  
				this.tblEconItem.removeRows();
				for (int i = 0; i < payItemCollection.size(); i++) {
					ContractPayItemInfo payItem = payItemCollection.get(i);
					IRow row=tblEconItem.addRow();
					row.getStyleAttributes().setLocked(true);
					row.setUserObject(payItem);
					row.getCell("payType").setValue(payItem.getPaymentType());
					row.getCell("payAmount").setValue(FDCHelper.toBigDecimal(payItem.getAmount(),2));
					row.getCell("payRate").setValue(FDCHelper.toBigDecimal(payItem.getProp(),2));
					row.getCell("date").setValue(payItem.getPayItemDate());
					row.getCell("payCondition").setValue(payItem.getPayCondition());
					row.getCell("desc").setValue(payItem.getDesc());
				}
			}
		}*/
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.tblEconItem.getStyleAttributes().setLocked(true);
		this.tblBail.getStyleAttributes().setLocked(true);
		loadContractBailAndPayItemData();
		String contractId = (String) this.getUIContext().get(UIContext.ID);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", contractId));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);
		if (ContractCostSplitFactory.getRemoteInstance().exists(filter)) {
			ContractCostSplitInfo info = ContractCostSplitFactory
					.getRemoteInstance().getContractCostSplitCollection(view)
					.get(0);
			if(FDCHelper.isFDCDebug()){
				System.out.print("hpw: "+filter.getFilterItems()+info);
			}
			//PBG064286因没有数据权限，查询数据存在但取不到导致空值
			if (info != null) {
				CostSplitStateEnum splitState = info.getSplitState();
				this.txtSplitState.setText(splitState.getAlias());
			}
		} else if (ConNoCostSplitFactory.getRemoteInstance().exists(filter)) {
			ConNoCostSplitInfo info = ConNoCostSplitFactory.getRemoteInstance()
					.getConNoCostSplitCollection(view).get(0);
			if (info != null) {
				CostSplitStateEnum splitState = info.getSplitState();
				this.txtSplitState.setText(splitState.getAlias());
			}
		} else {
			this.txtSplitState.setText(CostSplitStateEnum.NOSPLIT.toString());
		}
		ContractBillInfo contractBillInfo = ContractBillFactory
				.getRemoteInstance().getContractBillInfo(
						new ObjectUuidPK(BOSUuid.read(contractId)));
		if (contractBillInfo!=null&&contractBillInfo.isHasSettled()) {
			this.txtSettleState.setText(this.getResouce("HasSettle"));
			ContractSettlementBillCollection settles = ContractSettlementBillFactory
					.getRemoteInstance().getContractSettlementBillCollection(
							"select id,number where contractBill.Id='"
									+ contractId + "'");
			if (settles.size() > 0) {
				ContractSettlementBillInfo info = settles.get(0);
				this.txtSettleNumber.setText(info.getNumber());
			}
		} else {
			this.txtSettleState.setText(this.getResouce("NoSettle"));
		}
		this.txtOverAmt.setValue(new Double(editData.getOverRate()));
	}

	private String getResouce(String resName) {
		return EASResource.getString(resourcePath, resName);
	}

	/**
	 * output class constructor
	 */
	public ContractDetailFullInfoUI() throws Exception {
		super();
		registerBindings();
		registerUIState();
	}

	public void loadFields() {
		detachListeners();
		super.loadFields();
		isUseAmtWithoutCost = editData.isIsAmtWithoutCost();

		loadDetailEntries();

		if (editData.getState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}

		
		txtExRate.setValue(editData.getExRate());
		txtLocalAmount.setValue(editData.getAmount());

		GlUtils.setSelectedItem(comboCurrency,editData.getCurrency());
		
		/*
		 * 列表界面选择了合同类型, 根据合同类型填充带过来的字段
		 */
		BOSUuid typeId = (BOSUuid) getUIContext().get("contractTypeId");

		if (typeId != null) {
			try {
				ContractTypeInfo contractTypeInfo = (ContractTypeInfo) prmtcontractType
						.getData();
				if (contractTypeInfo != null) {
					fillInfoByContractType(contractTypeInfo);
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if(editData != null && editData.getCurProject() != null) {
			
			String projId = editData.getCurProject().getId().toString();
			CurProjectInfo curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);
			
			txtProj.setText(curProjectInfo.getDisplayName());
			
			FullOrgUnitInfo costOrg = FDCClientUtils.getCostOrgByProj(projId);
			
			txtOrg.setText(editData.getCurProject().getFullOrgUnit().getName());
			editData.setOrgUnit(costOrg);
			editData.setCU(curProjectInfo.getCU());
		}
		setCapticalAmount();
		//加载合同结算类型
		loadContractSettleType();
		
		try {
			loadContractModel();
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		
		//加载附件
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
		try {
			loadInvite();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		try {
			loadSupplyEntry();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("originalAmount"));
        sic.add(new SelectorItemInfo("chgPercForWarn"));
        sic.add(new SelectorItemInfo("payPercForWarn"));
        sic.add(new SelectorItemInfo("signDate"));
        sic.add(new SelectorItemInfo("lowestPrice"));
        sic.add(new SelectorItemInfo("lowerPrice"));
        sic.add(new SelectorItemInfo("middlePrice"));
        sic.add(new SelectorItemInfo("higherPrice"));
        sic.add(new SelectorItemInfo("highestPrice"));
        sic.add(new SelectorItemInfo("winPrice"));
        sic.add(new SelectorItemInfo("quantity"));
        sic.add(new SelectorItemInfo("fileNo"));
        sic.add(new SelectorItemInfo("basePrice"));
        sic.add(new SelectorItemInfo("secondPrice"));
        sic.add(new SelectorItemInfo("landDeveloper.*"));
        sic.add(new SelectorItemInfo("contractType.*"));
        sic.add(new SelectorItemInfo("inviteType.*"));
        sic.add(new SelectorItemInfo("winUnit.*"));
        sic.add(new SelectorItemInfo("costProperty"));
        sic.add(new SelectorItemInfo("contractPropert"));
        sic.add(new SelectorItemInfo("partB.*"));
        sic.add(new SelectorItemInfo("partC.*"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("lowestPriceUnit.*"));
        sic.add(new SelectorItemInfo("lowerPriceUnit.*"));
        sic.add(new SelectorItemInfo("middlePriceUnit.*"));
        sic.add(new SelectorItemInfo("higherPriceUnit.*"));
        sic.add(new SelectorItemInfo("highestPriceUni.*"));
        sic.add(new SelectorItemInfo("isCoseSplit"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("exRate"));
        sic.add(new SelectorItemInfo("grtAmount"));
        sic.add(new SelectorItemInfo("remark"));
        sic.add(new SelectorItemInfo("respDept.*"));
        sic.add(new SelectorItemInfo("payScale"));
        sic.add(new SelectorItemInfo("stampTaxRate"));
        sic.add(new SelectorItemInfo("stampTaxAmt"));
        sic.add(new SelectorItemInfo("respPerson.*"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("currency"));
        sic.add(new SelectorItemInfo("grtRate"));
        sic.add(new SelectorItemInfo("coopLevel"));
        sic.add(new SelectorItemInfo("priceType"));
        sic.add(new SelectorItemInfo("bookedDate"));
        sic.add(new SelectorItemInfo("period.*"));
        sic.add(new SelectorItemInfo("isPartAMaterialCon"));
        sic.add(new SelectorItemInfo("conChargeType.*"));
        sic.add(new SelectorItemInfo("contractSourceId.*"));
        sic.add(new SelectorItemInfo("bail.amount"));
        sic.add(new SelectorItemInfo("bail.prop"));
        sic.add(new SelectorItemInfo("payItems.*"));
//        sic.add(new SelectorItemInfo("payItems.number"));
	    sic.add(new SelectorItemInfo("payItems.payItemDate"));
	    sic.add(new SelectorItemInfo("payItems.payCondition"));
	    sic.add(new SelectorItemInfo("payItems.prop"));
	    sic.add(new SelectorItemInfo("payItems.amount"));
	    sic.add(new SelectorItemInfo("payItems.desc"));
        sic.add(new SelectorItemInfo("payItems.paymentType.*"));
//        sic.add(new SelectorItemInfo("payItems.paymentType.number"));
	    sic.add(new SelectorItemInfo("bail.entry.bailDate"));
	    sic.add(new SelectorItemInfo("bail.entry.bailConditon"));
	    sic.add(new SelectorItemInfo("bail.entry.prop"));
	    sic.add(new SelectorItemInfo("bail.entry.amount"));
	    sic.add(new SelectorItemInfo("bail.entry.desc"));
        sic.add(new SelectorItemInfo("bail.entry.*"));
		sic.add(new SelectorItemInfo("curProject.id"));
		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		sic.add(new SelectorItemInfo("currency.precision"));

		sic.add(new SelectorItemInfo("CU.id"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractType.isLeaf"));
		sic.add(new SelectorItemInfo("contractType.level"));
		sic.add(new SelectorItemInfo("contractType.number"));
		sic.add(new SelectorItemInfo("contractType.longnumber"));
		sic.add(new SelectorItemInfo("contractType.name"));

		sic.add(new SelectorItemInfo("entrys.*"));
		sic.add(new SelectorItemInfo("contractPlan.*"));

		sic.add(new SelectorItemInfo("amount"));
		sic.add(new SelectorItemInfo("originalAmount"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("isAmtWithoutCost"));
		sic.add(new SelectorItemInfo("codeType.number"));
		sic.add(new SelectorItemInfo("codeType.name"));

		sic.add(new SelectorItemInfo("isArchived"));
		sic.add(new SelectorItemInfo("splitState"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		
		sic.add(new SelectorItemInfo("auditor.id"));
		sic.add(new SelectorItemInfo("overRate"));
		
		sic.add(new SelectorItemInfo("mainContract.*"));
		sic.add(new SelectorItemInfo("effectiveStartDate"));
		sic.add(new SelectorItemInfo("effectiveEndDate"));
		sic.add(new SelectorItemInfo("isSubContract"));
		sic.add(new SelectorItemInfo("information"));
		
		sic.add(new SelectorItemInfo("createDept.id"));
		sic.add(new SelectorItemInfo("createDept.name"));
		sic.add(new SelectorItemInfo("createDept.number"));
		sic.add(new SelectorItemInfo("contractSettleType"));
		sic.add(new SelectorItemInfo("srcProID"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("isOpenContract"));
	    sic.add(new SelectorItemInfo("isStardContract"));
	    
	    
	    sic.add(new SelectorItemInfo("agreementID"));
	    sic.add(new SelectorItemInfo("contractModel"));
	    
	    sic.add(new SelectorItemInfo("programmingContract.*"));
//		sic.add(new SelectorItemInfo("programmingContract.programming.*"));
		sic.add(new SelectorItemInfo("programmingContract.programming.project.id"));
		sic.add(new SelectorItemInfo("programmingContract.contractType.id"));
		sic.add(new SelectorItemInfo("contractType.contractWFTypeEntry.contractWFType.*"));
		sic.add(new SelectorItemInfo("contractType.inviteTypeEntry.inviteType.*"));
		
		sic.add(new SelectorItemInfo("srcAmount"));
		sic.add(new SelectorItemInfo("orgType"));
		return sic;
	}
	//Regiester control's property binding.
	private void registerBindings(){
//		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
//		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
//		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
//		dataBinder.registerBinding("chgPercForWarn", java.math.BigDecimal.class, this.txtchgPercForWarn, "value");
//		dataBinder.registerBinding("payPercForWarn", java.math.BigDecimal.class, this.txtpayPercForWarn, "value");
//		dataBinder.registerBinding("signDate", java.util.Date.class, this.pksignDate, "value");
//		dataBinder.registerBinding("lowestPrice", java.math.BigDecimal.class, this.txtlowestPrice, "value");
//		dataBinder.registerBinding("lowerPrice", java.math.BigDecimal.class, this.txtlowerPrice, "value");
//		dataBinder.registerBinding("middlePrice", java.math.BigDecimal.class, this.txtmiddlePrice, "value");
//		dataBinder.registerBinding("higherPrice", java.math.BigDecimal.class, this.txthigherPrice, "value");
//		dataBinder.registerBinding("highestPrice", java.math.BigDecimal.class, this.txthighestPrice, "value");
//		dataBinder.registerBinding("winPrice", java.math.BigDecimal.class, this.txtwinPrice, "value");
//		dataBinder.registerBinding("quantity", java.math.BigDecimal.class, this.txtquantity, "value");
//		dataBinder.registerBinding("fileNo", String.class, this.txtfileNo, "text");
//		dataBinder.registerBinding("basePrice", java.math.BigDecimal.class, this.txtbasePrice, "value");
//		dataBinder.registerBinding("secondPrice", java.math.BigDecimal.class, this.txtsecondPrice, "value");
//		dataBinder.registerBinding("landDeveloper", com.kingdee.eas.fdc.basedata.LandDeveloperInfo.class, this.prmtlandDeveloper, "data");
//		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtcontractType, "data");
//		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.basedata.InviteTypeInfo.class, this.prmtinviteType, "data");
//		dataBinder.registerBinding("winUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtwinUnit, "data");
//		dataBinder.registerBinding("costProperty", com.kingdee.eas.fdc.contract.CostPropertyEnum.class, this.costProperty, "selectedItem");
//		dataBinder.registerBinding("contractPropert", com.kingdee.eas.fdc.contract.ContractPropertyEnum.class, this.contractPropert, "selectedItem");
//		dataBinder.registerBinding("partB", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartB, "data");
//		dataBinder.registerBinding("partC", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartC, "data");
//		dataBinder.registerBinding("name", String.class, this.txtcontractName, "text");
//		dataBinder.registerBinding("lowestPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowestPriceUnit, "data");
//		dataBinder.registerBinding("lowerPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowerPriceUnit, "data");
//		dataBinder.registerBinding("middlePriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtmiddlePriceUnit, "data");
//		dataBinder.registerBinding("higherPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthigherPriceUnit, "data");
//		dataBinder.registerBinding("highestPriceUni", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthighestPriceUni, "data");
//		dataBinder.registerBinding("isCoseSplit", boolean.class, this.chkCostSplit, "selected");
//		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtLocalAmount, "value");
//		dataBinder.registerBinding("exRate", java.math.BigDecimal.class, this.txtExRate, "value");
//		dataBinder.registerBinding("grtAmount", java.math.BigDecimal.class, this.txtGrtAmount, "value");
//		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
//		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
//		dataBinder.registerBinding("payScale", java.math.BigDecimal.class, this.txtPayScale, "value");
//		dataBinder.registerBinding("stampTaxRate", java.math.BigDecimal.class, this.txtStampTaxRate, "value");
//		dataBinder.registerBinding("stampTaxAmt", java.math.BigDecimal.class, this.txtStampTaxAmt, "value");
//		dataBinder.registerBinding("respPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtRespPerson, "data");
//		dataBinder.registerBinding("creator.name", String.class, this.txtCreator, "text");
//		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.comboCurrency, "selectedItem");
//		dataBinder.registerBinding("grtRate", java.math.BigDecimal.class, this.txtGrtRate, "value");
//		dataBinder.registerBinding("coopLevel", com.kingdee.eas.fdc.contract.CoopLevelEnum.class, this.comboCoopLevel, "selectedItem");
//		dataBinder.registerBinding("priceType", com.kingdee.eas.fdc.contract.PriceTypeEnum.class, this.comboPriceType, "selectedItem");
//		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
//		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
//		dataBinder.registerBinding("isPartAMaterialCon", boolean.class, this.chkIsPartAMaterialCon, "selected");
//		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtCharge, "data");
//		dataBinder.registerBinding("contractSourceId", com.kingdee.eas.fdc.basedata.ContractSourceInfo.class, this.contractSource, "data");
//		dataBinder.registerBinding("bail.amount", java.math.BigDecimal.class, this.txtBailOriAmount, "value");
//		dataBinder.registerBinding("bail.prop", java.math.BigDecimal.class, this.txtBailRate, "value");
//		dataBinder.registerBinding("payItems", com.kingdee.eas.fdc.contract.ContractPayItemInfo.class, this.tblEconItem, "userObject");
//		dataBinder.registerBinding("payItems.payItemDate", java.util.Date.class, this.tblEconItem, "date.text");
//		dataBinder.registerBinding("payItems.payCondition", String.class, this.tblEconItem, "payCondition.text");
//		dataBinder.registerBinding("payItems.prop", java.math.BigDecimal.class, this.tblEconItem, "payRate.text");
//		dataBinder.registerBinding("payItems.amount", java.math.BigDecimal.class, this.tblEconItem, "payAmount.text");
//		dataBinder.registerBinding("payItems.desc", String.class, this.tblEconItem, "desc.text");
//		dataBinder.registerBinding("payItems.paymentType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.tblEconItem, "payType.text");
//		dataBinder.registerBinding("bail.entry.bailDate", java.util.Date.class, this.tblBail, "bailDate.text");
//		dataBinder.registerBinding("bail.entry.bailConditon", String.class, this.tblBail, "bailCondition.text");
//		dataBinder.registerBinding("bail.entry.prop", java.math.BigDecimal.class, this.tblBail, "bailRate.text");
//		dataBinder.registerBinding("bail.entry.amount", java.math.BigDecimal.class, this.tblBail, "bailAmount.text");
//		dataBinder.registerBinding("bail.entry.desc", String.class, this.tblBail, "desc.text");
//		dataBinder.registerBinding("bail.entry", com.kingdee.eas.fdc.contract.ContractBailEntryInfo.class, this.tblBail, "userObject");	
//		
//		

		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.kDDateCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("originalAmount", java.math.BigDecimal.class, this.txtamount, "value");
		dataBinder.registerBinding("landDeveloper", com.kingdee.eas.fdc.basedata.LandDeveloperInfo.class, this.prmtlandDeveloper, "data");
		dataBinder.registerBinding("contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtcontractType, "data");
		dataBinder.registerBinding("contractPropert", com.kingdee.eas.fdc.contract.ContractPropertyEnum.class, this.contractPropert, "selectedItem");
		dataBinder.registerBinding("partB", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartB, "data");
		dataBinder.registerBinding("partC", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtpartC, "data");
		dataBinder.registerBinding("name", String.class, this.txtcontractName, "text");
		dataBinder.registerBinding("isSubContract", boolean.class, this.chkIsSubMainContract, "selected");
		dataBinder.registerBinding("lowestPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowestPriceUnit, "data");
		dataBinder.registerBinding("lowerPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtlowerPriceUnit, "data");
		dataBinder.registerBinding("middlePriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtmiddlePriceUnit, "data");
		dataBinder.registerBinding("higherPriceUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthigherPriceUnit, "data");
		dataBinder.registerBinding("highestPriceUni", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmthighestPriceUni, "data");
		dataBinder.registerBinding("remark", String.class, this.txtRemark, "text");
		dataBinder.registerBinding("coopLevel", com.kingdee.eas.fdc.contract.CoopLevelEnum.class, this.comboCoopLevel, "selectedItem");
		dataBinder.registerBinding("priceType", com.kingdee.eas.fdc.contract.PriceTypeEnum.class, this.comboPriceType, "selectedItem");
		dataBinder.registerBinding("mainContract", com.kingdee.eas.fdc.contract.ContractBillCollection.class, this.prmtMainContract, "data");
		dataBinder.registerBinding("effectiveStartDate", java.util.Date.class, this.kdpEffectStartDate, "value");
		dataBinder.registerBinding("effectiveEndDate", java.util.Date.class, this.kdpEffectiveEndDate, "value");
		dataBinder.registerBinding("information", String.class, this.txtInformation, "text");
		dataBinder.registerBinding("lowestPrice", java.math.BigDecimal.class, this.txtlowestPrice, "value");
		dataBinder.registerBinding("lowerPrice", java.math.BigDecimal.class, this.txtlowerPrice, "value");
		dataBinder.registerBinding("higherPrice", java.math.BigDecimal.class, this.txthigherPrice, "value");
		dataBinder.registerBinding("middlePrice", java.math.BigDecimal.class, this.txtmiddlePrice, "value");
		dataBinder.registerBinding("highestPrice", java.math.BigDecimal.class, this.txthighestPrice, "value");
		dataBinder.registerBinding("basePrice", java.math.BigDecimal.class, this.txtbasePrice, "value");
		dataBinder.registerBinding("secondPrice", java.math.BigDecimal.class, this.txtsecondPrice, "value");
		dataBinder.registerBinding("winPrice", java.math.BigDecimal.class, this.txtwinPrice, "value");
		dataBinder.registerBinding("winUnit", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.prmtwinUnit, "data");
		dataBinder.registerBinding("fileNo", String.class, this.txtfileNo, "text");
		dataBinder.registerBinding("quantity", java.math.BigDecimal.class, this.txtquantity, "value");
		dataBinder.registerBinding("payPercForWarn", java.math.BigDecimal.class, this.txtpayPercForWarn, "value");
		dataBinder.registerBinding("chgPercForWarn", java.math.BigDecimal.class, this.txtchgPercForWarn, "value");
		dataBinder.registerBinding("costProperty", com.kingdee.eas.fdc.contract.CostPropertyEnum.class, this.costProperty, "selectedItem");
		dataBinder.registerBinding("payScale", java.math.BigDecimal.class, this.txtPayScale, "value");
		dataBinder.registerBinding("conChargeType", com.kingdee.eas.fdc.basedata.ContractChargeTypeInfo.class, this.prmtCharge, "data");
		dataBinder.registerBinding("exRate", java.math.BigDecimal.class, this.txtExRate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtLocalAmount, "value");
		dataBinder.registerBinding("grtAmount", java.math.BigDecimal.class, this.txtGrtAmount, "value");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.comboCurrency, "selectedItem");
		dataBinder.registerBinding("respPerson", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtRespPerson, "data");
		dataBinder.registerBinding("creator.name", String.class, this.txtCreator, "text");
		dataBinder.registerBinding("grtRate", java.math.BigDecimal.class, this.txtGrtRate, "value");
		dataBinder.registerBinding("period", com.kingdee.eas.basedata.assistant.PeriodInfo.class, this.cbPeriod, "data");
		dataBinder.registerBinding("programmingContract.name", String.class, this.textFwContract, "text");
		dataBinder.registerBinding("bookedDate", java.util.Date.class, this.pkbookedDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDes, "text");
		dataBinder.registerBinding("isOpenContract", boolean.class, this.chkIsOpen, "selected");
		dataBinder.registerBinding("isStardContract", boolean.class, this.chkIsStandardContract, "selected");
		dataBinder.registerBinding("isPartAMaterialCon", boolean.class, this.chkIsPartAMaterialCon, "selected");
		dataBinder.registerBinding("isCoseSplit", boolean.class, this.chkCostSplit, "selected");
		dataBinder.registerBinding("respDept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtRespDept, "data");
		dataBinder.registerBinding("orgType", com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.class, this.cbOrgType, "selectedItem");
		dataBinder.registerBinding("contractSourceId", com.kingdee.eas.fdc.basedata.ContractSourceInfo.class, this.contractSource, "data");
		dataBinder.registerBinding("contractWFType", com.kingdee.eas.fdc.contract.ContractWFTypeInfo.class, this.prmtContractWFType, "data");
		dataBinder.registerBinding("model", com.kingdee.eas.base.attachment.AttachmentInfo.class, this.prmtModel, "data");
		dataBinder.registerBinding("srcAmount", java.math.BigDecimal.class, this.txtSrcAmount, "value");
		dataBinder.registerBinding("inviteType", com.kingdee.eas.fdc.basedata.InviteTypeInfo.class, this.prmtInviteType, "data");
		dataBinder.registerBinding("payItems", com.kingdee.eas.fdc.contract.ContractPayItemInfo.class, this.tblEconItem, "userObject");
		dataBinder.registerBinding("payItems.payItemDate", java.util.Date.class, this.tblEconItem, "date.text");
		dataBinder.registerBinding("payItems.payCondition", String.class, this.tblEconItem, "payCondition.text");
		dataBinder.registerBinding("payItems.prop", java.math.BigDecimal.class, this.tblEconItem, "payRate.text");
		dataBinder.registerBinding("payItems.amount", java.math.BigDecimal.class, this.tblEconItem, "payAmount.text");
		dataBinder.registerBinding("payItems.desc", String.class, this.tblEconItem, "desc.text");
		dataBinder.registerBinding("payItems.paymentType", com.kingdee.eas.fdc.basedata.PaymentTypeInfo.class, this.tblEconItem, "payType.text");
		dataBinder.registerBinding("bail.entry.bailDate", java.util.Date.class, this.tblBail, "bailDate.text");
		dataBinder.registerBinding("bail.entry.bailConditon", String.class, this.tblBail, "bailCondition.text");
		dataBinder.registerBinding("bail.entry.prop", java.math.BigDecimal.class, this.tblBail, "bailRate.text");
		dataBinder.registerBinding("bail.entry.amount", java.math.BigDecimal.class, this.tblBail, "bailAmount.text");
		dataBinder.registerBinding("bail.entry.desc", String.class, this.tblBail, "desc.text");
		dataBinder.registerBinding("bail.entry", com.kingdee.eas.fdc.contract.ContractBailEntryInfo.class, this.tblBail, "userObject");
		dataBinder.registerBinding("bail.amount", java.math.BigDecimal.class, this.txtBailOriAmount, "value");
		dataBinder.registerBinding("bail.prop", java.math.BigDecimal.class, this.txtBailRate, "value");		
	}
	//Regiester UI State
	private void registerUIState(){					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionUnAudit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionSplit, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionViewContent, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionContractPlan, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionAttachment, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.actionWorkFlowG, ActionStateConst.DISABLED);		
	}
	
	public void initUIContentLayout()
    {
		super.initUIContentLayout();
//        this.setBounds(new Rectangle(0, 0, 1013, 629));
//this.setLayout(new BorderLayout(0, 0));
//        this.add(tabPanel, BorderLayout.CENTER);
//        //tabPanel
//        tabPanel.add(mainPanel, resHelper.getString("mainPanel.constraints"));
//        tabPanel.add(ecoItemPanel, resHelper.getString("ecoItemPanel.constraints"));
//        //mainPanel
//        mainPanel.setLayout(new KDLayout());
//        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//        mainPanel.putClientProperty("OriginalBounds", new Rectangle(0,0,1013,629));        contCreateTime.setBounds(new Rectangle(9, 287, 270, 19));
//        mainPanel.add(contCreateTime, new KDLayout.Constraints(9, 287, 270, 19, 0));
//        contNumber.setBounds(new Rectangle(497, 37, 424, 19));
//        mainPanel.add(contNumber, new KDLayout.Constraints(497, 37, 424, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contamount.setBounds(new Rectangle(329, 162, 270, 19));
//        mainPanel.add(contamount, new KDLayout.Constraints(329, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contchgPercForWarn.setBounds(new Rectangle(651, 237, 270, 19));
//        mainPanel.add(contchgPercForWarn, new KDLayout.Constraints(651, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contpayPercForWarn.setBounds(new Rectangle(651, 212, 270, 19));
//        mainPanel.add(contpayPercForWarn, new KDLayout.Constraints(651, 212, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contsignDate.setBounds(new Rectangle(12, 137, 270, 19));
//        mainPanel.add(contsignDate, new KDLayout.Constraints(12, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contlandDeveloper.setBounds(new Rectangle(12, 87, 424, 19));
//        mainPanel.add(contlandDeveloper, new KDLayout.Constraints(12, 87, 424, 19, 0));
//        contcontractType.setBounds(new Rectangle(12, 37, 424, 19));
//        mainPanel.add(contcontractType, new KDLayout.Constraints(12, 37, 424, 19, 0));
//        contcostProperty.setBounds(new Rectangle(329, 237, 270, 19));
//        mainPanel.add(contcostProperty, new KDLayout.Constraints(329, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contcontractPropert.setBounds(new Rectangle(497, 112, 424, 19));
//        mainPanel.add(contcontractPropert, new KDLayout.Constraints(497, 112, 424, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contcontractSource.setBounds(new Rectangle(329, 212, 270, 19));
//        mainPanel.add(contcontractSource, new KDLayout.Constraints(329, 212, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contpartB.setBounds(new Rectangle(497, 87, 424, 19));
//        mainPanel.add(contpartB, new KDLayout.Constraints(497, 87, 424, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contpartC.setBounds(new Rectangle(12, 112, 424, 19));
//        mainPanel.add(contpartC, new KDLayout.Constraints(12, 112, 424, 19, 0));
//        contcontractName.setBounds(new Rectangle(12, 62, 909, 19));
//        mainPanel.add(contcontractName, new KDLayout.Constraints(12, 62, 909, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        kDTabbedPane1.setBounds(new Rectangle(10, 365, 911, 191));
//        mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(10, 365, 911, 191, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        chkCostSplit.setBounds(new Rectangle(329, 267, 99, 19));
//        mainPanel.add(chkCostSplit, new KDLayout.Constraints(329, 267, 99, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contExRate.setBounds(new Rectangle(651, 137, 270, 19));
//        mainPanel.add(contExRate, new KDLayout.Constraints(651, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contLocalAmount.setBounds(new Rectangle(651, 162, 270, 19));
//        mainPanel.add(contLocalAmount, new KDLayout.Constraints(651, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contGrtAmount.setBounds(new Rectangle(651, 187, 270, 19));
//        mainPanel.add(contGrtAmount, new KDLayout.Constraints(651, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contCurrency.setBounds(new Rectangle(329, 137, 270, 19));
//        mainPanel.add(contCurrency, new KDLayout.Constraints(329, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contRespDept.setBounds(new Rectangle(12, 162, 270, 19));
//        mainPanel.add(contRespDept, new KDLayout.Constraints(12, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contPayScale.setBounds(new Rectangle(651, 262, 270, 19));
//        mainPanel.add(contPayScale, new KDLayout.Constraints(651, 262, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contStampTaxRate.setBounds(new Rectangle(329, 287, 270, 19));
//        mainPanel.add(contStampTaxRate, new KDLayout.Constraints(329, 287, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contStampTaxAmt.setBounds(new Rectangle(651, 287, 270, 19));
//        mainPanel.add(contStampTaxAmt, new KDLayout.Constraints(651, 287, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contRespPerson.setBounds(new Rectangle(12, 188, 270, 19));
//        mainPanel.add(contRespPerson, new KDLayout.Constraints(12, 188, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contCreator.setBounds(new Rectangle(12, 262, 270, 19));
//        mainPanel.add(contCreator, new KDLayout.Constraints(12, 262, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contOrg.setBounds(new Rectangle(12, 12, 424, 19));
//        mainPanel.add(contOrg, new KDLayout.Constraints(12, 12, 424, 19, 0));
//        contProj.setBounds(new Rectangle(497, 12, 424, 19));
//        mainPanel.add(contProj, new KDLayout.Constraints(497, 12, 424, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        contGrtRate.setBounds(new Rectangle(329, 187, 270, 19));
//        mainPanel.add(contGrtRate, new KDLayout.Constraints(329, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        kDLabelContainer1.setBounds(new Rectangle(12, 212, 270, 19));
//        mainPanel.add(kDLabelContainer1, new KDLayout.Constraints(12, 212, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contIsPartAMaterialCon.setBounds(new Rectangle(12, 237, 270, 19));
//        mainPanel.add(contIsPartAMaterialCon, new KDLayout.Constraints(12, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        chkIsPartAMaterialCon.setBounds(new Rectangle(484, 267, 144, 19));
//        mainPanel.add(chkIsPartAMaterialCon, new KDLayout.Constraints(484, 267, 144, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        conChargeType.setBounds(new Rectangle(329, 313, 270, 19));
//        mainPanel.add(conChargeType, new KDLayout.Constraints(329, 313, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contAttachmentNameList.setBounds(new Rectangle(11, 340, 588, 19));
//        mainPanel.add(contAttachmentNameList, new KDLayout.Constraints(11, 340, 588, 19, 0));
//        btnViewAttachment.setBounds(new Rectangle(651, 340, 87, 21));
//        mainPanel.add(btnViewAttachment, new KDLayout.Constraints(651, 340, 87, 21, 0));
//        btnViewContrnt.setBounds(new Rectangle(834, 340, 87, 21));
//        mainPanel.add(btnViewContrnt, new KDLayout.Constraints(834, 340, 87, 21, 0));
//        lblOverRateContainer.setBounds(new Rectangle(9, 312, 270, 19));
//        mainPanel.add(lblOverRateContainer, new KDLayout.Constraints(9, 312, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contSplitState.setBounds(new Rectangle(12, 575, 270, 19));
//        mainPanel.add(contSplitState, new KDLayout.Constraints(12, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contSettleState.setBounds(new Rectangle(329, 575, 270, 19));
//        mainPanel.add(contSettleState, new KDLayout.Constraints(329, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contSettleNumber.setBounds(new Rectangle(651, 575, 270, 19));
//        mainPanel.add(contSettleNumber, new KDLayout.Constraints(651, 575, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        //contCreateTime
//        contCreateTime.setBoundEditor(kDDateCreateTime);
//        //contNumber
//        contNumber.setBoundEditor(txtNumber);
//        //contamount
//        contamount.setBoundEditor(txtamount);
//        //contchgPercForWarn
//        contchgPercForWarn.setBoundEditor(txtchgPercForWarn);
//        //contpayPercForWarn
//        contpayPercForWarn.setBoundEditor(txtpayPercForWarn);
//        //contsignDate
//        contsignDate.setBoundEditor(pksignDate);
//        //contlandDeveloper
//        contlandDeveloper.setBoundEditor(prmtlandDeveloper);
//        //contcontractType
//        contcontractType.setBoundEditor(prmtcontractType);
//        //contcostProperty
//        contcostProperty.setBoundEditor(costProperty);
//        //contcontractPropert
//        contcontractPropert.setBoundEditor(contractPropert);
//        //contcontractSource
//        contcontractSource.setBoundEditor(contractSource);
//        //contpartB
//        contpartB.setBoundEditor(prmtpartB);
//        //contpartC
//        contpartC.setBoundEditor(prmtpartC);
//        //contcontractName
//        contcontractName.setBoundEditor(txtcontractName);
//        //kDTabbedPane1
//        kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
//        kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
//        kDTabbedPane1.add(pnlCost, resHelper.getString("pnlCost.constraints"));
//        //pnlInviteInfo
//        pnlInviteInfo.setLayout(null);        contlowestPrice.setBounds(new Rectangle(8, 33, 270, 19));
//        pnlInviteInfo.add(contlowestPrice, null);
//        contlowerPrice.setBounds(new Rectangle(8, 60, 270, 19));
//        pnlInviteInfo.add(contlowerPrice, null);
//        contmiddlePrice.setBounds(new Rectangle(8, 87, 270, 19));
//        pnlInviteInfo.add(contmiddlePrice, null);
//        conthigherPrice.setBounds(new Rectangle(8, 114, 270, 19));
//        pnlInviteInfo.add(conthigherPrice, null);
//        conthighestPrice.setBounds(new Rectangle(8, 141, 270, 19));
//        pnlInviteInfo.add(conthighestPrice, null);
//        contbasePrice.setBounds(new Rectangle(8, 33, 270, 19));
//        pnlInviteInfo.add(contbasePrice, null);
//        contsecondPrice.setBounds(new Rectangle(8, 60, 270, 19));
//        pnlInviteInfo.add(contsecondPrice, null);
//        continviteType.setBounds(new Rectangle(636, 114, 346, 19));
//        pnlInviteInfo.add(continviteType, null);
//        contwinPrice.setBounds(new Rectangle(636, 33, 346, 19));
//        pnlInviteInfo.add(contwinPrice, null);
//        contwinUnit.setBounds(new Rectangle(636, 60, 346, 19));
//        pnlInviteInfo.add(contwinUnit, null);
//        contfileNo.setBounds(new Rectangle(636, 141, 346, 19));
//        pnlInviteInfo.add(contfileNo, null);
//        contquantity.setBounds(new Rectangle(636, 87, 346, 19));
//        pnlInviteInfo.add(contquantity, null);
//        lblPrice.setBounds(new Rectangle(169, 8, 58, 19));
//        pnlInviteInfo.add(lblPrice, null);
//        lblUnit.setBounds(new Rectangle(431, 8, 65, 19));
//        pnlInviteInfo.add(lblUnit, null);
//        prmtlowestPriceUnit.setBounds(new Rectangle(298, 33, 292, 19));
//        pnlInviteInfo.add(prmtlowestPriceUnit, null);
//        prmtlowerPriceUnit.setBounds(new Rectangle(298, 60, 292, 19));
//        pnlInviteInfo.add(prmtlowerPriceUnit, null);
//        prmtmiddlePriceUnit.setBounds(new Rectangle(298, 87, 292, 19));
//        pnlInviteInfo.add(prmtmiddlePriceUnit, null);
//        prmthigherPriceUnit.setBounds(new Rectangle(298, 114, 292, 19));
//        pnlInviteInfo.add(prmthigherPriceUnit, null);
//        prmthighestPriceUni.setBounds(new Rectangle(298, 141, 292, 19));
//        pnlInviteInfo.add(prmthighestPriceUni, null);
//        contRemark.setBounds(new Rectangle(8, 87, 270, 19));
//        pnlInviteInfo.add(contRemark, null);
//        contCoopLevel.setBounds(new Rectangle(8, 33, 270, 19));
//        pnlInviteInfo.add(contCoopLevel, null);
//        contPriceType.setBounds(new Rectangle(8, 60, 270, 19));
//        pnlInviteInfo.add(contPriceType, null);
//        //contlowestPrice
//        contlowestPrice.setBoundEditor(txtlowestPrice);
//        //contlowerPrice
//        contlowerPrice.setBoundEditor(txtlowerPrice);
//        //contmiddlePrice
//        contmiddlePrice.setBoundEditor(txtmiddlePrice);
//        //conthigherPrice
//        conthigherPrice.setBoundEditor(txthigherPrice);
//        //conthighestPrice
//        conthighestPrice.setBoundEditor(txthighestPrice);
//        //contbasePrice
//        contbasePrice.setBoundEditor(txtbasePrice);
//        //contsecondPrice
//        contsecondPrice.setBoundEditor(txtsecondPrice);
//        //continviteType
//        continviteType.setBoundEditor(prmtinviteType);
//        //contwinPrice
//        contwinPrice.setBoundEditor(txtwinPrice);
//        //contwinUnit
//        contwinUnit.setBoundEditor(prmtwinUnit);
//        //contfileNo
//        contfileNo.setBoundEditor(txtfileNo);
//        //contquantity
//        contquantity.setBoundEditor(txtquantity);
//        //contRemark
//        contRemark.setBoundEditor(txtRemark);
//        //contCoopLevel
//        contCoopLevel.setBoundEditor(comboCoopLevel);
//        //contPriceType
//        contPriceType.setBoundEditor(comboPriceType);
//        //pnlDetail
//        pnlDetail.getViewport().add(tblDetail, null);
//        //pnlCost
//        pnlCost.setLayout(new KDLayout());
//        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//        pnlCost.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        tblCost.setBounds(new Rectangle(10, 10, 965, 156));
//        pnlCost.add(tblCost, new KDLayout.Constraints(10, 10, 965, 156, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
//        //contExRate
//        contExRate.setBoundEditor(txtExRate);
//        //contLocalAmount
//        contLocalAmount.setBoundEditor(txtLocalAmount);
//        //contGrtAmount
//        contGrtAmount.setBoundEditor(txtGrtAmount);
//        //contCurrency
//        contCurrency.setBoundEditor(comboCurrency);
//        //contRespDept
//        contRespDept.setBoundEditor(prmtRespDept);
//        //contPayScale
//        contPayScale.setBoundEditor(txtPayScale);
//        //contStampTaxRate
//        contStampTaxRate.setBoundEditor(txtStampTaxRate);
//        //contStampTaxAmt
//        contStampTaxAmt.setBoundEditor(txtStampTaxAmt);
//        //contRespPerson
//        contRespPerson.setBoundEditor(prmtRespPerson);
//        //contCreator
//        contCreator.setBoundEditor(txtCreator);
//        //contOrg
//        contOrg.setBoundEditor(txtOrg);
//        //contProj
//        contProj.setBoundEditor(txtProj);
//        //contGrtRate
//        contGrtRate.setBoundEditor(txtGrtRate);
//        //kDLabelContainer1
//        kDLabelContainer1.setBoundEditor(pkbookedDate);
//        //contIsPartAMaterialCon
//        contIsPartAMaterialCon.setBoundEditor(cbPeriod);
//        //conChargeType
//        conChargeType.setBoundEditor(prmtCharge);
//        //contAttachmentNameList
//        contAttachmentNameList.setBoundEditor(comboAttachmentNameList);
//        //lblOverRateContainer
//        lblOverRateContainer.setBoundEditor(txtOverAmt);
//        //contSplitState
//        contSplitState.setBoundEditor(txtSplitState);
//        //contSettleState
//        contSettleState.setBoundEditor(txtSettleState);
//        //contSettleNumber
//        contSettleNumber.setBoundEditor(txtSettleNumber);
//        //ecoItemPanel
//ecoItemPanel.setLayout(new BorderLayout(0, 0));        ecoItemPanel.add(kDContainer1, BorderLayout.CENTER);
//        //kDContainer1
//kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDSplitPane1, BorderLayout.CENTER);
//        //kDSplitPane1
//        kDSplitPane1.add(contPayItem, "top");
//        kDSplitPane1.add(contBailItem, "bottom");
//        //contPayItem
//contPayItem.getContentPane().setLayout(new BorderLayout(0, 0));        contPayItem.getContentPane().add(tblEconItem, BorderLayout.CENTER);
//        //contBailItem
//        contBailItem.getContentPane().setLayout(new KDLayout());
//        //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//        contBailItem.getContentPane().putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        contBailOriAmount.setBounds(new Rectangle(5, 8, 463, 19));
//        contBailItem.getContentPane().add(contBailOriAmount, new KDLayout.Constraints(5, 8, 463, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//        contBailRate.setBounds(new Rectangle(544, 8, 450, 19));
//        contBailItem.getContentPane().add(contBailRate, new KDLayout.Constraints(544, 8, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//        tblBail.setBounds(new Rectangle(3, 40, 995, 218));
//        contBailItem.getContentPane().add(tblBail, new KDLayout.Constraints(3, 40, 995, 218, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
//        //contBailOriAmount
//        contBailOriAmount.setBoundEditor(txtBailOriAmount);
//        //contBailRate
//        contBailRate.setBoundEditor(txtBailRate);

		
		//		 this.setBounds(new Rectangle(0, 0, 1013, 629));
//	        this.setLayout(new BorderLayout(0, 0));
//	                this.add(tabPanel, BorderLayout.CENTER);
//	                //tabPanel
//	                tabPanel.add(mainPanel, resHelper.getString("mainPanel.constraints"));
//	                tabPanel.add(ecoItemPanel, resHelper.getString("ecoItemPanel.constraints"));
//	                //mainPanel
//	                mainPanel.setLayout(new KDLayout());
//	                //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//	                mainPanel.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 629));        contCreateTime.setBounds(new Rectangle(12, 287, 270, 19));
//	                mainPanel.add(contCreateTime, new KDLayout.Constraints(12, 287, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contNumber.setBounds(new Rectangle(532, 37, 470, 19));
//	                mainPanel.add(contNumber, new KDLayout.Constraints(532, 37, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contamount.setBounds(new Rectangle(374, 162, 270, 19));
//	                mainPanel.add(contamount, new KDLayout.Constraints(374, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contchgPercForWarn.setBounds(new Rectangle(732, 237, 270, 19));
//	                mainPanel.add(contchgPercForWarn, new KDLayout.Constraints(732, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contpayPercForWarn.setBounds(new Rectangle(732, 212, 270, 19));
//	                mainPanel.add(contpayPercForWarn, new KDLayout.Constraints(732, 212, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contsignDate.setBounds(new Rectangle(12, 137, 270, 19));
//	                mainPanel.add(contsignDate, new KDLayout.Constraints(12, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contlandDeveloper.setBounds(new Rectangle(12, 87, 470, 19));
//	                mainPanel.add(contlandDeveloper, new KDLayout.Constraints(12, 87, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contcontractType.setBounds(new Rectangle(12, 37, 470, 19));
//	                mainPanel.add(contcontractType, new KDLayout.Constraints(12, 37, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contcostProperty.setBounds(new Rectangle(374, 237, 270, 19));
//	                mainPanel.add(contcostProperty, new KDLayout.Constraints(374, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contcontractPropert.setBounds(new Rectangle(532, 112, 470, 19));
//	                mainPanel.add(contcontractPropert, new KDLayout.Constraints(532, 112, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contcontractSource.setBounds(new Rectangle(374, 212, 270, 19));
//	                mainPanel.add(contcontractSource, new KDLayout.Constraints(374, 212, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contpartB.setBounds(new Rectangle(532, 87, 470, 19));
//	                mainPanel.add(contpartB, new KDLayout.Constraints(532, 87, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contpartC.setBounds(new Rectangle(12, 112, 470, 19));
//	                mainPanel.add(contpartC, new KDLayout.Constraints(12, 112, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contcontractName.setBounds(new Rectangle(12, 62, 990, 19));
//	                mainPanel.add(contcontractName, new KDLayout.Constraints(12, 62, 990, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                kDTabbedPane1.setBounds(new Rectangle(10, 365, 994, 280));
//	                mainPanel.add(kDTabbedPane1, new KDLayout.Constraints(10, 365, 994, 280, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                chkCostSplit.setBounds(new Rectangle(373, 267, 99, 19));
//	                mainPanel.add(chkCostSplit, new KDLayout.Constraints(373, 267, 99, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contExRate.setBounds(new Rectangle(732, 137, 270, 19));
//	                mainPanel.add(contExRate, new KDLayout.Constraints(732, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contLocalAmount.setBounds(new Rectangle(732, 162, 270, 19));
//	                mainPanel.add(contLocalAmount, new KDLayout.Constraints(732, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contGrtAmount.setBounds(new Rectangle(732, 187, 270, 19));
//	                mainPanel.add(contGrtAmount, new KDLayout.Constraints(732, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contCurrency.setBounds(new Rectangle(374, 137, 270, 19));
//	                mainPanel.add(contCurrency, new KDLayout.Constraints(374, 137, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contRespDept.setBounds(new Rectangle(12, 162, 270, 19));
//	                mainPanel.add(contRespDept, new KDLayout.Constraints(12, 162, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contPayScale.setBounds(new Rectangle(732, 262, 270, 19));
//	                mainPanel.add(contPayScale, new KDLayout.Constraints(732, 262, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contStampTaxRate.setBounds(new Rectangle(374, 287, 270, 19));
//	                mainPanel.add(contStampTaxRate, new KDLayout.Constraints(374, 287, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contStampTaxAmt.setBounds(new Rectangle(732, 287, 270, 19));
//	                mainPanel.add(contStampTaxAmt, new KDLayout.Constraints(732, 287, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contRespPerson.setBounds(new Rectangle(12, 188, 270, 19));
//	                mainPanel.add(contRespPerson, new KDLayout.Constraints(12, 188, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contCreator.setBounds(new Rectangle(12, 262, 270, 19));
//	                mainPanel.add(contCreator, new KDLayout.Constraints(12, 262, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contOrg.setBounds(new Rectangle(12, 12, 470, 19));
//	                mainPanel.add(contOrg, new KDLayout.Constraints(12, 12, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contProj.setBounds(new Rectangle(532, 12, 470, 19));
//	                mainPanel.add(contProj, new KDLayout.Constraints(532, 12, 470, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                contGrtRate.setBounds(new Rectangle(374, 187, 270, 19));
//	                mainPanel.add(contGrtRate, new KDLayout.Constraints(374, 187, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                kDLabelContainer1.setBounds(new Rectangle(12, 212, 270, 19));
//	                mainPanel.add(kDLabelContainer1, new KDLayout.Constraints(12, 212, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contIsPartAMaterialCon.setBounds(new Rectangle(12, 237, 270, 19));
//	                mainPanel.add(contIsPartAMaterialCon, new KDLayout.Constraints(12, 237, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                chkIsPartAMaterialCon.setBounds(new Rectangle(496, 267, 144, 19));
//	                mainPanel.add(chkIsPartAMaterialCon, new KDLayout.Constraints(496, 267, 144, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                conChargeType.setBounds(new Rectangle(12, 312, 270, 19));
//	                mainPanel.add(conChargeType, new KDLayout.Constraints(12, 312, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                //合同结算提示比例
//	                lblOverRateContainer.setBounds(new Rectangle(9, 312, 270, 19));
//	                mainPanel.add(lblOverRateContainer, new KDLayout.Constraints(9, 312, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                
//	                contAttachmentNameList.setBounds(new Rectangle(11, 340, 636, 19));
//	                mainPanel.add(contAttachmentNameList, new KDLayout.Constraints(11, 340, 636, 19, 0));
//	                btnViewAttachment.setBounds(new Rectangle(667, 340, 87, 21));
//	                mainPanel.add(btnViewAttachment, new KDLayout.Constraints(667, 340, 87, 21, 0));
//	                btnViewContrnt.setBounds(new Rectangle(829, 340, 87, 21));
//	                mainPanel.add(btnViewContrnt, new KDLayout.Constraints(829, 340, 87, 21, 0));
//	                //contCreateTime
//	                contCreateTime.setBoundEditor(kDDateCreateTime);
//	                //contNumber
//	                contNumber.setBoundEditor(txtNumber);
//	                //contamount
//	                contamount.setBoundEditor(txtamount);
//	                //contchgPercForWarn
//	                contchgPercForWarn.setBoundEditor(txtchgPercForWarn);
//	                //contpayPercForWarn
//	                contpayPercForWarn.setBoundEditor(txtpayPercForWarn);
//	                //contsignDate
//	                contsignDate.setBoundEditor(pksignDate);
//	                //contlandDeveloper
//	                contlandDeveloper.setBoundEditor(prmtlandDeveloper);
//	                //contcontractType
//	                contcontractType.setBoundEditor(prmtcontractType);
//	                //contcostProperty
//	                contcostProperty.setBoundEditor(costProperty);
//	                //contcontractPropert
//	                contcontractPropert.setBoundEditor(contractPropert);
//	                //contcontractSource
//	                contcontractSource.setBoundEditor(contractSource);
//	                //contpartB
//	                contpartB.setBoundEditor(prmtpartB);
//	                //contpartC
//	                contpartC.setBoundEditor(prmtpartC);
//	                //contcontractName
//	                contcontractName.setBoundEditor(txtcontractName);
//	                //kDTabbedPane1
//	                kDTabbedPane1.add(pnlInviteInfo, resHelper.getString("pnlInviteInfo.constraints"));
//	                kDTabbedPane1.add(pnlDetail, resHelper.getString("pnlDetail.constraints"));
//	                kDTabbedPane1.add(pnlCost, resHelper.getString("pnlCost.constraints"));
//	                //pnlInviteInfo
//	                pnlInviteInfo.setLayout(null);        contlowestPrice.setBounds(new Rectangle(8, 33, 270, 19));
//	                pnlInviteInfo.add(contlowestPrice, null);
//	                contlowerPrice.setBounds(new Rectangle(8, 60, 270, 19));
//	                pnlInviteInfo.add(contlowerPrice, null);
//	                contmiddlePrice.setBounds(new Rectangle(8, 87, 270, 19));
//	                pnlInviteInfo.add(contmiddlePrice, null);
//	                conthigherPrice.setBounds(new Rectangle(8, 114, 270, 19));
//	                pnlInviteInfo.add(conthigherPrice, null);
//	                conthighestPrice.setBounds(new Rectangle(8, 141, 270, 19));
//	                pnlInviteInfo.add(conthighestPrice, null);
//	                contbasePrice.setBounds(new Rectangle(8, 33, 270, 19));
//	                pnlInviteInfo.add(contbasePrice, null);
//	                contsecondPrice.setBounds(new Rectangle(8, 60, 270, 19));
//	                pnlInviteInfo.add(contsecondPrice, null);
//	                continviteType.setBounds(new Rectangle(636, 114, 346, 19));
//	                pnlInviteInfo.add(continviteType, null);
//	                contwinPrice.setBounds(new Rectangle(636, 33, 346, 19));
//	                pnlInviteInfo.add(contwinPrice, null);
//	                contwinUnit.setBounds(new Rectangle(636, 60, 346, 19));
//	                pnlInviteInfo.add(contwinUnit, null);
//	                contfileNo.setBounds(new Rectangle(636, 141, 346, 19));
//	                pnlInviteInfo.add(contfileNo, null);
//	                contquantity.setBounds(new Rectangle(636, 87, 346, 19));
//	                pnlInviteInfo.add(contquantity, null);
//	                lblPrice.setBounds(new Rectangle(169, 8, 58, 19));
//	                pnlInviteInfo.add(lblPrice, null);
//	                lblUnit.setBounds(new Rectangle(431, 8, 65, 19));
//	                pnlInviteInfo.add(lblUnit, null);
//	                prmtlowestPriceUnit.setBounds(new Rectangle(298, 33, 292, 19));
//	                pnlInviteInfo.add(prmtlowestPriceUnit, null);
//	                prmtlowerPriceUnit.setBounds(new Rectangle(298, 60, 292, 19));
//	                pnlInviteInfo.add(prmtlowerPriceUnit, null);
//	                prmtmiddlePriceUnit.setBounds(new Rectangle(298, 87, 292, 19));
//	                pnlInviteInfo.add(prmtmiddlePriceUnit, null);
//	                prmthigherPriceUnit.setBounds(new Rectangle(298, 114, 292, 19));
//	                pnlInviteInfo.add(prmthigherPriceUnit, null);
//	                prmthighestPriceUni.setBounds(new Rectangle(298, 141, 292, 19));
//	                pnlInviteInfo.add(prmthighestPriceUni, null);
//	                //战 略合同
//	                chkIsSubMainContract.setBounds(new Rectangle(30, 25, 118, 19));
//	                pnlInviteInfo.add(chkIsSubMainContract, null);
//	                conMainContract.setBounds(new Rectangle(31, 51, 270, 19));
//	                pnlInviteInfo.add(conMainContract, null);
//	                conEffectiveStartDate.setBounds(new Rectangle(327, 25, 270, 19));
//	                pnlInviteInfo.add(conEffectiveStartDate, null);
//	                conEffectiveEndDate.setBounds(new Rectangle(701, 24, 270, 19));
//	                pnlInviteInfo.add(conEffectiveEndDate, null);
//	                kDScrollPane1.setBounds(new Rectangle(330, 75, 642, 78));
//	                pnlInviteInfo.add(kDScrollPane1, null);
//	                conInformation.setBounds(new Rectangle(326, 51, 270, 19));
//	                pnlInviteInfo.add(conInformation, null);
//	                kDScrollPane1.setBounds(new Rectangle(330, 75, 642, 58));
//	                pnlInviteInfo.add(kDScrollPane1, null);
//	                contRemark.setBounds(new Rectangle(8, 87, 270, 19));
//	                pnlInviteInfo.add(contRemark, null);
////	                contCoopLevel.setBounds(new Rectangle(8, 33, 270, 19));
////	                pnlInviteInfo.add(contCoopLevel, null);
//	                contCoopLevel.setBounds(new Rectangle(31, 80, 270, 19));
//	                pnlInviteInfo.add(contCoopLevel, null);
//	                contPriceType.setBounds(new Rectangle(8, 60, 270, 19));
//	                pnlInviteInfo.add(contPriceType, null);
//	                //contlowestPrice
//	                contlowestPrice.setBoundEditor(txtlowestPrice);
//	                //contlowerPrice
//	                contlowerPrice.setBoundEditor(txtlowerPrice);
//	                //contmiddlePrice
//	                contmiddlePrice.setBoundEditor(txtmiddlePrice);
//	                //conthigherPrice
//	                conthigherPrice.setBoundEditor(txthigherPrice);
//	                //conthighestPrice
//	                conthighestPrice.setBoundEditor(txthighestPrice);
//	                //contbasePrice
//	                contbasePrice.setBoundEditor(txtbasePrice);
//	                //contsecondPrice
//	                contsecondPrice.setBoundEditor(txtsecondPrice);
//	                //continviteType
//	                continviteType.setBoundEditor(prmtinviteType);
//	                //contwinPrice
//	                contwinPrice.setBoundEditor(txtwinPrice);
//	                //contwinUnit
//	                contwinUnit.setBoundEditor(prmtwinUnit);
//	                //contfileNo
//	                contfileNo.setBoundEditor(txtfileNo);
//	                //contquantity
//	                contquantity.setBoundEditor(txtquantity);
//	                //contRemark
//	                contRemark.setBoundEditor(txtRemark);
//	                //contCoopLevel
//	                contCoopLevel.setBoundEditor(comboCoopLevel);
//	                //contPriceType
//	                contPriceType.setBoundEditor(comboPriceType);
//	                //pnlDetail
//	                pnlDetail.getViewport().add(tblDetail, null);
//	                //pnlCost
//	                pnlCost.setLayout(new KDLayout());
//	                //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//	                pnlCost.putClientProperty("OriginalBounds", new Rectangle(0,0,1,1));        tblCost.setBounds(new Rectangle(10, 10, 965, 247));
//	                pnlCost.add(tblCost, new KDLayout.Constraints(10, 10, 965, 247, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
//	                //contExRate
//	                contExRate.setBoundEditor(txtExRate);
//	                //contLocalAmount
//	                contLocalAmount.setBoundEditor(txtLocalAmount);
//	                //contGrtAmount
//	                contGrtAmount.setBoundEditor(txtGrtAmount);
//	                //contCurrency
//	                contCurrency.setBoundEditor(comboCurrency);
//	                //contRespDept
//	                contRespDept.setBoundEditor(prmtRespDept);
//	                //contPayScale
//	                contPayScale.setBoundEditor(txtPayScale);
//	                //contStampTaxRate
//	                contStampTaxRate.setBoundEditor(txtStampTaxRate);
//	                //contStampTaxAmt
//	                contStampTaxAmt.setBoundEditor(txtStampTaxAmt);
//	                //contRespPerson
//	                contRespPerson.setBoundEditor(prmtRespPerson);
//	                //contCreator
//	                contCreator.setBoundEditor(txtCreator);
//	                //contOrg
//	                contOrg.setBoundEditor(txtOrg);
//	                //contProj
//	                contProj.setBoundEditor(txtProj);
//	                //contGrtRate
//	                contGrtRate.setBoundEditor(txtGrtRate);
//	                //kDLabelContainer1
//	                kDLabelContainer1.setBoundEditor(pkbookedDate);
//	                //contIsPartAMaterialCon
//	                contIsPartAMaterialCon.setBoundEditor(cbPeriod);
//	                //conChargeType
//	                conChargeType.setBoundEditor(prmtCharge);
//	                //contAttachmentNameList
//	                contAttachmentNameList.setBoundEditor(comboAttachmentNameList);
//	                //ecoItemPanel
//	        ecoItemPanel.setLayout(new BorderLayout(0, 0));        ecoItemPanel.add(kDContainer1, BorderLayout.CENTER);
//	                //kDContainer1
//	        kDContainer1.getContentPane().setLayout(new BorderLayout(0, 0));        kDContainer1.getContentPane().add(kDSplitPane1, BorderLayout.CENTER);
//	                //kDSplitPane1
//	                kDSplitPane1.add(contPayItem, "top");
//	                kDSplitPane1.add(contBailItem, "bottom");
//	                //contPayItem
//	        contPayItem.getContentPane().setLayout(new BorderLayout(0, 0));        contPayItem.getContentPane().add(tblEconItem, BorderLayout.CENTER);
//	                //contBailItem
//	                contBailItem.getContentPane().setLayout(new KDLayout());
//	                //TODO 由于该容器采用KDLayout布局，请在下面一条语句中修正该容器的初始大小：
//	                contBailItem.getContentPane().putClientProperty("OriginalBounds", new Rectangle(3, 0, 1005, 250));        contBailOriAmount.setBounds(new Rectangle(5, 8, 463, 19));
//	                contBailItem.getContentPane().add(contBailOriAmount, new KDLayout.Constraints(5, 8, 463, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT| KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
//	                contBailRate.setBounds(new Rectangle(544, 8, 450, 19));
//	                contBailItem.getContentPane().add(contBailRate, new KDLayout.Constraints(544, 8, 450, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
//	                tblBail.setBounds(new Rectangle(3, 40, 995, 210));
//	                contBailItem.getContentPane().add(tblBail, new KDLayout.Constraints(3, 40, 995, 210, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
//	                //contBailOriAmount
//	                contBailOriAmount.setBoundEditor(txtBailOriAmount);
//	                //contBailRate
//	                contBailRate.setBoundEditor(txtBailRate);
//	                //lblOverRateContainer
//	                lblOverRateContainer.setBoundEditor(txtOverAmt);
//	                
//	                contCoopLevel.setBoundEditor(comboCoopLevel);
//	                //contPriceType
//	                contPriceType.setBoundEditor(comboPriceType);
//	                //conMainContract
//	                conMainContract.setBoundEditor(prmtMainContract);
//	                //conEffectiveStartDate
//	                conEffectiveStartDate.setBoundEditor(kdpEffectStartDate);
//	                //conEffectiveEndDate
//	                conEffectiveEndDate.setBoundEditor(kdpEffectiveEndDate);
//	                //kDScrollPane1
//	                kDScrollPane1.getViewport().add(txtInformation, null);

    }
}