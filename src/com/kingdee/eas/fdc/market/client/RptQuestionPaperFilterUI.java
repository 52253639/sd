/**
 * @(#)<com.kingdee.eas.fdc.market.client.RptQuestionPaperFilterUI.java>
 *  
 * 金蝶国际软件集团有限公司版权所有 
 *
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.swing.SpinnerNumberModel;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ContextUtils;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.RptPaperAnswerConstant;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * 描述：问卷结果分析表——过滤界面
 * 
 * @author weiqiang_chen
 * 
 */
public class RptQuestionPaperFilterUI extends AbstractRptQuestionPaperFilterUI {



	private boolean isLoaded = false;
	
	private KDBizPromptBox[] prmtSellProjects = new KDBizPromptBox[] {
			prmtItem1, prmtItem2, prmtItem3, prmtItem4, prmtItem5, prmtItem6 };

	private KDRadioButton[] btnByTypes = new KDRadioButton[] { btnByDate,
			btnByMonth, btnBySeason, btnByYear };

	public RptQuestionPaperFilterUI() throws Exception {
		super();
	}
	
	public void onLoad() throws Exception {
    	super.onLoad();
    	
		if (!isLoaded ) {
			init();
			setDefaultValue();
		}
		isLoaded = true;
	}
	
	protected void init() throws EASBizException, BOSException {
		
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		
		SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
		spiMonthFrom.setModel(monthMo1);
		SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
		spiMonthTo.setModel(monthMo2);
		
		int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		SpinnerNumberModel quarterMo1 = new SpinnerNumberModel(
				SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
		spiSeasonFrom.setModel(quarterMo1);
		SpinnerNumberModel quarterMo2 = new SpinnerNumberModel(
				SEASON[Calendar.getInstance().get(Calendar.MONTH)], 1, 4, 1);
		spiSeasonTo.setModel(quarterMo2);
		
		initPrmtSellProject(prmtItem1);
		initPrmtSellProject(prmtItem2);
		initPrmtSellProject(prmtItem3);
		initPrmtSellProject(prmtItem4);
		initPrmtSellProject(prmtItem5);
		initPrmtSellProject(prmtItem6);
		
		
		initPrmtPaper(prmtPaper);
		prmtPaper.setRequired(true);
		
		initPrmtQuestion(prmtQuestion1);
		prmtQuestion1.setRequired(true);
		
		initPrmtQuestion(prmtQuestion2);
	}
	
	public void clear() {
		super.clear();
		
		setDefaultValue();
	}
	
	private void initPrmtSellProject(KDBizPromptBox box) throws EASBizException, BOSException{
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		box.setEntityViewInfo(CommerceHelper.getPermitProjectView());
	}
	
	
	private void initPrmtPaper(KDBizPromptBox box){
		box.setQueryInfo("com.kingdee.eas.fdc.market.app.QuestionPaperDefineQuery");
		box.setDisplayFormat("$topric$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("cu.id", ContextHelperFactory
						.getRemoteInstance().getCurrentCtrlUnit().getId()
						.toString()));
		evi.setFilter(filter);
		box.setEntityViewInfo(evi);
	}
	

	private void initPrmtQuestion(KDBizPromptBox box){
		box.setQueryInfo("com.kingdee.eas.fdc.market.app.F7DocumentItemQuery");
		box.setDisplayFormat("$topic$");
		box.setEditFormat("$topic$");
		box.setCommitFormat("$topic$");
		
		Object v = prmtPaper.getValue();
		if (v instanceof QuestionPaperDefineInfo) {
			QuestionPaperDefineInfo paperInfo = (QuestionPaperDefineInfo) v;
			EntityViewInfo evi = new EntityViewInfo();
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("id");
			sic.add("topic");
			sic.add("subjectId.*");
			sic.add("subjectId.documentId.*");
			box.setSelectorCollection(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("subjectId.documentId.id",paperInfo.getDocumentId()));
			evi.setFilter(filter);
			
			box.setEntityViewInfo(evi);
		}
	}
	protected void setDefaultValue() {
    	dpDateFrom.setValue(DateHelper.getFirstDayOfCurMonth());
    	dpDateTo.setValue(new Date());
    	chkIsShowAll.setSelected(false);
    	btnByDate.setSelected(true);
    	initControlByDateType();
    	
    	for(int i = 0;i < prmtSellProjects.length;i ++){
    		prmtSellProjects[i].setValue(null);
    	}

    	
    	prmtPaper.setValue(null);
    	prmtQuestion1.setValue(null);
    	prmtQuestion2.setValue(null);
    	
	}

