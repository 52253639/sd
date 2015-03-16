/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.SellTypeEnum;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class ShowRoomDetailInfoUI extends AbstractShowRoomDetailInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(ShowRoomDetailInfoUI.class);
    
    /**
     * output class constructor
     */
    public ShowRoomDetailInfoUI() throws Exception
    {
        super();
    }

    public void onLoad() throws Exception {
    	super.onLoad();
//    	this.menuBar.setVisible(false);
//    	this.toolBar.setVisible(false);
    	DecimalFormat format = new DecimalFormat("#,##0.00");
    	RoomInfo room = (RoomInfo)this.getUIContext().get("room");
    	if(room != null){
    		SellTypeEnum sellType = (SellTypeEnum)room.getSellType();
    		if(SellTypeEnum.LocaleSell.equals(sellType)){
    			this.labBuildingArea.setText("建筑面积： "+ format.format(room.getActualBuildingArea()!=null?room.getActualBuildingArea():FDCHelper.ZERO));
    			this.labRoomArea.setText("套内面积： "+ format.format(room.getActualRoomArea()!=null?room.getActualRoomArea():FDCHelper.ZERO));
    		}
    		else if(SellTypeEnum.PreSell.equals(sellType)){
    			this.labBuildingArea.setText("建筑面积： "+ format.format(room.getBuildingArea()!=null?room.getBuildingArea():FDCHelper.ZERO));
    			this.labRoomArea.setText("套内面积： "+ format.format(room.getRoomArea()!=null?room.getRoomArea():FDCHelper.ZERO));
    		}else{
    			this.labBuildingArea.setText("建筑面积： "+ format.format(room.getPlanBuildingArea()!=null?room.getPlanBuildingArea():FDCHelper.ZERO));
    			this.labRoomArea.setText("套内面积： "+ format.format(room.getPlanRoomArea()!=null?room.getPlanRoomArea():FDCHelper.ZERO));
    		}
    		this.labTotalAmount.setText("总价： "+format.format(room.getStandardTotalAmount()!= null?room.getStandardTotalAmount():FDCHelper.ZERO));
    		if(room.getRoomModel()!=null){
    			this.labRoomType.setText("户型： "+room.getRoomModel().getName());
    		}else {
    			this.labRoomType.setText("户型： ");
    		}
    		
    		this.labPrice.setText("建筑单价：" +format.format(room.getBuildPrice()!=null?room.getBuildPrice():FDCHelper.ZERO) );
    		this.labRoomPrice.setText("套内单价：" + format.format(room.getRoomPrice()!=null?room.getRoomPrice():FDCHelper.ZERO));
    	}
    	
    	
    }
    
    
    
}