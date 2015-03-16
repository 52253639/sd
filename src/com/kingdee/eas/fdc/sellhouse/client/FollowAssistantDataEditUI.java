/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataFactory;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.FollowAssistantDataTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * @auther qinyouzhen 
 * @date 2011-06-08
 * 跟进辅助资料编辑
 */
public class FollowAssistantDataEditUI extends AbstractFollowAssistantDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(FollowAssistantDataEditUI.class);
    
    /**
     * output class constructor
     */
    public FollowAssistantDataEditUI() throws Exception
    {
        super();
        this.prmtSellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
        this.prmtType.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7FollowAssistantDataTypeQuery");
        this.prmtSellProject.setEnabled(false);
        this.prmtType.setEnabled(false);
    }

	protected IObjectValue createNewData() {
		FollowAssistantDataTypeInfo typeInfo = (FollowAssistantDataTypeInfo) this.getUIContext().get("typeInfo");
		FollowAssistantDataInfo info = new FollowAssistantDataInfo();
		info.setType(typeInfo);
		info.setIsEnabled(true);//默认为启用
		SellProjectInfo sellProjectInfo = (SellProjectInfo) this.getUIContext().get("sellProjectInfo");
		info.setSellProject(sellProjectInfo);
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FollowAssistantDataFactory.getRemoteInstance();
	}

   
}