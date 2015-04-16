package com.kingdee.eas.fdc.invite.supplier.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.basedata.framework.DataBaseDAssignFactory;
import com.kingdee.eas.basedata.framework.DataBaseDAssignInfo;
import com.kingdee.eas.basedata.framework.DataBaseDException;
import com.kingdee.eas.basedata.framework.DataBaseDInfo;
import com.kingdee.eas.basedata.framework.IDataBaseDAssign;
import com.kingdee.eas.basedata.framework.app.DataBaseDNewImpl;
import com.kingdee.eas.basedata.master.batch.GeneralBatchLog;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupCollection;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardFactory;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupStandardInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerFactory;
import com.kingdee.eas.basedata.master.cssp.CustomerGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerInfo;
import com.kingdee.eas.basedata.master.cssp.CustomerSupplierException;
import com.kingdee.eas.basedata.master.cssp.StandardTypeEnum;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierCompanyInfoInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.SupplierGroupDetailInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.master.cssp.UsedStatusEnum;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.CUBDControlFactory;
import com.kingdee.eas.basedata.org.CUBDControlInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitCollection;
import com.kingdee.eas.basedata.org.CtrlUnitFactory;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitCollection;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.FDCMainCustomerInfo;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureCollection;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureFactory;
import com.kingdee.eas.fdc.basecrm.FDCOrgStructureInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.invite.BaseInviteInfo;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryCollection;
import com.kingdee.eas.fdc.invite.SupplierQualifyEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplQualificationAuditBillFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.invite.supplier.UpdateHistoryTypeEnum;
import com.kingdee.eas.framework.DeletedStatusEnum;
import com.kingdee.eas.framework.EffectedStatusEnum;
import com.kingdee.eas.framework.IFWEntityStruct;
import com.kingdee.eas.framework.report.util.DBUtil;
import com.kingdee.eas.framework.report.util.SqlParams;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LowTimer;
import com.kingdee.util.NumericExceptionSubItem;
import com.kingdee.util.db.SQLUtils;

