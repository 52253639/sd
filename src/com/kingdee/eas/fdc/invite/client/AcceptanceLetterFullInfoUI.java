/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.invite.AcceptanceLetterFactory;
import com.kingdee.eas.framework.ICoreBase;

/**
 * 中标通知书 仅用于招标执行信息中展示当前招标立项下的中标通知书
 */
public class AcceptanceLetterFullInfoUI extends AbstractAcceptanceLetterFullInfoUI
{
    private static final Logger logger = CoreUIObject.getLogger(AcceptanceLetterFullInfoUI.class);
    
    public AcceptanceLetterFullInfoUI() throws Exception
    {
        super();
    }

	protected ICoreBase getBizInterface() throws Exception {
		return AcceptanceLetterFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return AcceptanceLetterEditUI.class.getName();
	}
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	/**
	 * 仅展示 招标执行信息中当前招标立项下已审批的中标通知书
	 * 
	 * @author owen_wen 2011-04-19
	 */
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		FilterInfo filter = new FilterInfo();
		String invitePrjId = (String) this.getUIContext().get(UIContext.ID);
		filter.getFilterItems().add(new FilterItemInfo("inviteProject.id", invitePrjId));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
		viewInfo.setFilter(filter);
		return super.getQueryExecutor(queryPK, viewInfo);
	}
}