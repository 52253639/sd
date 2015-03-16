package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class MoneyDefineControllerBean extends
		AbstractMoneyDefineControllerBean {
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.MoneyDefineControllerBean");

	private static String ImportFlag = "isImportAction";
	
	protected int _importToOrg(Context ctx, CoreBaseCollection col) throws BOSException, EASBizException {
		ctx.put(ImportFlag, Boolean.TRUE);
		return this.addnew(ctx, col).size();
	}

	
	
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		if(ctx.get(ImportFlag) == null ){
			super._checkNumberDup(ctx, model);
		}
		
	}
	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		if(ctx.get(ImportFlag) == null ){
			super._checkNameDup(ctx, model);
		}
		
	}

	protected void _checkNumberBlank(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		if(isUseIntermitNumber(ctx, model)){
			return ;
		}
		
		super._checkNumberBlank(ctx, model);
	}

	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		DataBaseCodeRuleHelper.handleIntermitNumber(ctx, (DataBaseInfo) model);
		return super._submit(ctx, model);
	}


	private boolean isUseIntermitNumber(Context ctx, IObjectValue model)
	throws BOSException, EASBizException{
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		String costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		if (StringUtils.isEmpty(costUnitId)) {
			return false;
		}
		if (iCodingRuleManager.isExist(model, costUnitId)) {
			if (iCodingRuleManager.isUseIntermitNumber(model, costUnitId)){
				return true;
			}
		}
		return false;
	}
}