package com.kingdee.eas.fdc.tenancy.client;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.tenancy.AgencyCollection;
import com.kingdee.eas.fdc.tenancy.AgencyFactory;
import com.kingdee.eas.fdc.tenancy.AgencyInfo;
import com.kingdee.eas.fdc.tenancy.BuildigRentEntrysInfo;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysCollection;
import com.kingdee.eas.fdc.tenancy.BuildingRoomEntrysFactory;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyRentBillInfo;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.util.UuidException;

public class TenancyClientHelper {
	
	private TenancyClientHelper() {
	}
	
	/**
	 * 获得2个日期间的月份数量
	 * */
	public static BigDecimal getMonthesBetween(Date startDate, Date endDate) {
		if (!startDate.before(endDate)) {
			return FDCHelper._ONE;
		}
		
		BigDecimal monthes = FDCHelper.ZERO;
		Date tmpDate = startDate;
		while (!tmpDate.after(endDate)) {
			monthes = monthes.add(FDCHelper.ONE);
			tmpDate = TenancyHelper.addCalendar(tmpDate, Calendar.MONTH, 1);
		}
		
		if (tmpDate.compareTo(endDate) > 0) {
			Date lastBeginDate = TenancyHelper.addCalendar(tmpDate, Calendar.MONTH, -1);
			Calendar lastBeginCal = Calendar.getInstance();
			lastBeginCal.setTime(lastBeginDate);
			int days = getMonthDays(lastBeginCal);
			int diffDays = FDCDateHelper.getDiffDays(lastBeginDate, endDate);
			
			BigDecimal decDays = new BigDecimal(new Integer(days).toString());
			BigDecimal decDiffDays = new BigDecimal(new Integer(diffDays)
					.toString());
			
			monthes = monthes.add(decDiffDays
					.divide(decDays, 2, BigDecimal.ROUND_HALF_UP));
		}
		return monthes;
	}
	
