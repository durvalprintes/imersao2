package com.imersao.spring.sticker;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    @JsonIgnore
    String id;
    String title;
    String urlImage;
    String rating;
    @JsonIgnore
    String evaluation;
}

