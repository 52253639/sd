/**
 * @(#)<com.kingdee.eas.fdc.market.client.RptQuestionPaperMainUI.java>
 *  
 * 金蝶国际软件集团有限公司版权所有 
 *
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.analysis.bicomponent.DataCellItem;
import com.kingdee.bos.ctrl.analysis.olap.Member;
import com.kingdee.bos.ctrl.kdf.headfootdesigner.HeadFootModel;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.DocumentItemInfo;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.RptPaperAnswerConstant;
import com.kingdee.eas.fdc.market.RptQuestionPaperFacadeFactory;
import com.kingdee.eas.fm.common.FMIsqlFacadeFactory;
import com.kingdee.eas.fm.common.IFMIsqlFacade;
import com.kingdee.eas.framework.bireport.IBireportBaseFacade;
import com.kingdee.eas.framework.bireport.client.BireportBaseFilterUI;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 描述：问卷结果分析表
 * 
 * @author weiqiang_chen
 * 
 */
public class RptQuestionPaperMainUI extends AbstractRptQuestionPaperMainUI {

	public RptQuestionPaperMainUI() throws Exception {
		super();
	}
	
	protected BireportBaseFilterUI getQueryDialogUserPanel() throws Exception {
		return new RptQuestionPaperFilterUI();
	}
	
	protected IBireportBaseFacade getRemoteInstance() throws BOSException {
		return RptQuestionPaperFacadeFactory.getRemoteInstance();
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
		DocumentItemInfo question1 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		DocumentItemInfo question2 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		
		if(question2 != null){
			IRow row = tblMain.addHeadRow(0);
			row.getCell(0).setValue(question1.getTopic());
			IRow headR = tblMain.getHeadRow(1);
			headR.getCell(0).setValue(question1.getTopic());
			for(int i = 1;i < tblMain.getColumnCount(); i ++){
				row.getCell(i).setValue(question2.getTopic());
			}
			
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, tblMain.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

		}else{
			IRow headR = tblMain.getHeadRow(0);
			headR.getCell(0).setValue(question1.getTopic());
		}
		IColumn  column = tblMain.getColumn(0);
		column.setWidth(200);
	}
	
	/**
	 * TODO
	 * 框架去掉了维度名称显示，又没有提供接口，不得已而为之
	 * 后续可通过调整基类的config.showColumnDimensionHeader
	 * 和config.showRowDimensionHeader实现
	 */
	public void actionSwapAxes_actionPerformed(ActionEvent e) throws Exception {
		super.actionSwapAxes_actionPerformed(e);
		
		DocumentItemInfo question1 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		DocumentItemInfo question2 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		if(question2 != null){
			IRow row = tblMain.addHeadRow(0);

			IRow headR = tblMain.getHeadRow(1);
			Object o = headR.getCell(0).getValue();
			if(o.toString().equals("问题2") 
					|| o.toString().equals(question2.getTopic())){
				row.getCell(0).setValue(question2.getTopic());
				headR.getCell(0).setValue(question2.getTopic());
				for(int i = 1;i < tblMain.getColumnCount(); i ++){
					row.getCell(i).setValue(question1.getTopic());
				}
			}else {
				row.getCell(0).setValue(question1.getTopic());
				headR.getCell(0).setValue(question1.getTopic());
				for(int i = 1;i < tblMain.getColumnCount(); i ++){
					row.getCell(i).setValue(question2.getTopic());
				}
			}


			
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			mm.mergeBlock(0, 0, 1, tblMain.getColumnCount() - 1, KDTMergeManager.FREE_MERGE);

		}else{
			IRow headR = tblMain.getHeadRow(0);
			headR.getCell(0).setValue(question1.getTopic());
		}
		
		IColumn  column = tblMain.getColumn(0);
		column.setWidth(200);
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
			
			try {
				IUIWindow ui = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
						.create(QuestionPaperAnswerListUI.class.getName(),
								uiContext, null, OprtState.VIEW);

				((QuestionPaperAnswerListUI) ui.getUIObject()).getMainQuery()
						.setFilter(getJoinQueryFilter());

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
		

		
		
		Date dateFrom = (Date) params.getObject(RptPaperAnswerConstant.DATE_FROM);
		Date dateTo = (Date) params.getObject(RptPaperAnswerConstant.DATE_TO);
		
		boolean isShowAll = params.getBoolean(RptPaperAnswerConstant.IS_SHOW_ALL);
		
		Set sellProjectIdSet = (Set) params.getObject(RptPaperAnswerConstant.SELL_PROJECT_ID_SET);
		
		QuestionPaperDefineInfo paper = (QuestionPaperDefineInfo) params.getObject(RptPaperAnswerConstant.PAPER);
		DocumentItemInfo question1 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION1);
		DocumentItemInfo question2 = (DocumentItemInfo) params.getObject(RptPaperAnswerConstant.QUESTION2);
		
		boolean isSingleDimension = (question2 == null);
		filter.getFilterItems().add(new FilterItemInfo("questionPaper.id",paper.getId().toString()));
		
		if (sellProjectIdSet != null && sellProjectIdSet.size() > 0) {
			filter.getFilterItems().add(
					new FilterItemInfo("sellProject.id", sellProjectIdSet,
							CompareType.INCLUDE));
		}

		if (!isShowAll) {
			filter.getFilterItems().add(
					new FilterItemInfo("inputDate", dateFrom,
							CompareType.GREATER_EQUALS));
			filter.getFilterItems().add(
					new FilterItemInfo("inputDate", dateTo,
							CompareType.LESS_EQUALS));
		}
		
		String sql = null;
		Object[] param = null;
		if(isSingleDimension){
			sql = "select distinct FParentID from T_MAR_QuestionPaperAnswerEntry where FOption = ? or FInputStr = ?";
			param = new Object[]{q1Key,q1Key};
		}else{
			sql = " select  distinct p.FID " 
				+ " from T_MAR_QuestionPaperAnswer p"
				+ " left outer join T_MAR_QuestionPaperAnswerEntry q1"
				+ " on p.FID = q1.FParentID"
				+ " left join T_MAR_QuestionPaperAnswerEntry q2"
				+ " on p.FID = q2.FParentID "
				+ " where"
				+ " (q1.FOption = ? or q1.FInputStr = ?)"
				+ " and (q2.FOption = ? or q2.FInputStr = ?)";
			param = new Object[]{q1Key,q1Key,q2Key,q2Key};
		}
		
		IFMIsqlFacade facade = FMIsqlFacadeFactory.getRemoteInstance();
		IRowSet rs = facade.executeQuery(sql, param);
		Set idSet = new HashSet();
		while(rs.next()){
			idSet.add(rs.getString(1));
		}
		
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		
		return filter;
	}

}