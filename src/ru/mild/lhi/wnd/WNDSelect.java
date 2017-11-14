/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
* Author : Ruan Barros 
* Date : ?/04/2016
*/


package ru.mild.lhi.wnd;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ru.mild.lhi.bean.VertexType;

/**
 *
 * @author dmitry
 */
public class WNDSelect extends JDialog {

    /**
     * Creates new form WNDSelect
     */
    private int scale = 100;
    private Image image = null;
    private File lastImage;
    private final WNDMainWindow caller;

    public WNDSelect(WNDMainWindow calling) {

        File defaultFolder = new File("Maps");

        this.caller = calling;
        initComponents();
        jLbDel.setVisible(false);
        this.setVisible(false);
        this.setLocationRelativeTo(null);

        try {
               setIconImage(ImageIO.read(getClass().getResourceAsStream("/ru/mild/lhi/gfx/labyhinth.png")));
        } catch (IOException ex) {
            Logger.getLogger(WNDMainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        setTitle(WNDMainWindow.MAIN_TITLE + " - Select Map");

        if (defaultFolder.exists()) {
            for (File f : defaultFolder.listFiles()) {
                if (f.isDirectory()) {
                    ((DefaultTableModel) jTfolder.getModel()).addRow(new Object[]{f});
                }
            }
            this.setVisible(true);
        } else {

            this.dispose();

            defaultFolder.mkdir();
            new File("Maps/Custom").mkdir();

            int choice = JOptionPane.showConfirmDialog(null, "Maps folder no longer exist\n\n\nI've created a new, do not erase this time, little ninny!!!\n\nPut your maps in Maps folder and try again.\n\n\nDo you have the maps, have you?", "Ooops!", JOptionPane.INFORMATION_MESSAGE);
            if (choice != JOptionPane.YES_OPTION) {
                try {
                    Desktop.getDesktop().browse(new URI("https://mega.nz/#F!c4wjXQ6K!f7ruSMmkF5NWAgQCUTKDRw"));
                    JOptionPane.showMessageDialog(null, "Oh gosh... \n\n\n Allright allright, I'll be patient with you... \n\n Take this as a gift.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Oh gosh... \n\n\n Allright allright, I'll be patient with you... but CREATE or DOWNLOAD the MAPS!");
                }
            }

        }
    }

    public void buildInformations() {
        jLbDel.setVisible(false);
        int matrixX = 0;
        int cellWidth = 0;
        int matrixY = 0;
        int cellHeight = 0;
        BufferedImage bufferedImage = (BufferedImage) image;
        if (bufferedImage != null) {
            for (int i = 1; i < bufferedImage.getWidth(null); i++) {
                if (VertexType.isCloserThat(bufferedImage.getRGB(i, 1), Color.BLACK.getRGB(), 100)) {
                    matrixX = bufferedImage.getWidth(null) / i;
                    cellWidth = i;
                    break;
                }
            }
            for (int j = 1; j < bufferedImage.getHeight(); j++) {
                if (bufferedImage.getRGB(1, j) == Color.BLACK.getRGB()) {
                    matrixY = bufferedImage.getHeight() / j;
                    cellHeight = j;
                    break;
                }
            }
            if (!(matrixX + matrixY > 100)) {
                jLbMapInfo.setForeground(Color.WHITE);
                jLbMapInfo.setText("[" + matrixX + "," + matrixY + "] labyrinth");
            } else {
                jLbMapInfo.setForeground(Color.RED);
                jLbMapInfo.setText("[" + matrixX + "," + matrixY + "] megalabyrinth, WOOOW!");
            }

            if (!(cellWidth + cellHeight > 60)) {
                jLbImageInfo.setForeground(Color.WHITE);
                jLbImageInfo.setText("[" + cellWidth + "," + cellHeight + "] cell");
            } else {
                jLbImageInfo.setForeground(Color.RED);
                jLbImageInfo.setText("[" + cellWidth + "," + cellHeight + "] hypercell, WOOOW!");
            }
        } else {
            throw new Error("BufferedImage cannot be null in Spectrum(image)");
        }

        if (matrixX < 2 || matrixY < 2 || cellWidth < 5 || cellHeight < 5) {
            jLbAtrocity.setVisible(true);
            jLbMapInfo.setText("Are you kidding me?");
            jLbImageInfo.setText("It is not a Map!");
            jLbImageInfo.setForeground(Color.YELLOW);
            jLbMapInfo.setForeground(Color.YELLOW);
            jBselect.setVisible(false);
            jLbDel.setVisible(true);
        } else {
            jLbAtrocity.setVisible(false);
            jBselect.setVisible(true);
        }

        System.gc();
    }

    private void changeScale(int newScale) {
        if (image != null) {
            scale = newScale;
            jTperc.setText(scale + "%");

            int width = (image.getWidth(null) * newScale) / 100;
            int height = (image.getHeight(null) * newScale) / 100;
            jLbPreview.setIcon(new ImageIcon(image.getScaledInstance(width, height, 0)));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTfolder = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTmap = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLbPlus = new javax.swing.JLabel();
        jTperc = new javax.swing.JLabel();
        jLbMinus = new javax.swing.JLabel();
        jBselect = new javax.swing.JButton();
        jLbMapInfo = new javax.swing.JLabel();
        jLbImageInfo = new javax.swing.JLabel();
        jLbDel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLbAtrocity = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLbPreview = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 0, 0));

        jTfolder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Map Type"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTfolder.setGridColor(new java.awt.Color(153, 0, 0));
        jTfolder.setSelectionBackground(new java.awt.Color(255, 102, 102));
        jTfolder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTfolderMouseReleased(evt);
            }
        });
        jTfolder.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTfolderKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(jTfolder);
        if (jTfolder.getColumnModel().getColumnCount() > 0) {
            jTfolder.getColumnModel().getColumn(0).setResizable(false);
        }

        jTmap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Map"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTmap.setGridColor(new java.awt.Color(153, 0, 0));
        jTmap.setSelectionBackground(new java.awt.Color(255, 102, 102));
        jTmap.setVerifyInputWhenFocusTarget(false);
        jTmap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTmapMouseReleased(evt);
            }
        });
        jTmap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTmapKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTmapKeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTmap);
        if (jTmap.getColumnModel().getColumnCount() > 0) {
            jTmap.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Preview", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Dialog", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setOpaque(false);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setOpaque(false);

        jLbPlus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/mild/lhi/gfx/zoomIn.png"))); // NOI18N
        jLbPlus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbPlus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLbPlusMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbPlusMouseClicked(evt);
            }
        });

        jTperc.setBackground(new java.awt.Color(51, 51, 51));
        jTperc.setForeground(new java.awt.Color(255, 255, 255));
        jTperc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jTperc.setText("100 %");
        jTperc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTperc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTperc.setOpaque(true);
        jTperc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTpercMouseClicked(evt);
            }
        });

        jLbMinus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/mild/lhi/gfx/zoomOut.png"))); // NOI18N
        jLbMinus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbMinus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLbMinusMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbMinusMouseClicked(evt);
            }
        });

        jBselect.setBackground(new java.awt.Color(51, 0, 0));
        jBselect.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jBselect.setForeground(new java.awt.Color(255, 255, 255));
        jBselect.setText("Select");
        jBselect.setBorder(null);
        jBselect.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jBselect.setEnabled(false);
        jBselect.setFocusPainted(false);
        jBselect.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        jBselect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBselectActionPerformed(evt);
            }
        });
        jBselect.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jBselectKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLbMinus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTperc, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbPlus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBselect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jBselect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTperc, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbMinus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbPlus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLbMapInfo.setForeground(new java.awt.Color(255, 255, 255));
        jLbMapInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbMapInfo.setText(" ");

        jLbImageInfo.setForeground(new java.awt.Color(255, 255, 255));
        jLbImageInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbImageInfo.setText(" ");

        jLbDel.setForeground(new java.awt.Color(255, 51, 51));
        jLbDel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbDel.setText("Click here to delete this atrocity!");
        jLbDel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLbDelMouseClicked(evt);
            }
        });

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLbAtrocity.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLbAtrocity.setForeground(new java.awt.Color(255, 0, 0));
        jLbAtrocity.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbAtrocity.setText("ATROCITY DETECTED!");
        jPanel4.add(jLbAtrocity, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 280));
        jLbAtrocity.setVisible(false);

        jLbPreview.setBackground(new java.awt.Color(255, 255, 255));
        jLbPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbPreview.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 0), 1, true));
        jLbPreview.setOpaque(true);
        jScrollPane1.setViewportView(jLbPreview);

        jPanel4.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 312, 279));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLbDel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbMapInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLbImageInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLbImageInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbMapInfo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLbDel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowLostFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowLostFocus
        this.dispose();
        System.gc();
    }//GEN-LAST:event_formWindowLostFocus

    private void jTfolderMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTfolderMouseReleased
        selectType();
    }//GEN-LAST:event_jTfolderMouseReleased

    private void selectType() {
        if (jTfolder.getSelectedRow() >= 0) {
            ((DefaultTableModel) jTmap.getModel()).setNumRows(0);
            File atualFolder = (File) ((DefaultTableModel) jTfolder.getModel()).getValueAt(jTfolder.getSelectedRow(), 0);
            if (atualFolder.exists()) {
                for (File f : atualFolder.listFiles()) {
                    if (f.getName().endsWith(".png")) {
                        ((DefaultTableModel) jTmap.getModel()).addRow(new Object[]{f});
                    }
                }
            }
        }
    }

    private void jTmapMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTmapMouseReleased
        select();

    }//GEN-LAST:event_jTmapMouseReleased

    private void select() {
        if (jTmap.getSelectedRow() >= 0) {
            File atualImage = (File) ((DefaultTableModel) jTmap.getModel()).getValueAt(jTmap.getSelectedRow(), 0);
            if (atualImage.getName().endsWith(".png")) {
                try {
                    image = ImageIO.read(atualImage);
                    lastImage = atualImage;
                    jLbPreview.setIcon(new ImageIcon(image));
                    jLbPreview.repaint();
                    int lbW = jLbPreview.getWidth();
                    int iW = image.getWidth(null);
                    changeScale((lbW <= 0 || iW <= 0 ? 100 : lbW * 100 / iW) - 10);
                    buildInformations();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error in image opening", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            jBselect.setEnabled(true);
        }
        System.gc();
    }


    private void jLbMinusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbMinusMouseReleased

    }//GEN-LAST:event_jLbMinusMouseReleased

    private void jLbMinusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbMinusMouseClicked
        if (evt.isControlDown()) {
            changeScale(scale - 100);
        } else {
            changeScale(scale - 10);
        }
    }//GEN-LAST:event_jLbMinusMouseClicked

    private void jTpercMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTpercMouseClicked
        changeScale(100);
    }//GEN-LAST:event_jTpercMouseClicked

    private void jLbPlusMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbPlusMouseReleased

    }//GEN-LAST:event_jLbPlusMouseReleased

    private void jLbPlusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbPlusMouseClicked
        if (evt.isControlDown()) {
            changeScale(scale + 100);
        } else {
            changeScale(scale + 10);
        }
    }//GEN-LAST:event_jLbPlusMouseClicked

    private void jBselectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBselectActionPerformed
        if (image != null) {
            if (image.getWidth(null) > 1000 || image.getHeight(null) > 1000) {
                int a = JOptionPane.showConfirmDialog(null, "Big image detected, [" + image.getWidth(null) + "," + image.getHeight(null) + "] exactly. Are you sure you want to continue?", "Big Image!", JOptionPane.INFORMATION_MESSAGE);
                if (a == JOptionPane.YES_OPTION) {
                    caller.buildNrun(lastImage);
                    this.dispose();
                }
            } else {
                caller.buildNrun(lastImage);
                this.dispose();
            }

        } else {
            JOptionPane.showMessageDialog(null, "Select an image", "Error", JOptionPane.ERROR_MESSAGE);
        }
        System.gc();
    }//GEN-LAST:event_jBselectActionPerformed

    private void jLbDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLbDelMouseClicked

        ((DefaultTableModel) jTmap.getModel()).setNumRows(0);
        jLbPreview.setIcon(null);
        lastImage.delete();

    }//GEN-LAST:event_jLbDelMouseClicked

    private void jTmapKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTmapKeyReleased


    }//GEN-LAST:event_jTmapKeyReleased

    private void jTfolderKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTfolderKeyReleased
        selectType();

        if (evt.getKeyCode() == KeyEvent.VK_RIGHT) {
            jTmap.grabFocus();
        }
    }//GEN-LAST:event_jTfolderKeyReleased

    private void jBselectKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jBselectKeyReleased
        if (evt.getKeyCode() == KeyEvent.VK_LEFT) {
            jTmap.grabFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            jBselect.doClick();
        }
    }//GEN-LAST:event_jBselectKeyReleased

    private void jTmapKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTmapKeyPressed

        select();

        switch (evt.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                jBselect.grabFocus();
                break;
            case KeyEvent.VK_LEFT:
                jTfolder.grabFocus();
                break;
            case KeyEvent.VK_ENTER:
                int row = jTmap.getSelectedRow();
                if (row >= 1) {
                    jTmap.setRowSelectionInterval(row - 1, row - 1);
                    jBselect.doClick();
                }
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jTmapKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WNDSelect(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBselect;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLbAtrocity;
    private javax.swing.JLabel jLbDel;
    private javax.swing.JLabel jLbImageInfo;
    private javax.swing.JLabel jLbMapInfo;
    private javax.swing.JLabel jLbMinus;
    private javax.swing.JLabel jLbPlus;
    private javax.swing.JLabel jLbPreview;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTfolder;
    private javax.swing.JTable jTmap;
    private javax.swing.JLabel jTperc;
    // End of variables declaration//GEN-END:variables
}
