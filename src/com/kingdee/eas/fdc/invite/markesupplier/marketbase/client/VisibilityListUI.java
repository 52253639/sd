/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.markesupplier.marketbase.client;

import java.awt.event.*;
import java.util.List;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityFactory;
import com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class VisibilityListUI extends AbstractVisibilityListUI
{
    private static final Logger logger = CoreUIObject.getLogger(VisibilityListUI.class);
    
    /**
     * output class constructor
     */
    public VisibilityListUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	this.actionCancel.setVisible(true);
    	this.actionCancelCancel.setVisible(true);
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    protected void cancelCancel() throws Exception {
    	super.cancelCancel();
    	
    	this.refresh(null);
    }
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
//    	super.actionCancel_actionPerformed(e);
    	checkSelected();
    	VisibilityInfo info = VisibilityFactory.getRemoteInstance().getVisibilityInfo(new ObjectUuidPK(this.getSelectedKeyForAll()));
    	if(info.isIsEnabled()){
    		info.setIsEnabled(false);
    		VisibilityFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
    		this.refresh(null);
    	}
    }
    
	public void actionCancelCancel_actionPerformed(ActionEvent actionevent)
			throws Exception {
//		super.actionCancelCancel_actionPerformed(actionevent);
		checkSelected();
    	VisibilityInfo info = VisibilityFactory.getRemoteInstance().getVisibilityInfo(new ObjectUuidPK(this.getSelectedKeyForAll()));
    	if(!info.isIsEnabled()){
    		info.setIsEnabled(true);
    		VisibilityFactory.getRemoteInstance().update(new ObjectUuidPK(info.getId()), info);
    		this.refresh(null);
    	}
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List listID = this.getSelectedIdValues();
		for (int i = 0; i < listID.size(); i++) {
			VisibilityInfo info = VisibilityFactory.getRemoteInstance().getVisibilityInfo(new ObjectUuidPK((String)listID.get(i)));
			if(info.isIsEnabled()){
				MsgBox.showInfo("单据已启用，不能执行此操作！");
				SysUtil.abort();
			}
		}
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List listID = this.getSelectedIdValues();
		for (int i = 0; i < listID.size(); i++) {
			VisibilityInfo info = VisibilityFactory.getRemoteInstance().getVisibilityInfo(new ObjectUuidPK((String)listID.get(i)));
			if(info.isIsEnabled()){
				MsgBox.showInfo("单据已启用，不能执行此操作！");
				SysUtil.abort();
			}
		}
		super.actionRemove_actionPerformed(e);
	}
    
	
    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo objectValue = new com.kingdee.eas.fdc.invite.markesupplier.marketbase.VisibilityInfo();
		
        return objectValue;
    }

}