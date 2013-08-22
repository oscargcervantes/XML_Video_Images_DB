//package xmldbr;

import java.io.*;
import java.util.StringTokenizer;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import edu.lsivc.directory.Directory;
import edu.lsivc.directory.Index_Elements;
import edu.lsivc.directory.Persona;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * ElementsFrame.java
 *
 * Created on Aug 25, 2011, 12:10:12 PM
 */
//package xmldbr;

/**
 *
 * @author oscargcervantes
 */
public class ElementsFrame extends javax.swing.JFrame {
	/**
	 * @uml.property  name="dbdir"
	 */
	private String  dbdir;
    /**
	 * @uml.property  name="settingsdir"
	 */
    private String settingsdir;
    /**
	 * @uml.property  name="attr" multiplicity="(0 -1)" dimension="1"
	 */
    private String[] Attr;
    /**
	 * @uml.property  name="elements" multiplicity="(0 -1)" dimension="1"
	 */
    private String[] Elements;
    /**
	 * @uml.property  name="flag" multiplicity="(0 -1)" dimension="1"
	 */
    private boolean[] flag;
    private Object[] DBValues;
    private Object[] AngleValues;
    private Object[] CameraValues;
    private Object[] MotionValues;
    
    /** Creates new form ElementsFrame */
    public ElementsFrame() 
    {
        initComponents();
        LoadDirs();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Estatura = new javax.swing.JComboBox();
        EstaturaLabel = new javax.swing.JLabel();
        FechaNacimientiLabel = new javax.swing.JLabel();
        SexoLabel = new javax.swing.JLabel();
        PesoLabel = new javax.swing.JLabel();
        ColorPielLabel = new javax.swing.JLabel();
        ColorPeloLabel = new javax.swing.JLabel();
        FechaNacimientoCalendarButton = new org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton();
        FechaNacimientoTextField = new javax.swing.JTextField();
        Sexo = new javax.swing.JComboBox();
        Peso = new javax.swing.JComboBox();
        ColorPiel = new javax.swing.JComboBox();
        ColorPelo = new javax.swing.JComboBox();
        OKButton = new javax.swing.JButton();
        PersonaLabel = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        DBList = new javax.swing.JList();
        DBLabel = new javax.swing.JLabel();
        MotionLabel = new javax.swing.JLabel();
        AngleLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AngleList = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        MotionList = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        CamerasList = new javax.swing.JList();
        CamerasLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Estatura.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        
        DecimalFormat df = new DecimalFormat("#.##");
        
        for(double j=0.5;j<2.5;j+=.01)
        {	
          
        	Estatura.addItem(String.valueOf(df.format(j)));
         }
        Estatura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EstaturaActionPerformed(evt);
            }
        });

        EstaturaLabel.setText("Height:");

        FechaNacimientiLabel.setText("Birth Date:");

        SexoLabel.setText("Sex:");

        PesoLabel.setText("Weight:");

        ColorPielLabel.setText("Skin Color:");

        ColorPeloLabel.setText("Hair Color:");

        FechaNacimientoCalendarButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FechaNacimientoCalendarButtonMouseClicked(evt);
            }
        });
        FechaNacimientoCalendarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FechaNacimientoCalendarButtonActionPerformed(evt);
            }
        });
        FechaNacimientoCalendarButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                FechaNacimientoCalendarButtonPropertyChange(evt);
            }
        });
        
        FechaNacimientoCalendarButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateOnlyPopupChanged(evt);
            }
        });

        FechaNacimientoTextField.setText(" ");

        Sexo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        Sexo.addItem("Female");
        Sexo.addItem("Male");
        Sexo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SexoActionPerformed(evt);
            }
        });

        Peso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        for(int i=10;i<200;i++)
        {
          Peso.addItem(String.valueOf(i));	
         }	
        Peso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesoActionPerformed(evt);
            }
        });

        ColorPiel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        ColorPiel.addItem("Light");
        ColorPiel.addItem("Fair");
        ColorPiel.addItem("Medium");
        ColorPiel.addItem("Olive");
        ColorPiel.addItem("Brown");
        ColorPiel.addItem("Black");
        ColorPiel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColorPielActionPerformed(evt);
            }
        });

        ColorPelo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " " }));
        ColorPelo.addItem("Auburn");
        ColorPelo.addItem("Black");
        ColorPelo.addItem("Blonde");
        ColorPelo.addItem("Chestnut");
        ColorPelo.addItem("Gray");
        ColorPelo.addItem("Red");
        ColorPelo.addItem("White");
        
        ColorPelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColorPeloActionPerformed(evt);
            }
        });

        OKButton.setText("OK");
        OKButton.setEnabled(false);
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        PersonaLabel.setFont(new java.awt.Font("Ubuntu", 1, 24));
        PersonaLabel.setText("Persona");

        DBList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "DBGR", "DBFR" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        DBList.addListSelectionListener(new ListSelectionListener()
        {

			public void valueChanged(ListSelectionEvent e) 
			{
				if (e.getValueIsAdjusting() == false) 
				{

			        if (DBList.getSelectedIndex() == -1) 
			        {
			        
			         } 
			        else
			        {
			        //Selection, enable the fire button.
			        	DBValues = DBList.getSelectedValues();
						System.out.println("Values:" + DBValues[0].toString() + " Values length:" + DBValues.length);
						CheckAttrState();
			         }
			    }
				
			 }
		 });
        
        jScrollPane4.setViewportView(DBList);

        DBLabel.setText("DB:");

        MotionLabel.setText("Motion:");

        AngleLabel.setText("Angles:");

        AngleList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "45", "60", "90" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        AngleList.addListSelectionListener(new ListSelectionListener()
        {
			public void valueChanged(ListSelectionEvent e) 
			{
				if (e.getValueIsAdjusting() == false) 
				{

			        if (AngleList.getSelectedIndex() == -1) 
			        {
			        
			         } 
			        else
			        {
			        //Selection, enable the fire button.
			        	AngleValues = AngleList.getSelectedValues();
						System.out.println("Values:" + AngleValues[0].toString() + " Values length:" + AngleValues.length);
						CheckAttrState();
			         }
			    }
				
			 }
		 });
        
        jScrollPane1.setViewportView(AngleList);

        MotionList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "LR", "RL" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        MotionList.addListSelectionListener(new ListSelectionListener()
        {
			public void valueChanged(ListSelectionEvent e) 
			{
				if (e.getValueIsAdjusting() == false) 
				{
			        if (MotionList.getSelectedIndex() == -1) 
			        {
			        
			         } 
			        else
			        {
			        //Selection, enable the fire button.
			        	MotionValues = MotionList.getSelectedValues();
						System.out.println("Values:" + MotionValues[0].toString() + " Values length:" + MotionValues.length);
						CheckAttrState();
			         }
			    }	
			 }
		 });
        
        jScrollPane2.setViewportView(MotionList);

        CamerasList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "L", "R" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        CamerasList.addListSelectionListener(new ListSelectionListener()
        {
			public void valueChanged(ListSelectionEvent e) 
			{
				if (e.getValueIsAdjusting() == false) 
				{

			        if (CamerasList.getSelectedIndex() == -1) 
			        {
			        
			         } 
			        else
			        {
			        //Selection, enable the fire button.
			        	CameraValues = CamerasList.getSelectedValues();
						System.out.println("Values:" + CameraValues[0].toString() + " Values length:" + CameraValues.length);
						CheckAttrState();
			         }
			    }	
			 }
		 });
        
        jScrollPane3.setViewportView(CamerasList);

        CamerasLabel.setText("Cameras:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(189, 189, 189)
                .addComponent(PersonaLabel)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(394, Short.MAX_VALUE)
                .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(SexoLabel)
                    .addComponent(EstaturaLabel)
                    .addComponent(ColorPielLabel)
                    .addComponent(AngleLabel)
                    .addComponent(DBLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Estatura, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ColorPiel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ColorPeloLabel)
                    .addComponent(PesoLabel)
                    .addComponent(FechaNacimientiLabel)
                    .addComponent(MotionLabel)
                    .addComponent(CamerasLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(FechaNacimientoTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(FechaNacimientoCalendarButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Peso, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ColorPelo, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PersonaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(FechaNacimientoCalendarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 24, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(FechaNacimientiLabel)
                                .addComponent(FechaNacimientoTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 24, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PesoLabel)
                            .addComponent(Peso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ColorPeloLabel)
                            .addComponent(ColorPelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(MotionLabel)
                                .addGap(89, 89, 89)
                                .addComponent(CamerasLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Estatura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EstaturaLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SexoLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ColorPiel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ColorPielLabel))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AngleLabel)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DBLabel)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(38, 38, 38)
                .addComponent(OKButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void LoadDirs()
{
	try
    {		
	 FileInputStream fstream = new FileInputStream(System.getProperty("user.home") + File.separator + ".dbr");
     DataInputStream in = new DataInputStream(fstream);
     BufferedReader br = new BufferedReader(new InputStreamReader(in));
     String strLine;
    
		 while ((strLine = br.readLine()) != null)   
	     {
		   dbdir = strLine;
		  }
     in.close();
		 settingsdir = dbdir + ".Settings" + File.separator;
	 Index_Elements elm = new Index_Elements(dbdir,".Index.index", ".Elements.elms",".DBInitialized.db",".AllDirs", settingsdir);
	 Elements = elm.GetElements();	 
	 Attr = new String[Elements.length];
	 Attr[0] = " ";
	 flag = new boolean[Elements.length];
     }
    catch(Exception e)
    {
  	System.out.println(e.getMessage());  
     }
}

private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
// TODO add your handling code here:
	StringTokenizer tokens;
	String[] Videos, XMLs; //Store Video and XML file directories
	 
	Persona persona = new Persona(dbdir, settingsdir, "DBR", 1, 173, Elements, Attr, DBValues, AngleValues, CameraValues, MotionValues);
    String dirs = persona.Create(); //creates a new Persona directory in db
    this.setVisible(false);
}//GEN-LAST:event_OKButtonActionPerformed

private void EstaturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EstaturaActionPerformed
// TODO add your handling code here:
    Attr[1] = (String)Estatura.getSelectedItem();
    System.out.println(Attr[1]);
    //Estatura.setEnabled(false);
    flag[1] = true;
    CheckAttrState();
}//GEN-LAST:event_EstaturaActionPerformed

private void SexoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SexoActionPerformed
// TODO add your handling code here:
	Attr[3] = (String)Sexo.getSelectedItem();
	System.out.println(Attr[3]);
	//Sexo.setEnabled(false);
	flag[3] = true;
	CheckAttrState();
}//GEN-LAST:event_SexoActionPerformed

private void PesoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesoActionPerformed
// TODO add your handling code here:
	Attr[4] = (String)Peso.getSelectedItem();
	System.out.println(Attr[4]);
	//Peso.setEnabled(false);
	flag[4] = true;
	CheckAttrState();
}//GEN-LAST:event_PesoActionPerformed

