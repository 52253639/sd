/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.TranLinkEnum;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class RoomBizInfoUI extends AbstractRoomBizInfoUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomBizInfoUI.class);

	/**
	 * output class constructor
	 */
	public RoomBizInfoUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public void loadFields() {
		super.loadFields();
		
		pkPurchaseDate.setValue(null);
		pkSignDate.setValue(null);
		
		txtTotalAmount.setHorizontalAlignment(JTextField.RIGHT);
		txtTotalAmount.setRemoveingZeroInDispaly(false);
		txtTotalAmount.setPrecision(2);
		txtTotalAmount.setSupportedEmpty(true);
		
		String batchNumber = null;
		if(this.getUIContext().get("batchNumber") != null){
			batchNumber = (String)this.getUIContext().get("batchNumber");
		}
		try {
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("id");
			selector.add("number");
			selector.add("building.id");
			selector.add("building.number");
			selector.add("building.name");
			selector.add("building.sellProject.id");
			selector.add("building.sellProject.number");
			selector.add("building.sellProject.name");
			selector.add("buildingArea");
			selector.add("roomArea");
			selector.add("actualBuildingArea");
			selector.add("actualRoomArea");
			RoomInfo room = RoomFactory.getRemoteInstance().getRoomInfo(new ObjectUuidPK(editData.getId()), selector);
			
			f7RoomNumber.setValue(room);
			f7RoomNumber.setText(room.getNumber());
			f7Project.setValue(room.getBuilding().getSellProject());;
			
			EntityViewInfo view = new EntityViewInfo();
			view.getSelector().add("currentLink");
			view.getSelector().add("billId");
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
			filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));

			view.setFilter(filter);
			
			TransactionCollection transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
			if(transactionCol!=null && !transactionCol.isEmpty()){
				TransactionInfo transactionInfo = transactionCol.get(0);
				if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign)){  //签约单
					SelectorItemCollection selCol = new SelectorItemCollection();
					selCol.add("signPayListEntry.moneyDefine.moneyType");
					selCol.add("id");
					selCol.add("number");
					selCol.add("customerNames");
					selCol.add("customerPhone");
					selCol.add("payType.*");
					selCol.add("salesman.*");
					selCol.add("bizDate");
					selCol.add("bizNumber");
					selCol.add("contractTotalAmount");
					selCol.add("srcId");
					
					SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
						.getSignManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()), selCol);
					if(signInfo != null){
						f7CustomName.setText(signInfo.getCustomerNames());
						txtCustomerPhone.setText(signInfo.getCustomerPhone());
						f7PayType.setValue(signInfo.getPayType());
						f7Seller.setValue(signInfo.getSalesman());
						pkSignDate.setValue(signInfo.getBizDate());
						txtContractNumber.setText(signInfo.getBizNumber());
						txtTotalAmount.setValue(signInfo.getContractTotalAmount());
						
						//如果是转签约，则获取转签约前的认购单
						if(signInfo.getSrcId() != null){
							PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
								.getPurchaseManageInfo(new ObjectUuidPK(signInfo.getSrcId().toString()));
							if(purInfo != null){
								pkPurchaseDate.setValue(purInfo.getBizDate());
								txtPurchaseNumber.setText(purInfo.getBizNumber());
							}
						}
					}
				}
				else if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)){  //认购单
					SelectorItemCollection selCol = new SelectorItemCollection();
					selCol.add("id");
					selCol.add("number");
					selCol.add("payType.*");
					selCol.add("salesman.*");
					selCol.add("bizDate");
					selCol.add("bizNumber");
					selCol.add("customerNames");
					selCol.add("customerPhone");
					selCol.add("contractTotalAmount");
					
					PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
						.getPurchaseManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()), selCol);
					if(purInfo.getId() != null){
						f7CustomName.setText(purInfo.getCustomerNames());
						txtCustomerPhone.setText(purInfo.getCustomerPhone());
						f7PayType.setValue(purInfo.getPayType());
						f7Seller.setValue(purInfo.getSalesman());
						pkPurchaseDate.setValue(purInfo.getBizDate());
						txtPurchaseNumber.setText(purInfo.getBizNumber());
						txtTotalAmount.setValue(purInfo.getContractTotalAmount());
					}
				}
			}
			if(batchNumber != null){
				this.txtBatchNumber.setText(batchNumber);
				this.contBatchNumber.setVisible(true);
			}
		} catch (BOSException e) {
			logger.error(e);
		} catch (EASBizException e) {
			logger.error(e);
		}
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	public SellProjectInfo getSellProject() {
		return (SellProjectInfo) this.f7Project.getValue();
	}

	public List getCustomer() {
		List list = new ArrayList();
		Object[] customerList = (Object[]) this.f7CustomName.getUserObject();
		if (customerList.length > 0) {
			for (int i = 0; i < customerList.length; i++) {
				FDCCustomerInfo customerInfo = (FDCCustomerInfo) customerList[i];
				list.add(customerInfo.getId().toString());
			}
		}
		return list;
	}

	public void onLoad() throws Exception {
		super.onLoad();
	}
}