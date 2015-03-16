/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.invite.BaseInviteEntryInfo;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteFixCollection;
import com.kingdee.eas.fdc.invite.InviteFixFactory;
import com.kingdee.eas.fdc.invite.InviteProjectInfo;
import com.kingdee.eas.fdc.invite.ScalingRuleFactory;
import com.kingdee.eas.fdc.invite.ScalingRuleInfo;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordEntryInfo;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordCollection;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordFactory;
import com.kingdee.eas.fdc.invite.SupplierInviteRecordInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;

/**
 * 供应商投标记录 编辑界面
 */
public class SupplierInviteRecordEditUI extends AbstractSupplierInviteRecordEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(SupplierInviteRecordEditUI.class);
	public SupplierInviteRecordEditUI() throws Exception
	{
		super();
	}
	public void storeFields()
	{
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
	}
	
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
//		loadOther();
		
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
		
		super.loadFields();
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price"});
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			row.getCell("name").setValue("第"+(i+1)+"标段");
		}
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierInviteRecordFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.*"));
		return sic;
	}
	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplier);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplier);
	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/SupplierInviteRecordForPrint";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.SupplierInviteRecordForPrintQuery");
	}
	protected BaseInviteInfo createNewDate() {
		SupplierInviteRecordInfo info=new SupplierInviteRecordInfo();
		createBaseInvite(info);
		try {
			info.setReturnDate(FDCCommonServerHelper.getServerTimeStamp());
			info.setOpenDate(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new SupplierInviteRecordEntryInfo();
	}
	protected void closeDeleteAttachment() {
		
	}
	protected void prmtSupplier_dataChanged(DataChangeEvent e) throws Exception {
		SupplierStockInfo supplier=(SupplierStockInfo)this.prmtSupplier.getValue();
		if(this.editData.getInviteProject()!=null&&supplier!=null){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", this.editData.getInviteProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
			if(this.editData.getId()!=null){
				filter.getFilterItems().add(new FilterItemInfo("id", this.editData.getId().toString(),CompareType.NOTEQUALS));
			}
			if(this.editData.getCreateTime()!=null){
				filter.getFilterItems().add(new FilterItemInfo("createTime", this.editData.getCreateTime(),CompareType.LESS));
			}
			view.setFilter(filter);
			SupplierInviteRecordCollection col=SupplierInviteRecordFactory.getRemoteInstance().getSupplierInviteRecordCollection(view);
			this.txtTimes.setValue(Integer.valueOf(col.size()+1));
		}else{
			this.txtTimes.setValue(Integer.valueOf(1));
		}
	}
	
	protected void verifyInputForSave() throws Exception { 
		super.verifyInputForSave();
		if(this.cbIsMultiple.isSelected()){
			if(this.kdtEntry.getRowCount()==0){
				FDCMsgBox.showWarning(this,"分标段报价不能为空！");
				SysUtil.abort();
			}
			for (int i = 0; i < this.kdtEntry.getRowCount(); i++) {
				IRow row=this.kdtEntry.getRow(i);
				BigDecimal price = (BigDecimal)row.getCell("price").getValue();
				if(row.getCell("price").getValue()==null){
					FDCMsgBox.showWarning(this,"供应商报价(元)不能为空！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("price"));
					SysUtil.abort();
				}
				if(price.compareTo(FDCHelper.ZERO)==0){
					FDCMsgBox.showWarning(this,"供应商报价(元)不能为0！");
					this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("price"));
					SysUtil.abort();
				}
			}
		}else{
			FDCClientVerifyHelper.verifyEmpty(this, this.txtPrice);
		}
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplier);
	}
	
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		int count = Integer.parseInt(txtTimes.getText());
		if(count>=2) {
			ScalingRuleInfo rule = this.editData.getInviteProject().getScalingRule();
			rule = ScalingRuleFactory.getRemoteInstance().getScalingRuleInfo( new ObjectUuidPK(rule.getId()) );
				
			if( rule.isNeedCorrection() ) {//需修正系数
				SupplierStockInfo supplier=(SupplierStockInfo)this.prmtSupplier.getValue();
				
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id",supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("head.inviteProject.id", this.editData.getInviteProject().getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("head.state", FDCBillStateEnum.AUDITTED_VALUE));
				view.setFilter(filter);
				
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("supplier.id");
				sic.add("head.inviteProject.id");
				sic.add("head.state");
				view.setSelector(sic);
				
				InviteFixCollection coll = InviteFixFactory.getRemoteInstance().getInviteFixCollection(view);
				if( coll.size()<=0 ) {
					MsgBox.showWarning("必须先做修正系数");
					SysUtil.abort();
				}
			}
		}
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
		
		KDFormattedTextField price = new KDFormattedTextField();
		price.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		price.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		price.setPrecision(2);
		KDTDefaultCellEditor priceEditor = new KDTDefaultCellEditor(price);
		this.kdtEntry.getColumn("price").setEditor(priceEditor);
		this.kdtEntry.getColumn("price").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEntry.getColumn("price").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
	protected void initControl() {
		super.initControl();
		this.txtTimes.setPrecision(0);
		this.txtTimes.setDataType(0);
		try {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("parent.inviteProject.id", this.editData.getInviteProject().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("isAccept", Boolean.TRUE));
			view.setFilter(filter);
			
			SupplierQualifyEntryCollection col = SupplierQualifyEntryFactory.getRemoteInstance().getSupplierQualifyEntryCollection(view);
			
			Set supplierId=new HashSet();
			for(int i=0;i<col.size();i++){
				if(col.get(i).getSupplier()!=null){
					supplierId.add(col.get(i).getSupplier().getId().toString());
				}
			}
			view= new EntityViewInfo();
			filter = new FilterInfo();
			if(supplierId.size()>0){
				filter.getFilterItems().add(new FilterItemInfo("id", supplierId,CompareType.INCLUDE));
			}else{
				filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
			}
			view.setFilter(filter);
			this.prmtSupplier.setEntityViewInfo(view);
		} catch (BOSException e) {
			e.printStackTrace();
		}
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		CoreBillEntryBaseInfo info = this.createNewEntryDate();
		if(info == null ) {
			return;
		}
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow namerow=this.kdtEntry.getRow(i);
			namerow.getCell("name").setValue("第"+(i+1)+"标段");
		}
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
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow namerow=this.kdtEntry.getRow(i);
			namerow.getCell("name").setValue("第"+(i+1)+"标段");
		}
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
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				IRow row=this.kdtEntry.getRow(i);
				row.getCell("name").setValue("第"+(i+1)+"标段");
			}
			CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price"});
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException {
		
	}
	protected void cbIsMultiple_actionPerformed(ActionEvent e) throws Exception {
		if(cbIsMultiple.isSelected()){
			this.txtPrice.setValue(null);
			
			this.actionALine.setEnabled(true);
			this.actionILine.setEnabled(true);
			this.actionRLine.setEnabled(true);
			
			this.txtPrice.setEnabled(false);
			
		}else{
			this.kdtEntry.removeRows();
			CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price"});
			
			this.actionALine.setEnabled(false);
			this.actionILine.setEnabled(false);
			this.actionRLine.setEnabled(false);
			
			this.txtPrice.setEnabled(true);
		}
	}
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		CRMClientHelper.getFootRow(this.kdtEntry, new String[]{"price"});
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (!oprtType.equals(OprtState.VIEW)) {
			if(this.cbIsMultiple.isSelected()){
				this.actionALine.setEnabled(true);
				this.actionILine.setEnabled(true);
				this.actionRLine.setEnabled(true);
				
				this.txtPrice.setEnabled(false);
			}else{
				this.actionALine.setEnabled(false);
				this.actionILine.setEnabled(false);
				this.actionRLine.setEnabled(false);
				
				this.txtPrice.setEnabled(true);
			}
		}
	}
}