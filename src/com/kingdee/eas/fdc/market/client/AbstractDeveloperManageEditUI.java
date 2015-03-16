/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

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
public abstract class AbstractDeveloperManageEditUI extends com.kingdee.eas.fdc.propertymgmt.client.PPMBaseDataEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractDeveloperManageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlinkMan;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlinkTel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contaddress;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contpostCode;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contwebSite;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conteMail;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlicenceNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contlawPerson;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSimpleName;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlinkMan;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlinkTel;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtaddress;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtpostCode;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtwebSite;
    protected com.kingdee.bos.ctrl.swing.KDTextField txteMail;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlicenceNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtlawPerson;
    protected com.kingdee.eas.fdc.market.DeveloperManageInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractDeveloperManageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractDeveloperManageEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlinkMan = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlinkTel = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contaddress = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contpostCode = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contwebSite = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.conteMail = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlicenceNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contlawPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtSimpleName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.txtlinkMan = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlinkTel = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtaddress = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtpostCode = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtwebSite = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txteMail = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlicenceNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtlawPerson = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contlinkMan.setName("contlinkMan");
        this.contlinkTel.setName("contlinkTel");
        this.contaddress.setName("contaddress");
        this.contpostCode.setName("contpostCode");
        this.contwebSite.setName("contwebSite");
        this.conteMail.setName("conteMail");
        this.contlicenceNumber.setName("contlicenceNumber");
        this.contlawPerson.setName("contlawPerson");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.txtSimpleName.setName("txtSimpleName");
        this.kDTextArea1.setName("kDTextArea1");
        this.txtlinkMan.setName("txtlinkMan");
        this.txtlinkTel.setName("txtlinkTel");
        this.txtaddress.setName("txtaddress");
        this.txtpostCode.setName("txtpostCode");
        this.txtwebSite.setName("txtwebSite");
        this.txteMail.setName("txteMail");
        this.txtlicenceNumber.setName("txtlicenceNumber");
        this.txtlawPerson.setName("txtlawPerson");
        // CoreUI		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemPrint.setVisible(false);		
        this.menuItemPrintPreview.setVisible(false);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelAlignment(7);		
        this.kDLabelContainer1.setVisible(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelAlignment(7);		
        this.kDLabelContainer2.setVisible(true);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelAlignment(7);		
        this.kDLabelContainer3.setVisible(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setVisible(true);		
        this.kDLabelContainer4.setForeground(new java.awt.Color(0,0,0));
        // contlinkMan		
        this.contlinkMan.setBoundLabelText(resHelper.getString("contlinkMan.boundLabelText"));		
        this.contlinkMan.setBoundLabelLength(100);		
        this.contlinkMan.setBoundLabelUnderline(true);		
        this.contlinkMan.setVisible(true);		
        this.contlinkMan.setBoundLabelAlignment(7);
        // contlinkTel		
        this.contlinkTel.setBoundLabelText(resHelper.getString("contlinkTel.boundLabelText"));		
        this.contlinkTel.setBoundLabelLength(100);		
        this.contlinkTel.setBoundLabelUnderline(true);		
        this.contlinkTel.setVisible(true);		
        this.contlinkTel.setBoundLabelAlignment(7);
        // contaddress		
        this.contaddress.setBoundLabelText(resHelper.getString("contaddress.boundLabelText"));		
        this.contaddress.setBoundLabelLength(100);		
        this.contaddress.setBoundLabelUnderline(true);		
        this.contaddress.setVisible(true);		
        this.contaddress.setBoundLabelAlignment(7);
        // contpostCode		
        this.contpostCode.setBoundLabelText(resHelper.getString("contpostCode.boundLabelText"));		
        this.contpostCode.setBoundLabelLength(100);		
        this.contpostCode.setBoundLabelUnderline(true);		
        this.contpostCode.setVisible(true);		
        this.contpostCode.setBoundLabelAlignment(7);
        // contwebSite		
        this.contwebSite.setBoundLabelText(resHelper.getString("contwebSite.boundLabelText"));		
        this.contwebSite.setBoundLabelLength(100);		
        this.contwebSite.setBoundLabelUnderline(true);		
        this.contwebSite.setVisible(true);		
        this.contwebSite.setForeground(new java.awt.Color(0,0,0));		
        this.contwebSite.setBoundLabelAlignment(7);
        // conteMail		
        this.conteMail.setBoundLabelText(resHelper.getString("conteMail.boundLabelText"));		
        this.conteMail.setBoundLabelLength(100);		
        this.conteMail.setBoundLabelUnderline(true);		
        this.conteMail.setVisible(true);		
        this.conteMail.setBoundLabelAlignment(7);
        // contlicenceNumber		
        this.contlicenceNumber.setBoundLabelText(resHelper.getString("contlicenceNumber.boundLabelText"));		
        this.contlicenceNumber.setBoundLabelLength(100);		
        this.contlicenceNumber.setBoundLabelUnderline(true);		
        this.contlicenceNumber.setVisible(true);		
        this.contlicenceNumber.setBoundLabelAlignment(7);		
        this.contlicenceNumber.setForeground(new java.awt.Color(0,0,0));
        // contlawPerson		
        this.contlawPerson.setBoundLabelText(resHelper.getString("contlawPerson.boundLabelText"));		
        this.contlawPerson.setBoundLabelLength(100);		
        this.contlawPerson.setBoundLabelUnderline(true);		
        this.contlawPerson.setVisible(true);		
        this.contlawPerson.setForeground(new java.awt.Color(0,0,0));		
        this.contlawPerson.setBoundLabelAlignment(7);
        // txtNumber		
        this.txtNumber.setMaxLength(80);		
        this.txtNumber.setVisible(true);		
        this.txtNumber.setEnabled(true);		
        this.txtNumber.setHorizontalAlignment(2);		
        this.txtNumber.setRequired(true);
        // txtName		
        this.txtName.setVisible(true);		
        this.txtName.setEnabled(true);		
        this.txtName.setRequired(true);		
        this.txtName.setMaxLength(255);
        // txtSimpleName		
        this.txtSimpleName.setMaxLength(80);		
        this.txtSimpleName.setVisible(true);		
        this.txtSimpleName.setEnabled(true);		
        this.txtSimpleName.setHorizontalAlignment(2);		
        this.txtSimpleName.setRequired(false);
        // kDTextArea1		
        this.kDTextArea1.setMaxLength(255);
        // txtlinkMan		
        this.txtlinkMan.setVisible(true);		
        this.txtlinkMan.setHorizontalAlignment(2);		
        this.txtlinkMan.setMaxLength(100);		
        this.txtlinkMan.setRequired(false);		
        this.txtlinkMan.setEnabled(true);
        // txtlinkTel		
        this.txtlinkTel.setVisible(true);		
        this.txtlinkTel.setHorizontalAlignment(2);		
        this.txtlinkTel.setMaxLength(100);		
        this.txtlinkTel.setRequired(false);		
        this.txtlinkTel.setEnabled(true);
        // txtaddress		
        this.txtaddress.setVisible(true);		
        this.txtaddress.setHorizontalAlignment(2);		
        this.txtaddress.setMaxLength(100);		
        this.txtaddress.setRequired(false);		
        this.txtaddress.setEnabled(true);
        // txtpostCode		
        this.txtpostCode.setVisible(true);		
        this.txtpostCode.setHorizontalAlignment(2);		
        this.txtpostCode.setMaxLength(100);		
        this.txtpostCode.setRequired(false);		
        this.txtpostCode.setEnabled(true);
        // txtwebSite		
        this.txtwebSite.setVisible(true);		
        this.txtwebSite.setHorizontalAlignment(2);		
        this.txtwebSite.setForeground(new java.awt.Color(0,0,0));		
        this.txtwebSite.setMaxLength(100);		
        this.txtwebSite.setRequired(false);		
        this.txtwebSite.setEnabled(true);
        // txteMail		
        this.txteMail.setVisible(true);		
        this.txteMail.setHorizontalAlignment(2);		
        this.txteMail.setMaxLength(100);		
        this.txteMail.setRequired(false);		
        this.txteMail.setEnabled(true);
        // txtlicenceNumber		
        this.txtlicenceNumber.setVisible(true);		
        this.txtlicenceNumber.setHorizontalAlignment(2);		
        this.txtlicenceNumber.setMaxLength(100);		
        this.txtlicenceNumber.setRequired(false);		
        this.txtlicenceNumber.setEnabled(true);		
        this.txtlicenceNumber.setForeground(new java.awt.Color(0,0,0));
        // txtlawPerson		
        this.txtlawPerson.setVisible(true);		
        this.txtlawPerson.setHorizontalAlignment(2);		
        this.txtlawPerson.setForeground(new java.awt.Color(0,0,0));		
        this.txtlawPerson.setMaxLength(100);		
        this.txtlawPerson.setRequired(false);		
        this.txtlawPerson.setEnabled(true);
        this.setFocusTraversalPolicy(new com.kingdee.bos.ui.UIFocusTraversalPolicy(new java.awt.Component[] {txtNumber,txtName,txtSimpleName,txtlinkMan,txtlinkTel,txtaddress,txtpostCode,txtwebSite,txteMail,txtlicenceNumber,txtlawPerson,kDTextArea1}));
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
        this.setBounds(new Rectangle(0, 0, 1013, 296));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 296));
        kDLabelContainer1.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer2.setBounds(new Rectangle(341, 10, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(341, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDLabelContainer3.setBounds(new Rectangle(672, 10, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(672, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(10, 120, 934, 166));
        this.add(kDLabelContainer4, new KDLayout.Constraints(10, 120, 934, 166, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contlinkMan.setBounds(new Rectangle(10, 34, 270, 19));
        this.add(contlinkMan, new KDLayout.Constraints(10, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlinkTel.setBounds(new Rectangle(341, 34, 270, 19));
        this.add(contlinkTel, new KDLayout.Constraints(341, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contaddress.setBounds(new Rectangle(672, 34, 270, 19));
        this.add(contaddress, new KDLayout.Constraints(672, 34, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contpostCode.setBounds(new Rectangle(10, 58, 270, 19));
        this.add(contpostCode, new KDLayout.Constraints(10, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contwebSite.setBounds(new Rectangle(341, 58, 270, 19));
        this.add(contwebSite, new KDLayout.Constraints(341, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conteMail.setBounds(new Rectangle(672, 58, 270, 19));
        this.add(conteMail, new KDLayout.Constraints(672, 58, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contlicenceNumber.setBounds(new Rectangle(10, 82, 270, 19));
        this.add(contlicenceNumber, new KDLayout.Constraints(10, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contlawPerson.setBounds(new Rectangle(341, 82, 270, 19));
        this.add(contlawPerson, new KDLayout.Constraints(341, 82, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtNumber);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtName);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtSimpleName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kDTextArea1);
        //contlinkMan
        contlinkMan.setBoundEditor(txtlinkMan);
        //contlinkTel
        contlinkTel.setBoundEditor(txtlinkTel);
        //contaddress
        contaddress.setBoundEditor(txtaddress);
        //contpostCode
        contpostCode.setBoundEditor(txtpostCode);
        //contwebSite
        contwebSite.setBoundEditor(txtwebSite);
        //conteMail
        conteMail.setBoundEditor(txteMail);
        //contlicenceNumber
        contlicenceNumber.setBoundEditor(txtlicenceNumber);
        //contlawPerson
        contlawPerson.setBoundEditor(txtlawPerson);

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
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("simpleName", String.class, this.txtSimpleName, "text");
		dataBinder.registerBinding("description", String.class, this.kDTextArea1, "text");
		dataBinder.registerBinding("linkMan", String.class, this.txtlinkMan, "text");
		dataBinder.registerBinding("linkTel", String.class, this.txtlinkTel, "text");
		dataBinder.registerBinding("address", String.class, this.txtaddress, "text");
		dataBinder.registerBinding("postCode", String.class, this.txtpostCode, "text");
		dataBinder.registerBinding("webSite", String.class, this.txtwebSite, "text");
		dataBinder.registerBinding("eMail", String.class, this.txteMail, "text");
		dataBinder.registerBinding("licenceNumber", String.class, this.txtlicenceNumber, "text");
		dataBinder.registerBinding("lawPerson", String.class, this.txtlawPerson, "text");		
	}
	//Regiester UI State
	private void registerUIState(){
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_ADDNEW, this.txtSimpleName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtName, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtNumber, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.txtSimpleName, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtName, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtNumber, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.txtSimpleName, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.market.app.DeveloperManageEditUIHandler";
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
        this.txtNumber.requestFocusInWindow();
    }

	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.fdc.market.DeveloperManageInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer oufip=(com.kingdee.bos.ctrl.extendcontrols.ext.OrgUnitFilterInfoProducer)com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory.getOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("simpleName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkMan", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("linkTel", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("address", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("postCode", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("webSite", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("eMail", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("licenceNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lawPerson", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.txtName.setEnabled(true);
		            this.txtNumber.setEnabled(true);
		            this.txtSimpleName.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.txtName.setEnabled(false);
		            this.txtNumber.setEnabled(false);
		            this.txtSimpleName.setEnabled(false);
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("simpleName"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("linkMan"));
        sic.add(new SelectorItemInfo("linkTel"));
        sic.add(new SelectorItemInfo("address"));
        sic.add(new SelectorItemInfo("postCode"));
        sic.add(new SelectorItemInfo("webSite"));
        sic.add(new SelectorItemInfo("eMail"));
        sic.add(new SelectorItemInfo("licenceNumber"));
        sic.add(new SelectorItemInfo("lawPerson"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.market.client", "DeveloperManageEditUI");
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.market.client.DeveloperManageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.DeveloperManageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.DeveloperManageInfo objectValue = new com.kingdee.eas.fdc.market.DeveloperManageInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }



    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {        
        return null;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}