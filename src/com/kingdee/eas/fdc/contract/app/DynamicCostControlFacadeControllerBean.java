package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;

import java.awt.Color;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.IORMappingDAO;
import com.kingdee.bos.dao.ormapping.ORMappingDAO;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.db.SQLUtils;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.ChangeTypeCollection;
import com.kingdee.eas.fdc.basedata.ChangeTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.DynamicCostControlPhoto;
import com.kingdee.eas.fdc.contract.DynamicCostControlPhotoCollection;
import com.kingdee.eas.fdc.contract.DynamicCostControlPhotoFactory;
import com.kingdee.eas.fdc.contract.DynamicCostControlPhotoInfo;
import com.kingdee.eas.fdc.contract.IDynamicCostControlPhoto;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.framework.report.app.CommRptBaseControllerBean;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableColumn;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.util.app.ContextUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicCostControlFacadeControllerBean extends AbstractDynamicCostControlFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.DynamicCostControlFacadeControllerBean");
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
	    initColoum(header,col,"id",100,true);
	    initColoum(header,col,"isLeaf",100,true);
	    initColoum(header,col,"levelNumber",100,true);
	    initColoum(header,col,"number",100,false);
	    initColoum(header,col,"name",250,false);
	    initColoum(header,col,"amount",100,false);
	    initColoum(header,col,"contractAmount",120,false);
	    initColoum(header,col,"supplyAmount",120,false);
	    initColoum(header,col,"contractWTAmount",120,false);
	    
	    StringBuffer sb = new StringBuffer();
		CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
		sb.append(" select t.id,t.name,t.number from (select ct.fid id,ct.fname_l2 name,ct.fnumber number from T_CON_ContractChangeBill cb left join T_FDC_ChangeType ct on ct.fid=cb.FChangeTypeID");  
		sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ProgrammingContract pc on pc.fid=contract.FProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID where 1=1");
		if(curProject!=null){
    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and pro.fprojectID = 'null'");
    	}
		sb.append(" group by ct.fid,ct.fname_l2,ct.fnumber) t order by t.number");
		RptRowSet rs = executeQuery(sb.toString(), null,ctx);
		List changeTypeCol=new ArrayList();
		
	    Object[] one=new Object[23+rs.getRowCount()*2];
	    Object[] two=new Object[23+rs.getRowCount()*2];
	    Object[] three=new Object[23+rs.getRowCount()*2];
	    
	    String balance="";
	    if(params.getObject("fromDate")!=null&&params.getObject("toDate")!=null){
	    	balance="差异(A-B)";
	    }
	    one[0]="id";
	    one[1]="isLeaf";
	    one[2]="levelNumber";
	    one[3]="合约规划编码";
	    one[4]="合约规划名称";
	    one[5]="规划金额(元)";
	    one[6]="合同/无文本合同(A)"+balance;
	    one[7]="合同/无文本合同(A)"+balance;
	    one[8]="合同/无文本合同(A)"+balance;
	    
	    two[0]="id";
	    two[1]="isLeaf";
	    two[2]="levelNumber";
	    two[3]="合约规划编码";
	    two[4]="合约规划名称";
	    two[5]="规划金额(元)";
	    two[6]="合同/无文本合同(A)"+balance;
	    two[7]="合同/无文本合同(A)"+balance;
	    two[8]="合同/无文本合同(A)"+balance;
	    
	    three[0]="id";
	    three[1]="isLeaf";
	    three[2]="levelNumber";
	    three[3]="合约规划编码";
	    three[4]="合约规划名称";
	    three[5]="规划金额(元)";
	    three[6]="已签合同"+balance;
	    three[7]="补充协议"+balance;
	    three[8]="无文本合同"+balance;
	    
	    int k=9;
	    while(rs.next()){
	    	String key=rs.getString("id");
	    	changeTypeCol.add(key);
	    	initColoum(header,col,key+"UNCONFIRM",160,false);
	    	initColoum(header,col,key+"CONFIRM",160,false);
	    	
	    	String str=rs.getString("name");
	    	
	    	one[k]="合同变动(B)"+balance;
			one[k+1]="合同变动(B)"+balance;
			
			two[k]=str+balance;
			two[k+1]=str+balance;
			
			three[k]=str+balance+"(未确认)";
			three[k+1]=str+balance+"(已确认)";
			
			k=k+2;
		}
	    initColoum(header,col,"unContractAmount",140,false);
    	initColoum(header,col,"estimateAmount",140,false);
    	initColoum(header,col,"settleAdjustAmount",140,false);
    	initColoum(header,col,"dynamicTotalAmount",160,false);
    	initColoum(header,col,"settleAmount",140,false);
    	if(!balance.equals("")){
    		initColoum(header,col,"changeRate",140,true);
    	}else{
    		initColoum(header,col,"changeRate",140,false);
    	}
    	initColoum(header,col,"happenedAmount",140,false);
    	if(!balance.equals("")){
    		initColoum(header,col,"absolute",100,true);
        	initColoum(header,col,"rate",100,true);
        	initColoum(header,col,"payAmount",140,true);
        	initColoum(header,col,"payRate",140,true);
    	}else{
    		initColoum(header,col,"absolute",100,false);
        	initColoum(header,col,"rate",100,false);
        	initColoum(header,col,"payAmount",140,false);
        	initColoum(header,col,"payRate",140,false);
    	}
    	initColoum(header,col,"totalAmount",140,true);
    	initColoum(header,col,"isContract",140,true);
    	initColoum(header,col,"isSettle",140,true);
    	
	    one[k]="未签合同(C)"+balance;
		one[k+1]="预估合同变动(D)"+balance;
		one[k+2]="结算调整(E)"+balance;
		one[k+3]="动态成本总额(A+B+C+D+E)"+balance;
		one[k+4]="合同结算金额"+balance;
		one[k+5]="变更签证比例"+balance;
		one[k+6]="已发生金额"+balance;
		one[k+7]="变化值";
		one[k+8]="变化值";
		one[k+9]="累计实付金额";
		one[k+10]="累计实付比例(%)";
		one[k+11]="totalAmount";
		one[k+12]="isContract";
		one[k+13]="isSettle";
		
		two[k]="未签合同(C)"+balance;
		two[k+1]="预估合同变动(D)"+balance;
		two[k+2]="结算调整(E)"+balance;
		two[k+3]="动态成本总额(A+B+C+D+E)"+balance;
		two[k+4]="合同结算金额"+balance;
		two[k+5]="变更签证比例"+balance;
		two[k+6]="已发生金额"+balance;
		two[k+7]="变化值";
		two[k+8]="变化值";
		two[k+9]="累计实付金额";
		two[k+10]="累计实付比例(%)";
		two[k+11]="totalAmount";
		two[k+12]="isContract";
		two[k+13]="isSettle";
		
		three[k]="未签合同(C)"+balance;
		three[k+1]="预估合同变动(D)"+balance;
		three[k+2]="结算调整(E)"+balance;
		three[k+3]="动态成本总额(A+B+C+D+E)"+balance;
		three[k+4]="合同结算金额"+balance;
		three[k+5]="变更签证比例"+balance;
		three[k+6]="已发生金额"+balance;
		three[k+7]="绝对值";
		three[k+8]="比例(%)";
		three[k+9]="累计实付金额";
		three[k+10]="累计实付比例(%)";
		three[k+11]="totalAmount";
		three[k+12]="isContract";
		three[k+13]="isSettle";
		
		header.setLabels(new Object[][]{one,two,three},true);
	    params.setObject("header", header);
	    params.setObject("changeTypeCol", changeTypeCol);
	    return params;
	}
    protected RptParams _query(Context ctx, RptParams params, int from, int len) throws BOSException, EASBizException{
    	if(params.getObject("fromDate")!=null&&params.getObject("toDate")!=null){
    		params.setObject("auditDate", params.getObject("fromDate"));
    		RptRowSet fromRowset = executeQuery(getSql(ctx,params), null, from, len, ctx);
    		params.setObject("fromRowset", fromRowset);
    		params.setObject("fromChangeMap", getChangeMap(ctx,params));
    		
    		params.setObject("auditDate", params.getObject("toDate"));
    		RptRowSet toRowset = executeQuery(getSql(ctx,params), null, from, len, ctx);
    		params.setObject("toRowset", toRowset);
    		params.setObject("toChangeMap", getChangeMap(ctx,params));
    	}else{
    		RptRowSet rowSet = executeQuery(getSql(ctx,params), null, from, len, ctx);
    		params.setObject("rowset", rowSet);
    		params.setObject("changeMap", getChangeMap(ctx,params));
    	}
		return params;
    }
    protected Map getChangeMap(Context ctx,RptParams params) throws BOSException{
    	Map changeMap=new HashMap();
		StringBuffer sb = new StringBuffer();
		CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
		String longNumber = (String) params.getObject("longNumber");
    	Date auditDate=(Date)params.getObject("auditDate");
		Boolean isPhoto = (Boolean) params.getObject("isPhoto");
		sb.append(" select tt.ctNumber,tt.ctId,tt.pcId,sum(tt.CONFIRM) CONFIRM,sum(tt.UNCONFIRM) UNCONFIRM from(");
		if(auditDate!=null){
			sb.append(" select changeType.fnumber ctNumber,cb.FChangeTypeID ctId,contract.FProgrammingContract pcId,sum(changeSettle.fallowAmount*cb.fexRate) CONFIRM,0 UNCONFIRM from T_CON_ContractChangeBill cb");  
			sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ProgrammingContract pc on pc.fid=contract.FProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
			sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=cb.FChangeTypeID where cb.fstate='8VISA'");
			if(curProject!=null){
	    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
	    	}else{
	    		sb.append(" and pro.fprojectID = 'null'");
	    	}
			if(auditDate!=null){
	    		sb.append(" and changeSettle.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
	    	}
			if(longNumber!=null){
	    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
	    	}
			sb.append(" group by changeType.fnumber,cb.FChangeTypeID,contract.FProgrammingContract");
			
			sb.append(" union all select changeType.fnumber ctNumber,cb.FChangeTypeID ctId,contract.FProgrammingContract pcId,0 CONFIRM,sum(cb.famount) UNCONFIRM from T_CON_ContractChangeBill cb");  
			sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ProgrammingContract pc on pc.fid=contract.FProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
			sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=cb.FChangeTypeID where cb.fstate in('4AUDITTED','7ANNOUNCE')");
			if(curProject!=null){
	    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
	    	}else{
	    		sb.append(" and pro.fprojectID = 'null'");
	    	}
			if(auditDate!=null){
				sb.append(" and cb.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
//				sb.append(" or (changeSettle.fauditTime>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'} or changeSettle.fauditTime is null)");
	    	}
			if(longNumber!=null){
	    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
	    	}
			sb.append(" group by changeType.fnumber,cb.FChangeTypeID,contract.FProgrammingContract");
			
			sb.append(" union all select changeType.fnumber ctNumber,cb.FChangeTypeID ctId,contract.FProgrammingContract pcId,0 CONFIRM,sum(cb.famount) UNCONFIRM from T_CON_ContractChangeBill cb");  
			sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ProgrammingContract pc on pc.fid=contract.FProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
			sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=cb.FChangeTypeID where cb.fstate in('8VISA')");
			if(curProject!=null){
	    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
	    	}else{
	    		sb.append(" and pro.fprojectID = 'null'");
	    	}
			if(auditDate!=null){
//				sb.append(" and cb.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
				sb.append(" and (changeSettle.fauditTime>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'} or changeSettle.fauditTime is null)");
	    	}
			if(longNumber!=null){
	    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
	    	}
			sb.append(" group by changeType.fnumber,cb.FChangeTypeID,contract.FProgrammingContract");
		}else{
			sb.append(" select changeType.fnumber ctNumber,cb.FChangeTypeID ctId,contract.FProgrammingContract pcId,sum(case when cb.fstate='8VISA' then (changeSettle.fallowAmount*cb.fexRate) else 0 end) CONFIRM,sum(case when cb.fstate in('4AUDITTED','7ANNOUNCE') then cb.famount else 0 end) UNCONFIRM from T_CON_ContractChangeBill cb");  
			sb.append(" left join t_con_contractBill contract on contract.fid=cb.FContractBillID left join T_CON_ProgrammingContract pc on pc.fid=contract.FProgrammingContract left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.FConChangeBillID=cb.fid");
			sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=cb.FChangeTypeID where 1=1");
			if(curProject!=null){
	    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
	    	}else{
	    		sb.append(" and pro.fprojectID = 'null'");
	    	}
			if(longNumber!=null){
	    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
	    	}
			sb.append(" group by changeType.fnumber,cb.FChangeTypeID,contract.FProgrammingContract");
		}
		
		sb.append(" union all select t.ctNumber,t.ctId,t.pcId,sum(t.CONFIRM)CONFIRM,sum(t.UNCONFIRM)UNCONFIRM from (select changeType.fnumber ctNumber,cb.FChangeTypeID ctId,entry.fProgrammingContractId pcId,sum(entry.famount) CONFIRM,0 UNCONFIRM from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.fid=split.fContractChangeSettleBillId left join T_CON_ContractChangeBill cb on changeSettle.FConChangeBillID=cb.fid");
		sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=cb.FChangeTypeID where split.fcontractChangeSettleBillId is not null");
		if(curProject!=null){
    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and pro.fprojectID = 'null'");
    	}
		if(auditDate!=null){
    		sb.append(" and changeSettle.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
		if(longNumber!=null){
    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
    	}
		sb.append(" group by changeType.fnumber,cb.FChangeTypeID,entry.fProgrammingContractId");
		
		sb.append(" union all select changeType.fnumber ctNumber,cb.FChangeTypeID ctId,entry.fProgrammingContractId pcId,0 CONFIRM,sum(entry.famount) UNCONFIRM from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ProgrammingContract pc on pc.fid=entry.fprogrammingContractId left join T_CON_Programming pro on pro.fid=pc.FProgrammingID left join T_CON_ContractChangeBill cb on cb.fid=split.fcontractChangeBillId");
		sb.append(" left join (select entry.fProgrammingContractId pcId,changeSettle.FConChangeBillID cbId,changeSettle.fauditTime auditTime from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId");  
		sb.append(" left join T_CON_ContractChangeSettleBill changeSettle on changeSettle.fid=split.fContractChangeSettleBillId where split.fcontractChangeSettleBillId is not null  ) confirmSplit on confirmSplit.cbId=cb.fId and confirmSplit.pcId=pc.fId");
		sb.append(" left join T_FDC_ChangeType changeType on changeType.fid=cb.FChangeTypeID where split.fcontractChangeBillId is not null");
		if(curProject!=null){
    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and pro.fprojectID = 'null'");
    	}
		if(auditDate!=null){
    		sb.append(" and cb.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    		sb.append(" or (confirmSplit.auditTime>={ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'} or confirmSplit.cbId is null)");
    	}else{
    		sb.append(" and confirmSplit.cbId is null");
    	}
		if(longNumber!=null){
    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
    	}
		sb.append(" group by changeType.fnumber,cb.FChangeTypeID,entry.fProgrammingContractId) t group by t.ctNumber,t.ctId,t.pcId)tt group by tt.ctNumber,tt.ctId,tt.pcId");
		RptRowSet changeRowSet = executeQuery(sb.toString(), null,ctx);
		while(changeRowSet.next()){
			String ctId=changeRowSet.getString("ctId");
			if(isPhoto!=null){
				ctId=changeRowSet.getString("ctNumber");
			}
			String pcId=changeRowSet.getString("pcId");
			BigDecimal CONFIRM=changeRowSet.getBigDecimal("CONFIRM");
			BigDecimal UNCONFIRM=changeRowSet.getBigDecimal("UNCONFIRM");
			
			Map changeAmount=new HashMap();
			changeAmount.put("CONFIRM", CONFIRM);
			changeAmount.put("UNCONFIRM", UNCONFIRM);
			changeMap.put(pcId+ctId, changeAmount);
		}
		return changeMap;
    }
    protected String getSql(Context ctx,RptParams params){
    	String[] contractType=null;
    	HashMap hmParamIn = new HashMap();
    	hmParamIn.put("CIFI_PCCONTRACTTYPE", null);
		try {
			HashMap hmAllParam = ParamControlFactory.getLocalInstance(ctx).getParamHashMap(hmParamIn);
			if(hmAllParam.get("CIFI_PCCONTRACTTYPE")!=null&&!"".equals(hmAllParam.get("CIFI_PCCONTRACTTYPE"))){
				contractType=hmAllParam.get("CIFI_PCCONTRACTTYPE").toString().split(";");
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
    	CurProjectInfo curProject = (CurProjectInfo) params.getObject("curProject");
    	String longNumber = (String) params.getObject("longNumber");
    	Date auditDate=(Date)params.getObject("auditDate");
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select pc.fid id,(case when isLeaf.fparentid is not null then 0 else 1 end) isLeaf,pc.flevel levelNumber,pc.FLongNumber number,pc.fname_l2 name,pc.famount amount,");
    	sb.append(" (isnull(contract.famount,0)-isnull(supply.famount,0)) contractAmount,supply.famount supplyAmount,contractWT.famount contractWTAmount,estimate.famount estimateAmount,settle.famount settleAmount,pay.payAmount payAmount,contract.isContract,settle.isSettle,contractWT.isContractWT,contractType.fid contractTypeId,contractType.fnumber contractTypeNumber,contractType.fname_l2 contractTypeName");
    	sb.append(" from T_CON_ProgrammingContract pc left join T_CON_Programming pro on pro.fid=pc.FProgrammingID");
    	sb.append(" left join (select 'true' isContract,t.FProgrammingContract,sum(t.famount) famount from(select famount,fProgrammingContract from t_con_contractBill where fContractPropert!='SUPPLY' and fstate='4AUDITTED'");
    	if(auditDate!=null){
    		sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=split.fcontractBillId where split.fcontractBillId is not null");
    	if(auditDate!=null){
    		sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" group by entry.fProgrammingContractId)t group by t.FProgrammingContract) contract on contract.fProgrammingContract=pc.fid");
    	sb.append(" left join (select t.FProgrammingContract,sum(t.famount) famount from(select sum(famount) famount,fProgrammingContract from t_con_contractBill where fContractPropert='SUPPLY' and fstate='4AUDITTED'");
    	if(auditDate!=null){
    		sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" group by fProgrammingContract");
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join t_con_contractBill contract on contract.fid=split.fcontractBillId where contract.fContractPropert='SUPPLY' and split.fcontractBillId is not null");
    	if(auditDate!=null){
    		sb.append(" and contract.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" group by entry.fProgrammingContractId)t group by t.FProgrammingContract) supply on supply.fProgrammingContract=pc.fid");
    	sb.append(" left join (select 'true' isContractWT,sum(case when pay.contractbillId is null then cwt.famount else pay.payAmount end) famount,cwt.fProgrammingContract from T_CON_ContractWithoutText cwt left join (select sum(flocalAmount) payAmount,fcontractbillId contractbillId from t_cas_paymentBill where fbillstatus=15 group by fcontractbillId) pay on pay.contractbillId=cwt.fid where cwt.fstate='4AUDITTED'");
    	if(auditDate!=null){
    		sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" group by fProgrammingContract) contractWT on contractWT.fProgrammingContract=pc.fid");
    	sb.append(" left join (select 'true' isSettle,t.FProgrammingContract,sum(t.famount) famount from(select sum(settlement.fcurSettlePrice) famount,contract.fProgrammingContract from T_CON_ContractSettlementBill settlement left join t_con_contractBill contract on contract.fid=settlement.FContractBillID where settlement.fstate='4AUDITTED'");
    	if(auditDate!=null){
    		sb.append(" and settlement.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" group by contract.fProgrammingContract");
    	sb.append(" union all select sum(entry.famount) famount,entry.fProgrammingContractId fProgrammingContract from T_CON_ContractPCSplitBillEntry entry left join T_CON_ContractPCSplitBill split on split.fid=entry.fheadId left join T_CON_ContractSettlementBill settlement on settlement.fid=split.fcontractSettleBillId where split.fcontractSettleBillId is not null");
    	if(auditDate!=null){
    		sb.append(" and settlement.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" group by entry.fProgrammingContractId");
    	sb.append(" )t group by t.FProgrammingContract)settle on settle.fProgrammingContract=pc.fid");
    	if(auditDate!=null){
    		sb.append(" left join (select festimateAmount famount,ec.fprogrammingContractID fProgrammingContract from T_CON_ContractEstimateChange ec");
    		sb.append(" left join (select max(fauditTime) fauditTime,fprogrammingContractID from T_CON_ContractEstimateChange where fstate='4AUDITTED' and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'} group by fprogrammingContractID) t on t.fprogrammingContractID=ec.fprogrammingContractID and t.fauditTime=ec.fauditTime");
    		sb.append(" where t.fauditTime is not null and fstate='4AUDITTED' and ec.fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}else{
    		sb.append(" left join (select festimateAmount famount,fprogrammingContractID fProgrammingContract from T_CON_ContractEstimateChange where fstate='4AUDITTED' and fisLastest=1");
    		if(auditDate!=null){
    			sb.append(" and fauditTime<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    		}
    	}
    	sb.append(" )estimate on estimate.fProgrammingContract=pc.fid");
    	sb.append(" left join (select t.FProgrammingContract,sum(t.payAmount) payAmount from(select bill.FProgrammingContract,pay.FAmount payAmount from t_cas_paymentbill pay left join t_con_contractWithoutText bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15");
    	if(auditDate!=null){
    		sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" union all select bill.FProgrammingContract,pay.FAmount payAmount from t_cas_paymentbill pay left join t_con_contractbill bill on bill.fid=pay.fcontractbillid where pay.fbillstatus=15");
    	if(auditDate!=null){
    		sb.append(" and pay.fpayDate<{ts '"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(auditDate))+ "'}");
    	}
    	sb.append(" ) t group by t.FProgrammingContract) pay on pay.fProgrammingContract=pc.fid");
    	sb.append(" left join (select distinct fparentid from T_CON_ProgrammingContract) isLeaf on isLeaf.fparentid=pc.fid");
    	sb.append(" left join T_FDC_ContractType contractType on contractType.fid=pc.fcontractTypeId");
    	sb.append(" where pro.FIsLatest=1 and pro.fstate='4AUDITTED'");
    	if(contractType!=null&&contractType.length>0){
    		sb.append(" and (contractType.fisCost is null");
    		for(int i=0;i<contractType.length;i++){
    			sb.append(" or contractType.flongNumber like '"+contractType[i]+"%'");
    		}
    		sb.append(" )");
    	}
    	if(curProject!=null){
    		sb.append(" and pro.fprojectID ='"+curProject.getId().toString()+"'");
    	}else{
    		sb.append(" and pro.fprojectID = 'null'");
    	}
    	if(longNumber!=null){
    		sb.append(" and pc.FLongNumber like '"+longNumber+"%'");
    	}
		sb.append(" order by pc.flongNumber");
    	return sb.toString();
    }
	protected void _photo(Context ctx) throws BOSException, EASBizException {
		CurProjectCollection curProjectCol=CurProjectFactory.getLocalInstance(ctx).getCurProjectCollection("select id,codingNumber,name from where isLeaf=1");
		IDynamicCostControlPhoto DynamicCostControlPhoto=DynamicCostControlPhotoFactory.getLocalInstance(ctx);
        Date now=new Date();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("photoDate",FDCDateHelper.getSQLBegin(now),CompareType.GREATER));
		filter.getFilterItems().add(new FilterItemInfo("photoDate",FDCDateHelper.getSQLEnd(now),CompareType.LESS));
		DynamicCostControlPhoto.delete(filter);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("balance");
		for(int p=0;p<curProjectCol.size();p++){
			RptParams params=new RptParams();
			params.setObject("curProject", curProjectCol.get(p));
			params.setObject("isPhoto", Boolean.TRUE);
			this.query(ctx, params);
			
			RptRowSet rs = (RptRowSet)params.getObject("rowset");
	        Map sumMap=new HashMap();
	        Map changeMap=(HashMap)params.getObject("changeMap");
	        DynamicCostControlPhotoCollection col=new DynamicCostControlPhotoCollection();
	        while(rs.next()){
	        	 String curProjectId=curProjectCol.get(p).getId().toString();
	        	 String curProjectNumber=curProjectCol.get(p).getCodingNumber();
	        	 String curProjectName=curProjectCol.get(p).getName();
	        	 String id=rs.getString("id");
	        	 String number=rs.getString("number");
	        	 String name=rs.getString("name");
	        	 int isLeaf=rs.getInt("isLeaf");
	        	 int level=rs.getInt("levelNumber")-1;
	        	 BigDecimal amount=FDCHelper.ZERO;
 	        	 if(isLeaf==1){
 	        		amount=rs.getBigDecimal("amount")==null?FDCHelper.ZERO:rs.getBigDecimal("amount");
	        	 }
	        	 BigDecimal contractAmount=rs.getBigDecimal("contractAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("contractAmount");
	        	 BigDecimal supplyAmount=rs.getBigDecimal("supplyAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("supplyAmount");
	        	 BigDecimal contractWTAmount=rs.getBigDecimal("contractWTAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("contractWTAmount");
	        	 BigDecimal totalCONFIRM=FDCHelper.ZERO;
	        	 BigDecimal totalUNCONFIRM=FDCHelper.ZERO;
	        	 BigDecimal estimateAmount=rs.getBigDecimal("estimateAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("estimateAmount");
	        	 BigDecimal settleAmount=rs.getBigDecimal("settleAmount")==null?FDCHelper.ZERO:rs.getBigDecimal("settleAmount");
	        	 BigDecimal unContractAmount=FDCHelper.ZERO;
	        	 BigDecimal dynamicTotalAmount=FDCHelper.ZERO;
	        	 BigDecimal happenedAmount=FDCHelper.ZERO;
	        	 Boolean isContract=rs.getBoolean("isContract", false);
 	       	 	 Boolean isSettle=rs.getBoolean("isSettle",false);
 	       	     if(isSettle)estimateAmount=FDCHelper.ZERO;
	        	 DynamicCostControlPhotoInfo info=new DynamicCostControlPhotoInfo();
	        	
	        	 info.setCurProjectId(BOSUuid.read(curProjectId));
	        	 info.setCurProjectNumber(curProjectNumber);
	        	 info.setCurProjectName(curProjectName);
	        	 info.setPcId(BOSUuid.read(id));
	        	 info.setPcNumber(number);
	        	 info.setPcName(name);
	        	 info.setLevelNumber(level);
	        	 if(isLeaf==1){
	        		 info.setIsLeaf(true);
	        	 }else{
	        		 info.setIsLeaf(false);
	        	 }
	        	 info.setAmount(amount);
	        	 info.setContractAmount(contractAmount);
	        	 info.setSupplyAmount(supplyAmount);
	        	 info.setContractWTAmount(contractWTAmount);
	        	 
		       	 for(int i=1;i<=4;i++){
		       		 String key="00"+i;
		       		 String mapKey=id+key;
		       		 BigDecimal CONFIRM=FDCHelper.ZERO;
		       		 BigDecimal UNCONFIRM=FDCHelper.ZERO;
		       		 if(changeMap.containsKey(mapKey)){
		       			 Map changeAmount=(HashMap)changeMap.get(mapKey);
		       			 CONFIRM=changeAmount.get("CONFIRM")==null?FDCHelper.ZERO:(BigDecimal)changeAmount.get("CONFIRM");
		       			 UNCONFIRM=changeAmount.get("UNCONFIRM")==null?FDCHelper.ZERO:(BigDecimal)changeAmount.get("UNCONFIRM");
		       		 }
		       		 totalCONFIRM=totalCONFIRM.add(CONFIRM);
		   			 totalUNCONFIRM=totalUNCONFIRM.add(UNCONFIRM);
		   			 
		   			 if(key.equals("001")){
		   				 info.setOneCONFIRM(CONFIRM);
		   				 info.setOneUNCONFIRM(UNCONFIRM);
		   			 }else if(key.equals("002")){
		   				 info.setTwoCONFIRM(CONFIRM);
		   				 info.setTwoUNCONFIRM(UNCONFIRM);
		   			 }else if(key.equals("003")){
		   				 info.setThreeCONFIRM(CONFIRM);
		   				 info.setThreeUNCONFIRM(UNCONFIRM);
		   			 }else if(key.equals("004")){
		   				 info.setFourCONFIRM(CONFIRM);
		   				 info.setFourUNCONFIRM(UNCONFIRM);
		   			 }
		       	 }
		       	 info.setEstimateAmount(estimateAmount);
		       	 info.setSettleAmount(settleAmount);
		       	 
		       	 if(isLeaf==1){
		       		if(isContract){
		       			 unContractAmount=FDCHelper.ZERO;
		        	 }else{
		        		 unContractAmount=amount.subtract(contractWTAmount);
		        	 }
		       		 if(isSettle){
		       			 dynamicTotalAmount=settleAmount.add(contractWTAmount);
		       			 happenedAmount=settleAmount.add(contractWTAmount);
		       		 }else{
		       			if(isContract){
	           				dynamicTotalAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM).add(estimateAmount);
	           			 }else{
	           				dynamicTotalAmount=amount;
	           			 }
		       			 happenedAmount=contractAmount.add(supplyAmount).add(contractWTAmount).add(totalCONFIRM).add(totalUNCONFIRM);
		       		 }
		       		 info.setUnContractAmount(unContractAmount);
		       		 info.setDynamicTotalAmount(dynamicTotalAmount);
		       		 info.setHappenedAmount(happenedAmount);
		       		 if(contractAmount.add(supplyAmount).compareTo(FDCHelper.ZERO)==0){
		        		 info.setChangeRate(FDCHelper.ZERO);
		        	 }else{
		        		 info.setChangeRate((totalCONFIRM.add(totalUNCONFIRM)).divide(contractAmount.add(supplyAmount), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        	 }
		       		 info.setAbsolute(amount.subtract(dynamicTotalAmount));
		       		 
		        	 if(amount.compareTo(FDCHelper.ZERO)==0){
		        		 info.setRate(FDCHelper.ZERO);
		        	 }else{
		        		 info.setRate((amount.subtract(dynamicTotalAmount)).divide(amount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        	 }
		       	 }
		       	 sumMap.put(number, info);
	       	 
			   	 if(number.indexOf(".")>0){
			   		 String pnumber=number.substring(0, number.lastIndexOf("."));
			   		 for(int k=0;k<level;k++){
			   			 if(sumMap.get(pnumber)!=null){
			   				 DynamicCostControlPhotoInfo pInfo=(DynamicCostControlPhotoInfo) sumMap.get(pnumber);
			   				 pInfo.setAmount(FDCHelper.add(pInfo.getAmount(),amount));
			   				 pInfo.setContractAmount(FDCHelper.add(pInfo.getContractAmount(), contractAmount));
			   				 pInfo.setSupplyAmount(FDCHelper.add(pInfo.getSupplyAmount(), supplyAmount));
			   				 pInfo.setContractWTAmount(FDCHelper.add(pInfo.getContractWTAmount(), contractWTAmount));
			   				 
			   				 BigDecimal sumTotalCONFIRM=FDCHelper.ZERO;
		        			 BigDecimal sumTotalUNCONFIRM=FDCHelper.ZERO;
		        			 
			   				 pInfo.setOneCONFIRM(FDCHelper.add(pInfo.getOneCONFIRM(), info.getOneCONFIRM()));
			   				 pInfo.setOneUNCONFIRM(FDCHelper.add(pInfo.getOneUNCONFIRM(), info.getOneUNCONFIRM()));
			   				 pInfo.setTwoCONFIRM(FDCHelper.add(pInfo.getTwoCONFIRM(), info.getTwoCONFIRM()));
			   				 pInfo.setTwoUNCONFIRM(FDCHelper.add(pInfo.getTwoUNCONFIRM(), info.getTwoUNCONFIRM()));
			   				 pInfo.setThreeCONFIRM(FDCHelper.add(pInfo.getThreeCONFIRM(), info.getThreeCONFIRM()));
			   				 pInfo.setThreeUNCONFIRM(FDCHelper.add(pInfo.getThreeUNCONFIRM(), info.getThreeUNCONFIRM()));
			   				 pInfo.setFourCONFIRM(FDCHelper.add(pInfo.getFourCONFIRM(), info.getFourCONFIRM()));
			   				 pInfo.setFourUNCONFIRM(FDCHelper.add(pInfo.getFourUNCONFIRM(), info.getFourUNCONFIRM()));
			   				
			   				 sumTotalCONFIRM=pInfo.getOneCONFIRM().add(pInfo.getTwoCONFIRM()).add(pInfo.getThreeCONFIRM()).add(pInfo.getFourCONFIRM());
			   				 sumTotalUNCONFIRM=pInfo.getOneUNCONFIRM().add(pInfo.getTwoUNCONFIRM()).add(pInfo.getThreeUNCONFIRM()).add(pInfo.getFourUNCONFIRM());
				   			 
			   				 pInfo.setEstimateAmount(FDCHelper.add(pInfo.getEstimateAmount(), estimateAmount));
			   				 pInfo.setSettleAmount(FDCHelper.add(pInfo.getSettleAmount(), settleAmount));
			   				 pInfo.setUnContractAmount(FDCHelper.add(pInfo.getUnContractAmount(), unContractAmount));
			   				 pInfo.setDynamicTotalAmount(FDCHelper.add(pInfo.getDynamicTotalAmount(), dynamicTotalAmount));
			   				 pInfo.setHappenedAmount(FDCHelper.add(pInfo.getHappenedAmount(), happenedAmount));
			   				 
		        			 BigDecimal sumAmount=pInfo.getAmount();
		        			 BigDecimal sumDynamicTotalAmount=pInfo.getDynamicTotalAmount();
			        		
		        			 pInfo.setAbsolute(FDCHelper.subtract(sumAmount, sumDynamicTotalAmount));
		        			 if(sumAmount.compareTo(FDCHelper.ZERO)==0){
		        				 pInfo.setRate(FDCHelper.ZERO);
		        			 }else{
		        				 pInfo.setRate((sumAmount.subtract(sumDynamicTotalAmount)).divide(sumAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        			 }
		        			 if(FDCHelper.add(pInfo.getContractAmount(), pInfo.getSupplyAmount()).compareTo(FDCHelper.ZERO)==0){
		        				 pInfo.setChangeRate(FDCHelper.ZERO);
		        			 }else{
		        				 pInfo.setChangeRate((sumTotalCONFIRM.add(sumTotalUNCONFIRM)).divide(FDCHelper.add(pInfo.getContractAmount(), pInfo.getSupplyAmount()), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100)));
		        			 }
		        		 }
		        		 if(pnumber.indexOf(".")>0){
		        			 pnumber=pnumber.substring(0, pnumber.lastIndexOf("."));
		        		 }
		        	 }
		    	 }
			   	 col.add(info);
         	}
	        int seq=0;
	        for(int i=0;i<col.size();i++){
	        	DynamicCostControlPhotoInfo info=col.get(i);
	        	boolean isDelete=true;
				String number=info.getPcNumber();
				boolean isLeaf=info.isIsLeaf();
				if(seq==0){
					info.setPcName("四项成本");
 	        	}
				if(isLeaf){
					info.setSeq(seq);
					info.setPhotoDate(now);
					DynamicCostControlPhoto.addnew(info);
					
					seq=seq+1;
					
//					ProgrammingContractInfo pcInfo=ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(info.getPcId()),sel);
//		       		BigDecimal balance=FDCHelper.subtract(info.getAmount(), info.getHappenedAmount());
//					if(!(pcInfo.getBalance()!=null&&balance!=null&&pcInfo.getBalance().compareTo(balance)==0)){
//		       			pcInfo.setBalance(balance);
//		       			ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(pcInfo, sel);
//		       		}
				}else{
					for(int j=i+1;j<col.size();j++){
						DynamicCostControlPhotoInfo ninfo=col.get(j);
						String nnumber=ninfo.getPcNumber();
						boolean nisLeaf=ninfo.isIsLeaf();
						if(nisLeaf){
							if(nnumber.indexOf(number)==0){
								isDelete=false;
							}
							break;
						}
					}
					if(!isDelete){
						info.setSeq(seq);
						info.setPhotoDate(now);
						DynamicCostControlPhoto.addnew(info);
						seq=seq+1;
						
//						ProgrammingContractInfo pcInfo=ProgrammingContractFactory.getLocalInstance(ctx).getProgrammingContractInfo(new ObjectUuidPK(info.getPcId()),sel);
//						BigDecimal balance=FDCHelper.subtract(info.getAmount(), info.getHappenedAmount());
//						if(!(pcInfo.getBalance()!=null&&balance!=null&&pcInfo.getBalance().compareTo(balance)==0)){
//			       			pcInfo.setBalance(balance);
//			       			ProgrammingContractFactory.getLocalInstance(ctx).updatePartial(pcInfo, sel);
//			       		}
					}
				}
			}
		}
	}
}