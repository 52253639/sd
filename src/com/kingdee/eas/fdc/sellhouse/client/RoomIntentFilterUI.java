/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.math.BigDecimal;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorListener;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fm.nt.ChequeStatusEnum;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomIntentFilterUI extends AbstractRoomIntentFilterUI {
	private static final Logger logger = CoreUIObject
			.getLogger(RoomIntentFilterUI.class);

	protected ItemAction actionListOnLoad;

	protected ListUI listUI;

	private boolean isLoaded;

	/**
	 * output class constructor
	 */
	public RoomIntentFilterUI() throws Exception {
		super();
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public RoomIntentFilterUI(ListUI listUI, ItemAction actionListOnLoad)
			throws Exception {
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}

	public void clear() {
		this.f7BuildingProperty.setValue(null);
		this.f7RoomModelType.setValue(null);
		this.spiFloorFrom.setValue(new Integer(0));
		this.spiFloorTo.setValue(new Integer(0));
		this.f7Direction.setValue(null);
		//this.txtFace.setText(null);
		this.txtBuildingAreaFrom.setValue(null);
		this.txtBuildingAreaTo.setValue(null);
		this.txtStandardAmountFrom.setValue(null);
		this.txtStandardAmountTo.setValue(null);
		this.chkInit.setSelected(false);
		this.chkOnShow.setSelected(true);
		this.chkPrePurchase.setSelected(false);
		this.chkPurchase.setSelected(false);
		this.chkSign.setSelected(false);
		this.chkKeepDown.setSelected(false);
	}

	public void onLoad() throws Exception {	
		super.onLoad();
		
		//--------------修改BUG302822：只显示第2级的建筑性质-------
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("level", new Integer(2)));
		viewInfo.setFilter(filter);
		this.f7BuildingProperty.setEntityViewInfo(viewInfo);
		//---------------------------------------
		
		this.f7Building.addSelectorListener(new SelectorListener()
		{
			public void willShow(SelectorEvent e)
			{
				setF7BuildingFilter();
			}
		});
		
		SpinnerNumberModel model = new SpinnerNumberModel(0, -200, 200, 1);
		this.spiFloorFrom.setModel(model);
		model = new SpinnerNumberModel(0, -200, 200, 1);
		this.spiFloorTo.setModel(model);
		this.txtBuildingAreaFrom.setRemoveingZeroInDispaly(false);
		this.txtBuildingAreaFrom.setRemoveingZeroInEdit(false);
		this.txtBuildingAreaFrom.setPrecision(2);
		this.txtBuildingAreaFrom.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingAreaFrom.setSupportedEmpty(true);
		this.txtBuildingAreaTo.setRemoveingZeroInDispaly(false);
		this.txtBuildingAreaTo.setRemoveingZeroInEdit(false);
		this.txtBuildingAreaTo.setPrecision(2);
		this.txtBuildingAreaTo.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingAreaTo.setSupportedEmpty(true);
		this.txtStandardAmountFrom.setRemoveingZeroInDispaly(false);
		this.txtStandardAmountFrom.setRemoveingZeroInEdit(false);
		this.txtStandardAmountFrom.setPrecision(2);
		this.txtStandardAmountFrom.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardAmountFrom.setSupportedEmpty(true);
		this.txtStandardAmountTo.setRemoveingZeroInDispaly(false);
		this.txtStandardAmountTo.setRemoveingZeroInEdit(false);
		this.txtStandardAmountTo.setPrecision(2);
		this.txtStandardAmountTo.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardAmountTo.setSupportedEmpty(true);
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
	}

	private void setF7BuildingFilter() {
		if(this.listUI == null){
			return;
		}
		KDTree treeMain = ((RoomIntentListUI)listUI).getTreeMain();
		if(treeMain == null){
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if(node == null){
			return;
		}
		FilterInfo filter = new FilterInfo();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo project = (SellProjectInfo) node
					.getUserObject();
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", project.getId()
							.toString()));
		} else {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", null));
		}
		view.setFilter(filter);
		//防止更之前的过滤条件相叠加
		f7Building.getQueryAgent().setDefaultFilterInfo(null);
		f7Building.getQueryAgent().setHasCUDefaultFilter(false);
		f7Building.getQueryAgent().resetRuntimeEntityView();
		
		this.f7Building.setEntityViewInfo(view);
	}
	
	public FilterInfo getFilterInfo() {
		CustomerParams para = this.getCustomerParams();
		FilterInfo filter = new FilterInfo();
		String buildingId = para.getCustomerParam("building");
		if (buildingId != null) {
			filter.getFilterItems().add(new FilterItemInfo("building.id", buildingId));
		}
		
		String buildingPropertyId = para.getCustomerParam("buildingProperty");
		if (buildingPropertyId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("buildingProperty.id",
							buildingPropertyId));
		}
		String modelTypeId = para.getCustomerParam("modelType");
		if (modelTypeId != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("roomModel.roomModelType.id",
							modelTypeId));
		}
		String directionId = para.getCustomerParam("direction");
		if(directionId != null)
		{
			filter.getFilterItems().add(
					new FilterItemInfo("direction.id",
							directionId));
		}
		//String face = this.txtFace.getText();
