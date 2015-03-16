/**
 * output package name
 */
package com.kingdee.eas.fdc.finance.client;

import java.awt.event.ActionEvent;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.finance.FDCAdjustBillFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * 调整单查询
 */
public class FDCAdjustBillFullListUI extends AbstractFDCAdjustBillFullListUI
{
    private static final Logger logger = CoreUIObject.getLogger(FDCAdjustBillFullListUI.class);
    
    private CustomerQueryPanel filterUI = null;

	private CommonQueryDialog commonQueryDialog = null;
	
	protected EntityViewInfo view;

    /**
     * output class constructor
     */
    public FDCAdjustBillFullListUI() throws Exception
    {
        super();
    }
    
    public void onLoad() throws Exception {
    	if((!SysContext.getSysContext().getCurrentOrgUnit().isIsCompanyOrgUnit())&&(SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()))   	
    	{
    		MsgBox.showWarning(this, PaymentBillClientUtils.getRes("CostUnit"));
    		SysUtil.abort();
    	}
    	if (dataObjects != null && dataObjects.size() > 0) {
			java.util.Iterator keysIterater = dataObjects.keySet().iterator();

			while (keysIterater.hasNext()) {
				String key = (String) keysIterater.next();
				setDataObject((AbstractObjectValue) dataObjects.get(key));
			}
			
			setIsNeedDefaultFilter(false);
			this.actionQuery.setEnabled(false);
			this.actionQuery.setVisible(false);
		}
    	super.onLoad();
    	actionAddNew.setEnabled(false);
		actionEdit.setEnabled(false);
		actionRemove.setEnabled(false);
		actionCreateTo.setEnabled(false);
		actionTraceDown.setEnabled(false);
		actionTraceUp.setEnabled(false);
		actionCopyTo.setEnabled(false);
		actionVoucher.setEnabled(false);
		actionDelVoucher.setEnabled(false);
		actionAuditResult.setEnabled(false);
		actionMultiapprove.setEnabled(false);
		actionNextPerson.setEnabled(false);
		actionAttachment.setEnabled(false);
		
		actionAddNew.setVisible(false);
		actionEdit.setVisible(false);
		actionRemove.setVisible(false);
		actionCreateTo.setVisible(false);
		actionTraceDown.setVisible(false);
		actionTraceUp.setVisible(false);
		actionCopyTo.setVisible(false);
		actionVoucher.setVisible(false);
		actionDelVoucher.setVisible(false);
		actionAuditResult.setVisible(false);
		actionMultiapprove.setVisible(false);
		actionNextPerson.setVisible(false);
		actionWorkFlowG.setVisible(false);
		menuEdit.setVisible(false);
		menuBiz.setVisible(false);
		menuWorkFlow.setVisible(false);
		actionAttachment.setVisible(false);
		
		
    }
    protected String[] getLocateNames()
    {
    	String[] locateNames = new String[2];
        locateNames[0] = "number";
        locateNames[1] = "voucher.number";
        return locateNames;
    }

    protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new FDCAdjustBillFullFilterUI(this,
						this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}
	
	protected ICoreBase getBizInterface() throws Exception {
		return FDCAdjustBillFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return FDCAdjustBillEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	/**
	 * 
	 * 初始化默认过滤条件
	 * 
	 * @return 如果重载了（即做了初始化动作），请返回true;默认返回false;
	 */
	protected boolean initDefaultFilter() {
		//无需求先默认
		return super.initDefaultFilter();
	}
	public void afterActionPerformed(ActionEvent e) {
		super.afterActionPerformed(e);//试试注释这句什么效果:)
	}
	protected void initWorkButton() {
		super.initWorkButton();//试试注释这句什么效果:)
	}
	protected void updateButtonStatus() {
		super.updateButtonStatus();
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
	}
	protected void refresh(ActionEvent e) throws Exception {
		super.refresh(e);
		FDCClientHelper.setActionEnable(new ItemAction[] { actionVoucher,
				actionDelVoucher,actionQueryScheme,actionAttachment}, false);
	}
	protected boolean isIgnoreCUFilter() {
		// TODO Auto-generated method stub
		return super.isIgnoreCUFilter();
	}

}