	public void onInit(RptParams initParams) throws Exception {
	}
	
	public RptParams getCustomCondition() {
		RptConditionManager rcm=new RptConditionManager(); 
    	rcm.recordAllStatus(this); //保存控件值 
    	

		rcm.setProperty(RptPaperAnswerConstant.DATE_FROM, getDateFrom());
		rcm.setProperty(RptPaperAnswerConstant.DATE_TO, getDateTo());
		
    	rcm.setProperty(RptPaperAnswerConstant.IS_SHOW_ALL, Boolean.valueOf(chkIsShowAll.isSelected()));
    	
    	int byType = 0;
    	for(int i = 0;i < btnByTypes.length;i ++){
    		if(btnByTypes[i].isSelected()){
        		byType = i;
        	}
    	}

    	rcm.setProperty(RptPaperAnswerConstant.BY_TYPE, new Integer(byType));
    	
		Set idSet = new HashSet();
		for (int i = 0; i < prmtSellProjects.length; i++) {
			if (prmtSellProjects[i].getValue() != null) {
				idSet.add(((CoreBaseInfo) prmtSellProjects[i].getValue())
						.getId().toString());
			}
		}
    	
    	rcm.setProperty(RptPaperAnswerConstant.SELL_PROJECT_ID_SET, idSet);
    	
    	rcm.setProperty(RptPaperAnswerConstant.PAPER, prmtPaper.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.QUESTION1, prmtQuestion1.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.QUESTION2, prmtQuestion2.getValue());
    	
    	return rcm.toRptParams();
	}
	
	public void setCustomCondition(RptParams params) {
		RptConditionManager rcm=new RptConditionManager(params); 
		rcm.restoreAllStatus(this); //设置控件值
	}
	
	protected void chkIsShowAll_itemStateChanged(ItemEvent e) throws Exception {
		super.chkIsShowAll_itemStateChanged(e);
		initControlByDateType();
	}
	
	protected void btnByDate_actionPerformed(ActionEvent e) throws Exception {
		super.btnByDate_actionPerformed(e);
		initControlByDateType();
	}
	
	protected void btnByMonth_actionPerformed(ActionEvent e) throws Exception {
		super.btnByMonth_actionPerformed(e);
		initControlByDateType();
	}
	
	protected void btnBySeason_actionPerformed(ActionEvent e) throws Exception {
		super.btnBySeason_actionPerformed(e);
		initControlByDateType();
	}
	
	protected void btnByYear_actionPerformed(ActionEvent e) throws Exception {
		super.btnByYear_actionPerformed(e);
		initControlByDateType();
	}
	