	/**
	 * 获得该日期所在月的总天数
	 * */
	private static int getMonthDays(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		month = month + 1;//jdk API实现以0作为起始下标,需要加1
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return 31;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		case 2:
			return (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0) ? 29
					: 28);
		}
		return 0;
	}
	
	/**
	 * 隐藏按钮，工具栏和餐单栏同步隐藏
	 * @param itemActions
	 * @author laiquan_luo
	 */
	public static void hideButton(ItemAction[] itemActions)
	{
		if(itemActions == null)
			return;
		for(int i = 0;i<itemActions.length; i++)
		{
			if(itemActions[i] instanceof ItemAction)
			{
				itemActions[i].setVisible(false);
			}
		}
	}
	
	
	public static void main(String[] args) {
//		testGetMonthList();
//		testGetLeaseList();
		
//		int s = FDCDateHelper.getDiffDays(FDCDateHelper.stringToDate("2008-01-01"), FDCDateHelper.stringToDate("2008-01-01"));
		
//		System.out.println(addCalendar(FDCDateHelper.stringToDate("2007-01-31"), Calendar.MONTH, 1));
//		System.out.println(s);
	}
	
	/**
	 * 构建中介机构的树
	 * */
	public static TreeModel getAgencyTree() throws BOSException{
		AgencyCollection agencys = AgencyFactory.getRemoteInstance().getAgencyCollection();
		KDTreeNode root = new KDTreeNode("中介机构");
		TreeModel tree = new DefaultTreeModel(root);
		
		for(int i=0; i<agencys.size(); i++){
			AgencyInfo agency = agencys.get(i);
			KDTreeNode tmpNode = new KDTreeNode(agency.getName());
			tmpNode.setUserObject(agency);
			root.add(tmpNode);
		}
		return tree;
	}
	

	/**
	 * 单元格事件，解决单选框鼠标单击不能选中问题
	 */
	public static void tableName_activeCellChanged(KDTActiveCellEvent e,KDTable table)
	{
		int rowCount = e.getRowIndex();
		int cloumnCount = e.getColumnIndex();
		if(rowCount == -1 || cloumnCount ==-1)
		{
			return;
		}
		Object object = table.getRow(rowCount).getCell(cloumnCount).getValue();
		if (object instanceof Boolean) {
			if(object.equals(Boolean.valueOf(false)) && table.getRow(rowCount).getCell(cloumnCount).getStyleAttributes().isLocked()==false)
			{
				table.getRow(rowCount).getCell(cloumnCount).setValue(Boolean.valueOf(true));
			}if(object.equals(Boolean.valueOf(true)) && table.getRow(rowCount).getCell(cloumnCount).getStyleAttributes().isLocked()==true)
			{
				table.getRow(rowCount).getCell(cloumnCount).setValue(Boolean.valueOf(true));
			}if(object.equals(Boolean.valueOf(false)) && table.getRow(rowCount).getCell(cloumnCount).getStyleAttributes().isLocked()==true)
			{
				table.getRow(rowCount).getCell(cloumnCount).setValue(Boolean.valueOf(false));
			}
			if(object.equals(Boolean.valueOf(true)) && table.getRow(rowCount).getCell(cloumnCount).getStyleAttributes().isLocked()==false)
			{
				table.getRow(rowCount).getCell(cloumnCount).setValue(Boolean.valueOf(false));
			}
		}
	}
	
	/**
	 * 字符窜是否包含在数组中
	 */
	public static boolean isInclude(String str,List list)
	{
		boolean result = false;
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i)!=null && str!=null)
			{
				if(str.equals(list.get(i).toString()))
				{
					result = true;
					return result;
				}else
				{
					result = false;
				}
			}			
		}
		return result;
	}
	
	//定租单用到
	public static TenancyRentBillInfo getBuildingRentEntrys(String TenancyBillID) throws EASBizException, BOSException, UuidException
	{
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("project.*");
		sels.add("creator.*");
		sels.add("buildingEntrys.*");
		sels.add("buildingEntrys.buildings.*");
		sels.add("roomEntrys.*");
		sels.add("roomEntrys.rooms.*");
		TenancyRentBillInfo tenBill = (TenancyRentBillInfo) TenancyRentBillFactory.getRemoteInstance().getValue(
				new ObjectUuidPK(BOSUuid.read(TenancyBillID)), sels);
		//BuildigRentEntrysCollection buildingRentEntrysColl = tenBill.getBuildingEntrys();
		return tenBill;
	}
	
	//楼栋对应的房间分录
	//目前就定租单用了
	public static BuildingRoomEntrysCollection getBuildingRoomEntrys(BuildigRentEntrysInfo buildingRentInfo) throws BOSException
	{
		EntityViewInfo view1 = new EntityViewInfo();
		view1.getSelector().add("*");
		view1.getSelector().add("rooms.id");
		view1.getSelector().add("rooms.sellState");
		view1.getSelector().add("rooms.tenancyState");
		view1.getSelector().add("rooms.isActualAreaAudited");
		view1.getSelector().add("rooms.actualBuildingArea");
		view1.getSelector().add("rooms.roomArea");
		view1.getSelector().add("rooms.actualRoomArea");
		view1.getSelector().add("rooms.buildingArea");
		view1.getSelector().add("rooms.building.name");
		view1.getSelector().add("rooms.building.id");
		view1.getSelector().add("rooms.building.number");
		view1.getSelector().add("rooms.roomModel.name");
		FilterInfo filter1 = new FilterInfo();
		filter1.getFilterItems().add(
				new FilterItemInfo("head.id",buildingRentInfo.getId().toString(),CompareType.EQUALS));
		view1.setFilter(filter1);
	BuildingRoomEntrysCollection entrys = BuildingRoomEntrysFactory.getRemoteInstance().getBuildingRoomEntrysCollection(view1);
	return entrys;
	}
	
	//选择的楼栋对应的房间
	//目前就定租单编辑界面用了
	public static RoomCollection getRooms(Object[] tempBuilding) throws BOSException
	{
		BuildingInfo[] building = new BuildingInfo[tempBuilding.length];
		for (int i = 0; i < tempBuilding.length; i++) {
			building[i] = (BuildingInfo) tempBuilding[i];
		}
		
		//找出选择的楼栋ID，便于确认表格中的房间是否属于该楼栋
		Set idSet = new HashSet();
		for (int i = 0; i < building.length; i++) {
			idSet.add(building[i].getId().toString());
		}
		
		//查询选择的楼栋下的房间信息
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("*");
		view.getSelector().add("building.name");
		view.getSelector().add("building.id");
		view.getSelector().add("building.number");
		view.getSelector().add("building.sellProject.id");
		view.getSelector().add("building.sellProject.name");
		view.getSelector().add("building.sellProject.number");
		view.getSelector().add("building.subarea.name");
		view.getSelector().add("building.subarea.number");
		view.getSelector().add("buildingProperty.name");
		view.getSelector().add("roomModel.name");
		view.getSelector().add("sellOrder.name");
		view.getSelector().add("direction.name");
		view.getSelector().add("sight.name");
		view.getSorter().add(new SorterItemInfo("number"));
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(
				new FilterItemInfo("building.id", idSet, CompareType.INCLUDE));
		RoomCollection rooms = RoomFactory.getRemoteInstance()
		.getRoomCollection(view);
		return rooms;
	}
	
	/**
	 * 创建带文本编辑框的cellEditor
	 * @param length
	 *            文本框最大输入长度
	 * @param editable
	 *            是否可编辑
	 * */
	public static KDTDefaultCellEditor createTxtCellEditor(int length, boolean editable) {
		KDTextField textField = new KDTextField();
		textField.setMaxLength(length);
		textField.setEditable(editable);
		KDTDefaultCellEditor txtEditor = new KDTDefaultCellEditor(textField);
		return txtEditor;
	}
	
	/**
	 * 创建金额编辑的cellEditor
	 * @param editable 可否编辑
	 * */
	public static ICellEditor createFormattedCellEditor(boolean editable) {
		KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		formattedTextField.setEditable(editable);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		formattedTextField.setNegatived(false);
		ICellEditor cellEditor = new KDTDefaultCellEditor(formattedTextField);
		return cellEditor;
	}
	
	/**
	 * 创建Combo形式的cellEditor
	 * */
	public static KDTDefaultCellEditor createComboCellEditor(List enumList) {
		KDComboBox comboField = new KDComboBox();
		for (int i = 0; i < enumList.size(); i++) {
			comboField.addItem(enumList.get(i));
		}
//		comboField.setEditable(editable);
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
	}
	
	/**
	 * 创建Combo形式的cellEditor 并且默认选 中第一个
	 * */
	public static KDTDefaultCellEditor createComboCellEditorAndSelectOne(List enumList) {
		KDComboBox comboField = new KDComboBox();
		for (int i = 0; i < enumList.size(); i++) {
			comboField.addItem(enumList.get(i));
		}
		comboField.setSelectedIndex(1);
		KDTDefaultCellEditor comboEditor = new KDTDefaultCellEditor(comboField);
		return comboEditor;
	}
}
