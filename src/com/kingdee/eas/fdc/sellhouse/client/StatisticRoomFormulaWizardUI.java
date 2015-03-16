package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import com.kingdee.bos.ctrl.extendcontrols.IFormatter;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.StatisticRoomTypeEnum;
import com.kingdee.eas.fi.rpt.client.FormulaWizardUI;

public class StatisticRoomFormulaWizardUI extends FormulaWizardUI
{
	private KDBizPromptBox buildingProperty;

	private KDComboBox statisticRoomTypeEnum;
	
	private KDBizPromptBox sellProject;

	private KDBizPromptBox building;

	private KDDatePicker beginDate;

	private KDDatePicker endDate;
	
	private KDComboBox accessorialProperty;
	
	private KDComboBox preBiz;
	
	private KDComboBox orderState;
	
	private KDBizPromptBox productType;

	public StatisticRoomFormulaWizardUI() throws Exception
	{
		super();
	}
	
	private void addListenerToStatisticRoomType()
	{
		if(statisticRoomTypeEnum.getSelectedItem() == null)
			return;
		String type = statisticRoomTypeEnum.getSelectedItem().toString();
		if(StatisticRoomTypeEnum.totalSum.getAlias().equals(type) || StatisticRoomTypeEnum.totalBuildingArea.getAlias().equals(type)
				|| StatisticRoomTypeEnum.totalRoomArea.getAlias().equals(type) || StatisticRoomTypeEnum.totalStandardTotalPrice.getAlias().equals(type)
				|| StatisticRoomTypeEnum.totalBaseTotalPrice.getAlias().equals(type))
		{
			preBiz.setEnabled(false);
			preBiz.setSelectedItem(null);
		}
		else
		{
			preBiz.setEnabled(true);
		}
		
		if(StatisticRoomTypeEnum.sellSum.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellBuildingArea.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellRoomArea.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.compensateBuildingArea.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.compensateRoomArea.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellBaseTotalPrice.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellStandardTotalPrice.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellDealTotalPrice.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellContractTotalPrice.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellCompensatePrice.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellSaleTotalPrice.getAlias().equalsIgnoreCase(type)
				||StatisticRoomTypeEnum.sellGathering.getAlias().equalsIgnoreCase(type))
		{
			orderState.setEnabled(false);
			orderState.setSelectedIndex(0);
		}
		else
		{
			orderState.setEnabled(true);
		}
		
		if(type.equalsIgnoreCase(StatisticRoomTypeEnum.arrearageSum.getAlias()) || type.equalsIgnoreCase(StatisticRoomTypeEnum.totalBaseTotalPrice.getAlias())
				|| type.equalsIgnoreCase(StatisticRoomTypeEnum.totalBuildingArea.getAlias()) || type.equalsIgnoreCase(StatisticRoomTypeEnum.totalRoomArea.getAlias())
				|| type.equalsIgnoreCase(StatisticRoomTypeEnum.totalStandardTotalPrice.getAlias()) || type.equalsIgnoreCase(StatisticRoomTypeEnum.totalSum.getAlias()))
		{
			beginDate.setEnabled(false);
			endDate.setEnabled(false);
			orderState.setEnabled(false);
			orderState.setSelectedIndex(0);
			
			beginDate.setValue(null);
			endDate.setValue(null);
		}
		else
		{
			beginDate.setEnabled(true);
			endDate.setEnabled(true);
			orderState.setEnabled(true);
		}
	}

