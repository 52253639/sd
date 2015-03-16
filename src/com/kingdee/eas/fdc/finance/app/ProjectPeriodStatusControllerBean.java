package com.kingdee.eas.fdc.finance.app;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.ormapping.SQLAccessException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.assistant.ISystemStatusCtrol;
import com.kingdee.eas.basedata.assistant.PeriodException;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolFactory;
import com.kingdee.eas.basedata.assistant.SystemStatusCtrolUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.ICompanyOrgUnit;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusException;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusFactory;
import com.kingdee.eas.fdc.finance.ProjectPeriodStatusInfo;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.SystemEnum;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProjectPeriodStatusControllerBean extends AbstractProjectPeriodStatusControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusControllerBean");


	
	protected Map _closeInit(Context ctx, List projectIds, String curOrgId, boolean isCompany) throws BOSException, EASBizException {
		
		//没有结束初始化的结束初始化
		String statusSelectSql = " Select a.fid ,a.fprojectid,a.FIsClosed,a.Fstartperiodid,b.ffullorgunit from T_FNC_ProjectPeriodStatus  a   \r\n"
				+ " inner join t_fdc_curproject b on a.fprojectid=b.fid where a.fid=? ";//and   a.FIsClosed=0 
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=1 where fprojectid=? ";
		
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	        
        Map comMap = new HashMap();
        
		//结束初始化
		for(int i=0,count=projectIds.size();i<count;i++){
			String statusId = (String)projectIds.get(i);
			
			//判断是否已经存在T_FNC_ProjectPeriodStatus
			rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{statusId});

			//设置所有单据期间为空的数据
			String periodId;
			try {
				if(rs2!=null && rs2.next()){
					String projectId =  rs2.getString("fprojectid");	
					periodId = rs2.getString("Fstartperiodid");		
					
					//工程项目结束初始化
					DbUtil.execute(ctx,updateSql,new Object[]{projectId}); 
					
					//记录组织
					String comId = rs2.getString("ffullorgunit");
					if(!comMap.containsKey(comId)){
						comMap.put(rs2.getString("ffullorgunit"),Boolean.TRUE);
					}
					
					//同步单据
					syscBill(ctx,projectId,comId,periodId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		String statusComSql = " Select a.fid ,a.FIsClosed,a.Fstartperiodid,b.ffullorgunit from T_FNC_ProjectPeriodStatus  a   \r\n"
			+ " inner join t_fdc_curproject b on a.fprojectid=b.fid where b.ffullorgunit=? and   a.FIsClosed=0 ";
		//如果所有工程项目结束初始化了,那么:设置组织启用
		
		//设置组织启用
		Set set = comMap.keySet();
		Iterator it = set.iterator();
		try {
			while(it.hasNext()){
				String comId = (String)it.next();
				//公司
				rs2 = DbUtil.executeQuery(ctx,statusComSql,new Object[]{comId});

				if(rs2==null ||  !rs2.next()){
					companyInfo = GlUtils.getCurrentCompany(ctx,comId,null,false);
		            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
		            sysStatus.start(SystemEnum.FDC, companyInfo,true);
				}			
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
		return null;//_fetchInitData( ctx,  curOrgId,  isCompany);
	}

	/**
	 * 关闭工程，关闭之后，不能再继续新增合同（无文本），其他单据可以后续处理完
	 */
	protected void _closeProject(Context ctx, List projectStatusIds) throws BOSException, EASBizException {
		//找到该组织所有的工程项目
		String lang = ctx.getLocale().getLanguage();
		StringBuffer selectCurProSql = new StringBuffer();
		selectCurProSql.append("Select b.fid ,a.FIsEnd,a.FIsClosed ,a.FCostPeriodID,FFinacialPeriodID,b.FDisplayName_"+lang);
		selectCurProSql.append("  FDisplayName from T_FNC_ProjectPeriodStatus 	a							\r\n");
		selectCurProSql.append("　inner join t_fdc_curproject b on a.fprojectid=b.fid where a.fid=?    		\r\n");
		
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsEnd=1 where fprojectid=? ";
		
		IRowSet rs2 = null;
		try {
			for(int i=0,count=projectStatusIds.size();i<count;i++){
				String id = (String)projectStatusIds.get(i);
				
				rs2 = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{id});
				if(rs2!=null && rs2.next()){
					String projectId = rs2.getString("fid");
					String displayName = rs2.getString("FDisplayName");
					
					String FCostPeriodID = rs2.getString("FCostPeriodID");
					String FFinacialPeriodID = rs2.getString("FFinacialPeriodID");
					//工程项目{0}已经关闭提示已关闭
					if(rs2.getBoolean("FIsEnd")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASEND,new Object[]{displayName});
					}
					//工程项目{0}未结束初始化不能关闭
					if(!rs2.getBoolean("FIsClosed")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_UNINIT,new Object[]{displayName});
					}
					
					//工程项目{0}成本已经月结，财务成本没有月结不一致
					if(rs2.getString("FCostPeriodID").equals("FFinacialPeriodID")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASCLOSED,new Object[]{displayName});	
					}
					//工程项目{0}的当前期间是否有已审核的合同（无文本合同）
					if(checkContractBill( ctx, projectId,FCostPeriodID,false)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASCONTR,new Object[]{displayName});
					}
					if(checkContractBill( ctx, projectId,FFinacialPeriodID,true)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_HASCONTRNO,new Object[]{displayName});
					}
					
					DbUtil.execute(ctx,updateSql,new Object[]{projectId});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
	}

	protected Map _closeAll(Context ctx, String orgUnit,boolean isCompany) throws BOSException, EASBizException {
		
		//找到该组织所有的工程项目
		StringBuffer selectCurProSql = new StringBuffer();
		if(isCompany){//虚体组织
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit from t_fdc_curproject cp 		\r\n");
			selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
			selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  pbu.fid=?		");
		}else{
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate ,cp.ffullorgunit from T_FDC_ProjectWithCostCenterOU wi 		\r\n");
			selectCurProSql.append("inner join t_fdc_curproject pcp on wi.fcurprojectid=pcp.fid				\r\n");
			selectCurProSql.append("inner join t_fdc_curproject cp on charindex(pcp.FLongNumber || '!', cp.FLongNumber || '!') = 1				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and wi.fcostcenterouid=?			");
		}
		
		//没有结束初始化的结束初始化
		String statusSelectSql = " Select fid ,FIsClosed,Fstartperiodid from T_FNC_ProjectPeriodStatus where fprojectid=?  ";
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=1 where fprojectid=? ";
	
		List projectList = new ArrayList();
		Map projectComMap =  new HashMap();
		Map projectStartPrdMap =  new HashMap();
		
		IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{orgUnit}); 
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	
        
        Map comMap = new HashMap();
        Map comPeriodMap = new HashMap();
        
		if(rs!=null){
			try {
				while(rs.next()){
					String projectid = rs.getString("Fid");
					Date startDate = rs.getDate("FstartDate");
					String comId = rs.getString("ffullorgunit"); 
					
					//判断是否已经存在T_FNC_ProjectPeriodStatus
					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
					
					CurProjectInfo curProject = new CurProjectInfo();
					curProject.setId(BOSUuid.read(projectid));
					FullOrgUnitInfo company = new FullOrgUnitInfo();
					company.setId(BOSUuid.read(comId));
					
					projectList.add(projectid);
					//工程项目对应的财务组织
					projectComMap.put(projectid,comId);
						
					if(comMap.containsKey(comId)){
						companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
					}else{
						companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
						comMap.put(comId,companyInfo);
					}
					
					//已经结束初始化，返回
					boolean isClose = false;
					if(rs2!=null && rs2.next()){
						//工程项目对应的期间
						projectStartPrdMap.put(projectid,rs2.getString("Fstartperiodid"));
						
						if(rs2.getBoolean("FIsClosed")){
							continue ;
						}
						isClose = true;
					}
					
					if(isClose)
					{
						//对于当前组织在ProjectPeriodStatus已经存在数据的,还没有结束初始化，则update
						DbUtil.execute(ctx,updateSql,new Object[]{projectid}); 
					}else{

						//获取当前期间
				        PeriodInfo period = null;
				        if(comPeriodMap.containsKey(comId)){
				        	period = (PeriodInfo)comPeriodMap.get(comId);
				        }else{
				        	period = //FDCUtils.getCurrentPeriod(ctx,projectid,true); 
				        		SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, companyInfo);
				        	comPeriodMap.put(comId,period);
				        }
				        if(period==null){
				        	throw new PeriodException(PeriodException.NOT_SET_STARTPERIOD);
				        }
				        
				        //根据startDate获取一个期间，看哪个期间在前
				        PeriodInfo startperiod =  
				        	FDCUtils.fetchPeriod(ctx,companyInfo.getAccountPeriodType().getId().toString(),startDate);
				        	//PeriodUtils.getPeriodInfo(ctx,startDate,companyInfo);
				        if(startperiod!=null && startperiod.getBeginDate().after(period.getBeginDate())){
				        	period = startperiod;
				        }
				        
						//对于当前组织在ProjectPeriodStatus不存在数据的,则insert
						ProjectPeriodStatusInfo info = new ProjectPeriodStatusInfo();
						info.setIsClosed(true);
						info.setStartPeriod(period);
						info.setCostPeriod(period);
						info.setFinacialPeriod(period);
						info.setOrgUnit(company);
						info.setProject(curProject);
						
						_addnew(ctx,info);
						
						//工程项目对应的期间
						projectStartPrdMap.put(projectid,period.getId().toString());
					}
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		//设置单据
		for(int i=0,count=projectList.size();i<count;i++){
			String projectId = (String)projectList.get(i);
			String comId = (String)projectComMap.get(projectId);
			String periodId = (String)projectStartPrdMap.get(projectId);
			
			syscBill(ctx,projectId,comId,periodId);
		}
		
		//设置组织启用
		Set set = comMap.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String comId = (String)it.next();
			companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
            sysStatus.start(SystemEnum.FDC, companyInfo,true);
		}
		

		return null;//_fetchInitData( ctx,  orgUnit,  isCompany);
	}
	
	private void syscBill(Context ctx,String projectId,String comId,String periodId) throws BOSException{
		//设置所有单据期间为空的数据
//		update  T_CON_ContractBill  set FBookedDate = FSignDate where FBookedDate is null and Fcurprojectid=? ;
//		update  T_CON_ContractWithoutText  set FBookedDate = FSignDate where FBookedDate is null;
//		update  T_CON_ContractBillRevise  set FBookedDate = FSignDate where FBookedDate is null;
//		update   T_CON_ContractSettlementBill   set FBookedDate = fcreatetime where FBookedDate is null;
//		update  T_CON_PayRequestBill   set FBookedDate = FPayDate where FBookedDate is null;
//		update  t_con_changeauditbill   set FBookedDate = fcreatetime where FBookedDate is null;
		
		//合同
		String contractSql = "update T_CON_ContractBill set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null) and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractSql,new Object[]{periodId,projectId});
		
		//无文本合同
		String contractWithSql = "update T_CON_ContractWithoutText set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractWithSql,new Object[]{periodId,projectId});
		
		//修订单据
		String contractRevSql = "update T_CON_ContractBillRevise set FBookedDate = FSignDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,contractRevSql,new Object[]{periodId,projectId});	
				
		//变更单据
		String changeauditSql = "update t_con_changeauditbill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,changeauditSql,new Object[]{periodId,projectId});	
		
		String conchangeSql = "update t_con_contractchangebill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,conchangeSql,new Object[]{periodId,projectId});	
		
		//结算单
		String contractSettleSql = "update T_CON_ContractSettlementBill set FBookedDate = fcreatetime,FperiodId=? where ( FperiodId is null or FBookedDate is null) and Fcurprojectid=?   ";
		DbUtil.execute(ctx,contractSettleSql,new Object[]{periodId,projectId});	
		
		//付款申请单
		String payRequestSql = "update T_CON_PayRequestBill set FBookedDate = FPayDate,FperiodId=? where ( FperiodId is null or FBookedDate is null)  and Fcurprojectid=? ";
		DbUtil.execute(ctx,payRequestSql,new Object[]{periodId,projectId});	
		
		//合同拆分
		String contractSplitSql = "update T_CON_ContractCostSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and FContractBillid in (select fid from t_con_contractbill where fcurprojectid=?) ";
		DbUtil.execute(ctx, contractSplitSql, new Object[] { periodId,
				projectId });

		// 变更拆分
		String conChangeSplitSql = "update T_CON_ConChangeSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and fcontractchangeid in (select fid from t_con_contractchangebill where fcurprojectid=?) ";
		DbUtil.execute(ctx, conChangeSplitSql, new Object[] { periodId,
				projectId });

		// 结算拆分
		String settlementSplitSql = "update t_Con_Settlementcostsplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and fsettlementbillid in (select fid from t_con_contractsettlementbill  where fcurprojectid=?) ";
		DbUtil.execute(ctx, settlementSplitSql, new Object[] { periodId,
				projectId });

		// 付款拆分
		String paymentSplitSql = "update T_FNC_PaymentSplit set FBookedDate = FCreatetime,FperiodId=? where FperiodId is null and FPaymentBillid in (select fid from t_cas_paymentbill where fcurprojectid=?) ";
		DbUtil.execute(ctx, paymentSplitSql, new Object[] { periodId,
				projectId });
		
		//待发生成本调整
		String adjustSql = "update T_AIM_AdjustRecordEntry set FperiodId=? where FperiodId is null and FParentID in (select FID from T_AIM_DynamicCost where FAccountID in (select FID from T_FDC_CostAccount where FCurProject=?)) ";
		DbUtil.execute(ctx, adjustSql, new Object[] { periodId,
				projectId });
		
		//指标
		String prjIndexSql = "update T_FDC_ProjectIndexData set FperiodId=? where FperiodId is null and FProjOrOrgID=? ";
		DbUtil.execute(ctx, prjIndexSql, new Object[] { periodId,
				projectId });
		
		//目标成本
		String aimCostSql = "update T_AIM_AimCost set FperiodId=? where FperiodId is null and FOrgOrProId=? ";
		DbUtil.execute(ctx, aimCostSql, new Object[] { periodId,
				projectId });
	}
	
	private boolean checkBill(Context ctx,String projectId,int i) throws BOSException, SQLException{
		
		//是否
		String checkSql = " Select top 1 Fid from t_con_contractBill where fcurProjectId=?  and fstate='4AUDITTED' 			";
		if(i==2){
			 checkSql = "  Select top 1 Fid from t_con_contractwithouttext where fcurProjectId=?  and fstate='4AUDITTED' 		";
		}else if(i==3){
			checkSql = "  Select top 1 Fid from t_con_ChangeAuditBill where fcurProjectId=?  and fstate='4AUDITTED' 		";
		}
		
		IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId});
		if(rs!=null && rs.next()){
			return true;
		}
		
		return false;
	}
	
	private boolean checkContractBill(Context ctx,String projectId,String periodId,boolean isNotext) throws BOSException, SQLException{
		
		//是否
		String checkSql = (!isNotext)?" Select top 1 Fid from t_con_contractBill where fcurProjectId=? and fPeriodid=? and fstate='4AUDITTED' 			\r\n" 
				:"  Select top 1 Fid from t_con_contractwithouttext where fcurProjectId=?  and fPeriodid=? and fstate='4AUDITTED' 		";
		
		IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId,periodId});
		if(rs!=null && rs.next()){
			return true;
		}
		
		return false;
	}

	protected Map _closeUnInit(Context ctx, List projectIds,String orgUnit, boolean isCompany) throws BOSException, EASBizException {
		
		//没有结束初始化的结束初始化
		String lang = ctx.getLocale().getLanguage();
		String statusSelectSql = " Select a.fid ,a.fprojectid,a.FIsClosed,a.Fstartperiodid,a.FCostPeriodID,b.ffullorgunit,b.FDisplayName_"+lang+
				" FDisplayName" 
				+ " from T_FNC_ProjectPeriodStatus  a   									\r\n"
				+ " inner join t_fdc_curproject b on a.fprojectid=b.fid where a.fid=? 		";//and   a.FIsClosed=0 
		
		
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=0 where fprojectid=? ";
		
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	        
        Map comMap = new HashMap();
        
		//结束初始化
		for(int i=0,count=projectIds.size();i<count;i++){
			String statusId = (String)projectIds.get(i);
			
			//判断是否已经存在T_FNC_ProjectPeriodStatus
			rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{statusId});

			//设置所有单据期间为空的数据
			try {
				if(rs2!=null && rs2.next()){
					String projectId =  rs2.getString("fprojectid");	
					String startPeriodId = rs2.getString("Fstartperiodid");		
					String costPeriodId = rs2.getString("FCostPeriodID");
					String displayName = rs2.getString("FDisplayName");
					
					if(costPeriodId==null){
						return null;
					}
					//成本已经月结，工程项目{０}不能返初始化
					if(!costPeriodId.equals(startPeriodId)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCOS,new Object[]{displayName});
					}
					
					//IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId});
					if(checkBill( ctx, projectId,1)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
					}
					if(checkBill( ctx, projectId,2)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONNO,new Object[]{displayName});
					}
					if(checkBill( ctx, projectId,3)){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONAU,new Object[]{displayName});
					}
					
					//工程项目反初始化
					DbUtil.execute(ctx,updateSql,new Object[]{projectId}); 
					
					//记录组织
					String comId = rs2.getString("ffullorgunit");
					if(!comMap.containsKey(comId)){
						comMap.put(rs2.getString("ffullorgunit"),startPeriodId);
					}

				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		//设置组织启用
		Set set = comMap.keySet();
		Iterator it = set.iterator();

		while(it.hasNext()){
				String comId = (String)it.next();
				
				
				companyInfo = GlUtils.getCurrentCompany(ctx,comId,null,false);
				
				PeriodInfo curPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx,SystemEnum.FDC,companyInfo);
				PeriodInfo startPeriod = SystemStatusCtrolUtils.getStartPeriod(ctx,SystemEnum.FDC,companyInfo);
				String startPeriodId = startPeriod.getId().toString();
				
				if(curPeriod.getId().toString().equals(startPeriodId)){
					//公司					
		            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
		            sysStatus.start(SystemEnum.FDC, companyInfo,false);		
				}
		}

			
		return null;//_fetchInitData( ctx,  orgUnit,  isCompany);
	}

	protected void _closeUnProject(Context ctx, List projectStatusIds) throws BOSException, EASBizException {
		//找到该组织所有的工程项目
		String lang = ctx.getLocale().getLanguage();
		StringBuffer selectCurProSql = new StringBuffer();
		selectCurProSql.append("Select p.FBeginDate,b.fid ,a.FIsEnd,a.FIsClosed ,a.FCostPeriodID,FFinacialPeriodID,b.FDisplayName_"+lang);
		selectCurProSql.append("  FDisplayName,b.ffullorgunit											\r\n");
		selectCurProSql.append("  from T_FNC_ProjectPeriodStatus 	a									\r\n");
		selectCurProSql.append("　inner join t_fdc_curproject b on a.fprojectid=b.fid   					\r\n");
		selectCurProSql.append("　inner join t_bd_period p on a.FCostPeriodID=p.fid where a.fid=?    	\r\n");
		
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsEnd=0 where fprojectid=? ";
		
        CompanyOrgUnitInfo companyInfo = null ;	
        Map comMap = new HashMap();
        Map comPeriodMap = new HashMap();
		IRowSet rs2 = null;
		try {
			for(int i=0,count=projectStatusIds.size();i<count;i++){
				String id = (String)projectStatusIds.get(i);
				
				rs2 = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{id});
				if(rs2!=null && rs2.next()){
					String projectId = rs2.getString("fid");
					String displayName = rs2.getString("FDisplayName");
					//工程项目{0}未关闭,提示未关闭
					if(!rs2.getBoolean("FIsEnd")){
						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_NOTEND,new Object[]{displayName});
					}
						
					String comId = rs2.getString("ffullorgunit");
					if(comMap.containsKey(comId)){
						companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
					}else{
						companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
						comMap.put(comId,companyInfo);
					}
			        PeriodInfo period = null;
			        if(comPeriodMap.containsKey(comId)){
			        	period = (PeriodInfo)comPeriodMap.get(comId);
			        }else{
			        	period = SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, companyInfo);
			        	comPeriodMap.put(comId,period);
			        }
			        Date year = rs2.getDate("FBeginDate");			      
			        //工程项目当前成本期间比财务系统当前期间小，不能反关闭
			        if(year.before(period.getBeginDate())){
			        	throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CLOPRO_BEFORSY,new Object[]{displayName});
			        }					
					
					//工程项目当前期间是否没有已审核的合同（无文本合同）
