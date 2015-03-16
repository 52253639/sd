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
public abstract class AbstractCustomerShareUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractCustomerShareUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSharePerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contShareDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkShareDate;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlSharePerson;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblSharePerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnConfirm;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDeleteSharePerson;
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlCustomer;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddSharePerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelShareCustomer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SharePerson;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCancell;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAddUnit;
    protected com.kingdee.eas.framework.CoreBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractCustomerShareUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractCustomerShareUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contSharePerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contShareDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkShareDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pnlSharePerson = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblSharePerson = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnConfirm = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDeleteSharePerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.pnlCustomer = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblCustomer = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnAddSharePerson = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnDelShareCustomer = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.f7SharePerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.btnCancell = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddOrgUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAddUnit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contSharePerson.setName("contSharePerson");
        this.contShareDate.setName("contShareDate");
        this.pkShareDate.setName("pkShareDate");
        this.pnlSharePerson.setName("pnlSharePerson");
        this.tblSharePerson.setName("tblSharePerson");
        this.btnConfirm.setName("btnConfirm");
        this.btnDeleteSharePerson.setName("btnDeleteSharePerson");
        this.pnlCustomer.setName("pnlCustomer");
        this.tblCustomer.setName("tblCustomer");
        this.btnAddSharePerson.setName("btnAddSharePerson");
        this.btnDelShareCustomer.setName("btnDelShareCustomer");
        this.f7SharePerson.setName("f7SharePerson");
        this.btnCancell.setName("btnCancell");
        this.btnAddOrgUnit.setName("btnAddOrgUnit");
        this.btnAddUnit.setName("btnAddUnit");
        // CoreUI
        // contSharePerson		
        this.contSharePerson.setBoundLabelText(resHelper.getString("contSharePerson.boundLabelText"));		
        this.contSharePerson.setBoundLabelLength(100);		
        this.contSharePerson.setBoundLabelUnderline(true);
        // contShareDate		
        this.contShareDate.setBoundLabelText(resHelper.getString("contShareDate.boundLabelText"));		
        this.contShareDate.setBoundLabelLength(100);		
        this.contShareDate.setBoundLabelUnderline(true);
        // pkShareDate
        // pnlSharePerson		
        this.pnlSharePerson.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlSharePerson.border.title")));
        // tblSharePerson
		String tblSharePersonStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"shareModel\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"shareObject\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"operationPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isAgainShare\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"isUpdate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"descrption\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{shareModel}</t:Cell><t:Cell>$Resource{shareObject}</t:Cell><t:Cell>$Resource{operationPerson}</t:Cell><t:Cell>$Resource{isAgainShare}</t:Cell><t:Cell>$Resource{isUpdate}</t:Cell><t:Cell>$Resource{descrption}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblSharePerson.setFormatXml(resHelper.translateString("tblSharePerson",tblSharePersonStrXML));
        this.tblSharePerson.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblSharePerson_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblSharePerson.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblSharePerson_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

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
        // btnDeleteSharePerson		
        this.btnDeleteSharePerson.setText(resHelper.getString("btnDeleteSharePerson.text"));
        this.btnDeleteSharePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDeleteSharePerson_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // pnlCustomer		
        this.pnlCustomer.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(0,0,0),1), resHelper.getString("pnlCustomer.border.title")));
        // tblCustomer
		String tblCustomerStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"seller\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"customerNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"sex\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"bookDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"tradeTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seller}</t:Cell><t:Cell>$Resource{customerName}</t:Cell><t:Cell>$Resource{customerNumber}</t:Cell><t:Cell>$Resource{sex}</t:Cell><t:Cell>$Resource{bookDate}</t:Cell><t:Cell>$Resource{tradeTime}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblCustomer.setFormatXml(resHelper.translateString("tblCustomer",tblCustomerStrXML));

        

        // btnAddSharePerson		
        this.btnAddSharePerson.setText(resHelper.getString("btnAddSharePerson.text"));
        this.btnAddSharePerson.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddSharePerson_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnDelShareCustomer		
        this.btnDelShareCustomer.setText(resHelper.getString("btnDelShareCustomer.text"));
        this.btnDelShareCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelShareCustomer_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // f7SharePerson		
        this.f7SharePerson.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7SharePerson.setCommitFormat("$number$");		
        this.f7SharePerson.setEditFormat("$number$");		
        this.f7SharePerson.setDisplayFormat("$name$");
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
        // btnAddOrgUnit		
        this.btnAddOrgUnit.setText(resHelper.getString("btnAddOrgUnit.text"));
        this.btnAddOrgUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddOrgUnit_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // btnAddUnit		
        this.btnAddUnit.setText(resHelper.getString("btnAddUnit.text"));
        this.btnAddUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnAddUnit_actionPerformed(e);
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
        this.setBounds(new Rectangle(10, 10, 700, 430));
        this.setLayout(null);
        contSharePerson.setBounds(new Rectangle(15, 10, 270, 19));
        this.add(contSharePerson, null);
        contShareDate.setBounds(new Rectangle(413, 9, 270, 19));
        this.add(contShareDate, null);
        pnlSharePerson.setBounds(new Rectangle(9, 43, 681, 156));
        this.add(pnlSharePerson, null);
        btnConfirm.setBounds(new Rectangle(527, 392, 76, 19));
        this.add(btnConfirm, null);
        pnlCustomer.setBounds(new Rectangle(9, 211, 684, 171));
        this.add(pnlCustomer, null);
        btnCancell.setBounds(new Rectangle(609, 391, 77, 19));
        this.add(btnCancell, null);
        //contSharePerson
        contSharePerson.setBoundEditor(f7SharePerson);
        //contShareDate
        contShareDate.setBoundEditor(pkShareDate);
        //pnlSharePerson
        pnlSharePerson.setLayout(null);        tblSharePerson.setBounds(new Rectangle(9, 46, 663, 100));
        pnlSharePerson.add(tblSharePerson, null);
        btnDeleteSharePerson.setBounds(new Rectangle(592, 20, 74, 19));
        pnlSharePerson.add(btnDeleteSharePerson, null);
        btnAddSharePerson.setBounds(new Rectangle(277, 20, 109, 19));
        pnlSharePerson.add(btnAddSharePerson, null);
        btnAddOrgUnit.setBounds(new Rectangle(502, 20, 87, 19));
        pnlSharePerson.add(btnAddOrgUnit, null);
        btnAddUnit.setBounds(new Rectangle(388, 20, 111, 19));
        pnlSharePerson.add(btnAddUnit, null);
        //pnlCustomer
        pnlCustomer.setLayout(null);        tblCustomer.setBounds(new Rectangle(12, 42, 646, 117));
        pnlCustomer.add(tblCustomer, null);
        btnDelShareCustomer.setBounds(new Rectangle(546, 18, 96, 19));
        pnlCustomer.add(btnDelShareCustomer, null);

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
	    return "com.kingdee.eas.fdc.sellhouse.app.CustomerShareUIHandler";
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
     * output tblSharePerson_editStopped method
     */
    protected void tblSharePerson_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblSharePerson_activeCellChanged method
     */
    protected void tblSharePerson_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output btnConfirm_actionPerformed method
     */
    protected void btnConfirm_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDeleteSharePerson_actionPerformed method
     */
    protected void btnDeleteSharePerson_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddSharePerson_actionPerformed method
     */
    protected void btnAddSharePerson_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelShareCustomer_actionPerformed method
     */
    protected void btnDelShareCustomer_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnCancell_actionPerformed method
     */
    protected void btnCancell_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnAddOrgUnit_actionPerformed method
     */
    protected void btnAddOrgUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output btnAddUnit_actionPerformed method
     */
    protected void btnAddUnit_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        //write your code here
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
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "CustomerShareUI");
    }




}