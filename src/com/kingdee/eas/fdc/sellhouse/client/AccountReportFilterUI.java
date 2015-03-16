/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Rectangle;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * by ....wancheng
 */
public class AccountReportFilterUI extends AbstractAccountReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(AccountReportFilterUI.class);
    
    /**
     * output class constructor
     */
    public AccountReportFilterUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		
		this.contProject.setVisible(false);
		this.prmtProject.setValue(null);
		this.prmtMoneyDefine.setValue(null);
		this.prmtRoom.setValue(null);
		this.pkFromDate.setValue(null);
		this.pkToDate.setValue(null);
		this.pkFromRevDate.setValue(null);
		this.pkToRevDate.setValue(null);
		
//		prmtProject.setEntityViewInfo(NewCommerceHelper.getPermitProjectView());
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", NewCommerceHelper.getPermitProjectIdSql(SysContext.getSysContext().getCurrentUserInfo()),CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.SIGN_VALUE));
		view.setFilter(filter);
		prmtRoom.setEntityViewInfo(view);
	}

	public RptParams getCustomCondition() {
		 RptParams pp = new RptParams();
//		 SellProjectInfo sellProject = (SellProjectInfo)this.prmtProject.getValue();
//		 if(sellProject!=null){
//			 pp.setObject("sellProject", sellProject);
//		 }else{
//			 pp.setObject("sellProject", null);
//		 }
		 Object[] room = (Object[])prmtRoom.getValue();
		 if(room!=null ){
			 pp.setObject("room",room);
		 }else{
			 pp.setObject("room",null);
		 }
		 Object[] moneyDefine = (Object[])prmtMoneyDefine.getValue();
		 if(moneyDefine!=null ){
			 pp.setObject("moneyDefine",moneyDefine);
		 }else{
			 pp.setObject("moneyDefine",null);
		 }
         if(this.pkFromDate.getValue()!=null){
    		 pp.setObject("fromDate", this.pkFromDate.getValue());
         }else{
        	 pp.setObject("fromDate", null);
         }
         if(this.pkToDate.getValue()!=null){
    		 pp.setObject("toDate", this.pkToDate.getValue());
         }else{
        	 pp.setObject("toDate", null);
         }
         if(this.pkFromRevDate.getValue()!=null){
    		 pp.setObject("fromRevDate", this.pkFromRevDate.getValue());
         }else{
        	 pp.setObject("fromRevDate", null);
         }
         if(this.pkToRevDate.getValue()!=null){
    		 pp.setObject("toRevDate", this.pkToRevDate.getValue());
         }else{
        	 pp.setObject("toRevDate", null);
         }
         if(this.txtFromDays.getIntegerValue()!=null){
        	 pp.setObject("fromDays", this.txtFromDays.getIntegerValue());
         }else{
        	 pp.setObject("fromDays", null);
         }
         if(this.txtToDays.getIntegerValue()!=null){
        	 pp.setObject("toDays", this.txtToDays.getIntegerValue());
         }else{
        	 pp.setObject("toDays", null);
         }
         if(this.txtFromNotproPortion.getBigDecimalValue()!=null){
        	 pp.setBigDecimal("fromNotproPortion", this.txtFromNotproPortion.getBigDecimalValue());
         }else{
        	 pp.setBigDecimal("fromNotproPortion", null);
         }
         if(this.txtToNotproPortion.getBigDecimalValue()!=null){
        	 pp.setBigDecimal("toNotproPortion", this.txtToNotproPortion.getBigDecimalValue());
         }else{
        	 pp.setBigDecimal("toNotproPortion", null);
         }
		 return pp;
	}
	public void onInit(RptParams params) throws Exception {
	}
	public void setCustomCondition(RptParams params) {
//		this.prmtProject.setValue(params.getObject("sellProject"));
		this.prmtRoom.setValue(params.getObject("room"));
		this.prmtMoneyDefine.setValue(params.getObject("moneyDefine"));
		this.pkFromDate.setValue(params.getObject("fromDate"));
		this.pkToDate.setValue(params.getObject("toDate"));
		this.pkFromRevDate.setValue(params.getObject("fromRevDate"));
		this.pkToRevDate.setValue(params.getObject("toRevDate"));
		this.txtFromDays.setValue(params.getObject("fromDays"));
		this.txtToDays.setValue(params.getObject("toDays"));
		this.txtFromNotproPortion.setValue(params.getBigDecimal("fromNotproPortion"));
		this.txtToNotproPortion.setValue(params.getBigDecimal("toNotproPortion"));
	}
	protected void prmtProject_dataChanged(DataChangeEvent e) throws Exception {
//		EntityViewInfo view = new EntityViewInfo();
//		FilterInfo filter = new FilterInfo();
//		SellProjectInfo  sellInfo = (SellProjectInfo)prmtProject.getValue();
//		if(sellInfo!=null){
//			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", SHEManageHelper.getAllSellProjectCollection(null,sellInfo),CompareType.INCLUDE));
//			filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.SIGN_VALUE));
//		}else if(this.prmtProject.getValue()==null){
//			filter.getFilterItems().add(new FilterItemInfo("building.sellProject.id", NewCommerceHelper.getPermitProjectIdSql(SysContext.getSysContext().getCurrentUserInfo()),CompareType.INNER));
//			filter.getFilterItems().add(new FilterItemInfo("sellState", RoomSellStateEnum.SIGN_VALUE));
//		}
//		view.setFilter(filter);
//		prmtRoom.setEntityViewInfo(view);
	}
}