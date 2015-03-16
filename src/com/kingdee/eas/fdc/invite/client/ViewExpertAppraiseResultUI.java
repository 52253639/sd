/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessDefInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.biz.agent.DefaultMultiBillWorkAgent;
import com.kingdee.bos.workflow.biz.agent.IMultiBillWorkAgent;
import com.kingdee.bos.workflow.define.ProcessDef;
import com.kingdee.bos.workflow.monitor.client.BasicShowWfDefinePanel;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.workflow.util.ApplicationUtil;
import com.kingdee.eas.base.multiapprove.client.DesignatePerformerUI;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.assistant.util.MultiDataSourceDataProviderProxy;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.AppraiseTemplateEntryInfo;
import com.kingdee.eas.fdc.invite.AppraiseTemplateInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseEntryInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseInfo;
import com.kingdee.eas.fdc.invite.ExpertAppraiseResultFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseResultInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.workflow.WfClientUtils;
import com.kingdee.eas.framework.util.CommonDataProvider;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 专家评标结果汇总
 */
public class ViewExpertAppraiseResultUI extends AbstractExpertAppraiseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ViewExpertAppraiseResultUI.class);
    private Map initData = null;
    protected com.kingdee.eas.fdc.invite.ExpertAppraiseResultInfo editData = null;
    
    private boolean isUseWeight = true;
    /**
     * output class constructor
     */
    public ViewExpertAppraiseResultUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
//        super.storeFields();
//        storeLineFields(null,null,null);
    }

    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
//        super.actionSubmit_actionPerformed(e);
//    	if (editData.getBOSType() == null) {
//			return;
//		}
//    	
//
//		IEnactmentService service = EnactmentServiceFactory
//				.createRemoteEnactService();
//		IObjectValue bizObj = AgentUtility.getNoAgentValue(this.editData);
//		storeEditData4WfPreview();
//		String procDefID = service.findSubmitProcDef(SysContext.getSysContext()
//				.getCurrentUserInfo().getId().toString(), bizObj, this
//				.getWFUIFuctionName(), this.getWFActionName());
//
//		if (procDefID != null) {
			if(editData!=null&&editData.getId()!=null)
	    	{
	    		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
	    	}
	    	if(FDCBillStateEnum.AUDITTED.equals(editData.getState())){
	    		if(MsgBox.OK==FDCMsgBox.showConfirm2(this, "专家评标结果汇总已经审批！是否重新提交工作流审批？")){
	    			IObjectPK pk =ExpertAppraiseResultFactory.getRemoteInstance().submit(editData);
	    			editData.setId(BOSUuid.read(pk.toString()));
	    			showMessageForStatus();
	    	        showSubmitSuccess();
	    		}else{
	    			return;
	    		}
	    	}
	    	if(editData!=null)
	    	{
	    		IObjectPK pk = ExpertAppraiseResultFactory.getRemoteInstance().submit(editData);
	    		editData.setId(BOSUuid.read(pk.toString()));
	    		showMessageForStatus();
	            showSubmitSuccess();
	    	}
	    	
