/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ctrl.analysis.bicomponent.DataCellItem;
import com.kingdee.bos.ctrl.analysis.olap.Member;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.market.DocumentSubjectInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.RptPaperAnswerConstant;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.CommerceSrcEnum;
import com.kingdee.eas.fdc.sellhouse.RoomModelInfo;
import com.kingdee.eas.fdc.sellhouse.SHECusAssistantDataInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.client.CommerceChangeNewListUI;
import com.kingdee.eas.fdc.sellhouse.client.QuestionPaperAnswerListUI;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class QuestionPaperReportUI extends AbstractQuestionPaperReportUI
{

	private static final Logger logger = CoreUIObject.getLogger(QuestionPaperReportUI.class);
	private boolean isChange=false;
    public QuestionPaperReportUI() throws Exception {
		super();
	}
    protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
		return new QuestionPaperReportFilterUI();
	}
	
	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		return QuestionPaperReportFacadeFactory.getRemoteInstance();
	}
	
	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}

	protected RptParams getParamsForInit() {
		return null;
	}

	protected RptParams getParamsForRequest() {
		return this.params;
	}

	/**
	 * TODO
	 * 框架去掉了维度名称显示，又没有提供接口，不得已而为之
	 * 后续可通过调整基类的config.showColumnDimensionHeader
	 * 和config.showRowDimensionHeader实现
	 */
	protected void onAfterQuery() throws Exception {
		DocumentSubjectInfo question1 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		DocumentSubjectInfo question2 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		
		Object[] workArea=(Object[]) params.getObject(RptPaperAnswerConstant.WORKAREA);
		Object[] statyArea=(Object[]) params.getObject(RptPaperAnswerConstant.STAYAREA);
		Object[] intentionType=(Object[]) params.getObject(RptPaperAnswerConstant.INTENTIONTYPE);
		Object[] classify=(Object[]) params.getObject(RptPaperAnswerConstant.CLASSIFY);
		
		String head=null;
		String row=null;
		if(question1!=null&&question2!=null){
			head=question1.getTopic();
			row=question2.getTopic();
		}else if(question1!=null&&workArea!=null){
			head=question1.getTopic();
			row="工作区域";
		}else if(question1!=null&&statyArea!=null){
			head=question1.getTopic();
			row="居住区域";
		}else if(question1!=null&&intentionType!=null){
			head=question1.getTopic();
			row="意向户型";
		}else if(question1!=null&&classify!=null){
			head=question1.getTopic();
			row="媒体渠道";
		}else if(question2!=null&&workArea!=null){
			head=question2.getTopic();
			row="工作区域";
		}else if(question2!=null&&statyArea!=null){
			head=question2.getTopic();
			row="居住区域";
		}else if(question2!=null&&intentionType!=null){
			head=question2.getTopic();
			row="意向户型";
		}else if(question2!=null&&intentionType!=null){
			head=question2.getTopic();
			row="媒体渠道";
		}else if(workArea!=null&&statyArea!=null){
			head="工作区域";
			row="居住区域";
		}else if(workArea!=null&&intentionType!=null){
			head="工作区域";
			row="意向户型";
		}else if(workArea!=null&&classify!=null){
			head="工作区域";
			row="媒体渠道";
		}else if(statyArea!=null&&intentionType!=null){
			head="居住区域";
			row="意向户型";
		}else if(statyArea!=null&&classify!=null){
			head="居住区域";
			row="媒体渠道";
		}else if(intentionType!=null&&classify!=null){
			head="意向户型";
			row="媒体渠道";
		}else if(question1!=null){
			head=question1.getTopic();
		}else if(question2!=null){
			head=question2.getTopic();
		}else if(workArea!=null){
			head="工作区域";
		}else if(statyArea!=null){
			head="居住区域";
		}else if(intentionType!=null){
			head="意向户型";
		}else if(classify!=null){
			head="媒体渠道";
		}
		
		if(row==null){
			IRow headR = tblMain.getHeadRow(0);
			headR.getCell(0).setValue(head);
		}else {
			IRow addrow = tblMain.addHeadRow(0);
			addrow.getCell(0).setValue(head);
			IRow headR = tblMain.getHeadRow(1);
			headR.getCell(0).setValue(head);
			for(int i = 1;i < tblMain.getColumnCount(); i ++){
				addrow.getCell(i).setValue(row);
			}
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, tblMain.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		}
		IColumn  column = tblMain.getColumn(0);
		column.setWidth(200);
		for(int i=0;i<tblMain.getRowCount();i++){
			for(int j=1;j<tblMain.getColumnCount();j++){
				tblMain.getRow(i).getCell(j).getStyleAttributes().setFontColor(Color.BLUE);
				tblMain.getRow(i).getCell(j).getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
			}
		}
		addTotalRow();
	}
	private void addTotalRow(){
		IRow footRow=tblMain.addRow();
		String strTotal = EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Total");
		footRow.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
		footRow.getCell(0).setValue(strTotal);
		footRow.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
		for(int i=1;i<tblMain.getColumnCount();i++){
			int total=0;
			for(int j=0;j<tblMain.getRowCount();j++){
				if(tblMain.getRow(j).getCell(i).getValue()!=null&&!"".equals(tblMain.getRow(j).getCell(i).getValue().toString())){
					String value=tblMain.getRow(j).getCell(i).getValue().toString().replaceAll(",", "");
					total=total+new Integer(value);
				}
			}
			if(total>0)
			footRow.getCell(i).setValue(total);
		}
		if(tblMain.getHeadRowCount()>1){
			IColumn footColoum=tblMain.addColumn();
			footColoum.getStyleAttributes().setHorizontalAlign(com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment.getAlignment("right"));
			tblMain.getHeadRow(0).getCell(footColoum.getColumnIndex()).setValue(strTotal);
			
			tblMain.getHeadRow(1).getCell(footColoum.getColumnIndex()).setValue(strTotal);
			
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			mm.mergeBlock(0, footColoum.getColumnIndex(), 1, footColoum.getColumnIndex(), KDTMergeManager.FREE_MERGE);
			
			footColoum.getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
			for(int i=0;i<tblMain.getRowCount();i++){
				int total=0;
				for(int j=1;j<tblMain.getColumnCount();j++){
					if(tblMain.getRow(i).getCell(j).getValue()!=null&&!"".equals(tblMain.getRow(i).getCell(j).getValue().toString())){
						String value=tblMain.getRow(i).getCell(j).getValue().toString().replaceAll(",", "");
						total=total+new Integer(value);
					}
				}
				if(total>0)
				tblMain.getRow(i).getCell(footColoum.getColumnIndex()).setValue(total);
			}
		}
	}
	/**
	 * TODO
	 * 框架去掉了维度名称显示，又没有提供接口，不得已而为之
	 * 后续可通过调整基类的config.showColumnDimensionHeader
	 * 和config.showRowDimensionHeader实现
	 */
	public void actionSwapAxes_actionPerformed(ActionEvent e) throws Exception {
		super.actionSwapAxes_actionPerformed(e);
		
		DocumentSubjectInfo question1 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		DocumentSubjectInfo question2 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		
		Object[] workArea=(Object[]) params.getObject(RptPaperAnswerConstant.WORKAREA);
		Object[] statyArea=(Object[]) params.getObject(RptPaperAnswerConstant.STAYAREA);
		Object[] intentionType=(Object[]) params.getObject(RptPaperAnswerConstant.INTENTIONTYPE);
		Object[] classify=(Object[]) params.getObject(RptPaperAnswerConstant.CLASSIFY);
		String head=null;
		String row=null;
		if(question1!=null&&question2!=null){
			head=question1.getTopic();
			row=question2.getTopic();
		}else if(question1!=null&&workArea!=null){
			head=question1.getTopic();
			row="工作区域";
		}else if(question1!=null&&statyArea!=null){
			head=question1.getTopic();
			row="居住区域";
		}else if(question1!=null&&intentionType!=null){
			head=question1.getTopic();
			row="意向户型";
		}else if(question1!=null&&classify!=null){
			head=question1.getTopic();
			row="媒体渠道";
		}else if(question2!=null&&workArea!=null){
			head=question2.getTopic();
			row="工作区域";
		}else if(question2!=null&&statyArea!=null){
			head=question2.getTopic();
			row="居住区域";
		}else if(question2!=null&&intentionType!=null){
			head=question2.getTopic();
			row="意向户型";
		}else if(question2!=null&&classify!=null){
			head=question2.getTopic();
			row="媒体渠道";
		}else if(workArea!=null&&statyArea!=null){
			head="工作区域";
			row="居住区域";
		}else if(workArea!=null&&intentionType!=null){
			head="工作区域";
			row="意向户型";
		}else if(workArea!=null&&classify!=null){
			head="工作区域";
			row="媒体渠道";
		}else if(statyArea!=null&&intentionType!=null){
			head="居住区域";
			row="意向户型";
		}else if(statyArea!=null&&classify!=null){
			head="居住区域";
			row="媒体渠道";
		}else if(intentionType!=null&&classify!=null){
			head="意向户型";
			row="媒体渠道";
		}else if(question1!=null){
			head=question1.getTopic();
		}else if(question2!=null){
			head=question2.getTopic();
		}else if(workArea!=null){
			head="工作区域";
		}else if(statyArea!=null){
			head="居住区域";
		}else if(intentionType!=null){
			head="意向户型";
		}else if(classify!=null){
			head="媒体渠道";
		}
		
		if(row==null){
			IRow headR = tblMain.getHeadRow(0);
			headR.getCell(0).setValue(head);
		}else {
			IRow addrow = tblMain.addHeadRow(0);
			IRow headR = tblMain.getHeadRow(1);
			if(isChange){
				addrow.getCell(0).setValue(head);
				headR.getCell(0).setValue(head);
				for(int i = 1;i < tblMain.getColumnCount(); i ++){
					addrow.getCell(i).setValue(row);
				}
				addrow.getCell(0).setValue(row);
			}else {
				headR.getCell(0).setValue(row);
				for(int i = 1;i < tblMain.getColumnCount(); i ++){
					addrow.getCell(i).setValue(head);
				}
			}
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, tblMain.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);
		}
		IColumn  column = tblMain.getColumn(0);
		column.setWidth(200);
		for(int i=0;i<tblMain.getRowCount();i++){
			for(int j=1;j<tblMain.getColumnCount();j++){
				tblMain.getRow(i).getCell(j).getStyleAttributes().setFontColor(Color.BLUE);
			}
		}
		isChange=!isChange;
		addTotalRow();
	}

	protected void onBeforeQuery() throws Exception {
	}

	protected void preparePrintPageHeader(HeadFootModel header) {
		
	}

	protected Map preparePrintVariantMap() {
		return null;
	}
	
	/**
	 * 描述：实现联查功能
	 * 
	 */
	protected void onTableClicked(KDTMouseEvent e) throws Exception {
		
		if(e.getClickCount() == 2){
			
			Member[] mm = getSelectedDataCellItemInfo();

			if(mm == null){
				return;
			}
			
			Object val = tblMain.getCell(e.getRowIndex(),e.getColIndex()).getValue();
			if(val == null){
				return;
			}else if(val instanceof DataCellItem){
				DataCellItem item = (DataCellItem) val;
				if(item.getValue() == null){
					return;
				}
			}
			
			
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put("filter", getJoinQueryFilter());
			
			try {
				IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.MODEL)
						.create(CommerceChangeNewListUI.class.getName(),
								uiContext, null, OprtState.VIEW);
				ui.show();

			} catch (Exception ex) {
				this.handUIException(ex);
			}
		}
	}
	/**
	 * 获取联查过滤条件
	 * TODO
	 * sql部分可以抽取到服务器端
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws SQLException
	 */
	private FilterInfo getJoinQueryFilter() throws BOSException, EASBizException, SQLException{
		FilterInfo filter = new FilterInfo();
		
		Object q1Key = null;
		Object q2Key = null;
		Member[] mm = getSelectedDataCellItemInfo();
		for(int i = 0, n = mm.length; i < n; i++){
			if(mm[i].getDimension().getName().equals("Question1")){
				q1Key = mm[i].getKey();
			}else if(mm[i].getDimension().getName().equals("Question2")){
				q2Key = mm[i].getKey();
			}
		}
		DocumentSubjectInfo question1 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		DocumentSubjectInfo question2 = (DocumentSubjectInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		Date dateFrom = (Date) params.getObject(RptPaperAnswerConstant.DATE_FROM);
		Date dateTo = (Date) params.getObject(RptPaperAnswerConstant.DATE_TO);
		
		boolean isShowAll = params.getBoolean(RptPaperAnswerConstant.IS_SHOW_ALL);
		
		Set sellProjectIdSet = (Set) params.getObject(RptPaperAnswerConstant.SELL_PROJECT_ID_SET);
		Object[] workArea=(Object[]) params.getObject(RptPaperAnswerConstant.WORKAREA);
		Object[] stayArea=(Object[]) params.getObject(RptPaperAnswerConstant.STAYAREA);
		Object[] intentionType=(Object[]) params.getObject(RptPaperAnswerConstant.INTENTIONTYPE);
		Object[] classify=(Object[]) params.getObject(RptPaperAnswerConstant.CLASSIFY);
		
		QuestionPaperDefineInfo paper = (QuestionPaperDefineInfo) params.getObject(RptPaperAnswerConstant.PAPER);
		
		String commerceChanceStage=null;
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
		
		boolean isAll=params.getBoolean("cbIsAll");
		FDCSQLBuilder sql =null;
		if(question1!=null&&question2!=null){
			sql= new FDCSQLBuilder();
			sql.appendSql(" select  distinct p.FSheCustomerID from T_MAR_QuestionPaperAnswer p left outer join T_MAR_QuestionPaperAnswerEntry q1 ");
			sql.appendSql(" on p.FID = q1.FParentID left join T_MAR_QuestionPaperAnswerEntry q2 on p.FID = q2.FParentID ");
			sql.appendSql(" where p.FSheCustomerID is not null and (q1.FOption ='"+q1Key+"' or q1.FInputStr ='"+q1Key+"') and (q2.FOption ='"+q2Key+"' or q2.FInputStr ='"+q2Key+"') and p.fquestionPaper='"+paper.getId().toString()+"'");
			if(!isShowAll){
				if(isAll){
					sql.appendSql(" and p.FInputDate >= {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
					sql.appendSql("'} and p.FInputDate < {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
					sql.appendSql("'}");
				}else{
					sql.appendSql(" and p.FSheCustomerID in(select FCustomerId from T_SHE_Transaction tran left join T_SHE_CommerceChance cc on cc.fid=tran.FCommerceChanceID where FIsValid =0 ");
					sql.appendSql(" and FTranDate >= {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
					sql.appendSql("'} and FTranDate < {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
					sql.appendSql("'} )");
				}
			}
			if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
				sql.appendSql(" and FSellProject in("+SHEManageHelper.getStringFromSet(sellProjectIdSet)+")");
			}
		}else if(question1!=null||question2!=null){
			sql= new FDCSQLBuilder();
			sql.appendSql(" select distinct FSheCustomerID from T_MAR_QuestionPaperAnswerEntry p left outer join T_MAR_QuestionPaperAnswer q1 on q1.FID = p.FParentID where FSheCustomerID is not null and FOption ='"+q1Key+"' or FInputStr ='"+q1Key+"'");;
			if(!isShowAll){
				if(isAll){
					sql.appendSql(" and q1.FInputDate >= {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
					sql.appendSql("'} and q1.FInputDate < {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
					sql.appendSql("'}");
				}else{
					sql.appendSql(" and q1.FSheCustomerID in(select FCustomerId from T_SHE_Transaction tran left join T_SHE_CommerceChance cc on cc.fid=tran.FCommerceChanceID where FIsValid =0 ");
					sql.appendSql(" and FTranDate >= {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom)));
					sql.appendSql("'} and FTranDate < {ts'");
					sql.appendSql(FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo)));
					sql.appendSql("'} )");
				}
			}
			if(sellProjectIdSet != null && sellProjectIdSet.size() > 0){
				sql.appendSql(" and FSellProject in("+SHEManageHelper.getStringFromSet(sellProjectIdSet)+")");
			}
		}
		if(sql!=null){
			IRowSet rs = sql.executeQuery();
			Set idSet = new HashSet();
			while(rs.next()){
				idSet.add(rs.getString(1));
			}
			filter.getFilterItems().add(new FilterItemInfo("customer.id", idSet, CompareType.INCLUDE));
		}
		if (sellProjectIdSet != null && sellProjectIdSet.size() > 0) {
			filter.getFilterItems().add(new FilterItemInfo("sellProject.id", sellProjectIdSet,CompareType.INCLUDE));
		}
		if (!isShowAll) {
			if(isAll){
				filter.getFilterItems().add(new FilterItemInfo("createTime", FDCDateHelper.getSQLBegin(dateFrom),CompareType.GREATER_EQUALS));
				filter.getFilterItems().add(new FilterItemInfo("createTime", FDCDateHelper.getSQLEnd(dateTo),CompareType.LESS));
			}else{
				String wsql="select FCommerceChanceID from T_SHE_Transaction where FIsValid =0 and FTranDate >= {ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLBegin(dateFrom))+"'} and FTranDate < {ts'"+FDCConstants.FORMAT_TIME.format(FDCDateHelper.getSQLEnd(dateTo))+"'} )";
				filter.getFilterItems().add(new FilterItemInfo("id", wsql,CompareType.INNER));
			}
		}
		if(commerceChanceStage!=null){
			filter.getFilterItems().add(new FilterItemInfo("commerceChanceStage.id",commerceChanceStage,CompareType.INNER));
		}
		if(workArea!=null||stayArea!=null||intentionType!=null||classify!=null){
			String id="";
			if(q1Key!=null){
				id=id+"'"+q1Key.toString()+"'";
			}
			if(q2Key!=null){
				if(id.equals("")){
					id=id+"'"+q2Key.toString()+"'";
				}else{
					id=id+",'"+q2Key.toString()+"'";
				}
				
			}
			FilterInfo cmfilter=new FilterInfo();
			if(question1!=null&&workArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("workArea.id",id,CompareType.INNER));
			}else if(question1!=null&&stayArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("stayArea.id",id,CompareType.INNER));
			}else if(question1!=null&&intentionType!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("intentionType.id",id,CompareType.INNER));
			}else if(question1!=null&&classify!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("classify.id",id,CompareType.INNER));
			}else if(question2!=null&&workArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("workArea.id",id,CompareType.INNER));
			}else if(question2!=null&&stayArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("stayArea.id",id,CompareType.INNER));
			}else if(question2!=null&&intentionType!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("intentionType.id",id,CompareType.INNER));
			}else if(question2!=null&&classify!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("classify.id",id,CompareType.INNER));
			}else if(workArea!=null&&stayArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("workArea.id",id,CompareType.INNER));
				cmfilter.getFilterItems().add(new FilterItemInfo("stayArea.id",id,CompareType.INNER));
			}else if(workArea!=null&&intentionType!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("workArea.id",id,CompareType.INNER));
				cmfilter.getFilterItems().add(new FilterItemInfo("intentionType.id",id,CompareType.INNER));
			}else if(workArea!=null&&classify!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("workArea.id",id,CompareType.INNER));
				cmfilter.getFilterItems().add(new FilterItemInfo("classify.id",id,CompareType.INNER));
			}else if(stayArea!=null&&intentionType!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("stayArea.id",id,CompareType.INNER));
				cmfilter.getFilterItems().add(new FilterItemInfo("intentionType.id",id,CompareType.INNER));
			}else if(stayArea!=null&&classify!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("stayArea.id",id,CompareType.INNER));
				cmfilter.getFilterItems().add(new FilterItemInfo("classify.id",id,CompareType.INNER));
			}else if(intentionType!=null&&classify!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("intentionType.id",id,CompareType.INNER));
				cmfilter.getFilterItems().add(new FilterItemInfo("classify.id",id,CompareType.INNER));
			}else if(workArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("workArea.id",id,CompareType.INNER));
			}else if(stayArea!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("stayArea.id",id,CompareType.INNER));
			}else if(intentionType!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("intentionType.id",id,CompareType.INNER));
			}else if(classify!=null){
				cmfilter.getFilterItems().add(new FilterItemInfo("classify.id",id,CompareType.INNER));
			}
			filter.mergeFilter(cmfilter, "and");
		}
		return filter;
	}
	
}