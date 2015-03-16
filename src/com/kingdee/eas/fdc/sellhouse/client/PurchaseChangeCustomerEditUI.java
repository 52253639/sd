/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditListener;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CertifacateNameEnum;
import com.kingdee.eas.fdc.sellhouse.CertificateFactory;
import com.kingdee.eas.fdc.sellhouse.CertificateInfo;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurCustomerCollection;
import com.kingdee.eas.fdc.sellhouse.PurCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerOldEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCusomerOldEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.Uuid;

/**
 * output class name
 */
public class PurchaseChangeCustomerEditUI extends AbstractPurchaseChangeCustomerEditUI {

	private static final Logger logger = CoreUIObject.getLogger(PurchaseChangeCustomerEditUI.class);
	private UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	boolean first_oldRoom = true;
	/**
	 * output class constructor
	 */
	public PurchaseChangeCustomerEditUI() throws Exception {
		super();
	}

	protected void attachListeners() {
		// TODO 自动生成方法存根

	}

	protected void detachListeners() {
		// TODO 自动生成方法存根

	}

	protected KDTextField getNumberCtrl() {
		// TODO 自动生成方法存根
		return this.txtNumber;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseChangeCustomerFactory.getRemoteInstance();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.btnUp.setEnabled(true);
		this.btnDown.setEnabled(true);
		super.actionEdit_actionPerformed(e);
//		this.btnAudit.setVisible(true);
//		this.btnAudit.setEnabled(true);
		
		if(this.kdtNewTable.getColumn("certificateName")!=null){
			kdtNewTable.getColumn("certificateName").getStyleAttributes().setLocked(true);
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyBalance();
		super.actionSave_actionPerformed(e);
	}
	
	/*
	 * 保存客户信息到交易信息表，进行快照。
	 * by zgy 2010-12-18
	 */
	public void storePurCustomer(KDTable talbe, BOSUuid editdateID){
		if(talbe.getRowCount()!=0){
			FDCCustomerInfo customer = null;
			for(int i = 0 ;i<talbe.getRowCount();i++){
				 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
				if(talbe.getRow(i).getCell("id").getValue()!=null){
					String id  = talbe.getRow(i).getCell("id").getValue().toString();
					if(id!=null){
						try {
							customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
						} catch (EASBizException e) {
							e.printStackTrace();
						} catch (BOSException e) {
							e.printStackTrace();
						}
					}
				}
				PurCustomerInfo purCusinfo = new PurCustomerInfo();
				if(talbe.getName().equals("kdtOldTable")){
					purCusinfo.setOldPurCustomer(editdateID);
				}else{
					purCusinfo.setPurchase(editdateID);
				}
				if(customer!=null){
					purCusinfo.setSeq(i);
					purCusinfo.setPropertyPercent((BigDecimal) talbe.getRow(i).getCell("propertyPercent").getValue());
					purCusinfo.setCustomerName(talbe.getRow(i).getCell("customer").getValue().toString());
					purCusinfo.setCustomerID(customer.getId());
					purCusinfo.setTel((String) talbe.getRow(i).getCell("phone").getValue());
					purCusinfo.setPostalcode((String) talbe.getRow(i).getCell("postalcode").getValue());
					purCusinfo.setMailAddress((String) talbe.getRow(i).getCell("mailaddress").getValue());
					purCusinfo.setDescription((String) talbe.getRow(i).getCell("des").getValue());
					if (talbe.getRow(i).getCell("certificateName").getValue() != null) {
						//purCusinfo.setCertificateName((CertifacateNameEnum) talbe.getRow(i).getCell("certificateName").getValue());
						//update by renliang at 2011-3-3
						CertificateInfo info = (CertificateInfo)talbe.getRow(i).getCell("certificateName").getValue();
						if(info!=null){
							purCusinfo.setCertificateName(info);
						}
					}
					purCusinfo.setCertificateNumber((String) talbe.getRow(i).getCell("certificateNumber").getValue());
					try {
						PurCustomerFactory.getRemoteInstance().addnew(purCusinfo);
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}		
			}
		}
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyBalance();
		super.actionSubmit_actionPerformed(e);
		this.btnAudit.setVisible(true);
		this.btnAudit.setEnabled(true);
		this.setOprtState(STATUS_VIEW);
		if(editData.getId()!=null){
			FilterInfo info = new FilterInfo();
			info.getFilterItems().add(new FilterItemInfo("oldPurCustomer", this.editData.getId()));
			info.getFilterItems().add(new FilterItemInfo("purchase", this.editData.getId()));
			info.setMaskString("#0 or #1");
			try {
				PurCustomerFactory.getRemoteInstance().delete(info);
			} catch (BOSException e1) {
				e1.printStackTrace();
			}
			storePurCustomer(kdtOldTable, this.editData.getId());
			storePurCustomer(kdtNewTable, this.editData.getId());
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("CU");
		sic.add("orgUnit");
		sic.add("state");
		sic.add("purchase.id");
		sic.add("newCustomerEntry.*");
		sic.add("newCustomerEntry.customer.*");
		sic.add("newCustomerEntry.customer.certificateName.id");
		sic.add("newCustomerEntry.customer.certificateName.name");
		sic.add("newCustomerEntry.customer.certificateName.number");
		sic.add("oldCustomerEntry.*");
		sic.add("oldCustomerEntry.customer.*");
		sic.add("oldCustomerEntry.customer.certificateName.id");
		sic.add("oldCustomerEntry.customer.certificateName.name");
		sic.add("oldCustomerEntry.customer.certificateName.number");
		return sic;
	}

	PurchaseInfo purchaseInfo;

	public void onLoad() throws Exception {
		// TODO 自动生成方法存根
		super.onLoad();
		//为了兼容销售控制入口的更名 要判断是否有purchaseID传过来，有就是销售控制入口,没有就是在更名管理入口新增 eric_wang 2010.08.13
		if(this.editData.getPurchase()!=null){
			IObjectPK objectPK = new ObjectUuidPK(this.editData.getPurchase().getId().toString());
			SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
			selectorItemCollection.add("*");
			selectorItemCollection.add("room.*");
			selectorItemCollection.add("room.sellOrder.name");
			selectorItemCollection.add("room.building.name");
			selectorItemCollection.add("room.buildUnit.name");
			selectorItemCollection.add("room.building.subarea.name");
			selectorItemCollection.add("room.building.sellProject.name");
			selectorItemCollection.add("room.roomModel.name");
			selectorItemCollection.add("customerInfo.*");
			selectorItemCollection.add("customerInfo.customer.*");
	
			purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(objectPK, selectorItemCollection);
			initRoomInfo(purchaseInfo);
			initCustomerInfo(this.editData.getOldCustomerEntry(), this.kdtOldTable);
			//zgy 
			if(getUIContext().get("purchaseID")!=null){
				String id = getUIContext().get("purchaseID").toString();
				showNewTable(id, kdtOldTable, "purchase");
			}else{
				if(editData.getState().equals(FDCBillStateEnum.SAVED)){
					showNewTable(this.editData.getPurchase().getId().toString(), kdtOldTable, "purchase");
				}else{
					showNewTable(this.editData.getId().toString(), kdtOldTable, "oldPurchase");
				}
			}
			kdtOldTable.setEnabled(false);
	
			initCustomerInfo(this.editData.getNewCustomerEntry(), this.kdtNewTable);
			//by zgy 
			if(editData.getState().equals(FDCBillStateEnum.SUBMITTED)){
				showNewTable(this.editData.getId().toString(),kdtNewTable,"newPurchase");
			}
			initNewTableColumn();
			kdtNewTable.getColumn("seq").getStyleAttributes().setLocked(true);
		}else{
			this.kDDatePicker1.setValue(new java.util.Date());
			this.prmtLastUpdateUser.setValue(userInfo);
		}
		
		if ("VIEW".equals(this.getOprtState())) {
			this.btnUp.setEnabled(false);
			this.btnDown.setEnabled(false);
		}

		if(this.kdtNewTable.getColumn("certificateName")!=null){
			kdtNewTable.getColumn("certificateName").getStyleAttributes().setLocked(true);
		}
		this.actionAuditResult.setVisible(true);
		this.actionAuditResult.setEnabled(true);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
		this.actionAttachment.setEnabled(true);
		this.actionAddNew.setEnabled(false);
		this.actionAddNew.setVisible(false);

		this.actionAuditResult.setVisible(false);
		this.actionMultiapprove.setVisible(false);
		this.actionNextPerson.setVisible(false);

		if (!FDCBillStateEnum.SAVED.equals(this.editData.getState())) {
			actionSave.setEnabled(false);
		}
		txtDescription.setMaxLength(255);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.storeFields();
		this.initOldData(this.editData);
		this.setUITitle("更名管理");
		if(!SHEHelper.getCurrentSaleOrg().isIsBizUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if("4AUDITTED".equalsIgnoreCase(String.valueOf(this.editData.get("state")))){
			this.btnRemove.setEnabled(false);
		}
		
		//增加table监听器，当修改了客户的时候去更新数据 xiaoao_liu
		this.kdtNewTable.addKDTEditListener(new KDTEditListener() {

			public void editCanceled(KDTEditEvent arg0) {
				
			}

			public void editStarted(KDTEditEvent arg0) {
			}

			public void editStarting(KDTEditEvent arg0) {
			}

			public void editStopped(KDTEditEvent arg0) {
				if (arg0.getColIndex() == kdtNewTable.getColumn("customer").getColumnIndex()) {
					dealCustomerInfo();
				}
			}

			public void editStopping(KDTEditEvent arg0) {
				
			}

			public void editValueChanged(KDTEditEvent arg0) {
				
			}
			
		});
	}
	
	private void dealCustomerInfo(){
		
		int index = this.kdtNewTable.getSelectManager().getActiveRowIndex();
		IRow row  = this.kdtNewTable.getRow(index);
		if(row==null){
			return;
		}
		
		FDCCustomerInfo info  = null;
		if(row.getCell("customer").getValue()!=null){
			if(row.getCell("customer").getValue() instanceof FDCCustomerInfo){
				info=(FDCCustomerInfo)row.getCell("customer").getValue();
			}else{
				if(row.getCell("id").getValue()!=null){
					String id  = row.getCell("id").getValue().toString();
					try {
						info  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
					} catch (EASBizException e) {
						e.printStackTrace();
					} catch (BOSException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if(info!=null){
			setCustomerRowData(info,row);
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		if (this.txtNumber.isEnabled()) {
			this.txtNumber.requestFocus();
		}
	}

	void initNewTableColumn() {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		this.kdtNewTable.getColumn("propertyPercent").setEditor(numberEditor);
		this.kdtNewTable.getColumn("propertyPercent").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.kdtNewTable.getColumn("propertyPercent").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));

		KDBizPromptBox f7Customer = new KDBizPromptBox();
		f7Customer.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.CustomerQuery");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		view.setFilter(filter);
		f7Customer.setEntityViewInfo(view);
		f7Customer.setEditable(true);
		f7Customer.setDisplayFormat("$name$");
		f7Customer.setEditFormat("$number$");
		f7Customer.setCommitFormat("$number$");
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Customer);
		this.kdtNewTable.getColumn("customer").setEditor(f7Editor);

		this.kdtNewTable.getColumn("postalcode").getStyleAttributes().setLocked(true);
		this.kdtNewTable.getColumn("phone").getStyleAttributes().setLocked(true);
		this.kdtNewTable.getColumn("certificateName").getStyleAttributes().setLocked(true);
		this.kdtNewTable.getColumn("certificateNumber").getStyleAttributes().setLocked(true);
		this.kdtNewTable.getColumn("mailaddress").getStyleAttributes().setLocked(true);
		this.kdtNewTable.getColumn("bookDate").getStyleAttributes().setLocked(true);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.kdtNewTable.getColumn("des").setEditor(txtEditor);
	}

	// 初始化房间信息
	void initRoomInfo(PurchaseInfo purchaseInfo) {
		RoomInfo roomInfo = purchaseInfo.getRoom();
		txtRoomNumber.setText(roomInfo.getNumber());
		txtProjectName.setText(roomInfo.getBuilding().getSellProject().getName());
		if (roomInfo.getBuilding().getSubarea() != null) {
			txtSubarea.setText(roomInfo.getBuilding().getSubarea().getName());
		}
		spiUnit.setValue(new Integer(roomInfo.getBuildUnit() == null ? 0 : roomInfo.getBuildUnit().getSeq()));
		txtBuilding.setText(roomInfo.getBuilding().getName());
		if(roomInfo.getSellOrder()!=null){
			txtSellOrder.setText(roomInfo.getSellOrder().getName());
		}
		
		f7RoomModel.setValue(roomInfo.getRoomModel());
		if (roomInfo.getBuildingArea() != null) {
			txtBuildingArea.setText(roomInfo.getBuildingArea().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
		if (roomInfo.getRoomArea() != null) {
			txtRoomArea.setText(roomInfo.getRoomArea().setScale(2,BigDecimal.ROUND_HALF_UP).toString());
		}
	}
	/*
	 * 交易分录 ——排序
	 */
	private void sortBySeq_purcustomer(PurCustomerCollection purCustomers) {
		if (purCustomers == null || purCustomers.size() <= 1)
			return;
		Object[] objs = purCustomers.toArray();
		Arrays.sort(objs, new Comparator() {
			public int compare(Object arg0, Object arg1) { 
				PurCustomerInfo tmp0 = (PurCustomerInfo) arg0;
				PurCustomerInfo tmp1 = (PurCustomerInfo) arg1;
				if (tmp0 == null || tmp1 == null) {
					return 0;
				}
				return tmp0.getSeq() - tmp1.getSeq();
			}
		});
		purCustomers.clear();

		for (int i = 0; i < objs.length; i++) {
			purCustomers.add((PurCustomerInfo) objs[i]);
		}
	}
	// 初始化客户信息
	void initCustomerInfo(AbstractObjectCollection collection, KDTable talbe) {
		talbe.checkParsed();
		talbe.removeRows();
		CRMHelper.sortCollection(collection, "seq",true);
		boolean isMain = true;
//		//增加
//		PurCustomerCollection  coll =  null;
//		if(this.editData!=null&&this.editData.getId()!=null){
//			EntityViewInfo view = new EntityViewInfo();
//			FilterInfo info = new FilterInfo();
//			if(talbe.getName().equals("kdtOldTable")){
//				info.getFilterItems().add(new FilterItemInfo("oldPurCustomer", this.editData.getId()));
//			}else{
//				info.getFilterItems().add(new FilterItemInfo("purchase", this.editData.getId()));
//			}
//			SelectorItemCollection sic = super.getSelectors();
//			sic.add("*");
//			view.setSelector(sic);
//			view.setFilter(info);
//			try {
//				coll = PurCustomerFactory.getRemoteInstance().getPurCustomerCollection(view);
//			} catch (BOSException e) {
//				e.printStackTrace();
//			}
//		}
//		if(coll!=null&&coll.size()!=0&&first_oldRoom){
//			sortBySeq_purcustomer(coll);
//			for (int j = 0; coll != null && j < coll.size(); j++) {
//				PurCustomerInfo pCinfo  = coll.get(j);
//				if(pCinfo!=null){
//					IRow row = talbe.addRow();
//					row.getCell("seq").setValue(isMain?Boolean.TRUE:Boolean.FALSE);
//					isMain = false;
//					row.getCell("propertyPercent").setValue(pCinfo.getPropertyPercent()); 
//					row.getCell("des").setValue(pCinfo.getDescription());
//					//给性别赋值
//					row.getCell("customer").setValue(pCinfo.getCustomerName());
//					row.getCell("id").setValue(pCinfo.getCustomerID());
//					row.getCell("postalcode").setValue(pCinfo.getPostalcode());
//					row.getCell("phone").setValue(pCinfo.getTel());
//					row.getCell("certificateName").setValue(pCinfo.getCertificateName());
//					row.getCell("certificateNumber").setValue(pCinfo.getCertificateNumber());
//					row.getCell("mailaddress").setValue(pCinfo.getMailAddress());
//					row.getCell("bookDate").setValue(pCinfo.getCreateTime());
//				}
//			}
//		}else{
			for (int i = 0; collection != null && i < collection.size(); i++) {
				IObjectValue objectValue = collection.getObject(i);
				IRow row = talbe.addRow();
				row.getCell("propertyPercent").setValue(objectValue.get("propertyPercent"));
				FDCCustomerInfo customerInfo2 = (FDCCustomerInfo) objectValue.get("customer");
				if (customerInfo2 != null) {
					row.getCell("seq").setValue(isMain?Boolean.TRUE:Boolean.FALSE);
					isMain = false;
					row.getCell("customer").setValue(customerInfo2);
					row.getCell("id").setValue(customerInfo2.getId().toString());
					row.getCell("postalcode").setValue(customerInfo2.getPostalcode());
					row.getCell("phone").setValue(customerInfo2.getPhone());
					row.getCell("certificateName").setValue(customerInfo2.getCertificateName());
					row.getCell("certificateNumber").setValue(customerInfo2.getCertificateNumber());
					row.getCell("mailaddress").setValue(customerInfo2.getMailAddress());
					row.getCell("bookDate").setValue(customerInfo2.getCreateTime());
				}
				row.getCell("des").setValue(objectValue.get("description"));
			}
//		}
	}

	// base1是有数据的 base2是空的
	void changeObjectValue(CoreBaseInfo base1, CoreBaseInfo base2) {
		Enumeration enumeration = base1.keys();
		while (enumeration.hasMoreElements()) {
			String obj = enumeration.nextElement().toString();
			if (!"id".equals(obj)) {
				base2.put(obj, base1.get(obj));
			}
		}
	}

	protected IObjectValue createNewData() {
		PurchaseChangeCustomerInfo customerInfo = new PurchaseChangeCustomerInfo();
		customerInfo.setState(FDCBillStateEnum.SAVED);
		//为了兼容销售控制入口的更名 要判断是否有purchaseID传过来，有就是销售控制入口,没有就是在更名管理入口新增 eric_wang 2010.08.13
		if(getUIContext().get("purchaseID")!=null){
			String id = getUIContext().get("purchaseID").toString();
			try {
				SelectorItemCollection selectorItemCollection = new SelectorItemCollection();
				selectorItemCollection.add("*");
				selectorItemCollection.add("customerInfo.*");
				selectorItemCollection.add("customerInfo.customer.*");
				purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(id), selectorItemCollection);
				customerInfo.setPurchase(purchaseInfo);
	
				// 客户信息
				PurchaseChangeCusomerEntryCollection cusomerEntryCollection = new PurchaseChangeCusomerEntryCollection();
				for (int i = 0; purchaseInfo.getCustomerInfo() != null && i < purchaseInfo.getCustomerInfo().size(); i++) {
					PurchaseCustomerInfoInfo purchaseCustomerInfoInfo = purchaseInfo.getCustomerInfo().get(i);
					PurchaseChangeCusomerEntryInfo changeCusomerEntryInfo = new PurchaseChangeCusomerEntryInfo();
					changeObjectValue(purchaseCustomerInfoInfo, changeCusomerEntryInfo);
					cusomerEntryCollection.add(changeCusomerEntryInfo);
				}
				customerInfo.getNewCustomerEntry().addCollection(cusomerEntryCollection);
	
				PurchaseChangeCusomerOldEntryCollection changeCusomerOldEntryCollection = new PurchaseChangeCusomerOldEntryCollection();
				for (int i = 0; purchaseInfo.getCustomerInfo() != null && i < purchaseInfo.getCustomerInfo().size(); i++) {
					PurchaseCustomerInfoInfo purchaseCustomerInfoInfo = purchaseInfo.getCustomerInfo().get(i);
					PurchaseChangeCusomerOldEntryInfo changeCusomerOldEntryInfo = new PurchaseChangeCusomerOldEntryInfo();
					changeObjectValue(purchaseCustomerInfoInfo, changeCusomerOldEntryInfo);
					changeCusomerOldEntryCollection.add(changeCusomerOldEntryInfo);
				}
				customerInfo.getOldCustomerEntry().addCollection(changeCusomerOldEntryCollection);
	
				customerInfo.setCreator(userInfo);
				customerInfo.setBizDate(new Date());
				customerInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
				customerInfo.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
				customerInfo.setBookedDate(new Date());
			} catch (EASBizException e) {
				this.handleException(e);
			} catch (BOSException e) {
				this.handleException(e);
			}
		}
		return customerInfo;
	}

	public boolean destroyWindow() {
		boolean b = super.destroyWindow();
		if (getUIContext().get(UIContext.OWNER) instanceof PurchaseChangeCustomerListUI) {
			((PurchaseChangeCustomerListUI) getUIContext().get(UIContext.OWNER)).kDTable1.removeRows();
		}
		return b;
	}

	public void storeFields() {
		super.storeFields();
		StringBuffer newCustomer = new StringBuffer();
		String oldCustomer = "";
		for (int i = 0; i < kdtOldTable.getRowCount(); i++) {
			IRow row = kdtOldTable.getRow(i);
			PurchaseChangeCusomerEntryInfo info = new PurchaseChangeCusomerEntryInfo();

			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				try {
					customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
			}
			info.setCustomer(customer);
			if (i != 0) {
				oldCustomer += ",";
			}
			oldCustomer += customer.getName();
//			if (row.getCell("customer").getValue() instanceof FDCCustomerInfo) {
//				FDCCustomerInfo customerInfo = (FDCCustomerInfo) row.getCell("customer").getValue();
//				info.setCustomer(customerInfo);
//				if (i != 0) {
//					oldCustomer += ",";
//				}
//				oldCustomer += customerInfo.getName();
//			}

		}
		this.editData.setOldCustomer(oldCustomer);
		PurchaseChangeCusomerEntryCollection collection = new PurchaseChangeCusomerEntryCollection();

		for (int i = 0; i < kdtNewTable.getRowCount(); i++) {
			IRow row = kdtNewTable.getRow(i);
			PurchaseChangeCusomerEntryInfo info = new PurchaseChangeCusomerEntryInfo();
			if (row.getCell("propertyPercent").getValue() instanceof BigDecimal) {
				info.setPropertyPercent((BigDecimal) row.getCell("propertyPercent").getValue());
			}
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				try {
					customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
			}
			info.setCustomer(customer);
//			if (row.getCell("customer").getValue() instanceof FDCCustomerInfo) {
//				FDCCustomerInfo customerInfo = (FDCCustomerInfo) row.getCell("customer").getValue();
//				info.setCustomer(customerInfo);
//			}
			if (row.getCell("des").getValue() instanceof String) {
				info.setDescription((String) row.getCell("des").getValue());
			}
			info.setSeq(i);
			info.setHead(this.editData);

			collection.add(info);
		}

		for (int i = 0; i < collection.size(); i++) {
			PurchaseChangeCusomerEntryInfo purChangeCustomer = collection.get(i);
			if (i != 0) {
				newCustomer.append(",");
			}
			if (purChangeCustomer.getCustomer() != null) {
				newCustomer.append(purChangeCustomer.getCustomer());
			}
		}
		//为了兼容销售控制入口的更名 要判断是否有purchaseID传过来，有就是销售控制入口,没有就是在更名管理入口新增 eric_wang 2010.08.13
		if(this.editData.getPurchase()!=null){
			String number = this.editData.getPurchase().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("purchase.id", number));
			try {
				if(RoomSignContractFactory.getRemoteInstance().exists(filter)){
					this.editData.setChangeState("签约更名");
				}else{
					this.editData.setChangeState("认购更名");
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		this.editData.setNewCustomer(newCustomer.toString());
		this.editData.getNewCustomerEntry().clear();
		this.editData.getNewCustomerEntry().addCollection(collection);
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		// TODO 自动生成方法存根
		super.verifyInput(e);
		verifyCustomer();
		verifyBalance();
	}

	/**
	 * 对于已结算的期间，不允许进行更名及修改
	 * */
	private void verifyBalance() {
		if(this.txtName==null||"".equals(txtName.getText())||" ".equals(txtName.getText())){
			MsgBox.showInfo("名称不能为空。");
			this.abort();
		}
		Date bizDate = (Date) this.kDDatePicker1.getValue();
		if(bizDate==null){
			MsgBox.showInfo("更名日期不能为空。");
			this.abort();
		}
		Date balanceEndDate = null;
		PurchaseChangeCustomerInfo info = this.editData;
		SellProjectInfo sellProject=null;
		if(purchaseInfo!=null){
			sellProject = purchaseInfo.getSellProject();
		}
		if (sellProject == null && info != null) {
			if (info.getPurchase() != null) {
				if (info.getPurchase().getSellProject() != null) {
					sellProject = info.getPurchase().getSellProject();
				}
			}
		}

		if (sellProject != null) {
			try {
				balanceEndDate = getLastEndDate(sellProject.getId().toString());
			} catch (Exception e) {
				handleException(e);
				e.printStackTrace();
			}
			SHEHelper.verifyBalance(bizDate, balanceEndDate);
		}
	}

	/***
	 * 按销售项目取上次结算的截止日期。
	 * **/

	private Date getLastEndDate(String sellProjectId) throws Exception {
		Date lastEndDate = null;
		FDCSQLBuilder detailBuilder = new FDCSQLBuilder();
		detailBuilder.appendSql("select FBalanceEndDate from T_SHE_SellProject where FID = ?");
		detailBuilder.addParam(sellProjectId);
		try {
			IRowSet detailSet = detailBuilder.executeQuery();
			if (detailSet.next()) {
				lastEndDate = detailSet.getDate("FBalanceEndDate");
			}
		} catch (Exception e) {
			handleException(e);
			e.printStackTrace();
		}
		return lastEndDate;
	}

	private void verifyCustomer() throws BOSException {
		BigDecimal percent = FDCHelper.ZERO;
		Map customerMap = new HashMap();
		if (this.kdtNewTable.getRowCount() == 0) {
			MsgBox.showInfo("没有认购客户,请添加!");
			this.abort();
		}

		for (int i = 0; i < this.kdtNewTable.getRowCount(); i++) {
			IRow row = this.kdtNewTable.getRow(i);
			if (row.getCell("propertyPercent").getValue() == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有设置产权比例!");
				this.abort();
			}else{
				//添加单个产权比例不能等于0的判断  by renliang at 2010-12-22
				BigDecimal propertyPercent = (BigDecimal) row.getCell("propertyPercent").getValue();
				if (propertyPercent.compareTo(new BigDecimal("0")) == 0) {
					MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户的产权比例不能等于0!");
					this.abort();
				}
			}
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				try {
					customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
			}
//			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			if (customer == null) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户没有录入!");
				this.abort();
			}
			if (customer.getCertificateName() == null || customer.getCertificateNumber() == null) {
				MsgBox.showInfo("客户" + customer.getName() + "证件不能为空!!");
				CustomerCardUI.addTheCustomerAuthority(customer, this);
				setCustomerRowData(customer, row);
				this.abort();
			}
			if (customerMap.containsKey(customer)) {
				MsgBox.showInfo("第" + (row.getRowIndex() + 1) + "行客户重复!");
				this.abort();
			} else {
				customerMap.put(customer, customer);
			}
			percent = percent.add((BigDecimal) row.getCell("propertyPercent").getValue());
		}
		if (percent.compareTo(new BigDecimal("100")) != 0) {
			MsgBox.showInfo("产权比例不等100%!");
			this.abort();
		}
		// RoomInfo room = purchaseInfo.getRoom();
		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.getSelector().add("customerInfo.*");
		// view.setFilter(filter);
		// filter.getFilterItems().add(
		// new FilterItemInfo("room.id", room.getId().toString()));
		// filter.getFilterItems().add(
		// new FilterItemInfo("purchaseState",
		// PurchaseStateEnum.ChangeRoomBlankOut,
		// CompareType.NOTEQUALS));
		// filter.getFilterItems()
		// .add(
		// new FilterItemInfo("purchaseState",
		// PurchaseStateEnum.ManualBlankOut,
		// CompareType.NOTEQUALS));
		// filter.getFilterItems()
		// .add(
		// new FilterItemInfo("purchaseState",
		// PurchaseStateEnum.NoPayBlankOut,
		// CompareType.NOTEQUALS));
		// if (this.editData.getId() != null) {
		// filter.getFilterItems().add(
		// new FilterItemInfo("id", this.editData.getId().toString(),
		// CompareType.NOTEQUALS));
		// }
		// PurchaseCollection purchases = PurchaseFactory.getRemoteInstance()
		// .getPurchaseCollection(view);
		// for (int i = 0; i < purchases.size(); i++) {
		// PurchaseInfo pur = purchases.get(i);
		// PurchaseCustomerInfoCollection customerInfos = pur
		// .getCustomerInfo();
		// for (int j = 0; j < customerInfos.size(); j++) {
		// PurchaseCustomerInfoInfo customerInfoInfo = customerInfos
		// .get(j);
		// if (customerInfoInfo.getCustomer() != null) {
		// String aCusId = customerInfoInfo.getCustomer().getId()
		// .toString();
		// for (int k = 0; k < this.kdtNewTable.getRowCount(); k++) {
		// IRow row = this.kdtNewTable.getRow(k);
		// FDCCustomerInfo customer = (FDCCustomerInfo) row
		// .getCell("customer").getValue();
		// if (customer.getId().toString().equals(aCusId)) {
		// MsgBox.showInfo("第" + (row.getRowIndex() + 1)
		// + "行客户已经认购过了该房间!");
		// this.abort();
		// }
		// }
		// }
		// }
		// }
		// if (purchases.size() > 0) {
		// if (MsgBox.showConfirm2New(this, "已经有人认购该房间,但未付款,是否继续?") !=
		// MsgBox.YES) {
		// this.abort();
		// }
		// }
	}

	/**
	 * 判断是否是预定操作
	 */
	private boolean isPrePurchase() {
		Boolean bol = (Boolean) this.getUIContext().get("isPrePurchase");
		if (bol == null) {
			return false;
		}
		return bol.booleanValue();
	}

	protected void btnAddCustomer_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(CustomerEditUI.KEY_DESTORY_WINDOW, Boolean.TRUE);
		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerEditUI.class.getName(), uiContext, null, "ADDNEW");
		uiWindow.show();

		CustomerEditUI cusEditUI = (CustomerEditUI) uiWindow.getUIObject();
		FDCCustomerInfo cus = (FDCCustomerInfo) cusEditUI.getUIContext().get(CustomerEditUI.KEY_SUBMITTED_DATA);
		if (cus != null) {
			addCustomerRow(cus);
		}
	}

	protected void btnAdd_actionPerformed(ActionEvent e) throws Exception {
		if(this.purchaseInfo==null){
			FDCMsgBox.showInfo("请先选择房间");
			this.abort();
		}else{
			if(this.purchaseInfo.getSalesman()==null){
				FDCMsgBox.showInfo("请先选择房间");
				this.abort();
			}
		}
		this.purchaseInfo.getSalesman();
//		方波说这里的过滤条件改为和认购单上的一样  xiaoao_liu
		UserInfo info = SysContext.getSysContext().getCurrentUserInfo();// (UserInfo) this.f7Seller.getValue();
		FDCCustomerInfo customer = SHEHelper.selectCustomer(this.getUIWindow(), info, SHEHelper.getCurrentSaleOrg());
		if(customer!=null){
			if(customer.getCertificateName()!=null){
				if(customer.getCertificateName().getId()!=null){
					if(customer.getCertificateName().getName()==null){
						CertificateInfo certificateInfo= CertificateFactory.getRemoteInstance().getCertificateInfo("select id,number,name where id='"+customer.getCertificateName().getId()+"'");
						if(certificateInfo!=null){
							customer.setCertificateName(certificateInfo);
						}
					}
				}
			}
		}
		if (customer != null)
			addCustomerRow(customer);

		if(this.kdtNewTable.getRowCount()>1) {
			this.kdtNewTable.getRow(0).getCell("seq").setValue(Boolean.TRUE);
			this.kdtNewTable.getRow(1).getCell("seq").setValue(Boolean.FALSE);
		}
	}

	private void addCustomerRow(FDCCustomerInfo customer) {
		if (customer == null) {
			return;
		}
		int activeRowIndex = this.kdtNewTable.getSelectManager().getActiveRowIndex();
		IRow row = null;
		if (activeRowIndex == -1) {
			row = this.kdtNewTable.addRow();
		} else {
			row = this.kdtNewTable.addRow(activeRowIndex + 1);
		}

		setCustomerRowData(customer, row);

		row.setHeight(20);
		PurchaseChangeCusomerEntryInfo purChgCus = new PurchaseChangeCusomerEntryInfo();
		purChgCus.setCustomer(customer);
		row.setUserObject(purChgCus);
		if (this.kdtNewTable.getRowCount() == 1) {
			row.getCell("propertyPercent").setValue(new BigDecimal("100"));
		}
	}

	private void setCustomerRowData(FDCCustomerInfo customer, IRow row) {
		if(row.getRowIndex() == 0){
			row.getCell("seq").setValue(Boolean.TRUE);
		}else{
			row.getCell("seq").setValue(Boolean.FALSE);
		}
		if (customer != null) {
			row.getCell("customer").setValue(customer);
			//增加id值  by zgy 
			row.getCell("id").setValue(customer.getId().toString());
			row.getCell("phone").setValue(customer.getPhone());
			row.getCell("postalcode").setValue(customer.getPostalcode());
			row.getCell("certificateName").setValue(customer.getCertificateName());
			row.getCell("certificateNumber").setValue(customer.getCertificateNumber());
			row.getCell("mailaddress").setValue(customer.getMailAddress());
			row.getCell("bookDate").setValue(customer.getCreateTime());
		} else {
			row.getCell("phone").setValue(null);
			row.getCell("postalcode").setValue(null);
			row.getCell("certificateName").setValue(null);
			row.getCell("certificateNumber").setValue(null);
			row.getCell("mailaddress").setValue(null);
			row.getCell("bookDate").setValue(null);
		}
	}

	protected void tblCustomerInfo_editStopped(KDTEditEvent e) throws Exception {
		super.tblCustomerInfo_editStopped(e);
		if (e.getColIndex() == 2) {
			IRow row = this.kdtNewTable.getRow(e.getRowIndex());
//			根据id查客户
			FDCCustomerInfo customer = null;
			if(row.getCell("id").getValue()!=null){
				String id  = row.getCell("id").getValue().toString();
				try {
					customer  = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e2) {
					e2.printStackTrace();
				}
			}
//			FDCCustomerInfo customer = (FDCCustomerInfo) row.getCell("customer").getValue();
			setCustomerRowData(customer, row);
		}
	}

	protected void btnDelete_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.kdtNewTable.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.kdtNewTable.getRowCount();
		}
		this.kdtNewTable.removeRow(activeRowIndex);
		if(this.kdtNewTable.getRowCount()>=1 && activeRowIndex == 0) {
			this.kdtNewTable.getRow(0).getCell("seq").setValue(Boolean.TRUE);
//			this.kdtNewTable.getRow(1).getCell("seq").setValue(Boolean.FALSE);
		}
		
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_ADDNEW.equals(this.oprtState)) {
			this.kdtNewTable.getStyleAttributes().setLocked(false);
		} else if (STATUS_EDIT.equals(this.oprtState)) {
			this.kdtNewTable.getStyleAttributes().setLocked(false);
		} else if (STATUS_VIEW.equals(this.oprtState)) {
			this.kdtNewTable.getStyleAttributes().setLocked(true);
		} else if (STATUS_FINDVIEW.equals(this.oprtState)) {
			this.kdtNewTable.getStyleAttributes().setLocked(true);
		}
	}

	protected KDTable getDetailTable() {
		// TODO 自动生成方法存根
		return null;
	}
	
	//上移客户新信息
	protected void btnUp_actionPerformed(ActionEvent e) throws Exception {
		IRow selectRow = KDTableUtil.getSelectedRow(this.kdtNewTable);
		if(selectRow == null) return;
		if(selectRow.getRowIndex() == 0){
			MsgBox.showInfo("已是第一条客户信息！");
			return;
		}
		this.kdtNewTable.moveRow(selectRow.getRowIndex(), selectRow.getRowIndex()-1);
		KDTableUtil.setSelectedRow(this.kdtNewTable,selectRow.getRowIndex()-1);
		if(selectRow.getRowIndex()==1) {
			kdtNewTable.getRow(1).getCell("seq").setValue(Boolean.FALSE);
			kdtNewTable.getRow(0).getCell("seq").setValue(Boolean.TRUE);
		}
	}
	
	//下移新客户信息
	protected void btnDown_actionPerformed(ActionEvent e) throws Exception {
		IRow selectRow = KDTableUtil.getSelectedRow(this.kdtNewTable);
		if(selectRow == null) return;
		if(selectRow.getRowIndex() == (kdtNewTable.getRowCount()-1)){
			MsgBox.showInfo("已是最后一条客户信息！");
			return;
		}
//		这条语句对下移没用啊，不要用哈
//		this.kdtNewTable.moveRow(selectRow.getRowIndex(), selectRow.getRowIndex()+1);
		kdtNewTable.removeRow(selectRow.getRowIndex());
		kdtNewTable.addRow(selectRow.getRowIndex()+1, selectRow);
		KDTableUtil.setSelectedRow(this.kdtNewTable, selectRow.getRowIndex()+1);
		if(selectRow.getRowIndex() == 0){
			this.kdtNewTable.getRow(1).getCell("seq").setValue(Boolean.FALSE);
			this.kdtNewTable.getRow(0).getCell("seq").setValue(Boolean.TRUE);
		}
	}
	protected void btnSelectRoom_actionPerformed(ActionEvent e)
			throws Exception {
		
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");

		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sellProject != null && sellProject.getId() != null) {
			sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(sellProject.getId().toString())));
		}
		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo, buildUnit, MoneySysTypeEnum.SalehouseSys, null, sellProject);
		if (room == null) {
			return;
		}
		verifyAddNewRoom(room);
		if (room != null) {
			this.txtRoomNumber.setUserObject(room);
			this.txtRoomNumber.setText(room.getNumber());
			if(room.getSellOrder()!=null){
				this.txtSellOrder.setText(room.getSellOrder().getName());
			}
			this.txtProjectName.setText(room.getBuilding().getSellProject().getName());
			this.txtProjectName.setUserObject(sellProject);
			if (room.getBuilding().getSubarea() != null) {
				this.txtSubarea.setText(room.getBuilding().getSubarea().getName());
			}
			this.txtBuilding.setText(room.getBuilding().getName());
			this.spiUnit.setValue(new Integer(room.getBuildUnit() == null ? 0 : room.getBuildUnit().getSeq()));
			this.f7RoomModel.setValue(room.getRoomModel());
			this.txtBuildingArea.setText(FDCHelper.divide(room.getBuildingArea(), new BigDecimal("1")).toString());
			this.txtRoomArea.setText(FDCHelper.divide(room.getRoomArea(), new BigDecimal("1")).toString());
		}
		initCustomerTable(room);
		
