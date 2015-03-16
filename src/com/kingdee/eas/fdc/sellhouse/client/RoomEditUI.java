/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;

import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.LanguageManager;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.util.SimpleFileFilter;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.HousePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.NoiseInfo;
import com.kingdee.eas.fdc.sellhouse.PossessionStandardInfo;
import com.kingdee.eas.fdc.sellhouse.PriceAdjustEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ProductDetialInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisCollection;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisFactory;
import com.kingdee.eas.fdc.sellhouse.RoomAreaChangeHisInfo;
import com.kingdee.eas.fdc.sellhouse.RoomAttachmentEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomJoinStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceBillEntryFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomUsageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SightRequirementInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaFactory;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.fi.newrpt.client.designer.io.WizzardIO;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public class RoomEditUI extends AbstractRoomEditUI {
	
	private static Logger logger = Logger
	.getLogger("com.kingdee.eas.fdc.sellhouse.client.RoomEditUI");

	private SellProjectInfo sellProject = null;

	private SubareaInfo subarea = null;

	private BuildingInfo buildingInfo = null;

	SHEImagePanel pnlRoomPic = null;
	protected com.kingdee.bos.ctrl.swing.KDTree treeMain;

	/**
	 * output class constructor
	 */
	public RoomEditUI() throws Exception {
		super();
	}

	protected void txtNumber_KeyReleased(KeyEvent e) throws Exception {
		this.txtRoomPropNo.setText(this.txtNumber.getText());
	}

	private void setF7BuildingFloorFilter(RoomInfo room) {
		BuildingInfo building = room.getBuilding();
		if (building == null)
			return;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));

		this.f7BuildingFloor.setEntityViewInfo(view);
	}

	public void loadFields() {
		this.setImage();
		super.loadFields();

		RoomInfo room = (RoomInfo) this.editData;

		if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			this.prmtBuildUnit.setEnabled(false);
		} else {
			this.prmtBuildUnit.setRequired(true);
		}
		SpinnerNumberModel model = null;
		model = new SpinnerNumberModel(1, 1, buildingInfo.getFloorCount(), 1);
		spiFloor.setModel(model);
		model = new SpinnerNumberModel(1, 1, 10000, 1);
		spiSerialNumber.setModel(model);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("building.id", this.buildingInfo.getId().toString()));
		this.f7RoomModel.setEntityViewInfo(view);
		this.txtCompany.setText(sellProject.getOrgUnit().getName());
		this.txtProjectNumber.setText(sellProject.getNumber());
		this.txtProjectName.setText(sellProject.getName());
		if (subarea != null) {
			this.txtSubarea.setText(subarea.getName());
		}

		this.chkIsForSHE.setSelected(room.isIsForSHE());
		this.chkIsForTen.setSelected(room.isIsForTen());
		this.chkIsForPPM.setSelected(room.isIsForPPM());
		this.txtBuilding.setText(room.getBuilding().getName());
		this.txtNumber.setText(room.getDisplayName());
		this.txtRoomNo.setText(room.getRoomNo());
		this.txtRoomPropNo.setText(room.getRoomPropNo());

		this.spiFloor.setValue(new Integer(room.getFloor()));
		this.spiSerialNumber.setValue(new Integer(room.getSerialNumber()));
		this.txtBuildingArea.setValue(room.getBuildingArea());
		this.txtRoomArea.setValue(room.getRoomArea());
		this.txtApportionArea.setValue(room.getApportionArea());
		this.txtBalconyArea.setValue(room.getBalconyArea());
		this.txtGuardenArea.setValue(room.getGuardenArea());
		this.txtCarbarnArea.setValue(room.getCarbarnArea());
		this.txtUseArea.setValue(room.getUseArea());
		this.txtFlatArea.setValue(room.getFlatArea());
		this.txtActualBuildingArea.setValue(room.getActualBuildingArea());
		this.txtActualRoomArea.setValue(room.getActualRoomArea());
		this.txtFloorHeight.setValue(room.getFloorHeight());
		this.f7Direction.setValue(room.getDirection());
		this.f7Sight.setValue(room.getSight());
		this.f7RoomModel.setValue(room.getRoomModel());
		this.txtDeliverHouseStandard.setText(room.getDeliverHouseStandard());
		this.txtFitmentStandard.setText(room.getFitmentStandard());
		this.f7BuildingProperty.setValue(room.getBuildingProperty());
		this.f7ProductType.setValue(room.getProductType());
		this.f7ProductDetail.setValue(room.getProductDetail());
		if(room.getWindow()!=null){
			this.comboWindow.setSelectedItem(room.getWindow());
		}
		this.txtTenancyArea.setValue(room.getTenancyArea());
		this.f7roomForm.setValue(room.getRoomForm());
		this.comboHouseProperty.setSelectedItem(room.getHouseProperty());
		this.comboRoomState.setSelectedItem(room.getSellState());
		this.comboTenancyState.setSelectedItem(room.getTenancyState());
		this.f7RoomUsage.setValue(room.getRoomUsage());
		this.prmtBuildUnit.setValue(room.getBuildUnit());
		this.f7BuildingFloor.setValue(room.getBuildingFloor());

		this.f7EyeSihnht.setValue(room.getEyeSignht());
		this.f7DecoraStd.setValue(room.getDecoraStd());
		this.f7Noise.setValue(room.getNoise());
		this.f7PosseStd.setValue(room.getPosseStd());

		if (this.getOprtState().equals("EDIT")) {
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
			try {
				if (RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
					comboHouseProperty.setEnabled(false);
				}
			} catch (Exception e) {
				this.handleException(e);
			}
		}
		if (room.getImgData() != null) {
			this.pnlRoomPic.setImageData(room.getImgData());
		} else {
			this.pnlRoomPic.setImageData(null);
			this.txtRoomPic.setText(null);
		}
		if (this.getOprtState().equals("VIEW")) {
			this.btnRoomPic.setVisible(false);
			this.contRoomPic.setVisible(false);
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}

		this.setF7BuildingFloorFilter(room);
		
		loadAreaChangeHis(room);
	}

	/**
	 * 加载房间面积变更历史
	 * add by renliang at 2011-1-17
	 * @param editData--
	 */
	private void loadAreaChangeHis(RoomInfo room) {
		
		if(room==null || room.getId()==null){
			return;
		}
		try {
			EntityViewInfo view = new EntityViewInfo();
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("id"));
			selector.add(new SelectorItemInfo("buildingArea"));
			selector.add(new SelectorItemInfo("roomArea"));
			selector.add(new SelectorItemInfo("actualBuildingArea"));
			selector.add(new SelectorItemInfo("actualRoomArea"));
			selector.add(new SelectorItemInfo("type"));
			selector.add(new SelectorItemInfo("operationTime"));
			
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(
					new FilterItemInfo("head", room.getId().toString(),
							CompareType.EQUALS));
			
			view.setFilter(filterInfo);
			SorterItemCollection sort = new SorterItemCollection();
			SorterItemInfo itemInfo = new SorterItemInfo("operationTime");
			itemInfo.setSortType(SortType.DESCEND);
			sort.add(itemInfo);
			view.setSorter(sort);
			view.setSelector(selector);
			RoomAreaChangeHisCollection coll = RoomAreaChangeHisFactory.getRemoteInstance().getRoomAreaChangeHisCollection(view);
			if(coll!=null && coll.size()>0){
				if(this.tblAreaChageHis.getRowCount()>0){
					this.tblAreaChageHis.removeRows();
				}
				//by tim_gao 变更历史3位小数
				this.tblAreaChageHis.getColumn("buildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));;
				this.tblAreaChageHis.getColumn("roomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
				this.tblAreaChageHis.getColumn("actBuildingArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
				this.tblAreaChageHis.getColumn("actRoomArea").getStyleAttributes()
				.setNumberFormat(FDCHelper.getNumberFtm(3));
				for (int i = 0; i < coll.size(); i++) {
					RoomAreaChangeHisInfo info  = (RoomAreaChangeHisInfo)coll.get(i);
					if(info!=null){
						IRow row = this.tblAreaChageHis.addRow();
						row.getCell("id").setValue(info.getId());
						row.getCell("buildingArea").setValue(info.getBuildingArea());
						row.getCell("roomArea").setValue(info.getRoomArea());
						row.getCell("actBuildingArea").setValue(info.getActualBuildingArea());
						row.getCell("actRoomArea").setValue(info.getActualRoomArea());
						row.getCell("type").setValue(info.getType());
						row.getCell("operationTime").setValue(info.getOperationTime());
					}
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage()+"房间面积变更历史加载失败！");
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = (RoomInfo) this.editData;
		if (!room.getSellState().equals(RoomSellStateEnum.Init) && !room.getSellState().equals(RoomSellStateEnum.OnShow)) {
			MsgBox.showInfo("未推盘或待售房间才能修改!");
			return;
		}
		if(room.getTenancyState()!=null){
			if (!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
					|| room.getTenancyState().equals(TenancyStateEnum.waitTenancy))) {
				MsgBox.showInfo("只有未推盘、未放租、待售或待租的房间才能修改!");
				return;
			}
		}
		super.actionEdit_actionPerformed(e);
		this.btnRoomPic.setVisible(true);
		this.contRoomPic.setVisible(true);
		if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			this.prmtBuildUnit.setEnabled(false);
		}
		if (this.getOprtState().equals("EDIT")) {
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
			if (RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
				comboHouseProperty.setEnabled(false);
			}
		}

		setAreaEditable();
	}

	protected void btnRoomPic_actionPerformed(ActionEvent e) throws Exception {
		super.btnRoomPic_actionPerformed(e);
		KDFileChooser chsFile = new KDFileChooser();
		String JPG = "jpg";
		String Key_File = "Key_File";
		SimpleFileFilter Filter_JPG = new SimpleFileFilter(JPG, "JPG" + LanguageManager.getLangMessage(Key_File, WizzardIO.class, "操作失败"));
		chsFile.addChoosableFileFilter(Filter_JPG);
		int ret = chsFile.showOpenDialog(this);
		if (ret != JFileChooser.APPROVE_OPTION) {
			SysUtil.abort();
		} else {
			File file = chsFile.getSelectedFile();
			if (file == null)
				return;
			String path = file.getPath();
			String fileType = path.substring(path.lastIndexOf(".") + 1, path.length());
			if (JPG.equals(fileType) || fileType.equals("JPG")) {
				this.setImage();
				this.txtRoomPic.setText(file.getPath());
				this.pnlRoomPic.setImageFile(file);
			} else {
				MsgBox.showInfo("格式不正确，请插入正确的图片文件!");
				this.abort();
			}
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		RoomInfo room = (RoomInfo) this.editData;
		room.setNumber(this.txtNumber.getText());
		

		BuildingUnitInfo unitInfo = (BuildingUnitInfo) this.prmtBuildUnit.getValue();
		room.setBuildUnit(unitInfo);
		
		String longName = this.sellProject.getName();
		if(this.subarea!=null) longName += "-" + this.subarea.getName();
		longName += "-" + this.buildingInfo.getName();
		if(unitInfo!=null) longName += "-" + unitInfo.getName(); 
		room.setName(longName + "-" + room.getNumber());		
		room.setDisplayName(this.txtNumber.getText());
		
		if (unitInfo != null) {
			room.setUnit(unitInfo.getSeq());
		}

		// room.setFloor(((Integer) spiFloor.getValue()).intValue()); 已经替换为对象楼层
		room.setSerialNumber(((Integer) spiSerialNumber.getValue()).intValue());
		if (room.getEndSerialNumber() < room.getSerialNumber()) {
			room.setEndSerialNumber(room.getSerialNumber());
		}

		if (this.f7BuildingFloor.getValue() != null) {
			BuildingFloorEntryInfo buildFloorInfo = (BuildingFloorEntryInfo) this.f7BuildingFloor.getValue();
			room.setBuildingFloor(buildFloorInfo);
			room.setFloor(buildFloorInfo.getFloor());
		}

		room.setIsForSHE(this.chkIsForSHE.isSelected());
		room.setIsForTen(this.chkIsForTen.isSelected());
		room.setIsForPPM(this.chkIsForPPM.isSelected());

		room.setBuildingArea(this.txtBuildingArea.getBigDecimalValue());
		room.setRoomArea(this.txtRoomArea.getBigDecimalValue());
		room.setApportionArea(this.txtApportionArea.getBigDecimalValue());
		room.setBalconyArea(this.txtBalconyArea.getBigDecimalValue());
		room.setGuardenArea(this.txtGuardenArea.getBigDecimalValue());
		room.setCarbarnArea(this.txtCarbarnArea.getBigDecimalValue());
		room.setUseArea(this.txtUseArea.getBigDecimalValue());
		room.setFlatArea(this.txtFlatArea.getBigDecimalValue());
		room.setActualBuildingArea(this.txtActualBuildingArea.getBigDecimalValue());
		room.setActualRoomArea(this.txtActualRoomArea.getBigDecimalValue());
		room.setFloorHeight(this.txtFloorHeight.getBigDecimalValue());
		room.setDirection((HopedDirectionInfo) f7Direction.getValue());
		room.setSight((SightRequirementInfo) f7Sight.getValue());
		room.setRoomModel((RoomModelInfo) this.f7RoomModel.getValue());
		room.setDeliverHouseStandard(this.txtDeliverHouseStandard.getText());
		room.setFitmentStandard(this.txtFitmentStandard.getText());
		room.setRoomForm((RoomFormInfo) this.f7roomForm.getValue());
		room.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty.getValue());
		room.setProductType((ProductTypeInfo) this.f7ProductType.getValue());
		room.setProductDetail((ProductDetialInfo) this.f7ProductDetail.getValue());
		room.setHouseProperty((HousePropertyEnum) this.comboHouseProperty.getSelectedItem());
		room.setTenancyState((TenancyStateEnum) this.comboTenancyState.getSelectedItem());
		room.setRoomNo(this.txtRoomNo.getText());
		room.setRoomPropNo(this.txtRoomPropNo.getText());
		if(comboWindow.getSelectedItem()==null || comboWindow.getSelectedItem().equals("")){
			room.setString("window", null);
		}else{
			room.setWindow((RoomWindowsEnum) comboWindow.getSelectedItem());
		}
		room.setTenancyArea(txtTenancyArea.getBigDecimalValue()); 
		room.setRoomUsage((RoomUsageInfo) this.f7RoomUsage.getValue());
		room.setEyeSignht((EyeSignhtInfo) this.f7EyeSihnht.getValue());
		room.setNoise((NoiseInfo) this.f7Noise.getValue());
		room.setPosseStd((PossessionStandardInfo) this.f7PosseStd.getValue());
		room.setDecoraStd((DecorationStandardInfo) this.f7DecoraStd.getValue());

		room.setImgData(this.pnlRoomPic.getImageData());
	}

	private void setImage() {
		pnlRoomPic = new SHEImagePanel();
		this.scPanel.setViewportView(this.pnlRoomPic);
	}

	protected IObjectValue createNewData() {
		this.buildingInfo = (BuildingInfo) this.getUIContext().get("building");
		try {
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("project.fullOrgUnit.name");
			this.sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSellProject().getId().toString())), sels);
			if (this.buildingInfo.getSubarea() != null) {
				this.subarea = SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSubarea().getId().toString())));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		RoomInfo roomInfo = new RoomInfo();
		roomInfo.setSellState(RoomSellStateEnum.Init);
		roomInfo.setHouseProperty(HousePropertyEnum.NoAttachment);
		if (buildingInfo != null) {
			roomInfo.setBuilding(buildingInfo);
			if (CodingTypeEnum.UnitFloorNum.equals(buildingInfo.getCodingType())) {
				BuildingUnitInfo unitInfo = (BuildingUnitInfo) this.getUIContext().get("unit");
				if (unitInfo != null) {
					roomInfo.setBuildUnit(unitInfo);
					this.prmtBuildUnit.setValue(unitInfo);
				}
			}
		}
		roomInfo.setFloor(1);
		roomInfo.setSerialNumber(1);

		// add by yaowei_wen 初始化房间的销售项目租售属性2009-06-09
		// ---------start
		roomInfo.setIsForSHE(sellProject.isIsForSHE());
		roomInfo.setIsForTen(sellProject.isIsForTen());
		roomInfo.setIsForPPM(sellProject.isIsForPPM());
		// ---------end
		return roomInfo;

	}

	protected ICoreBase getBizInterface() throws Exception {
		return RoomFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		this.menuBiz.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.txtCompany.setEnabled(false);
		this.txtProjectName.setEnabled(false);
		this.txtProjectNumber.setEnabled(false);
		this.txtSubarea.setEnabled(false);
		this.txtBuilding.setEnabled(false);
		this.comboTenancyState.setEnabled(false);

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

		this.txtGuardenArea.setRemoveingZeroInDispaly(false);
		this.txtGuardenArea.setRemoveingZeroInEdit(false);
		this.txtGuardenArea.setNegatived(false);
		this.txtGuardenArea.setPrecision(3);
		this.txtGuardenArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtGuardenArea.setSupportedEmpty(true);

		this.txtCarbarnArea.setRemoveingZeroInDispaly(false);
		this.txtCarbarnArea.setRemoveingZeroInEdit(false);
		this.txtCarbarnArea.setNegatived(false);
		this.txtCarbarnArea.setPrecision(3);
		this.txtCarbarnArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtCarbarnArea.setSupportedEmpty(true);

		this.txtUseArea.setRemoveingZeroInDispaly(false);
		this.txtUseArea.setRemoveingZeroInEdit(false);
		this.txtUseArea.setNegatived(false);
		this.txtUseArea.setPrecision(3);
		this.txtUseArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtUseArea.setSupportedEmpty(true);

		this.txtFlatArea.setRemoveingZeroInDispaly(false);
		this.txtFlatArea.setRemoveingZeroInEdit(false);
		this.txtFlatArea.setNegatived(false);
		this.txtFlatArea.setPrecision(3);
		this.txtFlatArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFlatArea.setSupportedEmpty(true);

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
		this.txtFloorHeight.setPrecision(3);
		this.txtFloorHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtFloorHeight.setSupportedEmpty(true);
		super.onLoad();
		this.menuSubmitOption.setVisible(false);
		this.rMenuItemSubmitAndAddNew.setVisible(false);
		this.rMenuItemSubmitAndPrint.setVisible(false);
		this.txtNumber.setRequired(true);

		this.txtRoomPropNo.setRequired(true);
		this.txtRoomPropNo.setMaxLength(40);

		this.spiFloor.setRequired(true);
		this.spiSerialNumber.setRequired(true);
		this.f7RoomModel.setRequired(true);
		this.f7BuildingProperty.setRequired(true);
		this.f7ProductDetail.setRequired(false);
		this.f7RoomUsage.setRequired(false);// add By yaowei_wen
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("level", new Integer(2)));
		BuildingPropertyInfo buildingProperty = this.sellProject.getBuildingProperty();
		RoomInfo room = (RoomInfo) this.editData;

		// --如果已经帮定了附属房产的非附属房产,其房产性质不能修改 zhicheng_jin 090223 ---
		if (room.getAttachmentEntry() != null && !room.getAttachmentEntry().isEmpty()) {
			this.comboHouseProperty.setEnabled(false);
		}

		if (OprtState.EDIT.equalsIgnoreCase(this.getOprtState())) {
			if (RoomJoinStateEnum.JOINED.equals(room.getRoomJoinState())) {
				this.txtRoomNo.setEnabled(false);
			} else {
				this.txtRoomNo.setEnabled(true);
			}
			this.txtRoomPropNo.setEnabled(true);// 任何时候都可修改物业房号 luxiaoling
												// 091124
		}

		// ----------
		this.comboWindow.removeAllItems();
		this.comboWindow.addItem("");
		this.comboWindow.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.RoomWindowsEnum").toArray());
		if(getOprtState().equals(this.STATUS_ADDNEW)){
		}else{
			this.comboWindow.setSelectedItem(room.getWindow());
		}
		// 没复核过并且没有楼栋ID的说明是意向查询的房间不能修改
		if (this.getUIContext().get("buildingID") == null) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionAddNew.setVisible(false);
			this.actionEdit.setVisible(false);
		}
		// 没复核过但是有楼栋ID的说明是房间面积录入的房间可以修改
		if (!room.isIsAreaAudited() && this.getUIContext().get("buildingID") != null) {
			this.actionEdit.setEnabled(true);
			this.actionEdit.setVisible(true);
			this.actionAddNew.setEnabled(false);
			this.actionAddNew.setVisible(false);
		}
		if (room.isIsAreaAudited()) {
			this.actionAddNew.setEnabled(false);
			this.actionAddNew.setVisible(false);
		}

		if (room.getBuildingFloor() != null || OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState())) {
			this.lcBuildingFloor.setVisible(true);
			this.contFloor.setVisible(false);
		} else {
			this.lcBuildingFloor.setVisible(false);
			this.contFloor.setVisible(true);
		}

		setAreaEditable();
		this.storeFields();
		this.initOldData(this.editData);

		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}
		this.contRoomState.setVisible(this.chkIsForSHE.isSelected());
		this.contTenancyState.setVisible(this.chkIsForTen.isSelected());

		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);

		// this.setF7ProdcutDetailEntityViewAsOnload();
		// this.setF7RoomUsageEntityViewAsOnload();// add BY yaowei_wen
		// 2009-06-05
		// this.setF7PeoductTypeOnload();// add by yaowei_wen 2009-06-08

		setF7BuildUnitEntityView();
		this.groupFilterOnload();
		this.txtTenancyArea.setRemoveingZeroInDispaly(false);
		this.txtTenancyArea.setRemoveingZeroInEdit(false);
		this.txtTenancyArea.setNegatived(false);
		this.txtTenancyArea.setPrecision(2);
		this.txtTenancyArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtTenancyArea.setSupportedEmpty(true);
		this.txtTenancyArea.setDataType(BigDecimal.class);
		
		this.tblAreaChageHis.getStyleAttributes().setLocked(true);
	}

	private void groupFilterOnload() {
		if (this.sellProject == null)
			return;
		String spId = sellProject.getId().toString();
		// 设置房间景观的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7Sight, spId, "房间景观", "RoomArch");
		// 设置产品描述的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7ProductDetail, spId, "产品描述", "RoomArch");
		// 设置房间朝向的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7Direction, spId, "房间朝向", "RoomArch");
		// 设置建筑性质的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7BuildingProperty, spId, "建筑性质", "RoomArch");
		// //设置户型类别的集团管控过滤
		// SHEHelper.SetGroupFilters(this.f7RoomModel, spId, "户型类别",
		// "RoomArch");
		// 设置房间用途的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7RoomUsage, spId, "房间用途", "RoomArch");
		// 设置产品类型的集团管控过滤
		SHEHelper.SetGroupFilters(this.f7ProductType, spId, "产品类型", "RoomArch");	
		
		SHEHelper.SetGroupFilters(this.f7EyeSihnht, spId, "视野", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7DecoraStd, spId, "装修标准", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7PosseStd, spId, "交房标准", "RoomArch");
		SHEHelper.SetGroupFilters(this.f7Noise, spId, "噪音", "RoomArch");
	}

	// 单元控件过滤条件
	private void setF7BuildUnitEntityView() {
		String buildId = "null";
		if (buildingInfo != null)
			buildId = buildingInfo.getId().toString();
		EntityViewInfo unitView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildId));
		unitView.setFilter(filter);
		this.prmtBuildUnit.setEntityViewInfo(unitView);

		if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			this.contUnit.setEnabled(false);
			this.prmtBuildUnit.setEnabled(false);
		} else {
			this.prmtBuildUnit.setRequired(true);
		}
	}

	/**
	 * 设置产品描述的过滤
	 * 
	 * @author laiquan_luo
	 */
	private void setF7ProdcutDetailEntityViewAsOnload() {
		SellProjectInfo sp = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sp == null)
			return;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);

		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sp.getId().toString()));

		this.f7ProductDetail.setEntityViewInfo(view);
	}

	/**
	 * 设置房间用途的过滤
	 * 
	 * @author yaowei_wen
	 */
	private void setF7RoomUsageEntityViewAsOnload() {
		SellProjectInfo sp = (SellProjectInfo) this.getUIContext().get("sellProject");
		if (sp == null)
			return;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sp.getId().toString()));
		this.f7RoomUsage.setEntityViewInfo(view);
	}

	/**
	 * 设置产品类型的过滤
	 * 
	 * @author yaowei_wen
	 */
	private void setF7PeoductTypeOnload() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		this.f7ProductType.setEntityViewInfo(view);
	}

	/**
	 * 根据面积和实测面积是否复核来设置编辑房间界面的相应面积是否可编辑 面积复核后户型也不让改
	 * */
	protected void setAreaEditable() {
		if (!this.oprtState.equals("EDIT")) {
			return;
		}
		if (this.oprtState.equalsIgnoreCase("EDIT")) {
			this.prmtBuildUnit.setEnabled(false);
			this.spiFloor.setEnabled(false);
			this.f7BuildingFloor.setEnabled(false);
			this.spiSerialNumber.setEnabled(false);
		}
		RoomInfo room = (RoomInfo) this.editData;
		if (room.isIsAreaAudited()) {
			f7RoomModel.setRequired(false);
			f7RoomModel.setReadOnly(true);
			txtBuildingArea.setEditable(false);
			txtRoomArea.setEditable(false);
			this.txtTenancyArea.setEditable(false);
		}
		if (room.isIsActualAreaAudited()) {
			f7RoomModel.setRequired(false);
			f7RoomModel.setReadOnly(true);
			txtActualBuildingArea.setEditable(false);
			txtActualRoomArea.setEditable(false);
		}
		if (room.isIsActualAreaAudited() && room.isIsAreaAudited()) {
			f7RoomModel.setRequired(false);
			f7RoomModel.setReadOnly(true);
			txtBuildingArea.setEditable(false);
			txtRoomArea.setEditable(false);
			this.txtTenancyArea.setEditable(false);
			txtActualBuildingArea.setEditable(false);
			txtActualRoomArea.setEditable(false);
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("building.*");
		selectors.add("buildUnit.*");
		selectors.add("building.sellProject.id");
		selectors.add("building.sellProject.isForSHE");
		selectors.add("building.sellProject.isForTen");
		selectors.add("building.sellProject.isForPPM");
		selectors.add("buildingProperty.*");
		selectors.add("roomModel.*");
		selectors.add("direction.*");
		selectors.add("sight.*");
		selectors.add("roomForm.*");
		selectors.add("productType.*");
		selectors.add("productDetail.*");
		selectors.add("buildingFloor.*");
		selectors.add("roomUsage.*");
		selectors.add("noise.*");
		selectors.add("decoraStd.*");
		selectors.add("eyeSignht.*");
		selectors.add("posseStd.*");
		return selectors;
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		IObjectValue objectValue = super.getValue(pk);
		this.buildingInfo = ((RoomInfo) objectValue).getBuilding();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("orgUnit.name");
		this.sellProject = SellProjectFactory.getRemoteInstance().getSellProjectInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSellProject().getId().toString())), sels);
		if (this.buildingInfo.getSubarea() != null) {
			this.subarea = SubareaFactory.getRemoteInstance().getSubareaInfo(new ObjectUuidPK(BOSUuid.read(buildingInfo.getSubarea().getId().toString())));
		}
		return objectValue;
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);

