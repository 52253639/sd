package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplPeriodAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)							
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		定期评审服务端
 *		
 * @author		陈伟
 * @version		EAS7.0		
 * @createDate	2010-12-1	 
 * @see						
 */
public class FDCSplPeriodAuditBillControllerBean extends AbstractFDCSplPeriodAuditBillControllerBean
{
    /**
	 * 1.0
	 */
	private static final long serialVersionUID = -5246587620336374968L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplPeriodAuditBillControllerBean");
    
    /**
     * @description		审批时对供应商改变状态
     * @author			陈伟		
     * @createDate		2010-12-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
	  EASBizException {    	
      super._audit(ctx, billId); 
        /*
         * 回写供应商状态
         */
    	String sql = "";
    	try {
	    	List list = new ArrayList();
	    	list.add(billId);
	        /*
	         * 评审历史加一
	         */
            sql ="update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis+1  where FID=(select FSupplierID from T_SPL_FDCSplPeriodAuditBill as se where se.FID =? )";
			this.querySQL(ctx,sql,list,false,0);
	        /*
	         * 获取评审类型
	         */
			sql="Select auditBillEntry.fgrade ,serviceType.fid,auditBill.FSupplierID,auditBillEntry.FSupplierTypeID,serviceType.FSeq from T_SPL_FDCSplPeriAuditBillEntry as auditBillEntry "+
    	      " left   JOIN   T_SPL_FDCSplPeriodAuditBill as auditBill  on   auditBill.fid = auditBillEntry.FAuditBillID  "+
    	      " left    JOIN  T_SPL_FDCSupplierServiceType as serviceType on   auditBillEntry.FSupplierTypeID=serviceType.FServiceTypeID and auditBill.FSupplierID=serviceType.FSupplierID "+
    	      " where   auditBill.FId = ?"+
    	      " order by serviceType.FSeq desc ";
			List valueList = this.querySQL(ctx,sql,list,true,5);
			int maxSeq = 0;
			for(int i=0;i<valueList.size();i++){
				List listValue = (List)valueList.get(i);
				
				String grade = (String)(listValue.get(0)==null?"":listValue.get(0));
				String fid = (String)(listValue.get(1)==null?"":listValue.get(1));
				String supplierID = (String)(listValue.get(2)==null?"":listValue.get(2));	
				String serviceTypeID = (String)(listValue.get(3)==null?"":listValue.get(3));	
				String seqStr =(String)(listValue.get(4)==null?"0":listValue.get(4));
				int seq =Integer.parseInt(seqStr);
				if(maxSeq<seq){
					maxSeq = seq;
				}
				String sqlUpdate = "";
				if(!grade.equals("") && !fid.equals("")){
					/*
					 * 对等级不为空的同时在供应商中存在的供应商类型回写
					 */
					sqlUpdate ="update T_SPL_FDCSupplierServiceType set FState =? where FID=?";
					list = new ArrayList();
					list.add(grade);
					list.add(fid);
					this.querySQL(ctx,sqlUpdate,list,false,0);
				}else if(!grade.equals("") && fid.equals("")){
					/*
					 * 对等级不为空的同时在供应商中不存在的供应商类型增加
					 */
					sqlUpdate ="insert into T_SPL_FDCSupplierServiceType (FState,FSupplierID,FServiceTypeID,FSeq,FID) values "+
                             "(?,?,?,?,? )";
					fid = new ObjectUuidPK(new BOSObjectType("591DD2B6")).toString(); 
					maxSeq=maxSeq+1;
					list = new ArrayList();
					list.add(grade);
					list.add(supplierID);
					list.add(serviceTypeID);		
					list.add(String.valueOf(maxSeq));	
					list.add(fid);
					this.querySQL(ctx,sqlUpdate,list,false,0);
				}
				
			}

    	} catch (SQLException e) {
			logger.error(e);
		}


     }
    /**
     * 
     * @description		查询通用方法
     * @author			陈伟		
     * @createDate		2010-11-21
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
    /**
     * @description		
     * @author			陈伟		
     * @createDate		2010-11-17
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._save(ctx, model);
	}
    protected IObjectPK _submit(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		return super._submit(ctx, model);
	}
    /**
     * @description		反审批回写
     * @author			陈伟		
     * @createDate		2010-12-9
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		List list = new ArrayList();
		list.add(billId);
		try {
			String sql = "update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis-1  where FID=(select FSupplierID from T_SPL_FDCSplPeriodAuditBill as se where se.FID =? )";
			this.querySQL(ctx, sql, list, false, 0);
		} catch (SQLException e) {
			logger.error(e);
		}
	}
    
    protected boolean isNeedWriteBack(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {
    	
    	//获取全部的合格供应商登记
    	HashMap gradesMap = new HashMap(); 
    	GradeSetUpCollection grades = GradeSetUpFactory.getLocalInstance(ctx).getGradeSetUpCollection(new EntityViewInfo());
    	GradeSetUpInfo grade = null;
    	for (Iterator it = grades.iterator(); it.hasNext();) {
    		grade = (GradeSetUpInfo)it.next();
    		if (grade != null) {
//    			gradesMap.put(grade.getName(), grade.getIsGrade());
    		}
    	}
    	//如果该单据上有对供应商的服务进行评价，并且至少有一项服务是合格的，则需要向供应商主数据回写
    	if (info instanceof FDCSplPeriodAuditBillInfo) {
    		FDCSplPeriodAuditBillInfo periodInfo = (FDCSplPeriodAuditBillInfo) info;
    		FDCSplPeriodAuditBillEntryCollection entrys = periodInfo.getAuditBill();
    		if (entrys != null) {
    			for (Iterator it = entrys.iterator(); it.hasNext();) {
    				FDCSplPeriodAuditBillEntryInfo entry = (FDCSplPeriodAuditBillEntryInfo)it.next();
    				if (entry.isAvailable()) {
    					IsGradeEnum isGrade = (IsGradeEnum)gradesMap.get(entry.getGrade());
    					if(isGrade != null && IsGradeEnum.ELIGIBILITY.equals(isGrade)) {
    						return true;
    					}
    				}
    			}
    		}
    	}
    	return false;
    }
}