/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.Building;
import com.kingdee.eas.fdc.sellhouse.BuildingCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingFloorEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingPropertyInfo;
import com.kingdee.eas.fdc.sellhouse.EyeSignhtInfo;
import com.kingdee.eas.fdc.sellhouse.HopedDirectionInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFormInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class RptRoomFilterUI extends AbstractRptRoomFilterUI {
	private static final Logger logger = CoreUIObject.getLogger(RptRoomFilterUI.class);

	/**
	 * output class constructor
	 */
	public RptRoomFilterUI() throws Exception {
		super();
		ButtonGroup bg = new ButtonGroup();
		bg.add(this.jrbBuildingProperty);
		bg.add(this.jrbFace);
		bg.add(this.jrbFloor);
		bg.add(this.jrbOutLook);
		bg.add(this.jrbRoomModel);
		bg.add(this.jrbSellState);
		bg.add(this.jrbRoomModelType);
		bg.add(this.jrbRoomForm);
		
		addActionPerformedListener(this.jrbBuildingProperty);
		addActionPerformedListener(this.jrbFace);
		addActionPerformedListener(this.jrbFloor);
		addActionPerformedListener(this.jrbOutLook);
		addActionPerformedListener(this.jrbRoomModel);
		
		addActionPerformedListener(this.jrbSellState);
		addActionPerformedListener(this.jrbRoomModelType);
		addActionPerformedListener(this.jrbRoomForm);
	}

	private void addActionPerformedListener(KDRadioButton jrbSellState) {
		jrbSellState.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				setSellStateVisible();
			}
		});
	}
	
	private void setSellStateVisible() {
		if(this.jrbSellState.isSelected()){
			this.comboSellState.setSelectedItem(null);
			this.contSellState.setVisible(false);
		}else{
			this.contSellState.setVisible(true);
		}
	}
	
	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	public boolean verify() {
		/*
		 * if(!super.verify()){ return false; }
		 */
		Date orderDateFrom = (Date) this.orderDateFrom.getValue();
		Date orderDateTo = (Date) this.orderDateTo.getValue();

		BigDecimal areaFrom = (BigDecimal) this.rommAreaFrom.getValue();
		BigDecimal areaTo = (BigDecimal) this.roomAreaTo.getValue();

		BigDecimal priceFrom = (BigDecimal) this.priceFrom.getValue();
		BigDecimal priceTo = (BigDecimal) this.priceTo.getValue();

		// 先判断为空项
		if (orderDateFrom == null) {
			/*
			 * MsgBox.showInfo("开始日期不能为空。"); this.orderDateFrom.requestFocus();
			 * return false;
			 */
		} else if (orderDateTo == null) {
			/*
			 * MsgBox.showInfo("结束日期不能为空。"); this.orderDateTo.requestFocus();
			 * return false;
			 */
		}
		// 判断逻辑项
		else {
			if (orderDateTo.before(orderDateFrom)) {
				MsgBox.showInfo("日期范围不正确。");
				this.orderDateFrom.requestFocus();
				return false;
			} else if ((areaFrom != null && areaTo != null) && areaFrom.compareTo(areaTo) == 1) {
				MsgBox.showInfo("面积范围不正确。");
				this.rommAreaFrom.requestFocus();
				return false;
			} else if ((priceFrom != null && priceTo != null) && (priceFrom.compareTo(priceTo) == 1)) {
				MsgBox.showInfo("价格区间不正确。");
				this.rommAreaFrom.requestFocus();
				return false;
			}
		}
		return true;
	}

	private void setF7BuildingFloorFilter() {
		BuildingInfo building = (BuildingInfo) this.f7Building.getValue();
		if (building == null)
			return;
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("building.id", building.getId().toString()));
		this.f7BuildingFloor.setEntityViewInfo(view);
	}

	public void onInit(RptParams initParams) throws Exception {
		// TODO 自动生成方法存根

	}

	public RptParams getCustomCondition() {
		// TODO 自动生成方法存根
		RptConditionManager rm = new RptConditionManager();
		rm.recordAllStatus(this);

		/*
		 * rm.setProperty("roomAreaFrom",this.rommAreaFrom.getBigDecimalValue());
		 * rm.setProperty("roomAreaTo",this.roomAreaTo.getBigDecimalValue());
		 * rm.setProperty("priceFrom",this.priceFrom.getBigDecimalValue());
		 * rm.setProperty("priceTo",this.priceTo.getBigDecimalValue());
		 */
		Object[] o = (Object[]) f7Building.getData();
		if (o != null && o[0] != null) {
			Object[] bi = (Object[]) this.f7Building.getValue();
			List list = new ArrayList();
			for (int i = 0; i < bi.length; i++) {
				list.add(((BuildingInfo) bi[i]).getId().toString());
			}

			rm.setProperty("buildIdList", list);
		}
		/*
		 * 取得楼栋信息
		 */
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

		Set orgSet = SHEHelper.getLeafCompanyIdSet(null);

		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add(new SelectorItemInfo("*"));
		FilterInfo filter = new FilterInfo();
		/*
		 * filter.getFilterItems().add( new
		 * FilterItemInfo("sellProject.orgUnit.id",
		 * saleOrg.getId().toString()));
		 */
		filter.getFilterItems().add(new FilterItemInfo("sellProject.orgUnit.id", orgSet, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE));view.setFilter(filter);
		BuildingCollection buildingCollection = new BuildingCollection();

		List defaultList = new ArrayList();

		try {
			buildingCollection = BuildingFactory.getRemoteInstance().getBuildingCollection(view);
			for (int i = 0; i < buildingCollection.size(); i++) {
				defaultList.add(buildingCollection.get(i).getId());
			}

		} catch (BOSException e) {
			super.handUIException(e);
		}
		rm.setProperty("defaultBuild", defaultList);
		
		//销售状态comboSaleState
		if(this.comboSellState.getSelectedItem()!=null){
			rm.setProperty("sellState",this.comboSellState.getSelectedItem());
		}
		
		return rm.toRptParams();
	}

	public void setCustomCondition(RptParams params) {
		// TODO 自动生成方法存根
		RptConditionManager rm = new RptConditionManager(params);
		rm.restoreAllStatus(this);

	}

	protected void f7Building_willCommit(CommitEvent e) throws Exception {
		EntityViewInfo entityViewInfo = f7Building.getEntityViewInfo();
		f7Building.getQueryAgent().resetRuntimeEntityView();
		// SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		Set orgSet = SHEHelper.getLeafCompanyIdSet(null);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgSet, CompareType.INCLUDE));
		view.setFilter(filter);
		SellProjectCollection sellProjects = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		Set idSet = new HashSet();
		for (int i = 0; i < sellProjects.size(); i++) {
			idSet.add(sellProjects.get(i).getId().toString());
		}
		if (entityViewInfo == null || entityViewInfo.getFilter() == null) {
			entityViewInfo = new EntityViewInfo();
			filter = new FilterInfo();
			entityViewInfo.setFilter(filter);
			f7Building.setEntityViewInfo(entityViewInfo);
		}
		
		entityViewInfo.getFilter().getFilterItems().add(new FilterItemInfo("sellProject.id", idSet, CompareType.INCLUDE));
		entityViewInfo.getFilter().getFilterItems().add(new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE));
	}

	protected void f7Building_willShow(SelectorEvent e) throws Exception {
		EntityViewInfo entityViewInfo = f7Building.getEntityViewInfo();
		f7Building.getQueryAgent().resetRuntimeEntityView();
		// SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();

		Set orgSet = SHEHelper.getLeafCompanyIdSet(null);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgSet, CompareType.INCLUDE));
		view.setFilter(filter);
		SellProjectCollection sellProjects = SellProjectFactory.getRemoteInstance().getSellProjectCollection(view);
		Set idSet = new HashSet();
		for (int i = 0; i < sellProjects.size(); i++) {
			idSet.add(sellProjects.get(i).getId().toString());
		}
		if (entityViewInfo == null || entityViewInfo.getFilter() == null) {
			entityViewInfo = new EntityViewInfo();
			filter = new FilterInfo();
			entityViewInfo.setFilter(filter);
			f7Building.setEntityViewInfo(entityViewInfo);
		}
		
		entityViewInfo.getFilter().getFilterItems().add(new FilterItemInfo("sellProject.id", idSet, CompareType.INCLUDE));
		entityViewInfo.getFilter().getFilterItems().add(new FilterItemInfo("sellProject.isForSHE", Boolean.TRUE));

		/*
		 * EntityViewInfo entityViewInfo = f7Building.getEntityViewInfo();
		 * if(entityViewInfo==null) entityViewInfo = new EntityViewInfo();
		 * 
		 * FilterInfo filterInfo = new FilterInfo();
		 * filterInfo.appendFilterItem("floorCount",new Integer(3).toString());
		 * 
		 * entityViewInfo.setFilter(filterInfo);
		 * f7Building.setEntityViewInfo(entityViewInfo);
		 */

	}

	public void onLoad() throws Exception {
		super.onLoad();

		// txts[i].setHorizontalAlignment(KDFormattedTextField.RIGHT);
		/*
		 * txts[i].setRemoveingZeroInDispaly(false);
		 * txts[i].setMaximumValue(FDCHelper.MAX_VALUE);
		 */
		this.priceFrom.setMinimumValue(FDCHelper.ZERO);
		this.rommAreaFrom.setMinimumValue(FDCHelper.ZERO);
		this.priceTo.setMinimumValue(FDCHelper.ZERO);
		this.roomAreaTo.setMinimumValue(FDCHelper.ZERO);

		this.priceFrom.setPrecision(2);
		this.priceTo.setPrecision(2);
		this.rommAreaFrom.setPrecision(2);
		this.roomAreaTo.setPrecision(2);

		this.priceFrom.setSupportedEmpty(true);
		this.priceTo.setSupportedEmpty(true);
		this.rommAreaFrom.setSupportedEmpty(true);
		this.roomAreaTo.setSupportedEmpty(true);

		this.priceFrom.setRemoveingZeroInEdit(false);
		this.priceTo.setRemoveingZeroInEdit(false);
		this.rommAreaFrom.setRemoveingZeroInEdit(false);
		this.roomAreaTo.setRemoveingZeroInEdit(false);

		this.priceFrom.setRemoveingZeroInDispaly(false);
		this.priceTo.setRemoveingZeroInDispaly(false);
		this.rommAreaFrom.setRemoveingZeroInDispaly(false);
		this.roomAreaTo.setRemoveingZeroInDispaly(false);

		this.priceFrom.setHorizontalAlignment(JTextField.RIGHT);
		this.priceTo.setHorizontalAlignment(JTextField.RIGHT);
		this.rommAreaFrom.setHorizontalAlignment(JTextField.RIGHT);
		this.roomAreaTo.setHorizontalAlignment(JTextField.RIGHT);

		/*
		 * this.priceFrom.setRemoveingZeroInDispaly(false);
		 * this.priceTo.setRemoveingZeroInDispaly(false);
		 * this.rommAreaFrom.setRemoveingZeroInDispaly(false);
		 * this.roomAreaTo.setRemoveingZeroInDispaly(false);
		 */

		// txts[i].setHorizontalAlignment(JTextField.RIGHT);
		this.f7BuildingFloor.setEnabledMultiSelection(true);
		this.f7ModelType.setEnabledMultiSelection(true);
		this.f7BuildingProperty.setEnabledMultiSelection(true);
		this.f7RoomModel.setEnabledMultiSelection(true);
		this.f7EyeSight.setEnabledMultiSelection(true);
		this.f7Direction.setEnabledMultiSelection(true);
		this.f7roomForm.setEnabledMultiSelection(true);
		this.comboSellState.setSelectedItem(null);
		
		//TODO 这些过滤条件暂不加,暂设为不可见
		this.kDLabelContainer6.setVisible(false);
		this.kDLabelContainer8.setVisible(false);
		this.kDLabelContainer9.setVisible(false);
		this.kDLabelContainer10.setVisible(false);
		this.kDLabelContainer11.setVisible(false);
		this.kDLabelContainer12.setVisible(false);
		this.kDLabelContainer14.setVisible(false);
		
		setSellStateVisible();
	}
}