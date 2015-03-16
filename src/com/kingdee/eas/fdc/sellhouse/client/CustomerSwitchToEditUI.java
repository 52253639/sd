/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.util.List;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;

/**
 * output class name
 */
public class CustomerSwitchToEditUI extends AbstractCustomerSwitchToEditUI
{
    public CustomerSwitchToEditUI() throws Exception {
		super();
    }

	private static final Logger logger = CoreUIObject.getLogger(CustomerSwitchToEditUI.class);

	protected IObjectValue createNewData() {
		// TODO 自动生成方法存根
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return null;
	}
    
	public void onLoad() throws Exception {
		super.onLoad();
		this.f7salesman.setData(SysContext.getSysContext().getCurrentUserInfo());
	}
	
	protected void loadData() throws Exception {
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		UserInfo userInfo = (UserInfo) this.f7salesman.getData();
		List selectedIdValues = (List) this.getUIContext().get("SelectedIdValues");
		FDCCustomerFactory.getRemoteInstance().switchTo(selectedIdValues, userInfo.getId().toString());
		((ListUI)this.getUIContext().get("ListUI")).refreshList();
		this.destroyWindow();
	}
	
}