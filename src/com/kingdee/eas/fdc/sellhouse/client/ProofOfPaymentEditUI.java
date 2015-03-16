/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.EventListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.ProofOfPaymentCollection;
import com.kingdee.eas.fdc.sellhouse.ProofOfPaymentFactory;
import com.kingdee.eas.fdc.sellhouse.ProofOfPaymentInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class ProofOfPaymentEditUI extends AbstractProofOfPaymentEditUI {

	private static final Logger logger = CoreUIObject.getLogger(ProofOfPaymentEditUI.class);

	public void actionPaymentPrint_actionPerformed(ActionEvent e) throws Exception {
		ProofOfPaymentInfo info = (ProofOfPaymentInfo) this.editData;
		if (info != null && info.getId()!=null && info.getNumber() != null) {
			String payMentId = info.getId().toString();
			PaymentPrintDataProvider data = new PaymentPrintDataProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.print("/bim/fdc/sellhouse/ProofOfPayment", data, javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPaymentPrint_actionPerformed(e);
		} else {
			PurchaseInfo purchInfo = (PurchaseInfo) this.prmtPurchaseBill.getValue();
			if (purchInfo != null) {
				FilterInfo pruchBillFilter = new FilterInfo();
				pruchBillFilter.appendFilterItem("purchaseBill.id", purchInfo.getId().toString());
				if (ProofOfPaymentFactory.getRemoteInstance().exists(pruchBillFilter)) {
					String payMentId = purchInfo.getId().toString();
					PaymentPrintDataProvider data = new PaymentPrintDataProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentQuery"));
					com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
					appHlp.printPreview("/bim/fdc/sellhouse/ProofOfPayment", data, javax.swing.SwingUtilities.getWindowAncestor(this));
					super.actionPaymentPrint_actionPerformed(e);
				}else{
					MsgBox.showInfo("没有数据，请检查！");
					SysUtil.abort();
				}
			}else{
				MsgBox.showInfo("没有数据，请检查！");
				SysUtil.abort();
			}
		}
	}

	public void actionPaymentPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		ProofOfPaymentInfo info = (ProofOfPaymentInfo) this.editData;
		if (info != null && info.getId()!=null) {
			String payMentId = info.getId().toString();
			PaymentPrintDataProvider data = new PaymentPrintDataProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentQuery"));
			com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
			appHlp.printPreview("/bim/fdc/sellhouse/ProofOfPayment", data, javax.swing.SwingUtilities.getWindowAncestor(this));
			super.actionPaymentPrintPreview_actionPerformed(e);
		} else {
			PurchaseInfo purchInfo = (PurchaseInfo) this.prmtPurchaseBill.getValue();
			if (purchInfo != null) {
				FilterInfo pruchBillFilter = new FilterInfo();
				pruchBillFilter.appendFilterItem("purchaseBill.id", purchInfo.getId().toString());
				if (ProofOfPaymentFactory.getRemoteInstance().exists(pruchBillFilter)) {
					String payMentId = purchInfo.getId().toString();
					PaymentPrintDataProvider data = new PaymentPrintDataProvider(payMentId, new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.ProofOfPaymentQuery"));
					com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
					appHlp.printPreview("/bim/fdc/sellhouse/ProofOfPayment", data, javax.swing.SwingUtilities.getWindowAncestor(this));
					super.actionPaymentPrintPreview_actionPerformed(e);
				}else{
					MsgBox.showInfo("没有数据，请检查！");
					SysUtil.abort();
				}
			}else{
				MsgBox.showInfo("没有数据，请检查！");
				SysUtil.abort();
			}
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		CoreBaseCollection coreBol = new CoreBaseCollection();
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("purchaseBill.*");
		sel.add("purchaseBill.sellProject.*");
		sel.add("purchaseBill.room.*");
		sel.add("purchaseBill.room.building.*");
		sel.add("purchaseBill.room.buildUnit.*");
		sel.add("purchaseBill.room.buildingFloor.*");
		sel.add("purchaseBill.room.lastSignContract.*");

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("contractNumber", null, CompareType.IS));
		filter.getFilterItems().add(new FilterItemInfo("roomDesc", null, CompareType.IS));
		filter.setMaskString("#0 OR #1");
		view.setSelector(sel);
		// view.setFilter(filter);
		ProofOfPaymentCollection payCol = ProofOfPaymentFactory.getRemoteInstance().getProofOfPaymentCollection(view);
		for (int i = 0; i < payCol.size(); i++) {
			ProofOfPaymentInfo info = payCol.get(i);
			if (info != null) {
				PurchaseInfo purchase = info.getPurchaseBill();
				if (purchase != null) {
					info.setContractTotalPrice(FDCHelper.toBigDecimal(purchase.getContractTotalAmount()));
					SellProjectInfo sellProject = purchase.getSellProject();
					RoomInfo room = purchase.getRoom();
					if (sellProject != null && room != null) {
						String desc = "";
						BuildingInfo building = room.getBuilding();
						BuildingUnitInfo buildUnit = room.getBuildUnit();
						BuildingFloorEntryInfo buildingFloor = room.getBuildingFloor();
						RoomSignContractInfo signContract = room.getLastSignContract();
						if (sellProject != null) {
							desc = sellProject.getName() + " ";
						}
						if (building != null && building.getName() != null) {
							desc = desc + building.getName() + " ";
						}
						if (buildUnit != null && buildUnit.getName() != null) {
							desc = desc + buildUnit.getName() + " ";
						}

						if (buildingFloor != null && buildingFloor.getName() != null) {
							desc = desc + buildingFloor.getName() + "层  ";
						} else {
							desc = desc + room.getFloor() + "层 ";
						}
						desc = desc + room.getNumber() + "房 ";
						info.setRoomDesc(desc);
						if (signContract != null && signContract.getContractNumber() != null) {
							info.setContractNumber(signContract.getContractNumber());
						}
						coreBol.add(info);
					}
				}
			}
		}

		if (coreBol.size() > 0) {
			ProofOfPaymentFactory.getRemoteInstance().update(coreBol);
			MsgBox.showInfo("付清证明的合同号和所购楼宇补写成功！");
		} else {
			MsgBox.showInfo("没有相关数据，无法补写！");
		}
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {

		if (this.txtNumber.getText() == null || this.txtNumber.getText().toString().trim() == "") {
			MsgBox.showInfo("证明序号不能为空！");
			this.txtNumber.setFocusable(true);
			SysUtil.abort();
		}
		if (this.txtNumber.getText().length() < 1) {
			MsgBox.showInfo("证明序号不能为空！");
			this.txtNumber.setFocusable(true);
			SysUtil.abort();
		}
		PurchaseInfo purchInfo = (PurchaseInfo) this.prmtPurchaseBill.getValue();
		if (purchInfo == null) {
			MsgBox.showInfo("还没设置认购单！");
			this.prmtPurchaseBill.setFocusable(true);
			SysUtil.abort();
		}
		boolean isPaidOff = false;
		if (purchInfo != null) {
			FilterInfo pruchBillFilter = new FilterInfo();
			pruchBillFilter.appendFilterItem("purchaseBill.id", purchInfo.getId().toString());
			if (ProofOfPaymentFactory.getRemoteInstance().exists(pruchBillFilter)) {
				isPaidOff = true;
				if (!this.getOprtState().equals(OprtState.EDIT)) {
					MsgBox.showInfo("该认购单已有付清证明！");
					this.txtNumber.setFocusable(true);
					SysUtil.abort();
				}
			}
		}

		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("number", this.txtNumber.getText().toString().trim());

		if (ProofOfPaymentFactory.getRemoteInstance().exists(filter)) {
			if (isPaidOff) {
				MsgBox.showInfo("没有修改，无须保存！");
				SysUtil.abort();
			} else {
				MsgBox.showInfo("已经存在该证明序号！");
				this.txtNumber.setFocusable(true);
				SysUtil.abort();
			}

		}

		if (super.getActionFromActionEvent(e) == null && !(super.checkClickTime())) {
			return;
		}
		IObjectPK pk = null;
		super.doBeforeSubmit(e);
		if (UtilRequest.isPrepare("ActionSubmit", this)) {
			IUIActionPostman post = prepareSubmit();
			if (post != null) {
				post.callHandler();
			}
		}
		pk = super.runSubmit();
		if (pk == null) {
			return;
		}
		purchInfo.setIsPaidOff(true);
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("isPaidOff");
		PurchaseFactory.getRemoteInstance().updatePartial(purchInfo, sel);
		if (OprtState.ADDNEW.equals(getOprtState())) {
			// int currentIndex =idList.getCurrentIndex() + 1;
			int size = idList.size();
			idList.add(size, pk.toString());
			// idList.setCurrentIndex(currentIndex);
		}
		setOprtState(OprtState.EDIT);
		setDataObject(getValue(pk));

		// 为业务系统使用当前暂存后的editData来使用。2005-11-14 by psu_s
		showMessageForStatus();
		showSubmitSuccess();
		// 要在getValue后，工作流会用到值对象中的数据
		if (wfContext.isBindWorkFlow()) {
			wfContext.addToDataMap(pk.toString(), editData);
		}
		setSave(true);
		actionCopy.setEnabled(true);
		loadFields();
		initOldData(this.editData);

		this.btnAddNew.setEnabled(true);
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnSave.setVisible(false);
		this.btnSubmit.setVisible(false);
		this.btnSave.setEnabled(false);
		this.btnSubmit.setEnabled(false);
		this.menuItemAddNew.setVisible(true);
		this.menuItemAddNew.setEnabled(true);
		SHEHelper.setTextFormat(this.txtContractTotalPrice);

		ProofOfPaymentInfo info = (ProofOfPaymentInfo) this.editData;
		if (info != null&&info.getId()!=null) {
			this.actionPaymentPrint.setEnabled(true);
			this.actionPaymentPrintPreview.setEnabled(true);
			this.btnPaymentPrint.setEnabled(true);
			this.btnPaymentPrintPreview.setEnabled(true);
		} else {
			this.actionPaymentPrint.setEnabled(false);
			this.actionPaymentPrintPreview.setEnabled(false);
			this.btnPaymentPrint.setEnabled(false);
			this.btnPaymentPrintPreview.setEnabled(false);
		}

	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	protected void prmtPurchaseBill_dataChanged(DataChangeEvent e) throws Exception, EASBizException, BOSException, UuidException {
		PurchaseInfo purchaseInfo = (PurchaseInfo) e.getNewValue();
		if (purchaseInfo == null) {
			return;
		}
		this.txtNumber.setText("");
		this.txtCustomerName.setText("");
		this.txtContractTotalPrice.setText("");
		this.txtRoomDesc.setText("");
		this.txtContractNumber.setText("");
		String purchseBillId = purchaseInfo.getId().toString();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("*");
		sel.add("sellProject.*");
		sel.add("room.*");
		sel.add("room.building.*");
		sel.add("room.buildUnit.*");
		sel.add("room.buildingFloor.*");
		sel.add("room.lastSignContract.*");

		String roomDesc = "";
		purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchseBillId)), sel);
		if (purchaseInfo != null) {
			if (purchaseInfo.getSellProject() != null) {
				roomDesc = purchaseInfo.getSellProject().getName();
			}
			this.txtCustomerName.setText(purchaseInfo.getCustomerNames());
			this.txtContractTotalPrice.setValue(purchaseInfo.getContractTotalAmount());
//			this.txtContractTotalPrice.setText("" + purchaseInfo.getContractTotalAmount());
			RoomInfo roomInfo = purchaseInfo.getRoom();
			if (roomInfo != null) {
				if (roomInfo.getBuilding() != null && roomInfo.getBuilding().getName() != null) {
					roomDesc = roomDesc + roomInfo.getBuilding().getName() + " ";
				}
				if (roomInfo.getBuildUnit() != null && roomInfo.getBuildUnit().getName() != null) {
					roomDesc = roomDesc + roomInfo.getBuildUnit().getName() + " ";
				}

				if (roomInfo.getBuildingFloor() != null && roomInfo.getBuildingFloor().getFloorAlias() != null) {
					roomDesc = roomDesc + roomInfo.getBuildingFloor().getFloorAlias() + "层 ";
				} else {
					roomDesc = roomDesc + roomInfo.getFloor() + "层 ";
				}
				if (roomInfo.getNumber() != null) {
					roomDesc = roomDesc + roomInfo.getNumber() + "房 ";
				}
				this.txtRoomDesc.setText(roomDesc);
				if (roomInfo.getLastSignContract() != null) {
					RoomSignContractInfo signInfo = roomInfo.getLastSignContract();
					if (signInfo != null && signInfo.getContractNumber() != null) {
						this.txtContractNumber.setText("" + signInfo.getContractNumber());
					}
				}
				if (purchaseInfo.getNumber() != null) {
					this.txtNumber.setText(purchaseInfo.getNumber());
				}
			}

		}

	}

	public void onLoad() throws Exception {
		super.onLoad();
		initControl();
		prmtPurchaseBillSetFilter();
		if (this.getOprtState().equals(OprtState.VIEW)) {
			this.btnSubmit.setEnabled(false);
			this.menuItemSubmit.setEnabled(false);
		} else if (this.getOprtState().equals(OprtState.EDIT)) {
			this.prmtPurchaseBill.setEnabled(false);
		}
		ProofOfPaymentInfo info = (ProofOfPaymentInfo) this.editData;
		if (info != null && info.getNumber() != null) {
			this.actionPaymentPrint.setEnabled(true);
			this.actionPaymentPrintPreview.setEnabled(true);
			this.btnPaymentPrint.setEnabled(true);
			this.btnPaymentPrintPreview.setEnabled(true);
		} else {
			this.actionPaymentPrint.setEnabled(false);
			this.actionPaymentPrintPreview.setEnabled(false);
			this.btnPaymentPrint.setEnabled(false);
			this.btnPaymentPrintPreview.setEnabled(false);
		}

	}

	/**
	 * output class constructor
	 */
	public ProofOfPaymentEditUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		ProofOfPaymentInfo info = (ProofOfPaymentInfo) this.editData;
		if (info != null) {
			if (this.txtNumber != null && this.txtNumber.getText() != null) {
				info.setNumber(this.txtNumber.getText());
			}
			PurchaseInfo purchase = (PurchaseInfo) this.prmtPurchaseBill.getValue();
			if (purchase != null) {
				info.setPurchaseBill(purchase);
			}
			if (txtContractNumber != null && txtContractNumber.getText() != null) {
				info.setContractNumber(txtContractNumber.getText());
			}
			if (txtContractTotalPrice != null && txtContractTotalPrice.getBigDecimalValue() != null) {
				info.setContractTotalPrice(FDCHelper.toBigDecimal(txtContractTotalPrice.getBigDecimalValue()));
			}
			if (txtCustomerName != null && txtCustomerName.getText() != null) {
				info.setCustomerName(txtCustomerName.getText());
			}
			if (txtRoomDesc != null && txtRoomDesc.getText() != null) {
				info.setRoomDesc(txtRoomDesc.getText());
			}
		}
	}

	public void loadFields() {
		EventListener[] listeners = this.prmtPurchaseBill.getListeners(DataChangeListener.class);
		for (int i = 0; i < listeners.length; i++) {
			this.prmtPurchaseBill.removeDataChangeListener((DataChangeListener) listeners[i]);
		}
		super.loadFields();
		for (int i = 0; i < listeners.length; i++) {
			this.prmtPurchaseBill.addDataChangeListener((DataChangeListener) listeners[i]);
		}
	}

	protected IObjectValue createNewData() {
		this.initControl();
		ProofOfPaymentInfo info = new ProofOfPaymentInfo();
		String sourceName = (String) this.getUIContext().get("sourceName");
		if (sourceName != null && sourceName.endsWith("purchase")) {
			String purchseBillId = (String) this.getUIContext().get("ID");
			SelectorItemCollection sel = new SelectorItemCollection();
			sel.add("*");
			sel.add("sellProject.*");
			sel.add("room.*");
			sel.add("room.building.*");
			sel.add("room.buildUnit.*");
			sel.add("room.buildingFloor.*");
			sel.add("room.lastSignContract.*");
			this.txtCustomerName.setText("");
			this.txtContractTotalPrice.setText("");
			this.txtRoomDesc.setText("");
			this.txtContractNumber.setText("");
			if (purchseBillId != null && purchseBillId != "") {
				try {
					String roomDesc = "";
					PurchaseInfo purchaseInfo = PurchaseFactory.getRemoteInstance().getPurchaseInfo(new ObjectUuidPK(BOSUuid.read(purchseBillId)), sel);
					if (purchaseInfo != null) {
						if (purchaseInfo.getSellProject() != null) {
							roomDesc = purchaseInfo.getSellProject().getName() + " ";
						}
						info.setPurchaseBill(purchaseInfo);
						info.setCustomerName(purchaseInfo.getCustomerNames());
						info.setContractTotalPrice(purchaseInfo.getContractTotalAmount());
						RoomInfo roomInfo = purchaseInfo.getRoom();
						if (roomInfo != null) {
							if (roomInfo.getBuilding() != null && roomInfo.getBuilding().getName() != null) {
								roomDesc = roomDesc + roomInfo.getBuilding().getName() + " ";
							}
							if (roomInfo.getBuildUnit() != null && roomInfo.getBuildUnit().getName() != null) {
								roomDesc = roomDesc + roomInfo.getBuildUnit().getName() + " ";
							}

							if (roomInfo.getBuildingFloor() != null && roomInfo.getBuildingFloor().getFloorAlias() != null) {
								roomDesc = roomDesc + roomInfo.getBuildingFloor().getFloorAlias() + "层 ";
							} else {
								roomDesc = roomDesc + roomInfo.getFloor() + "层 ";
							}
							if (roomInfo.getNumber() != null) {
								roomDesc = roomDesc + roomInfo.getNumber() + "房 ";
							}
							info.setRoomDesc(roomDesc);
							if (roomInfo.getLastSignContract() != null) {
								RoomSignContractInfo signInfo = roomInfo.getLastSignContract();
								if (signInfo != null && signInfo.getContractNumber() != null) {
									info.setContractNumber(signInfo.getContractNumber());
								}
							}
							if (purchaseInfo.getNumber() != null) {
								info.setNumber(purchaseInfo.getNumber());
							}
						}
					}
				} catch (EASBizException e) {
					handleException(e);
					e.printStackTrace();
				} catch (BOSException e) {
					handleException(e);
					e.printStackTrace();
				} catch (UuidException e) {
					handleException(e);
					e.printStackTrace();
				}
			}
		}

		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return ProofOfPaymentFactory.getRemoteInstance();
	}

	protected void initControl() {
		this.btnAddNew.setEnabled(true);
		this.btnAttachment.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnCopy.setVisible(false);
		this.btnFirst.setVisible(false);
		this.btnLast.setVisible(false);
		this.btnNext.setVisible(false);
		this.btnPageSetup.setVisible(false);
		this.btnPre.setVisible(false);
		this.btnRemove.setVisible(false);

		String usenaumber = SysContext.getSysContext().getCurrentUserInfo().getNumber();
		if ("lilan_li".equals(usenaumber)) {
			this.btnSave.setVisible(true);
			this.btnSave.setEnabled(true);
		} else {
			this.btnSave.setVisible(false);
			this.btnSave.setEnabled(false);
		}
		this.btnSave.setText("补写合同号或所购楼宇");
		this.btnSave.setToolTipText("补写合同号或所购楼宇");
		this.btnSubmit.setVisible(true);
		this.btnSubmit.setEnabled(true);
		this.menuItemAddNew.setVisible(true);
		this.menuItemAddNew.setEnabled(true);
		SHEHelper.setTextFormat(this.txtContractTotalPrice);
	}

	protected void prmtPurchaseBillSetFilter() throws Exception {
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("sellProject");
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.PURCHASEAUDIT_VALUE));
		if (sellProject != null) {
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject.getId().toString()));
		}
		view.setFilter(filter);
		this.prmtPurchaseBill.setEntityViewInfo(view);
	}
}