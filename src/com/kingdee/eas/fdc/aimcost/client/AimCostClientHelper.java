package com.kingdee.eas.fdc.aimcost.client;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.logging.Logger;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.IMeasureCost;
import com.kingdee.eas.fdc.aimcost.MeasureCostCollection;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MeasureStageCollection;
import com.kingdee.eas.fdc.basedata.MeasureStageFactory;
import com.kingdee.eas.fdc.basedata.MeasureStageInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.AbortException;
import com.kingdee.eas.util.client.EASResource;

public class AimCostClientHelper {
	public final static String resource = "com.kingdee.eas.fdc.aimcost.AimCostResource";

	public static String getRes(String key) {
		return EASResource.getString(resource, key);
	}
	
	public static void setTotalCostRow(KDTable table,String[]columns) throws EASBizException, BOSException{
		if(table==null||columns==null||columns.length==0){
			return;
		}
		boolean addTotalRow=false;
		HashMap param = FDCUtils.getDefaultFDCParam(null,null);
		if(param.get(FDCConstants.FDC_PARAM_TOTALCOST)!=null){
			addTotalRow = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_TOTALCOST).toString()).booleanValue();
		}
		if(!addTotalRow){
			return;
		}
		
		KDTFootManager footRowManager= table.getFootManager();
		IRow footRow = null;
		if(footRowManager==null){
			String total=getRes("totalCost");
			footRowManager = new KDTFootManager(table);
			footRowManager.addFootView();
			table.setFootManager(footRowManager);
			footRow= footRowManager.addFootRow(0);
			footRow.setUserObject("FDC_PARAM_TOTALCOST");
			footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
			table.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
			table.getIndexColumn().setWidth(60);
			footRow.getStyleAttributes().setBackground(FDCTableHelper.totalColor);
			//设置到第一个可视行
			footRowManager.addIndexText(0, total);
		}else{
			footRow=table.getFootRow(0);
			if(footRow.getUserObject()==null||!footRow.getUserObject().equals("FDC_PARAM_TOTALCOST")){
				footRow=table.addFootRow(1);
			};
		}

		HashMap map=new HashMap();
		for(int i=0;i<columns.length;i++){
			map.put(columns[i], FDCHelper.ZERO);
		}
		for(int i=0;i<table.getRowCount();i++){
			IRow row=table.getRow(i);
			if(row.getTreeLevel()!=0){
				continue;
			}
			for(Iterator iter=map.keySet().iterator();iter.hasNext();){
				String key=(String)iter.next();
				BigDecimal amount=FDCHelper.toBigDecimal(row.getCell(key).getValue());
				amount=amount.add((BigDecimal)map.get(key));
				map.put(key, amount);
			}
		}
		for(Iterator iter=map.keySet().iterator();iter.hasNext();){
			String key=(String)iter.next();
			BigDecimal amount=FDCHelper.toBigDecimal(map.get(key));
			footRow.getCell(key).setValue(amount);
		}
		
		
	}

	public static MeasureStageInfo getLastMeasureStage(CurProjectInfo curProject,boolean isAimMeasure) throws EASBizException, BOSException{
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("measureStage.number");
		view.getSelector().add("measureStage.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("project.id",curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		if(isAimMeasure){
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.NOTEQUALS));
		}
		MeasureCostCollection measureCostCollection = MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
		if (measureCostCollection != null && measureCostCollection.size()==1){
			MeasureCostInfo measureCostInfo = measureCostCollection.get(0);
			if(measureCostInfo.getMeasureStage()!=null){
				MeasureStageInfo lastStageInfo = measureCostInfo.getMeasureStage();
				return lastStageInfo;
			}
		}
		return null;
	}
	
 public static MeasureStageInfo getLastMeasureStageForXuHui(CurProjectInfo curProject,boolean isAimMeasure) throws EASBizException, BOSException{
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("measureStage.number");
		view.getSelector().add("measureStage.name");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("project.id",curProject.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
//		filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
		if(isAimMeasure){
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.EQUALS));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("isAimMeasure",Boolean.TRUE,CompareType.NOTEQUALS));
		}
		MeasureCostCollection measureCostCollection = MeasureCostFactory.getRemoteInstance().getMeasureCostCollection(view);
		AimCostClientHelper.sortCollection(measureCostCollection,"measureStage.number",false);
		if (measureCostCollection != null && measureCostCollection.size() > 0 ){
			MeasureCostInfo measureCostInfo = measureCostCollection.get(0);
			if(measureCostInfo.getMeasureStage()!=null){
				MeasureStageInfo lastStageInfo = measureCostInfo.getMeasureStage();
				return lastStageInfo;
			}
		}
		return null;
	}
	
	public static void removeBeforeStage(KDComboBox comboMeasureStage, MeasureStageInfo lastStageInfo) {
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.appendFilterItem("isEnabled", Boolean.TRUE);
		MeasureStageCollection coll = null;
		 try {
			 coll = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		
		Integer  tagerNumber = null;
		try{
			tagerNumber = Integer.valueOf(lastStageInfo.getNumber());
		} catch(NumberFormatException e){
			Logger.error(e,"测算阶段的编码只能是数字！");
			return;
		}
		Integer  number = null;
		for(int i = 0 ; i < coll.size();i++){
			MeasureStageInfo info = coll.get(i);
			try{
				number =  Integer.valueOf(info.getNumber());
			} catch(NumberFormatException e){
				Logger.error(e,"测算阶段的编码只能是数字！");
				return;
			}
			if(number.compareTo(tagerNumber) <=  0){
				comboMeasureStage.removeItem(info);
			}
		}
		
	}

	public static void isCanAddNew(AimMeasureCostListUI aimMeasureCostListUI, CurProjectInfo curProj) throws EASBizException, BOSException {
		//如果是最后一个阶段，并且有了审批版本 则不让新增
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("isEnabled", Boolean.TRUE);
		view.setFilter(filter);
		MeasureStageCollection coll = MeasureStageFactory.getRemoteInstance().getMeasureStageCollection(view);
		
		IMeasureCost measureCost = MeasureCostFactory.getRemoteInstance();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",curProj.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
		filter.getFilterItems().add(new FilterItemInfo("measureStage.id",coll.get(coll.size()-1).getId().toString()));
		if(measureCost.exists(filter)){
			 FDCMsgBox.showWarning(aimMeasureCostListUI,"已存在最后阶段的成本测算，不能再新增！");
			 throw new AbortException();
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("project.id",curProj.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SAVED));
		filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.SUBMITTED));
		filter.setMaskString("#0 and (#1 or #2)");
		 if(measureCost.exists(filter)){
			 FDCMsgBox.showWarning(aimMeasureCostListUI,"请先处理未审批的单据！");
			 throw new AbortException();
		 }
	}
	
	/**
	 * 对集合按某字段进行排序,该字段的值需要是Comparable类型的.
	 * @param cols 要排序的集合
	 * @param sortColName 要排序的字段
	 * @param sortType 是否正序
	 * */
	public static void sortCollection(IObjectCollection cols, final String sortColName, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	
	/**
	 * 获取某对象的属性值，支持及联获取.key可以是 room.number 这种格式
	 * */
	public static Object getValue(IObjectValue value, String key){
		int in = key.indexOf(".");
		if(in == -1){
			return value.get(key);
		}else{
			Object tmp = value.get(key.substring(0, in));
			if(tmp != null  &&  tmp instanceof IObjectValue){
				return getValue((IObjectValue) tmp, key.substring(in + 1, key.length()));
			}
		}
		return null;
	}
}
