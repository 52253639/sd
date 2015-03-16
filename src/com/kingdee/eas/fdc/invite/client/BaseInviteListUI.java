package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.custom.fdcprojectcommonquery.ProjectCommonQueryUI;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteProjectCollection;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.ListUiHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public abstract class BaseInviteListUI extends AbstractBaseInviteListUI
{
	//反编译
	private static final Logger logger = CoreUIObject.getLogger(BaseInviteListUI.class);
    protected final String COL_ID = "id";
    protected final String COL_STATE = "state";
    protected final String COL_RESPDEPT = "respDept";
    protected final String COL_CREATOR = "creator";
    protected final String COL_CREATEDATE = "createDate";
    protected final String COL_AUDITOR = "auditor";
    protected final String COL_AUDITDATE = "auditDate";
    protected final String PROJECTDATE_COL = "prjDate";
    protected final String AUDITTIME_COL = "auditTime";
    protected final String COL_NUMBER = "number";

    public BaseInviteListUI() throws Exception
    {
        super();
    }
    protected String getTblMainSelectedKeyValue(){
    	String keyFiledName=this.subKeyFieldName==null?getKeyFieldName():this.subKeyFieldName;
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
        return ListUiHelper.getSelectedKeyValue(selectRows,this.tblMain,keyFiledName);
    }
	protected void checkInvitePorjectSelected() {
		if (this.tblMain.getRowCount() == 0|| this.tblMain.getSelectManager().size() == 0) {
			MsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_MustSelected"));
			SysUtil.abort();
		}
	}
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)throws Exception {
		if (e.getClickCount() == 2) {
			checkInvitePorjectSelected();
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, getTblMainSelectedKeyValue());

			IUIFactory uiFactory = UIFactory.createUIFactory(getEditUIModal());
			IUIWindow uiWindow = uiFactory.create(InviteProjectEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		} else {
			super.tblMain_tableClicked(e);
		}
	}
    protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)throws Exception {
		String inviteProjectId = getTblMainSelectedKeyValue();;
		refreshBillListTable(inviteProjectId.toString());
	}
    protected void refresh(ActionEvent e) throws Exception{
		super.refresh(e);
		String inviteProjectId = getTblMainSelectedKeyValue();;
		refreshBillListTable(inviteProjectId);
	}
    protected abstract void refreshBillListTable(String paramId) throws BOSException;
    

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	public KDTable getBillListTable() {
		return this.tblBaseInvite;
	}
	protected InviteProjectInfo getSelectedInviteProject()throws EASBizException, BOSException {
		int rowIndex = tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex==-1) return null;
		Object inviteProjectId = tblMain.getRow(rowIndex).getCell(COL_ID).getValue();
		if (inviteProjectId != null) {
			EntityViewInfo view = new EntityViewInfo();
			
			view.getSelector().add(new SelectorItemInfo("*"));
			view.getSelector().add(new SelectorItemInfo("project.*"));
			view.getSelector().add(new SelectorItemInfo("inviteType.*"));
			view.getSelector().add(new SelectorItemInfo("invitePurchaseMode.*"));
			view.getSelector().add(new SelectorItemInfo("orgUnit.*"));
			view.getSelector().add(new SelectorItemInfo("programmingContract.*"));
			view.getSelector().add(new SelectorItemInfo("scalingRule.*"));
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id", inviteProjectId.toString()));
			view.setFilter(filter);
			InviteProjectCollection prjInfo = InviteProjectFactory.getRemoteInstance().getInviteProjectCollection(view);
			if (prjInfo.size() > 0) {
				return prjInfo.get(0);
			}
		}
		return null;
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		try {
			InviteProjectInfo inviteProject = getSelectedInviteProject();
			uiContext.put("inviteProject", inviteProject);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
	protected void tblBaseInvite_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getClickCount() == 2) {
			checkSelected();
			String id = getSelectedKeyValue();
			BOSUuid recordId = BOSUuid.read(id);
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.ID, recordId);
			prepareUIContext(uiContext, new ActionEvent(btnView, 0, "Double Clicked"));
			IUIFactory uiFactory = UIFactory.createUIFactory(getEditUIModal());
			IUIWindow uiWindow = uiFactory.create(getEditUIName(), uiContext, null,"FINDVIEW");
			uiWindow.show();
		}
	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkInvitePorjectSelected();
		super.actionAddNew_actionPerformed(e);
	}
	public void actionViewDoProccess_actionPerformed(ActionEvent e) throws Exception{
		checkSelected();
		String id = getSelectedKeyValue();
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo[] procInsts = service.getProcessInstanceByHoldedObjectId(id);
		for (int i = 0, n = procInsts.length; i < n; i++) {
			if (procInsts[i].getState().startsWith("open")) {
				processInstInfo = procInsts[i];
			}
		}
		if (processInstInfo == null) {
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if(procInsts== null || procInsts.length <=0)
				MsgBox.showInfo(this ,EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_WFHasNotInstance"));
	        else if(procInsts.length ==1){
	        	showWorkflowDiagram(procInsts[0]);
	        }else{
	        	UIContext uiContext = new UIContext(this);
	        	uiContext.put("procInsts",procInsts);
	        	String className = ProcessRunningListUI.class.getName();
	        	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(className,uiContext);
	        	uiWindow.show();
	        }
		 } else {
			 showWorkflowDiagram(processInstInfo);
		 }
	}
    private void showWorkflowDiagram(ProcessInstInfo processInstInfo)throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		String className = BasicWorkFlowMonitorPanel.class.getName();
		IUIWindow uiWindow = UIFactory.createUIFactory(getEditUIModal()).create(className, uiContext);
		uiWindow.show();
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		preparePrintPage(getBillListTable());
		getBillListTable().getPrintManager().print();
	}
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		preparePrintPage(getBillListTable());
		getBillListTable().getPrintManager().printPreview();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.tblBaseInvite.setEnabled(false);
		this.tblBaseInvite.setColumnMoveable(true);
		this.tblBaseInvite.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblBaseInvite.setAutoscrolls(true);
		FDCHelper.formatTableDate(this.tblBaseInvite, "createDate");
		FDCHelper.formatTableDate(this.tblBaseInvite, "auditDate");
		
		this.tblBaseInvite.getColumn("respDept").getStyleAttributes().setHided(true);
		
		this.contBaseInvite.setTitle(getTitle());
		this.contBaseInvite.setEnableActive(false);
		this.contBaseInvite.setTitle(getTitle());
		
		this.contBill.setEnableActive(false);
		kDSplitPane1.setDividerLocation(300);
		
		HashMap hmParamIn = new HashMap();
		hmParamIn.put("CIFI_INVITEAUDITTIME", SysContext.getSysContext().getCurrentFIUnit().getId().toString());
		HashMap hmAllParam = ParamControlFactory.getRemoteInstance().getParamHashMap(hmParamIn);
		if(hmAllParam.get("CIFI_INVITEAUDITTIME")!=null&&Boolean.valueOf(hmAllParam.get("CIFI_INVITEAUDITTIME").toString()).booleanValue()){
			this.tblBaseInvite.setEnabled(true);
			
			String formatString = "yyyy-MM-dd";
			this.tblBaseInvite.getColumn(COL_AUDITDATE).getStyleAttributes().setNumberFormat(formatString);
			
			KDDatePicker pk = new KDDatePicker();
			KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
			this.tblBaseInvite.getColumn(COL_AUDITDATE).setEditor(dateEditor);
			
			for(int i=0;i<this.tblBaseInvite.getColumnCount();i++){
				if(this.tblBaseInvite.getColumnKey(i).equals(COL_AUDITDATE)){
					this.tblBaseInvite.getColumn(i).getStyleAttributes().setLocked(false);
				}else{
					this.tblBaseInvite.getColumn(i).getStyleAttributes().setLocked(true);
				}
			}
			
			KDWorkButton btnUpdate=new KDWorkButton();
			btnUpdate.setText("审批日期更新");
			this.toolBar.add(btnUpdate);
			btnUpdate.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnUpdate_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
	protected void btnUpdate_actionPerformed(ActionEvent e) throws Exception {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("auditTime");
		for(int i=0;i<this.tblBaseInvite.getRowCount();i++){
			BOSUuid id=(BOSUuid)this.tblBaseInvite.getRow(i).getCell(COL_ID).getValue();
			Date auditTime=(Date)this.tblBaseInvite.getRow(i).getCell(COL_AUDITDATE).getValue();
			BaseInviteInfo info=new BaseInviteInfo();
			info.setId(id);
			info.setAuditTime(auditTime);
			this.getBizInterface().updatePartial(info, sel);
		}
		FDCClientUtils.showOprtOK(this);
	}
	protected abstract String getTitle();
	
	//反编译结束

	

	private CommonQueryDialog commonQueryDialog;
    private CustomerQueryPanel filterUI;
    private int count = 0;
	   protected CommonQueryDialog initCommonQueryDialog()
	    {
	        if(commonQueryDialog != null)
	        {
	            return commonQueryDialog;
	        } else
	        {
	            commonQueryDialog = super.initCommonQueryDialog();
	            commonQueryDialog.setWidth(400);
	            commonQueryDialog.addUserPanel(getFilterUI());
	            count++;
	            return commonQueryDialog;
	        }
	    }
	
	    private CustomerQueryPanel getFilterUI()
	    {
	        if(filterUI == null)
	            try
	            {
	                filterUI = new ProjectCommonQueryUI(this, actionOnLoad);
	            }
	            catch(Exception e)
	            {
	                e.printStackTrace();
	                abort(e);
	            }
	        return filterUI;
	    }
	
		protected FilterInfo getInvitePorjectFilter() throws Exception {
			FilterInfo filter = super.getInvitePorjectFilter();
			FilterInfo ff = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
			if(count>0)
			{
				 ff = filterUI.getFilterInfo();
				 filter.mergeFilter(ff, "AND");
			}
			
			

			return filter;
		}
	  
	    
	

}
