package com.startup.oneSQYD;

import java.io.Serializable;

public class LandCard implements Serializable{

    private String _id,
        Email,
        Owner_name,
        Phone_number,
        Aadhar,
        State,
        District,
        Division,
        Mandal,
        Village,
        City,
        Survey_number,
        Total_size,
        width,
        length,
        Total_units,
        Percent_sold,
        Land_unit_value,
        Cost_unit_value,
        Available_units,
        LandImage,
        SurveyImage,
        AadharImage,
        PanImage,
        Land_status;


    public LandCard(String _id, String email, String owner_name, String phone_number, String aadhar, String state, String district, String division, String mandal, String village, String city, String survey_number, String total_size, String width, String length, String total_units, String percent_sold, String land_unit_value, String cost_unit_value, String available_units, String landImage, String surveyImage, String aadharImage, String panImage, String land_status) {
        this._id = _id;
        Email = email;
        Owner_name = owner_name;
        Phone_number = phone_number;
        Aadhar = aadhar;
        State = state;
        District = district;
        Division = division;
        Mandal = mandal;
        Village = village;
        City = city;
        Survey_number = survey_number;
        Total_size = total_size;
        this.width = width;
        this.length = length;
        Total_units = total_units;
        Percent_sold = percent_sold;
        Land_unit_value = land_unit_value;
        Cost_unit_value = cost_unit_value;
        Available_units = available_units;
        LandImage = landImage;
        SurveyImage = surveyImage;
        AadharImage = aadharImage;
        PanImage = panImage;
        Land_status = land_status;
    }


    public String get_id() {
        return _id;
    }

    public String getEmail() {
        return Email;
    }

    public String getOwner_name() {
        return Owner_name;
    }

    public String getPhone_number() {
        return Phone_number;
    }

    public String getAadhar() {
        return Aadhar;
    }

    public String getState() {
        return State;
    }

    public String getDistrict() {
        return District;
    }

    public String getDivision() {
        return Division;
    }

    public String getMandal() {
        return Mandal;
    }

    public String getVillage() {
        return Village;
    }

    public String getCity() {
        return City;
    }

    public String getSurvey_number() {
        return Survey_number;
    }

    public String getTotal_size() {
        return Total_size;
    }

    public String getWidth() {
        return width;
    }

    public String getLength() {
        return length;
    }

    public String getTotal_units() {
        return Total_units;
    }

    public String getPercent_sold() {
        return Percent_sold;
    }

    public String getLand_unit_value() {
        return Land_unit_value;
    }

    public String getCost_unit_value() {
        return Cost_unit_value;
    }

    public String getAvailable_units() {
        return Available_units;
    }

    public String getLandImage() {
        return LandImage;
    }

    public String getSurveyImage() {
        return SurveyImage;
    }

    public String getAadharImage() {
        return AadharImage;
    }

    public String getPanImage() {
        return PanImage;
    }

    public String getLand_status() {
        return Land_status;
    }
}
