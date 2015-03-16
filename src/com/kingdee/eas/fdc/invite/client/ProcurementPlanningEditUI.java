/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.invite.BaseInviteEntryInfo;
import com.kingdee.eas.fdc.invite.InviteContractFrameCollection;
import com.kingdee.eas.fdc.invite.InviteContractFrameFactory;
import com.kingdee.eas.fdc.invite.InviteContractFrameInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeInfo;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryCollection;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryFactory;
import com.kingdee.eas.fdc.invite.InviteTenderPlanningEntryInfo;
import com.kingdee.eas.fdc.invite.ProcurementPlanningAttEntryCollection;
import com.kingdee.eas.fdc.invite.ProcurementPlanningAttEntryFactory;
import com.kingdee.eas.fdc.invite.ProcurementPlanningAttEntryInfo;
import com.kingdee.eas.fdc.invite.ProcurementPlanningEntryCollection;
import com.kingdee.eas.fdc.invite.ProcurementPlanningEntryInfo;
import com.kingdee.eas.fdc.invite.ProcurementPlanningFactory;
import com.kingdee.eas.fdc.invite.ProcurementPlanningInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ProcurementPlanningEditUI extends AbstractProcurementPlanningEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ProcurementPlanningEditUI.class);
    
    /**
     * output class constructor
     */
    public ProcurementPlanningEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	kdtAttEntry.checkParsed();
    	super.onLoad();
    	initControl();
    	
    	if( this.getOprtState() == OprtState.ADDNEW ) {
    		actionAddAttachment_actionPerformed(null);
//    		row.getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
    	}
    	
    }
    
    protected void initControl() throws Exception{
    	KDDetailedArea desc = new KDDetailedArea(250, 200);
		desc.setMaxLength(1000);
    	kdtEntry.getColumn("desc").setEditor(new KDTDefaultCellEditor(desc));
    	kdtAttEntry.getColumn("attachment").getStyleAttributes().setLocked(true);
    	
		this.prmtProject.setSelector(new F7ProjectSelector(this));
		prmtProject.setDisplayFormat("$name$");
		prmtProject.setEditFormat("$number$");
		prmtProject.setCommitFormat("$number$");
		prmtProject.setRequired(true);
		
		prmtProject.addSelectorListener(new SelectorListener() {
			public void willShow(SelectorEvent e) {
				KDBizPromptBox f7 = (KDBizPromptBox) e.getSource();
				f7.getQueryAgent().resetRuntimeEntityView();
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				
				filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.longNumber", editData.getOrgUnit().getLongNumber()+"%",CompareType.LIKE));
				view.setFilter(filter);
				f7.setEntityViewInfo(view);
		}});
    	
		KDBizPromptBox prmtContract = new KDBizPromptBox();
		prmtContract.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteContractFrameQuery");
		prmtContract.setDisplayFormat("$name$");
		prmtContract.setEditFormat("$number$");
		prmtContract.setCommitFormat("$number$");
		kdtEntry.getColumn("contractFramework").setEditor(new KDTDefaultCellEditor(prmtContract));
		
    	KDFormattedTextField contractCount = new KDFormattedTextField();
    	contractCount.setDataType(KDFormattedTextField.INTEGER);
    	contractCount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		KDTDefaultCellEditor contractCountEditor = new KDTDefaultCellEditor(contractCount);
		this.kdtEntry.getColumn("contractCount").setEditor(contractCountEditor);
    	
		KDWorkButton btnAddEntry = new KDWorkButton();
		KDWorkButton btnInsertEntry  = new KDWorkButton();
		KDWorkButton btnDeleteEntry  = new KDWorkButton();
		
		this.actionAddEntry.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddEntry = (KDWorkButton)contEntry.add(this.actionAddEntry);
		btnAddEntry.setText("新增分录");
		btnAddEntry.setSize(new Dimension(140, 19));

		this.actionInsertEntry.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertEntry = (KDWorkButton) contEntry.add(this.actionInsertEntry);
		btnInsertEntry.setText("插入分录");
		btnInsertEntry.setSize(new Dimension(140, 19));

		this.actionDeleteEntry.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteEntry = (KDWorkButton) contEntry.add(this.actionDeleteEntry);
		btnDeleteEntry.setText("删除分录");
		btnDeleteEntry.setSize(new Dimension(140, 19));
		
		KDWorkButton btnAddAttachment = new KDWorkButton();
		KDWorkButton btnInsertAttachment= new KDWorkButton();
		KDWorkButton btnDeleteAttachment= new KDWorkButton();
		
		this.actionAddAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddAttachment = (KDWorkButton)contAttachment.add(this.actionAddAttachment);
		btnAddAttachment.setText("新增分录");
		btnAddAttachment.setSize(new Dimension(140, 19));

		this.actionInsertAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertAttachment = (KDWorkButton) contAttachment.add(this.actionInsertAttachment);
		btnInsertAttachment.setText("插入分录");
		btnInsertAttachment.setSize(new Dimension(140, 19));

		this.actionDeleteAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteAttachment = (KDWorkButton) contAttachment.add(this.actionDeleteAttachment);
		btnDeleteAttachment.setText("删除分录");
		btnDeleteAttachment.setSize(new Dimension(140, 19));
		
		/*if(kdtAttEntry.getRowCount()<=1) {
			btnDeleteAttachment.setEnabled(false);
    	}*/
	}
    
    protected String loadAttachment(String id) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name = "";
		for (int i = 0; i < col.size(); i++) {
			name = name + col.get(i).getAttachment().getName() + " ";
		}
		return name;
	}
    
    protected void loadEntry(){
//    	kdtAttEntry.checkParsed();
    	ProcurementPlanningAttEntryCollection col=editData.getAttEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtAttEntry.removeRows();
		for(int i=0;i<col.size();i++){
			ProcurementPlanningAttEntryInfo entry=col.get(i);
			IRow row=this.kdtAttEntry.addRow();
			row.setUserObject(entry);
			row.getCell("content").setValue(entry.getContent());
			row.getCell("time").setValue(entry.getTime());
			try {
				row.getCell("attachment").setValue(loadAttachment(entry.getId().toString()));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}	
    
    protected void storeEntry(){
    	editData.getAttEntry().clear();
    	for(int i=0;i<this.kdtAttEntry.getRowCount();i++){
    		IRow row = this.kdtAttEntry.getRow(i);
    		ProcurementPlanningAttEntryInfo entry=(ProcurementPlanningAttEntryInfo) row.getUserObject();
    		entry.setSeq(i);
    		entry.setContent((String)row.getCell("content").getValue());
    		entry.setTime((Date)row.getCell("time").getValue());
    		editData.getAttEntry().add(entry);
    	}
    }
    
    public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add("*");
		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("attEntry.*"));
		sic.add(new SelectorItemInfo("project.*"));
		
		return sic;
	}
    
    protected IObjectValue createNewData() {
    	ProcurementPlanningInfo info = new ProcurementPlanningInfo();
    	Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
    	info.setBizDate(now);
		info.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		Object project = this.getUIContext().get("curProject");
		if(project !=null && project instanceof CurProjectInfo ) {
			info.setProject((CurProjectInfo) project);
		}
		
		InviteContractFrameCollection conFrameColl = null;
		try {
			conFrameColl = InviteContractFrameFactory.getRemoteInstance().getInviteContractFrameCollection();
		} catch (BOSException e) {
			this.handleException(e);
		}
		
		if(conFrameColl!=null && conFrameColl.size()>0) {
			ProcurementPlanningEntryCollection  entryColl = info.getEntry();
			for(int i=0; i<conFrameColl.size(); i++) {
				ProcurementPlanningEntryInfo entry = new ProcurementPlanningEntryInfo();
				entry.setContractFrame(conFrameColl.get(i));
				entryColl.add(entry);
			}
		}
		
    	return info;
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	storeEntry();
        super.storeFields();
    }
    
    public void loadFields() {
		super.loadFields();
		if(this.getOprtState()!= OprtState.ADDNEW) {
			loadEntry();
		}
	}

    /**
     * output actionAddEntry_actionPerformed
     */
    public void actionAddEntry_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = this.kdtEntry.addRow();
    	ProcurementPlanningEntryInfo info = new ProcurementPlanningEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
    }

    /**
     * output actionInsertEntry_actionPerformed
     */
    public void actionInsertEntry_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = null;
		if (this.kdtEntry.getSelectManager().size() > 0) {
			int top = this.kdtEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtEntry))
				row = this.kdtEntry.addRow();
			else
				row = this.kdtEntry.addRow(top);
		} else {
			row = this.kdtEntry.addRow();
		}
		ProcurementPlanningEntryInfo info = new ProcurementPlanningEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
    }

    /**
     * output actionDeleteEntry_actionPerformed
     */
    public void actionDeleteEntry_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.kdtEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtEntry.removeRow(top);
			}
		}
    }

    /**
     * output actionAddAttachment_actionPerformed
     */
    public void actionAddAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = this.kdtAttEntry.addRow();
		ProcurementPlanningAttEntryInfo info = new ProcurementPlanningAttEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
		if(row.getCell("time")!=null)
			row.getCell("time").setValue(FDCCommonServerHelper.getServerTimeStamp());
    }

    /**
     * output actionInsertAttachment_actionPerformed
     */
    public void actionInsertAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	IRow row = null;
		if (this.kdtAttEntry.getSelectManager().size() > 0) {
			int top = this.kdtAttEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtAttEntry))
				row = this.kdtAttEntry.addRow();
			else
				row = this.kdtAttEntry.addRow(top);
		} else {
			row = this.kdtAttEntry.addRow();
		}
		ProcurementPlanningAttEntryInfo info = new ProcurementPlanningAttEntryInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
		if(row.getCell("time")!=null)
			row.getCell("time").setValue(FDCCommonServerHelper.getServerTimeStamp());
    }

    /**
     * output actionDeleteAttachment_actionPerformed
     */
    public void actionDeleteAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.kdtAttEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtAttEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}else if(kdtAttEntry.getRowCount() == 1) {
			MsgBox.showWarning("必须有一个附件");
			SysUtil.abort();
		}
    	
		if (confirmRemove(this)) {
			int top = this.kdtAttEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtAttEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtAttEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				try {
					deleteAttachment(((BaseInviteEntryInfo) this.kdtAttEntry.getRow(top).getUserObject()).getId().toString());
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
				this.kdtAttEntry.removeRow(top);
			}
		}
    }
    
    protected void deleteAttachment(String id) throws BOSException, EASBizException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if (col.size() > 0) {
			for (int i = 0; i < col.size(); i++) {
				EntityViewInfo attview = new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();

				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id", col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if (attcol.size() == 1) {
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				} else if (attcol.size() > 1) {
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
    
	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	
	protected void closeDeleteAttachment(){
		if(editData!=null){
			for(int i=0;i<this.editData.getAttEntry().size();i++){
				try {
					if(!BaseInviteEditUI.checkExist("T_INV_ProcurementPlanningAttEn", this.editData.getAttEntry().get(i).getId().toString())) {
						this.deleteAttachment(this.editData.getAttEntry().get(i).getId().toString());
					}
					/*if(!ProcurementPlanningAttEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getAttEntry().get(i).getId().toString()))){
					}*/
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean checkBeforeWindowClosing() {
		if (hasWorkThreadRunning()) {
			return false;
		}
		if (getTableForPrintSetting() != null) {
			this.savePrintSetting(this.getTableForPrintSetting());
		}
		boolean b = true;
		if (!b) {
			return b;
		} else {
			if (isModify()) {
				int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Save_Exit"));
				if (result == KDOptionPane.YES_OPTION) {
					try {
						if (editData.getState() == null || editData.getState() == FDCBillStateEnum.SAVED) {
							actionSave.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSave, ActionEvent.ACTION_PERFORMED, btnSave.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSubmit, ActionEvent.ACTION_PERFORMED, btnSubmit.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
						}
					} catch (Exception exc) {
						return false;
					}
				} else if (result == KDOptionPane.NO_OPTION) {
					closeDeleteAttachment();
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}
	
	protected void verifyInputForSave() throws Exception {
		/*if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		
		if(this.kdtAttEntry.isVisible()&&this.kdtAttEntry.getColumn("attachment")!=null){
			for (int i = 0; i < this.kdtAttEntry.getRowCount(); i++) {
				IRow row=this.kdtAttEntry.getRow(i);
				if(row.getCell("attachment").getValue()==null||"".equals(row.getCell("attachment").getValue().toString().trim())){
					FDCMsgBox.showWarning(this,"附件不能为空！");
					this.kdtAttEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtAttEntry.getColumnIndex("attachment"));
					SysUtil.abort();
				}
			}
		}*/
	}
	
	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
		
		for(int i=0; i<kdtEntry.getRowCount(); i++) {
			 if((InviteContractFrameInfo) kdtEntry.getCell(i, "contractFramework").getValue()==null) {
				 MsgBox.showWarning("第" +(i+1) + "行有必录项未录入");
				 SysUtil.abort();
			 }
		}
		
		if(this.kdtAttEntry.isVisible()&&this.kdtAttEntry.getColumn("attachment")!=null){
			for (int i = 0; i < this.kdtAttEntry.getRowCount(); i++) {
				IRow row=this.kdtAttEntry.getRow(i);
				if(row.getCell("attachment").getValue()==null||"".equals(row.getCell("attachment").getValue().toString().trim())){
					FDCMsgBox.showWarning(this,"附件不能为空！");
					this.kdtAttEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtAttEntry.getColumnIndex("attachment"));
					SysUtil.abort();
				}
			}
		}
	}
	
	protected void kdtAttEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && this.kdtAttEntry.getColumnKey(e.getColIndex()).equals("attachment")) {
			String id = ((ProcurementPlanningAttEntryInfo) this.kdtAttEntry.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
			boolean isEdit = false;
			if (OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
				isEdit = true;
			AttachmentUIContextInfo info = new AttachmentUIContextInfo();
			info.setBoID(id);
			info.setEdit(isEdit);
			String multi = (String) getUIContext().get("MultiapproveAttachment");
			if (multi != null && multi.equals("true")) {
				acm.showAttachmentListUIByBoIDNoAlready(this, info);
			} else {
				acm.showAttachmentListUIByBoID(this, info);
			}
			this.kdtAttEntry.getRow(e.getRowIndex()).getCell("attachment").setValue(loadAttachment(id));
		}
	}

	private void checkContractFrame(InviteContractFrameInfo info , int rowIndex ) {
		for(int i=0; i<kdtEntry.getRowCount(); i++ ) {
			InviteContractFrameInfo frame = (InviteContractFrameInfo) kdtEntry.getCell(i, "contractFramework").getValue();
			if( frame != null && i != rowIndex) {
				if(frame.getId().equals(info.getId())) {
					MsgBox.showWarning("所选合同架构已存在");
					kdtEntry.getCell(rowIndex, "contractFramework").setValue(null);
					SysUtil.abort();
				}
			}
		}
	}
	
	protected void kdtEntry_editStopped( KDTEditEvent e ) throws Exception {
		int colIndex = e.getColIndex();
		IRow row = kdtEntry.getRow(e.getRowIndex());
		if (row == null ) {
			return;
		}
		
		String key = kdtEntry.getColumnKey(colIndex);
		if ("contractFramework".equals(key)) { //
			InviteContractFrameInfo frame = (InviteContractFrameInfo) row.getCell("contractFramework").getValue();
			if(frame != null ) {
				checkContractFrame(frame, e.getRowIndex());
			}
		}
	}
	
	@Override
	protected void attachListeners() {
		
	}

	@Override
	protected void detachListeners() {
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return ProcurementPlanningFactory.getRemoteInstance();
	}

	@Override
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}

	@Override
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

}