		this.editData.setCreator(userInfo);
		this.editData.setBizDate(new Date());
		this.editData.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		this.editData.setOrgUnit(SysContext.getSysContext().getCurrentOrgUnit().castToFullOrgUnitInfo());
		this.editData.setBookedDate(new Date());
		this.editData.setState(FDCBillStateEnum.SAVED);
		
	}
	/*
	 * 从交易客户表取数，展示最新的数据到界面上。by zgy 2010-12-15
	 * 
	 */
	public void showNewTable(String id,KDTable table,String type) throws EASBizException, BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("certificateName.id");
		view.getSelector().add("certificateName.number");
		view.getSelector().add("certificateName.name");
		FilterInfo filter=new FilterInfo();
		if(type.equals("purchase")){
			filter.getFilterItems().add(new FilterItemInfo("parent",id,CompareType.EQUALS));
		}else
			if(type.equals("oldPurchase")){
				filter.getFilterItems().add(new FilterItemInfo("oldPurCustomer",id,CompareType.EQUALS));
			}else
				if(type.equals("newPurchase")){
					filter.getFilterItems().add(new FilterItemInfo("purchase",id,CompareType.EQUALS));
				}else{
					return ;
				}
		view.setFilter(filter);
		PurCustomerCollection coll = PurCustomerFactory.getRemoteInstance().getPurCustomerCollection(view);
		if(coll.size()!=0){
			sortBySeq_purcustomer(coll);
			showTable(table,coll);
		}
	}
	public void showTable(KDTable table,PurCustomerCollection coll){
		boolean isMain = true ;
		table.removeRows();
		for (int i = 0; i < coll.size(); i++) {
			PurCustomerInfo pCinfo  = coll.get(i);
			if(pCinfo!=null){
				IRow row = table.addRow();
				row.getCell("seq").setValue(isMain?Boolean.TRUE:Boolean.FALSE);
				isMain = false;
				row.getCell("propertyPercent").setValue(pCinfo.getPropertyPercent()); 
				row.getCell("des").setValue(pCinfo.getDescription());
				//给性别赋值
				row.getCell("customer").setValue(pCinfo.getCustomerName());
				row.getCell("id").setValue(pCinfo.getCustomerID());
				row.getCell("postalcode").setValue(pCinfo.getPostalcode());
				row.getCell("phone").setValue(pCinfo.getTel());
				row.getCell("certificateName").setValue(pCinfo.getCertificateName());
				row.getCell("certificateNumber").setValue(pCinfo.getCertificateNumber());
				row.getCell("mailaddress").setValue(pCinfo.getMailAddress());
				row.getCell("bookDate").setValue(pCinfo.getCreateTime());
			}
		}
	}
	private void initCustomerTable(RoomInfo room) throws EASBizException, BOSException{
	
		PurchaseInfo pi =room.getLastPurchase();
		SelectorItemCollection selector  = new SelectorItemCollection();
		selector.add("*");
		selector.add("salesman.*");
		PurchaseInfo purchaseinfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(pi.getId()), selector);
		this.purchaseInfo =purchaseinfo;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("customer.*");
		view.getSelector().add("head.id");
		view.getSelector().add("propertyPercent");
		view.getSelector().add("seq");
		//new add by renliang at 2011-3-3
		view.getSelector().add("customer.certificateName.id");
		view.getSelector().add("customer.certificateName.number");
		view.getSelector().add("customer.certificateName.name");
		view.getSelector().add("customer.certificateName.customerType");
		view.getSelector().add("customer.certificateName.description");
		
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id",pi.getId(),CompareType.EQUALS));
		view.setFilter(filter);
		PurchaseCustomerInfoCollection purchasecustomerinfo =PurchaseCustomerInfoFactory.getRemoteInstance().getPurchaseCustomerInfoCollection(view);
		// 客户信息
		PurchaseChangeCusomerEntryCollection cusomerEntryCollection = new PurchaseChangeCusomerEntryCollection();
		for (int i = 0;purchasecustomerinfo!= null && i < purchasecustomerinfo.size(); i++) {
			PurchaseCustomerInfoInfo purchaseCustomerInfoInfo = purchasecustomerinfo.get(i);
			PurchaseChangeCusomerEntryInfo changeCusomerEntryInfo = new PurchaseChangeCusomerEntryInfo();
			changeObjectValue(purchaseCustomerInfoInfo, changeCusomerEntryInfo);
			cusomerEntryCollection.add(changeCusomerEntryInfo);
		}
		

		PurchaseChangeCusomerOldEntryCollection changeCusomerOldEntryCollection = new PurchaseChangeCusomerOldEntryCollection();
		for (int i = 0; purchasecustomerinfo!= null && i < purchasecustomerinfo.size(); i++) {
			PurchaseCustomerInfoInfo purchaseCustomerInfoInfo = purchasecustomerinfo.get(i);
			PurchaseChangeCusomerOldEntryInfo changeCusomerOldEntryInfo = new PurchaseChangeCusomerOldEntryInfo();
			changeObjectValue(purchaseCustomerInfoInfo, changeCusomerOldEntryInfo);
			changeCusomerOldEntryCollection.add(changeCusomerOldEntryInfo);
		}
		initCustomerInfo(cusomerEntryCollection, this.kdtOldTable);
		showNewTable(pi.getId().toString(), kdtOldTable, "purchase");
		kdtOldTable.setEnabled(false);
		initCustomerInfo(changeCusomerOldEntryCollection, this.kdtNewTable);
