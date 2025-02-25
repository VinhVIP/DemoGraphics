package com.demo;

import com.demo.listeners.CanvasListener;
import com.demo.listeners.DialogListener;
import com.demo.models.Point2D;
import com.demo.models.Point3D;
import com.demo.shape.Geometry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Create by Warriors Team
 * On 05/05/2021
 */

public class Paint extends JFrame implements CanvasListener, DialogListener, ActionListener {

    private JButton btnRotate;
    private JButton btnMove;
    private JButton btnScale;
    private JButton btnReflect;
    private JButton btnRedo;
    private JButton btnClear;
    private JButton btnUndo;
    private JButton btnChooseColor;
    private JButton btnLine;
    private JButton btnRect;
    private JButton btnCircle;
    private JButton btnEllipse;
    private JList listShape;
    private JCheckBox cbShowAxis;
    private JCheckBox cbShowGrid;
    private JCheckBox cbShowPointCoord;
    private JComboBox cbChooseDrawMode;
    private JPanel mainPanel;
    private JLabel labelDrawMode;
    private JLabel labelCoordinate;
    private JPanel rootPanel;
    private JButton btnDeselected;
    private JButton btnTriangle;
    private JButton btnPolygon;
    private JButton btnCopy;
    private JButton btnFillColor;
    private JButton btnMotion;
    private JButton btnRectangular;
    private JButton btnCylinder;
    private JButton btnCone;
    private JRadioButton radio2D;
    private JRadioButton radio3D;
    private JCheckBox checkBoxColorFill;
    private JButton btnCustom3D;
    private JButton btnOpenFile;
    private JButton btnPen;
    private JButton btnSaveFile;
    private JButton btnFilm;
    private JButton btnPropertiesAnimation;
    private JButton btnInfo;

    private ButtonGroup radioGroup;


    private final DrawCanvas canvas;
    private DefaultListModel listModel = new DefaultListModel();