private void ColorPielActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorPielActionPerformed
// TODO add your handling code here:
	Attr[5] = (String)ColorPiel.getSelectedItem();
	System.out.println(Attr[5]);
	//ColorPiel.setEnabled(false);
	flag[5] = true;
	CheckAttrState();
}//GEN-LAST:event_ColorPielActionPerformed

private void ColorPeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorPeloActionPerformed
// TODO add your handling code here:
	Attr[6] = (String)ColorPelo.getSelectedItem();
	System.out.println(Attr[6]);
	//ColorPelo.setEnabled(false);
	flag[6] = true;
	CheckAttrState();
}//GEN-LAST:event_ColorPeloActionPerformed

public void setDate(Date date)
{
    String dateString = "";
    if (date != null)
    {	
		//dateString = dateFormat.format(date);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		dateString = formatter.format(date);
    }
	Attr[2] = dateString;
    System.out.println(Attr[2]);
    flag[2] = true;
	CheckAttrState();
    FechaNacimientoTextField.setText(dateString);
    FechaNacimientoCalendarButton.setTargetDate(date);
}

private void CheckAttrState()
{
  if((flag[1]==true) && (flag[6]==true) && (flag[2]==true) && (flag[3]==true) && (flag[4]==true) && (flag[5]==true) && (MotionValues!=null && MotionValues.length>=1) && (CameraValues!=null && CameraValues.length>=1) && (AngleValues!=null && AngleValues.length>=1) && (DBValues!=null && DBValues.length>=1))
  {
	 OKButton.setEnabled(true);
  }	  
}
private void FechaNacimientoCalendarButtonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_FechaNacimientoCalendarButtonPropertyChange
// TODO add your handling code here:
  //Attr[1] = (String)FechaNacimientoCalendarButton.getTargetDate().toString();
	//System.out.println(FechaNacimientoCalendarButton.get);
}//GEN-LAST:event_FechaNacimientoCalendarButtonPropertyChange

