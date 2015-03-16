/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.invite.NewListTempletPageFactory;
import com.kingdee.eas.fdc.invite.PageHeadFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.util.UIConfigUtility;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PageHeadListUI extends AbstractPageHeadListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PageHeadListUI.class);
    public OrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();
    /**
     * output class constructor
     */
    public PageHeadListUI() throws Exception
    {
        super();
    }

	protected String getEditUIName() {
		return PageHeadEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return PageHeadFactory.getRemoteInstance();
	}

	protected String getEditUIModal()
    {
        // return UIFactoryName.MODEL;
        // return UIFactoryName.NEWWIN;
        // 2006-4-29 胡博要求加入根据配置项来读取UI打开方式。
        String openModel = UIConfigUtility.getOpenModel();
        if (openModel != null)
        {
            return openModel;
        }
        else
        {
            return UIFactoryName.MODEL;
        }
    }

	public void onLoad() throws Exception {
		if (currentOrg == null || !currentOrg.isIsCompanyOrgUnit()) {
			MsgBox.showWarning(this, "非财务组织不能进入!");
			abort();
		}

		super.onLoad();
		if(OrgConstants.DEF_CU_ID.equals(currentOrg.getId().toString())){
			actionAddNew.setEnabled(true);
			actionEdit.setEnabled(true);
			actionRemove.setEnabled(true);
		}else{
			actionAddNew.setEnabled(false);
			actionEdit.setEnabled(false);
			actionRemove.setEnabled(false);
		}
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		ArrayList ids = getSelectedIdValues();
		for (int i = 0; i < ids.size(); i++) {
			String id = (String) ids.get(i);
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("pageHead.id",id));
			if(NewListTempletPageFactory.getRemoteInstance().exists(filter)){
				MsgBox.showError("页签被引用,不能删除");
				return;
			}
		}
		super.actionRemove_actionPerformed(e);
	}
}