    public Paint() {
        setTitle("Pro Paint");
        setSize(1500, 900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new DrawCanvas(this);

        mainPanel.add(canvas);


        btnClear.addActionListener(e -> {
            int[] indexes = listShape.getSelectedIndices();
            if (indexes.length > 0) {
                canvas.clearShapes(indexes);
            } else {
                canvas.clearScreen();
            }
        });

        btnLine.addActionListener(e -> {
            canvas.setMode(Mode.LINE);
        });
        btnRect.addActionListener(e ->
        {
            canvas.setMode(Mode.RECTANGLE);
        });

        btnPen.addActionListener(e ->
        {
            canvas.setMode(Mode.PEN);
        });

        btnCircle.addActionListener(e ->
        {
            canvas.setMode(Mode.CIRCLE);
        });

        btnEllipse.addActionListener(e -> {
            canvas.setMode(Mode.ELLIPSE);
        });

        btnTriangle.addActionListener(e -> {
            canvas.setMode(Mode.TRIANGLE);
        });

//        btnEllipseDash.addActionListener(e -> {
//            canvas.setShapeMode(ShapeMode.ELLIPSE_DASH);
//            labelDrawMode.setText("MODE: ELLIPSE DASH");
//        });

//        btnPoint.addActionListener(e ->
//        {
//            canvas.setShapeMode(ShapeMode.POINT);
//            labelDrawMode.setText("MODE: POINT");
//        });

        // Chọn màu vẽ
        btnChooseColor.addActionListener(e ->
        {
            Color color = JColorChooser.showDialog(null, "Choose Color", btnChooseColor.getBackground());
            if (color != null) {
                btnChooseColor.setBackground(color);
                DrawCanvas.currentColor = color.getRGB();
            }
        });

        cbShowAxis.addActionListener(e ->
        {
            boolean isSelected = cbShowAxis.isSelected();
            canvas.setShowAxis(isSelected);
        });

        cbShowGrid.addActionListener(e ->
        {
            boolean isSelected = cbShowGrid.isSelected();
            canvas.setShowGrid(isSelected);
        });

        cbShowPointCoord.addActionListener(e -> {
            canvas.setShowPointCoord(cbShowPointCoord.isSelected());
        });

        cbChooseDrawMode.addActionListener(e ->

        {
            String s = (String) cbChooseDrawMode.getSelectedItem();
            if (s.equals("DEFAULT")) canvas.setDrawMode(DrawMode.DEFAULT);
            else if (s.equals("DOT")) canvas.setDrawMode(DrawMode.DOT);
            else if (s.equals("DASH")) canvas.setDrawMode(DrawMode.DASH);
            else if (s.equals("DASHDOT")) canvas.setDrawMode(DrawMode.DASH_DOT);
            else if (s.equals("DASHDOTDOT")) canvas.setDrawMode(DrawMode.DASH_DOT_DOT);
            else if (s.equals("ARROW")) canvas.setDrawMode(DrawMode.ARROW);
        });

        btnUndo.addActionListener(e -> canvas.undo());

        btnRedo.addActionListener(e -> canvas.redo());

        btnMove.addActionListener(e -> {
            int[] indexMove = listShape.getSelectedIndices();
            if (indexMove.length == 0) {
                JOptionPane.showMessageDialog(null, "Chưa có hình nào được chọn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Arrays.sort(indexMove);
            for (int i : indexMove) {
                System.out.print(i + " ");
            }
            System.out.println();
            canvas.move(indexMove);

            canvas.setMode(Mode.MOVE);
        });

        btnDeselected.addActionListener(e -> listShape.clearSelection());

        btnRotate.addActionListener(e -> {
            int[] indexMove = listShape.getSelectedIndices();
            if (indexMove.length == 0) {
                JOptionPane.showMessageDialog(null, "Chưa có hình nào được chọn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new RotationDialog(this);
            canvas.setMode(Mode.ROTATE);
        });

        btnReflect.addActionListener(e -> {
            int[] indexMove = listShape.getSelectedIndices();
            if (indexMove.length == 0) {
                JOptionPane.showMessageDialog(null, "Chưa có hình nào được chọn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            new ReflectionDialog(this);
            canvas.setMode(Mode.REFLECT);
        });

        btnScale.addActionListener(e -> {
            int[] indexMove = listShape.getSelectedIndices();
            if (indexMove.length == 0) {
                JOptionPane.showMessageDialog(null, "Chưa có hình nào được chọn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }
            ScaleDialog dialog = new ScaleDialog(this);
            canvas.setMode(Mode.SCALE);

        });

        btnCopy.addActionListener(e -> {
            int[] selectedIndex = listShape.getSelectedIndices();
            if (selectedIndex.length == 0) {
                JOptionPane.showMessageDialog(null, "Chưa có hình nào được chọn!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                canvas.copyShapes(selectedIndex);
            }
        });

        btnFillColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose Fill Color", btnChooseColor.getBackground());
            if (color != null) {
                btnFillColor.setBackground(color);
                DrawCanvas.currentFillColor = color.getRGB();
            }
        });

        checkBoxColorFill.addActionListener(e -> {
            canvas.setFillColor(checkBoxColorFill.isSelected());
        });


        btnMotion.addActionListener(e -> {
            canvas.setShowMotions(!canvas.isShowMotions());
        });

        listShape.setModel(listModel);


        radio2D.addActionListener(this);
        radio3D.addActionListener(this);

        radioGroup = new ButtonGroup();
        radioGroup.add(radio2D);
        radioGroup.add(radio3D);

        stateButtons();

        btnRectangular.addActionListener(e -> {
            canvas.setMode(Mode.RECTANGULAR);
        });

        btnCylinder.addActionListener(e -> canvas.setMode(Mode.CYLINDER));

        btnCone.addActionListener(e -> canvas.setMode(Mode.CONE));

        btnCustom3D.addActionListener(e -> {
            new CustomShape3D(this);
        });


        btnPolygon.addActionListener(e -> {
            new PolygonCustom(this);
        });

        btnOpenFile.addActionListener(e -> canvas.openFile());

        btnSaveFile.addActionListener(e -> canvas.saveFile());

        btnFilm.addActionListener(e -> canvas.setShowFilm(!canvas.isShowFilm()));

        onRedoState(false);
        onUndoState(false);


        btnPropertiesAnimation.addActionListener(e -> {
            new AnimationDialog();
        });

        btnInfo.addActionListener(e -> new InfoDialog());

        // Important
        add(rootPanel);
        setLocation(250, 0);

    }

    @Override
    public void mouseCoordinate(int x, int y) {
        labelCoordinate.setText(String.format("X:%d , Y:%d", x, y));
    }

    @Override
    public int notifyShapeInserted(String shapeTitle) {
        listModel.addElement(shapeTitle);
        return listModel.size() - 1;
    }

    @Override
    public int notifyShapeDeleted(int position) {
        try {
            for (int i = position; i < listModel.size() - 1; i++) {
                notifyShapeChanged(i, (String) listModel.get(i + 1));
            }
            listModel.remove(listModel.size() - 1);
        } catch (Exception e) {
            System.out.println("oops");
        }
        return listModel.size();
    }

    @Override
    public void notifyDataSetChanged(List listShape) {
        System.out.println("list shape model: " + listShape.size());
        listModel.clear();
        for (int i = 0; i < listShape.size(); i++) {
            Geometry g = (Geometry) listShape.get(i);
            listModel.addElement(g.toString());
        }
    }

    @Override
    public void notifyShapeChanged(int position, String newTitle) {
        if (position >= listModel.size()) return;
        try {
            listModel.set(position, newTitle);
        } catch (Exception e) {
            System.out.println("oops 2");
        }
    }

    @Override
    public void notifyDeselectedAllItems() {
        listShape.clearSelection();
    }

    @Override
    public void notifyShapeModeChanged(Mode MODE) {
        labelDrawMode.setText("MODE: " + MODE);
    }

    @Override
    public void clear() {
        listModel.clear();
    }

    @Override
    public void clearFrom(int startPosition) {
        if (listModel.size() <= startPosition) return;
        listModel.removeRange(startPosition, listModel.size() - 1);
    }

    @Override
    public void onUndoState(boolean isEnable) {
        btnUndo.setEnabled(isEnable);
    }

    @Override
    public void onRedoState(boolean isEnable) {
        btnRedo.setEnabled(isEnable);
    }

    @Override
    public void onPointRotate(int x, int y) {
        int[] indexMove = listShape.getSelectedIndices();
        Arrays.sort(indexMove);
        canvas.rotate(indexMove, new Point2D(x, y));
    }

    @Override
    public void onPointReflect(int x, int y) {
        int[] indexMove = listShape.getSelectedIndices();
        Arrays.sort(indexMove);
        canvas.reflect(indexMove, new Point2D(x, y), null);
    }

    @Override
    public void onTwoPointReflect(int x1, int y1, int x2, int y2) {
        int[] indexMove = listShape.getSelectedIndices();
        Arrays.sort(indexMove);
        canvas.reflect(indexMove, new Point2D(x1, y1), new Point2D(x2, y2));
    }

    @Override
    public void onScale(Point2D root, double scaleX, double scaleY) {
        int[] indexMove = listShape.getSelectedIndices();
        Arrays.sort(indexMove);
        canvas.scale(indexMove, root, scaleX, scaleY);
    }

    @Override
    public void onDrawRectangular(Point3D root, int dx, int dy, int dz) {
        canvas.drawRectangular(root, dx, dy, dz);
    }

    @Override
    public void onDrawCylinder(Point3D root, int dx, int dy, int dz) {
        canvas.drawCylinder(root, dx, dy, dx);
    }

    @Override
    public void onDrawCone(Point3D root, int dx, int dy, int dz) {
        canvas.drawCone(root, dx, dy, dx);
    }

    @Override
    public void onDrawPolygon(Point2D[] points) {
        canvas.drawPolygon(points);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (canvas.motionThread != null || canvas.filmThread != null) {
            radio2D.setSelected(canvas.isIs2DCoordinates());
            return;
        }

        if (canvas.isIs2DCoordinates() != radio2D.isSelected()) {
            stateButtons();
            canvas.setIs2DCoordinates(radio2D.isSelected());
            canvas.setMode(Mode.NONE);
        }
    }

    private void stateButtons() {
        btnLine.setEnabled(radio2D.isSelected());
        btnRect.setEnabled(radio2D.isSelected());
        btnTriangle.setEnabled(radio2D.isSelected());
        btnCircle.setEnabled(radio2D.isSelected());
        btnEllipse.setEnabled(radio2D.isSelected());
        btnPolygon.setEnabled(radio2D.isSelected());

        btnRotate.setEnabled(radio2D.isSelected());
        btnReflect.setEnabled(radio2D.isSelected());
        btnMove.setEnabled(radio2D.isSelected());
        btnScale.setEnabled(radio2D.isSelected());

        btnCopy.setEnabled(radio2D.isSelected());
//        btnUndo.setEnabled(radio2D.isSelected());
//        btnRedo.setEnabled(radio2D.isSelected());

        btnRectangular.setEnabled(radio3D.isSelected());
        btnCone.setEnabled(radio3D.isSelected());
        btnCylinder.setEnabled(radio3D.isSelected());
        btnCustom3D.setEnabled(radio3D.isSelected());

    }
}
