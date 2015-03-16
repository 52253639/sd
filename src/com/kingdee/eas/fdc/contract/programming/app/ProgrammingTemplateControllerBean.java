package com.kingdee.eas.fdc.contract.programming.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingException;
import com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.IFWEntityStruct;

public class ProgrammingTemplateControllerBean extends AbstractProgrammingTemplateControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.programming.app.ProgrammingTemplateControllerBean");

	protected void _copy(Context ctx) throws BOSException {
		logger.assertLog(true, "复制功能");
	}
	
	protected void _checkNameBlank(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		//因名称缩进显示，调用基类校验时，不通过的话会把缩进效果去除所以覆盖掉基类方法、校验由前台完成
	}
	
	 protected void _checkNameDup(Context ctx , IObjectValue model) throws BOSException , EASBizException {
		//因名称缩进显示，调用基类校验时，不通过的话会把缩进效果去除所以覆盖掉基类方法、校验由前台完成
     }
}