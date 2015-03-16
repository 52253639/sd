/**
 * output package name
 */
package com.kingdee.eas.fdc.basecrm.client;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.client.f7.SaleF7;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ShareCustomerUI extends AbstractShareCustomerUI
{
    private static final Logger logger = CoreUIObject.getLogger(ShareCustomerUI.class);
    
    /**
     * output class constructor
     */
    public ShareCustomerUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	super.onLoad();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	Set ids = FDCSysContext.getInstance().getCusMgeMap().keySet();
    	filter.getFilterItems().add(new FilterItemInfo("id", ids, CompareType.INCLUDE));
    	view.setFilter(filter);
    	
    	SaleF7 s = new SaleF7();
    	s.setIsCUFilter(false);
    	s.setLevel(10);
    	f7ShareOrg.setSelector(s);
//    	f7ShareOrg.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleOrgUnitQuery");
//		f7ShareOrg.setEntityViewInfo(view);
		
		
		f7ShareOrg.setCommitFormat("$number$");
		f7ShareOrg.setEditFormat("$number$");
        f7ShareOrg.setDisplayFormat("$name$");
        
    }


    protected void btnOK_actionPerformed(ActionEvent e) throws Exception {
    	FullOrgUnitInfo saleOrg = ((OrgUnitInfo)this.f7ShareOrg.getValue()).castToFullOrgUnitInfo();
    	
    	if(saleOrg == null){
    		MsgBox.showInfo(this, "请录入共享组织！");
    		this.abort();
    	}
    	
    	Set ids = FDCSysContext.getInstance().getCusMgeMap().keySet();
    	if(!ids.contains(saleOrg.getId().toString())){
    		MsgBox.showInfo(this, "共享组织只能选择客户管理组织，请重新选择！");
    		this.f7ShareOrg.setValue(null);
    		this.abort();
    	}
    	
    	this.getUIContext().put("shareOrg", saleOrg);
    	this.destroyWindow();
    }

    protected void btnCancel_actionPerformed(ActionEvent e) throws Exception {
    	this.destroyWindow();
    }
    
}