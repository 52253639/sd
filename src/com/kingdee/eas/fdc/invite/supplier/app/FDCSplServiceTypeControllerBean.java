package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.ControlBeanException;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierException;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.framework.TreeBaseException;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class FDCSplServiceTypeControllerBean extends AbstractFDCSplServiceTypeControllerBean
{
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 8425822493165396692L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplServiceTypeControllerBean");

	
	/**
	 * @description		根节点创建时父节点为空在list界面查询不出来。所以强行赋值
	 * @author			GeneZhou		
	 * @createDate		2010-11-22
	 * @param			ctx,model
	 * @return			pk	
	 *	
	 * @version			EAS6.0
	 * @see						
	 */
	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo)model;
		IObjectPK pk = null;
		try {
			pk = super._addnew(ctx, info);
			if(info.getParent()==null){
				info.setParent(info);
				super.update(ctx, pk, info);
			}
			
		} catch (TreeBaseException e) {
			logger.info(e);
			throw new ControlBeanException(ControlBeanException.SERVICEEXCEPTION);
		}
		return pk;
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param ctx
	 * @param pk
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException									
	 * @version			EAS7.0
	 * @see	@see com.kingdee.eas.fdc.basedata.app.AbstractFDCTreeBaseDataControllerBean#_addnew(com.kingdee.bos.Context, com.kingdee.bos.dao.IObjectPK, com.kingdee.bos.dao.IObjectValue)					
	 */
	protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		try {
			
			super._addnew(ctx, pk, model);
		} catch (TreeBaseException e) {
			// TODO: handle exception
			throw new SupplierException(SupplierException.BIZDATEEXCEPTION);
		}
	}
	/**
	 * @description		
	 * @author			杜余		
	 * @createDate		2010-12-9
	 * @param ctx
	 * @param treeBaseInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws TreeBaseException									
	 * @version			EAS7.0
	 * @see	@see com.kingdee.eas.framework.app.TreeBaseControllerBean#checkLNForTree(com.kingdee.bos.Context, com.kingdee.eas.framework.TreeBaseInfo)					
	 */
	protected void checkLNForTree(Context ctx, TreeBaseInfo treeBaseInfo)
			throws BOSException, EASBizException, TreeBaseException {
		// TODO Auto-generated method stub
		//super.checkLNForTree(ctx, treeBaseInfo);
	}
	/**
	 * @description		删除功能：判断是否引用、是否为根节点分别执行删除操作
	 * @author			GeneZhou
	 * @createDate		2010-11-22
	 * @param			IObjectPK pk
	 * @return					
	 *	
	 * @version			EAS6.0
	 * @see						
	 */
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		super._delete(ctx, pk);
		//Get ServiceTypeId
		String id=pk.toString();
		/**
		 * 分别查询服务类型是否被引用SQL语句
		 */
		String sql_e="SELECT fparentid FROM T_SPL_SERVICETYPE where fparentid=? or fid=?";
		String sql_f="update T_SPL_SERVICETYPE set fisleaf = 1 where fid = ?";
		
		FDCSQLBuilder bulide=new FDCSQLBuilder(ctx);
		bulide.appendSql(sql_e);
		bulide.addParam(id);
		bulide.addParam(id);
		
		IRowSet set_e=bulide.executeQuery();
		
		//判断是否为一级子节点，其下是否有叶子节点
		FDCSplServiceTypeInfo info = FDCSplServiceTypeFactory.getLocalInstance(ctx).getFDCSplServiceTypeInfo(new ObjectUuidPK(id));
		if(info.getLevel()==1 && set_e.size()>1){
			super._delete(ctx, pk);
			return;
		}else
		{
			String parnetid = null;
			if(null != info.getParent().getId().toString()){
				parnetid = info.getParent().getId().toString();
				FDCSQLBuilder bulidf=new FDCSQLBuilder(ctx);
				bulidf.appendSql(sql_f);
				bulidf.addParam(parnetid);
				//将一级子节点的根节点修改为叶子节点
				bulidf.executeUpdate();
			}
			
		}
	}


	public static void setLogger(Logger logger) {
		FDCSplServiceTypeControllerBean.logger = logger;
	}




	public static Logger getLogger() {
		return logger;
	}
	protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("serviceType.id", arg1.toString()));
		if(FDCSupplierServiceTypeFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商档案登记引用，不能进行删除操作！"));
		}
	}
	
	
}