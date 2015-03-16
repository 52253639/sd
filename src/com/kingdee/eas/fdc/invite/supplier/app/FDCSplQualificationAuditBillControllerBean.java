package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAuditBaseBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpCollection;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpFactory;
import com.kingdee.eas.fdc.invite.supplier.GradeSetUpInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherContractEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

/**
 * 资格考察
 * 
 * @author owen_wen
 * 
 */
public class FDCSplQualificationAuditBillControllerBean extends AbstractFDCSplQualificationAuditBillControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.FDCSplQualificationAuditBillControllerBean");
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		// TODO Auto-generated method stub
		super._audit(ctx, billId);
//		/*
//         * 回写供应商状态
//         */
//    	String sql = "";
//    	
//	    	try {
//				List list = new ArrayList();
//				list.add(billId);
// /*
//				 * 评审历史加一
//				 */
//				
//				sql ="update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis+1  where FID=(select FSupplierID from T_SPL_FDCSplQualiAuditBill as se where se.FID =? )";
//				this.exequeSQl(ctx,sql,list,false,0);
//				/*
//				 * 获取评审类型
//				 */
//				StringBuffer buffer = new StringBuffer();
//				buffer.append("Select auditBillEntry.fgrade ,serviceType.fid,auditBill.FSupplierID,auditBillEntry.FSupplierTypeID,serviceType.FSeq from T_SPL_FDCSplQualAuditEntry as auditBillEntry ");
//				buffer.append(" left   JOIN   T_SPL_FDCSplQualiAuditBill as auditBill  on   auditBill.fid = auditBillEntry.FAuditBillID  ");
//				buffer.append(" left   JOIN  T_SPL_FDCSupplierServiceType as serviceType on   auditBillEntry.FSupplierTypeID=serviceType.FServiceTypeID ");
//				buffer.append(" and auditBill.FSupplierID=serviceType.FSupplierID");
//				buffer.append(" where   auditBill.FId = ? ");
//				buffer.append(" order by serviceType.FSeq desc ");
//				sql = buffer.toString();
//				List resultList = this.exequeSQl(ctx, sql, list, true, 5);
//				int sequence = 0;
//				for(int i =0;i<resultList.size();i++){
//					List valueList = (List) resultList.get(i);
//					String grade = (String)(valueList.get(0)==null?"":valueList.get(0));
//					String fid = (String)(valueList.get(1)==null?"":valueList.get(1));
//					String fSupplierID = (String)(valueList.get(2)==null?"":valueList.get(2));
//					String fSupplierTypeID = (String)(valueList.get(3)==null?"":valueList.get(3));
//					String fSeq = (String)(valueList.get(4)==null?"0":valueList.get(4));
//					int seq = Integer.parseInt(fSeq);
//					if(sequence < seq){
//						sequence = seq;
//					}
//					String updateSql = "";
//					if(!"".equals(grade) && !"".equals(fid)){
//						updateSql = "update T_SPL_FDCSupplierServiceType set FState =? where FID=?";
//						list = new ArrayList();
//						list.add(grade);
//						list.add(fid);
//						this.exequeSQl(ctx, updateSql, list, false, 0);
//					}else if(!"".equals(grade) && "".equals(fid)){
//						updateSql ="insert into T_SPL_FDCSupplierServiceType (FState,FSupplierID,FServiceTypeID,FSeq,FID) values "+
//				        "(?,?,?,?,? )";
//						fid = new ObjectUuidPK(new BOSObjectType("591DD2B6")).toString(); 
//						sequence=sequence+1;
//						list = new ArrayList();
//						list.add(grade);
//						list.add(fSupplierID);
//						list.add(fSupplierTypeID);		
//						list.add(String.valueOf(sequence));	
//						list.add(fid);
//						this.exequeSQl(ctx,updateSql,list,false,0);
//					}
//				}
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		  	
		}
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
     * @description		
     * @author				
     * @createDate		2010-11-30
     * @param	
     * @return					
     *	
     * @version			EAS1.0
     * @see						
     */
//    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
//    		EASBizException {
    	// TODO Auto-generated method stub