public class SupplierStockControllerBean extends AbstractSupplierStockControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.SupplierStockControllerBean");
	/**
	 * @description		批量核准
	 * @author			杜余		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected java.util.Map _approve(Context ctx, IObjectPK[] pks)
			throws BOSException, EASBizException {
		HashMap map;
        SqlParams sqlParams;
        GeneralBatchLog log;
        Connection con;
        /*
         * 更新本条记录在供应商录入序时薄显示的状态SQL
         */
        String sql;
        /*
         * 更新供应商服务类型状态的SQL,与录入序时薄显示的不是同一个状态
         */
        String sql1;
        map = new HashMap();
        SupplierStockInfo supplierStockInfo = null;
        StringBuffer strSuccBuffer = new StringBuffer();
        sqlParams = new SqlParams();
        int count = sqlParams.size() + 1;
        StringBuffer paramsNum = new StringBuffer();
        boolean isSucc = false;
        log = new GeneralBatchLog(pks.length);
        CustomerSupplierException notApproveException = new CustomerSupplierException(CustomerSupplierException.CSSP_MSGAPPROVE_FAILD, new String[] {
            ""
        });
        CustomerSupplierException exception = null;
        log.setFailRecordCount(0);
        log.setSuccessRecordCount(pks.length);
        int failCount = 0;
        int successCount = pks.length;
        int i = 0;
        for(int c = pks.length; i < c; i++)
        {
        	supplierStockInfo = getSupplierStockInfo(ctx, pks[i]);
            exception = new CustomerSupplierException(CustomerSupplierException.SUPPLIER_AUDIT_FAILD, new String[] {
            		supplierStockInfo.getNumber()
            });
//            if(!supplierStockInfo.getState().equals(SupplierStateEnum.SUBMIT) || !supplierStockInfo.getCU().getId().equals(ContextUtil.getCurrentCtrlUnit(ctx).getId()))
            if(!supplierStockInfo.getState().equals(SupplierStateEnum.SUBMIT))
            {
                log.addException(exception.getMessage(), notApproveException);
                log.setFailRecordCount(++failCount);
                log.setSuccessRecordCount(--successCount);
                continue;
            }
            if(isSucc)
            {
            	strSuccBuffer.append(",");
                paramsNum.append(",");
            }
            sqlParams.setString(count++, pks[i].toString());
            paramsNum.append("?");
            strSuccBuffer.append(supplierStockInfo.getName());
            
            isSucc = true;
            
            SelectorItemCollection sel=new SelectorItemCollection();
        	sel.add("recommended");
        	SupplierQualifyEntryCollection col=SupplierQualifyEntryFactory.getLocalInstance(ctx).getSupplierQualifyEntryCollection("select * from where supplier.id='"+supplierStockInfo.getId().toString()+"' and recommended!='"+supplierStockInfo.getRecommended()+"'");
            for(int k=0;k<col.size();k++){
            	col.get(k).setRecommended(supplierStockInfo.getRecommended());
            	SupplierQualifyEntryFactory.getLocalInstance(ctx).updatePartial(col.get(k), sel);
            }
        }
        if(sqlParams.size() != 0){
        	con = null;
        	sql = "update T_FDC_SupplierStock set FState = 3 , fauditorid='"+ContextUtil.getCurrentUserInfo(ctx).getId()+"' , fauditDate= getDate() where FID in (" + paramsNum.toString() + ")";
//        	sql1= "update T_SPL_FDCSupplierServiceType set fstate = '核准' where fsupplierid in (" + paramsNum.toString() + ")";
        	try
        	{
        		con = getConnection(ctx);
        		DBUtil.executeUpdate(sql, sqlParams, con);
//        		DBUtil.executeUpdate(sql1, sqlParams, con);
        		
        		
        	}
        	catch(SQLException e)
        	{
        		e.setNextException(new SQLException("SQL: " + sql));
        		throw new SQLDataException(e);
        	}
        	SQLUtils.cleanup(con);
        }
        	if(log.getFailRecordCount() != 0)
        		map.put("status", Boolean.valueOf(true));
        	else
        		map.put("status", Boolean.valueOf(false));
        	map.put("info", log);
        	
        return map;
	}

	/**
	 * @description		批量反核准
	 * @author			杜余		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected java.util.Map _unApprove(Context ctx, IObjectPK[] pks)
			throws BOSException, EASBizException {
		HashMap map;
        SqlParams sqlParams;
        GeneralBatchLog log;
        Connection con;
        String sql;
        String sql1;
        map = new HashMap();
        SupplierStockInfo supplierStockInfo = null;
        StringBuffer strSuccBuffer = new StringBuffer();
        sqlParams = new SqlParams();
        int count = sqlParams.size() + 1;
        StringBuffer paramsNum = new StringBuffer();
        boolean isSucc = false;
        log = new GeneralBatchLog(pks.length);
        CustomerSupplierException notApproveException = new CustomerSupplierException(CustomerSupplierException.CSSP_MSGUNAPPROVE_FAILD, new String[] {
            ""
        });
        CustomerSupplierException exception = null;
        log.setFailRecordCount(0);
        log.setSuccessRecordCount(pks.length);
        int failCount = 0;
        int successCount = pks.length;
        int i = 0;
        for(int c = pks.length; i < c; i++)
        {
        	supplierStockInfo = getSupplierStockInfo(ctx, pks[i]);
            exception = new CustomerSupplierException(CustomerSupplierException.SUPPLIER_UNAUDIT_FAILD, new String[] {
            		supplierStockInfo.getNumber()
            });
//            if(!supplierStockInfo.getState().equals(SupplierStateEnum.APPROVE) || !supplierStockInfo.getCU().getId().equals(ContextUtil.getCurrentCtrlUnit(ctx).getId()))
            if(!supplierStockInfo.getState().equals(SupplierStateEnum.APPROVE))
            {
                log.addException(exception.getMessage(), notApproveException);
                log.setFailRecordCount(++failCount);
                log.setSuccessRecordCount(--successCount);
                continue;
            }
            if(isSucc)
            {
                strSuccBuffer.append(",");
                paramsNum.append(",");
            }
            sqlParams.setString(count++, pks[i].toString());
            paramsNum.append("?");
            strSuccBuffer.append(supplierStockInfo.getName());
            isSucc = true;
        }

        if(sqlParams.size() != 0){
        	con = null;
            sql = "update T_FDC_SupplierStock set FState = 2 , fauditorid=null , fauditDate=null where FID in (" + paramsNum.toString() + ")";
//            sql1= "update T_SPL_FDCSupplierServiceType set fstate = '' where fsupplierid in (" + paramsNum.toString() + ")";
            try
            {
                con = getConnection(ctx);
                DBUtil.executeUpdate(sql, sqlParams, con);
//                DBUtil.executeUpdate(sql1, sqlParams, con);
            }
            catch(SQLException e)
            {
                e.setNextException(new SQLException("SQL: " + sql));
                throw new SQLDataException(e);
            }
            SQLUtils.cleanup(con);
        }
        if(log.getFailRecordCount() != 0)
            map.put("status", Boolean.valueOf(true));
        else
            map.put("status", Boolean.valueOf(false));
        map.put("info", log);
        return map;
	}
	/**
	 * @description		保存
	 * @author			杜余		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		SupplierStockInfo info = (SupplierStockInfo)model;
		
		info.setState(SupplierStateEnum.SAVE);
		checkBill(ctx,model);
		if(info.getAuthorizePerson()==null||(info.getAuthorizePerson()!=null&&"".equals(info.getAuthorizePerson().trim()))){
			for(int i=0;i<info.getLinkPerson().size();i++){
				if(info.getLinkPerson().get(i).isIsDefault()){
					info.setAuthorizePerson(info.getLinkPerson().get(i).getPersonName());
					info.setAuthorizePhone(info.getLinkPerson().get(i).getPhone());
					info.setAuthorizeJob(info.getLinkPerson().get(i).getPosition());
				}
			}
		}
		return super._save(ctx, model);
	}
	protected void checkBill(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		SupplierStockInfo info = ((SupplierStockInfo) model);
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", info.getNumber()));
		if (info.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", info.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
		
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", info.getName()));
		if(info.getBusinessNum()!=null&&!info.getBusinessNum().trim().equals("")){
			filter.getFilterItems().add(new FilterItemInfo("businessNum", info.getBusinessNum().trim()));
		}
		if(info.getInviteType()!=null){
			filter.getFilterItems().add(new FilterItemInfo("inviteType.id", info.getInviteType().getId().toString()));
		}
		if(info.getPurchaseOrgUnit()!=null){
			filter.getFilterItems().add(new FilterItemInfo("purchaseOrgUnit.id", info.getPurchaseOrgUnit().getId().toString()));
		}
		if (info.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", info.getId().toString(),
							CompareType.NOTEQUALS));
		}
		if (_exists(ctx, filter)) {
			throw new ContractException(new NumericExceptionSubItem("100", "供应商名称+营业执照+采购类别+所属组织不能重复！"));
		}
	}
	/**
	 * @description		提交
	 * @author			杜余		
	 * @createDate		2010-11-8
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		
		SupplierStockInfo info = (SupplierStockInfo)model;
		info.setState(SupplierStateEnum.SUBMIT);
		checkBill(ctx,model);
		if(info.getAuthorizePerson()==null||(info.getAuthorizePerson()!=null&&"".equals(info.getAuthorizePerson().trim()))){
			for(int i=0;i<info.getLinkPerson().size();i++){
				if(info.getLinkPerson().get(i).isIsDefault()){
					info.setAuthorizePerson(info.getLinkPerson().get(i).getPersonName());
					info.setAuthorizePhone(info.getLinkPerson().get(i).getPhone());
					info.setAuthorizeJob(info.getLinkPerson().get(i).getPosition());
				}
			}
		}
		return super._submit(ctx, model);
	}

	/**
	 * @description		保存状态在提交
	 * @author			杜余		
	 * @createDate		2010-11-9
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void _submitTwo(Context ctx, IObjectValue supObjectValue)
			throws BOSException, EASBizException {
		SupplierStockInfo info = (SupplierStockInfo)supObjectValue;
		info.setState(SupplierStateEnum.SUBMIT);
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		String sql = "update T_FDC_SupplierStock set FState = 2 where FID = ? ";
        builder.appendSql(sql);
        if(null != info.getId().toString()){
        	builder.addParam(info.getId().toString());
        	builder.executeUpdate();
        }
	}

	/**
	 * @description		从主数据中得到供应商
	 * @author			杜余		
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IObjectCollection _getSupplierDataBase(Context ctx,
			String supLongNumber) throws BOSException, EASBizException {
		SupplierCollection sc = new SupplierCollection();
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo info = new FilterInfo();
	    //增加管理单元过滤  by zhiyuan_tang 2011-02-24 (R110223-225:供应商从主数据引入没有按照管理单元隔离)
	    info.getFilterItems().add(new FilterItemInfo("CU.id", ContextUtil.getCurrentCtrlUnit(ctx).getId()));
	    String assignidsql = "SELECT FDataBaseDID FROM T_BD_DataBaseDAssign WHERE FBOSObjectType = '" + SupplierInfo.getBosType() + 
	    						"' AND FAssignCUID = '" + ContextUtil.getCurrentCtrlUnit(ctx).getId() + "'";
	    info.getFilterItems().add(new FilterItemInfo("id", assignidsql, CompareType.INNER));
	    if(!"".equals(supLongNumber)){
	    	info.getFilterItems().add(new FilterItemInfo("browseGroup.longNumber",supLongNumber+"!%",CompareType.LIKE));
	    	info.getFilterItems().add(new FilterItemInfo("browseGroup.longNumber",supLongNumber));
	    	info.setMaskString("( #0 or #1 ) and (#2 or #3)");
	    } else {
	    	info.setMaskString(" #0 or #1 ");
	    }
	    view.setFilter(info);
	    sc = SupplierFactory.getLocalInstance(ctx).getSupplierCollection(view);
		return sc;
	}
	/**
	 * @description		从主数据导入,写入表方法
	 * @author			杜余		
	 * @createDate		2010-11-10
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected Map _importDataBase(Context ctx, IObjectCollection objCollection,
			String typeLongNumber) throws BOSException, EASBizException {
		HashMap map = new HashMap();
		Connection connection = null;
		/*
		 * 相同的长编码下主数据中的供应商
		 */
		SupplierCollection ss = (SupplierCollection) objCollection;
		List sqlParams = new ArrayList();
		/*
		 * 取出相同的长编码下本来已经有的供应商,判断重复的供应商,避免重复导入 
		 */
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo info = new FilterInfo();
	    //增加管理单元过滤  by zhiyuan_tang 2011-02-24 (R110223-225:供应商从主数据引入没有按照管理单元隔离)
	    info.getFilterItems().add(new FilterItemInfo("CU.id", ContextUtil.getCurrentCtrlUnit(ctx).getId()));
	    String assignidsql = "SELECT FDataBaseDID FROM T_BD_DataBaseDAssign WHERE FBOSObjectType = '" + SupplierInfo.getBosType() + 
								"' AND FAssignCUID = '" + ContextUtil.getCurrentCtrlUnit(ctx).getId() + "'";
	    info.getFilterItems().add(new FilterItemInfo("id", assignidsql, CompareType.INNER));
	    if(!"".equals(typeLongNumber)){
	    	info.getFilterItems().add(new FilterItemInfo("supplierType.longNumber",typeLongNumber+"!%",CompareType.LIKE));
	    	info.getFilterItems().add(new FilterItemInfo("supplierType.longNumber",typeLongNumber));
	    	info.setMaskString("( #0 or #1 ) and (#2 or #3)");
	    } else {
	    	info.setMaskString(" #0 or #1 ");
	    }
	    view.setFilter(info);
		SupplierStockCollection ssc = SupplierStockFactory.getLocalInstance(ctx).getSupplierStockCollection(view);
		/*
		 * 根据编码判断是否为相同的供应商
		 */
		for(int i = 0 ; i < ss.size() ;i++){
			for(int j = 0 ; j<ssc.size() ; j++){
				if(ss.get(i).getNumber().equals(ssc.get(j).getNumber())){
					ss.removeObject(i);
					i--;
					break;
				}
			}
		}
		Integer in = new Integer(1);
		if(ss.size()!=0){
			for(int i = 0 ; i < ss.size() ;i++){
				sqlParams.add(Arrays.asList(new Object[]{ss.get(i).getBrowseGroup().getId().toString()
						,ss.get(i).getArtificialPerson()
						,ss.get(i).getBusiLicence()
						,null != ss.get(i).getCity()?ss.get(i).getCity().getId().toString():null
						,null != ss.get(i).getProvince()?ss.get(i).getProvince().getId().toString():null
						,null != ss.get(i).getAddress() ? ss.get(i).getAddress():""
						,ss.get(i).getName()
						,ss.get(i).getNumber()
						,new ObjectUuidPK(new BOSObjectType("3B04106C")).toString()
						,ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()
						,ContextUtil.getCurrentUserInfo(ctx).getId().toString()
						,FDCDateHelper.getServerTimeStamp()
						, in, new Integer(1) 
						,ContextUtil.getCurrentCtrlUnit(ctx).getId().toString()})); //add by duyu at 2011-8-24 增加当前管理单元CU(adminCU)批量分配用
				
				// 从主数据导入
																																			// ，
																																			// 将此字段标识置为1
																																			// ，
																																			// 表示不需要反写主数据了
																																			// 。
																																			// By
																																			// Owen_wen
																																			// 2011
																																			// -
																																			// 05
																																			// -
																																			// 27
			}
		}
	
		int successCount = ss.size(); 
		int failCount = 0;
		GeneralBatchLog log = new GeneralBatchLog(ss.size());
		if(ss.size()>0){
			String sql = "insert into T_FDC_supplierstock (fsuppliertypeid,fenterpriseMaster,fbusinessNum,fcityid,fprovinceid,faddress,fname_l2,fnumber,fid,fcontrolunitid,fcreatorid,fcreatetime,fstate, FHasCreatedSupplierBill ,fadmincuid) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			try{
				connection = getConnection(ctx);
				FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
				builder.executeBatch(sql, sqlParams);
			}catch (Exception e) {
				log.addException(e.getMessage(), new Throwable("SQL : "+ sql));
                log.setFailRecordCount(++failCount);
			}
			log.setSuccessRecordCount(successCount - failCount);
		}
		SQLUtils.cleanup(connection);
		if(log.getFailRecordCount() != 0){
			map.put("status", Boolean.valueOf(true));
		}
    	else{
    		map.put("status", Boolean.valueOf(false));
    	}
    	map.put("info", log);
		return map;
	}

	/**
	 * @description		得到区域设置中启用的东西
	 * @author			杜余		
	 * @createDate		2010-11-13
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected IObjectCollection _getSericeArea(Context ctx)
			throws BOSException, EASBizException {
		FDCSplAreaCollection ac = new FDCSplAreaCollection();
		EntityViewInfo view = new EntityViewInfo();
	    FilterInfo info = new FilterInfo();
	    info.getFilterItems().add(new FilterItemInfo("isEnable","1"));
	    view.setFilter(info);
	    ac = FDCSplAreaFactory.getLocalInstance(ctx).getFDCSplAreaCollection(view);
		return ac;
	}
	
	/**
	 * @description		材料信息查询
	 * @author			周航建	
	 * @createDate		2010-11-14
	 * @param			list 供应商
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected Set _getDataBase(Context ctx, List list) throws BOSException,EASBizException {

		StringBuffer sql = new StringBuffer();
		sql.append("select fid,materialID,price,quoteTime,projectName,contractName,supplier,id,number,model,baseUnit,stuffName,fnumber from ");
		sql.append("( ");
		sql.append("select fid,materialID,price,quoteTime,projectName,contractName,supplier,id,number,model,baseUnit,stuffName,fnumber from ");
		sql.append("( ");
		sql.append("SELECT mi.Fid fid,mi.FMaterialID materialID,mi.FPrice price, mi.FQuoteTime quoteTime, p.FName_l2 projectName, c.FName contractName,s.FName_l2 supplier,s.fnumber fnumber ");
		sql.append("FROM T_MTR_MaterialInfo AS mi ");
		sql.append("LEFT OUTER JOIN T_CON_ContractBill AS c ON mi.FContractBillID = c.FID ");
		sql.append("LEFT OUTER JOIN T_FDC_CurProject AS p ON mi.FProjectID = p.FID ");
		sql.append("LEFT OUTER JOIN T_BD_Material AS m ON mi.FMaterialID = m.FID ");
		sql.append("LEFT OUTER JOIN T_BD_Supplier AS s ON mi.FSupplierID = s.FID ");
		sql.append(") ");
		sql.append("as t_tempa , ");
		sql.append("( ");
		sql.append("select s.FID id, s.FNumber number,s.FModel model,u.FName_l2 baseUnit,s.FName_L2 stuffName from T_BD_Material as s ");
		sql.append("LEFT OUTER JOIN T_BD_MeasureUnit as u on s.FBaseUnit=u.FID ");
		sql.append(") ");
		sql.append("as t_tempb ");
		sql.append("where t_tempa.materialID=t_tempb.id  ");
		sql.append(") ");
		sql.append("as t_tempc ");
		sql.append("where t_tempc.fnumber=?;");
		
		FDCSQLBuilder build=new FDCSQLBuilder(ctx);
		build.appendSql(sql.toString());
		if(null != list && list.size() > 0){
			build.appendParam(list.get(0).toString().trim());
		}
		
		Set set=new HashSet();
		
		
		try {
			String testSql = build.getTestSql();
			testSql = testSql.substring(0,testSql.indexOf(";"));
			build.setPrepareStatementSql(testSql);
			IRowSet rowSet=build.executeQuery();
			while(rowSet.next())
			{
				Map map=new HashMap();
				map.put("materinfoId", rowSet.getString("fid"));
				map.put("MaterialNumber", rowSet.getString("number"));
				map.put("MaterialName", rowSet.getString("stuffName"));
				map.put("Model", rowSet.getString("model"));
				map.put("Unit", rowSet.getString("baseUnit"));
				map.put("Prices", rowSet.getString("price"));
				map.put("Time", rowSet.getString("quoteTime"));
				map.put("ProjetName", rowSet.getString("projectName"));
				map.put("DealName", rowSet.getString("contractName"));
				set.add(map);
				  
			}
		} catch (Exception e) {
			throw new BOSException(e);
		}
		return set;
	}

	/**
	 * 
	* @description		评审历史查询
	* @author				田宝平
	* @createDate		2010-11-14
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.fdc.supply.app.AbstractSupplierStockControllerBean#_getAHDBValue(com.kingdee.bos.Context, java.util.List)
	 */
	protected Set _getAHDBValue(Context ctx, List list) throws BOSException {
		
		/*
		 * 资格考察
		 */
		StringBuffer sql1 = new StringBuffer("SELECT b.FId cId,'资格考察' cType,b.FBusinessDate businessDate,a.FGrade FGrade,a.FID aFID,f.fname_l2 FName,a.FScore FScore ");
    	sql1.append("FROM T_SPL_FDCSplQualAuditEntry a LEFT OUTER JOIN T_SPL_FDCSplQualiAuditBill b ON a.FAuditBillID = b.FID LEFT OUTER JOIN T_FDC_SupplierStock d ");
    	sql1.append("ON b.FSupplierID = d.FID LEFT OUTER JOIN T_SPL_ServiceType f ON a.FSupplierTypeID = f.FID ");
    	sql1.append("where b.FState = '4AUDITTED' and d.FNumber =?;");
    	    	
    	/*
    	 * 履约评估
    	 */
    	StringBuffer sql2 = new StringBuffer("SELECT b.FId cId,'履约评估' cType,b.FBusinessDate businessDate,a.FGrade FGrade,a.FID aFID,f.fname_l2 FName,a.FScore FScore ");
    	sql2.append("FROM T_SPL_FDCSplKeepAuditEnty a LEFT OUTER JOIN T_SPL_FDCSplKeepContractA b ON a.FAuditBillID = b.FID LEFT OUTER JOIN T_FDC_SupplierStock d ");
    	sql2.append("ON b.FSupplierID = d.FID LEFT OUTER JOIN T_SPL_ServiceType f ON a.FSupplierTypeID = f.FID ");
    	sql2.append("where b.FState = '4AUDITTED' and d.FNumber =?;");
    	
    	/*
    	 * 定期评审
    	 */
    	StringBuffer sql3 = new StringBuffer("SELECT b.FId cId,'定期评审' cType,b.FBusinessDate businessDate,a.FGrade FGrade,a.FID aFID,f.fname_l2 FName,a.FScore FScore ");
    	sql3.append("FROM T_SPL_FDCSplPeriAuditBillEntry a LEFT OUTER JOIN T_SPL_FDCSplPeriodAuditBill b ON a.FAuditBillID = b.FID LEFT OUTER JOIN T_FDC_SupplierStock d ");
    	sql3.append("ON b.FSupplierID = d.FID LEFT OUTER JOIN T_SPL_ServiceType f ON a.FSupplierTypeID = f.FID ");
    	sql3.append("where b.FState = '4AUDITTED' and d.FNumber =?;");
    	
    	FDCSQLBuilder builder1 = new FDCSQLBuilder(ctx);
    	builder1.appendSql(sql1.toString());
    	
    	FDCSQLBuilder builder2 = new FDCSQLBuilder(ctx);
    	builder2.appendSql(sql2.toString());
    	
    	FDCSQLBuilder builder3 = new FDCSQLBuilder(ctx);
    	builder3.appendSql(sql3.toString());
    	
    	/*
    	 * 更新评审历史
    	 */
    	StringBuffer sql4 = new StringBuffer("update T_FDC_SupplierStock set FAppraiseHis=? where FID=?;");
    	FDCSQLBuilder builder4 = new FDCSQLBuilder(ctx);
    	builder4.appendSql(sql4.toString());
    	
    	/*
    	 * 判断参数是否为空
    	 */
    	if(null != list && list.size() > 0){
    		builder1.addParam(list.get(0).toString().trim());	
        	builder2.addParam(list.get(0).toString().trim());
        	builder3.addParam(list.get(0).toString().trim());
    	}
    	
    	Set set = new HashSet();
    	/*
    	 * 定义总记录条数
    	 */
    	int sum = 0;
    	try {
        	/*
        	 * 资格考察
        	 */
    		String testSql1 = builder1.getTestSql();
    		testSql1 = testSql1.substring(0,testSql1.indexOf(";"));
    		builder1.setPrepareStatementSql(testSql1);
			IRowSet iRowSet1 = builder1.executeQuery();
			while(iRowSet1.next()){
				sum++;
				Map map = new HashMap();
				map.put("aFID",iRowSet1.getString("aFID"));
				map.put("id",iRowSet1.getString("cId"));
				map.put("cType", iRowSet1.getString("cType"));
				map.put("businessDate", iRowSet1.getString("businessDate"));
				map.put("FScore", iRowSet1.getString("FScore"));
				map.put("FName", iRowSet1.getString("FName"));
				map.put("FState", iRowSet1.getString("FGrade"));
				set.add(map);
			}
	    	/*
	    	 * 履约评估
	    	 */
    		String testSql2 = builder2.getTestSql();
    		testSql2 = testSql2.substring(0,testSql2.indexOf(";"));
    		builder2.setPrepareStatementSql(testSql2);
			IRowSet iRowSet2 = builder2.executeQuery();
			while(iRowSet2.next()){
				sum++;
				Map map = new HashMap();
				map.put("aFID",iRowSet2.getString("aFID"));
				map.put("id",iRowSet2.getString("cId"));
				map.put("cType", iRowSet2.getString("cType"));
				map.put("businessDate", iRowSet2.getString("businessDate"));
				map.put("FScore", iRowSet2.getString("FScore"));
				map.put("FName", iRowSet2.getString("FName"));
				map.put("FState", iRowSet2.getString("FGrade"));
				set.add(map);
			}
	    	/*
	    	 * 定期评审
	    	 */
    		String testSql3 = builder3.getTestSql();
    		testSql3 = testSql3.substring(0,testSql3.indexOf(";"));
    		builder3.setPrepareStatementSql(testSql3);
			IRowSet iRowSet3 = builder3.executeQuery();
			while(iRowSet3.next()){
				sum++;
				Map map = new HashMap();
				map.put("aFID",iRowSet3.getString("aFID"));
				map.put("id",iRowSet3.getString("cId"));
				map.put("cType", iRowSet3.getString("cType"));
				map.put("businessDate", iRowSet3.getString("businessDate"));
				map.put("FScore", iRowSet3.getString("FScore"));
				map.put("FName", iRowSet3.getString("FName"));
				map.put("FState", iRowSet3.getString("FGrade"));
				set.add(map);
			}
	    	/*
	    	 * 更新评审历史的记录数
	    	 */
			builder4.addParam(Integer.toString(sum));
        	builder4.addParam(list.get(1).toString().trim());
        	String testSql4 = builder4.getTestSql();
    		testSql4 = testSql4.substring(0,testSql4.indexOf(";"));
    		builder4.setPrepareStatementSql(testSql4);
        	builder4.executeUpdate();
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return set;
	}

	/**
	 * @description 在信息变更中可以重复名称的方式增加新供应商
	 *              <p>
	 *              前台EditUI中已经添加了检查是否重名方法，因此这里不需要实现。By Owen_wen 2011-02-23
	 * @author 陈伟
	 * @createDate 2010-11-18
	 * @param
	 * @return
	 * 
	 * @version EAS7.0
	 * @see boolean
	 *      com.kingdee.eas.fdc.invite.supplier.client.SupplierStockEditUI
	 *      .isExist(String FiledName, String filedValue)
	 */
    protected void _checkNameDup(Context ctx, IObjectValue model) throws BOSException, EASBizException
    {
		// SupplierStockInfo dataBaseInfo = (SupplierStockInfo) model;
		// if (dataBaseInfo.getSrcSupplier().getId() == dataBaseInfo.getId()) {
		// super._checkNameDup(ctx, model);
		// }
	}
    
    
	/**
	 * @description		在信息变更中可以重复编码的方式增加新供应商
	 * @author			陈伟		
	 * @createDate		2010-11-18
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void _checkNumberDup(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
//		SupplierStockInfo dataBaseInfo = (SupplierStockInfo) model;
//		FilterInfo filter = new FilterInfo();
//		FilterItemInfo filterItem = new FilterItemInfo(IFWEntityStruct.dataBase_Number, dataBaseInfo.getNumber(), CompareType.EQUALS);
//		filter.getFilterItems().add(filterItem);
//		if (dataBaseInfo.getId() != null) {
//			filterItem = new FilterItemInfo(IFWEntityStruct.coreBase_ID, dataBaseInfo.getId(), CompareType.NOTEQUALS);
//			filter.getFilterItems().add(filterItem);
//			// filter.setMaskString("#1 and #2");
//		}
//		EntityViewInfo view = new EntityViewInfo();
//		view.setFilter(filter);
//		SorterItemCollection sorter = new SorterItemCollection();
//		sorter.add(new SorterItemInfo(IFWEntityStruct.coreBase_ID));
//		/*
//		 * 新建时判断是否重复
//		 */
//		if ( dataBaseInfo.getSrcSupplier().getId()==dataBaseInfo.getId()) {
//		  if (  super._exists(ctx, filter)) {
//		    	String number = this._getPropertyAlias(ctx, dataBaseInfo, IFWEntityStruct.dataBase_Number) + dataBaseInfo.getNumber();
//			    throw new EASBizException(EASBizException.CHECKDUPLICATED, new Object[] { number });
//		  }
//		}
	}
	/**
	 * @description		入围历史查询
	 * @author			田宝平		
	 * @createDate		2010-11-17
	 * @param	
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	
	protected Set _getEHDBValue(Context ctx, List list) throws BOSException {
		
    	StringBuffer sql = new StringBuffer("SELECT d.FParentID from T_INV_InvEntSuppChkBillEntry d LEFT OUTER JOIN T_INV_InviteEntSuppChkBill c ON d.FParentID = c.FID LEFT OUTER JOIN T_BD_Supplier f  ON d.FSupplierId = f.FID WHERE c.FState='4AUDITTED' and f.FNumber =?;");
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sql.toString());
    	
    	/*
    	 * 判断参数是否为空
    	 */
    	if(null != list && list.size() > 0){
    		builder.addParam(list.get(0).toString().trim());	
    	}
    	
    	Set set = new HashSet();
    	/*
    	 * 定义总记录条数
    	 */
    	int sum = 0;
    	try {
    		/*
    		 * 入围历史
    		 */
    		String testSql = builder.getTestSql();
    		testSql = testSql.substring(0,testSql.indexOf(";"));
    		builder.setPrepareStatementSql(testSql);
			IRowSet iRowSet = builder.executeQuery();
			while(iRowSet.next()){
				set.add(iRowSet.getString("FParentID"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return set;
	}
	
	/**
	 * 
	* @description		更新签约历史的记录数
	* @author			田宝平	
	* @createDate		2010-11-19
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.fdc.invite.supplier.app.AbstractSupplierStockControllerBean#_getSHDBValue(com.kingdee.bos.Context, java.util.List)
	 */
	protected Set _getSHDBValue(Context ctx, List list) throws BOSException,
			EASBizException {
		
		/*
		 * 更新签约历史
		 */
    	StringBuffer sql4 = new StringBuffer("update T_FDC_SupplierStock set FConstractHis=? where FID=?;");
    	FDCSQLBuilder builder4 = new FDCSQLBuilder(ctx);
    	builder4.appendSql(sql4.toString());
    	
    	/*
    	 * 判断参数是否为空
    	 */
    	if(null != list && list.size() > 0){
    		builder4.addParam(list.get(0).toString().trim());	
    		builder4.addParam(list.get(1).toString().trim());	
    	}
    	
    	Set set = new HashSet();
    	
	    /*
	     *  更新签约历史的记录数
	     */
		String testSql4 = builder4.getTestSql();
		testSql4 = testSql4.substring(0, testSql4.indexOf(";"));
		builder4.setPrepareStatementSql(testSql4);
		builder4.executeUpdate();
		
		return set;
	}
	/**
	 * 
	* @description		评审指标是否被引用的查询
	* @author			田宝平	
	* @createDate		2010-11-23
	*	
	* @version			EAS7.0
	* @see com.kingdee.eas.fdc.invite.supplier.app.AbstractSupplierStockControllerBean#_getATRFValue(com.kingdee.bos.Context, java.util.List)
	 */
	protected boolean _getATRFValue(Context ctx, List list)
			throws BOSException, EASBizException {
		StringBuffer sql = new StringBuffer("select * from T_FDC_SupplierGuideEntry g ");
    	sql.append("where g.FSplAuditIndexID =?;");
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sql.toString());
    	
    	/*
    	 * 判断参数是否为空
    	 */
    	if(null != list && list.size() > 0){
    		builder.addParam(list.get(0).toString().trim());
    	}
    	
    	/*
    	 * 定义评审指标是否被引用的标记
    	 */
    	boolean referenceFlag =  false;
    	try {
    		String testSql = builder.getTestSql();
    		testSql = testSql.substring(0,testSql.indexOf(";"));
    		builder.setPrepareStatementSql(testSql);
			IRowSet iRowSet = builder.executeQuery();
			while(iRowSet.next()){
				referenceFlag = true;
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		}
		return referenceFlag;
	}

	/**
	 * 
	 * @description		批量更新供应商历史类型的次数(其它模块引用回写接口方法)
	 * @author			杜余		
	 * @createDate		2010-11-28
	 * @param	historyType	历史类型枚举 枚举中对应的值是本表的字段名
	 * @param	pks	供应商PK[]
	 * @param	isAudit	true为增1,false为减1
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see
	 */
	protected void _beachUpdateHisCount(Context ctx,
			UpdateHistoryTypeEnum historyType, IObjectPK[] pks, boolean isAudit)
			throws BOSException {
		SqlParams sqlParams = new SqlParams();
        int count = sqlParams.size() + 1;
        Connection con = null ;
        for(int i = 0 ; i<pks.length ; i++){
        	sqlParams.setString(count++, pks[i].toString());
		}
		String sql = "";
		if(isAudit){
			sql="update t_fdc_supplierstock set " + historyType.getValue().toString() + " = " + historyType.getValue().toString() + " + 1 where fid = ? ";
		}else{
			sql="update t_fdc_supplierstock set " + historyType.getValue().toString() + " = " + historyType.getValue().toString() + " - 1 where fid = ? and " + historyType.getValue().toString() + " > 0 ";
		}
		if(sqlParams.size()>0){
            try {
            	con = getConnection(ctx);
				DBUtil.executeUpdate(sql, sqlParams, con);
			} catch (SQLException e) {
				logger.error(e.getMessage());
				throw new BOSException(e);
			}
			
		}
	}

	/**
	 * @description		更新供应商历史类型的次数(其它模块引用回写接口方法)
	 * @author			杜余		
	 * @createDate		2010-11-28
	 * @param	historyType 历史类型枚举
	 * @param	pk	供应商PK
	 * @param	isAudit	true为增1,false为减1
	 * @return					
	 *	
	 * @version			EAS7.0
	 * @see						
	 */
	protected void _updateHisCount(Context ctx,
			UpdateHistoryTypeEnum historyType, IObjectPK pk, boolean isAudit)
			throws BOSException {
		String sql = "";
		if(isAudit){
			sql="update t_fdc_supplierstock set " + historyType.getValue().toString() + " = " + historyType.getValue().toString()  + "+1 where fid =? ";
		}else{
			sql="update t_fdc_supplierstock set " + historyType.getValue().toString()  + " = " + historyType.getValue() + "-1 where fid = ? and " + historyType.getValue() + " > 0 ;";
		}
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql(sql.toString());
    	builder.addParam(pk.getKeyValue("id").toString());
    	builder.executeUpdate();
    	builder.getTestSql();
	}

	protected void _batchAssign2(Context ctx, String cuid, String[] diddata, String[] cuiddata) throws BOSException, EASBizException {
		
		String data[][] = cuAndDids(cuiddata, diddata);
//		if(useOptimizedBatchAssginAndUnassign()){
//			batchAssign(getBOSType().toString(), getConnection(ctx), data);
//			return;
//		}
		int i = 0;
		for(int n = data.length; i < n; i++){
			assign(ctx, new ObjectUuidPK(cuid), new ObjectUuidPK(data[i][1]), new ObjectUuidPK(data[i][0]));
		}
	}
	private String[][] cuAndDids(String cuiddata[], String diddata[])
    {
		int size = diddata.length;
		int cuSize = cuiddata.length;
		String data[][] = new String[size * cuSize][2];
		for(int i = 0; i < cuSize; i++){
			for(int j = 0; j < size; j++){
				data[i * size + j] = parsePairOfCUIDAndDataID(cuiddata[i].toString(), diddata[j].toString());
			}
		}
		return data;
    }
	private String[] parsePairOfCUIDAndDataID(String cuid, String dataid)
    {
		String aPair[] = new String[2];
		aPair[0] = cuid;
		aPair[1] = dataid;
		return aPair;
    }
	 public void batchAssign(String bosType, Connection cn, String data[][]) throws BOSException, EASBizException
	 {
	     LowTimer allTimer;
	     String sqlTemplate;
	     Statement stmt;
	     allTimer = new LowTimer();
	     sqlTemplate = getSQLTemplateForAssigning(bosType);
	     logger.debug("start executing batchassign time " + allTimer.msValue());
	     stmt = null;
	     try{
				stmt = cn.createStatement();
				String deleteSql = getSQLDeleteAssign(data);
				if(deleteSql != null)
				stmt.addBatch(deleteSql);
				int i = 0;
				for(int n = data.length; i < n; i++){
					stmt.addBatch(MessageFormat.format(sqlTemplate, data[i]));
					stmt.executeBatch();
				}
				logger.debug("execute batchassign cost time  " + allTimer.msValue());
	     	}catch(SQLException e){
	     		logger.error("sql next exception:" + e.getNextException());
	     		throw new BOSException(e);
			}
			SQLUtils.cleanup(stmt);
			SQLUtils.cleanup(cn);
	 }
	 private String getSQLTemplateForAssigning(String bosType)
     {
		 StringBuffer tempSql = new StringBuffer(500);
		 tempSql.append("insert into T_BD_DataBaseDAssign (FID ,FCreatorID,FCreateTime ,FLastUpdateUserID,FLastUpdateTime ,FControlUnitID ,FDataBaseDID ,FAssignCUID ,FBOSObjectType ,FStatus) select * from (").append("select newbosid(''").append(bosType).append("'') as \"F1\",").append("''00000000-0000-0000-0000-00000000000013B7DE7F'' as \"F2\",getdate() as \"F3\",").append("''00000000-0000-0000-0000-00000000000013B7DE7F'' as \"F4\",getdate() as \"F5\",").append("''00000000-0000-0000-0000-000000000000CCE7AED4'' as \"F6\",").append("''{1}'' as \"DATAID\",''{0}'' as \"CUID\",''").append(bosType).append("'' as \"F9\",0 as  \"F10\") as \"A\" ");
		 return tempSql.toString();
     }
	 private String getSQLDeleteAssign(String data[][])
     {
			StringBuffer sql = new StringBuffer(" delete from T_BD_DataBaseDAssign where FDataBaseDID in ");
			StringBuffer cuids = new StringBuffer("(");
			StringBuffer dids = new StringBuffer("(");
			if(data.length == 0)
			return null;
			int i = 0;
			for(int n = data.length; i < n; i++)
			         {
			String tmp[] = data[i];
			if(i == data.length - 1)
			             {
			cuids.append("'").append(tmp[0]).append("')");
			dids.append("'").append(tmp[1]).append("')");
			             } else
			             {
			cuids.append("'").append(tmp[0]).append("',");
			dids.append("'").append(tmp[1]).append("',");
			             }
			         }
			sql.append(dids).append(" and FAssignCUID in ").append(cuids);
			return sql.toString();
     }
	 
	 protected void assign(Context ctx, IObjectPK ctrlUnitPK, IObjectPK dataBaseDPK, IObjectPK assignCUPK)throws BOSException, EASBizException
	 {
			IDataBaseDAssign iDataBaseDAssign = DataBaseDAssignFactory.getLocalInstance(ctx);
			if(iDataBaseDAssign.checkAssignment(assignCUPK, dataBaseDPK)){
				return;
			}
			String bosType = null;
			boolean isSubordinateAssign = false;
			bosType = BOSUuid.read(dataBaseDPK.toString()).getType().toString();
			String currentCU = ContextUtil.getCurrentCtrlUnit(ctx).getId().toString();
	    	 	DataBaseDAssignInfo dataBaseDAssignInfo = new DataBaseDAssignInfo();
	    	 	dataBaseDAssignInfo.setDataBaseDID(BOSUuid.read(dataBaseDPK.toString()));
	
	    	 	CtrlUnitInfo assignCU = new CtrlUnitInfo();
	    	 	assignCU.setId(BOSUuid.read(assignCUPK.toString()));
	    	 	dataBaseDAssignInfo.setAssignCU(assignCU);
	    	 	dataBaseDAssignInfo.setBosObjectType(((ObjectUuidPK)dataBaseDPK).getObjectType().toString());
	    	 	iDataBaseDAssign.addnew(dataBaseDAssignInfo);
	    	 	return;
//	     }
	 }
	protected void _batchAssign2(Context ctx, String cuid, String sql, HashSet basedata, HashSet cuData) throws BOSException, EASBizException {
	}
	
    protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("supplier.id", arg1.toString()));
		if(FDCSplQualificationAuditBillFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审引用，不能进行删除操作！"));
		}
		if(SupplierReviewGatherFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被供应商评审汇总引用，不能进行删除操作！"));
		}
	}

	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		SupplierStockInfo info=this.getSupplierStockInfo(ctx, pk);
		for(int i=0;i<info.getSupplierAttachListEntry().size();i++){
			deleteAttachment(ctx,info.getSupplierAttachListEntry().get(i).getId().toString());
		}
		super._delete(ctx, pk);
	}
	protected void deleteAttachment(Context ctx,String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getLocalInstance(ctx).getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getLocalInstance(ctx).delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getLocalInstance(ctx).delete(filter);
				}
			}
		}
	}
	protected void _addToSysSupplier(Context ctx, IObjectValue objectValue) throws BOSException, EASBizException {
		SupplierStockInfo info = (SupplierStockInfo)objectValue;
		if(info==null){
			return;
		}else{
			SelectorItemCollection supplier = new SelectorItemCollection();
			supplier.add("*");
			supplier.add("sysSupplier.*");
			supplier.add("purchaseOrgUnit.*");
			info=SupplierStockFactory.getLocalInstance(ctx).getSupplierStockInfo(new ObjectUuidPK(info.getId()),supplier);
		}
		SupplierInfo supplier = info.getSysSupplier();
    	
    	if(supplier == null||supplier.getId()==null||supplier.getAdminCU()==null){
    		EntityViewInfo view = new EntityViewInfo();
    		FilterInfo filter = new FilterInfo();
    		view.setFilter(filter);
    		filter.getFilterItems().add(new FilterItemInfo("name", info.getName()));
    		
    		SupplierCollection col=SupplierFactory.getLocalInstance(ctx).getSupplierCollection(view);
    		if(col.size()>0){
    			supplier=col.get(0);
    			if(supplier.getNumber().indexOf("FDC-")<0){
    				Set cuIds = getSupplierMgeCu(ctx,supplier.getAdminCU().getId().toString());
        	    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
        	    		String cuId = (String) itor.next();
        	    		SupplierFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(supplier.getAdminCU().getId()), new ObjectUuidPK(supplier.getId()), new ObjectUuidPK(cuId));
        	    	}
    			}
    		}else{
    			supplier = new SupplierInfo();
        		supplier.setId(BOSUuid.create(supplier.getBosType()));
        		setSysSupplierValue(ctx, info, supplier);
    			
        		CtrlUnitInfo cu = new CtrlUnitInfo();
        		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
        		
        		CSSPGroupInfo groupInfo = null;
        		
        		view = new EntityViewInfo();
        		filter = new FilterInfo();
        		view.setFilter(filter);
        		filter.getFilterItems().add(new FilterItemInfo("name", "房地产供应商"));
        		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
        		
        		CSSPGroupCollection sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
        		if(sheGroupCol.isEmpty()){
        			CSSPGroupStandardInfo strd = new CSSPGroupStandardInfo();
        			strd.setId(BOSUuid.create(strd.getBOSType()));
        			strd.setNumber("fdcsupplierGstrd");
        			strd.setName("房地产供应商分类标准");
        			strd.setType(2);
        			strd.setIsBasic(StandardTypeEnum.defaultStandard);
        			strd.setCU(cu);
        			
        			CSSPGroupStandardFactory.getLocalInstance(ctx).addnew(strd);
        			
        			CSSPGroupInfo gr = new CSSPGroupInfo();
        			gr.setDeletedStatus(DeletedStatusEnum.NORMAL);
        			gr.setId(BOSUuid.create(gr.getBOSType()));
        			gr.setNumber("fdcsupplierG");
        			gr.setName("房地产供应商");
        			gr.setCU(cu);
        			gr.setGroupStandard(strd);
        			
        			CSSPGroupFactory.getLocalInstance(ctx).addnew(gr);
        			
        			groupInfo = gr;
        		}else{
        			groupInfo = sheGroupCol.get(0);
        		}
        		supplier.setBrowseGroup(groupInfo);
        		
        		SupplierGroupDetailInfo Gdinfo = new SupplierGroupDetailInfo();
        		Gdinfo.setSupplierGroup(groupInfo);
        		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
        		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
        		supplier.getSupplierGroupDetails().add(Gdinfo);
        		
        		view = new EntityViewInfo();
        		filter = new FilterInfo();
        		view.setFilter(filter);
        		filter.getFilterItems().add(new FilterItemInfo("name", "工程建设",CompareType.LIKE));
        		filter.getFilterItems().add(new FilterItemInfo("CU.id", OrgConstants.DEF_CU_ID));
        		
        		sheGroupCol = CSSPGroupFactory.getLocalInstance(ctx).getCSSPGroupCollection(view);
        		if(sheGroupCol.size()>0){
        			groupInfo = sheGroupCol.get(0);
        			
        			Gdinfo = new SupplierGroupDetailInfo();
            		Gdinfo.setSupplierGroup(groupInfo);
            		Gdinfo.setSupplierGroupFullName(groupInfo.getName());
            		Gdinfo.setSupplierGroupStandard(groupInfo.getGroupStandard());
            		supplier.getSupplierGroupDetails().add(Gdinfo);
        		}
    			SupplierFactory.getLocalInstance(ctx).addnew(supplier);
    			
    			SupplierCompanyInfoInfo com = new SupplierCompanyInfoInfo();
				CompanyOrgUnitInfo company=new CompanyOrgUnitInfo();
				company.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
				com.setCompanyOrgUnit(company);
				com.setSupplier(supplier);
				com.setCU(cu);
				SupplierCompanyInfoFactory.getLocalInstance(ctx).addnew(com);
				
    			Set cuIds = getSupplierMgeCu(ctx,supplier.getAdminCU().getId().toString());
    	    	for(Iterator itor = cuIds.iterator(); itor.hasNext(); ){
    	    		String cuId = (String) itor.next();
    	    		SupplierFactory.getLocalInstance(ctx).assign(new ObjectUuidPK(supplier.getAdminCU().getId()), new ObjectUuidPK(supplier.getId()), new ObjectUuidPK(cuId));
    	    	}
    		}
	    	info.setSysSupplier(supplier);
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("sysSupplier");
			this.updatePartial(ctx, info, sels);
    	}else{
    		updateSysSupplier(ctx, info, supplier);
    	}
	}

	private Set getSupplierMgeCu(Context ctx,String cuId) throws BOSException {
		Set set = new HashSet();
		
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("id");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", OrgConstants.SYS_CU_ID,CompareType.NOTEQUALS));
		view.setFilter(filter);
		view.setSelector(sel);
		
		CtrlUnitCollection orgColl = CtrlUnitFactory.getLocalInstance(ctx).getCtrlUnitCollection(view);
		for (int i = 0; i < orgColl.size(); i++) {
			if(cuId.equals(orgColl.get(i).getId().toString())){
				continue;
			}
			set.add(orgColl.get(i).getId().toString());
		}
		return set;
	}
	
	private void updateSysSupplier(Context ctx, SupplierStockInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		setSysSupplierValue(ctx, info, supplier);
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("number");
		selCol.add("name");
		selCol.add("address");
		selCol.add("province");
		selCol.add("city");
		selCol.add("description");
		selCol.add("adminCU");
		selCol.add("CU");
		selCol.add("browseGroup");
		selCol.add("customerGroupDetails");
		selCol.add("bizRegisterNo");
		selCol.add("artificialPerson");
		selCol.add("busiLicence");
		selCol.add("taxRegisterNo");
		selCol.add("version");
		selCol.add("usedStatus");
		selCol.add("effectedStatus");
		selCol.add("simpleName");
		selCol.add("isInternalCompany");
		SupplierFactory.getLocalInstance(ctx).updatePartial(supplier, selCol);
	}
    
	private void setSysSupplierValue(Context ctx, SupplierStockInfo info, SupplierInfo supplier) throws BOSException, EASBizException {
		supplier.setNumber("FDC-" + info.getNumber());
		supplier.setName(info.getName());
		supplier.setAddress(info.getAddress());
		supplier.setProvince(info.getProvince());
		supplier.setCity(info.getCity());
		supplier.setDescription(info.getDescription());
		supplier.setArtificialPerson(info.getEnterpriseMaster());
		supplier.setBizRegisterNo(info.getBizRegisterNo());
		supplier.setBusiLicence(info.getBusinessNum());
		supplier.setTaxRegisterNo(info.getTaxRegisterNo());
		CtrlUnitInfo cu = new CtrlUnitInfo();
		cu.setId(BOSUuid.read(OrgConstants.DEF_CU_ID));
		supplier.setCU(cu);
		supplier.setAdminCU(cu);
		
		supplier.setVersion(1);
		supplier.setUsedStatus(UsedStatusEnum.APPROVED);
		supplier.setEffectedStatus(EffectedStatusEnum.EFFECTED);
		supplier.setIsInternalCompany(false);
		if(info.getPurchaseOrgUnit()!=null){
			supplier.setSimpleName(info.getPurchaseOrgUnit().getName());
		}
	}
}