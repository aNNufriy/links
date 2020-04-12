package ru.testfield.links.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    @Id
    private ObjectId _id;
    private String targetLink;
    private String shortLink;
    private String description;

    @JsonProperty("id")
    public String getHexString(){
        return this._id.toHexString();
    }
}
