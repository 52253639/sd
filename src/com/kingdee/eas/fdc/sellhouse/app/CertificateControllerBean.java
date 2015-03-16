package com.kingdee.eas.fdc.sellhouse.app;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.insider.InsiderApplicationCollection;
import com.kingdee.eas.fdc.insider.InsiderApplicationFactory;
import com.kingdee.eas.fdc.insider.InsiderCollection;
import com.kingdee.eas.fdc.insider.InsiderFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerCollection;
import com.kingdee.eas.framework.DataBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class CertificateControllerBean extends
		AbstractCertificateControllerBean {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1538049918292700821L;
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.CertificateControllerBean");

	protected void _isDeleteCertificate(Context ctx, String id)
			throws BOSException, EASBizException {

		isForCustomer(ctx,id);
		isForPurchase(ctx,id);
		isForPurchaseChange(ctx,id);
		isForTenancy(ctx,id);
		isForInsider(ctx,id);
		isForInsiderAppliation(ctx,id);
	}
	
	public void _checkNameDup(Context ctx, DataBaseInfo dataBaseInfo)
			throws BOSException, EASBizException {
		//super.checkNameDup(ctx, dataBaseInfo);
	}
	public void checkNameDup(Context ctx, DataBaseInfo dataBaseInfo)
			throws BOSException, EASBizException {
		//super.checkNameDup(ctx, dataBaseInfo);
	}
	
	protected void _checkNameDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		//super._checkNameDup(ctx, model);
	}

	private void isForCustomer(Context ctx,String id) throws BOSException, EASBizException{
		
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("certificateName.id", id, CompareType.EQUALS));
		view.setFilter(filterInfo);
		FDCCustomerCollection coll  = FDCCustomerFactory.getLocalInstance(ctx).getFDCCustomerCollection(view);
		if(coll!=null && coll.size()>0){
			throw new BOSException("certificateName in customer!");
		}
	}

	private void isForInsider(Context ctx,String id) throws BOSException, EASBizException{
		
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("certificateType.id", id, CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("fdcCustomer.certificateName.id", id, CompareType.EQUALS));
		filterInfo.setMaskString("#0 or #1");
		view.setFilter(filterInfo);
		InsiderCollection coll  = InsiderFactory.getLocalInstance(ctx).getInsiderCollection(view);
		if(coll!=null && coll.size()>0){
			throw new BOSException("certificateName in insider!");
		}
	}
	
	private void isForInsiderAppliation(Context ctx,String id) throws BOSException, EASBizException{
		
		EntityViewInfo view  = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.getFilterItems().add(
				new FilterItemInfo("certificateName.id", id, CompareType.EQUALS));
		filterInfo.getFilterItems().add(
				new FilterItemInfo("customer.certificateName.id", id, CompareType.EQUALS));
		filterInfo.setMaskString("#0 or #1");
		view.setFilter(filterInfo);
		InsiderApplicationCollection coll  = InsiderApplicationFactory.getLocalInstance(ctx).getInsiderApplicationCollection(view);
		if(coll!=null && coll.size()>0){
			throw new BOSException("certificateName in insiderAppliation!");
		}
	}

	private void isForTenancy(Context ctx,String id) throws BOSException, EASBizException{
		
	    StringBuffer sbf = new StringBuffer();
	    sbf.append(" select cer.fid,cer.fnumber,cer.fname_l2 from T_TEN_TenancyBill ten ");
	    sbf.append(" left join T_TEN_TenancyCustomerEntry entry on ten.fid = entry.FTenancyBillID ");
	    sbf.append(" left join t_she_fdccustomer fdc on entry.FFdcCustomerID = fdc.fid ");
	    sbf.append(" left join t_she_certificate cer on fdc.FCertificateName = cer.fid ");
	    sbf.append(" where cer.fid='"+id+"'");
	    
	    try {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(sbf.toString());
			IRowSet row = sql.executeQuery();
			if (row.next()) {
				throw new BOSException("certificateName in Tenancy!");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void isForPurchase(Context ctx,String id) throws BOSException, EASBizException{
	
	    StringBuffer sbf = new StringBuffer();
	    sbf.append("select cid,cnumber,cname from (");
	    sbf.append(" select cer.fid as cid,cer.fnumber as cnumber,cer.fname_l2 as cname from t_she_purchase pur ");
	    sbf.append(" left join T_SHE_PurchaseCustomerInfo pc on pur.fid = pc.fheadid ");
	    sbf.append(" left join t_she_fdccustomer fdc on pc.FCustomerID = fdc.fid ");
	    sbf.append(" left join t_she_certificate cer on fdc.FCertificateName = cer.fid ");
	    sbf.append(" where cer.fid='"+id+"'");
	    sbf.append(" union all ");
	    sbf.append(" select cer.fid as cid,cer.fnumber as cnumber,cer.fname_l2 as cname  from t_she_purchase pur ");
	    sbf.append(" left join T_SHE_PurchaseCustomer pc on pur.fid = pc.FParentID ");
	    sbf.append(" left join t_she_certificate cer on pc.FCertificateName = cer.fid ");
	    sbf.append(" where cer.fid='"+id+"'");
	    sbf.append(") as temp");
	    
	    try {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(sbf.toString());
			IRowSet row = sql.executeQuery();
			if (row.next()) {
				throw new BOSException("certificateName in purchase!");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	private void isForPurchaseChange(Context ctx,String id) throws BOSException, EASBizException{
		
	    StringBuffer sbf = new StringBuffer();
	    sbf.append(" select cer.fid,cer.fnumber,cer.fname_l2 from T_SHE_PChCustomer pch ");
	    sbf.append(" left join T_SHE_PChCusomerEntry entry on pch.fid = entry.FHeadID ");
	    sbf.append(" left join t_she_fdccustomer fdc on entry.FCustomerID = fdc.fid ");
	    sbf.append(" left join t_she_certificate cer on fdc.FCertificateName = cer.fid ");
	    sbf.append(" where cer.fid='"+id+"'");
	    
	    try {
			FDCSQLBuilder sql = new FDCSQLBuilder(ctx);
			sql.appendSql(sbf.toString());
			IRowSet row = sql.executeQuery();
			if (row.next()) {
				throw new BOSException("certificateName in purchaseChange!");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
}
