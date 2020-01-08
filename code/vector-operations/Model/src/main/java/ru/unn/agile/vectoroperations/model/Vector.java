package ru.unn.agile.vectoroperations.model;


public class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(final double x0, final double y0, final double z0) {
        this.x = x0;
        this.y = y0;
        this.z = z0;
    }

    @Override
    public boolean equals(final Object obj) {
        final double eps = 0.01;
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(getClass() == obj.getClass())) {
            return false;
        }
        Vector tmp = (Vector) obj;
        return Math.abs(tmp.x - this.x) < eps
                && Math.abs(tmp.y - this.y) < eps
                && Math.abs(tmp.z - this.z) < eps;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public static double getLengthNorm(final Vector v1) {
        double len = 0;
        double sum = Math.pow(v1.x, 2) + Math.pow(v1.y, 2) + Math.pow(v1.z, 2);
        len = Math.sqrt(sum);
        return len;
    }

    public static Vector getNormalizedVector(final Vector v1) {
        double vectorNorm = getLengthNorm(v1);
        double newX = v1.getX() / vectorNorm;
        double newY = v1.getY() / vectorNorm;
        double newZ = v1.getZ() / vectorNorm;
        Vector newVector = new Vector(newX, newY, newZ);
        return newVector;
    }

    public static double getScalarMult(final Vector v1, final Vector v2) {
        return (v1.x * v2.x) + (v1.y * v2.y) + (v1.z * v2.z);
    }

    public static Vector getVectMult(final  Vector v1, final Vector v2) {
        double newX = v1.y * v2.z - v1.z * v2.y;
        double newY = v1.x * v2.z - v1.z * v2.x;
        double newZ = v1.x * v2.y - v1.y * v2.x;
        Vector res = new Vector(newX, newY, newZ);
        return res;
    }


    @Override
    public String toString() {
        String stringRepresentation = "(" + String.format("%.3f", this.x)
                + ", " + String.format("%.3f", this.y)
                + ", " + String.format("%.3f", this.z) + ")";
        return stringRepresentation;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public enum Operation {
        CALCULATE_NORM("Calculate norm") {
            public String apply(final Vector x, final Vector y) {
                Double lengthNorm = Vector.getLengthNorm(x);
                return lengthNorm.toString();
            }
        },

        CALCULATE_NORMALIZED_VECTOR("Calculate normalized vector") {
            public String apply(final Vector x, final Vector y) {
                Vector normalizedVector = Vector.getNormalizedVector(x);
                return normalizedVector.toString();
            }
        },

        CALCULATE_SCALAR_MULT("Calculate scalar mult") {
            public String apply(final Vector x, final Vector y) {
                Double scalarMult = Vector.getScalarMult(x, y);
                return scalarMult.toString();
            }
        },

        CALCULATE_VECTOR_MULT("Calculate vector mult") {
            public String apply(final Vector x, final Vector y) {
                Vector vectMult = Vector.getVectMult(x, y);
                return vectMult.toString();
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

        public abstract String apply(Vector x, Vector y);

    }
}
