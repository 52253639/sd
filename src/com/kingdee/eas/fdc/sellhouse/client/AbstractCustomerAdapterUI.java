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
public abstract class AbstractCustomerAdapterUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerAdapterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNewSeller;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdapterPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAdapterDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCustomer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7NewSeller;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7AdapterPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAdapterDate;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsAdapterInter;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkAdapterFunction;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEndAdapter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancell;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCustomerAdapterUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerAdapterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contNewSeller = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdapterPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAdapterDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pnlCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnDeleteCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.f7NewSeller = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7AdapterPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAdapterDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.chkIsAdapterInter = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkAdapterFunction = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.chkIsEndAdapter = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCancell = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contNewSeller.setName("contNewSeller");
        this.contAdapterPerson.setName("contAdapterPerson");
        this.contAdapterDate.setName("contAdapterDate");
        this.pnlCustomer.setName("pnlCustomer");
        this.tblCustomer.setName("tblCustomer");
        this.btnDeleteCustomer.setName("btnDeleteCustomer");
        this.f7NewSeller.setName("f7NewSeller");
        this.f7AdapterPerson.setName("f7AdapterPerson");
        this.pkAdapterDate.setName("pkAdapterDate");
        this.chkIsAdapterInter.setName("chkIsAdapterInter");
        this.chkAdapterFunction.setName("chkAdapterFunction");
        this.chkIsEndAdapter.setName("chkIsEndAdapter");
        this.btnConfirm.setName("btnConfirm");
        this.btnCancell.setName("btnCancell");
        // CoreUI
        // contNewSeller		
        this.contNewSeller.setBoundLabelText(resHelper.getString("contNewSeller.boundLabelText"));		
        this.contNewSeller.setBoundLabelLength(100);		
        this.contNewSeller.setBoundLabelUnderline(true);
        // contAdapterPerson		
        this.contAdapterPerson.setBoundLabelText(resHelper.getString("contAdapterPerson.boundLabelText"));		
        this.contAdapterPerson.setBoundLabelLength(100);		
        this.contAdapterPerson.setBoundLabelUnderline(true);
        // contAdapterDate		
        this.contAdapterDate.setBoundLabelText(resHelper.getString("contAdapterDate.boundLabelText"));		
        this.contAdapterDate.setBoundLabelLength(100);		
        this.contAdapterDate.setBoundLabelUnderline(true);
        // pnlCustomer		
        this.pnlCustomer.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlCustomer.border.title")));
        // tblCustomer
		String tblCustomerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"oldSeller\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookedDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tradeTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{oldSeller}</t:Cell><t:Cell>$Resource{customerName}</t:Cell><t:Cell>$Resource{customerNumber}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{bookedDate}</t:Cell><t:Cell>$Resource{tradeTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCustomer.setFormatXml(resHelper.translateString("tblCustomer",tblCustomerStrXML));

        

        // btnDeleteCustomer		
        this.btnDeleteCustomer.setText(resHelper.getString("btnDeleteCustomer.text"));
        this.btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // f7NewSeller		
        this.f7NewSeller.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.f7NewSeller.setCommitFormat("$number$");		
        this.f7NewSeller.setEditFormat("$number$");		
        this.f7NewSeller.setDisplayFormat("$name$");
        // f7AdapterPerson		
        this.f7AdapterPerson.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7AdapterPerson.setDisplayFormat("$name$");		
        this.f7AdapterPerson.setEditFormat("$number$");		
        this.f7AdapterPerson.setCommitFormat("$number$");
        // pkAdapterDate
        // chkIsAdapterInter		
        this.chkIsAdapterInter.setText(resHelper.getString("chkIsAdapterInter.text"));
        this.chkIsAdapterInter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    chkIsAdapterInter_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // chkAdapterFunction		
        this.chkAdapterFunction.setText(resHelper.getString("chkAdapterFunction.text"));
        // chkIsEndAdapter		
        this.chkIsEndAdapter.setText(resHelper.getString("chkIsEndAdapter.text"));
        // btnConfirm		
        this.btnConfirm.setText(resHelper.getString("btnConfirm.text"));
        this.btnConfirm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnConfirm_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnCancell		
        this.btnCancell.setText(resHelper.getString("btnCancell.text"));
        this.btnCancell.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnCancell_actionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 780, 330));
        this.setLayout(null);
        contNewSeller.setBounds(new Rectangle(16, 11, 270, 19));
        this.add(contNewSeller, null);
        contAdapterPerson.setBounds(new Rectangle(494, 11, 270, 19));
        this.add(contAdapterPerson, null);
        contAdapterDate.setBounds(new Rectangle(17, 47, 270, 19));
        this.add(contAdapterDate, null);
        pnlCustomer.setBounds(new Rectangle(10, 79, 760, 194));
        this.add(pnlCustomer, null);
        chkIsAdapterInter.setBounds(new Rectangle(301, 47, 131, 19));
        this.add(chkIsAdapterInter, null);
        chkAdapterFunction.setBounds(new Rectangle(448, 47, 161, 19));
        this.add(chkAdapterFunction, null);
        chkIsEndAdapter.setBounds(new Rectangle(622, 47, 139, 19));
        this.add(chkIsEndAdapter, null);
        btnConfirm.setBounds(new Rectangle(604, 283, 74, 19));
        this.add(btnConfirm, null);
        btnCancell.setBounds(new Rectangle(687, 283, 75, 19));
        this.add(btnCancell, null);
        //contNewSeller
        contNewSeller.setBoundEditor(f7NewSeller);
        //contAdapterPerson
        contAdapterPerson.setBoundEditor(f7AdapterPerson);
        //contAdapterDate
        contAdapterDate.setBoundEditor(pkAdapterDate);
        //pnlCustomer
        pnlCustomer.setLayout(null);        tblCustomer.setBounds(new Rectangle(12, 50, 736, 133));
        pnlCustomer.add(tblCustomer, null);
        btnDeleteCustomer.setBounds(new Rectangle(637, 22, 106, 19));
        pnlCustomer.add(btnDeleteCustomer, null);

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
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerAdapterUIHandler";
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
        this.editData = (com.kingdee.eas.framework.CoreBaseInfo)ov;
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
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output btnDeleteCustomer_actionPerformed method
     */
    protected void btnDeleteCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output chkIsAdapterInter_actionPerformed method
     */
    protected void chkIsAdapterInter_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancell_actionPerformed method
     */
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerAdapterUI");
    }




}