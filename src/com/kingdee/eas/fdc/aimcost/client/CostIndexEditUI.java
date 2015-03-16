/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ActivityInstInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.io.kds.KDSBookToBook;
import com.kingdee.bos.ctrl.excel.model.struct.Sheet;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.read.POIXlsReader;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.aimcost.ContractPhaseEnum;
import com.kingdee.eas.fdc.aimcost.CostIndexCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexConfigInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.CostIndexEntryTypeEnum;
import com.kingdee.eas.fdc.aimcost.CostIndexFactory;
import com.kingdee.eas.fdc.aimcost.CostIndexInfo;
import com.kingdee.eas.fdc.aimcost.FieldTypeEnum;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.PlanIndexCollection;
import com.kingdee.eas.fdc.aimcost.PlanIndexFactory;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanCollection;
import com.kingdee.eas.fdc.contract.ContractPayPlanEntryInfo;
import com.kingdee.eas.fdc.contract.ContractPayPlanFactory;
import com.kingdee.eas.fdc.contract.ContractPayPlanInfo;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanDateEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryCollection;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanEntryInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanInfo;
import com.kingdee.eas.fdc.finance.ProjectYearPlanEntryInfo;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fi.gl.common.KDSpinnerCellEditor;
import com.kingdee.eas.fi.gr.cslrpt.EntryTypeEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.BgItemObject;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class CostIndexEditUI extends AbstractCostIndexEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(CostIndexEditUI.class);
    private Map kdtEntrys = new HashMap();
    private String path =null;
    public CostIndexEditUI() throws Exception
    {
        super();
    }
	protected void attachListeners() {
		addDataChangeListener(this.prmtInviteType);
		addDataChangeListener(this.txtBuildPrice);
		addDataChangeListener(this.txtTotalBuildArea);
		
		addDataChangeListener(this.txtSaleArea);
		addDataChangeListener(this.txtSalePrice);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtInviteType);
		removeDataChangeListener(this.txtBuildPrice);
		removeDataChangeListener(this.txtTotalBuildArea);
		
		removeDataChangeListener(this.txtSaleArea);
		removeDataChangeListener(this.txtSalePrice);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return CostIndexFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if(!this.editData.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能修改！");
	        SysUtil.abort();
		}
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(!this.editData.isIsLatest()){
			MsgBox.showWarning(this, "非最新版本不能删除！");
	        SysUtil.abort();
		}
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
			super.actionSave_actionPerformed(e);
			actionSubmit_actionPerformed(e);
			
			IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
			ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(this.editData.getContract().getId().toString());

			for (int j = 0; j < procInsts.length; j++) {
				ProcessInstInfo instInfo = procInsts[j];
				if ("open.running".equals(instInfo.getState())) {
					ActivityInstInfo[] actInst=service.getActInstMetaArrayByProcInstId(instInfo.getProcInstId());
					for (int k = 0; k < actInst.length; k++) {
						ActivityInstInfo actInstInfo = actInst[k];
						if ("open.not_running.not_started".equals(actInstInfo.getState())) {
							service.completeActivityInst(actInstInfo.getActInstId());
						}
					}
				}
			}
			ContractSettlementBillCollection col=ContractSettlementBillFactory.getRemoteInstance().getContractSettlementBillCollection("select id from where contractBill.id='"+this.editData.getContract().getId()+"'");
			if(col.size()>0){
				ProcessInstInfo[] selprocInsts = service.getProcessInstanceByHoldedObjectId(col.get(0).getId().toString());
				for (int j = 0; j < selprocInsts.length; j++) {
					ProcessInstInfo instInfo = selprocInsts[j];
					if ("open.running".equals(instInfo.getState())) {
						ActivityInstInfo[] actInst=service.getActInstMetaArrayByProcInstId(instInfo.getProcInstId());
						for (int k = 0; k < actInst.length; k++) {
							ActivityInstInfo actInstInfo = actInst[k];
							if ("open.not_running.not_started".equals(actInstInfo.getState())) {
								service.completeActivityInst(actInstInfo.getActInstId());
							}
						}
					}
				}
			}
		}else{
			super.actionSave_actionPerformed(e);
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		
		actionAudit_actionPerformed(e);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	protected void initControl() {
		this.txtNumber.setMaxLength(255);

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
		
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		this.actionWorkFlowG.setVisible(false);
		this.actionAuditResult.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
		this.menuWorkflow.setVisible(false);
		this.actionAddNew.setVisible(false);
		
		this.btnSubmit.setText("提交&审批");
		this.btnSubmit.setToolTipText("提交&审批");
		this.txtVersion.setPrecision(1);
		
		Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
			this.actionSubmit.setVisible(false);
			this.btnSubmit.setVisible(false);
	    }
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			this.actionImportExcel.setEnabled(false);
			
			if(this.kdtEntrys!=null){
				Object[] key = this.kdtEntrys.keySet().toArray(); 
				for (int k = 0; k < key.length; k++) { 
					KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
					table.setEnabled(false);
				}
			}
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			this.actionImportExcel.setEnabled(true);
			
			if(this.kdtEntrys!=null){
				Object[] key = this.kdtEntrys.keySet().toArray(); 
				for (int k = 0; k < key.length; k++) { 
					KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
					table.setEnabled(true);
				}
			}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		super.storeFields();
		this.editData.getEntry().clear();
		
		if(this.kdtEntrys!=null){
			Object[] key = this.kdtEntrys.keySet().toArray(); 
			Arrays.sort(key); 
			
			List productType=new ArrayList();
			String productTypeStr="";
			for (int k = 0; k < key.length; k++) { 
				KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
				for(int i=0;i<table.getRowCount();i++){
					CostIndexEntryInfo entry=(CostIndexEntryInfo) table.getRow(i).getUserObject();
					if(entry!=null){
						for(int j=0;j<table.getColumnCount();j++){
							Object value=table.getRow(i).getCell(j).getValue();
							if(value!=null){
								if(value instanceof Date){
									entry.put(table.getColumnKey(j), FDCDateHelper.formatDate2((Date) value));
								}else{
									entry.put(table.getColumnKey(j), value.toString());
								}
								CostIndexConfigEntryInfo configEntry=(CostIndexConfigEntryInfo) table.getColumn(j).getUserObject();
								if(configEntry!=null&&FieldTypeEnum.PRODUCTTYPE.equals(configEntry.getFieldType())&&!productType.contains(value.toString())){
									productTypeStr=productTypeStr+value.toString()+";";
									productType.add(value.toString());
								}
							}else{
								entry.put(table.getColumnKey(j),null);
							}
						}
						this.editData.getEntry().add(entry);
					}
				}
			}
			if(!productTypeStr.equals("")){
				this.editData.setProductType(productTypeStr);
			}
		}
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getContract()!=null){
			this.txtContractInfo.setText(this.editData.getContract().getNumber() + " " + editData.getContract().getName());
			this.txtProj.setText(this.editData.getContract().getCurProject().getDisplayName());
			this.txtOrg.setText(this.editData.getContract().getOrgUnit().getDisplayName());
			this.txtAmount.setValue(this.editData.getContract().getAmount());
			try {
				this.txtLastAmount.setValue(FDCUtils.getContractLastAmt (null,this.editData.getContract().getId().toString()));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (UuidException e) {
				e.printStackTrace();
			} 
			if(this.oprtState.equals(OprtState.ADDNEW)){
				if(this.editData.getContract().isHasSettled()){
					this.cbContractPhase.removeItem(ContractPhaseEnum.SIGN);
					this.cbContractPhase.removeItem(ContractPhaseEnum.BUDGET);
					this.cbContractPhase.setEnabled(false);
				}else{
					this.cbContractPhase.removeItem(ContractPhaseEnum.SETTLE);
					this.cbContractPhase.setEnabled(true);
				}
			}
		}
		try {
			loadEntry();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	protected void fetchInitData() throws Exception {
		
	}
	protected void initTable(InviteTypeInfo inviteType) throws BOSException {
		kdtEntrys=null;
		if(inviteType!=null){
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id",inviteType.getId().toString()));
			
			SelectorItemCollection sel = super.getSelectors();
			sel.add("id");
			sel.add("name");
			sel.add("number");
			sel.add("entry.*");
			view.setSelector(sel);
			
			SorterItemCollection sort=new SorterItemCollection();
			sort.add(new SorterItemInfo("number"));
			view.setSorter(sort);
			
			view.setFilter(filter);
			CostIndexConfigCollection col=CostIndexConfigFactory.getRemoteInstance().getCostIndexConfigCollection(view);
			if(col.size()>0){
				kdtEntrys=new HashMap();
			}
			for(int i=0;i<col.size();i++){
				KDTable table=new KDTable();
				kdtEntrys.put(col.get(i).getNumber()+"NUMBER"+col.get(i).getId().toString(),table);
				createTable(col.get(i),table);
			}
		}
	}

	protected void createTable(CostIndexConfigInfo config,KDTable table){
		table.checkParsed();
		table.setUserObject(config);
		IRow headRow=table.addHeadRow();
		for(int i=0;i<config.getEntry().size();i++){
			CostIndexConfigEntryInfo configEntry=config.getEntry().get(i);
			IColumn column=table.addColumn();
			column.setKey("field"+i);
			column.setUserObject(configEntry);
			headRow.getCell("field"+i).setValue(configEntry.getFieldName());
			if(FieldTypeEnum.TEXT.equals(configEntry.getFieldType())){
				KDTextField text=new KDTextField();
				KDTDefaultCellEditor textEditor = new KDTDefaultCellEditor(text);
				table.getColumn("field"+i).setEditor(textEditor);
			}else if(FieldTypeEnum.DIGITAL.equals(configEntry.getFieldType())){
				KDFormattedTextField amount = new KDFormattedTextField();
				amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
				amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
				amount.setPrecision(2);
				KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
				table.getColumn("field"+i).setEditor(amountEditor);
			}else if(FieldTypeEnum.TIME.equals(configEntry.getFieldType())){
				KDDatePicker pk = new KDDatePicker();
				KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
				table.getColumn("field"+i).setEditor(dateEditor);
				table.getColumn("field"+i).getStyleAttributes().setNumberFormat("yyyy-MM-dd");
			}else if(FieldTypeEnum.PRODUCTTYPE.equals(configEntry.getFieldType())){
				KDBizPromptBox f7Box = new KDBizPromptBox(); 
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
				f7Box.setDisplayFormat("$name$");
				f7Box.setEditFormat("$number$");
				f7Box.setCommitFormat("$number$");
				f7Box.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProductTypeQuery");
				f7Editor = new KDTDefaultCellEditor(f7Box);
				table.getColumn("field"+i).setEditor(f7Editor);
			}else if(FieldTypeEnum.UNIT.equals(configEntry.getFieldType())){
				KDBizPromptBox f7Box = new KDBizPromptBox(); 
				KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
				f7Box.setDisplayFormat("$name$");
				f7Box.setEditFormat("$number$");
				f7Box.setCommitFormat("$number$");
				f7Box.setQueryInfo("com.kingdee.eas.basedata.assistant.app.F7MeasureUnitQuery");
				f7Editor = new KDTDefaultCellEditor(f7Box);
				table.getColumn("field"+i).setEditor(f7Editor);
			}
			if(configEntry.isIsHide()){
				table.getColumn("field"+i).getStyleAttributes().setHided(true);
			}
			if(configEntry.isIsRequired()){
				table.getColumn("field"+i).getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
			}
		}
		KDContainer contEntry = new KDContainer();
		contEntry.setName(table.getName());
		contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        
		contEntry.getContentPane().add(table, BorderLayout.CENTER);
		

		KDWorkButton btnImportExcel = new KDWorkButton();
		KDWorkButton btnExportExcel = new KDWorkButton();
		KDWorkButton btnViewConfigAttach = new KDWorkButton();
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionViewConfigAttach.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnViewConfigAttach = (KDWorkButton)contEntry.add(this.actionViewConfigAttach);
		btnViewConfigAttach.setText("文档说明");
		btnViewConfigAttach.setSize(new Dimension(140, 19));
		
		this.actionImportExcel.putValue("SmallIcon", EASResource.getIcon("imgTbtn_input"));
		btnImportExcel = (KDWorkButton)contEntry.add(this.actionImportExcel);
		btnImportExcel.setText("导入");
		btnImportExcel.setSize(new Dimension(140, 19));
		
		this.actionExportExcel.putValue("SmallIcon", EASResource.getIcon("imgTbtn_output"));
		btnExportExcel = (KDWorkButton)contEntry.add(this.actionExportExcel);
		btnExportExcel.setText("导出");
		btnExportExcel.setSize(new Dimension(140, 19));
		
		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)contEntry.add(this.actionALine);
		btnAddRowinfo.setText("新增行");
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText("插入行");
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText("删除行");
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.entryPanel.add(contEntry,config.getName());
	}
	protected void loadEntry() throws Exception{
		this.entryPanel.removeAll();
		initTable((InviteTypeInfo) this.prmtInviteType.getValue());
		if(kdtEntrys!=null){
			CostIndexEntryCollection col=this.editData.getEntry();
			CRMHelper.sortCollection(col, "seq", true);
			
			for(int i=0;i<col.size();i++){
				KDTable entry=(KDTable) kdtEntrys.get(col.get(i).getConfig().getNumber()+"NUMBER"+col.get(i).getConfig().getId().toString());
				if(entry!=null){
					IRow row=entry.addRow();
					row.setUserObject(col.get(i));
					for(int j=0;j<entry.getColumnCount();j++){
						row.getCell(j).setValue(col.get(i).get(entry.getColumnKey(j)));
					}
				}
			}
		}
		
		setOprtState(this.oprtState);
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("contract.id");
    	sel.add("contract.number");
    	sel.add("contract.name");
    	sel.add("contract.curProject.displayName");
    	sel.add("contract.orgUnit.displayName");
    	sel.add("contract.amount");
    	sel.add("contract.hasSettled");
    	sel.add("entry.*");
    	sel.add("entry.config.*");
    	sel.add("productType");
    	sel.add("isLatest");
		return sel;
	}
	protected IObjectValue createNewData() {
		CostIndexInfo info=(CostIndexInfo)this.getUIContext().get("info");
		
		Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
		String contractId=null;
		if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()&&this.getUIContext().get(UIContext.ID)!=null){
			contractId=this.getUIContext().get(UIContext.ID).toString();
        	getUIContext().remove(UIContext.ID);
        	
        	SelectorItemCollection sel=new SelectorItemCollection();
    		sel.add("*");
    		sel.add("orgUnit.*");
        	sel.add("CU.*");
        	sel.add("creator.*");
        	sel.add("auditor.*");
        	sel.add("curProject.*");
        	sel.add("contract.id");
        	sel.add("contract.number");
        	sel.add("contract.name");
        	sel.add("contract.curProject.displayName");
        	sel.add("contract.orgUnit.displayName");
        	sel.add("contract.amount");
        	sel.add("entry.*");
        	sel.add("entry.config.*");
        	sel.add("productType");
        	sel.add("isLatest");
        	sel.add("inviteType.*");
        	
        	EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contract.id",contractId));
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isLatest",Boolean.TRUE));
			view.setFilter(filter);
			view.setSelector(sel);
			try {
				CostIndexCollection col = CostIndexFactory.getRemoteInstance().getCostIndexCollection(view);
				if(col.size()>0){
	        		info=col.get(0);
	        	}
			} catch (BOSException e) {
				e.printStackTrace();
			}
        }else{
        	contractId = (String)getUIContext().get("contractBillId");
        }
		if(info==null){
			info= new CostIndexInfo();
			info.setVersion(1);
			ContractBillInfo contractBillInfo = null;
			try {
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("number");
				sic.add("name");
				sic.add("amount");
				sic.add("curProject.displayName");
				sic.add("orgUnit.displayName");
				sic.add("inviteType.*");
				sic.add("inviteType.*");
				sic.add("hasSettled");
				
				contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractId), sic);	
				
				if(contractBillInfo!=null){
					info.setOrgUnit(contractBillInfo.getOrgUnit());
					info.setContract(contractBillInfo);
					info.setInviteType(contractBillInfo.getInviteType());
					
					if(contractBillInfo.isHasSettled()){
						info.setContractPhase(ContractPhaseEnum.SETTLE);
					}else{
						info.setContractPhase(ContractPhaseEnum.SIGN);
					}
					initTable(contractBillInfo.getInviteType());
					
					EntityViewInfo view=new EntityViewInfo();
					FilterInfo filter=new FilterInfo();
					filter.getFilterItems().add(new FilterItemInfo("project.id",contractBillInfo.getCurProject().getId().toString()));
					filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
					filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
					view.setFilter(filter);
					MeasureCostCollection mcCol=MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
					if(mcCol.size()>0){
						view=new EntityViewInfo();
						filter=new FilterInfo();
						filter.getFilterItems().add(new FilterItemInfo("headID",mcCol.get(0).getId().toString()));
						view.setFilter(filter);
						sic=new SelectorItemCollection();
						sic.add("totalBuildArea");
						sic.add("totalSellArea");
						view.setSelector(sic);
						PlanIndexCollection col=PlanIndexFactory.getRemoteInstance().getPlanIndexCollection(view);
						if(col.size()>0){
							BigDecimal lastAmount=FDCUtils.getContractLastAmt(null,contractBillInfo.getId().toString());
							info.setAmount(col.get(0).getTotalBuildArea());
							info.setBuildPrice(FDCHelper.divide(lastAmount,col.get(0).getTotalBuildArea(),2,BigDecimal.ROUND_HALF_UP));
						
							info.setSaleArea(col.get(0).getTotalSellArea());
							info.setSalePrice(FDCHelper.divide(lastAmount,col.get(0).getTotalSellArea(),2,BigDecimal.ROUND_HALF_UP));
						}
					}
				}else{
					info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
				}
			} catch (Exception e1) {
				handUIException(e1);
			}
		}else{
			info.setVersion(info.getVersion()+1);
			info.setId(null);
			for(int i=0;i<info.getEntry().size();i++){
				info.getEntry().get(i).setId(BOSUuid.create(info.getEntry().get(i).getBOSType()));
			}
		}
		
		info.setState(FDCBillStateEnum.SAVED);
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		info.setBizDate(now);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		info.setCreator(null);
		info.setCreateTime(null);
		info.setAuditor(null);
		info.setAuditTime(null);
		info.setLastUpdateUser(null);
		info.setLastUpdateTime(null);
		info.setIsLatest(false);
		return info;
	}
	private void handleWorkFlow() {
		//重载处理工作流问题，将工作流的参数进行转化
        Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
        if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
        	if(this.getUIContext().get(UIContext.ID)!=null){
        		setOprtState(OprtState.ADDNEW);
        	}
        }
	}
	
	protected void loadData() throws Exception {
		handleWorkFlow();
		super.loadData();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		Boolean isFromWorkflow =(Boolean)getUIContext().get("isFromWorkflow");
		if(isFromWorkflow!=null&&isFromWorkflow.booleanValue()){
			verifyInputForSubmint();
		}else{
			if(isSaveAction()) {
				verifyInputForSave();
				return;
			}
			verifyInputForSubmint();
		}
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTotalBuildArea);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBuildPrice);
		FDCClientVerifyHelper.verifyEmpty(this, this.pkBizDate);
		
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSaleArea);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtSalePrice);
	}
	protected void verifyTable(KDTable table,int k){
		if(table!=null){
			if(table.getRowCount()==0){
				FDCMsgBox.showWarning(this,((CostIndexConfigInfo)table.getUserObject()).getName()+"不能为空！");
				this.entryPanel.setSelectedIndex(k);
				SysUtil.abort();
			}
			IRow headRow=table.getHeadRow(0);
			for (int i = 0; i < table.getRowCount(); i++) {
				IRow row = table.getRow(i);
				for(int j=0;j<table.getColumnCount();j++){
					if(!row.getCell(j).getStyleAttributes().isHided()&&row.getCell(j).getStyleAttributes().getBackground().equals(FDCClientHelper.KDTABLE_COMMON_BG_COLOR)){
						if(row.getCell(j).getValue()!=null&&row.getCell(j).getValue() instanceof String){
							if("".equals(row.getCell(j).getValue().toString().trim())){
								FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+"不能为空！");
								this.entryPanel.setSelectedIndex(k);
								table.getEditManager().editCellAt(i, j);
								SysUtil.abort();
							}
						}else if(row.getCell(j).getValue()==null){
							FDCMsgBox.showWarning(this,headRow.getCell(j).getValue().toString()+"不能为空！");
							this.entryPanel.setSelectedIndex(k);
							table.getEditManager().editCellAt(i, j);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		
		if(this.kdtEntrys!=null){
			Object[] key = this.kdtEntrys.keySet().toArray(); 
			Arrays.sort(key); 
			
			for (int k = 0; k < key.length; k++) { 
				KDTable table = (KDTable) this.kdtEntrys.get(key[k]);
				verifyTable(table,k);
			}
		}
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contract.id",this.editData.getContract().getId().toString()));
    	if(this.editData.getId()!=null){
    		filter.getFilterItems().add(new FilterItemInfo("id",this.editData.getId().toString(),CompareType.NOTEQUALS));
    	}
		filter.getFilterItems().add(new FilterItemInfo("inviteType",((InviteTypeInfo)this.prmtInviteType.getValue()).getId().toString()));
    	SorterItemInfo version=new SorterItemInfo("version");
    	version.setSortType(SortType.DESCEND);
    	view.getSorter().add(version);
    	view.getSelector().add(new SelectorItemInfo("state"));
    	view.getSelector().add(new SelectorItemInfo("version"));
    	view.setFilter(filter);
    	CostIndexCollection col=CostIndexFactory.getRemoteInstance().getCostIndexCollection(view);
    	if(col.size()>0&&!col.get(0).getState().equals(FDCBillStateEnum.AUDITTED)){
    		FDCMsgBox.showWarning(this,"最新造价指标库还未审批！");
    		SysUtil.abort();
    	}else{
    		if(col.size()==0){
    			this.txtVersion.setValue(1);
    		}else{
    			this.txtVersion.setValue(col.get(0).getVersion()+1);
    		}
    	}
	}
	protected KDTable getSelectTable(){
		for(int i=0;i<((KDContainer)this.entryPanel.getSelectedComponent()).getContentPane().getComponentCount();i++){
			if(((KDContainer)this.entryPanel.getSelectedComponent()).getContentPane().getComponent(i) instanceof KDTable){
				return (KDTable) ((KDContainer)this.entryPanel.getSelectedComponent()).getContentPane().getComponent(i);
			}
		}
		return null;
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=getSelectTable();
		IRow row =table.addRow();
		CostIndexEntryInfo entry = new CostIndexEntryInfo();
		entry.setId(BOSUuid.create(entry.getBOSType()));
		entry.setConfig((CostIndexConfigInfo) table.getUserObject());
		row.setUserObject(entry);
	}
	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=getSelectTable();
		IRow row = null;
		if (table.getSelectManager().size() > 0) {
			int top = table.getSelectManager().get().getTop();
			if (isTableColumnSelected(table)) {
				row = table.addRow();
				CostIndexEntryInfo entry = new CostIndexEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setConfig((CostIndexConfigInfo) table.getUserObject());
				row.setUserObject(entry);
			} else {
				row = table.addRow(top);
				CostIndexEntryInfo entry = new CostIndexEntryInfo();
				entry.setId(BOSUuid.create(entry.getBOSType()));
				entry.setConfig((CostIndexConfigInfo) table.getUserObject());
				row.setUserObject(entry);
			}
		} else {
			row = table.addRow();
			CostIndexEntryInfo entry = new CostIndexEntryInfo();
			entry.setId(BOSUuid.create(entry.getBOSType()));
			entry.setConfig((CostIndexConfigInfo) table.getUserObject());
			row.setUserObject(entry);
		}
	}
	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=getSelectTable();
		removeLine(table);
	}
    protected void removeLine(KDTable table) {
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
	protected void prmtInviteType_dataChanged(DataChangeEvent e)throws Exception {
		boolean isChanged = true;
		isChanged = BizCollUtil.isF7ValueChanged(e);
        if(!isChanged){
        	return;
        }
		this.entryPanel.removeAll();
		initTable((InviteTypeInfo) this.prmtInviteType.getValue());
	}
	protected void txtBuildPrice_dataChanged(DataChangeEvent e)throws Exception {
		this.txtTotalBuildArea.setValue(FDCHelper.divide(this.txtLastAmount.getBigDecimalValue(),this.txtBuildPrice.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP),false);
	}
	protected void txtTotalBuildArea_dataChanged(DataChangeEvent e)throws Exception {
		this.txtBuildPrice.setValue(FDCHelper.divide(this.txtLastAmount.getBigDecimalValue(),this.txtTotalBuildArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP),false);
	}
	protected void txtSaleArea_dataChanged(DataChangeEvent e) throws Exception {
		this.txtSalePrice.setValue(FDCHelper.divide(this.txtLastAmount.getBigDecimalValue(),this.txtSaleArea.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP),false);
	}
	protected void txtSalePrice_dataChanged(DataChangeEvent e) throws Exception {
		this.txtSaleArea.setValue(FDCHelper.divide(this.txtLastAmount.getBigDecimalValue(),this.txtSalePrice.getBigDecimalValue(),2,BigDecimal.ROUND_HALF_UP),false);
	}

	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	public void actionViewConfigAttach_actionPerformed(ActionEvent e)throws Exception {
		KDTable table=getSelectTable();
		if(table!=null&&table.getUserObject()!=null){
			CostIndexConfigInfo config=(CostIndexConfigInfo) table.getUserObject();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", config.getId().toString()));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSelector().add(new SelectorItemInfo("attachment.id"));
			evi.setFilter(filter);
			BoAttchAssoCollection cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			if(cols.size()>0&&cols.get(0).getAttachment()!=null){
				getFileGetter();
				fileGetter.viewAttachment(cols.get(0).getAttachment().getId().toString());
				return;
			}
		}
		FDCMsgBox.showWarning(this,"文档说明为空！");
	}
	public void actionExportExcel_actionPerformed(ActionEvent e)throws Exception {
		if(this.prmtInviteType.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择采购类别！");
			return;
		}
		ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();

        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[this.kdtEntrys.size()];
        Object[] key = this.kdtEntrys.keySet().toArray(); 
		Arrays.sort(key);
        for(int i =0;i< key.length;i++){
        	KDTable table = (KDTable) this.kdtEntrys.get(key[i]);
            tablesVO[i] = new KDTables2KDSBookVO(table);
            String title = this.entryPanel.getTitleAt(i);
            title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i].setTableName(title);
        }
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
        
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File(((InviteTypeInfo)this.prmtInviteType.getValue()).getName()+"-造价指标库.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				FDCMsgBox.showInfo("导出成功！");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
	}
	public void actionImportExcel_actionPerformed(ActionEvent e)throws Exception {
		if(this.prmtInviteType.getValue()==null){
			FDCMsgBox.showInfo(this,"请先选择采购类别！");
			return;
		}
		path = SHEHelper.showExcelSelectDlg(this);
		if (path == null) {
			return;
		}
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
			public void afterExec(Object arg0) throws Exception {
				Boolean bol=(Boolean)arg0;
				if(bol){
					FDCMsgBox.showInfo("导入成功！");
				}
			}
			public Object exec() throws Exception {
				boolean bol=importExcelToTable(path,kdtEntrys);
				return bol;
			}
    	}
	    );
	    dialog.show();
	}
	private boolean importExcelToTable(String fileName, Map map) throws Exception {
		KDSBook kdsbook = null;
		try {
			kdsbook = POIXlsReader.parse2(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			FDCMsgBox.showWarning(this,"读EXCEL出错,EXCEl格式不匹配！");
			return false;
		}
		if (kdsbook == null) {
			return false;
		}
		for(int i=0;i<KDSBookToBook.traslate(kdsbook).getSheetCount();i++){
			Sheet excelSheet = KDSBookToBook.traslate(kdsbook).getSheet(i);
			Object[] key = this.kdtEntrys.keySet().toArray(); 
			Arrays.sort(key);
	        for(int j =0;j< key.length;j++){
	        	KDTable table = (KDTable) this.kdtEntrys.get(key[j]);
	            String title = this.entryPanel.getTitleAt(j);
	            if(excelSheet.getSheetName().equals(title)){
	            	Map e_colNameMap = new HashMap();
	        		int e_maxRow = excelSheet.getMaxRowIndex();
	        		int e_maxColumn = excelSheet.getMaxColIndex();
	        		for (int col = 0; col <= e_maxColumn; col++) {
	        			String excelColName = excelSheet.getCell(0, col, true).getText();
	        			e_colNameMap.put(excelColName, new Integer(col));
	        		}
	        		for (int col = 0; col< table.getColumnCount(); col++) {
	        			if (table.getColumn(col).getStyleAttributes().isHided()) {
	        				continue;
	        			}
	        			String colName = (String) table.getHeadRow(0).getCell(col).getValue();
	        			Integer colInt = (Integer) e_colNameMap.get(colName);
	        			if (colInt == null) {
	        				FDCMsgBox.showWarning(this,title+"  表头结构不一致！表格上的关键列:" + colName + "在EXCEL中没有出现！");
	        				return false;
	        			}
	        		}
	        		table.removeRows();
	        		for (int rowIndex = 1; rowIndex <= e_maxRow; rowIndex++) {
	        			IRow row = table.addRow();
	        			CostIndexEntryInfo entry = new CostIndexEntryInfo();
	        			entry.setId(BOSUuid.create(entry.getBOSType()));
	        			entry.setConfig((CostIndexConfigInfo) table.getUserObject());
	        			row.setUserObject(entry);
	        			for (int col = 0; col < table.getColumnCount(); col++) {
	        				if (table.getColumn(col).getStyleAttributes().isHided()) {
		        				continue;
		        			}
	        				ICell tblCell = row.getCell(col);
	        				String colName = (String) table.getHeadRow(0).getCell(col).getValue();
	        				Integer colInt = (Integer) e_colNameMap.get(colName);

	        				if (colInt == null) {
	        					continue;
	        				}
	        				Variant cellRawVal = excelSheet.getCell(rowIndex, colInt.intValue(), true).getValue();
	        				if (Variant.isNull(cellRawVal)) {
	        					continue;
	        				}
	        				String colValue = cellRawVal.toString();
        					tblCell.setValue(colValue);
	        			}
	        		}
	            	break;
	            }
	        }
		}
		return true;
	}
}