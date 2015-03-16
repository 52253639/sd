/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteFixHeadFactory;
import com.kingdee.eas.fdc.invite.InviteFixHeadInfo;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class InviteFixEditUI extends AbstractInviteFixEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(InviteFixEditUI.class);
    
    /**
     * output class constructor
     */
    public InviteFixEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
		super.onLoad();
		if(this.getOprtState() == OprtState.ADDNEW) {
			actionFixALine_actionPerformed(null);
		}
	}
    
    protected void verifyInputForSave() throws Exception {
    	super.verifyInputForSave();
    	checkRequired();
    	if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"附件分录不能为空！");
			SysUtil.abort();
		}
    }
    
    
    private void checkRequired() {
    	for(int i=0; i<kdtEntry.getRowCount(); i++) {
    		boolean b1 = kdtEntry.getCell(i, "supplier").getValue() == null;
    		boolean b2 = kdtEntry.getCell(i, "fix").getValue() == null;
    		if(b1||b2) {
    			MsgBox.showConfirm2("第" + (i+1) + "行未填写完整");
    			SysUtil.abort();
    		}
    	}
    }
    
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	@Override
	protected void closeDeleteAttachment() {
	}

	@Override
	protected BaseInviteInfo createNewDate() {
		InviteFixHeadInfo info=new InviteFixHeadInfo();
		createBaseInvite(info);
		return info;
	}

	@Override
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new InviteFixInfo();
	}

	@Override
	protected String getTDFileName() {
		return null;
	}

	@Override
	protected IMetaDataPK getTDQueryPK() {
		return null;
	}

	@Override
	protected void attachListeners() {
		
	}

	@Override
	protected void detachListeners() {
		
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return InviteFixHeadFactory.getRemoteInstance();
	}
	
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
//		loadOther();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		
		super.loadFields();
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("*"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("entry.supplier.grade.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.*"));
		return sic;
	}
	
	protected void initTable() {
		KDWorkButton btnFixAddRowinfo = new KDWorkButton();
		KDWorkButton btnFixInsertRowinfo = new KDWorkButton();
		KDWorkButton btnFixDeleteRowinfo = new KDWorkButton();

		this.actionFixALine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnFixAddRowinfo = (KDWorkButton) contEntry.add(this.actionFixALine);
		btnFixAddRowinfo.setText(getResource("addRow"));
		btnFixAddRowinfo.setSize(new Dimension(140, 19));

		this.actionFixILine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnFixInsertRowinfo = (KDWorkButton) contEntry.add(this.actionFixILine);
		btnFixInsertRowinfo.setText(getResource("insertRow"));
		btnFixInsertRowinfo.setSize(new Dimension(140, 19));

		this.actionFixRLine.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnFixDeleteRowinfo = (KDWorkButton) contEntry.add(this.actionFixRLine);
		btnFixDeleteRowinfo.setText(getResource("deleteRow"));
		btnFixDeleteRowinfo.setSize(new Dimension(140, 19));
		
		this.kdtEntry.checkParsed();
		FDCTableHelper.disableAutoAddLine(kdtEntry);
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.F7GradeSetUpQuery");
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		this.kdtEntry.getColumn("grade").setEditor(f7Editor);
		KDTextField remark = new KDTextField();
		KDTDefaultCellEditor remarkEditor = new KDTDefaultCellEditor(remark);
		this.kdtEntry.getColumn("remark").setEditor(remarkEditor);
		
		KDFormattedTextField fix = new KDFormattedTextField();
		fix.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		fix.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		fix.setPrecision(2);
		KDTDefaultCellEditor fixEditor = new KDTDefaultCellEditor(fix);
		this.kdtEntry.getColumn("fix").setEditor(fixEditor);
		this.kdtEntry.getColumn("fix").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("fix").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	
	protected void initControl() {
		super.initControl();
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");
		
		Set supplierId=new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.inviteProject.id",this.editData.getInviteProject().getId()));
		filter.getFilterItems().add(new FilterItemInfo("parent.state",FDCBillStateEnum.AUDITTED_VALUE));
		view.setFilter(filter);
		
		try {
			SupplierQualifyEntryCollection coll = SupplierQualifyEntryFactory.getRemoteInstance().getSupplierQualifyEntryCollection(view);
			for(int i=0;i<coll.size();i++){
				supplierId.add(coll.get(i).getSupplier().getId().toString());
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.ELIGIBILITY_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
		if(supplierId.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id",supplierId,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id","'null'"));
		}
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		this.kdtEntry.getColumn("supplier").setEditor(f7Editor);
	}
	
	public void actionFixALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		InviteFixInfo info = new InviteFixInfo();
		row.setUserObject(info);
	}
	public void actionFixILine_actionPerformed(ActionEvent e) throws Exception {
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
		InviteFixInfo info = new InviteFixInfo();
		row.setUserObject(info);
	}
	public void actionFixRLine_actionPerformed(ActionEvent e) throws Exception {
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
	
	private boolean isExistSupplier(SupplierStockInfo info, int selectedIndex ) {
		for(int i =0; i<kdtEntry.getRowCount(); i++ ) {
			if(i==selectedIndex) {
				continue;
			}
			SupplierStockInfo supplier = (SupplierStockInfo) kdtEntry.getCell(i, "supplier").getValue();
			if(supplier!=null && supplier.getId().toString().equals(info.getId().toString())) {
				return true;
			}
		}
		
		return false;
	}
	
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception{
		if(e.getValue() == null ) {
			return;
		}
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		
		String key = kdtEntry.getColumnKey(colIndex);
		if("supplier".equals(key)) {
			SupplierStockInfo info = (SupplierStockInfo) kdtEntry.getCell(rowIndex, colIndex).getValue();
			if(this.isExistSupplier(info, rowIndex)) {
				kdtEntry.getCell(rowIndex, colIndex).setValue(null);
				return;
			}
			
			GradeSetUpInfo gradeInfo = GradeSetUpFactory.getRemoteInstance().getGradeSetUpInfo(new ObjectUuidPK(info.getGrade().getId()));
			kdtEntry.getCell(rowIndex, "grade").setValue(gradeInfo);
			
		}
    }

	
	
	public void setOprtState(String oprtType) {
		if(oprtType.equals("FINDVIEW")){
			oprtType=OprtState.VIEW;
		}
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.actionFixALine.setEnabled(false);
			this.actionFixILine.setEnabled(false);
			this.actionFixRLine.setEnabled(false);
			this.lockUIForViewStatus();
		} else {
			/*if(this.editData!=null&&this.editData.getInviteProject()!=null&&this.editData.getInviteProject().isScalingRules()){
				this.actionFixALine.setEnabled(false);
				this.actionFixILine.setEnabled(false);
				this.actionFixRLine.setEnabled(false);
			}*/
			if(this.editData!=null&&this.editData.getInviteProject()!=null ){
				if(this.editData.getInviteProject().getScalingRule() == null || this.editData.getInviteProject().getScalingRule().isNeedCorrection()) {
					this.actionFixALine.setEnabled(false);
					this.actionFixILine.setEnabled(false);
					this.actionFixRLine.setEnabled(false);
				}else{
					this.actionFixALine.setEnabled(true);
					this.actionFixILine.setEnabled(true);
					this.actionFixRLine.setEnabled(true);
				}
			}
			this.unLockUI();
		}
	}
}