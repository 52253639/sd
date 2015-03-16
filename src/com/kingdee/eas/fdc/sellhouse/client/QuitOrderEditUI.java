/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomCollection;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellOrderFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class QuitOrderEditUI extends AbstractQuitOrderEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuitOrderEditUI.class);
    
    public QuitOrderEditUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
    	ItemAction[] itemActions = new ItemAction[]{actionAddNew, actionEdit,actionSave, actionCopy, actionRemove, actionAttachment,actionPrint,actionPrintPreview,actionNext,actionLast,actionFirst, actionPre,actionCancelCancel,actionCancel};
		FDCClientHelper.setActionVisible(itemActions, false);
		this.btnSubmit.setText("确定");
		this.btnSubmit.setToolTipText("确定");
		
		this.tblRoom.getStyleAttributes().setLocked(true);
    }
    
    protected void checkIsOUSealUp() throws Exception {
    }
    
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
    	//不使用基类的提交逻辑，这里只是借用这个菜单
    	int result = MsgBox.showConfirm2New(null, "是否确认撤盘！");
		if(result != MsgBox.YES){
			return;
		}
    	Set quitRoomIds = new HashSet();
    	for(int i=0; i<this.tblRoom.getRowCount(); i++){
    		IRow row = this.tblRoom.getRow(i);
    		quitRoomIds.add(row.getCell("id").getValue());
    	}
    	
		SellOrderFactory.getRemoteInstance().quitOrder(quitRoomIds);
		MsgBox.showInfo(this, "撤盘成功!");
		this.destroyWindow();
    }
    
    protected void btnAddRoom_actionPerformed(ActionEvent e) throws Exception {
    	RoomCollection rooms = RoomSelectUI.showMultiRoomSelectUI(this, null, null,MoneySysTypeEnum.SalehouseSys);
    	if(rooms == null  ||  rooms.isEmpty()){
    		return;
    	}
    	
    	Map map = new HashMap();
    	for(int i=0; i<rooms.size(); i++){
    		RoomInfo room = rooms.get(i);
    		if(!RoomSellStateEnum.OnShow.equals(room.getSellState())){
    			MsgBox.showInfo(this, "房间" + room.getNumber() + "不是待售状态，不能撤盘.");
    			continue;
    		}
    		map.put(room.getId().toString(), room);
    	}
    	if(map.isEmpty()){
    		return;
    	}
    	
    	Set tmpSet = new HashSet(map.keySet());
    	
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("room.id", tmpSet, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
    	filter.getFilterItems().add(new FilterItemInfo("purchaseState", PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
    	view.setFilter(filter);
    	
    	view.getSelector().add("id");
    	view.getSelector().add("room.id");
    	
    	PurchaseCollection purs = PurchaseFactory.getRemoteInstance().getPurchaseCollection(view);
    	
    	for(int i=0; i<purs.size(); i++){
    		PurchaseInfo pur = purs.get(i);
    		String roomid = pur.getRoom().getId().toString();
    		RoomInfo room = (RoomInfo) map.get(roomid);
    		/**
    		 * 增加room为空的判断
    		 * update by renliang 
    		 * at 2010-10-8
    		 */
    		if(room==null){
    			return;
    		}
    		MsgBox.showInfo(this, "房间" + room.getNumber() + "存在非作废状态的认购单，不能撤盘.");
    		map.remove(roomid);
    	}
    	
    	Set set = map.keySet();
    	for(Iterator itor = set.iterator(); itor.hasNext(); ){
    		String roomId = (String) itor.next();
    		RoomInfo room = (RoomInfo) map.get(roomId);
    		
    		IRow row = this.tblRoom.addRow();
    		row.setUserObject(room);
    		row.getCell("id").setValue(room.getId().toString());
    		row.getCell("sellProject").setValue(room.getBuilding().getSellProject().getName());
    		row.getCell("subarea").setValue(room.getBuilding().getSubarea() == null ? null : room.getBuilding().getSubarea().getName());
    		row.getCell("building").setValue(room.getBuilding().getName());
    		row.getCell("unit").setValue(room.getBuildUnit()==null?"":room.getBuildUnit().getName());
    		row.getCell("floor").setValue(new Integer(room.getFloor()));
    		row.getCell("number").setValue(room.getNumber());
    	}
    }
    
    protected void btnRmRoom_actionPerformed(ActionEvent e) throws Exception {
		int activeRowIndex = this.tblRoom.getSelectManager().getActiveRowIndex();
		if (activeRowIndex == -1) {
			activeRowIndex = this.tblRoom.getRowCount();
		}
		this.tblRoom.removeRow(activeRowIndex);
	}
    
	protected IObjectValue createNewData() {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
    
}