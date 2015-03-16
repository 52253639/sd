/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.LevelSetUpInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class LevelSetUpListUI extends AbstractLevelSetUpListUI
{
    public LevelSetUpListUI() throws Exception {
		super();
	}
	private static final Logger logger = CoreUIObject.getLogger(LevelSetUpListUI.class);
    
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new LevelSetUpInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return LevelSetUpFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return LevelSetUpEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}

}