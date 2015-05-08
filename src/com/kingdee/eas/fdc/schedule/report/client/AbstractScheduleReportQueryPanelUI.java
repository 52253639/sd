/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.report.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractScheduleReportQueryPanelUI extends com.kingdee.eas.base.commonquery.client.CustomerQueryPanel
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractScheduleReportQueryPanelUI.class);
    protected com.kingdee.bos.ctrl.swing.KDDatePicker startDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker endDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField orgIDList;
    protected com.kingdee.bos.ctrl.swing.KDLabel selectRangeLabel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton selectOrgs;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea checkedIdsArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField projectNameTxtField;
    protected actionSelectOrg actionSelectOrg = null;
    /**
     * output class constructor
     */
    public AbstractScheduleReportQueryPanelUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractScheduleReportQueryPanelUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSelectOrg
        this.actionSelectOrg = new actionSelectOrg(this);
        getActionManager().registerAction("actionSelectOrg", actionSelectOrg);
         this.actionSelectOrg.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.startDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.endDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.orgIDList = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.selectRangeLabel = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.selectOrgs = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.checkedIdsArea = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.projectNameTxtField = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.startDate.setName("startDate");
        this.endDate.setName("endDate");
        this.orgIDList.setName("orgIDList");
        this.selectRangeLabel.setName("selectRangeLabel");
        this.selectOrgs.setName("selectOrgs");
        this.kDLabel1.setName("kDLabel1");
        this.kDLabel2.setName("kDLabel2");
        this.checkedIdsArea.setName("checkedIdsArea");
        this.projectNameTxtField.setName("projectNameTxtField");
        // CustomerQueryPanel
        // startDate
        this.startDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    startDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // endDate
        // orgIDList		
        this.orgIDList.setEnabled(false);
        this.orgIDList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    orgIDList_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // selectRangeLabel		
        this.selectRangeLabel.setText(resHelper.getString("selectRangeLabel.text"));
        // selectOrgs
        this.selectOrgs.setAction((IItemAction)ActionProxyFactory.getProxy(actionSelectOrg, new Class[] { IItemAction.class }, getServiceContext()));		
        this.selectOrgs.setText(resHelper.getString("selectOrgs.text"));
        this.selectOrgs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    selectOrgs_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // checkedIdsArea		
        this.checkedIdsArea.setEnabled(false);		
        this.checkedIdsArea.setVisible(false);
        // projectNameTxtField		
        this.projectNameTxtField.setEnabled(false);		
        this.projectNameTxtField.setVisible(false);
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 430, 300));
        this.setLayout(null);
        startDate.setBounds(new Rectangle(89, 48, 237, 19));
        this.add(startDate, null);
        endDate.setBounds(new Rectangle(89, 82, 237, 19));
        this.add(endDate, null);
        orgIDList.setBounds(new Rectangle(89, 13, 237, 19));
        this.add(orgIDList, null);
        selectRangeLabel.setBounds(new Rectangle(5, 13, 76, 28));
        this.add(selectRangeLabel, null);
        selectOrgs.setBounds(new Rectangle(343, 13, 83, 25));
        this.add(selectOrgs, null);
        kDLabel1.setBounds(new Rectangle(5, 48, 87, 19));
        this.add(kDLabel1, null);
        kDLabel2.setBounds(new Rectangle(343, 48, 83, 19));
        this.add(kDLabel2, null);
        checkedIdsArea.setBounds(new Rectangle(95, 132, 231, 93));
        this.add(checkedIdsArea, null);
        projectNameTxtField.setBounds(new Rectangle(99, 239, 170, 19));
        this.add(projectNameTxtField, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.report.app.ScheduleReportQueryPanelUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output startDate_dataChanged method
     */
    protected void startDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output orgIDList_actionPerformed method
     */
    protected void orgIDList_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output selectOrgs_actionPerformed method
     */
    protected void selectOrgs_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSelectOrg_actionPerformed method
     */
    public void actionSelectOrg_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareactionSelectOrg(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareactionSelectOrg() {
    	return false;
    }

    /**
     * output actionSelectOrg class
     */     
    protected class actionSelectOrg extends ItemAction {     
    
        public actionSelectOrg()
        {
            this(null);
        }

        public actionSelectOrg(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("actionSelectOrg.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSelectOrg.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("actionSelectOrg.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractScheduleReportQueryPanelUI.this, "actionSelectOrg", "actionSelectOrg_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.report.client", "ScheduleReportQueryPanelUI");
    }




}