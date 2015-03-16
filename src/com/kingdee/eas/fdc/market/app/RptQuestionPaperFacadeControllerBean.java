/*
 * @(#)com.kingdee.eas.fdc.market.app.RptQuestionPaperFacadeControllerBean.java
 *
 * 金蝶国际软件集团有限公司版权所有 
 */
package com.kingdee.eas.fdc.market.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.DocumentSubjectTypeEnum;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.RptPaperAnswerConstant;
import com.kingdee.eas.framework.bireport.util.SchemaSource;
import com.kingdee.eas.framework.report.util.RptParams;

/**
 * 描述：问卷结果分析表
 * 
 * @author weiqiang_chen
 * 
 */
public class RptQuestionPaperFacadeControllerBean extends
		AbstractRptQuestionPaperFacadeControllerBean {
	
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.market.app.RptQuestionPaperFacadeControllerBean");
	
	
	private Date dateFrom;
	private Date dateTo;
	private int byType;
	private boolean isShowAll;
	private Set sellProjectIdSet;
	private QuestionPaperDefineInfo paper;
	private DocumentItemInfo question1;
	private DocumentItemInfo question2;
	private boolean isSingleDimension;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	
	
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
		
		byType = params.getInt(RptPaperAnswerConstant.BY_TYPE);
		sellProjectIdSet = (Set) params.getObject(RptPaperAnswerConstant.SELL_PROJECT_ID_SET);
		
		paper = (QuestionPaperDefineInfo) params.getObject(RptPaperAnswerConstant.PAPER);
		question1 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		question2 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		
		isSingleDimension = (question2 == null);
	}


	private String getConfigXML() {
		if(isSingleDimension){
			return "RptQuestionPaper_One.xml";
		}else{
			return "RptQuestionPaper_Two.xml";
		}
	}


	private String getMDX(Context ctx, RptParams params) {
		if(isSingleDimension){
			return "select  {[Measures].members} on columns, {[Question1].members} on rows from Fact";
		}else{
			return "select  {[Question2].members} on columns, {[Question1].members} on rows from Fact";
		}
	}


	private String[] getDimensionSqls() {
		if (isSingleDimension) {
			return new String[] {getDemensionSql(question1)};
		} else {
			return new String[] { getDemensionSql(question1),
					getDemensionSql(question2) };
		}
	}
	
	private String getDemensionSql(DocumentItemInfo question){
		if (question.getSubjectId().getSubjectType().equals(
				DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE)
				|| question.getSubjectId().getSubjectType().equals(
						DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE)) {
			return "select o.FID as FID,o.FTopic as FName,o.FOptionNumber as FSeq from T_MAR_DocumentOption o " +
					"where o.FItemId = '"
					+ question.getId() + "'";
			
		}else if(question.getSubjectId().getSubjectType().equals(
				DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL) || question.getSubjectId().getSubjectType().equals(
						DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION)){
			return "select e.FInputStr as FID,e.FInputStr as FName,e.FInputStr as FSeq" 
					+ " from  T_MAR_QuestionPaperAnswer a "
					+ " left outer join T_MAR_QuestionPaperAnswerEntry e on a.Fid = e.FParentId"
					+ " left outer join T_MAR_DocumentOption d on d.Fid = e.FOption"
					+ " where d.FItemId = '"
					+ question.getId() + "'" ;
		}
		
		return null;
	}


	private String[] getDimensionNames() {
		if(isSingleDimension){
			return new String[]{"Question1"};
		}else{
			return new String[]{"Question1","Question2"};
		}
	}


	private String getFactSql(Context ctx, RptParams params) {
		StringBuffer sql = new StringBuffer();
		if(isSingleDimension){
			sql.append(" select FID as FID,FQuestionID as FQuestion1ID,1 as FCount \r\n");
			sql.append(" from ( \r\n");
			sql.append(getOneFactSql(question1));
			sql.append(" ) as q1 \r\n");
		}else{
			sql.append(" select q1.FID || q2.FID as FID, \r\n");
			sql.append(" q1.FQuestionID as FQuestion1ID,\r\n");
			sql.append(" q2.FQuestionID as FQuestion2ID,\r\n");
			sql.append(" 1 as FCount \r\n");
			sql.append(" from ( \r\n");
			sql.append(" select * \r\n");
			sql.append(" from ( \r\n");
			sql.append(getOneFactSql(question1));
			sql.append(" ) as q1 ) as q1 ,\r\n");
			sql.append(" (select * \r\n");
			sql.append(" from ( \r\n");
			sql.append(getOneFactSql(question2));
			sql.append(" ) as q2 ) as q2 \r\n");
			sql.append(" where q1.FPaper = q2.FPaper \r\n");
		}
		return sql.toString();
	}
	
	/**
	 * 描述：目前以一份答卷为一个客户统计
	 * @param isFirstDemension
	 * @return
	 */
	private String getOneFactSql(DocumentItemInfo question){
		StringBuffer sql = new StringBuffer();
		sql.append(" select  \r\n");
		if (question.getSubjectId().getSubjectType().equals(
				DocumentSubjectTypeEnum.SUBJECT_TYPE_SINGLE)
				|| question.getSubjectId().getSubjectType().equals(
						DocumentSubjectTypeEnum.SUBJECT_TYPE_MULTIPLE)) {
			sql.append(" e.FID as FID, e.FOption as FQuestionID, \r\n");
			
		}else if(question.getSubjectId().getSubjectType().equals(
				DocumentSubjectTypeEnum.SUBJECT_TYPE_FILL) || question.getSubjectId().equals(
						DocumentSubjectTypeEnum.SUBJECT_TYPE_DESCRIPTION)){
			sql.append(" e.FID as FID, e.FInputStr as FQuestionID, \r\n");
		}
		sql.append(" a.FID as FPaper \r\n");
		sql.append(" from T_MAR_QuestionPaperAnswer a \r\n");
		sql.append(" left outer join  T_MAR_QuestionPaperAnswerEntry e \r\n");
		sql.append(" on A.FID = e.FParentID \r\n");
		sql.append(" inner join T_MAR_QuestionPaperDefine d \r\n");
		sql.append(" on a.FQuestionPaper = d.FID \r\n");
		sql.append(" where \r\n");
		sql.append(" d.FDocumentId = '");
		sql.append(paper.getDocumentId());
		sql.append("' and \r\n");
		sql.append(" e.FOption in (");
		sql.append(" select op.FID from T_MAR_DocumentOption op \r\n");
		sql.append(" where \r\n");
		sql.append(" op.FItemId = '");
		sql.append(question.getId().toString());
		sql.append("' \r\n");
		sql.append(")");
		
		if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
			sql.append(" and ");
			sql.append(buildIn("a.FSellProject", sellProjectIdSet.toArray()));
			sql.append(" \r\n");
		}

		if(!isShowAll){
			sql.append(" and a.FInputDate >= {ts'");
			sql.append(format.format(dateFrom));
			sql.append("'} and a.FInputDate <= {ts'");
			sql.append(format.format(dateTo));
			sql.append("'}");
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