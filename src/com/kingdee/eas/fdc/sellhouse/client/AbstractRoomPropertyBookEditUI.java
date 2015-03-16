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
public abstract class AbstractRoomPropertyBookEditUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractRoomPropertyBookEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDPanel pnlRoomInfo;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel3;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPropertyNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMortgageDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMortgageBank;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contScheme;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransactDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPromFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox checkBox;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane2;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPropertyNumber;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkMortgageDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtMortgageBank;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtScheme;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkTransactDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPromFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox combPropertyState;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkActFinishDate;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblEntryTwo;
    protected com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractRoomPropertyBookEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractRoomPropertyBookEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.pnlRoomInfo = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel3 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPropertyNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMortgageDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMortgageBank = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contScheme = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransactDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPromFinishDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.checkBox = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contActFinishDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane2 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtPropertyNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkMortgageDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtMortgageBank = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtScheme = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkTransactDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkPromFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.combPropertyState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.pkActFinishDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.tblEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblEntryTwo = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.pnlRoomInfo.setName("pnlRoomInfo");
        this.kDPanel3.setName("kDPanel3");
        this.sclPanel.setName("sclPanel");
        this.contCreateTime.setName("contCreateTime");
        this.contCreator.setName("contCreator");
        this.contNumber.setName("contNumber");
        this.contPropertyNumber.setName("contPropertyNumber");
        this.contMortgageDate.setName("contMortgageDate");
        this.contMortgageBank.setName("contMortgageBank");
        this.contScheme.setName("contScheme");
        this.contTransactDate.setName("contTransactDate");
        this.contPromFinishDate.setName("contPromFinishDate");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.checkBox.setName("checkBox");
        this.contActFinishDate.setName("contActFinishDate");
        this.contDescription.setName("contDescription");
        this.kDTabbedPane2.setName("kDTabbedPane2");
        this.pkCreateTime.setName("pkCreateTime");
        this.prmtCreator.setName("prmtCreator");
        this.txtNumber.setName("txtNumber");
        this.txtPropertyNumber.setName("txtPropertyNumber");
        this.pkMortgageDate.setName("pkMortgageDate");
        this.txtMortgageBank.setName("txtMortgageBank");
        this.prmtScheme.setName("prmtScheme");
        this.pkTransactDate.setName("pkTransactDate");
        this.pkPromFinishDate.setName("pkPromFinishDate");
        this.combPropertyState.setName("combPropertyState");
        this.pkActFinishDate.setName("pkActFinishDate");
        this.txtDescription.setName("txtDescription");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.tblEntry.setName("tblEntry");
        this.tblEntryTwo.setName("tblEntryTwo");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));
        // pnlRoomInfo		
        this.pnlRoomInfo.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("pnlRoomInfo.border.title")));
        // kDPanel3		
        this.kDPanel3.setBorder(new TitledBorder(BorderFactory.createEtchedBorder(new Color(255,255,255),new Color(148,145,140)), resHelper.getString("kDPanel3.border.title")));
        // sclPanel		
        this.sclPanel.setAutoscrolls(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setEnabled(false);		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);		
        this.contCreator.setEnabled(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contPropertyNumber		
        this.contPropertyNumber.setBoundLabelLength(100);		
        this.contPropertyNumber.setBoundLabelUnderline(true);		
        this.contPropertyNumber.setBoundLabelText(resHelper.getString("contPropertyNumber.boundLabelText"));
        // contMortgageDate		
        this.contMortgageDate.setBoundLabelText(resHelper.getString("contMortgageDate.boundLabelText"));		
        this.contMortgageDate.setBoundLabelLength(100);		
        this.contMortgageDate.setBoundLabelUnderline(true);		
        this.contMortgageDate.setEnabled(false);
        // contMortgageBank		
        this.contMortgageBank.setBoundLabelText(resHelper.getString("contMortgageBank.boundLabelText"));		
        this.contMortgageBank.setBoundLabelLength(100);		
        this.contMortgageBank.setBoundLabelUnderline(true);		
        this.contMortgageBank.setEnabled(false);
        // contScheme		
        this.contScheme.setBoundLabelText(resHelper.getString("contScheme.boundLabelText"));		
        this.contScheme.setBoundLabelLength(100);		
        this.contScheme.setBoundLabelUnderline(true);
        // contTransactDate		
        this.contTransactDate.setBoundLabelText(resHelper.getString("contTransactDate.boundLabelText"));		
        this.contTransactDate.setBoundLabelLength(100);		
        this.contTransactDate.setBoundLabelUnderline(true);
        // contPromFinishDate		
        this.contPromFinishDate.setBoundLabelText(resHelper.getString("contPromFinishDate.boundLabelText"));		
        this.contPromFinishDate.setBoundLabelLength(100);		
        this.contPromFinishDate.setBoundLabelUnderline(true);
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        this.kDLabelContainer1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    kDLabelContainer1_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // checkBox		
        this.checkBox.setText(resHelper.getString("checkBox.text"));
        this.checkBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    checkBox_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.checkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    checkBox_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.checkBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    checkBox_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contActFinishDate		
        this.contActFinishDate.setBoundLabelText(resHelper.getString("contActFinishDate.boundLabelText"));		
        this.contActFinishDate.setBoundLabelLength(80);		
        this.contActFinishDate.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // kDTabbedPane2
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(30);		
        this.txtNumber.setRequired(true);
        // txtPropertyNumber		
        this.txtPropertyNumber.setMaxLength(30);
        // pkMortgageDate
        // txtMortgageBank
        // prmtScheme		
        this.prmtScheme.setRequired(true);		
        this.prmtScheme.setDisplayFormat("$name$");		
        this.prmtScheme.setEditFormat("$name$");		
        this.prmtScheme.setCommitFormat("$name$");		
        this.prmtScheme.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7PropertyDoSchemeQuery");		
        this.prmtScheme.setEditable(true);
        this.prmtScheme.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtScheme_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkTransactDate
        // pkPromFinishDate
        // combPropertyState		
        this.combPropertyState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PropertyStateEnum").toArray());		
        this.combPropertyState.setPrototypeDisplayValue("-1");
        this.combPropertyState.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    combPropertyState_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.combPropertyState.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    combPropertyState_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkActFinishDate		
        this.pkActFinishDate.setEnabled(false);
        // txtDescription		
        this.txtDescription.setMaxLength(80);
        // kDPanel1
        this.kDPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent e) {
                try {
                    kDPanel1_mouseExited(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDPanel1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                try {
                    kDPanel1_focusGained(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        this.kDPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent e) {
                try {
                    kDPanel1_keyPressed(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void keyReleased(java.awt.event.KeyEvent e) {
                try {
                    kDPanel1_keyReleased(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // kDPanel2
        // tblEntry
		String tblEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol1\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol7\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" t:styleID=\"sCol1\" /><t:Column t:key=\"promFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"isFinish\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"transactor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"description\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"processDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"isFinishFlag\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{promFinishDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actFinishDate}</t:Cell><t:Cell>$Resource{transactor}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{processDate}</t:Cell><t:Cell>$Resource{isFinishFlag}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblEntry.setFormatXml(resHelper.translateString("tblEntry",tblEntryStrXML));
        this.tblEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntry_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntry_editStopping(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblEntry.putBindContents("editData",new String[] {"id","name","promiseFinishDate","isFinish","actualFinishDate","transactor","description","processDate","isFinishFlag"});


        // tblEntryTwo
		String tblEntryTwoStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"name\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"isFinish\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"processDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"description\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{processDate}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblEntryTwo.setFormatXml(resHelper.translateString("tblEntryTwo",tblEntryTwoStrXML));
        this.tblEntryTwo.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    tblEntryTwo_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.tblEntryTwo.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntryTwo_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
            public void editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    tblEntryTwo_editValueChanged(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.tblEntryTwo.putBindContents("editData",new String[] {"name","isFinish","processDate","description","id"});


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
        this.setBounds(new Rectangle(10, 10, 1013, 750));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 750));
        pnlRoomInfo.setBounds(new Rectangle(4, 5, 1005, 136));
        this.add(pnlRoomInfo, new KDLayout.Constraints(4, 5, 1005, 136, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDPanel3.setBounds(new Rectangle(4, 147, 1005, 593));
        this.add(kDPanel3, new KDLayout.Constraints(4, 147, 1005, 593, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //pnlRoomInfo
pnlRoomInfo.setLayout(new BorderLayout(0, 0));        pnlRoomInfo.add(sclPanel, BorderLayout.CENTER);
        //kDPanel3
        kDPanel3.setLayout(new KDLayout());
        kDPanel3.putClientProperty("OriginalBounds", new Rectangle(4, 147, 1005, 593));        contCreateTime.setBounds(new Rectangle(30, 548, 270, 19));
        kDPanel3.add(contCreateTime, new KDLayout.Constraints(30, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreator.setBounds(new Rectangle(364, 548, 270, 19));
        kDPanel3.add(contCreator, new KDLayout.Constraints(364, 548, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(30, 21, 270, 19));
        kDPanel3.add(contNumber, new KDLayout.Constraints(30, 21, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPropertyNumber.setBounds(new Rectangle(30, 43, 270, 19));
        kDPanel3.add(contPropertyNumber, new KDLayout.Constraints(30, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMortgageDate.setBounds(new Rectangle(30, 65, 270, 19));
        kDPanel3.add(contMortgageDate, new KDLayout.Constraints(30, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMortgageBank.setBounds(new Rectangle(364, 65, 270, 19));
        kDPanel3.add(contMortgageBank, new KDLayout.Constraints(364, 65, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contScheme.setBounds(new Rectangle(364, 43, 270, 19));
        kDPanel3.add(contScheme, new KDLayout.Constraints(364, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransactDate.setBounds(new Rectangle(364, 21, 270, 19));
        kDPanel3.add(contTransactDate, new KDLayout.Constraints(364, 21, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPromFinishDate.setBounds(new Rectangle(698, 21, 270, 19));
        kDPanel3.add(contPromFinishDate, new KDLayout.Constraints(698, 21, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer1.setBounds(new Rectangle(698, 43, 270, 19));
        kDPanel3.add(kDLabelContainer1, new KDLayout.Constraints(698, 43, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        checkBox.setBounds(new Rectangle(698, 65, 78, 19));
        kDPanel3.add(checkBox, new KDLayout.Constraints(698, 65, 78, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contActFinishDate.setBounds(new Rectangle(786, 65, 182, 19));
        kDPanel3.add(contActFinishDate, new KDLayout.Constraints(786, 65, 182, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDescription.setBounds(new Rectangle(30, 87, 939, 48));
        kDPanel3.add(contDescription, new KDLayout.Constraints(30, 87, 939, 48, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTabbedPane2.setBounds(new Rectangle(30, 169, 939, 356));
        kDPanel3.add(kDTabbedPane2, new KDLayout.Constraints(30, 169, 939, 356, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contPropertyNumber
        contPropertyNumber.setBoundEditor(txtPropertyNumber);
        //contMortgageDate
        contMortgageDate.setBoundEditor(pkMortgageDate);
        //contMortgageBank
        contMortgageBank.setBoundEditor(txtMortgageBank);
        //contScheme
        contScheme.setBoundEditor(prmtScheme);
        //contTransactDate
        contTransactDate.setBoundEditor(pkTransactDate);
        //contPromFinishDate
        contPromFinishDate.setBoundEditor(pkPromFinishDate);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(combPropertyState);
        //contActFinishDate
        contActFinishDate.setBoundEditor(pkActFinishDate);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //kDTabbedPane2
        kDTabbedPane2.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane2.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblEntry, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(tblEntryTwo, BorderLayout.CENTER);

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
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("propertyNumber", String.class, this.txtPropertyNumber, "text");
		dataBinder.registerBinding("mortgageDate", java.util.Date.class, this.pkMortgageDate, "value");
		dataBinder.registerBinding("mortgageBank", String.class, this.txtMortgageBank, "text");
		dataBinder.registerBinding("propertyDoScheme", com.kingdee.eas.fdc.sellhouse.PropertyDoSchemeInfo.class, this.prmtScheme, "data");
		dataBinder.registerBinding("transactDate", java.util.Date.class, this.pkTransactDate, "value");
		dataBinder.registerBinding("promiseFinishDate", java.util.Date.class, this.pkPromFinishDate, "value");
		dataBinder.registerBinding("propertyState", com.kingdee.eas.fdc.sellhouse.PropertyStateEnum.class, this.combPropertyState, "selectedItem");
		dataBinder.registerBinding("actualFinishDate", java.util.Date.class, this.pkActFinishDate, "value");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("entry.id", com.kingdee.bos.util.BOSUuid.class, this.tblEntry, "id.text");
		dataBinder.registerBinding("entry.processDate", java.util.Date.class, this.tblEntry, "processDate.text");
		dataBinder.registerBinding("entry.isFinish", boolean.class, this.tblEntry, "isFinish.text");
		dataBinder.registerBinding("entry.name", String.class, this.tblEntry, "name.text");
		dataBinder.registerBinding("entry.description", String.class, this.tblEntry, "description.text");
		dataBinder.registerBinding("entry.promiseFinishDate", java.util.Date.class, this.tblEntry, "promFinishDate.text");
		dataBinder.registerBinding("entry.actualFinishDate", java.util.Date.class, this.tblEntry, "actFinishDate.text");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryInfo.class, this.tblEntry, "userObject");
		dataBinder.registerBinding("entry.transactor", com.kingdee.eas.base.permission.UserInfo.class, this.tblEntry, "transactor.text");
		dataBinder.registerBinding("entry.isFinishFlag", boolean.class, this.tblEntry, "isFinishFlag.text");
		dataBinder.registerBinding("entryTwo.id", com.kingdee.bos.util.BOSUuid.class, this.tblEntryTwo, "id.text");
		dataBinder.registerBinding("entryTwo.processDate", java.util.Date.class, this.tblEntryTwo, "processDate.text");
		dataBinder.registerBinding("entryTwo.isFinish", boolean.class, this.tblEntryTwo, "isFinish.text");
		dataBinder.registerBinding("entryTwo.name", String.class, this.tblEntryTwo, "name.text");
		dataBinder.registerBinding("entryTwo.description", String.class, this.tblEntryTwo, "description.text");
		dataBinder.registerBinding("entryTwo", com.kingdee.eas.fdc.sellhouse.RoomPropertyBookEntryTwoInfo.class, this.tblEntryTwo, "userObject");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.RoomPropertyBookEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.RoomPropertyBookInfo)ov;
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mortgageDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mortgageBank", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyDoScheme", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transactDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("promiseFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("propertyState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.processDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.promiseFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.actualFinishDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.transactor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.isFinishFlag", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entryTwo.id", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entryTwo.processDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entryTwo.isFinish", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entryTwo.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entryTwo.description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entryTwo", ValidateHelper.ON_SAVE);    		
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
     * output kDLabelContainer1_propertyChange method
     */
    protected void kDLabelContainer1_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output checkBox_stateChanged method
     */
    protected void checkBox_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output checkBox_actionPerformed method
     */
    protected void checkBox_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output checkBox_itemStateChanged method
     */
    protected void checkBox_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output prmtScheme_dataChanged method
     */
    protected void prmtScheme_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output combPropertyState_propertyChange method
     */
    protected void combPropertyState_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output combPropertyState_itemStateChanged method
     */
    protected void combPropertyState_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_mouseExited method
     */
    protected void kDPanel1_mouseExited(java.awt.event.MouseEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_focusGained method
     */
    protected void kDPanel1_focusGained(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_keyPressed method
     */
    protected void kDPanel1_keyPressed(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output kDPanel1_keyReleased method
     */
    protected void kDPanel1_keyReleased(java.awt.event.KeyEvent e) throws Exception
    {
    }

    /**
     * output tblEntry_editStopped method
     */
    protected void tblEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblEntry_editValueChanged method
     */
    protected void tblEntry_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblEntry_editStopping method
     */
    protected void tblEntry_editStopping(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblEntryTwo_editStopped method
     */
    protected void tblEntryTwo_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblEntryTwo_editValueChanged method
     */
    protected void tblEntryTwo_editValueChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output tblEntryTwo_activeCellChanged method
     */
    protected void tblEntryTwo_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("propertyNumber"));
        sic.add(new SelectorItemInfo("mortgageDate"));
        sic.add(new SelectorItemInfo("mortgageBank"));
        sic.add(new SelectorItemInfo("propertyDoScheme.*"));
        sic.add(new SelectorItemInfo("transactDate"));
        sic.add(new SelectorItemInfo("promiseFinishDate"));
        sic.add(new SelectorItemInfo("propertyState"));
        sic.add(new SelectorItemInfo("actualFinishDate"));
        sic.add(new SelectorItemInfo("description"));
    sic.add(new SelectorItemInfo("entry.id"));
    sic.add(new SelectorItemInfo("entry.processDate"));
    sic.add(new SelectorItemInfo("entry.isFinish"));
    sic.add(new SelectorItemInfo("entry.name"));
    sic.add(new SelectorItemInfo("entry.description"));
    sic.add(new SelectorItemInfo("entry.promiseFinishDate"));
    sic.add(new SelectorItemInfo("entry.actualFinishDate"));
        sic.add(new SelectorItemInfo("entry.*"));
//        sic.add(new SelectorItemInfo("entry.number"));
        sic.add(new SelectorItemInfo("entry.transactor.*"));
//        sic.add(new SelectorItemInfo("entry.transactor.number"));
    sic.add(new SelectorItemInfo("entry.isFinishFlag"));
    sic.add(new SelectorItemInfo("entryTwo.id"));
    sic.add(new SelectorItemInfo("entryTwo.processDate"));
    sic.add(new SelectorItemInfo("entryTwo.isFinish"));
    sic.add(new SelectorItemInfo("entryTwo.name"));
    sic.add(new SelectorItemInfo("entryTwo.description"));
        sic.add(new SelectorItemInfo("entryTwo.*"));
//        sic.add(new SelectorItemInfo("entryTwo.number"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "RoomPropertyBookEditUI");
    }




}