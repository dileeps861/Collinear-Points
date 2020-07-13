import java.util.ArrayList;
import java.util.List;

public class BruteCollinearPoints {
    private List<LineSegment> lSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        assertArgument(points);
        lSegments = new ArrayList<>();
        findCollinear(points.clone());
    }
    // Find exactly 4 collinear points
    private void findCollinear(Point[] points) {
        
        // For validation of no null Point or duplicate values if size is lezz than 4 
        if (points.length <= 3) {
            for (int i = 0; i < points.length; i++) {
                assertPoint(points[i]);
                for (int j = i + 1; j < points.length; j++) {
                    assertPoint(points[j]);
                    assertTwoPoints(points[i], points[j]);
                }
            }
        }
        else {
            
            // For finding collinear points if size is more than or equals to 4 
            for (int i = 0; i < points.length - 3; i++) {
                assertPoint(points[i]);
                Point mxP = points[i];
                Point mnP = points[i];

                for (int j = i + 1; j < points.length - 2; j++) {
                    assertPoint(points[j]);
                    assertTwoPoints(points[i], points[j]);

                    for (int k = j + 1; k < points.length - 1; k++) {
                        assertPoint(points[k]);
                        assertTwoPoints(points[j], points[k]);
                        assertTwoPoints(points[i], points[k]);

                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {
                            for (int l = k + 1; l < points.length; l++) {
                                assertPoint(points[l]);
                                // Check two points equality
                                assertTwoPoints(points[i], points[l]);
                                assertTwoPoints(points[j], points[l]);
                                assertTwoPoints(points[k], points[l]);
                                // StdOut.println("6. " + i + ", j=" + j + ", k=" + k + ", l=" + l);
                                if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[l])) {
                                    // Find the lowest point
                                    mnP = points[i].compareTo(points[j]) > 0 ? points[j] :
                                          points[i];
                                    mnP = mnP.compareTo(points[k]) > 0 ? points[k] : mnP;
                                    mnP = mnP.compareTo(points[l]) > 0 ? points[l] : mnP;

                                    // Find the heighest point
                                    mxP = points[i].compareTo(points[j]) < 0 ? points[j] :
                                          points[i];
                                    mxP = mxP.compareTo(points[k]) < 0 ? points[k] : mxP;
                                    mxP = mxP.compareTo(points[l]) < 0 ? points[l] : mxP;

                                    lSegments.add(new LineSegment(mnP, mxP));

                                }

                            } // End of fourth loop
                        }  
                        
                        else {  // if no exactly 4 collinear points, only check null points and duplicate points
                            
                            for (int l = k + 1; l < points.length; l++) {
                                assertPoint(points[l]);
                                
                                // Check duplicate points
                                assertTwoPoints(points[i], points[l]);
                                assertTwoPoints(points[j], points[l]);
                                assertTwoPoints(points[k], points[l]);

                            }
                        }
                    } // End of third loop

                } // End of second loop
            }
        }

    }

    // Assert if the constructor argument provided is null
    private void assertArgument(Point[] arg) {
        if (arg == null)
            throw new IllegalArgumentException("Constructor argument cannot be null");
    }

    // Assert if the point provided is null
    private void assertPoint(Point arg) {
        if (arg == null) throw new IllegalArgumentException("Point cannot be null");
    }

    // Assert if the point provided is null
    private void assertTwoPoints(Point arg1, Point arg2) {
        if (arg1.compareTo(arg2) == 0)
            throw new IllegalArgumentException("Two points cannot be same");
    }

    // the number of line segments
    public int numberOfSegments() {
        return lSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return lSegments.toArray(new LineSegment[lSegments.size()]);
    }
}
