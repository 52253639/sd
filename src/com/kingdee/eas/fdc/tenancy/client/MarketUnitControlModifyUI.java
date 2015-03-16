/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.base.permission.UserType;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlCollection;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlFactory;
import com.kingdee.eas.fdc.tenancy.MarketUnitControlInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketUnitControlModifyUI extends AbstractMarketUnitControlModifyUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketUnitControlModifyUI.class);
    
    /**
     * output class constructor
     */
    public MarketUnitControlModifyUI() throws Exception
    {
        super();
    }

	protected IObjectValue createNewData() {
		MarketUnitControlInfo muConInfo = new MarketUnitControlInfo();
		OrgStructureInfo orgStrInfo = (OrgStructureInfo)this.getUIContext().get("OrgStructureInfo");
		muConInfo.setOrgUnit(orgStrInfo.getUnit());
		muConInfo.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		muConInfo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return muConInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return MarketUnitControlFactory.getRemoteInstance();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionAddNew.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionSave.setVisible(false);
		this.actionRemove.setVisible(false);
		this.actionCopy.setVisible(false);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isDelete", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isLocked", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("isForbidden", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.SYSTEM_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(UserType.AUTHENTICATEADMIN_VALUE), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type", new Integer(70), CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("type",null, CompareType.ISNOT));
		view.setFilter(filter);		
		this.prmtControler.setEntityViewInfo(view);
	}
	
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		UserInfo userInfo = (UserInfo)this.prmtControler.getValue();
		if(userInfo==null) {
			MsgBox.showInfo("管控人员不能为空！");
			return;
		}
		
		Date beginDate = (Date)this.pkAccessionDate.getValue();
		Date endDate = (Date)this.pkDimissionDate.getValue();
		
		if(beginDate==null || endDate==null) {
			MsgBox.showInfo("上岗和离岗日期都不能为空！");
			return;
		}
		if(beginDate.after(endDate)) {
			MsgBox.showInfo("上岗不能迟于离岗日期！");
			return;
		}
		
		//如果已经是该公司或上级公司的管控人员，则禁止再添加
		FullOrgUnitInfo orgUnitInfo = (FullOrgUnitInfo)this.prmtOrgUnit.getValue();
		MarketUnitControlCollection muConColl = MarketUnitControlFactory.getRemoteInstance()
					.getMarketUnitControlCollection("select orgUnit.name,orgUnit.number,orgUnit.longNumber where controler.id = '"+userInfo.getId()+"'");
		for(int i=0;i<muConColl.size();i++) {
			MarketUnitControlInfo muConInfo = muConColl.get(i);
			if(orgUnitInfo.getNumber().equals(muConInfo.getOrgUnit().getNumber()) ||  orgUnitInfo.getLongNumber().indexOf(muConInfo.getOrgUnit().getLongNumber()+"!")>=0) {
				MsgBox.showInfo("所选人员‘"+userInfo.getName()+"’已经是‘"+muConInfo.getOrgUnit().getName()+"’的管控人员，不必再重复添加了！");
				return;
			}
		}
		
		super.actionSubmit_actionPerformed(e);
	}
	

}