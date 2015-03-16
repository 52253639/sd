/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;
import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDLabelContainer;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.PriceTypeForPriceBillEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryCollection;
import com.kingdee.eas.fdc.sellhouse.RoomPriceAdjustEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SymbolEnum;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.SysUtil;

/**
 * output class name
 */
public class BatchAdjustColUI extends AbstractBatchAdjustColUI {
	private static final Logger logger = CoreUIObject.getLogger(BatchAdjustColUI.class);
	
	private int[] paramArray = null;

	public BatchAdjustColUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
		paramArray = (int[])this.getUIContext().get("paramArray");
		this.radioColInc.setSelected(true);
	}

	public void storeFields() {
		super.storeFields();
	}

	protected void btnYes_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnYes_actionPerformed(e);
		
		//纵向递增
		if(this.radioColInc.isSelected()){
			this.verifyInput(this.txtIncFrom, this.txtIncTo, this.comboIncSymbol, this.txtIncYuan);
			this.dealSinglePrice(this.txtIncFrom, this.txtIncTo, this.comboIncSymbol, this.txtIncYuan);
		}
		else{
			this.verifyInput(this.txtAddFrom, this.txtAddTo, this.comboAddSymbol, this.txtAddYuan);
			this.dealSinglePrice(this.txtAddFrom, this.txtAddTo, this.comboAddSymbol, this.txtAddYuan);
		}
		
		this.destroyWindow();
	}

	protected void btnNo_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.btnNo_actionPerformed(e);
		
		this.destroyWindow();
	}

	protected void radioColInc_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.radioColInc_actionPerformed(e);
		
		this.txtIncFrom.setRequired(true);
		this.txtIncTo.setRequired(true);
		this.txtIncYuan.setRequired(true);
		
		this.txtAddFrom.setRequired(false);
		this.txtAddTo.setRequired(false);
		this.txtAddYuan.setRequired(false);
	}

	protected void radioColAdd_actionPerformed(java.awt.event.ActionEvent e)
			throws Exception {
		super.radioColAdd_actionPerformed(e);
		
		this.txtIncFrom.setRequired(false);
		this.txtIncTo.setRequired(false);
		this.txtIncYuan.setRequired(false);
		
		this.txtAddFrom.setRequired(true);
		this.txtAddTo.setRequired(true);
		this.txtAddYuan.setRequired(true);
	}

	protected void comboIncSymbol_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		this.comboSymbolStateChanged(e, this.contIncYuan);
	}

	protected void comboAddSymbol_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {
		this.comboSymbolStateChanged(e, this.contAddYuan);
	}
	
	/**
	 * 根据选择的计算方式，控制单位【元】的显示
	 * @param e
	 * @param yuan
	 */
	private void comboSymbolStateChanged(java.awt.event.ItemEvent e, KDLabelContainer yuan){
		if(e.getItem()!=null && e.getItem().equals(SymbolEnum.MULTIPLY)){
			yuan.setVisible(false);
		}
		else{
			yuan.setVisible(true);
		}
	}
	
	public static RoomPriceAdjustEntryCollection showUI(CoreUI ui, boolean isSelectAdjustSoldRoom, boolean isAdjustSoldRoom,
			RoomPriceAdjustEntryCollection entryCollection, int[] paramArray,String number) throws BOSException{
		//保存选中的楼栋房间
		UIContext uiContext = new UIContext(ui);
		uiContext.put("isSelectAdjustSoldRoom", new Boolean(isSelectAdjustSoldRoom));
		uiContext.put("isAdjustSoldRoom", new Boolean(isAdjustSoldRoom));
		uiContext.put("entryCollection", entryCollection);
		uiContext.put("paramArray", paramArray);
		uiContext.put("colNumber", number);

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(BatchAdjustColUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		return entryCollection;
	}
	
	/**
	 * 检验数据合法性
	 */
	private void verifyInput(KDFormattedTextField txtFrom, KDFormattedTextField txtTo, 
			KDComboBox comboSymbol, KDFormattedTextField txtYuan){
		HashMap floorRangeMap = this.getFloorRange();
		Integer maxFloor = (Integer)floorRangeMap.get("maxFloor");
		Integer minFloor = (Integer)floorRangeMap.get("minFloor");
		
		if(txtFrom.getNumberValue() == null){
			FDCMsgBox.showInfo("请输入开始层");
			txtFrom.requestFocus();
			SysUtil.abort();
		}
		else if(txtTo.getNumberValue() == null){
			FDCMsgBox.showInfo("请输入结束层");
			txtTo.requestFocus();
			SysUtil.abort();
		}
		
		if(txtFrom.getNumberValue().intValue() < minFloor.intValue()){
			FDCMsgBox.showInfo("开始层不能小于最小楼层");
			txtFrom.requestFocus();
			SysUtil.abort();
		}
		else if(txtTo.getNumberValue().intValue() > maxFloor.intValue()){
			FDCMsgBox.showInfo("结束层不能大于最大楼层");
			txtTo.requestFocus();
			SysUtil.abort();
		}
		else if(txtFrom.getNumberValue().intValue() > txtTo.getNumberValue().intValue()){
			FDCMsgBox.showInfo("开始层不能大于结束层");
			txtFrom.requestFocus();
			SysUtil.abort();
		}
		
		if(comboSymbol.getSelectedItem() == null){
			FDCMsgBox.showInfo("请选择运算符号");
			comboSymbol.requestFocus();
			SysUtil.abort();
		}
		if(txtYuan.getNumberValue() == null){
			FDCMsgBox.showInfo("请输入调整价格");
			txtYuan.requestFocus();
			SysUtil.abort();
		}
	}
	
	/**
	 * 获取楼层的最大和最小值
	 * @return 最大和最小值Map, maxFloor和minFloor
	 */
	private HashMap getFloorRange(){
		HashMap map = new HashMap();
		
		RoomPriceAdjustEntryCollection entryCollection = (RoomPriceAdjustEntryCollection) uiWindow
			.getUIObject().getUIContext().get("entryCollection");
		
		for(int i=0; i<entryCollection.size(); i++){
			RoomInfo room = entryCollection.get(i).getRoom();
			Integer maxFloor = (Integer)map.get("maxFloor");
			Integer minFloor = (Integer)map.get("minFloor");
			if(maxFloor == null || maxFloor.intValue() < room.getFloor()){
				map.put("maxFloor", new Integer(room.getFloor()));
			}
			if(minFloor == null || minFloor.intValue() > room.getFloor()){
				map.put("minFloor", new Integer(room.getFloor()));
			}
		}
		
		return map;
	}
	
	/**
	 * 根据输入的调整方式，调整单价
	 */
	private void dealSinglePrice(KDFormattedTextField txtFrom, KDFormattedTextField txtTo, 
			KDComboBox comboSymbol, KDFormattedTextField txtYuan) throws BOSException{
		RoomPriceAdjustEntryCollection entryCollection = (RoomPriceAdjustEntryCollection) uiWindow
		.getUIObject().getUIContext().get("entryCollection");
		
		String colNumber = "";
		
		if(uiWindow.getUIObject().getUIContext().get("colNumber")!=null){
			colNumber = uiWindow.getUIObject().getUIContext().get("colNumber").toString();
		}
		
		//取出最小楼层，方便做递增运算
		int symbol = comboSymbol.getSelectedIndex();
		int fromFloor = txtFrom.getNumberValue().intValue();
		int toFloor = txtTo.getNumberValue().intValue();
		
		//遍历纵向房间，计算新单价和总价
		for(int i=0; i<entryCollection.size(); i++){
			RoomPriceAdjustEntryInfo priceEntryInfo = entryCollection.get(i);
			//已售房源是否参与调整
			if(!this.isPriceEditable(priceEntryInfo.getRoom())){
				continue;
			}
			
			int roomFloor = priceEntryInfo.getRoom().getFloor();
			//如果房间的楼层不在计算的范围之内，忽略
			if(roomFloor<fromFloor || roomFloor>toFloor){
				continue;
			}
			
			if(!"".equals(colNumber) && priceEntryInfo.getRoom()!=null){
				if(!colNumber.equals(String.valueOf(priceEntryInfo.getRoom().getSerialNumber()))){
					continue;
				}
			}
			
			BigDecimal singlePrice = null;
			BigDecimal newSinglePrice = null;
			BigDecimal priceFactor = txtYuan.getBigDecimalValue();
			
			PriceTypeForPriceBillEnum priceType = priceEntryInfo.getPriceType();
			if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
				singlePrice = priceEntryInfo.getNewBuildingPrice();
			}
			else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
				singlePrice = priceEntryInfo.getNewRoomPrice();
			}
			if(singlePrice == null){
				singlePrice = FDCHelper.ZERO;
			}
			if(this.radioColInc.isSelected()){  //纵向递增
				int offFloor = roomFloor - fromFloor;
				if(offFloor != 0){  //最底层直接计算，不用计算递增值
					if(roomFloor > offFloor ){
						offFloor += 1;
					}
					if(SymbolEnum.MULTIPLY_VALUE == symbol){  //运算符是乘号，递增值取平方值
						priceFactor = this.pow(priceFactor, offFloor);
					}
					else{  //运算符非乘号，只需取累加值
						priceFactor = priceFactor.multiply(new BigDecimal(offFloor));
					}
				}
				
				if (SymbolEnum.ADD_VALUE == symbol) {
					newSinglePrice = singlePrice.add(priceFactor).setScale(paramArray[0], paramArray[1]);
				}
				else if (SymbolEnum.MINUS_VALUE == symbol) {
					newSinglePrice = singlePrice.subtract(priceFactor).setScale(paramArray[0], paramArray[1]);
				} 
				else if (SymbolEnum.MULTIPLY_VALUE == symbol) {
					newSinglePrice = singlePrice.multiply(priceFactor).setScale(paramArray[0], paramArray[1]);
				}
			}
			else{  //纵向普加
				if (SymbolEnum.ADD_VALUE == symbol) {
					newSinglePrice = singlePrice.add(priceFactor).setScale(paramArray[0], paramArray[1]);
				}
				else if (SymbolEnum.MINUS_VALUE == symbol) {
					newSinglePrice = singlePrice.subtract(priceFactor).setScale(paramArray[0], paramArray[1]);
				} 
				else if (SymbolEnum.MULTIPLY_VALUE == symbol) {
					newSinglePrice = singlePrice.multiply(priceFactor).setScale(paramArray[0], paramArray[1]);
				}
			}
			
			if(newSinglePrice.compareTo(FDCHelper.ZERO) < 0){
				newSinglePrice = FDCHelper.ZERO;
			}
			
			//保存新单价
			if(PriceTypeForPriceBillEnum.UseBuildingArea.equals(priceType)){
				BigDecimal area = priceEntryInfo.getNewBuildingArea();
				if(area == null){
					continue;
				}
				priceEntryInfo.setNewBuildingPrice(newSinglePrice);
				//反算总价
				priceEntryInfo.setNewSumAmount(newSinglePrice.multiply(area).setScale(paramArray[2], paramArray[3]));
				priceEntryInfo.setModyfied(true);
			}
			else if(PriceTypeForPriceBillEnum.UseRoomArea.equals(priceType)){
				BigDecimal area = priceEntryInfo.getNewRoomArea();
				if(area == null){
					continue;
				}
				priceEntryInfo.setNewRoomPrice(newSinglePrice);
				//反算总价
				priceEntryInfo.setNewSumAmount(newSinglePrice.multiply(area).setScale(paramArray[2], paramArray[3]));
				priceEntryInfo.setModyfied(true);
			}
		}
	}
	
	/**
	 * 是否调整已售房源，若不调整，则锁定列
	 * @return
	 */
	private boolean isPriceEditable(RoomInfo room){
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

	public static BigDecimal pow(BigDecimal dec1,int dec2){
		if(dec1==null){
			return null;
		}
		if(dec2<0){
			return null;
		}
		BigDecimal temp  = FDCHelper.ONE;
		
		if(dec2==0){
			return FDCHelper.ONE;
		}
		
		for (int i = 1; i <=dec2; i++) {
			temp = temp.multiply(dec1);
		}
		
		return temp;
	}
}