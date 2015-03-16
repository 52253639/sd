package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepAuditEntyInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplKeepContractAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * @(#)			FDCSplKeepContractAuditBillControllerBean.java				
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		履约评估的服务端
 *		
 * @author		胥凯
 * @version		EAS7.0		
 * @createDate	2010-12-9	 
 * @see						
 */
public class FDCSplKeepContractAuditBillControllerBean extends AbstractFDCSplKeepContractAuditBillControllerBean
{
    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 7211616842463873805L;
	private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplKeepContractAuditBillControllerBean");
     /**
         * @description		审批
         * @author			胥凯	
         * @createDate		2010-12-1
         * @param			Context   BOSUuid
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._audit(ctx, billId);
    	
    	String sql = "";
         try {
        	 List list = new ArrayList();
        	 list.add(billId); 
        	 /*
        	  * 签约历史加一
        	  */
            sql ="update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis+1  where FID=(select FSupplierID from T_SPL_FDCSplKeepContractA as se where se.FID =? )";
          	this.exequeSQl(ctx,sql,list,false,0);
          	StringBuffer buffer = new StringBuffer();
          	buffer.append("Select auditBillEntry.fgrade ,serviceType.fid,auditBill.FSupplierID,auditBillEntry.FSupplierTypeID,serviceType.FSeq from T_SPL_FDCSplKeepAuditEnty as auditBillEntry ");
          	buffer.append(" left   JOIN   T_SPL_FDCSplKeepContractA as auditBill  on   auditBill.fid = auditBillEntry.FAuditBillID  ");
          	buffer.append(" left    JOIN  T_SPL_FDCSupplierServiceType as serviceType on   auditBillEntry.FSupplierTypeID=serviceType.FServiceTypeID ");
          	buffer.append(" and auditBill.FSupplierID=serviceType.FSupplierID");
          	buffer.append(" where   auditBill.FId = ? ");
          	buffer.append(" order by serviceType.FSeq desc ");
          	sql = buffer.toString();
          	List resultList = this.exequeSQl(ctx, sql, list, true, 5);
          	int sequence = 0;
          	for(int i =0;i<resultList.size();i++){
          		List valueList = (List) resultList.get(i);
          		String grade = (String)(valueList.get(0)==null?"":valueList.get(0));
          		String fid = (String)(valueList.get(1)==null?"":valueList.get(1));
          		String fSupplierID = (String)(valueList.get(2)==null?"":valueList.get(2));
          		String fSupplierTypeID = (String)(valueList.get(3)==null?"":valueList.get(3));
          		String fSeq = (String)(valueList.get(4)==null?"0":valueList.get(4));
          		int seq = Integer.parseInt(fSeq);
          		if(sequence < seq){
          			sequence = seq;
          		}
          		String updateSql = "";
          		if(!"".equals(grade) && !"".equals(fid)){
          			updateSql = "update T_SPL_FDCSupplierServiceType set FState =? where FID=?";
          			list = new ArrayList();
          			list.add(grade);
          			list.add(fid);
          			this.exequeSQl(ctx, updateSql, list, false, 0);
          		}else if(!"".equals(grade) && "".equals(fid)){
          			updateSql ="insert into T_SPL_FDCSupplierServiceType (FState,FSupplierID,FServiceTypeID,FSeq,FID) values "+
                    "(?,?,?,?,? )";
          			fid = new ObjectUuidPK(new BOSObjectType("591DD2B6")).toString(); 
          			sequence=sequence+1;
          			list = new ArrayList();
          			list.add(grade);
          			list.add(fSupplierID);
          			list.add(fSupplierTypeID);		
          			list.add(String.valueOf(sequence));	
          			list.add(fid);
          			this.exequeSQl(ctx,updateSql,list,false,0);
          		}
          	}
          	
		} catch (SQLException e) { 
			logger.error(e);
		}
    }
     /**
         * @description		执行SQL查询
         * @author			胥凯	
         * @createDate		2010-12-1
         * @param			
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    private List exequeSQl(Context ctx,String sql,List valueList,boolean boo,int valueSize) throws BOSException, SQLException{
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	List list = new ArrayList();
    	valueList.size();
    	builder.appendSql(sql);
    	for(int i = 0 ; i < valueList.size() ; i++){
    		builder.addParam(valueList.get(i).toString().trim());
    	}
    	if(boo){
	    	IRowSet irowSet = builder.executeQuery();
	    	while(irowSet.next()){
	    		int q = 1 ;
	    		List lists  = new ArrayList();
	    		for(int j = 0 ; j < valueSize ; j++){
	    			lists.add(irowSet.getString(q++));
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
         * @description		反审批
         * @author			胥凯	
         * @createDate		2010-12-1
         * @param	
         * @return					
         *	
         * @version			EAS7.0
         * @see						
         */
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
    		EASBizException {
    	super._unAudit(ctx, billId);
    	 List list = new ArrayList();
      	  list.add(billId); 
            try {
               String sql ="update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis-1  where FID=(select FSupplierID from T_SPL_FDCSplKeepContractA as se where se.FID =? )";
             	this.exequeSQl(ctx,sql,list,false,0);
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
    	if (info instanceof FDCSplKeepContractAuditBillInfo) {
    		FDCSplKeepContractAuditBillInfo keepInfo = (FDCSplKeepContractAuditBillInfo) info;
    		FDCSplKeepAuditEntyCollection entrys = keepInfo.getAuditBill();
    		if (entrys != null) {
    			for (Iterator it = entrys.iterator(); it.hasNext();) {
    				FDCSplKeepAuditEntyInfo entry = (FDCSplKeepAuditEntyInfo)it.next();
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