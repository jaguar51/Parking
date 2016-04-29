package me.academeg.API;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AutoOnPark {

    private Auto auto;
    private int operationId;

    public AutoOnPark() {
    }

    public Auto getAuto() {
        return auto;
    }

    public int getOperationId() {
        return operationId;
    }

    public static AutoOnPark parse(ResultSet rs) throws SQLException {
        AutoOnPark res = new AutoOnPark();
        res.auto = Auto.parse(rs);
        res.operationId = rs.getInt(6);
        return res;
    }

    @Override
    public String toString() {
        return "AutoOnPark{" +
                "auto=" + auto +
                ", operationId=" + operationId +
                '}';
    }
}
