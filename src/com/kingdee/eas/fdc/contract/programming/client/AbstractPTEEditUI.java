/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

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
public abstract class AbstractPTEEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPTEEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDWorkButton bntSubmit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstract;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstractNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtConstractName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtAttachment;
    protected com.kingdee.bos.ctrl.swing.KDPanel kdpConstract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer6;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer7;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtProblem;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtScope;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcCost;
    protected com.kingdee.bos.ctrl.swing.KDContainer kdcEconomy;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCostEntries;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEconomyEntriese;
    protected com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo editData = null;
    protected ActionSubmit actionSubmit = null;
    protected ActionAttachment actionAttachment = null;
    /**
     * output class constructor
     */
    public AbstractPTEEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPTEEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        this.actionSubmit = new ActionSubmit(this);
        getActionManager().registerAction("actionSubmit", actionSubmit);
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAttachment
        this.actionAttachment = new ActionAttachment(this);
        getActionManager().registerAction("actionAttachment", actionAttachment);
         this.actionAttachment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.bntSubmit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAttachment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.txtConstract = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConstractNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtConstractName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAttachment = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kdpConstract = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer5 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer6 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer7 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.txtProblem = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtScope = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kdcCost = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdcEconomy = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kdtCostEntries = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtEconomyEntriese = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.bntSubmit.setName("bntSubmit");
        this.btnAttachment.setName("btnAttachment");
        this.txtConstract.setName("txtConstract");
        this.txtConstractNumber.setName("txtConstractNumber");
        this.txtConstractName.setName("txtConstractName");
        this.txtDescription.setName("txtDescription");
        this.txtAttachment.setName("txtAttachment");
        this.kdpConstract.setName("kdpConstract");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDLabelContainer5.setName("kDLabelContainer5");
        this.kDLabelContainer6.setName("kDLabelContainer6");
        this.kDLabelContainer7.setName("kDLabelContainer7");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.txtProblem.setName("txtProblem");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtScope.setName("txtScope");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kdcCost.setName("kdcCost");
        this.kdcEconomy.setName("kdcEconomy");
        this.kdtCostEntries.setName("kdtCostEntries");
        this.kdtEconomyEntriese.setName("kdtEconomyEntriese");
        // CoreUI
        // bntSubmit
        this.bntSubmit.setAction((IItemAction)ActionProxyFactory.getProxy(actionSubmit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.bntSubmit.setText(resHelper.getString("bntSubmit.text"));		
        this.bntSubmit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
        // btnAttachment
        this.btnAttachment.setAction((IItemAction)ActionProxyFactory.getProxy(actionAttachment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAttachment.setText(resHelper.getString("btnAttachment.text"));		
        this.btnAttachment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_affixmanage"));
        // txtConstract		
        this.txtConstract.setMaxLength(80);		
        this.txtConstract.setEditable(false);		
        this.txtConstract.setOpaque(false);
        // txtConstractNumber		
        this.txtConstractNumber.setRequired(true);		
        this.txtConstractNumber.setMaxLength(80);
        this.txtConstractNumber.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    txtConstractNumber_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtConstractNumber.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    txtConstractNumber_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void mouseReleased(java.awt.event.MouseEvent e) {
                try {
                    txtConstractNumber_mouseReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtConstractNumber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    txtConstractNumber_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtConstractNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent e) {
                try {
                    txtConstractNumber_keyTyped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    txtConstractNumber_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyPressed(java.awt.event.KeyEvent e) {
                try {
                    txtConstractNumber_keyPressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtConstractName		
        this.txtConstractName.setRequired(true);		
        this.txtConstractName.setMaxLength(80);
        // txtDescription		
        this.txtDescription.setMaxLength(1024);
        // txtAttachment		
        this.txtAttachment.setOpaque(false);		
        this.txtAttachment.setEditable(false);		
        this.txtAttachment.setMaxLength(80);
        this.txtAttachment.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                try {
                    txtAttachment_mouseEntered(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                try {
                    txtAttachment_mouseExited(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.txtAttachment.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent e) {
                try {
                    txtAttachment_mouseMoved(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kdpConstract		
        this.kdpConstract.setBorder(null);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelUnderline(true);		
        this.kDLabelContainer1.setBoundLabelLength(100);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelUnderline(true);		
        this.kDLabelContainer2.setBoundLabelLength(100);
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelUnderline(true);		
        this.kDLabelContainer3.setBoundLabelLength(100);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelUnderline(true);		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setAutoscrolls(true);
        // kDLabelContainer5		
        this.kDLabelContainer5.setBoundLabelText(resHelper.getString("kDLabelContainer5.boundLabelText"));		
        this.kDLabelContainer5.setBoundLabelUnderline(true);		
        this.kDLabelContainer5.setBoundLabelLength(100);
        // kDLabelContainer6		
        this.kDLabelContainer6.setBoundLabelText(resHelper.getString("kDLabelContainer6.boundLabelText"));		
        this.kDLabelContainer6.setBoundLabelUnderline(true);		
        this.kDLabelContainer6.setBoundLabelLength(100);
        // kDLabelContainer7		
        this.kDLabelContainer7.setBoundLabelText(resHelper.getString("kDLabelContainer7.boundLabelText"));		
        this.kDLabelContainer7.setBoundLabelUnderline(true);		
        this.kDLabelContainer7.setBoundLabelLength(100);		
        this.kDLabelContainer7.setToolTipText(resHelper.getString("kDLabelContainer7.toolTipText"));
        // kDTabbedPane1
        // txtProblem		
        this.txtProblem.setMaxLength(1024);		
        this.txtProblem.setRows(3);
        // kDScrollPane1		
        this.kDScrollPane1.setAutoscrolls(true);
        // txtScope		
        this.txtScope.setMaxLength(1024);		
        this.txtScope.setRows(3);
        // kDScrollPane2
        // kdcCost
        // kdcEconomy
        // kdtCostEntries
		String kdtCostEntriesStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection locked=\"true\" /><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol4\"><c:Alignment horizontal=\"center\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"costAccountNumber\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"costAccount\" t:width=\"120\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" /><t:Column t:key=\"assignScale\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol3\" /><t:Column t:key=\"contractScale\" t:width=\"70\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"description\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{costAccountNumber}</t:Cell><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{assignScale}</t:Cell><t:Cell>$Resource{contractScale}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtCostEntries.setFormatXml(resHelper.translateString("kdtCostEntries",kdtCostEntriesStrXML));
        this.kdtCostEntries.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtCostEntries_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCostEntries.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kdtCostEntries_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCostEntries.addKDTDataRequestListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestListener() {
            public void tableDataRequest(com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent e) {
                try {
                    kdtCostEntries_tableDataRequest(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtCostEntries.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStarting(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntries_editStarted(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtCostEntries.putBindContents("editData",new String[] {"id","costAccount.longNumber","costAccount.name","assignScale","contractScale","description"});


        // kdtEconomyEntriese
		String kdtEconomyEntrieseStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol1\"><c:Alignment horizontal=\"center\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"paymentType\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"scale\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"true\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"condition\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"description\" t:width=\"210\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{paymentType}</t:Cell><t:Cell>$Resource{scale}</t:Cell><t:Cell>$Resource{condition}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kdtEconomyEntriese.setFormatXml(resHelper.translateString("kdtEconomyEntriese",kdtEconomyEntrieseStrXML));
        this.kdtEconomyEntriese.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    kdtEconomyEntriese_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kdtEconomyEntriese.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtEconomyEntriese_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtEconomyEntriese.putBindContents("editData",new String[] {"paymentType.name","scale","condition","description","id"});


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
        this.setBounds(new Rectangle(10, 10, 670, 545));
        this.setLayout(null);
        kdpConstract.setBounds(new Rectangle(5, 5, 660, 280));
        this.add(kdpConstract, null);
        kDTabbedPane1.setBounds(new Rectangle(10, 285, 650, 250));
        this.add(kDTabbedPane1, null);
        //kdpConstract
        kdpConstract.setLayout(new KDLayout());
        kdpConstract.putClientProperty("OriginalBounds", new Rectangle(5, 5, 660, 280));        kDLabelContainer1.setBounds(new Rectangle(5, 5, 650, 20));
        kdpConstract.add(kDLabelContainer1, new KDLayout.Constraints(5, 5, 650, 20, 0));
        kDLabelContainer2.setBounds(new Rectangle(5, 35, 300, 20));
        kdpConstract.add(kDLabelContainer2, new KDLayout.Constraints(5, 35, 300, 20, 0));
        kDLabelContainer3.setBounds(new Rectangle(355, 35, 300, 20));
        kdpConstract.add(kDLabelContainer3, new KDLayout.Constraints(355, 35, 300, 20, 0));
        kDLabelContainer4.setBounds(new Rectangle(4, 65, 650, 70));
        kdpConstract.add(kDLabelContainer4, new KDLayout.Constraints(4, 65, 650, 70, 0));
        kDLabelContainer5.setBounds(new Rectangle(5, 145, 650, 70));
        kdpConstract.add(kDLabelContainer5, new KDLayout.Constraints(5, 145, 650, 70, 0));
        kDLabelContainer6.setBounds(new Rectangle(5, 225, 650, 20));
        kdpConstract.add(kDLabelContainer6, new KDLayout.Constraints(5, 225, 650, 20, 0));
        kDLabelContainer7.setBounds(new Rectangle(5, 255, 650, 20));
        kdpConstract.add(kDLabelContainer7, new KDLayout.Constraints(5, 255, 650, 20, 0));
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtConstract);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(txtConstractNumber);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtConstractName);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtScope, null);
        //kDLabelContainer5
        kDLabelContainer5.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtProblem, null);
        //kDLabelContainer6
        kDLabelContainer6.setBoundEditor(txtDescription);
        //kDLabelContainer7
        kDLabelContainer7.setBoundEditor(txtAttachment);
        //kDTabbedPane1
        kDTabbedPane1.add(kdcCost, resHelper.getString("kdcCost.constraints"));
        kDTabbedPane1.add(kdcEconomy, resHelper.getString("kdcEconomy.constraints"));
        //kdcCost
kdcCost.getContentPane().setLayout(new BorderLayout(0, 0));        kdcCost.getContentPane().add(kdtCostEntries, BorderLayout.CENTER);
        //kdcEconomy
kdcEconomy.getContentPane().setLayout(new BorderLayout(0, 0));        kdcEconomy.getContentPane().add(kdtEconomyEntriese, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuTool);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemPageSetup);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemExitCurrent);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
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
        this.toolBar.add(bntSubmit);
        this.toolBar.add(btnAttachment);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("parent.longNumber", String.class, this.txtConstract, "text");
		dataBinder.registerBinding("number", String.class, this.txtConstractNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtConstractName, "text");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("attachment", String.class, this.txtAttachment, "text");
		dataBinder.registerBinding("problem", String.class, this.txtProblem, "text");
		dataBinder.registerBinding("scope", String.class, this.txtScope, "text");
		dataBinder.registerBinding("pteCost.id", com.kingdee.bos.util.BOSUuid.class, this.kdtCostEntries, "id.text");
		dataBinder.registerBinding("pteCost.costAccount.longNumber", String.class, this.kdtCostEntries, "costAccountNumber.text");
		dataBinder.registerBinding("pteCost.assignScale", java.math.BigDecimal.class, this.kdtCostEntries, "assignScale.text");
		dataBinder.registerBinding("pteCost.contractScale", java.math.BigDecimal.class, this.kdtCostEntries, "contractScale.text");
		dataBinder.registerBinding("pteCost.description", String.class, this.kdtCostEntries, "description.text");
		dataBinder.registerBinding("pteCost", com.kingdee.eas.fdc.contract.programming.PTECostInfo.class, this.kdtCostEntries, "userObject");
		dataBinder.registerBinding("pteCost.costAccount.name", String.class, this.kdtCostEntries, "costAccount.text");
		dataBinder.registerBinding("pteEnonomy.id", com.kingdee.bos.util.BOSUuid.class, this.kdtEconomyEntriese, "id.text");
		dataBinder.registerBinding("pteEnonomy.paymentType.name", String.class, this.kdtEconomyEntriese, "paymentType.text");
		dataBinder.registerBinding("pteEnonomy.scale", java.math.BigDecimal.class, this.kdtEconomyEntriese, "scale.text");
		dataBinder.registerBinding("pteEnonomy.condition", String.class, this.kdtEconomyEntriese, "condition.text");
		dataBinder.registerBinding("pteEnonomy.description", String.class, this.kdtEconomyEntriese, "description.text");
		dataBinder.registerBinding("pteEnonomy", com.kingdee.eas.fdc.contract.programming.PTEEnonomyInfo.class, this.kdtEconomyEntriese, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.programming.app.PTEEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.programming.ProgrammingTemplateEntireInfo)ov;
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
		getValidateHelper().registerBindProperty("parent.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("problem", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("scope", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.costAccount.longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.assignScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.contractScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteCost.costAccount.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.paymentType.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.scale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.condition", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("pteEnonomy", ValidateHelper.ON_SAVE);    		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output txtConstractNumber_keyTyped method
     */
    protected void txtConstractNumber_keyTyped(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_propertyChange method
     */
    protected void txtConstractNumber_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_keyReleased method
     */
    protected void txtConstractNumber_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_keyPressed method
     */
    protected void txtConstractNumber_keyPressed(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_focusGained method
     */
    protected void txtConstractNumber_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_mouseClicked method
     */
    protected void txtConstractNumber_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtConstractNumber_mouseReleased method
     */
    protected void txtConstractNumber_mouseReleased(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtAttachment_mouseMoved method
     */
    protected void txtAttachment_mouseMoved(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtAttachment_mouseEntered method
     */
    protected void txtAttachment_mouseEntered(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output txtAttachment_mouseExited method
     */
    protected void txtAttachment_mouseExited(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_tableClicked method
     */
    protected void kdtCostEntries_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStopped method
     */
    protected void kdtCostEntries_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStarting method
     */
    protected void kdtCostEntries_editStarting(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_activeCellChanged method
     */
    protected void kdtCostEntries_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStopping method
     */
    protected void kdtCostEntries_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editValueChanged method
     */
    protected void kdtCostEntries_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_editStarted method
     */
    protected void kdtCostEntries_editStarted(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntries_tableDataRequest method
     */
    protected void kdtCostEntries_tableDataRequest(com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomyEntriese_tableClicked method
     */
    protected void kdtEconomyEntriese_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
    }

    /**
     * output kdtEconomyEntriese_editStopped method
     */
    protected void kdtEconomyEntriese_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("parent.longNumber"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("attachment"));
        sic.add(new SelectorItemInfo("problem"));
        sic.add(new SelectorItemInfo("scope"));
    sic.add(new SelectorItemInfo("pteCost.id"));
    sic.add(new SelectorItemInfo("pteCost.costAccount.longNumber"));
    sic.add(new SelectorItemInfo("pteCost.assignScale"));
    sic.add(new SelectorItemInfo("pteCost.contractScale"));
    sic.add(new SelectorItemInfo("pteCost.description"));
        sic.add(new SelectorItemInfo("pteCost.*"));
//        sic.add(new SelectorItemInfo("pteCost.number"));
    sic.add(new SelectorItemInfo("pteCost.costAccount.name"));
    sic.add(new SelectorItemInfo("pteEnonomy.id"));
    sic.add(new SelectorItemInfo("pteEnonomy.paymentType.name"));
    sic.add(new SelectorItemInfo("pteEnonomy.scale"));
    sic.add(new SelectorItemInfo("pteEnonomy.condition"));
    sic.add(new SelectorItemInfo("pteEnonomy.description"));
        sic.add(new SelectorItemInfo("pteEnonomy.*"));
//        sic.add(new SelectorItemInfo("pteEnonomy.number"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAttachment_actionPerformed method
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionAttachment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAttachment() {
    	return false;
    }

    /**
     * output ActionSubmit class
     */     
    protected class ActionSubmit extends ItemAction {     
    
        public ActionSubmit()
        {
            this(null);
        }

        public ActionSubmit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSubmit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPTEEditUI.this, "ActionSubmit", "actionSubmit_actionPerformed", e);
        }
    }

    /**
     * output ActionAttachment class
     */     
    protected class ActionAttachment extends ItemAction {     
    
        public ActionAttachment()
        {
            this(null);
        }

        public ActionAttachment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionAttachment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAttachment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPTEEditUI.this, "ActionAttachment", "actionAttachment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.programming.client", "PTEEditUI");
    }




}