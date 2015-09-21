/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EEnum;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataLoader;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataLoaderFactory;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.finance.client.ContractOutPayPlanPrintProvider;
import com.kingdee.eas.fdc.sellhouse.AgioEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AgioParam;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CalcTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.MortagageControlFactory;
import com.kingdee.eas.fdc.sellhouse.MortagageEnum;
import com.kingdee.eas.fdc.sellhouse.PrePurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAccountTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPreChangeStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHECustomerInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEParamConstant;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SignAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageCollection;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialAgioEnum;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountAgioEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountEntryInfo;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory;
import com.kingdee.eas.fdc.sellhouse.SpecialDiscountInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.KDCommonPromptDialog;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;

/**
 * output class name
 */
public class SpecialDiscountEditUI extends AbstractSpecialDiscountEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SpecialDiscountEditUI.class);
    private static final String CANTEDIT = "cantEdit";
    protected AgioParam currAgioParam=new AgioParam();
    protected Map listenersMap = new HashMap();
    protected KDCommonPromptDialog dlg = null;
    public SpecialDiscountEditUI() throws Exception
    {
        super();
    }
    protected void initTable(){
    	this.kdtEntry.checkParsed();
    	
    	KDFormattedTextField amount = new KDFormattedTextField();
		amount.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		amount.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		amount.setPrecision(2);
		amount.setNegatived(false);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		KDFormattedTextField percent = new KDFormattedTextField();
		percent.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		percent.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		percent.setPrecision(4);
		percent.setNegatived(false);
		percent.setMaximumValue(new BigDecimal(100));
		KDTDefaultCellEditor percentEditor = new KDTDefaultCellEditor(percent);
		
		this.kdtEntry.getColumn("basePrice").setEditor(amountEditor);
		this.kdtEntry.getColumn("subPrice").setEditor(amountEditor);
		this.kdtEntry.getColumn("discountAmount").setEditor(amountEditor);
//		this.kdtEntry.getColumn("discountAmount").setRequired(true);
//		this.kdtEntry.getColumn("discountPercent").setEditor(percentEditor);
//		this.kdtEntry.getColumn("discountPercent").setRequired(true);
//		this.kdtEntry.getColumn("discountPercent").getStyleAttributes().setLocked(true);
		this.kdtEntry.getColumn("baseStandardPrice").setEditor(amountEditor);
		this.kdtEntry.getColumn("baseDiscountAmount").setEditor(amountEditor);
		this.kdtEntry.getColumn("basePercent").setEditor(percentEditor);
		
		this.kdtEntry.getColumn("discountAfAmount").setEditor(amountEditor);
		
		this.kdtEntry.getColumn("discountAfBPrice").getStyleAttributes().setFontColor(Color.RED);
		this.kdtEntry.getColumn("discountAfAmount").getStyleAttributes().setFontColor(Color.RED);
		this.kdtEntry.getColumn("basePercent").getStyleAttributes().setFontColor(Color.RED);
		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				if(o==null){
					return null;
				}else{
					String str = o.toString();
					return str + "%";
				}
				
			}
		});
