/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.ShareStyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.VerticalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitCollection;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;

/**
 * output class name
 */
public class BatchRoomPriceAdjustUI extends AbstractBatchRoomPriceAdjustUI {
	private static final Logger logger = CoreUIObject.getLogger(BatchRoomPriceAdjustUI.class);
	
	/**
	 * 是否清空定价记录
	 */
	private boolean isClearData = true;
	
	private int[] paramArray = null;

	public BatchRoomPriceAdjustUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}

	public void onLoad() throws Exception {
		this.buildingTable.checkParsed();
		super.onLoad();
		paramArray = (int[])this.getUIContext().get("paramArray");		
		this.createBatchAdjustTable();
	}

	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		super.btnYes_actionPerformed(e);
		
		this.isClearData = false;
		this.destroyWindow();
}

	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);

		this.destroyWindow();
	}
	
	public boolean destroyWindow() {
		if(isClearData){
			this.getUIContext().put("entryCollection", null);
		}
		return super.destroyWindow();
	}

	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
	
	protected void buildingTable_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			int rowIndex = e.getRowIndex();
			int colIndex = e.getColIndex();
			int type = e.getType();
			
			Object adjustCellValObj = null;
			Object adjustCellObj = null;
			
		
			//0-表头，1-表体
			if(type == 0){
				adjustCellValObj = this.buildingTable.getHeadRow(rowIndex).getCell(colIndex).getValue();
				adjustCellObj = this.buildingTable.getHeadRow(rowIndex).getCell(colIndex).getUserObject();
			}
			else{
				adjustCellValObj = this.buildingTable.getCell(rowIndex, colIndex).getValue();
				adjustCellObj = this.buildingTable.getCell(rowIndex, colIndex).getUserObject();
			}
			
			//【按列调整】和【按层调整】的单元格值是字符串，所以加上字符串对象判断
			if(adjustCellValObj!=null && adjustCellObj!=null && adjustCellValObj instanceof String){
				//遍历列，取出现单价；【按列调整】单元格的值对象为价格列的key
				if("整列调整".equals(adjustCellValObj.toString())){
					RoomPriceAdjustEntryCollection priceEntry = new RoomPriceAdjustEntryCollection();
					for(int i=0; i<this.buildingTable.getRowCount(); i++){
						//价格列
						ICell priceCell = this.buildingTable.getCell(i, adjustCellObj.toString());
						//如果价格列的对象不为空且为单价列，则加入调整集合中
						if(priceCell!=null && priceCell.getUserObject()!=null){
							//取价格列的名称作判断
							String priceName = (String)this.buildingTable.getCell(i, priceCell.getColumnIndex()-1).getUserObject();
							if("newSinglePrice".equals(priceName)){
								priceEntry.add((RoomPriceAdjustEntryInfo)priceCell.getUserObject());
							}
						}
					}
					if(priceEntry.size() > 0){
						Boolean isSelectAdjustSoldRoom = (Boolean)this.getUIContext().get("isSelectAdjustSoldRoom");
						Boolean isAdjustSoldRoom = (Boolean)this.getUIContext().get("isAdjustSoldRoom");
						String colNumber = null;
						colNumber = this.buildingTable.getHeadRow(1).getCell(colIndex).getValue().toString();
						BatchAdjustColUI.showUI(this, isSelectAdjustSoldRoom.booleanValue(), isAdjustSoldRoom.booleanValue(), 
								priceEntry, paramArray,colNumber);
						
						this.buildingTable.removeHeadRows();
						this.buildingTable.removeRows();
						this.buildingTable.removeColumns();
						this.createBatchAdjustTable();
					}
				}
				//遍历行，取出现单价；【按层调整】单元格的值对象为价格列的行号
				else if("按层调整".equals(adjustCellValObj.toString())){
					RoomPriceAdjustEntryCollection priceEntry = new RoomPriceAdjustEntryCollection();
					Integer priceRowIndex = (Integer)adjustCellObj;
					for(int i=0; i<this.buildingTable.getColumnCount(); i++){
						ICell currentCell = this.buildingTable.getCell(priceRowIndex.intValue(), i);
						if(currentCell!=null && currentCell.getUserObject() instanceof RoomPriceAdjustEntryInfo){
							priceEntry.add((RoomPriceAdjustEntryInfo)currentCell.getUserObject());
						}
					}
					if(priceEntry.size() > 0){
						Boolean isSelectAdjustSoldRoom = (Boolean)this.getUIContext().get("isSelectAdjustSoldRoom");
						Boolean isAdjustSoldRoom = (Boolean)this.getUIContext().get("isAdjustSoldRoom");
					
						String rowNumber = null;
						rowNumber = this.buildingTable.getRow(rowIndex).getCell(0).getValue().toString();
						String colNumber = null;
						colNumber = this.buildingTable.getHeadRow(1).getCell(colIndex+1).getValue().toString();
					
						BatchAdjustRowUI.showUI(this, isSelectAdjustSoldRoom.booleanValue(), isAdjustSoldRoom.booleanValue(),
								priceEntry, paramArray,rowNumber,colNumber);
						
						this.buildingTable.removeHeadRows();
						this.buildingTable.removeRows();
						this.buildingTable.removeColumns();
						this.createBatchAdjustTable();
					}
				}
			}
		}
	}

	/**
	 * 显示批量调整界面
	 * @param ui
	 * @param isSelectAdjustSoldRoom - 是否选择已售房源是否参与调整
	 * @param isAdjustSoldRoom - 已售房源是否参与调整
	 * @param entryCollection - 选中的楼栋房间集合
	 * @param paramArray - 价格的保留位数和取整方式
	 * 
	 * @return 调整后的房间集合
	 * @throws UIException
	 */
	public static RoomPriceAdjustEntryCollection showUI(CoreUI ui, boolean isSelectAdjustSoldRoom, boolean isAdjustSoldRoom,
			RoomPriceAdjustEntryCollection entryCollection, int[] paramArray) throws UIException {
		//保存选中的楼栋房间
		UIContext uiContext = new UIContext(ui);
		uiContext.put("entryCollection", entryCollection);
		uiContext.put("isSelectAdjustSoldRoom", new Boolean(isSelectAdjustSoldRoom));
		uiContext.put("isAdjustSoldRoom", new Boolean(isAdjustSoldRoom));
		uiContext.put("paramArray", paramArray);

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(BatchRoomPriceAdjustUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		RoomPriceAdjustEntryCollection retEntryCollection = (RoomPriceAdjustEntryCollection) uiWindow
			.getUIObject().getUIContext().get("entryCollection");
		return retEntryCollection;
	}
	
	/**
	 * 创建楼栋房间表格
	 * @throws BOSException
	 */
	private void createBatchAdjustTable() throws BOSException{
		//隐藏行号
		this.buildingTable.getStyleAttributes().setLocked(true);
		this.buildingTable.getIndexColumn().getStyleAttributes().setHided(true);
		
		RoomPriceAdjustEntryCollection entryCollection = (RoomPriceAdjustEntryCollection) this
			.getUIContext().get("entryCollection");
		
		Map buildingMaxFloor = new HashMap(); // 楼栋id和最大楼层的映射
		Map buildingMinFloor = new HashMap();
		Map buildingMaxNum = new HashMap(); // 楼栋id和房间的最大序列号的映射
		Map buildingMinNum = new HashMap();
		for (int i = 0; i < entryCollection.size(); i++) {  //得到楼栋下每个单元的层数范围和房间数范围
			RoomInfo room = entryCollection.get(i).getRoom();

			int unitNum = room.getUnit();
			String key = room.getBuilding().getId().toString() + unitNum;
			Integer maxFloor = (Integer) buildingMaxFloor.get(key);
			Integer minFloor = (Integer) buildingMinFloor.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			Integer minNum = (Integer) buildingMinNum.get(key);
			if (minFloor == null || room.getFloor() < minFloor.intValue()) {
				buildingMinFloor.put(key, new Integer(room.getFloor()));
			}
			if (maxFloor == null || room.getFloor() > maxFloor.intValue()) {
				buildingMaxFloor.put(key, new Integer(room.getFloor()));
			}
			if (minNum == null || room.getSerialNumber() < minNum.intValue()) {
				buildingMinNum.put(key, new Integer(room.getSerialNumber()));
			}
			if (maxNum == null || room.getEndSerialNumber() > maxNum.intValue()) {
				buildingMaxNum.put(key, new Integer(room.getEndSerialNumber()));
			}
		}
		//创建层列
		IColumn column = this.buildingTable.addColumn();
		column.setKey("floor");
		column.setWidth(30);
		
		//创建【按层调整】列
		IColumn batchColumn = this.buildingTable.addColumn();
		batchColumn.setKey("byFloor");
		batchColumn.setWidth(60);

		BuildingInfo building = entryCollection.get(0).getRoom().getBuilding();
		BuildingUnitCollection unitCollection  = this.getBuildingUnits(building.getId().toString());
		String key = null;
		
		IRow unitRow = this.buildingTable.addHeadRow(0);  //单元行
		unitRow.getCell(0).setValue("单元");
		
		IRow roomNumberRow = this.buildingTable.addHeadRow(1);  //编号行
		roomNumberRow.getCell(0).setValue("编号");
		
		IRow batchAdjustRow = this.buildingTable.addHeadRow(2);  //批量操作行
		batchAdjustRow.getCell(1).setValue("整栋调整");
		
		//创建编号列，楼栋下没有单元，直接创建编号列
		if(unitCollection==null || unitCollection.isEmpty()){
			key = building.getId().toString() + 0;
			Integer minNum = (Integer) buildingMinNum.get(key);
			Integer maxNum = (Integer) buildingMaxNum.get(key);
			if (minNum != null && maxNum != null) {
				for (int j = minNum.intValue(); j <= maxNum.intValue(); j++) { // 构建表体
					column = this.buildingTable.addColumn();
					column.setKey(key + j + 0);
					
					column = this.buildingTable.addColumn();
					column.setKey(key + j + 1);

					//合并【整列调整】单元格
					ICell batchAdjustCell = batchAdjustRow.getCell(key + j + 0);
					batchAdjustCell.setUserObject(key + j + 1);  //保存数值列的索引
					batchAdjustCell.setValue("整列调整");
					batchAdjustCell.getStyleAttributes().setFontColor(Color.BLUE);
					batchAdjustCell.getStyleAttributes().setUnderline(true);
					
					int offColumn = batchAdjustRow.getCell(key + j + 1).getColumnIndex() - batchAdjustCell.getColumnIndex();
					this.buildingTable.getHeadMergeManager().mergeBlock(batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex(),
							batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex() + offColumn );
					
					//合并编号行
					ICell roomNumberCell = roomNumberRow.getCell(key + j + 0);
					roomNumberCell.setValue(new Integer(j));
					this.buildingTable.getHeadMergeManager().mergeBlock(roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex(), 
							roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex() + 1);
				}
			}
		}
		else{  //楼栋下存在单元，为每个单元分别创建编号列
			for(int i=0; i<unitCollection.size(); i++){
				key = building.getId().toString() + unitCollection.get(i).getSeq();
				Integer minNum = (Integer) buildingMinNum.get(key);
				Integer maxNum = (Integer) buildingMaxNum.get(key);
				if (minNum != null && maxNum != null) {
					for (int j = minNum.intValue(); j <= maxNum.intValue(); j++) { // 构建表体
						column = this.buildingTable.addColumn();
						column.setKey(key + j + 0);
						
						column = this.buildingTable.addColumn();
						column.setKey(key + j + 1);
						
						//合并【整列调整】单元格
						ICell batchAdjustCell = batchAdjustRow.getCell(key + j + 0);
						batchAdjustCell.setUserObject(key + j + 1);  //保存数值列的索引
						batchAdjustCell.setValue("整列调整");
						batchAdjustCell.getStyleAttributes().setFontColor(Color.BLUE);
						batchAdjustCell.getStyleAttributes().setUnderline(true);
						
						int offColumn = batchAdjustRow.getCell(key + j + 1).getColumnIndex() - batchAdjustCell.getColumnIndex();
						this.buildingTable.getHeadMergeManager().mergeBlock(batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex(),
								batchAdjustRow.getRowIndex(), batchAdjustCell.getColumnIndex() + offColumn);
						
						//合并编号行
						ICell roomNumberCell = roomNumberRow.getCell(key + j + 0);
						roomNumberCell.setValue(new Integer(j));
						this.buildingTable.getHeadMergeManager().mergeBlock(roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex(), 
								roomNumberRow.getRowIndex(), roomNumberCell.getColumnIndex() + 1);
					}
					//合并单元行
					ICell unitCell = unitRow.getCell(key + minNum.intValue() + 0);
					unitCell.setValue(new Integer(unitCollection.get(i).getSeq()));
					int offSet = (maxNum.intValue() - minNum.intValue()) * 2 + 1;
					this.buildingTable.getHeadMergeManager().mergeBlock(unitRow.getRowIndex(), unitCell.getColumnIndex(), 
							unitRow.getRowIndex(), offSet + unitCell.getColumnIndex());
				}
			}
		}

		//创建楼层
		int minRow = 1;
		int maxRow = 0;
		//取所有单元最高层
		Collection collection = buildingMaxFloor.values();
		for (Iterator iter = collection.iterator(); iter.hasNext();) {
			Integer num = (Integer) iter.next();
			if (num.intValue() > maxRow)
				maxRow = num.intValue();
		}
		
		//取所有单元最小层
		Collection tempColl = buildingMinFloor.values();
		for (Iterator iter = tempColl.iterator(); iter.hasNext();) {
			Integer num = (Integer) iter.next();
			if (num.intValue() < minRow)
				minRow = num.intValue();
		}

		//创建表格房间行
		for (int i = minRow; i <= maxRow; i++) {
			IRow row = this.buildingTable.addRow();
			
			int currFloor = maxRow - i + minRow;
			row.getCell("floor").setValue(new Integer(currFloor) + "层");
			row.getCell("byFloor").setValue("按层调整");
			row.getCell("byFloor").getStyleAttributes().setFontColor(Color.BLUE);
			row.getCell("byFloor").getStyleAttributes().setUnderline(true);
			if(currFloor==0){  //0层隐藏
				row.getStyleAttributes().setHided(true);
			}
			
			//面积、原总价、原单价、现总价、现单价，每一项单独列一行，所以这里先创建行，后面再填充数据
			IRow subRow = null;
			for(int j=0; j<4; j++){
				subRow = this.buildingTable.addRow();
				if(currFloor==0){  //0层隐藏
					subRow.getStyleAttributes().setHided(true);
				}
			}
			
			//由于每层都根据以上的分项拆分为多行，所以显示楼层的一列需要合并起来
			this.buildingTable.getMergeManager().mergeBlock(row.getRowIndex(), 0, subRow.getRowIndex(), 0);  //楼层列
			this.buildingTable.getMergeManager().mergeBlock(row.getRowIndex(), 1, subRow.getRowIndex(), 1);  //批量操作列
			row.setUserObject(new Integer(i));
		}
		
		//填充表格
		this.fillRoomTable(this.buildingTable, entryCollection);
	}
	
	private void fillRoomTable(KDTable table, RoomPriceAdjustEntryCollection entryCollection) throws UIException {
		Integer minRow = (Integer) table.getRow(0).getUserObject();
		for (int i = 0; i < entryCollection.size(); i++) {
			RoomInfo room = entryCollection.get(i).getRoom();
			String buildingId = room.getBuilding().getId().toString();

			int unitNum = room.getUnit();
			String key = buildingId + unitNum;
			//融合房间的显示
			for (int j = room.getSerialNumber(); j <= room.getEndSerialNumber(); j++) {
				int newFloor = room.getFloor() + (room.getFloor()-minRow.intValue()) * 4;
				int rowIndex = table.getRowCount() + minRow.intValue() - 1 - newFloor;
				
				this.createPriceRow(table, rowIndex, key + j, entryCollection.get(i));
			}
			//融合的房间所在单元格，左右合并
			if (room.getSerialNumber() != room.getEndSerialNumber()) {
				int newFloor = room.getFloor() + (room.getFloor()-minRow.intValue()) * 4;
				int curRow = table.getRowCount() + minRow.intValue() - 1 - newFloor;
				int leftBeginCol = table.getColumnIndex(key + room.getSerialNumber() + 1);
				int columnOff = ( room.getEndSerialNumber() - room.getSerialNumber() ) * 2; 
				
				int beginColIndex = leftBeginCol + 1;
				int endColIndex = leftBeginCol + columnOff;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
				
				curRow--;
				this.clearCellValue(table, curRow, beginColIndex, endColIndex);
				table.getMergeManager().mergeBlock(curRow, leftBeginCol, curRow, leftBeginCol + columnOff);
			}
			//上下合并
			if(room.getSub()!=null&&room.getSub().indexOf(",")>0){
				int newFloor = room.getFloor() + (room.getFloor()-minRow.intValue()) * 4;
				int curRow = table.getRowCount() + minRow.intValue() - 1 - newFloor;
				int titleCol = table.getColumnIndex(key + room.getSerialNumber() + 0);
				int valueCol = table.getColumnIndex(key + room.getSerialNumber() + 1);
				
				String[] sub = room.getSub().split(",");
				int merger = sub.length - 1;
				int rowOffSet = curRow - (merger * 5) - 4;
				boolean isRow = true;
				if (!sub[0].split(";")[0].equals(sub[1].split(";")[0])){
					isRow = false;
				}
				if(!isRow){
					table.getMergeManager().mergeBlock(rowOffSet, titleCol, curRow - 4, titleCol);
					table.getMergeManager().mergeBlock(rowOffSet, valueCol, curRow - 4, valueCol);
					table.getCell(rowOffSet, titleCol).getStyleAttributes().setVerticalAlign(VerticalAlignment.BOTTOM);
					table.getCell(rowOffSet, valueCol).getStyleAttributes().setVerticalAlign(VerticalAlignment.BOTTOM);
				}
			}
		}
		
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				ICell cell = table.getRow(i).getCell(j);
				if (cell.getUserObject() == null) {
					if (j == 0) {
						cell.getStyleAttributes().setBackground(new Color(184, 204, 221));
					}
					else if(j == 1){
						cell.getStyleAttributes().setBackground(Color.WHITE);
					}
					else {
						cell.getStyleAttributes().setBackground(Color.GRAY);
					}
				}
			}

		}
	}
	
	/**
	 * 合并时，清空被合并的单元格值
	 * @param table
	 * @param curRow
	 * @param columnOffSet
	 */
	private void clearCellValue(KDTable table, int curRowIndex, int beginColIndex, int endColIndex){
		for(int i=beginColIndex; i<=endColIndex; i++){
			table.getCell(curRowIndex, i).setValue(null);
		}
	}
	
	/**
	 * @param table
	 * @param rowIndex
	 * @param colKey
	 * @param entry
	 */
	private void createPriceRow(KDTable table, int rowIndex, String colKey, RoomPriceAdjustEntryInfo entry){
		ICell nameCell = table.getCell(rowIndex, colKey + 0);
		ICell priceCell = table.getCell(rowIndex, colKey + 1);
		if (nameCell == null)
			return;
		else{
			Integer singlePriceRow = null;  //现单价的行号
			
			PriceTypeForPriceBillEnum priceType = entry.getPriceType();
			BigDecimal oldSinglePrice = null;
			BigDecimal singlePrice = null;
			BigDecimal area = null;
			if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
				oldSinglePrice = entry.getOldBuildingPrice();
				singlePrice = entry.getNewBuildingPrice();
				area = entry.getNewBuildingArea();
			}
			else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
				oldSinglePrice = entry.getOldRoomPrice();
				singlePrice = entry.getNewRoomPrice();
				area = entry.getNewRoomArea();
			}
			
			//从下往上
			//现总价
			nameCell.setUserObject("newSumAmount");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("现总价:");
			
			priceCell.setUserObject(entry);  //保存定价分录对象，供变更操作使用
			priceCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			if(entry.getNewSumAmount() != null){
				priceCell.setValue(entry.getNewSumAmount().setScale(paramArray[2], paramArray[3]));
			}
			else{
				priceCell.setValue(null);
			}
			if(this.isCellEditable(entry.getRoom())){
				FDCFormattedTextField sumField = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
				sumField.setNegatived(false);
				sumField.setPrecision(paramArray[2]);
				sumField.setRoundingMode(paramArray[3]);
				
				priceCell.setEditor(new KDTDefaultCellEditor(sumField));
				priceCell.getStyleAttributes().setLocked(false);
			}
			
			//现单价
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("newSinglePrice");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("现单价:");
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			priceCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.LEFT);
			
			
			if(singlePrice != null){
				priceCell.setValue(singlePrice.setScale(paramArray[0], paramArray[1]));
			}
			else{
				priceCell.setValue(null);
			}
			if(this.isCellEditable(entry.getRoom()) && area!=null && area.compareTo(FDCHelper.ZERO)!=0){
				FDCFormattedTextField singleField = new FDCFormattedTextField(KDFormattedTextField.DECIMAL);
				singleField.setNegatived(false);
				singleField.setPrecision(paramArray[0]);
				singleField.setRoundingMode(paramArray[1]);
				
				priceCell.setEditor(new KDTDefaultCellEditor(singleField));
				priceCell.getStyleAttributes().setLocked(false);
			}
			
			//记录现单价的行号，供【按层调整】使用
			singlePriceRow = new Integer(rowIndex);
			
			//原总价
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("oldSumAmount");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("原总价:");
			table.getRow(rowIndex).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			if(entry.getOldSumAmount() != null){
				priceCell.setValue(entry.getOldSumAmount().setScale(paramArray[2], paramArray[3]));
			}
			else{
				priceCell.setValue(null);
			}
			
			//原单价
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("oldSinglePrice");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("原单价:");
			table.getRow(rowIndex).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			if(oldSinglePrice != null){
				priceCell.setValue(oldSinglePrice.setScale(paramArray[0], paramArray[1]));
			}
			else{
				priceCell.setValue(null);
			}
			
			//面积
			rowIndex--;
			nameCell = table.getCell(rowIndex, colKey + 0);
			nameCell.setUserObject("area");
			nameCell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			nameCell.setValue("面积:");
			table.getRow(rowIndex).getStyleAttributes().setBackground(FDCTableHelper.cantEditColor);  //add by shilei
			
			priceCell = table.getCell(rowIndex, colKey + 1);
			priceCell.setUserObject(entry);
			if(area != null){
				priceCell.setValue(area.setScale(3,BigDecimal.ROUND_HALF_UP));
			}
			else{
				priceCell.setValue(null);
			}
			
			//保存当前单价行的索引，供【按层调整】使用；后续会用面积单元格合并其他单元格，所以这里将现单价行号保存在面积的一行中
			table.getRow(rowIndex).getCell("byFloor").setUserObject(singlePriceRow);
		}
	}
	
	/**
	 * 根据楼栋获取单元集合
	 * @param builldingId - 楼栋Id
	 * @return
	 * @throws BOSException 
	 */
	private BuildingUnitCollection getBuildingUnits(String builldingId) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("seq");		
		view.getSorter().add(new SorterItemInfo("seq"));

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.id", builldingId));

		view.setFilter(filter);
		
		BuildingUnitCollection units = BuildingUnitFactory.getRemoteInstance().getBuildingUnitCollection(view);
		
		return units;
	}
	
	protected void buildingTable_editStopped(KDTEditEvent e) throws Exception {
		super.buildingTable_editStopped(e);
		
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();

		IRow currentRow = this.buildingTable.getRow(rowIndex);
		
		RoomPriceAdjustEntryInfo priceEntryInfo = (RoomPriceAdjustEntryInfo)this.buildingTable.getCell(rowIndex, colIndex).getUserObject();
		//取得编辑项的名称
		String priceName = (String)currentRow.getCell(colIndex - 1).getUserObject();
		PriceTypeForPriceBillEnum priceType = priceEntryInfo.getPriceType();
		
		BigDecimal sumAmount = null;
		BigDecimal singlePrice = null;
		BigDecimal area = null;
		
		//判断修改的是否为现总价
		if(priceName.equals("newSumAmount")){
			//新建筑单价和套内单价
			if(currentRow.getCell(colIndex).getValue() != null){
				sumAmount = KDTableTools.getBigDecimal(currentRow.getCell(colIndex).getValue());
			}
			//获取面积，根据界面的顺序来获取
			if(this.buildingTable.getCell(rowIndex-4, colIndex).getValue() != null){
				area = KDTableTools.getBigDecimal(this.buildingTable.getCell(rowIndex-4, colIndex).getValue());
			}
			//反算单价
			if(sumAmount!=null && area!=null){
				singlePrice = sumAmount.divide(area, paramArray[0], paramArray[1]);
				this.buildingTable.getCell(rowIndex-1, colIndex).setValue(singlePrice);
			}
		}
		else {  //修改的是现单价
			Object priceObj = this.buildingTable.getCell(rowIndex, colIndex).getValue();
			if(priceObj != null){  //计算总价
				singlePrice = KDTableTools.getBigDecimal(priceObj);
				area = KDTableTools.getBigDecimal(this.buildingTable.getCell(rowIndex-3, colIndex).getValue());
				sumAmount = singlePrice.multiply(area);
				this.buildingTable.getCell(rowIndex+1, colIndex).setValue(sumAmount.setScale(paramArray[2], paramArray[3]));
			}
		}
		
		BigDecimal entrySinglePrice = null;
		if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
			entrySinglePrice = priceEntryInfo.getNewBuildingPrice();
		}
		else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
			entrySinglePrice = priceEntryInfo.getNewRoomPrice();
		}
		
		//检查值是否发生变化
		if(sumAmount!=null && priceEntryInfo.getNewSumAmount()==null || sumAmount==null && priceEntryInfo.getNewSumAmount()!=null){
			priceEntryInfo.setModyfied(true);
		}
		else if(sumAmount != null && priceEntryInfo.getNewSumAmount()!=null){
			if(priceEntryInfo.getNewSumAmount().compareTo(sumAmount) != 0){
				priceEntryInfo.setModyfied(true);
			}
		}
		else if(singlePrice!=null && entrySinglePrice==null || singlePrice==null && entrySinglePrice!=null){
			priceEntryInfo.setModyfied(true);
		}
		else if(singlePrice != null && entrySinglePrice!=null){
			if(entrySinglePrice.compareTo(singlePrice) != 0){
				priceEntryInfo.setModyfied(true);
			}
		}
		
		//保存价格
		priceEntryInfo.setNewSumAmount(sumAmount);
		if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){  //保存新建筑单价
			priceEntryInfo.setNewBuildingPrice(singlePrice);
		}
		else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){  //保存新套内单价
			priceEntryInfo.setNewRoomPrice(singlePrice);
		}
		
	}
	
	/**
	 * 是否调整已售房源，若不调整，则锁定列
	 * @return
	 */
	private boolean isCellEditable(RoomInfo room){
		boolean result = true;
		Boolean isSelectAdjustSoldRoom = (Boolean)this.getUIContext().get("isSelectAdjustSoldRoom");
		Boolean isAdjustSoldRoom = (Boolean)this.getUIContext().get("isAdjustSoldRoom");
		RoomSellStateEnum sellState = room.getSellState();
		if(RoomSellStateEnum.PrePurchase.equals(sellState) || RoomSellStateEnum.Purchase.equals(sellState) 
				|| RoomSellStateEnum.Sign.equals(sellState)){
			if(isSelectAdjustSoldRoom.booleanValue() && !isAdjustSoldRoom.booleanValue()){
				result = false;
			}
		}
		return result;
	}
}