//		showNewTable(pi.getId().toString(), kdtNewTable, "purchase");
		initNewTableColumn();
		kdtNewTable.getColumn("seq").getStyleAttributes().setLocked(true);
		this.editData.setPurchase(pi);
		this.editData.getOldCustomerEntry().clear();
		this.editData.getOldCustomerEntry().addCollection(changeCusomerOldEntryCollection);
		
	}
	private void verifyAddNewRoom(RoomInfo room) {
		if(!RoomSellStateEnum.SIGN_VALUE.equals(room.getSellState().getValue())&&!RoomSellStateEnum.PURCHASE_VALUE.equals(room.getSellState().getValue())&&!RoomSellStateEnum.PREPURCHASE_VALUE.equals(room.getSellState().getValue())){
			FDCMsgBox.showInfo("未产生交易的房间，不可进行更名操作!");
			this.abort();
		}
	}
	
	//点击关联到对应的认购单  eric_wang
	public void actionRelationPurchase_actionPerformed(ActionEvent e)
			throws Exception {
		String purchaseId=null;
		if(purchaseInfo!=null){
			purchaseId =purchaseInfo.getId().toString();
			IUIFactory uiFactory = null;
			UIContext newContext = new UIContext(this);
			newContext.put(UIContext.ID, purchaseId);
			newContext.put("isReversion", Boolean.TRUE);
			uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow curDialog = uiFactory.create(PurchaseEditUI.class.getName(), newContext, null, OprtState.VIEW);
			curDialog.show();
		}else{
			MsgBox.showInfo("请先选择房间");
			this.abort();
		}
	}
	
	/**
	 * @author tim_gao
	 * @see 增加查询客户信息按钮
	 * @param e
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void btnSearchOld_actionPerformed(java.awt.event.ActionEvent e) throws EASBizException, BOSException{
	      //write your code here
 		 int index = this.kdtOldTable.getSelectManager().getActiveRowIndex();
		 IRow row = this.kdtOldTable.getRow(index);
		 String id  = null;
		 FDCCustomerInfo customerInfo = null;
		 if(row == null){
			 MsgBox.showWarning("请选择行!");
		 }
		 else{
			 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			 if(row.getCell("id").getValue()!=null){
				 id = row.getCell("id").getValue().toString();
				 customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
			 }else{
				 customerInfo = (FDCCustomerInfo)row.getCell("customer").getValue();
			 }	
		 
		 String customerId = customerInfo.getId().toString();
	
		 UIContext uiContext = new UIContext(this);
		 uiContext.put(UIContext.ID,  customerId);
		 uiContext.put("CustomerView",  "CustomerView");
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			} catch (UIException ee) {
				logger.error(ee.getMessage()+"获得客户信息失败！");
			}
		 }	
	}
	/**
	 * @author tim_gao
	 * @see 增加查询客户信息按钮
	 * @param e
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public void btnSearchNew_actionPerformed(java.awt.event.ActionEvent e) throws EASBizException, BOSException{
	      //write your code here
 		 int index = this.kdtNewTable.getSelectManager().getActiveRowIndex();
		 IRow row = this.kdtNewTable.getRow(index);
		 String id  = null;
		 FDCCustomerInfo customerInfo = null;
		 if(row == null){
			 MsgBox.showWarning("请选择行!");
		 }
		 else{
			 //by zgy 替换原来逻辑，根据分录id列来取客户数，从交易表中取数，不从客户资料取数，防止客户资料与交易表同步更新
			 if(row.getCell("id").getValue()!=null){
				 id = row.getCell("id").getValue().toString();
				 customerInfo = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(id));
			 }else{
				 customerInfo = (FDCCustomerInfo)row.getCell("customer").getValue();
			 }
		 
		 String customerId = customerInfo.getId().toString();
	
		 UIContext uiContext = new UIContext(this);
		 uiContext.put(UIContext.ID,  customerId);
		 uiContext.put("CustomerView",  "CustomerView");
		 if(customerInfo.getCustomerType().equals(CustomerTypeEnum.PersonalCustomer)){
			 uiContext.put("isPerson",  "true");
		 }else{
			 uiContext.put("isPerson",  "false");
		 }
		 uiContext.put("CustomerView",  "CustomerView");
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create (CustomerEditUI.class.getName(), uiContext, null, OprtState.VIEW);
				uiWindow.show();
			} catch (UIException ee) {
				logger.error(ee.getMessage()+"获得客户信息失败！");
			}
		 }	
	}
}