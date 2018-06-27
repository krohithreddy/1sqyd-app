package com.startup.oneSQYD;

import java.io.Serializable;

public class BuyCard implements Serializable {
    private String _id,
    Email,
    Owner_email,
    OrderId,
    TradeId,
    LandId,
    Available_units,
    Cost_unit_value,
    Owner_name,
    Phone_number,
    Land_unit_value,
    Total_size,
    Percent_sold,
    Total_units,
    City;

    public BuyCard(String _id, String email, String owner_email, String orderId, String tradeId, String landId, String available_units, String cost_unit_value, String owner_name, String phone_number, String land_unit_value, String total_size, String percent_sold, String total_units, String city) {
        this._id = _id;
        Email = email;
        Owner_email = owner_email;
        OrderId = orderId;
        TradeId = tradeId;
        LandId = landId;
        Available_units = available_units;
        Cost_unit_value = cost_unit_value;
        Owner_name = owner_name;
        Phone_number = phone_number;
        Land_unit_value = land_unit_value;
        Total_size = total_size;
        Percent_sold = percent_sold;
        Total_units = total_units;
        City = city;
    }

    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return Email;
    }

    public String getOwner_email() {
        return Owner_email;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getTradeId() {
        return TradeId;
    }

    public String getLandId() {
        return LandId;
    }

    public String getAvailable_units() {
        return Available_units;
    }

    public String getCost_unit_value() {
        return Cost_unit_value;
    }

    public String getOwner_name() {
        return Owner_name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public String getLand_unit_value() {
        return Land_unit_value;
    }

    public String getTotal_size() {
        return Total_size;
    }

    public String getPercent_sold() {
        return Percent_sold;
    }

    public String getTotal_units() {
        return Total_units;
    }

    public String getCity() {
        return City;
    }
}