//		} else {
//			MsgBox.showInfo(this, EASResource
//					.getString(FrameWorkClientUtils.strResource
//							+ "Msg_WFHasNotDef"));
//		}
    	
    	
    	
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
        if(editData!=null)
    	{
    		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
    	}

        if(editData!=null&&editData.getId()!=null){
        	IObjectPK pk = new ObjectUuidPK(this.editData.getId());
            if (confirmRemove())
            {
            	boolean bool=UtilRequest.isPrepare("ActionRemove",this);
            	if (bool)
            	{
            		prepareRomove(null).callHandler();
            	}
                removeByPK(pk);
            }
        }
    }   

    /**
     * output actionMultiapprove_actionPerformed
     */
    public void actionMultiapprove_actionPerformed(ActionEvent e) throws Exception
    {
    	String userID = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
		String[] boID = new String[1];
		boID[0] = this.editData.getId() != null? this.editData.getId().toString() : null;
		if (boID[0] == null) {
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_WFHasNotInstance"));
			this.abort();
		}
		MultiApproveUtil.multiapproveBills(this, userID, boID);
    }

    /**
     * output actionNextPerson_actionPerformed
     */
    public void actionNextPerson_actionPerformed(ActionEvent e) throws Exception
    {
    	if (editData == null || editData.getBOSType() == null) {
			return ;
		}
		IObjectValue bizObj = AgentUtility.getNoAgentValue(this.editData);
		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		// 该方法有副作用,会修改editData对象的值, By Michael Pan 2007/06/11
		storeEditData4WfPreview();
		String procDefID = service.findSubmitProcDef(SysContext.getSysContext()
				.getCurrentUserInfo().getId().toString(), bizObj, this
				.getWFUIFuctionName(), this.getWFActionName());

    	if (procDefID == null) {
			MsgBox.showInfo(EASResource.getString("com.kingdee.eas.framework.FrameWorkResource",
					"Msg_NoBindingProcess"));
			return;
		}

		String boID = this.editData.getId() != null? this.editData.getId().toString() : null;
		ProcessDefInfo procDefInfo = service.getProcessDefInfo(procDefID);
		ProcessDef procDef = ApplicationUtil
				.getProcessDefByDefHashValue(procDefInfo.getMd5HashValue());
		if (boID == null) {
			storeEditData4WfPreview();
			DesignatePerformerUI.process(this, this.editData,
					procDef, getWFUIFuctionName(),
					getWFActionName(), null);
		} else {
			ProcessInstInfo[] procInsts = EnactmentServiceFactory
					.createRemoteEnactService()
					.getProcessInstanceByHoldedObjectId(boID);
			if (procInsts == null || procInsts.length == 0
					|| procInsts[0] == null) {
				DesignatePerformerUI.process(this, this.editData,
						procDef, getWFUIFuctionName(),
						getWFActionName(), null);
				// // 如果不在流程中
			} else {
				DesignatePerformerUI.processByBussinessObjId(this, boID, null);
			}
		}
    }

    /**
     * output actionStartWorkFlow_actionPerformed
     */
    public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionStartWorkFlow_actionPerformed(e);
    }

    /**
     * output actionVoucher_actionPerformed
     */
    public void actionVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionVoucher_actionPerformed(e);
    }

    /**
     * output actionDelVoucher_actionPerformed
     */
    public void actionDelVoucher_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionDelVoucher_actionPerformed(e);
    }

    /**
     * output actionCreateTo_actionPerformed
     */
    public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCreateTo_actionPerformed(e);
    }

    /**
     * output actionSendingMessage_actionPerformed
     */
    public void actionSendingMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendingMessage_actionPerformed(e);
    }

    /**
     * output actionSignature_actionPerformed
     */
    public void actionSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSignature_actionPerformed(e);
    }

    
    /**
     * output actionViewSignature_actionPerformed
     */
    public void actionViewSignature_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionViewSignature_actionPerformed(e);
    }


	
	protected KDTable getDetailTable() {
		
		return this.kdtEntry;
	}

	
	public SelectorItemCollection getSelectors() {
		
		SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("auditor.*"));
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("inviteProject.*"));
        sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
        sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));
        sic.add(new SelectorItemInfo("inviteProject.name"));
        sic.add(new SelectorItemInfo("inviteProject.project.name"));
        sic.add(new SelectorItemInfo("inviteProject.appraiseTemplate.templateType.name"));
        sic.add(new SelectorItemInfo("inviteProject.appraiseTemplate.number"));
        sic.add(new SelectorItemInfo("inviteProject.appraiseTemplate.name"));
        sic.add(new SelectorItemInfo("createTime"));
        
        
		return sic;
	}

	
	protected ICoreBase getBizInterface() throws Exception {
		
		return ExpertAppraiseResultFactory.getRemoteInstance();
	}

	
	protected void initWorkButton() {
		
		super.initWorkButton();
		this.actionAddNew.setVisible(false);
		this.actionSave.setVisible(false);
//		this.actionSubmit.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionReset.setVisible(false);
//		this.actionAuditResult.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionInsertLine.setVisible(false);
		this.actionAddLine.setVisible(false);
		this.actionRemoveLine.setVisible(false);
//		this.actionWorkFlowG.setVisible(false);
//		this.actionWorkflowList.setVisible(false);
		this.actionNext.setVisible(false);
//		this.actionNextPerson.setVisible(false);
		this.actionAttachment.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionCreateFrom.setVisible(false);
//		this.actionMultiapprove.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		
	}



	
	private void fetchInitData() throws EASBizException, BOSException{
		if(initData!=null)
			return;
		Map param = new HashMap();
		
		String inviteProjectId = (String)this.getUIContext().get(UIContext.ID);
		param.put(UIContext.ID,inviteProjectId);
		if(this.getUIContext().containsKey("isFromWorkflow")&&((Boolean)getUIContext().get("isFromWorkflow")).booleanValue()){
			param.put("resultID", inviteProjectId);
			if(getUIContext().containsKey("DATAOBJECTS")){
				HashMap objects = (HashMap)getUIContext().get("DATAOBJECTS");
				editData = (ExpertAppraiseResultInfo)objects.get("billInfo");
				param.put(UIContext.ID,editData.getInviteProject().getId().toString());
			}
		}
		
		initData = ExpertAppraiseFactory.getRemoteInstance().fetchInitSumData(param);
		
		SupplierQualifyInfo supplierQualify = (SupplierQualifyInfo)initData.get("supplierQualify");
		AppraiseTemplateInfo template = (AppraiseTemplateInfo)initData.get("appraiseTemplate"); 
		ExpertAppraiseCollection expertAppCols = (ExpertAppraiseCollection)initData.get("auditExpert");
		 
		
		if(supplierQualify == null || template == null || expertAppCols == null)
		{
			abort();
		}
		
		if(initData.containsKey("resultInfo")){
			editData = (ExpertAppraiseResultInfo)initData.get("resultInfo");
		}else{
			editData = (ExpertAppraiseResultInfo) this.createNewData();
			editData.setInviteProject((InviteProjectInfo)initData.get("inviteProject"));
			editData.setName(editData.getInviteProject().getName());
			editData.setNumber(editData.getInviteProject().getNumber());
		}
	}
	public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
			throws Exception {
		if (editData.getBOSType() == null) {
			return;
		}

		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		IObjectValue bizObj = AgentUtility.getNoAgentValue(this.editData);
		storeEditData4WfPreview();
		String procDefID = service.findSubmitProcDef(SysContext.getSysContext()
				.getCurrentUserInfo().getId().toString(), bizObj, this
				.getWFUIFuctionName(), this.getWFActionName());

		if (procDefID != null) {
			// 显示UI
			ProcessDefInfo processDefInfo = service
					.getProcessDefInfo(procDefID);
			Locale currentLocale = SysContext.getSysContext().getLocale();
			ProcessDef processDef = service
					.getProcessDefByDefineHashValue(processDefInfo
							.getMd5HashValue());
			String procDefDiagramTitle = "";
			if (processDef != null) {
				procDefDiagramTitle = processDef.getName(currentLocale);
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("define", processDef);
			uiContext.put("title", procDefDiagramTitle);
			// xichu_yu 20081023 最大化打开
			BasicShowWfDefinePanel.Show(uiContext);
		} else {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotDef"));
		}
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		String selectId = null;
		if (editData.getId() != null) {
			selectId = editData.getId().toString();
		}
		IEnactmentService service2 = EnactmentServiceFactory
				.createRemoteEnactService();
		List openProcInsts = new ArrayList();
		ProcessInstInfo[] procInsts = service2
				.getProcessInstanceByHoldedObjectId(selectId);
		if (procInsts == null || procInsts.length == 0) {
			// 首先查看是否有ID，有ID则认为是以前单据，没有则认为是新增
			if (!StringUtils.isEmpty(selectId)) {
				// 如果没有运行时流程，判断是否有可以展现的流程图并展现
				procInsts = service2.getAllProcessInstancesByBizobjId(selectId);
				if (procInsts == null || procInsts.length <= 0)
					this.actionViewSubmitProccess_actionPerformed(e);
				else if (procInsts.length == 1) {
					showWorkflowDiagram(procInsts[0]);
				} else {
					showWorkflowListDiagram(procInsts);
				}
			} else {
				actionViewSubmitProccess_actionPerformed(e);
			}
		} else {
			for (int i = 0; i < procInsts.length; i++) {
				ProcessInstInfo processInstInfo = (ProcessInstInfo) procInsts[i];
				if ("open".equalsIgnoreCase(processInstInfo.getState()
						.substring(0, 4))) {// 显示所有状态为open.*的流程
					openProcInsts.add(processInstInfo);
				}
			}
			if (openProcInsts.size() == 0 || openProcInsts == null) {
				actionViewSubmitProccess_actionPerformed(e);
			} else if (openProcInsts.size() == 1) { // 如果只有一条运行流程,直接显示出来
				showWorkflowDiagram((ProcessInstInfo) openProcInsts.get(0));
			} else {
				showWorkflowListDiagram((ProcessInstInfo[]) openProcInsts
						.toArray(new ProcessInstInfo[] {}));
			}
		}
	}

	// [end]

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
			throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);

		// xichu_yu 添加，使流程图总是以可以最大化的非模态窗口形式打开 20080922
		BasicWorkFlowMonitorPanel.Show(uiContext);
		/*
		 * String className = BasicWorkFlowMonitorPanel.class.getName();
		 * IUIWindow uiWindow; uiWindow =
		 * UIFactory.createUIFactory(UIFactoryName.MODEL).create( className,
		 * uiContext); uiWindow.show();
		 */
	}

	private void showWorkflowListDiagram(ProcessInstInfo[] processInstInfos)
			throws UIException {
		UIContext uiContext = new UIContext(this);
		uiContext.put("procInsts", processInstInfos);
		String className = ProcessRunningListUI.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(className, uiContext);
		uiWindow.show();
	}
	
	public void actionWorkflowList_actionPerformed(ActionEvent e)
			throws Exception {
		String selectId = null;
		if (editData.getId() != null) {
			selectId = editData.getId().toString();
		}
		IEnactmentService service = EnactmentServiceFactory
				.createRemoteEnactService();
		List openProcInsts = new ArrayList();
		ProcessInstInfo[] procInsts = service
				.getProcessInstanceByHoldedObjectId(selectId);
		if (procInsts == null || procInsts.length == 0) {
			MsgBox.showInfo(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_WFHasNotInstance"));
		} else {
			for (int i = 0; i < procInsts.length; i++) {
				ProcessInstInfo processInstInfo = (ProcessInstInfo) procInsts[i];
				if ("open".equalsIgnoreCase(processInstInfo.getState()
						.substring(0, 4))) {// 显示所有状态为open.*的流程
					openProcInsts.add(processInstInfo);
				}
			}
			if (openProcInsts.size() == 0 || openProcInsts == null) {
				MsgBox.showInfo(this, EASResource
						.getString(FrameWorkClientUtils.strResource
								+ "Msg_WFHasNotInstance"));
			} else {
				ProcessInstInfo procInstInfo = (ProcessInstInfo) openProcInsts
						.get(0);
				WfClientUtils.invokeWfInstActivityTable(procInstInfo, this);
			}
		}
	}
	
	public void onLoad() throws Exception {
		
		fetchInitData();
		if(initData != null){
			InviteProjectInfo inviteProject = (InviteProjectInfo) initData.get("inviteProject");
			isUseWeight = inviteProject.getAppraiseTemplate().isIsUserWidth();
		}
		super.onLoad();
		
		InviteProjectInfo inviteProject = (InviteProjectInfo)initData.get("inviteProject");
		prmtInviteProject.setValue(inviteProject);
		this.txtInviteProjectName.setText(inviteProject.getName());
		/*if(inviteProject.getProject()!=null)
			this.txtCurProject.setText(inviteProject.getProject().getName());*/
		
		//招标立项查看执行信息
		if(inviteProject.getAppraiseTemplate() != null)
		{
			this.txtAppraiseTempName.setText(inviteProject.getAppraiseTemplate().getName());
			this.txtAppraiseTempNumber.setText(inviteProject.getAppraiseTemplate().getNumber());
			this.txtAppraiseTempType.setText(inviteProject.getAppraiseTemplate().getTemplateType().getName());
		}
		/*if(editData.getInviteProject().getInviteMode() != null){
			this.prmtInviteMode.setDataNoNotify(inviteProject.getInviteMode());
		}*/
		if(editData.getInviteProject().getOrgUnit() != null){
			this.txtInviteProjectOrg.setText(inviteProject.getOrgUnit().getName());
		}
		
		initDetailTable();
		loadDetailTable();
		this.actionAuditResult.setVisible(false);
		this.contAuditor.setVisible(false);
		this.contCreator.setVisible(false);
		this.contCreateTime.setVisible(false);
		this.contAuditTime.setVisible(false);
		this.setToolTipText("专家评标结果汇总");
		this.setUITitle("专家评标结果汇总");
		

		Component[] components = this.menuWorkflow.getPopupMenu().getComponents();

		for(int i=components.length-1; i>=0; --i){
			this.menuWorkflow.remove(i);
		}
		
		this.actionCopyFrom.setVisible(false);
		this.actionSubmitOption.setVisible(false);
		
		Component[] tools = this.menuTool.getPopupMenu().getComponents();
		for(int i=tools.length-1; i>=0; --i){
			this.menuTool.remove(i);
		}
		
		this.contInviteProject.setEnabled(false);
		this.prmtInviteProject.setEnabled(false);
		if(initData.get("expertAppraiseDesc") != null)
		{
			txtDesc.setText(initData.get("expertAppraiseDesc").toString());
		}
	
		this.menuEdit.setVisible(false);
		this.menuView.setVisible(false);
		this.menuBiz.setVisible(false);
		this.menuTable1.setVisible(false);
		this.menuTool.setVisible(false);
		this.menuWorkflow.setVisible(false);
		
		this.txtDesc.setEnabled(false);
		
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		this.actionPrint.setEnabled(true);
		
		this.btnInviteAllInformation.setVisible(false);
		
		//add by david_yang PT043562 2011.04.02 (扩充name到255个字符)
		this.txtInviteProjectName.setMaxLength(255);
	}
	protected void loadData() throws Exception{
		
	}
	public void initDetailTable(){
		SupplierQualifyInfo supplierQualify = (SupplierQualifyInfo)initData.get("supplierQualify");
		if(supplierQualify==null){
//			FDCMsgBox.showError(this,"此立项还没有进行供应商资格预审");
//			abort();
			return ;
		}
		Set hasRecordSupplierSet = (HashSet)initData.get("hasRecordSupplierSet");
		if(hasRecordSupplierSet==null||hasRecordSupplierSet.size()==0){
			return;
		}
		KDTable table = this.getDetailTable();
		int baseIndex = table.getColumnIndex("score");
		
		String baseCollName = (String)table.getHeadRow(0).getCell(baseIndex).getValue();
		for(Iterator it = supplierQualify.getEntry().iterator();it.hasNext();){
			
			SupplierQualifyEntryInfo entryInfo = (SupplierQualifyEntryInfo)it.next();
			if(!hasRecordSupplierSet.contains(entryInfo.getSupplier().getId().toString())){
				continue;
			}
			baseIndex++;
			IColumn column = table.addColumn(baseIndex);
			column.setKey(entryInfo.getSupplier().getName());
			column.setUserObject(entryInfo.getSupplier());
			KDFormattedTextField indexValue_TextField = new KDFormattedTextField();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setHorizontalAlignment(FDCClientHelper.NUMBERTEXTFIELD_ALIGNMENT);
			indexValue_TextField.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
			indexValue_TextField.setMaximumValue(FDCHelper.ONE_HUNDRED);
			indexValue_TextField.setMinimumValue(FDCHelper.ZERO);
			indexValue_TextField.setSupportedEmpty(true);
			indexValue_TextField.setPrecision(2);
			
			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			column.setEditor(indexValue_CellEditor);
			table.getHeadRow(0).getCell(column.getKey()).setValue(baseCollName);
			table.getHeadRow(1).getCell(column.getKey()).setValue(entryInfo.getSupplier().getName());
		}
		table.getHeadMergeManager().mergeBlock(0,table.getColumnIndex("score"),0,baseIndex);
		table.getColumn("score").getStyleAttributes().setHided(true);
		table.getColumn("glTypeName").getStyleAttributes().setLocked(true);
		table.getColumn("glName").getStyleAttributes().setLocked(true);
		table.getColumn("weight").getStyleAttributes().setLocked(true);
		table.getColumn("weight").getStyleAttributes().setNumberFormat("#0.00");
		table.getColumn("weight").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		table.getColumn("score").getStyleAttributes().setLocked(true);
		
		final KDBizMultiLangArea textField = new KDBizMultiLangArea();
		
		textField.setMaxLength(2000);
		textField.setAutoscrolls(true);
		textField.setEditable(true);
		textField.setToolTipText("Alt+Enter换行");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
		table.getColumn("description").setEditor(editor);
		table.getColumn("description").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		table.getColumn("description").getStyleAttributes().setWrapText(true);
		
		if(!isUseWeight){
			table.getColumn("weight").getStyleAttributes().setHided(true);
		}
	}
	public void loadDetailTable(){
		KDTable table = this.getDetailTable();
		
		String expStr = "";
		SupplierQualifyInfo supplierQualify = (SupplierQualifyInfo)initData.get("supplierQualify");
		AppraiseTemplateInfo template = (AppraiseTemplateInfo)initData.get("appraiseTemplate"); 
		ExpertAppraiseCollection expertAppCols = (ExpertAppraiseCollection)initData.get("auditExpert");
		
		Map supplierScores = (Map)initData.get("supplierScores");
		if(template!=null&&template.getTemplateEntry()!=null){
			for(Iterator it=template.getTemplateEntry().iterator();it.hasNext();){
				AppraiseTemplateEntryInfo info = (AppraiseTemplateEntryInfo)it.next();
				IRow row = table.addRow();
				row.setUserObject(info);
				row.getCell("glTypeName").setValue(info.getGuideLine().getGuideLineType().getName());
				row.getCell("glName").setValue(info.getGuideLine().getName());
				row.getCell("weight").setValue(info.getWeight());
//				row.getCell("description").setValue();
//				BindCellDesctMap.put(info,row.getCell("description"));
				
				for(int i=0;i<supplierQualify.getEntry().size();i++){
					String colKey = supplierQualify.getEntry().get(i).getSupplier().getName();
					String key = info.getGuideLine().getId().toString()+"_"+supplierQualify.getEntry().get(i).getSupplier().getName();
					if(supplierScores.get(key) !=null){
						row.getCell(colKey).setValue(supplierScores.get(key));
						row.getCell(colKey).getStyleAttributes().setNumberFormat("#0.00");
						row.getCell(colKey).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
					}
					
					
//					BindCellScoreMap.put(info.getScores().get(i),row.getCell(colKey));
				}
//				expStr += (expStr.length()==0?"":"+")+"c"+(row.getRowIndex()+1)+"*~"+(row.getRowIndex()+1)+"";
				if(isUseWeight){
					expStr += (expStr.length()==0?"":"+")+"c"+(row.getRowIndex()+1)+"*~"+(row.getRowIndex()+1)+"";
				}else{
					expStr += (expStr.length()==0?"":"+")+"~"+(row.getRowIndex()+1)+"";
				}
				logger.info(expStr);
			}
		}
		
		//设置混合说明列的值 description
		for(Iterator iter = expertAppCols.iterator(); iter.hasNext(); )
		{
			ExpertAppraiseInfo tmpApp = (ExpertAppraiseInfo)iter.next();
			String expertName = tmpApp.getCreator().getName();
			ExpertAppraiseEntryCollection tmpEntryCols = tmpApp.getEntry();
			for(int index = 0; index < table.getRowCount(); ++index)
			{
				IRow tmpRow = table.getRow(index);
				String desc = getAssiocationDescrption(tmpEntryCols, tmpRow);
				
				StringBuffer tmpBuffer = new StringBuffer();
				if(tmpRow.getCell("description").getValue() != null)
				{
					tmpBuffer.append(tmpRow.getCell("description").getValue().toString());
					tmpBuffer.append(expertName);
					tmpBuffer.append(":");
					tmpBuffer.append(desc);
					tmpBuffer.append(";  ");
				}
				else
				{
					tmpBuffer.append(expertName);
					tmpBuffer.append(":");
					tmpBuffer.append(desc);
					tmpBuffer.append(";  ");
				}
				
				tmpRow.getCell("description").setValue(tmpBuffer.toString());
			}
			
			
		}
		
		KDTFootManager footRowManager= kdtEntry.getFootManager();
		footRowManager = new KDTFootManager(kdtEntry);
		footRowManager.addFootView();
		kdtEntry.setFootManager(footRowManager);
		
		IRow sumRow = getDetailTable().addRow();
		IRow footRow = footRowManager.addFootRow(0);
		
		sumRow.getCell(0).setValue("合计");
		sumRow.getCell(2).setExpressions("=SUM(c1:c"+template.getTemplateEntry().size()+")");
		for(int i=3;i<=table.getColumnCount()-2;i++){
//			String str = "=("+expStr.replaceAll("~",String.valueOf((char)(i+97)))+")/SUM(c1:c"+template.getTemplateEntry().size()+")";
			String str = "";
			if(isUseWeight){
				str = "=("+expStr.replaceAll("~",String.valueOf((char)(i+97)))+")/SUM(c1:c"+template.getTemplateEntry().size()+")";
			}else{
				str = "=("+expStr.replaceAll("~",String.valueOf((char)(i+97)))+")";
			}
			sumRow.getCell(i).setExpressions(str);
			sumRow.getCell(i).getStyleAttributes().setNumberFormat("#0.00");
			sumRow.getCell(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			logger.info(str);
		}
		
		for(int i=2;i<=table.getColumnCount()-2;i++){
			footRow.getCell(i).setValue(sumRow.getCell(i).getValue());
			footRow.getCell(i).getStyleAttributes().setNumberFormat("#0.00");
			footRow.getCell(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		}
		sumRow.getStyleAttributes().setLocked(true);
		getDetailTable().removeRow(table.getRowCount()-1);
		footRow.getCell(0).setValue("合计");
		table.getStyleAttributes().setLocked(true);
		footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
	}

	private String getAssiocationDescrption(ExpertAppraiseEntryCollection paramEntry, IRow paramRow)
	{
		String description = "";
		AppraiseTemplateEntryInfo entryInfo = (AppraiseTemplateEntryInfo)paramRow.getUserObject();
		Iterator iter = paramEntry.iterator();
		while(iter.hasNext())
		{
			ExpertAppraiseEntryInfo expertEntryInfo = (ExpertAppraiseEntryInfo)iter.next();
			if(entryInfo.getGuideLine() != null && expertEntryInfo.getGuideLine() != null)
			{
				if(entryInfo.getGuideLine().getId().equals(expertEntryInfo.getGuideLine().getId()))
				{
					if(expertEntryInfo.getDescription() == null)
						return description;
					
					description = expertEntryInfo.getDescription();
					return description;
				}
			}
		}
		 
		return description ;
	}
	
	protected IObjectValue createNewDetailData(KDTable table) {
		
		return null;
	}
	protected IObjectValue createNewData() {
		
		ExpertAppraiseResultInfo object = new ExpertAppraiseResultInfo();
		
		return object;
	}
	protected void doBeforeSubmitForWF(CoreBaseInfo editData) throws Exception
    {
        //2005-8-21
        //在单据提交的时候，发现是AddNew（或者值对象的ID为空），在提交前调用如下方法：
        //由于许多单据没有继承CoreBillEditUI，所以加入这里。 modify 2005-9-8
        IMultiBillWorkAgent agent = new DefaultMultiBillWorkAgent();
        agent.markNewBill(this.editData, this.getUIContext());
     }
	
	/**
	 * 打印
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		MultiDataSourceDataProviderProxy data = new MultiDataSourceDataProviderProxy();
		BOSQueryDelegate headData = new CommonDataProvider(idList, getTDQueryPK());
		String[] desc=new String[this.getDetailTable().getRowCount()];
		for(int index = 0; index < this.getDetailTable().getRowCount(); ++index){
			desc[index]=getDetailTable().getCell(index,"description").getValue()==null? "":getDetailTable().getCell(index,"description").getValue().toString();
		}
//		BOSQueryDelegate otherData = new ViewExpertAppraisePrintDataProvider((Map)initData.get("supplierScores"),(SupplierQualifyInfo)initData.get("supplierQualify"),
//				(AppraiseTemplateInfo)initData.get("appraiseTemplate"),desc);
		InvitePrintDataProvider otherData = new InvitePrintDataProvider(editData.getString("id"), getTDQueryPK(), kdtEntry);
		InvitePrintDataProvider audit = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		ViewExpertAppraiseResultDescDataProvider remark = new ViewExpertAppraiseResultDescDataProvider(
				editData.getString("id"), getTDQueryPK(),txtDesc.getText());
		data.put("MAINBILL", headData );
		data.put("AUDITINFO1", audit );
		data.put("ViewExpertAppraiseResultEntry", otherData );
		data.put("DESC", remark );
		/*InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());*/
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	/**
	 * 打印预览
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null
				&& !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData
						.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		MultiDataSourceDataProviderProxy data = new MultiDataSourceDataProviderProxy();
		BOSQueryDelegate headData = new CommonDataProvider(idList, getTDQueryPK());
		String[] desc=new String[this.getDetailTable().getRowCount()];
		for(int index = 0; index < this.getDetailTable().getRowCount(); ++index){
			desc[index]=getDetailTable().getCell(index,"description").getValue()==null? "":getDetailTable().getCell(index,"description").getValue().toString();
		}
//		BOSQueryDelegate otherData = new ViewExpertAppraisePrintDataProvider((Map)initData.get("supplierScores"),(SupplierQualifyInfo)initData.get("supplierQualify"),
//				(AppraiseTemplateInfo)initData.get("appraiseTemplate"),desc);
		InvitePrintDataProvider otherData = new InvitePrintDataProvider(editData.getString("id"), getTDQueryPK(), kdtEntry);
		InvitePrintDataProvider audit = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());
		ViewExpertAppraiseResultDescDataProvider remark = new ViewExpertAppraiseResultDescDataProvider(
				editData.getString("id"), getTDQueryPK(),txtDesc.getText());
		data.put("MAINBILL", headData );
		data.put("AUDITINFO1", audit );
		data.put("ViewExpertAppraiseResultEntry", otherData );
		data.put("DESC", remark );
		/*InvitePrintDataProvider data = new InvitePrintDataProvider(
				editData.getString("id"), getTDQueryPK());*/
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// 获得无文本合同套打对应的目录
	protected String getTDFileName() {
		return "/bim/fdc/invite/ViewExpertAppraiseResultForPrint";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.invite.app.ViewExpertAppraiseResultForPrintQuery");
	}
}