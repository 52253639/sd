/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionFactory;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class RoomIntentFilterUI extends AbstractRoomIntentFilterUI
{
	private static final Logger logger = CoreUIObject.getLogger(RoomIntentFilterUI.class);
	
	protected ItemAction actionListOnLoad;

	protected ListUI listUI;

	private boolean isLoaded;

	
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
		
		this.txtRoomAreaFrom.setValue(null);
		this.txtRoomAreaTo.setValue(null);
		this.txtStandardRentFrom.setValue(null);
		this.txtStandardRentTo.setValue(null);
		this.chkUnTenancy.setSelected(false);
		this.chkWaitTenancy.setSelected(true);
		this.chkHavaTenancy.setSelected(false);
//		this.chkNewTenancy.setSelected(false);
//		this.chkContinueTenancy.setSelected(false);
		this.chkSincerObligate.setSelected(false);
		this.chkKeepTenancy.setSelected(false);
	}
//	保持与房间定义时层级的选择一致，去掉了限定条件层级为2    xiaoao_liu
	public void onLoad() throws Exception {	
		super.onLoad();
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(new FilterItemInfo("level", new Integer(2)));
		viewInfo.setFilter(filter);
		this.f7BuildingProperty.setEntityViewInfo(viewInfo);
		SpinnerNumberModel model = new SpinnerNumberModel(0, 0, 200, 1);
		this.spiFloorFrom.setModel(model);
		model = new SpinnerNumberModel(0, 0, 200, 1);
		this.spiFloorTo.setModel(model);
		model = new SpinnerNumberModel(30, 0, 1000, 1);
		this.spiMutrueDate.setModel(model);
		this.txtRoomAreaFrom.setRemoveingZeroInDispaly(false);
		this.txtRoomAreaFrom.setRemoveingZeroInEdit(false);
		this.txtRoomAreaFrom.setPrecision(2);
		this.txtRoomAreaFrom.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomAreaFrom.setSupportedEmpty(true);
		this.txtRoomAreaTo.setRemoveingZeroInDispaly(false);
		this.txtRoomAreaTo.setRemoveingZeroInEdit(false);
		this.txtRoomAreaTo.setPrecision(2);
		this.txtRoomAreaTo.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomAreaTo.setSupportedEmpty(true);
		this.txtStandardRentFrom.setRemoveingZeroInDispaly(false);
		this.txtStandardRentFrom.setRemoveingZeroInEdit(false);
		this.txtStandardRentFrom.setPrecision(2);
		this.txtStandardRentFrom.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardRentFrom.setSupportedEmpty(true);
		this.txtStandardRentTo.setRemoveingZeroInDispaly(false);
		this.txtStandardRentTo.setRemoveingZeroInEdit(false);
		this.txtStandardRentTo.setPrecision(2);
		this.txtStandardRentTo.setHorizontalAlignment(JTextField.RIGHT);
		this.txtStandardRentTo.setSupportedEmpty(true);
		this.chkIsIncludeMatrueRoom.setVisible(false);
		this.contMutrueDate.setVisible(false);
		
		if (!isLoaded) {
			this.clear();
		}
//		if(this.chkWaitTenancy.isSelected())
//		{
//			this.chkIsIncludeMatrueRoom.setVisible(true);
//			this.contMutrueDate.setVisible(true);
//		}
		isLoaded = true;
	}
	
	public FilterInfo getFilterInfo() {
		CustomerParams para = this.getCustomerParams();
		FilterInfo filter = new FilterInfo();
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
		if (para.getCustomerParamsHashMap().containsKey("roomAreaFrom")) {
			BigDecimal roomAreaFrom = new BigDecimal(para
					.getDouble("roomAreaFrom"));
			filter.getFilterItems().add(
					new FilterItemInfo("roomArea", roomAreaFrom,
							CompareType.GREATER_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("roomAreaTo")) {
			BigDecimal roomAreaTo = new BigDecimal(para
					.getDouble("roomAreaTo"));
			filter.getFilterItems().add(
					new FilterItemInfo("roomArea", roomAreaTo,
							CompareType.LESS_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("standardRentFrom")) {
			BigDecimal standardRentFrom = new BigDecimal(para
					.getDouble("standardRentFrom"));
			filter.getFilterItems().add(
					new FilterItemInfo("standardTotalRent",
							standardRentFrom, CompareType.GREATER_EQUALS));
		}
		if (para.getCustomerParamsHashMap().containsKey("standardRentTo")) {
			BigDecimal standardRentTo = new BigDecimal(para
					.getDouble("standardRentTo"));
			filter.getFilterItems().add(
					new FilterItemInfo("standardTotalRent", standardRentTo,
							CompareType.LESS_EQUALS));
		}
		if (!this.chkUnTenancy.isSelected()) {
			filter.getFilterItems()
					.add(
							new FilterItemInfo("tenancyState",
									TenancyStateEnum.UNTENANCY_VALUE,
									CompareType.NOTEQUALS));
		}
		if (!this.chkWaitTenancy.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",
							TenancyStateEnum.WAITTENANCY_VALUE,
							CompareType.NOTEQUALS));
		}
		///* TODO 这里请去掉已租的过滤 by zhicheng_jin
		if (!this.chkHavaTenancy.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",
							TenancyStateEnum.NEWTENANCY_VALUE,
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",
							TenancyStateEnum.CONTINUETENANCY_VALUE,
							CompareType.NOTEQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",
							TenancyStateEnum.ENLARGETENANCY_VALUE,
							CompareType.NOTEQUALS));
		}
		
//		if (!this.chkNewTenancy.isSelected()) {
//			filter.getFilterItems().add(
//					new FilterItemInfo("tenancyState",
//							TenancyStateEnum.NEWTENANCY_VALUE,
//							CompareType.NOTEQUALS));
//		}
//		if (!this.chkContinueTenancy.isSelected()) {
//			filter.getFilterItems()
//					.add(
//							new FilterItemInfo("tenancyState",
//									TenancyStateEnum.CONTINUETENANCY_VALUE,
//									CompareType.NOTEQUALS));
//		}
		if(!this.chkSincerObligate.isSelected())
		{
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",
							TenancyStateEnum.SINCEROBLIGATE_VALUE,
							CompareType.NOTEQUALS));
		}
		if (!this.chkKeepTenancy.isSelected()) {
			filter.getFilterItems().add(
					new FilterItemInfo("tenancyState",
							TenancyStateEnum.KEEPTENANCY_VALUE,
							CompareType.NOTEQUALS));
		}
		if(this.chkIsIncludeMatrueRoom.isSelected())
		{
			Date date = FDCDateHelper.getDayBegin();
			Integer mutrueDate = (Integer)this.spiMutrueDate.getValue();
			EntityViewInfo viewInfo1 = new EntityViewInfo();
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("tenancy.tenancyState", TenancyBillStateEnum.Executing,
					                     CompareType.EQUALS));
			filter1.getFilterItems().add(new FilterItemInfo("tenancy.startDate", date,
                    CompareType.LESS_EQUALS));
			filter1.getFilterItems().add(new FilterItemInfo("tenancy.endDate", TenancyHelper.addCalendar(date, Calendar.DATE, mutrueDate.intValue()),
                    CompareType.GREATER_EQUALS));
			viewInfo1.setFilter(filter1);
			List idList = new ArrayList();
			try {
				TenancyRoomEntryCollection tenColl = TenancyRoomEntryFactory.getRemoteInstance().getTenancyRoomEntryCollection(viewInfo1);
				for(int i=0;i<tenColl.size();i++)
				{
					TenancyRoomEntryInfo tenancyRoomInfo = (TenancyRoomEntryInfo)tenColl.get(i);
					idList.add(tenancyRoomInfo.getRoom().getId().toString());
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
//			filter.getFilterItems().add(new FilterItemInfo("id",FDCHelper.list2Set(idList),
//							CompareType.INCLUDE));
		}
		return filter;
	}
	
	public CustomerParams getCustomerParams() {
		CustomerParams para = new CustomerParams();
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
		BigDecimal roomAreaFrom = this.txtRoomAreaFrom
				.getBigDecimalValue();
		if (roomAreaFrom != null) {
			para.putDouble("roomAreaFrom", roomAreaFrom.doubleValue());
		}
		BigDecimal roomAreaTo = this.txtRoomAreaTo.getBigDecimalValue();
		if (roomAreaTo != null) {
			para.putDouble("roomAreaTo", roomAreaTo.doubleValue());
		}
		BigDecimal standardRentFrom = this.txtStandardRentFrom
				.getBigDecimalValue();
		if (standardRentFrom != null) {
			para.putDouble("standardRentFrom", standardRentFrom
					.doubleValue());
		}
		BigDecimal standardRentTo = this.txtStandardRentTo
				.getBigDecimalValue();
		if (standardRentTo != null) {
			para.putDouble("standardRentTo", standardRentTo.doubleValue());
		}
		Integer mutrueDate = (Integer)this.spiMutrueDate.getValue();
		if(mutrueDate.intValue() != 0)
		{
			para.putInt("mutrueDate",mutrueDate.intValue());
		}
		para.putBoolean("isUnTenancy", this.chkUnTenancy.isSelected());
		para.putBoolean("isWaitTenancy", this.chkWaitTenancy.isSelected());
		para.putBoolean("isHavaTenancy", this.chkHavaTenancy
				.isSelected());
//		para.putBoolean("isNewTenancy", this.chkNewTenancy.isSelected());
//		para.putBoolean("isContinueTenancy", this.chkContinueTenancy.isSelected());
		para.putBoolean("isSincerObligate", this.chkSincerObligate.isSelected());
		para.putBoolean("isKeepTenancy", this.chkKeepTenancy.isSelected());
		para.putBoolean("isIncludeMatrueRoom",this.chkIsIncludeMatrueRoom.isSelected());
		return para;
	}

	public void setCustomerParams(CustomerParams para) {
		if (para == null)
			return;
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
		if (para.getCustomerParamsHashMap().containsKey("roomAreaFrom")) {
			BigDecimal roomAreaFrom = new BigDecimal(para
					.getDouble("roomAreaFrom"));
			this.txtRoomAreaFrom.setValue(roomAreaFrom);
		}
		if (para.getCustomerParamsHashMap().containsKey("roomAreaTo")) {
			BigDecimal roomAreaTo = new BigDecimal(para
					.getDouble("roomAreaTo"));
			this.txtRoomAreaTo.setValue(roomAreaTo);
		}
		if (para.getCustomerParamsHashMap().containsKey("standardRentFrom")) {
			BigDecimal standardRentFrom = new BigDecimal(para
					.getDouble("standardAmountFrom"));
			this.txtStandardRentFrom.setValue(standardRentFrom);
		}
		if (para.getCustomerParamsHashMap().containsKey("standardRentTo")) {
			BigDecimal standardRentTo = new BigDecimal(para
					.getDouble("standardRentTo"));
			this.txtStandardRentTo.setValue(standardRentTo);
		}
		int mutrueDate = para.getInt("mutrueDate");
		this.spiMutrueDate.setValue(new Integer(mutrueDate));
		this.chkUnTenancy.setSelected(para.getBoolean("isUnTenancy"));
		this.chkWaitTenancy.setSelected(para.getBoolean("isWaitTenancy"));
		this.chkHavaTenancy
				.setSelected(para.getBoolean("isHavaTenancy"));
//		this.chkNewTenancy.setSelected(para.getBoolean("isNewTenancy"));
//		this.chkContinueTenancy.setSelected(para.getBoolean("isContinueTenancy"));
		this.chkSincerObligate.setSelected(para.getBoolean("isSincerObligate"));
		this.chkKeepTenancy.setSelected(para.getBoolean("isKeepTenancy"));
		this.chkIsIncludeMatrueRoom.setSelected(para.getBoolean("isIncludeMatrueRoom"));
	}

	protected void chkHavaTenancy_stateChanged(ChangeEvent e) throws Exception {
		super.chkHavaTenancy_stateChanged(e);
		if(this.chkHavaTenancy.isSelected())
		{
			this.chkIsIncludeMatrueRoom.setVisible(true);
			chkIsIncludeMatrueRoom_stateChanged(e);
		}else
		{
			this.chkIsIncludeMatrueRoom.setVisible(false);
			this.contMutrueDate.setVisible(false);
		}
	}
	
//	protected void chkWaitTenancy_stateChanged(ChangeEvent e) throws Exception {
//		super.chkWaitTenancy_stateChanged(e);
//		if(this.chkWaitTenancy.isSelected())
//		{
//			this.chkIsIncludeMatrueRoom.setVisible(true);
//			chkIsIncludeMatrueRoom_stateChanged(e);
//		}else
//		{
//			this.chkIsIncludeMatrueRoom.setVisible(false);
//			this.contMutrueDate.setVisible(false);
//		}
//	}
	
	protected void chkIsIncludeMatrueRoom_stateChanged(ChangeEvent e) throws Exception {
		super.chkIsIncludeMatrueRoom_stateChanged(e);
		if(!this.chkIsIncludeMatrueRoom.isSelected())
		{
			this.contMutrueDate.setVisible(true);
			this.contMutrueDate.setEnabled(false);
		}else
		{
			this.contMutrueDate.setVisible(true);
			this.contMutrueDate.setEnabled(true);
		}
	}
	public void storeFields()
	{
		super.storeFields();
	}
	
}