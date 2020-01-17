
package com.juara.apk_kendaraan.addKendaraan;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddModel implements Serializable, Parcelable
{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    public final static Creator<AddModel> CREATOR = new Creator<AddModel>() {


        @SuppressWarnings({
            "unchecked"
        })
        public AddModel createFromParcel(Parcel in) {
            return new AddModel(in);
        }

        public AddModel[] newArray(int size) {
            return (new AddModel[size]);
        }

    }
    ;
    private final static long serialVersionUID = -976968664076519454L;

    protected AddModel(Parcel in) {
        this.status = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public AddModel() {
    }

    /**
     * 
     * @param message
     * @param status
     */
    public AddModel(Boolean status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(status);
        dest.writeValue(message);
    }

    public int describeContents() {
        return  0;
    }

}