//		this.setOprtState(OprtState.VIEW);
		this.setOprtState(OprtState.ADDNEW);

		// this.storeFields();
		// this.initOldData(this.editData);

	}

	protected void beforeStoreFields(ActionEvent e) throws Exception {
		super.beforeStoreFields(e);
		boolean isEdit = false;
		RoomInfo room = (RoomInfo) this.editData;
		if (room.getStandardTotalAmount() != null) {
			BigDecimal newRoomArea = this.txtRoomArea.getBigDecimalValue();
			if (newRoomArea == null) {
				newRoomArea = FDCHelper.ZERO;
			}
			BigDecimal oldRoomArea = room.getRoomArea();
			if (oldRoomArea == null) {
				oldRoomArea = FDCHelper.ZERO;
			}
			if (oldRoomArea.compareTo(newRoomArea) != 0) {
				isEdit = true;
			}
			BigDecimal newBuildingArea = this.txtBuildingArea.getBigDecimalValue();
			if (newBuildingArea == null) {
				newBuildingArea = FDCHelper.ZERO;
			}
			BigDecimal oldBuildingArea = room.getBuildingArea();
			if (oldBuildingArea == null) {
				oldBuildingArea = FDCHelper.ZERO;
			}
			if (oldBuildingArea.compareTo(newBuildingArea) != 0) {
				isEdit = true;
			}
			if (isEdit) {
				if (MsgBox.showConfirm2New(this, "已经定价,更改面积将清除原来价格,是否继续?") == MsgBox.NO) {
					this.abort();
				} else {
					room.setStandardTotalAmount(null);
					room.setBuildPrice(null);
					room.setRoomPrice(null);
				}
			}
		}
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		if (StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("房间编号不能为空!");
			this.abort();
		}
		if (this.f7BuildingProperty.getValue() == null) {
			MsgBox.showInfo("建筑性质不能为空!");
			this.abort();
		}
		if (this.f7RoomModel.getValue() == null) {
			MsgBox.showInfo("户型不能为空!");
			this.abort();
		}
		// if(this.f7ProductDetail.getValue() == null)
		// {
		// MsgBox.showInfo("产品描述不能为空！");
		// this.abort();
		// }
		if (this.f7ProductType.getValue() == null) {
			MsgBox.showInfo("产品类型不能为空！	");
			this.abort();
		}

		if (this.lcBuildingFloor.isVisible() && this.f7BuildingFloor.getValue() == null) {
			MsgBox.showInfo("楼层不能为空！	");
			this.abort();
		}

		// if(this.f7RoomUsage.getValue()==null){
		//			
		// MsgBox.showInfo("房间用途不能为空!");
		// this.abort();
		// }

		if (buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) { //
			BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.prmtBuildUnit.getValue();
			if (buildUnit == null) {
				MsgBox.showInfo("单元不能为空！");
				this.abort();
			}
		}

		if (StringUtils.isEmpty(this.txtRoomPropNo.getText())) {
			MsgBox.showInfo("物业房号不能为空!");
			this.abort();
		}

		if (!this.chkIsForSHE.isSelected() && !this.chkIsForTen.isSelected() && !this.chkIsForPPM.isSelected()) {
			MsgBox.showInfo(this, "售楼,租赁,物业属性至少选一项!");
			this.abort();
		}

		if (!sellProject.isIsForSHE() && this.chkIsForSHE.isSelected()) {
			MsgBox.showWarning(this, "该项目没有售楼属性，房间不能大于项目的范围！");
			this.abort();
		}

		if (!sellProject.isIsForTen() && this.chkIsForTen.isSelected()) {
			MsgBox.showWarning(this, "该项目没有租赁属性，房间不能大于项目的范围！");
			this.abort();
		}

		if (!sellProject.isIsForPPM() && this.chkIsForPPM.isSelected()) {
			MsgBox.showWarning(this, "该项目没有物业属性，房间不能大于项目的范围！");
			this.abort();
		}

		BigDecimal buildingArea = this.txtBuildingArea.getBigDecimalValue();
		BigDecimal roomArea = this.txtRoomArea.getBigDecimalValue();
		if (buildingArea == null) {
			buildingArea = FDCHelper.ZERO;
		}
		if (roomArea == null) {
			roomArea = FDCHelper.ZERO;
		}
		// if (buildingArea == null || buildingArea.compareTo(FDCHelper.ZERO) ==
		// 0) {
		// MsgBox.showInfo("建筑面积不能为空!");
		// this.abort();
		// }
		// if (roomArea == null || roomArea.compareTo(FDCHelper.ZERO) == 0) {
		// MsgBox.showInfo("套内面积不能为空!");
		// this.abort();
		// }
		BigDecimal appArea = buildingArea.subtract(roomArea);
		if (appArea.compareTo(FDCHelper.ZERO) < 0) {
			MsgBox.showInfo("建筑面积不能小于套内面积!");
			this.abort();
		}
		RoomInfo room = (RoomInfo) this.editData;
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", buildingInfo.getId().toString()));
		if (this.prmtBuildUnit.getValue() != null) {
			filter.getFilterItems().add(new FilterItemInfo("buildUnit.id", ((BuildingUnitInfo) this.prmtBuildUnit.getValue()).getId().toString()));
		}

		filter.getFilterItems().add(new FilterItemInfo("floor", new Integer(room.getFloor())));
		filter.getFilterItems().add(new FilterItemInfo("serialNumber", new Integer(room.getSerialNumber())));
		if (room.getId() != null) {
			filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
		}
		if ((filter != null) && RoomFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("序号:" + room.getSerialNumber() + "已存在!");
			this.abort();
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
		if (room.getBuildUnit() != null)
			filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
		filter.getFilterItems().add(new FilterItemInfo("number", room.getNumber()));
		if (RoomFactory.getRemoteInstance().exists(filter)) {
			if (room.getId() != null)
				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
		}
		if (RoomFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("预测房号:" + room.getNumber() + "已存在!");
			this.abort();
		}

		if (room.getRoomNo() != null && room.getRoomNo().trim().length() > 0) {
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
			if (room.getBuildUnit() != null)
				filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
			filter.getFilterItems().add(new FilterItemInfo("roomNo", room.getRoomNo()));
			if (room.getId() != null)
				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("实测房号:" + room.getRoomNo() + "已存在!");
				this.abort();
			}
		}

		if (room.getRoomPropNo() != null && room.getRoomPropNo().trim().length() > 0) {
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("building", buildingInfo.getId().toString()));
			if (room.getBuildUnit() != null)
				filter.getFilterItems().add(new FilterItemInfo("buildUnit", room.getBuildUnit().getId()));
			filter.getFilterItems().add(new FilterItemInfo("roomNo", room.getRoomPropNo()));
			if (room.getId() != null)
				filter.getFilterItems().add(new FilterItemInfo("id", room.getId().toString(), CompareType.NOTEQUALS));
			if (RoomFactory.getRemoteInstance().exists(filter)) {
				MsgBox.showInfo("物业房号:" + room.getRoomPropNo() + "已存在!");
				this.abort();
			}
		}

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

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		RoomInfo room = (RoomInfo) this.editData;
		if (room == null) {
			return;
		}

		if (!room.getSellState().equals(RoomSellStateEnum.Init)) {
			MsgBox.showInfo("非初始房间不能删除!");
			return;
		}
		if(room.getTenancyState()!=null){
			if(!(room.getTenancyState().equals(TenancyStateEnum.unTenancy) 
					||room.getTenancyState().equals(TenancyStateEnum.waitTenancy))){
				MsgBox.showInfo("只有未推盘、未放租、待售或待租的房间才能删除!");
				return;
			}
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		if (RoomPriceBillEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房间已参与定价,不能删除!");
			return;
		}

		if (PriceAdjustEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("房间已参与调价,不能删除!");
			return;
		}
		if (room.getAttachmentEntry() != null && !room.getAttachmentEntry().isEmpty()) {
			MsgBox.showInfo("已绑定其他房间,不能删除!");
			return;
		}

		if (RoomAttachmentEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo("已被其他房间绑定,不能删除！");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}

	protected void txtBuildingArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtBuildingArea_dataChanged(e);
		updateAppArea();
	}

	protected void chkIsForSHE_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForSHE_actionPerformed(e);
		boolean bo = this.chkIsForSHE.isSelected();
		this.contRoomState.setVisible(bo);
	}

	protected void chkIsForTen_actionPerformed(ActionEvent e) throws Exception {
		super.chkIsForTen_actionPerformed(e);
		this.contTenancyState.setVisible(this.chkIsForTen.isSelected());
	}

	protected void txtRoomArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtRoomArea_dataChanged(e);
		updateAppArea();
	}

	protected void f7BuildingFloor_dataChanged(DataChangeEvent e) throws Exception {/*
																					 * BuildingFloorEntryInfo
																					 * info
																					 * =
																					 * (
																					 * BuildingFloorEntryInfo
																					 * )
																					 * this
																					 * .
																					 * f7BuildingFloor
																					 * .
																					 * getValue
																					 * (
																					 * )
																					 * ;
																					 * if
																					 * (
																					 * info
																					 * ==
																					 * null
																					 * )
																					 * return
																					 * ;
																					 * 
																					 * int
																					 * temp
																					 * =
																					 * info
																					 * .
																					 * getFloor
																					 * (
																					 * )
																					 * ;
																					 * this
																					 * .
																					 * spiFloor
																					 * .
																					 * setValue
																					 * (
																					 * new
																					 * Integer
																					 * (
																					 * temp
																					 * )
																					 * )
																					 * ;
																					 */
	}

}