//    	super._unAudit(ctx, billId);
//    	List list = new ArrayList();
//  	  list.add(billId); 
//        try {
//           String sql ="update T_FDC_SupplierStock  set FappraiseHis=FappraiseHis-1  where FID=(select FSupplierID from T_SPL_FDCSplQualiAuditBill as se where se.FID =? )";
//         	this.exequeSQl(ctx,sql,list,false,0);
//		} catch (SQLException e) { 
//			e.printStackTrace();
//		}
//    }
	
    protected boolean isNeedWriteBack(Context ctx, FDCSplAuditBaseBillInfo info) throws BOSException, EASBizException {

		// 获取全部的合格供应商登记
		HashMap gradesMap = new HashMap();
		GradeSetUpCollection grades = GradeSetUpFactory.getLocalInstance(ctx).getGradeSetUpCollection(new EntityViewInfo());
		GradeSetUpInfo grade = null;
		for (Iterator it = grades.iterator(); it.hasNext();) {
			grade = (GradeSetUpInfo) it.next();
			if (grade != null) {
//				gradesMap.put(grade.getName(), grade.getIsGrade());
			}
		}
		
		// 如果该单据上有对供应商的服务进行评价，并且至少有一项服务是合格的，则需要向供应商主数据回写
		if (info instanceof FDCSplQualificationAuditBillInfo) {
			FDCSplQualificationAuditBillInfo qualificationInfo = (FDCSplQualificationAuditBillInfo) info;
			FDCSplQualificationAuditEntryCollection entrys = qualificationInfo.getAuditResult();
			if (entrys != null) {
				for (Iterator it = entrys.iterator(); it.hasNext();) {
					FDCSplQualificationAuditEntryInfo entry = (FDCSplQualificationAuditEntryInfo) it.next();
					// 直接取entry.getGrade()取不到值，是因为前面的sic没有加上要取Grade字段，不得已，再取一次。
					// added by owen_wen 2011-05-27
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add(new SelectorItemInfo("*"));
					entry = FDCSplQualificationAuditEntryFactory.getLocalInstance(ctx).getFDCSplQualificationAuditEntryInfo(
							new ObjectUuidPK(entry.getId()), sic);
					if (entry.isAvailable()) {
						IsGradeEnum isGrade = (IsGradeEnum) gradesMap.get(entry.getGrade());
						if (isGrade != null && IsGradeEnum.ELIGIBILITY.equals(isGrade)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	public IObjectPK save(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		FDCSplQualificationAuditBillInfo info=(FDCSplQualificationAuditBillInfo) model;
		updateSupplierInfo(ctx,info.getSupplier());
		return super.save(ctx, model);
	}
	public void save(Context ctx, IObjectPK pk, CoreBaseInfo model) throws BOSException, EASBizException {
		FDCSplQualificationAuditBillInfo info=(FDCSplQualificationAuditBillInfo) model;
		updateSupplierInfo(ctx,info.getSupplier());
		super.save(ctx, pk, model);
	}
	public IObjectPK submit(Context ctx, CoreBaseInfo model) throws BOSException, EASBizException {
		FDCSplQualificationAuditBillInfo info=(FDCSplQualificationAuditBillInfo) model;
		updateSupplierInfo(ctx,info.getSupplier());
		return super.submit(ctx, model);
	}
	public void submit(Context ctx, IObjectPK arg1, CoreBaseInfo model) throws BOSException, EASBizException {
		FDCSplQualificationAuditBillInfo info=(FDCSplQualificationAuditBillInfo) model;
		updateSupplierInfo(ctx,info.getSupplier());
		super.submit(ctx, arg1, model);
	}
	protected void updateSupplierInfo(Context ctx,SupplierStockInfo info) throws EASBizException, BOSException{
		if(info!=null){
			SelectorItemCollection sel=new SelectorItemCollection();
			sel.add("partProject");
//			sel.add("authorizePerson");
//			sel.add("authorizePhone");
			sel.add("authorizeJob");
//			sel.add("contractor");
//			sel.add("contractorPhone");
//			sel.add("manager");
//			sel.add("managerPhone");
			SupplierStockFactory.getLocalInstance(ctx).updatePartial(info, sel);
		}
	}
	protected boolean isUseName() {
		return false;
	}
	protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		Set entryId=new HashSet();
    	FDCSplQualificationAuditBillInfo info=(FDCSplQualificationAuditBillInfo) this.getValue(arg0, arg1);
    	for(int i=0;i<info.getIndexValue().size();i++){
    		entryId.add(info.getIndexValue().get(i).getId().toString());
    	}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("auditTemplate.id", entryId,CompareType.INCLUDE));
		if(SupplierReviewGatherEntryFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行删除操作！"));
		}
	}
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("supplierEvaluation.id", billId.toString()));
    	if(SupplierReviewGatherContractEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行反审批操作！"));
		}
    	Set entryId=new HashSet();
    	FDCSplQualificationAuditBillInfo info=(FDCSplQualificationAuditBillInfo) this.getValue(ctx, new ObjectUuidPK(billId));
    	for(int i=0;i<info.getIndexValue().size();i++){
    		entryId.add(info.getIndexValue().get(i).getId().toString());
    	}
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("auditTemplate.id", entryId,CompareType.INCLUDE));
		if(SupplierReviewGatherEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行反审批操作！"));
		}
		super._unAudit(ctx, billId);
	}
}