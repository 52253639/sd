/**
 * 保留房间单
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;

import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;

import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;

import com.kingdee.eas.fdc.sellhouse.client.RoomSelectUI;
import com.kingdee.eas.fdc.tenancy.KeepRoomDownFactory;
import com.kingdee.eas.fdc.tenancy.KeepRoomDownInfo;
import com.kingdee.eas.fdc.tenancy.TenancyStateEnum;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class KeepRoomDownEditUI extends AbstractKeepRoomDownEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(KeepRoomDownEditUI.class);
    
    private ItemAction[] hideItemActions =
    { 
    		this.actionCalculator, this.actionAuditResult, this.actionRemoveLine,
			this.actionInsertLine, this.actionTraceDown, this.actionTraceUp,
		    this.actionMultiapprove,this.actionNextPerson,
			this.actionSubmit,this.actionAddNew,this.actionRemove,this.actionViewSubmitProccess,
			this.actionWorkFlowG,this.actionRemoveLine,this.actionEdit,this.actionInsertLine,
			this.actionCreateFrom,this.actionAddLine,this.actionPre,this.actionNext,this.actionFirst,
			this.actionLast,this.actionPrint,this.actionPrintPreview
	};
  
    
    
    /**
     * output class constructor
     */
    public KeepRoomDownEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
        
        KeepRoomDownInfo keepRoomDownInfo = (KeepRoomDownInfo) this.editData;
		this.txtNumber.setText(keepRoomDownInfo.getNumber());
		this.txtAreaDescription.setText(keepRoomDownInfo.getDescription());
		RoomInfo room = keepRoomDownInfo.getRoom();
		if (room != null)
		{
		
			this.txtRoom.setText(room.getNumber());
			this.txtRoom.setUserObject(room);
		}
		this.dateBizDate.setValue(keepRoomDownInfo.getBizDate());

		UserInfo handler = keepRoomDownInfo.getHandler();
		if (handler != null)
		{
			this.txtOperator.setText(handler.getName());
			this.txtOperator.setUserObject(handler);
		} 
    }

    /**
     * output storeFields method
     */
    public void storeFields()
	{
    	super.storeFields();
    	
		KeepRoomDownInfo keepRoomDownInfo = (KeepRoomDownInfo) this.editData;
		keepRoomDownInfo.setNumber(this.txtNumber.getText());
		if(this.txtRoom.getUserObject() instanceof RoomInfo)
			keepRoomDownInfo.setRoom((RoomInfo) this.txtRoom.getUserObject());
		
		keepRoomDownInfo.setBizDate(this.dateBizDate.getTimestamp());
		keepRoomDownInfo.setDescription(this.txtAreaDescription.getText());
		
		
	}

  

    /**
	 * output getBizInterface method
	 */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return KeepRoomDownFactory.getRemoteInstance();
    }

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable()
    {        
        return null;
	}    

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        KeepRoomDownInfo keepRoomDownInfo = new KeepRoomDownInfo();
        
        RoomInfo room = (RoomInfo) this.getUIContext().get("room");
        keepRoomDownInfo.setRoom(room);
		
		keepRoomDownInfo.setHandler(SysContext.getSysContext().getCurrentUserInfo());
		keepRoomDownInfo.setBizDate(new Date());
        
        return keepRoomDownInfo;
    }
    
    /**
     * 选择房间
     */
    protected void btChooseRoom_actionPerformed(ActionEvent e) throws Exception
    {

		BuildingInfo buildingInfo = (BuildingInfo) this.getUIContext().get(
				"building");
		BuildingUnitInfo buildUnit = (BuildingUnitInfo) this.getUIContext().get("buildUnit");

		//弹出房间选择界面
		RoomInfo room = RoomSelectUI.showOneRoomSelectUI(this, buildingInfo,buildUnit,MoneySysTypeEnum.TenancySys);
		if (room == null)
		{
			return;
		}
		verifyRoom(room);
		if (room != null)
		{
			this.txtRoom.setUserObject(room);
			this.txtRoom.setText(room.getNumber());
		}
	
    }
    
    /**
     * 对选择房间是否复核保留条件进行校验
     * @param room
     * @throws EASBizException
     * @throws BOSException
     */
    private void verifyRoom(RoomInfo room) throws EASBizException, BOSException
	{
    	//租赁的校验为什么用售楼的代码来校验。自己都不试下功能的吗？
//		FilterInfo filter = new FilterInfo();
//		filter.getFilterItems().add(
//				new FilterItemInfo("room.id", room.getId().toString()));
//		filter.getFilterItems().add(
//				new FilterItemInfo("purchaseState",
//						PurchaseStateEnum.ChangeRoomBlankOut,
//						CompareType.NOTEQUALS));
//		filter.getFilterItems()
//				.add(
//						new FilterItemInfo("purchaseState",
//								PurchaseStateEnum.ManualBlankOut,
//								CompareType.NOTEQUALS));
//		filter.getFilterItems()
//				.add(
//						new FilterItemInfo("purchaseState",
//								PurchaseStateEnum.NoPayBlankOut,
//								CompareType.NOTEQUALS));
//		filter.getFilterItems().add(
//				new FilterItemInfo("purchaseState",
//						PurchaseStateEnum.QuitRoomBlankOut,
//						CompareType.NOTEQUALS));
//		filter.getFilterItems().add(
//				new FilterItemInfo("purchaseState",
//						PurchaseStateEnum.AdjustBlankOut,
//						CompareType.NOTEQUALS));
//		if (PurchaseFactory.getRemoteInstance().exists(filter))
//		{
//			MsgBox.showInfo("已经存在认购单,请先作废!");
//			this.abort();
//		}
    	if(room.isIsForTen()==false)
		{
			MsgBox.showInfo(room.getNumber() + " 非租赁房间!");
			return;
		}
		if(room.getTenancyState() == null  ||  TenancyStateEnum.unTenancy.equals(room.getTenancyState())){
			MsgBox.showInfo(this, "房间" + room.getNumber() + "未放租，不能出租！");
			this.abort();
		}
		if (TenancyStateEnum.newTenancy.equals(room.getTenancyState()) ||
				TenancyStateEnum.continueTenancy.equals(room.getTenancyState())
				|| TenancyStateEnum.enlargeTenancy.equals(room.getTenancyState())) {
			MsgBox.showInfo(room.getNumber() + " 已经出租!");
			this.abort();
		}
		if (TenancyStateEnum.sincerObligate.equals(room.getTenancyState())) {
			MsgBox.showInfo(room.getNumber() + " 已诚意预留!");
			this.abort();
		}
		if (TenancyStateEnum.keepTenancy.equals(room.getTenancyState())) {
			MsgBox.showInfo(room.getNumber() + " 已保留!");
			this.abort();
		}
	}
    
    protected void verifyInput(ActionEvent e) throws Exception
	{
		super.verifyInput(e);
		
		String number = txtNumber.getText();
		
		if (this.txtNumber.isEnabled() != false)
		{
			if (StringUtils.isEmpty(number))
			{
				MsgBox.showInfo("单据编号不能为空!");
				this.abort();
			}
		}

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number));
		if (this.getBizInterface().exists(filter))
		{
			MsgBox.showInfo("单据编号" + number + "已经存在!");
			this.abort();
		}
	}
    
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    	super.actionSave_actionPerformed(e);
    	
    	this.disposeUIWindow();
    }

    public SelectorItemCollection getSelectors()
    {
    	SelectorItemCollection selector = super.getSelectors();
    	
    	selector.add("*");
    	selector.add("room.*");
    	selector.add("room.building");
    	
    	
    	return selector;
    }
    
    public void onLoad() throws Exception
    {
    	super.onLoad();
    	
    	TenancyClientHelper.hideButton(hideItemActions);
    }
    protected OrgType getMainBizOrgType()
    {
        return OrgType.Sale;
    }   	    
}