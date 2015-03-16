/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceCollection;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceFactory;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceInfo;
import com.kingdee.eas.fdc.sellhouse.TrackRecordEnum;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class CommerceSelectUI extends AbstractCommerceSelectUI
{
    private static final Logger logger = CoreUIObject.getLogger(CommerceSelectUI.class);
    
    /**
     * output class constructor
     */
    public CommerceSelectUI() throws Exception
    {
        super();
    }


	protected String getEditUIName() {
		return CommerceChanceEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return CommerceChanceFactory.getRemoteInstance();
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		
		uiContext.put("ActionView", "OnlyView");  //只是显示查看界面，不用任何按钮的功能
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		CommerceChanceCollection commColl = (CommerceChanceCollection)this.getUIContext().get("CommerceChanceCollection");
		
	  /**  根据传进来的商机集合显示
id,number,name,fdcCustomer.number,fdcCustomer.name,commerceStatus,sellProject.name,commerceLevel.name,
commerceDate,intentBuildingPro,intentProductType,intentArea,intentDirection,intentSight,intentRoomType

ID,商机编码,商机名称,客户编码,客户名称,商机状态,租售项目,商机级别,
商机日期,意向建筑性质,意向产品类型,面积需求, 意向朝向,景观需求,户型需求
	   */
		if(commColl!=null) {
			for(int i=0;i<commColl.size();i++) {
				CommerceChanceInfo commInfo = commColl.get(i);
				IRow row = this.tblMain.addRow();
				row.setUserObject(commInfo);
				row.getCell("id").setValue(commInfo.getId().toString());
				row.getCell("number").setValue(commInfo.getNumber());
				row.getCell("name").setValue(commInfo.getName());
				if(commInfo.getFdcCustomer()!=null) {
					row.getCell("fdcCustomer.number").setValue(commInfo.getFdcCustomer().getNumber());
					row.getCell("fdcCustomer.name").setValue(commInfo.getFdcCustomer().getName());
				}			
				row.getCell("commerceStatus").setValue(commInfo.getCommerceStatus());
				if(commInfo.getSellProject()!=null) row.getCell("sellProject.name").setValue(commInfo.getSellProject().getName());
				if(commInfo.getCommerceLevel()!=null) row.getCell("commerceLevel.name").setValue(commInfo.getCommerceLevel().getName());
				row.getCell("commerceDate").setValue(commInfo.getCommerceDate());
				if(commInfo.getHopedBuildingProperty()!=null) row.getCell("intentBuildingPro").setValue(commInfo.getHopedBuildingProperty().getName());
				if(commInfo.getHopedProductType()!=null) row.getCell("intentProductType").setValue(commInfo.getHopedProductType().getName());
				if(commInfo.getHopedAreaRequirement()!=null) row.getCell("intentArea").setValue(commInfo.getHopedAreaRequirement().getName());
				if(commInfo.getHopedDirection()!=null) row.getCell("intentDirection").setValue(commInfo.getHopedDirection().getName());
				if(commInfo.getHopedSightRequirement()!=null) row.getCell("intentSight").setValue(commInfo.getHopedSightRequirement().getName());
				if(commInfo.getHopedRoomModelType()!=null) row.getCell("intentRoomType").setValue(commInfo.getHopedRoomModelType().getName());
			}
		}
		
		
		
	}


	
	
	protected void btnAddTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.btnAddTrackRecord_actionPerformed(e);
		
		CommerceChanceInfo commInfo = null;
		if(this.tblMain.getRowCount()==1) {
			commInfo = (CommerceChanceInfo)this.tblMain.getRow(0).getUserObject();
		}else{
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			if(rowIndex<0) {
				MsgBox.showInfo("请选择要增加跟进的商机!");
				return;
			}
			commInfo = (CommerceChanceInfo)this.tblMain.getRow(rowIndex).getUserObject();
		}
		
		if(commInfo!=null)  {
			TrackRecordEnum trackEnum = (TrackRecordEnum)this.getUIContext().get("TrackRecordEnum");
			String numberStr = (String)this.getUIContext().get("BillNumberString");
			String idStr = (String)this.getUIContext().get("BillIdString");
			//打开新增跟踪记录界面
			UIContext uiContext = new UIContext(this); 		
			uiContext.put("CommerceChance", commInfo); 
			uiContext.put("FdcCustomer", commInfo.getFdcCustomer());
			uiContext.put("SellProject", commInfo.getSellProject());
			if(trackEnum!=null)  uiContext.put("TrackRecordEnum", trackEnum);			
			if(numberStr!=null) uiContext.put("BillNumberString", numberStr);
			if(idStr!=null) uiContext.put("BillIdString", idStr);			
			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			} catch (UIException ee) {
				ee.printStackTrace();
			}
		}
	}



		

}