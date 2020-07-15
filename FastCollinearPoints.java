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
    
    // Method to find all collinear points
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
            int c = 0;          // To Backtrack the already found subsegments and to avoid re-including them
            for (int j = 0; j < points.length - 1; j++) {
                if (points[i].slopeTo(pointsBySlope[j]) == points[i].slopeTo(pointsBySlope[j + 1])) {
                    if (points[i].compareTo(pointsBySlope[j]) < 0) {
                        count++;
                        if (mx.compareTo(pointsBySlope[j]) < 0) mx = pointsBySlope[j];      // taking the highest point of the line segment
                        if (mx.compareTo(pointsBySlope[j + 1]) < 0) mx = pointsBySlope[j + 1]; // taking the highest point of the line segment
                        if (count >= 3 && j == pointsBySlope.length - 2 && c < 1) {     // Add the segment only if it's segment or subsegment is not already included
                            lSegments.add(new LineSegment(points[i], mx));
                            mx = points[i];
                            count = 1;
                            c = 0; // Resetting the backtrack;
                        }

                    }
                    else {
                        c++;   //Increasing the backtrack in case of the point is already included in any segment and may not be considered in a subsegment;     
                    }

                }
                else if (count >= 3 && c < 1) {   // Add the segment only if it's segment or subsegment is not already included
                    lSegments.add(new LineSegment(points[i], mx));
                    mx = points[i];
                    count = 1;
                    c = 0;  // Resetting the backtrack;
                }
                else {


                    mx = points[i];
                    count = 1;
                    c = 0; // Resetting the backtrack;
                }
            }
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
    // Asserting if the argument array contains duplicate points
    private void assertDuplicate(Point[] arg) {
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
