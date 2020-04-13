package ru.testfield.links.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import ru.testfield.links.model.serializer.ObjectIdDeSerializer;
import ru.testfield.links.model.serializer.ObjectIdSerializer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    @JsonDeserialize(using = ObjectIdDeSerializer.class)
    private ObjectId _id;
    private String targetLink;

    private String shortLink;
    private String description;
}
