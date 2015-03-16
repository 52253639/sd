/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingStructureInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.CodingTypeEnum;
import com.kingdee.eas.fdc.sellhouse.ConditionTypeEnum;
import com.kingdee.eas.fdc.sellhouse.NetWorkTypeEnum;
import com.kingdee.eas.fdc.sellhouse.RoomDesFactory;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomModelCollection;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class BuildingEditUI extends AbstractBuildingEditUI {
	private static final Logger logger = CoreUIObject
			.getLogger(BuildingEditUI.class);

	boolean isShowNoEditWar = false;
	
	//存储楼层分录的信息
	Map floorMap = new HashMap();
	/**
	 * output class constructor
	 */
	public BuildingEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();

		this.tblRoomModel
				.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		KDTextField textField = new KDTextField();
		textField.setMaxLength(80);
		ICellEditor txtEditor = new KDTDefaultCellEditor(textField);
		this.tblRoomModel.getColumn("number").setEditor(txtEditor);
		this.tblRoomModel.getColumn("number").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblRoomModel.getColumn("name").setEditor(txtEditor);
		this.tblRoomModel.getColumn("name").getStyleAttributes().setBackground (
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblRoomModel.getColumn("des").setEditor(txtEditor);
		this.tblRoomModel.getColumn("modelType").getStyleAttributes().setBackground(
				FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		this.tblUnitList.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		
		this.tblUnitList.getColumn("name").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		this.tblUnitList.getColumn("number").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		
		if(this.floorTable.getColumn("floorAlias") != null)
		{
			this.floorTable.getColumn("floorAlias").getStyleAttributes().setBackground(FDCClientHelper.KDTABLE_COMMON_BG_COLOR);
		}
		
		this.floorTable.checkParsed();
		this.floorTable.getColumn("floor").getStyleAttributes().setLocked(true);
		this.floorTable.getColumn("isEnable").getStyleAttributes().setLocked(true);
		
		KDBizPromptBox f7Product = new KDBizPromptBox();
		f7Product
				.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.RoomModelTypeQuery");
		f7Product.setEditable(true);
		f7Product.setDisplayFormat("$name$");
		f7Product.setEditFormat("$number$");
		f7Product.setCommitFormat("$number$");
		//设置户型类别的集团管控过滤
		SellProjectInfo sp = (SellProjectInfo) this.getUIContext().get("sellProject");
		if(sp!=null){
			SHEHelper.SetGroupFilters(f7Product, sp.getId().toString(), "户型类别", "RoomArch");
			SHEHelper.SetGroupFilters(this.f7ProductType, sp.getId().toString(), "产品类型", "RoomArch");			
			SHEHelper.SetGroupFilters(this.comboBuildingStructure, sp.getId().toString(), "建筑结构", "RoomArch");
		}		
		ICellEditor f7Editor = new KDTDefaultCellEditor(f7Product);
		this.tblRoomModel.getColumn("modelType").setEditor(f7Editor);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		
		/*  亿达5.9售楼改动  ?	删除租售项目,楼栋中的建筑性质，采用界面隐藏即可.  20081223
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems()
				.add(new FilterItemInfo("level", new Integer(2)));
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		BuildingPropertyInfo buildingProperty = sellProject
				.getBuildingProperty();
		if (buildingProperty != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("parent.id", buildingProperty.getId()
							.toString()));
		}
		this.f7BuildingProperty.setEntityViewInfo(view);
		*/
		
		if(OprtState.ADDNEW.equalsIgnoreCase(this.getOprtState()))
		{
			int up = this.spiFloorCount.getIntegerVlaue().intValue();
			int down = this.spSubFloor.getIntegerVlaue().intValue();
			
			this.setBuildingFloorEntryTable(up, down);
		}
		
		
		this.storeFields();
		this.initOldData(this.editData);
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
		}
	}

	public void lockNoEdit() {
		if (!this.getOprtState().equals("EDIT")) {
			return;
		}
		String buildingId = this.editData.getId().toString();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", buildingId));
		try {
			if (RoomDesFactory.getRemoteInstance().exists(filter)
					|| RoomFactory.getRemoteInstance().exists(filter)) {
				if (!isShowNoEditWar) {
					MsgBox.showInfo("已经建立了房间或房间定义,将锁定相关属性!");
				}
				this.comboCodingType.setEnabled(false);
				this.spiFloorCount.setEnabled(false);
				this.spSubFloor.setEnabled(false);
				this.spiUnitCount.setEnabled(false);
				this.btnDeleteRoomModel.setEnabled(false);
				this.isShowNoEditWar = true;
				
				if(this.floorTable.getColumn("floorAlias") != null)
				{
					this.floorTable.getColumn("floorAlias").getStyleAttributes().setLocked(true);
				}
			}
		} catch (EASBizException e) {
			this.handleException(e);
		} catch (BOSException e) {
			this.handleException(e);
		}
	}

	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (STATUS_VIEW.equals(this.getOprtState())) {
			
		}else if(STATUS_ADDNEW.equals(this.getOprtState())){
			this.tblRoomModel.getStyleAttributes().setLocked(false);
			this.tblUnitList.getStyleAttributes().setLocked(false);

			this.btnAddRoomModel.setEnabled(true);
			this.btnDeleteRoomModel.setEnabled(true);
			
			this.btnAddUnit.setEnabled(true);
			this.btnRemoveUnit.setEnabled(true);
			
			this.comboCodingType.setEnabled(true);
			//this.spiFloorCount.setEnabled(true);
		}
		
	}
	
	private void initControl() {
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.txtProjectNumber.setEnabled(false);
		this.txtProjectName.setEnabled(false);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		
		this.txtNumber.setMaxLength(80);
		this.txtName.setMaxLength(80);
		
//		亿达5.9售楼改动  ?	删除租售项目,楼栋中的建筑性质，采用界面隐藏即可.  20081223
		//this.f7BuildingProperty.setRequired(true);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		filter.getFilterItems().add(
				new FilterItemInfo("sellProject.id", sellProject.getId()
						.toString()));
		this.f7Subarea.setEntityViewInfo(view);
		this.txtBuildingHeight.setRemoveingZeroInDispaly(false);
		this.txtBuildingHeight.setRemoveingZeroInEdit(false);
		this.txtBuildingHeight.setPrecision(2);
		this.txtBuildingHeight.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingHeight.setSupportedEmpty(true);
		this.txtBuildingHeight.setNegatived(false);
		this.txtBuildingTerraArea.setRemoveingZeroInDispaly(false);
		this.txtBuildingTerraArea.setRemoveingZeroInEdit(false);
		this.txtBuildingTerraArea.setPrecision(2);
		this.txtBuildingTerraArea.setHorizontalAlignment(JTextField.RIGHT);
		this.txtBuildingTerraArea.setSupportedEmpty(true);
		this.txtBuildingTerraArea.setNegatived(false);
		SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 1000, 1);
		this.spiFloorCount.setModel(model);
		model = new SpinnerNumberModel(0, 0, 100, 1);
		this.spiUnitCount.setModel(model);
		model = new SpinnerNumberModel(0, 0, 100, 1);
		this.spiCustomerLiftCount.setModel(model);
		model = new SpinnerNumberModel(0, 0, 100, 1);
		this.spiCargoLiftCount.setModel(model);
		this.spiFloorCount.setRequired(true);
		this.spiUnitCount.setRequired(true);
		this.comboBuildingStructure.setRequired(true);
		
		model = new SpinnerNumberModel(0,-1000,0,1);
		this.spSubFloor.setModel(model);
		this.spSubFloor.setRequired(true);
		
		
		
		//对消防设施说明、天花说明、 地面说明、 电梯详细说明、 承重说明进行80字符长度控制
		//add by wenyaowei 200906-05
		this.txtFireControl.setMaxLength(80);
		this.txtCeling.setMaxLength(80);
		this.txtFloorExplain.setMaxLength(80);
		this.txtLiftExplain.setMaxLength(80);
		this.txtSupportWeight.setMaxLength(80);
		//对	电力设施，通讯情况，网络情况，空调标准进行80字符长度控制
		//add by wenyaowei 200906-05
		this.txtEstablishment.setMaxLength(80);
		this.txtCommunication.setMaxLength(80);
		this.txtNetWorkCircs.setMaxLength(80);
		this.txtConditionStandard.setMaxLength(80);
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		this.storeFields();
		this.initOldData(this.editData);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		
		CacheServiceFactory.getInstance().discardType(new RoomModelInfo().getBOSType());
	}

    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		
		CacheServiceFactory.getInstance().discardType(new RoomModelInfo().getBOSType());
	}
    
	protected void comboCodingType_actionPerformed(ActionEvent e)
			throws Exception {
		super.comboCodingType_actionPerformed(e);
		CodingTypeEnum codingType = (CodingTypeEnum) this.comboCodingType
				.getSelectedItem();

		this.kDTabbedPane1.setSelectedIndex(1);
		this.kDTabbedPane1.setEnabledAt(0, false);
		this.kDTabbedPane1.setEnabledAt(2, false);
		this.spSubFloor.setEnabled(true);
		this.spiFloorCount.setEnabled(true);
		if (codingType.equals(CodingTypeEnum.UnitFloorNum)) {
			this.kDTabbedPane1.setSelectedIndex(0);
			this.kDTabbedPane1.setEnabledAt(0, true);
			this.kDTabbedPane1.setEnabledAt(2, true);		
		}else if(codingType.equals(CodingTypeEnum.FloorNum)){
			this.kDTabbedPane1.setSelectedIndex(2);
			this.kDTabbedPane1.setEnabledAt(2, true);			
		}
		
		if (!codingType.equals(CodingTypeEnum.UnitFloorNum)) {
			this.spiUnitCount.setValue(new Integer(0));
			this.tblUnitList.removeRows();
		}
		if(codingType.equals(CodingTypeEnum.Num)){
			this.spSubFloor.setEnabled(false);
			this.spiFloorCount.setEnabled(false);
			this.spSubFloor.setValue(new Integer(0));
			this.spiFloorCount.setValue(new Integer(1));
		}
	}

	public void loadFields() {
		super.loadFields();
		SHEHelper.setNumberEnabled(editData,getOprtState(),txtNumber);
		BuildingInfo buildingInfo = (BuildingInfo) this.editData;
		this.txtProjectNumber
				.setText(buildingInfo.getSellProject().getNumber());
		this.txtProjectName.setText(buildingInfo.getSellProject().getName());
		this.txtName.setText(buildingInfo.getName());
		this.txtNumber.setText(buildingInfo.getNumber());
		this.f7Subarea.setValue(buildingInfo.getSubarea());
		this.spiFloorCount.setValue(new Integer(buildingInfo.getFloorCount()));
		this.spSubFloor.setValue(new Integer(buildingInfo.getSubFloorCount()));
		this.pkJoinInDate.setValue(buildingInfo.getJoinInDate());
		this.pkCompleteDate.setValue(buildingInfo.getCompleteDate());
		this.f7ConstructPart.setValue(buildingInfo.getConstructPart());
		this.txtBuildingHeight.setValue(buildingInfo.getBuildingHeight());
		this.f7BuildingProperty.setValue(buildingInfo.getBuildingProperty());
		this.txtBuildingTerraArea.setValue(buildingInfo.getBuildingTerraArea());
		this.comboCodingType.setSelectedItem(buildingInfo.getCodingType());
		this.comboBuildingStructure.setValue(buildingInfo.getBuildingStructure());
		this.spiUnitCount.setValue(new Integer(buildingInfo.getUnitCount()));
		this.comboConditionType.setSelectedItem(buildingInfo.getConditionType());
		this.comboNetWorkType.setSelectedItem(buildingInfo.getNetWorkType());
		this.txtConditionStandard.setText(buildingInfo.getConditionStandard());
		this.txtNetWorkCircs.setText(buildingInfo.getNetWorkCircs());
		this.txtCommunication.setText(buildingInfo.getCommunication());
		this.txtEstablishment.setText(buildingInfo.getEstablishment());
		this.txtFireControl.setText(buildingInfo.getFireControl());
		this.txtCeling.setText(buildingInfo.getCeiling());
		this.txtFloorExplain.setText(buildingInfo.getFloorExplain());
		this.spiCustomerLiftCount.setValue(new Integer(buildingInfo.getCustomerLiftCount()));
		this.spiCargoLiftCount.setValue(new Integer(buildingInfo.getCargoLiftCount()));
		this.txtLiftExplain.setText(buildingInfo.getLiftExplain());
		this.txtSupportWeight.setText(buildingInfo.getSupportWeight());
		this.f7ProductType.setValue(buildingInfo.getProductType());
		this.txtAdministrativeNumber.setText(buildingInfo.getAdministrativeNumber());
		if (!buildingInfo.getCodingType().equals(CodingTypeEnum.UnitFloorNum)) {
			this.kDTabbedPane1.setEnabledAt(0, false);
		} else {
			this.kDTabbedPane1.setEnabledAt(0, true);
		}
		RoomModelCollection roomModels = buildingInfo.getRoomModels();
		this.tblRoomModel.removeRows();
		this.tblRoomModel.checkParsed();
		this.tblRoomModel.setUserObject(null);
		for (int i = 0; i < roomModels.size(); i++) {
			RoomModelInfo info = roomModels.get(i);
			IRow row = tblRoomModel.addRow();
			row.setUserObject(info);
			row.getCell("id").setValue(info.getId().toString());
			row.getCell("number").setValue(info.getNumber());
			row.getCell("name").setValue(info.getName());
			row.getCell("modelType").setValue(info.getRoomModelType());
			row.getCell("des").setValue(info.getDescription());
		}
		BuildingUnitCollection buildColl = buildingInfo.getUnits();
		this.tblUnitList.removeRows();
		this.tblUnitList.checkParsed();
		for(int i=0;i<buildColl.size();i++) {
			BuildingUnitInfo unitInfo = buildColl.get(i);
			IRow row = this.tblUnitList.addRow();
			row.setUserObject(unitInfo);
			row.getCell("id").setValue(unitInfo.getId().toString());
			row.getCell("seq").setValue(new Integer(unitInfo.getSeq()));
			row.getCell("number").setValue(unitInfo.getNumber());
			row.getCell("name").setValue(unitInfo.getName());
			row.getCell("des").setValue(unitInfo.getDescription());
		}
		//BuildingFloorEntryCollection floorColl = buildingInfo.getFloorEntry();
		
		BuildingFloorEntryCollection floorColl = this.getBuildingFloorEntryColl(buildingInfo);
		this.floorTable.checkParsed();
		this.floorTable.removeRows();
		for(int i = 0; i < floorColl.size(); i ++)
		{
			BuildingFloorEntryInfo info = floorColl.get(i);
			IRow row = this.floorTable.addRow();
			row.setUserObject(info);
			
			row.getCell("floor").setValue(new Integer(info.getFloor()));
			
			row.getCell("floorAlias").setValue(info.getFloorAlias());
			
			row.getCell("description").setValue(info.getDescription());
			
			row.getCell("isEnable").setValue(new Boolean( info.isIsEnable()));
			
			floorMap.put(new Integer(info.getFloor()), info);
		}
		
		
		
		lockNoEdit();
		if (this.getOprtState().equals("VIEW")) {
			this.tblRoomModel.getStyleAttributes().setLocked(true);
			this.tblUnitList.getStyleAttributes().setLocked(true);
			this.btnAddRoomModel.setEnabled(false);
			this.btnDeleteRoomModel.setEnabled(false);
			this.btnAddUnit.setEnabled(false);
			this.btnRemoveUnit.setEnabled(false);
		}

	}
	
	private BuildingFloorEntryCollection getBuildingFloorEntryColl(BuildingInfo building)
	{
		BuildingFloorEntryCollection coll = null;
		
		if(building == null || building.getId() == null)
			return new BuildingFloorEntryCollection();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		
		SorterItemCollection sorColl = new SorterItemCollection();
		
		
		SorterItemInfo item = new SorterItemInfo("floor");
		item.setSortType(SortType.DESCEND);
		
		sorColl.add(item);
		
		view.setSorter(sorColl);
		
		filter.getFilterItems().add(new FilterItemInfo("building.id",building.getId().toString()));
		
		try
		{
			coll = BuildingFloorEntryFactory.getRemoteInstance().getBuildingFloorEntryCollection(view);
		} catch (BOSException e)
		{
			super.handUIExceptionAndAbort(e);
		}
		return coll;
	}
	
	public void actionEdit_actionPerformed(ActionEvent arg0) throws Exception {
		super.actionEdit_actionPerformed(arg0);
		this.tblRoomModel.getStyleAttributes().setLocked(false);
		this.tblUnitList.getColumn("name").getStyleAttributes().setLocked(false);
		this.tblUnitList.getColumn("des").getStyleAttributes().setLocked(false);
		this.tblUnitList.getColumn("number").getStyleAttributes().setLocked(false);
		this.btnAddRoomModel.setEnabled(true);
		this.btnDeleteRoomModel.setEnabled(true);
		this.btnAddUnit.setEnabled(true);
		this.btnRemoveUnit.setEnabled(true);
		lockNoEdit();
		//清除缓存
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
		BuildingInfo buildingInfo = (BuildingInfo)this.editData;
		CacheServiceFactory.getInstance().discardType(buildingInfo.getBOSType());
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
//		清除缓存
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
		.get("sellProject");
		CacheServiceFactory.getInstance().discardType(sellProject.getBOSType());
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
		
		BuildingInfo buildingInfo = (BuildingInfo) this.editData;
		buildingInfo.setNumber(this.txtNumber.getText());
		buildingInfo.setName(this.txtName.getText());	
		
		//这里可能会导致 simpleName 长度过长，数据库保存时提示长度超出异常 -zhicheng_jin 090702
//		buildingInfo.setSimpleName(buildingInfo.getSellProject().getNumber()
//				+ "." + buildingInfo.getNumber());
		buildingInfo.setSubarea((SubareaInfo) this.f7Subarea.getValue());
		buildingInfo.setFloorCount(((Integer) this.spiFloorCount.getValue()).intValue());
		buildingInfo.setSubFloorCount(((Integer)this.spSubFloor.getValue()).intValue());
		buildingInfo.setJoinInDate((Date) this.pkJoinInDate.getValue());
		buildingInfo.setCompleteDate((Date) this.pkCompleteDate.getValue());
		buildingInfo.setConstructPart((SupplierInfo) this.f7ConstructPart
				.getValue());
		buildingInfo.setBuildingHeight(this.txtBuildingHeight
				.getBigDecimalValue());
		buildingInfo
				.setBuildingProperty((BuildingPropertyInfo) this.f7BuildingProperty
						.getValue());
		buildingInfo.setBuildingTerraArea(this.txtBuildingTerraArea
				.getBigDecimalValue());
		if (this.comboBuildingStructure.getValue() != null) {
			buildingInfo.setBuildingStructure((BuildingStructureInfo) this.comboBuildingStructure
							.getValue());
		}
		buildingInfo.setProductType((ProductTypeInfo) this.f7ProductType.getValue());
		buildingInfo.setAdministrativeNumber(this.txtAdministrativeNumber.getText());
		
		buildingInfo.setCodingType((CodingTypeEnum) this.comboCodingType
				.getSelectedItem());
		buildingInfo.setUnitCount(((Integer) this.spiUnitCount.getValue())
				.intValue());
		buildingInfo.setConditionType((ConditionTypeEnum)this.comboConditionType.getSelectedItem());
		buildingInfo.setNetWorkType((NetWorkTypeEnum)this.comboNetWorkType.getSelectedItem());
		buildingInfo.setConditionStandard(this.txtConditionStandard.getText());
		buildingInfo.setNetWorkCircs(this.txtNetWorkCircs.getText());
		buildingInfo.setCommunication(this.txtCommunication.getText());
		buildingInfo.setEstablishment(this.txtEstablishment.getText());
		buildingInfo.setFireControl(this.txtFireControl.getText());
		buildingInfo.setCeiling(this.txtCeling.getText());
		buildingInfo.setFloorExplain(this.txtFloorExplain.getText());
		buildingInfo.setCustomerLiftCount(((Integer)this.spiCustomerLiftCount.getValue()).intValue());
		buildingInfo.setCargoLiftCount(((Integer)this.spiCargoLiftCount.getValue()).intValue());
		buildingInfo.setLiftExplain(this.txtLiftExplain.getText());
		buildingInfo.setSupportWeight(this.txtSupportWeight.getText());
		buildingInfo.getRoomModels().clear();
		for (int i = 0; i < this.tblRoomModel.getRowCount(); i++) {
			IRow row = this.tblRoomModel.getRow(i);
			RoomModelInfo roomModel = (RoomModelInfo) row.getUserObject();
			roomModel.setNumber((String) row.getCell("number").getValue());
			roomModel.setName((String) row.getCell("name").getValue());
			roomModel.setRoomModelType((RoomModelTypeInfo) row.getCell(
					"modelType").getValue());
			roomModel.setDescription((String) row.getCell("des").getValue());
			buildingInfo.getRoomModels().add(roomModel);
		}
		buildingInfo.getUnits().clear();
		for (int i = 0; i < this.tblUnitList.getRowCount(); i++) {
			IRow row = this.tblUnitList.getRow(i);
			BuildingUnitInfo unitInfo = (BuildingUnitInfo) row.getUserObject();
			unitInfo.setNumber((String) row.getCell("number").getValue());
			unitInfo.setName((String) row.getCell("name").getValue());
			unitInfo.setDescription((String) row.getCell("des").getValue());
			unitInfo.setSeq(((Integer) row.getCell("seq").getValue()).intValue());
			buildingInfo.getUnits().add(unitInfo);
		}
		
		buildingInfo.getFloorEntry().clear();
		this.floorTable.checkParsed();
		for(int i = 0; i < this.floorTable.getRowCount(); i ++)
		{
			IRow row = this.floorTable.getRow(i);
			
			BuildingFloorEntryInfo info = (BuildingFloorEntryInfo)row.getUserObject();
			if(info == null)
				info = new BuildingFloorEntryInfo(); 
			
			info.setFloor(row.getCell("floor").getValue() == null ? 0
			:((Integer)row.getCell("floor").getValue()).intValue());
			
			if(row.getCell("floorAlias").getValue() instanceof Integer)
			{
				info.setFloorAlias(String.valueOf(((Integer) row.getCell("floorAlias").getValue()).intValue()));
			}
			else if(row.getCell("floorAlias").getValue() instanceof String)
			{
				info.setFloorAlias((String)row.getCell("floorAlias").getValue());
			}
			info.setDescription((String)row.getCell("description").getValue());
			
			info.setIsEnable(((Boolean)row.getCell("isEnable").getValue()).booleanValue());
			
			buildingInfo.getFloorEntry().add(info);
		}
		
		
		//长名称 项目名称-分区名称-楼栋名称
		buildingInfo.setDisplayName(buildingInfo.getSellProject()==null?"":buildingInfo.getSellProject().getName()
					+(buildingInfo.getSubarea()==null?"":"-"+buildingInfo.getSubarea().getName())+"-"+buildingInfo.getName());
	}

	protected IObjectValue createNewData() {
		BuildingInfo buildingInfo = new BuildingInfo();
		SellProjectInfo sellProject = (SellProjectInfo) this.getUIContext()
				.get("sellProject");
		SubareaInfo subarea = (SubareaInfo) this.getUIContext().get("subarea");
		buildingInfo.setSellProject(sellProject);
		buildingInfo.setSubarea(subarea);
		buildingInfo.setCodingType(CodingTypeEnum.FloorNum);
		buildingInfo.setUnitCount(0);
		buildingInfo.setFloorCount(1);
		buildingInfo.setSubFloorCount(0);
		buildingInfo.setCustomerLiftCount(0);
		buildingInfo.setCargoLiftCount(0);
		buildingInfo.setConditionType(ConditionTypeEnum.CENTER);
		buildingInfo.setNetWorkType(NetWorkTypeEnum.TELECOM);
		buildingInfo.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		//buildingInfo.setBuildingStructure(BuildingStructureEnum.KuangJia);
		return buildingInfo;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection selectors = super.getSelectors();
		selectors.add("*");
		selectors.add("sellProject.*");
		selectors.add("productType.*");
		selectors.add("buildingProperty.*");
		selectors.add("constructPart.*");
		selectors.add("subarea.*");
		selectors.add("roomModels.*");
		selectors.add("buildingStructure.*");
		selectors.add("roomModels.roomModelType.*");
		selectors.add("units.*");
		selectors.add("floorEntry.*");
		return selectors;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return BuildingFactory.getRemoteInstance();
	}

	protected void btnAddRoomModel_actionPerformed(ActionEvent e)
			throws Exception {
		int index = this.tblRoomModel.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			index = this.tblRoomModel.getRowCount() - 1;
		}
		IRow row = this.tblRoomModel.addRow(index + 1);
		row.setUserObject(new RoomModelInfo());
		this.tblRoomModel.setUserObject("isEdited");
	}

	protected void btnDeleteRoomModel_actionPerformed(ActionEvent e)
			throws Exception {
		int index = this.tblRoomModel.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		tblRoomModel.removeRow(index);
		this.tblRoomModel.setUserObject("isEdited");
	}

	public boolean isModify() {
		boolean isModify = super.isModify();
		if (this.tblRoomModel.getUserObject() != null) {
			isModify = true;
		}
		for (int i = 0; i < tblRoomModel.getRowCount(); i++) {
			IRow row = this.tblRoomModel.getRow(i);
			RoomModelInfo roomModel = (RoomModelInfo) row.getUserObject();
			if (row.getCell("number").getValue() == null) {
				isModify = true;
			} else {
				if (!roomModel.getNumber().equals(
						row.getCell("number").getValue())) {
					isModify = true;
				}
			}
			if (row.getCell("name").getValue() == null) {
				isModify = true;
			} else {
				if (!roomModel.getName().equals(row.getCell("name").getValue())) {
					isModify = true;
				}
			}
			if (row.getCell("modelType").getValue() == null) {
				isModify = true;
			} else {
				if (!roomModel.getRoomModelType().equals(
						row.getCell("modelType").getValue())) {
					isModify = true;
				}
			}
		}
		return isModify;
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
	  
	   this.tabPanel.setSelectedIndex(0);
	   if (this.txtNumber.isEditable()) {
		if (StringUtils.isEmpty(this.txtNumber.getText())) {
			MsgBox.showInfo("楼栋编码必须录入！");
			abort();
		 }
		}
		if (StringUtils.isEmpty(this.txtName.getText())) {
			MsgBox.showInfo("楼栋名称必须录入！");
			abort();
		}
		
//		if (this.f7BuildingProperty.getValue() == null) {
//			MsgBox.showInfo("建筑性质必须录入！");
//			abort();
//		}
		
		if(this.comboBuildingStructure.getValue() == null)
		{
			MsgBox.showInfo("建筑结构必须录入！");
			abort();
		}
		if(this.tblRoomModel.getRowCount()==0){
			MsgBox.showInfo("户型必须录入！");
			abort();
		}
		
		Date joinInDate = this.pkJoinInDate.getTimestamp();
		Date completeDate = this.pkCompleteDate.getTimestamp();
		if(joinInDate != null  &&  completeDate != null){
			if(joinInDate.before(completeDate)){
				MsgBox.showInfo("入住日期不能早于竣工日期！");
				abort();
			}
		}
		
		if(CodingTypeEnum.UnitFloorNum.equals(this.comboCodingType.getSelectedItem())){
			if(this.spiUnitCount.getIntegerVlaue() != null  &&  this.spiUnitCount.getIntegerVlaue().intValue() == 0){
				MsgBox.showInfo("单元数不能为0！");
				abort();
			}
			if(this.tblUnitList.getRowCount()!=this.spiUnitCount.getIntegerVlaue().intValue()){
				MsgBox.showInfo("单元数与实际描述列数不符！");
				abort();
			}
		}
		
		Map numberMap = new HashMap();
		Map nameMap = new HashMap();
		for (int i = 0; i < this.tblRoomModel.getRowCount(); i++) {
			IRow row = this.tblRoomModel.getRow(i);
			if (row.getCell("number").getValue() == null) {
				MsgBox.showInfo("户型编码不能为空！");
				this.kDTabbedPane1.setSelectedIndex(1);
				tblRoomModel.getSelectManager().select(row.getRowIndex(), 0);
				abort();
			} else {
				if (numberMap.containsKey(row.getCell("number").getValue())) {
					MsgBox.showInfo("户型编码不能重复！");
					abort();
				} else {
					numberMap.put(row.getCell("number").getValue(), row
							.getCell("number").getValue());
				}
			}
			if (row.getCell("name").getValue() == null) {
				MsgBox.showInfo("户型名称不能为空！");
				this.kDTabbedPane1.setSelectedIndex(1);
				tblRoomModel.getSelectManager().select(row.getRowIndex(), 1);
				abort();
			} else {
				if (nameMap.containsKey(row.getCell("name").getValue())) {
					MsgBox.showInfo("户型名称不能重复！");
					abort();
				} else {
					nameMap.put(row.getCell("name").getValue(), row.getCell(
							"name").getValue());
				}
			}
			if (row.getCell("modelType").getValue() == null) {
				MsgBox.showInfo("户型类别不能为空！");
				this.kDTabbedPane1.setSelectedIndex(1);
				tblRoomModel.getSelectManager().select(row.getRowIndex(), 1);
				abort();
			}
		}		
		
		Set unitNumberSet = new HashSet();
		Set unitNameSet = new HashSet();
		for(int i=0;i<this.tblUnitList.getRowCount();i++){
			IRow row = this.tblUnitList.getRow(i);
			String number = (String)row.getCell("number").getValue();
			String name = (String)row.getCell("name").getValue();
			if(number==null || number.trim().equals("")) {
				MsgBox.showInfo("单元编码不能为空！");
				this.kDTabbedPane1.setSelectedIndex(0);
				tblUnitList.getSelectManager().select(row.getRowIndex(), 2);
				abort();
			}else if(name==null || name.trim().equals("")) {
				MsgBox.showInfo("单元名称不能为空！");
				this.kDTabbedPane1.setSelectedIndex(0);
				tblUnitList.getSelectManager().select(row.getRowIndex(), 3);
				abort();
			}
			String des = (String)row.getCell("des").getValue();
			if(number.length()>44 || name.length()>80 || (des!=null && des.length()>255)) {
				MsgBox.showInfo("单元编码或名称或描述字符过长！");
				abort();
			}
			if(unitNumberSet.contains(number)){
				MsgBox.showInfo("单元编码不能重复！");
				this.kDTabbedPane1.setSelectedIndex(0);
				tblUnitList.getSelectManager().select(row.getRowIndex(), 2);
				abort();
			}else if(unitNameSet.contains(name)){
				MsgBox.showInfo("单元名称不能重复！");
				this.kDTabbedPane1.setSelectedIndex(0);
				tblUnitList.getSelectManager().select(row.getRowIndex(), 3);
				abort();
			}
			unitNumberSet.add(number);
			unitNameSet.add(name);
		}
		
		this.floorTable.checkParsed();
		Set floorSet = new HashSet();
		for(int i = 0; i < floorTable.getRowCount(); i ++)
		{
			IRow row = this.floorTable.getRow(i);
			
			String floorAlias =  null;
			
			Object obj = row.getCell("floorAlias").getValue();
			
			if(obj instanceof String)
				floorAlias = (String)obj;
			else if(obj instanceof Integer)
				floorAlias = String.valueOf(((Integer)obj).intValue());
			
			if(floorAlias == null || floorAlias.trim().length() < 1)
			{
				MsgBox.showWarning("楼层别名不能为空！");
				this.abort();
			}
			if(floorSet.contains(floorAlias))
			{
				MsgBox.showWarning("楼层别名不能重复！");
				this.abort();
			}
			if(floorAlias.length() > 40)
			{
				MsgBox.showWarning("楼层别名长度不能超过40！");
				this.abort();
			}
			
			String des = null;
			
			Object o = row.getCell("description").getValue();
			
			if(o instanceof String)
				des = (String)o;
			else if(o instanceof Integer)
				des = String.valueOf(((Integer)o).intValue());
			
			if(des != null && des.length() > 40)
			{
				MsgBox.showWarning("描述字段长度不能超过40！");
				this.abort();
			}
			
			
			
			floorSet.add(floorAlias);
		}
		
		
		this.tabPanel.setSelectedIndex(1);
		if(this.txtConditionStandard.getText().length()>255)
		{
			MsgBox.showInfo("空调标准说明长度不能超过255!");
			abort();
		}
		if(this.txtNetWorkCircs.getText().length()>255)
		{
			MsgBox.showInfo("网络情况说明长度不能超过255!");
			abort();
		}
		if(this.txtCommunication.getText().length()>255)
		{
			MsgBox.showInfo("通讯情况说明长度不能超过255!");
			abort();
		}
		if(this.txtFireControl.getText().length()>255)
		{
			MsgBox.showInfo("消防设施说明长度不能超过255!");
			abort();
		}
			
		if(this.txtCeling.getText().length()>255)
		{
			MsgBox.showInfo("天花说明长度不能超过255!");
			abort();
		}
		if(this.txtFloorExplain.getText().length()>255)
		{
			MsgBox.showInfo("地面说明长度不能超过255!");
			abort();
		}
		if(this.txtEstablishment.getText().length()>255)
		{
			MsgBox.showInfo("电力设施说明长度不能超过255!");
			abort();
		}
		if(this.txtSupportWeight.getText().length()>255)
		{
			MsgBox.showInfo("承重说明长度不能超过255!");
			abort();
		}
		if(this.txtLiftExplain.getText().length()>255)
		{
			MsgBox.showInfo("电梯详细说明长度不能超过255!");
			abort();
		}
		
	}
	

	
	protected void btnAddUnit_actionPerformed(ActionEvent e) throws Exception {
		IRow row = this.tblUnitList.addRow();
		row.setUserObject(new BuildingUnitInfo());
		row.getCell("seq").setValue(new Integer(row.getRowIndex()+1));
		row.getCell("name").setValue((row.getRowIndex()+1)+"单元");
		this.spiUnitCount.setValue(new Integer(this.tblUnitList.getRowCount()));
	}
	
	protected void btnRemoveUnit_actionPerformed(ActionEvent e)			throws Exception {
		int index = this.tblUnitList.getSelectManager().getActiveRowIndex();
		if (index == -1) {
			return;
		}
		tblUnitList.removeRow(index);
		this.spiUnitCount.setValue(new Integer(this.tblUnitList.getRowCount()));		
		
		//刷新单元列表，重新排序，如果名称为i+单元，重新命名
		for(int i=0;i<this.tblUnitList.getRowCount();i++) {
			IRow row = this.tblUnitList.getRow(i);
			row.getCell("seq").setValue(new Integer(row.getRowIndex()+1));
			String valueName = (String)row.getCell("name").getValue();
			if(valueName!=null && valueName.equals((row.getRowIndex()+2)+"单元"))
				row.getCell("name").setValue((row.getRowIndex()+1)+"单元");
		}
		
	}
	
	protected void spSubFloor_stateChanged(ChangeEvent e) throws Exception
	{
		 int downCount = ((Integer)this.spSubFloor.getValue()).intValue();
		 int upCount = ((Integer)this.spiFloorCount.getValue()).intValue();
		 this.setBuildingFloorEntryTable(upCount, downCount);
	}
	protected void spiFloorCount_stateChanged(ChangeEvent e) throws Exception
	{
		 int downCount = ((Integer)this.spSubFloor.getValue()).intValue();
		 int upCount = ((Integer)this.spiFloorCount.getValue()).intValue();
		 this.setBuildingFloorEntryTable(upCount, downCount);
	}
	
	private void  setBuildingFloorEntryTable(int upCount,int downCount)
	{
		this.floorTable.checkParsed();
		this.floorTable.removeRows();
		
		for(int i = upCount; i >= downCount; i --)
		{
			if(i == 0)
				continue;
			IRow row = this.floorTable.addRow();
			row.getCell("floor").setValue(new Integer(i));
			
			if(floorMap.get(new Integer(i)) != null)
			{
				row.setUserObject((BuildingFloorEntryInfo)floorMap.get(new Integer(i)));
				
				row.getCell("floorAlias").setValue(((BuildingFloorEntryInfo)floorMap.get(new Integer(i))).getFloorAlias());
				
				row.getCell("isEnable").setValue(new Boolean( ((BuildingFloorEntryInfo)floorMap.get(new Integer(i))).isIsEnable()));
				
				row.getCell("description").setValue(((BuildingFloorEntryInfo)floorMap.get(new Integer(i))).getDescription());
			}
			else
			{
				row.getCell("floorAlias").setValue(new Integer(i));
				
				row.getCell("isEnable").setValue(Boolean.TRUE);
			}
			
			
		}
	}
	
}