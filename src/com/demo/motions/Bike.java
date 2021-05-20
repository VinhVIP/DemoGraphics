package com.demo.motions;

import com.demo.DrawCanvas;
import com.demo.DrawMode;
import com.demo.models.Point2D;
import com.demo.shape.Circle;
import com.demo.shape.Line;
import com.demo.shape.Rectangle;

import java.util.List;

/**
 * Create by VinhIT
 * On 15/05/2021
 */

public class Bike {
    private Circle wheel;
    private Line rim1, rim2;
    private Rectangle bg;

    private DrawCanvas canvas;

    public Bike(DrawCanvas canvas) {
        this.canvas = canvas;

        wheel = new Circle(canvas, null, null, DrawMode.DEFAULT, 0xffff00, 0xff00ff, true, true);
        rim1 = new Line(canvas, DrawMode.DEFAULT, 0x000);
        rim2 = new Line(canvas, DrawMode.DEFAULT, DrawCanvas.currentColor);
        bg = new Rectangle(canvas, null, null, DrawMode.DEFAULT, 0xffff00, 0xffff00, true, true);

        wheel.setPoints(new Point2D[]{new Point2D(0, 0), new Point2D(10, 0)});
        rim1.setPoints(new Point2D[]{new Point2D(0, 10), new Point2D(0, -10)});
        rim2.setPoints(new Point2D[]{new Point2D(-20, 0), new Point2D(20, 0)});
        bg.setPoints(new Point2D[]{new Point2D(-40, 0), new Point2D(90, 0), new Point2D(90, -40), new Point2D(-40, -40)});
    }


    Line rim1Temp;
    Circle wheelTemp;
    double scale = 1, dv = 0.1;

    public void run() {
        // Di chuyển sang phải 1 đơn vị
//        wheel.move(1, 0);
//        rim1.move(1, 0);
//        rim2.move(1, 0);

        // Quay quanh tâm đường thẳng
//        rim1.rotate(rim1.getCenterPoint(), -Math.PI / 12);
//        rim2.rotate(rim2.getCenterPoint(), -Math.PI / 12);

        // Tạo 1 bản copy rồi mới scale trên bản copy đó
        rim1Temp = (Line) rim1.copy();
        scale += dv;
        if (scale > 2.5 || scale < 0.5) dv = -dv;
        rim1Temp.scale(scale, scale);
        System.out.println(rim1Temp.getCenterPoint());

        wheelTemp = (Circle) wheel.copy();
        wheelTemp.scale(scale, scale);

        // Thiết lập những điểm vẽ mới
        wheelTemp.processDraw();
        rim1Temp.processDraw();
        rim2.processDraw();
        bg.processDraw();

//        wheel.fillColor();
        bg.fillColor();

        int[][] b = DrawCanvas.newDefaultBoard();
        addToBoard(b, wheelTemp.getListDraw(), rim1Temp.getListDraw(), rim2.getListDraw());
        canvas.applyBoard(b);

    }

    private void addToBoard(int[][] b, List<Point2D>... lists) {
        for (List<Point2D> list : lists) {
            for (Point2D p : list) {
                if (p.insideScreen()) {
                    b[p.getComputerX()][p.getComputerY()] = p.getColor();
                }
            }
        }
    }
}
