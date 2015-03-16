/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ICommonBOSType;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.entity.EntityObjectInfo;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.BaseInviteEntryInfo;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public abstract class BaseInviteEditUI extends AbstractBaseInviteEditUI {
	private static final Logger logger = CoreUIObject.getLogger(BaseInviteEditUI.class);
	protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
	public final static String INVITEPROJECTID="inviteProjectID";
	

	public static boolean checkExist( String tableName, String id ) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder();
		String sql = "select fid from " + tableName + " where fid='" +id + "'"; 
		builder.appendSql(sql);
		IRowSet set = builder.executeQuery();
		if(set!=null && set.size()>0) {
			return true;
		}
		
		return false;
	}
	
	public BaseInviteEditUI() throws Exception {
		super();
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

	protected String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}

	protected void initTable() {
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		KDWorkButton btnInsertRowinfo = new KDWorkButton();
		KDWorkButton btnDeleteRowinfo = new KDWorkButton();

		this.actionALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton) contEntry.add(this.actionALine);
		btnAddRowinfo.setText(getResource("addRow"));
		btnAddRowinfo.setSize(new Dimension(140, 19));

		this.actionILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton) contEntry.add(this.actionILine);
		btnInsertRowinfo.setText(getResource("insertRow"));
		btnInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionRLine);
		btnDeleteRowinfo.setText(getResource("deleteRow"));
		btnDeleteRowinfo.setSize(new Dimension(140, 19));

		KDTextField testField = new KDTextField();
		testField.setMaxLength(2000);
		KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField);

		this.kdtEntry.checkParsed();
		FDCTableHelper.disableAutoAddLine(kdtEntry);

		KDDatePicker pk = new KDDatePicker();
		KDTDefaultCellEditor dateEditor = new KDTDefaultCellEditor(pk);
		this.kdtEntry.getColumn("time").setEditor(dateEditor);

		String formatString = "yyyy-MM-dd";
		this.kdtEntry.getColumn("time").getStyleAttributes().setNumberFormat(formatString);

		this.kdtEntry.getColumn("content").setEditor(editorSize);
		this.kdtEntry.getColumn("content").getStyleAttributes().setWrapText(true);

		this.kdtEntry.getColumn("attach").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("attach").getStyleAttributes().setFontColor(Color.BLUE);
		this.kdtEntry.getColumn("attach").setWidth(400);
	}

	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		CoreBillEntryBaseInfo info = this.createNewEntryDate();
		if(info == null ) {
			return;
		}
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
		if(row.getCell("time")!=null)
			row.getCell("time").setValue(FDCCommonServerHelper.getServerTimeStamp());
	}

	public void actionILine_actionPerformed(ActionEvent e) throws Exception {
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
		CoreBillEntryBaseInfo info = this.createNewEntryDate();
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
		if(row.getCell("time")!=null)
			row.getCell("time").setValue(FDCCommonServerHelper.getServerTimeStamp());
	}

	protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && this.kdtEntry.getColumnKey(e.getColIndex()).equals("attach")) {
			String id = ((BaseInviteEntryInfo) this.kdtEntry.getRow(e.getRowIndex()).getUserObject()).getId().toString();
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
			this.kdtEntry.getRow(e.getRowIndex()).getCell("attach").setValue(loadAttachment(id));
		}
	}

	public boolean confirmRemove(Component comp) {
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}

	public void actionRLine_actionPerformed(ActionEvent e) throws Exception {
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
				try {
					if(this.kdtEntry.getRow(top).getUserObject() instanceof BaseInviteEntryInfo) {
						deleteAttachment(((BaseInviteEntryInfo) this.kdtEntry.getRow(top).getUserObject()).getId().toString());
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
				this.kdtEntry.removeRow(top);
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
	
	protected void storeOther() {

	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		if(editData.getId()!=null){
			FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		}
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
	protected KDTable getDetailTable() {
		return this.kdtEntry;
	}

	public void onLoad() throws Exception {
		initTable();
		super.onLoad();
		initControl();
		this.txtName.setEnabled(false);
		this.contRespDept.setVisible(false);
		this.prmtRespDept.setVisible(false);
		
		if(this.getOprtState() == OprtState.ADDNEW && this.kdtEntry.getColumn("attach")!=null) {//附件分录默认有一行
			this.actionALine_actionPerformed(null);
		}
	}
	protected abstract CoreBillEntryBaseInfo createNewEntryDate();

	protected abstract BaseInviteInfo createNewDate();

	protected void createBaseInvite(BaseInviteInfo info) {
		InviteProjectInfo prjInfo = (InviteProjectInfo) (getUIContext().get("inviteProject"));
		if (prjInfo != null) {
			info.setInviteProject(prjInfo);
			info.setOrgUnit(prjInfo.getOrgUnit());
			Object obj = this.getUIContext().get("MainMenuName");
			String title = "";
			if (!FDCHelper.isEmpty(obj)) {
				title = obj.toString();
			}

			info.setName(prjInfo.getName() + "-" + title);
			
		}
		info.setState(FDCBillStateEnum.SAVED);
	}
	protected IObjectValue createNewData() {
		return createNewDate();
	}
	protected boolean checkCanOperate() {
		boolean flag = false;
		if (editData.getOrgUnit() == null) {
			flag = false;
		}
		String orgId = editData.getOrgUnit() .getId().toString();
		if (currentOrg.getId().toString().equals(orgId)) {
			flag = true;
		} else {
			flag = false;
		}
		return flag;
	}
	
	// 责任人是否可以选择其他部门的人员
	protected boolean canSelecOtherOrgPerson() {
		boolean canSelectOtherOrgPerson = false;
		try {
			canSelectOtherOrgPerson = FDCUtils.getDefaultFDCParamByKey(null,
					null, FDCConstants.FDC_PARAM_SELECTPERSON);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return canSelectOtherOrgPerson;
	}
	
	protected void initControl() {
//		this.prmtRespDept.setRequired(true);
//		CtrlUnitInfo obj = editData.getCU();
//		if (obj == null) {
//			obj = SysContext.getSysContext().getCurrentCtrlUnit();
//		}
//		String cuId = obj.getId().toString();
//		FDCClientUtils.setRespDeptF7(prmtRespDept, this,
//				canSelecOtherOrgPerson() ? null : cuId);
		
//		FDCClientUtils.setRespDeptF7(prmtRespDept, this, this.editData.getOrgUnit().isIsCU() ? this.editData.getOrgUnit().getId().toString() : SysContext.getSysContext().getCurrentCtrlUnit().getId().toString());

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
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.btnPre.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		
		this.menuView.setVisible(false);
//		this.txtAllDays.setPrecision(0);
//		this.txtAllDays.setNegatived(false);
		
		 
//		UIContext uiContext = new UIContext(this);
//		uiContext.put("ID", ((BaseInviteInfo)this.editData).getInviteProject().getId().toString());
//		try {
//			InviteProjectEditUI ui = (InviteProjectEditUI) UIFactoryHelper.initUIObject(InviteProjectEditUI.class.getName(), uiContext, null,OprtState.VIEW);
//			ui.setVisible(true);
//			ui.setEnabled(false);
//			inviteProjectPanel.setEnabled(true);
//			inviteProjectPanel.setViewportView(ui);
//			inviteProjectPanel.setKeyBoardControl(true);
//			ui.setComponentEnable(false);
//		} catch (UIException e) {
//			e.printStackTrace();
//		}
		if(!checkCanOperate()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
	}
	protected void btnShowProject_actionPerformed(ActionEvent e)throws Exception {
		if(this.editData.getInviteProject()!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", this.editData.getInviteProject().getId().toString());
			IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InviteProjectEditUIEx.class.getName(),uiContext, null, OprtState.VIEW);
			ui.show();
		}
	}

	@Override
	protected void attachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void detachListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void verifyInputForSave() throws Exception {
		if(this.prmtRespDept.isVisible() ) {
			FDCClientVerifyHelper.verifyEmpty(this, prmtRespDept);
		}
		
		if(getNumberCtrl().isEnabled()) {
			FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		}
//		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteProject);
		
		if(this.kdtEntry.isVisible()&&this.kdtEntry.getColumn("attach")!=null){
			if(this.kdtEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"附件分录不能为空！");
				SysUtil.abort();
			}
			for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
				IRow row=this.kdtEntry.getRow(i);
				if(row.getCell("attach").getValue()==null||"".equals(row.getCell("attach").getValue().toString().trim())){
					FDCMsgBox.showWarning(this,"附件不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("attach"));
					SysUtil.abort();
				}
			}
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
	}
	protected abstract String getTDFileName();

	protected abstract IMetaDataPK getTDQueryPK();

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		logger.info("打印预览");
		ArrayList idList = new ArrayList();
		if (editData != null && !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InvitePrintDataProvider data = new InvitePrintDataProvider(editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}

	protected abstract void closeDeleteAttachment();

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

	public void setOprtState(String oprtType) {
		if(oprtType.equals("FINDVIEW")){
			oprtType=OprtState.VIEW;
		}
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			this.lockUIForViewStatus();
		} else {
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			this.unLockUI();
		}
	}
	public void showDialog(JComponent owner, KDTable table, int X, int Y, int len)
    {
        int rowIndex = table.getSelectManager().getActiveRowIndex();
        int colIndex = table.getSelectManager().getActiveColumnIndex();
        ICell cell = table.getCell(rowIndex, colIndex);
        if(cell.getValue() == null)
            return;
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
}