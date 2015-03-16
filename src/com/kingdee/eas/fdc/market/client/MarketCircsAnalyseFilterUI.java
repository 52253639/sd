/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import javax.swing.ButtonGroup;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.swing.event.CommitEvent;
import com.kingdee.bos.ctrl.swing.event.SelectorEvent;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.framework.report.util.RptConditionManager;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.hr.rec.util.DateUtil;
import com.kingdee.eas.hr.train.MonthEnum;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class MarketCircsAnalyseFilterUI extends AbstractMarketCircsAnalyseFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketCircsAnalyseFilterUI.class);
    
    /**
     * output class constructor
     */
    public MarketCircsAnalyseFilterUI() throws Exception
    {
        super();
        ButtonGroup bg = new ButtonGroup();
        bg.add(this.rdRoomType);
        bg.add(this.rdDate);
    }
    
    
    public void onLoad() throws Exception {
		// TODO Auto-generated method stub
		super.onLoad();
		
		this.prmtprovince.setEnabled(true);
		this.prmtarea.setEnabled(true);
		String yearValue = new Integer(DateUtil.getYear(DateUtil.getCurrentDate())).toString();
		this.txtSurveyYear.setText(yearValue);
		this.prmtprovince.setRequired(true);
		this.prmtarea.setRequired(true);
		this.prmtprovince.setForeground(new java.awt.Color(0,0,0));	
		this.prmtarea.setForeground(new java.awt.Color(0,0,0));	
		this.txtSurveyYear.setForeground(new java.awt.Color(0,0,0));
		this.surveyMonth.setForeground(new java.awt.Color(0,0,0));
		String monthValue = new Integer(DateUtil.getCurrentDate().getMonth()).toString();
		this.surveyMonth.setSelectedItem(MonthEnum.getEnum(monthValue));
	}


	/**
	 * 判断所属入的年份中是否存在非法字符
	 * @param year
	 * @return
	 */
	private boolean isIlligleChar(String year){
		char ch = ' ';
		for(int i = 0;i<year.length();i++){
			ch = year.charAt(i);
			if(ch == '0' || ch == '1' || ch == '2' || ch == '3' || ch == '4' ||
					ch == '5' || ch == '6' || ch == '7' || ch == '8' || ch == '9'){
				continue;
			}else{
				return true;
			}
		}
		return false;
	}
	
	private boolean setF7filter() {
		// TODO 自动生成方法存根
		//return false;
		if (this.prmtprovince.getData()==null){
			return false;
		}
		else{
			EntityViewInfo evi = new EntityViewInfo();
			FilterInfo filterInfo = new FilterInfo();
			filterInfo.getFilterItems().add(new FilterItemInfo("province.id", ((ProvinceInfo)prmtprovince.getData()).getId(), CompareType.EQUALS));
			evi.setFilter(filterInfo);
			prmtarea.setEntityViewInfo(evi);
			prmtarea.getQueryAgent().resetRuntimeEntityView();
			return true;
		}
	}
	

    protected void prmtarea_willCommit(CommitEvent e) throws Exception {
    	// TODO 自动生成方法存根
		if (!setF7filter()){
			e.setCanceled(true);
			return;
		}
		super.prmtarea_willCommit(e);
	}


	protected void prmtarea_willShow(SelectorEvent e) throws Exception {
		// TODO 自动生成方法存根
		if (!setF7filter()){
			e.setCanceled(true);
			return;
		}
		super.prmtarea_willShow(e);
	}


	/**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output txtSurveyYear_focusLost method
     */
    protected void txtSurveyYear_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
        super.txtSurveyYear_focusLost(e);
    }

    /**
     * output surveyMonth_actionPerformed method
     */
    protected void surveyMonth_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.surveyMonth_actionPerformed(e);
    }

    /**
     * output surveyMonth_itemStateChanged method
     */
    protected void surveyMonth_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.surveyMonth_itemStateChanged(e);
    }

	public RptParams getCustomCondition() {
		RptConditionManager rcm=new RptConditionManager(); 
    	rcm.recordAllStatus(this); //保存控件值 
    	
    	return rcm.toRptParams();
	}

	public void onInit(RptParams initParams) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void setCustomCondition(RptParams params) {
		RptConditionManager rcm=new RptConditionManager(params); 
		rcm.restoreAllStatus(this); //设置控件值
	}

	public boolean verify() {
		// TODO Auto-generated method stub
		if (isIlligleChar(this.txtSurveyYear.getText())){
			MsgBox.showInfo("调查年度填写错误，请核对。");
			return false;
		}
		if (this.prmtprovince.getData()==null){
			MsgBox.showInfo("省不能为空。");
			return false;
		}
		if (this.prmtarea.getData()==null){
			MsgBox.showInfo("市不能为空。");
			return false;
		}
		return true;
	}

}