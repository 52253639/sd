/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.ExpertInfo;
import com.kingdee.eas.fdc.invite.ExpertQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.ExpertQualifyFactory;
import com.kingdee.eas.fdc.invite.ExpertQualifyInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InviteProjectStateEnum;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.offline.util.AttachmentPermissionUtil;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 确定评标专家 编辑界面
 */
public class ExpertQualifyEditUI extends AbstractExpertQualifyEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(ExpertQualifyEditUI.class);
	boolean canSelectOtherOrgPerson = false;
	private FullOrgUnitInfo orgUnitInfo = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();

	/**招标文件合成、供应商资格预审、确定评标专家、确定评标模板一块审批**/
	private CompanyOrgUnitInfo currentCompany = SysContext.getSysContext().getCurrentFIUnit();
	private boolean isTogetherAudit = false ;
	/**
	 * output class constructor
	 */
	public ExpertQualifyEditUI() throws Exception
	{
		super();
	}

	public void actionInviteExecute_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionInviteExecute_actionPerformed(e);
		InviteProjectInfo info = null;
		if(editData.getInviteProject() != null){
			info = editData.getInviteProject();
		}else{
			if(prmtInviteProject.getData() != null ){
				info = (InviteProjectInfo) prmtInviteProject.getData();
			}
		}
		if(info == null){
			FDCMsgBox.showError("请选择招标立项");
			abort();
		}
		UIContext uiContext = new UIContext(this);

		uiContext.put(UIContext.ID, null);
		uiContext.put("INVITE_PROJECT", info.getId().toString());
		uiContext.put("LIST_UI", "com.kingdee.eas.fdc.invite.client.ExpertQualifyEditUI");
		uiContext.put("INVITEPROJECT_NAME", null);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteAllInformationUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * output prmtInviteProject_willShow method
	 */
	protected void prmtInviteProject_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
	{
		EntityViewInfo view = new EntityViewInfo();

		view.getSelector().add(new SelectorItemInfo("id"));
		view.getSelector().add(new SelectorItemInfo("number"));
		view.getSelector().add(new SelectorItemInfo("name"));
		view.getSelector().add(new SelectorItemInfo("state"));

		view.getSelector().add(new SelectorItemInfo("project.id"));
		view.getSelector().add(new SelectorItemInfo("project.number"));
		view.getSelector().add(new SelectorItemInfo("project.name"));

		view.getSelector().add(new SelectorItemInfo("inviteForm"));
		view.getSelector().add(new SelectorItemInfo("respPerson.number"));
		view.getSelector().add(new SelectorItemInfo("prjDate"));

		view.getSelector().add(new SelectorItemInfo("inviteType.longNumber"));
		view.getSelector().add(new SelectorItemInfo("inviteType.id"));
		view.getSelector().add(new SelectorItemInfo("inviteType.number"));
		view.getSelector().add(new SelectorItemInfo("inviteType.name"));

		view.getSelector().add(new SelectorItemInfo("state"));
		view.getSelector().add(new SelectorItemInfo("inviteProjectState"));
		view.getSelector().add(new SelectorItemInfo("orgUnit.id"));

		view.getSelector().add(new SelectorItemInfo("creator.name"));
		view.getSelector().add(new SelectorItemInfo("createTime"));

		view.getSelector().add(new SelectorItemInfo("auditor.name"));
		view.getSelector().add(new SelectorItemInfo("auditTime"));

		prmtInviteProject.setSelectorCollection(getInviteProjectNewSelector());

		FilterInfo filter = new FilterInfo();
		Set unSeeState = new HashSet();
		unSeeState.add(InviteProjectStateEnum.AUDITTED_VALUE);
		unSeeState.add(InviteProjectStateEnum.FILEMAKING_VALUE);
		unSeeState.add(InviteProjectStateEnum.ANSWERING_VALUE);

		//状态过滤
		filter.getFilterItems().add(new FilterItemInfo("inviteProjectState", unSeeState, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE, CompareType.EQUALS));

		//选择当前组织下的招标立项
		if(orgUnitInfo != null)
		{
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgUnitInfo.getId().toString()));
		}

		//招标立项自身的过滤条件

		//招标类型过滤条件
		if(getSelectInviteType() != null && (getSelectInviteType() instanceof com.kingdee.eas.fdc.invite.InviteTypeInfo))
		{
			FilterInfo typeFilter = new FilterInfo();
			InviteTypeInfo typeInfo = (InviteTypeInfo)getSelectInviteType();
			BOSUuid id = typeInfo.getId();

			Set idSet = getInviteTypeIdSet(id);
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id", idSet, CompareType.INCLUDE));

		}

		view.setFilter(filter);
		prmtInviteProject.setEntityViewInfo(view);

		if (prmtInviteProject.getSelector() != null && prmtInviteProject.getSelector() instanceof com.kingdee.eas.framework.client.ListUI) {
			((com.kingdee.eas.framework.client.ListUI)prmtInviteProject.getSelector()).setFilterForQuery(filter);
			((com.kingdee.eas.framework.client.ListUI)prmtInviteProject.getSelector()).onLoad();

		}else {
			if(filter.getFilterItems().size() <= 0)
			{
				return ;
			}
			prmtInviteProject.getEntityViewInfo().setFilter(filter);
			prmtInviteProject.getQueryAgent().resetRuntimeEntityView();
			prmtInviteProject.setRefresh(true);
		}
	}

	/**
	 * output prmtInviteProject_dataChanged method
	 */
	protected void prmtInviteProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
	{
		if(e.getNewValue()!=null){
			InviteProjectInfo invProject = (InviteProjectInfo)e.getNewValue();
			if(!this.isFirstOnload()){
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("inviteProject.id",invProject.getId().toString()));
				if(editData.getId()!=null)
					filter.getFilterItems().add(new FilterItemInfo("id",editData.getId().toString(),CompareType.NOTEQUALS));
				if(this.getBizInterface().exists(filter)){
					FDCMsgBox.showError(this,"此招标立项已经确定了评标专家，请重新选择！");
					this.prmtInviteProject.setValue(e.getOldValue());
					return;
				}
			}
			editData.setInviteProject(invProject);
			// 修改为名称带出编码 modify by msb 2010/04/27
			this.txtInviteProjectNumber.setText(invProject.getNumber());
			editData.setNumber(invProject.getNumber());
			editData.setName(invProject.getName());
			if(invProject.getInviteType()!=null)
				this.txtInviteTypeName.setText(invProject.getInviteType().getName());
			/*if(invProject.getProject()!=null)
				this.txtCurProjectName.setText(invProject.getProject().getName());*/
			if(invProject.getOrgUnit()!=null)
				this.txtInviteProjectOrg.setText(invProject.getOrgUnit().getName());
			/*if(invProject.getInviteMode()!=null)
				this.prmtInviteMode.setData(invProject.getInviteMode());*/
		}else{
			prmtInviteMode.setDataNoNotify(null);
			txtInviteProjectOrg.setText(null,false);
		}
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
	{
		if(this.isTogetherAudit)
		{
			MsgBox.showWarning(this, "系统参数设置招标文件合成、供应商资格预审、确定评标专家、确定评标模板一起审批，不能执行此操作");
			abort();
		}

		if(checkWorkFlow("FExpertQualifyID"))
		{
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_BindWfInstance"));
			SysUtil.abort();
		}
		
		/*
		 * Modify by qinyouzhen 2011-05-10 , 在提交时，若评标专家为偶数则需提示消息
		 */
		if(editData.getEntry().size() > 0 && editData.getEntry().size() % 2 == 0){
			int result = FDCMsgBox.showConfirm2("评标专家人数为偶数，是否确认提交？");
			if(result == FDCMsgBox.CANCEL){
				return;
			}
		}
		
		super.actionSubmit_actionPerformed(e);
		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}		
	}
	
	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception
	{
		checkSysParam();
		if(checkWorkFlow("FExpertQualifyID"))
		{
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_BindWfInstance"));
			SysUtil.abort();
		}
    	
		super.actionEdit_actionPerformed(e);

		if(editData!=null&&FDCBillStateEnum.SUBMITTED.equals(editData.getState())){
			this.actionSave.setEnabled(false);
		}
		this.txtCurProjectName.setEnabled(false);
		this.txtInviteTypeName.setEnabled(false);
		this.txtInviteProjectNumber.setEnabled(false);
		this.actionAddLine.setEnabled(true);
		this.actionRemoveLine.setEnabled(true);
		this.actionInsertLine.setEnabled(true);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception
	{
		//引用检测，当被一起审批时不能进行操作
		checkSysParam();
    	if(checkWorkFlow("FExpertQualifyID"))
		{
			MsgBox.showWarning(this, EASResource
					.getString(FrameWorkClientUtils.strResource
							+ "Msg_BindWfInstance"));
			SysUtil.abort();
		}    	
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
	{
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = getSelectBOID();
        if(boID == null)
        {
            return;
        } 
        boolean isEdit = false;
        if(STATUS_ADDNEW.equals(getOprtState())||STATUS_EDIT.equals(getOprtState()))
        {
            isEdit = true;
        }
        
        //add liuguangyue 2010-4-27
        isEdit = AttachmentPermissionUtil.checkAuditingAttachmentEdit(editData.getState(), boID,isEdit);
		
        if(this.isTogetherAudit)
		{
			if(!editData.getState().equals(FDCBillStateEnum.SAVED))
	    	{
//				acm.showAttachmentListUIByBoID(boID, this, false);
//				return ;
				isEdit = false;
	    	}
		}
        
        if(checkWorkFlow("FExpertQualifyID"))
        {
//        	acm.showAttachmentListUIByBoID(boID, this, false);
//			return ;
        	isEdit = false;
        }
        //modify liuguangyue 2010-4-27
		AttachmentUIContextInfo info = new AttachmentUIContextInfo();
		info.setBoID(boID);
		MultiApproveUtil.showAttachmentManager(info,this,editData,String.valueOf("1"),isEdit);
//        acm.showAttachmentListUIByBoID(boID,this,isEdit); // boID 是 与附件关联的 业务对象的 ID
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception
	{
		if (checkWorkFlow("FExpertQualifyID")) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_BindWfInstance"));
			SysUtil.abort();
		}
		
		super.actionStartWorkFlow_actionPerformed(e);
	}

	private void initUI() {
		String cuId = SysContext.getSysContext().getCurrentCtrlUnit().getId().toString();
		FDCClientUtils.setRespDeptF7(prmtCreateDept, this, canSelectOtherOrgPerson?null:cuId);

		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionLast.setVisible(false);
		this.actionNext.setVisible(false);
		this.actionPre.setVisible(false);

		remove(btnAddLine);
		remove(btnInsertLine);
		remove(btnRemoveLine);

		this.ctnEntry.addButton(btnAddLine);
		this.ctnEntry.addButton(btnInsertLine);
		this.ctnEntry.addButton(btnRemoveLine);
	}

	protected IObjectValue createNewData() {
		ExpertQualifyInfo object = new ExpertQualifyInfo();
		object.setState(FDCBillStateEnum.SAVED);
		return object;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ExpertQualifyFactory.getRemoteInstance();
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		ExpertQualifyEntryInfo entry = new ExpertQualifyEntryInfo();
		entry.setParent(editData);
		editData.getEntry().add(entry);
		
		return entry;
	}

	public void onLoad() throws Exception {
		fetchInitParam();
		super.onLoad();
		isTogetherAudit = FDCUtils.getDefaultFDCParamByKey(null,currentCompany.getId().toString(), FDCConstants.FDC_PARAM_INVITE_SUPPLIER_EXPERT_TEMPLATE);

		if(STATUS_EDIT.equals(this.getOprtState()))
		{
			if(this.editData!=null&&FDCBillStateEnum.SUBMITTED.equals(this.editData.getState()))
			{
				this.actionSave.setEnabled(false);
			}
		}else if(STATUS_VIEW.equals(this.getOprtState()))
		{
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
		this.prmtInviteProject.setRequired(true);
		this.txtCurProjectName.setEnabled(false);
		this.txtInviteTypeName.setEnabled(false);
		this.txtInviteProjectNumber.setEnabled(false);
		getDetailTable().getColumn("expertName").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("expertTypeName").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("expertTel").getStyleAttributes().setLocked(true);
		getDetailTable().getColumn("expertOrgName").getStyleAttributes().setLocked(true);
		InviteClientHelper.initInviteProjectF7(prmtInviteProject);
		KDTable table = this.getDetailTable();

		if(editData!=null&&FDCBillStateEnum.AUDITTED.equals(editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionAddLine.setEnabled(false);
			this.actionRemoveLine.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionInsertLine.setEnabled(false);
		}
		this.prmtCreateDept.setRequired(true);
		this.txtDesc.setMaxLength(2000);

		final KDTextField textField = new KDTextField();

		textField.setMaxLength(100);
		textField.setAutoscrolls(true);
		textField.setEditable(true);
		textField.setToolTipText("Alt+Enter换行");
		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(textField);
		table.getColumn("remark").setEditor(editor);
		table.getColumn("remark").getStyleAttributes().setVerticalAlign(VerticalAlignment.TOP);
		table.getColumn("remark").getStyleAttributes().setWrapText(true);

		table.getColumn("expertNumber").getStyleAttributes().setBackground(FDCTableHelper.requiredColor);
		getDetailTable().getColumn("expertName").getStyleAttributes().setBackground(FDCTableHelper.lockColor);
		getDetailTable().getColumn("expertTypeName").getStyleAttributes().setBackground(FDCTableHelper.lockColor);
		getDetailTable().getColumn("expertOrgName").getStyleAttributes().setBackground(FDCTableHelper.lockColor);
		getDetailTable().getColumn("expertTel").getStyleAttributes().setBackground(FDCTableHelper.lockColor);
		
//		CellExpertRenderImpl render = new CellExpertRenderImpl();
		ObjectValueRender segNum = new ObjectValueRender();
		segNum.setFormat((IDataFormat) new BizDataFormat("$number$"));
		kdtEntry.getColumn("expertNumber").setRenderer(segNum);

//		kdtEntry.getColumn("expertNumber").setRenderer(render);
		
		FDCTableHelper.disableDelete(this.kdtEntry);
	}

	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}

	protected  void fetchInitParam() throws Exception{
		HashMap param = FDCUtils.getDefaultFDCParam(null,orgUnitInfo.getId().toString());		
		if(param.get(FDCConstants.FDC_PARAM_SELECTPERSON)!=null){
			canSelectOtherOrgPerson = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString()).booleanValue();
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		initUI();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("creator.*"));
		sic.add(new SelectorItemInfo("auditor.*"));
		sic.add(new SelectorItemInfo("auditTime"));
		sic.add(new SelectorItemInfo("createDept.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		sic.add(new SelectorItemInfo("inviteProject.name"));
		sic.add(new SelectorItemInfo("inviteProject.inviteType.name"));
		sic.add(new SelectorItemInfo("inviteProject.project.name"));
		sic.add(new SelectorItemInfo("entry.description"));
		sic.add(new SelectorItemInfo("entry.remark"));
		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("entry.expert.number"));
		sic.add(new SelectorItemInfo("entry.expert.name"));
		sic.add(new SelectorItemInfo("entry.expert.expertType.name"));
		sic.add(new SelectorItemInfo("entry.expert.user.defOrgUnit.name"));
		sic.add(new SelectorItemInfo("entry.expert.tel"));
		sic.add(new SelectorItemInfo("inviteProject.inviteMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.orgUnit.*"));
		sic.add("*");
		return sic;
	}

	protected void addLine(KDTable table) {
		if (table == null) {
			return;
		}
		IObjectValue detailData = createNewDetailData(table);
		if (detailData!=null)
		{
			IRow row = table.addRow();
			KDBizPromptBox f7 = this.getF7Expert();
			row.getCell("expertNumber").setEditor(new KDTDefaultCellEditor(f7));
			getUILifeCycleHandler().fireOnAddNewLine(table,detailData);
			loadLineFields(table, row, detailData);
			afterAddLine(table, detailData);
		}

	}

	private KDBizPromptBox getF7Expert(){
		KDBizPromptBox f7 = new KDBizPromptBox();
		f7.setDisplayFormat("$number$");
		f7.setCommitFormat("$number$");
		f7.setEditFormat("$number$");
		f7.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7ExpertQuery");
		SelectorItemCollection selectors = new SelectorItemCollection();
		selectors.add("*");
		selectors.add("expertType.name");
		selectors.add("user.defOrgUnit.name");
		selectors.add("user.defOrgUnit.name");
		EntityViewInfo evInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		evInfo.setFilter(filter);
		f7.setSelectorCollection(selectors);
		f7.setEntityViewInfo(evInfo);
		f7.setEditable(true);
		f7.setEditorLength(500);
	
		return f7;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if(editData.getInviteProject()==null){
			FDCMsgBox.showInfo(this,"请选择采购招标立项");;
			abort();
		}
		if(editData.getCreateDept()==null){
			FDCMsgBox.showInfo(this,"请录入编制部门");
			abort();
		}
		boolean show = false;
		if(editData.getEntry().size()==0){
			show=true;
		}
		for(int i=0;i<editData.getEntry().size();i++){
			if(editData.getEntry().get(i).getExpert()==null){
				show = true;
				break;
			}
		}
		if(show){
			FDCMsgBox.showInfo(this,"没有录入专家，或存在没有录入专家的行，请录入");
			abort();
		}
					
		//如果确定评标专家姓名一样，提示不能执行此操作。包括保存和提交
		for(int i = 0; i < this.kdtEntry.getRowCount(); i++)
		{
			IRow rowI = kdtEntry.getRow(i);
			ExpertQualifyEntryInfo entryInfoI = (ExpertQualifyEntryInfo)rowI.getUserObject();
			boolean isRepeat = false ;
			int repeatRowIndex = -1;
			if(entryInfoI.getExpert().getUser() != null)
			{
				for(int j = 0; j < this.kdtEntry.getRowCount(); ++j)
				{
					IRow rowJ = kdtEntry.getRow(j);
					ExpertQualifyEntryInfo entryInfoJ = (ExpertQualifyEntryInfo)rowJ.getUserObject();
					if(i != j && entryInfoJ.getExpert().getUser() != null)
					{
						if(entryInfoI.getExpert().getUser().getId().equals(entryInfoJ.getExpert().getUser().getId()))
						{
							isRepeat = true ;
							repeatRowIndex = j;
							break ;
						}
					}
				}
			}
			
			if(isRepeat)
			{
				StringBuffer buffer = new StringBuffer();
				buffer.append("第");
				buffer.append(i+1);
				buffer.append("行和第");
				buffer.append(repeatRowIndex+1);
				buffer.append("行专家用户重复，不能执行此操作。");
				
				FDCMsgBox.showWarning(buffer.toString());
				abort();
			}
		}
	}

	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if(e.getColIndex()==getDetailTable().getColumnIndex("expertNumber")){
			if(e.getValue()!=null&&e.getValue() instanceof ExpertInfo){
				ExpertInfo expert = (ExpertInfo)e.getValue();
				
				for(int i = 0; i < kdtEntry.getRowCount(); ++i)
				{
					if(i == e.getRowIndex())
						continue;
					IRow row = kdtEntry.getRow(i);
					if(row.getCell("expertNumber").getValue() != null
							&& row.getCell("expertNumber").getValue() instanceof ExpertInfo)
					{
						ExpertInfo tmpExpert = (ExpertInfo)row.getCell("expertNumber").getValue();
						if(expert.getId().equals(tmpExpert.getId()))
						{
							getDetailTable().removeRow(e.getRowIndex());
							return ;
						}
					}
				}
				IRow row = getDetailTable().getRow(e.getRowIndex()); 
				row.getCell("expertName").setValue(expert.getName());
				row.getCell("expertTypeName").setValue(expert.getExpertType().getName());
				row.getCell("expertTel").setValue(expert.getTel());
				row.getCell("expertOrgName").setValue(expert.getUser().getDefOrgUnit().getName());
				if(getDetailTable().getRow(e.getRowIndex()).getUserObject()!=null&&getDetailTable().getRow(e.getRowIndex()).getUserObject() instanceof ExpertQualifyEntryInfo)
				{
					ExpertQualifyEntryInfo entry = (ExpertQualifyEntryInfo)getDetailTable().getRow(e.getRowIndex()).getUserObject();
					entry.setExpert(expert);
					row.setUserObject(entry);
				}
				
			}
		}
	}

	protected void afterSelectLine(KDTable table, IObjectValue lineData) {
		if(table.getSelectManager()!=null&&table.getSelectManager().get().getTop()>=0){
			int index = table.getSelectManager().get().getTop();
			if(table.getCell(index,table.getColumnIndex("expertNumber")).getEditor()==null||!(table.getCell(index,table.getColumnIndex("expertNumber")).getEditor().getComponent() instanceof KDBizPromptBox)){
				KDBizPromptBox f7 = this.getF7Expert();
				table.getCell(index,table.getColumnIndex("expertNumber")).setEditor(new KDTDefaultCellEditor(f7));
			}
		}
	}

	/**
	 * 获取所选的招标立项类型
	 * @return
	 */
	private InviteTypeInfo getSelectInviteType()
	{
		InviteTypeInfo typeInfo = null ;
		if(getUIContext().get("INVITE_TYPE")!= null && getUIContext().get("INVITE_TYPE") instanceof InviteTypeInfo)
		{

			typeInfo = (InviteTypeInfo)(getUIContext().get("INVITE_TYPE"));
		}

		return typeInfo ;
	}

	private Set getInviteTypeIdSet(BOSUuid id) throws EASBizException, BOSException
	{
		Set idSet = new HashSet();
		IObjectPK pk = new ObjectUuidPK(id);
		InviteTypeInfo parentTypeInfo = InviteTypeFactory.getRemoteInstance().getInviteTypeInfo(pk);

		String longNumber = parentTypeInfo.getLongNumber();
		EntityViewInfo view = new EntityViewInfo();

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber+"!%", CompareType.LIKE));
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber));

		filter.setMaskString("#0 or #1");
		view.setFilter(filter);

		com.kingdee.eas.fdc.invite.InviteTypeCollection typeCols = InviteTypeFactory.getRemoteInstance().getInviteTypeCollection(view);

		Iterator iter = typeCols.iterator();
		while(iter.hasNext())
		{
			InviteTypeInfo tmp = (InviteTypeInfo)iter.next();
			idSet.add(tmp.getId().toString());
		}

		return idSet ;
	}

	protected SelectorItemCollection getInviteProjectNewSelector()
	{
		SelectorItemCollection sic = new SelectorItemCollection();

		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("state"));

		sic.add(new SelectorItemInfo("project.id"));
		sic.add(new SelectorItemInfo("project.number"));
		sic.add(new SelectorItemInfo("project.name"));

		sic.add(new SelectorItemInfo("inviteForm"));
		sic.add(new SelectorItemInfo("respPerson.number"));
		sic.add(new SelectorItemInfo("prjDate"));

		sic.add(new SelectorItemInfo("inviteType.longNumber"));
		sic.add(new SelectorItemInfo("inviteType.id"));
		sic.add(new SelectorItemInfo("inviteType.number"));
		sic.add(new SelectorItemInfo("inviteType.name"));

		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("inviteProjectState"));
		sic.add(new SelectorItemInfo("orgUnit.id"));

		sic.add(new SelectorItemInfo("creator.name"));
		sic.add(new SelectorItemInfo("createTime"));

		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("auditTime"));
	
		sic.add(new SelectorItemInfo("inviteMode.*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));

		return sic ;
	}
	
	protected void checkSysParam() throws Exception
	{
		if(this.isTogetherAudit)
		{
			if(!(editData.getState().equals(FDCBillStateEnum.SAVED)))
	    	{
	    		StringBuffer buffer = new StringBuffer();
	    		buffer.append("系统参数设置招标文件合成、供应商资格预审、确定评标专家、确定评标模板一起审批。");
	        	buffer.append("确定评标专家为");
	        	buffer.append(editData.getState().getAlias());
	        	buffer.append(", 不能执行此操作。");
	        	
	        	MsgBox.showWarning(this, buffer.toString());
				SysUtil.abort();
	    	}
		}
	}
	
	protected boolean checkWorkFlow(String assObject) throws BOSException
	{
		if(editData.getId() == null)
			return false;
		
		//在启用参数时一起审批，没有审批完的时候然后再更改为否，就要检测是否进入工作流
    	String checkId = editData.getId().toString();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder();
    	builder.appendSql("select FID, ");
    	builder.appendSql(assObject);
    	builder.appendSql(", FState from t_inv_inviteallinformation where ");
    	builder.appendParam(assObject, checkId);
		
    	String inviteAllInfoId = null ;
		
		try {
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				if(rowSet.getString("FID") != null)
				{
					inviteAllInfoId = rowSet.getString("FID");
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		ProcessInstInfo instInfo = null;
		ProcessInstInfo[] procInsts = null;
		try {
			IEnactmentService service2 = EnactmentServiceFactory
					.createRemoteEnactService();
			procInsts = service2.getProcessInstanceByHoldedObjectId(inviteAllInfoId);
		} catch (BOSException e) {
			ExceptionHandler.handle(e);
		}
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if ("open.running".equals(procInsts[i].getState())
					|| "open.not_running.suspended".equals(procInsts[i]
							.getState())) {
				instInfo = procInsts[i];
			}
		}
		if (instInfo != null) {
			return true ;
		}
		return false ;
	}
	/**
	 * 
	 * @description 打印
	 * @author duhongming
	 * @createDate 2011-11-22
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.framework.client.AbstractCoreBillEditUI#actionPrint_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		List idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, "当前单据的状态不适合打印操作");
			return;
		}
		ExpertQualifyDataProvider data = new ExpertQualifyDataProvider(editData.getString("id"),
				getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	protected IMetaDataPK getTDQueryPK() {
		return MetaDataPK.create("com.kingdee.eas.fdc.invite.app.ExpertQualifyForPrintQuery");
	}

	private String getTDFileName() {
		return "/bim/fdc/invite/expertQualify";
	}

	/**
	 * 
	 * @description 打印预览
	 * @author duhongming
	 * @createDate 2011-11-22
	 * @param e
	 * @throws Exception
	 * @version EAS7.0
	 * @see com.kingdee.eas.framework.client.AbstractCoreBillEditUI#actionPrintPreview_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, "当前单据的状态不适合打印操作");
			return;

		}
		ExpertQualifyDataProvider data = new ExpertQualifyDataProvider(editData.getString("id"),
				getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
}