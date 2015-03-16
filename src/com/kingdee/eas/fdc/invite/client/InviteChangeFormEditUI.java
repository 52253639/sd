/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
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
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormEntryInfo;
import com.kingdee.eas.fdc.invite.InviteChangeFormFactory;
import com.kingdee.eas.fdc.invite.InviteChangeFormInfo;
import com.kingdee.eas.fdc.invite.InviteChangeFormSupplierCollection;
import com.kingdee.eas.fdc.invite.InviteChangeFormSupplierInfo;
import com.kingdee.eas.fdc.invite.InviteProjectFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InviteChangeFormEditUI extends AbstractInviteChangeFormEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteChangeFormEditUI.class);
    protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    public InviteChangeFormEditUI() throws Exception
    {
        super();
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

		this.kdtEntry.checkParsed();
		FDCTableHelper.disableAutoAddLine(kdtEntry);

		this.kdtEntry.getColumn("name").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("name").getStyleAttributes().setFontColor(Color.BLUE);
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
    protected String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
    public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		InviteChangeFormEntryInfo info = new InviteChangeFormEntryInfo();
		row.setUserObject(info);
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
		InviteChangeFormEntryInfo info = new InviteChangeFormEntryInfo();
		row.setUserObject(info);
	}

	private boolean confirmRemove(Component comp) {
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
				this.kdtEntry.removeRow(top);
			}
			loadInvitePorject();
		}
	}
	public void storeFields()
	{
		super.storeFields();
		this.editData.setName(this.txtProject.getText()+ "-改变采购方式请示");
	}
	
	public void loadFields() {
		detachListeners();
		super.loadFields();
		
		setSaveActionStatus();
		
		loadInvitePorject();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
	}
	protected void loadInvitePorject(){
		String projectStr="";
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			InviteProjectInfo info=(InviteProjectInfo) this.kdtEntry.getRow(i).getCell("number").getValue();
			if(info!=null){
				projectStr=projectStr+info.getName()+";";
			}
		}
		this.txtProject.setText(projectStr);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return InviteChangeFormFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		return sic;
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
		if(this.getOprtState()==OprtState.ADDNEW) {
			this.actionALine_actionPerformed(null);
		}
	}
	
	protected IObjectValue createNewData() {
		InviteChangeFormInfo info = new InviteChangeFormInfo();
		info.setOrgUnit((FullOrgUnitInfo) getUIContext().get("org"));
		return info;
	}
	protected void initControl() {
		this.actionPrintPreview.setVisible(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionPrint.setVisible(true);
		this.actionPrint.setEnabled(true);
		
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
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		f7Box.setDisplayFormat("$number$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteProjectQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",editData.getOrgUnit().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		this.kdtEntry.getColumn("number").setEditor(f7Editor);
		this.kdtEntry.getColumn("number").setRenderer(new ObjectValueRender(){
			public String getText(Object obj)
			{
				if(obj instanceof InviteProjectInfo){
					
					return ((InviteProjectInfo) obj).getNumber();
				}
				return null;
			}
		});
		
		KDBizPromptBox prmtInviteForm = new KDBizPromptBox();
		prmtInviteForm.setDisplayFormat("$name$");
		prmtInviteForm.setEditFormat("$number$");
		prmtInviteForm.setCommitFormat("$number$");
		prmtInviteForm.setQueryInfo("com.kingdee.eas.fdc.invite.app.InviteFormQuery");
		KDTDefaultCellEditor inviteFormEditor = new KDTDefaultCellEditor(prmtInviteForm);
		
		kdtEntry.getColumn("shouldInviteForm").setEditor(inviteFormEditor);
		kdtEntry.getColumn("nowInviteForm").setEditor(inviteFormEditor);
		
		if(!checkCanOperate()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
		}
		
		this.txtName.setEnabled(false);
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	protected void verifyInputForSave() throws Exception {
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtReason);
		
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"分录不能为空！");
			SysUtil.abort();
		}
		for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
			IRow row = this.kdtEntry.getRow(i);
				
			if (row.getCell("number").getValue() == null) {
				FDCMsgBox.showWarning(this,"立项编码不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("number"));
				SysUtil.abort();
			} 
			if (row.getCell("shouldInviteForm").getValue() == null) {
				FDCMsgBox.showWarning(this,"规定应采用的采购方式不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("shouldInviteForm"));
				SysUtil.abort();
			} 
			if (row.getCell("nowInviteForm").getValue() == null) {
				FDCMsgBox.showWarning(this,"现拟采用的采购方式不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("nowInviteForm"));
				SysUtil.abort();
			} 
		}
	}
	protected void verifyInputForSubmint() throws Exception {
		verifyInputForSave();
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !com.kingdee.bos.ctrl.swing.StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null || getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		InviteChangeFormPrintDataProvider data = new InviteChangeFormPrintDataProvider(editData.getString("id"), getTDQueryPK());
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
		InviteChangeFormPrintDataProvider data = new InviteChangeFormPrintDataProvider(editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/InviteChangeForm";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.InviteChangeFormPrintQuery");
	}
	public void setOprtState(String oprtType) {
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
	protected void attachListeners() {
	}
	protected void detachListeners() {
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if("number".equals(this.kdtEntry.getColumn(e.getColIndex()).getKey())){
			InviteProjectInfo project=(InviteProjectInfo) this.kdtEntry.getRow(e.getRowIndex()).getCell("number").getValue();
			if(project==null){
				this.kdtEntry.getRow(e.getRowIndex()).getCell("name").setValue(null);
			}else{
				for(int i=0;i<this.kdtEntry.getRowCount();i++){
					InviteProjectInfo pro=(InviteProjectInfo) this.kdtEntry.getRow(i).getCell("number").getValue();
					if(i==e.getRowIndex()||pro==null) continue;
					if(pro.getId().toString().equals(project.getId().toString())){
						FDCMsgBox.showWarning(this, "立项编码不能重复！");
						this.kdtEntry.getRow(e.getRowIndex()).getCell("number").setValue(null);
						return;
					}
				}
				this.kdtEntry.getRow(e.getRowIndex()).getCell("name").setValue(project.getName());
				loadInvitePorject();
			}
		}
	}
	protected void kdtEntry_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2 && this.kdtEntry.getColumnKey(e.getColIndex()).equals("name")) {
			InviteProjectInfo info=(InviteProjectInfo) this.kdtEntry.getRow(e.getRowIndex()).getCell("number").getValue();
			if(info!=null){
				UIContext uiContext = new UIContext(this);
				uiContext.put("ID", info.getId().toString());
				IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL).create(InviteProjectEditUIEx.class.getName(),uiContext, null, OprtState.VIEW);
				ui.show();
			}
		}
	}
}