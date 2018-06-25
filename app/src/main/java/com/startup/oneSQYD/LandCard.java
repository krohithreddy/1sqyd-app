package com.startup.oneSQYD;

public class LandCard {
//    private int _id;
    private String Email,Aadhar,LandImage,SurveyImage;


    public LandCard(String Email, String Aadhar, String LandImage, String SurveyImage) {
//        this._id = _id;
        this.Email = Email;
        this.Aadhar = Aadhar;
        this.LandImage = LandImage;
        this.SurveyImage = SurveyImage;
    }

//    public int getId() {
//        return _id;
//    }

    public String getEmail() {
        return Email;
    }

    public String getAadhar() {
        return Aadhar;
    }

    public String getLandImage() {
        return LandImage;
    }

    public String  getSurveyImage() {
        return SurveyImage;
    }


}
