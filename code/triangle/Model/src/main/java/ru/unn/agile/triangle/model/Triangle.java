package ru.unn.agile.triangle.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.*;

public class Triangle {
    private final Point a;
    private final Point b;
    private final Point c;
    private final int sideNumber = 3;

    public Triangle(final Point a, final Point b, final Point c) {
        if (a.equals(b) || b.equals(c)) {
            throw new IllegalArgumentException("Points should not be in one place");
        }
        if (isOnSameLine(a, b, c)) {
            throw new IllegalArgumentException("Points should not be on one line");
        }
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public Point getC() {
        return c;
    }

    private double getSideLength(final Point point1, final Point point2) {
        return sqrt(pow(point1.getX() - point2.getX(), 2) + pow(point1.getY() - point2.getY(), 2));
    }

    public double getSideABLength() {
        return getSideLength(a, b);
    }

    public double getSideBCLength() {
        return getSideLength(b, c);
    }

    public double getSideACLength() {
        return getSideLength(a, c);
    }

    public double getAngleA() {
        return getAngle(a, b, c);
    }

    public double getAngleB() {
        return getAngle(b, a, c);
    }

    public double getAngleC() {
        return getAngle(c, a, b);
    }

    private double getAngle(final Point anglePoint,
                            final Point sidePoint1,
                            final Point sidePoint2) {
        double angle = acos(((sidePoint1.getX() - anglePoint.getX())
                * (sidePoint2.getX() - anglePoint.getX())
                + (sidePoint1.getY() - anglePoint.getY()) * (sidePoint2.getY() - anglePoint.getY()))
                / (sqrt(pow(sidePoint1.getX() - anglePoint.getX(), 2)
                + pow(sidePoint1.getY() - anglePoint.getY(), 2))
                * sqrt(pow(sidePoint2.getX() - anglePoint.getX(), 2)
                + pow(sidePoint2.getY() - anglePoint.getY(), 2))));
        return toDegrees(angle);
    }

    public List calculateAngles() {
        List<Double> angles = new ArrayList<Double>();
        double angle1 = getAngleA();
        angles.add(angle1);
        double angle2 = getAngleB();
        angles.add(angle2);
        double angle3 = getAngleC();
        angles.add(angle3);
        return angles;
    }

    public double getPerimeter() {
        double ab = getSideABLength();
        double ac = getSideACLength();
        double bc = getSideBCLength();
        return ab + ac + bc;
    }

    public double getArea() {
        return abs((a.getX() - c.getX()) * (b.getY() - c.getY())
                - (a.getY() - c.getY()) * (b.getX() - c.getX())) / 2;
    }

    public double getBisectorA() {
        return getBisector(a, b, c);
    }

    public double getBisectorB() {
        return getBisector(b, a, c);
    }

    public double getBisectorC() {
        return getBisector(c, a, b);
    }

    private double getBisector(final Point anglePoint,
                               final Point sidePoint1,
                               final Point sidePoint2) {
        double ab = getSideLength(anglePoint, sidePoint1);
        double ac = getSideLength(anglePoint, sidePoint2);
        double bc = getSideLength(sidePoint1, sidePoint2);
        double bisector = sqrt(ab * ac * (ab + ac + bc) * (ab + ac - bc)) / (ab + ac);
        return bisector;
    }

    public double getMedianA() {
        return getMedian(a, b, c);
    }

    public double getMedianB() {
        return getMedian(b, a, c);
    }

    public double getMedianC() {
        return getMedian(c, a, b);
    }

    private double getMedian(final Point anglePoint,
                             final Point sidePoint1,
                             final Point sidePoint2) {
        double medianX = (sidePoint1.getX() + sidePoint2.getX()) / 2;
        double medianY = (sidePoint1.getY() + sidePoint2.getY()) / 2;
        return sqrt(pow(medianX - anglePoint.getX(), 2) + pow(medianY - anglePoint.getY(), 2));
    }

    public double getHeightA() {
        return getHeight(a, b, c);
    }

    public double getHeightB() {
        return getHeight(b, a, c);
    }

    public double getHeightC() {
        return getHeight(c, a, b);
    }

    private double getHeight(final Point anglePoint,
                             final Point sidePoint1,
                             final Point sidePoint2) {
        return abs((sidePoint1.getY() - sidePoint2.getY()) * anglePoint.getX()
                + (sidePoint2.getX() - sidePoint1.getX()) * anglePoint.getY()
                + (sidePoint1.getX() * sidePoint2.getY() - sidePoint2.getX() * sidePoint1.getY()))
                / getSideLength(sidePoint1, sidePoint2);
    }

    public Point getMedianIntersection() {
        double centroidX = (a.getX() + b.getX() + c.getX()) / sideNumber;
        double centroidY = (a.getY() + b.getY() + c.getY()) / sideNumber;
        return new Point(Formatter.format(centroidX), Formatter.format(centroidY));
    }

    private boolean isOnSameLine(final Point point1, final Point point2, final Point point3) {
        return point3.getX() - point1.getX() * (point2.getY() - point1.getY())
                == (point3.getY() - point1.getY()) * (point2.getX() - point1.getX());
    }

    public enum Operation {

        GETANGLEA("getAngleA") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getAngleA());
            }
        },
        GETANGLEB("getAngleB") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getAngleB());
            }
        },
        GETANGLEC("getAngleC") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getAngleC());
            }
        }, GETSIDEABLENGTH("getSideABLength") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getSideABLength());
            }
        },
        GETSIDEACLENGTH("getSideACLength") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getSideACLength());
            }
        },
        GETSIDEBCLENGTH("getSideBCLength") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getSideBCLength());
            }
        }, GETAREA("getArea") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getArea());
            }
        },
        GETPERIMETER("getPerimeter") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getPerimeter());
            }
        }, GETHEIGHTA("GetHeightA") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getHeightA());
            }
        },
        GETHEIGHTB("GetHeightB") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getHeightB());
            }
        },
        GETHEIGHTC("GetHeightC") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getHeightC());
            }
        },
        GETMEDIANA("getMedianA") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getMedianA());
            }
        },
        GETMEDIANB("getMedianB") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getMedianB());
            }
        },
        GETMEDIANC("getMedianC") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getMedianC());
            }
        },
        GETBISECTORA("getBisectorA") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getBisectorA());
            }
        },
        GETBISECTORB("getBisectorB") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getBisectorB());
            }
        },
        GETBISECTORC("getBisectorC") {
            public String apply(final Triangle triangle) {
                return Formatter.format(triangle.getBisectorC());
            }
        },
        GETMEDIANINTERSECTION("getMedianIntersection") {
            public String apply(final Triangle triangle) {
                return triangle.getMedianIntersection().toString();
            }
        };

        private final String name;

        Operation(final String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public abstract String apply(Triangle triangle);
    }

    @Override
    public String toString() {
        return "A" + a
                + ", B" + b
                + ", C" + c;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Triangle)) {
            return false;
        }
        Triangle triangle = (Triangle) o;
        return sideNumber == triangle.sideNumber
                && Objects.equals(getA(), triangle.getA())
                && Objects.equals(getB(), triangle.getB())
                && Objects.equals(getC(), triangle.getC());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getA(), getB(), getC(), sideNumber);
    }
}
