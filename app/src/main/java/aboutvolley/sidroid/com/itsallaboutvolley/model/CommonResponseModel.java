package aboutvolley.sidroid.com.itsallaboutvolley.model;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;

/**
 * Created by siddheshs2 on 01-12-2015.
 */
public class CommonResponseModel {

    @SerializedName("error")
    private String error;

    @SerializedName("message")
    private int message;

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public int getMessage() {
        return message;
    }


}
