/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractSavePlanVersionUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSavePlanVersionUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contVersion;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustReson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdjustDesciption;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtVersion;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAdjustDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAdjustReson;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDesc;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected ActionSave actionSave = null;
    /**
     * output class constructor
     */
    public AbstractSavePlanVersionUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSavePlanVersionUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contVersion = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdjustDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdjustReson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdjustDesciption = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtVersion = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkAdjustDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtAdjustReson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtDesc = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contVersion.setName("contVersion");
        this.contAdjustDate.setName("contAdjustDate");
        this.contAdjustReson.setName("contAdjustReson");
        this.contAdjustDesciption.setName("contAdjustDesciption");
        this.txtVersion.setName("txtVersion");
        this.pkAdjustDate.setName("pkAdjustDate");
        this.prmtAdjustReson.setName("prmtAdjustReson");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtDesc.setName("txtDesc");
        this.btnSave.setName("btnSave");
        // CoreUI		
        this.setPreferredSize(new Dimension(580,180));
        // contVersion		
        this.contVersion.setBoundLabelText(resHelper.getString("contVersion.boundLabelText"));		
        this.contVersion.setBoundLabelLength(100);		
        this.contVersion.setBoundLabelUnderline(true);
        // contAdjustDate		
        this.contAdjustDate.setBoundLabelText(resHelper.getString("contAdjustDate.boundLabelText"));		
        this.contAdjustDate.setBoundLabelLength(100);		
        this.contAdjustDate.setBoundLabelUnderline(true);
        // contAdjustReson		
        this.contAdjustReson.setBoundLabelText(resHelper.getString("contAdjustReson.boundLabelText"));		
        this.contAdjustReson.setBoundLabelLength(100);		
        this.contAdjustReson.setBoundLabelUnderline(true);
        // contAdjustDesciption		
        this.contAdjustDesciption.setBoundLabelText(resHelper.getString("contAdjustDesciption.boundLabelText"));		
        this.contAdjustDesciption.setBoundLabelLength(100);		
        this.contAdjustDesciption.setBoundLabelUnderline(true);
        // txtVersion		
        this.txtVersion.setRequired(true);		
        this.txtVersion.setMaxLength(250);
        // pkAdjustDate		
        this.pkAdjustDate.setRequired(true);
        // prmtAdjustReson		
        this.prmtAdjustReson.setRequired(true);		
        this.prmtAdjustReson.setQueryInfo("com.kingdee.eas.fdc.schedule.app.PlanAdjustReasonQuery");		
        this.prmtAdjustReson.setEditFormat("$name$");		
        this.prmtAdjustReson.setDisplayFormat("$name$");		
        this.prmtAdjustReson.setCommitFormat("$name$");
        // kDScrollPane1
        // txtDesc		
        this.txtDesc.setMaxLength(200);
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));
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
        this.setBounds(new Rectangle(10, 10, 580, 180));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 580, 180));
        contVersion.setBounds(new Rectangle(10, 10, 560, 19));
        this.add(contVersion, new KDLayout.Constraints(10, 10, 560, 19, 0));
        contAdjustDate.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contAdjustDate, new KDLayout.Constraints(10, 40, 270, 19, 0));
        contAdjustReson.setBounds(new Rectangle(300, 40, 270, 19));
        this.add(contAdjustReson, new KDLayout.Constraints(300, 40, 270, 19, 0));
        contAdjustDesciption.setBounds(new Rectangle(10, 70, 560, 100));
        this.add(contAdjustDesciption, new KDLayout.Constraints(10, 70, 560, 100, 0));
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contAdjustDate
        contAdjustDate.setBoundEditor(pkAdjustDate);
        //contAdjustReson
        contAdjustReson.setBoundEditor(prmtAdjustReson);
        //contAdjustDesciption
        contAdjustDesciption.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDesc, null);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnSave);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.SavePlanVersionUIHandler";
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
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSave(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSave() {
    	return false;
    }

    /**
     * output ActionSave class
     */     
    protected class ActionSave extends ItemAction {     
    
        public ActionSave()
        {
            this(null);
        }

        public ActionSave(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSave.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSave.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSavePlanVersionUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "SavePlanVersionUI");
    }




}