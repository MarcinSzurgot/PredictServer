package main.java.healthkeeper.predictserver.dbo;

public class GpsPosition {
    private String  Value;  
    private double  XCoordinate;
    private double  YCoordinate;
    private int     SRID;
    private boolean IsNull;
    
    public GpsPosition(){
        
    }
    
    public GpsPosition(String value, double xCoordinate, double yCoordinate, int sRID, boolean isNull) {
        super();
        Value = value;
        XCoordinate = xCoordinate;
        YCoordinate = yCoordinate;
        SRID = sRID;
        IsNull = isNull;
    }
    public String getValue() {
        return Value;
    }
    public void setValue(String value) {
        Value = value;
    }
    public double getXCoordinate() {
        return XCoordinate;
    }
    public void setXCoordinate(double xCoordinate) {
        XCoordinate = xCoordinate;
    }
    public double getYCoordinate() {
        return YCoordinate;
    }
    public void setYCoordinate(double yCoordinate) {
        YCoordinate = yCoordinate;
    }
    public int getSRID() {
        return SRID;
    }
    public void setSRID(int sRID) {
        SRID = sRID;
    }
    public boolean isIsNull() {
        return IsNull;
    }
    public void setIsNull(boolean isNull) {
        IsNull = isNull;
    }

    @Override
    public String toString() {
        return "GpsPosition [Value=" + Value + ", XCoordinate=" + XCoordinate + ", YCoordinate=" + YCoordinate
                + ", SRID=" + SRID + ", IsNull=" + IsNull + "]";
    }
}
