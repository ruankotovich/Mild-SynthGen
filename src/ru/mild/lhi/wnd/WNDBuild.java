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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import ru.mild.lhi.bean.Spectrum;
import ru.mild.lhi.bean.VertexType;
import ru.mild.lhi.cmp.JAPanel;

/**
 *
 * @author dmitry
 */
public class WNDBuild extends JFrame implements MouseListener {

    /**
     * Creates new form WNDSelect
     */
    private final WNDMainWIndow caller;
    private JAPanel[][] panels;

    private JLabel lastSelected = null;
    private boolean working = false;
    private JPanel start = null, end = null;
    private int cb = 0, tc = 0;
    private boolean editMode = false;

    private final Border defaultBorder = BorderFactory.createLineBorder(Color.BLACK);
    private final Border selectedBorder = BorderFactory.createLineBorder(Color.RED);
    private final int WHITE_RGB = 0xFFFFFF, BLUE_RGB = 0x0000FF, GREEN_RGB = 0x00FF00, ORANGE_RGB = 0xFFC800, PURPLE_RGB = 0x660066, RED_RGB = 0xCC0000;

    private final MouseListener selectModel = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

            lastSelected.setBorder(defaultBorder);
            lastSelected = (JLabel) e.getSource();
            lastSelected.setBorder(selectedBorder);

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    public WNDBuild(WNDMainWIndow calling, int cellBreadth, int totalCells) {

        initComponents();

        panels = new JAPanel[totalCells][totalCells];

        jLbEnd.addMouseListener(selectModel);
        jLbStart.addMouseListener(selectModel);
        jLbFloor.addMouseListener(selectModel);
        jLbWall1.addMouseListener(selectModel);
        jLbWall2.addMouseListener(selectModel);
        jLbWall3.addMouseListener(selectModel);

        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("/ru/mild/lhi/gfx/labyhinth.png")));
        } catch (IOException ex) {
            Logger.getLogger(WNDMainWIndow.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.caller = calling;
        lastSelected = jLbWall1;

        cb = cellBreadth + 1;
        tc = totalCells;

        setTitle(WNDMainWIndow.MAIN_TITLE + " - Map Builder | New map");

        this.setLocationRelativeTo(null);

        JAPanel iterator;

        jPitens.setPreferredSize(new Dimension((cb * totalCells) - totalCells, (cb * totalCells) - totalCells));

        jPitens.setBorder(selectedBorder);

        for (int y = 0; y < totalCells; y++) {
            for (int x = 0; x < totalCells; x++) {
                panels[x][y] = iterator = new JAPanel(x, y);
                iterator.setSize(cb, cb);
                iterator.setBackground(Color.WHITE);
                iterator.setBorder(defaultBorder);
                iterator.setLocation((x * cb) - x, (y * cb) - y);
                jPitens.add(iterator);
                iterator.setVisible(true);
                iterator.addMouseListener(this);
            }
        }
    }

    public WNDBuild(WNDMainWIndow calling, Spectrum pathBuilder, File fileName) {

        initComponents();

        this.caller = calling;

        panels = new JAPanel[pathBuilder.getMatrixX()][pathBuilder.getMatrixX()];

        try {
            setIconImage(ImageIO.read(getClass().getResourceAsStream("/ru/mild/lhi/gfx/labyhinth.png")));
        } catch (IOException ex) {
            Logger.getLogger(WNDMainWIndow.class.getName()).log(Level.SEVERE, null, ex);
        }

        jLbEnd.addMouseListener(selectModel);
        jLbStart.addMouseListener(selectModel);
        jLbFloor.addMouseListener(selectModel);
        jLbWall1.addMouseListener(selectModel);
        jLbWall2.addMouseListener(selectModel);
        jLbWall3.addMouseListener(selectModel);

        lastSelected = jLbWall1;

        tc = pathBuilder.getMatrixX();
        cb = pathBuilder.getCellWidth() + 1;

        setTitle(WNDMainWIndow.MAIN_TITLE + " - Map Builder | Editing " + fileName.getName());

        this.setLocationRelativeTo(null);

        JAPanel iterator;

        jPitens.setPreferredSize(new Dimension((cb * tc) - tc, (cb * tc) - tc));

        jPitens.setBorder(selectedBorder);

        int[][] vertexes = pathBuilder.getAnonymousSpectrum();
        int verIterator;

        for (int y = 0; y < tc; y++) {
            for (int x = 0; x < tc; x++) {
                verIterator = vertexes[x][y];

                panels[x][y] = iterator = new JAPanel(x, y);
                iterator.setSize(cb, cb);

                if (verIterator == jLbStart.getBackground().getRGB()) {
                    iterator.setBackground(jLbStart.getBackground());
                    start = iterator;
                } else if (verIterator == jLbEnd.getBackground().getRGB()) {
                    iterator.setBackground(jLbEnd.getBackground());
                    end = iterator;
                } else if (verIterator == jLbFloor.getBackground().getRGB()) {
                    iterator.setBackground(jLbFloor.getBackground());
                } else if (verIterator == jLbWall3.getBackground().getRGB()) {
                    iterator.setBackground(jLbWall3.getBackground());
                } else if (verIterator == jLbWall2.getBackground().getRGB()) {
                    iterator.setBackground(jLbWall2.getBackground());
                } else {
                    iterator.setBackground(jLbWall1.getBackground());
                }

                jTextField1.setText("edit_" + fileName.getName().replace("edit_", "").replace(".png", ""));

                iterator.setBorder(defaultBorder);
                iterator.setLocation((x * cb) - x, (y * cb) - y);
                jPitens.add(iterator);
                iterator.setVisible(true);
                iterator.addMouseListener(this);
            }
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
        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jBexport = new javax.swing.JButton();
        jBexportOpen = new javax.swing.JButton();
        jBreset = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTexp = new javax.swing.JLabel();
        jPsteps = new javax.swing.JPanel();
        jPend = new javax.swing.JPanel();
        jLbEnd = new javax.swing.JLabel();
        jPfloor = new javax.swing.JPanel();
        jLbFloor = new javax.swing.JLabel();
        jPstart = new javax.swing.JPanel();
        jLbStart = new javax.swing.JLabel();
        jPwall1 = new javax.swing.JPanel();
        jLbWall1 = new javax.swing.JLabel();
        jPwall2 = new javax.swing.JPanel();
        jLbWall2 = new javax.swing.JLabel();
        jPwall3 = new javax.swing.JPanel();
        jLbWall3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jSPitens = new javax.swing.JScrollPane();
        jPitens = new javax.swing.JPanel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
                formWindowLostFocus(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(102, 0, 0));
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel1MousePressed(evt);
            }
        });
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jBexport.setBackground(new java.awt.Color(51, 51, 51));
        jBexport.setForeground(new java.awt.Color(255, 255, 255));
        jBexport.setText("Save");
        jBexport.setFocusPainted(false);
        jBexport.setFocusable(false);
        jBexport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBexportActionPerformed(evt);
            }
        });

        jBexportOpen.setBackground(new java.awt.Color(51, 51, 51));
        jBexportOpen.setForeground(new java.awt.Color(255, 255, 255));
        jBexportOpen.setText("Save & Open");
        jBexportOpen.setFocusPainted(false);
        jBexportOpen.setFocusable(false);
        jBexportOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBexportOpenActionPerformed(evt);
            }
        });

        jBreset.setBackground(new java.awt.Color(51, 51, 51));
        jBreset.setForeground(new java.awt.Color(255, 255, 255));
        jBreset.setText("Reset");
        jBreset.setFocusPainted(false);
        jBreset.setFocusable(false);
        jBreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBresetActionPerformed(evt);
            }
        });

        jTextField1.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("adabo");
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTexp.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jTexp.setText(" ");

        jPsteps.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPend.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPend.setPreferredSize(new java.awt.Dimension(40, 40));

        jLbEnd.setBackground(java.awt.Color.green);
        jLbEnd.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLbEnd.setForeground(new java.awt.Color(255, 255, 255));
        jLbEnd.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbEnd.setText("End");
        jLbEnd.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLbEnd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbEnd.setOpaque(true);

        javax.swing.GroupLayout jPendLayout = new javax.swing.GroupLayout(jPend);
        jPend.setLayout(jPendLayout);
        jPendLayout.setHorizontalGroup(
            jPendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbEnd, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        jPendLayout.setVerticalGroup(
            jPendLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbEnd, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );

        jPfloor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPfloor.setPreferredSize(new java.awt.Dimension(40, 40));

        jLbFloor.setBackground(new java.awt.Color(255, 255, 255));
        jLbFloor.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLbFloor.setForeground(new java.awt.Color(0, 0, 0));
        jLbFloor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbFloor.setText("Floor");
        jLbFloor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLbFloor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbFloor.setOpaque(true);

        javax.swing.GroupLayout jPfloorLayout = new javax.swing.GroupLayout(jPfloor);
        jPfloor.setLayout(jPfloorLayout);
        jPfloorLayout.setHorizontalGroup(
            jPfloorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbFloor, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        jPfloorLayout.setVerticalGroup(
            jPfloorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbFloor, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPstart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPstart.setPreferredSize(new java.awt.Dimension(40, 40));

        jLbStart.setBackground(java.awt.Color.blue);
        jLbStart.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLbStart.setForeground(new java.awt.Color(255, 255, 255));
        jLbStart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbStart.setText("Start");
        jLbStart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLbStart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbStart.setOpaque(true);

        javax.swing.GroupLayout jPstartLayout = new javax.swing.GroupLayout(jPstart);
        jPstart.setLayout(jPstartLayout);
        jPstartLayout.setHorizontalGroup(
            jPstartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbStart, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        jPstartLayout.setVerticalGroup(
            jPstartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbStart, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPwall1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPwall1.setPreferredSize(new java.awt.Dimension(40, 40));

        jLbWall1.setBackground(java.awt.Color.orange);
        jLbWall1.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLbWall1.setForeground(new java.awt.Color(255, 255, 255));
        jLbWall1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbWall1.setText("Wall");
        jLbWall1.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.red));
        jLbWall1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbWall1.setOpaque(true);

        javax.swing.GroupLayout jPwall1Layout = new javax.swing.GroupLayout(jPwall1);
        jPwall1.setLayout(jPwall1Layout);
        jPwall1Layout.setHorizontalGroup(
            jPwall1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbWall1, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        jPwall1Layout.setVerticalGroup(
            jPwall1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbWall1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPwall2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPwall2.setPreferredSize(new java.awt.Dimension(40, 40));

        jLbWall2.setBackground(new java.awt.Color(102, 0, 102));
        jLbWall2.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLbWall2.setForeground(new java.awt.Color(255, 255, 255));
        jLbWall2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbWall2.setText("Wall");
        jLbWall2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLbWall2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbWall2.setOpaque(true);

        javax.swing.GroupLayout jPwall2Layout = new javax.swing.GroupLayout(jPwall2);
        jPwall2.setLayout(jPwall2Layout);
        jPwall2Layout.setHorizontalGroup(
            jPwall2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbWall2, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        jPwall2Layout.setVerticalGroup(
            jPwall2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbWall2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPwall3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPwall3.setPreferredSize(new java.awt.Dimension(40, 40));

        jLbWall3.setBackground(new java.awt.Color(204, 0, 0));
        jLbWall3.setFont(new java.awt.Font("Dialog", 1, 8)); // NOI18N
        jLbWall3.setForeground(new java.awt.Color(255, 255, 255));
        jLbWall3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLbWall3.setText("Wall");
        jLbWall3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jLbWall3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLbWall3.setOpaque(true);

        javax.swing.GroupLayout jPwall3Layout = new javax.swing.GroupLayout(jPwall3);
        jPwall3.setLayout(jPwall3Layout);
        jPwall3Layout.setHorizontalGroup(
            jPwall3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbWall3, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE)
        );
        jPwall3Layout.setVerticalGroup(
            jPwall3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLbWall3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ru/mild/lhi/gfx/change.png"))); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPstepsLayout = new javax.swing.GroupLayout(jPsteps);
        jPsteps.setLayout(jPstepsLayout);
        jPstepsLayout.setHorizontalGroup(
            jPstepsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPstepsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPstepsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPstepsLayout.createSequentialGroup()
                        .addComponent(jPstart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPfloor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPstepsLayout.createSequentialGroup()
                        .addComponent(jPwall1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPwall2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPwall3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPstepsLayout.setVerticalGroup(
            jPstepsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPstepsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPstepsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPstart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPfloor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPstepsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPwall1, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jPwall2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(jPwall3, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jTexp, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jBexport, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTextField1)
            .addComponent(jBexportOpen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jBreset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPsteps, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPsteps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(jTexp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBexport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBexportOpen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jBreset)
                .addContainerGap())
        );

        jSPitens.setBackground(new java.awt.Color(255, 255, 255));

        jPitens.setBackground(new java.awt.Color(0, 0, 0));
        jPitens.setCursor(new java.awt.Cursor(java.awt.Cursor.CROSSHAIR_CURSOR));

        javax.swing.GroupLayout jPitensLayout = new javax.swing.GroupLayout(jPitens);
        jPitens.setLayout(jPitensLayout);
        jPitensLayout.setHorizontalGroup(
            jPitensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 731, Short.MAX_VALUE)
        );
        jPitensLayout.setVerticalGroup(
            jPitensLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
        );

        jSPitens.setViewportView(jPitens);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSPitens, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPitens, javax.swing.GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)
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
        //this.dispose();
        System.gc();
    }//GEN-LAST:event_formWindowLostFocus

    private void jBresetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBresetActionPerformed
        for (Component c : jPitens.getComponents()) {

            if (c instanceof JPanel) {
                ((JPanel) c).setBackground(Color.WHITE);
            }
            start = end = null;
        }
    }//GEN-LAST:event_jBresetActionPerformed

    private void jBexportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBexportActionPerformed
        if (start != null && end != null) {
            BufferedImage bufferedImage = new BufferedImage(jPitens.getPreferredSize().width, jPitens.getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            jPitens.printAll(g);

            try {
                String name = jTextField1.getText() + ".png";
                File imageFile = new File("Maps/Custom/" + name);

                if (imageFile.exists()) {
                    int a = JOptionPane.showConfirmDialog(null, "This map already exists, overwrite?");
                    if (a == JOptionPane.NO_OPTION) {
                        return;
                    }
                }

                ImageIO.write(bufferedImage, "png", imageFile);
                jTexp.setText("Exported as " + name);
            } catch (IOException ex) {
                Logger.getLogger(WNDBuild.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Start or End not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBexportActionPerformed

    private void jBexportOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBexportOpenActionPerformed
        if (start != null && end != null) {
            BufferedImage bufferedImage = new BufferedImage(jPitens.getPreferredSize().width, jPitens.getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
            Graphics g = bufferedImage.getGraphics();
            jPitens.printAll(g);

            try {
                String name = jTextField1.getText() + ".png";
                File imageFile = new File("Maps/Custom/" + name);
                if (imageFile.exists()) {
                    int a = JOptionPane.showConfirmDialog(null, "This map already exists, overwrite?");
                    if (a == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                ImageIO.write(bufferedImage, "png", imageFile);
                caller.buildNrun(imageFile);
                this.dispose();
            } catch (IOException ex) {
                Logger.getLogger(WNDBuild.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Start or End not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jBexportOpenActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased

    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed

    }//GEN-LAST:event_jTextField1KeyPressed

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
        if (end != null && start != null) {
            JAPanel startGuardian = (JAPanel) start;

            end.setBackground(Color.BLUE);
            start.setBackground(Color.GREEN);

            start = end;
            end = startGuardian;
        } else {
            JOptionPane.showMessageDialog(null, "Start or End not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jPanel1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MousePressed

    }//GEN-LAST:event_jPanel1MousePressed

    private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed
        
    }//GEN-LAST:event_jPanel1KeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new WNDBuild(null, 50, 10).setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBexport;
    private javax.swing.JButton jBexportOpen;
    private javax.swing.JButton jBreset;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLbEnd;
    private javax.swing.JLabel jLbFloor;
    private javax.swing.JLabel jLbStart;
    private javax.swing.JLabel jLbWall1;
    private javax.swing.JLabel jLbWall2;
    private javax.swing.JLabel jLbWall3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPend;
    private javax.swing.JPanel jPfloor;
    private javax.swing.JPanel jPitens;
    private javax.swing.JPanel jPstart;
    private javax.swing.JPanel jPsteps;
    private javax.swing.JPanel jPwall1;
    private javax.swing.JPanel jPwall2;
    private javax.swing.JPanel jPwall3;
    private javax.swing.JScrollPane jSPitens;
    private javax.swing.JLabel jTexp;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!working) {

            working = true;
            editMode = true;

            JAPanel panel = (JAPanel) e.getSource();
            Color c = lastSelected.getBackground();
            if (!e.isControlDown() || c.equals(Color.GREEN) || c.equals(Color.BLUE)) {
                if (e.getButton() != MouseEvent.BUTTON1) {
                    paintThis(panel, Color.WHITE);
                } else {
                    paintThis(panel, c);
                }
            } else {
                paintNeighbours(panel, panel.getBackground(), c);
            }
            working = false;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        editMode = false;

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (!working) {
            if (editMode) {
                working = true;
                paintThis((JAPanel) e.getSource(), lastSelected.getBackground());
                working = false;
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void paintThis(JAPanel panel, Color color) {

        if (color.equals(Color.GREEN)) {
            if (end != null) {
                end.setBackground(Color.WHITE);
            }
            end = panel;
        } else if (color.equals(Color.BLUE)) {
            if (start != null) {
                start.setBackground(Color.WHITE);
            }
            start = panel;
        } else {

            if (panel.equals(end)) {
                end = null;
            }

            if (panel.equals(start)) {
                start = null;
            }

        }
        panel.setBackground(color);
    }

    private void paintNeighbours(JAPanel panel, Color startColor, Color color) {

        Set<JAPanel> founds = new HashSet<>();
        Queue<JAPanel> search = new LinkedList<>();

        JAPanel iterator, navigator = null;
        search.add(panel);

        while (!search.isEmpty()) {

            navigator = search.poll();
            navigator.setBackground(color);

            if (navigator.getXpos() > 0) {

                iterator = panels[navigator.getXpos() - 1][navigator.getYpos()];

                if (!founds.contains(iterator)) {
                    if (VertexType.isCloserThat(iterator.getBackground().getRGB(), startColor.getRGB(), 10000)) {
                        search.add(iterator);
                        founds.add(iterator);
                    }
                }
            }

            if (navigator.getXpos() < tc - 1) {
                iterator = panels[navigator.getXpos() + 1][navigator.getYpos()];
                if (!founds.contains(iterator)) {
                    if (VertexType.isCloserThat(iterator.getBackground().getRGB(), startColor.getRGB(), 10000)) {
                        search.add(iterator);
                        founds.add(iterator);
                    }
                }
            }

            if (navigator.getYpos() > 0) {
                iterator = panels[navigator.getXpos()][navigator.getYpos() - 1];
                if (!founds.contains(iterator)) {
                    if (VertexType.isCloserThat(iterator.getBackground().getRGB(), startColor.getRGB(), 10000)) {
                        search.add(iterator);
                        founds.add(iterator);
                    }
                }
            }

            if (navigator.getYpos() < tc - 1) {
                iterator = panels[navigator.getXpos()][navigator.getYpos() + 1];
                if (!founds.contains(iterator)) {
                    if (VertexType.isCloserThat(iterator.getBackground().getRGB(), startColor.getRGB(), 10000)) {
                        search.add(iterator);
                        founds.add(iterator);
                    }
                }
            }
        }
    }

}
