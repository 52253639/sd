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
public abstract class AbstractSincerityChangeNameEditUI extends com.kingdee.eas.framework.client.CoreUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSincerityChangeNameEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOperator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRemark;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer2;
    protected com.kingdee.bos.ctrl.swing.KDContainer kDContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Operater;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChooser;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChooseRoomPhone;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator6;
    protected com.kingdee.bos.ctrl.swing.KDLabel newCus3;
    protected com.kingdee.bos.ctrl.swing.KDLabel newCus2;
    protected com.kingdee.bos.ctrl.swing.KDLabel newCus1;
    protected com.kingdee.bos.ctrl.swing.KDLabel newCustomer;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSelecCus;
    protected com.kingdee.bos.ctrl.swing.KDLabel newCus5;
    protected com.kingdee.bos.ctrl.swing.KDLabel newCus4;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChooser;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtChooseRoomPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabel oldCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabel oldCus1;
    protected com.kingdee.bos.ctrl.swing.KDLabel oldCus2;
    protected com.kingdee.bos.ctrl.swing.KDSeparator kDSeparator5;
    protected com.kingdee.bos.ctrl.swing.KDLabel oldCus3;
    protected com.kingdee.bos.ctrl.swing.KDLabel oldCus4;
    protected com.kingdee.bos.ctrl.swing.KDLabel oldCus5;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer cluesPhone;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCluesPhone;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtyuanChooser;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkChangeDate;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnEdit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected ActionSave actionSave = null;
    protected ActionEdit actionEdit = null;
    /**
     * output class constructor
     */
    public AbstractSincerityChangeNameEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSincerityChangeNameEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSave
        this.actionSave = new ActionSave(this);
        getActionManager().registerAction("actionSave", actionSave);
         this.actionSave.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionEdit
        this.actionEdit = new ActionEdit(this);
        getActionManager().registerAction("actionEdit", actionEdit);
         this.actionEdit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contOperator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRemark = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDContainer2 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDContainer1 = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contChangeDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Operater = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contChooser = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChooseRoomPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDSeparator6 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.newCus3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.newCus2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.newCus1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.newCustomer = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.btnSelecCus = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.newCus5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.newCus4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.txtChooser = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtChooseRoomPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.oldCustomer = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.oldCus1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.oldCus2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDSeparator5 = new com.kingdee.bos.ctrl.swing.KDSeparator();
        this.oldCus3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.oldCus4 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.oldCus5 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cluesPhone = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCluesPhone = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtyuanChooser = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkChangeDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.btnEdit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contOperator.setName("contOperator");
        this.contRemark.setName("contRemark");
        this.kDContainer2.setName("kDContainer2");
        this.kDContainer1.setName("kDContainer1");
        this.contChangeDate.setName("contChangeDate");
        this.f7Operater.setName("f7Operater");
        this.txtRemark.setName("txtRemark");
        this.contChooser.setName("contChooser");
        this.contChooseRoomPhone.setName("contChooseRoomPhone");
        this.kDSeparator6.setName("kDSeparator6");
        this.newCus3.setName("newCus3");
        this.newCus2.setName("newCus2");
        this.newCus1.setName("newCus1");
        this.newCustomer.setName("newCustomer");
        this.btnSelecCus.setName("btnSelecCus");
        this.newCus5.setName("newCus5");
        this.newCus4.setName("newCus4");
        this.txtChooser.setName("txtChooser");
        this.txtChooseRoomPhone.setName("txtChooseRoomPhone");
        this.oldCustomer.setName("oldCustomer");
        this.oldCus1.setName("oldCus1");
        this.oldCus2.setName("oldCus2");
        this.kDSeparator5.setName("kDSeparator5");
        this.oldCus3.setName("oldCus3");
        this.oldCus4.setName("oldCus4");
        this.oldCus5.setName("oldCus5");
        this.cluesPhone.setName("cluesPhone");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.txtCluesPhone.setName("txtCluesPhone");
        this.txtyuanChooser.setName("txtyuanChooser");
        this.pkChangeDate.setName("pkChangeDate");
        this.btnEdit.setName("btnEdit");
        this.btnSave.setName("btnSave");
        // CoreUI		
        this.setPreferredSize(new Dimension(610,440));
        // contOperator		
        this.contOperator.setBoundLabelText(resHelper.getString("contOperator.boundLabelText"));		
        this.contOperator.setBoundLabelLength(80);		
        this.contOperator.setBoundLabelUnderline(true);
        // contRemark		
        this.contRemark.setBoundLabelText(resHelper.getString("contRemark.boundLabelText"));		
        this.contRemark.setBoundLabelLength(80);		
        this.contRemark.setBoundLabelUnderline(true);
        // kDContainer2		
        this.kDContainer2.setTitle(resHelper.getString("kDContainer2.title"));
        // kDContainer1		
        this.kDContainer1.setTitle(resHelper.getString("kDContainer1.title"));
        // contChangeDate		
        this.contChangeDate.setBoundLabelText(resHelper.getString("contChangeDate.boundLabelText"));		
        this.contChangeDate.setBoundLabelLength(80);		
        this.contChangeDate.setBoundLabelUnderline(true);
        // f7Operater		
        this.f7Operater.setDisplayFormat("$name$");		
        this.f7Operater.setEditFormat("$number$");		
        this.f7Operater.setCommitFormat("$number$");		
        this.f7Operater.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Operater.setEnabled(false);
        // txtRemark
        this.txtRemark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    txtRemark_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // contChooser		
        this.contChooser.setBoundLabelText(resHelper.getString("contChooser.boundLabelText"));		
        this.contChooser.setBoundLabelLength(80);		
        this.contChooser.setBoundLabelUnderline(true);
        // contChooseRoomPhone		
        this.contChooseRoomPhone.setBoundLabelText(resHelper.getString("contChooseRoomPhone.boundLabelText"));		
        this.contChooseRoomPhone.setBoundLabelLength(80);		
        this.contChooseRoomPhone.setBoundLabelUnderline(true);
        // kDSeparator6		
        this.kDSeparator6.setForeground(new java.awt.Color(0,0,0));
        // newCus3
        this.newCus3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    newCus3_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // newCus2
        this.newCus2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    newCus2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // newCus1
        this.newCus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    newCus1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // newCustomer		
        this.newCustomer.setText(resHelper.getString("newCustomer.text"));
        // btnSelecCus		
        this.btnSelecCus.setText(resHelper.getString("btnSelecCus.text"));
        this.btnSelecCus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnSelecCus_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // newCus5
        this.newCus5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    newCus5_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // newCus4
        this.newCus4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    newCus4_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtChooser		
        this.txtChooser.setRequired(true);
        // txtChooseRoomPhone		
        this.txtChooseRoomPhone.setRequired(true);
        // oldCustomer		
        this.oldCustomer.setText(resHelper.getString("oldCustomer.text"));
        // oldCus1
        this.oldCus1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    oldCus1_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // oldCus2
        this.oldCus2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    oldCus2_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDSeparator5		
        this.kDSeparator5.setForeground(new java.awt.Color(0,0,0));
        // oldCus3
        this.oldCus3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    oldCus3_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // oldCus4
        this.oldCus4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    oldCus4_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // oldCus5
        this.oldCus5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                try {
                    oldCus5_mouseClicked(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // cluesPhone		
        this.cluesPhone.setBoundLabelText(resHelper.getString("cluesPhone.boundLabelText"));		
        this.cluesPhone.setBoundLabelLength(80);		
        this.cluesPhone.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(80);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // txtCluesPhone		
        this.txtCluesPhone.setRequired(true);
        // txtyuanChooser
        // pkChangeDate		
        this.pkChangeDate.setEnabled(false);
        // btnEdit
        this.btnEdit.setAction((IItemAction)ActionProxyFactory.getProxy(actionEdit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnEdit.setText(resHelper.getString("btnEdit.text"));		
        this.btnEdit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));		
        this.btnEdit.setEnabled(false);		
        this.btnEdit.setVisible(false);
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSave, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
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
        this.setBounds(new Rectangle(10, 10, 610, 440));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 610, 440));
        contOperator.setBounds(new Rectangle(9, 299, 270, 19));
        this.add(contOperator, new KDLayout.Constraints(9, 299, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRemark.setBounds(new Rectangle(10, 345, 565, 60));
        this.add(contRemark, new KDLayout.Constraints(10, 345, 565, 60, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer2.setBounds(new Rectangle(4, 150, 600, 130));
        this.add(kDContainer2, new KDLayout.Constraints(4, 150, 600, 130, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDContainer1.setBounds(new Rectangle(4, 4, 600, 130));
        this.add(kDContainer1, new KDLayout.Constraints(4, 4, 600, 130, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChangeDate.setBounds(new Rectangle(305, 299, 269, 19));
        this.add(contChangeDate, new KDLayout.Constraints(305, 299, 269, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contOperator
        contOperator.setBoundEditor(f7Operater);
        //contRemark
        contRemark.setBoundEditor(txtRemark);
        //kDContainer2
        kDContainer2.getContentPane().setLayout(new KDLayout());
        kDContainer2.getContentPane().putClientProperty("OriginalBounds", new Rectangle(4, 150, 600, 130));        contChooser.setBounds(new Rectangle(5, 63, 270, 19));
        kDContainer2.getContentPane().add(contChooser, new KDLayout.Constraints(5, 63, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contChooseRoomPhone.setBounds(new Rectangle(301, 63, 270, 19));
        kDContainer2.getContentPane().add(contChooseRoomPhone, new KDLayout.Constraints(301, 63, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDSeparator6.setBounds(new Rectangle(5, 34, 358, 14));
        kDContainer2.getContentPane().add(kDSeparator6, new KDLayout.Constraints(5, 34, 358, 14, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        newCus3.setBounds(new Rectangle(178, 14, 59, 19));
        kDContainer2.getContentPane().add(newCus3, new KDLayout.Constraints(178, 14, 59, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        newCus2.setBounds(new Rectangle(113, 14, 63, 19));
        kDContainer2.getContentPane().add(newCus2, new KDLayout.Constraints(113, 14, 63, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        newCus1.setBounds(new Rectangle(50, 14, 61, 19));
        kDContainer2.getContentPane().add(newCus1, new KDLayout.Constraints(50, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        newCustomer.setBounds(new Rectangle(5, 14, 44, 19));
        kDContainer2.getContentPane().add(newCustomer, new KDLayout.Constraints(5, 14, 44, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSelecCus.setBounds(new Rectangle(487, 14, 82, 19));
        kDContainer2.getContentPane().add(btnSelecCus, new KDLayout.Constraints(487, 14, 82, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        newCus5.setBounds(new Rectangle(302, 14, 61, 19));
        kDContainer2.getContentPane().add(newCus5, new KDLayout.Constraints(302, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        newCus4.setBounds(new Rectangle(239, 14, 61, 19));
        kDContainer2.getContentPane().add(newCus4, new KDLayout.Constraints(239, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contChooser
        contChooser.setBoundEditor(txtChooser);
        //contChooseRoomPhone
        contChooseRoomPhone.setBoundEditor(txtChooseRoomPhone);
        //kDContainer1
        kDContainer1.getContentPane().setLayout(new KDLayout());
        kDContainer1.getContentPane().putClientProperty("OriginalBounds", new Rectangle(4, 4, 600, 130));        oldCustomer.setBounds(new Rectangle(4, 15, 44, 19));
        kDContainer1.getContentPane().add(oldCustomer, new KDLayout.Constraints(4, 15, 44, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        oldCus1.setBounds(new Rectangle(49, 14, 61, 19));
        kDContainer1.getContentPane().add(oldCus1, new KDLayout.Constraints(49, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        oldCus2.setBounds(new Rectangle(112, 14, 63, 19));
        kDContainer1.getContentPane().add(oldCus2, new KDLayout.Constraints(112, 14, 63, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator5.setBounds(new Rectangle(4, 34, 360, 9));
        kDContainer1.getContentPane().add(kDSeparator5, new KDLayout.Constraints(4, 34, 360, 9, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        oldCus3.setBounds(new Rectangle(177, 14, 61, 19));
        kDContainer1.getContentPane().add(oldCus3, new KDLayout.Constraints(177, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        oldCus4.setBounds(new Rectangle(240, 14, 61, 19));
        kDContainer1.getContentPane().add(oldCus4, new KDLayout.Constraints(240, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        oldCus5.setBounds(new Rectangle(303, 14, 61, 19));
        kDContainer1.getContentPane().add(oldCus5, new KDLayout.Constraints(303, 14, 61, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cluesPhone.setBounds(new Rectangle(301, 65, 270, 19));
        kDContainer1.getContentPane().add(cluesPhone, new KDLayout.Constraints(301, 65, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(5, 65, 270, 19));
        kDContainer1.getContentPane().add(kDLabelContainer1, new KDLayout.Constraints(5, 65, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //cluesPhone
        cluesPhone.setBoundEditor(txtCluesPhone);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(txtyuanChooser);
        //contChangeDate
        contChangeDate.setBoundEditor(pkChangeDate);

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
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SincerityChangeNameEditUIHandler";
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
     * output txtRemark_actionPerformed method
     */
    protected void txtRemark_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output newCus3_mouseClicked method
     */
    protected void newCus3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output newCus2_mouseClicked method
     */
    protected void newCus2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output newCus1_mouseClicked method
     */
    protected void newCus1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output btnSelecCus_actionPerformed method
     */
    protected void btnSelecCus_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output newCus5_mouseClicked method
     */
    protected void newCus5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output newCus4_mouseClicked method
     */
    protected void newCus4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output oldCus1_mouseClicked method
     */
    protected void oldCus1_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output oldCus2_mouseClicked method
     */
    protected void oldCus2_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output oldCus3_mouseClicked method
     */
    protected void oldCus3_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output oldCus4_mouseClicked method
     */
    protected void oldCus4_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output oldCus5_mouseClicked method
     */
    protected void oldCus5_mouseClicked(java.awt.event.MouseEvent e) throws Exception
    {
    }

    	

    /**
     * output actionSave_actionPerformed method
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionEdit_actionPerformed method
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionEdit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionEdit() {
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
            innerActionPerformed("eas", AbstractSincerityChangeNameEditUI.this, "ActionSave", "actionSave_actionPerformed", e);
        }
    }

    /**
     * output ActionEdit class
     */     
    protected class ActionEdit extends ItemAction {     
    
        public ActionEdit()
        {
            this(null);
        }

        public ActionEdit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionEdit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionEdit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSincerityChangeNameEditUI.this, "ActionEdit", "actionEdit_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SincerityChangeNameEditUI");
    }




}