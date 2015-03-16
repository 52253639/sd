package com.kingdee.eas.fdc.sellhouse.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Iterator;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryFactory;
import com.kingdee.eas.fdc.basedata.ProjectInfo;
import com.kingdee.eas.fdc.sellhouse.IWebMarkProcess;
import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionCollection;
import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionFactory;
import com.kingdee.eas.fdc.sellhouse.WebMarkFunctionInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessCollection;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessFactory;
import com.kingdee.eas.fdc.sellhouse.WebMarkProcessInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.sellhouse.WebMarkSchemaCollection;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.app.DataBaseControllerBean;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.util.app.DbUtil;

public class WebMarkSchemaControllerBean extends AbstractWebMarkSchemaControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.WebMarkSchemaControllerBean");

	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
	{
		super._addnew(ctx, pk, model);
	}
	protected void _update(Context ctx, IObjectPK pk, IObjectValue model) throws BOSException, EASBizException
    {
		//处理方案修改时步骤总是新增的问题:把当前方案下所有步骤删掉先，但要确保model中每个步骤数据有原来的Id
		String sqlOld = "delete from t_she_webmarkprocess where fparentid in " 
			+ "(select fid from t_she_webmarkfunction where fparentid='" + pk.toString() + "')";
		DbUtil.execute(ctx,sqlOld);
		super._update(ctx, pk, model);
    }
}