private void FechaNacimientoCalendarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FechaNacimientoCalendarButtonActionPerformed
// TODO add your handling code here:
	
}//GEN-LAST:event_FechaNacimientoCalendarButtonActionPerformed

private void FechaNacimientoCalendarButtonMouseClicked(java.awt.event.MouseEvent evt) {                                                           
// TODO add your handling code here:
    
}                                                         

private void dateOnlyPopupChanged(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateOnlyPopupChanged
    if (evt.getNewValue() instanceof Date)
        setDate((Date)evt.getNewValue());
}
    // Variables declaration - do not modify//GEN-BEGIN:variables
    /**
	 * @uml.property  name="angleLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel AngleLabel;
    /**
	 * @uml.property  name="angleList"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JList AngleList;
    /**
	 * @uml.property  name="camerasLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel CamerasLabel;
    /**
	 * @uml.property  name="camerasList"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JList CamerasList;
    /**
	 * @uml.property  name="colorPelo"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private javax.swing.JComboBox ColorPelo;
    /**
	 * @uml.property  name="colorPeloLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel ColorPeloLabel;
    /**
	 * @uml.property  name="colorPiel"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private javax.swing.JComboBox ColorPiel;
    /**
	 * @uml.property  name="colorPielLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel ColorPielLabel;
    /**
	 * @uml.property  name="dBLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel DBLabel;
    /**
	 * @uml.property  name="dBList"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JList DBList;
    /**
	 * @uml.property  name="estatura"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private javax.swing.JComboBox Estatura;
    /**
	 * @uml.property  name="estaturaLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel EstaturaLabel;
    /**
	 * @uml.property  name="fechaNacimientiLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel FechaNacimientiLabel;
    /**
	 * @uml.property  name="fechaNacimientoCalendarButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private org.jbundle.thin.base.screen.jcalendarbutton.JCalendarButton FechaNacimientoCalendarButton;
    /**
	 * @uml.property  name="fechaNacimientoTextField"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JTextField FechaNacimientoTextField;
    /**
	 * @uml.property  name="motionLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel MotionLabel;
    /**
	 * @uml.property  name="motionList"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JList MotionList;
    /**
	 * @uml.property  name="oKButton"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JButton OKButton;
    /**
	 * @uml.property  name="personaLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel PersonaLabel;
    /**
	 * @uml.property  name="peso"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private javax.swing.JComboBox Peso;
    /**
	 * @uml.property  name="pesoLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel PesoLabel;
    /**
	 * @uml.property  name="sexo"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
    private javax.swing.JComboBox Sexo;
    /**
	 * @uml.property  name="sexoLabel"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JLabel SexoLabel;
    /**
	 * @uml.property  name="jScrollPane1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JScrollPane jScrollPane1;
    /**
	 * @uml.property  name="jScrollPane2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JScrollPane jScrollPane2;
    /**
	 * @uml.property  name="jScrollPane3"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JScrollPane jScrollPane3;
    /**
	 * @uml.property  name="jScrollPane4"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}