	protected void onOpen()
	{

		orderState = (KDComboBox)this.getParamComponent("orderState");
		
		statisticRoomTypeEnum = (KDComboBox) this.getParamComponent("type");
		
		sellProject = (KDBizPromptBox)this.getParamComponent("sellProject");
		
		productType = (KDBizPromptBox)this.getParamComponent("productType");
		productType.setEnabledMultiSelection(true);
		
		Set orgSet = SHEHelper.getLeafCompanyIdSet(null);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", orgSet, CompareType.INCLUDE));
		view.setFilter(filter);
	
		this.sellProject.setEntityViewInfo(view);
		
		
		building = (KDBizPromptBox) this.getParamComponent("building");
		building.setEnabledMultiSelection(true);
		building.setEnabled(false);
		
		buildingProperty = (KDBizPromptBox) this.getParamComponent("buildingProperty");
		buildingProperty.setEnabledMultiSelection(true);
		
		preBiz = (KDComboBox)this.getParamComponent("preBiz");
		
		beginDate = (KDDatePicker) this.getParamComponent("begingDate");

		endDate = (KDDatePicker) this.getParamComponent("endDate");
		
		EntityViewInfo bpView = new EntityViewInfo();
		FilterInfo bpFilter = new FilterInfo();
		bpView.setFilter(bpFilter);
		bpFilter.getFilterItems().add(new FilterItemInfo("level", new Integer(2)));

		this.buildingProperty.setEntityViewInfo(bpView);

		//改变显示格式
		final IFormatter displayFormatter = buildingProperty.getDisplayFormatter();

		IFormatter format = new IFormatter()
		{
			public void applyPattern(String pattern)
			{
				displayFormatter.applyPattern(pattern);
			}
			public String valueToString(Object o)
			{
				if (displayFormatter.valueToString(o) != null)
				{
					return displayFormatter.valueToString(o).replaceAll("!",
							"\\.");
				}
				return null;
			}
		};
		buildingProperty.setDisplayFormatter(format);

		
		this.initAllListener();
		
		this.addListenerToStatisticRoomType();
	}
	
	private void initAllListener() 
	{
		this.statisticRoomTypeEnum.addItemListener(new ItemListener()
				{

					public void itemStateChanged(ItemEvent arg0)
					{
						String type = statisticRoomTypeEnum.getSelectedItem().toString();
						if(StatisticRoomTypeEnum.totalSum.getAlias().equals(type) || StatisticRoomTypeEnum.totalBuildingArea.getAlias().equals(type)
								|| StatisticRoomTypeEnum.totalRoomArea.getAlias().equals(type) || StatisticRoomTypeEnum.totalStandardTotalPrice.getAlias().equals(type)
								|| StatisticRoomTypeEnum.totalBaseTotalPrice.getAlias().equals(type))
						{
							preBiz.setEnabled(false);
							preBiz.setSelectedItem(null);
						}
						else
						{
							preBiz.setEnabled(true);
						}
						
						if(StatisticRoomTypeEnum.sellSum.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellBuildingArea.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellRoomArea.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.compensateBuildingArea.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.compensateRoomArea.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellBaseTotalPrice.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellStandardTotalPrice.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellDealTotalPrice.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellContractTotalPrice.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellCompensatePrice.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellSaleTotalPrice.getAlias().equalsIgnoreCase(type)
								||StatisticRoomTypeEnum.sellGathering.getAlias().equalsIgnoreCase(type))
						{
							orderState.setEnabled(false);
							orderState.setSelectedIndex(0);
						}
						else
						{
							orderState.setEnabled(true);
						}
						
						if(type.equalsIgnoreCase(StatisticRoomTypeEnum.arrearageSum.getAlias()) || type.equalsIgnoreCase(StatisticRoomTypeEnum.totalBaseTotalPrice.getAlias())
								|| type.equalsIgnoreCase(StatisticRoomTypeEnum.totalBuildingArea.getAlias()) || type.equalsIgnoreCase(StatisticRoomTypeEnum.totalRoomArea.getAlias())
								|| type.equalsIgnoreCase(StatisticRoomTypeEnum.totalStandardTotalPrice.getAlias()) || type.equalsIgnoreCase(StatisticRoomTypeEnum.totalSum.getAlias()))
						{
							beginDate.setEnabled(false);
							endDate.setEnabled(false);
							orderState.setEnabled(false);
							orderState.setSelectedIndex(0);
							
							beginDate.setValue(null);
							endDate.setValue(null);
						}
						else
						{
							beginDate.setEnabled(true);
							endDate.setEnabled(true);
							preBiz.setEnabled(true);
						}
						
						
					}
			
				});

		
		this.sellProject.addDataChangeListener(new DataChangeListener() 
		{
			public void dataChanged(DataChangeEvent arg0) 
			{
				building.setValue(null);
				building.setData(null);
				
				SellProjectInfo sp = (SellProjectInfo) sellProject.getValue();
				if(sp != null)
				{
					building.setEnabled(true);
					
					String id = sp.getId().toString();
					
					EntityViewInfo view = new EntityViewInfo();
					FilterInfo filter = new FilterInfo();
					view.setFilter(filter);
					
					filter.getFilterItems().add(new FilterItemInfo("sellProject.id",id));
					
					building.setEntityViewInfo(view);
				}
				else
				{
					building.setEnabled(false);
				}
			}
		});
		
	}

	/**
	 * 
	 */

	private static final long serialVersionUID = 4134892076882436686L;

}