//					if(checkBill( ctx, projectId)){
//						throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
//					}
					
					DbUtil.execute(ctx,updateSql,new Object[]{projectId});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BOSException(e);
		}
		
	}

	protected Map _closeUnAll(Context ctx, String orgUnit, boolean isCompany) throws BOSException, EASBizException {
		
		//找到该组织所有的工程项目
		String lang = ctx.getLocale().getLanguage();
		StringBuffer selectCurProSql = new StringBuffer();
		if(isCompany){//虚体组织
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit,cp.FDisplayName_"+lang+" FDisplayName from t_fdc_curproject cp 		\r\n");
			selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
			selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  pbu.fid=?		");
		}else{
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate ,cp.ffullorgunit,cp.FDisplayName_"+lang+" FDisplayName from T_FDC_ProjectWithCostCenterOU wi 		\r\n");
			selectCurProSql.append("inner join t_fdc_curproject pcp on wi.fcurprojectid=pcp.fid				\r\n");
			selectCurProSql.append("inner join t_fdc_curproject cp on charindex(pcp.FLongNumber || '!', cp.FLongNumber || '!') = 1				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and wi.fcostcenterouid=?			");
		}
		
		//没有结束初始化的结束初始化
		String statusSelectSql = " Select fid ,FIsClosed,Fstartperiodid,FCostPeriodID from T_FNC_ProjectPeriodStatus where fprojectid=?  and FIsClosed=1 ";
		String updateSql = 		"update T_FNC_ProjectPeriodStatus set FIsClosed=0 where fprojectid=? ";
		String checkSql = " Select fid from t_con_contractBill where fcurProjectId=? and fstate='4AUDITTED' ";
		
		
		IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{orgUnit}); 
		IRowSet rs2 = null;
        CompanyOrgUnitInfo companyInfo = null ;	
        
        Map comMap = new HashMap();
        
		if(rs!=null){
			try {
				while(rs.next()){
					String projectid = rs.getString("Fid");
					String comId = rs.getString("ffullorgunit"); 
					String displayName = rs.getString("FDisplayName");
					
					//判断是否已经存在T_FNC_ProjectPeriodStatus
					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
					
					if(rs2!=null && rs2.next()){	
						String startPeriodId = rs2.getString("Fstartperiodid");		
						String costPeriodId = rs2.getString("FCostPeriodID");						
						
						//成本已经月结，工程项目{０}不能返初始化
						if(!costPeriodId.equals(startPeriodId)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCOS,new Object[]{displayName});
						}
						
						IRowSet rs3 = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectid});
						if(rs3!=null && rs3.next()){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
						}
						
						//IRowSet rs = DbUtil.executeQuery(ctx,checkSql,new Object[]{projectId});
						if(checkBill( ctx, projectid,1)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCON,new Object[]{displayName});
						}					
						if(checkBill( ctx, projectid,2)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONNO,new Object[]{displayName});
						}
						if(checkBill( ctx, projectid,3)){
							throw new ProjectPeriodStatusException(ProjectPeriodStatusException.CNT_UNINITPROBYCONAU,new Object[]{displayName});
						}
												
						//工程项目反初始化
						DbUtil.execute(ctx,updateSql,new Object[]{projectid}); 
						
						//记录组织
						if(!comMap.containsKey(comId)){
							comMap.put(comId,startPeriodId);
						}
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		
		
		//设置组织启用
		Set set = comMap.keySet();
		Iterator it = set.iterator();

		while(it.hasNext()){
				String comId = (String)it.next();
				
				//String startPeriodId = (String)comMap.get(comId);
				companyInfo = GlUtils.getCurrentCompany(ctx,comId,null,false);
				
				PeriodInfo curPeriod = SystemStatusCtrolUtils.getCurrentPeriod(ctx,SystemEnum.FDC,companyInfo);
				PeriodInfo startPeriod = SystemStatusCtrolUtils.getStartPeriod(ctx,SystemEnum.FDC,companyInfo);
				String startPeriodId = startPeriod.getId().toString();
				
				if(curPeriod.getId().toString().equals(startPeriodId)){
					//公司					
		            ISystemStatusCtrol sysStatus = SystemStatusCtrolFactory.getLocalInstance(ctx);
		            sysStatus.start(SystemEnum.FDC, companyInfo,false);		
				}
		}
			
		return null;//_fetchInitData( ctx,  orgUnit,  isCompany);
	}

	/**
	 * 根据日期获得一个期间
	 * 如果找不到期间,则提示:根据日期没有找到合适的期间
	 * 如果找到的期间比当前工程项目的成本当前期间前,按么提示:获取的期间不能早于当前期间
	 * @param projectId   	工程项目Id	
	 * @param bookedDate	业务发生日期
	 * @param isCost		是否成本单据
	 */
	protected IObjectValue _fetchPeriod(Context ctx, String projectId, java.util.Date bookedDate,boolean isCost) throws BOSException, EASBizException {
        
        SelectorItemCollection sic = new SelectorItemCollection();//view.getSelector();
        sic.add(new SelectorItemInfo("fullOrgUnit.id"));
        
        CurProjectInfo info = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),sic);
        String comId = info.getFullOrgUnit().getId().toString();
        
        CompanyOrgUnitInfo company = GlUtils.getCurrentCompany(ctx,comId,null,true);
        
        //根据日期获得一个期间
        PeriodInfo period = FDCUtils.fetchPeriod(ctx,company.getAccountPeriodType().getId().toString(),bookedDate);
        //当前工程项目的成本当前期间
        PeriodInfo curPeriod = FDCUtils.getCurrentPeriod(ctx,projectId,isCost);
        
        if(curPeriod!=null && period!=null && period.getBeginDate().before(curPeriod.getBeginDate())){
        	//throw new ProjectPeriodStatusException(ProjectPeriodStatusException.AUDIT_CUR_PERIOD);
        	return curPeriod;
        }
        
		return period;
	}

	protected Map _fetchInitData(Context ctx, String curOrgId, boolean isCompany) throws BOSException, EASBizException {

		Map initData = new HashMap();
		boolean isAllClosed = true;
		boolean isUnAllClosed = true;
		boolean hasClosed = false;
		
		//找到该组织所有的工程项目
		StringBuffer selectCurProSql = new StringBuffer();
		if(isCompany){//虚体组织
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit from t_fdc_curproject cp 		\r\n");
			selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
			selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1  and  pbu.fid=?		");
		}else{
			selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate ,cp.ffullorgunit from T_FDC_ProjectWithCostCenterOU wi 		\r\n");
			selectCurProSql.append("inner join t_fdc_curproject pcp on wi.fcurprojectid=pcp.fid				\r\n");
			selectCurProSql.append("inner join t_fdc_curproject cp on charindex(pcp.FLongNumber || '!', cp.FLongNumber || '!') = 1				\r\n");
			selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and wi.fcostcenterouid=?			");
		}
		
		IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{curOrgId}); 
		String statusSelectSql = " Select FIsClosed from T_FNC_ProjectPeriodStatus where fprojectid=?  ";
		IRowSet rs2 = null;
		
		if(rs!=null){
			try {
				while(rs.next()){
					
					String projectid = rs.getString("Fid");					
					//判断是否已经存在T_FNC_ProjectPeriodStatus
					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
					
					if(rs2!=null && rs2.next()){
						boolean isClosed = rs2.getBoolean("FIsClosed");
						if(isClosed){
							isUnAllClosed = false;
						}else{
							isAllClosed = false;
						}
					}
				}
				
				hasClosed = !isUnAllClosed && !isAllClosed;
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new BOSException(e);
			}
		}
		//是否全部结束初始化		
		//是否全部未结束初始化
		
		initData.put("isAllClosed",Boolean.valueOf(isAllClosed));
		initData.put("isUnAllClosed",Boolean.valueOf(isUnAllClosed));
		initData.put("hasClosed",Boolean.valueOf(hasClosed));
		
		return initData;
	}

	//
	protected void _paramCheck(Context ctx, String orgUnit) throws BOSException, EASBizException {
		
		List projectList = new ArrayList();
		Map projectComMap =  new HashMap();
		Map projectStartPrdMap =  new HashMap();
		
		
		CompanyOrgUnitInfo companyInfo = GlUtils.getCurrentCompany(ctx, orgUnit,
				null, false);
		// 获取期间;
		PeriodInfo period = SystemStatusCtrolUtils.getCurrentPeriod(ctx,
				SystemEnum.FDC, companyInfo);
		// 当前组织成本与财务一体化，系统设置的系统状态控制中，还没有设置房地产系统启用期间。
		if (period == null) {
			throw new ProjectPeriodStatusException(
					ProjectPeriodStatusException.NOT_COM_START);
		}
		
		try {
			
			
	        Map comMap = new HashMap();
	        comMap.put(orgUnit,companyInfo);
//	        CompanyOrgUnitInfo companyInfo = null ;	
	        Map comPeriodMap = new HashMap();

				//找到该组织所有的工程项目
				StringBuffer selectCurProSql = new StringBuffer();
				selectCurProSql.append("select cp.fid Fid,cp.FstartDate FstartDate,cp.ffullorgunit from t_fdc_curproject cp 		\r\n");
//				selectCurProSql.append("inner join t_org_baseUnit cbu on cp.ffullorgunit=cbu.fid									\r\n");
//				selectCurProSql.append("inner join t_org_baseUnit pbu on charindex(pbu.FLongNumber || '!', cbu.FLongNumber || '!') = 1   				\r\n");
//				selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  pbu.fid=?	and cp.fid not in ( " );
				selectCurProSql.append("where cp.fisenabled = 1 and cp.FisLeaf=1 and  cp.ffullorgunit=?	and cp.fid not in ( " );
				selectCurProSql.append(		" select fprojectid from T_FNC_ProjectPeriodStatus where  FOrgUnitID=? )	");
				
				//没有结束初始化的结束初始化
//				String statusSelectSql = " Select fid  from T_CON_ContractBill where fcurprojectid=? and Fstate ='4AUDITTED' ";
			
				IRowSet rs = DbUtil.executeQuery(ctx,selectCurProSql.toString(),new Object[]{orgUnit,orgUnit}); 
//				IRowSet rs2 = null;
				
				while(rs.next()){
					String projectid = rs.getString("Fid");
					Date startDate = rs.getDate("FstartDate");
					String comId = rs.getString("ffullorgunit"); 
					
					projectList.add(projectid);
					projectComMap.put(projectid,comId);
			
					if(comMap.containsKey(comId)){
						companyInfo = (CompanyOrgUnitInfo)comMap.get(comId);
					}else{
						companyInfo = GlUtils.getCurrentCompany(ctx, comId,null,false) ;
						comMap.put(comId,companyInfo);
					}
					
					CurProjectInfo curProject = new CurProjectInfo();
					curProject.setId(BOSUuid.read(projectid));
					FullOrgUnitInfo company = new FullOrgUnitInfo();
					company.setId(BOSUuid.read(comId));
					
					//获取当前期间
				    period = null;
				    if(comPeriodMap.containsKey(comId)){
				        period = (PeriodInfo)comPeriodMap.get(comId);
				    }else{
				        period =
				        		SystemStatusCtrolUtils.getCurrentPeriod(ctx, SystemEnum.FDC, companyInfo);
				        comPeriodMap.put(comId,period);
				    }
				    if(period==null){
				        	throw new PeriodException(PeriodException.NOT_SET_STARTPERIOD);
				    }
				    
				    boolean isStart = SystemStatusCtrolUtils.isStart(ctx,SystemEnum.FDC, companyInfo);
				        
				     //根据startDate获取一个期间，看哪个期间在前
				    PeriodInfo startperiod =  
				        	FDCUtils.fetchPeriod(ctx,companyInfo.getAccountPeriodType().getId().toString(),startDate);
				    if(startperiod!=null && startperiod.getBeginDate().after(period.getBeginDate())){
				        	period = startperiod;
				    }
				        
					//对于当前组织在ProjectPeriodStatus不存在数据的,则insert
					ProjectPeriodStatusInfo info = new ProjectPeriodStatusInfo();
					info.setIsClosed(isStart);
					info.setStartPeriod(period);
					info.setCostPeriod(period);
					info.setFinacialPeriod(period);
					
					info.setOrgUnit(company);
					info.setProject(curProject);
						
					projectStartPrdMap.put(projectid,period.getId().toString());
					
					IObjectPK prPpk = this.addnew(ctx,info);
//						
//					//判断是否已经存在T_FNC_ProjectPeriodStatus
//					rs2 = DbUtil.executeQuery(ctx,statusSelectSql,new Object[]{projectid});
//					if(rs2!=null && rs2.next()){
//						info.setIsClosed(isStart);
//						info.setId(BOSUuid.read(prPpk.toString()));
//						this.update(ctx,prPpk,info);
//					}
				}
			
		} catch (SQLException e) {
			throw new SQLAccessException(e);
		}
		
		//设置单据
		for(int i=0,count=projectList.size();i<count;i++){
			String projectId = (String)projectList.get(i);
			String comId = (String)projectComMap.get(projectId);
			String periodId = (String)projectStartPrdMap.get(projectId);
			
			syscBill(ctx,projectId,comId,periodId);
		}
		
	}
}