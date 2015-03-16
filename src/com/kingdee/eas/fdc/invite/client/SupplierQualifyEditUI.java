/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.BasicView;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
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
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.util.KDDetailedArea;
import com.kingdee.eas.fdc.basedata.util.KDDetailedAreaDialog;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.InviteFixInfo;
import com.kingdee.eas.fdc.invite.InvitePurchaseModeEnum;
import com.kingdee.eas.fdc.invite.ResultEnum;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyFactory;
import com.kingdee.eas.fdc.invite.SupplierQualifyInfo;
import com.kingdee.eas.fdc.invite.supplier.DefaultStatusEnum;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.client.NewSupplierStockEditUI;
import com.kingdee.eas.fdc.invite.supplier.client.NoPerSupplierStockEditUI;
import com.kingdee.eas.fdc.invite.supplier.client.SupplierStockEditUI;
import com.kingdee.eas.framework.CoreBillEntryBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class SupplierQualifyEditUI extends AbstractSupplierQualifyEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierQualifyEditUI.class);
    public SupplierQualifyEditUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.kdtEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                	kdtEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
    	
    	if(this.getOprtState() == OprtState.ADDNEW ) {//附件分录默认有一行
			this.actionALine_actionPerformed(null);
			this.actionALine_actionPerformed(null);
			this.actionALine_actionPerformed(null);
			this.actionALine_actionPerformed(null);
			this.actionALine_actionPerformed(null);
//			this.actionALine_actionPerformed(null);
//			this.actionALine_actionPerformed(null);
//			this.actionALine_actionPerformed(null);
		}
    }
    
    protected void verifyInputForSave() throws Exception {
    	super.verifyInputForSave();
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"供应商入围明细不能为空！");
			SysUtil.abort();
		}else {
			for(int i=0; i<kdtEntry.getRowCount(); i++) {
				if(kdtEntry.getCell(i, "supplier").getValue()==null) {
					FDCMsgBox.showWarning(this,"供应商不能为空！");
					this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("supplier"));
					SysUtil.abort();
				}
				if(kdtEntry.getCell(i, "isAccept").getValue()==null) {
					FDCMsgBox.showWarning(this,"是否入围不能为空！");
					this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("isAccept"));
					SysUtil.abort();
				}
				if(kdtEntry.getCell(i, "coState").getValue()==null) {
					FDCMsgBox.showWarning(this,"合作状态不能为空！");
					this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("coState"));
					SysUtil.abort();
				}
				if(kdtEntry.getCell(i, "manager").getValue()==null) {
					FDCMsgBox.showWarning(this,"项目经理不能为空！");
					this.kdtEntry.getEditManager().editCellAt(i, this.kdtEntry.getColumnIndex("manager"));
					SysUtil.abort();
				}
			}
		}
	}
    
    public void storeFields()
	{
		super.storeFields();
	}
	public void loadFields() {
		detachListeners();
		super.loadFields();
		setSaveActionStatus();
		
		/*try {
			loadEntry();
		} catch (BOSException e) {
			this.handleException(e);
		}*/
//	
		attachListeners();
		setAuditButtonStatus(this.getOprtState());
//		super.loadFields();
	}
	
	//实时取
	protected void loadEntry() throws BOSException{
		for(int i=0; i<kdtEntry.getRowCount(); i++) {
			SupplierStockInfo supplier=(SupplierStockInfo) this.kdtEntry.getRow(i).getCell("supplier").getValue();
			if(supplier!=null){
				//履约得分...
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("evaluationType.number");
				sic.add("supplier.id");
				sic.add("amount");
				sic.add("state");
				view.setSelector(sic);
				
				view.setTopCount(1);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "003"));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "004"));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				filter.setMaskString("#0 and (#1 or #2) and #3");
				view.setFilter(filter);
				
				SorterItemCollection sorter = new SorterItemCollection();
				SorterItemInfo sorterInfo = new SorterItemInfo("bizDate");
				sorterInfo.setSortType(SortType.DESCEND);
				sorter.add(sorterInfo);
				view.setSorter(sorter);
				SupplierReviewGatherCollection coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				if(coll.size()>0) {
					this.kdtEntry.getRow(i).getCell("score1").setValue(coll.get(0).getAmount());
				}
				
				//考察得分。。
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "002"));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				view.setFilter(filter);
				coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				if(coll.size()>0) {
					this.kdtEntry.getRow(i).getCell("score2").setValue(coll.get(0).getAmount());
				}
			}
		}
	}	
	
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierQualifyFactory.getRemoteInstance();
	}
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();;
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.*"));
		
		sic.add(new SelectorItemInfo("entry.*"));
		sic.add(new SelectorItemInfo("entry.supplier.*"));
		sic.add(new SelectorItemInfo("entry.supplier.grade.*"));
		sic.add(new SelectorItemInfo("entry.supplier.quaLevel.*"));
		
		sic.add(new SelectorItemInfo("inviteProject.inviteType.*"));
		sic.add(new SelectorItemInfo("inviteProject.programmingContract.*"));
		sic.add(new SelectorItemInfo("inviteProject.project.*"));
		sic.add(new SelectorItemInfo("inviteProject.invitePurchaseMode.*"));
		sic.add(new SelectorItemInfo("inviteProject.*"));
		
		
		return sic;
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
		testField.setMaxLength(80);
		KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField);

