package com.kingdee.eas.fdc.sellhouse.report;

import org.apache.log4j.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.kingdee.bos.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.market.ChannelTypeInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.RptPaperAnswerConstant;
import com.kingdee.eas.fdc.market.app.RptQuestionPaperFacadeControllerBean;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;

public class QuestionPaperReportFacadeControllerBean extends AbstractQuestionPaperReportFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.report.QuestionPaperReportFacadeControllerBean");
    private Date dateFrom;
	private Date dateTo;
	private boolean isShowAll;
	private Set sellProjectIdSet;
	private QuestionPaperDefineInfo paper;
	private DocumentSubjectInfo question1;
	private DocumentSubjectInfo question2;
	private Object[] workArea;
	private Object[] statyArea;
	private Object[] intentionType;
	private Object[] classify;
	private String commerceChanceStage=null;
	private boolean isAll;
	protected SchemaSource readySchemaSource(RptParams params, Context ctx)
			throws BOSException, EASBizException {
		initParam(params);
		
		SchemaSource ss = new SchemaSource();
		ss.setDataItem("Fact", getFactSql(ctx, params), null);
		String[] name = this.getDimensionNames();
		String[] sqls = this.getDimensionSqls();
		for (int i = 0; i < name.length; i++) {
			ss.setDataItem(name[i], sqls[i], null);
		}
		ss.setMdx(getMDX(ctx, params));
		ss.setFilename(getConfigXML());
		ss.setCaller(RptQuestionPaperFacadeControllerBean.class);
		return ss;
	}
	private void initParam(RptParams params) {
		dateFrom = (Date) params.getObject(RptPaperAnswerConstant.DATE_FROM);
		dateTo = (Date) params.getObject(RptPaperAnswerConstant.DATE_TO);
		
		isShowAll = params.getBoolean(RptPaperAnswerConstant.IS_SHOW_ALL);
		
		sellProjectIdSet = (Set) params.getObject(RptPaperAnswerConstant.SELL_PROJECT_ID_SET);
		
		paper = (QuestionPaperDefineInfo) params.getObject(RptPaperAnswerConstant.PAPER);
		question1 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		question2 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		
		workArea=(Object[]) params.getObject(RptPaperAnswerConstant.WORKAREA);
		statyArea=(Object[]) params.getObject(RptPaperAnswerConstant.STAYAREA);
		intentionType=(Object[]) params.getObject(RptPaperAnswerConstant.INTENTIONTYPE);
		classify=(Object[]) params.getObject(RptPaperAnswerConstant.CLASSIFY);
		Object[] value=(Object[])params.getObject(RptPaperAnswerConstant.COMMERCECHANCESTAGE);
		if(value!=null){
			StringBuffer id=new StringBuffer();
	    	for(int i=0;i<value.length;i++){
	        	if(i==0){
	        		id.append("'"+((CommerceChanceAssistantInfo)value[i]).getId().toString()+"'");
	        	}else{
	        		id.append(",'"+((CommerceChanceAssistantInfo)value[i]).getId().toString()+"'");
	        	}
	        }
	    	commerceChanceStage=id.toString();
		}
		isAll=params.getBoolean("cbIsAll");
		
	}


	private String getConfigXML() {
		if(question1!=null&&question2==null&&workArea==null&&statyArea==null&&intentionType==null&&classify==null){
			return "RptQuestionPaper_One.xml";
		}else if(question2!=null&&question1==null&&workArea==null&&statyArea==null&&intentionType==null&&classify==null){
			return "RptQuestionPaper_One.xml";
		}else if(workArea!=null&&question1==null&&question2==null&&statyArea==null&&intentionType==null&&classify==null){
			return "RptQuestionPaper_One.xml";
		}else if(statyArea!=null&&question1==null&&question2==null&&workArea==null&&intentionType==null&&classify==null){
			return "RptQuestionPaper_One.xml";
		}else if(intentionType!=null&&question1==null&&question2==null&&workArea==null&&statyArea==null&&classify==null){
			return "RptQuestionPaper_One.xml";
		}else if(classify!=null&&question1==null&&question2==null&&workArea==null&&statyArea==null&&intentionType==null){
			return "RptQuestionPaper_One.xml";
		}else{
			return "RptQuestionPaper_Two.xml";
		}
	}


	private String getMDX(Context ctx, RptParams params) {
		if(question1!=null&&question2==null&&workArea==null&&statyArea==null&&intentionType==null&&classify==null){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else if(question2!=null&&question1==null&&workArea==null&&statyArea==null&&intentionType==null&&classify==null){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else if(workArea!=null&&question1==null&&question2==null&&statyArea==null&&intentionType==null&&classify==null){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else if(statyArea!=null&&question1==null&&question2==null&&workArea==null&&intentionType==null&&classify==null){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else if(intentionType!=null&&question1==null&&question2==null&&workArea==null&&statyArea==null&&classify==null){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else if(classify!=null&&question1==null&&question2==null&&workArea==null&&statyArea==null&&intentionType==null){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else{
			return "select  {[Question2].members} on columns, {[Question1].members} on rows from Fact";
		}
	}


	private String[] getDimensionSqls() {
		if(question1!=null&&question2!=null){
			return new String[] { getDemensionSql(question1),getDemensionSql(question2) };
		}else if(question1!=null&&workArea!=null){
			return new String[] { getDemensionSql(question1),getSHECusAssistantDataSql(workArea) };
		}else if(question1!=null&&statyArea!=null){
			return new String[] { getDemensionSql(question1),getSHECusAssistantDataSql(statyArea) };
		}else if(question1!=null&&intentionType!=null){
			return new String[] { getDemensionSql(question1),getIntentionTypeSql(intentionType) };
		}else if(question1!=null&&classify!=null){
			return new String[] { getDemensionSql(question1),getClassifySql(classify) };
		}else if(question2!=null&&workArea!=null){
			return new String[] { getDemensionSql(question2),getSHECusAssistantDataSql(workArea) };
		}else if(question2!=null&&statyArea!=null){
			return new String[] { getDemensionSql(question2),getSHECusAssistantDataSql(statyArea) };
		}else if(question2!=null&&intentionType!=null){
			return new String[] { getDemensionSql(question2),getIntentionTypeSql(intentionType) };
		}else if(question2!=null&&classify!=null){
			return new String[] { getDemensionSql(question2),getClassifySql(classify) };
		}else if(workArea!=null&&statyArea!=null){
			return new String[] { getSHECusAssistantDataSql(workArea),getSHECusAssistantDataSql(statyArea) };
		}else if(workArea!=null&&intentionType!=null){
			return new String[] { getSHECusAssistantDataSql(workArea),getIntentionTypeSql(intentionType) };
		}else if(workArea!=null&&classify!=null){
			return new String[] { getSHECusAssistantDataSql(workArea),getClassifySql(classify) };
		}else if(statyArea!=null&&intentionType!=null){
			return new String[] { getSHECusAssistantDataSql(statyArea),getIntentionTypeSql(intentionType) };
		}else if(statyArea!=null&&classify!=null){
			return new String[] { getSHECusAssistantDataSql(statyArea),getClassifySql(classify) };
		}else if(intentionType!=null&&classify!=null){
			return new String[] { getIntentionTypeSql(intentionType),getClassifySql(classify) };
		}else if(question1!=null){
			return new String[] {getDemensionSql(question1)};
		}else if(question2!=null){
			return new String[] {getDemensionSql(question1)};
		}else if(workArea!=null){
			return new String[] {getSHECusAssistantDataSql(workArea)};
		}else if(statyArea!=null){
			return new String[] {getSHECusAssistantDataSql(statyArea)};
		}else if(intentionType!=null){
			return new String[] {getIntentionTypeSql(intentionType)};
		}else if(classify!=null){
			return new String[] {getClassifySql(classify)};
		}
		return null;
	}
	private String getSHECusAssistantDataSql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((SHECusAssistantDataInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((SHECusAssistantDataInfo)value[i]).getId().toString()+"'");
        	}
        }
		return "select fid,fname_l2 fname,fnumber fseq from T_SHE_SHECusAssistantData where fid in("+id+")";
	}
	private String getIntentionTypeSql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((RoomModelInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((RoomModelInfo)value[i]).getId().toString()+"'");
        	}
        }
		return "select rm.fid,sp.fname_l2||rm.fname_l2 fname,rm.fnumber fseq from T_SHE_RoomModel rm left join t_she_sellProject sp on sp.fid=rm.fsellProjectId where rm.fid in("+id+")";
	}
	private String getClassifySql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((ChannelTypeInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((ChannelTypeInfo)value[i]).getId().toString()+"'");
        	}
        }
		return "select fid,fname_l2 fname,fnumber fseq from T_MAR_ChannelType where fid in("+id+")";
	}
	private String getDemensionSql(DocumentSubjectInfo question){
		if (question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE)
				|| question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE)) {
			return "select d.FID as FID,d.FTopic as FName,d.FOptionNumber as FSeq from T_MAR_DocumentOption d left join T_MAR_DocumentItem di on di.fid=d.FItemId where di.fsubjectid  = '"+ question.getId() + "'";
		}else if(question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL) 
				|| question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION)){
			return "select e.FInputStr as FID,e.FInputStr as FName,e.FInputStr as FSeq from  T_MAR_QuestionPaperAnswer a "
					+ " left outer join T_MAR_QuestionPaperAnswerEntry e on a.Fid = e.FParentId"
					+ " left outer join T_MAR_DocumentOption d on d.Fid = e.FOption left join T_MAR_DocumentItem di on di.fid=d.FItemId"
					+ " where di.fsubjectid  = '"+ question.getId() + "'" ;
		}
		return null;
	}
	private String[] getDimensionNames() {
		if(question1!=null&&question2==null&&workArea==null&&statyArea==null&&intentionType==null&&classify==null){
			return new String[]{"Question1"};
		}else if(question2!=null&&question1==null&&workArea==null&&statyArea==null&&intentionType==null&&classify==null){
			return new String[]{"Question1"};
		}else if(workArea!=null&&question1==null&&question2==null&&statyArea==null&&intentionType==null&&classify==null){
			return new String[]{"Question1"};
		}else if(statyArea!=null&&question1==null&&question2==null&&workArea==null&&intentionType==null&&classify==null){
			return new String[]{"Question1"};
		}else if(intentionType!=null&&question1==null&&question2==null&&workArea==null&&statyArea==null&&classify==null){
			return new String[]{"Question1"};
		}else if(classify!=null&&question1==null&&question2==null&&workArea==null&&statyArea==null&&intentionType==null){
			return new String[]{"Question1"};
		}else{
			return new String[]{"Question1","Question2"};
		}
	}

	private String getFactSql(Context ctx, RptParams params) {
		StringBuffer sql = new StringBuffer();
		
		if(question1!=null&&question2!=null){
			sql.append(" select q1.FID ||','|| q2.FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question1)+") as q1 ) as q1 ,(select * from ( "+getOneFactSql(question2)+") as q2 ) as q2 ");
			sql.append(" where q1.FPaper = q2.FPaper and q1.fcustomer = q2.fcustomer");
		}else if(question1!=null&&workArea!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question1)+") as q1 ) as q1 ,(select * from ( "+getWorkAreaFactSql(workArea)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question1!=null&&statyArea!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question1)+") as q1 ) as q1 ,(select * from ( "+getStayAreaFactSql(statyArea)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question1!=null&&intentionType!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question1)+") as q1 ) as q1 ,(select * from ( "+getIntentionTypeFactSql(intentionType)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question1!=null&&classify!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question1)+") as q1 ) as q1 ,(select * from ( "+getClassifyFactSql(classify)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question2!=null&&workArea!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question2)+") as q1 ) as q1 ,(select * from ( "+getWorkAreaFactSql(workArea)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question2!=null&&statyArea!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question2)+") as q1 ) as q1 ,(select * from ( "+getStayAreaFactSql(statyArea)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question2!=null&&intentionType!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question2)+") as q1 ) as q1 ,(select * from ( "+getIntentionTypeFactSql(intentionType)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(question2!=null&&classify!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getOneFactSql(question2)+") as q1 ) as q1 ,(select * from ( "+getClassifyFactSql(classify)+") as q2 ) as q2 ");
			sql.append(" where q1.fcustomer = q2.fcustomer ");
		}else if(workArea!=null&&statyArea!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getWorkAreaFactSql(workArea)+") as q1 ) as q1 ,(select * from ( "+getStayAreaFactSql(statyArea)+") as q2 ) as q2 ");
			sql.append(" where q1.fid=q2.fid");
		}else if(workArea!=null&&intentionType!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getWorkAreaFactSql(workArea)+") as q1 ) as q1 ,(select * from ( "+getIntentionTypeFactSql(intentionType)+") as q2 ) as q2 ");
			sql.append(" where q1.fid=q2.fid");
		}else if(workArea!=null&&classify!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getWorkAreaFactSql(workArea)+") as q1 ) as q1 ,(select * from ( "+getClassifyFactSql(classify)+") as q2 ) as q2 ");
			sql.append(" where q1.fid=q2.fid");
		}else if(statyArea!=null&&intentionType!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getStayAreaFactSql(statyArea)+") as q1 ) as q1 ,(select * from ( "+getIntentionTypeFactSql(intentionType)+") as q2 ) as q2 ");
			sql.append(" where q1.fid=q2.fid");
		}else if(statyArea!=null&&classify!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getStayAreaFactSql(statyArea)+") as q1 ) as q1 ,(select * from ( "+getClassifyFactSql(classify)+") as q2 ) as q2 ");
			sql.append(" where q1.fid=q2.fid");
		}else if(intentionType!=null&&classify!=null){
			sql.append(" select q1.FID ||','|| q2.FID as FID,q1.FQuestionID as FQuestion1ID,q2.FQuestionID as FQuestion2ID,1 as FCount");
			sql.append(" from ");
			sql.append(" (select * from ( "+getIntentionTypeFactSql(intentionType)+") as q1 ) as q1 ,(select * from ( "+getClassifyFactSql(classify)+") as q2 ) as q2 ");
			sql.append(" where q1.fid=q2.fid");
		}else if(question1!=null){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount from ("+getOneFactSql(question1)+") as q1");
		}else if(question2!=null){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount from ("+getOneFactSql(question2)+") as q1");
		}else if(workArea!=null){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount from ("+getWorkAreaFactSql(workArea)+") as q1");
		}else if(statyArea!=null){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount from ("+getStayAreaFactSql(statyArea)+") as q1");
		}else if(intentionType!=null){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount from ("+getIntentionTypeFactSql(intentionType)+") as q1");
		}else if(classify!=null){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount from ("+getClassifyFactSql(classify)+") as q1");
		}
		return sql.toString();
	}
	private String getWorkAreaFactSql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((SHECusAssistantDataInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((SHECusAssistantDataInfo)value[i]).getId().toString()+"'");
        	}
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select commerce.fid,commerce.FWorkAreaID FQuestionID,commerce.FCustomerId fcustomer from T_SHE_CommerceChance commerce where commerce.FCustomerID is not null and commerce.FWorkAreaID is not null");
		if(id.length()>0){
			sql.append(" and commerce.FWorkAreaID in ("+id+")");
		}
		if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
			sql.append(" and ");
			sql.append(buildIn("FSellProjectID", sellProjectIdSet.toArray()));
			sql.append(" \r\n");
		}
		if(!isShowAll){
			if(isAll){
				sql.append(" and fcreatetime >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and fcreatetime < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'}");
			}else{
				sql.append(" and commerce.fid in(select FCommerceChanceID from T_SHE_Transaction where FIsValid =0 ");
				sql.append(" and FTranDate >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and FTranDate < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'} )");
			}
		}
		if(commerceChanceStage!=null){
			sql.append(" and FCommerceChanceStage in("+commerceChanceStage+")");
		}
		return sql.toString();
	}
	private String getStayAreaFactSql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((SHECusAssistantDataInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((SHECusAssistantDataInfo)value[i]).getId().toString()+"'");
        	}
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select commerce.fid,commerce.FStayAreaID FQuestionID,commerce.FCustomerId fcustomer from T_SHE_CommerceChance commerce where commerce.FCustomerID is not null and commerce.FStayAreaID is not null");
	
		if(id.length()>0){
			sql.append(" and commerce.FStayAreaID in ("+id+")");
		}
		if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
			sql.append(" and ");
			sql.append(buildIn("FSellProjectID", sellProjectIdSet.toArray()));
			sql.append(" \r\n");
		}
		if(!isShowAll){
			if(isAll){
				sql.append(" and fcreatetime >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and fcreatetime < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'}");
			}else{
				sql.append(" and commerce.fid in(select FCommerceChanceID from T_SHE_Transaction where FIsValid =0 ");
				sql.append(" and FTranDate >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and FTranDate < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'} )");
			}
		}
		if(commerceChanceStage!=null){
			sql.append(" and FCommerceChanceStage in("+commerceChanceStage+")");
		}
		return sql.toString();
	}
	private String getIntentionTypeFactSql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((RoomModelInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((RoomModelInfo)value[i]).getId().toString()+"'");
        	}
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select commerce.fid,commerce.FIntentionTypeID FQuestionID,commerce.FCustomerId fcustomer from T_SHE_CommerceChance commerce where commerce.FCustomerID is not null and commerce.FIntentionTypeID is not null");
	
		if(id.length()>0){
			sql.append(" and commerce.FIntentionTypeID in ("+id+")");
		}
		if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
			sql.append(" and ");
			sql.append(buildIn("FSellProjectID", sellProjectIdSet.toArray()));
			sql.append(" \r\n");
		}
		if(!isShowAll){
			if(isAll){
				sql.append(" and fcreatetime >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and fcreatetime < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'}");
			}else{
				sql.append(" and commerce.fid in(select FCommerceChanceID from T_SHE_Transaction where FIsValid =0 ");
				sql.append(" and FTranDate >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and FTranDate < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'} )");
			}
		}
		if(commerceChanceStage!=null){
			sql.append(" and FCommerceChanceStage in("+commerceChanceStage+")");
		}
		return sql.toString();
	}
	private String getClassifyFactSql(Object[] value){
		StringBuffer id=new StringBuffer();
    	for(int i=0;i<value.length;i++){
        	if(i==0){
        		id.append("'"+((ChannelTypeInfo)value[i]).getId().toString()+"'");
        	}else{
        		id.append(",'"+((ChannelTypeInfo)value[i]).getId().toString()+"'");
        	}
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select commerce.fid fid,commerce.FClassifyID FQuestionID,commerce.FCustomerId fcustomer from T_SHE_CommerceChance commerce where commerce.FCustomerID is not null and commerce.FClassifyID is not null");
	
		if(id.length()>0){
			sql.append(" and commerce.FClassifyID in ("+id+")");
		}
		if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
			sql.append(" and ");
			sql.append(buildIn("FSellProjectID", sellProjectIdSet.toArray()));
			sql.append(" \r\n");
		}
		if(!isShowAll){
			if(isAll){
				sql.append(" and fcreatetime >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and fcreatetime < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'}");
			}else{
				sql.append(" and commerce.fid in(select FCommerceChanceID from T_SHE_Transaction where FIsValid =0 ");
				sql.append(" and FTranDate >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and FTranDate < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'} )");
			}
		}
		if(commerceChanceStage!=null){
			sql.append(" and FCommerceChanceStage in("+commerceChanceStage+")");
		}
		return sql.toString();
	}
	/**
	 * 描述：目前以一份答卷为一个客户统计
	 * @param isFirstDemension
	 * @return
	 */
	private String getOneFactSql(DocumentSubjectInfo question){
		StringBuffer sql = new StringBuffer();
		sql.append(" select  distinct \r\n");
		if (question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE)
				|| question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE)) {
			sql.append(" a.FSheCustomerID as FID, e.FOption as FQuestionID, \r\n");
		}else if(question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL) 
				|| question.getSubjectType().equals(DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION)){
			sql.append(" a.FSheCustomerID as FID, e.FInputStr as FQuestionID, \r\n");
		}
		sql.append(" a.FSheCustomerID FCustomer,a.FID as FPaper \r\n");
		sql.append(" from T_MAR_QuestionPaperAnswer a \r\n");
		sql.append(" left outer join  T_MAR_QuestionPaperAnswerEntry e \r\n");
		sql.append(" on A.FID = e.FParentID \r\n");
		sql.append(" inner join T_MAR_QuestionPaperDefine d \r\n");
		sql.append(" on a.FQuestionPaper = d.FID \r\n");
		if(commerceChanceStage!=null){
			sql.append(" where a.FSheCustomerID is not null and a.FSheCustomerID in(select FCustomerId from T_SHE_CommerceChance where FCommerceChanceStage in("+commerceChanceStage+")) and ");
		}else{
			sql.append(" where a.FSheCustomerID is not null and ");
			
		}
		sql.append(" d.FDocumentId = '");
		sql.append(paper.getDocumentId());
		sql.append("' and \r\n");
		sql.append(" e.FOption in (");
		sql.append(" select op.FID from T_MAR_DocumentOption op left join T_MAR_DocumentItem di on di.fid=op.FItemId \r\n");
		sql.append(" where \r\n");
		sql.append(" di.fsubjectid = '");
		sql.append(question.getId().toString());
		sql.append("' \r\n");
		sql.append(")");
		if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
			sql.append(" and ");
			sql.append(buildIn("a.FSellProject", sellProjectIdSet.toArray()));
			sql.append(" \r\n");
		}
		if(!isShowAll){
			if(isAll){
				sql.append(" and a.FInputDate >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and a.FInputDate < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'}");
			}else{
				sql.append(" and a.FSheCustomerID in(select FCustomerId from T_SHE_Transaction t left join T_SHE_CommerceChance cc on cc.fid=t.FCommerceChanceID where FIsValid =0 ");
				sql.append(" and FTranDate >= {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
				sql.append("'} and FTranDate < {ts'");
				sql.append(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
				sql.append("'} )");
			}
			
		}
		return sql.toString();
	}
	private String buildIn(String name, Object[] set) {
		if (set == null || set.length == 0) {
			return " ";
		}
		StringBuffer sb = new StringBuffer(1204);
		int size = set.length;
		int num = size / 800;
		int j = 0;
		for (int i = 0; i < num; i++) {
			sb.append(" ");
			sb.append(name);
			sb.append(" ");
			sb.append(" in (");
			appendStrValue(sb, set[j]);
			j++;
			for (; j < (i + 1) * 800; j++) {
				sb.append(",");
				appendStrValue(sb, set[j]);
			}
			sb.append(")\n ");
			sb.append(" or ");
		}
		if (j == size) {
			sb.append("1=1");
			sb.append(" )");

			return sb.toString();
		}
		sb.append(" " + name + " ");
		sb.append(" in (");
		appendStrValue(sb, set[j]);

		j++;
		for (; j < size; j++) {
			sb.append(",");
			appendStrValue(sb, set[j]);
		}
		sb.append(")");


		return sb.toString();
	}

	private void appendStrValue(StringBuffer sb, Object object) {
		if (object instanceof Integer) {
			sb.append(object);
		} else {
			sb.append("'");
			sb.append(object);
			sb.append("'");
		}
	}
}