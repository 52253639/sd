/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class RoomSourceReportFilterUI extends AbstractRoomSourceReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(RoomSourceReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public RoomSourceReportFilterUI() throws Exception
    {
        super();
    }

    /* (non-Javadoc)
	 * @see com.kingdee.eas.framework.report.client.CommRptBaseUI#onLoad()
	 */
	public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		SellProjectCollection col=SellProjectFactory.getRemoteInstance().getSellProjectCollection("select id from where orgUnit.id='"+SysContext.getSysContext().getCurrentOrgUnit().getId()+"'");
		
		if(col.size()>0){
			Set sellProject=new HashSet();
			for(int i=0;i<col.size();i++){
				sellProject.add(col.get(i).getId().toString());
			}
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProject,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", "'null'"));
		}
		view.setFilter(filter);
		this.prmtRoomMode.setEntityViewInfo(view);
		this.prmtBuilding.setEntityViewInfo(view);
		
		Set ptId=new HashSet();
		FDCSQLBuilder _builder = new FDCSQLBuilder();
		_builder.appendSql(" select distinct room.FProductTypeID id from T_SHE_Room room left join T_SHE_Building build on build.fid=room.fbuildingId left join T_SHE_SellProject sp on sp.fid=build.fsellProjectId");
		_builder.appendSql(" where sp.forgUnitId='"+SysContext.getSysContext().getCurrentOrgUnit().getId()+"'");
		IRowSet rowSet = _builder.executeQuery();
		while(rowSet.next()){
			ptId.add(rowSet.getString("id"));
		}
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		if(ptId.size()>0){
			filter.getFilterItems().add(new FilterItemInfo("id", ptId,CompareType.INCLUDE));
		}else{
			filter.getFilterItems().add(new FilterItemInfo("id", "'null'"));
		}
		view.setFilter(filter);
		this.f7productType.setEntityViewInfo(view);
		
		this.f7productType.setValue(null);
		this.prmtRoomMode.setValue(null);
		this.prmtBuilding.setValue(null);
	}

	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

	public RptParams getCustomCondition() {
		RptParams pp = new RptParams();
		String sellState="(";
		if(this.cbInit.isSelected()){
			sellState=sellState+"'Init',";
		}
		if(this.cbOnshow.isSelected()){
			sellState=sellState+"'Onshow',";
		}
		if(this.cbKeepDown.isSelected()){
			sellState=sellState+"'KeepDown',";
		}
		if(this.cbControl.isSelected()){
			sellState=sellState+"'Control',";
		}
		if(this.cbSincerPurchase.isSelected()){
			sellState=sellState+"'SincerPurchase',";
		}
		if(this.cbPrePurchase.isSelected()){
			sellState=sellState+"'PrePurchase',";
		}
		if(this.cbPurchase.isSelected()){
			sellState=sellState+"'Purchase',";
		}
		if(this.cbSign.isSelected()){
			sellState=sellState+"'Sign',";
		}
		if(sellState.indexOf(",")>0){
			 pp.setObject("sellState", sellState.substring(0,sellState.length()-1)+")");
		}else{
			pp.setObject("sellState", null);
		}
		if(this.f7productType.getValue()!=null){
			 pp.setObject("productType", this.f7productType.getValue());
		}else{
		  	 pp.setObject("productType", null);
		}
		 Object[] roomMode = (Object[])this.prmtRoomMode.getValue();
         if(roomMode!=null ){
			 pp.setObject("roomMode",roomMode);
		 }else{
			 pp.setObject("roomMode",null);
		 }
         Object[] building = (Object[])this.prmtBuilding.getValue();
         if(building!=null ){
			 pp.setObject("buildingObj",building);
		 }else{
			 pp.setObject("buildingObj",null);
		 }
         if(this.txtFloorFrom.getIntegerValue()!=null){
        	 pp.setObject("floorFrom",this.txtFloorFrom.getIntegerValue());
         }else{
        	 pp.setObject("floorFrom",null);
         }
         if(this.txtFloorTo.getIntegerValue()!=null){
        	 pp.setObject("floorTo",this.txtFloorTo.getIntegerValue());
         }else{
        	 pp.setObject("floorTo",null);
         }
		return pp;
	}

	public void onInit(RptParams arg0) throws Exception {
		
	}

	public void setCustomCondition(RptParams arg0) {
		
	}
}