//		KDTextField testField1 = new KDTextField();
//    	testField1.setMaxLength(2000);
//		KDTDefaultCellEditor editorSize1 = new KDTDefaultCellEditor(testField1);
		
		KDDetailedArea desc = new KDDetailedArea(250, 200);
		desc.setMaxLength(50000);
		KDTDefaultCellEditor editorDesc=new KDTDefaultCellEditor(desc);
		
		this.kdtEntry.checkParsed();
		FDCTableHelper.disableAutoAddLine(kdtEntry);

		this.kdtEntry.getColumn("linkPerson").setEditor(editorSize);
	 	this.kdtEntry.getColumn("linkPhone").setEditor(editorSize);
	 	this.kdtEntry.getColumn("coState").setEditor(editorSize);
	 	
	 	KDComboBox combo = new KDComboBox();
        for(int i = 0; i < IsGradeEnum.getEnumList().size(); i++){
        	combo.addItem(IsGradeEnum.getEnumList().get(i));
        }
        KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(combo);
        this.kdtEntry.getColumn("supplierState").setEditor(comboEditor);
	 	this.kdtEntry.getColumn("supplierState").getStyleAttributes().setLocked(true);
	 	
	 	combo = new KDComboBox();
        for(int i = 0; i < ResultEnum.getEnumList().size(); i++){
        	combo.addItem(ResultEnum.getEnumList().get(i));
        }
        comboEditor = new KDTDefaultCellEditor(combo);
		this.kdtEntry.getColumn("result").setEditor(comboEditor);
		
	 	this.kdtEntry.getColumn("reason").setEditor(editorSize);
	 	
	 	combo = new KDComboBox();
        for(int i = 0; i < DefaultStatusEnum.getEnumList().size(); i++){
        	combo.addItem(DefaultStatusEnum.getEnumList().get(i));
        }
        comboEditor = new KDTDefaultCellEditor(combo);
	 	this.kdtEntry.getColumn("isAccept").setEditor(comboEditor);
	 		
		this.kdtEntry.getColumn("remark").setEditor(editorDesc);
		this.kdtEntry.getColumn("remark").getStyleAttributes().setWrapText(true);
		
		this.kdtEntry.getColumn("recommended").getStyleAttributes().setLocked(true);
		
		kdtEntry.getColumn("manager").getStyleAttributes().setLocked(false);
		/*if(getUIContext().containsKey("isFromWorkflow")) {
			kdtEntry.getColumn("isAccept").getStyleAttributes().setLocked(false);
		}*/
		
		kdtEntry.getColumn("isAccept").getStyleAttributes().setLocked(false);
		
		kdtEntry.getColumn("supplier").getStyleAttributes().setFontColor(Color.BLUE);
	}
	
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		if("supplier".equals(this.kdtEntry.getColumn(e.getColIndex()).getKey())){
			SupplierStockInfo supplier=(SupplierStockInfo) this.kdtEntry.getRow(e.getRowIndex()).getCell("supplier").getValue();
			if(supplier!=null){
				for(int i=0;i<this.kdtEntry.getRowCount();i++){
					if(i==e.getRowIndex()) continue;
					SupplierStockInfo sameSupplier=(SupplierStockInfo) this.kdtEntry.getRow(i).getCell("supplier").getValue();
					if(sameSupplier!=null&&supplier.getId().toString().equals(sameSupplier.getId().toString())){
						FDCMsgBox.showWarning(this,supplier.getName()+"已存在！");
						this.kdtEntry.getRow(e.getRowIndex()).getCell("supplier").setValue(null);
						this.kdtEntry.getRow(e.getRowIndex()).getCell("supplierState").setValue(null);
						this.kdtEntry.getRow(e.getRowIndex()).getCell("grade").setValue(null);
						this.kdtEntry.getRow(e.getRowIndex()).getCell("recommended").setValue(null);
						this.kdtEntry.getRow(e.getRowIndex()).getCell("linkPerson").setValue(null);
						this.kdtEntry.getRow(e.getRowIndex()).getCell("linkPhone").setValue(null);
						return;
					}
				}
				this.kdtEntry.getRow(e.getRowIndex()).getCell("supplierState").setValue(supplier.getIsPass());
				if(supplier.getGrade()!=null){
					this.kdtEntry.getRow(e.getRowIndex()).getCell("grade").setValue(GradeSetUpFactory.getRemoteInstance().getGradeSetUpInfo(new ObjectUuidPK(supplier.getGrade().getId())).getName());
				}
				if(supplier.getState()== SupplierStateEnum.APPROVE && supplier.getQuaLevel()!=null) {
					this.kdtEntry.getRow(e.getRowIndex()).getCell("quaLevel").setValue(QuaLevelFactory.getRemoteInstance().getQuaLevelInfo(new ObjectUuidPK(supplier.getQuaLevel().getId())).getName());
				}
				
				//履约得分...
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("evaluationType.number");
				sic.add("supplier.id");
				sic.add("amount");
				view.setSelector(sic);
				
				view.setTopCount(1);
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "003"));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "004"));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				filter.setMaskString("#0 and (#1 or #2) and #3");
				view.setFilter(filter);
				
				SorterItemCollection sorter = new SorterItemCollection();
				SorterItemInfo sorterInfo = new SorterItemInfo("bizDate");
				sorterInfo.setSortType(SortType.DESCEND);
				sorter.add(sorterInfo);
				view.setSorter(sorter);
				SupplierReviewGatherCollection coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				if(coll.size()>0) {
					this.kdtEntry.getRow(e.getRowIndex()).getCell("score1").setValue(coll.get(0).getAmount());
				}
				
				//考察得分。。
				filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id", supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("evaluationType.number", "002"));
				filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
				view.setFilter(filter);
				coll = SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				if(coll.size()>0) {
					this.kdtEntry.getRow(e.getRowIndex()).getCell("score2").setValue(coll.get(0).getAmount());
				}
				
				
				this.kdtEntry.getRow(e.getRowIndex()).getCell("recommended").setValue(supplier.getRecommended());
				view = new EntityViewInfo();
				filter = new FilterInfo();
				view.setFilter(filter);
				filter.getFilterItems().add(new FilterItemInfo("parent.id",supplier.getId().toString()));
				filter.getFilterItems().add(new FilterItemInfo("isDefault",Boolean.TRUE));
				
				SupplierLinkPersonCollection person=SupplierLinkPersonFactory.getRemoteInstance().getSupplierLinkPersonCollection(view);
				if(person.size()>0){
					this.kdtEntry.getRow(e.getRowIndex()).getCell("linkPerson").setValue(person.get(0).getPersonName());
					this.kdtEntry.getRow(e.getRowIndex()).getCell("linkPhone").setValue(person.get(0).getPhone());
				}
			}else{
				this.kdtEntry.getRow(e.getRowIndex()).getCell("linkPerson").setValue(null);
				this.kdtEntry.getRow(e.getRowIndex()).getCell("linkPhone").setValue(null);
				this.kdtEntry.getRow(e.getRowIndex()).getCell("supplierState").setValue(null);
				this.kdtEntry.getRow(e.getRowIndex()).getCell("grade").setValue(null);
				this.kdtEntry.getRow(e.getRowIndex()).getCell("recommended").setValue(null);
			}
		}else if("result".equals(this.kdtEntry.getColumn(e.getColIndex()).getKey())){
			ResultEnum result=(ResultEnum)this.kdtEntry.getRow(e.getRowIndex()).getCell("result").getValue();
			
			if(result!=null){
				if(result.equals(ResultEnum.NO)){
					this.kdtEntry.getRow(e.getRowIndex()).getCell("isAccept").setValue(DefaultStatusEnum.NO);
					this.kdtEntry.getRow(e.getRowIndex()).getCell("isAccept").getStyleAttributes().setLocked(true);
					
					this.kdtEntry.getRow(e.getRowIndex()).getCell("reason").getStyleAttributes().setLocked(false);
				}else{
					this.kdtEntry.getRow(e.getRowIndex()).getCell("isAccept").getStyleAttributes().setLocked(false);
					
					this.kdtEntry.getRow(e.getRowIndex()).getCell("reason").setValue(null);
					this.kdtEntry.getRow(e.getRowIndex()).getCell("reason").getStyleAttributes().setLocked(true);
				}
			}
		}
	}
	protected void attachListeners() {

	}

	protected void detachListeners() {

	}
	protected String getTDFileName() {
		return "/bim/fdc/invite/SupplierQualify";
	}
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK("com.kingdee.eas.fdc.invite.app.SupplierQualifyForPrintQuery");
	}
	protected BaseInviteInfo createNewDate() {
		SupplierQualifyInfo info=new SupplierQualifyInfo();
		createBaseInvite(info);
		return info;
	}
	public void actionALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtEntry.addRow();
		CoreBillEntryBaseInfo info = this.createNewEntryDate();
		if(info == null ) {
			return;
		}
		info.setId(BOSUuid.create(info.getBOSType()));
		row.setUserObject(info);
		row.getCell("isAccept").setValue(DefaultStatusEnum.YES);
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
		row.getCell("isAccept").setValue(DefaultStatusEnum.YES);
	}
	protected CoreBillEntryBaseInfo createNewEntryDate() {
		return new SupplierQualifyEntryInfo();
	}
	protected void closeDeleteAttachment(){
	}
	
	protected void initControl() {
		super.initControl();
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.invite.supplier.app.NewSupplierStockQuery");
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		
		if( this.editData.getInviteProject().getInvitePurchaseMode().getType() == InvitePurchaseModeEnum .SINGLE ) {//单项采购按组织过滤。。
			Set org=new HashSet();
			FullOrgUnitInfo curFullOrg = SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo();
			org.add(OrgConstants.DEF_CU_ID);
			if(curFullOrg.isIsPurchaseOrgUnit()) {
				org.add(curFullOrg.getId().toString());
			}
			
			while(curFullOrg.getParent() !=null && !OrgConstants.DEF_CU_ID.equals(curFullOrg.getParent().getId().toString()) ) {
				FullOrgUnitInfo parent = curFullOrg.getParent();
				try {
					parent = FullOrgUnitFactory.getRemoteInstance().getFullOrgUnitInfo(new ObjectUuidPK(parent.getId()));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				
				if(parent.isIsPurchaseOrgUnit()) {
					org.add(parent.getId().toString());
					break;
				}
				curFullOrg = parent;
			}
			filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id",org,CompareType.INCLUDE));
			
		}
		
		filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.ELIGIBILITY_VALUE)));
