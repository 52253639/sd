/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.client.Util;
import com.kingdee.eas.fdc.invite.InviteFileItemTypeEnum;

/**
 * 技术条款序时薄
 */
public class InviteFileItemTechListUI extends AbstractInviteFileItemTechListUI
{
	public void onLoad() throws Exception {
		super.onLoad();
	    this.btnQuery.setVisible(true);
	    this.actionReversion.setEnabled(true);
	    this.actionReversion.setVisible(true);
	    getUIContext().put("billType", InviteFileItemTypeEnum.TECHCLAUSE_VALUE);
	}
	
	private static final Logger logger = CoreUIObject.getLogger(InviteFileItemTechListUI.class);
    
    /**
     * output class constructor
     */
    public InviteFileItemTechListUI() throws Exception
    {
        super();       
    }

}