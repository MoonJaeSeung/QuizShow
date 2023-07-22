package com.example.gp.webSocket;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChallengeMessage {
    @JsonCreator
    public ChallengeMessage(@JsonProperty("sourceSessionId") String sourceSessionId, @JsonProperty("targetNick") String targetNick) {
        this.sourceSessionId = sourceSessionId;
        this.targetNick = targetNick;
    }

    private String sourceSessionId;
    private String targetNick;



    public String getSourceSessionId() {
        return sourceSessionId;
    }

    public void setSourceSessionId(String sourceSessionId) {
        this.sourceSessionId = sourceSessionId;
    }

    public String getTargetNick() {
        return targetNick;
    }

    public void setTargetNick(String targetNick) {
        this.targetNick = targetNick;
    }



}
