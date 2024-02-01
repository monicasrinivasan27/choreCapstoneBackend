package org.launchcode.taskcrusher.models;

public class ApiResponseBody {
    private String date;
    private String localName;
    private String name;
    private String countryCode ;
    private Boolean fixed;
    private Boolean global;
    private String[] counties;
    private String[] types;

    public String getDate() {
       return date;
   }

   public void setDate(String date)
   {this.date = date;
    }

    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Boolean getFixed() {
        return fixed;
    }

    public void setFixed(Boolean fixed) {
        this.fixed = fixed;
    }

    public Boolean getGlobal() {
        return global;
    }

    public void setGlobal(Boolean global) {
        this.global = global;
    }

    public String[] getCounties() {
        return counties;
    }

    public void setCounties(String[] counties) {
        this.counties = counties;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public ApiResponseBody(String date, String localName, String name, String countryCode, Boolean fixed, Boolean global, String[] counties, String[] types) {
        this.date = date;
        this.localName = localName;
        this.name = name;
        this.countryCode = countryCode;
        this.fixed = fixed;
        this.global = global;
        this.counties = counties;
        this.types = types;
    }
}