//		this.kdtEntry.getColumn("discountPercent").setRenderer(render_scale);
		this.kdtEntry.getColumn("basePercent").setRenderer(render_scale);
		
		KDWorkButton btnRoomSelect = new KDWorkButton();
		KDWorkButton btnRoomDelete = new KDWorkButton();

		this.actionRoomSelect.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnRoomSelect = (KDWorkButton)contEntry.add(this.actionRoomSelect);
		btnRoomSelect.setText("选择房间");
		btnRoomSelect.setSize(new Dimension(140, 19));

		this.actionRoomDelete.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnRoomDelete = (KDWorkButton) contEntry.add(this.actionRoomDelete);
		btnRoomDelete.setText("删除房间");
		btnRoomDelete.setSize(new Dimension(140, 19));
		
		this.kdtEntry.getColumn("room").setWidth(300);
    }
	public void actionRoomDelete_actionPerformed(ActionEvent e) throws Exception {
		KDTable table=this.kdtEntry;
		if (table == null) {
			return;
		}
		if ((table.getSelectManager().size() == 0)){
			MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
			return;
		}
		if (confirmRemove()) {
			KDTSelectManager selectManager = table.getSelectManager();
			int size = selectManager.size();
			KDTSelectBlock selectBlock = null;
			Set indexSet = new HashSet();

			for (int blockIndex = 0; blockIndex < size; blockIndex++) {
				selectBlock = selectManager.get(blockIndex);
				int top = selectBlock.getBeginRow();
				int bottom = selectBlock.getEndRow();
				if (table.getRow(top) == null) {
					MsgBox.showInfo(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Msg_NoneEntry"));
					return;
				}
				for (int i = top; i <= bottom; i++) {
					indexSet.add(new Integer(i));
				}
			}
			Integer[] indexArr = new Integer[indexSet.size()];
			Object[] indexObj = indexSet.toArray();
			System.arraycopy(indexObj, 0, indexArr, 0, indexArr.length);
			Arrays.sort(indexArr);
			if (indexArr == null){
				return;
			}
			for (int i = indexArr.length - 1; i >= 0; i--) {
				int rowIndex = Integer.parseInt(String.valueOf(indexArr[i]));
				table.removeRow(rowIndex);
			}
			if (table.getRow(0) != null){
				table.getSelectManager().select(0, 0);
			}
		}
	}
	public void actionRoomSelect_actionPerformed(ActionEvent e)throws Exception {
		if(this.kdtEntry.getRowCount()>0){
			FDCMsgBox.showWarning(this,"只能选择一套房间！");
			return;
		}
		if(dlg==null){
			dlg = new KDCommonPromptDialog();
			IMetaDataLoader loader = MetaDataLoaderFactory.getRemoteMetaDataLoader();
			dlg.setQueryInfo(loader.getQuery(new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.RoomSelectQuery")));
			
			SellProjectInfo sellproject=this.editData.getSellProject();
			
			BuildingInfo buildInfo = (BuildingInfo) this.getUIContext().get("building");
			BuildingUnitInfo buildUnitInfo = (BuildingUnitInfo) this.getUIContext().get("buildUnit");
			
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellproject.getId().toString()));
			if(buildInfo!=null) filter.getFilterItems().add(new FilterItemInfo("building.id", buildInfo.getId().toString()));
			if(buildUnitInfo!=null) filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnitInfo.getId().toString()));
			
			String per=NewCommerceHelper.getPermitSaleManIdSql(sellproject,SysContext.getSysContext().getCurrentUserInfo());
			StringBuffer sql = new StringBuffer();
			
			sql.append("select * from ( ");
			sql.append("select distinct bill.froomId from t_she_prePurchaseManage bill ");
			sql.append("left join T_SHE_PrePurchaseSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.fsellProjectId='"+sellproject.getId()+"' and bill.fbizState in('PreApple','PreAudit') ");
			sql.append("union ");
			sql.append("select distinct bill.froomId from t_she_purchaseManage bill ");
			sql.append("left join T_SHE_PurSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.fsellProjectId='"+sellproject.getId()+"' and bill.fbizState in('PurApple','PurAudit') ");
			sql.append("union ");
			sql.append("select distinct bill.froomId from t_she_signManage bill ");
			sql.append("left join T_SHE_SignSaleManEntry saleMan on saleMan.fheadid=bill.fid where saleMan.fuserid in ("+per+") and bill.fsellProjectId='"+sellproject.getId()+"' and bill.fbizState in('SignApple','SignAudit') )");
		
			filter.getFilterItems().add(new FilterItemInfo("id", sql,CompareType.INNER));
			
			FilterInfo mfilter = new FilterInfo();
			mfilter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellproject.getId().toString()));
			mfilter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.ONSHOW_VALUE));
			if(buildInfo!=null) mfilter.getFilterItems().add(new FilterItemInfo("building.id", buildInfo.getId().toString()));
			if(buildUnitInfo!=null) mfilter.getFilterItems().add(new FilterItemInfo("buildUnit.id", buildUnitInfo.getId().toString()));
			
			filter.mergeFilter(mfilter, "or");
			
			view.setFilter(filter);
			
			dlg.setEntityViewInfo(view);
			dlg.setEnabledMultiSelection(true);
		}
		dlg.show();
		Object[] object = (Object[]) dlg.getData();
		Set roomId=new HashSet();
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			if(this.kdtEntry.getRow(i).getCell("room").getValue()!=null){
				roomId.add(((RoomInfo)this.kdtEntry.getRow(i).getCell("room").getValue()).getId().toString());
			}
		}
		if (object != null) {
			if(object.length>1){
				FDCMsgBox.showWarning(this,"只能选择一套房间！");
				return;
			}
			for (int i = 0; i < object.length; i++) {
				RoomInfo room = (RoomInfo) object[i];
				if(roomId.contains(room.getId().toString())){
					continue;
				}
				IRow row=this.kdtEntry.addRow();
				row.getCell("room").setValue(room);
				
				SpecialDiscountEntryInfo entry=new SpecialDiscountEntryInfo();
    			row.setUserObject(entry);
    			
    			KDTEditEvent ee = new KDTEditEvent(row.getCell("room"));
    			ee.setRowIndex(row.getRowIndex());
    			ee.setColIndex(this.kdtEntry.getColumnIndex("room"));
    			ee.setValue(room);
    			this.kdtEntry_editStopped(ee);
			}
		}
	}
	public void onLoad() throws Exception {
    	initTable();
		super.onLoad();
		initControl();
		setF7PayTypeFilter();
		setF7CustomerFilter();

		setAuditButtonStatus(this.getOprtState());
	}
    protected void setSaveActionStatus() {
		if (this.editData.getBizState() == FDCBillStateEnum.SUBMITTED) {
			actionSave.setEnabled(false);
		}
	}

    public void loadFields()
    {
    	detachListeners();
		
		super.loadFields();

		setSaveActionStatus();
		setAuditButtonStatus(this.getOprtState());
		
		loadCustomerInfo();
		try {
			for(int i=0;i<this.kdtEntry.getRowCount();i++){
				IRow row=this.kdtEntry.getRow(i);
				loadPurInfo(row);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		AgioEntryCollection agioEntryColl = new AgioEntryCollection(); 
		for(int i=0;i<editData.getAgioEntry().size();i++)
			agioEntryColl.add(editData.getAgioEntry().get(i));
		this.txtAgioDesc.setUserObject(agioEntryColl);
		currAgioParam.setAgios(agioEntryColl);
		attachListeners();
    }
	public void storeFields() {
		AgioEntryCollection agioEntrys = (AgioEntryCollection)this.txtAgioDesc.getUserObject();
		editData.getAgioEntry().clear();
		for (int i = 0; i < agioEntrys.size(); i++) {
			SpecialDiscountAgioEntryInfo entryInfo = (SpecialDiscountAgioEntryInfo)agioEntrys.get(i);
			editData.getAgioEntry().add(entryInfo);
		}
		if(this.kdtEntry.getRowCount()>0){
			this.editData.setBasePercent(((BigDecimal) this.kdtEntry.getRow(0).getCell("basePercent").getValue()));
			this.editData.setSubPrice(((BigDecimal) this.kdtEntry.getRow(0).getCell("subPrice").getValue()));
			RoomInfo room = (RoomInfo)this.kdtEntry.getRow(0).getCell("room").getValue();
			if(room!=null){
				this.editData.setProductTyupe(room.getProductType());
			}else{
				this.editData.setProductTyupe(null);
			}
		}else{
			this.editData.setBasePercent(null);
			this.editData.setSubPrice(null);
			this.editData.setProductTyupe(null);
		}
		super.storeFields();
	}
	protected void loadCustomerInfo(){
    	SHECustomerInfo info=(SHECustomerInfo)this.prmtCustomer.getValue();
    	if(info!=null){
			String phone="";
			String mob="";
			String tel="";
			if(info.getPhone()!=null&&!info.getPhone().trim().equals("")){
				mob=info.getPhone()+"(M)";
			}
			if(info.getTel()!=null&&!info.getTel().trim().equals("")){
				tel=info.getTel()+"(T)";
			}
			if(!mob.equals("")&&!tel.equals("")){
				phone=mob+","+tel;
			}else if(!mob.equals("")){
				phone=mob;
			}else if(!tel.equals("")){
				phone=tel;
			}
			this.txtPhone.setText(phone);
			this.txtCertificateNumber.setText(info.getCertificateNumber());
			this.txtMailAddress.setText(info.getMailAddress());
			this.txtRecommended.setText(info.getRecommended());
			
			this.txtUnitPriceName.setText(((SHECustomerInfo)this.prmtCustomer.getValue()).getName()+"客户特殊优惠折扣单");
		}else{
			this.txtPhone.setText(null);
			this.txtCertificateNumber.setText(null);
			this.txtMailAddress.setText(null);
			this.txtRecommended.setText(null);
			
			this.txtUnitPriceName.setText(null);
		}
    }
    protected void loadPurInfo(IRow row) throws BOSException{
    	SHECustomerInfo info=(SHECustomerInfo)this.prmtCustomer.getValue();
    	RoomInfo room=(RoomInfo) row.getCell("room").getValue();
    	if(room!=null&&info!=null){
    		EntityViewInfo view=new EntityViewInfo();
        	FilterInfo filter=new FilterInfo();
        	filter.getFilterItems().add(new FilterItemInfo("head.room.id",room.getId().toString()));
        	Set state=new HashSet();
        	state.add(TransactionStateEnum.PURAPPLE_VALUE);
        	state.add(TransactionStateEnum.PURAUDITING_VALUE);
        	state.add(TransactionStateEnum.PURAUDIT_VALUE);
        	state.add(TransactionStateEnum.TOSIGN_VALUE);
        	state.add(TransactionStateEnum.CHANGENAMEAUDITING_VALUE);
        	state.add(TransactionStateEnum.CHANGEPIRCEAUDITING_VALUE);
        	state.add(TransactionStateEnum.QUITROOMAUDITING_VALUE);
        	filter.getFilterItems().add(new FilterItemInfo("head.bizState",state,CompareType.INCLUDE));
        	filter.getFilterItems().add(new FilterItemInfo("customer.id",info.getId().toString()));
        	filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id",this.editData.getSellProject().getId().toString()));
        	view.setFilter(filter);
        	SelectorItemCollection sel=new SelectorItemCollection();
        	sel.add("head.bizDate");
        	sel.add("head.planSignDate");
        	sel.add("head.contractTotalAmount");
        	sel.add("head.payType.*");
        	view.setSelector(sel);
        	PurCustomerEntryCollection col=PurCustomerEntryFactory.getRemoteInstance().getPurCustomerEntryCollection(view);
        	if(col.size()>0){
        		row.getCell("purBizDate").setValue(col.get(0).getHead().getBizDate());
        		row.getCell("planSignDate").setValue(col.get(0).getHead().getPlanSignDate());
        		row.getCell("contractTotalAmount").setValue(col.get(0).getHead().getContractTotalAmount());
        		if(row.getCell("payType").getValue()==null){
        			row.getCell("payType").setValue(col.get(0).getHead().getPayType());
        		}
        		return;
        	}
    	}
    	row.getCell("purBizDate").setValue(null);
		row.getCell("planSignDate").setValue(null);
		row.getCell("contractTotalAmount").setValue(null);
    }
    /**
     * 初始化显示
     * */
    private void initControl(){
    	this.menuTable1.setVisible(false);
		this.btnAddLine.setVisible(false);
		this.btnInsertLine.setVisible(false);
		this.btnRemoveLine.setVisible(false);
		this.actionCreateFrom.setVisible(false);
		this.actionCreateTo.setVisible(false);
		this.actionTraceDown.setVisible(false);
		this.actionTraceUp.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionCopyFrom.setVisible(false);
		
		this.actionNext.setVisible(false);
		this.actionFirst.setVisible(false);
		this.actionPre.setVisible(false);
		this.actionLast.setVisible(false);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
    	
        this.btnAudit.setIcon(EASResource.getIcon("imgTbtn_audit"));
        this.btnUnAudit.setIcon(EASResource.getIcon("imgTbtn_unaudit"));
    }
	/**
	 * 初始化付款方案
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	protected void setF7PayTypeFilter() throws EASBizException, BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("validDate", FDCDateHelper.addDays(new Date(), 1), CompareType.LESS_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", new Date(), CompareType.GREATER_EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("invalidDate", null, CompareType.IS));
		
		filter.getFilterItems().add(new FilterItemInfo("project.id", SHEManageHelper.getAllParentSellProjectCollection((editData).getSellProject(),new HashSet()),CompareType.INCLUDE));
		filter.setMaskString("#0 and #1 and (#2 or #3) and #4 ");
		view.setFilter(filter);
		
		KDBizPromptBox f7Box = new KDBizPromptBox(); 
		KDTDefaultCellEditor f7Editor = new KDTDefaultCellEditor(f7Box);
		f7Box.setDisplayFormat("$name$");
		f7Box.setEditFormat("$number$");
		f7Box.setCommitFormat("$number$");
		f7Box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SHEPayTypeQuery");
		f7Box.setEntityViewInfo(view);
		this.kdtEntry.getColumn("payType").setEditor(f7Editor);
	}
    /**
     * 初始化客户 
     */
    protected void setF7CustomerFilter() throws EASBizException, BOSException{
    	SellProjectInfo sellProject=SHEManageHelper.getParentSellProject(null,this.editData.getSellProject());
    	EntityViewInfo view = NewCommerceHelper.getPermitCustomerView(sellProject,SysContext.getSysContext().getCurrentUserInfo());
    	this.prmtCustomer.setEntityViewInfo(view);
    }
	protected void kdtEntry_editStopped(KDTEditEvent e) throws Exception {
		IRow row=this.kdtEntry.getRow(e.getRowIndex());
		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("room")){
			RoomInfo room = (RoomInfo)row.getCell("room").getValue();
			if(room!=null){
				row.getCell("buildingArea").setValue(room.getActualBuildingArea()==null?room.getBuildingArea():room.getActualBuildingArea());
				
				row.getCell("buildingPrice").setValue(FDCHelper.divide(room.getStandardTotalAmount(), row.getCell("buildingArea").getValue(), 2, BigDecimal.ROUND_HALF_UP));
				row.getCell("standardTotalAmount").setValue(room.getStandardTotalAmount());
				row.getCell("baseStandardPrice").setValue(room.getBaseStandardPrice());
				row.getCell("basePrice").setValue(FDCHelper.divide(room.getBaseStandardPrice(), row.getCell("buildingArea").getValue(), 2, BigDecimal.ROUND_HALF_UP));
				
				IObjectValue objectValue=SHEManageHelper.getCurTransactionBill(room.getId());
				SHECustomerInfo customer=null;
				if(objectValue!=null){
					if(objectValue instanceof PrePurchaseManageInfo){
						for(int i=0;i<((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().size();i++){
							if(((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(i).isIsMain())
								customer=((PrePurchaseManageInfo)objectValue).getPrePurchaseCustomerEntry().get(i).getCustomer();
						}
			    	}else if(objectValue instanceof PurchaseManageInfo){
						for(int i=0;i<((PurchaseManageInfo)objectValue).getPurCustomerEntry().size();i++){
							if(((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(i).isIsMain())
								customer=((PurchaseManageInfo)objectValue).getPurCustomerEntry().get(i).getCustomer();
						}
			    	}else if(objectValue instanceof SignManageInfo){
						for(int i=0;i<((SignManageInfo)objectValue).getSignCustomerEntry().size();i++){
							if(((SignManageInfo)objectValue).getSignCustomerEntry().get(i).isIsMain())
								customer=((SignManageInfo)objectValue).getSignCustomerEntry().get(i).getCustomer();
						}
			    	}
				}
				if(customer!=null&&this.prmtCustomer.getValue()!=null){
					SHECustomerInfo info=(SHECustomerInfo)this.prmtCustomer.getValue();
					if(!info.getId().equals(customer.getId())){
						FDCMsgBox.showWarning(this,"不同客户不能进行特殊折扣优惠申请！");
						row.getCell("room").setValue(null);
						SysUtil.abort();
					}
				}else if(this.prmtCustomer.getValue()==null&&customer!=null){
					removeDataChangeListener(this.prmtCustomer);
					this.prmtCustomer.setValue(customer);
					addDataChangeListener(this.prmtCustomer);
					loadCustomerInfo();
				}
				loadPurInfo(row);
				
//				AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), 
//						(AgioEntryCollection)this.txtAgioDesc.getUserObject(), this.currAgioParam ,this.editData,false,false);
//				if (agioParam != null&&!this.currAgioParam.equals(agioParam)) {
//					this.currAgioParam = agioParam;
//					this.txtAgioDesc.setUserObject(agioParam.getAgios());
//					
//					BigDecimal buildingArea=room.getActualBuildingArea()==null?room.getBuildingArea():room.getActualBuildingArea();
//					
//					PurchaseParam purParam = SHEManageHelper.getAgioParam(this.currAgioParam, room, 
//							null,room.getCalcType(),false,room.getRoomArea(),buildingArea,room.getRoomPrice(),room.getBuildPrice(),room.getStandardTotalAmount(),null,null,null,SpecialAgioEnum.DaZhe,null,null);
//					if(purParam!=null) {
//						this.txtAgioDesc.setText(purParam.getAgioDes());
//						this.kdtEntry.getRow(0).getCell("agioPrice").setValue(purParam.getContractBuildPrice());
//						this.kdtEntry.getRow(0).getCell("agioAmount").setValue(purParam.getContractTotalAmount());
//						KDTEditEvent ee = new KDTEditEvent(this.kdtEntry.getRow(0).getCell("discountAmount"));
//						ee.setRowIndex(0);
//						ee.setColIndex(this.kdtEntry.getColumnIndex("discountAmount"));
//						ee.setValue(this.kdtEntry.getRow(0).getCell("discountAmount").getValue());
//						kdtEntry_editStopped(ee);
//					}
//				}else{
//					this.kdtEntry.getRow(0).getCell("agioPrice").setValue(room.getStandardTotalAmount());
//					this.kdtEntry.getRow(0).getCell("agioAmount").setValue(room.getBaseStandardPrice());
//				}
			}else{
				row.getCell("buildingArea").setValue(null);
				row.getCell("buildingPrice").setValue(null);
				row.getCell("standardTotalAmount").setValue(null);
				row.getCell("baseStandardPrice").setValue(null);
				row.getCell("basePrice").setValue(null);
			}
			e = new KDTEditEvent(row.getCell("discountAfAmount"));
			e.setRowIndex(row.getRowIndex());
			e.setColIndex(this.kdtEntry.getColumnIndex("discountAfAmount"));
			e.setValue(row.getCell("discountAfAmount").getValue());
			kdtEntry_editStopped(e);
		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("discountAfAmount")){
			BigDecimal standardTotalAmount=(BigDecimal) row.getCell("standardTotalAmount").getValue();
			BigDecimal discountAfAmount=(BigDecimal) row.getCell("discountAfAmount").getValue();
			BigDecimal baseStandardPrice=(BigDecimal) row.getCell("baseStandardPrice").getValue();
			
			row.getCell("discountAmount").setValue(FDCHelper.subtract(standardTotalAmount, discountAfAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
			row.getCell("baseDiscountAmount").setValue(FDCHelper.subtract(baseStandardPrice, discountAfAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
			
			BigDecimal baseDiscountAmount=(BigDecimal) row.getCell("baseDiscountAmount").getValue();
			BigDecimal basePercent=FDCHelper.multiply(FDCHelper.divide(baseDiscountAmount, baseStandardPrice,6,BigDecimal.ROUND_HALF_UP), new BigDecimal(100));
			row.getCell("basePercent").setValue(basePercent);
			
			BigDecimal buildingArea=(BigDecimal) row.getCell("buildingArea").getValue();
			
			row.getCell("discountAfBPrice").setValue(FDCHelper.divide(discountAfAmount,buildingArea, 2, BigDecimal.ROUND_HALF_UP));
			row.getCell("subPrice").setValue(FDCHelper.subtract(row.getCell("basePrice").getValue(), row.getCell("discountAfBPrice").getValue()));
		}
//		else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("discountAmount")){
//			BigDecimal amount=(BigDecimal) row.getCell("discountAmount").getValue();
////			if(amount!=null){
////				amount=amount.setScale(0, BigDecimal.ROUND_UP);
////				row.getCell("discountAmount").setValue(amount);
////			}
//			BigDecimal standardTotalAmount=(BigDecimal) row.getCell("standardTotalAmount").getValue();
//			BigDecimal buildingArea=(BigDecimal) row.getCell("buildingArea").getValue();
//			
//			row.getCell("discountAfAmount").setValue(FDCHelper.subtract(standardTotalAmount, amount).setScale(0, BigDecimal.ROUND_HALF_UP));
//			
//			BigDecimal discountAfAmount=(BigDecimal) row.getCell("discountAfAmount").getValue();
//			row.getCell("discountAfBPrice").setValue(FDCHelper.divide(discountAfAmount, buildingArea));
////			row.getCell("discountPercent").setValue(FDCHelper.subtract(new BigDecimal(100),FDCHelper.multiply(FDCHelper.divide(discountAfAmount, standardTotalAmount,6,BigDecimal.ROUND_HALF_UP), new BigDecimal(100))));
//			
//			BigDecimal baseStandardPrice=(BigDecimal) row.getCell("baseStandardPrice").getValue();
//			BigDecimal basePercent=FDCHelper.multiply(FDCHelper.divide(FDCHelper.subtract(baseStandardPrice, discountAfAmount), baseStandardPrice,6,BigDecimal.ROUND_HALF_UP), new BigDecimal(100));
//			if(basePercent!=null&&basePercent.compareTo(FDCHelper.ZERO)<=0){
//				basePercent=null;
//			}
//			row.getCell("basePercent").setValue(basePercent);
//			
//			BigDecimal baseDiscountAmount=FDCHelper.subtract(baseStandardPrice, discountAfAmount);
//			if(baseDiscountAmount!=null&&baseDiscountAmount.compareTo(FDCHelper.ZERO)<=0){
//				baseDiscountAmount=null;
//			}
//			row.getCell("baseDiscountAmount").setValue(baseDiscountAmount);
//			
//		}else if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("discountPercent")){
//			BigDecimal percent=(BigDecimal) row.getCell("discountPercent").getValue();
//			BigDecimal standardTotalAmount=(BigDecimal) row.getCell("standardTotalAmount").getValue();
//			BigDecimal buildingArea=(BigDecimal) row.getCell("buildingArea").getValue();
//			
//			
//			row.getCell("discountAfAmount").setValue(FDCHelper.divide(FDCHelper.multiply(standardTotalAmount, percent),new BigDecimal(100)).setScale(0, BigDecimal.ROUND_HALF_UP));
//			
//			BigDecimal discountAfAmount=(BigDecimal) row.getCell("discountAfAmount").getValue();
//			row.getCell("discountAfBPrice").setValue(FDCHelper.divide(discountAfAmount, buildingArea));
//			row.getCell("discountAmount").setValue(FDCHelper.subtract(standardTotalAmount, discountAfAmount).setScale(0, BigDecimal.ROUND_HALF_UP));
//			
//			BigDecimal baseStandardPrice=(BigDecimal) row.getCell("baseStandardPrice").getValue();
//			BigDecimal basePercent=FDCHelper.multiply(FDCHelper.divide(FDCHelper.subtract(baseStandardPrice, discountAfAmount), baseStandardPrice,6,BigDecimal.ROUND_HALF_UP), new BigDecimal(100));
//			if(basePercent!=null&&basePercent.compareTo(FDCHelper.ZERO)<=0){
//				basePercent=null;
//			}
//			row.getCell("basePercent").setValue(basePercent);
//			
//			BigDecimal baseDiscountAmount=FDCHelper.subtract(baseStandardPrice, discountAfAmount);
//			if(baseDiscountAmount!=null&&baseDiscountAmount.compareTo(FDCHelper.ZERO)<=0){
//				baseDiscountAmount=null;
//			}
//			row.getCell("baseDiscountAmount").setValue(baseDiscountAmount);
//		}
//		
//		if(this.kdtEntry.getColumnKey(e.getColIndex()).equals("discountAmount")||
//				this.kdtEntry.getColumnKey(e.getColIndex()).equals("discountPercent")){
////			BigDecimal percent=(BigDecimal) row.getCell("discountPercent").getValue();
////			RoomInfo room = (RoomInfo)row.getCell("room").getValue();
//			
////			SpecialDiscountEntryInfo entry=(SpecialDiscountEntryInfo) row.getUserObject();
////			entry.setSubManagerAgio(FDCHelper.subtract(percent, room.getManagerAgio()));
////			entry.setSubSalesDirectorAgio(FDCHelper.subtract(percent, room.getSalesDirectorAgio()));
////			entry.setSubSceneManagerAgio(FDCHelper.subtract(percent, room.getSceneManagerAgio()));
//		}
	}
	/**
	 * 选择客户后单价名称显示
	 */
	protected void prmtCustomer_dataChanged(DataChangeEvent e) throws Exception {
		SHECustomerInfo info=(SHECustomerInfo)this.prmtCustomer.getValue();
		if(info!=null){
			this.txtUnitPriceName.setText(((SHECustomerInfo)this.prmtCustomer.getValue()).getName()+"客户特殊优惠折扣单");
			
			EntityViewInfo view=new EntityViewInfo();
        	FilterInfo filter=new FilterInfo();
        	Set state=new HashSet();
        	state.add(TransactionStateEnum.PURAPPLE_VALUE);
        	state.add(TransactionStateEnum.PURAUDITING_VALUE);
        	state.add(TransactionStateEnum.PURAUDIT_VALUE);
        	state.add(TransactionStateEnum.CHANGENAMEAUDITING_VALUE);
        	state.add(TransactionStateEnum.CHANGEPIRCEAUDITING_VALUE);
        	state.add(TransactionStateEnum.QUITROOMAUDITING_VALUE);
        	filter.getFilterItems().add(new FilterItemInfo("head.bizState",state,CompareType.INCLUDE));
        	filter.getFilterItems().add(new FilterItemInfo("customer.id",info.getId().toString()));
        	filter.getFilterItems().add(new FilterItemInfo("head.sellProject.id",this.editData.getSellProject().getId().toString()));
        	
        	view.setFilter(filter);
        	SelectorItemCollection sel=new SelectorItemCollection();
        	sel.add("head.room.*");
        	sel.add("head.room.building.*");
        	view.setSelector(sel);
        	PurCustomerEntryCollection col=PurCustomerEntryFactory.getRemoteInstance().getPurCustomerEntryCollection(view);
        	Set roomId=new HashSet();
        	if(col.size()>0){
        		this.kdtEntry.removeRows();
        	}
        	for(int i=0;i<col.size();i++){
        		if(col.get(i).getHead().getRoom()!=null&&!roomId.contains(col.get(i).getHead().getRoom().getId().toString())){
        			IRow row=this.kdtEntry.addRow();
        			row.getCell("room").setValue(col.get(i).getHead().getRoom());
        			
        			SpecialDiscountEntryInfo entry=new SpecialDiscountEntryInfo();
        			row.setUserObject(entry);
        			
        			KDTEditEvent ee = new KDTEditEvent(row.getCell("room"));
        			ee.setRowIndex(row.getRowIndex());
        			ee.setColIndex(this.kdtEntry.getColumnIndex("room"));
        			ee.setValue(col.get(i).getHead().getRoom());
        			this.kdtEntry_editStopped(ee);
        			roomId.add(col.get(i).getHead().getRoom().getId().toString());
        		}
        	}
		}
		loadCustomerInfo();
	}
	public void actionRemove_actionPerformed(ActionEvent arg0) throws Exception {
		checkBeforeEditOrRemove("CANREMOVE");
		super.actionRemove_actionPerformed(arg0);
	}
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		checkBeforeEditOrRemove(CANTEDIT);
		super.actionEdit_actionPerformed(arg0);
		setSaveActionStatus();
	}
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		handleOldData();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		if(!checkCanSubmit()){
			MsgBox.showWarning(this,"单据状态已经在审批中或者已审批，不能再提交！");
			SysUtil.abort();
		}
		super.actionSubmit_actionPerformed(e);
		this.setOprtState("VIEW");
		this.auditAction.setVisible(true);
		this.auditAction.setEnabled(true);
		
		handleOldData();
	}
	protected void verifyInput(ActionEvent e) throws Exception{
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCustomer);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbRelate);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtOnePerson);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTwoPerson);
		Set BuildSet=new HashSet();
		if(this.kdtEntry.getRowCount()==0){
			FDCMsgBox.showWarning(this,"申请优惠不能为空！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtEntry.getRowCount();i++){
			IRow row=this.kdtEntry.getRow(i);
			RoomInfo room=(RoomInfo) row.getCell("room").getValue();
			if(room==null){
				FDCMsgBox.showWarning(this,"房间不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("room"));
				SysUtil.abort();
			}
			BuildSet.add(room.getBuilding().getId());
			if(BuildSet.size()>1){
				FDCMsgBox.showWarning(this,"不同楼栋不能进行特殊折扣优惠申请！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("room"));
				SysUtil.abort();
			}
//			if(room.getSellState().equals(RoomSellStateEnum.Sign)){
//				FDCMsgBox.showWarning(this,"房间已签约！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("room"));
//				SysUtil.abort();
//			}
			if(row.getCell("discountAfAmount").getValue()==null){
				FDCMsgBox.showWarning(this,"最终成交总价不能为空！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("discountAfAmount"));
				SysUtil.abort();
			}
			if(((BigDecimal)row.getCell("discountAfAmount").getValue()).compareTo(FDCHelper.ZERO)==0){
				FDCMsgBox.showWarning(this,"最终成交总价不能为0！");
				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("discountAfAmount"));
				SysUtil.abort();
			}
//			if(row.getCell("discountPercent").getValue()==null){
//				FDCMsgBox.showWarning(this,"表价优惠折扣比例%不能为空！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("discountPercent"));
//				SysUtil.abort();
//			}
//			if(((BigDecimal)row.getCell("discountPercent").getValue()).compareTo(FDCHelper.ZERO)==0){
//				FDCMsgBox.showWarning(this,"表价优惠折扣比例%不能为0！");
//				this.kdtEntry.getEditManager().editCellAt(row.getRowIndex(), this.kdtEntry.getColumnIndex("discountPercent"));
//				SysUtil.abort();
//			}
			Map detailSet = RoomDisplaySetting.getNewProjectSet(null,this.editData.getSellProject().getId().toString());
			boolean isBasePriceSell=false;
			if(detailSet!=null){
				isBasePriceSell = ((Boolean)detailSet.get(SHEParamConstant.T2_IS_FORCE_LIMIT_PRICE)).booleanValue();
			}
//			SignManageCollection signCol=SignManageFactory.getRemoteInstance().getSignManageCollection("select digit,toIntegerType,isBasePriceSell,DealTotalAmount from where room.id='"+room.getId().toString()+"' and (bizState='SignApple' or bizState='SignAudit')");
//			if(signCol.size()>0){
				if(isBasePriceSell){
					if(room.getBaseStandardPrice()==null){
						FDCMsgBox.showWarning(this,room.getName()+"底价不能为空！");
						SysUtil.abort();
					}
//					if(FDCHelper.subtract(row.getCell("discountAfAmount").getValue(),room.getBaseStandardPrice()).compareTo(FDCHelper.ZERO)<0){
//						FDCMsgBox.showWarning(this,room.getName()+"破底价，不能进行特殊优惠折扣申请！");
//						SysUtil.abort();
//					}
				}
//			}
		}
	}
	
    protected boolean checkCanSubmit() throws Exception {
		
		if(editData.getId()==null){
			return true;
		}
		//检查是否在工作流中
//		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		FDCBillStateEnum state = getBizState(editData.getId().toString());
		if (state != null
				&& (FDCBillStateEnum.AUDITTED.equals(state))) {
			return false;
		}else{
			return true;
		}
		
	}
    protected void checkBeforeEditOrRemove(String warning) {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, editData.getId().toString());
		
		FDCBillStateEnum bizState = ((SpecialDiscountInfo)editData).getBizState();
		
		if (bizState != null
				&& (bizState == FDCBillStateEnum.AUDITTING || bizState == FDCBillStateEnum.AUDITTED )) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("该单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
    protected void syncDataFromDB() throws Exception {
		//由传递过来的ID获取值对象
        if(getUIContext().get(UIContext.ID) == null)
        {
            String s = EASResource.getString(FrameWorkClientUtils.strResource + "Msg_IDIsNull");
            MsgBox.showError(s);
            SysUtil.abort();
        }
        IObjectPK pk = new ObjectUuidPK(BOSUuid.read(getUIContext().get(UIContext.ID).toString()));
        setDataObject(getValue(pk));
        loadFields();
	}
    protected void handleOldData() {
		if(!(getOprtState()==STATUS_FINDVIEW||getOprtState()==STATUS_VIEW)){
			FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
				public void run() {
					storeFields();
					initOldData(editData);
				}
			});
		}
	}
	public void auditAction_actionPerformed(ActionEvent e) throws Exception {
		String id = getSelectBOID();
		if (!FDCBillStateEnum.SUBMITTED.equals(getBizState(id))) {
			if (FDCBillStateEnum.AUDITTED.equals(getBizState(id))) {
				FDCMsgBox.showWarning("该单据已经是已审批状态！");
			}else{
				FDCMsgBox.showWarning("该单据不是提交状态，不能进行审批操作！");
			}
			return;
		}
		if (isModify()) {
			FDCMsgBox.showWarning("单据已被修改，请先提交。");
			this.abort();
		}
		
    	FDCClientUtils.checkBillInWorkflow(this, id);
		SpecialDiscountFactory.getRemoteInstance().audit(BOSUuid.read(id));
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
		this.unAuditAction.setVisible(true);
		this.unAuditAction.setEnabled(true);
		this.auditAction.setVisible(false);
		this.auditAction.setEnabled(false);
	}
	
    public FDCBillStateEnum getBizState(String id) throws EASBizException, BOSException, Exception{
    	if(id==null) return null;
    	SelectorItemCollection sels =new SelectorItemCollection();
    	sels.add("bizState");
    	return ((SpecialDiscountInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels)).getBizState();
    }
	public void unAuditAction_actionPerformed(ActionEvent e) throws Exception {
		super.unAuditAction_actionPerformed(e);
		String id = getSelectBOID();
		
		if (!FDCBillStateEnum.AUDITTED.equals(getBizState(id))) {
			FDCMsgBox.showWarning("该单据不是审批状态，不能进行反审批操作！");
			return;
		}
    	
    	FDCClientUtils.checkBillInWorkflow(this, id);
		
		SpecialDiscountFactory.getRemoteInstance().unAudit(BOSUuid.read(id));
		FDCClientUtils.showOprtOK(this);
		syncDataFromDB();
		handleOldData();
		setSaveActionStatus();
		this.unAuditAction.setVisible(false);
		this.unAuditAction.setEnabled(false);
		this.auditAction.setVisible(true);
		this.auditAction.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	    setAuditButtonStatus(oprtType);
	    if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
			this.actionRoomSelect.setEnabled(false);
			this.actionRoomDelete.setEnabled(false);
		} else {
			this.unLockUI();
			this.actionRoomSelect.setEnabled(true);
			this.actionRoomDelete.setEnabled(true);
		}
	}
    protected void setAuditButtonStatus(String oprtType){
    	if(STATUS_VIEW.equals(oprtType)) {
    		auditAction.setVisible(true);
    		unAuditAction.setVisible(true);
    		auditAction.setEnabled(true);
    		unAuditAction.setEnabled(true);
    		
    		SpecialDiscountInfo bill = (SpecialDiscountInfo)editData;
    		if(editData!=null){
    			if(FDCBillStateEnum.AUDITTED.equals(bill.getBizState())){
    				unAuditAction.setVisible(true);
    				unAuditAction.setEnabled(true);   
    	    		auditAction.setVisible(false);
    	    		auditAction.setEnabled(false);
    			}else{
    				unAuditAction.setVisible(false);
    				unAuditAction.setEnabled(false);   
    	    		auditAction.setVisible(true);
    	    		auditAction.setEnabled(true);
    			}
    		}
    	}else {
    		auditAction.setVisible(false);
    		unAuditAction.setVisible(false);
    		auditAction.setEnabled(false);
    		unAuditAction.setEnabled(false);
    	}
    }
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.SpecialDiscountFactory.getRemoteInstance();
    }
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        SpecialDiscountInfo info = new SpecialDiscountInfo();
        info.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
        try{
        	info.setBizDate(FDCCommonServerHelper.getServerTimeStamp());//业务日期
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		SellProjectInfo sellproject=(SellProjectInfo) this.getUIContext().get("sellProject");
		info.setSellProject(sellproject);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		String roomId=(String)this.getUIContext().get("roomId");
		if(roomId!=null){
			RoomInfo room=null;
			try {
				room=RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(roomId));
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
//			if(room!=null){
//				if(room.getSellState().equals(RoomSellStateEnum.Sign)){
//					FDCMsgBox.showWarning(this,"房间已签约！");
//					SysUtil.abort();
//				}
//			}
			SpecialDiscountEntryInfo entry=new SpecialDiscountEntryInfo();
			entry.setRoom(room);
			info.getEntrys().add(entry);
		}
        return info;
    }
    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection sel = super.getSelectors();
    	sel.add("discountPercent");
    	sel.add("CU.*");
    	sel.add("bizState");
    	sel.add("entrys.room.*");
    	sel.add("entrys.room.productType.*");
    	sel.add("entrys.room.building.id");
    	sel.add("entrys.subManagerAgio");
    	sel.add("entrys.subSceneManagerAgio");
    	sel.add("entrys.subSalesDirectorAgio");
    	sel.add("agioEntry.*");
		sel.add("agioEntry.agio.*");
		sel.add("productType.*");
		sel.add("subPrice");
		sel.add("basePercent");
    	return sel;
    }
	protected IObjectValue createNewDetailData(KDTable arg0) {
		return null;
	}
	protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
    }
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
  	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	} 
    	else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    protected void attachListeners() {
		addDataChangeListener(this.prmtCustomer);
	}

	protected void detachListeners() {
		removeDataChangeListener(this.prmtCustomer);
	}
	protected void btnChooseAgio_actionPerformed(ActionEvent e)throws Exception {
		if (this.kdtEntry.getRowCount()==0||this.kdtEntry.getRow(0).getCell("room").getValue() == null) {
			FDCMsgBox.showWarning(this,"请先选择房间！");
			return;
		}
		RoomInfo room = (RoomInfo)this.kdtEntry.getRow(0).getCell("room").getValue();
		AgioParam agioParam = AgioSelectUI.showUI(this, room.getId().toString(), 
				(AgioEntryCollection)this.txtAgioDesc.getUserObject(), this.currAgioParam ,this.editData,false,false);
		if (agioParam != null&&!this.currAgioParam.equals(agioParam)) {
			this.currAgioParam = agioParam;
			this.txtAgioDesc.setUserObject(agioParam.getAgios());
			
			BigDecimal buildingArea=room.getActualBuildingArea()==null?room.getBuildingArea():room.getActualBuildingArea();
			
			PurchaseParam purParam = SHEManageHelper.getAgioParam(this.currAgioParam, room, 
					null,room.getCalcType(),false,room.getRoomArea(),buildingArea,room.getRoomPrice(),room.getBuildPrice(),room.getStandardTotalAmount(),null,null,null,SpecialAgioEnum.DaZhe,null,null);
			if(purParam!=null) {
				this.txtAgioDesc.setText(purParam.getAgioDes());
				this.kdtEntry.getRow(0).getCell("agioPrice").setValue(purParam.getContractBuildPrice());
				this.kdtEntry.getRow(0).getCell("agioAmount").setValue(purParam.getContractTotalAmount());
				KDTEditEvent ee = new KDTEditEvent(this.kdtEntry.getRow(0).getCell("discountAmount"));
				ee.setRowIndex(0);
				ee.setColIndex(this.kdtEntry.getColumnIndex("discountAmount"));
				ee.setValue(this.kdtEntry.getRow(0).getCell("discountAmount").getValue());
				kdtEntry_editStopped(ee);
			}
		}
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		SpecialDiscountPrintProvider data = new SpecialDiscountPrintProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
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
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		SpecialDiscountPrintProvider data = new SpecialDiscountPrintProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/sellhouse/SpecialDiscount";
	}

	// 对应的套打Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.sellhouse.app.SpecialDiscountPrintQuery");
	}
}