//		filter.getFilterItems().add(new FilterItemInfo("isPass",Integer.valueOf(IsGradeEnum.TEMP_VALUE)));
		filter.getFilterItems().add(new FilterItemInfo("state",Integer.valueOf(SupplierStateEnum.APPROVE_VALUE)));
		
		if(this.editData.getInviteProject().getInviteType()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id",this.editData.getInviteProject().getInviteType().getId().toString()));
		}
		
		f7Box.setEntityViewInfo(view);
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		
		this.kdtEntry.getColumn("supplier").setEditor(f7Editor);
		
		/*if(this.editData.getInviteProject()!=null&&this.editData.getInviteProject().isScalingRules()){
			this.actionFixALine.setEnabled(false);
			this.actionFixILine.setEnabled(false);
			this.actionFixRLine.setEnabled(false);
		}*/
	}
/*	public void actionFixALine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.kdtFixEntry.addRow();
		InviteFixInfo info = new InviteFixInfo();
		row.setUserObject(info);
	}
	public void actionFixILine_actionPerformed(ActionEvent e) throws Exception {
		IRow row = null;
		if (this.kdtFixEntry.getSelectManager().size() > 0) {
			int top = this.kdtFixEntry.getSelectManager().get().getTop();
			if (isTableColumnSelected(this.kdtFixEntry))
				row = this.kdtFixEntry.addRow();
			else
				row = this.kdtFixEntry.addRow(top);
		} else {
			row = this.kdtFixEntry.addRow();
		}
		InviteFixInfo info = new InviteFixInfo();
		row.setUserObject(info);
	}
	public void actionFixRLine_actionPerformed(ActionEvent e) throws Exception {
		if (this.kdtFixEntry.getSelectManager().size() == 0 || isTableColumnSelected(this.kdtFixEntry)) {
			FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
			return;
		}
		if (confirmRemove(this)) {
			int top = this.kdtFixEntry.getSelectManager().get().getBeginRow();
			int bottom = this.kdtFixEntry.getSelectManager().get().getEndRow();
			for (int i = top; i <= bottom; i++) {
				if (this.kdtFixEntry.getRow(top) == null) {
					FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
					return;
				}
				this.kdtFixEntry.removeRow(top);
			}
		}
	}*/
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.actionFixALine.setEnabled(false);
			this.actionFixILine.setEnabled(false);
			this.actionFixRLine.setEnabled(false);
		} else {
			if(this.editData!=null&&this.editData.getInviteProject()!=null&&this.editData.getInviteProject().isScalingRules()){
				this.actionFixALine.setEnabled(false);
				this.actionFixILine.setEnabled(false);
				this.actionFixRLine.setEnabled(false);
			}else{
				this.actionFixALine.setEnabled(true);
				this.actionFixILine.setEnabled(true);
				this.actionFixRLine.setEnabled(true);
			}
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException {
		
	}
	
	
	protected void kdtEntry_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		//单据穿透
		 if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 ){
				IRow row=KDTableUtil.getSelectedRow(kdtEntry);
				if(kdtEntry.getColumnKey(e.getColIndex()).equals("supplier")&& e.getClickCount() == 2) {
					if(row.getCell("supplier").getValue() instanceof SupplierStockInfo&&
							row.getCell("supplier").getValue()!=null) {
						setCursorOfWair();
						SupplierStockInfo supplier = (SupplierStockInfo) row.getCell("supplier").getValue();
						UIContext uiContext = new UIContext(this);
						uiContext.put(UIContext.ID, supplier.getId().toString());
						IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(NoPerSupplierStockEditUI.class.getName(), uiContext, null,"VIEW");
						uiWindow.show();
						setCursorOfDefault();
					}
				}
				if(kdtEntry.getColumnKey(e.getColIndex()).equals("remark")){
					this.showDialog(this, kdtEntry, 250, 200, 50000);
				}
	      }
	 }
	private HashMap getProviderDataMap() {
		HashMap<String,Object> map = new HashMap<String, Object>();
		map.put(BaseInviteEditUI.INVITEPROJECTID, this.editData.getInviteProject().getId().toString());
		
		return map;
	}
	
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		SupplierQualifyPrintDataProvider dataPvd = new SupplierQualifyPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		SupplierQualifyPrintDataProvider dataPvd = new SupplierQualifyPrintDataProvider(
				editData.getString("id"), getTDQueryPK(), getProviderDataMap());
//		dataPvd.setBailId(editData.getBail().getId().toString());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
}