/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.FDCTreeBaseDataInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.RESchTemplateCatagoryInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * 版权：		金蝶国际软件集团有限公司版权所有		 	
 * 描述：		计划管理任务列表编辑界面
 *		
 * @author		罗小龙
 * @version		EAS7.0		
 * @createDate	2011-08-04 
 * @see
 */
public class RESchTemplateEditOldUI extends AbstractRESchTemplateEditOldUI
{
	//缺省版本号
	private static final long serialVersionUID = 1L;

	//日志
    private static final Logger logger = CoreUIObject.getLogger(RESchTemplateEditUI.class);
    
    //编码
    private static final String NUMBER = "number";
    
    /**
     * output class constructor
     */
    public RESchTemplateEditOldUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
    	super.storeFields(); 		
		Object ownerObj = getUIContext().get("Owner");
		if(null != ownerObj && ownerObj instanceof FDCScheduleBaseEditUI && null != this.editData.getName() && !"".equals(this.editData.getName().toString())){
			FDCScheduleBaseEditUI fDCScheduleBaseEditUI = (FDCScheduleBaseEditUI)ownerObj;
			fDCScheduleBaseEditUI.exportPlanTemplate(this.editData);
		}
		initOldData(editData);
    }
//    protected void prmtParent_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
//    {
//    	try {
//			txtNumber.setText(getNewNumber(((RESchTemplateCatagoryInfo)e.getNewValue()).getId().toString()));
//		} catch (BOSException e1) {
//			e1.printStackTrace();
//		} catch (SQLException e1) {
//			e1.printStackTrace();
//		}
//    }
    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	Pattern tDouble = Pattern.compile("([0-9]{1,4})");
    	if(this.txtNumber.getText().length() == 0){
    		FDCMsgBox.showInfo("编码不能为空！");
    		return ;
    	}
		if (!tDouble.matcher(this.txtNumber.getText().trim()).matches()) {
			FDCMsgBox.showInfo("计划模板编码只允许录入不超过4个的数字！");
		  	return ;	
		}
		if (prmtParent.getValue() == null) {
			FDCMsgBox.showInfo("上级模板不能为空！");
			return;
		}
		if (IsExitNumber(this.txtNumber.getText().trim())){
			FDCMsgBox.showInfo("计划模板编码存在重复！");
			return;
		}
		/* 判断上级模板有没有模板任务，有则不能保存 */
//    	if(!judgeLastTemplateExistIsTask()){
//    		FDCMsgBox.showInfo("上级模板存在模板任务，不能保存！");
//    		return ;
//    	}
    	
    	/* 执行导入计划模板操作 */
