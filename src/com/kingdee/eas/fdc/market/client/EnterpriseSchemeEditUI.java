/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.DynamicObjectFactory;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.VerifyInputUtil;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryCollection;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanEntryInfo;
import com.kingdee.eas.fdc.market.EnterprisePlanFactory;
import com.kingdee.eas.fdc.market.EnterprisePlanInfo;
import com.kingdee.eas.fdc.market.EnterpriseSchemeCollection;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryCollection;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryFactory;
import com.kingdee.eas.fdc.market.EnterpriseSchemeEntryInfo;
import com.kingdee.eas.fdc.market.EnterpriseSchemeFactory;
import com.kingdee.eas.fdc.market.EnterpriseSchemeInfo;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanCollection;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanFactory;
import com.kingdee.eas.fdc.market.EnterpriseSellPlanInfo;
import com.kingdee.eas.fdc.market.IsFinishEnum;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.SaleManPromptDialog;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class EnterpriseSchemeEditUI extends AbstractEnterpriseSchemeEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(EnterpriseSchemeEditUI.class);

	/**
	 * output class constructor
	 */
	public EnterpriseSchemeEditUI() throws Exception {
		super();
	}
	protected void attachListeners() {
	}

	protected void detachListeners() {
	}

	protected ICoreBase getBizInterface() throws Exception {
		return EnterpriseSchemeFactory.getRemoteInstance();
	}

	protected KDTable getDetailTable() {
		return kdtEntry;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public void onLoad() throws Exception {
		
		initUI();
		
		super.onLoad();
		
//		this.setPreferredSize(getMaximumSize());
		
		this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.menuBiz.setVisible(false);
		this.menuSubmitOption.setVisible(false);
		
		this.actionAddNew.setVisible(false);
		
		
		prmtOrgUnit.setEnabled(false);
		prmtEnterprisePlan.setEnabled(false);
		prmtSellProject.setEnabled(false);
		comboState.setEnabled(false);
		contThemeName.setEnabled(false);
		contTotalFactAmount.setEnabled(false);
		contTotalContractAmount.setEnabled(false);
		contTotalPayAmount.setEnabled(false);
		txtThemeDescription.setEnabled(false);
		pkStartDate.setRequired(true);
		pkEndDate.setRequired(true);
		
//		SellProjectInfo sellProject = (SellProjectInfo)prmtSellProject.getValue();
//		String curproject = "";
//		
//		SelectorItemCollection sic = new SelectorItemCollection();
//		sic.add("project.id");
//		if(sellProject!=null){
//			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(sellProject.getId()),sic);
//			if(sellProject.getProject()!=null)
//				curproject = sellProject.getProject().getId().toString();
//		}
		/*
		 * 设置分录中合同编号的编辑器为：F7格式编辑器
		 */
		CurProjectCollection pro=CurProjectFactory.getRemoteInstance().getCurProjectCollection("select * from where fullorgunit.id='"+SysContext.getSysContext().getCurrentFIUnit().getId().toString()+"'");
		FilterInfo contractFilter=null;
		if(pro.size()>0){
			contractFilter= new FilterInfo();
			Set id=new HashSet();
			for(int i=0;i<pro.size();i++){
				id.add(pro.get(i).getId().toString());
			}
//			contractFilter.getFilterItems().add(new FilterItemInfo("contractType.parent.parent.name","营销合同"));
			contractFilter.getFilterItems().add(new FilterItemInfo("curProject.id",id,CompareType.INCLUDE));
			contractFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SUBMITTED_VALUE));
			contractFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			contractFilter.setMaskString("#0 and (#1 or #2)");
		}
		ContractPromptDialog contract=new ContractPromptDialog(this,contractFilter);
		KDBizPromptBox box = new KDBizPromptBox();
		KDTDefaultCellEditor f7contractNumber = new KDTDefaultCellEditor(box);
		box.setDisplayFormat("$number$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		box.setSelector(contract);
		box.setEditable(false);
		box.setEnabledMultiSelection(false);
		kdtEntry.getColumn("contractNumber").setEditor(f7contractNumber);
		this.kdtEntry.getColumn("contractNumber").setRenderer(new ObjectValueRender(){
        	public String getText(Object obj) {
        		if(obj instanceof ContractBillInfo){
        			return ((ContractBillInfo)obj).getNumber();
        		}else if(obj instanceof ContractWithoutTextInfo){
        			return ((ContractWithoutTextInfo)obj).getNumber();
        		}
        		return null;
        	}
        }
        );
		txtResult.setMaxLength(5000);
		this.actionRemove.setVisible(false);
		
		this.kdtEntry.getColumn("contractNumber").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("contractName").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("contractAmount").getStyleAttributes().setHided(true);
		this.kdtEntry.getColumn("payAmount").getStyleAttributes().setHided(true);
	}
	
	public void initUI() {
		kdtEntry.checkParsed();
		
		TableUtils.changeTableNumberFormat(kdtEntry, new String[]{"factAmount","payAmount","contractAmount",});
		kdtEntry.getColumn("factAmount").setEditor(TableUtils.getCellNumberEdit());
		kdtEntry.getColumn("payAmount").setEditor(TableUtils.getCellNumberEdit());
		kdtEntry.getColumn("contractAmount").setEditor(TableUtils.getCellNumberEdit());
		
		final KDDatePicker tblMainPlan_startTime_PromptBox = new KDDatePicker();
		tblMainPlan_startTime_PromptBox.setName("tblMainPlan_endTime_PromptBox");
		tblMainPlan_startTime_PromptBox.setVisible(true);
		tblMainPlan_startTime_PromptBox.setEnabled(true);
		KDTDefaultCellEditor tblMainPlan_startTime_CellEditor = new KDTDefaultCellEditor(tblMainPlan_startTime_PromptBox);
		this.kdtEntry.getColumn("startTime").setEditor(tblMainPlan_startTime_CellEditor);
		
	    final KDDatePicker tblMainPlan_endTime_PromptBox = new KDDatePicker();
	    tblMainPlan_endTime_PromptBox.setName("tblMainPlan_endTime_PromptBox");
	    tblMainPlan_endTime_PromptBox.setVisible(true);
	    tblMainPlan_endTime_PromptBox.setEnabled(true);
	    KDTDefaultCellEditor tblMainPlan_endTime_CellEditor = new KDTDefaultCellEditor(tblMainPlan_endTime_PromptBox);
	    this.kdtEntry.getColumn("endTime").setEditor(tblMainPlan_endTime_CellEditor);
	    
	    KDComboBox comboField = new KDComboBox();
        for(int i = 0; i < IsFinishEnum.getEnumList().size(); i++)
        {
            comboField.addItem(IsFinishEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
	    this.kdtEntry.getColumn("isEnd").setEditor(comboEditor);
	    
	    this.contTotalPlanAmount.setEnabled(false);
	    
	    CRMClientHelper.changeTableNumberFormat(kdtEntry, new String[]{"planAmount","factAmount","contractAmount","payAmount"});
		
	}
	public void loadEntry(){
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("subject.*");
		sel.add("classify.*");
		sel.add("mediaName.*");
		BigDecimal planAmount = new BigDecimal("0.00");
		for(int i=0;i<kdtEntry.getRowCount();i++){
			if(IsFinishEnum.FINISH.equals(kdtEntry.getRow(i).getCell("isEnd").getValue())){
				kdtEntry.getRow(i).getCell("startTime").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				kdtEntry.getRow(i).getCell("endTime").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				kdtEntry.getRow(i).getCell("factAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}else{
				kdtEntry.getRow(i).getCell("startTime").getStyleAttributes().setBackground(Color.WHITE);
				kdtEntry.getRow(i).getCell("endTime").getStyleAttributes().setBackground(Color.WHITE);
				kdtEntry.getRow(i).getCell("factAmount").getStyleAttributes().setBackground(Color.WHITE);
			}
			String sellId = (String) kdtEntry.getRow(i).getCell("sellPlanID").getValue();
			try {
		       EnterpriseSellPlanInfo esp=EnterpriseSellPlanFactory.getRemoteInstance().getEnterpriseSellPlanInfo(new ObjectUuidPK(sellId),sel);
		       kdtEntry.getRow(i).getCell("accountView").setValue(esp.getSubject());
               kdtEntry.getRow(i).getCell("channelType").setValue(esp.getClassify());
               kdtEntry.getRow(i).getCell("mediaName").setValue(esp.getMediaName());
               kdtEntry.getRow(i).getCell("planAmount").setValue(esp.getPlanCost());
		       kdtEntry.getRow(i).getCell("contentItem").setValue(esp.getProceeding());
		       
		       if(esp.getPlanCost()!=null){
		    	   planAmount=planAmount.add(esp.getPlanCost());
		       }
		       BOSUuid id=((EnterpriseSchemeEntryInfo)kdtEntry.getRow(i).getUserObject()).getContractNumberID();
		       ObjectUuidPK pk=new ObjectUuidPK(id);
		       if(id!=null){
		    	   IObjectValue objectValue=DynamicObjectFactory.getRemoteInstance().getValue(pk.getObjectType(),pk);
		    	   if(objectValue instanceof ContractBillInfo){
		    		   kdtEntry.getRow(i).getCell("contractNumber").setValue((ContractBillInfo)objectValue);
		    	   }else{
		    		   kdtEntry.getRow(i).getCell("contractNumber").setValue((ContractWithoutTextInfo)objectValue);
		    	   }
		       }
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		this.txtTotalPlanAmount.setValue(planAmount);	
		
		if(this.pkStartDate.getValue()==null&&this.pkEndDate.getValue()==null){
			try {
				setHeadTime();
			} catch (BOSException e) {
				e.printStackTrace();
			}

		}
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();

        int idx = idList.getCurrentIndex();
        if (idx >= 0) {
            billIndex = "(" + (idx + 1) + ")";
        } else {
        	billIndex = "";
        }
		refreshUITitle();
		setAuditButtonStatus(this.getOprtState());
		
		loadEntry();
		
		attachListeners();
	}
	
	/**
	 * 若主题结束已打钩，则下面的所有事项自动全部打钩
	 * */
	protected void chkIsEnd_stateChanged(ChangeEvent e) throws Exception {
		super.chkIsEnd_stateChanged(e);
		if (chkIsEnd.isSelected()) {
			for (int i = 0; i < kdtEntry.getRowCount(); i++) {
				if(IsFinishEnum.UNSTART.equals(kdtEntry.getRow(i).getCell("isEnd").getValue())||kdtEntry.getRow(i).getCell("isEnd").getValue()==null){
					kdtEntry.getRow(i).getCell("isEnd").setValue(IsFinishEnum.FINISH);
					setEndTime(kdtEntry.getRow(i));
				}
			}
		} 
	}
	/**
	 * 若”是否已结束“打钩，则前2字段必填；开始时间默认为计划开始时间，结束时间默认为当天日期；
	 * */
	public void setEndTime(IRow row) throws Exception{
		Date date = null ;
		if(row != null){
			IsFinishEnum isend = (IsFinishEnum)row.getCell("isEnd").getValue();
			if (isend.equals(IsFinishEnum.FINISH)){
				date = FDCCommonServerHelper.getServerTime();
			}
			if(row.getCell("startTime").getValue()==null){
				String getThemeId = (String)row.getCell("sellPlanID").getValue();
				EnterpriseSellPlanInfo sellPlanInfo = EnterpriseSellPlanFactory.getRemoteInstance().getEnterpriseSellPlanInfo(new ObjectUuidPK(getThemeId));
				if(sellPlanInfo!=null){
					row.getCell("startTime").setValue(sellPlanInfo.getStartTime());
				}
			}
			if(row.getCell("endTime").getValue()==null){
				row.getCell("endTime").setValue(date);
			}
		}
		setHeadTime();
	}
	protected void setHeadTime() throws BOSException{
		Date startDate=FDCCommonServerHelper.getServerTime();
		Date endDate=FDCCommonServerHelper.getServerTime();;
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			if(row.getCell("startTime").getValue()!=null&&((Date)row.getCell("startTime").getValue()).before(startDate)){
				startDate=(Date)row.getCell("startTime").getValue();
			}
			if(row.getCell("endTime").getValue()!=null&&((Date)row.getCell("endTime").getValue()).after(endDate)){
				endDate=(Date)row.getCell("endTime").getValue();
			}
		}
		this.pkStartDate.setValue(startDate);
		this.pkEndDate.setValue(endDate);
	}

	/**
	 * 输入结束触发事件:
	 * 结束按钮的联动事件
	 * 实际金额、合同金额、付款金额的合计
	 */
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		String keyName = kdtEntry.getColumnKey(e.getColIndex());
		if ("isEnd".equals(keyName)) {
			if(IsFinishEnum.FINISH.equals(kdtEntry.getRow(rowIndex).getCell(keyName).getValue())){
				kdtEntry.getRow(rowIndex).getCell("startTime").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				kdtEntry.getRow(rowIndex).getCell("endTime").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
				kdtEntry.getRow(rowIndex).getCell("factAmount").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}else{
				kdtEntry.getRow(rowIndex).getCell("startTime").getStyleAttributes().setBackground(Color.WHITE);
				kdtEntry.getRow(rowIndex).getCell("endTime").getStyleAttributes().setBackground(Color.WHITE);
				kdtEntry.getRow(rowIndex).getCell("factAmount").getStyleAttributes().setBackground(Color.WHITE);
			}
			setEndTime(kdtEntry.getRow(rowIndex));
		}else if ("factAmount".equals(keyName)) {
			BigDecimal	factAmount = calculate("factAmount");
			this.txtTotalFactAmount.setValue(factAmount);
		}else if ("payAmount".equals(keyName)) {
			BigDecimal	payAmount = calculate("payAmount");
			this.txtTotalPayAmount.setValue(payAmount);
		}else if ("contractAmount".equals(keyName)) {
			BigDecimal	contractAmount=calculate("contractAmount");
			this.txtTotalContractAmount.setValue(contractAmount);
		}else if("contractNumber".equals(keyName)){
			Object value = (Object)kdtEntry.getRow(rowIndex).getCell("contractNumber").getValue();
			if(value!=null){
				if(value instanceof ContractBillInfo){
					kdtEntry.getRow(rowIndex).getCell("contractNumber").setValue((ContractBillInfo)value);
					kdtEntry.getRow(rowIndex).getCell("contractName").setValue(((ContractBillInfo)value).getName());
					kdtEntry.getRow(rowIndex).getCell("contractAmount").setValue(((ContractBillInfo)value).getAmount());
					kdtEntry.getRow(rowIndex).getCell("payAmount").setValue(getPayAmount(((ContractBillInfo)value).getId().toString()));
					((EnterpriseSchemeEntryInfo)kdtEntry.getRow(rowIndex).getUserObject()).setContractNumberID(((ContractBillInfo)value).getId());
				}else{
					kdtEntry.getRow(rowIndex).getCell("contractNumber").setValue((ContractWithoutTextInfo)value);
					kdtEntry.getRow(rowIndex).getCell("contractName").setValue(((ContractWithoutTextInfo)value).getName());
					kdtEntry.getRow(rowIndex).getCell("contractAmount").setValue(((ContractWithoutTextInfo)value).getAmount());
					kdtEntry.getRow(rowIndex).getCell("payAmount").setValue(getPayAmount(((ContractWithoutTextInfo)value).getId().toString()));
					((EnterpriseSchemeEntryInfo)kdtEntry.getRow(rowIndex).getUserObject()).setContractNumberID(((ContractWithoutTextInfo)value).getId());
				}
				
			}else{
				kdtEntry.getRow(rowIndex).getCell("contractName").setValue(null);
				kdtEntry.getRow(rowIndex).getCell("contractAmount").setValue(FDCHelper.ZERO);
				kdtEntry.getRow(rowIndex).getCell("payAmount").setValue(FDCHelper.ZERO);
				((EnterpriseSchemeEntryInfo)kdtEntry.getRow(rowIndex).getUserObject()).setContractNumberID(null);
			}
			BigDecimal	contractAmount=calculate("contractAmount");
			this.txtTotalContractAmount.setValue(contractAmount);
			
			BigDecimal	payAmount = calculate("payAmount");
			this.txtTotalPayAmount.setValue(payAmount);
		}else if("startTime".equals(keyName)){
			Date startDate=(Date) kdtEntry.getRow(rowIndex).getCell("startTime").getValue();
			Date endDate=(Date) kdtEntry.getRow(rowIndex).getCell("endTime").getValue();
			if(startDate!=null&&endDate!=null){
				if(startDate.compareTo(endDate)>0){
					kdtEntry.getRow(rowIndex).getCell("startTime").setValue(null);
					FDCMsgBox.showWarning(this,"实际结束日期不能小于实际开始日期！");
					SysUtil.abort();
				}
			}
			setHeadTime();
		}else if("endTime".equals(keyName)){
			Date startDate=(Date) kdtEntry.getRow(rowIndex).getCell("startTime").getValue();
			Date endDate=(Date) kdtEntry.getRow(rowIndex).getCell("endTime").getValue();
			if(startDate!=null&&endDate!=null){
				if(startDate.compareTo(endDate)>0){
					kdtEntry.getRow(rowIndex).getCell("endTime").setValue(null);
					FDCMsgBox.showWarning(this,"实际结束日期不能小于实际开始日期！");
					SysUtil.abort();
				}
			}
			setHeadTime();
		}
	}
	private BigDecimal getPayAmount(String id) throws BOSException{
		BigDecimal amount=FDCHelper.ZERO;
		IPaymentBill ipb = PaymentBillFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
        FilterInfo filter = new FilterInfo();
        filter.getFilterItems().add(new FilterItemInfo("contractBillId", id));
        filter.getFilterItems().add(new FilterItemInfo("billStatus", BillStatusEnum.PAYED));
        evi.setFilter(filter);
        evi.getSelector().add(new SelectorItemInfo("actPayLocAmt"));
        PaymentBillCollection pbc = ipb.getPaymentBillCollection(evi);
        if(pbc != null && pbc.size() != 0)
        {
            for(int i = 0; i < pbc.size(); i++)
            {
            	amount=amount.add(pbc.get(i).getActPayLocAmt());
            }

        }
        return amount;
	}
	/**
	 * 计算金额
	 * @param name 金额名
	 * @return 总额
	 */
	private BigDecimal calculate(String name) {
		BigDecimal amount = new BigDecimal(0);
		for (int i = 0; i < kdtEntry.getRowCount(); i++) {
			// 判断不为空
			if (this.kdtEntry.getRow(i).getCell(name).getValue() != null)
				amount = amount.add((BigDecimal) this.kdtEntry.getRow(i).getCell(name).getValue());
		}
		return amount;
	}
	
	/**
	 * 初始化界面数据
	 */
	protected IObjectValue createNewData() {
		EnterpriseSchemeInfo info = new EnterpriseSchemeInfo();
		String enterprisePlanEntryId = (String) getUIContext().get("enterprisePlanEntryId");
			if(enterprisePlanEntryId!=null){
			try {
				SelectorItemCollection sel=new SelectorItemCollection();
				sel.add("*");
				sel.add("sellPlanEntry.id");
				sel.add("sellPlanEntry.esEntryId");
				sel.add("parent.CU.*");
				sel.add("parent.sellProject.*");
				sel.add("parent.*");
				EnterprisePlanEntryInfo entry = EnterprisePlanEntryFactory.getRemoteInstance().getEnterprisePlanEntryInfo(new ObjectUuidPK(enterprisePlanEntryId),sel);
			
				EnterpriseSellPlanCollection col=entry.getSellPlanEntry();
				for(int i=0;i<col.size();i++){
					EnterpriseSellPlanInfo sellPlan=col.get(i);
					String eseId = sellPlan.getId().toString();
					
					EntityViewInfo scEnv = new EntityViewInfo();
					FilterInfo scFilter = new FilterInfo();
					scFilter.getFilterItems().add(new FilterItemInfo("sellPlanID",eseId));
					scEnv.setFilter(scFilter);
					
					EnterpriseSchemeEntryCollection enCol=EnterpriseSchemeEntryFactory.getRemoteInstance().getEnterpriseSchemeEntryCollection(scEnv);
					if(enCol.size()>0){
						info=EnterpriseSchemeFactory.getRemoteInstance().getEnterpriseSchemeInfo(new ObjectUuidPK(enCol.get(0).getParent().getId()),this.getSelectors());
						if(info.getState().equals(FDCBillStateEnum.AUDITTED)){
							FDCMsgBox.showWarning(this,"关联企划实施已审批！");
							SysUtil.abort();
						}
					}
					boolean isAdd=true;
					for(int j=0;j<info.getEntry().size();j++){
						if(eseId.equals(info.getEntry().get(j).getSellPlanID())){
							isAdd=false;
						}
					}
					if(isAdd){
						if(sellPlan.getEsEntryId()==null){
							EnterpriseSchemeEntryInfo entryInfo = new EnterpriseSchemeEntryInfo();
					        entryInfo.setSellPlanID(eseId);
					        entryInfo.setIsEnd(IsFinishEnum.UNSTART);
						  	info.getEntry().add(entryInfo);
						}else{
							EnterpriseSchemeEntryInfo entryInfo=EnterpriseSchemeEntryFactory.getRemoteInstance().getEnterpriseSchemeEntryInfo(new ObjectUuidPK(sellPlan.getEsEntryId()));
							entryInfo.setSellPlanID(eseId);
							entryInfo.setId(null);
							info.getEntry().add(entryInfo);
						}
					}
			    }
				info.setSellProject(entry.getParent().getSellProject());
				info.setEnterprisePlan(entry.getParent());
				info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
				info.setThemeName(entry.getTheme());
				info.setThemeDescription(entry.getDescribe());
			} catch (Exception e) { 
				e.printStackTrace();
				SysUtil.abort();
			}
			if(info.getId()==null){
				info.setState(FDCBillStateEnum.SAVED);
			}else{
				this.setOprtState(STATUS_EDIT);
			}
		}
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		return info;
	}
	public SelectorItemCollection getSelectors(){
		SelectorItemCollection sel=super.getSelectors();
		sel.add("CU.*");
		sel.add("entry.contractNumberID");
		return sel;
	}
	protected void verifyInputForSave() throws Exception {
		if(getNumberCtrl().isEnabled()) {
			VerifyInputUtil.verifyNull(this, txtNumber,"企划实施编号" );
		}
	}
	/**
	 * 设置在提交时检验不能为空
	 */
	public void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		
		VerifyInputUtil.verifyNull(this, pkStartDate, "开始日期");
		VerifyInputUtil.verifyNull(this, pkEndDate, "结束日期");
		
		Date startDate=(Date)pkStartDate.getValue();
		Date endDate=(Date)pkEndDate.getValue();
		if(startDate!=null&&endDate!=null){
			if(startDate.compareTo(endDate)>0){
				FDCMsgBox.showWarning(this,"结束日期不能小于开始日期！");
				SysUtil.abort();
			}
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			if(IsFinishEnum.FINISH.equals(row.getCell("isEnd").getValue())){
				BigDecimal amount = (BigDecimal)row.getCell("factAmount").getValue();
				if (amount == null){
					FDCMsgBox.showWarning(this,"实际金额不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("factAmount"));
					SysUtil.abort();
				}
				if (amount.compareTo(FDCHelper.ZERO)==0){
					FDCMsgBox.showWarning(this,"实际金额不能为0！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("factAmount"));
					SysUtil.abort();
				}
				if (row.getCell("startTime").getValue() == null){
					FDCMsgBox.showWarning(this,"实际开始日期不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("startTime"));
					SysUtil.abort();
				}
				if (row.getCell("endTime").getValue() == null){
					FDCMsgBox.showWarning(this,"实际结束日期不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("endTime"));
					SysUtil.abort();
				}
			}
		}
	}
	
	protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		super.kdtEntry_tableClicked(e);
		if(e.getColIndex() == kdtEntry.getColumnIndex("contentItem"))
		{
			this.showDialog(this, kdtEntry, 250, 100, 500,this.getOprtState());
		}
	}
	public void showDialog(JComponent owner, KDTable table, int X, int Y, int len,String state)
    {
        int rowIndex = table.getSelectManager().getActiveRowIndex();
        int colIndex = table.getSelectManager().getActiveColumnIndex();
        ICell cell = table.getCell(rowIndex, colIndex);
        BasicView view = table.getViewManager().getView(5);
        Point p = view.getLocationOnScreen();
        Rectangle rect = view.getCellRectangle(rowIndex, colIndex);
        java.awt.Window parent = SwingUtilities.getWindowAncestor(owner);
        KDDetailedAreaDialog dialog;
        if(parent instanceof Dialog)
        {
            dialog = new KDDetailedAreaDialog((Dialog)parent, X, Y, true);
        }
        else
        if(parent instanceof Frame){
            dialog = new KDDetailedAreaDialog((Frame)parent, X, Y, true);
        }
        else{
            dialog = new KDDetailedAreaDialog(true);
        }
        String vals = cell.getValue().toString();
        dialog.setData(vals);
        dialog.setPRENTE_X(p.x + rect.x + rect.width);
        dialog.setPRENTE_Y(p.y + rect.y);
        dialog.setMaxLength(len);
        dialog.setEditable(false);
        dialog.show();
    }
    

    public static void setWrapFalse(ICell cell)
    {
        if(cell != null)
            cell.getStyleAttributes().setWrapText(true);
    }
    
    protected void setThisCellEditor(KDTable table, String colName, boolean isRequired, int maxLength)
    {
        KDDetailedArea area = new KDDetailedArea(280, 250);
        area.setRequired(isRequired);
        area.setEnabled(true);
        area.setMaxLength(maxLength);
        table.getColumn(colName).setEditor(new KDTDefaultCellEditor(area));
    }
   
}