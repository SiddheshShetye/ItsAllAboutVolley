package aboutvolley.sidroid.com.itsallaboutvolley.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by siddheshs2 on 13-01-2016.
 */
public class EntityMapperResponseModel {

    @SerializedName("published_at")
    private String publishedAt;

    @SerializedName("question")
    private String question;

    @SerializedName("choices")
    private List<Choice> choices;

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getQuestion() {
        return question;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    private class Choice{

        @SerializedName("votes")
        private String votes;

        @SerializedName("choice")
        private String choice;

        public String getVotes() {
            return votes;
        }

        public String getChoice() {
            return choice;
        }
    }
}