//		if (!StringUtils.isEmpty(face)) {
//			filter.getFilterItems().add(
//					new FilterItemInfo("face", "%" + face + "%",
//							CompareType.LIKE));
//		}
		if (para.getCustomerParamsHashMap().containsKey("floorFrom")) {
			int floorFrom = para.getInt("floorFrom");
			if (floorFrom != 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("floor", new Integer(floorFrom),
								CompareType.GREATER_EQUALS));
			}
		}
		if (para.getCustomerParamsHashMap().containsKey("floorTo")) {
			int floorTo = para.getInt("floorTo");
			if (floorTo != 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("floor", new Integer(floorTo),
								CompareType.LESS_EQUALS));
			}
		}
		if (para.getCustomerParamsHashMap().containsKey("buildingAreaFrom")) {
			BigDecimal buildingAreaFrom = new BigDecimal(para
					.getDouble("buildingAreaFrom"));
			filter.getFilterItems().add(
					new FilterItemInfo("buildingArea", buildingAreaFrom,
							CompareType.GREATER_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("buildingAreaTo")) {
			BigDecimal buildingAreaTo = new BigDecimal(para
					.getDouble("buildingAreaTo"));
			filter.getFilterItems().add(
					new FilterItemInfo("buildingArea", buildingAreaTo,
							CompareType.LESS_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("standardAmountFrom")) {
			BigDecimal standardAmountFrom = new BigDecimal(para
					.getDouble("standardAmountFrom"));
			filter.getFilterItems().add(
					new FilterItemInfo("standardTotalAmount",
							standardAmountFrom, CompareType.GREATER_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("standardAmountTo")) {
			BigDecimal standardAmountTo = new BigDecimal(para
					.getDouble("standardAmountTo"));
			filter.getFilterItems().add(
					new FilterItemInfo("standardTotalAmount", standardAmountTo,
							CompareType.LESS_EQUALS));
		}
		if (!this.chkInit.isSelected()) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("sellState",
									RoomSellStateEnum.INIT_VALUE,
									CompareType.NOTEQUALS));
		}
		if (!this.chkOnShow.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellState",
							RoomSellStateEnum.ONSHOW_VALUE,
							CompareType.NOTEQUALS));
		}
		if (!this.chkPrePurchase.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellState",
							RoomSellStateEnum.PREPURCHASE_VALUE,
							CompareType.NOTEQUALS));
		}
		if (!this.chkPurchase.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellState",
							RoomSellStateEnum.PURCHASE_VALUE,
							CompareType.NOTEQUALS));
		}
		if (!this.chkSign.isSelected()) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("sellState",
									RoomSellStateEnum.SIGN_VALUE,
									CompareType.NOTEQUALS));
		}
		if (!this.chkKeepDown.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellState",
							RoomSellStateEnum.KEEPDOWN_VALUE,
							CompareType.NOTEQUALS));
		}
		return filter;
	}

	public CustomerParams getCustomerParams() {
		CustomerParams para = new CustomerParams();
		
		BuildingInfo building = (BuildingInfo) this.f7Building.getValue();
		if(building != null){
			para.addCustomerParam("building", building.getId()
					.toString());
		}
		
		BuildingPropertyInfo buildingPro = (BuildingPropertyInfo) this.f7BuildingProperty
				.getValue();
		if (buildingPro != null) {
			para.addCustomerParam("buildingProperty", buildingPro.getId()
					.toString());
		}
		RoomModelTypeInfo modelType = (RoomModelTypeInfo) this.f7RoomModelType
				.getValue();
		if (modelType != null) {
			para.addCustomerParam("modelType", modelType.getId().toString());
		}
		HopedDirectionInfo directionInfo = (HopedDirectionInfo)this.f7Direction.getValue();
		if(directionInfo !=null)
		{
			para.addCustomerParam("direction", directionInfo.getId().toString());
		}
		//String face = this.txtFace.getText();
//		if (!StringUtils.isEmpty(face)) {
//			para.addCustomerParam("face", face);
//		}
		Integer floorFrom = (Integer) spiFloorFrom.getValue();
		if (floorFrom.intValue() != 0) {
			para.putInt("floorFrom", floorFrom.intValue());
		}
		Integer floorTo = (Integer) spiFloorTo.getValue();
		if (floorTo.intValue() != 0) {
			para.putInt("floorTo", floorTo.intValue());
		}
		BigDecimal buildingAreaFrom = this.txtBuildingAreaFrom
				.getBigDecimalValue();
		if (buildingAreaFrom != null) {
			para.putDouble("buildingAreaFrom", buildingAreaFrom.doubleValue());
		}
		BigDecimal buildingAreaTo = this.txtBuildingAreaTo.getBigDecimalValue();
		if (buildingAreaTo != null) {
			para.putDouble("buildingAreaTo", buildingAreaTo.doubleValue());
		}
		BigDecimal standardAmountFrom = this.txtStandardAmountFrom
				.getBigDecimalValue();
		if (standardAmountFrom != null) {
			para.putDouble("standardAmountFrom", standardAmountFrom
					.doubleValue());
		}
		BigDecimal standardAmountTo = this.txtStandardAmountTo
				.getBigDecimalValue();
		if (standardAmountTo != null) {
			para.putDouble("standardAmountTo", standardAmountTo.doubleValue());
		}
		para.putBoolean("isIncludeInit", this.chkInit.isSelected());
		para.putBoolean("isIncludeOnShow", this.chkOnShow.isSelected());
		para.putBoolean("isIncludePrePurchase", this.chkPrePurchase
				.isSelected());
		para.putBoolean("isIncludePurchase", this.chkPurchase.isSelected());
		para.putBoolean("isIncludeSign", this.chkSign.isSelected());
		para.putBoolean("isIncludeKeepDown", this.chkKeepDown.isSelected());
		return para;
	}

	public void setCustomerParams(CustomerParams para) {
		if (para == null)
			return;
		String buildingId = para.getCustomerParam("building");
		if (buildingId != null) {
			BuildingInfo building = null;
			try {
				building = BuildingFactory.getRemoteInstance()
						.getBuildingInfo(new ObjectUuidPK(BOSUuid.read(buildingId)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.f7BuildingProperty.setValue(building);
		}
		
		String buildingProId = para.getCustomerParam("buildingProperty");
		if (buildingProId != null) {
			BuildingPropertyInfo buildingPro = null;
			try {
				buildingPro = BuildingPropertyFactory.getRemoteInstance()
						.getBuildingPropertyInfo(
								new ObjectUuidPK(BOSUuid.read(buildingProId)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.f7BuildingProperty.setValue(buildingPro);
		}

		String modelTypeId = para.getCustomerParam("modelType");
		if (modelTypeId != null) {
			RoomModelTypeInfo modelType = null;
			try {
				modelType = RoomModelTypeFactory.getRemoteInstance()
						.getRoomModelTypeInfo(
								new ObjectUuidPK(BOSUuid.read(modelTypeId)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (modelType != null) {
				this.f7RoomModelType.setValue(modelType);
			}
		}
		String directionId = para.getCustomerParam("direction");
		if(directionId !=null)
		{
			HopedDirectionInfo  directionInfo = null;
			try {
				directionInfo = HopedDirectionFactory.getRemoteInstance()
				                .getHopedDirectionInfo(new ObjectUuidPK(BOSUuid.read(directionId)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (directionInfo != null) {
				this.f7RoomModelType.setValue(directionInfo);
			}
		}
//		String face = para.getCustomerParam("face");
//		if (!StringUtils.isEmpty(face)) {
//			this.txtFace.setText(face);
//		}
		int floorFrom = para.getInt("floorFrom");
		this.spiFloorFrom.setValue(new Integer(floorFrom));
		int floorTo = para.getInt("floorTo");
		this.spiFloorTo.setValue(new Integer(floorTo));
		if (para.getCustomerParamsHashMap().containsKey("buildingAreaFrom")) {
			BigDecimal buildingAreaFrom = new BigDecimal(para
					.getDouble("buildingAreaFrom"));
			this.txtBuildingAreaFrom.setValue(buildingAreaFrom);
		}
		if (para.getCustomerParamsHashMap().containsKey("buildingAreaTo")) {
			BigDecimal buildingAreaTo = new BigDecimal(para
					.getDouble("buildingAreaTo"));
			this.txtBuildingAreaTo.setValue(buildingAreaTo);
		}
		if (para.getCustomerParamsHashMap().containsKey("standardAmountFrom")) {
			BigDecimal standardAmountFrom = new BigDecimal(para
					.getDouble("standardAmountFrom"));
			this.txtBuildingAreaFrom.setValue(standardAmountFrom);
		}
		if (para.getCustomerParamsHashMap().containsKey("standardAmountTo")) {
			BigDecimal standardAmountTo = new BigDecimal(para
					.getDouble("standardAmountTo"));
			this.txtBuildingAreaTo.setValue(standardAmountTo);
		}
		this.chkInit.setSelected(para.getBoolean("isIncludeInit"));
		this.chkOnShow.setSelected(para.getBoolean("isIncludeOnShow"));
		this.chkPrePurchase
				.setSelected(para.getBoolean("isIncludePrePurchase"));
		this.chkPurchase.setSelected(para.getBoolean("isIncludePurchase"));
		this.chkSign.setSelected(para.getBoolean("isIncludeSign"));
		this.chkKeepDown.setSelected(para.getBoolean("isIncludeKeepDown"));
	}

	public boolean verify()
	{
		int from = this.spiFloorFrom.getIntegerVlaue().intValue();
		int to = this.spiFloorTo.getIntegerVlaue().intValue();
		
		if(from > to)
		{
			MsgBox.showWarning("起始楼层不能大于结束楼层！");
			return false;
		}
		
		return true;
	}
	
}