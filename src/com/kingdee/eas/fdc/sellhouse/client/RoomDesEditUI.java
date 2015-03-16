/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.NoiseInfo;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomDesInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomNumPrefixEnum;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.eas.util.client.SelectorConstant;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomDesEditUI extends AbstractRoomDesEditUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomDesEditUI.class);

	BuildingInfo buildingInfo = null;

	/**
	 * output class constructor
	 */
	public RoomDesEditUI() throws Exception {
		super();
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		IObjectValue objectValue = super.getValue(pk);
		this.buildingInfo = ((RoomDesInfo) objectValue).getBuilding();
		return objectValue;
	}

	public void loadFields() {
		super.loadFields();
		RoomDesInfo roomDes = (RoomDesInfo) this.editData;
		SpinnerNumberModel model = null;
		if (buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			model = new SpinnerNumberModel(1, 1, buildingInfo.getUnitCount(), 1);
			spiUnitFrom.setModel(model);
			model = new SpinnerNumberModel(1, 1, buildingInfo.getUnitCount(), 1);
			spiUnitTo.setModel(model);
		} else if (buildingInfo.getCodingType().equals(CodingTypeEnum.FloorNum)) {
			this.contUnitFrom.setVisible(false);
			this.contUnitTo.setVisible(false);
		} else {
			this.contUnitFrom.setVisible(false);
			this.contUnitTo.setVisible(false);
			this.contFloorFrom.setVisible(false);
			this.contFloorTo.setVisible(false);
		}
		model = new SpinnerNumberModel(0, buildingInfo.getSubFloorCount(), buildingInfo.getFloorCount(), 1);
		spiFloorFrom.setModel(model);
		model = new SpinnerNumberModel(1, buildingInfo.getSubFloorCount(), buildingInfo.getFloorCount(), 1);
		spiFloorTo.setModel(model);
		model = new SpinnerNumberModel(1, 1, 10000, 1);
		spiNumberFrom.setModel(model);
		model = new SpinnerNumberModel(1, 1, 10000, 1);
		spiNumberTo.setModel(model);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("building.id", this.buildingInfo.getId().toString()));
		this.f7RoomModel.setEntityViewInfo(view);
		this.txtBuilding.setText(roomDes.getBuilding().getName());
		this.spiUnitFrom.setValue(new Integer(roomDes.getUnitBegin()));
		this.spiUnitTo.setValue(new Integer(roomDes.getUnitEnd()));
		this.spiFloorFrom.setValue(new Integer(roomDes.getFloorBegin()));
		this.spiFloorTo.setValue(new Integer(roomDes.getFloorEnd()));
		this.spiNumberFrom.setValue(new Integer(roomDes.getSerialNumberBegin()));
		this.spiNumberTo.setValue(new Integer(roomDes.getSerialNumberEnd()));
		this.txtBuildingArea.setValue(roomDes.getBuildingArea());
		this.txtRoomArea.setValue(roomDes.getRoomArea());
		this.txtApportionArea.setValue(roomDes.getApportionArea());
		this.txtBalconyArea.setValue(roomDes.getBalconyArea());
		this.txtFloorHeight.setValue(roomDes.getFloorHeight());
		this.f7Direction.setValue(roomDes.getDirection());
		this.f7Sight.setValue(roomDes.getSight());
		this.f7RoomModel.setValue(roomDes.getRoomModel());
		this.comboNumPrefixType.setSelectedItem(roomDes.getNumPrefixType() == null ? RoomNumPrefixEnum.noBuildPrefix : roomDes.getNumPrefixType());
		this.f7BuildingProperty.setValue(roomDes.getBuildingProperty());

		this.chkIsForSHE.setSelected(roomDes.isIsForSHE());
		this.chkIsForTen.setSelected(roomDes.isIsForTen());
		this.chkIsForPPM.setSelected(roomDes.isIsForPPM());

		this.f7ProductType.setValue(roomDes.getProductType());

		this.comboHouseProperty.setSelectedItem(roomDes.getHouseProperty());
		this.chkIsConvertToChar.setSelected(roomDes.isIsConvertToChar());
		this.txtActualBuildingArea.setValue(roomDes.getActualBuildingArea());
		this.txtActualRoomArea.setValue(roomDes.getActualRoomArea());

		this.f7RoomUsage.setValue(roomDes.getRoomUsage());
		this.f7EyeSihnht.setValue(roomDes.getEyeSignht());
		this.f7DecoraStd.setValue(roomDes.getDecoraStd());
		this.f7Noise.setValue(roomDes.getNoise());
		this.f7PosseStd.setValue(roomDes.getPosseStd());

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if (this.getOprtState().equals("EDIT")) {
			try {
				BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
				RoomCollection rooms = SHEHelper.getRooms(buildingInfo);
				for (int i = 0; i < rooms.size(); i++) {
					RoomInfo room = (RoomInfo) rooms.get(i);
					if (room.isIsAreaAudited() || room.isIsActualAreaAudited()) {
						this.txtBuildingArea.setEditable(false);
						this.txtRoomArea.setEditable(false);
					}
				}
			} catch (BOSException e) {
				this.handleException(e);
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		RoomDesInfo roomDes = (RoomDesInfo) this.editData;

		roomDes.setIsForSHE(this.chkIsForSHE.isSelected());
		roomDes.setIsForTen(this.chkIsForTen.isSelected());
		roomDes.setIsForPPM(this.chkIsForPPM.isSelected());

		roomDes.setUnitBegin(((Integer) spiUnitFrom.getValue()).intValue());
		roomDes.setUnitEnd(((Integer) spiUnitTo.getValue()).intValue());
		roomDes.setFloorBegin(((Integer) spiFloorFrom.getValue()).intValue());
		roomDes.setFloorEnd(((Integer) spiFloorTo.getValue()).intValue());
		roomDes.setSerialNumberBegin(((Integer) spiNumberFrom.getValue()).intValue());
		roomDes.setSerialNumberEnd(((Integer) spiNumberTo.getValue()).intValue());
		roomDes.setBuildingArea(this.txtBuildingArea.getBigDecimalValue());
		roomDes.setRoomArea(this.txtRoomArea.getBigDecimalValue());
		roomDes.setApportionArea(this.txtApportionArea.getBigDecimalValue());
		roomDes.setBalconyArea(this.txtBalconyArea.getBigDecimalValue());
		roomDes.setActualBuildingArea(this.txtActualBuildingArea.getBigDecimalValue());
		roomDes.setActualRoomArea(this.txtActualRoomArea.getBigDecimalValue());
		roomDes.setFloorHeight(this.txtFloorHeight.getBigDecimalValue());
		roomDes.setDirection((HopedDirectionInfo) f7Direction.getValue());
		roomDes.setSight((SightRequirementInfo) f7Sight.getValue());
		roomDes.setRoomModel((RoomModelInfo) this.f7RoomModel.getValue());

		roomDes.setRoomUsage((RoomUsageInfo) this.f7RoomUsage.getValue());
		roomDes.setEyeSignht((EyeSignhtInfo) this.f7EyeSihnht.getValue());
		roomDes.setNoise((NoiseInfo) this.f7Noise.getValue());
		roomDes.setPosseStd((PossessionStandardInfo) this.f7PosseStd.getValue());
		roomDes.setDecoraStd((DecorationStandardInfo) this.f7DecoraStd.getValue());

		roomDes.setNumPrefixType((RoomNumPrefixEnum) this.comboNumPrefixType.getSelectedItem());
		roomDes.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty.getValue());
		roomDes.setProductType((ProductTypeInfo) this.f7ProductType.getValue());

		roomDes.setHouseProperty((HousePropertyEnum) this.comboHouseProperty.getSelectedItem());
		roomDes.setIsConvertToChar(this.chkIsConvertToChar.isSelected());
	}

	protected IObjectValue createNewData() {
		RoomDesInfo roomDesInfo = new RoomDesInfo();
		roomDesInfo.setIsConvertToChar(false);
		this.buildingInfo = (BuildingInfo) this.getUIContext().get("building");

		if (buildingInfo == null || buildingInfo.getId() == null) {
			logger.error("buildingInfo is null.");
			MsgBox.showError(this, "系统错误！");
			this.abort();
		}

		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.isForSHE");
		sels.add("sellProject.isForTen");
		sels.add("sellProject.isForPPM");
		try {
			buildingInfo = BuildingFactory.getRemoteInstance().getBuildingInfo(new ObjectUuidPK(buildingInfo.getId().toString()), sels);
		} catch (EASBizException e) {
			this.handleException(e);
			this.abort();
		} catch (BOSException e) {
			this.handleException(e);
			this.abort();
		}

		roomDesInfo.setBuilding(buildingInfo);
		roomDesInfo.setHouseProperty(HousePropertyEnum.NoAttachment);
		if (buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			roomDesInfo.setUnitBegin(1);
			roomDesInfo.setUnitEnd(1);
			roomDesInfo.setFloorBegin(1);
			roomDesInfo.setFloorEnd(1);
		} else if (buildingInfo.getCodingType().equals(CodingTypeEnum.FloorNum)) {
			roomDesInfo.setUnitBegin(0);
			roomDesInfo.setUnitEnd(0);
			roomDesInfo.setFloorBegin(1);
			roomDesInfo.setFloorEnd(1);
		} else {
			roomDesInfo.setUnitBegin(0);
			roomDesInfo.setUnitEnd(0);
			roomDesInfo.setFloorBegin(1);
			roomDesInfo.setFloorEnd(1);
		}
		roomDesInfo.setSerialNumberBegin(1);
		roomDesInfo.setSerialNumberEnd(1);
		roomDesInfo.setNumPrefixType(RoomNumPrefixEnum.noBuildPrefix);
		return roomDesInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomDesFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		this.storeFields();
		this.initOldData(this.editData);
		setGroupFilter();
	}

	private void setGroupFilter() {
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext().get("projectSelected");
		if (sellProject == null) {
			sellProject = buildingInfo.getSellProject();
			if (sellProject == null) {
				return;
			}
		}
		String spId = sellProject.getId().toString();
		// 设置房间朝向的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7Direction, spId, "房间朝向", "RoomArch");
		// 设置房间景观的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7Sight, spId, "房间景观", "RoomArch");
		// 设置建筑性质的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7BuildingProperty, spId, "建筑性质", "RoomArch");
		// 设置产品类型的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7ProductType, spId, "产品类型", "RoomArch");
		
		SHEHelper.SetGroupFilters(this.f7RoomUsage, spId, "房间用途", "RoomArch");	
		
		SHEHelper.SetGroupFilters(this.f7EyeSihnht, spId, "视野", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7DecoraStd, spId, "装修标准", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7PosseStd, spId, "交房标准", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7Noise, spId, "噪音", "RoomArch");
	}

	private void initControl() throws EASBizException, BOSException, UuidException {
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.txtBuilding.setEnabled(false);
		this.txtBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingArea.setRemoveingZeroInEdit(false);
		this.txtBuildingArea.setNegatived(false);
		this.txtBuildingArea.setPrecision(3);
		this.txtBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingArea.setSupportedEmpty(true);

		this.txtRoomArea.setRemoveingZeroInDispaly(false);
		this.txtRoomArea.setRemoveingZeroInEdit(false);
		this.txtRoomArea.setNegatived(false);
		this.txtRoomArea.setPrecision(3);
		this.txtRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtRoomArea.setSupportedEmpty(true);

		this.txtApportionArea.setRemoveingZeroInDispaly(false);
		this.txtApportionArea.setRemoveingZeroInEdit(false);
		this.txtApportionArea.setNegatived(false);
		this.txtApportionArea.setPrecision(3);
		this.txtApportionArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApportionArea.setSupportedEmpty(true);

		this.txtBalconyArea.setRemoveingZeroInDispaly(false);
		this.txtBalconyArea.setRemoveingZeroInEdit(false);
		this.txtBalconyArea.setNegatived(false);
		this.txtBalconyArea.setPrecision(3);
		this.txtBalconyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBalconyArea.setSupportedEmpty(true);

		this.txtActualBuildingArea.setRemoveingZeroInDispaly(false);
		this.txtActualBuildingArea.setRemoveingZeroInEdit(false);
		this.txtActualBuildingArea.setNegatived(false);
		this.txtActualBuildingArea.setPrecision(3);
		this.txtActualBuildingArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualBuildingArea.setSupportedEmpty(true);

		this.txtActualRoomArea.setRemoveingZeroInDispaly(false);
		this.txtActualRoomArea.setRemoveingZeroInEdit(false);
		this.txtActualRoomArea.setNegatived(false);
		this.txtActualRoomArea.setPrecision(3);
		this.txtActualRoomArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtActualRoomArea.setSupportedEmpty(true);

		this.txtFloorHeight.setRemoveingZeroInDispaly(false);
		this.txtFloorHeight.setRemoveingZeroInEdit(false);
		this.txtFloorHeight.setNegatived(false);
		this.txtFloorHeight.setPrecision(2);
		this.txtFloorHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFloorHeight.setSupportedEmpty(true);

		this.f7RoomModel.setRequired(true);
		this.f7BuildingProperty.setRequired(true);

		// 亿达5.9售楼改动 房间定义和房间中的建筑性质选择不在根据级次过滤,任意选择 20081223
		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); view.setFilter(filter);
		 * 
		 * filter.getFilterItems() .add(new FilterItemInfo("level", new
		 * Integer(2)));
		 * 
		 * this.buildingInfo = (BuildingInfo)
		 * this.getUIContext().get("building"); String proId =
		 * this.buildingInfo.getSellProject().getId().toString();
		 * SellProjectInfo pro = SellProjectFactory.getRemoteInstance()
		 * .getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(proId)));
		 * BuildingPropertyInfo buildingProperty = pro.getBuildingProperty(); if
		 * (buildingProperty != null) { filter.getFilterItems().add( new
		 * FilterItemInfo("parent.id", buildingProperty.getId() .toString())); }
		 * this.f7BuildingProperty.setEntityViewInfo(view);
		 */
	}

	protected void updateAppArea() {
		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		if (buildingArea != null && roomArea != null) {
			BigDecimal appArea = buildingArea.subtract(roomArea);
			if (appArea.compareTo(FDCHelper.ZERO) >= 0) {
				this.txtApportionArea.setValue(appArea);
			} else {
				this.txtApportionArea.setValue(null);
			}
		} else {
			this.txtApportionArea.setValue(null);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("building.*");
		selectors.add("building.sellProject.isForSHE");
		selectors.add("building.sellProject.isForTen");
		selectors.add("building.sellProject.isForPPM");
		// selectors.add("building.rooms.*");
		selectors.add("buildingProperty.*");
		selectors.add("roomModel.*");
		selectors.add("direction.*");
		selectors.add("sight.*");
		selectors.add("productType.*");
		selectors.add("roomUsage.*");
		selectors.add("noise.*");
		selectors.add("decoraStd.*");
		selectors.add("eyeSignht.*");
		selectors.add("posseStd.*");
		return selectors;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		RoomCollection rooms = SHEHelper.getRooms(buildingInfo);
		for (int i = 0; i < rooms.size(); i++) {
			RoomInfo room = (RoomInfo) rooms.get(i);
			if (room.isIsAreaAudited() || room.isIsActualAreaAudited()) {
				this.txtBuildingArea.setEditable(false);
				this.txtRoomArea.setEditable(false);
			}
		}

	}

	protected void verifyInput(ActionEvent e) throws Exception {
		if (this.f7RoomModel.getValue() == null) {
			MsgBox.showInfo("户型不能为空!");
			this.abort();
		}
		if (this.f7BuildingProperty.getValue() == null) {
			MsgBox.showInfo("建筑性质不能为空!");
			this.abort();
		}
		if (this.f7ProductType.getValue() == null) {
			MsgBox.showInfo("产品类型不能为空!");
			this.abort();
		}

		if (!this.chkIsForSHE.isSelected() && !this.chkIsForTen.isSelected() && !this.chkIsForPPM.isSelected()) {
			MsgBox.showInfo(this, "售楼,租赁,物业属性至少选一项!");
			this.abort();
		}

		SellProjectInfo sellPro = buildingInfo.getSellProject();
		if (sellPro == null) {
			MsgBox.showError(this, "系统错误！");
			this.abort();
		}
		if (!sellPro.isIsForSHE() && this.chkIsForSHE.isSelected()) {
			MsgBox.showWarning(this, "该项目没有售楼属性，房间不能大于项目的范围！");
			this.abort();
		}

		if (!sellPro.isIsForTen() && this.chkIsForTen.isSelected()) {
			MsgBox.showWarning(this, "该项目没有租赁属性，房间不能大于项目的范围！");
			this.abort();
		}

		if (!sellPro.isIsForPPM() && this.chkIsForPPM.isSelected()) {
			MsgBox.showWarning(this, "该项目没有物业属性，房间不能大于项目的范围！");
			this.abort();
		}

		BigDecimal buildAreaTxtValue = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal buildingArea = buildAreaTxtValue == null ? FDCHelper.ZERO : buildAreaTxtValue;
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		// 录入建筑面积的情况下，套内面积不允许为空
		if (buildAreaTxtValue != null && roomArea == null) {
			MsgBox.showInfo("请录入套内面积");
			this.abort();
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		// if (buildingArea == null || buildingArea.equals(FDCHelper.ZERO)) {
		// MsgBox.showInfo("建筑面积不能为空!");
		// this.abort();
		// }
		// if (roomArea == null || buildingArea.equals(FDCHelper.ZERO)) {
		// MsgBox.showInfo("套内面积不能为空!");
		// this.abort();
		// }
		BigDecimal appArea = buildingArea.subtract(roomArea);
		if (appArea.compareTo(FDCHelper.ZERO) < 0) {
			MsgBox.showInfo("建筑面积不能小于套内面积!");
			this.abort();
		}
		Integer unitFrom = (Integer) this.spiUnitFrom.getValue();
		Integer unitTo = (Integer) this.spiUnitTo.getValue();
		if (unitFrom.intValue() > unitTo.intValue()) {
			MsgBox.showInfo("单元区间输入错误!");
			this.abort();
		}
		Integer floorFrom = (Integer) this.spiFloorFrom.getValue();
		Integer floorTo = (Integer) this.spiFloorTo.getValue();
		if (floorFrom.intValue() > floorTo.intValue() || (floorFrom.intValue() == 0 && floorTo.intValue() == 0)) {
			MsgBox.showInfo("楼层区间输入错误!");
			this.abort();
		}
		Integer numberFrom = (Integer) this.spiNumberFrom.getValue();
		Integer numberTo = (Integer) this.spiNumberTo.getValue();
		if (numberFrom.intValue() > numberTo.intValue()) {
			MsgBox.showInfo("序号区间输入错误!");
			this.abort();
		}
		if (this.chkIsConvertToChar.isSelected()) {
			if (numberTo.byteValue() > 26) {
				MsgBox.showInfo("字母编码最大到Z,对应序号不能超过26!");
				this.abort();
			}
		}
		super.verifyInput(e);
	}

	protected void txtBuildingArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtBuildingArea_dataChanged(e);
		updateAppArea();
	}

	protected void txtRoomArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtRoomArea_dataChanged(e);
		updateAppArea();
	}
}