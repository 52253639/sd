package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplChangeHistroyInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierException;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		供应商信息变更
 *		
 * @author		陈伟
 * @version		EAS7.0		
 * @createDate	2010-12-9	 
 * @see						
 */
public class FDCSplChangeHistroyControllerBean extends AbstractFDCSplChangeHistroyControllerBean
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -6671543567314441431L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplChangeHistroyControllerBean");
    /**
     * @description		审批时候对供应商改变版本和状态
     * @author			陈伟		
     * @createDate		2010-11-26
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
		FDCSplChangeHistroyInfo info = super.getFDCSplChangeHistroyInfo(ctx, pk );
		SupplierStockInfo supplier= info.getSupplier(); 
    	List list = new ArrayList();
    	list.add(supplier.getId().toString());
        /*
         * 将变更后的供应商是否最新设置为true，将原供应商是否最新设置为false
         */
        String sql ="";
		try {
			sql="update T_FDC_SupplierStock  set  FIsLatestVer=1 , fstate=3 where FSrcSupplierID=? and fversion>1";
			this.querySQL(ctx,sql,list,false,0);
			sql="update T_FDC_SupplierStock  set  FIsLatestVer=0 where fid=?";
			this.querySQL(ctx,sql,list,false,0);

		} catch (SQLException e) {
			logger.error(e);
		}
    	super._audit(ctx, billId);
    }
    
    /**
     * @description		反审批将状态回写
     * @author			陈伟		
     * @createDate		2010-11-29
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	IObjectPK pk = new ObjectUuidPK(billId.toString());
		FDCSplChangeHistroyInfo info = super.getFDCSplChangeHistroyInfo(ctx, pk );
		SupplierStockInfo supplier= info.getSupplier(); 
    	List list = new ArrayList();
    	list.add(supplier.getId().toString());
        /*
         * 将变更后的供应商是否最新设置为false，将原供应商是否最新设置为true
         */
        String sql ="";
		try {
			//如果有更新的版本就不能反审批
			sql="select FIsLatestVer from T_FDC_SupplierStock where FIsLatestVer >( select max( FIsLatestVer) from T_FDC_SupplierStock where FSrcSupplierID=?) and fnumber = ( select distinct fnumber from T_FDC_SupplierStock where FSrcSupplierID=?)";
			list.add(supplier.getId().toString());
			List supplierNumber =this.querySQL(ctx,sql,list,true,1);
			if(supplierNumber != null && supplierNumber.size()>0){
				
				throw new SupplierException(SupplierException.UNAUDITEXCEPTION);
			}
			list = new ArrayList();
	    	list.add(supplier.getId().toString());

			sql="update T_FDC_SupplierStock  set  FIsLatestVer=0 , fstate=3 where FSrcSupplierID=? and fversion>1";
			this.querySQL(ctx,sql,list,false,0);
			sql="update T_FDC_SupplierStock  set  FIsLatestVer=1, fstate=3 where fid=?";
			this.querySQL(ctx,sql,list,false,0);
		} catch (SQLException e) {
			logger.error(e);
		}
    	super._unAudit(ctx, billId);
    }
    /**
     * @description		通用查询方法
     * @author			陈伟		
     * @createDate		2010-11-26
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    private List querySQL(Context ctx,String sql,List valueList,boolean boo,int valueSize) throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	List list = new ArrayList();
    	
    	builder.appendSql(sql);
    	for(int i = 0 ; i < valueList.size() ; i++){
    		builder.addParam(valueList.get(i).toString().trim());
    	}
    	if(boo){
	    	IRowSet irowSet = builder.executeQuery();
	    	while(irowSet.next()){
	    		int i = 1 ;
	    		List lists  = new ArrayList();
	    		for(int j = 0 ; j < valueSize ; j++){
	    			lists.add(irowSet.getString(i++));
	    		}
	    		list.add(lists);
	    		
	    	}
	    	return list ;
    	}else{
    		builder.executeUpdate();
    		return null ;
    	}
    }
}