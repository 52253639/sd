/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractJoinDoSchemeEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractJoinDoSchemeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox txtName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtApprEntries;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtDataEntries;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApprDeleteRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApprAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnApprInsertRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataAddRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataInsertRow;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDataDeleteRow;
    protected com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractJoinDoSchemeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractJoinDoSchemeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.txtName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtApprEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtDataEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnApprDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApprAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnApprInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataAddRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataInsertRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDataDeleteRow = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contName.setName("contName");
        this.contNumber.setName("contNumber");
        this.contDescription.setName("contDescription");
        this.kDPanel1.setName("kDPanel1");
        this.txtName.setName("txtName");
        this.txtNumber.setName("txtNumber");
        this.txtDescription.setName("txtDescription");
        this.kDContainer1.setName("kDContainer1");
        this.kdtApprEntries.setName("kdtApprEntries");
        this.kDContainer2.setName("kDContainer2");
        this.kdtDataEntries.setName("kdtDataEntries");
        this.btnApprDeleteRow.setName("btnApprDeleteRow");
        this.btnApprAddRow.setName("btnApprAddRow");
        this.btnApprInsertRow.setName("btnApprInsertRow");
        this.btnDataAddRow.setName("btnDataAddRow");
        this.btnDataInsertRow.setName("btnDataInsertRow");
        this.btnDataDeleteRow.setName("btnDataDeleteRow");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);		
        this.btnSave.setEnabled(false);		
        this.btnSave.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setEnabled(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setEnabled(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setEnabled(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setEnabled(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setEnabled(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setEnabled(false);		
        this.btnPrintPreview.setVisible(false);		
        this.btnAttachment.setEnabled(false);		
        this.btnAttachment.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDPanel1
        // txtName		
        this.txtName.setRequired(true);
        // txtNumber		
        this.txtNumber.setRequired(true);
        // txtDescription		
        this.txtDescription.setMaxLength(100);
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // kdtApprEntries
		String kdtApprEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol8\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"referenceTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"intervalMonth\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"intervalDays\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"scheduledDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{referenceTime}</t:Cell><t:Cell>$Resource{intervalMonth}</t:Cell><t:Cell>$Resource{intervalDays}</t:Cell><t:Cell>$Resource{scheduledDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtApprEntries.setFormatXml(resHelper.translateString("kdtApprEntries",kdtApprEntriesStrXML));

                this.kdtApprEntries.putBindContents("editData",new String[] {"number","name","remark","referenceTime","intervalMonth","intervalDays","scheduledDate","isFinish","id"});


        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kdtDataEntries
		String kdtDataEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{remark}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtDataEntries.setFormatXml(resHelper.translateString("kdtDataEntries",kdtDataEntriesStrXML));

                this.kdtDataEntries.putBindContents("editData",new String[] {"number","name","remark","id"});


        // btnApprDeleteRow		
        this.btnApprDeleteRow.setText(resHelper.getString("btnApprDeleteRow.text"));
        this.btnApprDeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnApprDeleteRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnApprAddRow		
        this.btnApprAddRow.setText(resHelper.getString("btnApprAddRow.text"));
        this.btnApprAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnApprAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnApprInsertRow		
        this.btnApprInsertRow.setText(resHelper.getString("btnApprInsertRow.text"));
        this.btnApprInsertRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnApprInsertRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDataAddRow		
        this.btnDataAddRow.setText(resHelper.getString("btnDataAddRow.text"));
        this.btnDataAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDataAddRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDataInsertRow		
        this.btnDataInsertRow.setText(resHelper.getString("btnDataInsertRow.text"));
        this.btnDataInsertRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDataInsertRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDataDeleteRow		
        this.btnDataDeleteRow.setText(resHelper.getString("btnDataDeleteRow.text"));
        this.btnDataDeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDataDeleteRow_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 700, 455));
        this.setLayout(null);
        contName.setBounds(new Rectangle(371, 9, 270, 19));
        this.add(contName, null);
        contNumber.setBounds(new Rectangle(8, 10, 270, 19));
        this.add(contNumber, null);
        contDescription.setBounds(new Rectangle(10, 40, 636, 19));
        this.add(contDescription, null);
        kDPanel1.setBounds(new Rectangle(10, 82, 680, 357));
        this.add(kDPanel1, null);
        //contName
        contName.setBoundEditor(txtName);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //kDPanel1
        kDPanel1.setLayout(null);        kDContainer1.setBounds(new Rectangle(-1, 0, 150, 19));
        kDPanel1.add(kDContainer1, null);
        kdtApprEntries.setBounds(new Rectangle(-1, 29, 680, 167));
        kDPanel1.add(kdtApprEntries, null);
        kDContainer2.setBounds(new Rectangle(-1, 206, 150, 19));
        kDPanel1.add(kDContainer2, null);
        kdtDataEntries.setBounds(new Rectangle(-1, 235, 680, 120));
        kDPanel1.add(kdtDataEntries, null);
        btnApprDeleteRow.setBounds(new Rectangle(553, 0, 74, 19));
        kDPanel1.add(btnApprDeleteRow, null);
        btnApprAddRow.setBounds(new Rectangle(370, 0, 74, 19));
        kDPanel1.add(btnApprAddRow, null);
        btnApprInsertRow.setBounds(new Rectangle(462, 0, 74, 19));
        kDPanel1.add(btnApprInsertRow, null);
        btnDataAddRow.setBounds(new Rectangle(370, 206, 74, 19));
        kDPanel1.add(btnDataAddRow, null);
        btnDataInsertRow.setBounds(new Rectangle(462, 206, 74, 19));
        kDPanel1.add(btnDataInsertRow, null);
        btnDataDeleteRow.setBounds(new Rectangle(553, 206, 74, 19));
        kDPanel1.add(btnDataDeleteRow, null);
        kDContainer1.getContentPane().setLayout(null);        kDContainer2.getContentPane().setLayout(null);
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
		dataBinder.registerBinding("name", String.class, this.txtName, "_multiLangItem");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("apprEntries.number", String.class, this.kdtApprEntries, "number.text");
		dataBinder.registerBinding("apprEntries.name", String.class, this.kdtApprEntries, "name.text");
		dataBinder.registerBinding("apprEntries.remark", String.class, this.kdtApprEntries, "remark.text");
		dataBinder.registerBinding("apprEntries.referenceTime", String.class, this.kdtApprEntries, "referenceTime.text");
		dataBinder.registerBinding("apprEntries.intervalMonth", int.class, this.kdtApprEntries, "intervalMonth.text");
		dataBinder.registerBinding("apprEntries.intervalDays", int.class, this.kdtApprEntries, "intervalDays.text");
		dataBinder.registerBinding("apprEntries.scheduledDate", java.util.Date.class, this.kdtApprEntries, "scheduledDate.text");
		dataBinder.registerBinding("apprEntries.isFinish", boolean.class, this.kdtApprEntries, "isFinish.text");
		dataBinder.registerBinding("apprEntries", com.kingdee.eas.fdc.sellhouse.JoinApproachEntryInfo.class, this.kdtApprEntries, "userObject");
		dataBinder.registerBinding("apprEntries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtApprEntries, "id.text");
		dataBinder.registerBinding("dataEntries.number", String.class, this.kdtDataEntries, "number.text");
		dataBinder.registerBinding("dataEntries.name", String.class, this.kdtDataEntries, "name.text");
		dataBinder.registerBinding("dataEntries.remark", String.class, this.kdtDataEntries, "remark.text");
		dataBinder.registerBinding("dataEntries", com.kingdee.eas.fdc.sellhouse.JoinDataEntryInfo.class, this.kdtDataEntries, "userObject");
		dataBinder.registerBinding("dataEntries.id", com.kingdee.bos.util.BOSUuid.class, this.kdtDataEntries, "id.text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.JoinDoSchemeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.JoinDoSchemeInfo)ov;
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
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.referenceTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.intervalMonth", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.intervalDays", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.scheduledDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("apprEntries.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntries.number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntries.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntries.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntries", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dataEntries.id", ValidateHelper.ON_SAVE);    		
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
     * output btnApprDeleteRow_actionPerformed method
     */
    protected void btnApprDeleteRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnApprAddRow_actionPerformed method
     */
    protected void btnApprAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnApprInsertRow_actionPerformed method
     */
    protected void btnApprInsertRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDataAddRow_actionPerformed method
     */
    protected void btnDataAddRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDataInsertRow_actionPerformed method
     */
    protected void btnDataInsertRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDataDeleteRow_actionPerformed method
     */
    protected void btnDataDeleteRow_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("description"));
    sic.add(new SelectorItemInfo("apprEntries.number"));
    sic.add(new SelectorItemInfo("apprEntries.name"));
    sic.add(new SelectorItemInfo("apprEntries.remark"));
    sic.add(new SelectorItemInfo("apprEntries.referenceTime"));
    sic.add(new SelectorItemInfo("apprEntries.intervalMonth"));
    sic.add(new SelectorItemInfo("apprEntries.intervalDays"));
    sic.add(new SelectorItemInfo("apprEntries.scheduledDate"));
    sic.add(new SelectorItemInfo("apprEntries.isFinish"));
        sic.add(new SelectorItemInfo("apprEntries.*"));
//        sic.add(new SelectorItemInfo("apprEntries.number"));
    sic.add(new SelectorItemInfo("apprEntries.id"));
    sic.add(new SelectorItemInfo("dataEntries.number"));
    sic.add(new SelectorItemInfo("dataEntries.name"));
    sic.add(new SelectorItemInfo("dataEntries.remark"));
        sic.add(new SelectorItemInfo("dataEntries.*"));
//        sic.add(new SelectorItemInfo("dataEntries.number"));
    sic.add(new SelectorItemInfo("dataEntries.id"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "JoinDoSchemeEditUI");
    }




}