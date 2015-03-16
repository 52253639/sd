/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.*;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.param.IOtherParam;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.param.ParamItemFactory;
import com.kingdee.eas.base.param.ParamItemInfo;
import com.kingdee.eas.base.param.client.ICustomParamUI;
import com.kingdee.eas.basedata.org.OrgTreeInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class TENParamEditUI extends AbstractTENParamEditUI implements IOtherParam, ICustomParamUI
{
	private static final Logger logger = CoreUIObject.getLogger(TENParamEditUI.class);
	
	protected SaleOrgUnitInfo curOrgUnit = null;
	protected HashMap paramHashMap = null;
	
	public TENParamEditUI() throws Exception {
		super();
	}

	public void otherParamSave() throws BOSException, EASBizException {
		ParamItemInfo itemInfo = ((ParamItemInfo)paramHashMap.get(""));
		if(itemInfo != null){
			itemInfo.setValue("");
			itemInfo.setIsControlSub(true);
			itemInfo.setIsModify(false);
			itemInfo.setOrgUnitID(curOrgUnit);
			ParamItemFactory.getRemoteInstance().submit(itemInfo);
		}
	}

	public void sendOrgInfo(OrgType orgType, OrgUnitInfo orgUnit, OrgTreeInfo orgTree) {
		try {
			if (curOrgUnit == null) {
				curOrgUnit = SysContext.getSysContext().getCurrentSaleUnit();
			}			
			HashMap paramHashMap = ParamControlFactory.getRemoteInstance().getParamHashMap(curOrgUnit.getId().toString(),"com.kingdee.eas.fdc.sellhouse.sellhouse");

			ParamItemInfo param = (ParamItemInfo)paramHashMap.get("");

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
    


}