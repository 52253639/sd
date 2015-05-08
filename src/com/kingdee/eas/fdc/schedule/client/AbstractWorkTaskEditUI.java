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
public abstract class AbstractWorkTaskEditUI extends com.kingdee.eas.framework.client.TreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractWorkTaskEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLevel;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLevel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEstimateDays;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtEstimateDays;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEstDaysLocked;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contParent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtParent;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea txtDescription;
    protected com.kingdee.eas.fdc.schedule.WorkTaskInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractWorkTaskEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractWorkTaskEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contLevel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLevel = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contEstimateDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtEstimateDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.chkIsEstDaysLocked = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contParent = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtParent = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contLevel.setName("contLevel");
        this.txtLevel.setName("txtLevel");
        this.contEstimateDays.setName("contEstimateDays");
        this.txtEstimateDays.setName("txtEstimateDays");
        this.chkIsEstDaysLocked.setName("chkIsEstDaysLocked");
        this.contParent.setName("contParent");
        this.prmtParent.setName("prmtParent");
        this.txtName.setName("txtName");
        this.kDLabel1.setName("kDLabel1");
        this.txtDescription.setName("txtDescription");
        // CoreUI
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // contLevel		
        this.contLevel.setBoundLabelText(resHelper.getString("contLevel.boundLabelText"));		
        this.contLevel.setBoundLabelLength(100);		
        this.contLevel.setBoundLabelUnderline(true);
        // txtLevel		
        this.txtLevel.setRequired(true);		
        this.txtLevel.setSupportedEmpty(true);
        this.txtLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtLevel_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contEstimateDays		
        this.contEstimateDays.setBoundLabelText(resHelper.getString("contEstimateDays.boundLabelText"));		
        this.contEstimateDays.setBoundLabelLength(100);		
        this.contEstimateDays.setBoundLabelUnderline(true);
        // txtEstimateDays		
        this.txtEstimateDays.setSupportedEmpty(true);
        // chkIsEstDaysLocked		
        this.chkIsEstDaysLocked.setText(resHelper.getString("chkIsEstDaysLocked.text"));		
        this.chkIsEstDaysLocked.setSelectState(1);		
        this.chkIsEstDaysLocked.setVisible(false);
        this.chkIsEstDaysLocked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsEstDaysLocked_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contParent		
        this.contParent.setBoundLabelText(resHelper.getString("contParent.boundLabelText"));		
        this.contParent.setBoundLabelLength(100);		
        this.contParent.setBoundLabelUnderline(true);		
        this.contParent.setEnabled(false);
        // prmtParent		
        this.prmtParent.setEnabled(false);
        // txtName		
        this.txtName.setRequired(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // txtDescription
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {prmtParent,txtNumber,txtName,txtLevel,txtEstimateDays,chkIsEstDaysLocked,txtDescription}));
        this.setFocusCycleRoot(true);
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
        this.setBounds(new Rectangle(10, 10, 290, 270));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contNumber, null);
        contLevel.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contLevel, null);
        contEstimateDays.setBounds(new Rectangle(10, 130, 270, 19));
        this.add(contEstimateDays, null);
        chkIsEstDaysLocked.setBounds(new Rectangle(120, 160, 170, 19));
        this.add(chkIsEstDaysLocked, null);
        contParent.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contParent, null);
        kDLabel1.setBounds(new Rectangle(10, 160, 100, 19));
        this.add(kDLabel1, null);
        txtDescription.setBounds(new Rectangle(10, 180, 270, 70));
        this.add(txtDescription, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contLevel
        contLevel.setBoundEditor(txtLevel);
        //contEstimateDays
        contEstimateDays.setBoundEditor(txtEstimateDays);
        //contParent
        contParent.setBoundEditor(prmtParent);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
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
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("level", int.class, this.txtLevel, "value");
		dataBinder.registerBinding("estimateDays", int.class, this.txtEstimateDays, "value");
		dataBinder.registerBinding("isEstDaysLocked", boolean.class, this.chkIsEstDaysLocked, "selected");
		dataBinder.registerBinding("parent.longNumber", String.class, this.prmtParent, "data");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "_multiLangItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.WorkTaskEditUIHandler";
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
     * output onShow method
     */
    public void onShow() throws Exception
    {
        super.onShow();
        this.prmtParent.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.schedule.WorkTaskInfo)ov;
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
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("level", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("estimateDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEstDaysLocked", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("parent.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output txtLevel_actionPerformed method
     */
    protected void txtLevel_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsEstDaysLocked_actionPerformed method
     */
    protected void chkIsEstDaysLocked_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("level"));
        sic.add(new SelectorItemInfo("estimateDays"));
        sic.add(new SelectorItemInfo("isEstDaysLocked"));
        sic.add(new SelectorItemInfo("parent.longNumber"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "WorkTaskEditUI");
    }




}