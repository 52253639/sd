/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import org.apache.log4j.Logger;

import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.invite.ScalingRuleFactory;
import com.kingdee.eas.fdc.invite.ScalingRuleInfo;
import com.kingdee.eas.framework.ICoreBase;

/**
 * output class name
 */
public class ScalingRuleListUI extends AbstractScalingRuleListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ScalingRuleListUI.class);
    
    /**
     * output class constructor
     */
    public ScalingRuleListUI() throws Exception
    {
        super();
    }

	@Override
	protected FDCDataBaseInfo getBaseDataInfo() {
		return new ScalingRuleInfo();
	}

	@Override
	protected ICoreBase getBizInterface() throws Exception {
		return ScalingRuleFactory.getRemoteInstance();
	}

	@Override
	protected String getEditUIName() {
		return ScalingRuleEditUI.class.getName();
	}

    

}