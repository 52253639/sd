package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import javax.sql.RowSet;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.jdbc.rowset.IRowSet;

public class MonthPlanExcuteFacadeControllerBean extends AbstractMonthPlanExcuteFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.MonthPlanExcuteFacadeControllerBean");
    
    protected RptParams _init(Context ctx, RptParams params)throws BOSException, EASBizException
	{
	    RptParams pp = new RptParams();
	    return pp;
	}
    private void initColoum(RptTableHeader header,RptTableColumn col,String name,int width,boolean isHide){
    	col= new RptTableColumn(name);
    	col.setWidth(width);
	    col.setHided(isHide);
	    header.addColumn(col);
    }
    protected RptParams _createTempTable(Context ctx, RptParams params)    throws BOSException, EASBizException
	{
	    RptTableHeader header = new RptTableHeader();
	    RptTableColumn col = null;
	    initColoum(header,col,"pCurProjectId",100,true);
	    initColoum(header,col,"curProjectId",100,true);
	    initColoum(header,col,"curProjectNumber",150,false);
	    initColoum(header,col,"curProjectName",150,false);
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"number",150,false);
	    initColoum(header,col,"name",150,false);
	    initColoum(header,col,"supplier",200,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"lastPrice",100,false);
	    initColoum(header,col,"proId",100,true);
	    initColoum(header,col,"proNumber",150,true);
	    initColoum(header,col,"proName",150,true);
	    initColoum(header,col,"proAmount",100,true);
	    initColoum(header,col,"accActPayAmount",100,false);
	    initColoum(header,col,"accActPayRate",100,false);
	    initColoum(header,col,"accActOnLoadPayAmount",100,false);
	    
	    Object[] headLabels=new Object[]{"pCurProjectId","curProjectId","工程项目编码","工程项目名称","id","合同编码","合同名称","乙方信息","合同金额","合同最新造价","proId","合约规划编码","合约规划名称","合约规划金额","累计已付款金额","累计付款比例（%）","在途付款金额"};
	    Object[][] monthLabels=addMonthColoum(ctx,params,header,headLabels);
	   
	    header.setLabels(monthLabels,true);
	    params.setObject("header", header);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	FDCSQLBuilder _builder = new FDCSQLBuilder(ctx);
		_builder.appendSql(getSql(ctx,params));
		final IRowSet rowSet = _builder.executeQuery();
		params.setObject("rowset", rowSet);
		return params;
    }
    protected Set getCurProjectIdSet(Context ctx,BOSUuid id) throws BOSException, EASBizException {
		Set idSet = new HashSet();
		CurProjectInfo curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(id));
		String longNumber = curProjectInfo.getLongNumber();

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber + "!%",CompareType.LIKE));
		filter.getFilterItems().add(
				new FilterItemInfo("longNumber", longNumber));
		filter.getFilterItems().add(
				new FilterItemInfo("fullOrgUnit.id", curProjectInfo.getFullOrgUnit().getId().toString()));
		filter.setMaskString("(#0 or #1) and #2");
		view.setFilter(filter);

		CurProjectCollection curProjectCollection = CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection(view);

		for (Iterator iter = curProjectCollection.iterator(); iter.hasNext();) {
			CurProjectInfo element = (CurProjectInfo) iter.next();
			idSet.add(element.getId().toString());
		}
		return idSet;
	}
    protected String getSql(Context ctx,RptParams params){
    	Date bizDate = (Date)params.getObject("bizDate");
    	OrgStructureInfo org=(OrgStructureInfo) params.getObject("org");
    	CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
    	
    	String curProjectStr=null;
    	if(curProject!=null){
    		try {
    			curProjectStr=SHEManageHelper.getStringFromSet(getCurProjectIdSet(ctx,curProject.getId()));
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} 
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select * from(select curProject.fid pCurProjectId,curProject.fid curProjectId,REPLACE(curProject.flongNumber, '!', '.') curProjectNumber,curProject.fname_l2 curProjectName,");
    	sb.append(" case when contract.fid is null and wtContract.fid is null then proContract.fid");
    	sb.append(" when proContract.fid is null and wtContract.fid is null then contract.fid else wtContract.fid end id,");
    	
    	sb.append(" case when contract.fnumber is null and wtContract.fnumber is null then proContract.fnumber");
    	sb.append(" when proContract.fnumber is null and wtContract.fnumber is null then contract.fnumber else wtContract.fnumber end number,");
    	
    	sb.append(" case when contract.fname is null and wtContract.fname is null then proContract.fname");
    	sb.append(" when proContract.fname is null and wtContract.fname is null then contract.fname else wtContract.fname end name,");
    	
    	sb.append(" case when supplier.fname_l2 is null and wtSupplier.fname_l2 is null and wtPerson.fname_l2 is null then proSupplier.fname_l2");
    	sb.append(" when proSupplier.fname_l2 is null and wtSupplier.fname_l2 is null and wtPerson.fname_l2 is null then supplier.fname_l2 ");
    	sb.append(" when proSupplier.fname_l2 is null and wtSupplier.fname_l2 is null and supplier.fname_l2 is null then wtPerson.fname_l2 else wtSupplier.fname_l2 end supplier,");
    	
    	sb.append(" case when contract.famount is null and wtContract.famount is null then proContract.famount");
    	sb.append(" when proContract.famount is null and wtContract.famount is null then contract.famount else wtContract.famount end amount,");
    	
    	sb.append(" case when contractPc.fid is null then pc.fid else contractPc.fid end proId,");
    	sb.append(" case when contractPc.fnumber is null then pc.fnumber else contractPc.fnumber end proNumber,");
    	sb.append(" case when contractPc.fname_l2 is null then pc.fname_l2 else contractPc.fname_l2 end proName,");
    	sb.append(" case when contractPc.famount is null then pc.famount else contractPc.famount end proAmount,");
    	
    	sb.append(" entry.fyear year,entry.fmonth month,entry.famount dateEntryAmount,entry.fid entryId");
    	
		sb.append(" from T_FNC_ProjectMonthPlanGather ga");
		sb.append(" left join (select fyear,fmonth,sum(dateEntry.famount) famount,fprogrammingContractid,contractBillId,(case when contractBillId is not null then entry.fId else entry.fheadid end) fid,entry.fheadId from T_FNC_ProjectMonthPlanGEntry entry");
    	sb.append(" left join T_FNC_ProjectMonthPGDateEntry dateEntry on dateEntry.FHeadEntryId=entry.fid group by fyear,fmonth,fprogrammingContractid,contractBillId,(case when contractBillId is not null then entry.fId else entry.fheadid end),entry.fheadId) entry on entry.fheadId=ga.fid");
    	sb.append(" left join T_FDC_CurProject curProject on curProject.fid=ga.fcurProjectid");
    	sb.append(" left join T_ORG_BaseUnit orgUnit on orgUnit.fid=ga.FOrgUnitID");
    	
    	sb.append(" left join T_CON_ContractBill contract on contract.fid=entry.contractBillId");
    	sb.append(" left join T_BD_Supplier supplier on supplier.fid=contract.FPartBID");
    	sb.append(" left join T_CON_ProgrammingContract contractPc on contractPc.fid=contract.FProgrammingContract");
    	
    	sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractid");
    	
    	sb.append(" left join T_CON_ContractWithoutText wtContract on wtContract.FProgrammingContract=pc.fid");
    	sb.append(" left join T_BD_Supplier wtSupplier on wtSupplier.fid=wtContract.FReceiveUnitID");
    	sb.append(" left join T_BD_Person wtPerson on wtPerson.fid=wtContract.FReceiveUnitID");
    	
    	sb.append(" left join T_CON_ContractBill proContract on proContract.FProgrammingContract=pc.fid");
    	sb.append(" left join T_BD_Supplier proSupplier on proSupplier.fid=proContract.FPartBID");
    	
    	sb.append(" where ga.fstate='4AUDITTED' and ga.fisLatest=1");
    	if(bizDate!=null){
    		sb.append(" and ga.fbizDate={ts '" + FDCConstants.FORMAT_TIME.format(bizDate)+ "'}");
    	}
    	if(org!=null){
        	sb.append(" and orgUnit.flongNumber like '"+org.getLongNumber()+"%'");
    	}else if(curProjectStr!=null){
    		sb.append(" and ga.fcurProjectid in("+curProjectStr+")");
    	}else{
    		sb.append(" and ga.fcurProjectid in ('null')");
    	}
    	sb.append(")t order by t.number desc");
    	return sb.toString();
    }
    protected Object[][] addMonthColoum(Context ctx,RptParams params,RptTableHeader header,Object[] headLabels) throws BOSException{
    	Date bizDate = (Date)params.getObject("bizDate");
    	OrgStructureInfo org=(OrgStructureInfo) params.getObject("org");
    	CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
    	
    	String curProjectStr=null;
    	if(curProject!=null){
    		try {
    			curProjectStr=SHEManageHelper.getStringFromSet(getCurProjectIdSet(ctx,curProject.getId()));
			} catch (BOSException e) {
				e.printStackTrace();
			} catch (EASBizException e) {
				e.printStackTrace();
			} 
    	}
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select FCycle cycle from T_FNC_ProjectMonthPlanGather ga left join T_FDC_PayPlanCycle cy on cy.fid=ga.FCycleId");
    	sb.append(" left join T_ORG_BaseUnit orgUnit on orgUnit.fid=ga.FOrgUnitID");
    	sb.append(" where ga.fstate='4AUDITTED' and ga.fisLatest=1");
    	sb.append(" and ga.fbizDate={ts '" + FDCConstants.FORMAT_TIME.format(bizDate)+ "'}");
    	if(bizDate!=null){
    		sb.append(" and ga.fbizDate={ts '" + FDCConstants.FORMAT_TIME.format(bizDate)+ "'}");
    	}
    	if(org!=null){
        	sb.append(" and orgUnit.flongNumber like '"+org.getLongNumber()+"%'");
    	}else if(curProjectStr!=null){
    		sb.append(" and ga.fcurProjectid in("+curProjectStr+")");
    	}else{
    		sb.append(" and ga.fcurProjectid in ('null')");
    	}
    	RptRowSet rowSet = executeQuery(sb.toString(), null, 0, -1, ctx);
    	int cycle=0;
    	while(rowSet.next()){
    		cycle=rowSet.getInt("cycle");
    	}
    	Calendar cal = Calendar.getInstance();
		cal.setTime(bizDate);
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		Object[] mheadOne=new Object[cycle*3+headLabels.length];
		Object[] mheadTwo=new Object[cycle*3+headLabels.length];
		
		for(int i=0;i<headLabels.length;i++){
			mheadOne[i]=headLabels[i];
			mheadTwo[i]=headLabels[i];
		}
    	for(int i=0;i<cycle;i++){
			if (month > 12) {
				year += 1;
				month = 1;
			}
			String monthStr= year + "年" + month + "月";
			String key=year+"year"+month+"m";
			
			initColoum(header,new RptTableColumn(),key+"planAmount",100,false);
			initColoum(header,new RptTableColumn(),key+"actAmount",100,false);
			initColoum(header,new RptTableColumn(),key+"payRate",100,false);
			
			int head=i*3;
			mheadOne[head+headLabels.length]=monthStr;
			mheadOne[head+headLabels.length+1]=monthStr;
			mheadOne[head+headLabels.length+2]=monthStr;
			
			mheadTwo[head+headLabels.length]="计划金额";
			mheadTwo[head+headLabels.length+1]="实际金额";
			mheadTwo[head+headLabels.length+2]="付款比例（%）";
			
			month++;
    	}
    	Object[][] labels=new Object[][]{mheadOne,mheadTwo};
    	return labels;
    }
}