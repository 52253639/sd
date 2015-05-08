/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.util.client.EASResource;

/**
 * output class name
 */
public class F7RESchTemplateUI extends AbstractF7RESchTemplateUI
{
    private static final Logger logger = CoreUIObject.getLogger(F7RESchTemplateUI.class);
    
    /**
     * output class constructor
     */
    public F7RESchTemplateUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output prmtCatagory_dataChanged method
     */
    protected void prmtCatagory_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	
//    	this.combTemplate
        super.prmtCatagory_dataChanged(e);
        if(e.getNewValue()!=null && e.getNewValue() instanceof RESchTemplateCatagoryInfo){
        	RESchTemplateCollection cols=getRESchTemplate(((RESchTemplateCatagoryInfo)e.getNewValue()).getId().toString());
        	for(int i =0;i < cols.size();i++){
        		this.combTemplate.addItem((cols.get(i).getName()));
        	}
        	
        }
    }
    public static RESchTemplateCollection getRESchTemplate(String catagory) {
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
    	sic.add("name");
    	sic.add("number");
    	sic.add("state");
    	sic.add("id");
    	sic.add("orgUnit.id");
    	sic.add("orgUnit.name");
    	sic.add("orgUnit.number");
    	sic.add("orgUnit.longNumber");
		view.setSelector(sic);
    	FilterInfo filter = new FilterInfo();
		CompanyOrgUnitInfo org = SysContext.getSysContext().getCurrentFIUnit();
		String[] numbers = org.getLongNumber().split("!");
		Set orgSet = new HashSet();
		for (int i = 0; i < numbers.length; i++) {
			orgSet.add(numbers[i]);
		}
		
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.number", orgSet, CompareType.INCLUDE));
    	filter.getFilterItems().add(new FilterItemInfo("catagory.id",catagory));
		SorterItemCollection sorter = new SorterItemCollection();
		SorterItemInfo si = new SorterItemInfo("number");
		si.setSortType(SortType.ASCEND);
		sorter.add(si);
		view.setFilter(filter);
		view.setSorter(sorter);    

		RESchTemplateCollection cols = null;
		try {
			cols = RESchTemplateFactory.getRemoteInstance().getRESchTemplateCollection(view);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return cols;
	}
    /**
     * output prmtCatagory_willShow method
     */
    protected void prmtCatagory_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
        super.prmtCatagory_willShow(e);
    }

    /**
     * output combTemplate_itemStateChanged method
     */
    protected void combTemplate_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
        super.combTemplate_itemStateChanged(e);
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionExportSave_actionPerformed
     */
    public void actionExportSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSave_actionPerformed(e);
    }

    /**
     * output actionExportSelectedSave_actionPerformed
     */
    public void actionExportSelectedSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelectedSave_actionPerformed(e);
    }

    /**
     * output actionKnowStore_actionPerformed
     */
    public void actionKnowStore_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionKnowStore_actionPerformed(e);
    }

    /**
     * output actionAnswer_actionPerformed
     */
    public void actionAnswer_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAnswer_actionPerformed(e);
    }

    /**
     * output actionRemoteAssist_actionPerformed
     */
    public void actionRemoteAssist_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRemoteAssist_actionPerformed(e);
    }

    /**
     * output actionPopupCopy_actionPerformed
     */
    public void actionPopupCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupCopy_actionPerformed(e);
    }
    public void onShow()  throws Exception {
    	super.onShow();
    	this.btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
    	this.btnClearAll.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		this.contTask.addButton(this.btnSelectAll);
		this.contTask.addButton(this.btnClearAll);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.MAINPLANTEMPLATE_VALUE);
		filter.getFilterItems().add(new FilterItemInfo("name","主项计划模板",CompareType.NOTEQUALS));
		filter.appendFilterItem("isLeaf", 1);
		view.setFilter(filter);
		this.prmtCatagory.setEntityViewInfo(view);		
    }
    /**
     * output actionHTMLForMail_actionPerformed
     */
    public void actionHTMLForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForMail_actionPerformed(e);
    }

    /**
     * output actionExcelForMail_actionPerformed
     */
    public void actionExcelForMail_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForMail_actionPerformed(e);
    }

    /**
     * output actionHTMLForRpt_actionPerformed
     */
    public void actionHTMLForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHTMLForRpt_actionPerformed(e);
    }

    /**
     * output actionExcelForRpt_actionPerformed
     */
    public void actionExcelForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExcelForRpt_actionPerformed(e);
    }

    /**
     * output actionLinkForRpt_actionPerformed
     */
    public void actionLinkForRpt_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLinkForRpt_actionPerformed(e);
    }

    /**
     * output actionPopupPaste_actionPerformed
     */
    public void actionPopupPaste_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPopupPaste_actionPerformed(e);
    }

    /**
     * output actionToolBarCustom_actionPerformed
     */
    public void actionToolBarCustom_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionToolBarCustom_actionPerformed(e);
    }

    /**
     * output actionCloudFeed_actionPerformed
     */
    public void actionCloudFeed_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudFeed_actionPerformed(e);
    }

    /**
     * output actionCloudScreen_actionPerformed
     */
    public void actionCloudScreen_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCloudScreen_actionPerformed(e);
    }

    /**
     * output actionSelectAll_actionPerformed
     */
    public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSelectAll_actionPerformed(e);
    }

    /**
     * output actionClearAll_actionPerformed
     */
    public void actionClearAll_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionClearAll_actionPerformed(e);
    }

    /**
     * output actionOk_actionPerformed
     */
    public void actionOk_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOk_actionPerformed(e);
    }

    /**
     * output actionQuit_actionPerformed
     */
    public void actionQuit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionQuit_actionPerformed(e);
    }

}