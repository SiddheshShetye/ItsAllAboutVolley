package aboutvolley.sidroid.com.itsallaboutvolley.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by siddheshs2 on 13-01-2016.
 */
public class EntityMapperRequestModel {

    @SerializedName("question")
    private String question;

    @SerializedName("choices")
    private List<String> choices;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
