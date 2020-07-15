import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    private List<LineSegment> lSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        assertArgument(points);
        lSegments = new ArrayList<>();
        findLinesegments(points.clone());
    }

    private void findLinesegments(Point[] points) {

        // Assertion of the argument
        assertArgument(points);
        assertPoint(points);
        Arrays.sort(points);
        assertDuplicate(points);

        for (int i = 0; i < points.length - 3; i++) {
            Point[] pointsBySlope = points.clone();
            Arrays.sort(pointsBySlope, points[i].slopeOrder());
            int count = 1;
            Point mx = points[i];
            int c = 0;
            for (int j = 0; j < points.length - 1; j++) {
                if (points[i].slopeTo(pointsBySlope[j]) == points[i]
                        .slopeTo(pointsBySlope[j + 1])) {
                    if (points[i].compareTo(pointsBySlope[j]) < 0) {

                        count++;
                        if (mx.compareTo(pointsBySlope[j]) < 0) mx = pointsBySlope[j];
                        if (mx.compareTo(pointsBySlope[j + 1]) < 0) mx = pointsBySlope[j + 1];
                        if (count >= 3 && j == pointsBySlope.length - 2 && c < 1) {
                            // StdOut.println("inside j.. point[i]=" + points[i] +
                            //                        ", pointsBySlope[j]=" + pointsBySlope[j]
                            //                        + ", pointsBySlope[j+1]=" + pointsBySlope[j + 1]
                            //                        +
                            //                        "slope=" + points[i].slopeTo(pointsBySlope[j])
                            //                        + ", slope[j]=" + points[i]
                            //         .slopeTo(pointsBySlope[j + 1])
                            //                        + " j=" + j + ", len==" + pointsBySlope.length
                            // );
                            lSegments.add(new LineSegment(points[i], mx));
                            mx = points[i];
                            count = 1;
                            c = 0;
                        }

                    }
                    else {
                        c++;
                    }

                }
                else if (count >= 3 && c < 1) {
                    // StdOut.println("3.) inside j.. point[i]=" + points[i] +
                    //                        ", pointsBySlope[j]=" + pointsBySlope[j]
                    //                        + ", pointsBySlope[j+1]=" + pointsBySlope[j + 1]
                    //                        +
                    //                        "slope=" + points[i].slopeTo(pointsBySlope[j])
                    //                        + ", slope[j]=" + points[i]
                    //         .slopeTo(pointsBySlope[j + 1]));
                    lSegments.add(new LineSegment(points[i], mx));
                    mx = points[i];
                    count = 1;
                    c = 0;
                }
                else {


                    mx = points[i];
                    count = 1;
                    c = 0;
                }
            }

            // if (count >= 3) {
            //     // StdOut.println("1");
            //     // StdOut.println("Points:= " + str);
            //     lSegments.add(new LineSegment(mnP, mxP));
            // }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {

        return lSegments.toArray(new LineSegment[lSegments.size()]);
    }

    // Assert if the constructor argument provided is null
    private void assertArgument(Point[] arg) {
        if (arg == null) throw new IllegalArgumentException("Constructor argument cannot be null");
    }

    private void assertDuplicate(Point[] arg) {
        if (arg == null) throw new IllegalArgumentException("Constructor argument cannot be null");
        for (int i = 0; i < arg.length - 1; i++) {
            if (arg[i].compareTo(arg[i + 1]) == 0) {
                throw new IllegalArgumentException("Points cannot be duplicate");
            }
        }
    }

    // Assert if the point provided is null
    private void assertPoint(Point[] arg) {
        for (Point p : arg) {
            if (p == null) throw new IllegalArgumentException("Point cannot be null");
        }
    }
}
