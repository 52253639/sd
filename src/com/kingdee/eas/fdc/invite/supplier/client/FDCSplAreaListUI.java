/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.ActionEvent;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

public class FDCSplAreaListUI extends AbstractFDCSplAreaListUI
{
	private static final Logger logger = CoreUIObject.getLogger(FDCSplAreaListUI.class);
    
	public FDCSplAreaListUI() throws Exception
    {
        super();
    }
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new FDCSplAreaInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return FDCSplAreaFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return FDCSplAreaEditUI.class.getName();
	}
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
}