//    	this.editData = new RESchTemplateInfo();
//		this.editData.setId(BOSUuid.create((new RESchTemplateInfo()).getBOSType()));
//    	this.editData.setName(this.txtName.getSelectedItemData().toString());
//    	this.editData.setNumber(this.txtNumber.getText());
//    	this.editData.setDescription(this.txtDescription.getText());
//    	this.editData.setCatagory((RESchTemplateCatagoryInfo)this.prmtParent.getValue());
//    	this.editData.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo());
//    	this.editData.setState(FDCBillStateEnum.SAVED);

		super.actionSubmit_actionPerformed(e);
		/* 判断是否需要关闭窗体(如果是从主项计划编制与调整导出计划模板过来的，就需要关闭窗体) */
		Object closeWindowObj = getUIContext().get("isCloseWindow");
		if(null == closeWindowObj){
			return ;
		}
		
		if("true".equals(closeWindowObj.toString())){
			destroyWindow();
		}
    }
    
    protected void doAfterSubmit(IObjectPK pk) throws Exception {
    	super.doAfterSubmit(pk);
    }
  
    
	protected FDCTreeBaseDataInfo getEditData() {
		return editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected IObjectValue createNewData() {
    	this.editData = new RESchTemplateInfo();
		this.editData.setId(BOSUuid.create((new RESchTemplateInfo()).getBOSType()));
       	this.editData.setOrgUnit(SysContext.getSysContext().getCurrentFIUnit().castToFullOrgUnitInfo());
    	this.editData.setState(FDCBillStateEnum.SAVED);
		return this.editData;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return RESchTemplateFactory.getRemoteInstance();
	}
	
	
	
	public void loadFields() {
		
		super.loadFields();
		
		//为“模板类型”赋值
		if(null != this.getUIContext().get("templateType")){
			this.comTemplateType.addItem(this.getUIContext().get("templateType"));
			this.comTemplateType.setSelectedIndex(0);
		}
	}

	
	public boolean isModify() {
		return super.isModify();
	}
	
	protected KDTextField getNumberCtrl() {
		return (KDTextField)txtNumber;
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		this.txtName.setMaxLength(100);
		this.txtNumber.setMaxLength(4);
		
		/* 为上级模板加过滤条件 */
		Object templateType = this.comTemplateType.getSelectedItem();
		if(null != templateType && !"".equals(templateType.toString().trim())){
			templateType = String.valueOf("主项计划模板".equals(templateType.toString().trim()) ? "1" : "2");
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.appendFilterItem("templateType", templateType);
			view.setFilter(filter);
			this.prmtParent.setEntityViewInfo(view);
		}
		this.prmtParent.addDataChangeListener(new DataChangeListener(){

			public void dataChanged(DataChangeEvent e) {
				try {
					txtNumber.setText(getNewNumber(((RESchTemplateCatagoryInfo)e.getNewValue()).getId().toString()));
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	/**
	 * @discription  <判断模板类型是否为空>
	 * @author       <Xiaolong Luo>
	 * @createDate   <2011/08/22> <p>
	 * @param        <空>
	 * @return       <返回值描述>
	 * 
	 * modifier      <空> <p>
	 * modifyDate    <空> <p>
	 * modifyInfo	 <空> <p>
	 * @see          <相关的类>
	 */
	public boolean judgeTemplateTypeIsNull(){
		Object templateTypeObj = this.comTemplateType.getSelectedItem();
		if(null == templateTypeObj || "".equals(templateTypeObj.toString().trim())){
			return true;
		}
		return false;
	}
	
	/**
	 * 自动生成编码
	 * */
	private String getNewNumber(String id) throws BOSException, SQLException{
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select top 1 fnumber ");
		sql.appendSql(" from T_SCH_RESchTemplate ");
		sql.appendSql(" where FCATAGORYID=? ");
		if(id!=null){
			sql.addParam(id);
		}else{
			sql.addParam(" ");
		}
		sql.appendSql(" ORDER BY fnumber desc ");

		IRowSet rs = sql.executeQuery();
		String currMaxNumber = null;
		while (rs.next()) {
			currMaxNumber= rs.getString("fnumber");
		}
		if (StringUtils.isEmpty(currMaxNumber)) {
			return "0001";
		}
		String currNumber = null;
        currNumber = String.valueOf(Integer.parseInt(currMaxNumber) + 1);
        if (Integer.parseInt(currMaxNumber) < 9) {
			currNumber = "000" + currNumber;
		}
		return currNumber;
	}
	/**
	 * 自动生成编码
	 * */
	private boolean IsExitNumber(String number) throws BOSException, SQLException{
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select top 1 fnumber ");
		sql.appendSql(" from T_SCH_RESchTemplate ");
		sql.appendSql(" where FNUMBER=? ");
		if(number!=null){
			sql.addParam(number);
		}else{
			sql.addParam(" ");
		}
		IRowSet rs = sql.executeQuery();
		String currMaxNumber = null;
		while (rs.next()) {
			if(rs.getString("fnumber").equals(number)){				
				return true;
			}
		}
		return false;
	}
	public void onShow() throws Exception {
		super.onShow();
		
		/* 设置启用禁用按扭不显示 */
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		
		/* 执行导入计划模板操作 */
		Object ownerObj = getUIContext().get("Owner");
		if(null != ownerObj && ownerObj instanceof FDCScheduleBaseEditUI){
			this.btnAddNew.setVisible(false);
			this.btnEdit.setVisible(false);
			
			if(ownerObj instanceof MainScheduleEditUI){
				this.comTemplateType.removeAllItems();
				this.comTemplateType.addItem(ScheduleTemplateTypeEnum.MainPlanTemplate);
			}else{
				this.comTemplateType.removeAllItems();
				this.comTemplateType.addItem(ScheduleTemplateTypeEnum.OtherPlanTemplate);
			}
		}
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		
		//设置模板类型复选框不可编辑
		this.comTemplateType.setEditable(false);
		this.comTemplateType.setEnabled(false);
		this.comTemplateType.setRequired(true);
		
		/* F7 上级模板 */
		this.prmtParent.setQueryInfo("com.kingdee.eas.fdc.schedule.app.F7RESchTemplateCataQuery");
		this.prmtParent.setEditable(false);
		
		/* 设置编码的最大长度 */
//		this.txtNumber.setMaxLength(4);
		this.txtNumber.setRequired(true);
		this.txtNumber.setBackground(Color.RED);
		this.txtNumber.setEnabled(true);
		
		/* 设置名称的最大长度 */
		this.txtName.setMaxLength(100);
		this.txtName.setRequired(true);
		
		/* 设置描述的最大长度 */
		this.txtDescription.setMaxLength(200);
		
		Object ownerObj = getUIContext().get("Owner");
		if(null != ownerObj && ownerObj instanceof FDCScheduleBaseEditUI){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			if(ownerObj instanceof MainScheduleEditUI){
				filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.MAINPLANTEMPLATE_VALUE);
				filter.getFilterItems().add(new FilterItemInfo("name","主项计划模板",CompareType.NOTEQUALS));
			}else{
				filter.appendFilterItem("templateType", ScheduleTemplateTypeEnum.OTHERPLANTEMPLATE_VALUE);
				filter.getFilterItems().add(new FilterItemInfo("name","专项计划模板",CompareType.NOTEQUALS));
			}
			view.setFilter(filter);
			this.prmtParent.setEntityViewInfo(view);
		}
		
	}
	
	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("state"));
        sic.add(new SelectorItemInfo("templateType"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("catagory.id"));
		sic.add(new SelectorItemInfo("catagory.name"));
		sic.add(new SelectorItemInfo("catagory.number"));
		return sic;
	}
}