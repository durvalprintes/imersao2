package com.imersao.spring.sticker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Content {

    @JsonIgnore
    String id;

    String title;
    String urlImage;
    String rating;
}

