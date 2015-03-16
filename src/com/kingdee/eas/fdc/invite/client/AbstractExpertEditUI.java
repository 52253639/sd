/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

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
public abstract class AbstractExpertEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractExpertEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExpertType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtExpertType;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox kDCheckBox1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contUser;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtUser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTel;
    protected com.kingdee.eas.fdc.invite.ExpertInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractExpertEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractExpertEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contExpertType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtExpertType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDCheckBox1 = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contUser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtUser = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contName.setName("contName");
        this.txtName.setName("txtName");
        this.contNumber.setName("contNumber");
        this.txtNumber.setName("txtNumber");
        this.contExpertType.setName("contExpertType");
        this.prmtExpertType.setName("prmtExpertType");
        this.kDTextArea1.setName("kDTextArea1");
        this.kDLabel1.setName("kDLabel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.kDCheckBox1.setName("kDCheckBox1");
        this.contUser.setName("contUser");
        this.prmtUser.setName("prmtUser");
        this.contTel.setName("contTel");
        this.txtTel.setName("txtTel");
        // CoreUI
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);
        // txtName		
        this.txtName.setRequired(true);		
        this.txtName.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // contExpertType		
        this.contExpertType.setBoundLabelText(resHelper.getString("contExpertType.boundLabelText"));		
        this.contExpertType.setBoundLabelLength(100);		
        this.contExpertType.setBoundLabelUnderline(true);
        // prmtExpertType		
        this.prmtExpertType.setDisplayFormat("$name$");		
        this.prmtExpertType.setEditFormat("$number$");		
        this.prmtExpertType.setCommitFormat("$id$");		
        this.prmtExpertType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7ExpertTypeQuery");		
        this.prmtExpertType.setEnabled(false);
        // kDTextArea1
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // kDScrollPane1
        // kDCheckBox1		
        this.kDCheckBox1.setText(resHelper.getString("kDCheckBox1.text"));
        // contUser		
        this.contUser.setBoundLabelText(resHelper.getString("contUser.boundLabelText"));		
        this.contUser.setBoundLabelLength(100);		
        this.contUser.setBoundLabelUnderline(true);
        // prmtUser		
        this.prmtUser.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.prmtUser.setCommitFormat("$id$");		
        this.prmtUser.setDisplayFormat("$name$");		
        this.prmtUser.setEditFormat("$number$");
        this.prmtUser.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtUser_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contTel		
        this.contTel.setBoundLabelText(resHelper.getString("contTel.boundLabelText"));		
        this.contTel.setBoundLabelLength(100);		
        this.contTel.setBoundLabelUnderline(true);
        // txtTel		
        this.txtTel.setMaxLength(80);
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
        this.setBounds(new Rectangle(10, 10, 660, 340));
        this.setLayout(null);
        contName.setBounds(new Rectangle(378, 49, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(378, 12, 270, 19));
        this.add(contNumber, null);
        contExpertType.setBounds(new Rectangle(15, 12, 270, 19));
        this.add(contExpertType, null);
        kDLabel1.setBounds(new Rectangle(15, 116, 100, 19));
        this.add(kDLabel1, null);
        kDScrollPane1.setBounds(new Rectangle(17, 139, 630, 189));
        this.add(kDScrollPane1, null);
        kDCheckBox1.setBounds(new Rectangle(582, 86, 76, 19));
        this.add(kDCheckBox1, null);
        contUser.setBounds(new Rectangle(15, 49, 270, 19));
        this.add(contUser, null);
        contTel.setBounds(new Rectangle(15, 84, 270, 19));
        this.add(contTel, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contExpertType
        contExpertType.setBoundEditor(prmtExpertType);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(kDTextArea1, null);
        //contUser
        contUser.setBoundEditor(prmtUser);
        //contTel
        contTel.setBoundEditor(txtTel);

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
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("ExpertType", com.kingdee.eas.fdc.invite.ExpertTypeInfo.class, this.prmtExpertType, "data");
		dataBinder.registerBinding("description", String.class, this.kDTextArea1, "text");
		dataBinder.registerBinding("isEnable", boolean.class, this.kDCheckBox1, "selected");
		dataBinder.registerBinding("User", com.kingdee.eas.base.permission.UserInfo.class, this.prmtUser, "data");
		dataBinder.registerBinding("tel", String.class, this.txtTel, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.invite.app.ExpertEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.invite.ExpertInfo)ov;
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("ExpertType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isEnable", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("User", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tel", ValidateHelper.ON_SAVE);    		
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
     * output prmtUser_dataChanged method
     */
    protected void prmtUser_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("ExpertType.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isEnable"));
        sic.add(new SelectorItemInfo("User.*"));
        sic.add(new SelectorItemInfo("tel"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.invite.client", "ExpertEditUI");
    }




}