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
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * @(#)			GradeSetUpControllerBean.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		
 *		
 * @author		胥凯
 * @version		EAS7.0		
 * @createDate	2010-12-9	 
 * @see						
 */
public class GradeSetUpControllerBean extends AbstractGradeSetUpControllerBean
{
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -3065677113692471690L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.GradeSetUpControllerBean");
    
	 /**
	     * @description		
	     * @author			胥凯	
	     * @createDate		2010-12-2
	     * @param	
	     * @return					
	     *	
	     * @version			EAS1.0
	     * @see						
	     */
	protected void _isNdelete(Context ctx, String gradeName)
			throws BOSException, EASBizException {
		logger.info(gradeName);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	FDCSQLBuilder builderOne = new FDCSQLBuilder(ctx);
    	FDCSQLBuilder builderTwo = new FDCSQLBuilder(ctx);
		builder.appendSql("select auditEntry.fid from T_SPL_FDCSplQualAuditEntry as auditEntry where fgrade = ?");
		builderOne.appendSql("select auditEntry.fid from T_SPL_FDCSplPeriAuditBillEntry as auditEntry where fgrade=?");
		builderTwo.appendSql("select auditentry.fid from T_SPL_FDCSplKeepAuditEnty as auditentry where fgrade = ?");
		builder.addParam(gradeName);
		builderOne.addParam(gradeName);
		builderTwo.addParam(gradeName);
		IRowSet setTwo =builderTwo.executeQuery(ctx);
		IRowSet setOne = builderOne.executeQuery(ctx);
		IRowSet set = builder.executeQuery(ctx);
		if(set.size() > 0 || setOne.size()>0 || setTwo.size() > 0)
		{
			/**
			 * 已经被引用，不能执行此操作
			 */
			throw new ControlBeanException(ControlBeanException.GRADE_DELETEEXCEPTION);
			
		}
		
	}
	protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("grade.id", arg1.toString()));
		if(SupplierReviewGatherFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行删除操作！"));
		}
		if(SupplierStockFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行删除操作！"));
		}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcGrade.id", arg1.toString()));
		if(SupplierReviewGatherFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行删除操作！"));
		}
	}
}