	private void initControlByDateType() {

		if (this.btnByDate.isSelected()) {
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblSeasonFrom.setVisible(false);
			this.lblSeasonTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.spiSeasonFrom.setVisible(false);
			this.spiSeasonTo.setVisible(false);
			this.contDateFrom.setVisible(true);
			this.contDateTo.setVisible(true);

		} else if (this.btnByMonth.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblSeasonFrom.setVisible(false);
			this.lblSeasonTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.spiSeasonFrom.setVisible(false);
			this.spiSeasonTo.setVisible(false);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);

		} else if (this.btnBySeason.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblSeasonFrom.setVisible(true);
			this.lblSeasonTo.setVisible(true);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.spiSeasonFrom.setVisible(true);
			this.spiSeasonTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
		} else if (this.btnByYear.isSelected()) {
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblSeasonFrom.setVisible(false);
			this.lblSeasonTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.spiSeasonFrom.setVisible(false);
			this.spiSeasonTo.setVisible(false);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
		}
		if (this.chkIsShowAll.isSelected()) {
			this.btnByDate.setEnabled(false);
			this.btnByMonth.setEnabled(false);
			this.btnBySeason.setEnabled(false);
			this.btnByYear.setEnabled(false);
			this.spiYearFrom.setEnabled(false);
			this.spiYearTo.setEnabled(false);
			this.spiMonthFrom.setEnabled(false);
			this.spiMonthTo.setEnabled(false);
			this.spiSeasonFrom.setEnabled(false);
			this.spiSeasonTo.setEnabled(false);
			this.dpDateFrom.setEnabled(false);
			this.dpDateTo.setEnabled(false);
		} else {
			this.btnByDate.setEnabled(true);
			this.btnByMonth.setEnabled(true);
			this.btnBySeason.setEnabled(true);
			this.btnByYear.setEnabled(true);
			this.spiYearFrom.setEnabled(true);
			this.spiYearTo.setEnabled(true);
			this.spiMonthFrom.setEnabled(true);
			this.spiMonthTo.setEnabled(true);
			this.spiSeasonFrom.setEnabled(true);
			this.spiSeasonTo.setEnabled(true);
			this.dpDateFrom.setEnabled(true);
			this.dpDateTo.setEnabled(true);
		}
	}
	
	protected void prmtPaper_dataChanged(DataChangeEvent e) throws Exception {
		super.prmtPaper_dataChanged(e);
		if (e.getNewValue() != null) {
			prmtQuestion1.setValue(null);
			prmtQuestion2.setValue(null);
			
			initPrmtQuestion(prmtQuestion1);
			initPrmtQuestion(prmtQuestion2);
		}
	}
	
	protected void prmtQuestion1_willCommit(CommitEvent e) throws Exception {
		super.prmtQuestion1_willCommit(e);
		e.setCanceled(!checkPrmtPaper());
	}

	protected void prmtQuestion1_willShow(SelectorEvent e) throws Exception {
		super.prmtQuestion1_willShow(e);
		e.setCanceled(!checkPrmtPaper());
	}

	protected void prmtQuestion2_willCommit(CommitEvent e) throws Exception {
		super.prmtQuestion2_willCommit(e);
		e.setCanceled(!checkPrmtPaper());
	}

	protected void prmtQuestion2_willShow(SelectorEvent e) throws Exception {
		super.prmtQuestion2_willShow(e);
		e.setCanceled(!checkPrmtPaper());
	}
	
	private boolean checkPrmtPaper(){
		if(prmtPaper.getValue() == null){
			MsgBox.showInfo("请先设置调查问卷");
			prmtPaper.requestFocus();
			return false;
		}
		return true;
	}

	public boolean verify() {
		if(prmtPaper.getValue() == null){
			MsgBox.showInfo("调查问卷不能为空");
			prmtPaper.requestFocus();
			return false;
		}
		
		if(prmtQuestion1.getValue() == null){
			MsgBox.showInfo("问卷问题一不能为空");
			prmtQuestion1.requestFocus();
			return false;
		}
		
		if(prmtQuestion2.getValue() != null){
			CoreBaseInfo info1 = (CoreBaseInfo) prmtQuestion1.getValue();
			CoreBaseInfo info2 = (CoreBaseInfo) prmtQuestion2.getValue();
			if(info1.getId().equals(info2.getId())){
				MsgBox.showInfo("问卷问题一和问卷问题二相同，请重新选择");
				prmtQuestion2.requestFocus();
				return false;
			}
		}
		
		if(!chkIsShowAll.isSelected()){
			if (btnByDate.isSelected()) {
				if (dpDateFrom.getValue() == null) {
					MsgBox.showInfo("统计开始时间不能为空");
					dpDateFrom.requestFocus();
					return false;
				}
				if (dpDateTo.getValue() == null) {
					MsgBox.showInfo("统计结束时间不能为空");
					dpDateTo.requestFocus();
					return false;
				}
			}
			Date dateFrom = getDateFrom();
			Date dateTo = getDateTo();
			if(!dateFrom.before(dateTo)){
				MsgBox.showInfo("结束日期必須大于开始日期!");
				return false;
			}
		}
		
		return true;
	}
	
	private Date getDateFrom(){
		Date date = new Date();
		if(btnByDate.isSelected()){
			date = (Date) dpDateFrom.getValue();
		}else if(btnByMonth.isSelected()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ((Integer)spiYearFrom.getValue()).intValue());
			cal.set(Calendar.MONTH, ((Integer)spiMonthFrom.getValue()).intValue() - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}else if(btnBySeason.isSelected()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ((Integer)spiYearFrom.getValue()).intValue());
			cal.set(Calendar.MONTH, (((Integer)spiSeasonFrom.getValue()).intValue() - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}else if(btnByYear.isSelected()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ((Integer)spiYearFrom.getValue()).intValue());
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		
		return DateTimeUtils.truncateDate(date);
	}
	
	private Date getDateTo(){
		Date date = new Date();
		if(btnByDate.isSelected()){
			date = (Date) dpDateTo.getValue();
		}else if(btnByMonth.isSelected()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ((Integer)spiYearTo.getValue()).intValue());
			cal.set(Calendar.MONTH, ((Integer)spiMonthTo.getValue()).intValue());
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}else if(btnBySeason.isSelected()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ((Integer)spiYearTo.getValue()).intValue());
			cal.set(Calendar.MONTH, ((Integer)spiSeasonTo.getValue()).intValue() * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}else if(btnByYear.isSelected()){
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, ((Integer)spiYearTo.getValue()).intValue() + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		
		return DateTimeUtils.truncateDate(date);
	}
}