import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SimpleClient {
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In("D://ALGO_DS_AI_ML_WS//Coursera Algo//collinear//inarow.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            // StdOut.println("Point= (" + x + ", " + y + ")");
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.01);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        Point[] ps
                = new Point[5];
        ps[0] = new Point(7829, 23049);
        ps[1] = new Point(4730, 9300);


        FastCollinearPoints collinear = new FastCollinearPoints(points);
        StdOut.println("no of line seg= " + collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
