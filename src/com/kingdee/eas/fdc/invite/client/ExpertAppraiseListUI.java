/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.ExpertAppraiseCollection;
import com.kingdee.eas.fdc.invite.ExpertAppraiseFactory;
import com.kingdee.eas.fdc.invite.ExpertAppraiseInfo;
import com.kingdee.eas.fdc.invite.IExpertAppraise;
import com.kingdee.eas.fdc.invite.InviteProjectAppraiseFacadeFactory;
import com.kingdee.eas.fdc.invite.InviteProjectException;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.attachMent.AttachMentConstant;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * 专家评标 列表界面
 */
public class ExpertAppraiseListUI extends AbstractExpertAppraiseListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExpertAppraiseListUI.class);
    private final String COL_STATE = "state";
    private int setSelectedRowIndex = -1;

    public ExpertAppraiseListUI() throws Exception
    {
        super();
    }

    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    	if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2)
        {
    		if (e.getType() == 0) {
                return;
            }
            checkSelected(tblMain);

            UIContext uiContext = new UIContext(this);
            uiContext.put(UIContext.ID , getSelectedKeyValue(tblMain));

            // 供子类定义要传递到EditUI中扩展的属性
//            prepareUIContext(uiContext , new ActionEvent(btnView , 0 ,
//                "Double Clicked"));

            // 创建UI对象并显示，如果原窗口是模态的，则显示模态；否则非模态新开窗口。
            IUIWindow window = this.getUIWindow();
			window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteProjectEditUI.class.getName(), uiContext, null, "VIEW");
            
            window.show();
        }else
        	super.tblMain_tableClicked(e);
    }
    
    /**
     * 招标执行信息
     */
    public void actionInviteAllInformation_actionPerformed(ActionEvent e)
    		throws Exception {
    	checkSelected();
		String inviteProjectId = tblMain.getRow(tblMain.getSelectManager().getActiveRowIndex()).getCell("id").getValue().toString();
		UIContext uiContext = new UIContext(this);

		uiContext.put(UIContext.ID, null);
		uiContext.put("INVITE_PROJECT", inviteProjectId);
		uiContext.put("LIST_UI", "com.kingdee.eas.fdc.invite.client.InviteProjectListUI");
		uiContext.put("INVITEPROJECT_NAME", null);

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(InviteAllInformationUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    protected boolean checkBeforeOprate(String[] states) throws Exception 
	{
		boolean flag = false;
		
		List idList = ContractClientUtils.getSelectedIdValues(this.tblDetail, getKeyFieldName());

		if(idList==null || idList.size()==0){
			return flag ;
		}

		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = ((IFDCBill)getBizInterface()).getCoreBillBaseCollection(view);

		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) 
			{
				if(billState.equals(states[i])) 
				{
					pass = true;
				}
			}
			if(!pass)
			{
				flag = false;
				break ;
			}
			else
			{
				flag = pass;
			}
		}

		return flag;
	}
    protected String[] getStateForExpertAppraiseAudit() {
		return new String[]{FDCBillStateEnum.SUBMITTED_VALUE};
	}
	protected String[] getStateForExpertAppraiseUnAudit() {
		return  new String[]{FDCBillStateEnum.AUDITTED_VALUE};
	}
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected(this.tblDetail);
		
		if(!checkBeforeOprate(getStateForExpertAppraiseUnAudit()))
    	{
    		StringBuffer buffer = new StringBuffer();
        	buffer.append("专家评标状态为");
        	
        	int rowIndex = tblDetail.getSelectManager().getActiveRowIndex();
        	if(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue() != null)
        	{
        		buffer.append(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue().toString());
        	}
        	
        	buffer.append(", 不能执行此操作");
        	
        	MsgBox.showWarning(this, buffer.toString());
			SysUtil.abort();
    	}
		
    	super.actionUnAudit_actionPerformed(e);
    }
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected(this.tblDetail);
		
		if(!checkBeforeOprate(getStateForExpertAppraiseAudit()))
    	{
    		StringBuffer buffer = new StringBuffer();
        	buffer.append("专家评标状态为");
        	
        	int rowIndex = tblDetail.getSelectManager().getActiveRowIndex();
        	if(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue() != null)
        	{
        		buffer.append(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue().toString());
        	}
        	
        	buffer.append(", 不能执行此操作");
        	
        	MsgBox.showWarning(this, buffer.toString());
			SysUtil.abort();
    	}
		
    	super.actionAudit_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	if (tblMain.getRowCount() == 0 || tblMain.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, "请先选中招标立项");
            SysUtil.abort();
        }
    	String inviteProjectId = this.getInviteProjectId();
    	InviteProjectAppraiseFacadeFactory.getRemoteInstance().checkCanAddExpertAppraise(inviteProjectId);
    	
    	setSelectedRowIndex = tblMain.getSelectManager().getActiveRowIndex();
    	
        super.actionAddNew_actionPerformed(e);
    }

    public void checkSelected()
    {
        if (tblDetail.getRowCount() == 0 || tblDetail.getSelectManager().size() == 0)
        {
            MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
            SysUtil.abort();
        }
    }
    
    public void checkSelected(KDTable table) {
		if (table.getRowCount() == 0 || table.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			SysUtil.abort();
		}
	}


    protected String[] getStateForEdit()
	{
		return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
	}
    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(this.tblDetail);

    	if(!checkBeforeOprate(getStateForEdit()))
    	{
    		StringBuffer buffer = new StringBuffer();
        	buffer.append("专家评标状态为");
        	
        	int rowIndex = tblDetail.getSelectManager().getActiveRowIndex();
        	if(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue() != null)
        	{
        		buffer.append(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue().toString());
        	}
        	
        	buffer.append(", 不能执行此操作");
        	
        	MsgBox.showWarning(this, buffer.toString());
			SysUtil.abort();
    	}
    	
        super.actionEdit_actionPerformed(e);
    }

    protected String[] getStateForRemove()
    {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	checkSelected(this.tblDetail);
    	
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",this.getSelectedKeyValue()));
    	filter.getFilterItems().add(new FilterItemInfo("creator",SysContext.getSysContext().getCurrentUserInfo().getId().toString()));
    	if(ExpertAppraiseFactory.getRemoteInstance().exists(filter))
    	{
    		if(!checkBeforeOprate(getStateForRemove()))
    		{
    			StringBuffer buffer = new StringBuffer();
    			buffer.append("专家评标状态为");

    			int rowIndex = tblDetail.getSelectManager().getActiveRowIndex();
    			if(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue() != null)
    			{
    				buffer.append(tblDetail.getRow(rowIndex).getCell(COL_STATE).getValue().toString());
    			}

    			buffer.append(", 不能执行此操作");

    			MsgBox.showWarning(this, buffer.toString());
    			SysUtil.abort();
    		}

    		super.actionRemove_actionPerformed(e);
    	}
    	else{
    		throw new InviteProjectException(InviteProjectException.NOTTHECREATOR);
    	}
    }

	protected String getEditUIName() {
		return ExpertAppraiseEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ExpertAppraiseFactory.getRemoteInstance();
	}

	protected void displayBillByInvite(EntityViewInfo view) throws BOSException {
		// TODO 自动生成方法存根
		view.getSelector().add("*");
		view.getSelector().add("creator.*");
		view.getSelector().add("auditor.*");
		ExpertAppraiseCollection coll = ExpertAppraiseFactory.getRemoteInstance().getExpertAppraiseCollection(view);
		this.tblDetail.removeRows();
		for(Iterator it = coll.iterator();it.hasNext();){
			ExpertAppraiseInfo info = (ExpertAppraiseInfo)it.next();
			IRow row = tblDetail.addRow();
			row.getCell("state").setValue(info.getState());
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("creator.id").setValue(info.getCreator()==null?null:info.getCreator().getId().toString());
			row.getCell("creator.name").setValue(info.getCreator()==null?null:info.getCreator().getName());
			row.getCell("createTime").setValue(info.getCreateTime());
			row.getCell("auditor.id").setValue(info.getAuditor()==null?null:info.getAuditor().getId().toString());
			row.getCell("auditor.name").setValue(info.getAuditor()==null?null:info.getAuditor().getName());
			row.getCell("auditTime").setValue(info.getAuditTime());
		}
		tblDetail.getStyleAttributes().setLocked(true);
	}
	
	public boolean isInviteProjectIsLeafFilter() {
		return true;
	}

	
	/**
	 * 实现通用过滤并保存默认方案
	 */
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		FilterInfo filter = null;
		try {
			filter = this.getMainFilter();
			if(this.getDialog()!=null){
				FilterInfo commFilter = this.getDialog().getCommonFilter();
				if(filter!=null&&commFilter!=null)
					filter.mergeFilter(commFilter, "and");
			}else{
				QuerySolutionInfo querySolution = this.getQuerySolutionInfo();
				if (querySolution!=null&&querySolution.getEntityViewInfo()!=null)
		        {
		            EntityViewInfo ev=Util.getInnerFilterInfo(querySolution);
		            if(ev.getFilter()!=null){
		            	filter.mergeFilter(ev.getFilter(), "and");
		            }
		        }
			}
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		viewInfo.setFilter(filter);
		
		return super.getQueryExecutor(queryPK, viewInfo);
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.btnQuery.setVisible(true);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionInviteAllInformation.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.tblDetail.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		pnlSplit.setDividerLocation(270);
		pnlSplit.setResizeWeight(0.2);
		
		FDCHelper.formatTableDate(tblMain, "createTime");
		FDCHelper.formatTableDate(tblMain, "auditTime");
		FDCHelper.formatTableDate(tblMain, "prjDate");
		
		FDCHelper.formatTableDate(tblDetail, "createTime");
		FDCHelper.formatTableDate(tblDetail, "auditTime");
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);
		
		this.menuWorkFlow.setVisible(false);
		this.actionCopyTo.setVisible(false);
		this.menuItemSwitchView.setVisible(false);
		
		this.menuItemRemove.setVisible(false);
	
		if(setSelectedRowIndex != -1)
		{
			tblMain.getSelectManager().set(setSelectedRowIndex, 0);
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	public void doUnAudit(List idList) throws Exception {
		((IExpertAppraise)getBizInterface()).unAudit(idList);
	}

	public void doAudit(List idList) throws Exception {
		((IExpertAppraise)getBizInterface()).audit(idList);
	}

	protected void tblDetail_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO 自动生成方法存根
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1)
    	{
    		if (tblDetail.getColumn(e.getColIndex()).getKey().equals(AttachMentConstant.COLUMN_ATTACHMENT))
    		{
    			actionAttachment_actionPerformed(null);
    			return;
    		}else if (tblDetail.getColumn(e.getColIndex()).getKey().equals(getEntriesName()+"."+AttachMentConstant.COLUMN_ATTACHMENT)){
    			showSubAttacheMent(null);
    		}
    	}
    	if (e.getClickCount() == 2 )
        {
            //modify to view when doubleClick head row by Jacky 2006-5-24
            if (e.getType() == 0) {
                return;
            }
            checkSelected(tblDetail);

            UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, getSelectedKeyValue());

			// 供子类定义要传递到EditUI中扩展的属性
			prepareUIContext(uiContext, new ActionEvent(btnView, 0, "Double Clicked"));

            // 创建UI对象并显示，如果原窗口是模态的，则显示模态；否则非模态新开窗口。
            IUIWindow window = this.getUIWindow();
			window = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(getEditUIName(), uiContext, null, "VIEW");
            
            window.show();
        }
	}
	protected boolean isShowAttachmentAction() {
		return false;
	}
		
	protected DefaultKingdeeTreeNode getSelectedTreeNode(KDTree selectTree)
	{
		if(selectTree.getLastSelectedPathComponent() != null)
		{
			DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)selectTree.getLastSelectedPathComponent();
			return treeNode;
		}
		return null ;
	}
	
   public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String fieldName = getQueryFieldNameBindingWf();
		String id = (String) getSelectedFDCFieldValues(fieldName).get(0);
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if (procInsts[i].getState().startsWith("open")) {
				processInstInfo = procInsts[i];
			}
		}
		if (processInstInfo == null) {
			//如果没有运行时流程，判断是否有可以展现的流程图并展现
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if (procInsts == null || procInsts.length <= 0)
				MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_WFHasNotInstance"));
			else if (procInsts.length == 1) {
				showWorkflowDiagram(procInsts[0]);
			} else {
				UIContext uiContext = new UIContext(this);
				uiContext.put("procInsts", procInsts);
				String className = ProcessRunningListUI.class.getName();
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className, uiContext);
				uiWindow.show();
			}
		} else {
			showWorkflowDiagram(processInstInfo);
		}
	}

	private void showWorkflowDiagram(ProcessInstInfo processInstInfo) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(className, uiContext);
		uiWindow.show();
	}

	/**
	 * 所见即所选，不处理虚模式。
	 * @param fieldName
	 * @return
	 */
	public final ArrayList getSelectedFDCFieldValues(String fieldName) {
		ArrayList list = new ArrayList();
		int[] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		for (int i = 0; i < selectRows.length; i++) {
			ICell cell = getBillListTable().getRow(selectRows[i]).getCell(fieldName);
			if (cell == null) {
				MsgBox.showError(EASResource.getString(FrameWorkClientUtils.strResource + "Error_KeyField_Fail"));
				SysUtil.abort();
			}
			if (cell.getValue() != null) {
				String id = cell.getValue().toString();
				if (!list.contains(id))
					list.add(id);
			}

	        }
		return list;
	}
	
	public void actionAuditResult_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int selectRowIndex = tblDetail.getSelectManager().getActiveRowIndex();
		String id = tblDetail.getRow(selectRowIndex).getCell("id").getValue().toString();
		if (!StringUtils.isEmpty(id)) {
			MultiApproveUtil.showApproveHis(BOSUuid.read(id), UIModelDialogFactory.class.getName(), this);
		}
	}
}