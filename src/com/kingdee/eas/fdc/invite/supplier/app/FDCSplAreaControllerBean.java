package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.ControlBeanException;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCSplAreaControllerBean extends AbstractFDCSplAreaControllerBean
{
    /**
	 * 序列号                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     
	 */
	private static final long serialVersionUID = 1172466535737627494L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplAreaControllerBean");

	protected void _IsNdelete(Context ctx, String areaName)
			throws BOSException, EASBizException {
		
		FDCSQLBuilder bsql=new FDCSQLBuilder(ctx);
		bsql.appendSql("SELECT FServiceArea FROM T_FDC_SUPPLIERSTOCK WHERE FServiceArea like ? or FServiceArea like ? or FServiceArea like ? or FServiceArea=?");
		bsql.addParam("%|"+areaName);
		bsql.addParam(areaName+"|%");
		bsql.addParam("%|"+areaName+"|%");
		bsql.addParam(areaName);
		IRowSet set= bsql.executeQuery(ctx);
		if(set.size()>0)
		{
			/*
			 * 检测被引用抛出异常警告
			 */
			throw new ControlBeanException(ControlBeanException.AREA_DELETEEXCEPETION);
		}
			
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		FDCSplAreaControllerBean.logger = logger;
	}
	protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcSplArea.id", arg1.toString()));
		if(SupplierSplAreaEntryFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商档案登记引用，不能进行删除操作！"));
		}
	}
}