/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.report;

import java.awt.Color;
import java.awt.Window;
import java.awt.event.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.swing.KDRadioButton;
import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.CRMConstants;
import com.kingdee.eas.fdc.basecrm.client.NewCommerceHelper;
import com.kingdee.eas.fdc.market.QuestionPaperDefineInfo;
import com.kingdee.eas.fdc.market.RptPaperAnswerConstant;
import com.kingdee.eas.fdc.market.client.ChannelTypeListUI;
import com.kingdee.eas.fdc.sellhouse.CommerceChanceAssistantInfo;
import com.kingdee.eas.fdc.sellhouse.MarketingUnitFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectCollection;
import com.kingdee.eas.fdc.sellhouse.SellProjectFactory;
import com.kingdee.eas.fdc.sellhouse.client.CommerceHelper;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fm.common.ContextHelperFactory;
import com.kingdee.eas.fm.common.DateHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.rptclient.newrpt.util.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class QuestionPaperReportFilterUI extends AbstractQuestionPaperReportFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuestionPaperReportFilterUI.class);
    
private boolean isLoaded = false;
	
	public QuestionPaperReportFilterUI() throws Exception {
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
	
	protected void init() throws Exception {
		
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
		initPrmtPaper(prmtPaper);
		initPrmtQuestion(prmtQuestion1);
		initPrmtQuestion(prmtQuestion2);
		
		String psql="";
		try {
			psql = NewCommerceHelper.getPermitProjectIdSql(null);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		FilterInfo filter = new FilterInfo();
		EntityViewInfo evi = new EntityViewInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.name", "居住区域"));
		filter.getFilterItems().add(new FilterItemInfo("project.id",psql ,CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("project.id",null));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),CompareType.EQUALS));
		
		filter.setMaskString("#0 and (#1 or (#2 and #3) )");
		
		evi.setFilter(filter);
		prmtStayArea.setEntityViewInfo(evi);
		prmtStayArea.setEnabledMultiSelection(true);
		prmtStayArea.setDisplayFormat("$name$");
		prmtStayArea.setEditFormat("$number$");
		prmtStayArea.setCommitFormat("$number$");
		
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("group.name", "工作区域"));
		filter.getFilterItems().add(new FilterItemInfo("project.id", psql,CompareType.INNER));
		filter.getFilterItems().add(new FilterItemInfo("project.id",null));
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id", SysContext.getSysContext().getCurrentOrgUnit().getId().toString(),CompareType.EQUALS));
		
		filter.setMaskString("#0 and (#1 or (#2 and #3) )");
		evi.setFilter(filter);
		prmtWorkArea.setEntityViewInfo(evi);
		prmtWorkArea.setEnabledMultiSelection(true);
		prmtWorkArea.setDisplayFormat("$name$");
		prmtWorkArea.setEditFormat("$number$");
		prmtWorkArea.setCommitFormat("$number$");
		
		evi = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sellProject.id", psql,CompareType.INNER));
		evi.setFilter(filter);
		prmtIntentionType.setEntityViewInfo(evi);
		prmtIntentionType.setEnabledMultiSelection(true);
		prmtIntentionType.setDisplayFormat("$name$");
		prmtIntentionType.setEditFormat("$number$");
		prmtIntentionType.setCommitFormat("$number$");
		
		evi= new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name",null,CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",SysContext.getSysContext().getCurrentCtrlUnit().getId().toString()));
		evi.setFilter(filter);
		
		prmtClassify.setEntityViewInfo(evi);
		prmtClassify.setEnabledMultiSelection(true);
		prmtClassify.setDisplayFormat("$name$");
		prmtClassify.setEditFormat("$number$");
		prmtClassify.setCommitFormat("$number$");
		
		evi= new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID));
		filter.getFilterItems().add(new FilterItemInfo("CU.id",OrgConstants.DEF_CU_ID));
		evi.setFilter(filter);
		
		prmtCommerceChanceStage.setEntityViewInfo(evi);
		prmtCommerceChanceStage.setEnabledMultiSelection(true);
		prmtCommerceChanceStage.setDisplayFormat("$name$");
		prmtCommerceChanceStage.setEditFormat("$number$");
		prmtCommerceChanceStage.setCommitFormat("$number$");
	}
	
	public void clear() {
		super.clear();
		setDefaultValue();
	}
	
	private void initPrmtSellProject(KDBizPromptBox box) throws EASBizException, BOSException{
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7SellProjectQuery");
		box.setDisplayFormat("$name$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		EntityViewInfo env=NewCommerceHelper.getPermitProjectView();
		env.getFilter().getFilterItems().add(new FilterItemInfo("level","1"));
		box.setEnabledMultiSelection(true);
		box.setEntityViewInfo(env);
	}
	
	
	private void initPrmtPaper(KDBizPromptBox box){
		box.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.QuestionPaperDefineQuery");
		box.setDisplayFormat("$topric$");
		box.setEditFormat("$number$");
		box.setCommitFormat("$number$");
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",SysContext.getSysContext().getCurrentOrgUnit().getId()));
		evi.setFilter(filter);
		box.setEntityViewInfo(evi);
	}
	

	private void initPrmtQuestion(KDBizPromptBox box){
		box.setQueryInfo("com.kingdee.eas.fdc.market.app.F7DocumentSubjectQuery");
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
			sic.add("subjectType");
			sic.add("documentId.id");
			box.setSelectorCollection(sic);
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("documentId.id",paperInfo.getDocumentId()));
			evi.setFilter(filter);
			
			box.setEntityViewInfo(evi);
		}
	}
	protected void setDefaultValue(){
    	dpDateFrom.setValue(DateHelper.getFirstDayOfCurMonth());
    	dpDateTo.setValue(new Date());
    	chkIsShowAll.setSelected(false);
    	btnByDate.setSelected(true);
    	initControlByDateType();
    	
    	
		try {
			UserInfo currUserInfo = SysContext.getSysContext().getCurrentUserInfo();
			String permitSpIdStr = MarketingUnitFactory.getRemoteInstance().getPermitSellProjectIdSql(currUserInfo);
			SellProjectCollection sellProjectColl = SellProjectFactory.getRemoteInstance().getSellProjectCollection(
					"select * where id in ("+  permitSpIdStr  +") and level = 1 order by level,number"); 
			if(sellProjectColl.size()>0){
				Object []sellproject=new Object [1];
				sellproject[0]=sellProjectColl.get(0);
				prmtItem1.setValue(sellproject);
			}else{
				prmtItem1.setValue(null);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
    	
    	prmtCommerceChanceStage.setValue(null);
    	prmtPaper.setValue(null);
    	prmtQuestion1.setValue(null);
    	prmtQuestion2.setValue(null);
    	
    	prmtStayArea.setValue(null);
    	prmtWorkArea.setValue(null);
    	prmtIntentionType.setValue(null);
    	prmtClassify.setValue(null);
    	
    	this.isAll.setSelected(true);
	}

	public void onInit(RptParams initParams) throws Exception {
	}
	
	public RptParams getCustomCondition() {
		RptConditionManager rcm=new RptConditionManager(); 
    	rcm.recordAllStatus(this); //保存控件值 
    	

		rcm.setProperty(RptPaperAnswerConstant.DATE_FROM, getDateFrom());
		rcm.setProperty(RptPaperAnswerConstant.DATE_TO, getDateTo());
    	rcm.setProperty(RptPaperAnswerConstant.IS_SHOW_ALL, Boolean.valueOf(chkIsShowAll.isSelected()));
    	
		Set idSet = new HashSet();
		if(this.prmtItem1.getValue()!=null){
			Object []sellproject=(Object[]) this.prmtItem1.getValue();
			for (int i = 0; i < sellproject.length; i++) {
				idSet.add(((CoreBaseInfo) sellproject[i]).getId().toString());
			}
		}
    	rcm.setProperty(RptPaperAnswerConstant.SELL_PROJECT_ID_SET, idSet);
    	
    	rcm.setProperty(RptPaperAnswerConstant.PAPER, prmtPaper.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.QUESTION1, prmtQuestion1.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.QUESTION2, prmtQuestion2.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.WORKAREA, prmtWorkArea.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.STAYAREA, prmtStayArea.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.INTENTIONTYPE, prmtIntentionType.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.CLASSIFY, prmtClassify.getValue());
    	rcm.setProperty(RptPaperAnswerConstant.COMMERCECHANCESTAGE,prmtCommerceChanceStage.getValue());
    	
    	rcm.setProperty("cbIsAll",Boolean.valueOf(this.isAll.isSelected()));
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
		if(prmtItem1.getValue()==null){
			MsgBox.showInfo("项目不能为空");
			prmtItem1.requestFocus();
			return false;
		}
		if(prmtQuestion2.getValue()!= null&&prmtQuestion1.getValue()!=null){
			CoreBaseInfo info1 = (CoreBaseInfo) prmtQuestion1.getValue();
			CoreBaseInfo info2 = (CoreBaseInfo) prmtQuestion2.getValue();
			if(info1.getId().equals(info2.getId())){
				MsgBox.showInfo("问卷问题一和问卷问题二相同，请重新选择");
				prmtQuestion2.requestFocus();
				return false;
			}
		}
		int count=0;
		if(prmtQuestion1.getValue()!=null){
			count=count+1;
		}
		if(prmtQuestion2.getValue()!=null){
			count=count+1;
		}
		if(prmtWorkArea.getValue()!=null){
			count=count+1;
		}
		if(prmtStayArea.getValue()!=null){
			count=count+1;
		}
		if(prmtIntentionType.getValue()!=null){
			count=count+1;
		}
		if(prmtClassify.getValue()!=null){
			count=count+1;
		}
		if(count>2||count==0){
			MsgBox.showInfo("分析维度条件不能为空并且不能多于2条，请重新选择");
			return false;
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
			if(dateFrom.after(dateTo)){
				MsgBox.showInfo("结束日期必须大于开始日期!");
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
	protected void isAll_actionPerformed(ActionEvent e) throws Exception {
		if(isAll.isSelected()){
			EntityViewInfo evi= new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID));
			filter.getFilterItems().add(new FilterItemInfo("CU.id",OrgConstants.DEF_CU_ID));
			evi.setFilter(filter);
			
			prmtCommerceChanceStage.setEntityViewInfo(evi);
			prmtCommerceChanceStage.setEnabledMultiSelection(true);
			prmtCommerceChanceStage.setDisplayFormat("$name$");
			prmtCommerceChanceStage.setEditFormat("$number$");
			prmtCommerceChanceStage.setCommitFormat("$number$");
			this.panelTime.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), "客户新增时间"));
		}else{
			Set id=new HashSet();
			id.add(CRMConstants.COMMERCECHANCE_STAGE_BOOKING);
			id.add(CRMConstants.COMMERCECHANCE_STAGE_PURCHASE);
			id.add(CRMConstants.COMMERCECHANCE_STAGE_SIGN);
			
			if(prmtCommerceChanceStage.getValue()!=null){
				Object[] value=(Object[])prmtCommerceChanceStage.getValue();
				if(value!=null){
			    	for(int i=0;i<value.length;i++){
			        	if(!id.contains(((CommerceChanceAssistantInfo)value[i]).getId().toString())){
			        		prmtCommerceChanceStage.setValue(null);
			        		break;
			        	}
			        }
				}
			}

			EntityViewInfo evi= new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID));
			filter.getFilterItems().add(new FilterItemInfo("CU.id",OrgConstants.DEF_CU_ID));
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
			
			evi.setFilter(filter);
			
			prmtCommerceChanceStage.setEntityViewInfo(evi);
			prmtCommerceChanceStage.setEnabledMultiSelection(true);
			prmtCommerceChanceStage.setDisplayFormat("$name$");
			prmtCommerceChanceStage.setEditFormat("$number$");
			prmtCommerceChanceStage.setCommitFormat("$number$");
			this.panelTime.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), "客户成交时间"));
		}
	}
	protected void isDeal_actionPerformed(ActionEvent e) throws Exception {
		if(isAll.isSelected()){
			EntityViewInfo evi= new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID));
			filter.getFilterItems().add(new FilterItemInfo("CU.id",OrgConstants.DEF_CU_ID));
			evi.setFilter(filter);
			
			prmtCommerceChanceStage.setEntityViewInfo(evi);
			prmtCommerceChanceStage.setEnabledMultiSelection(true);
			prmtCommerceChanceStage.setDisplayFormat("$name$");
			prmtCommerceChanceStage.setEditFormat("$number$");
			prmtCommerceChanceStage.setCommitFormat("$number$");
			this.panelTime.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), "客户新增时间"));
		}else{
			Set id=new HashSet();
			id.add(CRMConstants.COMMERCECHANCE_STAGE_BOOKING);
			id.add(CRMConstants.COMMERCECHANCE_STAGE_PURCHASE);
			id.add(CRMConstants.COMMERCECHANCE_STAGE_SIGN);
			
			if(prmtCommerceChanceStage.getValue()!=null){
				Object[] value=(Object[])prmtCommerceChanceStage.getValue();
				if(value!=null){
			    	for(int i=0;i<value.length;i++){
			        	if(!id.contains(((CommerceChanceAssistantInfo)value[i]).getId().toString())){
			        		prmtCommerceChanceStage.setValue(null);
			        		break;
			        	}
			        }
				}
			}

			EntityViewInfo evi= new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
			filter.getFilterItems().add(new FilterItemInfo("type.id", CRMConstants.HOPE_STAGE_ID));
			filter.getFilterItems().add(new FilterItemInfo("CU.id",OrgConstants.DEF_CU_ID));
			filter.getFilterItems().add(new FilterItemInfo("id",id,CompareType.INCLUDE));
			
			evi.setFilter(filter);
			
			prmtCommerceChanceStage.setEntityViewInfo(evi);
			prmtCommerceChanceStage.setEnabledMultiSelection(true);
			prmtCommerceChanceStage.setDisplayFormat("$name$");
			prmtCommerceChanceStage.setEditFormat("$number$");
			prmtCommerceChanceStage.setCommitFormat("$number$");
			this.panelTime.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), "客户成交时间"));
		}
	}

}