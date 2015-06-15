/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractEvaluationEntryInfo;
import com.kingdee.eas.fdc.contract.EvaluationTypeCollection;
import com.kingdee.eas.fdc.contract.EvaluationTypeFactory;
import com.kingdee.eas.fdc.contract.EvaluationTypeInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.ContractEvaluationFactory;
import com.kingdee.eas.fdc.contract.ContractEvaluationInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ContractEvaluationEditUI extends AbstractContractEvaluationEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ContractEvaluationEditUI.class);
    public ContractEvaluationEditUI() throws Exception
    {
        super();
    }
    protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ContractEvaluationFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
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
	protected void initControl() throws EASBizException, BOSException {
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
		
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
//		this.actionWorkFlowG.setVisible(false);
//		this.actionAuditResult.setVisible(false);
		
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPre.setVisible(false);
		this.menuView.setVisible(false);
		
		this.menuBiz.setVisible(false);
//		this.menuWorkflow.setVisible(false);
		this.actionAddNew.setVisible(false);
		
//		this.btnSubmit.setText("提交&审批");
//		this.btnSubmit.setToolTipText("提交&审批");
//		this.txtVersion.setPrecision(1);
		
//		String cuId=SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
//    	Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
//		if (param == null) {
//			param = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo().getId().toString());
//		}
//		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
//			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
//		}
//		FDCClientUtils.setRespDeptF7(prmtRespDept, this,canSelectOtherOrgPerson ? null : cuId);
//		FDCClientUtils.setPersonF7(prmtRespPerson, this,canSelectOtherOrgPerson ? null : cuId);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
		}
	}
	public void onLoad() throws Exception {
		this.kdtEntry.checkParsed();
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$longNumber$");
		f7Box.setCommitFormat("$longNumber$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.contract.app.EvaluationTypeQuery");
		f7Editor = new KDTDefaultCellEditor(f7Box);
		this.kdtEntry.getColumn("type").setEditor(f7Editor);
		
		super.onLoad();
		initControl();
	}
	public void storeFields(){
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		if(this.editData.getContract()!=null){
			
			try {
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", editData.getContract().getId().toString());
				ContractBillEditUI ui = (ContractBillEditUI) UIFactoryHelper.initUIObject(ContractBillEditUI.class.getName(), uiContext, null,OprtState.VIEW);
				this.contract.setViewportView(ui);
				this.contract.setKeyBoardControl(true);
				this.contract.setEnabled(false);
			} catch (UIException e) {
				e.printStackTrace();
			}
		}
		
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			EvaluationTypeInfo type=(EvaluationTypeInfo) row.getCell("type").getValue();
			if(type!=null){
				if(type.getLevel()==1){
					row.getStyleAttributes().setLocked(true);
					row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_TOTAL_BG_COLOR);
					row.getStyleAttributes().setBold(true);
				}else{
					KDBizPromptBox f7Box = new KDBizPromptBox(); 
					KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
					f7Box.setDisplayFormat("$name$");
					f7Box.setEditFormat("$number$");
					f7Box.setCommitFormat("$number$");
					f7Box.setQueryInfo("com.kingdee.eas.fdc.contract.app.EvaluationResultQuery");
					f7Editor = new KDTDefaultCellEditor(f7Box);
					
					Set idSet=new HashSet();
					for(int j=0;j<type.getEntry().size();j++){
						idSet.add(type.getEntry().get(j).getResult().getId().toString());
					}
					EntityViewInfo view=new EntityViewInfo();
					FilterInfo filter=new FilterInfo();
					if(idSet.size()>0){
						filter.getFilterItems().add(new FilterItemInfo("id",idSet,CompareType.INCLUDE));
					}else{
						filter.getFilterItems().add(new FilterItemInfo("id","'null'"));
					}
					view.setFilter(filter);
					f7Box.setEntityViewInfo(view);
					row.getCell("result").setEditor(f7Editor);
				}
			}else{
				row.getStyleAttributes().setLocked(true);
			}
		}
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	protected void fetchInitData() throws Exception {
		
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sel = super.getSelectors();
		sel.add("orgUnit.*");
    	sel.add("CU.*");
    	sel.add("state");
    	sel.add("contract.*");
    	sel.add("entry.type.entry.result.*");
    	sel.add("entry.type.*");
		return sel;
	}
	protected IObjectValue createNewData() {
		ContractEvaluationInfo info=(ContractEvaluationInfo)this.getUIContext().get("info");
		if(info==null){
			info= new ContractEvaluationInfo();
			String contractBillId = (String)getUIContext().get("contractBillId");
		
			ContractBillInfo contractBillInfo = null;
			try {
				SelectorItemCollection sic=new SelectorItemCollection();
				sic.add("*");
				sic.add("curProject.displayName");
				sic.add("partB.name");
				contractBillInfo = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(contractBillId), sic);	
			} catch (Exception e1) {
				handUIException(e1);
			}
			if(contractBillInfo!=null){
				info.setOrgUnit(contractBillInfo.getOrgUnit());
				info.setContract(contractBillInfo);
			}else{
				FDCMsgBox.showWarning(this,"合同为空！");
	    		SysUtil.abort();
			}
			try {
				EvaluationTypeCollection col = EvaluationTypeFactory.getRemoteInstance().getEvaluationTypeCollection("select entry.*,* from where isEnabled=1 order by longNumber");
				for(int i=0;i<col.size();i++){
					ContractEvaluationEntryInfo entry=new ContractEvaluationEntryInfo();
					entry.setType(col.get(i));
					info.getEntry().add(entry);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}else{
			info.setId(null);
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
		return info;
	}
	protected void verifyInputForSave() throws Exception{
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"评估分录不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			if(row.getCell("type").getValue()==null){
				FDCMsgBox.showWarning(this,"评估类型不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("type"));
				SysUtil.abort();
			}
			EvaluationTypeInfo type=(EvaluationTypeInfo) row.getCell("type").getValue();
			if(type.getLevel()!=1&&row.getCell("result").getValue()==null){
				FDCMsgBox.showWarning(this,"评估结果不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("result"));
				SysUtil.abort();
			}
		}
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
//		ArrayList idList = new ArrayList();
//		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
//			idList.add(editData.getString("id"));
//		}
//		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
//				|| getTDFileName() == null) {
//			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
//			return;
//		}
//		ContractEvaluationPrintProvider data = new ContractEvaluationPrintProvider(
//				editData.getString("id"), getTDQueryPK(),this.txtTotalAmount.getBigDecimalValue(),this.txtGCL.getBigDecimalValue());
//		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
//		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
//				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
//		ArrayList idList = new ArrayList();
//		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
//			idList.add(editData.getString("id"));
//		}
//		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
//				|| getTDFileName() == null) {
//			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
//			return;
//		}
//		ContractEvaluationPrintProvider data = new ContractEvaluationPrintProvider(
//				editData.getString("id"), getTDQueryPK(),this.txtTotalAmount.getBigDecimalValue(),this.txtGCL.getBigDecimalValue());
//		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
//		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
//				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractEvaluation";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.finance.app.ContractEvaluationQuery");
	}
}