/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.GTNBizTypeCollection;
import com.kingdee.eas.fdc.schedule.GTNBizTypeInfo;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeFactory;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleTemplateTypeEnum;
import com.kingdee.eas.fdc.schedule.TaskLinkTypeEnum;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * 
 * @(#)						
 * 版权：金蝶国际软件集团有限公司版权所有 描述： 集团管控节点编辑界面
 * 
 * @author 刘新桐
 * @version EAS7.0.5
 * @createDate 2013-4-27
 * @see
 */
public class GlobalTaskNodeEditUI extends AbstractGlobalTaskNodeEditUI
{
	/* 日志 */
    private static final Logger logger = CoreUIObject.getLogger(GlobalTaskNodeEditUI.class);
	private List alreadyExistNumber = new ArrayList();
    public void onLoad() throws Exception {
    	super.onLoad();
    	initAchType();

		//设置启用图标为隐藏，在编辑查看情况下会显示启用图标
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
		this.btnRemove.setVisible(false);
		this.prmtBizType.setEditable(false);
		this.prmtAchType.setEditable(false);
    	this.prmtBizType.setDisplayFormat("$name$");
    	this.prmtBizType.setEditFormat("$name$");

		// 业务类型添加监听-先选择业务类型，根据所选的业务类型选择成果类别。
		DataChangeListener prmtBizTypeDateListener = new DataChangeListener() {
			public void dataChanged(DataChangeEvent e) {
				prmtBizTypeDateListener(e);
			}
		};
		prmtBizType.addDataChangeListener(prmtBizTypeDateListener);
	
    }
    
    private void initAchType(){
    	Object[] taskBizTypes = (Object[]) prmtBizType.getValue();
		if (taskBizTypes != null) {
			for (int i = 0; i < taskBizTypes.length; i++) {
				ScheduleBizTypeInfo item = (ScheduleBizTypeInfo) taskBizTypes[i];
				if (null != item && "阶段性成果".equals(item.getName())) {
					prmtAchType.setEnabled(true);
					prmtAchType.setRequired(true);
					return;
				}
			}
		}
		prmtAchType.setEnabled(false);
		prmtAchType.setRequired(false);
		prmtAchType.setValue(null);
    }
	private void prmtBizTypeDateListener(DataChangeEvent e) {
		initAchType();
	}
	
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {		
		super.actionEdit_actionPerformed(e);
		//序时簿点查看，编辑界面修改 
		onLoad();
		if (prmtBizType.getValue() instanceof Object[]) {
			Object[] typeArray = (Object[]) prmtBizType.getValue();
			if (typeArray == null || typeArray.length==0) {
				prmtAchType.setEnabled(false);
				prmtAchType.setRequired(false);
				prmtAchType.setValue(null);
			} else {
				boolean isRequire = false;
				ScheduleBizTypeInfo bizType = null;
				for (int i = 0; i < typeArray.length; i++) {
					bizType = (ScheduleBizTypeInfo) typeArray[i];
					if (bizType.getId().toString().equals(FDCScheduleConstant.ACHIEVEMANAGER)) {
						isRequire = true;
					}
				}
				prmtAchType.setEnabled(isRequire);
				prmtAchType.setRequired(isRequire);
			}
		}


	}
	
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		//序时簿点查看，编辑界面新增
		onLoad();
		prmtBizType.setValue(null);
		
	}

	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Submit_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString(FrameWorkClientUtils.strResource + "Msg_Edit"));
		setShowMessagePolicy(SHOW_MESSAGE_DEFAULT);
		setIsShowTextOnly(false);
		showMessage();
	}

	/** 主要是为了修改保存后的的提示 **/
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
		setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
		setShowMessagePolicy(0);
		setIsShowTextOnly(false);
		showMessage();
	}
	
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		checkNumber();
		if (prmtAchType.isRequired() && prmtAchType.getValue() == null) {
			FDCMsgBox.showInfo("业务类型为阶段性成果时，成果类别必录！");
			return;
		}
		super.actionSave_actionPerformed(e);

	}
	
	
	public void actionPre_actionPerformed(ActionEvent e) throws Exception {
		super.actionPre_actionPerformed(e);
		onLoad();
	}
	private void checkNumber() {
		// 校验格式是不是数字
		String number = this.txtNumber.getText().trim();
		if (StringUtils.isEmpty(number)) {
			FDCMsgBox.showInfo("编码不能为空！");
			abort();
		}
		Pattern tDouble = Pattern.compile("([0-9]{1,20})");
		if (!tDouble.matcher(number).matches()) {
			FDCMsgBox.showInfo("编码只能输入数字！");
			abort();
		}
		int currNumber = Integer.parseInt(number);
		if (alreadyExistNumber.contains(currNumber)) {
			FDCMsgBox.showInfo("编码已经存在！");
			abort();
		}
	}
	
	public void storeFields() {
		super.storeFields();
		/* 存业务类型的值 */
		while (null != this.editData.getBizType().get(0)) {
			this.editData.getBizType().remove(this.editData.getBizType().get(0));
		}

		Object[] businessTypes = (Object[]) this.prmtBizType.getValue();
		StringBuffer businessTypeDesc = new StringBuffer();
		if (businessTypes != null) {
			for (int k = 0; k < businessTypes.length; k++) {
				GTNBizTypeInfo myGTNBizTypeInfo = new GTNBizTypeInfo();
				if (businessTypes[k] instanceof ScheduleBizTypeInfo) {
					ScheduleBizTypeInfo scheduleBizTypeInfo = (ScheduleBizTypeInfo) businessTypes[k];
					myGTNBizTypeInfo.setBizType(scheduleBizTypeInfo);
					myGTNBizTypeInfo.setId(BOSUuid.create((new GTNBizTypeInfo()).getBOSType()));
					this.editData.getBizType().add(myGTNBizTypeInfo);
					businessTypeDesc.append(scheduleBizTypeInfo.getName()).append(",");
				}
			}

		}
		/* 存入业务类型描述 */
		if (businessTypeDesc.indexOf(",") != -1) {
			this.editData.setBizTypeDesc(businessTypeDesc.substring(0, businessTypeDesc.lastIndexOf(",")));
		} else {
			this.editData.setBizTypeDesc(businessTypeDesc.toString());
		}
		
		initOldData(editData);
	}

    public GlobalTaskNodeEditUI() throws Exception
    {
        super();
    }
    
	public void loadFields() {
		super.loadFields();

		this.prmtBizType.setDisplayFormat("$name$");
    	this.prmtBizType.setEditFormat("$name$");    
    	GlobalTaskNodeListUI myGlobalTaskNodeListUI = (GlobalTaskNodeListUI) this.getUIContext().get("Owner");
    	
		if (null == this.editData.getId()) {
			return;
		}
		
		List objList = new ArrayList();
		try {
			GlobalTaskNodeInfo myGlobalTaskNodeInfo = editData;
			GTNBizTypeCollection myGTNBizTypeCollection = myGlobalTaskNodeInfo.getBizType();
			for (int s = 0; s < myGTNBizTypeCollection.size(); s++) {
				objList.add(myGTNBizTypeCollection.get(s).getBizType());
			}
			this.prmtBizType.setValue(objList.toArray());

		} catch (Exception e1) {
			logger.error(e1);
		}
	}
	
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return txtName;
	}

	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	protected IObjectValue createNewData() {
		GlobalTaskNodeInfo globalTaskNodeInfo = new GlobalTaskNodeInfo();
		//这个在loadfields里搞的。
		try {
			globalTaskNodeInfo.setNumber(getNewNumber());
		} catch (BOSException e) {
			e.printStackTrace();
			abort();
		} catch (SQLException e) {
			e.printStackTrace();
			abort();
		}

		globalTaskNodeInfo.setIsEnabled(isEnabled);
		return globalTaskNodeInfo;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return GlobalTaskNodeFactory.getRemoteInstance();
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("isBuidingPlan"));
		sic.add(new SelectorItemInfo("isOperationPlan"));
		sic.add(new SelectorItemInfo("description"));
		sic.add(new SelectorItemInfo("name"));
		sic.add(new SelectorItemInfo("number"));
		sic.add(new SelectorItemInfo("taskType"));
		sic.add(new SelectorItemInfo("achType.id"));
		sic.add(new SelectorItemInfo("achType.name"));
		sic.add(new SelectorItemInfo("achType.number"));
		sic.add(new SelectorItemInfo("bizType.bizType.id"));
		sic.add(new SelectorItemInfo("bizType.bizType.name"));
		sic.add(new SelectorItemInfo("bizType.bizType.number"));
		return sic;
	}
	/**
	 * 自动生成编码
	 * */
	private String getNewNumber() throws BOSException, SQLException{
		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select top 1 fnumber ");
		sql.appendSql(" from T_SCH_GlobalTaskNode ORDER BY fnumber desc");
		IRowSet rs = sql.executeQuery();
		String currMaxNumber = null;
		while (rs.next()) {
			currMaxNumber= rs.getString("fnumber");
		}
		if (StringUtils.isEmpty(currMaxNumber)) {
			return "01";
		}
		String currNumber = null;
        currNumber = String.valueOf(Integer.parseInt(currMaxNumber) + 1);
        if (Integer.parseInt(currMaxNumber) < 9) {
			currNumber = "0" + currNumber;
		}
		return currNumber;
	}

}