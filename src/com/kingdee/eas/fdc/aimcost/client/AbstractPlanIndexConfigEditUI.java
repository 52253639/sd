/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

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
public abstract class AbstractPlanIndexConfigEditUI extends com.kingdee.eas.framework.client.TreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPlanIndexConfigEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea bizDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsEdit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFieldType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtFormula;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsProductType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contformulaType;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelect;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProp;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsEntityIndex;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbFieldType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbFormulaType;
    protected com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractPlanIndexConfigEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPlanIndexConfigEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionFirst
        String _tempStr = null;
        actionFirst.setEnabled(true);
        actionFirst.setDaemonRun(false);

        actionFirst.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl <"));
        _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
        actionFirst.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
        actionFirst.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.NAME");
        actionFirst.putValue(ItemAction.NAME, _tempStr);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.bizDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.cbIsEdit = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contFieldType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFormula = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.cbIsProductType = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contformulaType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnSelect = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtProp = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbIsEntityIndex = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbFieldType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbFormulaType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contName.setName("contName");
        this.contLongNumber.setName("contLongNumber");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.kDLabel1.setName("kDLabel1");
        this.bizDescription.setName("bizDescription");
        this.cbIsEdit.setName("cbIsEdit");
        this.contFieldType.setName("contFieldType");
        this.txtFormula.setName("txtFormula");
        this.cbIsProductType.setName("cbIsProductType");
        this.contformulaType.setName("contformulaType");
        this.kDLabel2.setName("kDLabel2");
        this.btnSelect.setName("btnSelect");
        this.txtProp.setName("txtProp");
        this.cbIsEntityIndex.setName("cbIsEntityIndex");
        this.bizName.setName("bizName");
        this.txtLongNumber.setName("txtLongNumber");
        this.cbFieldType.setName("cbFieldType");
        this.cbFormulaType.setName("cbFormulaType");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contLongNumber		
        this.contLongNumber.setBoundLabelText(resHelper.getString("contLongNumber.boundLabelText"));		
        this.contLongNumber.setBoundLabelLength(100);		
        this.contLongNumber.setBoundLabelUnderline(true);
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setVisible(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // bizDescription		
        this.bizDescription.setMaxLength(200);
        // cbIsEdit		
        this.cbIsEdit.setText(resHelper.getString("cbIsEdit.text"));
        this.cbIsEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    cbIsEdit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contFieldType		
        this.contFieldType.setBoundLabelText(resHelper.getString("contFieldType.boundLabelText"));		
        this.contFieldType.setBoundLabelLength(100);		
        this.contFieldType.setBoundLabelUnderline(true);
        // txtFormula		
        this.txtFormula.setText(resHelper.getString("txtFormula.text"));		
        this.txtFormula.setLineWrap(true);		
        this.txtFormula.setWrapStyleWord(true);		
        this.txtFormula.setMaxLength(200);
        // cbIsProductType		
        this.cbIsProductType.setText(resHelper.getString("cbIsProductType.text"));
        // contformulaType		
        this.contformulaType.setBoundLabelText(resHelper.getString("contformulaType.boundLabelText"));		
        this.contformulaType.setBoundLabelLength(100);		
        this.contformulaType.setBoundLabelUnderline(true);
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // btnSelect		
        this.btnSelect.setText(resHelper.getString("btnSelect.text"));
        this.btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelect_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // txtProp		
        this.txtProp.setEnabled(false);
        // cbIsEntityIndex		
        this.cbIsEntityIndex.setText(resHelper.getString("cbIsEntityIndex.text"));
        // bizName		
        this.bizName.setRequired(true);		
        this.bizName.setMaxLength(80);
        // txtLongNumber		
        this.txtLongNumber.setRequired(true);		
        this.txtLongNumber.setMaxLength(80);
        // cbFieldType		
        this.cbFieldType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum").toArray());		
        this.cbFieldType.setRequired(true);
        // cbFormulaType		
        this.cbFormulaType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum").toArray());
        this.cbFormulaType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    cbFormulaType_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
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
        this.setBounds(new Rectangle(10, 10, 600, 350));
        this.setLayout(null);
        contName.setBounds(new Rectangle(319, 7, 270, 19));
        this.add(contName, null);
        contLongNumber.setBounds(new Rectangle(10, 7, 270, 19));
        this.add(contLongNumber, null);
        chkIsEnabled.setBounds(new Rectangle(448, 189, 140, 19));
        this.add(chkIsEnabled, null);
        kDLabel1.setBounds(new Rectangle(10, 196, 100, 19));
        this.add(kDLabel1, null);
        bizDescription.setBounds(new Rectangle(10, 218, 579, 126));
        this.add(bizDescription, null);
        cbIsEdit.setBounds(new Rectangle(319, 29, 97, 19));
        this.add(cbIsEdit, null);
        contFieldType.setBounds(new Rectangle(10, 29, 270, 19));
        this.add(contFieldType, null);
        txtFormula.setBounds(new Rectangle(10, 96, 579, 96));
        this.add(txtFormula, null);
        cbIsProductType.setBounds(new Rectangle(319, 51, 107, 19));
        this.add(cbIsProductType, null);
        contformulaType.setBounds(new Rectangle(10, 51, 270, 19));
        this.add(contformulaType, null);
        kDLabel2.setBounds(new Rectangle(10, 76, 100, 19));
        this.add(kDLabel2, null);
        btnSelect.setBounds(new Rectangle(485, 51, 102, 19));
        this.add(btnSelect, null);
        txtProp.setBounds(new Rectangle(485, 72, 102, 19));
        this.add(txtProp, null);
        cbIsEntityIndex.setBounds(new Rectangle(319, 72, 107, 19));
        this.add(cbIsEntityIndex, null);
        //contName
        contName.setBoundEditor(bizName);
        //contLongNumber
        contLongNumber.setBoundEditor(txtLongNumber);
        //contFieldType
        contFieldType.setBoundEditor(cbFieldType);
        //contformulaType
        contformulaType.setBoundEditor(cbFormulaType);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
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
        menuEdit.add(menuItemReset);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
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
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkIsEnabled, "selected");
		dataBinder.registerBinding("description", String.class, this.bizDescription, "_multiLangItem");
		dataBinder.registerBinding("isEdit", boolean.class, this.cbIsEdit, "selected");
		dataBinder.registerBinding("formula", String.class, this.txtFormula, "text");
		dataBinder.registerBinding("isProductType", boolean.class, this.cbIsProductType, "selected");
		dataBinder.registerBinding("prop", String.class, this.txtProp, "text");
		dataBinder.registerBinding("isEntityIndex", boolean.class, this.cbIsEntityIndex, "selected");
		dataBinder.registerBinding("name", String.class, this.bizName, "_multiLangItem");
		dataBinder.registerBinding("longNumber", String.class, this.txtLongNumber, "text");
		dataBinder.registerBinding("fieldType", com.kingdee.eas.fdc.aimcost.PlanIndexFieldTypeEnum.class, this.cbFieldType, "selectedItem");
		dataBinder.registerBinding("formulaType", com.kingdee.eas.fdc.aimcost.PlanIndexFormulaTypeEnum.class, this.cbFormulaType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.aimcost.app.PlanIndexConfigEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.aimcost.PlanIndexConfigInfo)ov;
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
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEdit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("formula", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isProductType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("prop", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEntityIndex", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fieldType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("formulaType", ValidateHelper.ON_SAVE);    		
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
     * output cbIsEdit_actionPerformed method
     */
    protected void cbIsEdit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnSelect_actionPerformed method
     */
    protected void btnSelect_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output cbFormulaType_itemStateChanged method
     */
    protected void cbFormulaType_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isEdit"));
        sic.add(new SelectorItemInfo("formula"));
        sic.add(new SelectorItemInfo("isProductType"));
        sic.add(new SelectorItemInfo("prop"));
        sic.add(new SelectorItemInfo("isEntityIndex"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("longNumber"));
        sic.add(new SelectorItemInfo("fieldType"));
        sic.add(new SelectorItemInfo("formulaType"));
        return sic;
    }        
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }
	public RequestContext prepareActionFirst(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionFirst(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirst() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.aimcost.client", "PlanIndexConfigEditUI");
    }




}